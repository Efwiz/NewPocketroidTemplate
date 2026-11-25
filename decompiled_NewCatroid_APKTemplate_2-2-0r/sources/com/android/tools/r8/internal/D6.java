package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0753u;
import com.android.tools.r8.ir.optimize.C3550d0;
import com.android.tools.r8.naming.AbstractC3649o0;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class D6 extends C6 {
    public D6(EnumC2523mE enumC2523mE) {
        super(enumC2523mE);
    }

    private int U() {
        switch (T()) {
            case OBJECT:
                return 83;
            case BOOLEAN_OR_BYTE:
                return 84;
            case CHAR:
                return 85;
            case SHORT:
                return 86;
            case INT:
                return 79;
            case FLOAT:
                return 81;
            case LONG:
                return 80;
            case DOUBLE:
                return 82;
            default:
                throw new C3405y10("Unexpected type " + T());
        }
    }

    @Override // com.android.tools.r8.internal.B7
    public final D6 a() {
        return this;
    }

    @Override // com.android.tools.r8.internal.B7
    public final int z() {
        return U();
    }

    @Override // com.android.tools.r8.internal.B7
    public final void a(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var, C0722p1 c0722p1, AbstractC1476Wj abstractC1476Wj, AbstractC2331jn abstractC2331jn, AbstractC3649o0 abstractC3649o0, C2363kB c2363kB, AbstractC1899eF abstractC1899eF) {
        abstractC1899eF.a(U());
    }

    @Override // com.android.tools.r8.internal.B7
    public final com.android.tools.r8.ir.optimize.V a(C3550d0 c3550d0, C0753u c0753u, com.android.tools.r8.graph.o5 o5Var) {
        return com.android.tools.r8.ir.optimize.V.d;
    }

    @Override // com.android.tools.r8.internal.B7
    public final void a(C2757p8 c2757p8) {
        c2757p8.a(this);
    }

    @Override // com.android.tools.r8.internal.B7
    public final void a(C2102gl c2102gl, N8 n8, A8 a8) {
        H8 h8E = n8.e();
        H8 h8E2 = n8.e();
        c2102gl.b(T(), h8E.a, n8.e().a, h8E2.a);
    }

    @Override // com.android.tools.r8.internal.B7
    public final AbstractC2359k7 a(AbstractC2359k7 abstractC2359k7, C0702m c0702m, InterfaceC3107u6 interfaceC3107u6) {
        C0722p1 c0722p1A = c0702m.a();
        EnumC2523mE enumC2523mET = T();
        abstractC2359k7.getClass();
        C0722p1 c0722p1A2 = c0702m.a();
        return abstractC2359k7.a(c0702m, interfaceC3107u6, InterfaceC1348Si.a(enumC2523mET, c0722p1A2).a(c0722p1A2)).a(c0702m, interfaceC3107u6, c0722p1A.A1).a(c0702m, interfaceC3107u6, a(c0722p1A));
    }
}
