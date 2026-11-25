package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import java.util.Set;

/* JADX INFO: renamed from: com.android.tools.r8.internal.bQ, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1692bQ extends AbstractC3424yG {
    static final /* synthetic */ boolean p = true;
    private final C0702m n;
    private final Set o;

    private C1692bQ(C0702m c0702m, Set set) {
        super(c0702m, AbstractC3424yG.j, AbstractC3424yG.k, AbstractC3424yG.l);
        this.n = c0702m;
        this.o = set;
    }

    static C1612aQ m() {
        return new C1612aQ();
    }

    @Override // com.android.tools.r8.internal.AbstractC3424yG, com.android.tools.r8.internal.DH
    public final KE a(KE ke, C0684j2 c0684j2) {
        if (ke.f() != EnumC1953et.c || !this.o.contains(ke.b())) {
            return ke;
        }
        boolean z = p;
        if (!z) {
            C0684j2 c0684j3 = (C0684j2) this.n.E().a((C0684j2) ke.b(), c0684j2, EnumC1953et.g, (AbstractC1476Wj) null).b();
            AbstractC0727q0 abstractC0727q0C = this.n.c(c0684j3.e);
            if (!z && abstractC0727q0C == null) {
                throw new AssertionError();
            }
            com.android.tools.r8.graph.W0 w0D = abstractC0727q0C.d(c0684j3);
            if (!z && w0D == null) {
                throw new AssertionError();
            }
            if (!z && !w0D.getAccessFlags().m()) {
                throw new AssertionError();
            }
        }
        return ((JE) KE.d().b((C0684j2) ke.b())).a(ke.e()).a(EnumC1953et.g).a();
    }

    @Override // com.android.tools.r8.internal.AbstractC3424yG, com.android.tools.r8.internal.DH
    protected final C1237Oh b(C1237Oh c1237Oh) {
        return c1237Oh;
    }

    @Override // com.android.tools.r8.internal.AbstractC3424yG
    protected final boolean l() {
        return true;
    }
}
