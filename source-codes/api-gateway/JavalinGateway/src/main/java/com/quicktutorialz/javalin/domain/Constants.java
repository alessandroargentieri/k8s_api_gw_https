package com.quicktutorialz.javalin.domain;

public interface Constants {

    String API_GATEWAY_PREFIX = "/javalin-api-gateway";
    int API_GATEWAY_PORT = 4000;

    interface Paths {
        String LOGIN_PATH = "/login";
    }

    interface Envs {
        String OAUTH2_CLIENT_SECRET = "OAUTH2_CLIENT_SECRET";
        String OAUTH2_ACCESS_TOKEN_URL = "OAUTH2_ACCESS_TOKEN_URL";
        String OAUTH2_PROVIDER_URL = "OAUTH2_PROVIDER_URL";
        String JWT_SECRET_KEY = "JWT_SECRET_KEY";
        String ENCRYPTION_PASSPHRASE = "ENCRYPTION_PASSPHRASE";
    }

    interface MediaTypes {
        String CONTENT_TYPE = "Content-Type";
        String APPLICATION_JSON = "application/json";
    }

    interface HttpStatuses {
        int FORBIDDEN = 400;
        int INTERNAL_SERVER_ERROR = 500;
    }

    String ACCESS_TOKEN_LABEL = "access_token=";
    String SCOPE_LABEL = "scope=";
    String EMPTY_STRING = "";
    String QUESTION_MARK = "?";
    String AUTHORIZATION = "Authorization";
    String UNAUTHORIZED = "Unauthorized";
    String SESSION_EXPIRED = "Session expired";
    String TOKEN = "token";
    String TOKEN_PREFIX = "token ";
    String BEARER_PREFIX = "Bearer ";
    String ID = "id";
    String NAME = "name";
    String JWT = "jwt";
    String CONTENT_LENGTH = "Content-Length";
    String ZERO = "0";

    interface QueryParams {
        String CLIENT_ID_QUERY_PARAM_LABEL = "?client_id=";
        String CLIENT_SECRET_QUERY_PARAM_LABEL = "&client_secret=";
        String CODE_QUERY_PARAM_LABEL = "&code=";
        String REDIRECT_URI_QUERY_PARAM_LABEL = "&redirect_uri=";
        String STATE_QUERY_PARAM_LABEL = "&state=";
    }

    String JWT_ISSUER = "Javalin-Gateway";
    String JWT_DATA = "data";

}
