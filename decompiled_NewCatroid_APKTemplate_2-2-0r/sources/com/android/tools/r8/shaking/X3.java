package com.android.tools.r8.shaking;

import com.android.tools.r8.graph.B5;
import com.android.tools.r8.graph.C0702m;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class X3 extends C3707d4 {
    private final L I;

    private X3(C0702m c0702m, L l, B5 b5) {
        super(c0702m, U3.a(l.k()), b5, null);
        this.I = l;
    }

    @Override // com.android.tools.r8.shaking.C3707d4
    final void a(AbstractC3714f abstractC3714f) {
        if (this.I.i().c()) {
            abstractC3714f.getClass();
            if (abstractC3714f instanceof C3702d) {
                this.I.a(abstractC3714f.a().b());
            }
        }
    }
}
