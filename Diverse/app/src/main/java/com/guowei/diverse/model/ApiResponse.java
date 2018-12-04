package com.guowei.diverse.model;

import com.google.gson.annotations.SerializedName;

public class ApiResponse<T> {

    @SerializedName("errorCode")
    public int errorCode;
    @SerializedName("errorMsg")
    public String errorMsg;
    @SerializedName("data")
    public T data;

}