package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.io, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2258io extends C2332jo {
    final /* synthetic */ C2332jo f;
    final /* synthetic */ C2332jo g;

    C2258io(C2332jo c2332jo) {
        C2332jo c2332jo2 = C2332jo.d;
        this.g = c2332jo;
        this.f = c2332jo2;
    }

    @Override // com.android.tools.r8.internal.C2332jo
    public final boolean a() {
        return this.g.a() || this.f.a();
    }

    @Override // com.android.tools.r8.internal.C2332jo
    public final boolean b() {
        return this.g.b() || this.f.b();
    }

    @Override // com.android.tools.r8.internal.C2332jo
    public final boolean c() {
        return this.g.b() || this.f.b();
    }
}
