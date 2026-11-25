package com.huawei.hms.mlsdk.langdetect;

import com.huawei.hms.mlsdk.common.MLApplication;
import com.huawei.hms.mlsdk.langdetect.cloud.MLRemoteLangDetector;
import com.huawei.hms.mlsdk.langdetect.cloud.MLRemoteLangDetectorSetting;
import com.huawei.hms.mlsdk.langdetect.local.MLLocalLangDetector;
import com.huawei.hms.mlsdk.langdetect.local.MLLocalLangDetectorSetting;

/* JADX INFO: loaded from: classes10.dex */
public class MLLangDetectorFactory {
    private static final MLRemoteLangDetectorSetting b = new MLRemoteLangDetectorSetting.Factory().create();
    private static final MLLocalLangDetectorSetting c = new MLLocalLangDetectorSetting.Factory().create();
    private final MLApplication a;

    private MLLangDetectorFactory(MLApplication mLApplication) {
        this.a = mLApplication;
    }

    public static MLLangDetectorFactory getInstance() {
        return getInstance(MLApplication.getInstance());
    }

    public MLLocalLangDetector getLocalLangDetector(MLLocalLangDetectorSetting mLLocalLangDetectorSetting) {
        return MLLocalLangDetector.a(this.a, mLLocalLangDetectorSetting);
    }

    public MLRemoteLangDetector getRemoteLangDetector() {
        return MLRemoteLangDetector.a(this.a, b);
    }

    public static MLLangDetectorFactory getInstance(MLApplication mLApplication) {
        return new MLLangDetectorFactory(mLApplication);
    }

    public MLLocalLangDetector getLocalLangDetector() {
        return MLLocalLangDetector.a(this.a, c);
    }

    public MLRemoteLangDetector getRemoteLangDetector(MLRemoteLangDetectorSetting mLRemoteLangDetectorSetting) {
        return MLRemoteLangDetector.a(this.a, mLRemoteLangDetectorSetting);
    }
}
