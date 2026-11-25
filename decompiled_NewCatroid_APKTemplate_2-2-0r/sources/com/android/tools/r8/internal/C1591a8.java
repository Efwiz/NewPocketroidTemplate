package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0753u;
import com.android.tools.r8.ir.optimize.C3550d0;
import com.android.tools.r8.naming.AbstractC3649o0;

/* JADX INFO: renamed from: com.android.tools.r8.internal.a8, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1591a8 extends B7 {
    static final /* synthetic */ boolean d = true;
    private final EnumC2527mI b;
    private final EnumC2527mI c;

    public C1591a8(EnumC2527mI enumC2527mI, EnumC2527mI enumC2527mI2) {
        boolean z = d;
        if (!z && enumC2527mI == enumC2527mI2) {
            throw new AssertionError();
        }
        if (!z && (enumC2527mI == EnumC2527mI.BYTE || enumC2527mI == EnumC2527mI.SHORT || enumC2527mI == EnumC2527mI.CHAR)) {
            throw new AssertionError();
        }
        if (!z && ((enumC2527mI2 == EnumC2527mI.BYTE || enumC2527mI2 == EnumC2527mI.SHORT || enumC2527mI2 == EnumC2527mI.CHAR) && enumC2527mI != EnumC2527mI.INT)) {
            throw new AssertionError();
        }
        this.b = enumC2527mI;
        this.c = enumC2527mI2;
    }

    public final int T() {
        int[] iArr = Z7.a;
        int i = iArr[this.b.ordinal()];
        if (i == 4) {
            int i2 = iArr[this.c.ordinal()];
            if (i2 == 5) {
                return 137;
            }
            if (i2 == 6) {
                return 138;
            }
            if (i2 == 7) {
                return 136;
            }
            throw new C3405y10("Invalid CfNumberConversion from " + this.b + " to " + this.c);
        }
        if (i == 5) {
            int i3 = iArr[this.c.ordinal()];
            if (i3 == 4) {
                return 140;
            }
            if (i3 == 6) {
                return 141;
            }
            if (i3 == 7) {
                return 139;
            }
            throw new C3405y10("Invalid CfNumberConversion from " + this.b + " to " + this.c);
        }
        if (i == 6) {
            int i4 = iArr[this.c.ordinal()];
            if (i4 == 4) {
                return 143;
            }
            if (i4 == 5) {
                return 144;
            }
            if (i4 == 7) {
                return 142;
            }
            throw new C3405y10("Invalid CfNumberConversion from " + this.b + " to " + this.c);
        }
        if (i != 7) {
            throw new C3405y10("Invalid CfNumberConversion from " + this.b + " to " + this.c);
        }
        switch (iArr[this.c.ordinal()]) {
            case 1:
                return 145;
            case 2:
                return 146;
            case 3:
                return 147;
            case 4:
                return 133;
            case 5:
                return 134;
            case 6:
                return 135;
            default:
                throw new C3405y10("Invalid CfNumberConversion from " + this.b + " to " + this.c);
        }
    }

    public final EnumC2527mI U() {
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
    public final void a(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var, C0722p1 c0722p1, AbstractC1476Wj abstractC1476Wj, AbstractC2331jn abstractC2331jn, AbstractC3649o0 abstractC3649o0, C2363kB c2363kB, AbstractC1899eF abstractC1899eF) {
        abstractC1899eF.a(T());
    }

    @Override // com.android.tools.r8.internal.B7
    public final void a(C2757p8 c2757p8) {
        c2757p8.a(this);
    }

    public static C1591a8 a(int i) {
        switch (i) {
            case 133:
                return new C1591a8(EnumC2527mI.INT, EnumC2527mI.LONG);
            case 134:
                return new C1591a8(EnumC2527mI.INT, EnumC2527mI.FLOAT);
            case 135:
                return new C1591a8(EnumC2527mI.INT, EnumC2527mI.DOUBLE);
            case 136:
                return new C1591a8(EnumC2527mI.LONG, EnumC2527mI.INT);
            case 137:
                return new C1591a8(EnumC2527mI.LONG, EnumC2527mI.FLOAT);
            case 138:
                return new C1591a8(EnumC2527mI.LONG, EnumC2527mI.DOUBLE);
            case 139:
                return new C1591a8(EnumC2527mI.FLOAT, EnumC2527mI.INT);
            case 140:
                return new C1591a8(EnumC2527mI.FLOAT, EnumC2527mI.LONG);
            case 141:
                return new C1591a8(EnumC2527mI.FLOAT, EnumC2527mI.DOUBLE);
            case 142:
                return new C1591a8(EnumC2527mI.DOUBLE, EnumC2527mI.INT);
            case 143:
                return new C1591a8(EnumC2527mI.DOUBLE, EnumC2527mI.LONG);
            case 144:
                return new C1591a8(EnumC2527mI.DOUBLE, EnumC2527mI.FLOAT);
            case 145:
                return new C1591a8(EnumC2527mI.INT, EnumC2527mI.BYTE);
            case 146:
                return new C1591a8(EnumC2527mI.INT, EnumC2527mI.CHAR);
            case 147:
                return new C1591a8(EnumC2527mI.INT, EnumC2527mI.SHORT);
            default:
                throw new C3405y10("Unexpected CfNumberConversion opcode " + i);
        }
    }

    @Override // com.android.tools.r8.internal.B7
    public final void a(C2102gl c2102gl, N8 n8, A8 a8) {
        int i = n8.e().a;
        EnumC2527mI enumC2527mI = this.c;
        c2102gl.a(enumC2527mI, this.b, n8.a(EnumC1812d20.a(enumC2527mI)).a, i);
    }

    @Override // com.android.tools.r8.internal.B7
    public final AbstractC2359k7 a(AbstractC2359k7 abstractC2359k7, C0702m c0702m, InterfaceC3107u6 interfaceC3107u6) {
        EnumC2527mI enumC2527mI = this.b;
        abstractC2359k7.getClass();
        AbstractC2359k7 abstractC2359k7A = abstractC2359k7.a(c0702m, interfaceC3107u6, enumC2527mI.a(c0702m.a()));
        EnumC2527mI enumC2527mI2 = this.c;
        abstractC2359k7A.getClass();
        return abstractC2359k7A.a(interfaceC3107u6, enumC2527mI2.a(c0702m.a()));
    }

    @Override // com.android.tools.r8.internal.B7
    public final com.android.tools.r8.ir.optimize.V a(C3550d0 c3550d0, C0753u c0753u, com.android.tools.r8.graph.o5 o5Var) {
        return com.android.tools.r8.ir.optimize.V.d;
    }
}
