package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.utils.AbstractC3938n2;
import com.huawei.hms.ml.language.common.utils.Constant;

/* JADX INFO: renamed from: com.android.tools.r8.internal.qV, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C2853qV extends AbstractC2467lV implements InterfaceC1204Nc {
    static final /* synthetic */ boolean e = true;
    private final long d;

    C2853qV(long j) {
        this.d = j;
    }

    @Override // com.android.tools.r8.internal.B1
    public final boolean D() {
        return this.d == 1;
    }

    public final boolean F() {
        if (!e) {
            long j = this.d;
            if (j != 0 && j != 1) {
                throw new AssertionError();
            }
        }
        return this.d != 0;
    }

    public final int G() {
        return (int) this.d;
    }

    public final long H() {
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

    @Override // com.android.tools.r8.internal.B1, com.android.tools.r8.internal.InterfaceC1204Nc
    public final C2853qV e() {
        return this;
    }

    @Override // com.android.tools.r8.internal.B1
    public final boolean equals(Object obj) {
        return this == obj;
    }

    @Override // com.android.tools.r8.internal.B1
    public final int hashCode() {
        return System.identityHashCode(this);
    }

    @Override // com.android.tools.r8.internal.B1
    public final InterfaceC1204Nc k() {
        return this;
    }

    @Override // com.android.tools.r8.internal.B1
    public final String toString() {
        return "SingleNumberValue(" + this.d + Constant.AFTER_QUTO;
    }

    @Override // com.android.tools.r8.internal.B1
    public final boolean x() {
        return this.d == 0;
    }

    @Override // com.android.tools.r8.internal.B1
    public final boolean z() {
        return x() || D();
    }

    @Override // com.android.tools.r8.internal.InterfaceC1204Nc
    public final boolean a(int i) {
        return i == ((int) this.d);
    }

    @Override // com.android.tools.r8.internal.InterfaceC1204Nc
    public final AbstractC3938n2 a(int[] iArr) {
        int i = (int) this.d;
        int i2 = com.android.tools.r8.utils.B.b;
        boolean z = false;
        for (int i3 : iArr) {
            if (i3 == i) {
                z = true;
                break;
            }
        }
        return AbstractC3938n2.a(z);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.tools.r8.internal.InterfaceC1204Nc
    public final boolean a(InterfaceC1204Nc interfaceC1204Nc) {
        B1 b1 = (B1) interfaceC1204Nc;
        b1.getClass();
        if (b1 instanceof C2853qV) {
            return equals(interfaceC1204Nc.e());
        }
        if (e || interfaceC1204Nc.c()) {
            return interfaceC1204Nc.b().a((int) this.d);
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.internal.AbstractC3292wV
    public final AbstractC2412ko a(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var, C2145hI c2145hI, InterfaceC3176v00 interfaceC3176v00) {
        AbstractC3472z00 abstractC3472z00A = interfaceC3176v00.a();
        com.android.tools.r8.graph.X xJ = interfaceC3176v00.j();
        if (!e && abstractC3472z00A.I() && this.d != 0) {
            throw new AssertionError();
        }
        int iA = c2145hI.a();
        if (abstractC3472z00A.I()) {
            abstractC3472z00A = AbstractC3472z00.m();
        }
        return new C0978Fc(new S10(iA, abstractC3472z00A, xJ), this.d);
    }
}
