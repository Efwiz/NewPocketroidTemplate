package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0735r0;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0737r2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.shaking.AbstractC3735i1;
import com.android.tools.r8.shaking.C3733i;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Jg, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1100Jg {
    static final /* synthetic */ boolean g = true;
    private final C0702m a;
    private final C0982Fg b;
    private final C0722p1 c;
    private C1155Lg d = new C1155Lg();
    private final IdentityHashMap e = new IdentityHashMap();
    private final Set f = OU.c();

    C1100Jg(C0702m c0702m, C0982Fg c0982Fg) {
        this.a = c0702m;
        this.b = c0982Fg;
        this.c = c0702m.a();
    }

    private void b() {
        this.d.a(new Consumer() { // from class: com.android.tools.r8.internal.Jg$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.b((C0729q2) obj);
            }
        });
    }

    final C1155Lg a(AbstractC1476Wj abstractC1476Wj) {
        boolean z;
        if (this.b.h() != null) {
            AbstractC0735r0 abstractC0735r0H = this.b.h();
            abstractC0735r0H.getClass();
            if (!(abstractC0735r0H instanceof com.android.tools.r8.graph.m5)) {
                for (C0729q2 c0729q2 : ((C3733i) this.a.f()).d()) {
                    if (c0729q2.B1()) {
                        this.a.Y().n1.getClass();
                        if (!g && !c0729q2.B1()) {
                            throw new AssertionError();
                        }
                        if (c0729q2.c(this.a)) {
                            z = true;
                        } else {
                            C0982Fg c0982Fg = this.b;
                            C2989sQ c2989sQ = AbstractC3287wQ.f;
                            c0982Fg.getClass();
                            if (c0982Fg.a(c0729q2.getType(), c2989sQ)) {
                                z = false;
                            }
                        }
                        if (a(c0729q2) && z) {
                            this.d.a(this.a, c0729q2, abstractC1476Wj);
                        }
                    }
                }
                Iterator it = this.f.iterator();
                while (it.hasNext()) {
                    this.d.d((C0767v2) it.next());
                }
                for (C0729q2 c0729q3 : ((C3733i) this.a.f()).d()) {
                    if (c0729q3.e.T()) {
                        if (!g && !c0729q3.g.a(this.a.a().f2)) {
                            throw new AssertionError();
                        }
                        for (com.android.tools.r8.graph.W0 w0 : c0729q3.P1()) {
                            if (!g && !w0.l1().isEmpty() && !this.a.Y().n1.A) {
                                throw new AssertionError();
                            }
                            C0767v2 c0767v2B = w0.V1().b(this.a.a());
                            if (this.d.c(c0767v2B) && !this.b.a(c0767v2B, AbstractC3287wQ.b)) {
                                this.d.d(c0767v2B);
                            }
                        }
                    }
                }
                a();
                if (this.a.Y().x1().c()) {
                    this.d.d(this.a.Z().h.n);
                }
                b();
                if (!g) {
                    this.d.f();
                }
                return this.d;
            }
        }
        return this.d;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(C0729q2 c0729q2) {
        this.d.a(c0729q2.getType(), (Set) this.e.getOrDefault(c0729q2.getType(), AbstractC1048Hm.k()));
    }

    private boolean a(C0729q2 c0729q2) {
        boolean z;
        if (!g && !c0729q2.B1()) {
            throw new AssertionError();
        }
        if (c0729q2.f != this.c.e2) {
            C0982Fg c0982Fg = this.b;
            C2989sQ c2989sQ = AbstractC3287wQ.e;
            c0982Fg.getClass();
            if (!c0982Fg.a(c0729q2.getType(), c2989sQ)) {
                return false;
            }
            z = false;
        } else {
            z = true;
        }
        if (c0729q2.w1().size() <= 7) {
            return z;
        }
        C0982Fg c0982Fg2 = this.b;
        C2989sQ c2989sQ2 = AbstractC3287wQ.g;
        c0982Fg2.getClass();
        return !c0982Fg2.a(c0729q2.getType(), c2989sQ2) ? false : false;
    }

    private void a() {
        AbstractC3735i1 abstractC3735i1W = ((C3733i) this.a.f()).w();
        com.android.tools.r8.utils.A1 a1Y = this.a.Y();
        abstractC3735i1W.c(new Consumer() { // from class: com.android.tools.r8.internal.Jg$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((C0767v2) obj);
            }
        }, a1Y);
        abstractC3735i1W.a(new Consumer() { // from class: com.android.tools.r8.internal.Jg$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((com.android.tools.r8.graph.Y0) obj);
            }
        }, a1Y);
        abstractC3735i1W.b(new Consumer() { // from class: com.android.tools.r8.internal.Jg$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((C0684j2) obj);
            }
        }, a1Y);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(com.android.tools.r8.graph.Y0 y0) {
        C0767v2 c0767v2B = y0.h.b(this.c);
        if (c0767v2B != y0.e) {
            a(c0767v2B);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C0684j2 c0684j2) {
        C0737r2 c0737r2 = c0684j2.h;
        C0767v2 c0767v2B = c0737r2.e.b(this.c);
        if (c0767v2B != c0684j2.e) {
            a(c0767v2B);
        }
        for (C0767v2 c0767v2 : c0737r2.f.a) {
            C0767v2 c0767v2B2 = c0767v2.b(this.c);
            if (c0767v2B2 != c0684j2.e) {
                a(c0767v2B2);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C0767v2 c0767v2) {
        if (this.d.c(c0767v2)) {
            this.b.a(c0767v2, AbstractC3287wQ.c);
            this.d.d(c0767v2);
        }
    }
}
