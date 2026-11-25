package com.huawei.hms.mlsdk.tts.engine.common;

import com.huawei.hms.mlsdk.t.k0;

/* JADX INFO: compiled from: EngineSettings.java */
/* JADX INFO: loaded from: classes10.dex */
public class a {
    private String a;
    private String b;
    private int c;
    private float d;
    private float e;
    private float f;
    private int g;
    private String h;

    public int a() {
        return this.g;
    }

    public String b() {
        return this.h;
    }

    public String c() {
        return this.b;
    }

    public String d() {
        return this.a;
    }

    public float e() {
        return this.f;
    }

    public int f() {
        return this.c;
    }

    public float g() {
        return this.d;
    }

    public float h() {
        return this.e;
    }

    public a a(int i) {
        this.g = i;
        return this;
    }

    public a b(String str) {
        this.b = str;
        return this;
    }

    public a c(String str) {
        if (k0.a.containsKey(str)) {
            this.a = k0.a.get(str);
            return this;
        }
        this.a = str;
        return this;
    }

    public a a(String str) {
        this.h = str;
        return this;
    }

    public a b(int i) {
        this.c = i;
        return this;
    }

    public a a(float f) {
        float fRound = Math.round((f * 5.0f) * 10.0f) / 10.0f;
        if (fRound < 1.0f) {
            this.f = 1.0f;
            return this;
        }
        if (fRound > 20.0f) {
            this.f = 20.0f;
            return this;
        }
        this.f = fRound;
        return this;
    }

    public a b(float f) {
        float fRound = Math.round((f * 5.0f) * 10.0f) / 10.0f;
        if (fRound < 1.0f) {
            this.d = 1.0f;
            return this;
        }
        if (fRound > 25.0f) {
            this.d = 25.0f;
            return this;
        }
        this.d = fRound;
        return this;
    }

    public a c(float f) {
        float fRound = Math.round((f * 5.0f) * 10.0f) / 10.0f;
        if (fRound < 1.0f) {
            this.e = 1.0f;
            return this;
        }
        if (fRound > 9.0f) {
            this.e = 9.0f;
            return this;
        }
        this.e = fRound;
        return this;
    }
}
