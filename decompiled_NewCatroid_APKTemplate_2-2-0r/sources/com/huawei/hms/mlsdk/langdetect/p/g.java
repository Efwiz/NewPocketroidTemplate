package com.huawei.hms.mlsdk.langdetect.p;

import android.content.Context;
import com.huawei.hms.ml.common.utils.SmartLog;
import com.huawei.hms.ml.language.common.utils.ExceptionUtils;
import com.huawei.hms.mlsdk.common.MLApplication;
import com.huawei.hms.mlsdk.common.MLApplicationSetting;
import com.huawei.hms.mlsdk.common.MLException;
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
public abstract class g implements h {
    protected j a;

    public g(MLApplication mLApplication) {
        this.a = new j(mLApplication);
    }

    /* JADX WARN: Code duplicated, block: B:16:0x0029  */
    public i a(String str) throws MLException {
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
            } catch (MLException e) {
                SmartLog.e("g", "Failure to get rest detect client");
                throw ExceptionUtils.getMlException(e);
            }
        } else {
            mLApplicationSettingB = this.a.b();
            if (mLApplicationSettingB != null) {
                str = "Unkonwn";
            } else {
                str = "Unkonwn";
            }
        }
        return new i(new Retrofit.Builder().baseUrl(str).addConverterFactory(ScalarsConverterFactory.create()).client(a()).build());
    }

    private OkHttpClient a() throws MLException {
        Context contextA = this.a.a();
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        try {
            builder.sslSocketFactory(SecureSSLSocketFactory.getInstance(contextA), new SecureX509TrustManager(contextA));
            builder.hostnameVerifier(SecureSSLSocketFactory.STRICT_HOSTNAME_VERIFIER).connectTimeout(20L, TimeUnit.SECONDS).callTimeout(20L, TimeUnit.SECONDS).readTimeout(20L, TimeUnit.SECONDS);
            return builder.build();
        } catch (IOException e) {
            SmartLog.e("g", "initHttpsClient detect ioException error");
            throw ExceptionUtils.getMlException(e);
        } catch (IllegalAccessException e2) {
            SmartLog.e("g", "initHttpsClient detect illegalAccessException error");
            throw ExceptionUtils.getMlException(e2);
        } catch (KeyManagementException e3) {
            SmartLog.e("g", "initHttpsClient detect keyManagementException error");
            throw ExceptionUtils.getMlException(e3);
        } catch (KeyStoreException e4) {
            SmartLog.e("g", "initHttpsClient detect keyStoreException error");
            throw ExceptionUtils.getMlException(e4);
        } catch (NoSuchAlgorithmException e5) {
            SmartLog.e("g", "initHttpsClient detect noSuchAlgorithmException error");
            throw ExceptionUtils.getMlException(e5);
        } catch (CertificateException e6) {
            SmartLog.e("g", "initHttpsClient detect certificateException error");
            throw ExceptionUtils.getMlException(e6);
        }
    }
}
