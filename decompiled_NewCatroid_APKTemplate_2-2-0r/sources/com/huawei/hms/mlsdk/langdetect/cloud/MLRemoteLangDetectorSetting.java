package com.huawei.hms.mlsdk.langdetect.cloud;

import com.huawei.hms.common.internal.Objects;

/* JADX INFO: loaded from: classes10.dex */
public class MLRemoteLangDetectorSetting {
    private float a;

    public static class Factory {
        private float a = 0.0f;

        public MLRemoteLangDetectorSetting create() {
            return new MLRemoteLangDetectorSetting(this.a, null);
        }

        public Factory setTrustedThreshold(float f) {
            this.a = f;
            return this;
        }
    }

    /* synthetic */ MLRemoteLangDetectorSetting(float f, a aVar) {
        this.a = f;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof MLRemoteLangDetectorSetting) {
            return Objects.equal(Float.valueOf(this.a), Float.valueOf(((MLRemoteLangDetectorSetting) obj).getTrustedThreshold()));
        }
        return false;
    }

    public float getTrustedThreshold() {
        return this.a;
    }

    public int hashCode() {
        return Objects.hashCode(Float.valueOf(this.a));
    }
}
