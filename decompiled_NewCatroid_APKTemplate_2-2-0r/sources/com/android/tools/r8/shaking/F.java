package com.android.tools.r8.shaking;

import com.android.tools.r8.ClassFileConsumer;
import com.android.tools.r8.graph.C0633d0;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0699l2;
import com.android.tools.r8.graph.C0721p0;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0737r2;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.InterfaceC0694k5;
import com.android.tools.r8.graph.l5;
import com.android.tools.r8.graph.m5;
import com.android.tools.r8.graph.o5;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class F implements com.android.tools.r8.dex.L {
    private final InterfaceC0694k5 a;
    private final AbstractC3837z1 b;
    final /* synthetic */ L c;

    private F(L l, C0633d0 c0633d0, InterfaceC0694k5 interfaceC0694k5) {
        this.c = l;
        this.a = interfaceC0694k5;
        this.b = AbstractC3837z1.a(c0633d0, interfaceC0694k5);
    }

    @Override // com.android.tools.r8.dex.L
    public final boolean a(C0699l2 c0699l2) {
        return false;
    }

    @Override // com.android.tools.r8.dex.L
    public final boolean a(C0721p0 c0721p0) {
        return false;
    }

    @Override // com.android.tools.r8.dex.L
    public final boolean a(C0737r2 c0737r2) {
        return false;
    }

    @Override // com.android.tools.r8.dex.L
    public final boolean a(C0759u2 c0759u2) {
        return false;
    }

    @Override // com.android.tools.r8.dex.L
    public final boolean a(com.android.tools.r8.graph.Y0 y0) {
        m5 m5VarD;
        com.android.tools.r8.graph.U2 u2A;
        L l = this.c;
        InterfaceC0694k5 interfaceC0694k5 = this.a;
        l.getClass();
        l.b(y0.B0(), (l5) interfaceC0694k5);
        l.b(y0.getType(), (l5) interfaceC0694k5);
        L l2 = this.c;
        InterfaceC0694k5 interfaceC0694k6 = this.a;
        l2.getClass();
        C0729q2 c0729q2C = l2.c(y0.B0(), interfaceC0694k6);
        if (c0729q2C == null || (m5VarD = c0729q2C.d(y0)) == null || m5VarD.getReference() != y0) {
            return false;
        }
        if (m5VarD.e().E0()) {
            if (this.c.s.a(y0)) {
                u2A = this.c.s.b(y0);
            } else {
                u2A = this.c.s.a(y0, new com.android.tools.r8.graph.U2(y0));
            }
            u2A.v();
            this.c.a(m5VarD, this.a, this.b);
            if ((this.c.n.f instanceof ClassFileConsumer) && m5VarD.a().B1()) {
                this.c.b(m5VarD.a(), this.b);
                this.c.c(Y.c(), m5VarD, (C3698c1) C3704d1.p().f());
            }
        } else {
            this.c.U.a(m5VarD, this.a, this.b);
        }
        return false;
    }

    @Override // com.android.tools.r8.dex.L
    public final boolean a(C0684j2 c0684j2) {
        this.c.a(c0684j2, this.a);
        L l = this.c;
        InterfaceC0694k5 interfaceC0694k5 = this.a;
        l.getClass();
        C0729q2 c0729q2C = l.c(c0684j2.B0(), interfaceC0694k5);
        if (c0729q2C == null) {
            return false;
        }
        com.android.tools.r8.graph.W0 w0B = c0729q2C.b(c0684j2);
        if (w0B != null) {
            if (w0B.getReference() == c0684j2) {
                this.c.c(this.b, new o5(c0729q2C, w0B));
            }
        } else {
            com.android.tools.r8.graph.W0 w0D = c0729q2C.d(c0684j2);
            if (w0D != null && w0D.getReference() == c0684j2) {
                this.c.b(this.b, new o5(c0729q2C, w0D));
            }
        }
        return false;
    }

    @Override // com.android.tools.r8.dex.L
    public final boolean a(C0767v2 c0767v2) {
        this.c.a(c0767v2, this.a, this.b);
        return false;
    }
}
