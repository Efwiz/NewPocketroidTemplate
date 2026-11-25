package com.android.tools.r8.internal;

import com.android.tools.r8.naming.AbstractC3649o0;

/* JADX INFO: renamed from: com.android.tools.r8.internal.xz, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3400xz implements InterfaceC1001Fz {
    private final String a;

    public C3400xz(String str) {
        this.a = str;
    }

    @Override // com.android.tools.r8.shaking.InterfaceC3680a0
    public final void a(com.android.tools.r8.graph.Q0 q0) {
    }

    @Override // com.android.tools.r8.internal.InterfaceC1285Pz
    public final boolean e() {
        return true;
    }

    @Override // com.android.tools.r8.internal.InterfaceC1285Pz
    public final C3400xz f() {
        return this;
    }

    final boolean a(C1283Px c1283Px, com.android.tools.r8.graph.Y0 y0, AbstractC3649o0 abstractC3649o0) {
        String string = abstractC3649o0.a(y0).toString();
        c1283Px.c(string);
        return !string.equals(this.a);
    }
}
