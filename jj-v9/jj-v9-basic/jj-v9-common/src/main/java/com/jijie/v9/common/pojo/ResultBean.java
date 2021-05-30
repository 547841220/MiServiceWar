package com.jijie.v9.common.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>Description: 用来客户端的返回结果</p>
 *
 * @author jijie
 * @Date 2021/5/17 11:11
 */
@Data
@AllArgsConstructor
public class ResultBean<T> implements Serializable{

    //返回状态码
    private String statusCode;
    //成功之后，返回的数据
    private T data;

}
