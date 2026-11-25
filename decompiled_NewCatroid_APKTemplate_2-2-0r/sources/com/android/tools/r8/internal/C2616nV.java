package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0744s2;
import com.android.tools.r8.graph.C0702m;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.Objects;

/* JADX INFO: renamed from: com.android.tools.r8.internal.nV, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2616nV extends AbstractC2467lV {
    static final /* synthetic */ boolean f = true;
    private final AbstractC0744s2 d;
    private final AbstractC2525mG e;

    C2616nV(AbstractC0744s2 abstractC0744s2, AbstractC2525mG abstractC2525mG) {
        this.d = abstractC0744s2;
        this.e = abstractC2525mG;
    }

    public final AbstractC0744s2 F() {
        return this.d;
    }

    public final AbstractC2525mG G() {
        return this.e;
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
    /* JADX INFO: renamed from: c */
    public final AbstractC3292wV b(C0702m c0702m, AbstractC1476Wj abstractC1476Wj, AbstractC1476Wj abstractC1476Wj2) {
        C1 c1B = c0702m.b();
        AbstractC0744s2 abstractC0744s2A = abstractC1476Wj.a(this.d, abstractC1476Wj2);
        AbstractC2525mG abstractC2525mG = this.e;
        c1B.getClass();
        return new C2616nV(abstractC0744s2A, abstractC2525mG);
    }

    @Override // com.android.tools.r8.internal.B1
    public final boolean equals(Object obj) {
        if (obj == null || C2616nV.class != obj.getClass()) {
            return false;
        }
        C2616nV c2616nV = (C2616nV) obj;
        return this.d == c2616nV.d && this.e == c2616nV.e;
    }

    @Override // com.android.tools.r8.internal.B1
    public final int hashCode() {
        return Objects.hash(this.d, this.e);
    }

    @Override // com.android.tools.r8.internal.B1
    public final C2616nV o() {
        return this;
    }

    @Override // com.android.tools.r8.internal.B1
    public final String toString() {
        return "DexItemBasedConstString(" + this.d.r0() + Constant.AFTER_QUTO;
    }

    @Override // com.android.tools.r8.internal.AbstractC3292wV
    public final AbstractC2412ko a(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var, C2145hI c2145hI, InterfaceC3176v00 interfaceC3176v00) {
        AbstractC3472z00 abstractC3472z00A = interfaceC3176v00.a();
        com.android.tools.r8.graph.X xJ = interfaceC3176v00.j();
        boolean z = f;
        if (!z && !abstractC3472z00A.w()) {
            throw new AssertionError();
        }
        if (!z && !c0702m.a(c0702m.a().X1, abstractC3472z00A.b().R()).d()) {
            throw new AssertionError();
        }
        return new C2477lf(new S10(c2145hI.a(), AbstractC3472z00.b((C0702m<?>) c0702m, WH.b()), xJ), this.d, this.e);
    }
}
