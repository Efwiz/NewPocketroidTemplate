package com.android.tools.r8.shaking;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.AbstractC0744s2;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.InterfaceC0694k5;
import com.android.tools.r8.graph.m5;
import com.android.tools.r8.graph.o5;
import com.android.tools.r8.internal.C3405y10;
import com.android.tools.r8.internal.DH;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.shaking.i1, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC3735i1 {
    static final /* synthetic */ boolean a = true;

    public abstract Y0 a(C0729q2 c0729q2);

    public abstract C3704d1 a(com.android.tools.r8.graph.T0 t0, C0729q2 c0729q2);

    public abstract C3730h1 a(DH dh, com.android.tools.r8.utils.A1 a1);

    public abstract C3730h1 a(Consumer consumer);

    public final AbstractC3740j1 a(com.android.tools.r8.graph.U0 u0, C0729q2 c0729q2) {
        u0.getClass();
        if (u0 instanceof com.android.tools.r8.graph.T0) {
            return a(u0.u0(), c0729q2);
        }
        if (a || u0.D0()) {
            return a(c0729q2, u0.w0());
        }
        throw new AssertionError();
    }

    public abstract C3762m1 a(C0729q2 c0729q2, com.android.tools.r8.graph.W0 w0);

    abstract void a(C0702m c0702m, BiConsumer biConsumer, BiConsumer biConsumer2, BiConsumer biConsumer3);

    public abstract void a(com.android.tools.r8.utils.A1 a1, Set set);

    public abstract void a(Consumer consumer, com.android.tools.r8.utils.A1 a1);

    public abstract void b(Consumer consumer, com.android.tools.r8.utils.A1 a1);

    public abstract void c(Consumer consumer, com.android.tools.r8.utils.A1 a1);

    public final Y0 a(AbstractC0727q0 abstractC0727q0) {
        if (abstractC0727q0 != null && abstractC0727q0.e0()) {
            return a(abstractC0727q0.a0());
        }
        return Y0.i();
    }

    public final C3762m1 a(o5 o5Var) {
        return a(o5Var.a(), o5Var.e());
    }

    public final boolean a(AbstractC0744s2 abstractC0744s2, com.android.tools.r8.utils.A1 a1, com.android.tools.r8.graph.Q0 q0) {
        com.android.tools.r8.graph.T0 t0A;
        AbstractC3722g1 abstractC3722g1I;
        com.android.tools.r8.graph.W0 w0C;
        abstractC0744s2.getClass();
        if (abstractC0744s2 instanceof C0767v2) {
            abstractC3722g1I = a(q0.a(abstractC0744s2.w0()));
        } else if (abstractC0744s2.y0()) {
            C0684j2 c0684j2V0 = abstractC0744s2.v0();
            C0729q2 c0729q2A = C0729q2.a(q0.a(c0684j2V0.e));
            abstractC3722g1I = (c0729q2A == null || (w0C = c0729q2A.c(c0684j2V0)) == null) ? C3762m1.i() : a(c0729q2A, w0C);
        } else if (abstractC0744s2.x0()) {
            com.android.tools.r8.graph.Y0 y0T0 = abstractC0744s2.t0();
            C0729q2 c0729q2A2 = C0729q2.a(q0.c(y0T0.e));
            abstractC3722g1I = (c0729q2A2 == null || (t0A = c0729q2A2.a(y0T0)) == null) ? C3704d1.i() : a(t0A, c0729q2A2);
        } else {
            throw new C3405y10();
        }
        return abstractC3722g1I.c((K0) a1);
    }

    public final AbstractC3722g1 a(com.android.tools.r8.graph.O0 o0, com.android.tools.r8.graph.Q0 q0) {
        if (o0.A0()) {
            return a(o0.t0());
        }
        if (o0.D0()) {
            com.android.tools.r8.graph.W0 w0 = o0.w0();
            C0729q2 c0729q2A = C0729q2.a(q0.a(w0.J0()));
            if (c0729q2A == null) {
                return C3762m1.i();
            }
            if (a || w0 == c0729q2A.c(w0.getReference())) {
                return a(c0729q2A, w0);
            }
            throw new AssertionError();
        }
        if (o0 instanceof com.android.tools.r8.graph.T0) {
            com.android.tools.r8.graph.T0 t0U0 = o0.u0();
            C0729q2 c0729q2A2 = C0729q2.a(q0.a(t0U0.J0()));
            if (c0729q2A2 == null) {
                return C3704d1.i();
            }
            if (a || c0729q2A2.a(t0U0.getReference()) == t0U0) {
                return a(t0U0, c0729q2A2);
            }
            throw new AssertionError();
        }
        throw new C3405y10();
    }

    public final AbstractC3722g1 a(InterfaceC0694k5 interfaceC0694k5) {
        if (interfaceC0694k5.e0()) {
            return a(interfaceC0694k5.a0());
        }
        if (interfaceC0694k5.k()) {
            return a(interfaceC0694k5.g0());
        }
        if (interfaceC0694k5.l0()) {
            m5 m5VarQ = interfaceC0694k5.Q();
            return a(m5VarQ.e(), m5VarQ.a());
        }
        throw new C3405y10();
    }

    public final boolean a(o5 o5Var, com.android.tools.r8.utils.A1 a1) {
        return a1.k1() && a((InterfaceC0694k5) o5Var).a((K0) a1);
    }
}
