package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0753u;
import com.android.tools.r8.ir.optimize.C3550d0;
import com.android.tools.r8.naming.AbstractC3649o0;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class Q7 extends B7 {
    static final /* synthetic */ boolean d = true;
    private final P7 b;
    private final EnumC2527mI c;

    public Q7(P7 p7, EnumC2527mI enumC2527mI) {
        boolean z = d;
        if (!z && p7 == null) {
            throw new AssertionError();
        }
        if (!z && enumC2527mI == null) {
            throw new AssertionError();
        }
        if (!z && (enumC2527mI == EnumC2527mI.FLOAT || enumC2527mI == EnumC2527mI.DOUBLE)) {
            throw new AssertionError();
        }
        this.b = p7;
        this.c = enumC2527mI;
    }

    public final int T() {
        int iOrdinal = this.b.ordinal();
        if (iOrdinal == 0) {
            return this.c.a() ? 121 : 120;
        }
        if (iOrdinal == 1) {
            return this.c.a() ? 123 : 122;
        }
        if (iOrdinal == 2) {
            return this.c.a() ? 125 : 124;
        }
        if (iOrdinal == 3) {
            return this.c.a() ? 127 : 126;
        }
        if (iOrdinal == 4) {
            return this.c.a() ? 129 : 128;
        }
        if (iOrdinal == 5) {
            return this.c.a() ? 131 : 130;
        }
        throw new C3405y10("CfLogicalBinop has unknown opcode " + this.b);
    }

    public final P7 U() {
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

    @Override // com.android.tools.r8.internal.B7
    public final com.android.tools.r8.ir.optimize.V a(C3550d0 c3550d0, C0753u c0753u, com.android.tools.r8.graph.o5 o5Var) {
        return com.android.tools.r8.ir.optimize.V.d;
    }

    public static Q7 a(int i) {
        switch (i) {
            case 120:
                return new Q7(P7.a, EnumC2527mI.INT);
            case 121:
                return new Q7(P7.a, EnumC2527mI.LONG);
            case 122:
                return new Q7(P7.b, EnumC2527mI.INT);
            case 123:
                return new Q7(P7.b, EnumC2527mI.LONG);
            case 124:
                return new Q7(P7.c, EnumC2527mI.INT);
            case 125:
                return new Q7(P7.c, EnumC2527mI.LONG);
            case 126:
                return new Q7(P7.d, EnumC2527mI.INT);
            case 127:
                return new Q7(P7.d, EnumC2527mI.LONG);
            case 128:
                return new Q7(P7.e, EnumC2527mI.INT);
            case 129:
                return new Q7(P7.e, EnumC2527mI.LONG);
            case 130:
                return new Q7(P7.f, EnumC2527mI.INT);
            case 131:
                return new Q7(P7.f, EnumC2527mI.LONG);
            default:
                throw new C3405y10("Wrong ASM opcode for CfLogicalBinop " + i);
        }
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
        int i2 = n8.e().a;
        int i3 = n8.a(EnumC1812d20.a(this.c)).a;
        int iOrdinal = this.b.ordinal();
        if (iOrdinal == 0) {
            c2102gl.n(this.c, i3, i2, i);
            return;
        }
        if (iOrdinal == 1) {
            c2102gl.p(this.c, i3, i2, i);
            return;
        }
        if (iOrdinal == 2) {
            c2102gl.s(this.c, i3, i2, i);
            return;
        }
        if (iOrdinal == 3) {
            c2102gl.c(this.c, i3, i2, i);
            return;
        }
        if (iOrdinal == 4) {
            c2102gl.i(this.c, i3, i2, i);
        } else {
            if (iOrdinal != 5) {
                throw new C3405y10("CfLogicalBinop has unknown opcode " + this.b);
            }
            c2102gl.u(this.c, i3, i2, i);
        }
    }

    @Override // com.android.tools.r8.internal.B7
    public final AbstractC2359k7 a(AbstractC2359k7 abstractC2359k7, C0702m c0702m, InterfaceC3107u6 interfaceC3107u6) {
        EnumC2527mI enumC2527mI = this.c;
        int iOrdinal = this.b.ordinal();
        EnumC2527mI enumC2527mI2 = (iOrdinal == 3 || iOrdinal == 4 || iOrdinal == 5) ? enumC2527mI : EnumC2527mI.INT;
        abstractC2359k7.getClass();
        AbstractC2359k7 abstractC2359k7A = abstractC2359k7.a(c0702m, interfaceC3107u6, enumC2527mI2.a(c0702m.a()));
        abstractC2359k7A.getClass();
        AbstractC2359k7 abstractC2359k7A2 = abstractC2359k7A.a(c0702m, interfaceC3107u6, enumC2527mI.a(c0702m.a()));
        abstractC2359k7A2.getClass();
        return abstractC2359k7A2.a(interfaceC3107u6, enumC2527mI.a(c0702m.a()));
    }
}
