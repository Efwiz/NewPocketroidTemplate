package com.android.tools.r8.naming;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.W0;
import com.android.tools.r8.graph.m5;
import com.android.tools.r8.shaking.C3733i;
import com.android.tools.r8.utils.Y2;
import java.util.function.BiPredicate;

/* JADX INFO: renamed from: com.android.tools.r8.naming.h0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
class C3628h0 extends AbstractC3613c0 implements S {
    static final /* synthetic */ boolean h = true;
    final C0702m e;
    private final C0722p1 f;
    private final boolean g;

    public C3628h0(C0702m c0702m) {
        super(c0702m.Y().D0().m(), false);
        this.e = c0702m;
        this.f = c0702m.a();
        this.g = c0702m.t.a();
    }

    public C0759u2 a(W0 w0, W w, BiPredicate biPredicate) {
        C0759u2 c0759u2C;
        boolean z = h;
        if (!z) {
            AbstractC0727q0 abstractC0727q0C = this.e.c(w0.J0());
            if (!z && (abstractC0727q0C == null || !a(abstractC0727q0C))) {
                throw new AssertionError();
            }
        }
        do {
            c0759u2C = this.f.c(a(Y2.a, w));
        } while (!biPredicate.test(c0759u2C, w0.getReference()));
        return c0759u2C;
    }

    public C0759u2 a(m5 m5Var, C3671y c3671y, BiPredicate biPredicate) {
        C0759u2 c0759u2C;
        boolean z = h;
        if (!z) {
            AbstractC0727q0 abstractC0727q0C = this.e.c(m5Var.t());
            if (!z && (abstractC0727q0C == null || !a(abstractC0727q0C))) {
                throw new AssertionError();
            }
        }
        do {
            c0759u2C = this.f.c(a(Y2.a, c3671y));
        } while (!biPredicate.test(c0759u2C, m5Var));
        return c0759u2C;
    }

    public C0759u2 a(AbstractC0727q0 abstractC0727q0, W0 w0) {
        if (a(abstractC0727q0) && !abstractC0727q0.e.T() && !w0.f.Y() && ((C3733i) this.e.f()).a(w0)) {
            if (this.g && w0.D1().d() && this.e.t.a(w0.getReference().h, this.e)) {
                return w0.getReference().f;
            }
            return null;
        }
        return w0.getReference().f;
    }

    public C0759u2 a(AbstractC0727q0 abstractC0727q0, com.android.tools.r8.graph.T0 t0) {
        if (abstractC0727q0.f0() || !((C3733i) this.e.f()).a((com.android.tools.r8.graph.O0) t0)) {
            return t0.getReference().f;
        }
        return null;
    }

    public boolean a(AbstractC0727q0 abstractC0727q0) {
        return abstractC0727q0.e0();
    }
}
