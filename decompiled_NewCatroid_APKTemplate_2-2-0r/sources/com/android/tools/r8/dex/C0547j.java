package com.android.tools.r8.dex;

import com.android.tools.r8.graph.B2;
import com.android.tools.r8.graph.C0633d0;
import com.android.tools.r8.graph.C0640e0;
import com.android.tools.r8.graph.C0658g0;
import com.android.tools.r8.graph.C0663g5;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0800x2;
import com.android.tools.r8.graph.M0;
import com.android.tools.r8.graph.S0;
import com.android.tools.r8.graph.W0;
import com.android.tools.r8.naming.AbstractC3649o0;

/* JADX INFO: renamed from: com.android.tools.r8.dex.j, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C0547j extends V {
    private final AbstractC3649o0 a;

    public C0547j(AbstractC3649o0 abstractC3649o0) {
        this.a = abstractC3649o0;
    }

    @Override // com.android.tools.r8.dex.V
    public final void a(C0729q2 c0729q2, C0640e0 c0640e0) {
    }

    @Override // com.android.tools.r8.dex.V
    public final boolean a(M0 m0) {
        return true;
    }

    @Override // com.android.tools.r8.dex.V
    public final boolean a(W0 w0) {
        return true;
    }

    @Override // com.android.tools.r8.dex.V
    public final boolean a(C0663g5 c0663g5) {
        return true;
    }

    @Override // com.android.tools.r8.dex.V
    public final boolean a(C0729q2 c0729q2) {
        return true;
    }

    @Override // com.android.tools.r8.dex.V
    public final boolean a(C0800x2 c0800x2) {
        return true;
    }

    @Override // com.android.tools.r8.dex.V
    public final boolean a(C0658g0 c0658g0) {
        c0658g0.a(this.a);
        return true;
    }

    @Override // com.android.tools.r8.dex.V
    public final boolean a(C0633d0 c0633d0) {
        c0633d0.b.t0();
        return true;
    }

    @Override // com.android.tools.r8.dex.V
    public final boolean a(S0 s0) {
        for (B2 b2 : s0.a) {
            b2.i1();
        }
        return true;
    }
}
