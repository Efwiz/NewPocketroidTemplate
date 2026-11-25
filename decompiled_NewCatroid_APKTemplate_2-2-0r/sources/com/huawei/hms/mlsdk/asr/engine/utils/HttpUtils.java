package com.huawei.hms.mlsdk.asr.engine.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import com.google.common.net.HttpHeaders;
import com.huawei.hms.framework.common.Logger;
import com.huawei.hms.ml.grs.GrsListener;
import com.huawei.hms.ml.grs.GrsRegionUtils;
import com.huawei.hms.ml.grs.GrsUtils;
import com.huawei.hms.mlsdk.common.MLApplication;
import com.huawei.hms.mlsdk.common.MLApplicationSetting;
import com.huawei.secure.android.common.ssl.SecureSSLSocketFactory;
import com.huawei.secure.android.common.ssl.SecureX509TrustManager;
import java.io.IOException;
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
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes10.dex */
public final class HttpUtils {
    private static final String HMS_PACKAGE_NAME = "com.huawei.hwid";
    private static final String HMS_SERVICE = "com.huawei.hms.core.aidlservice";
    private static final int NET_TIMEOUT_SECONDS = 10;
    private static final String SUCCESS = "0";
    private static final String TAG = "HttpUtils";
    private static final String TAG_LANGUAGES = "languages";
    private static final String TAG_RETCODE = "retCode";
    private static final String TAG_RETMSG = "retMsg";

    class a implements GrsListener {
        final /* synthetic */ String a;
        final /* synthetic */ OkHttpClient b;
        final /* synthetic */ Callback c;

        a(String str, OkHttpClient okHttpClient, Callback callback) {
            this.a = str;
            this.b = okHttpClient;
            this.c = callback;
        }

        @Override // com.huawei.hms.ml.grs.GrsListener
        public void onAsynGrsUrls(List<String> list) {
            Logger.i(HttpUtils.TAG, com.huawei.hms.mlsdk.asr.o.a.a("requestSpeaker GRS:[").append(list.size()).append("]").append(list).toString(), true);
            this.b.newCall(HttpUtils.this.buildGetRequest(HttpUtils.formate(com.huawei.hms.mlsdk.asr.o.a.a(list.get(0)).append(this.a).toString()))).enqueue(this.c);
        }
    }

    private static class b {
        private static final HttpUtils a = new HttpUtils(null);
    }

    /* synthetic */ HttpUtils(a aVar) {
        this();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Request buildGetRequest(String str) {
        Request.Builder builder = new Request.Builder();
        builder.url(str);
        SmartLogger.i(TAG, "requestSpeaker domian: " + str);
        Headers.Builder builder2 = new Headers.Builder();
        for (Map.Entry<String, String> entry : getDefaultHeader().entrySet()) {
            builder2.add(entry.getKey(), entry.getValue());
        }
        builder.headers(builder2.build());
        builder.get();
        return builder.build();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String formate(String str) {
        String strTrim = str.trim();
        if (!strTrim.startsWith("http://") && !strTrim.startsWith("https://")) {
            strTrim = com.huawei.hms.mlsdk.asr.o.a.a("https://", strTrim);
        }
        int iIndexOf = strTrim.indexOf("//");
        return strTrim.substring(0, iIndexOf) + "//" + strTrim.substring(iIndexOf + 2).replaceAll("/+", "/");
    }

    private Map<String, String> getDefaultHeader() {
        HashMap map = new HashMap();
        MLApplication mLApplication = MLApplication.getInstance();
        MLApplicationSetting appSetting = mLApplication.getAppSetting();
        if (appSetting == null) {
            return map;
        }
        UUID uuidRandomUUID = UUID.randomUUID();
        map.put("Content-Type", "application/json;charset=UTF-8");
        map.put(HttpHeaders.X_REQUEST_ID, String.valueOf(uuidRandomUUID));
        SmartLogger.i(TAG, "X-Request-ID: " + uuidRandomUUID);
        map.put("X-User-Agent", "X-User-Agent");
        map.put("appId", appSetting.getAppId());
        map.put("HMS-APPLICATION-ID", appSetting.getAppId());
        map.put("X-Package-Name", appSetting.getPackageName());
        map.put("X-Country-Code", new c(mLApplication.getAppContext(), false).a());
        map.put("supplierId", "supplierId");
        map.put("accept", "application/json");
        map.put("certFingerprint", appSetting.getCertFingerprint());
        map.put("Authorization", "Bearer " + MLApplication.getInstance().getAuthorizationToken());
        map.put("X-Mlkit-Version", "3.7.0.301");
        SmartLogger.i(TAG, "X-Mlkit-Version: " + appSetting.getMLSdkVersion());
        Pair<String, ResolveInfo> hmsCoreInfo = getHmsCoreInfo();
        String str = SchemaSymbols.ATTVAL_TRUE_1;
        if (hmsCoreInfo != null) {
            String str2 = (String) hmsCoreInfo.first;
            Log.d(TAG, "HMScore PackageName is :" + str2);
            if (!isPackageInternal(mLApplication.getAppContext(), str2)) {
                str = "0";
            }
            map.put("isHmsCore", str);
        } else {
            if (!isPackageInternal(mLApplication.getAppContext(), "com.huawei.hwid")) {
                str = "0";
            }
            map.put("isHmsCore", str);
        }
        return map;
    }

    private static Pair<String, ResolveInfo> getHmsCoreInfo() {
        List<ResolveInfo> listQueryIntentServices = MLApplication.getInstance().getAppContext().getPackageManager().queryIntentServices(new Intent("com.huawei.hms.core.aidlservice"), 128);
        if (listQueryIntentServices.size() == 0) {
            return null;
        }
        Iterator<ResolveInfo> it = listQueryIntentServices.iterator();
        if (!it.hasNext()) {
            return null;
        }
        ResolveInfo next = it.next();
        return new Pair<>(next.serviceInfo.applicationInfo.packageName, next);
    }

    public static final HttpUtils getInstance() {
        return b.a;
    }

    private boolean isPackageInternal(Context context, String str) {
        try {
            return (context.getPackageManager().getPackageInfo(str, 0).applicationInfo.flags & 1) != 0;
        } catch (RuntimeException e) {
            SmartLogger.e(TAG, "isPackageInternal Exception e: " + e);
            return false;
        } catch (Exception e2) {
            SmartLogger.e(TAG, "isPackageInternal Exception e: " + e2);
            return false;
        }
    }

    public OkHttpClient getOkHttpClient() {
        return getOkHttpClient(10L);
    }

    public boolean isLegalJson(String str) {
        if (TextUtils.isEmpty(str)) {
            SmartLogger.e(TAG, "Language list isEmpty.");
            return false;
        }
        JSONObject json = JsonUtil.parseJson(str);
        if (json == null) {
            SmartLogger.e(TAG, "Response json incorrect format.");
            return false;
        }
        if (!"0".equals(JsonUtil.getJsonStringField(json, TAG_RETCODE))) {
            SmartLogger.e(TAG, com.huawei.hms.mlsdk.asr.o.a.a("Response error! errcode=[").append(JsonUtil.getJsonStringField(json, TAG_RETCODE)).append("] retMsg=[").append(JsonUtil.getJsonStringField(json, TAG_RETMSG)).append("]").toString());
            return false;
        }
        try {
            JSONArray jsonArray = JsonUtil.getJsonArray(json.getJSONObject("result"), TAG_LANGUAGES);
            if (jsonArray != null && jsonArray.length() != 0) {
                return true;
            }
            SmartLogger.e(TAG, "Response json not contain languages");
            return false;
        } catch (JSONException e) {
            SmartLogger.e(TAG, "JSONException", e);
            return true;
        }
    }

    public void requestSupportLanguages(OkHttpClient okHttpClient, String str, Callback callback) {
        GrsUtils.setGrsListener(new a(str, okHttpClient, callback));
        String grsCountryCode = GrsRegionUtils.getGrsCountryCode();
        SmartLogger.i(TAG, "Grs-CountryCode:" + grsCountryCode);
        if (grsCountryCode != null) {
            GrsUtils.getAsrVisionSearchUrls(GrsUtils.initGrsVisionSearchClientWithCountry(MLApplication.getInstance().getAppContext(), grsCountryCode));
        } else {
            GrsUtils.getAsrUrls(MLApplication.getInstance().getAppContext(), false);
        }
    }

    private HttpUtils() {
    }

    public OkHttpClient getOkHttpClient(long j) {
        Context appContext = MLApplication.getInstance().getAppContext();
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        try {
            builder.sslSocketFactory(SecureSSLSocketFactory.getInstance(appContext), new SecureX509TrustManager(appContext));
        } catch (IOException e) {
            SmartLogger.e(TAG, e.getMessage());
        } catch (IllegalAccessException e2) {
            SmartLogger.e(TAG, e2.getMessage());
        } catch (KeyManagementException e3) {
            SmartLogger.e(TAG, e3.getMessage());
        } catch (KeyStoreException e4) {
            SmartLogger.e(TAG, e4.getMessage());
        } catch (NoSuchAlgorithmException e5) {
            SmartLogger.e(TAG, e5.getMessage());
        } catch (CertificateException e6) {
            SmartLogger.e(TAG, e6.getMessage());
        }
        builder.hostnameVerifier(SecureSSLSocketFactory.STRICT_HOSTNAME_VERIFIER).connectTimeout(j, TimeUnit.SECONDS).callTimeout(j, TimeUnit.SECONDS).readTimeout(j, TimeUnit.SECONDS).connectionPool(new ConnectionPool(5, 1L, TimeUnit.SECONDS));
        return builder.build();
    }
}
