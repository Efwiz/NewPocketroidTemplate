package com.huawei.hms.mlsdk.document;

import com.huawei.hms.mlsdk.text.MLRemoteTextSetting;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/* JADX INFO: loaded from: classes10.dex */
public class MLDocumentSetting {
    private final String borderType;
    private final boolean enableFingerprintVerification;
    private final List<String> possibleLanguages;

    public static class Factory {
        private List<String> hintedLanguages = new ArrayList();
        private boolean enableFingerprintVerification = false;
        private String borderType = MLRemoteTextSetting.NGON;

        public MLDocumentSetting create() {
            return new MLDocumentSetting(this.hintedLanguages, this.enableFingerprintVerification, this.borderType);
        }

        public Factory enableFingerprintVerification() {
            this.enableFingerprintVerification = true;
            return this;
        }

        public Factory setBorderType(String str) {
            if (str.equals(MLRemoteTextSetting.ARC)) {
                this.borderType = str;
            } else {
                this.borderType = MLRemoteTextSetting.NGON;
            }
            return this;
        }

        public Factory setLanguageList(List<String> list) {
            this.hintedLanguages = list;
            if (list == null) {
                this.hintedLanguages = new ArrayList();
            }
            return this;
        }
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof MLDocumentSetting)) {
            return false;
        }
        MLDocumentSetting mLDocumentSetting = (MLDocumentSetting) obj;
        return this.possibleLanguages.equals(mLDocumentSetting.getLanguageList()) && this.enableFingerprintVerification == mLDocumentSetting.enableFingerprintVerification && this.borderType.equals(mLDocumentSetting.borderType);
    }

    public String getBorderType() {
        return this.borderType;
    }

    public List<String> getLanguageList() {
        return this.possibleLanguages;
    }

    public int hashCode() {
        return Objects.hashCode(new Object[]{this.possibleLanguages, Boolean.valueOf(this.enableFingerprintVerification), this.borderType});
    }

    public final boolean isEnableFingerprintVerification() {
        return this.enableFingerprintVerification;
    }

    private MLDocumentSetting(List<String> list, boolean z, String str) {
        if (list == null) {
            this.possibleLanguages = new ArrayList();
        } else {
            this.possibleLanguages = list;
        }
        this.enableFingerprintVerification = z;
        if (str.equals(MLRemoteTextSetting.ARC)) {
            this.borderType = str;
        } else {
            this.borderType = MLRemoteTextSetting.NGON;
        }
    }
}
