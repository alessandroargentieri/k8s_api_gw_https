package com.quicktutorialz.javalin.domain.http;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

import static com.quicktutorialz.javalin.domain.Constants.*;

public class HttpUtils {

    @NotNull
    public static Request composeGetRequest(String url, Map<String, String> headers) {
        return new Request.Builder()
                .url(url)
                .headers(Headers.of(headers))
                .get()
                .build();
    }

    @NotNull
    public static Request composePostRequest(String url, Map<String, String> headers, String mediaType, byte[] content) {
        if (content==null){
            RequestBody reqbody = RequestBody.create(null, new byte[0]);
            return new Request.Builder().url(url).method(HttpMethod.POST.name(), reqbody).header(CONTENT_LENGTH, ZERO).build();
        }
        return new Request.Builder()
                .url(url)
                .headers(Headers.of(headers))
                .post(RequestBody.create(MediaType.parse(mediaType), content))
                .build();
    }

    @NotNull
    public static Request composePutRequest(String url, Map<String, String> headers, String mediaType, byte[] content) {
        if (content==null){
            RequestBody reqbody = RequestBody.create(null, new byte[0]);
            return new Request.Builder().url(url).method(HttpMethod.PUT.name(), reqbody).header(CONTENT_LENGTH, ZERO).build();
        }
        return new Request.Builder()
                .url(url)
                .headers(Headers.of(headers))
                .put(RequestBody.create(MediaType.parse(mediaType), content))
                .build();
    }

    @NotNull
    public static Request composeDeleteRequest(String url, Map<String, String> headers) {
        return new Request.Builder()
                .url(url)
                .headers(Headers.of(headers))
                .delete()
                .build();
    }

    @NotNull
    public static Request composePatchRequest(String url, Map<String, String> headers, String mediaType, byte[] content) {
        if (content==null){
            RequestBody reqbody = RequestBody.create(null, new byte[0]);
            return new Request.Builder().url(url).method(HttpMethod.PATCH.name(), reqbody).header(CONTENT_LENGTH, ZERO).build();
        }
        return new Request.Builder()
                .url(url)
                .headers(Headers.of(headers))
                .patch(RequestBody.create(MediaType.parse(mediaType), content))
                .build();
    }

    @NotNull
    public static Request composeHeadRequest(String url, Map<String, String> headers) {
        return new Request.Builder()
                .url(url)
                .headers(Headers.of(headers))
                .head()
                .build();
    }
}
