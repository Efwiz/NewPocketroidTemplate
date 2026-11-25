package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.dF, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1828dF extends IndexOutOfBoundsException {
    private final String a;
    private final String b;
    private final String c;
    private final int d;

    public C1828dF(int i, String str, String str2, String str3) {
        super("Method too large: " + str + "." + str2 + " " + str3);
        this.a = str;
        this.b = str2;
        this.c = str3;
        this.d = i;
    }

    public final String a() {
        return this.a;
    }

    public final int b() {
        return this.d;
    }

    public final String c() {
        return this.c;
    }

    public final String d() {
        return this.b;
    }
}
