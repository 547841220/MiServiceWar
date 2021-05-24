package com.jijie.jjv9productservice;

import com.jj.api.IProductService;
import com.jj.api.IProductTypeService;
import com.jj.entity.TProduct;
import com.jj.entity.TProductType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
public class JjV9ProductServiceApplicationTests {

    @Resource
    private IProductService productService;
    @Resource
    private IProductTypeService productTypeService;

    @Test
    public void contextLoads() {

    }

}
