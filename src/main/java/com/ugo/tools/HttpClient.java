package com.ugo.tools;

import okhttp3.*;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class HttpClient {

    OkHttpClient okHttpClient;

    public HttpClient() {
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build();
    }

    private String attachHttpGetParams(String url, Map<String, String> params) {

        Iterator<String> keys = params.keySet().iterator();
        Iterator<String> values = params.values().iterator();
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("?");

        for (int i = 0; i < params.size(); i++) {
            String value = null;
            try {
                value = URLEncoder.encode(values.next(), "utf-8");
            } catch (Exception e) {
                e.printStackTrace();
            }

            stringBuffer.append(keys.next() + "=" + value);
            if (i != params.size() - 1) {
                stringBuffer.append("&");
            }
        }

        return url + stringBuffer.toString();
    }

    public String get(String url, Map<String, String> params) throws IOException {
        Request request = new Request.Builder().url(attachHttpGetParams(url, params)).build();
        return okHttpClient.newCall(request).execute().body().string();
    }

    public String post(String url, Map<String, String> formValues) throws IOException {

        FormBody.Builder builder = new FormBody.Builder();
        formValues.forEach((k, v) -> {
            builder.add(k, v);
        });

        Request request = new Request.Builder().url(url).post(builder.build()).build();
        Call call = okHttpClient.newCall(request);
        return call.execute().body().string();
    }

    public String postJson(String url, String content) throws IOException {
        RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8"), content);
        Request request = new Request.Builder().url(url).post(requestBody).build();
        Call call = okHttpClient.newCall(request);
        return call.execute().body().string();
    }
}
