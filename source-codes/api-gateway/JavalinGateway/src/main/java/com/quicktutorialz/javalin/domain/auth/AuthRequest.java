package com.quicktutorialz.javalin.domain.auth;

import java.io.Serializable;

public class AuthRequest implements Serializable {

    private String clientId;
    private String code;
    private String redirectUri;
    private String state;

    public AuthRequest() {
    }

    public AuthRequest(String clientId, String code, String redirectUri, String state) {
        this.clientId = clientId;
        this.code = code;
        this.redirectUri = redirectUri;
        this.state = state;
    }

    public String getClientId() {
        return clientId;
    }

    public String getCode() {
        return code;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public String getState() {
        return state;
    }
}
