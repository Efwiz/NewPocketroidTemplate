package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.fx, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC2035fx {
    public static AbstractC2035fx a(String str) {
        AbstractC2035fx abstractC2035fx = new C1803cx().a(str).a;
        if (abstractC2035fx != null) {
            return abstractC2035fx;
        }
        throw new C2883qv("Invalid package pattern: null");
    }

    public static AbstractC2035fx f() {
        return C1959ex.c;
    }

    public String b() {
        throw new IllegalStateException();
    }

    public abstract boolean c();

    public abstract boolean d();

    public abstract boolean e();

    public static AbstractC2035fx a() {
        return C1726bx.a;
    }
}
