package com.huawei.hms.mlsdk.landmark;

import com.huawei.hms.mlsdk.internal.client.Objects;

/* JADX INFO: loaded from: classes10.dex */
public class MLRemoteLandmarkAnalyzerSetting {
    public static final MLRemoteLandmarkAnalyzerSetting DEFAULT_SETTINGS = new Factory().create();
    public static final int NEWEST_PATTERN = 2;
    public static final int STEADY_PATTERN = 1;
    private final boolean enforceCertFingerprintMatch;
    private final int maxResults;
    private final int modelType;

    public static class Factory {
        private int maxResults = 10;
        private int modelType = 1;
        private boolean enforceCertFingerprintMatch = false;

        public MLRemoteLandmarkAnalyzerSetting create() {
            return new MLRemoteLandmarkAnalyzerSetting(this.maxResults, this.modelType, this.enforceCertFingerprintMatch);
        }

        public Factory enableFingerprintVerification() {
            this.enforceCertFingerprintMatch = true;
            return this;
        }

        public Factory setLargestNumOfReturns(int i) {
            this.maxResults = i;
            return this;
        }

        public Factory setPatternType(int i) {
            this.modelType = i;
            return this;
        }
    }

    public Factory create() {
        return new Factory();
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof MLRemoteLandmarkAnalyzerSetting)) {
            return false;
        }
        MLRemoteLandmarkAnalyzerSetting mLRemoteLandmarkAnalyzerSetting = (MLRemoteLandmarkAnalyzerSetting) obj;
        return this.maxResults == mLRemoteLandmarkAnalyzerSetting.maxResults && this.modelType == mLRemoteLandmarkAnalyzerSetting.modelType && this.enforceCertFingerprintMatch == mLRemoteLandmarkAnalyzerSetting.enforceCertFingerprintMatch;
    }

    public int getLargestNumOfReturns() {
        return this.maxResults;
    }

    public int getPatternType() {
        return this.modelType;
    }

    public int hashCode() {
        return Objects.hashCode(Integer.valueOf(this.maxResults), Integer.valueOf(this.modelType), Boolean.valueOf(this.enforceCertFingerprintMatch));
    }

    public final boolean isEnableFingerprintVerification() {
        return this.enforceCertFingerprintMatch;
    }

    private MLRemoteLandmarkAnalyzerSetting(int i, int i2, boolean z) {
        this.maxResults = i;
        this.modelType = i2;
        this.enforceCertFingerprintMatch = z;
    }
}
