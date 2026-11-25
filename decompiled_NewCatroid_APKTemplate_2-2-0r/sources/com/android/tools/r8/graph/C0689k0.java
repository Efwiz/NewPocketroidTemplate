package com.android.tools.r8.graph;

import com.android.tools.r8.internal.AbstractC1048Hm;
import com.android.tools.r8.internal.C0862Bm;

/* JADX INFO: renamed from: com.android.tools.r8.graph.k0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C0689k0 {
    private boolean a;
    private boolean b;
    private final C0862Bm c;
    private final C0862Bm d;
    private final C0862Bm e;

    private C0689k0() {
        this.c = AbstractC1048Hm.g();
        this.d = AbstractC1048Hm.g();
        this.e = AbstractC1048Hm.g();
    }

    public final C0689k0 a(boolean z) {
        this.b = z;
        return this;
    }

    public final C0689k0 b(boolean z) {
        this.a = z;
        return this;
    }

    public final void c(C0767v2 c0767v2) {
        synchronized (this.d) {
            this.d.a(c0767v2);
        }
    }

    public final void a(C0767v2 c0767v2) {
        synchronized (this.e) {
            this.e.a(c0767v2);
        }
    }

    public final void b(C0767v2 c0767v2) {
        synchronized (this.c) {
            this.c.a(c0767v2);
        }
    }

    public final C0696l0 a() {
        return new C0696l0(this.a, this.b, this.c.a(), this.d.a(), this.e.a());
    }
}
