package com.jijie.jjv9item.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jijie.v9.common.pojo.ResultBean;
import com.jj.api.IProductService;
import com.jj.entity.TProduct;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * <p>Description: </p>
 *
 * @author jijie
 * @Date 2021/5/17 11:11
 */
@Controller
@RequestMapping("item")
@Slf4j
public class ItemController {

    @Reference
    private IProductService productService;

    @Autowired
    private Configuration configuration;

    @Autowired
    private ThreadPoolExecutor pool;


    @RequestMapping("createById/{id}")
    @ResponseBody
    public ResultBean createById(@PathVariable("id") Long id) throws IOException, TemplateException {
        create(id);
        return new ResultBean("200","生成静态页面成功");

    }

    private void create(Long id) throws IOException, TemplateException {
        //1.根据id,获取到商品火速据
        TProduct product = productService.selectByPrimaryKey(id);
        //2.采用Freemarker生成对应的商品详情页
        Template template = configuration.getTemplate("item.ftl");
        Map<String,Object> data = new HashMap<>();
        data.put("product",product);
        //3.输出
        //这里会有问题
        //我们目前是跑在本地的，生成的文件生成在我们本地电脑，那将来在服务器上呢？还有d盘吗？
        //所以这里我们要获取到项目运行时的路径。--static的路径
        String serverpath = ResourceUtils.getURL("classpath:static").getPath();
        StringBuilder path = new StringBuilder(serverpath).append(File.separator).append(id).append(".html");

        FileWriter out = new FileWriter(path.toString());
        template.process(data,out);
    }

    /**
     * <p>Description: 批量生成</p>
     *
     * @author jijie
     * @Date 2021/6/1 0:11
     */
    @RequestMapping("batchCreate")
    @ResponseBody
    public ResultBean batchCreate(@RequestParam List<Long> ids) throws TemplateException, IOException {


        //有没有什么优化的想法
        //1.放缓存，减少数据库查询
        //2.其中某个出错了，所有都出错
        //3.很重要的问题
        //16核服务器，8g内存
        //但这种写法用单核就行了。

        //此处代码是一个单线程的工作模式 现在要1000个页面
        //批量生成页面：不需要关注先后的生成顺序，1-1000 1000.html,1.html，对顺序没有要求，可以使用多线程
        //多线程 16*2=32
        //1.线程池的知识点（线程数？为什么这样呢？）
        //2.如何创建线程
        List<Future<Long>> results = new ArrayList<>(ids.size());
        for (Long id : ids) {
            //调用方法生成页面---创建一个线程来执行任务，
            //优化：串行---》并行
            //create(id);
            //需要返回结果，我们需要用submit方法。线程实现的是callable接口
            results.add(pool.submit(new CreateHTMLTask(id)));
            //不需要返回结果，执行excute方法。任务实现的是runnable接口
            //pool.execute();
        }

        //后续根据处理结果进行处理
        List<Long> errors = new ArrayList<>();
        for (Future<Long> future : results) {
            try {
                //获取执行结果的时候，是一个阻塞等待的状态。
                Long result = future.get();
                if (result != 0) {
                    errors.add(result);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        for (Long error : errors) {
            //做错误处理的工作
            //1.输出日志
            log.error("批量生成页面失败，失败的页面为[{}]",errors);
            //2.将处理错误的id信息保存到日志表中
            //id product_id retry_times create_update update_time
            //1   1         0           2020           2020

            //3.通过定时任务，扫描这张表
            //select * from t_create_html_log where retry_time<3;
            //update retry_time = retry_time+1

            //4.超过3次的记录，需要人工介入。
        }

        return new ResultBean("200","批量生成页面成功");
        //TODO 还有什么地方需要改进的？ -- 目前实现的是每启动一个线程进入一个线程池
    }

    private class  CreateHTMLTask implements Callable<Long> {

        private Long id;

        public CreateHTMLTask(Long id) {
            this.id = id;
        }

        /**
         * 如果执行成功，则返回0
         * 如果执行失败，返回当前记录的id
         *
         *
         */
        @Override
        public Long call() throws TemplateException {
            //1.根据id,获取到商品火速据
            TProduct product = productService.selectByPrimaryKey(id);
            //2.采用Freemarker生成对应的商品详情页
            Template template = null;
            try {
                template = configuration.getTemplate("item.ftl");
                Map<String,Object> data = new HashMap<>();
                data.put("product",product);
                //3.输出
                //这里会有问题
                //我们目前是跑在本地的，生成的文件生成在我们本地电脑，那将来在服务器上呢？还有d盘吗？
                //所以这里我们要获取到项目运行时的路径。--static的路径
                String serverpath = ResourceUtils.getURL("classpath:static").getPath();
                StringBuilder path = new StringBuilder(serverpath).append(File.separator).append(id).append(".html");

                FileWriter out = new FileWriter(path.toString());
                template.process(data,out);
            } catch (IOException | TemplateException e) {
                //如果我们针对不同的异常，处理方式是不同的，那么就应该分别catch
                e.printStackTrace();
                return id;
            }
            return 0L;
        }
    }

    /*public static void main(String[] args) {
        //单例和定长线程池，等待队列默认都太长了，可能会导致oom,oom，内存要求过大，会导致堆内存溢出。
        //单例线程池
        ExecutorService pool1 = Executors.newSingleThreadExecutor();//阿里规约不推荐这种默认创建。推荐手动创建。因为默认是new ThreadPoolExecutor(--初始线程数 1,最大线程数 1,--最大发呆时间-0L, --时间单位 TimeUnit.MILLISECONDS,--等待的队列长度 new LinkedBlockingQueue<Runnable>()));
        //定长线程池
        ExecutorService pool2 = Executors.newFixedThreadPool(10);//阿里规约不推荐这种默认创建。推荐手动创建。因为默认是new ThreadPoolExecutor(--初始线程数 10,最大线程数 10,--最大发呆时间-0L, --时间单位 TimeUnit.MILLISECONDS,--等待的队列长度 new LinkedBlockingQueue<Runnable>()));

        //CachedThreadPool--根据需要而创建的线程池
        // 和newScheduledThreadPool 创建一个定长线程池，支持定时及周期性任务执行。，
        // 允许创建的线程数量为Integer.MAX_VALUE，可能会创建大量线程，从而导致oom--oom是程序申请内存太大，满足不了。就会报Oom异常，内存不足。
        ExecutorService pool3 = Executors.newCachedThreadPool();//阿里规约不推荐这种默认创建。推荐手动创建。因为默认是new ThreadPoolExecutor(--初始线程数 0,最大线程数 Integer.MAX_VALUE--会把线程池干爆,--最大发呆时间-60L, --时间单位 TimeUnit.SECONDS,--等待的队列长度 new SynchronousQueue<Runnable>()));
        ExecutorService pool4 = Executors.newScheduledThreadPool(10);
    }*/

    /*public static void main(String[] args) {
        //1.获取当前服务器cpu核数
        int processors = Runtime.getRuntime().availableProcessors();
        System.out.println(processors);//12核
        //2.
    }*/
}
