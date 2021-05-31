package com.jijie.jjv9item.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jijie.v9.common.pojo.ResultBean;
import com.jj.api.IProductService;
import com.jj.entity.TProduct;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
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

/**
 * <p>Description: </p>
 *
 * @author jijie
 * @Date 2021/5/17 11:11
 */
@Controller
@RequestMapping("item")
public class ItemController {

    @Reference
    private IProductService productService;

    @Autowired
    private Configuration configuration;


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

        for (Long id : ids) {
            create(id);
        }
        return new ResultBean("200","批量生成页面成功");
    }
}
