package com.jijie.v9.common.base;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * <p>Description: </p>
 *
 * @author jijie
 * @Date 2021/5/17 11:11
 */
public abstract class BaseServiceImpl<T> implements IBaseService<T>{

    public abstract IBaseDao<T> getBaseDao();

    @Override
    public int deleteByPrimaryKey(Long id) {
        return getBaseDao().deleteByPrimaryKey(id);
    }

    @Override
    public int insert(T t) {
        return getBaseDao().insert(t);
    }

    @Override
    public int insertSerlective(T t) {
        return getBaseDao().insertSerlective(t);
    }

    @Override
    public T selectByPrimaryKeySelective(T t) {
        return getBaseDao().selectByPrimaryKeySelective(t);
    }

    public T selectByPrimaryKey(Long id) {
        return getBaseDao().selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(T t) {
        return getBaseDao().updateByPrimaryKeySelective(t);
    }

    @Override
    public int updateByPrimaryKey(T t) {
        return getBaseDao().updateByPrimaryKey(t);
    }

    @Override
    public List<T> list() {
        return getBaseDao().list();
    }

    @Override
    public PageInfo<T> page(Integer pageIndex, Integer pageSize) {
        PageHelper.startPage(pageIndex,pageSize);
        List<T> list = this.list();
        PageInfo<T> pageInfo = new PageInfo<T>(list,3);
        return pageInfo;
    }
}
