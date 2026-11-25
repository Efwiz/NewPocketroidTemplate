package com.android.tools.r8.internal;

import java.util.List;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class V2 extends AbstractC2412ko implements InterfaceC1299Qm {
    V2(S10 s10, List list) {
        super(s10, list);
    }

    public S10 J2() {
        return (S10) this.b.get(0);
    }

    public final S10 K2() {
        return (S10) this.b.get(1);
    }

    public abstract V2 L2();

    @Override // com.android.tools.r8.internal.AbstractC2412ko, com.android.tools.r8.internal.InterfaceC3174v
    public final boolean g() {
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean i1() {
        if (!K2().L() || J2().i()) {
            return true;
        }
        AbstractC2412ko abstractC2412ko = J2().b;
        abstractC2412ko.getClass();
        if (!(abstractC2412ko instanceof AG)) {
            return true;
        }
        S10 s10J2 = J2().b.r0().J2();
        if (!s10J2.L()) {
            return true;
        }
        int iN2 = s10J2.n().E().N2();
        int iN3 = K2().n().E().N2();
        return iN2 <= 0 || iN3 < 0 || iN2 <= iN3;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean o1() {
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final V2 s() {
        return this;
    }
}
