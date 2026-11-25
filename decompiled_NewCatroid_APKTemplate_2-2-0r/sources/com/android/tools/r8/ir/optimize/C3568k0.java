package com.android.tools.r8.ir.optimize;

import com.android.tools.r8.internal.AbstractC2412ko;
import com.android.tools.r8.internal.C3492zF;
import com.android.tools.r8.internal.S10;
import java.util.HashSet;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.ir.optimize.k0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3568k0 {
    private final HashSet a = new HashSet();
    private final com.android.tools.r8.ir.regalloc.f b;

    C3568k0(com.android.tools.r8.ir.regalloc.f fVar) {
        this.b = fVar;
    }

    public final boolean a(AbstractC2412ko abstractC2412ko) {
        if (abstractC2412ko.l2()) {
            C3492zF c3492zFN0 = abstractC2412ko.n0();
            int iB = this.b.b(c3492zFN0.K2(), c3492zFN0.W0());
            int iA = this.b.a(c3492zFN0.J2(), c3492zFN0.W0());
            if (iB == iA) {
                return true;
            }
            for (C3492zF c3492zF : this.a) {
                int iB2 = this.b.b(c3492zF.K2(), c3492zF.W0());
                int iA2 = this.b.a(c3492zF.J2(), c3492zF.W0());
                if (iB2 == iB && iA2 == iA) {
                    return true;
                }
                if (iA2 == iB && iB2 == iA) {
                    if (!c3492zFN0.H2().b()) {
                        return true;
                    }
                    if (iB != iA + 1 && iB + 1 != iA) {
                        return true;
                    }
                }
            }
        }
        if (abstractC2412ko.b() != null && abstractC2412ko.b().Y()) {
            final S10 s10B = abstractC2412ko.b();
            final int iA3 = this.b.a(s10B, abstractC2412ko.W0());
            this.a.removeIf(new Predicate() { // from class: com.android.tools.r8.ir.optimize.k0$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return this.f$0.a(s10B, iA3, (C3492zF) obj);
                }
            });
        }
        if (!abstractC2412ko.l2()) {
            return false;
        }
        this.a.add(abstractC2412ko.n0());
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean a(S10 s10, int i, C3492zF c3492zF) {
        int iB = this.b.b(c3492zF.K2(), c3492zF.W0());
        int iA = this.b.a(c3492zF.J2(), c3492zF.W0());
        for (int i2 = 0; i2 < s10.e0(); i2++) {
            for (int i3 = 0; i3 < c3492zF.b().e0(); i3++) {
                int i4 = i + i2;
                if (i4 == iA + i3 || i4 == iB + i3) {
                    return true;
                }
            }
        }
        return false;
    }
}
