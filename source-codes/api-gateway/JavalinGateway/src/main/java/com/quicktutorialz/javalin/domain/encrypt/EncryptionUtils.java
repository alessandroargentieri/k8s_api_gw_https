package com.quicktutorialz.javalin.domain.encrypt;

import com.quicktutorialz.javalin.domain.Constants;
import com.quicktutorialz.javalin.domain.auth.AuthResponse;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

import static com.quicktutorialz.javalin.domain.env.EnvVarRegistry.getEnv;

public class EncryptionUtils {

    private static StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
    static {
        encryptor.setPassword(getEnv(Constants.Envs.ENCRYPTION_PASSPHRASE));
        encryptor.setAlgorithm("PBEWithMD5AndTripleDES");
    }

    public static String encrypt(String id, String name, String accessToken) {
        return encryptor.encrypt( id.concat(":").concat(name).concat(":").concat(accessToken) );
    }

    public static AuthResponse decrypt(String encryptedPayload) {
        String[] payload = encryptor.decrypt(encryptedPayload).split(":");
        return new AuthResponse(payload[0], payload[1], payload[2]);
    }
}
