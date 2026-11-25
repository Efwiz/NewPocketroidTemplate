package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.vX, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3217vX extends IX implements InterfaceC3511zX, GX {
    private final C2115gt e;
    private C3363xX f;
    private String g;
    private S10 h;

    private C3217vX(C2115gt c2115gt) {
        super();
        this.e = c2115gt;
    }

    @Override // com.android.tools.r8.internal.GX
    public final AbstractC2412ko a() {
        return this.e;
    }

    @Override // com.android.tools.r8.internal.InterfaceC3511zX
    public final boolean b() {
        return this.g != null;
    }

    @Override // com.android.tools.r8.internal.InterfaceC3511zX
    public final boolean c() {
        return this.h != null;
    }

    @Override // com.android.tools.r8.internal.InterfaceC3511zX
    public final S10 d() {
        return this.h;
    }

    @Override // com.android.tools.r8.internal.InterfaceC3511zX
    public final String e() {
        return this.g;
    }

    @Override // com.android.tools.r8.internal.InterfaceC3511zX
    public final C3363xX f() {
        return this.f;
    }

    @Override // com.android.tools.r8.internal.IX
    public final C3217vX g() {
        return this;
    }

    @Override // com.android.tools.r8.internal.IX
    final InterfaceC3511zX j() {
        return this;
    }

    @Override // com.android.tools.r8.internal.IX
    public final GX l() {
        return this;
    }

    @Override // com.android.tools.r8.internal.InterfaceC3511zX
    public final void a(String str) {
        this.g = str;
    }

    @Override // com.android.tools.r8.internal.InterfaceC3511zX
    public final void a(C3363xX c3363xX) {
        this.f = c3363xX;
    }

    @Override // com.android.tools.r8.internal.InterfaceC3511zX
    public final void a(S10 s10) {
        this.h = s10;
    }
}
