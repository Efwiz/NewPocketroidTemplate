package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.rw, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2954rw extends AbstractC0998Fw {
    private AbstractC2450lF f;
    private AbstractC2450lF g;

    private C2954rw() {
        this.f = AbstractC2450lF.a();
        this.g = AbstractC2450lF.a();
    }

    public final void d(boolean z) {
        this.g = AbstractC2450lF.a(z);
    }

    @Override // com.android.tools.r8.internal.AbstractC0998Fw
    public final AbstractC0998Fw e() {
        return this;
    }

    public final C3023sw f() {
        C3023sw c3023sw = new C3023sw(a(), c(), b(), this.f, this.g, d());
        return c3023sw.f() ? C3023sw.i : c3023sw;
    }

    public final void e(boolean z) {
        this.f = AbstractC2450lF.a(z);
    }
}
