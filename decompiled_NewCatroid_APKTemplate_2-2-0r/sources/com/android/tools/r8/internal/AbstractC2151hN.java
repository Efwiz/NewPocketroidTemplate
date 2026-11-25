package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import java.util.function.Consumer;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.internal.hN, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC2151hN {
    public static IH c() {
        return IH.e();
    }

    public abstract AbstractC2151hN a(AbstractC3104u3 abstractC3104u3);

    public abstract AbstractC2151hN a(AbstractC3135uW abstractC3135uW);

    public abstract AbstractC2151hN a(Function function);

    public C2626nc a() {
        return null;
    }

    public abstract void a(C0684j2 c0684j2, Consumer consumer);

    public abstract void a(C0702m c0702m);

    public abstract void a(com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2);

    public boolean b() {
        return this instanceof IH;
    }

    public abstract void d();

    public static AbstractC2151hN b(C0702m c0702m) {
        AbstractC3104u3 abstractC3104u3S = c0702m.s();
        AbstractC3135uW abstractC3135uWC = c0702m.C();
        return (abstractC3104u3S.isEmpty() && abstractC3135uWC.c()) ? c() : new C2626nc(abstractC3104u3S, abstractC3135uWC);
    }
}
