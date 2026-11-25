package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Mw, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1198Mw extends AbstractC0998Fw {
    private AbstractC2450lF f;
    private AbstractC2450lF g;
    private AbstractC2450lF h;
    private AbstractC2450lF i;
    private AbstractC2450lF j;

    private C1198Mw() {
        this.f = AbstractC2450lF.a();
        this.g = AbstractC2450lF.a();
        this.h = AbstractC2450lF.a();
        this.i = AbstractC2450lF.a();
        this.j = AbstractC2450lF.a();
    }

    public final void d(boolean z) {
        this.i = AbstractC2450lF.a(z);
    }

    @Override // com.android.tools.r8.internal.AbstractC0998Fw
    public final AbstractC0998Fw e() {
        return this;
    }

    public final C1225Nw f() {
        C1225Nw c1225Nw = new C1225Nw(a(), c(), b(), this.f, this.g, this.h, this.i, d(), this.j);
        return c1225Nw.f() ? C1225Nw.l : c1225Nw;
    }

    public final void g(boolean z) {
        this.j = AbstractC2450lF.a(z);
    }

    public final void h(boolean z) {
        this.f = AbstractC2450lF.a(z);
    }

    public final void e(boolean z) {
        this.g = AbstractC2450lF.a(z);
    }

    public final void f(boolean z) {
        this.h = AbstractC2450lF.a(z);
    }
}
