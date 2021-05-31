package com.jijie.v9.common.base;

import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * <p>Description: </p>
 *
 * @author jijie
 * @Date 2021/5/17 11:11
 */
public interface IBaseService<T> {

    int deleteByPrimaryKey(Long id);

    int insert(T t);

    int insertSerlective(T t);

    T selectByPrimaryKeySelective(T t);

    T selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(T t);

    int updateByPrimaryKey(T t);

    List<T> list();

    PageInfo<T> page(Integer pageIndex, Integer pageSize);
}
