package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0767v2;
import java.util.Set;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class WP {
    static final /* synthetic */ boolean j = true;
    public final C2076gQ a;
    public final AP b;
    public final C0831Aj c;
    public final C1158Lj d;
    public final C1132Kj e;
    public final C0856Bg f;
    public final C3286wP g;
    public final VP h;
    private Set i = OU.c();

    public WP(C0702m c0702m, VP vp) {
        AP ap = new AP();
        C2076gQ c2076gQ = new C2076gQ(ap, vp);
        this.a = c2076gQ;
        this.b = ap;
        this.c = c0702m.Y().x1().a ? new C0831Aj(c0702m, vp) : null;
        this.d = c0702m.Y().x1().b ? new C1158Lj(c0702m, c2076gQ, vp) : null;
        this.e = c0702m.Y().x1().c ? new C1132Kj(c0702m, vp) : null;
        this.f = c0702m.Y().x1().b() ? new C0856Bg(c0702m, vp) : null;
        this.g = c0702m.Y().x1().a() ? new C3286wP(vp) : null;
        this.h = vp;
    }

    public final Set a() {
        return this.i;
    }

    public final boolean a(com.android.tools.r8.dex.r0 r0Var) {
        for (C0767v2 c0767v2 : this.i) {
            boolean z = j;
            if (!z && r0Var.a(c0767v2.e)) {
                throw new AssertionError();
            }
            if (!z && r0Var.a(c0767v2)) {
                throw new AssertionError();
            }
        }
        return true;
    }

    public final void a(Set set) {
        if (com.android.tools.r8.utils.A1.b()) {
            this.i = set;
            return;
        }
        throw new C3405y10();
    }
}
