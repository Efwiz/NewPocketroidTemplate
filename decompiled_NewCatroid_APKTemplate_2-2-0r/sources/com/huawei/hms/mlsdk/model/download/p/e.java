package com.huawei.hms.mlsdk.model.download.p;

import java.io.IOException;
import java.util.Map;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/* JADX INFO: compiled from: HttpClientUtils.java */
/* JADX INFO: loaded from: classes10.dex */
public class e {
    public static <T> Response a(OkHttpClient okHttpClient, String str, Map<String, String> map, String str2) throws IOException {
        Request.Builder builder = new Request.Builder();
        builder.url(str);
        Headers.Builder builder2 = new Headers.Builder();
        if (map != null && !map.isEmpty()) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                builder2.add(entry.getKey(), entry.getValue());
            }
            builder.headers(builder2.build());
            if (str2 != null) {
                builder.post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), str2));
            } else {
                builder.get();
            }
        }
        return okHttpClient.newCall(builder.build()).execute();
    }
}
