package com.android.tools.r8.internal;

import com.android.tools.r8.dex.code.InterfaceC0424d;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0753u;
import com.android.tools.r8.ir.optimize.C3550d0;
import java.util.ListIterator;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class O8 extends AbstractC2206i7 implements InterfaceC0424d {
    public O8(com.android.tools.r8.graph.Y0 y0) {
        super(y0);
    }

    @Override // com.android.tools.r8.internal.B7
    public final boolean D() {
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC2206i7
    public final int U() {
        return 178;
    }

    @Override // com.android.tools.r8.internal.AbstractC2206i7
    public final AbstractC2206i7 a(com.android.tools.r8.graph.Y0 y0) {
        return new O8(y0);
    }

    public O8(com.android.tools.r8.graph.Y0 y0, com.android.tools.r8.graph.Y0 y1) {
        super(y0, y1);
    }

    @Override // com.android.tools.r8.internal.B7
    final void a(com.android.tools.r8.graph.J5 j5, ListIterator listIterator) {
        j5.a(this);
    }

    @Override // com.android.tools.r8.internal.B7
    public final void a(C2102gl c2102gl, N8 n8, A8 a8) {
        c2102gl.a(n8.a(getField().getType()).a, getField());
    }

    @Override // com.android.tools.r8.internal.B7
    public final com.android.tools.r8.ir.optimize.V a(C3550d0 c3550d0, C0753u c0753u, com.android.tools.r8.graph.o5 o5Var) {
        return c3550d0.d(getField(), o5Var);
    }

    @Override // com.android.tools.r8.internal.B7
    public final AbstractC2359k7 a(AbstractC2359k7 abstractC2359k7, C0702m c0702m, InterfaceC3107u6 interfaceC3107u6) {
        return abstractC2359k7.a(interfaceC3107u6, getField().getType());
    }
}
