package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0753u;
import com.android.tools.r8.ir.optimize.C3550d0;
import com.android.tools.r8.naming.AbstractC3649o0;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class U7 extends B7 {
    private final EnumC2527mI b;

    public U7(EnumC2527mI enumC2527mI) {
        this.b = enumC2527mI;
    }

    public final int T() {
        switch (T7.a[this.b.ordinal()]) {
            case 1:
            case 2:
            case 3:
            case 4:
                return 116;
            case 5:
                return 117;
            case 6:
                return 118;
            case 7:
                return 119;
            default:
                throw new C3405y10("Invalid type for CfNeg " + this.b);
        }
    }

    public final EnumC2527mI U() {
        return this.b;
    }

    @Override // com.android.tools.r8.internal.B7
    public final void a(com.android.tools.r8.utils.structural.o oVar) {
    }

    @Override // com.android.tools.r8.internal.B7
    public final int z() {
        return T();
    }

    @Override // com.android.tools.r8.internal.B7
    public final int a(B7 b7, com.android.tools.r8.utils.structural.a aVar, com.android.tools.r8.graph.C c) {
        com.android.tools.r8.graph.C.a(this, b7);
        return 0;
    }

    @Override // com.android.tools.r8.internal.B7
    public final void a(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var, C0722p1 c0722p1, AbstractC1476Wj abstractC1476Wj, AbstractC2331jn abstractC2331jn, AbstractC3649o0 abstractC3649o0, C2363kB c2363kB, AbstractC1899eF abstractC1899eF) {
        abstractC1899eF.a(T());
    }

    @Override // com.android.tools.r8.internal.B7
    public final void a(C2757p8 c2757p8) {
        c2757p8.a(this);
    }

    public static U7 a(int i) {
        switch (i) {
            case 116:
                return new U7(EnumC2527mI.INT);
            case 117:
                return new U7(EnumC2527mI.LONG);
            case 118:
                return new U7(EnumC2527mI.FLOAT);
            case 119:
                return new U7(EnumC2527mI.DOUBLE);
            default:
                throw new C3405y10("Invalid opcode for CfNeg " + i);
        }
    }

    @Override // com.android.tools.r8.internal.B7
    public final void a(C2102gl c2102gl, N8 n8, A8 a8) {
        int i = n8.e().a;
        EnumC2527mI enumC2527mI = this.b;
        c2102gl.a(enumC2527mI, n8.a(EnumC1812d20.a(enumC2527mI)).a, i);
    }

    @Override // com.android.tools.r8.internal.B7
    public final AbstractC2359k7 a(AbstractC2359k7 abstractC2359k7, C0702m c0702m, InterfaceC3107u6 interfaceC3107u6) {
        EnumC2527mI enumC2527mI = this.b;
        abstractC2359k7.getClass();
        AbstractC2359k7 abstractC2359k7A = abstractC2359k7.a(c0702m, interfaceC3107u6, enumC2527mI.a(c0702m.a()));
        EnumC2527mI enumC2527mI2 = this.b;
        abstractC2359k7A.getClass();
        return abstractC2359k7A.a(interfaceC3107u6, enumC2527mI2.a(c0702m.a()));
    }

    @Override // com.android.tools.r8.internal.B7
    public final com.android.tools.r8.ir.optimize.V a(C3550d0 c3550d0, C0753u c0753u, com.android.tools.r8.graph.o5 o5Var) {
        return com.android.tools.r8.ir.optimize.V.d;
    }
}
