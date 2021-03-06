package com.jj.mapper;

import com.jijie.v9.common.base.IBaseDao;
import com.jj.entity.TProductType;

public interface TProductTypeMapper extends IBaseDao<TProductType> {
    int deleteByPrimaryKey(Long id);

    int insert(TProductType record);

    int insertSelective(TProductType record);

    TProductType selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TProductType record);

    int updateByPrimaryKey(TProductType record);
}