package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class VH extends ZU {
    static final /* synthetic */ boolean d = true;
    private final WH c;

    private VH(int i, WH wh) {
        super(i);
        if (!d && !wh.f() && !wh.e()) {
            throw new AssertionError();
        }
        this.c = wh;
    }

    static VH a(int i, WH wh, C2005fV c2005fV) {
        if (d || c2005fV != null) {
            return new VH(i, wh);
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.internal.ZU
    final ZU a(int i, C2005fV c2005fV) {
        throw null;
    }

    @Override // com.android.tools.r8.internal.AbstractC1617aV
    public final AbstractC1617aV a(C0702m c0702m, com.android.tools.r8.graph.proto.c cVar, C2005fV c2005fV) {
        com.android.tools.r8.graph.proto.b bVarA = cVar.a(h());
        if (bVarA.c()) {
            com.android.tools.r8.graph.proto.h hVarA = cVar.a(h()).a();
            if (!hVarA.h()) {
                if (d) {
                    return C3493zG.b;
                }
                throw new AssertionError();
            }
            if (hVarA.f().x() && this.c.f()) {
                return P1.b;
            }
            return C3493zG.b;
        }
        if (bVarA instanceof com.android.tools.r8.graph.proto.l) {
            if (!bVarA.b().g().R0()) {
                return this;
            }
            if (this.c.f()) {
                int iH = h();
                c2005fV.getClass();
                return C2018fh.a(iH, 0L, c2005fV);
            }
            int iH2 = h();
            c2005fV.getClass();
            return MH.a(iH2, 0L, c2005fV);
        }
        return c2005fV.a(cVar.b(h()), this.c);
    }

    @Override // com.android.tools.r8.internal.AbstractC1617aV
    public final boolean a(AbstractC1305Qs abstractC1305Qs) {
        S10 s10B = b(abstractC1305Qs);
        AbstractC3472z00 abstractC3472z00W = s10B.w();
        if (!d && !abstractC3472z00W.I()) {
            throw new AssertionError();
        }
        if (abstractC3472z00W.N() == this.c) {
            return true;
        }
        return s10B.b(new VH$$ExternalSyntheticLambda0()) && s10B.m().w().N() == this.c;
    }
}
