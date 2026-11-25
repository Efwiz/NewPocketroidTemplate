package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.yX, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3441yX extends IX implements InterfaceC3511zX, GX {
    static final /* synthetic */ boolean i = true;
    private final C1166Ls e;
    private C3363xX f;
    private String g;
    private S10 h;

    private C3441yX(C1166Ls c1166Ls) {
        super();
        this.e = c1166Ls;
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
        if (i || this.h != null) {
            return this.h;
        }
        throw new AssertionError();
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
    public final C3441yX i() {
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

    @Override // com.android.tools.r8.internal.IX
    final boolean v() {
        return true;
    }

    @Override // com.android.tools.r8.internal.IX
    public final boolean w() {
        return true;
    }

    @Override // com.android.tools.r8.internal.InterfaceC3511zX
    public final void a(String str) {
        this.g = str;
    }

    @Override // com.android.tools.r8.internal.InterfaceC3511zX
    public final void a(S10 s10) {
        this.h = s10;
    }

    @Override // com.android.tools.r8.internal.InterfaceC3511zX
    public final void a(C3363xX c3363xX) {
        this.f = c3363xX;
    }

    final boolean a(RX rx) {
        return ((QX) rx).b((AbstractC1332Rs) this.e);
    }
}
