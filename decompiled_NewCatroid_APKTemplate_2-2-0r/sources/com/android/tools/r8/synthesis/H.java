package com.android.tools.r8.synthesis;

import com.android.tools.r8.graph.C0658g0;
import com.android.tools.r8.graph.C0663g5;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0706m3;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0725p4;
import com.android.tools.r8.graph.C0737r2;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.W0;
import com.android.tools.r8.internal.Z8;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class H {
    static final /* synthetic */ boolean p = true;
    private final C0722p1 a;
    private final C0767v2 b;
    private final L.b c;
    private Z8 f;
    private C0759u2 d = null;
    private C0737r2 e = null;
    private G g = null;
    private C0725p4 h = null;
    private C0706m3.g i = C0706m3.g.d();
    private C0658g0 j = C0658g0.t0();
    private C0663g5 k = C0663g5.t0();
    private com.android.tools.r8.androidapi.h l = com.android.tools.r8.androidapi.h.w();
    private com.android.tools.r8.androidapi.h m = com.android.tools.r8.androidapi.h.w();
    private com.android.tools.r8.ir.optimize.info.h n = com.android.tools.r8.ir.optimize.info.d.b;
    private boolean o = true;

    H(AbstractC3847f abstractC3847f) {
        this.a = abstractC3847f.c();
        this.b = abstractC3847f.e();
        this.c = abstractC3847f.d();
    }

    public final H a(C0759u2 c0759u2) {
        C0759u2 c0759u3;
        boolean z = p;
        if (!z && c0759u2 == null) {
            throw new AssertionError();
        }
        if (!z && (c0759u3 = this.d) != null && c0759u3 != c0759u2) {
            throw new AssertionError();
        }
        this.d = c0759u2;
        return this;
    }

    public final H b(com.android.tools.r8.androidapi.h hVar) {
        this.l = hVar;
        return this;
    }

    public final void c() {
        a(this.a.c("m"));
    }

    public final H b() {
        this.o = false;
        return this;
    }

    public final H a(com.android.tools.r8.ir.optimize.info.l lVar) {
        this.n = lVar;
        return this;
    }

    public final H a(C0737r2 c0737r2) {
        this.e = c0737r2;
        return this;
    }

    public final H a(Z8 z8) {
        this.f = z8;
        return this;
    }

    public final H a(G g) {
        this.g = g;
        return this;
    }

    public final H a(C0725p4 c0725p4) {
        this.h = c0725p4;
        return this;
    }

    public final H a(C0706m3.g gVar) {
        this.i = gVar;
        return this;
    }

    public final H a(C0658g0 c0658g0) {
        this.j = c0658g0;
        return this;
    }

    public final H a(C0663g5 c0663g5) {
        this.k = c0663g5;
        return this;
    }

    public final H a(com.android.tools.r8.androidapi.h hVar) {
        this.m = hVar;
        return this;
    }

    final W0 a() {
        G g;
        boolean z = p;
        if (!z && this.d == null) {
            throw new AssertionError();
        }
        C0684j2 c0684j2A = this.a.a(this.b, this.e, this.d);
        C0725p4 c0725p4 = this.h;
        W0 w0A = W0.Y1().a(c0684j2A).a(c0725p4).a(this.i).a(this.j).a(this.k).a((c0725p4.W() || (g = this.g) == null) ? null : g.a(c0684j2A)).a(this.f).b(this.l).a(this.m).a(this.n).a(!this.o, new Consumer() { // from class: com.android.tools.r8.synthesis.H$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((W0.a) obj).c();
            }
        }).a();
        if (z || !this.c.i() || a(w0A, this.c)) {
            return w0A;
        }
        throw new AssertionError();
    }

    H(C0722p1 c0722p1, C0767v2 c0767v2, L.b bVar) {
        this.a = c0722p1;
        this.b = c0767v2;
        this.c = bVar;
    }

    public static boolean a(W0 w0, L.b bVar) {
        if (p || bVar.i()) {
            return w0.E0() && w0.F1() && w0.getAccessFlags().m() && w0.s0().isEmpty() && w0.j1().isEmpty();
        }
        throw new AssertionError();
    }
}
