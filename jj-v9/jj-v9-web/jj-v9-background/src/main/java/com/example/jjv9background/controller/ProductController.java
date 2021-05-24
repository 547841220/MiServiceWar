package com.example.jjv9background.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.jj.api.IProductService;
import com.jj.api.vo.ProductVO;
import com.jj.entity.TProduct;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * <p>Description: </p>
 *
 * @author jijie
 * @Date 2021/5/17 11:11
 */
@Controller
@RequestMapping("product")
public class ProductController {

    @Reference
    private IProductService productService;

    @GetMapping("list")
    public String list(Model model) {
        List<TProduct> list = productService.list();
        model.addAttribute("list",list);
        return "product/list";
    }

    @GetMapping("page/{pageIndex}/{pageSize}")
    public String list(Model model,
                       @PathVariable("pageIndex") Integer pageIndex,
                       @PathVariable("pageSize") Integer pageSize) {

        PageInfo<TProduct> pageInfo = productService.page(pageIndex,pageSize);
        model.addAttribute("pageInfo",pageInfo);
        return "product/list";
    }

    @PostMapping("add")
    public String add(ProductVO productVO){
        //
        //productService.add(productVO);
        //productService.add(productVO.getProduct(),productVO.getProductDesc());
        //返回添加后的商品ID
        Long productId = productService.add(productVO);
        //商品id后续有用
        return "redirect:/product/page/1/1";
    }
}
