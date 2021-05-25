package com.jijie.v9.common.pojo;

/**
 * <p>Description: 用来描述服务器给客户端的返回结果</p>
 *
 * @author jijie
 * @Date 2021/5/17 11:11
 */
public class ResultBean {

    //返回状态码
    private Integer statusCode;
    //成功之后，返回的数据
    private String data;
    //失败之后，返回的错误信息
    private String msg;

    public static ResultBean success(String data) {
        ResultBean resultBean = new ResultBean();
        resultBean.setStatusCode(200);
        resultBean.setData(data);
        return resultBean;
    }

    public static ResultBean error(String msg) {
        ResultBean resultBean = new ResultBean();
        resultBean.setStatusCode(500);
        resultBean.setMsg(msg);
        return resultBean;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
