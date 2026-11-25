package com.android.tools.r8.internal;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class X4 {
    static final /* synthetic */ boolean c = true;
    final boolean a;
    final W4 b;

    public X4(V4 v4, W4 w4) {
        if (!c && v4 == V4.a) {
            throw new AssertionError();
        }
        this.a = v4 == V4.b;
        this.b = w4;
    }

    public X4(String str) {
        int iIndexOf = str.indexOf(58);
        this.a = str.substring(0, iIndexOf).trim().equals("good");
        this.b = new W4(str.substring(iIndexOf + 1).trim());
    }
}
