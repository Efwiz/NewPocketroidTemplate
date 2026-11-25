package com.huawei.hms.mlsdk.langdetect;

import com.huawei.hms.common.internal.Objects;

/* JADX INFO: loaded from: classes10.dex */
public class MLDetectedLang {
    private float a;
    private String b;

    public MLDetectedLang(float f, String str) {
        this.a = f;
        this.b = str;
    }

    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public String getLangCode() {
        return this.b;
    }

    public float getProbability() {
        return this.a;
    }

    public int hashCode() {
        return Objects.hashCode(Float.valueOf(this.a), this.b);
    }

    public String toString() {
        return "MLDetectedLang{probability=" + this.a + ", langCode='" + this.b + "'}";
    }
}
