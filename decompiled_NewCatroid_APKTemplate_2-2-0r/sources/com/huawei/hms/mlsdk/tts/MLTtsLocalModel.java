package com.huawei.hms.mlsdk.tts;

import android.text.TextUtils;
import com.huawei.hms.mlsdk.model.download.MLRemoteModel;
import com.huawei.hms.mlsdk.model.download.strategy.ModelConfigManagerStrategy;
import com.huawei.hms.mlsdk.model.download.strategy.ModelDownloadStrategy;
import com.huawei.hms.mlsdk.model.download.strategy.ModelFileManagerStrategy;
import com.huawei.hms.mlsdk.tts.download.strategy.ConfigManager;
import com.huawei.hms.mlsdk.tts.download.strategy.DownloadManager;
import com.huawei.hms.mlsdk.tts.download.strategy.FileManager;
import java.util.Locale;
import java.util.Objects;

/* JADX INFO: loaded from: classes10.dex */
public class MLTtsLocalModel extends MLRemoteModel {
    private ModelConfigManagerStrategy modelConfigManagerStrategy;
    private ModelDownloadStrategy modelDownloadStrategy;
    private ModelFileManagerStrategy modelFileManagerStrategy;
    private String speakerName;

    public static class Factory {
        private String speakerName;

        public Factory(String str) {
            this.speakerName = str;
        }

        public MLTtsLocalModel create() {
            return new MLTtsLocalModel(this.speakerName);
        }
    }

    @Override // com.huawei.hms.mlsdk.model.download.MLRemoteModel
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof MLTtsLocalModel)) {
            return false;
        }
        MLTtsLocalModel mLTtsLocalModel = (MLTtsLocalModel) obj;
        String str = this.speakerName;
        if (str != null) {
            return TextUtils.equals(str, mLTtsLocalModel.getSpeakerName());
        }
        return false;
    }

    @Override // com.huawei.hms.mlsdk.model.download.MLRemoteModel
    public ModelConfigManagerStrategy getConfigStrategy() {
        return this.modelConfigManagerStrategy;
    }

    @Override // com.huawei.hms.mlsdk.model.download.MLRemoteModel
    public ModelDownloadStrategy getDownloadStrategy() {
        return this.modelDownloadStrategy;
    }

    @Override // com.huawei.hms.mlsdk.model.download.MLRemoteModel
    public ModelFileManagerStrategy getFileStrategy() {
        return this.modelFileManagerStrategy;
    }

    @Override // com.huawei.hms.mlsdk.model.download.MLRemoteModel
    public String getModelName() {
        return this.speakerName.toLowerCase(Locale.ENGLISH);
    }

    public String getSpeakerName() {
        return this.speakerName;
    }

    @Override // com.huawei.hms.mlsdk.model.download.MLRemoteModel
    public int hashCode() {
        return Objects.hashCode(this.speakerName);
    }

    private MLTtsLocalModel(String str) {
        this.modelDownloadStrategy = new DownloadManager();
        this.modelConfigManagerStrategy = new ConfigManager();
        this.modelFileManagerStrategy = new FileManager();
        this.speakerName = str;
    }
}
