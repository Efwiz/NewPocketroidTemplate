package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0642e2;
import com.android.tools.r8.graph.C0767v2;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Nh, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1210Nh extends ZD {
    private C0767v2 c;
    private C0767v2 d;

    private C1210Nh() {
    }

    public final ZD a(AbstractC0642e2 abstractC0642e2) {
        this.b = abstractC0642e2;
        return this;
    }

    public final ZD b(AbstractC0642e2 abstractC0642e2) {
        this.a = abstractC0642e2;
        return this;
    }

    public final C1210Nh a(C0767v2 c0767v2) {
        this.c = c0767v2;
        return this;
    }

    public final C1210Nh b(C0767v2 c0767v2) {
        this.d = c0767v2;
        return this;
    }

    public final C1237Oh a() {
        return new C1237Oh((com.android.tools.r8.graph.Y0) this.a, (com.android.tools.r8.graph.Y0) this.b, this.c, this.d);
    }
}
