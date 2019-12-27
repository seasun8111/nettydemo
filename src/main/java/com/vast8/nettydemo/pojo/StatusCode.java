package com.vast8.nettydemo.pojo;

import lombok.Getter;

/**
 * @author Dingyuan
 * @version 1.0
 * @date 2019/10/25 11:15
 */
public enum StatusCode {


    SUCCESS(200, "SUCCESS"),
    FAILED(500, "FAILED");


    @Getter
    private int code;
    @Getter
    private String info;

    StatusCode(int code, String info) {
        this.code = code;
        this.info = info;
    }

    @Override
    public String toString() {
        return String.valueOf(this.getCode());
    }

}
