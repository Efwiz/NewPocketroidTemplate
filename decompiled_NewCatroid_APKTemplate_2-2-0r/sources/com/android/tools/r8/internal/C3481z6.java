package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0753u;
import com.android.tools.r8.ir.optimize.C3550d0;
import com.android.tools.r8.naming.AbstractC3649o0;

/* JADX INFO: renamed from: com.android.tools.r8.internal.z6, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C3481z6 extends B7 {
    static final /* synthetic */ boolean d = true;
    private final a b;
    private final EnumC2527mI c;

    /* JADX INFO: renamed from: com.android.tools.r8.internal.z6$a */
    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public enum a {
        Add,
        Sub,
        Mul,
        Div,
        Rem;
    }

    public C3481z6(a aVar, EnumC2527mI enumC2527mI) {
        boolean z = d;
        if (!z && aVar == null) {
            throw new AssertionError();
        }
        if (!z && enumC2527mI == null) {
            throw new AssertionError();
        }
        this.b = aVar;
        this.c = enumC2527mI;
    }

    public int T() {
        return a(this.b, this.c);
    }

    public a U() {
        return this.b;
    }

    public EnumC2527mI V() {
        return this.c;
    }

    @Override // com.android.tools.r8.internal.B7
    public final void a(com.android.tools.r8.utils.structural.o oVar) {
    }

    @Override // com.android.tools.r8.internal.B7
    public final boolean x() {
        a aVar;
        EnumC2527mI enumC2527mI = this.c;
        return (enumC2527mI == EnumC2527mI.FLOAT || enumC2527mI == EnumC2527mI.DOUBLE || ((aVar = this.b) != a.Div && aVar != a.Rem)) ? false : true;
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

    public static C3481z6 a(int i) {
        switch (i) {
            case 96:
                return new C3481z6(a.Add, EnumC2527mI.INT);
            case 97:
                return new C3481z6(a.Add, EnumC2527mI.LONG);
            case 98:
                return new C3481z6(a.Add, EnumC2527mI.FLOAT);
            case 99:
                return new C3481z6(a.Add, EnumC2527mI.DOUBLE);
            case 100:
                return new C3481z6(a.Sub, EnumC2527mI.INT);
            case 101:
                return new C3481z6(a.Sub, EnumC2527mI.LONG);
            case 102:
                return new C3481z6(a.Sub, EnumC2527mI.FLOAT);
            case 103:
                return new C3481z6(a.Sub, EnumC2527mI.DOUBLE);
            case 104:
                return new C3481z6(a.Mul, EnumC2527mI.INT);
            case 105:
                return new C3481z6(a.Mul, EnumC2527mI.LONG);
            case 106:
                return new C3481z6(a.Mul, EnumC2527mI.FLOAT);
            case 107:
                return new C3481z6(a.Mul, EnumC2527mI.DOUBLE);
            case 108:
                return new C3481z6(a.Div, EnumC2527mI.INT);
            case 109:
                return new C3481z6(a.Div, EnumC2527mI.LONG);
            case 110:
                return new C3481z6(a.Div, EnumC2527mI.FLOAT);
            case 111:
                return new C3481z6(a.Div, EnumC2527mI.DOUBLE);
            case 112:
                return new C3481z6(a.Rem, EnumC2527mI.INT);
            case 113:
                return new C3481z6(a.Rem, EnumC2527mI.LONG);
            case 114:
                return new C3481z6(a.Rem, EnumC2527mI.FLOAT);
            case 115:
                return new C3481z6(a.Rem, EnumC2527mI.DOUBLE);
            default:
                throw new C3405y10("Wrong ASM opcode for CfArithmeticBinop " + i);
        }
    }

    public static int a(a aVar, EnumC2527mI enumC2527mI) {
        int i;
        int i2 = AbstractC3412y6.b[enumC2527mI.ordinal()];
        if (i2 == 1) {
            i = 1;
        } else if (i2 != 2) {
            i = i2 != 3 ? 0 : 3;
        } else {
            i = 2;
        }
        int i3 = AbstractC3412y6.a[aVar.ordinal()];
        if (i3 == 1) {
            return i + 96;
        }
        if (i3 == 2) {
            return i + 100;
        }
        if (i3 == 3) {
            return i + 104;
        }
        if (i3 == 4) {
            return i + 108;
        }
        if (i3 == 5) {
            return i + 112;
        }
        throw new C3405y10("CfArithmeticBinop has unknown opcode " + aVar);
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
        int i4 = AbstractC3412y6.a[this.b.ordinal()];
        if (i4 == 1) {
            c2102gl.a(this.c, i3, i2, i);
            return;
        }
        if (i4 == 2) {
            c2102gl.r(this.c, i3, i2, i);
            return;
        }
        if (i4 == 3) {
            c2102gl.g(this.c, i3, i2, i);
            return;
        }
        if (i4 == 4) {
            c2102gl.e(this.c, i3, i2, i);
        } else {
            if (i4 != 5) {
                throw new C3405y10("CfArithmeticBinop has unknown opcode " + this.b);
            }
            c2102gl.k(this.c, i3, i2, i);
        }
    }

    @Override // com.android.tools.r8.internal.B7
    public final AbstractC2359k7 a(AbstractC2359k7 abstractC2359k7, C0702m c0702m, InterfaceC3107u6 interfaceC3107u6) {
        EnumC2527mI enumC2527mI = this.c;
        abstractC2359k7.getClass();
        AbstractC2359k7 abstractC2359k7A = abstractC2359k7.a(c0702m, interfaceC3107u6, enumC2527mI.a(c0702m.a()));
        EnumC2527mI enumC2527mI2 = this.c;
        abstractC2359k7A.getClass();
        AbstractC2359k7 abstractC2359k7A2 = abstractC2359k7A.a(c0702m, interfaceC3107u6, enumC2527mI2.a(c0702m.a()));
        EnumC2527mI enumC2527mI3 = this.c;
        abstractC2359k7A2.getClass();
        return abstractC2359k7A2.a(interfaceC3107u6, enumC2527mI3.a(c0702m.a()));
    }
}
