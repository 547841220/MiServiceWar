package com.jijie.jjv9searchservice.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.jijie.api.ISearchService;
import com.jijie.v9.common.pojo.PageResultBean;
import com.jijie.v9.common.pojo.ResultBean;
import com.jj.entity.TProduct;
import com.jj.mapper.TProductMapper;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>Description: ISearchService实现类</p>
 *
 * @author jijie
 * @Date 2021/5/17 11:11
 */
@Service
public class SearchServiceImpl implements ISearchService {

    @Autowired
    private TProductMapper productMapper;

    @Autowired
    private SolrClient solrClient;

    @Override
    public ResultBean synAllData() {
        //1.查询源数据
        //查询所有字段，有必要么？合理吗？
        //TODO 只查询需要的字段
        List<TProduct> list = productMapper.list();
        //2.mysql-->solr
        for (TProduct product:list) {
            //product--->document
            SolrInputDocument document = new SolrInputDocument();
            //2.创建相关属性值
            document.setField("id",product.getId());
            document.setField("product_name",product.getName());
            document.setField("product_price",product.getPrice());
            document.setField("product_sale_point",product.getSalePoint());
            document.setField("product_image",product.getImage());
            //3.保存
            try {
                solrClient.add(document);
            } catch (SolrServerException | IOException e) {
                e.printStackTrace();
                //TODO log
                return new ResultBean("500","同步数据失败");
            }
        }
        try {
            solrClient.commit();
        } catch (SolrServerException | IOException e) {
            e.printStackTrace();
            //TODO log
            return new ResultBean("500","同步数据失败");
        }
        return new ResultBean("200","同步数据成功");
    }

    @Override
    public ResultBean synById(long id) {
        //1.查询源数据
        TProduct product = productMapper.selectByPrimaryKey(id);
        //2.mysql-->solr
        //product--->document
        SolrInputDocument document = new SolrInputDocument();
        //2.创建相关属性值
        document.setField("id",product.getId());
        document.setField("product_name",product.getName());
        document.setField("product_price",product.getPrice());
        document.setField("product_sale_point",product.getSalePoint());
        document.setField("product_image",product.getImage());
        //3.保存
        try {
            solrClient.add(document);
            solrClient.commit();
        } catch (SolrServerException | IOException e) {
            e.printStackTrace();
            //TODO log
            return new ResultBean("500","同步数据失败");
        }

        return new ResultBean("200","同步数据成功");
    }

    @Override
    public ResultBean delById(long id) {
        try {
            solrClient.deleteById(String.valueOf(id));
            solrClient.commit();
        } catch (SolrServerException | IOException e) {
            e.printStackTrace();
            //TODO log
            return new ResultBean("500","删除数据失败");
        }
        return new ResultBean("200","删除数据成功");
    }

    @Override
    public ResultBean queryByKeywords(String keywords) {
        //1.组装查询条件
        SolrQuery queryCondition = new SolrQuery();
        if (keywords == null || "".equals(keywords)){
            //如果搜索关键词不存在，默认给个
            queryCondition.setQuery("product_name:华为");

        }else{
            queryCondition.setQuery("product_name:"+keywords);
        }
        //add1 添加高亮的效果，product_name添加高亮效果
        queryCondition.setHighlight(true);
        queryCondition.addHighlightField("product_name");
        queryCondition.setHighlightSimplePre("<font color='red'>");
        queryCondition.setHighlightSimplePost("</font>");

        //2.执行查询,获取结果，并将结果转化为List
        List<TProduct> list = null;
        try {
            QueryResponse response = solrClient.query(queryCondition);
            SolrDocumentList results = response.getResults();
            //results-->list
            list = new ArrayList<>(results.size());
            //add2 获取高亮的信息
            Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
            //搜的记录结果有多条
            //外层Map kye(String) --1001
            //外层Map value --1001这条记录对应的高亮信息

            //为啥里层又是一个map?
            //因为添加一条高亮记录对应的高亮字段可能是对个
            //里层的map，key（String）对应的字段名，value，字段对应额高亮信息

            for (SolrDocument result : results) {
                TProduct product = new TProduct();
                product.setId(Long.parseLong(result.getFieldValue("id").toString()));

                //product.setName(result.getFieldValue("product_name").toString());
                product.setPrice(Long.parseLong(result.getFieldValue("product_price").toString()));
                product.setSalePoint(result.getFieldValue("product_sale_point").toString());
                product.setImage(result.getFieldValue("product_image").toString());
                //todo 我只需要4个字段，但你给我传递一个对象，有点不合适。

                //设置product_name高亮信息
                //1.获取到当前记录的高亮信息
                Map<String, List<String>> higlight = highlighting.get(result.getFieldValue("id").toString());
                //2.获取字段对应的高亮信息
                List<String> productNameHiglight = higlight.get("product_name");
                //3.判断该字段是否有高亮信息
                if (productNameHiglight != null && productNameHiglight.size() > 0) {
                    //高亮信息
                    product.setName(productNameHiglight.get(0));
                }else{
                    //普通的文本信息
                    product.setName(result.getFieldValue("product_name").toString());
                }
                list.add(product);
            }
        } catch (SolrServerException | IOException e) {
            e.printStackTrace();
            //TODO log
            return new ResultBean("500","同步数据失败");
        }

        return new ResultBean("200",list);
    }

    @Override
    public ResultBean queryByKeywords(String keywords, Integer pageIndex, Integer pageSize) {
        //1.组装查询条件
        SolrQuery queryCondition = new SolrQuery();
        if (keywords == null || "".equals(keywords)){
            //如果搜索关键词不存在，默认给个
            queryCondition.setQuery("product_name:华为");

        }else{
            queryCondition.setQuery("product_name:"+keywords);
        }
        //add1 添加高亮的效果，product_name添加高亮效果
        queryCondition.setHighlight(true);
        queryCondition.addHighlightField("product_name");
        queryCondition.setHighlightSimplePre("<font color='red'>");
        queryCondition.setHighlightSimplePost("</font>");

        //添加分页的东西
        //从哪个下标开始
        //比如要查看  第三页，每页5条记录，pageIndex=3,pageSize=5;
        //pageIndex-1 = 2,2*5 = 10,从第10开始展示5条。而排列是从0开始的，所以10就是对应的第11个元素。没毛病
        queryCondition.setStart((pageIndex-1)*pageSize);
        //查看几条
        queryCondition.setRows(pageSize);
        //2.执行查询,获取结果，并将结果转化为List
        List<TProduct> list = null;

        //list--->PageResultBean
        PageResultBean<TProduct> pageResultBean = new PageResultBean<>();
        //总记录数
        long total = 0L;


        try {
            QueryResponse response = solrClient.query(queryCondition);
            SolrDocumentList results = response.getResults();
            //results-->list
            list = new ArrayList<>(results.size());
            //add2 获取高亮的信息
            Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
            //搜的记录结果有多条
            //外层Map kye(String) --1001
            //外层Map value --1001这条记录对应的高亮信息

            //为啥里层又是一个map?
            //因为添加一条高亮记录对应的高亮字段可能是对个
            //里层的map，key（String）对应的字段名，value，字段对应额高亮信息

            //总记录数
            total = results.getNumFound();
            //
            pageResultBean.setTotal(total);
            for (SolrDocument result : results) {
                TProduct product = new TProduct();
                product.setId(Long.parseLong(result.getFieldValue("id").toString()));

                //product.setName(result.getFieldValue("product_name").toString());
                product.setPrice(Long.parseLong(result.getFieldValue("product_price").toString()));
                product.setSalePoint(result.getFieldValue("product_sale_point").toString());
                product.setImage(result.getFieldValue("product_image").toString());
                //todo 我只需要4个字段，但你给我传递一个对象，有点不合适。

                //设置product_name高亮信息
                //1.获取到当前记录的高亮信息
                Map<String, List<String>> higlight = highlighting.get(result.getFieldValue("id").toString());
                //2.获取字段对应的高亮信息
                List<String> productNameHiglight = higlight.get("product_name");
                //3.判断该字段是否有高亮信息
                if (productNameHiglight != null && productNameHiglight.size() > 0) {
                    //高亮信息
                    product.setName(productNameHiglight.get(0));
                }else{
                    //普通的文本信息
                    product.setName(result.getFieldValue("product_name").toString());
                }
                list.add(product);
            }
        } catch (SolrServerException | IOException e) {
            e.printStackTrace();
            //TODO log
            return new ResultBean("500","同步数据失败");
        }
        //给pageResultBean赋值
        pageResultBean.setList(list);
        pageResultBean.setPageNum(pageIndex);
        pageResultBean.setPageSize(pageSize);
        pageResultBean.setTotal(total);
        pageResultBean.setPages((int) (total%pageSize==0?total/pageSize:(total/pageSize)+1));
        pageResultBean.setPageNum(3);

        return new ResultBean("200",pageResultBean);
    }
}
