package com.jijie.jjv9item.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

/**
 * <p>Description: </p>
 *
 * @author jijie
 * @Date 2021/5/17 11:11
 */
@Data
@AllArgsConstructor
public class Product {
    private String name;
    private Long price;

    private Date createTime;
}
