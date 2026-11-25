package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;

/* JADX INFO: renamed from: com.android.tools.r8.internal.iI, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC2222iI extends ZU {
    static final /* synthetic */ boolean d = true;
    private final long c;

    AbstractC2222iI(int i, long j) {
        super(i);
        this.c = j;
    }

    @Override // com.android.tools.r8.internal.AbstractC1617aV
    public final AbstractC1617aV a(C0702m c0702m, com.android.tools.r8.graph.proto.c cVar, C2005fV c2005fV) {
        if (!cVar.a(h()).c()) {
            if (d || !cVar.c(h())) {
                return a(cVar.b(h()), c2005fV);
            }
            throw new AssertionError();
        }
        com.android.tools.r8.graph.proto.h hVarA = cVar.a(h()).a();
        if (hVarA.h()) {
            AbstractC3292wV abstractC3292wVF = hVarA.f();
            abstractC3292wVF.getClass();
            return ((abstractC3292wVF instanceof C2853qV) && a(abstractC3292wVF.e().H())) ? P1.b : C3493zG.b;
        }
        if (d) {
            return C3493zG.b;
        }
        throw new AssertionError();
    }

    abstract boolean a(long j);

    final long i() {
        return this.c;
    }

    @Override // com.android.tools.r8.internal.AbstractC1617aV
    public final boolean a(AbstractC1305Qs abstractC1305Qs) {
        S10 s10M = b(abstractC1305Qs).m();
        return s10M.b(new C1406Ug$$ExternalSyntheticLambda13()) && a(s10M.q().E().P2());
    }
}
