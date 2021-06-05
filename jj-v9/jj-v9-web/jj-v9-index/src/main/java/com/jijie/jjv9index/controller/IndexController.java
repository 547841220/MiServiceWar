package com.jijie.jjv9index.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jijie.v9.common.pojo.ResultBean;
import com.jj.api.IProductTypeService;
import com.jj.entity.TProductType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * <p>Description: </p>
 *
 * @author jijie
 * @Date 2021/5/17 11:11
 */
@Controller
@RequestMapping("index")
public class IndexController {

    @Reference
    private IProductTypeService productTypeService;


    @RequestMapping("show")
    public String showIndex(Model model) {
        //1.获取到数据
        List<TProductType> list = productTypeService.list();
        //2.传到前端页面展示
        model.addAttribute("list", list);
        return "index";

    }

    @RequestMapping("listType")
    @ResponseBody
    public ResultBean listType() {
        //1.获取到数据
        List<TProductType> list = productTypeService.list();
        //2.封装返回
        return new ResultBean("200", list);
    }

}

    /*public static void main(String[] args) {
        MyThread thread = new MyThread(); //创建状态
        //正确的启动线程方式
        //thread.run();//只是调用方法，并非开启新线程。
        thread.start();//就绪状态

        MyTask task = new MyTask();
        //task.start(); //并不能直接以线程的方式来启动。
        //它表达的是一个任务，需要启动一个线程来执行。
        new Thread(task).start();
    }
}
//1.继承Thread类
class MyThread extends Thread{

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+"：running.....");
    }

}
//2.实现Runnable，只是创建了一个可执行任务，并不是开启一个线程。
class MyTask implements Runnable{

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+":running....");
    }
}
//3.实现Callable
class Mytask2 implements Callable{

    @Override
    public Object call() throws Exception {
        return null;
    }
}*/