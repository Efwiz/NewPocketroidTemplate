package com.huawei.hms.mlsdk.text;

import com.huawei.hms.ml.common.base.SmartLog;
import com.huawei.hms.mlsdk.internal.client.Objects;

/* JADX INFO: loaded from: classes10.dex */
public class MLLocalTextSetting {
    public static final int OCR_DETECT_MODE = 1;
    public static final int OCR_TRACKING_MODE = 2;
    private final String language;
    private final int ocrMode;

    public static class Factory {
        private String language = "rm";
        private int ocrMode = 1;

        public MLLocalTextSetting create() {
            return new MLLocalTextSetting(this.language, this.ocrMode);
        }

        public Factory setLanguage(String str) {
            if (str != null) {
                this.language = str;
            }
            return this;
        }

        public Factory setOCRMode(int i) {
            if (i != 1 && i != 2) {
                this.ocrMode = 1;
                SmartLog.d("RecognizerOptions", "ocrMode should be either 1 for OCR_DETECT_MODE or 2 for OCR_TRACKING_MODE, set ocrMode OCR_DETECT_MODE.");
            }
            this.ocrMode = i;
            return this;
        }
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof MLLocalTextSetting)) {
            return false;
        }
        MLLocalTextSetting mLLocalTextSetting = (MLLocalTextSetting) obj;
        return this.language.equals(mLLocalTextSetting.getLanguage()) && this.ocrMode == mLLocalTextSetting.ocrMode;
    }

    public String getLanguage() {
        return this.language;
    }

    public int getOCRMode() {
        return this.ocrMode;
    }

    public int hashCode() {
        return Objects.hashCode(this.language, Integer.valueOf(this.ocrMode));
    }

    private MLLocalTextSetting(String str, int i) {
        this.language = str;
        this.ocrMode = i;
    }
}
