package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0767v2;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Ji, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1103Ji {
    private C0767v2 a;
    private C0684j2 b;
    private C0684j2 c;
    private C0767v2 d;
    private C0684j2 e;
    private EnumC1953et f;
    private boolean g;

    public C1103Ji(C0684j2 c0684j2) {
        this.b = c0684j2;
        this.c = c0684j2;
    }

    public final C1103Ji a(C0767v2 c0767v2) {
        this.a = c0767v2;
        return this;
    }

    public final C1103Ji b(C0767v2 c0767v2) {
        this.d = c0767v2;
        return this;
    }

    public final C1103Ji a(C0684j2 c0684j2) {
        this.c = c0684j2;
        return this;
    }

    public final C1103Ji b(C0684j2 c0684j2) {
        this.e = c0684j2;
        return this;
    }

    public final C1103Ji a(EnumC1953et enumC1953et) {
        this.f = enumC1953et;
        return this;
    }

    public final void a(boolean z) {
        this.g = z;
    }

    public final C1131Ki a(AbstractC1915eM.c cVar) {
        return new C1131Ki(this.a, this.b, this.c, this.d, this.e, this.f, cVar, this.g);
    }
}
