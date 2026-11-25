package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Nk, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C1213Nk extends AbstractC2656o {
    static final /* synthetic */ boolean i = true;
    private com.android.tools.r8.graph.P2 c;
    private C0767v2 d;
    private C0767v2 e;
    private C0759u2 f;
    private com.android.tools.r8.graph.Y0 g;
    private com.android.tools.r8.graph.Q2 h;

    public C1213Nk(C0722p1 c0722p1) {
        super(c0722p1);
    }

    private boolean e() {
        return this.g != null;
    }

    @Override // com.android.tools.r8.internal.AbstractC3402y0
    protected final void a(int i2) {
        if (!i && e()) {
            throw new AssertionError();
        }
        this.c.a(i2);
    }

    @Override // com.android.tools.r8.internal.AbstractC2656o
    protected final void b(C0767v2 c0767v2) {
        if (!i && e()) {
            throw new AssertionError();
        }
        this.e = c0767v2;
    }

    public final com.android.tools.r8.graph.Y0 c() {
        if (i || e()) {
            return this.g;
        }
        throw new AssertionError();
    }

    public final com.android.tools.r8.graph.Q2 d() {
        if (i || e()) {
            return this.h;
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.internal.AbstractC2656o
    protected final void a(C0759u2 c0759u2) {
        if (!i && e()) {
            throw new AssertionError();
        }
        this.f = c0759u2;
    }

    @Override // com.android.tools.r8.internal.AbstractC2656o
    protected final void b() {
        int i2 = com.android.tools.r8.graph.Q2.f;
        this.c = new com.android.tools.r8.graph.P2();
        this.d = null;
        this.e = null;
        this.f = null;
        this.g = null;
        this.h = null;
    }

    @Override // com.android.tools.r8.internal.AbstractC2656o
    protected final void a() {
        this.g = this.a.a(this.e, this.d, this.f);
        this.h = (com.android.tools.r8.graph.Q2) this.c.a();
    }

    @Override // com.android.tools.r8.internal.AbstractC2656o
    protected final void a(C0767v2 c0767v2) {
        if (!i && e()) {
            throw new AssertionError();
        }
        this.d = c0767v2;
    }
}
