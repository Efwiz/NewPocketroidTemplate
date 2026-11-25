package com.huawei.hms.mlsdk.classification.internal.client;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Base64;
import android.util.SparseArray;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.huawei.hmf.tasks.Task;
import com.huawei.hmf.tasks.Tasks;
import com.huawei.hms.ml.common.utils.ImageConvertUtils;
import com.huawei.hms.ml.common.utils.SmartLog;
import com.huawei.hms.ml.grs.GrsRegionUtils;
import com.huawei.hms.ml.grs.GrsUtils;
import com.huawei.hms.mlsdk.asr.engine.AsrError;
import com.huawei.hms.mlsdk.classification.MLImageClassification;
import com.huawei.hms.mlsdk.classification.MLRemoteClassificationAnalyzerSetting;
import com.huawei.hms.mlsdk.classification.internal.client.bo.CloudImageLabelRequest;
import com.huawei.hms.mlsdk.classification.internal.client.bo.CloudImageLabelResponse;
import com.huawei.hms.mlsdk.classification.internal.client.bo.LabelResult;
import com.huawei.hms.mlsdk.cloud.RemoteRequestService;
import com.huawei.hms.mlsdk.common.MLApplication;
import com.huawei.hms.mlsdk.common.MLException;
import com.huawei.hms.mlsdk.internal.client.rest.RestErrorResponse;
import com.huawei.hms.mlsdk.internal.client.rest.e;
import com.huawei.hms.mlsdk.internal.client.rest.f;
import com.huawei.hms.mlsdk.mlvision.a;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import okhttp3.ResponseBody;
import retrofit2.Response;

/* JADX INFO: loaded from: classes10.dex */
public class NativeCloudImageLabeler {
    private static final int BITMAP_WIDTH = 300;
    private static final int CODE_SUCCESS = 200;
    private static final String FAILED_MASSAGE = "Failed to detect cloud classification.";
    private static final int RESIZE_QUALITY = 100;
    private static final String TAG = "NativeCloudImageLabeler";

    /* JADX INFO: renamed from: app, reason: collision with root package name */
    private MLApplication f189app;
    private final float possibility;
    private RemoteRequestService requestService;
    private MLRemoteClassificationAnalyzerSetting setting;

    public NativeCloudImageLabeler(MLApplication mLApplication, MLRemoteClassificationAnalyzerSetting mLRemoteClassificationAnalyzerSetting) {
        this.possibility = mLRemoteClassificationAnalyzerSetting.getMinAcceptablePossibility();
        this.f189app = mLApplication;
        this.setting = mLRemoteClassificationAnalyzerSetting;
    }

    private String base64Img(Bitmap bitmap) {
        return Base64.encodeToString(ImageConvertUtils.bitmap2Jpeg(bitmap, 100), 2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public List<MLImageClassification> getClassifications(Bitmap bitmap) throws Exception {
        String grsCountryCode = GrsRegionUtils.getGrsCountryCode();
        SmartLog.i(TAG, "GRS countryCode is " + grsCountryCode);
        List<String> listAddHttpsHeaders = GrsUtils.addHttpsHeaders(grsCountryCode != null ? GrsUtils.getVisionSearchUrls(GrsUtils.initGrsVisionSearchClientWithCountry(MLApplication.getInstance().getAppContext(), grsCountryCode)) : GrsUtils.getUrls(MLApplication.getInstance().getAppContext(), false));
        if (listAddHttpsHeaders == null || listAddHttpsHeaders.isEmpty()) {
            throw new MLException("UrlList is empty, failed to detect classification.", 2);
        }
        Map<String, String> mapA = new f.b().a().a();
        if (isHeaderInvalidate(mapA)) {
            throw new MLException("Header param error, failed to detect classification.", 2);
        }
        String strPackageParamJson = packageParamJson(base64Img(resizeImage(bitmap)));
        List<MLImageClassification> listHandleResult = null;
        boolean z = false;
        Response<String> responseExecute = null;
        for (String str : listAddHttpsHeaders) {
            try {
                SmartLog.i(TAG, "request start, url = " + str, false);
                RemoteRequestService remoteRequestService = (RemoteRequestService) e.a().a(str).a(RemoteRequestService.class);
                this.requestService = remoteRequestService;
                responseExecute = remoteRequestService.detect("/v1/image/recognition/label", mapA, strPackageParamJson).execute();
                z = responseExecute != null && responseExecute.code() == 200;
                if (z) {
                    listHandleResult = handleResult(responseExecute);
                    break;
                }
                continue;
            } catch (IOException e) {
                SmartLog.e(TAG, a.a("Error===>").append(e.getMessage()).toString());
            }
        }
        if (z) {
            return listHandleResult;
        }
        if (responseExecute == null || responseExecute.code() != 401) {
            throw new MLException("Cloud image classification failed.", 3);
        }
        if (isTokenInvalid(responseExecute.errorBody())) {
            throw new MLException("Token is invalid or expired.", 19);
        }
        throw new MLException("Cloud image classification failed.", 15);
    }

    private List<MLImageClassification> handleResult(Response<String> response) throws Exception {
        ArrayList arrayList = new ArrayList();
        if (!response.isSuccessful()) {
            throw new MLException("Get cloud response failed.", 2);
        }
        String strBody = response.body();
        int asInt = new JsonParser().parse(strBody).getAsJsonObject().get("retCode").getAsInt();
        if (asInt != 0) {
            if (asInt == 2005) {
                throw new MLException("Picture is not recognized.", 2);
            }
            if (asInt == 2001) {
                throw new MLException("Identity authentication required.", 15);
            }
            if (asInt == 2002) {
                throw new MLException("Incorrect parameter. This exception is irrelevant to services.", 5);
            }
            switch (asInt) {
                case AsrError.SERVICE_OUTSTANDING /* 4005 */:
                    throw new MLException("The project has been out of credit and grace period is over.", 9);
                case AsrError.SERVICE_UNSUBSCRIBED /* 4006 */:
                    throw new MLException("Please subscribe package first on https://developer.huawei.com.", 9);
                case AsrError.SERVICE_FREE_USED_UP /* 4007 */:
                    throw new MLException("The free quota has been used up,please upgrade package on https://developer.huawei.com.", 9);
                default:
                    throw new MLException("Internal error.", 2);
            }
        }
        List<LabelResult> responses = ((CloudImageLabelResponse) new Gson().fromJson(strBody, CloudImageLabelResponse.class)).getResponses();
        if (responses == null || responses.isEmpty()) {
            throw new MLException("Cloud service return the empty result.", 2);
        }
        for (LabelResult labelResult : responses) {
            String description = labelResult.getDescription();
            float f = Float.parseFloat(String.valueOf(labelResult.getScore()));
            if (this.possibility <= f) {
                arrayList.add(new MLImageClassification(description, f, ""));
            }
        }
        return arrayList;
    }

    private boolean isHeaderInvalidate(Map<String, String> map) {
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

    private boolean isTokenInvalid(ResponseBody responseBody) {
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

    private String packageParamJson(String str) {
        return new Gson().toJson(new CloudImageLabelRequest(str, this.setting.getLargestNumOfReturns()));
    }

    private Bitmap resizeImage(Bitmap bitmap) {
        return Bitmap.createScaledBitmap(bitmap, 300, 300, true);
    }

    public SparseArray<MLImageClassification> analyseFrame(Bitmap bitmap) {
        SparseArray<MLImageClassification> sparseArray = new SparseArray<>();
        List<MLImageClassification> arrayList = new ArrayList<>();
        try {
            arrayList = getClassifications(bitmap);
        } catch (IOException e) {
            SmartLog.e(TAG, a.a(FAILED_MASSAGE).append(e.getMessage()).toString());
        } catch (Exception e2) {
            SmartLog.e(TAG, a.a(FAILED_MASSAGE).append(e2.getMessage()).toString());
        }
        for (int i = 0; i < arrayList.size(); i++) {
            sparseArray.put(i, arrayList.get(i));
        }
        return sparseArray;
    }

    public Task<List<MLImageClassification>> asyncAnalyseFrame(final Bitmap bitmap) {
        SmartLog.i(TAG, "analyzePic");
        return Tasks.callInBackground(new Callable<List<MLImageClassification>>() { // from class: com.huawei.hms.mlsdk.classification.internal.client.NativeCloudImageLabeler.1
            @Override // java.util.concurrent.Callable
            public List<MLImageClassification> call() throws Exception {
                String certFingerprint;
                try {
                    if (NativeCloudImageLabeler.this.setting.isEnableFingerprintVerification() && ((certFingerprint = NativeCloudImageLabeler.this.f189app.getAppSetting().getCertFingerprint()) == null || certFingerprint.isEmpty())) {
                        throw new MLException(NativeCloudImageLabeler.FAILED_MASSAGE, 2);
                    }
                    return NativeCloudImageLabeler.this.getClassifications(bitmap);
                } catch (IOException unused) {
                    throw new MLException(NativeCloudImageLabeler.FAILED_MASSAGE, 2);
                }
            }
        });
    }

    public void close() {
    }
}
