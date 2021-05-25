package com.jijie.v9.common.pojo;

/**
 * <p>Description: 封装批量上传图片的返回结果</p>
 *
 * @author jijie
 * @Date 2021/5/17 11:11
 */
public class MultiUploadBean {

    private String errno;
    private String[] data;

    public String getErrno() {
        return errno;
    }

    public void setErrno(String errno) {
        this.errno = errno;
    }

    public String[] getData() {
        return data;
    }

    public void setData(String[] data) {
        this.data = data;
    }
}
