package com.android.tools.r8.shaking;

import com.android.tools.r8.graph.AbstractC0677i3;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.m5;
import com.android.tools.r8.graph.o5;
import java.util.concurrent.ExecutorService;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class P {
    public static P a(C0702m c0702m, L l, L.a aVar) {
        if (aVar.c()) {
            return new E();
        }
        com.android.tools.r8.utils.A1 a1Y = c0702m.Y();
        return (a1Y.l1() && a1Y.o1() && a1Y.J0) ? new Q(c0702m, l, aVar) : new E();
    }

    public abstract void a(m5 m5Var);

    public abstract void a(ExecutorService executorService);

    public abstract boolean a(com.android.tools.r8.graph.Y0 y0, AbstractC0677i3 abstractC0677i3, o5 o5Var, H h, I i);

    public abstract boolean a(F0 f0);
}
