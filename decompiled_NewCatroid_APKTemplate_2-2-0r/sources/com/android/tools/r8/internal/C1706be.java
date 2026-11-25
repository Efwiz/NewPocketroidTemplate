package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.ir.optimize.EnumC3542a0;
import com.android.tools.r8.shaking.C3733i;

/* JADX INFO: renamed from: com.android.tools.r8.internal.be, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1706be implements InterfaceC3457yn {
    static final /* synthetic */ boolean d = true;
    private final C0702m a;
    private final AbstractC2755p6 b;
    private final com.android.tools.r8.utils.A1.i c;

    public C1706be(C0702m c0702m, AbstractC2755p6 abstractC2755p6) {
        this.a = c0702m;
        this.b = abstractC2755p6;
        this.c = c0702m.Y().P0();
    }

    /* JADX WARN: Code duplicated, block: B:42:0x00c0  */
    @Override // com.android.tools.r8.internal.InterfaceC3457yn
    public final EnumC3542a0 a(AbstractC1305Qs abstractC1305Qs, com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2, com.android.tools.r8.ir.optimize.N n, TE te) {
        boolean zY;
        com.android.tools.r8.graph.W0 w0E = o5Var.e();
        C0684j2 reference = o5Var.getReference();
        if (w0E.i1().g()) {
            if (d || this.a.a(o5Var).h(this.a.Y())) {
                return EnumC3542a0.a;
            }
            throw new AssertionError();
        }
        ((C3733i) this.a.f()).i();
        if (((C3733i) this.a.i0().f()).i(reference)) {
            return EnumC3542a0.b;
        }
        if (this.c.i && w0E.D1().d()) {
            return EnumC3542a0.e;
        }
        if (this.b.a(o5Var) && !((C3733i) this.a.f()).b(o5Var) && (this.a.Y().n1.U == null || this.a.Y().n1.U.contains(EnumC3542a0.c))) {
            return EnumC3542a0.c;
        }
        if (n.b(o5Var, abstractC1305Qs)) {
            zY = false;
        } else if (te.c()) {
            zY = this.b.b(o5Var);
        } else if (te instanceof C2914rM) {
            zY = o5Var.B().y();
        } else {
            zY = false;
        }
        if (zY) {
            return te.c() ? EnumC3542a0.d : EnumC3542a0.b;
        }
        return EnumC3542a0.e;
    }
}
