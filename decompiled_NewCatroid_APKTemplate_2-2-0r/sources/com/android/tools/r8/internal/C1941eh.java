package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;

/* JADX INFO: renamed from: com.android.tools.r8.internal.eh, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1941eh extends ZU {
    static final /* synthetic */ boolean d = true;
    private final boolean c;

    private C1941eh(int i, boolean z) {
        super(i);
        this.c = z;
    }

    static C1941eh a(int i, boolean z, C2005fV c2005fV) {
        if (d || c2005fV != null) {
            return new C1941eh(i, z);
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.internal.ZU
    final ZU a(int i, C2005fV c2005fV) {
        throw null;
    }

    @Override // com.android.tools.r8.internal.AbstractC1617aV
    public final AbstractC1617aV a(C0702m c0702m, com.android.tools.r8.graph.proto.c cVar, C2005fV c2005fV) {
        if (cVar.a(h()).c()) {
            com.android.tools.r8.graph.proto.h hVarA = cVar.a(h()).a();
            if (!hVarA.h()) {
                if (d) {
                    return C3493zG.b;
                }
                throw new AssertionError();
            }
            AbstractC3292wV abstractC3292wVF = hVarA.f();
            abstractC3292wVF.getClass();
            if ((abstractC3292wVF instanceof C2853qV) && abstractC3292wVF.e().F() == this.c) {
                return P1.b;
            }
            return C3493zG.b;
        }
        if (d || !cVar.c(h())) {
            return c2005fV.a(cVar.b(h()), this.c);
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.internal.AbstractC1617aV
    public final boolean a(AbstractC1305Qs abstractC1305Qs) {
        S10 s10M = b(abstractC1305Qs).m();
        return s10M.b(new C1406Ug$$ExternalSyntheticLambda13()) && s10M.q().E().K2() == this.c;
    }
}
