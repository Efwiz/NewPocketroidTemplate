package com.android.tools.r8.internal;

import com.android.tools.r8.dex.code.C0524x0;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.ir.optimize.C3550d0;
import java.util.Arrays;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class BG extends AbstractC2412ko {
    static final /* synthetic */ boolean k = true;
    public final int h;
    public final long i;
    public final short[] j;

    public BG(S10 s10, int i, long j, short[] sArr) {
        super((S10) null, s10);
        this.h = i;
        this.i = j;
        this.j = sArr;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final int E2() {
        return 255;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final int F2() {
        if (k) {
            return 0;
        }
        throw new AssertionError("NewArrayFilledData defines no values.");
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final int G2() {
        return 48;
    }

    public final S10 J2() {
        return (S10) this.b.get(0);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final Object a(AbstractC1855de abstractC1855de) {
        return null;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean b(AbstractC2412ko abstractC2412ko) {
        if (!abstractC2412ko.o2()) {
            return false;
        }
        BG bgS0 = abstractC2412ko.s0();
        return bgS0.h == this.h && bgS0.i == this.i && Arrays.equals(bgS0.j, this.j);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean c1() {
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean d(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var) {
        return false;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko, com.android.tools.r8.internal.InterfaceC3174v
    public final boolean g() {
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean o2() {
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final BG s0() {
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(C2322jf c2322jf) {
        c2322jf.a(this, new C0524x0(c2322jf.a(W0(), J2())));
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(L6 l6) {
        throw new C3405y10("Conversion from DEX to classfile not supported for NewArrayFilledData");
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(InterfaceC2641no interfaceC2641no, C3340xC c3340xC) {
        throw new C3405y10("Conversion from DEX to classfile not supported for NewArrayFilledData");
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final B1 a(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var) {
        if (!b(c0702m, o5Var) && J2().Z() <= 1) {
            boolean z = k;
            if (!z && J2().f0() != this) {
                throw new AssertionError();
            }
            if (!z && J2().i()) {
                throw new AssertionError();
            }
            if (!z) {
                AbstractC2412ko abstractC2412ko = J2().b;
                abstractC2412ko.getClass();
                if (!(abstractC2412ko instanceof AG)) {
                    throw new AssertionError();
                }
            }
            if (this.i <= 2147483647L) {
                if (z) {
                    return GW.a(c0702m.b().a((int) this.i));
                }
                throw new AssertionError();
            }
        }
        return C2892r10.F();
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean b(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var) {
        return c0702m.Y().W0 || J2().w().F();
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean a(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var, C2332jo c2332jo) {
        if (b(c0702m, o5Var) || J2().Z() > 1) {
            return true;
        }
        boolean z = k;
        if (!z && J2().f0() != this) {
            throw new AssertionError();
        }
        if (!z && J2().i()) {
            throw new AssertionError();
        }
        if (z) {
            return false;
        }
        AbstractC2412ko abstractC2412ko = J2().b;
        abstractC2412ko.getClass();
        if (abstractC2412ko instanceof AG) {
            return false;
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final com.android.tools.r8.ir.optimize.V a(C3550d0 c3550d0, com.android.tools.r8.graph.o5 o5Var) {
        return com.android.tools.r8.ir.optimize.V.d;
    }
}
