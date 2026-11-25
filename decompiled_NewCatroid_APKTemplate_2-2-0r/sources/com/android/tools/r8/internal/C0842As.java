package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.As, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C0842As {
    private static final C0842As b = new C0842As(true);
    private static final C0842As c = new C0842As(false);
    private final boolean a;

    C0842As(boolean z) {
        this.a = z;
    }

    public static C0842As a() {
        return b;
    }

    public static C0842As b() {
        return c;
    }

    public final boolean c() {
        return this.a;
    }
}
