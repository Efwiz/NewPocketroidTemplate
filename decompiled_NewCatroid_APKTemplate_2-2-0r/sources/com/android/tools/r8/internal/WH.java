package com.android.tools.r8.internal;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class WH {
    private static final WH a = new WH();
    private static final WH b = new WH();
    private static final WH c = new WH();
    private static final WH d = new WH();

    private WH() {
    }

    public static WH c() {
        return a;
    }

    public static WH i() {
        return c;
    }

    public final WH a(WH wh) {
        WH wh2 = d;
        if (this == wh2) {
            return wh;
        }
        return (wh == wh2 || this == wh) ? this : c;
    }

    public final WH b(WH wh) {
        WH wh2 = c;
        if (this == wh2) {
            return wh;
        }
        return (wh == wh2 || this == wh) ? this : d;
    }

    public final boolean d() {
        return this == d;
    }

    public boolean e() {
        return this == b;
    }

    public boolean f() {
        return this == a;
    }

    public boolean g() {
        return this == c;
    }

    public final boolean h() {
        return g() || f();
    }

    public final String toString() {
        if (this == c) {
            return "@Nullable";
        }
        if (this == a) {
            return "@Null";
        }
        if (this == b) {
            return "@NotNull";
        }
        if (this == d) {
            return "@Bottom";
        }
        throw new C3405y10("Unknown Nullability.");
    }

    public static WH a() {
        return d;
    }

    public static WH b() {
        return b;
    }
}
