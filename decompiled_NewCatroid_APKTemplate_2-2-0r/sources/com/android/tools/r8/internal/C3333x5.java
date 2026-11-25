package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.utils.InterfaceC3880d;

/* JADX INFO: renamed from: com.android.tools.r8.internal.x5, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3333x5 extends A5 {
    private static final C3333x5 a = new C3333x5();
    static final /* synthetic */ boolean b = true;

    private C3333x5() {
    }

    public static C3333x5 g() {
        return a;
    }

    @Override // com.android.tools.r8.internal.AbstractC3122uL
    public final AbstractC3122uL a(C0702m c0702m, AbstractC3122uL abstractC3122uL, C0767v2 c0767v2, FW fw, InterfaceC3880d interfaceC3880d) {
        if (abstractC3122uL.c()) {
            return this;
        }
        if (abstractC3122uL instanceof C2582n10) {
            return abstractC3122uL;
        }
        boolean z = b;
        if (!z && !abstractC3122uL.d()) {
            throw new AssertionError();
        }
        if (!z && !abstractC3122uL.a().p()) {
            throw new AssertionError();
        }
        AbstractC2862qc abstractC2862qcJ = abstractC3122uL.a().j();
        B1 b1A = abstractC2862qcJ.a(c0702m);
        AbstractC1154Lf abstractC1154LfA = F20.a(c0702m, abstractC2862qcJ.q(), c0767v2);
        if (!(abstractC2862qcJ instanceof C1624ac) || abstractC1154LfA.m()) {
            return (b1A.isUnknown() && abstractC1154LfA.m()) ? C2582n10.g() : new C1624ac(b1A, abstractC1154LfA, abstractC2862qcJ.l());
        }
        return fw.a(abstractC2862qcJ);
    }
}
