package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0753u;
import com.android.tools.r8.ir.optimize.C3550d0;
import com.android.tools.r8.naming.AbstractC3649o0;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class U6 extends B7 {
    static final /* synthetic */ boolean d = true;
    private final EnumC2246ib b;
    private final EnumC2527mI c;

    public U6(EnumC2246ib enumC2246ib, EnumC2527mI enumC2527mI) {
        boolean z = d;
        if (!z && enumC2246ib == null) {
            throw new AssertionError();
        }
        if (!z && enumC2527mI == null) {
            throw new AssertionError();
        }
        if (!z && enumC2527mI != EnumC2527mI.LONG && enumC2527mI != EnumC2527mI.FLOAT && enumC2527mI != EnumC2527mI.DOUBLE) {
            throw new AssertionError();
        }
        if (!z && enumC2527mI == EnumC2527mI.LONG && enumC2246ib != EnumC2246ib.NONE) {
            throw new AssertionError();
        }
        if (!z && enumC2527mI != EnumC2527mI.LONG && enumC2246ib == EnumC2246ib.NONE) {
            throw new AssertionError();
        }
        this.b = enumC2246ib;
        this.c = enumC2527mI;
    }

    public final int T() {
        int i = T6.a[this.c.ordinal()];
        if (i == 1) {
            return 148;
        }
        if (i == 2) {
            return this.b == EnumC2246ib.LT ? 149 : 150;
        }
        if (i == 3) {
            return this.b == EnumC2246ib.LT ? 151 : 152;
        }
        throw new C3405y10("CfCmp has unknown type " + this.c);
    }

    public final EnumC2246ib U() {
        return this.b;
    }

    public final EnumC2527mI V() {
        return this.c;
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

    public static U6 a(int i) {
        switch (i) {
            case 148:
                return new U6(EnumC2246ib.NONE, EnumC2527mI.LONG);
            case 149:
                return new U6(EnumC2246ib.LT, EnumC2527mI.FLOAT);
            case 150:
                return new U6(EnumC2246ib.GT, EnumC2527mI.FLOAT);
            case 151:
                return new U6(EnumC2246ib.LT, EnumC2527mI.DOUBLE);
            case 152:
                return new U6(EnumC2246ib.GT, EnumC2527mI.DOUBLE);
            default:
                throw new C3405y10("Wrong ASM opcode for CfCmp " + i);
        }
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
    public final void a(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var, C0722p1 c0722p1, AbstractC1476Wj abstractC1476Wj, AbstractC2331jn abstractC2331jn, AbstractC3649o0 abstractC3649o0, C2363kB c2363kB, AbstractC1899eF abstractC1899eF) {
        abstractC1899eF.a(T());
    }

    @Override // com.android.tools.r8.internal.B7
    public final void a(C2102gl c2102gl, N8 n8, A8 a8) {
        int i = n8.e().a;
        c2102gl.a(this.c, this.b, n8.a(EnumC1812d20.INT).a, n8.e().a, i);
    }

    @Override // com.android.tools.r8.internal.B7
    public final AbstractC2359k7 a(AbstractC2359k7 abstractC2359k7, C0702m c0702m, InterfaceC3107u6 interfaceC3107u6) {
        EnumC2527mI enumC2527mI = this.c;
        abstractC2359k7.getClass();
        AbstractC2359k7 abstractC2359k7A = abstractC2359k7.a(c0702m, interfaceC3107u6, enumC2527mI.a(c0702m.a()));
        EnumC2527mI enumC2527mI2 = this.c;
        abstractC2359k7A.getClass();
        return abstractC2359k7A.a(c0702m, interfaceC3107u6, enumC2527mI2.a(c0702m.a())).a(interfaceC3107u6, c0702m.a().A1);
    }
}
