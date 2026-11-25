package com.huawei.hms.mlsdk.skeleton;

import com.huawei.hms.mlsdk.common.MLApplication;

/* JADX INFO: loaded from: classes10.dex */
public class MLSkeletonAnalyzerFactory {
    public static MLSkeletonAnalyzerSetting b = new MLSkeletonAnalyzerSetting.Factory().create();
    public final MLApplication a;

    public MLSkeletonAnalyzerFactory(MLApplication mLApplication) {
        this.a = mLApplication;
    }

    public static MLSkeletonAnalyzerFactory getInstance() {
        return new MLSkeletonAnalyzerFactory(MLApplication.getInstance());
    }

    public MLSkeletonAnalyzer getSkeletonAnalyzer() {
        return MLSkeletonAnalyzer.a(this.a, b);
    }

    public MLSkeletonAnalyzer getSkeletonAnalyzer(MLSkeletonAnalyzerSetting mLSkeletonAnalyzerSetting) {
        return MLSkeletonAnalyzer.a(this.a, mLSkeletonAnalyzerSetting);
    }
}
