package com.android.tools.r8.graph;

import com.android.tools.r8.internal.C3405y10;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.graph.s2, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC0744s2 extends I3 {
    static final /* synthetic */ boolean d = true;

    public static Object a(AbstractC0744s2 abstractC0744s2, AbstractC0744s2 abstractC0744s3, BiFunction biFunction, BiFunction biFunction2, BiFunction biFunction3) {
        abstractC0744s2.getClass();
        if (abstractC0744s2 instanceof C0767v2) {
            return biFunction.apply(abstractC0744s2.w0(), abstractC0744s3.w0());
        }
        if (abstractC0744s2.x0()) {
            return biFunction2.apply(abstractC0744s2.t0(), abstractC0744s3.t0());
        }
        if (abstractC0744s2.y0()) {
            return biFunction3.apply(abstractC0744s2.v0(), abstractC0744s3.v0());
        }
        throw new C3405y10();
    }

    public final int A0() {
        if (this instanceof C0767v2) {
            return 1;
        }
        if (x0()) {
            return 2;
        }
        if (d || y0()) {
            return 3;
        }
        throw new AssertionError();
    }

    public abstract C0767v2 C();

    public abstract int a(AbstractC0744s2 abstractC0744s2);

    public abstract Object a(Function function, Function function2, Function function3);

    public abstract void a(C0702m c0702m, com.android.tools.r8.dex.L l);

    public abstract void a(Consumer consumer, Consumer consumer2, Consumer consumer3);

    public Y0 t0() {
        return null;
    }

    public AbstractC0642e2 u0() {
        return null;
    }

    public C0684j2 v0() {
        return null;
    }

    public C0767v2 w0() {
        return null;
    }

    public boolean x0() {
        return false;
    }

    public boolean y0() {
        return false;
    }

    public boolean z0() {
        return this instanceof C0767v2;
    }
}
