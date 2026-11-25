package com.android.tools.r8.internal;

import java.util.ArrayList;

/* JADX INFO: renamed from: com.android.tools.r8.internal.te, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3075te extends AbstractC2795pe {
    static final /* synthetic */ boolean c = true;
    ArrayList a = new ArrayList();
    InterfaceC2864qe b = null;

    C3075te() {
    }

    @Override // com.android.tools.r8.internal.AbstractC2795pe
    public final AbstractC2795pe a(InterfaceC3147ue interfaceC3147ue) {
        if (!c && interfaceC3147ue == null) {
            throw new AssertionError();
        }
        this.a.add(interfaceC3147ue);
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC2795pe
    public final AbstractC2795pe a(InterfaceC2864qe interfaceC2864qe) {
        boolean z = c;
        if (!z && this.b != null) {
            throw new AssertionError();
        }
        if (!z && interfaceC2864qe == null) {
            throw new AssertionError();
        }
        this.b = interfaceC2864qe;
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC2795pe
    public final C3224ve a() {
        return new C3005se(this);
    }
}
