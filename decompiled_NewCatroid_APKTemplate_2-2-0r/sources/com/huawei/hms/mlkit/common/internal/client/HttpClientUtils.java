package com.huawei.hms.mlkit.common.internal.client;

import android.content.Context;
import android.util.Log;
import com.huawei.secure.android.common.ssl.SecureSSLSocketFactory;
import com.huawei.secure.android.common.ssl.SecureX509TrustManager;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/* JADX INFO: loaded from: classes10.dex */
public class HttpClientUtils {
    public static final String TAG = "HttpClientUtils";
    public static final int TIMEOUT_SECONDS = 20;
    public static HttpClientUtils instance;
    public OkHttpClient okHttpClient;

    private <T> Request buildRequest(String str, Map<String, String> map, String str2) {
        return requestBuilder(str, map, str2).build();
    }

    public static HttpClientUtils getInstance() {
        HttpClientUtils httpClientUtils;
        synchronized (HttpClientUtils.class) {
            if (instance == null) {
                instance = new HttpClientUtils();
            }
            httpClientUtils = instance;
        }
        return httpClientUtils;
    }

    private Request.Builder requestBuilder(String str, Map<String, String> map, String str2) {
        Request.Builder builder = new Request.Builder();
        builder.url(str);
        Headers.Builder builder2 = new Headers.Builder();
        if (map != null && !map.isEmpty()) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                builder2.add(entry.getKey(), entry.getValue());
            }
            builder.headers(builder2.build());
            builder.post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), str2));
        }
        return builder;
    }

    public <T> Response httpPost(String str, Map<String, String> map, String str2) throws IOException {
        return this.okHttpClient.newCall(buildRequest(str, map, str2)).execute();
    }

    public void initHttpsClient(Context context) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        try {
            builder.sslSocketFactory(SecureSSLSocketFactory.getInstance(context), new SecureX509TrustManager(context));
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        } catch (IllegalAccessException e2) {
            Log.e(TAG, e2.getMessage());
        } catch (KeyManagementException e3) {
            Log.e(TAG, e3.getMessage());
        } catch (KeyStoreException e4) {
            Log.e(TAG, e4.getMessage());
        } catch (NoSuchAlgorithmException e5) {
            Log.e(TAG, e5.getMessage());
        } catch (CertificateException e6) {
            Log.e(TAG, e6.getMessage());
        }
        builder.hostnameVerifier(SecureSSLSocketFactory.STRICT_HOSTNAME_VERIFIER).connectTimeout(20L, TimeUnit.SECONDS).callTimeout(20L, TimeUnit.SECONDS).readTimeout(20L, TimeUnit.SECONDS);
        this.okHttpClient = builder.build();
    }
}
