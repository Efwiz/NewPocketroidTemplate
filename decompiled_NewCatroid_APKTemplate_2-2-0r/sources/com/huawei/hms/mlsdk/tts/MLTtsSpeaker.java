package com.huawei.hms.mlsdk.tts;

import com.huawei.hms.mlsdk.t.a;
import java.util.Arrays;

/* JADX INFO: loaded from: classes10.dex */
public class MLTtsSpeaker {
    private String mLanguage;
    private String mModelSize;
    private String mName;
    private String mSpeakerDesc;

    public MLTtsSpeaker(String str, String str2, String str3) {
        this.mLanguage = str;
        this.mName = str2;
        this.mSpeakerDesc = str3;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof MLTtsSpeaker)) {
            return false;
        }
        MLTtsSpeaker mLTtsSpeaker = (MLTtsSpeaker) obj;
        return this.mLanguage.equals(mLTtsSpeaker.getLanguage()) && this.mName.equals(mLTtsSpeaker.getName());
    }

    public String getLanguage() {
        return this.mLanguage;
    }

    public String getModelSize() {
        return this.mModelSize;
    }

    public String getName() {
        return this.mName;
    }

    public String getSpeakerDesc() {
        return this.mSpeakerDesc;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.mLanguage, this.mName, this.mSpeakerDesc, this.mModelSize});
    }

    public String toString() {
        return a.a(a.a(a.a(a.a("MLTtsSpeaker{language='"), this.mLanguage, '\'', ", mName='"), this.mName, '\'', ", speakerDesc='"), this.mSpeakerDesc, '\'', ", modelSize='").append(this.mModelSize).append('\'').append('}').toString();
    }

    public MLTtsSpeaker(String str, String str2, String str3, String str4) {
        this.mLanguage = str;
        this.mName = str2;
        this.mModelSize = str3;
        this.mSpeakerDesc = str4;
    }
}
