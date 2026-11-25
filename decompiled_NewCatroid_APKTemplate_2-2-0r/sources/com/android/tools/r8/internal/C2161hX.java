package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;

/* JADX INFO: renamed from: com.android.tools.r8.internal.hX, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2161hX implements InterfaceC2390kX {
    static final /* synthetic */ boolean b = true;
    private final String a;

    C2161hX(String str) {
        this.a = str;
    }

    @Override // com.android.tools.r8.internal.InterfaceC2390kX
    public final void a(C0702m c0702m, C2409kl c2409kl, C2561ml c2561ml, AbstractC2412ko abstractC2412ko, QX qx) {
        if (!b && !qx.a(abstractC2412ko, abstractC2412ko.T0())) {
            throw new AssertionError();
        }
        c2561ml.b(c0702m, c2409kl, c0702m.a().c(this.a));
    }
}
