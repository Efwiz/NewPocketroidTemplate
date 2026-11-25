package com.android.tools.r8.shaking;

import com.android.tools.r8.graph.m5;
import com.android.tools.r8.graph.o5;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class B0 extends AbstractC3709e0 {
    private final m5 a;
    private final o5 b;
    private final H c;

    B0(m5 m5Var, o5 o5Var, H h) {
        this.a = m5Var;
        this.b = o5Var;
        this.c = h;
    }

    @Override // com.android.tools.r8.shaking.AbstractC3709e0
    public final void a(L l) {
        H h = this.c;
        if (h == null) {
            l.a(this.a, this.b);
        } else if (h == H.a || h == H.c) {
            l.b(this.a, this.b);
        } else {
            l.c(this.a, this.b);
        }
    }
}
