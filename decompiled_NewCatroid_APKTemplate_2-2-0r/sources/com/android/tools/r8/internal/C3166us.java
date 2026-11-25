package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0767v2;

/* JADX INFO: renamed from: com.android.tools.r8.internal.us, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3166us {
    private C0862Bm a = AbstractC1048Hm.g();
    private C0862Bm b = AbstractC1048Hm.g();
    private C0862Bm c = AbstractC1048Hm.g();
    private boolean d;
    private boolean e;
    private boolean f;

    C3166us() {
    }

    final C3166us a(C0767v2 c0767v2) {
        this.a.a(c0767v2);
        return this;
    }

    final C3166us b() {
        this.d = true;
        return this;
    }

    final C3166us c() {
        this.e = true;
        return this;
    }

    final C3166us d() {
        this.f = true;
        return this;
    }

    final C3166us a(com.android.tools.r8.graph.Y0 y0) {
        this.b.a(y0);
        return this;
    }

    final C3166us a(AbstractC1332Rs abstractC1332Rs) {
        this.c.a(abstractC1332Rs);
        return this;
    }

    final C3239vs a() {
        return new C3239vs(this.a.a(), this.b.a(), this.c.a(), this.d, this.e, this.f);
    }
}
