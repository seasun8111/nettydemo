package com.vast8.nettydemo.serialize;

import com.alibaba.fastjson.JSON;

/**
 * @author Dingyuan
 * @version 1.0
 * @date 2019/10/25 11:34
 */
public class JSONSerializer {


    public byte[] serialize(Object object) {
        return JSON.toJSONBytes(object);
    }


    public <T> T deserialize(Class<T> clazz, byte[] bytes) {
        return JSON.parseObject(bytes,clazz);
    }
}
