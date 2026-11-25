package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.naming.AbstractC3649o0;
import com.huawei.hms.ml.language.common.utils.Constant;

/* JADX INFO: renamed from: com.android.tools.r8.internal.rn, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2945rn extends AbstractC2804pn {
    static final /* synthetic */ boolean d = true;
    private final C0767v2 c;

    C2945rn(C0767v2 c0767v2) {
        boolean z = d;
        if (!z && c0767v2 == null) {
            throw new AssertionError();
        }
        if (!z && !c0767v2.X0()) {
            throw new AssertionError();
        }
        if (!z && c0767v2.U0()) {
            throw new AssertionError();
        }
        this.c = c0767v2;
    }

    public final C0767v2 T() {
        return this.c;
    }

    @Override // com.android.tools.r8.internal.AbstractC2826q4, com.android.tools.r8.internal.InterfaceC1348Si
    public final C0767v2 a(C0722p1 c0722p1) {
        return this.c;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && C2945rn.class == obj.getClass() && this.c == ((C2945rn) obj).c;
    }

    public final int hashCode() {
        return this.c.hashCode();
    }

    @Override // com.android.tools.r8.internal.AbstractC2826q4, com.android.tools.r8.internal.InterfaceC1348Si
    public final C2945rn j() {
        return this;
    }

    public final String toString() {
        return "Initialized(" + this.c.toString() + Constant.AFTER_QUTO;
    }

    @Override // com.android.tools.r8.internal.AbstractC2804pn
    public final BS a(C0702m c0702m) {
        return this.c.c((C0702m<?>) c0702m).d();
    }

    @Override // com.android.tools.r8.internal.InterfaceC1348Si
    public final Object a(AbstractC1476Wj abstractC1476Wj, AbstractC3649o0 abstractC3649o0) {
        C0767v2 c0767v2D = abstractC1476Wj.d(this.c);
        if (!d && c0767v2D == C0722p1.S5) {
            throw new AssertionError();
        }
        char cC1 = c0767v2D.c1();
        if (cC1 == 'D') {
            return JK.d;
        }
        if (cC1 == 'F') {
            return JK.c;
        }
        if (cC1 == 'L') {
            return abstractC3649o0.d(c0767v2D);
        }
        if (cC1 == 'I') {
            return JK.b;
        }
        if (cC1 == 'J') {
            return JK.e;
        }
        throw new C3405y10("Unexpected value type: " + c0767v2D);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.tools.r8.internal.InterfaceC2782pV
    public final InterfaceC2782pV a(C0702m c0702m, InterfaceC2782pV interfaceC2782pV) {
        if (!equals(interfaceC2782pV)) {
            AbstractC2826q4 abstractC2826q4 = (AbstractC2826q4) interfaceC2782pV;
            abstractC2826q4.getClass();
            if (!(abstractC2826q4 instanceof TH)) {
                if (!(abstractC2826q4 instanceof FK) && !interfaceC2782pV.isPrimitive() && !interfaceC2782pV.r()) {
                    if (!d && !interfaceC2782pV.m()) {
                        throw new AssertionError();
                    }
                    BS bsA = a(c0702m).a(interfaceC2782pV.S().a(c0702m), c0702m);
                    if (AbstractC1322Ri.a || !bsA.E()) {
                        return new C2875qn(bsA);
                    }
                    throw new AssertionError();
                }
                return FK.c;
            }
        }
        return this;
    }
}
