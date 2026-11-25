package com.huawei.hms.mlsdk.t;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.google.common.net.HttpHeaders;
import com.google.gson.Gson;
import com.huawei.hms.ml.grs.CountryCodeHelper;
import com.huawei.hms.ml.grs.GrsRegionUtils;
import com.huawei.hms.ml.grs.GrsUtils;
import com.huawei.hms.mlsdk.common.MLApplication;
import com.huawei.hms.mlsdk.common.MLApplicationSetting;
import com.huawei.hms.mlsdk.tts.download.data.ModelQueryRequest;
import com.huawei.secure.android.common.ssl.SecureSSLSocketFactory;
import com.huawei.secure.android.common.ssl.SecureX509TrustManager;
import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import okhttp3.Callback;
import okhttp3.ConnectionPool;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.apache.xerces.impl.xs.SchemaSymbols;

/* JADX INFO: compiled from: HttpUtils.java */
/* JADX INFO: loaded from: classes10.dex */
public final class l {

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: HttpUtils.java */
    static class b {
        private static final l a = new l(null);
    }

    /* synthetic */ l(a aVar) {
    }

    private Map<String, String> c() {
        HashMap map = new HashMap();
        Context appContext = MLApplication.getInstance().getAppContext();
        MLApplicationSetting appSetting = MLApplication.getInstance().getAppSetting() != null ? MLApplication.getInstance().getAppSetting() : MLApplicationSetting.fromResource(appContext);
        UUID uuidRandomUUID = UUID.randomUUID();
        if (g0.a()) {
            i0.c("HttpUtils", "Request from HMS Core!");
            map.put("isHmsCore", SchemaSymbols.ATTVAL_TRUE_1);
        }
        i0.c("HttpUtils", "X-Request-ID:" + uuidRandomUUID);
        map.put("Content-Type", "application/json");
        map.put(HttpHeaders.X_REQUEST_ID, String.valueOf(uuidRandomUUID));
        map.put("X-User-Agent", "X-User-Agent");
        map.put("appId", appSetting.getAppId());
        map.put("HMS-APPLICATION-ID", appSetting.getAppId());
        map.put("X-Package-Name", appSetting.getPackageName());
        map.put("X-Country-Code", CountryCodeHelper.getInstance().prepare(appContext, false).getCountryCode());
        map.put("supplierId", "supplierId");
        map.put("accept", "application/json");
        map.put("certFingerprint", appSetting.getCertFingerprint());
        map.put("Authorization", "Bearer " + MLApplication.getInstance().getAuthorizationToken());
        map.put("X-Mlkit-Version", "3.7.0.303");
        return map;
    }

    public static final l d() {
        return b.a;
    }

    public List<String> a() {
        String grsCountryCode = GrsRegionUtils.getGrsCountryCode();
        i0.c("HttpUtils", "GRS countryCode is " + grsCountryCode);
        return grsCountryCode != null ? GrsUtils.getVisionSearchUrls(GrsUtils.initGrsVisionSearchClientWithCountry(MLApplication.getInstance().getAppContext(), grsCountryCode)) : GrsUtils.getUrls(MLApplication.getInstance().getAppContext(), false);
    }

    public OkHttpClient b() {
        Context appContext = MLApplication.getInstance().getAppContext();
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        try {
            builder.sslSocketFactory(SecureSSLSocketFactory.getInstance(appContext), new SecureX509TrustManager(appContext));
        } catch (IOException e) {
            i0.b("HttpUtils", e.getMessage());
        } catch (IllegalAccessException e2) {
            i0.b("HttpUtils", e2.getMessage());
        } catch (KeyManagementException e3) {
            i0.b("HttpUtils", e3.getMessage());
        } catch (KeyStoreException e4) {
            i0.b("HttpUtils", e4.getMessage());
        } catch (NoSuchAlgorithmException e5) {
            i0.b("HttpUtils", e5.getMessage());
        } catch (CertificateException e6) {
            i0.b("HttpUtils", e6.getMessage());
        }
        builder.hostnameVerifier(SecureSSLSocketFactory.STRICT_HOSTNAME_VERIFIER).connectTimeout(3600L, TimeUnit.SECONDS).callTimeout(3600L, TimeUnit.SECONDS).readTimeout(3600L, TimeUnit.SECONDS).connectionPool(new ConnectionPool(5, 1L, TimeUnit.SECONDS));
        return builder.build();
    }

    public void a(OkHttpClient okHttpClient, String str, Callback callback) {
        List<String> listA = a();
        i0.a("HttpUtils", com.huawei.hms.mlsdk.t.a.a("requestSpeaker GRS:[").append(listA.size()).append("]").append(listA).toString(), true);
        String strA = a(listA.get(0) + str);
        Request.Builder builder = new Request.Builder();
        builder.url(strA);
        i0.c("HttpUtils", "requestSpeaker domian: " + strA);
        Headers.Builder builder2 = new Headers.Builder();
        for (Map.Entry<String, String> entry : c().entrySet()) {
            builder2.add(entry.getKey(), entry.getValue());
        }
        builder.headers(builder2.build());
        builder.get();
        okHttpClient.newCall(builder.build()).enqueue(callback);
    }

    public void a(String str, OkHttpClient okHttpClient, String str2, Callback callback) {
        List<String> listA = a();
        i0.a("HttpUtils", com.huawei.hms.mlsdk.t.a.a("requestOfflineSpeaker GRS:[").append(listA.size()).append("]").append(listA).toString(), true);
        String strA = a(listA.get(0) + str2);
        String str3 = "query address is " + strA;
        okHttpClient.newCall(a(strA, new Gson().toJson(new ModelQueryRequest("", str, h0.a(), 2, 1)))).enqueue(callback);
    }

    public n a(OkHttpClient okHttpClient, String str, String str2) {
        NetworkInfo activeNetworkInfo;
        n nVar = new n();
        List<String> listA = a();
        Iterator<String> it = listA.iterator();
        while (it.hasNext()) {
            i0.c("HttpUtils", "grsServerUrlList: " + a(it.next() + str));
        }
        Iterator<String> it2 = listA.iterator();
        Response responseExecute = null;
        boolean z = false;
        while (it2.hasNext()) {
            String strA = a(it2.next() + str);
            i0.c("HttpUtils", "Target domian: " + strA);
            try {
                responseExecute = okHttpClient.newCall(a(strA, str2)).execute();
                if (responseExecute != null) {
                    i0.c("HttpUtils", "Response code: " + responseExecute.code());
                    z = responseExecute.code() == 200;
                }
                if (z) {
                    break;
                }
            } catch (IOException e) {
                i0.b("HttpUtils", com.huawei.hms.mlsdk.t.a.a("Got response failed:").append(e.getClass().getSimpleName()).append(";reasons:").append(e.getMessage()).append(";cause:").append(e.getCause()).toString());
                Context appContext = MLApplication.getInstance().getAppContext();
                if (appContext == null || (activeNetworkInfo = ((ConnectivityManager) appContext.getSystemService("connectivity")).getActiveNetworkInfo()) == null || !activeNetworkInfo.isAvailable() || !activeNetworkInfo.isConnected()) {
                    nVar.a(3);
                    break;
                }
                if (e instanceof SocketException) {
                    break;
                }
                if (e.getCause() != null && (e.getCause() instanceof SocketTimeoutException)) {
                    nVar.a(4);
                } else {
                    nVar.a(1);
                    nVar.a("Got response failed:" + e.getClass().getSimpleName());
                }
            } catch (IllegalStateException e2) {
                i0.b("HttpUtils", com.huawei.hms.mlsdk.t.a.a("The call has already been executed:").append(e2.getMessage()).toString());
                nVar.a(2);
                nVar.a("The call has already been executed!");
            }
        }
        return nVar.a(responseExecute);
    }

    private <T> Request a(String str, String str2) {
        Request.Builder builder = new Request.Builder();
        builder.url(str);
        Headers.Builder builder2 = new Headers.Builder();
        for (Map.Entry<String, String> entry : c().entrySet()) {
            builder2.add(entry.getKey(), entry.getValue());
        }
        builder.headers(builder2.build());
        String str3 = "Request is " + str2;
        builder.post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), str2));
        return builder.build();
    }

    public void a(OkHttpClient okHttpClient) {
        if (okHttpClient != null) {
            okHttpClient.dispatcher().cancelAll();
        }
    }

    private static String a(String str) {
        String strTrim = str.trim();
        if (!strTrim.startsWith("http://") && !strTrim.startsWith("https://")) {
            strTrim = "https://" + strTrim;
        }
        int iIndexOf = strTrim.indexOf("//");
        return strTrim.substring(0, iIndexOf) + "//" + strTrim.substring(iIndexOf + 2).replaceAll("/+", "/");
    }
}
