package com.jijie.jjv9index.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jj.api.IProductTypeService;
import com.jj.entity.TProductType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

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
        model.addAttribute("list",list);
        return "index";

    }
}