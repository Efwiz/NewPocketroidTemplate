package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.utils.InterfaceC3880d;

/* JADX INFO: renamed from: com.android.tools.r8.internal.v5, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3183v5 extends A5 {
    private static final C3183v5 a = new C3183v5();
    static final /* synthetic */ boolean b = true;

    private C3183v5() {
    }

    public static C3183v5 g() {
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
        if (abstractC2862qcJ instanceof C1497Xb) {
            return fw.a(abstractC2862qcJ);
        }
        WH whR = abstractC2862qcJ.r();
        return whR.g() ? C2582n10.g() : new C1497Xb(whR, abstractC2862qcJ.l());
    }
}
