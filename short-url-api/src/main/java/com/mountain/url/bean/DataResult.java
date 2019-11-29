package com.mountain.url.bean;

import java.io.Serializable;

public class DataResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final int CODE_SUCCESS = 0;
    public static final int CODE_FAIL = -99;

    private int successCode = CODE_SUCCESS;
    private int code;
    private T data;
    private String msg = "";

    public DataResult() {
    }

    /**
     * 初始化一个失败的DataBean
     *
     * @param msg
     */
    public DataResult(String msg) {
        this.code = CODE_FAIL;
        this.msg = msg;
    }

    /**
     * 初始化一个给定code的DataBean
     *
     * @param code
     */
    public DataResult(int code) {
        this.code = code;
    }

    public DataResult(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static <T> DataResult<T> getSuccessData(T data) {
        DataResult<T> obj = new DataResult<>(CODE_SUCCESS);
        obj.setSuccessData(data);
        return obj;
    }

    public boolean isSuccessCode() {
        return this.code == successCode;
    }

    public DataResult<T> setData(T data) {
        this.data = data;
        return this;
    }

    public DataResult<T> setData(int code, T data) {
        this.code = code;
        this.data = data;
        return this;
    }

    public DataResult<T> setSuccessData(T data) {
        this.code = CODE_SUCCESS;
        this.data = data;
        return this;
    }

    public DataResult<T> setSuccessData(int code, T data) {
        this.code = code;
        this.successCode = code;
        this.data = data;
        return this;
    }

    public DataResult<T> setMsg(String msg) {
        this.code = CODE_FAIL;
        this.msg = msg;
        return this;
    }

    public DataResult<T> setMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
        return this;
    }

    public void setCode(int successCode) {
        this.successCode = successCode;
        this.code = successCode;
    }

    public String getMsg() {
        return msg;
    }

    public int getCode() {
        return code;
    }

    public T getData() {
        return data;
    }
}
