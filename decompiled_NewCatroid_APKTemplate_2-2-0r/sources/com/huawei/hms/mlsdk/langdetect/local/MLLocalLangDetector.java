package com.huawei.hms.mlsdk.langdetect.local;

import com.huawei.hmf.tasks.Task;
import com.huawei.hmf.tasks.Tasks;
import com.huawei.hms.ml.common.utils.SmartLog;
import com.huawei.hms.ml.language.common.langdetect.LangDetectFrameParcel;
import com.huawei.hms.ml.language.common.langdetect.LangDetectOptionsParcel;
import com.huawei.hms.ml.language.common.langdetect.LangDetectParcel;
import com.huawei.hms.ml.language.common.utils.ExceptionUtils;
import com.huawei.hms.ml.language.common.utils.LanguageCodeUtil;
import com.huawei.hms.mlsdk.common.AppSettingHolder;
import com.huawei.hms.mlsdk.common.MLApplication;
import com.huawei.hms.mlsdk.common.MLException;
import com.huawei.hms.mlsdk.langdetect.MLDetectedLang;
import com.huawei.hms.mlsdk.langdetect.p.d;
import com.huawei.hms.mlsdk.langdetect.p.f;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

/* JADX INFO: loaded from: classes10.dex */
public class MLLocalLangDetector {
    public static final float FIRST_BEST_DETECTION_LANGUAGE_TRUSTED_THRESHOLD = 0.5f;
    public static final float PROBABILITY_DETECTION_LANGUAGE_TRUSTED_THRESHOLD = 0.01f;
    public static final String UNDETECTION_LANGUAGE_TRUSTED_THRESHOLD = "unknown";
    private static final Map<AppSettingHolder<MLLocalLangDetectorSetting>, MLLocalLangDetector> d = new HashMap();
    private volatile boolean a = false;
    private MLLocalLangDetectorSetting b;
    private MLApplication c;

    class a implements Callable<String> {
        final /* synthetic */ String a;

        a(String str) {
            this.a = str;
        }

        @Override // java.util.concurrent.Callable
        public String call() throws Exception {
            return MLLocalLangDetector.this.a(this.a);
        }
    }

    class b implements Callable<List<MLDetectedLang>> {
        final /* synthetic */ String a;

        b(String str) {
            this.a = str;
        }

        @Override // java.util.concurrent.Callable
        public List<MLDetectedLang> call() throws Exception {
            return MLLocalLangDetector.this.b(this.a);
        }
    }

    private MLLocalLangDetector(MLApplication mLApplication, MLLocalLangDetectorSetting mLLocalLangDetectorSetting) {
        this.b = mLLocalLangDetectorSetting;
        this.c = mLApplication;
        SmartLog.d("MLLocalLangDetector", "MLLocalLangDetector init ok, appName=" + this.c.getAppName());
    }

    public Task<String> firstBestDetect(String str) {
        this.a = true;
        return Tasks.callInBackground(new a(str));
    }

    public Task<List<MLDetectedLang>> probabilityDetect(String str) {
        this.a = true;
        return Tasks.callInBackground(new b(str));
    }

    public void stop() {
        if (this.a) {
            return;
        }
        f.b().b(this.c.getAppContext());
    }

    public String syncFirstBestDetect(String str) throws MLException {
        try {
            return a(str);
        } catch (Exception e) {
            throw ExceptionUtils.getMlException(e);
        }
    }

    public List<MLDetectedLang> syncProbabilityDetect(String str) throws MLException {
        try {
            return b(str);
        } catch (Exception e) {
            throw ExceptionUtils.getMlException(e);
        }
    }

    public static synchronized MLLocalLangDetector a(MLApplication mLApplication, MLLocalLangDetectorSetting mLLocalLangDetectorSetting) {
        MLLocalLangDetector mLLocalLangDetector;
        AppSettingHolder<MLLocalLangDetectorSetting> appSettingHolderCreate = AppSettingHolder.create(mLApplication.getUniqueKey(), mLLocalLangDetectorSetting);
        Map<AppSettingHolder<MLLocalLangDetectorSetting>, MLLocalLangDetector> map = d;
        mLLocalLangDetector = map.get(appSettingHolderCreate);
        if (mLLocalLangDetector == null) {
            mLLocalLangDetector = new MLLocalLangDetector(mLApplication, mLLocalLangDetectorSetting);
            map.put(appSettingHolderCreate, mLLocalLangDetector);
        }
        f.b().a(mLApplication.getAppContext());
        LangDetectOptionsParcel langDetectOptionsParcel = new LangDetectOptionsParcel(mLLocalLangDetectorSetting.getTrustedThreshold(), mLApplication.toBundle());
        f fVarB = f.b();
        mLApplication.getAppContext();
        fVarB.a(langDetectOptionsParcel);
        return mLLocalLangDetector;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public List<MLDetectedLang> b(String str) throws MLException {
        this.a = true;
        try {
            if (str.isEmpty()) {
                throw new MLException("probabilityDetect text is empty", 5);
            }
            if (str.length() > 5000) {
                throw new MLException("probabilityDetect source text is to long", 5);
            }
            float trustedThreshold = this.b.getTrustedThreshold();
            if (trustedThreshold < 0.0f || trustedThreshold > 1.0f) {
                throw new MLException("probabilityDetect threshold is illegal", 5);
            }
            String strA = d.a(str);
            if (strA.isEmpty()) {
                MLDetectedLang mLDetectedLang = new MLDetectedLang(1.0f, LanguageCodeUtil.EN);
                ArrayList arrayList = new ArrayList();
                arrayList.add(mLDetectedLang);
                this.a = false;
                return arrayList;
            }
            List<LangDetectParcel> listA = f.b().a(this.c.getAppContext(), this.c.toBundle(), new LangDetectFrameParcel(strA, 50), new LangDetectOptionsParcel(trustedThreshold, this.c.toBundle()));
            if (listA != null && !listA.isEmpty()) {
                ArrayList arrayList2 = new ArrayList();
                for (LangDetectParcel langDetectParcel : listA) {
                    arrayList2.add(new MLDetectedLang(langDetectParcel.prob, langDetectParcel.languageCode));
                }
                this.a = false;
                return arrayList2;
            }
            List<MLDetectedLang> listEmptyList = Collections.emptyList();
            this.a = false;
            return listEmptyList;
        } catch (Throwable th) {
            this.a = false;
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String a(String str) throws MLException {
        this.a = true;
        try {
            if (!str.isEmpty()) {
                if (str.length() <= 5000) {
                    float trustedThreshold = this.b.getTrustedThreshold();
                    if (trustedThreshold >= 0.0f && trustedThreshold <= 1.0f) {
                        String strA = d.a(str);
                        if (strA.isEmpty()) {
                            this.a = false;
                            return LanguageCodeUtil.EN;
                        }
                        List<LangDetectParcel> listA = f.b().a(this.c.getAppContext(), this.c.toBundle(), new LangDetectFrameParcel(strA, 1), new LangDetectOptionsParcel(trustedThreshold, this.c.toBundle()));
                        if (listA != null && !listA.isEmpty()) {
                            String str2 = listA.get(0).languageCode;
                            this.a = false;
                            return str2;
                        }
                        this.a = false;
                        return "unknown";
                    }
                    throw new MLException("firstBestDetect threshold is illegal", 5);
                }
                throw new MLException("firstBestDetect source text is to long", 5);
            }
            throw new MLException("firstBestDetect text is empty", 5);
        } catch (Throwable th) {
            this.a = false;
            throw th;
        }
    }
}
