package com.android.tools.r8.internal;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class D00 {
    private final int a;

    public D00(int i) {
        this.a = i;
    }

    public final int a() {
        return (this.a & 16776960) >> 8;
    }

    public final int b() {
        return (this.a & 16711680) >> 16;
    }

    public final int c() {
        return this.a >>> 24;
    }

    public final int d() {
        return (short) ((this.a & 16776960) >> 8);
    }

    public final int e() {
        return (this.a & 16776960) >> 8;
    }

    public final int f() {
        return this.a & 255;
    }

    public final int g() {
        return (this.a & 65280) >> 8;
    }

    public final int h() {
        return (this.a & 16711680) >> 16;
    }
}
