package com.android.tools.r8.graph;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class X2 {
    static boolean c(T0 t0) {
        return !t0.E0();
    }

    static boolean d(T0 t0) {
        return t0.E0();
    }

    abstract T0 a(Y0 y0);

    abstract Iterable a(Predicate predicate);

    abstract void a();

    abstract void a(T0 t0);

    abstract void a(Collection collection);

    abstract void a(Function function);

    abstract void a(T0[] t0Arr);

    abstract T0 b(Y0 y0);

    abstract com.android.tools.r8.utils.D3 b(Function function);

    abstract void b();

    abstract void b(T0 t0);

    abstract void b(T0[] t0Arr);

    abstract T0 c(Y0 y0);

    abstract List c();

    abstract int d();

    abstract int e();

    abstract int f();

    abstract List g();

    abstract void h();
}
