package com.huawei.hms.mlsdk.objects;

import java.util.Arrays;

/* JADX INFO: loaded from: classes10.dex */
public class MLObjectAnalyzerSetting {
    public static final int TYPE_PICTURE = 0;
    public static final int TYPE_VIDEO = 1;
    private final int analyzerType;
    private final boolean isClassificationAllowed;
    private final boolean isMultipleResultsAllowed;

    public static class Factory {
        private int analyzerType = 1;
        private boolean isMultipleResultsAllowed = false;
        private boolean isClassificationAllowed = false;

        public Factory allowClassification() {
            this.isClassificationAllowed = true;
            return this;
        }

        public Factory allowMultiResults() {
            this.isMultipleResultsAllowed = true;
            return this;
        }

        public MLObjectAnalyzerSetting create() {
            return new MLObjectAnalyzerSetting(this.analyzerType, this.isMultipleResultsAllowed, this.isClassificationAllowed);
        }

        public Factory setAnalyzerType(int i) {
            this.analyzerType = i;
            return this;
        }
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof MLObjectAnalyzerSetting)) {
            return false;
        }
        MLObjectAnalyzerSetting mLObjectAnalyzerSetting = (MLObjectAnalyzerSetting) obj;
        return mLObjectAnalyzerSetting.analyzerType == this.analyzerType && mLObjectAnalyzerSetting.isClassificationAllowed == this.isClassificationAllowed && mLObjectAnalyzerSetting.isMultipleResultsAllowed == this.isMultipleResultsAllowed;
    }

    public final int getAnalyzerType() {
        return this.analyzerType;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{Integer.valueOf(this.analyzerType), Boolean.valueOf(this.isClassificationAllowed), Boolean.valueOf(this.isMultipleResultsAllowed)});
    }

    public final boolean isClassificationAllowed() {
        return this.isClassificationAllowed;
    }

    public final boolean isMultipleResultsAllowed() {
        return this.isMultipleResultsAllowed;
    }

    private MLObjectAnalyzerSetting(int i, boolean z, boolean z2) {
        this.analyzerType = i;
        this.isMultipleResultsAllowed = z;
        this.isClassificationAllowed = z2;
    }
}
