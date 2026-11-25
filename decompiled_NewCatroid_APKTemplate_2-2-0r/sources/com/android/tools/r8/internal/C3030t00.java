package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0680j;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0767v2;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.t00, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3030t00 {
    static final /* synthetic */ boolean e = true;
    private final boolean a;
    private final C0702m c;
    private int b = 1;
    private final ArrayDeque d = new ArrayDeque();

    public C3030t00(C0702m c0702m, boolean z) {
        this.c = c0702m;
        this.a = z;
    }

    private void a() {
        while (!this.d.isEmpty()) {
            S10 s10 = (S10) this.d.poll();
            AbstractC3472z00 abstractC3472z00W = s10.w();
            AbstractC3472z00 abstractC3472z00C = s10.i() ? s10.l().c(this.c) : s10.b.a(this.c);
            boolean z = e;
            if (!z && !this.a && !abstractC3472z00C.G()) {
                throw new AssertionError();
            }
            if (!z && abstractC3472z00W.G() && !abstractC3472z00C.G()) {
                throw new AssertionError();
            }
            a(s10, abstractC3472z00C);
        }
    }

    public final void b(C2409kl c2409kl) {
        this.b = 2;
        if (!e && !this.d.isEmpty()) {
            throw new AssertionError();
        }
        c2409kl.D().forEach(new t00$$ExternalSyntheticLambda0(this));
        a();
    }

    public final void b(Iterable iterable) {
        a(iterable, 4);
    }

    public final void a(Set set) {
        a(set, 2);
    }

    public final void a(C2409kl c2409kl) {
        this.b = 3;
        if (!e && !this.d.isEmpty()) {
            throw new AssertionError();
        }
        c2409kl.D().forEach(new t00$$ExternalSyntheticLambda0(this));
        a();
    }

    public final void a(Iterable iterable) {
        a(iterable, 3);
    }

    private void a(Iterable iterable, int i) {
        this.b = i;
        if (!e && !this.d.isEmpty()) {
            throw new AssertionError();
        }
        iterable.forEach(new Consumer() { // from class: com.android.tools.r8.internal.t00$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((S10) obj);
            }
        });
        a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(S10 s10) {
        if (!e && s10 == null) {
            throw new AssertionError();
        }
        if (this.d.contains(s10)) {
            return;
        }
        this.d.add(s10);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C3332x4 c3332x4) {
        for (AbstractC2412ko abstractC2412ko : c3332x4.p()) {
            S10 s10B = abstractC2412ko.b();
            if (s10B != null && !abstractC2412ko.m1()) {
                if (abstractC2412ko.c1()) {
                    a(s10B, abstractC2412ko.a(this.c));
                } else {
                    a(s10B);
                }
            }
        }
        Iterator<ML> it = c3332x4.x().iterator();
        while (it.hasNext()) {
            a(it.next());
        }
    }

    private void a(S10 s10, AbstractC3472z00 abstractC3472z00) {
        boolean z = e;
        if (!z && this.b == 1) {
            throw new AssertionError();
        }
        if (s10.w().equals(abstractC3472z00)) {
            return;
        }
        if (!z && this.b == 4) {
            throw new AssertionError();
        }
        abstractC3472z00.getClass();
        if (abstractC3472z00 instanceof G5) {
            return;
        }
        int i = this.b;
        if (i == 2) {
            s10.b(this.c, abstractC3472z00);
        } else {
            if (!z && i != 3) {
                throw new AssertionError();
            }
            s10.a(this.c, abstractC3472z00);
        }
        Iterator<AbstractC2412ko> it = s10.h0().iterator();
        while (it.hasNext()) {
            S10 s10B = it.next().b();
            if (s10B != null) {
                a(s10B);
            }
        }
        Iterator it2 = s10.g0().iterator();
        while (it2.hasNext()) {
            a((ML) it2.next());
        }
    }

    public static C0767v2 a(AbstractC1154Lf abstractC1154Lf, C0684j2 c0684j2, C0702m c0702m) {
        C0767v2 c0767v2D;
        C0767v2 c0767v2B0 = c0684j2.B0();
        AbstractC3472z00 abstractC3472z00A = abstractC1154Lf.a(c0767v2B0.c((C0702m<?>) c0702m));
        if (abstractC3472z00A.w()) {
            C1229Oa c1229OaB = abstractC3472z00A.b();
            C0767v2 c0767v2R = c1229OaB.R();
            if (c0767v2R == c0702m.a().Z1 && (c0767v2D = c1229OaB.S().d()) != null) {
                c0767v2R = c0767v2D;
            }
            if (((C0680j) c0702m.f()).c(c0767v2R, c0767v2B0)) {
                return c0767v2R;
            }
        }
        return c0767v2B0;
    }
}
