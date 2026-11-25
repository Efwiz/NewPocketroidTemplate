package com.huawei.hms.mlsdk.skeleton;

/* JADX INFO: loaded from: classes10.dex */
public class MLSkeletonAnalyzerSetting {
    public static final int TYPE_NORMAL = 0;
    public static final int TYPE_YOGA = 1;
    public int a;
    public int b;

    public static class Factory {
        public int a = 0;
        public int b = 0;

        public MLSkeletonAnalyzerSetting create() {
            return new MLSkeletonAnalyzerSetting(this.a, this.b, null);
        }

        public Factory setAnalyzerType(int i) {
            this.b = i;
            return this;
        }
    }

    public /* synthetic */ MLSkeletonAnalyzerSetting(int i, int i2, a aVar) {
        this.a = i;
        this.b = i2;
    }

    public int a() {
        return this.b;
    }

    public int b() {
        return this.a;
    }
}
