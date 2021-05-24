package com.jj.api;

import com.github.pagehelper.PageInfo;
import com.jijie.v9.common.base.IBaseService;
import com.jj.api.vo.ProductVO;
import com.jj.entity.TProduct;

/**
 * <p>Description: </p>
 *
 * @author jijie
 * @Date 2021/5/17 11:11
 */
public interface IProductService extends IBaseService<TProduct> {


    PageInfo<TProduct> page(Integer pageIndex, Integer pageSize);


    Long add(ProductVO productVO);
}
