package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0639e;
import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.shaking.C3733i;
import com.huawei.hms.ml.language.common.utils.Constant;

/* JADX INFO: renamed from: com.android.tools.r8.internal.kV, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2388kV extends AbstractC2467lV {
    static final /* synthetic */ boolean e = true;
    private final C0767v2 d;

    C2388kV(C0767v2 c0767v2) {
        this.d = c0767v2;
    }

    public final C0767v2 F() {
        return this.d;
    }

    @Override // com.android.tools.r8.internal.InterfaceC0989Fn
    public final InterfaceC0989Fn a(com.android.tools.r8.graph.proto.c cVar) {
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC3292wV, com.android.tools.r8.internal.B1
    /* JADX INFO: renamed from: c, reason: merged with bridge method [inline-methods] */
    public final AbstractC3292wV b(C0702m c0702m, AbstractC1476Wj abstractC1476Wj, AbstractC1476Wj abstractC1476Wj2) {
        if (e || abstractC1476Wj.a(abstractC1476Wj2, this.d) == this.d) {
            return this;
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.internal.B1
    public final boolean equals(Object obj) {
        return this == obj;
    }

    @Override // com.android.tools.r8.internal.B1
    public final int hashCode() {
        return this.d.hashCode();
    }

    @Override // com.android.tools.r8.internal.B1
    public final C2388kV m() {
        return this;
    }

    @Override // com.android.tools.r8.internal.B1
    public final String toString() {
        return "SingleConstClassValue(" + this.d.r0() + Constant.AFTER_QUTO;
    }

    @Override // com.android.tools.r8.internal.AbstractC3292wV
    public final AbstractC2412ko a(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var, C2145hI c2145hI, InterfaceC3176v00 interfaceC3176v00) {
        AbstractC3472z00 abstractC3472z00A = interfaceC3176v00.a();
        com.android.tools.r8.graph.X xJ = interfaceC3176v00.j();
        boolean z = e;
        if (!z && !abstractC3472z00A.w()) {
            throw new AssertionError();
        }
        if (!z && !c0702m.a(c0702m.a().m2, abstractC3472z00A.b().R()).d()) {
            throw new AssertionError();
        }
        C3446yc c3446yc = new C3446yc(new S10(c2145hI.a(), AbstractC3472z00.a(c0702m.a().m2, WH.b(), (C0702m<?>) c0702m).b(), xJ), this.d);
        if (z || !c3446yc.b(c0702m, o5Var)) {
            return c3446yc;
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.internal.AbstractC3292wV
    final boolean a(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var) {
        C0767v2 c0767v2B = this.d.b(c0702m.a());
        if (c0767v2B.O0()) {
            AbstractC0727q0 abstractC0727q0C = c0702m.c(c0767v2B);
            return abstractC0727q0C != null && abstractC0727q0C.d(c0702m) && AbstractC0639e.a(abstractC0727q0C, o5Var, c0702m).d();
        }
        if (e || c0767v2B.W0()) {
            return true;
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.internal.AbstractC3292wV
    public final boolean a(C0702m c0702m) {
        C0767v2 c0767v2B = this.d.b(c0702m.a());
        if (c0767v2B.O0()) {
            AbstractC0727q0 abstractC0727q0C = c0702m.c(c0767v2B);
            if (abstractC0727q0C == null || !abstractC0727q0C.J1() || !abstractC0727q0C.d(c0702m)) {
                return false;
            }
            ((C3733i) c0702m.f()).getClass();
            return (abstractC0727q0C.e0() && C1121Ka.a(c0702m, abstractC0727q0C.a0())) ? false : true;
        }
        if (e || c0767v2B.W0()) {
            return true;
        }
        throw new AssertionError();
    }
}
