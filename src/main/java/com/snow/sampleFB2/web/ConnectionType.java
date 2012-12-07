package com.snow.sampleFB2.web;


import com.restfb.types.User;

public enum ConnectionType {
    Friend("me/friends",User.class);
    private String url;
    private Class clazz;

    private ConnectionType(String url, Class clazz) {
        this.url = url;
        this.clazz = clazz;
    }

    public String getUrl() {
        return url;
    }

    public Class getClazz() {
        return clazz;
    }
}
