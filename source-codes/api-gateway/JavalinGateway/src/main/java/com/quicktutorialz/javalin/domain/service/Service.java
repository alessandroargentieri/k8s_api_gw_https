package com.quicktutorialz.javalin.domain.service;

import java.io.Serializable;

@Deprecated
public class Service implements Serializable {
    String code;
    String hostUrl;

    public Service() {
    }

    public Service(String code, String hostUrl) {
        this.code = code;
        this.hostUrl = hostUrl;
    }

    public String getCode() {
        return code;
    }

    public String getHostUrl() {
        return hostUrl;
    }
}
