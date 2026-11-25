package com.huawei.hms.mlsdk.tts.model;

import java.util.List;

/* JADX INFO: loaded from: classes10.dex */
public class LanguageInfo {
    private String language;
    private List<SpeakerInfo> speakers;

    public String getLanguage() {
        return this.language;
    }

    public List<SpeakerInfo> getSpeakers() {
        return this.speakers;
    }

    public void setLanguage(String str) {
        this.language = str;
    }

    public void setSpeakers(List<SpeakerInfo> list) {
        this.speakers = list;
    }
}
