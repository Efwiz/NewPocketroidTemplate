package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.La, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1149La extends IndexOutOfBoundsException {
    private final String a;
    private final int b;

    public C1149La(String str, int i) {
        super("Class too large: " + str);
        this.a = str;
        this.b = i;
    }

    public final String a() {
        return this.a;
    }

    public final int b() {
        return this.b;
    }
}
