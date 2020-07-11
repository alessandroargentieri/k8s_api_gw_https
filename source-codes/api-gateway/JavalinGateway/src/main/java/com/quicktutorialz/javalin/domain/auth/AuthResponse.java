package com.quicktutorialz.javalin.domain.auth;

import java.io.Serializable;

public class AuthResponse implements Serializable {

    private final String id;
    private final String name;
    private final String jwt;

    public AuthResponse(String id, String name, String jwt) {
        this.id = id;
        this.name = name;
        this.jwt = jwt;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getJwt() {
        return jwt;
    }
}
