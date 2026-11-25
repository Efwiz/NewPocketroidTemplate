package com.huawei.hms.mlsdk.landmark;

import android.graphics.Bitmap;
import android.graphics.Rect;
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
import com.huawei.hms.mlsdk.common.MLCoordinate;
import com.huawei.hms.mlsdk.common.MLException;
import com.huawei.hms.mlsdk.common.MLFrame;
import com.huawei.hms.mlsdk.internal.client.rest.RestErrorResponse;
import com.huawei.hms.mlsdk.internal.client.rest.e;
import com.huawei.hms.mlsdk.internal.client.rest.f;
import com.huawei.hms.mlsdk.landmark.bo.LandMarkResponse;
import com.huawei.hms.mlsdk.landmark.bo.LandMarkResult;
import com.huawei.hms.mlsdk.landmark.bo.Location;
import com.huawei.hms.mlsdk.landmark.bo.Vertex;
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
public class MLRemoteLandmarkAnalyzer implements Closeable {

    /* JADX INFO: renamed from: app, reason: collision with root package name */
    private MLApplication f194app;
    private Call<String> currentCall;
    private MLRemoteLandmarkAnalyzerSetting setting;
    private static Map<AppSettingHolder<MLRemoteLandmarkAnalyzerSetting>, MLRemoteLandmarkAnalyzer> appSettingAnalyzerMap = new HashMap();
    private static final String TAG = "MLRemoteLandmarkAnalyzer";

    private MLRemoteLandmarkAnalyzer(MLApplication mLApplication, MLRemoteLandmarkAnalyzerSetting mLRemoteLandmarkAnalyzerSetting) {
        this.f194app = mLApplication;
        this.setting = mLRemoteLandmarkAnalyzerSetting;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String base64Img(Bitmap bitmap) {
        return Base64.encodeToString(ImageConvertUtils.bitmap2Jpeg(bitmap, 100), 2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public double calculateScale(Bitmap bitmap) {
        float fMin = (Math.min(bitmap.getWidth(), bitmap.getHeight()) * 1.0f) / 640.0f;
        return fMin >= 1.0f ? fMin : 1.0f;
    }

    public static synchronized MLRemoteLandmarkAnalyzer create(MLApplication mLApplication, MLRemoteLandmarkAnalyzerSetting mLRemoteLandmarkAnalyzerSetting) {
        MLRemoteLandmarkAnalyzer mLRemoteLandmarkAnalyzer;
        AppSettingHolder<MLRemoteLandmarkAnalyzerSetting> appSettingHolderCreate = AppSettingHolder.create(mLApplication.getUniqueKey(), mLRemoteLandmarkAnalyzerSetting);
        mLRemoteLandmarkAnalyzer = appSettingAnalyzerMap.get(appSettingHolderCreate);
        if (mLRemoteLandmarkAnalyzer == null) {
            mLRemoteLandmarkAnalyzer = new MLRemoteLandmarkAnalyzer(mLApplication, mLRemoteLandmarkAnalyzerSetting);
            appSettingAnalyzerMap.put(appSettingHolderCreate, mLRemoteLandmarkAnalyzer);
        }
        return mLRemoteLandmarkAnalyzer;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public List<MLRemoteLandmark> handleResult(Response<String> response, double d) throws Exception {
        ArrayList arrayList = new ArrayList();
        if (!response.isSuccessful()) {
            throw new MLException("Get cloud response failed.", 2);
        }
        LandMarkResponse landMarkResponse = (LandMarkResponse) new Gson().fromJson(response.body(), LandMarkResponse.class);
        if (landMarkResponse == null) {
            throw new MLException("Cloud service return the empty result.", 2);
        }
        if (!"0".equals(landMarkResponse.getRetCode())) {
            if ("2001".equals(landMarkResponse.getRetCode())) {
                throw new MLException("Identity authentication required.", 15);
            }
            if ("2002".equals(landMarkResponse.getRetCode())) {
                throw new MLException("Incorrect parameter. This exception is irrelevant to services.", 5);
            }
            if ("2005".equals(landMarkResponse.getRetCode())) {
                throw new MLException("Picture is not recognized.", 2);
            }
            if ("4005".equals(landMarkResponse.getRetCode())) {
                throw new MLException("The project has been out of credit and grace period is over.", 9);
            }
            if ("4006".equals(landMarkResponse.getRetCode())) {
                throw new MLException("Please subscribe package first on https://developer.huawei.com.", 9);
            }
            if ("4007".equals(landMarkResponse.getRetCode())) {
                throw new MLException("The free quota has been used up,please upgrade package on https://developer.huawei.com.", 9);
            }
            throw new MLException("Internal error.", 2);
        }
        for (LandMarkResult landMarkResult : landMarkResponse.getLandMarkResults()) {
            MLRemoteLandmark mLRemoteLandmark = new MLRemoteLandmark();
            mLRemoteLandmark.setLandmarkId("");
            mLRemoteLandmark.setLandmark(landMarkResult.getDescription() == null ? "Unknown" : landMarkResult.getDescription());
            mLRemoteLandmark.setPossibility(Float.parseFloat(String.valueOf(landMarkResult.getScore())));
            List<Vertex> vertices = landMarkResult.getVertices();
            if (vertices != null) {
                mLRemoteLandmark.setBorder(new Rect((int) (((double) vertices.get(0).getX()) * d), (int) (((double) vertices.get(0).getY()) * d), (int) (((double) vertices.get(1).getX()) * d), (int) (((double) vertices.get(1).getY()) * d)));
            } else {
                mLRemoteLandmark.setBorder(new Rect(0, 0, 0, 0));
            }
            List<Location> locations = landMarkResult.getLocations();
            ArrayList arrayList2 = new ArrayList();
            if (locations != null) {
                for (Location location : locations) {
                    arrayList2.add(new MLCoordinate(location.getLatitude(), location.getLongitude()));
                }
            } else {
                arrayList2.add(new MLCoordinate(0.0d, 0.0d));
            }
            mLRemoteLandmark.setPositionInfos(arrayList2);
            arrayList.add(mLRemoteLandmark);
        }
        return arrayList;
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
    public String packageParamJson(String str, int i) {
        return String.format(Locale.ENGLISH, "{\"imgBase64\":\"%s\",\"topNum\":\"%s\"}", str, String.valueOf(i));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Bitmap resizeImage(Bitmap bitmap, double d) {
        return Bitmap.createScaledBitmap(bitmap, (int) (((double) bitmap.getWidth()) / d), (int) (((double) bitmap.getHeight()) / d), true);
    }

    public Task<List<MLRemoteLandmark>> asyncAnalyseFrame(final MLFrame mLFrame) {
        return Tasks.callInBackground(new Callable<List<MLRemoteLandmark>>() { // from class: com.huawei.hms.mlsdk.landmark.MLRemoteLandmarkAnalyzer.1
            @Override // java.util.concurrent.Callable
            public List<MLRemoteLandmark> call() throws Exception {
                String certFingerprint;
                if (MLRemoteLandmarkAnalyzer.this.setting.isEnableFingerprintVerification() && ((certFingerprint = MLRemoteLandmarkAnalyzer.this.f194app.getAppSetting().getCertFingerprint()) == null || certFingerprint.isEmpty())) {
                    throw new MLException("Failed to detect cloud landmark.", 2);
                }
                String grsCountryCode = GrsRegionUtils.getGrsCountryCode();
                SmartLog.i(MLRemoteLandmarkAnalyzer.TAG, "GRS countryCode is " + grsCountryCode);
                List<String> listAddHttpsHeaders = GrsUtils.addHttpsHeaders(grsCountryCode != null ? GrsUtils.getVisionSearchUrls(GrsUtils.initGrsVisionSearchClientWithCountry(MLApplication.getInstance().getAppContext(), grsCountryCode)) : GrsUtils.getUrls(MLApplication.getInstance().getAppContext(), false));
                if (listAddHttpsHeaders == null || listAddHttpsHeaders.isEmpty()) {
                    throw new MLException("UrlList is empty, fail to detect cloud landmark.", 2);
                }
                Map<String, String> mapA = new f.b().a().a();
                if (MLRemoteLandmarkAnalyzer.this.isHeaderInvalidate(mapA)) {
                    throw new MLException("Header param error, fail to detect cloud landmark.", 2);
                }
                double dCalculateScale = MLRemoteLandmarkAnalyzer.this.calculateScale(mLFrame.readBitmap());
                String strBase64Img = MLRemoteLandmarkAnalyzer.this.base64Img(MLRemoteLandmarkAnalyzer.this.resizeImage(mLFrame.readBitmap(), dCalculateScale));
                MLRemoteLandmarkAnalyzer mLRemoteLandmarkAnalyzer = MLRemoteLandmarkAnalyzer.this;
                String strPackageParamJson = mLRemoteLandmarkAnalyzer.packageParamJson(strBase64Img, mLRemoteLandmarkAnalyzer.setting.getLargestNumOfReturns());
                List<MLRemoteLandmark> listHandleResult = null;
                boolean z = false;
                Response responseExecute = null;
                for (String str : listAddHttpsHeaders) {
                    try {
                        SmartLog.i(MLRemoteLandmarkAnalyzer.TAG, "request start, url = " + str, false);
                        MLRemoteLandmarkAnalyzer.this.currentCall = ((RemoteRequestService) e.a().a(str).a(RemoteRequestService.class)).detect("v1/image/recognition/landmark", mapA, strPackageParamJson);
                        responseExecute = MLRemoteLandmarkAnalyzer.this.currentCall.execute();
                        z = responseExecute != null && responseExecute.code() == 200;
                        if (z) {
                            listHandleResult = MLRemoteLandmarkAnalyzer.this.handleResult(responseExecute, dCalculateScale);
                            break;
                        }
                        continue;
                    } catch (IOException e) {
                        SmartLog.e(MLRemoteLandmarkAnalyzer.TAG, a.a("Error===>").append(e.getMessage()).toString());
                    }
                }
                if (z) {
                    return listHandleResult;
                }
                if (responseExecute == null || responseExecute.code() != 401) {
                    throw new MLException("Cloud landmark detection failed.", 3);
                }
                if (MLRemoteLandmarkAnalyzer.this.isTokenInvalid(responseExecute.errorBody())) {
                    throw new MLException("Token is invalid or expired.", 19);
                }
                throw new MLException("Cloud landmark detection failed.", 15);
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
