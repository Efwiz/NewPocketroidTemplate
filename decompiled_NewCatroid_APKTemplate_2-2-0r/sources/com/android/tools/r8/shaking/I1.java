package com.android.tools.r8.shaking;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0699l2;
import com.android.tools.r8.graph.C0721p0;
import com.android.tools.r8.graph.C0737r2;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class I1 implements com.android.tools.r8.dex.L {
    final /* synthetic */ K1 a;

    private I1(K1 k1) {
        this.a = k1;
    }

    @Override // com.android.tools.r8.dex.L
    public final boolean a(C0759u2 c0759u2) {
        return false;
    }

    @Override // com.android.tools.r8.dex.L
    public final boolean a(com.android.tools.r8.graph.Y0 y0) {
        this.a.c.accept(y0.e);
        this.a.c.accept(y0.h);
        return false;
    }

    @Override // com.android.tools.r8.dex.L
    public final boolean a(C0684j2 c0684j2) {
        this.a.c.accept(c0684j2.e);
        a(c0684j2.h);
        return false;
    }

    @Override // com.android.tools.r8.dex.L
    public final boolean a(C0737r2 c0737r2) {
        this.a.c.accept(c0737r2.e);
        for (C0767v2 c0767v2 : c0737r2.f.a) {
            this.a.c.accept(c0767v2);
        }
        return false;
    }

    @Override // com.android.tools.r8.dex.L
    public final boolean a(C0767v2 c0767v2) {
        this.a.c.accept(c0767v2);
        return false;
    }

    @Override // com.android.tools.r8.dex.L
    public final boolean a(C0721p0 c0721p0) {
        throw new AssertionError("CallSite are not supported when tracing for legacy multi dex");
    }

    @Override // com.android.tools.r8.dex.L
    public final boolean a(C0699l2 c0699l2) {
        throw new AssertionError("DexMethodHandle are not supported when tracing for legacy multi dex");
    }
}
