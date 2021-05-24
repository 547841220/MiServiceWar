package com.jj.mapper;

import com.jijie.v9.common.base.IBaseDao;
import com.jj.entity.TProductDesc;

public interface TProductDescMapper extends IBaseDao<TProductDesc> {
    int deleteByPrimaryKey(Long id);

    int insert(TProductDesc record);

    int insertSelective(TProductDesc record);

    TProductDesc selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TProductDesc record);

    int updateByPrimaryKeyWithBLOBs(TProductDesc record);

    int updateByPrimaryKey(TProductDesc record);
}