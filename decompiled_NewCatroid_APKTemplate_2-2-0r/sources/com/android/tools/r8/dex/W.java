package com.android.tools.r8.dex;

import com.android.tools.r8.graph.C0633d0;
import com.android.tools.r8.graph.C0640e0;
import com.android.tools.r8.graph.C0658g0;
import com.android.tools.r8.graph.C0663g5;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0800x2;
import com.android.tools.r8.graph.S0;
import com.android.tools.r8.graph.o5;
import com.android.tools.r8.internal.AbstractC3135uW;
import java.util.Collection;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class W {
    static final /* synthetic */ boolean a = true;

    public static W a(C0702m c0702m, I i, r0 r0Var) {
        AbstractC3135uW abstractC3135uWB;
        if (r0Var.l().c()) {
            abstractC3135uWB = AbstractC3135uW.b();
        } else {
            if (!a && r0Var.g() != 0) {
                throw new AssertionError();
            }
            abstractC3135uWB = c0702m.Y().G0().f() ? r0Var.l().b(c0702m) : AbstractC3135uW.b();
        }
        return (W) c0702m.Y().n1.o.apply(abstractC3135uWB.c() ? new C0584v(c0702m, i) : new f0(c0702m, i, abstractC3135uWB, r0Var), r0Var);
    }

    public abstract Collection<C0640e0> a();

    public abstract Collection<C0633d0> b();

    public abstract Collection<C0658g0> c();

    public abstract Collection<C0663g5> d();

    public abstract Collection<C0729q2> e();

    public abstract Collection<o5> f();

    public abstract Collection<S0> g();

    public abstract Collection<C0759u2> h();

    public abstract Collection<C0800x2> i();
}
