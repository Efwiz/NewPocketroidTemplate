package com.huawei.hms.mlsdk.classification;

import com.huawei.hms.mlsdk.internal.client.Objects;

/* JADX INFO: loaded from: classes10.dex */
public class MLLocalClassificationAnalyzerSetting {
    private final float possibility;

    public static class Factory {
        private static final float DEFAULT_MIN_POSSIBILITY = 0.5f;
        private float possibility = 0.5f;

        public MLLocalClassificationAnalyzerSetting create() {
            return new MLLocalClassificationAnalyzerSetting(this.possibility);
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
        return (obj instanceof MLLocalClassificationAnalyzerSetting) && this.possibility == ((MLLocalClassificationAnalyzerSetting) obj).possibility;
    }

    public float getMinAcceptablePossibility() {
        return this.possibility;
    }

    public int hashCode() {
        return Objects.hashCode(Float.valueOf(this.possibility));
    }

    private MLLocalClassificationAnalyzerSetting(float f) {
        this.possibility = f;
    }
}
