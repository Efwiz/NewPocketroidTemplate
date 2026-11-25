package com.huawei.hms.mlsdk.document;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Base64;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.huawei.hmf.tasks.Task;
import com.huawei.hmf.tasks.Tasks;
import com.huawei.hms.ml.common.utils.ImageConvertUtils;
import com.huawei.hms.ml.common.utils.SmartLog;
import com.huawei.hms.ml.grs.GrsRegionUtils;
import com.huawei.hms.ml.grs.GrsUtils;
import com.huawei.hms.mlsdk.cloud.RemoteRequestService;
import com.huawei.hms.mlsdk.common.AppSettingHolder;
import com.huawei.hms.mlsdk.common.MLApplication;
import com.huawei.hms.mlsdk.common.MLException;
import com.huawei.hms.mlsdk.common.MLFrame;
import com.huawei.hms.mlsdk.document.internal.client.bo.Document;
import com.huawei.hms.mlsdk.document.internal.client.bo.RemoteOcrDocumentResponse;
import com.huawei.hms.mlsdk.internal.client.rest.RestErrorResponse;
import com.huawei.hms.mlsdk.internal.client.rest.e;
import com.huawei.hms.mlsdk.internal.client.rest.f;
import com.huawei.hms.mlsdk.mlvision.a;
import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Callable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/* JADX INFO: loaded from: classes10.dex */
public class MLDocumentAnalyzer implements Closeable {
    private static final String TAG = "MLCloudDocumentAnalyzer";
    private static Map<AppSettingHolder<MLDocumentSetting>, MLDocumentAnalyzer> appSettingAnalyzerMap = new HashMap();

    /* JADX INFO: renamed from: app, reason: collision with root package name */
    private MLApplication f190app;
    private Call<String> currentCall;
    private MLDocumentSetting setting;

    private MLDocumentAnalyzer(MLApplication mLApplication, MLDocumentSetting mLDocumentSetting) {
        this.f190app = mLApplication;
        this.setting = mLDocumentSetting;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public float calculateScale(Bitmap bitmap) {
        float fMin = (Math.min(bitmap.getWidth(), bitmap.getHeight()) * 1.0f) / 1080.0f;
        if (fMin < 1.0f) {
            return 1.0f;
        }
        return fMin;
    }

    /* JADX WARN: Code duplicated, block: B:8:0x0020 A[Catch: all -> 0x002c, TRY_LEAVE, TryCatch #0 {, blocks: (B:5:0x0005, B:6:0x000e, B:8:0x0020), top: B:14:0x0005 }] */
    public static synchronized MLDocumentAnalyzer create(MLApplication mLApplication, MLDocumentSetting mLDocumentSetting) {
        AppSettingHolder<MLDocumentSetting> appSettingHolderCreate;
        MLDocumentAnalyzer mLDocumentAnalyzer;
        if (mLDocumentSetting == null) {
            mLDocumentSetting = new MLDocumentSetting.Factory().create();
            appSettingHolderCreate = AppSettingHolder.create(mLApplication.getUniqueKey(), mLDocumentSetting);
            mLDocumentAnalyzer = appSettingAnalyzerMap.get(appSettingHolderCreate);
            if (mLDocumentAnalyzer == null) {
                mLDocumentAnalyzer = new MLDocumentAnalyzer(mLApplication, mLDocumentSetting);
                appSettingAnalyzerMap.put(appSettingHolderCreate, mLDocumentAnalyzer);
            }
        } else {
            appSettingHolderCreate = AppSettingHolder.create(mLApplication.getUniqueKey(), mLDocumentSetting);
            mLDocumentAnalyzer = appSettingAnalyzerMap.get(appSettingHolderCreate);
            if (mLDocumentAnalyzer == null) {
                mLDocumentAnalyzer = new MLDocumentAnalyzer(mLApplication, mLDocumentSetting);
                appSettingAnalyzerMap.put(appSettingHolderCreate, mLDocumentAnalyzer);
            }
        }
        throw th;
        return mLDocumentAnalyzer;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public MLDocument handleResult(Response<String> response, float f) throws Exception {
        new MLDocument("", new ArrayList());
        if (!response.isSuccessful()) {
            throw new MLException("Get cloud response failed.", 2);
        }
        RemoteOcrDocumentResponse remoteOcrDocumentResponse = (RemoteOcrDocumentResponse) new Gson().fromJson(response.body(), RemoteOcrDocumentResponse.class);
        if (remoteOcrDocumentResponse == null) {
            throw new MLException("Cloud service return the empty result.", 2);
        }
        if ("0".equals(remoteOcrDocumentResponse.getRetCode())) {
            List<Document> docs = remoteOcrDocumentResponse.getDocs();
            if (docs == null || docs.size() == 0) {
                throw new MLException("Cloud service return the empty result.", 2);
            }
            return MLDocument.createDocument(docs, f);
        }
        if ("2001".equals(remoteOcrDocumentResponse.getRetCode())) {
            throw new MLException("Identity authentication required.", 15);
        }
        if ("2002".equals(remoteOcrDocumentResponse.getRetCode())) {
            throw new MLException("Incorrect parameter. This exception is irrelevant to services.", 5);
        }
        if ("2005".equals(remoteOcrDocumentResponse.getRetCode())) {
            throw new MLException("Picture is not recognized.", 2);
        }
        if ("4005".equals(remoteOcrDocumentResponse.getRetCode())) {
            throw new MLException("The project has been out of credit and grace period is over.", 9);
        }
        if ("4006".equals(remoteOcrDocumentResponse.getRetCode())) {
            throw new MLException("Please subscribe package first on https://developer.huawei.com.", 9);
        }
        if ("4007".equals(remoteOcrDocumentResponse.getRetCode())) {
            throw new MLException("The free quota has been used up,please upgrade package on https://developer.huawei.com.", 9);
        }
        throw new MLException("Internal error.", 2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isHeaderInvalidate(Map<String, String> map) {
        String str = map.get("appId");
        if (str == null || str.isEmpty()) {
            SmartLog.e(TAG, "header app_id is empty");
            return true;
        }
        if (map.get("Authorization").replace("Bearer ", "").isEmpty()) {
            SmartLog.e(TAG, "header file api_key is empty");
            return true;
        }
        String str2 = map.get("X-Package-Name");
        if (str2 != null && !str2.isEmpty()) {
            return false;
        }
        SmartLog.e(TAG, "header file package_name is empty");
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isTokenInvalid(ResponseBody responseBody) {
        if (responseBody == null) {
            return false;
        }
        try {
            String strString = responseBody.string();
            if (TextUtils.isEmpty(strString)) {
                return false;
            }
            return "001001".equals(((RestErrorResponse) new Gson().fromJson(strString, RestErrorResponse.class)).getRetCode());
        } catch (JsonSyntaxException | IOException unused) {
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String packageParams(String str, MLDocumentSetting mLDocumentSetting) {
        return String.format(Locale.ENGLISH, "{\"imgBase64\":\"%s\",\"coordsType\":\"%s\",\"languages\":%s}", str, mLDocumentSetting.getBorderType(), new Gson().toJson(mLDocumentSetting.getLanguageList()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Bitmap resizeImage(Bitmap bitmap, float f) {
        return Bitmap.createScaledBitmap(bitmap, (int) (bitmap.getWidth() / f), (int) (bitmap.getHeight() / f), true);
    }

    public Task<MLDocument> asyncAnalyseFrame(final MLFrame mLFrame) {
        return Tasks.callInBackground(new Callable<MLDocument>() { // from class: com.huawei.hms.mlsdk.document.MLDocumentAnalyzer.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // java.util.concurrent.Callable
            public MLDocument call() throws Exception {
                String certFingerprint;
                if (MLDocumentAnalyzer.this.setting.isEnableFingerprintVerification() && ((certFingerprint = MLDocumentAnalyzer.this.f190app.getAppSetting().getCertFingerprint()) == null || certFingerprint.isEmpty())) {
                    throw new MLException("Failed to detect document.", 2);
                }
                String grsCountryCode = GrsRegionUtils.getGrsCountryCode();
                SmartLog.i(MLDocumentAnalyzer.TAG, "GRS countryCode is " + grsCountryCode);
                List<String> listAddHttpsHeaders = GrsUtils.addHttpsHeaders(grsCountryCode != null ? GrsUtils.getVisionSearchUrls(GrsUtils.initGrsVisionSearchClientWithCountry(MLApplication.getInstance().getAppContext(), grsCountryCode)) : GrsUtils.getUrls(MLApplication.getInstance().getAppContext(), false));
                if (listAddHttpsHeaders == null || listAddHttpsHeaders.isEmpty()) {
                    throw new MLException("UrlList is empty, failed to detect document.", 2);
                }
                Map<String, String> mapA = new f.b().a().a();
                if (MLDocumentAnalyzer.this.isHeaderInvalidate(mapA)) {
                    throw new MLException("Header param error, fail to detect document.", 2);
                }
                float fCalculateScale = MLDocumentAnalyzer.this.calculateScale(mLFrame.readBitmap());
                String strEncodeToString = Base64.encodeToString(ImageConvertUtils.bitmap2Jpeg(MLDocumentAnalyzer.this.resizeImage(mLFrame.readBitmap(), fCalculateScale), 90), 2);
                MLDocumentAnalyzer mLDocumentAnalyzer = MLDocumentAnalyzer.this;
                String strPackageParams = mLDocumentAnalyzer.packageParams(strEncodeToString, mLDocumentAnalyzer.setting);
                MLDocument mLDocumentHandleResult = null;
                boolean z = false;
                Response responseExecute = null;
                for (String str : listAddHttpsHeaders) {
                    try {
                        SmartLog.i(MLDocumentAnalyzer.TAG, "request start, url = " + str, false);
                        MLDocumentAnalyzer.this.currentCall = ((RemoteRequestService) e.a().a(str).a(RemoteRequestService.class)).detect("v1/image/recognition/ocr/document", mapA, strPackageParams);
                        responseExecute = MLDocumentAnalyzer.this.currentCall.execute();
                        z = responseExecute != null && responseExecute.code() == 200;
                        if (z) {
                            mLDocumentHandleResult = MLDocumentAnalyzer.this.handleResult(responseExecute, fCalculateScale);
                            break;
                        }
                        continue;
                    } catch (IOException e) {
                        SmartLog.e(MLDocumentAnalyzer.TAG, a.a("Error===>").append(e.getMessage()).toString());
                    }
                }
                if (z) {
                    return mLDocumentHandleResult;
                }
                if (responseExecute == null || responseExecute.code() != 401) {
                    throw new MLException("Cloud document detection failed.", 3);
                }
                if (MLDocumentAnalyzer.this.isTokenInvalid(responseExecute.errorBody())) {
                    throw new MLException("Token is invalid or expired.", 19);
                }
                throw new MLException("Cloud document detection failed.", 15);
            }
        });
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        stop();
    }

    public void stop() throws IOException {
        Call<String> call = this.currentCall;
        if (call == null || call.isCanceled() || !this.currentCall.isExecuted()) {
            return;
        }
        this.currentCall.cancel();
        this.currentCall = null;
    }
}
