package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0767v2;

/* JADX INFO: renamed from: com.android.tools.r8.internal.bh, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1709bh extends AbstractC1629ah {
    static final /* synthetic */ boolean b = true;
    private final C0767v2 a;

    C1709bh(C0767v2 c0767v2) {
        if (!b && c0767v2 == null) {
            throw new AssertionError();
        }
        this.a = c0767v2;
    }

    @Override // com.android.tools.r8.internal.AbstractC1629ah
    final C0767v2 a(int i) {
        return this.a;
    }

    @Override // com.android.tools.r8.internal.AbstractC1629ah
    public final boolean equals(Object obj) {
        return (obj instanceof C1709bh) && this.a == ((C1709bh) obj).a;
    }

    public final int hashCode() {
        return this.a.hashCode();
    }

    @Override // com.android.tools.r8.internal.AbstractC1629ah
    final AbstractC1629ah a(AbstractC1476Wj abstractC1476Wj, AbstractC1476Wj abstractC1476Wj2) {
        C0767v2 c0767v2A = abstractC1476Wj.a(abstractC1476Wj2, this.a);
        if (b || c0767v2A.O0()) {
            return new C1709bh(c0767v2A);
        }
        throw new AssertionError();
    }
}
