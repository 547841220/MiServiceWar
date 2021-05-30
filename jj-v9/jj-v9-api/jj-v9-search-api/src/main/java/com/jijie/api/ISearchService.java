package com.jijie.api;

import com.jijie.v9.common.pojo.ResultBean;

/**
 * <p>Description: search服务</p>
 *
 * @author jijie
 * @Date 2021/5/17 11:11
 */
public interface ISearchService {

    /**
     * <p>Description:全量同步，数据初始化的时候使用 </p>
     *
     * @author jijie
     * @Date 2021/5/30 17:07
     */
    public ResultBean synAllData();

    /**
     * <p>Description:根据id增量添加 </p>
     *
     * @author jijie
     * @Date 2021/5/30 17:08
     */
    public ResultBean synById(long id);

    /**
     * <p>Description:根据id删除 </p>
     *
     * @author jijie
     * @Date 2021/5/30 17:09
     */
    public ResultBean delById(long id);

    /**
     * <p>Description: 查询操作</p>
     *
     * @author jijie
     * @Date 2021/5/30 17:09
     */
    public ResultBean queryByKeywords(String keywords);

    //体现了方法重载的思想
    ResultBean queryByKeywords(String keywords, Integer pageIndex, Integer pageSize);
}