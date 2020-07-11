package com.quicktutorialz.javalin.domain.env;

import com.quicktutorialz.javalin.domain.Constants;

public class EnvVarRegistry {

    public static String getEnv(String envName) {
        String envValue = System.getenv(envName);
        if(envValue==null)
            envValue = Constants.EMPTY_STRING;
        return envValue;
    }
}
