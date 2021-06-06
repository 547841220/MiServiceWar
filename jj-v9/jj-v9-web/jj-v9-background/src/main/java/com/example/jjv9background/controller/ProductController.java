package com.example.jjv9background.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.jijie.v9.common.constant.MQConstant;
import com.jj.api.IProductService;
import com.jj.api.vo.ProductVO;
import com.jj.entity.TProduct;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private RabbitTemplate rabbitTemplate;

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
        //处理核心业务逻辑T1
        //productService.add(productVO);
        //productService.add(productVO.getProduct(),productVO.getProductDesc());
        //返回添加后的商品ID
        Long newId = productService.add(productVO);
        //去调用其他系统的接口
        //java 调用http接口 Apache HttpClient
        //http://localhost:9093/item/createById/10 --更新详情系统 //T2
        //http://localhost:9092/search/synAllData  --更新搜索系统 //T3

        //新方案，消息中间件
        //发送一个消息。
        rabbitTemplate.convertAndSend(MQConstant.EXCHANGE.BACKGROUND_PRODUCT_EXCHANGE,"product.add",newId);
        //商品id后续有用
        return "redirect:/product/page/1/1";
    }
}
