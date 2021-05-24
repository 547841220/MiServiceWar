package com.example.jjv9background.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jj.api.IProductTypeService;
import com.jj.entity.TProductType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>Description: </p>
 *
 * @author jijie
 * @Date 2021/5/17 11:11
 */
@RestController
@RequestMapping("productType")
public class ProductTypeController {

    @Reference
    private IProductTypeService productTypeService;

    @GetMapping("list")
    public List<TProductType> list(){
        return productTypeService.list();
    }

    @GetMapping("productType")
    public TProductType selectByPrimaryKeySelective(TProductType t){
        return new TProductType();
    }
}
