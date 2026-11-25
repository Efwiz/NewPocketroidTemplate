package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.naming.AbstractC3649o0;
import com.huawei.hms.ml.language.common.utils.Constant;

/* JADX INFO: renamed from: com.android.tools.r8.internal.qn, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2875qn extends AbstractC2804pn {
    static final /* synthetic */ boolean e = true;
    private final BS c;
    private C0767v2 d;

    C2875qn(BS bs) {
        boolean z = e;
        if (!z && bs == null) {
            throw new AssertionError();
        }
        if (!z && bs.E()) {
            throw new AssertionError();
        }
        this.c = bs;
    }

    public final BS T() {
        return this.c;
    }

    @Override // com.android.tools.r8.internal.AbstractC2826q4, com.android.tools.r8.internal.InterfaceC1348Si
    public final C0767v2 a(C0722p1 c0722p1) {
        if (this.d == null) {
            this.d = this.c.a(c0722p1);
        }
        return this.d;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || C2875qn.class != obj.getClass()) {
            return false;
        }
        return this.c.equals(((C2875qn) obj).c);
    }

    @Override // com.android.tools.r8.internal.AbstractC2826q4, com.android.tools.r8.internal.InterfaceC1348Si
    public final C2875qn f() {
        return this;
    }

    public final int hashCode() {
        return this.c.hashCode();
    }

    public final String toString() {
        return "Initialized(" + this.c.toString() + Constant.AFTER_QUTO;
    }

    @Override // com.android.tools.r8.internal.AbstractC2804pn
    public final BS a(C0702m c0702m) {
        return this.c;
    }

    @Override // com.android.tools.r8.internal.InterfaceC1348Si
    public final Object a(AbstractC1476Wj abstractC1476Wj, AbstractC3649o0 abstractC3649o0) {
        throw new C3405y10("Unexpected InitializedNonNullReferenceFrameTypeWithInterfaces in writer");
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.tools.r8.internal.InterfaceC2782pV
    public final InterfaceC2782pV a(C0702m c0702m, InterfaceC2782pV interfaceC2782pV) {
        if (!equals(interfaceC2782pV)) {
            AbstractC2826q4 abstractC2826q4 = (AbstractC2826q4) interfaceC2782pV;
            abstractC2826q4.getClass();
            if (!(abstractC2826q4 instanceof TH)) {
                if (!(abstractC2826q4 instanceof FK) && !interfaceC2782pV.isPrimitive() && !interfaceC2782pV.r()) {
                    if (!e && !interfaceC2782pV.m()) {
                        throw new AssertionError();
                    }
                    BS bsA = this.c.a(interfaceC2782pV.S().a(c0702m), c0702m);
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
