package com.android.tools.r8.naming;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.AbstractC0744s2;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.W0;
import com.android.tools.r8.graph.Y0;
import com.android.tools.r8.graph.m5;
import com.android.tools.r8.position.Position;
import com.android.tools.r8.shaking.C3733i;
import com.android.tools.r8.utils.O2;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.function.BiPredicate;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class A0 extends C3628h0 {
    static final /* synthetic */ boolean k = true;
    private final Map i;
    private final C0722p1 j;

    public A0(C0702m c0702m, IdentityHashMap identityHashMap) {
        super(c0702m);
        this.i = identityHashMap;
        this.j = c0702m.a();
        O2 o2 = c0702m.Y().e;
    }

    @Override // com.android.tools.r8.naming.C3628h0, com.android.tools.r8.naming.S
    public final boolean a(AbstractC0727q0 abstractC0727q0) {
        return true;
    }

    @Override // com.android.tools.r8.naming.C3628h0, com.android.tools.r8.naming.S
    public final C0759u2 a(W0 w0, W w, BiPredicate biPredicate) {
        C0684j2 reference = w0.getReference();
        C0702m c0702m = this.e;
        c0702m.getClass();
        AbstractC0727q0 abstractC0727q0C = c0702m.c(reference.e);
        boolean z = k;
        if (!z && abstractC0727q0C == null) {
            throw new AssertionError();
        }
        C0759u2 c0759u2A = a(w0, reference.f, abstractC0727q0C);
        if (c0759u2A != null) {
            if (!biPredicate.test(c0759u2A, reference)) {
                Q q = (Q) this.i.get(reference);
                if (!z && !reference.y0() && !reference.x0()) {
                    throw new AssertionError();
                }
                C3603a.b(reference.r0(), c0759u2A.toString(), q == null ? Position.UNKNOWN : q.f());
            }
        } else {
            if (!z && this.i.containsKey(reference)) {
                throw new AssertionError();
            }
            if (!z && !((C3733i) this.e.f()).a(w0)) {
                throw new AssertionError();
            }
            c0759u2A = super.a(w0, w, biPredicate);
        }
        if (!z && c0759u2A != reference.f && w0.A1()) {
            throw new AssertionError();
        }
        if (z || c0759u2A == reference.f || !abstractC0727q0C.e.T()) {
            return c0759u2A;
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.naming.C3628h0, com.android.tools.r8.naming.S
    public final C0759u2 a(m5 m5Var, C3671y c3671y, BiPredicate biPredicate) {
        Y0 reference = m5Var.getReference();
        C0759u2 c0759u2A = a(m5Var.e(), reference.f, m5Var.a());
        if (c0759u2A != null) {
            if (!biPredicate.test(c0759u2A, m5Var)) {
                Q q = (Q) this.i.get(reference);
                if (!k && !reference.y0() && !reference.x0()) {
                    throw new AssertionError();
                }
                C3603a.b(reference.r0(), c0759u2A.toString(), q == null ? Position.UNKNOWN : q.f());
            }
            return c0759u2A;
        }
        boolean z = k;
        if (!z && this.i.containsKey(reference)) {
            throw new AssertionError();
        }
        if (z || ((C3733i) this.e.f()).b(m5Var)) {
            return super.a(m5Var, c3671y, biPredicate);
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.naming.C3628h0, com.android.tools.r8.naming.S
    public final C0759u2 a(AbstractC0727q0 abstractC0727q0, W0 w0) {
        return a(w0, w0.getReference().f, abstractC0727q0);
    }

    @Override // com.android.tools.r8.naming.C3628h0, com.android.tools.r8.naming.S
    public final C0759u2 a(AbstractC0727q0 abstractC0727q0, com.android.tools.r8.graph.T0 t0) {
        return a(t0, t0.getReference().f, abstractC0727q0);
    }

    private C0759u2 a(com.android.tools.r8.graph.O0 o0, C0759u2 c0759u2, AbstractC0727q0 abstractC0727q0) {
        C0759u2 c0759u2A;
        boolean z = k;
        if (!z && !o0.D0() && !(o0 instanceof com.android.tools.r8.graph.T0)) {
            throw new AssertionError();
        }
        AbstractC0744s2 reference = o0.getReference();
        if (abstractC0727q0.I1()) {
            return this.i.containsKey(reference) ? this.j.c(((Q) this.i.get(reference)).a()) : c0759u2;
        }
        if (!z && !abstractC0727q0.e0()) {
            throw new AssertionError();
        }
        if (o0.D0()) {
            c0759u2A = super.a(abstractC0727q0, o0.w0());
        } else {
            c0759u2A = super.a(abstractC0727q0, o0.u0());
        }
        if (c0759u2A != null) {
            return c0759u2A;
        }
        if (this.i.containsKey(reference)) {
            return this.j.c(((Q) this.i.get(reference)).a());
        }
        return null;
    }
}
