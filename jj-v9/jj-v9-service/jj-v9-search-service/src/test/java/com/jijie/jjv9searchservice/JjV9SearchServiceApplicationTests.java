package com.jijie.jjv9searchservice;

import com.jijie.api.ISearchService;
import com.jijie.v9.common.pojo.ResultBean;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@SpringBootTest
@RunWith(SpringRunner.class)
public class JjV9SearchServiceApplicationTests {

    @Autowired
    private SolrClient solrClient;

    @Autowired
    private ISearchService searchService;

    @Test
    public void synAllDataTest() {
        ResultBean resultBean = searchService.synAllData();
        System.out.println(resultBean.getStatusCode()+"------"+resultBean.getData());

    }

    //增，改，改的话就是不动id。
    @Test
    public void addOrUpdateTest() throws SolrServerException, IOException {
        //1.创建一个document对象
        SolrInputDocument document = new SolrInputDocument();
        //2.创建相关属性值
        document.setField("id",1);
        document.setField("product_name","小米9");
        document.setField("product_price",9999);
        document.setField("product_sale_point","全球最高像素的手机");
        document.setField("product_images","123");
        //3.保存
        solrClient.add(document);
        solrClient.commit();
    }
    //查。这里是条件查询
    @Test
    public void queryTest() throws SolrServerException, IOException {
        //1.组装查询条件
        SolrQuery queryCondition = new SolrQuery();
        //会分词
        queryCondition.setQuery("product_name:小米9");
        //2.执行查询
        QueryResponse response = solrClient.query(queryCondition);
        //3.得到结果
        SolrDocumentList solrDocuments = response.getResults();
        for (SolrDocument document:solrDocuments) {

        }
    }
    //删除，可以精确删除，也可以条件删除
    public void delTest() throws SolrServerException, IOException {
        //精确删除，根据id
        // solrClient.deleteById("1");
        //条件删除
        solrClient.deleteByQuery("product_name:小米9");
        solrClient.commit();

    }

}
