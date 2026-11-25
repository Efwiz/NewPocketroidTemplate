package com.android.tools.r8.shaking;

import com.android.tools.r8.graph.C0633d0;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.EnumC0608b0;
import com.android.tools.r8.graph.InterfaceC0694k5;
import com.android.tools.r8.graph.m5;
import com.android.tools.r8.graph.o5;
import com.android.tools.r8.utils.InterfaceC3880d;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/* JADX INFO: renamed from: com.android.tools.r8.shaking.p0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3777p0 extends F0 {
    static final /* synthetic */ boolean c = true;

    C3777p0(L l) {
        super(l, new ConcurrentLinkedQueue());
    }

    @Override // com.android.tools.r8.shaking.F0
    final void a(AbstractC3709e0 abstractC3709e0) {
        this.b.add(abstractC3709e0);
    }

    @Override // com.android.tools.r8.shaking.F0
    final C3772o0 b() {
        return new C3772o0(this);
    }

    @Override // com.android.tools.r8.shaking.F0
    public final void c(C0684j2 c0684j2, o5 o5Var) {
        this.b.add(new C3830y0(c0684j2, o5Var));
    }

    @Override // com.android.tools.r8.shaking.F0
    final void a(InterfaceC3880d interfaceC3880d) {
        if (com.android.tools.r8.utils.A1.b()) {
            this.b.add(new C3703d0(interfaceC3880d));
        }
    }

    @Override // com.android.tools.r8.shaking.F0
    final void b(C0729q2 c0729q2, L0 l0) {
        boolean z = c;
        if (!z && c0729q2.e.T()) {
            throw new AssertionError();
        }
        if (!z && !c0729q2.isInterface()) {
            throw new AssertionError();
        }
        this.b.add(new C3739j0(c0729q2, l0));
    }

    @Override // com.android.tools.r8.shaking.F0
    public final void c(m5 m5Var, o5 o5Var) {
        com.android.tools.r8.graph.U2 u2B = this.a.f().b(m5Var.getReference());
        if (u2B == null || !u2B.m()) {
            this.b.add(new B0(m5Var, o5Var, m5Var.getAccessFlags().o() ? H.d : H.b));
        }
    }

    @Override // com.android.tools.r8.shaking.F0
    final void a(C0684j2 c0684j2, o5 o5Var, AbstractC3837z1 abstractC3837z1) {
        this.b.add(new C3761m0(c0684j2, o5Var, abstractC3837z1));
    }

    @Override // com.android.tools.r8.shaking.F0
    final void a(C0684j2 c0684j2, o5 o5Var) {
        this.b.add(new C3766n0(c0684j2, o5Var));
    }

    @Override // com.android.tools.r8.shaking.F0
    public final void a(m5 m5Var, InterfaceC0694k5 interfaceC0694k5, AbstractC3837z1 abstractC3837z1) {
        this.b.add(new C3721g0(m5Var, interfaceC0694k5, abstractC3837z1));
    }

    @Override // com.android.tools.r8.shaking.F0
    public final void a(C0729q2 c0729q2, o5 o5Var, V0 v0, AbstractC3837z1 abstractC3837z1) {
        boolean z = c;
        if (!z && c0729q2.e.T()) {
            throw new AssertionError();
        }
        if (!z && c0729q2.isInterface()) {
            throw new AssertionError();
        }
        this.b.add(new C3734i0(c0729q2, o5Var, v0, abstractC3837z1));
    }

    @Override // com.android.tools.r8.shaking.F0
    public final void b(C0684j2 c0684j2, o5 o5Var) {
        this.b.add(new C3823x0(c0684j2, o5Var));
    }

    @Override // com.android.tools.r8.shaking.F0
    public final void b(C0767v2 c0767v2, o5 o5Var) {
        this.b.add(new A0(c0767v2, o5Var));
    }

    @Override // com.android.tools.r8.shaking.F0
    public final void b(m5 m5Var, o5 o5Var) {
        H h;
        com.android.tools.r8.graph.U2 u2B = this.a.f().b(m5Var.getReference());
        if (u2B == null || !u2B.l()) {
            Queue queue = this.b;
            if (m5Var.getAccessFlags().o()) {
                h = H.c;
            } else {
                h = H.a;
            }
            queue.add(new B0(m5Var, o5Var, h));
        }
    }

    @Override // com.android.tools.r8.shaking.F0
    public final void c(C0729q2 c0729q2, L0 l0) {
        this.b.add(new E0(c0729q2, l0));
    }

    @Override // com.android.tools.r8.shaking.F0
    final void a(C0729q2 c0729q2, L0 l0) {
        boolean z = c;
        if (!z && !c0729q2.e.T()) {
            throw new AssertionError();
        }
        if (!z && !c0729q2.isInterface()) {
            throw new AssertionError();
        }
        this.b.add(new C3715f0(c0729q2, l0));
    }

    @Override // com.android.tools.r8.shaking.F0
    final boolean a(o5 o5Var, InterfaceC0694k5 interfaceC0694k5, AbstractC3837z1 abstractC3837z1) {
        if (!this.a.a(abstractC3837z1, o5Var)) {
            return false;
        }
        this.b.add(new C3753l0(o5Var, interfaceC0694k5));
        L l = this.a;
        l.getClass();
        if (l.b(o5Var.e())) {
            return true;
        }
        this.b.add(new C3836z0(o5Var));
        return true;
    }

    @Override // com.android.tools.r8.shaking.F0
    final void a(AbstractC3837z1 abstractC3837z1, o5 o5Var) {
        this.b.add(new C3744k0(abstractC3837z1, o5Var));
    }

    @Override // com.android.tools.r8.shaking.F0
    final void a(L0 l0, m5 m5Var) {
        this.b.add(new C3729h0(l0, m5Var));
    }

    @Override // com.android.tools.r8.shaking.F0
    final void a(InterfaceC0694k5 interfaceC0694k5, C0633d0 c0633d0, EnumC0608b0 enumC0608b0) {
        this.b.add(new C3783q0(interfaceC0694k5, c0633d0, enumC0608b0));
    }

    @Override // com.android.tools.r8.shaking.F0
    public final void a(o5 o5Var) {
        this.b.add(new C3787r0(o5Var));
    }

    @Override // com.android.tools.r8.shaking.F0
    public final void a(C0767v2 c0767v2, o5 o5Var) {
        this.b.add(new C3793s0(c0767v2, o5Var));
    }

    @Override // com.android.tools.r8.shaking.F0
    public final void a(C0729q2 c0729q2) {
        this.b.add(new C3799t0(c0729q2));
    }

    @Override // com.android.tools.r8.shaking.F0
    public final void a(m5 m5Var, o5 o5Var) {
        com.android.tools.r8.graph.U2 u2B = this.a.f().b(m5Var.getReference());
        if (u2B != null && u2B.l() && u2B.m()) {
            return;
        }
        this.b.add(new B0(m5Var, o5Var, null));
    }

    @Override // com.android.tools.r8.shaking.F0
    public final void a(com.android.tools.r8.graph.Y0 y0, o5 o5Var) {
        this.b.add(new C0(y0, o5Var, I.c));
    }
}
