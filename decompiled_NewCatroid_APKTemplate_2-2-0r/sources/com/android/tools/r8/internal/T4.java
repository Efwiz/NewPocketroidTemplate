package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.ir.optimize.C3550d0;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class T4 extends AbstractC2412ko {
    protected final EnumC2527mI h;

    public T4(EnumC2527mI enumC2527mI, S10 s10, S10 s11, S10 s12) {
        super(s10);
        this.h = enumC2527mI;
        if (K2() && !s12.J() && s11.J()) {
            c(s12);
            c(s11);
        } else {
            c(s11);
            c(s12);
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public int E2() {
        return M2();
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public int F2() {
        return M2();
    }

    public final EnumC2527mI J2() {
        return this.h;
    }

    public abstract boolean K2();

    public final S10 L2() {
        return (S10) this.b.get(0);
    }

    int M2() {
        return (!h(N2()) || N2().n().E().R2()) ? 255 : 15;
    }

    public final S10 N2() {
        return (S10) this.b.get(1);
    }

    abstract double a(double d, double d2);

    abstract float a(float f, float f2);

    abstract int a(int i, int i2);

    abstract long a(long j, long j2);

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean a(com.android.tools.r8.ir.regalloc.f fVar) {
        if (!N2().Y() || !L2().Y()) {
            return false;
        }
        int iA = fVar.a(L2(), W0());
        int iA2 = fVar.a(N2(), W0());
        int iA3 = fVar.a(this.a, W0());
        if ((iA == iA3 || (K2() && iA2 == iA3)) && iA <= 15 && iA2 <= 15) {
            return (fVar.b().v() && (this instanceof CF)) ? false : true;
        }
        return false;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean c1() {
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean d(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var) {
        return false;
    }

    boolean h(S10 s10) {
        return this.h == EnumC2527mI.INT && s10.L() && s10.n().E().Q2();
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean v1() {
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final T4 x() {
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final com.android.tools.r8.ir.optimize.V a(C3550d0 c3550d0, com.android.tools.r8.graph.o5 o5Var) {
        return com.android.tools.r8.ir.optimize.V.d;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(InterfaceC2641no interfaceC2641no, C3340xC c3340xC) {
        c3340xC.a(this, interfaceC2641no);
        c3340xC.b(this, interfaceC2641no);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public AbstractC3472z00 a(C0702m c0702m) {
        return XM.a(this.h);
    }
}
