package com.jijie.jjv9productservice.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jijie.v9.common.base.BaseServiceImpl;
import com.jijie.v9.common.base.IBaseDao;
import com.jj.api.IProductService;
import com.jj.api.vo.ProductVO;
import com.jj.entity.TProduct;
import com.jj.entity.TProductDesc;
import com.jj.mapper.TProductDescMapper;
import com.jj.mapper.TProductMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * <p>Description: </p>
 *
 * @author jijie
 * @Date 2021/5/17 11:11
 */
@Service
public class ProductServiceImpl extends BaseServiceImpl<TProduct> implements IProductService {

    @Autowired
    private TProductMapper productMapper;

    @Autowired
    private TProductDescMapper productDescMapper;


    @Override
    public IBaseDao getBaseDao() {
        return productMapper;
    }

    @Override
    public PageInfo<TProduct> page(Integer pageIndex, Integer pageSize) {
        PageHelper.startPage(pageIndex,pageSize);
        List<TProduct> list = productMapper.list();
        PageInfo<TProduct> pageInfo = new PageInfo<TProduct>(list,3);
        return pageInfo;
    }

    @Override
    public Long add(ProductVO productVO) {
        //1.添加商品信息
        TProduct product = productVO.getProduct();
        //设置常规属性的值
        product.setFlag(true);
        product.setCreateTime(new Date());
        product.setUpdateTime(product.getCreateTime());
        product.setCreateUser(1L);
        product.setUpdateUser(product.getCreateUser());
        //设置主键回填
        productMapper.insertSelective(product);
        //2.添加商品描述信息
        TProductDesc productDesc = new TProductDesc();
        productDesc.setProductId(product.getId());
        productDesc.setpDesc(productVO.getProductDesc());
        productDescMapper.insertSelective(productDesc);
        //返回商品的id
        return product.getId();
    }
}
