package com.android.tools.r8.naming;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.J3;
import com.android.tools.r8.graph.Y0;
import com.android.tools.r8.utils.A1;

/* JADX INFO: renamed from: com.android.tools.r8.naming.m0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3645m0 extends AbstractC3649o0 {
    private C3645m0() {
    }

    @Override // com.android.tools.r8.naming.AbstractC3649o0
    public final String a(String str) {
        return str;
    }

    @Override // com.android.tools.r8.naming.AbstractC3649o0
    public final C0759u2 c(C0767v2 c0767v2) {
        return c0767v2.e;
    }

    @Override // com.android.tools.r8.naming.AbstractC3649o0
    protected final C0759u2 a(C0767v2 c0767v2) {
        return c0767v2.e;
    }

    @Override // com.android.tools.r8.naming.AbstractC3649o0
    public final C0759u2 a(J3 j3, A1 a1) {
        return j3.c();
    }

    @Override // com.android.tools.r8.naming.AbstractC3649o0
    public final C0759u2 a(C0684j2 c0684j2) {
        return c0684j2.f;
    }

    @Override // com.android.tools.r8.naming.AbstractC3649o0
    public final C0759u2 a(Y0 y0) {
        return y0.f;
    }
}
