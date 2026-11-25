package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.internal.uK, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC3121uK {
    public static C2096gg b() {
        return C2096gg.f();
    }

    public abstract B1 a(com.android.tools.r8.graph.T0 t0);

    public C1858dh a() {
        return null;
    }

    public abstract AbstractC3121uK a(C0702m c0702m, AbstractC1476Wj abstractC1476Wj, AbstractC1476Wj abstractC1476Wj2);

    public abstract void a(BiConsumer biConsumer);

    public int c() {
        throw new C3405y10();
    }

    public boolean d() {
        return this instanceof C1858dh;
    }

    public abstract boolean e();

    public abstract boolean equals(Object obj);

    public abstract int hashCode();

    public final boolean a(C0702m c0702m, com.android.tools.r8.graph.T0 t0, com.android.tools.r8.graph.o5 o5Var, Predicate predicate) {
        B1 b1A = a(t0);
        if (!b1A.g()) {
            return false;
        }
        AbstractC3292wV abstractC3292wVJ = b1A.j();
        if (abstractC3292wVJ.b(c0702m, o5Var)) {
            return predicate.test(abstractC3292wVJ);
        }
        return false;
    }
}
