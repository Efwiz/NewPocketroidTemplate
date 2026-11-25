package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0722p1;

/* JADX INFO: renamed from: com.android.tools.r8.internal.bb, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1703bb extends AbstractC2094ge {
    static final /* synthetic */ boolean f = true;

    public C1703bb(C0722p1 c0722p1, AbstractC1476Wj abstractC1476Wj) {
        super(c0722p1, abstractC1476Wj);
    }

    @Override // com.android.tools.r8.internal.AbstractC2094ge, com.android.tools.r8.internal.AbstractC1476Wj
    /* JADX INFO: renamed from: a */
    public final com.android.tools.r8.graph.Y0 b(AbstractC1476Wj abstractC1476Wj, com.android.tools.r8.graph.Y0 y0) {
        return this != abstractC1476Wj ? k().b((AbstractC1476Wj) null, y0) : y0;
    }

    @Override // com.android.tools.r8.internal.AbstractC2094ge, com.android.tools.r8.internal.AbstractC1476Wj
    /* JADX INFO: renamed from: b */
    public final C0684j2 c(AbstractC1476Wj abstractC1476Wj, C0684j2 c0684j2) {
        return this != abstractC1476Wj ? k().c(abstractC1476Wj, c0684j2) : c0684j2;
    }

    @Override // com.android.tools.r8.internal.AbstractC2094ge, com.android.tools.r8.internal.AbstractC1476Wj
    public final com.android.tools.r8.graph.proto.k d(AbstractC1476Wj abstractC1476Wj, C0684j2 c0684j2) {
        return AbstractC1476Wj.e().d(abstractC1476Wj, c0684j2);
    }

    @Override // com.android.tools.r8.internal.AbstractC2094ge, com.android.tools.r8.internal.AbstractC1476Wj
    public final boolean g() {
        return AbstractC1476Wj.e().g();
    }

    @Override // com.android.tools.r8.internal.AbstractC2094ge, com.android.tools.r8.internal.DH
    protected final C1237Oh b(C1237Oh c1237Oh) {
        throw new C3405y10();
    }

    @Override // com.android.tools.r8.internal.DH, com.android.tools.r8.internal.AbstractC1476Wj
    protected final C1237Oh a(com.android.tools.r8.graph.Y0 y0, AbstractC1476Wj abstractC1476Wj, InterfaceC1424Uj interfaceC1424Uj) {
        return AbstractC1476Wj.e().a(y0, abstractC1476Wj, interfaceC1424Uj);
    }

    @Override // com.android.tools.r8.internal.DH, com.android.tools.r8.internal.AbstractC1476Wj
    protected final KE a(C0684j2 c0684j2, C0684j2 c0684j3, EnumC1953et enumC1953et, AbstractC1476Wj abstractC1476Wj, InterfaceC1450Vj interfaceC1450Vj) {
        if (!f && abstractC1476Wj != null && abstractC1476Wj != this) {
            throw new AssertionError();
        }
        AbstractC1476Wj abstractC1476WjE = AbstractC1476Wj.e();
        return abstractC1476WjE.a(c0684j2, c0684j3, enumC1953et, abstractC1476WjE, interfaceC1450Vj);
    }

    @Override // com.android.tools.r8.internal.AbstractC2094ge, com.android.tools.r8.internal.DH
    public final KE a(KE ke, C0684j2 c0684j2) {
        throw new C3405y10();
    }
}
