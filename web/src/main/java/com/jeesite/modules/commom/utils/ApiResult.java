package com.jeesite.modules.commom.utils;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

public class ApiResult<T> implements Serializable {

    private static final long serialVersionUID = 7862943413756060973L;
    private String code = "-1";
    private String msg = "failure";
    private T data;
    private JSONObject validateInfo;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public JSONObject getValidateInfo() {
        return validateInfo;
    }

    public void setValidateInfo(JSONObject validateInfo) {
        this.validateInfo = validateInfo;
    }

    public boolean isSuccess() {
        return null != code && "200".equals(code);
    }
}
