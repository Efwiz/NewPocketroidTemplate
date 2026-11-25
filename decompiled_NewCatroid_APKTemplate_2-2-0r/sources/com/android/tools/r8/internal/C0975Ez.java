package com.android.tools.r8.internal;

import com.android.tools.r8.naming.AbstractC3649o0;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Ez, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C0975Ez implements InterfaceC1001Fz {
    private final String a;

    public C0975Ez(String str) {
        this.a = str;
    }

    @Override // com.android.tools.r8.shaking.InterfaceC3680a0
    public final void a(com.android.tools.r8.graph.Q0 q0) {
    }

    @Override // com.android.tools.r8.internal.InterfaceC1285Pz
    public final boolean j() {
        return true;
    }

    @Override // com.android.tools.r8.internal.InterfaceC1285Pz
    public final C0975Ez k() {
        return this;
    }

    public final String u() {
        return this.a;
    }

    final boolean a(C1283Px c1283Px, com.android.tools.r8.graph.Y0 y0, AbstractC3649o0 abstractC3649o0) {
        String string = abstractC3649o0.a(y0).toString();
        c1283Px.d(string);
        return !string.equals(this.a);
    }
}
