package com.android.tools.r8.ir.optimize;

import com.android.tools.r8.internal.AbstractC2251ih;
import com.android.tools.r8.internal.AbstractC2412ko;
import com.android.tools.r8.internal.C2409kl;
import com.android.tools.r8.internal.CE;
import com.android.tools.r8.internal.S10;

/* JADX INFO: renamed from: com.android.tools.r8.ir.optimize.g0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3560g0 extends AbstractC2251ih {
    private final com.android.tools.r8.ir.regalloc.f a;
    private final CE.a b;

    C3560g0(C2409kl c2409kl, com.android.tools.r8.ir.regalloc.f fVar) {
        this.a = fVar;
        this.b = c2409kl.k();
    }

    @Override // com.android.tools.r8.internal.AbstractC2251ih
    protected final boolean a(Object obj, Object obj2) {
        AbstractC2412ko abstractC2412ko = (AbstractC2412ko) obj;
        AbstractC2412ko abstractC2412ko2 = (AbstractC2412ko) obj2;
        return abstractC2412ko.a(abstractC2412ko2, this.a, this.b) && abstractC2412ko.h().n().equals(abstractC2412ko2.h().n());
    }

    @Override // com.android.tools.r8.internal.AbstractC2251ih
    protected final int a(Object obj) {
        AbstractC2412ko abstractC2412ko = (AbstractC2412ko) obj;
        int iA = (abstractC2412ko.b() == null || !abstractC2412ko.b().Y()) ? 0 : this.a.a(abstractC2412ko.b(), abstractC2412ko.W0());
        for (S10 s10 : abstractC2412ko.h1()) {
            iA <<= 4;
            if (s10.Y()) {
                iA += this.a.a(s10, abstractC2412ko.W0());
            }
        }
        return abstractC2412ko.h().n().hashCode() + (iA * 37);
    }
}
