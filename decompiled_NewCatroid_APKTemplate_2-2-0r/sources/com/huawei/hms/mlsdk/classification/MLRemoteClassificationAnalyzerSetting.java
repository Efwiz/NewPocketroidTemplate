package com.huawei.hms.mlsdk.classification;

import com.huawei.hms.mlsdk.internal.client.Objects;

/* JADX INFO: loaded from: classes10.dex */
public class MLRemoteClassificationAnalyzerSetting {
    private final boolean isEnableFingerprintVerification;
    private final int maxResults;
    private final float possibility;

    public static class Factory {
        private static final float DEFAULT_MIN_POSSIBILITY = 0.5f;
        private float possibility = 0.5f;
        private int maxResults = 0;
        private boolean isEnableFingerprintVerification = false;

        public MLRemoteClassificationAnalyzerSetting create() {
            return new MLRemoteClassificationAnalyzerSetting(this.possibility, this.isEnableFingerprintVerification, this.maxResults);
        }

        public Factory enableFingerprintVerification() {
            this.isEnableFingerprintVerification = true;
            return this;
        }

        public Factory setLargestNumOfReturns(int i) {
            this.maxResults = i;
            return this;
        }

        public Factory setMinAcceptablePossibility(float f) {
            if (f < 0.0f || f > 1.0f) {
                throw new IllegalArgumentException("Minimum possibility should be in range [0.0f, 1.0f].");
            }
            this.possibility = f;
            return this;
        }
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof MLRemoteClassificationAnalyzerSetting)) {
            return false;
        }
        MLRemoteClassificationAnalyzerSetting mLRemoteClassificationAnalyzerSetting = (MLRemoteClassificationAnalyzerSetting) obj;
        return Float.compare(this.possibility, mLRemoteClassificationAnalyzerSetting.possibility) == 0 && this.isEnableFingerprintVerification == mLRemoteClassificationAnalyzerSetting.isEnableFingerprintVerification && this.maxResults == mLRemoteClassificationAnalyzerSetting.maxResults;
    }

    public int getLargestNumOfReturns() {
        return this.maxResults;
    }

    public float getMinAcceptablePossibility() {
        return this.possibility;
    }

    public int hashCode() {
        return Objects.hashCode(Float.valueOf(this.possibility), Boolean.valueOf(this.isEnableFingerprintVerification), Integer.valueOf(this.maxResults));
    }

    public boolean isEnableFingerprintVerification() {
        return this.isEnableFingerprintVerification;
    }

    private MLRemoteClassificationAnalyzerSetting(float f, boolean z, int i) {
        this.possibility = f;
        this.isEnableFingerprintVerification = z;
        this.maxResults = i;
    }
}
