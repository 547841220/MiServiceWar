package com.jijie.jjv9productservice.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.jijie.v9.common.base.BaseServiceImpl;
import com.jijie.v9.common.base.IBaseDao;
import com.jj.api.IProductTypeService;
import com.jj.entity.TProductType;
import com.jj.mapper.TProductTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>Description: </p>
 *
 * @author jijie
 * @Date 2021/5/17 11:11
 */
@Service
public class ProductTypeServiceImpl extends BaseServiceImpl<TProductType> implements IProductTypeService {

    @Autowired
    private TProductTypeMapper productTypeMapper;

    @Override
    public IBaseDao<TProductType> getBaseDao() {
        return productTypeMapper;
    }



}
