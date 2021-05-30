package com.jijie.jjv9serarch.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jijie.api.ISearchService;
import com.jijie.v9.common.pojo.PageResultBean;
import com.jijie.v9.common.pojo.ResultBean;
import com.jj.entity.TProduct;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * <p>Description: SearchController</p>
 *
 * @author jijie
 * @Date 2021/5/17 11:11
 */
@Controller
@RequestMapping("search")
public class SearchController {

    @Reference
    private ISearchService searchService;

    //同步所有数据到solr
    @RequestMapping("synAllData")
    @ResponseBody
    public ResultBean synAllData() {
        return searchService.synAllData();
    }

    /**
     * 此接口适合于app端
     * 或者Ajax异步加载数据的方式
     *
     * @author jijie
     * @Date 2021/5/30 18:48
     */
    @RequestMapping("queryByKeyWords")
    @ResponseBody
    public ResultBean queryByKeyWords(String keywords){
        return searchService.queryByKeywords(keywords);
    }

    //log4j for
    @RequestMapping("queryByKeyWords4PC/{pageIndex}/{pageSize}")
    public String queryByKeyWords4PC(String keywords, Model model,
                                     @PathVariable("pageIndex") Integer pageIndex,
                                     @PathVariable("pageSize") Integer pageSize) {
        ResultBean resultBean = searchService.queryByKeywords(keywords,pageIndex,pageSize);
        if ("200".equals(resultBean.getStatusCode())){

            //List<TProduct> list = (List<TProduct>)resultBean.getData();
            PageResultBean<TProduct> pageResultBean = (PageResultBean<TProduct>) resultBean.getData();
            model.addAttribute("page",pageResultBean);
        }
        return "list";
    }
}
