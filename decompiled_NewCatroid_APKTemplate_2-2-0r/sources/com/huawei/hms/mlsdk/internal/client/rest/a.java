package com.huawei.hms.mlsdk.internal.client.rest;

import android.content.Context;
import com.huawei.hms.ml.common.utils.SmartLog;
import com.huawei.hms.mlsdk.common.MLApplication;
import com.huawei.hms.mlsdk.common.MLApplicationSetting;
import com.huawei.secure.android.common.ssl.SecureSSLSocketFactory;
import com.huawei.secure.android.common.ssl.SecureX509TrustManager;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/* JADX INFO: compiled from: AbstractRestClientProvider.java */
/* JADX INFO: loaded from: classes10.dex */
public abstract class a implements b {
    protected d a;

    public a(MLApplication mLApplication) {
        this.a = new d(mLApplication);
    }

    /* JADX WARN: Code duplicated, block: B:18:0x002b  */
    public c a(String str) {
        MLApplicationSetting mLApplicationSettingB;
        List<String> mLServiceUrls;
        if (str != null) {
            try {
                if ("".equals(str)) {
                    mLApplicationSettingB = this.a.b();
                    if (mLApplicationSettingB != null || (mLServiceUrls = mLApplicationSettingB.getMLServiceUrls()) == null || mLServiceUrls.isEmpty()) {
                        str = "Unkonwn";
                    } else {
                        str = mLServiceUrls.get(0);
                    }
                }
            } catch (RuntimeException e) {
                throw e;
            } catch (Exception e2) {
                SmartLog.e("AbstractRestClientProvider", "Failure to get rest client", e2);
                return null;
            }
        } else {
            mLApplicationSettingB = this.a.b();
            if (mLApplicationSettingB != null) {
                str = "Unkonwn";
            } else {
                str = "Unkonwn";
            }
        }
        return new c(new Retrofit.Builder().baseUrl(str).addConverterFactory(ScalarsConverterFactory.create()).client(a()).build());
    }

    private OkHttpClient a() {
        Context contextA = this.a.a();
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        try {
            builder.sslSocketFactory(SecureSSLSocketFactory.getInstance(contextA), new SecureX509TrustManager(contextA));
        } catch (IOException e) {
            SmartLog.e("AbstractRestClientProvider", e.getMessage());
        } catch (IllegalAccessException e2) {
            SmartLog.e("AbstractRestClientProvider", e2.getMessage());
        } catch (KeyManagementException e3) {
            SmartLog.e("AbstractRestClientProvider", e3.getMessage());
        } catch (KeyStoreException e4) {
            SmartLog.e("AbstractRestClientProvider", e4.getMessage());
        } catch (NoSuchAlgorithmException e5) {
            SmartLog.e("AbstractRestClientProvider", e5.getMessage());
        } catch (CertificateException e6) {
            SmartLog.e("AbstractRestClientProvider", e6.getMessage());
        }
        builder.hostnameVerifier(SecureSSLSocketFactory.STRICT_HOSTNAME_VERIFIER).connectTimeout(20L, TimeUnit.SECONDS).callTimeout(20L, TimeUnit.SECONDS).readTimeout(20L, TimeUnit.SECONDS);
        return builder.build();
    }
}
