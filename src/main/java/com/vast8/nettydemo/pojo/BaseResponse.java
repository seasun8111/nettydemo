package com.vast8.nettydemo.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * @author Dingyuan
 * @version 1.0
 * @date 2019/10/25 11:08
 */
@Data
public class BaseResponse<T> implements Serializable {

    int code;
    StatusCode info;
    T data;

}
