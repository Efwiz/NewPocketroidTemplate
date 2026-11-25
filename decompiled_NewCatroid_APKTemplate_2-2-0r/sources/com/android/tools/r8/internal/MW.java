package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.C0798x1;
import java.util.function.Predicate;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class MW extends JW {
    static final /* synthetic */ boolean c = true;
    private final C1946em a = AbstractC2178hm.e();
    private B1 b;

    MW() {
    }

    @Override // com.android.tools.r8.internal.JW
    public final void a(com.android.tools.r8.graph.T0 t0, B1 b1, C0722p1 c0722p1) {
        B1 b2;
        if (!c0722p1.r4.b(t0, t0.J0())) {
            C0798x1 c0798x1 = c0722p1.r4;
            C0767v2 c0767v2J0 = t0.J0();
            c0798x1.getClass();
            if (C0798x1.a(t0, c0767v2J0) && b1.B() && !b1.p().t().e()) {
                this.a.a(t0.getReference(), b1.p().t());
                return;
            }
            return;
        }
        if (b1.B()) {
            AbstractC3121uK abstractC3121uKT = b1.p().t();
            abstractC3121uKT.getClass();
            if (abstractC3121uKT instanceof C1858dh) {
                if (!c && (b2 = this.b) != null && !b2.equals(b1)) {
                    throw new AssertionError();
                }
                this.b = b1;
                this.a.a(t0.getReference(), b1.p().t());
            }
        }
    }

    @Override // com.android.tools.r8.internal.JW
    public final OW a() {
        AbstractC2178hm abstractC2178hmA = this.a.a();
        if (abstractC2178hmA.isEmpty()) {
            return LW.a;
        }
        if (c || abstractC2178hmA.values().stream().noneMatch(new Predicate() { // from class: com.android.tools.r8.internal.MW$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((AbstractC3121uK) obj).e();
            }
        })) {
            return new NW(abstractC2178hmA);
        }
        throw new AssertionError();
    }
}
