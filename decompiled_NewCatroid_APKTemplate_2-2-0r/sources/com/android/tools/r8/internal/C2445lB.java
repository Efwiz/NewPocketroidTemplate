package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;

/* JADX INFO: renamed from: com.android.tools.r8.internal.lB, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2445lB extends com.android.tools.r8.graph.K5 {
    final /* synthetic */ C2520mB f;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C2445lB(C2520mB c2520mB, C0702m c0702m, com.android.tools.r8.graph.o5 o5Var) {
        super(c0702m, o5Var, Boolean.FALSE);
        this.f = c2520mB;
    }

    private void i(C0684j2 c0684j2) {
        if (g(c0684j2.I0())) {
            return;
        }
        AbstractC2734ot.b(c0684j2.G0(), new InterfaceC3205vM() { // from class: com.android.tools.r8.internal.lB$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.internal.InterfaceC3205vM
            public final boolean apply(Object obj) {
                return this.f$0.g((C0767v2) obj);
            }
        });
    }

    @Override // com.android.tools.r8.graph.J5
    public final void a(com.android.tools.r8.graph.Y0 y0) {
        if (g(y0.B0())) {
            return;
        }
        g(y0.getType());
    }

    @Override // com.android.tools.r8.graph.J5
    public final void b(C0767v2 c0767v2) {
        g(c0767v2);
    }

    @Override // com.android.tools.r8.graph.J5
    public final void c(com.android.tools.r8.graph.Y0 y0) {
        if (g(y0.B0())) {
            return;
        }
        g(y0.getType());
    }

    @Override // com.android.tools.r8.graph.J5
    public final void d(C0767v2 c0767v2) {
        g(c0767v2);
    }

    @Override // com.android.tools.r8.graph.J5
    public final void e(com.android.tools.r8.graph.Y0 y0) {
        if (g(y0.B0())) {
            return;
        }
        g(y0.getType());
    }

    @Override // com.android.tools.r8.graph.J5
    public final void f(C0684j2 c0684j2) {
        i(c0684j2);
    }

    @Override // com.android.tools.r8.graph.J5
    public final void g(com.android.tools.r8.graph.Y0 y0) {
        if (g(y0.B0())) {
            return;
        }
        g(y0.getType());
    }

    @Override // com.android.tools.r8.graph.J5
    public final void h(C0684j2 c0684j2) {
        i(c0684j2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean g(C0767v2 c0767v2) {
        C0759u2 c0759u2 = c0767v2.e;
        C0759u2 c0759u3 = this.f.a;
        c0759u2.getClass();
        if (!c0759u2.a(c0759u3.e)) {
            return false;
        }
        a(Boolean.TRUE);
        return true;
    }

    @Override // com.android.tools.r8.graph.J5
    public final void a(C0684j2 c0684j2) {
        i(c0684j2);
    }

    @Override // com.android.tools.r8.graph.J5
    public final void b(C0684j2 c0684j2) {
        i(c0684j2);
    }

    @Override // com.android.tools.r8.graph.J5
    public final void c(C0767v2 c0767v2) {
        g(c0767v2);
    }

    @Override // com.android.tools.r8.graph.J5
    public final void e(C0684j2 c0684j2) {
        i(c0684j2);
    }

    @Override // com.android.tools.r8.graph.J5
    public final void f(C0767v2 c0767v2) {
        g(c0767v2);
    }

    @Override // com.android.tools.r8.graph.J5
    public final void g(C0684j2 c0684j2) {
        i(c0684j2);
    }
}
