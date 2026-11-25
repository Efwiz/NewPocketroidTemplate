package com.huawei.hms.mlsdk.langdetect.local;

import com.huawei.hms.common.internal.Objects;

/* JADX INFO: loaded from: classes10.dex */
public class MLLocalLangDetectorSetting {
    private float a;

    public static class Factory {
        private float a = 0.0f;

        public MLLocalLangDetectorSetting create() {
            return new MLLocalLangDetectorSetting(this.a, null);
        }

        public Factory setTrustedThreshold(float f) {
            this.a = f;
            return this;
        }
    }

    /* synthetic */ MLLocalLangDetectorSetting(float f, a aVar) {
        this.a = f;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof MLLocalLangDetectorSetting) {
            return Objects.equal(Float.valueOf(this.a), Float.valueOf(((MLLocalLangDetectorSetting) obj).getTrustedThreshold()));
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
