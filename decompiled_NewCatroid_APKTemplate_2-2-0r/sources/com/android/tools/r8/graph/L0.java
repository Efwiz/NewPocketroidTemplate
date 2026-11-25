package com.android.tools.r8.graph;

import com.android.tools.r8.dex.C0583u;
import com.android.tools.r8.internal.AbstractC1476Wj;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class L0 extends J0.a implements M0 {
    static final /* synthetic */ boolean i = true;

    private L0(int i2, C0759u2[] c0759u2Arr, B0[] b0Arr) {
        super(i2, c0759u2Arr, b0Arr);
    }

    @Override // com.android.tools.r8.graph.M0
    public final void a(C0702m c0702m, com.android.tools.r8.dex.L l) {
        for (C0759u2 c0759u2 : this.f) {
            if (c0759u2 != null) {
                l.a(c0759u2);
            }
        }
        for (B0 b0 : this.g) {
            b0.a(c0702m, l);
        }
    }

    @Override // com.android.tools.r8.graph.M0
    public final int p0() {
        return (this.f.length * 4) + com.android.tools.r8.utils.Q1.d(this.f.length) + com.android.tools.r8.utils.Q1.d(this.e) + this.g.length + 1;
    }

    @Override // com.android.tools.r8.graph.J0.a, com.android.tools.r8.graph.AbstractC0594a1
    public final void a(com.android.tools.r8.dex.V v) {
        if (!i && !com.android.tools.r8.utils.E0.a(this)) {
            throw new AssertionError();
        }
        v.a(this);
    }

    @Override // com.android.tools.r8.graph.M0
    public final void a(C0583u c0583u, C0645e5 c0645e5, AbstractC1476Wj abstractC1476Wj) {
        c0583u.c(this.e);
        c0583u.c(this.f.length);
        for (C0759u2 c0759u2 : this.f) {
            c0583u.a(c0759u2);
        }
        for (B0 b0 : this.g) {
            if (!B0.b && !b0.w0()) {
                throw new AssertionError();
            }
            b0.a(c0583u, abstractC1476Wj);
        }
        c0583u.a(0);
    }
}
