package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0753u;
import com.android.tools.r8.ir.optimize.C3550d0;
import com.android.tools.r8.naming.AbstractC3649o0;
import java.util.function.Function;
import java.util.function.ToLongFunction;

/* JADX INFO: renamed from: com.android.tools.r8.internal.d7, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C1818d7 extends B7 {
    static final /* synthetic */ boolean d = true;
    public static final /* synthetic */ int e = 0;
    private final long b;
    private final EnumC1812d20 c;

    public C1818d7(long j, EnumC1812d20 enumC1812d20) {
        if (!d && enumC1812d20.a()) {
            throw new AssertionError("Should use CfConstNull");
        }
        this.b = j;
        this.c = enumC1812d20;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(com.android.tools.r8.utils.structural.z zVar) {
        zVar.a(new ToLongFunction() { // from class: com.android.tools.r8.internal.d7$$ExternalSyntheticLambda1
            @Override // java.util.function.ToLongFunction
            public final long applyAsLong(Object obj) {
                return ((C1818d7) obj).X();
            }
        }).c(new Function() { // from class: com.android.tools.r8.internal.d7$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((C1818d7) obj).Y();
            }
        });
    }

    public final double T() {
        if (d || this.c == EnumC1812d20.DOUBLE) {
            return Double.longBitsToDouble(this.b);
        }
        throw new AssertionError();
    }

    public final float U() {
        if (d || this.c == EnumC1812d20.FLOAT) {
            return Float.intBitsToFloat((int) this.b);
        }
        throw new AssertionError();
    }

    public final int V() {
        if (d || this.c == EnumC1812d20.INT) {
            return (int) this.b;
        }
        throw new AssertionError();
    }

    public final long W() {
        if (d || this.c == EnumC1812d20.LONG) {
            return this.b;
        }
        throw new AssertionError();
    }

    public long X() {
        return this.b;
    }

    public final EnumC1812d20 Y() {
        return this.c;
    }

    @Override // com.android.tools.r8.internal.B7
    public final int z() {
        return 203;
    }

    @Override // com.android.tools.r8.internal.B7
    public final int a(B7 b7, com.android.tools.r8.utils.structural.a aVar, com.android.tools.r8.graph.C c) {
        return aVar.a(this, (C1818d7) b7, new d7$$ExternalSyntheticLambda0());
    }

    @Override // com.android.tools.r8.internal.B7
    public final void a(com.android.tools.r8.utils.structural.o oVar) {
        oVar.a(this, new d7$$ExternalSyntheticLambda0());
    }

    @Override // com.android.tools.r8.internal.B7
    public final void a(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var, C0722p1 c0722p1, AbstractC1476Wj abstractC1476Wj, AbstractC2331jn abstractC2331jn, AbstractC3649o0 abstractC3649o0, C2363kB c2363kB, AbstractC1899eF abstractC1899eF) {
        int i = AbstractC1742c7.a[this.c.ordinal()];
        if (i == 1) {
            int iV = V();
            if (-1 <= iV && iV <= 5) {
                abstractC1899eF.a(iV + 3);
                return;
            }
            if (-128 <= iV && iV <= 127) {
                abstractC1899eF.b(16, iV);
                return;
            } else if (-32768 <= iV && iV <= 32767) {
                abstractC1899eF.b(17, iV);
                return;
            } else {
                abstractC1899eF.a(Integer.valueOf(iV));
                return;
            }
        }
        if (i == 2) {
            long jW = W();
            if (jW != 0 && jW != 1) {
                abstractC1899eF.a(Long.valueOf(jW));
                return;
            } else {
                abstractC1899eF.a(((int) jW) + 9);
                return;
            }
        }
        if (i == 3) {
            float fU = U();
            if (fU != 0.0f && fU != 1.0f && fU != 2.0f) {
                abstractC1899eF.a(Float.valueOf(fU));
                return;
            }
            abstractC1899eF.a(((int) fU) + 11);
            if (Float.floatToIntBits(fU) == Float.floatToIntBits(-0.0f)) {
                abstractC1899eF.a(118);
                return;
            }
            return;
        }
        if (i != 4) {
            throw new C3405y10("Non supported type in cf backend: " + this.c);
        }
        double dT = T();
        if (dT != 0.0d && dT != 1.0d) {
            abstractC1899eF.a(Double.valueOf(dT));
            return;
        }
        abstractC1899eF.a(((int) dT) + 14);
        if (Double.doubleToLongBits(dT) == Double.doubleToLongBits(-0.0d)) {
            abstractC1899eF.a(119);
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
    public final void a(C2102gl c2102gl, N8 n8, A8 a8) {
        c2102gl.a(this.c.d(), n8.a(this.c).a, this.b);
    }

    @Override // com.android.tools.r8.internal.B7
    public final AbstractC2359k7 a(AbstractC2359k7 abstractC2359k7, C0702m c0702m, InterfaceC3107u6 interfaceC3107u6) {
        if (!d && this.c.a()) {
            throw new AssertionError();
        }
        EnumC1812d20 enumC1812d20 = this.c;
        abstractC2359k7.getClass();
        return abstractC2359k7.a(interfaceC3107u6, enumC1812d20.a(c0702m.a()));
    }
}
