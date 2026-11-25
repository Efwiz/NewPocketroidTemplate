package com.huawei.hms.mlsdk.t;

/* JADX INFO: compiled from: PlayerSetting.java */
/* JADX INFO: loaded from: classes10.dex */
public class e0 {
    private int a;
    private int b;
    private int c;
    private int d;
    private int e;

    public int a() {
        return this.d;
    }

    public int b() {
        return this.c;
    }

    public int c() {
        return this.e;
    }

    public int d() {
        return this.b;
    }

    public int e() {
        return this.a;
    }

    public String toString() {
        return a.a("PlayerSetting{streamType=").append(this.a).append(", sampleRateInHz=").append(this.b).append(", channelConfig=").append(this.c).append(", audioFormat=").append(this.d).append(", playMode=").append(this.e).append('}').toString();
    }

    public e0 a(int i) {
        this.d = i;
        return this;
    }

    public e0 b(int i) {
        this.c = i;
        return this;
    }

    public e0 c(int i) {
        this.e = i;
        return this;
    }

    public e0 d(int i) {
        this.b = i;
        return this;
    }

    public e0 e(int i) {
        this.a = i;
        return this;
    }
}
