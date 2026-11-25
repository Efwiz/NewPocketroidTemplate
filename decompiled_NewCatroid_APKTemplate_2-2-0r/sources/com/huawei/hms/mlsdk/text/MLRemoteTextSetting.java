package com.huawei.hms.mlsdk.text;

import com.huawei.hms.ml.common.base.SmartLog;
import com.huawei.hms.mlsdk.internal.client.Objects;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* JADX INFO: loaded from: classes10.dex */
public class MLRemoteTextSetting {
    public static final String ARC = "ARC";
    public static final String NGON = "NGON";
    public static final int OCR_COMPACT_SCENE = 2;
    public static final int OCR_LOOSE_SCENE = 1;
    private final String borderType;
    private final boolean enableFingerprintVerification;
    private final List<String> languageList;
    private final int textDensityScene;

    public static class Factory {
        private List<String> languageList = new ArrayList();
        private int textType = 1;
        private boolean enableFingerprintVerification = false;
        private String borderType = MLRemoteTextSetting.NGON;

        public MLRemoteTextSetting create() {
            return new MLRemoteTextSetting(this.languageList, this.textType, this.enableFingerprintVerification, this.borderType);
        }

        public Factory enableFingerprintVerification() {
            this.enableFingerprintVerification = true;
            return this;
        }

        public Factory setBorderType(String str) {
            this.borderType = str;
            return this;
        }

        public Factory setLanguageList(List<String> list) {
            if (list != null) {
                this.languageList = list;
            }
            Collections.sort(this.languageList);
            return this;
        }

        public Factory setTextDensityScene(int i) {
            if (i != 1 && i != 2) {
                this.textType = 1;
                SmartLog.d("MLRemoteTextSetting-RecognizerOptions", "ModelType should be either 1 for TYPE_LOOSE or 2 for TYPE_COMPACT, set ModelType TYPE_LOOSE.");
            }
            this.textType = i;
            return this;
        }
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof MLRemoteTextSetting)) {
            return false;
        }
        MLRemoteTextSetting mLRemoteTextSetting = (MLRemoteTextSetting) obj;
        return this.languageList.equals(mLRemoteTextSetting.getLanguageList()) && this.borderType == mLRemoteTextSetting.borderType && this.enableFingerprintVerification == mLRemoteTextSetting.enableFingerprintVerification;
    }

    public String getBorderType() {
        return this.borderType;
    }

    public List<String> getLanguageList() {
        return this.languageList;
    }

    public int getTextDensityScene() {
        return this.textDensityScene;
    }

    public int hashCode() {
        return Objects.hashCode(this.languageList, this.borderType, Boolean.valueOf(this.enableFingerprintVerification), this.borderType);
    }

    public final boolean isEnableFingerprintVerification() {
        return this.enableFingerprintVerification;
    }

    private MLRemoteTextSetting(List<String> list, int i, boolean z, String str) {
        if (list == null) {
            this.languageList = new ArrayList();
        } else {
            this.languageList = list;
        }
        this.textDensityScene = i;
        this.enableFingerprintVerification = z;
        this.borderType = str;
    }
}
