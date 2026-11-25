package com.android.tools.r8.naming;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class J0 {
    static final /* synthetic */ boolean c = true;
    private final String a;
    private final String b;

    private J0(String str, String str2) {
        boolean z = c;
        if (!z && str == null) {
            throw new AssertionError();
        }
        if (!z && str2 == null) {
            throw new AssertionError();
        }
        this.a = str;
        this.b = str2;
    }

    public final String a() {
        return this.b;
    }

    public final String b() {
        return this.a;
    }
}
