package com.jijie.jjv9item;

import com.jijie.jjv9item.entity.Product;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

@SpringBootTest
@RunWith(SpringRunner.class)
class JjV9ItemApplicationTests {

    @Autowired
    private Configuration configuration;

    @Test
    void createHtmlTest() throws IOException, TemplateException {
        //模板+数据=输出
        //1.获取到模板对象
        Template template = configuration.getTemplate("freemarker.ftl");
        //2.组装数据
        Map<String,Object> data = new HashMap<>();
        data.put("username","ftl");
        //保存对象
        Product product = new Product("3天",1999L,new Date());
        data.put("product",product);
        //保存集合
        List<Product> list = new ArrayList<>();
        list.add(new Product("1天",19999L,new Date()));
        list.add(new Product("7天",888L,new Date()));
        data.put("list",list);
        //保存当前的财富
        data.put("money",100);
        //3.模板+数据结合
        FileWriter fileWriter = new FileWriter(
                "D:\\微服务实战\\MiServiceWar\\jj-v9\\jj-v9-web\\jj-v9-item\\src\\main\\resources\\static\\f.html");
        template.process(data,fileWriter);
        System.out.println("生成静态页面成功");
    }

}
