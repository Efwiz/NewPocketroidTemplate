package com.huawei.hms.mlsdk.langdetect.cloud;

import android.os.Bundle;
import android.text.TextUtils;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.huawei.hmf.tasks.Task;
import com.huawei.hmf.tasks.Tasks;
import com.huawei.hms.ml.common.utils.SmartLog;
import com.huawei.hms.ml.grs.GrsUtils;
import com.huawei.hms.ml.language.common.base.RestErrorResponse;
import com.huawei.hms.ml.language.common.utils.ErrorMessage;
import com.huawei.hms.ml.language.common.utils.ExceptionUtils;
import com.huawei.hms.ml.language.common.utils.ResultDomainList;
import com.huawei.hms.ml.language.common.utils.ResultsProcessingUtil;
import com.huawei.hms.ml.language.common.utils.RuleVerificationUtil;
import com.huawei.hms.mlsdk.common.AppSettingHolder;
import com.huawei.hms.mlsdk.common.MLApplication;
import com.huawei.hms.mlsdk.common.MLException;
import com.huawei.hms.mlsdk.langdetect.MLDetectedLang;
import com.huawei.hms.mlsdk.langdetect.cloud.bo.Language;
import com.huawei.hms.mlsdk.langdetect.cloud.bo.RemoteDetectData;
import com.huawei.hms.mlsdk.langdetect.cloud.bo.RemoteDetectResult;
import com.huawei.hms.mlsdk.langdetect.cloud.bo.RemoteDetectResults;
import com.huawei.hms.mlsdk.langdetect.cloud.bo.RemoteLangDetectReqContent;
import com.huawei.hms.mlsdk.langdetect.cloud.bo.RemoteLangDetectRequest;
import com.huawei.hms.mlsdk.langdetect.p.c;
import com.huawei.hms.mlsdk.langdetect.p.k;
import com.huawei.hms.mlsdk.langdetect.p.l;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import okhttp3.ResponseBody;
import retrofit2.Response;

/* JADX INFO: loaded from: classes10.dex */
public class MLRemoteLangDetector {
    public static final float FIRST_BEST_DETECTION_LANGUAGE_TRUSTED_THRESHOLD = 0.5f;
    public static final float PROBABILITY_DETECTION_LANGUAGE_TRUSTED_THRESHOLD = 0.01f;
    public static final String UNDETECTION_LANGUAGE_TRUSTED_THRESHOLD = "unknown";
    private static final Map<AppSettingHolder<MLRemoteLangDetectorSetting>, MLRemoteLangDetector> d = new HashMap();
    private MLApplication a;
    private MLRemoteLangDetectorSetting b;
    private c c;

    class a implements Callable<String> {
        final /* synthetic */ String a;

        a(String str) {
            this.a = str;
        }

        @Override // java.util.concurrent.Callable
        public String call() throws Exception {
            return MLRemoteLangDetector.this.b(this.a);
        }
    }

    class b implements Callable<List<MLDetectedLang>> {
        final /* synthetic */ String a;

        b(String str) {
            this.a = str;
        }

        @Override // java.util.concurrent.Callable
        public List<MLDetectedLang> call() throws Exception {
            return MLRemoteLangDetector.this.c(this.a);
        }
    }

    private MLRemoteLangDetector(MLApplication mLApplication, MLRemoteLangDetectorSetting mLRemoteLangDetectorSetting) {
        this.b = mLRemoteLangDetectorSetting;
        this.a = mLApplication;
        if (mLApplication != null) {
            SmartLog.e("MLRemoteLangDetect", "MLRemoteLangDetector init ok, appName=" + this.a.getAppName());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public List<MLDetectedLang> c(String str) throws MLException {
        com.huawei.hms.mlsdk.langdetect.p.b.a().a("TranslateEvent");
        com.huawei.hms.mlsdk.langdetect.p.b.a().a("TranslateEvent", 1, new Bundle());
        Bundle bundle = new Bundle();
        bundle.putInt("charNum", str.length());
        com.huawei.hms.mlsdk.langdetect.p.b.a().a("TranslateEvent", 0, bundle);
        List<MLDetectedLang> listA = a(a(Arrays.asList(str.split(File.separatorChar == '\\' ? "\\\\" : File.separator))));
        float trustedThreshold = this.b.getTrustedThreshold() > 0.0f ? this.b.getTrustedThreshold() : 0.01f;
        if (listA != null && listA.size() != 0) {
            Iterator<MLDetectedLang> it = listA.iterator();
            while (it.hasNext()) {
                if (it.next().getProbability() < trustedThreshold) {
                    it.remove();
                }
            }
            MLDetectedLang mLDetectedLang = listA.size() >= 1 ? listA.get(0) : null;
            bundle.putString("language", mLDetectedLang == null ? "" : mLDetectedLang.getLangCode());
            bundle.putBoolean("result", true);
            com.huawei.hms.mlsdk.langdetect.p.b.a().a("TranslateEvent", 0, bundle);
            com.huawei.hms.mlsdk.langdetect.p.b.a().a("TranslateEvent", 2, new Bundle());
            com.huawei.hms.mlsdk.langdetect.p.b.a().a("TranslateEvent", 3, new Bundle());
            com.huawei.hms.mlsdk.langdetect.p.b.a().a("TranslateEvent", 4, bundle);
            com.huawei.hms.mlsdk.langdetect.p.b.a().b("TranslateEvent");
        }
        return listA;
    }

    public Task<String> firstBestDetect(String str) {
        return Tasks.callInBackground(new a(str));
    }

    public Task<List<MLDetectedLang>> probabilityDetect(String str) {
        return Tasks.callInBackground(new b(str));
    }

    public void stop() {
        if (this.b != null) {
            AppSettingHolder appSettingHolderCreate = AppSettingHolder.create(this.a.getUniqueKey(), this.b);
            Map<AppSettingHolder<MLRemoteLangDetectorSetting>, MLRemoteLangDetector> map = d;
            map.get(appSettingHolderCreate);
            map.remove(appSettingHolderCreate);
            this.b = null;
        }
    }

    public String syncFirstBestDetect(String str) throws MLException {
        try {
            return b(str);
        } catch (Exception e) {
            throw ExceptionUtils.getMlException(e);
        }
    }

    public List<MLDetectedLang> syncProbabilityDetect(String str) throws MLException {
        try {
            return c(str);
        } catch (Exception e) {
            throw ExceptionUtils.getMlException(e);
        }
    }

    public static synchronized MLRemoteLangDetector a(MLApplication mLApplication, MLRemoteLangDetectorSetting mLRemoteLangDetectorSetting) {
        MLRemoteLangDetector mLRemoteLangDetector;
        AppSettingHolder<MLRemoteLangDetectorSetting> appSettingHolderCreate = AppSettingHolder.create(mLApplication.getUniqueKey(), mLRemoteLangDetectorSetting);
        Map<AppSettingHolder<MLRemoteLangDetectorSetting>, MLRemoteLangDetector> map = d;
        mLRemoteLangDetector = map.get(appSettingHolderCreate);
        if (mLRemoteLangDetector == null) {
            mLRemoteLangDetector = new MLRemoteLangDetector(mLApplication, mLRemoteLangDetectorSetting);
            map.put(appSettingHolderCreate, mLRemoteLangDetector);
        }
        return mLRemoteLangDetector;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String b(String str) throws MLException {
        com.huawei.hms.mlsdk.langdetect.p.b.a().a("TranslateEvent");
        com.huawei.hms.mlsdk.langdetect.p.b.a().a("TranslateEvent", 1, new Bundle());
        Bundle bundle = new Bundle();
        bundle.putInt("charNum", str.length());
        com.huawei.hms.mlsdk.langdetect.p.b.a().a("TranslateEvent", 0, bundle);
        List<MLDetectedLang> listA = a(a(Arrays.asList(str)));
        if (listA != null && !listA.isEmpty()) {
            float trustedThreshold = this.b.getTrustedThreshold() > 0.0f ? this.b.getTrustedThreshold() : 0.5f;
            MLDetectedLang mLDetectedLang = listA.get(0);
            if (mLDetectedLang != null) {
                String langCode = mLDetectedLang.getLangCode();
                if (mLDetectedLang.getProbability() >= trustedThreshold) {
                    bundle.putString("language", langCode);
                    bundle.putBoolean("result", true);
                    com.huawei.hms.mlsdk.langdetect.p.b.a().a("TranslateEvent", 0, bundle);
                    com.huawei.hms.mlsdk.langdetect.p.b.a().a("TranslateEvent", 2, new Bundle());
                    com.huawei.hms.mlsdk.langdetect.p.b.a().a("TranslateEvent", 3, new Bundle());
                    com.huawei.hms.mlsdk.langdetect.p.b.a().a("TranslateEvent", 4, bundle);
                    com.huawei.hms.mlsdk.langdetect.p.b.a().b("TranslateEvent");
                    return langCode;
                }
            }
        }
        SmartLog.e("MLRemoteLangDetect", "cloudResponse is empty");
        return "unknown";
    }

    private List<MLDetectedLang> a(RemoteLangDetectRequest remoteLangDetectRequest) throws MLException {
        List<String> listAddHttpsHeaders = GrsUtils.addHttpsHeaders(ResultDomainList.getDomainListUrl());
        if (listAddHttpsHeaders != null && !listAddHttpsHeaders.isEmpty()) {
            Map<String, String> mapA = new l.b().a().a();
            if (!RuleVerificationUtil.isHeaderInvalidate(mapA)) {
                String json = new Gson().toJson(remoteLangDetectRequest);
                List<MLDetectedLang> listA = null;
                boolean zEquals = false;
                Response<String> responseExecute = null;
                boolean z = false;
                for (String str : listAddHttpsHeaders) {
                    try {
                        SmartLog.i("MLRemoteLangDetect", "request start, url = " + str, false);
                        c cVar = (c) k.a().a(str).a(c.class);
                        this.c = cVar;
                        responseExecute = cVar.a("v1/translation/language/detect", mapA, json).execute();
                        z = responseExecute != null && responseExecute.code() == 200;
                        if (responseExecute != null) {
                            SmartLog.e("MLRemoteLangDetect", "response=" + responseExecute.toString());
                        }
                        if (z) {
                            listA = a(responseExecute);
                            break;
                        }
                        continue;
                    } catch (IOException e) {
                        SmartLog.e("MLRemoteLangDetect", "Error===>" + e.getMessage());
                    }
                }
                if (z) {
                    return listA;
                }
                a(String.valueOf(responseExecute != null ? responseExecute.code() : -1));
                if (responseExecute != null && responseExecute.code() == 401) {
                    ResponseBody responseBodyErrorBody = responseExecute.errorBody();
                    if (responseBodyErrorBody != null) {
                        try {
                            String strString = responseBodyErrorBody.string();
                            if (!TextUtils.isEmpty(strString)) {
                                try {
                                    zEquals = "001001".equals(((RestErrorResponse) new Gson().fromJson(strString, RestErrorResponse.class)).getRetCode());
                                } catch (JsonSyntaxException unused) {
                                }
                            }
                        } catch (IOException unused2) {
                            SmartLog.e("MLRemoteLangDetect", "detect isTokenInvalid io exception");
                        }
                    }
                    if (zEquals) {
                        throw new MLException("Token is invalid or expired.", 19);
                    }
                    throw new MLException("Identity authentication required.", 15);
                }
                throw new IllegalArgumentException("network error, failed to lang detect.");
            }
            throw new IllegalArgumentException("header param error, fail to lang detect");
        }
        throw new IllegalArgumentException("urlList is empty, fail to lang detect");
    }

    private void a(String str) {
        Bundle bundle = new Bundle();
        bundle.putString("errMsg", str);
        bundle.putBoolean("result", false);
        com.huawei.hms.mlsdk.langdetect.p.b.a().a("TranslateEvent", 5, bundle);
        com.huawei.hms.mlsdk.langdetect.p.b.a().a("TranslateEvent", 2, new Bundle());
        com.huawei.hms.mlsdk.langdetect.p.b.a().a("TranslateEvent", 4, bundle);
        com.huawei.hms.mlsdk.langdetect.p.b.a().b("TranslateEvent");
    }

    private RemoteLangDetectRequest a(List<String> list) {
        RemoteLangDetectRequest remoteLangDetectRequest = new RemoteLangDetectRequest();
        ArrayList arrayList = new ArrayList();
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(new RemoteLangDetectReqContent("", it.next()));
        }
        remoteLangDetectRequest.a(arrayList);
        return remoteLangDetectRequest;
    }

    private List<MLDetectedLang> a(Response<String> response) throws MLException {
        List<RemoteDetectResult> listA;
        RemoteDetectResult remoteDetectResult;
        List<Language> listA2;
        ArrayList arrayList = new ArrayList();
        RemoteDetectResults remoteDetectResults = (RemoteDetectResults) new Gson().fromJson(response.body(), RemoteDetectResults.class);
        if (remoteDetectResults != null) {
            String strB = remoteDetectResults.b();
            SmartLog.i("MLRemoteLangDetect", "detect responseCode : " + strB);
            if (!"0".equals(strB)) {
                a(strB);
                if (!"4003".equals(strB)) {
                    if (!"4005".equals(strB)) {
                        if (!"4007".equals(strB)) {
                            if ("2002".equals(strB)) {
                                throw new IllegalArgumentException(ResultsProcessingUtil.getMessage(remoteDetectResults.c()));
                            }
                            throw new IllegalArgumentException(ErrorMessage.ERROR_MESSAGE_CLOUD_EMPTY);
                        }
                        throw new MLException(ErrorMessage.ERROR_MESSAGE_QUOTA_UP_ON, 9);
                    }
                    throw new MLException(ErrorMessage.ERROR_MESSAGE_QUOTA_UP, 9);
                }
                throw new MLException(ErrorMessage.ERROR_MESSAGE_MAXIMUM, 9);
            }
            float trustedThreshold = this.b.getTrustedThreshold();
            RemoteDetectData remoteDetectDataA = remoteDetectResults.a();
            if (remoteDetectDataA != null && (listA = remoteDetectDataA.a()) != null && !listA.isEmpty() && (remoteDetectResult = listA.get(0)) != null && (listA2 = remoteDetectResult.a()) != null && !listA2.isEmpty()) {
                for (Language language : listA2) {
                    if (language != null) {
                        float fB = language.b();
                        if (trustedThreshold < 0.0f || fB >= trustedThreshold) {
                            arrayList.add(new MLDetectedLang(fB, language.a()));
                        }
                    }
                }
            }
            return arrayList;
        }
        throw new IllegalArgumentException("Cloud service return the empty result.");
    }
}
