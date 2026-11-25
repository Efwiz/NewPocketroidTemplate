package com.huawei.hms.mlsdk.model.download.p;

import android.content.Context;
import com.huawei.hms.ml.common.utils.SmartLog;
import com.huawei.secure.android.common.ssl.SecureSSLSocketFactory;
import com.huawei.secure.android.common.ssl.SecureX509TrustManager;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;

/* JADX INFO: compiled from: OkHttpClientCreator.java */
/* JADX INFO: loaded from: classes10.dex */
public class g {
    public static OkHttpClient a(Context context) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        try {
            builder.sslSocketFactory(SecureSSLSocketFactory.getInstance(context), new SecureX509TrustManager(context));
        } catch (IOException e) {
            SmartLog.e("MLSDK_MODEL_OkHttpClientCreator", e.getMessage());
        } catch (IllegalAccessException e2) {
            SmartLog.e("MLSDK_MODEL_OkHttpClientCreator", e2.getMessage());
        } catch (KeyManagementException e3) {
            SmartLog.e("MLSDK_MODEL_OkHttpClientCreator", e3.getMessage());
        } catch (KeyStoreException e4) {
            SmartLog.e("MLSDK_MODEL_OkHttpClientCreator", e4.getMessage());
        } catch (NoSuchAlgorithmException e5) {
            SmartLog.e("MLSDK_MODEL_OkHttpClientCreator", e5.getMessage());
        } catch (CertificateException e6) {
            SmartLog.e("MLSDK_MODEL_OkHttpClientCreator", e6.getMessage());
        }
        builder.hostnameVerifier(SecureSSLSocketFactory.STRICT_HOSTNAME_VERIFIER).retryOnConnectionFailure(false).connectTimeout(10L, TimeUnit.SECONDS).readTimeout(20L, TimeUnit.SECONDS).writeTimeout(20L, TimeUnit.SECONDS);
        return builder.build();
    }
}
