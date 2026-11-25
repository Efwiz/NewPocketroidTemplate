package com.huawei.hms.mlsdk.tts;

import com.huawei.hms.mlsdk.t.a;

/* JADX INFO: loaded from: classes10.dex */
public class MLTtsConfig {
    private String synthesizeMode = "online";
    private String person = null;
    private float speed = 0.0f;
    private float volume = 0.0f;
    private String language = null;

    public MLTtsConfig attach(MLTtsConfig mLTtsConfig) {
        if (mLTtsConfig == null) {
            return this;
        }
        if (mLTtsConfig.getSynthesizeMode() != null) {
            this.synthesizeMode = mLTtsConfig.getSynthesizeMode();
        }
        if (mLTtsConfig.getPerson() != null) {
            this.person = mLTtsConfig.getPerson();
        }
        if (mLTtsConfig.getVolume() != 0.0f) {
            this.volume = mLTtsConfig.getVolume();
        }
        if (mLTtsConfig.getSpeed() != 0.0f) {
            this.speed = mLTtsConfig.getSpeed();
        }
        if (mLTtsConfig.getLanguage() != null) {
            this.language = mLTtsConfig.getLanguage();
        }
        return this;
    }

    public String getLanguage() {
        return this.language;
    }

    public String getPerson() {
        return this.person;
    }

    public float getSpeed() {
        return this.speed;
    }

    public String getSynthesizeMode() {
        return this.synthesizeMode;
    }

    public float getVolume() {
        return this.volume;
    }

    public MLTtsConfig setLanguage(String str) {
        this.language = str;
        return this;
    }

    public MLTtsConfig setPerson(String str) {
        this.person = str;
        return this;
    }

    public MLTtsConfig setSpeed(float f) {
        this.speed = f;
        return this;
    }

    public MLTtsConfig setSynthesizeMode(String str) {
        this.synthesizeMode = str;
        return this;
    }

    public MLTtsConfig setVolume(float f) {
        this.volume = f;
        return this;
    }

    public String toString() {
        return a.a(a.a("MLTtsConfig{synthesizeMode=").append(this.synthesizeMode).append(",person='"), this.person, '\'', ", speed=").append(this.speed).append(", volume=").append(this.volume).append(", language='").append(this.language).append('\'').append('}').toString();
    }
}
