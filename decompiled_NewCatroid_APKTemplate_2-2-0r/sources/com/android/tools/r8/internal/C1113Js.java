package com.android.tools.r8.internal;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0721p0;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.ir.optimize.C3550d0;
import com.android.tools.r8.shaking.C3733i;
import com.android.tools.r8.shaking.C3804u;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Js, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1113Js extends AbstractC1084Is {
    static final /* synthetic */ boolean j = true;
    private final C0721p0 i;

    public C1113Js(C0721p0 c0721p0, S10 s10, List list) {
        super(s10, list);
        if (!j && c0721p0 == null) {
            throw new AssertionError();
        }
        this.i = c0721p0;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final int G2() {
        return 32;
    }

    @Override // com.android.tools.r8.internal.AbstractC1084Is
    public final C0767v2 L2() {
        return this.i.e.e;
    }

    @Override // com.android.tools.r8.internal.AbstractC1084Is
    protected final String N2() {
        return TypedValues.Custom.NAME;
    }

    public final C0721p0 Q2() {
        return this.i;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final C1113Js Y() {
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC1084Is, com.android.tools.r8.internal.AbstractC2412ko
    public final AbstractC3472z00 a(C0702m c0702m) {
        C0702m<C3733i> c0702mI0;
        ArrayList arrayListA;
        AbstractC3472z00 abstractC3472z00A = super.a(c0702m);
        if (!c0702m.f().i() || (arrayListA = JA.a(this.i, (c0702mI0 = c0702m.i0()))) == null || arrayListA.isEmpty()) {
            return abstractC3472z00A;
        }
        boolean z = j;
        if (!z && !(abstractC3472z00A instanceof C1229Oa)) {
            throw new AssertionError();
        }
        C1513Xr c1513XrS = abstractC3472z00A.b().S();
        C0767v2 c0767v2 = c0702m.a().Z1;
        if (abstractC3472z00A.b().R() == c0767v2) {
            if (!z && !c1513XrS.e()) {
                throw new AssertionError();
            }
            if (arrayListA.size() == 1) {
                if (z || arrayListA.get(0) == c1513XrS.d()) {
                    return abstractC3472z00A;
                }
                throw new AssertionError();
            }
        }
        final C1513Xr.a aVarA = C1513Xr.a();
        arrayListA.forEach(new Consumer() { // from class: com.android.tools.r8.internal.Js$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                aVarA.a((C0767v2) obj, true);
            }
        });
        C1513Xr c1513XrA = aVarA.a();
        if (!z) {
            C1513Xr c1513XrS2 = abstractC3472z00A.b().S();
            if (abstractC3472z00A.b().R() == c0767v2) {
                if (!z && !c1513XrA.b(c1513XrS2.d())) {
                    throw new AssertionError();
                }
            } else {
                if (!z && !c1513XrS2.f()) {
                    throw new AssertionError();
                }
                if (!z && !c1513XrA.b(abstractC3472z00A.b().R())) {
                    throw new AssertionError();
                }
            }
        }
        return C1229Oa.a(c0767v2, WH.i(), c0702mI0, c1513XrA);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean b(AbstractC2412ko abstractC2412ko) {
        abstractC2412ko.getClass();
        return (abstractC2412ko instanceof C1113Js) && this.i == abstractC2412ko.Y().i;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean c1() {
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean d(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var) {
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final String toString() {
        return super.toString() + "; call site: " + this.i.r0();
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final C0767v2 a(C0702m c0702m, Q00 q00) {
        return this.i.e.e;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(C2322jf c2322jf) {
        com.android.tools.r8.dex.code.B1 k1;
        int iP2 = P2();
        c2322jf.a(iP2);
        if (d(c2322jf)) {
            if (!j) {
                c(c2322jf);
            }
            k1 = new com.android.tools.r8.dex.code.L1(b(c2322jf), iP2, this.i);
        } else {
            int[] iArr = new int[5];
            k1 = new com.android.tools.r8.dex.code.K1(a(c2322jf, iArr), this.i, iArr[0], iArr[1], iArr[2], iArr[3], iArr[4]);
        }
        a(k1, c2322jf);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(L6 l6) {
        l6.a(new J7(this.i), this);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final com.android.tools.r8.ir.optimize.V a(C3550d0 c3550d0, com.android.tools.r8.graph.o5 o5Var) {
        return com.android.tools.r8.ir.optimize.V.c;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final Object a(AbstractC1855de abstractC1855de) {
        abstractC1855de.a(this);
        return null;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(InterfaceC2641no interfaceC2641no, C3340xC c3340xC) {
        c3340xC.a(this, interfaceC2641no);
        if (this.i.e.e.Y0()) {
            return;
        }
        S10 s10 = this.a;
        if (s10 == null) {
            c3340xC.a(this.i.e.e, this, interfaceC2641no);
        } else {
            if (!j && !s10.S()) {
                throw new AssertionError();
            }
            c3340xC.b(this, interfaceC2641no);
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    final void a(C3804u c3804u) {
        c3804u.a(this.i);
    }
}
