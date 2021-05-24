package com.jj.mapper;

import com.jijie.v9.common.base.IBaseDao;
import com.jj.entity.TProduct;

public interface TProductMapper extends IBaseDao<TProduct> {
    int deleteByPrimaryKey(Long id);

    int insert(TProduct record);

    int insertSelective(TProduct record);

    TProduct selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TProduct record);

    int updateByPrimaryKey(TProduct record);
}