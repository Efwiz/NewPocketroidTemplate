package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Lw, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC1171Lw {
    AbstractC1171Lw() {
    }

    public static AbstractC1171Lw a() {
        return C1058Hw.a;
    }

    public static AbstractC1171Lw j() {
        return C1117Jw.a;
    }

    public C3319ww b() {
        return null;
    }

    public C1463Vw c() {
        return null;
    }

    public C1027Gw d() {
        throw new C2883qv("Invalid access to member access pattern");
    }

    public boolean e() {
        return this instanceof C1058Hw;
    }

    public final boolean f() {
        return b() != null;
    }

    public final boolean g() {
        return ((this instanceof C1117Jw) || h() || f()) ? false : true;
    }

    public final boolean h() {
        return c() != null;
    }

    public boolean i() {
        return this instanceof C1117Jw;
    }
}
