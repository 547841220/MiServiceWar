package com.example.jjv9background;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import com.jj.api.IProductTypeService;
import com.jj.entity.TProductType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.awt.*;
import java.util.List;

@SpringBootTest
@EnableDubbo
public class JjV9BackgroundApplicationTests {

    @Reference
    private IProductTypeService productTypeService;

    @Test
    public void contextLoads() {
        List<TProductType> typeList =  productTypeService.list();
        for (TProductType t:typeList){
            System.out.println(t.getName());
        }
    }


}
