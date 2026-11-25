package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.utils.InterfaceC3880d;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class F5 extends A5 {
    private static final F5 a = new F5();
    static final /* synthetic */ boolean b = true;

    private F5() {
    }

    public static F5 g() {
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
        abstractC2862qcJ.getClass();
        if (abstractC2862qcJ instanceof C2716oc) {
            return fw.a(abstractC2862qcJ);
        }
        AbstractC1154Lf abstractC1154LfQ = abstractC2862qcJ.q();
        return abstractC1154LfQ.m() ? C2582n10.g() : new C2716oc(abstractC1154LfQ, abstractC2862qcJ.l());
    }
}
