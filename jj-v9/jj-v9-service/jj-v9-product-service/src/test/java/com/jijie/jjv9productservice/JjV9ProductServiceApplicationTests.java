package com.jijie.jjv9productservice;

import com.github.pagehelper.PageInfo;
import com.jj.api.IProductService;
import com.jj.api.IProductTypeService;
import com.jj.entity.TProduct;
import com.jj.entity.TProductType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

@SpringBootTest
public class JjV9ProductServiceApplicationTests {

    @Resource
    private IProductService productService;
    @Resource
    private IProductTypeService productTypeService;

    @Autowired
    private DataSource dataSource;

    @Test
    public void contextLoads() {

    }
    @Test
    public void poolTest() throws SQLException {
        System.out.println(dataSource.getConnection());
    }
    @Test
    public void pageTest() {
        PageInfo<TProduct> page = productService.page(1,1);
        System.out.println(page.getList().size());
    }

}
