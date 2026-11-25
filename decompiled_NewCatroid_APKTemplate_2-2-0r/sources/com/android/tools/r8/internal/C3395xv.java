package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.xv, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3395xv {
    private static final C3395xv c = new C3395xv(C3092tv.a, C3169uv.b);
    private final C3092tv a;
    private final C3169uv b;

    private C3395xv(C3092tv c3092tv, C3169uv c3169uv) {
        this.a = c3092tv;
        this.b = c3169uv;
    }

    public static C2953rv a() {
        return new C2953rv();
    }

    public static C3395xv f() {
        return c;
    }

    public final String b() {
        return this.a.a();
    }

    public final String c() {
        return this.b.a;
    }

    public final boolean d() {
        return !C3092tv.a.equals(this.a);
    }

    public final boolean e() {
        return !C3169uv.b.equals(this.b);
    }
}
