package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0759u2;
import com.huawei.hms.ml.language.common.utils.Constant;

/* JADX INFO: renamed from: com.android.tools.r8.internal.vV, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C3214vV extends AbstractC2467lV {
    static final /* synthetic */ boolean e = true;
    private final C0759u2 d;

    C3214vV(C0759u2 c0759u2) {
        this.d = c0759u2;
    }

    @Override // com.android.tools.r8.internal.B1
    public final boolean C() {
        return true;
    }

    public C0759u2 F() {
        return this.d;
    }

    @Override // com.android.tools.r8.internal.AbstractC3292wV, com.android.tools.r8.internal.InterfaceC0989Fn
    /* JADX INFO: renamed from: a */
    public final InterfaceC0989Fn b(C0702m c0702m, AbstractC1476Wj abstractC1476Wj, AbstractC1476Wj abstractC1476Wj2) {
        return this;
    }

    @Override // com.android.tools.r8.internal.InterfaceC0989Fn
    public final InterfaceC0989Fn a(com.android.tools.r8.graph.proto.c cVar) {
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC3292wV
    public final boolean a(C0702m c0702m) {
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC3292wV
    final boolean a(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var) {
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC3292wV, com.android.tools.r8.internal.B1
    public final B1 b(C0702m c0702m, AbstractC1476Wj abstractC1476Wj, AbstractC1476Wj abstractC1476Wj2) {
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC3292wV
    /* JADX INFO: renamed from: c */
    public final AbstractC3292wV b(C0702m c0702m, AbstractC1476Wj abstractC1476Wj, AbstractC1476Wj abstractC1476Wj2) {
        return this;
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
    public final C3214vV q() {
        return this;
    }

    @Override // com.android.tools.r8.internal.B1
    public final String toString() {
        return "SingleStringValue(" + this.d + Constant.AFTER_QUTO;
    }

    @Override // com.android.tools.r8.internal.AbstractC3292wV
    public final AbstractC2412ko a(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var, C2145hI c2145hI, InterfaceC3176v00 interfaceC3176v00) {
        AbstractC3472z00 abstractC3472z00A = interfaceC3176v00.a();
        com.android.tools.r8.graph.X xJ = interfaceC3176v00.j();
        boolean z = e;
        if (!z && !abstractC3472z00A.w()) {
            throw new AssertionError();
        }
        if (!z && !c0702m.a(c0702m.a().X1, abstractC3472z00A.b().R()).d()) {
            throw new AssertionError();
        }
        C1033Hc c1033Hc = new C1033Hc(new S10(c2145hI.a(), AbstractC3472z00.b((C0702m<?>) c0702m, WH.b()), xJ), this.d);
        if (z || !c1033Hc.i1()) {
            return c1033Hc;
        }
        throw new AssertionError();
    }
}
