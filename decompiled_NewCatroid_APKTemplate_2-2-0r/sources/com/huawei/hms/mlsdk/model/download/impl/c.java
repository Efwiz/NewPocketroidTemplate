package com.huawei.hms.mlsdk.model.download.impl;

import android.content.Context;
import com.google.common.net.HttpHeaders;
import com.huawei.hms.ml.common.utils.CountryCodeBean;
import com.huawei.hms.ml.common.utils.SmartLog;
import com.huawei.hms.ml.grs.GrsRegionUtils;
import com.huawei.hms.ml.grs.GrsUtils;
import com.huawei.hms.mlsdk.common.MLApplication;
import com.huawei.hms.mlsdk.common.MLApplicationSetting;
import com.huawei.hms.mlsdk.common.MLException;
import com.huawei.hms.mlsdk.model.download.MLRemoteModel;
import com.huawei.hms.mlsdk.model.download.p.g;
import com.huawei.hms.mlsdk.model.download.p.i;
import com.huawei.hms.mlsdk.model.download.strategy.ModelDownloadStrategy;
import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import okhttp3.Response;

/* JADX INFO: compiled from: ModelDownloadManager.java */
/* JADX INFO: loaded from: classes10.dex */
public class c {
    private WeakReference<Context> a;
    private com.huawei.hms.mlsdk.model.download.strategy.a b;
    private Map<String, i> c = new HashMap();

    public c(WeakReference<Context> weakReference, com.huawei.hms.mlsdk.model.download.strategy.a aVar) {
        this.a = weakReference;
        this.b = aVar;
    }

    public ModelResponse a(MLRemoteModel mLRemoteModel, String str) throws MLException {
        List<String> visionSearchUrls;
        String grsCountryCode = GrsRegionUtils.getGrsCountryCode();
        if (str != null) {
            visionSearchUrls = GrsUtils.getVisionSearchUrls(GrsUtils.initGrsVisionSearchClientWithCountry(MLApplication.getInstance().getAppContext(), str));
        } else {
            visionSearchUrls = grsCountryCode != null ? GrsUtils.getVisionSearchUrls(GrsUtils.initGrsVisionSearchClientWithCountry(MLApplication.getInstance().getAppContext(), grsCountryCode)) : GrsUtils.getUrls(MLApplication.getInstance().getAppContext(), false);
        }
        List<String> listAddHttpsHeaders = GrsUtils.addHttpsHeaders(visionSearchUrls);
        if (listAddHttpsHeaders == null || listAddHttpsHeaders.isEmpty()) {
            SmartLog.e("MLSDK_MODEL_ModelDownloadManager", "queryModelInfo urlList is null!");
            throw new MLException("Query Url List failed", 3);
        }
        WeakReference<Context> weakReference = this.a;
        MLApplicationSetting appSetting = MLApplication.getInstance().getAppSetting() != null ? MLApplication.getInstance().getAppSetting() : MLApplicationSetting.fromResource(weakReference.get());
        HashMap map = new HashMap();
        UUID uuidRandomUUID = UUID.randomUUID();
        map.put("Content-Type", "application/json");
        map.put(HttpHeaders.X_REQUEST_ID, String.valueOf(uuidRandomUUID));
        map.put("X-User-Agent", "X-User-Agent");
        map.put("appId", appSetting.getAppId());
        map.put("HMS-APPLICATION-ID", appSetting.getAppId());
        map.put("X-Package-Name", appSetting.getPackageName());
        map.put("X-Country-Code", new CountryCodeBean(weakReference.get(), false).getCountryCode());
        map.put("supplierId", "supplierId");
        map.put("accept", "application/json");
        map.put("certFingerprint", appSetting.getCertFingerprint());
        map.put("Authorization", "Bearer " + MLApplication.getInstance().getAuthorizationToken());
        map.put("X-Mlkit-Version", appSetting.getMLSdkVersion());
        String str2 = (String) map.get("appId");
        if (str2 == null || str2.isEmpty()) {
            SmartLog.e("MLSDK_MODEL_HttpUtils", "Http request app_id is empty");
        } else {
            String str3 = (String) map.get("Authorization");
            if (str3 == null || str3.equals("Bearer ")) {
                SmartLog.e("MLSDK_MODEL_HttpUtils", "Http request Authorization is empty");
            } else {
                String str4 = (String) map.get("X-Package-Name");
                if (str4 != null && !str4.isEmpty()) {
                    ModelDownloadStrategy modelDownloadStrategyB = this.b.b(mLRemoteModel);
                    Iterator<String> it = listAddHttpsHeaders.iterator();
                    while (it.hasNext()) {
                        String string = com.huawei.hms.mlsdk.model.download.p.a.a(it.next()).append(modelDownloadStrategyB.getRequestUrl()).toString();
                        int iIndexOf = string.indexOf("//");
                        String str5 = string.substring(0, iIndexOf) + "//" + string.substring(iIndexOf + 2).replaceAll("/+", "/");
                        SmartLog.d("MLSDK_MODEL_ModelDownloadManager", "query address is " + str5);
                        String requestBody = modelDownloadStrategyB.getRequestBody(mLRemoteModel);
                        SmartLog.d("MLSDK_MODEL_ModelDownloadManager", "post param is " + requestBody);
                        try {
                            Response responseA = com.huawei.hms.mlsdk.model.download.p.e.a(g.a(this.a.get()), str5, map, requestBody);
                            int iCode = responseA.code();
                            SmartLog.i("MLSDK_MODEL_ModelDownloadManager", "queryModelInfo response code: " + iCode);
                            if (iCode == 200) {
                                return new ModelResponse(responseA);
                            }
                            if (iCode == 401) {
                                modelDownloadStrategyB.handleRequestFail(new ModelResponse(responseA));
                                break;
                            }
                        } catch (IOException e) {
                            SmartLog.e("MLSDK_MODEL_ModelDownloadManager", com.huawei.hms.mlsdk.model.download.p.a.a("post failed: ").append(e.getMessage()).toString());
                            throw new MLException("queryModelInfo post failed", 3);
                        }
                    }
                    return null;
                }
                SmartLog.e("MLSDK_MODEL_HttpUtils", "header file package_name is empty");
            }
        }
        SmartLog.e("MLSDK_MODEL_ModelDownloadManager", "queryModelInfo headers is invalid!");
        throw new MLException("Create Http request header failed", 3);
    }

    public void b(MLRemoteModel mLRemoteModel, ModelResponse modelResponse) throws MLException {
        this.b.b(mLRemoteModel).handleRequestFail(modelResponse);
    }

    /* JADX WARN: Code duplicated, block: B:12:0x0032  */
    /* JADX WARN: Code duplicated, block: B:14:0x0037 A[RETURN] */
    public boolean a(MLRemoteModel mLRemoteModel) {
        i value;
        String modelName = mLRemoteModel.getModelName();
        for (Map.Entry<String, i> entry : this.c.entrySet()) {
            String key = entry.getKey();
            if (key != null && key.equalsIgnoreCase(modelName)) {
                value = entry.getValue();
                if (value != null) {
                    return value.d();
                }
                return false;
            }
        }
        value = null;
        if (value != null) {
            return value.d();
        }
        return false;
    }

    public void a(MLRemoteModel mLRemoteModel, com.huawei.hms.mlsdk.model.download.p.f fVar, ModelResponse modelResponse) throws MLException {
        ModelDownloadStrategy modelDownloadStrategyB = this.b.b(mLRemoteModel);
        String downloadUrl = modelDownloadStrategyB.getDownloadUrl(modelResponse);
        if (downloadUrl != null && !downloadUrl.isEmpty()) {
            SmartLog.d("MLSDK_MODEL_ModelDownloadManager", "downloadModel downloadUrl: " + downloadUrl);
            Context context = this.a.get();
            String str = null;
            if (context == null) {
                SmartLog.i("MLSDK_MODEL_ModelDownloadManager", "getDownloadTempFolder context is invalid");
            } else {
                String str2 = context.getCacheDir() + File.separator + "temp";
                SmartLog.d("MLSDK_MODEL_ModelDownloadManager", "downloadModel tempFolder: " + str2);
                File file = new File(str2);
                if (file.exists() || file.mkdir()) {
                    str = str2;
                } else {
                    SmartLog.e("MLSDK_MODEL_ModelDownloadManager", "mkdir temp folder failed");
                }
            }
            if (str != null) {
                String string = com.huawei.hms.mlsdk.model.download.p.a.a(str).append(File.separator).append(modelDownloadStrategyB.getModelFileName(modelResponse)).toString();
                SmartLog.d("MLSDK_MODEL_ModelDownloadManager", "downloadModel tempFile: " + string);
                i iVar = new i(mLRemoteModel.getModelName(), downloadUrl, new File(string), new com.huawei.hms.mlsdk.model.download.p.d(this.c, fVar));
                this.c.put(mLRemoteModel.getModelName(), iVar);
                iVar.a(g.a(this.a.get()), 2);
                return;
            }
            throw new MLException("downloadModel failed because mkdir temp folder failed", 2);
        }
        SmartLog.e("MLSDK_MODEL_ModelDownloadManager", "The model does not exist");
        throw new MLException("The model does not exist", 7);
    }

    public String a(MLRemoteModel mLRemoteModel, ModelResponse modelResponse) {
        return this.b.b(mLRemoteModel).getHaField(modelResponse);
    }
}
