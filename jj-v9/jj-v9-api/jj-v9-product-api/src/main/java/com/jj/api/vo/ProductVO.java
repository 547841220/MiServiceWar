package com.jj.api.vo;

import com.jj.entity.TProduct;

import java.io.Serializable;

/**
 * <p>Description: ProductVO</p>
 *
 * @author jijie
 * @Date 2021/5/17 11:11
 */
public class ProductVO implements Serializable {

    private TProduct product;

    private String productDesc;

    public TProduct getProduct() {
        return product;
    }

    public void setProduct(TProduct product) {
        this.product = product;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }
}
