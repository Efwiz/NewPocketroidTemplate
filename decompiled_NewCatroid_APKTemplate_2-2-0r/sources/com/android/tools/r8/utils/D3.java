package com.android.tools.r8.utils;

import java.util.function.Function;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public abstract class D3 {
    static final /* synthetic */ boolean a = true;

    public static A3 d() {
        return A3.c;
    }

    public static C3 e() {
        return C3.c;
    }

    public A3 a() {
        return null;
    }

    public final A3 b() {
        A3 a3A = a();
        return a3A == null ? new A3(null) : a3A;
    }

    public C3 c() {
        return null;
    }

    public boolean f() {
        return false;
    }

    public boolean g() {
        return false;
    }

    public final boolean h() {
        return g();
    }

    public final D3 a(Function function, Function function2) {
        if (f()) {
            return new A3(function.apply(a().i()));
        }
        if (a || g()) {
            return new C3(function2.apply(c().i()));
        }
        throw new AssertionError();
    }

    public static C3 b(Object obj) {
        return new C3(obj);
    }

    public static D3 a(boolean z) {
        return z ? C3.c : A3.c;
    }

    public final D3 a(Function function) {
        return g() ? (D3) function.apply(c()) : this;
    }

    public static A3 a(Object obj) {
        return new A3(obj);
    }
}
