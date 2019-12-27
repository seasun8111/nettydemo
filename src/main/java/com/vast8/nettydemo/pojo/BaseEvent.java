/**
 * Copyright 2019 bejson.com
 */
package com.vast8.nettydemo.pojo;

/**
 * Auto-generated: 2019-10-25 10:31:15
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class BaseEvent {

    private String appId;
    private long seq;
    private String eventName;
    private EventBody eventBody;
    private DeviceInfo deviceInfo;
    private long deviceTimestamp;
    public void setAppId(String appId) {
        this.appId = appId;
    }
    public String getAppId() {
        return appId;
    }

    public void setSeq(long seq) {
        this.seq = seq;
    }
    public long getSeq() {
        return seq;
    }

    public void setEvent_name(String eventName) {
        this.eventName = eventName;
    }
    public String getEventName() {
        return eventName;
    }

    public void setEvent_body(EventBody eventBody) {
        this.eventBody = eventBody;
    }
    public EventBody getEventBody() {
        return eventBody;
    }

    public void setDevice_info(DeviceInfo deviceInfo) {
        this.deviceInfo = deviceInfo;
    }
    public DeviceInfo getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceTimestamp(long deviceTimestamp) {
        this.deviceTimestamp = deviceTimestamp;
    }
    public long getDeviceTimestamp() {
        return deviceTimestamp;
    }

}