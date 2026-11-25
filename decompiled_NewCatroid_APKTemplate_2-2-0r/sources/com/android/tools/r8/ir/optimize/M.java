package com.android.tools.r8.ir.optimize;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.internal.AbstractC1639am;
import com.android.tools.r8.internal.AbstractC2412ko;
import com.android.tools.r8.internal.C1507Xl;
import com.android.tools.r8.internal.C2179hn;
import com.android.tools.r8.internal.C2409kl;
import com.android.tools.r8.internal.C2969s6;
import com.android.tools.r8.internal.C2972s9;
import com.android.tools.r8.internal.C3038t6;
import com.android.tools.r8.internal.C3332x4;
import com.android.tools.r8.internal.InterfaceC2641no;
import com.android.tools.r8.internal.ML;
import com.android.tools.r8.internal.QW;
import com.android.tools.r8.internal.S10;
import com.android.tools.r8.internal.Z10;
import com.android.tools.r8.internal.uX$$ExternalSyntheticLambda0;
import com.android.tools.r8.shaking.C3733i;
import com.android.tools.r8.utils.y3;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Queue;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class M {
    static final /* synthetic */ boolean c = true;
    private final C0702m a;
    private final F b;

    public M(C0702m c0702m, F f) {
        this.a = c0702m;
        this.b = f;
    }

    public final F a() {
        return this.b;
    }

    public final void b(C2409kl c2409kl) {
        boolean z = c;
        if (!z && this.b.j(c2409kl)) {
            throw new AssertionError();
        }
        if (!z && a(c2409kl)) {
            throw new AssertionError();
        }
        Z10 z10 = new Z10(this.a, c2409kl);
        for (C3332x4 c3332x4 : c2409kl.d) {
            if (!c && z10.a(c3332x4)) {
                throw new AssertionError();
            }
            for (AbstractC2412ko abstractC2412ko : c3332x4.p()) {
                boolean z2 = c;
                if (!z2 && abstractC2412ko.U1() && abstractC2412ko.d1() && !abstractC2412ko.b().y()) {
                    throw new AssertionError();
                }
                if (!z2) {
                    L lA = abstractC2412ko.a(this.a, c2409kl);
                    lA.getClass();
                    if ((lA instanceof I) && (!abstractC2412ko.d1() || z10.a(abstractC2412ko.b()))) {
                        throw new AssertionError();
                    }
                }
            }
        }
    }

    public final void a(C2409kl c2409kl, y3 y3Var) {
        y3Var.a("Remove dead code");
        this.b.j(c2409kl);
        ArrayDeque arrayDeque = new ArrayDeque();
        while (true) {
            Z10 z10 = new Z10(this.a, c2409kl);
            arrayDeque.addAll(c2409kl.D());
            while (!arrayDeque.isEmpty()) {
                C3332x4 c3332x4 = (C3332x4) arrayDeque.removeLast();
                a(arrayDeque, c2409kl, c3332x4, z10);
                Iterator<ML> it = c3332x4.x().iterator();
                while (it.hasNext()) {
                    ML next = it.next();
                    if (z10.a(next)) {
                        it.remove();
                        for (S10 s10 : next.k0()) {
                            s10.c(next);
                            C3332x4 c3332x4H = s10.i() ? s10.l().h() : s10.b.a1() ? s10.b.h() : null;
                            if (c3332x4H != null) {
                                arrayDeque.add(c3332x4H);
                            }
                        }
                    }
                }
            }
            if (!this.b.p(c2409kl).a() && !a(c2409kl)) {
                break;
            }
        }
        if (!c && !c2409kl.b(this.a)) {
            throw new AssertionError();
        }
        y3Var.c();
    }

    private void a(Queue queue, C2409kl c2409kl, C3332x4 c3332x4, Z10 z10) {
        C3332x4 c3332x4H;
        C3332x4 c3332x4H2;
        InterfaceC2641no interfaceC2641noA = c3332x4.a(c2409kl, c3332x4.p().size());
        while (interfaceC2641noA.hasPrevious()) {
            AbstractC2412ko abstractC2412koPrevious = interfaceC2641noA.previous();
            if (abstractC2412koPrevious.d1()) {
                if (abstractC2412koPrevious.w1()) {
                    C2972s9 c2972s9Y = abstractC2412koPrevious.y();
                    if (!c2972s9Y.a(this.a.Y()) && c2972s9Y.b().s() == c2972s9Y.e().s()) {
                        c2972s9Y.b().g(c2972s9Y.e());
                        c2972s9Y.e().g0().forEach(new uX$$ExternalSyntheticLambda0());
                    }
                }
                if (abstractC2412koPrevious.U1() && !abstractC2412koPrevious.b().S()) {
                    abstractC2412koPrevious.f(null);
                }
                if ((abstractC2412koPrevious instanceof QW) && !abstractC2412koPrevious.b().S() && this.a.f().i()) {
                    final com.android.tools.r8.utils.N n = new com.android.tools.r8.utils.N();
                    if (interfaceC2641noA.a(this.a.i0(), c2409kl, abstractC2412koPrevious.F0().getField().B0(), new Consumer() { // from class: com.android.tools.r8.ir.optimize.M$$ExternalSyntheticLambda0
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            n.a((C2179hn) obj);
                        }
                    })) {
                        if (n.c()) {
                            abstractC2412koPrevious = interfaceC2641noA.previous();
                            if (!c && abstractC2412koPrevious != n.a()) {
                                throw new AssertionError();
                            }
                        } else {
                            continue;
                        }
                    }
                }
            }
            L lA = abstractC2412koPrevious.a(this.a, c2409kl);
            lA.getClass();
            if (!(lA instanceof J)) {
                if (lA instanceof K) {
                    Iterator it = lA.b().iterator();
                    while (true) {
                        if (it.hasNext()) {
                            if (!z10.a((S10) it.next())) {
                            }
                        }
                    }
                }
                S10 s10B = abstractC2412koPrevious.b();
                if (s10B == null || z10.a(s10B)) {
                    for (S10 s10 : (ArrayList) abstractC2412koPrevious.h1()) {
                        if (s10.i()) {
                            c3332x4H2 = s10.l().h();
                        } else {
                            c3332x4H2 = s10.b.a1() ? s10.b.h() : null;
                        }
                        if (c3332x4H2 != null) {
                            ((ArrayDeque) queue).add(c3332x4H2);
                        }
                    }
                    for (S10 s11 : abstractC2412koPrevious.S0()) {
                        if (s11.i()) {
                            c3332x4H = s11.l().h();
                        } else {
                            c3332x4H = s11.b.a1() ? s11.b.h() : null;
                        }
                        if (c3332x4H != null) {
                            ((ArrayDeque) queue).add(c3332x4H);
                        }
                    }
                    if (s10B != null) {
                        s10B.d();
                    }
                    interfaceC2641noA.s();
                }
            }
        }
    }

    private boolean a(C2409kl c2409kl) {
        C0729q2 c0729q2A;
        boolean z = false;
        for (C3332x4 c3332x4 : c2409kl.d) {
            if (c3332x4.D()) {
                if (c3332x4.a()) {
                    if (this.a.q()) {
                        C3733i c3733iO = this.a.f().o();
                        C1507Xl c1507XlG = AbstractC1639am.g();
                        C3038t6 c3038t6N = c3332x4.n();
                        for (int i = 0; i < c3038t6N.size(); i++) {
                            C0767v2 c0767v2 = (C0767v2) c3038t6N.b().get(i);
                            C3332x4 c3332x5 = (C3332x4) c3038t6N.a().get(i);
                            int i2 = 0;
                            while (true) {
                                if (i2 < i) {
                                    if (this.a.a(c0767v2, (C0767v2) c3038t6N.b().get(i2)).d()) {
                                        c1507XlG.a(new C2969s6(c0767v2, c3332x5));
                                        break;
                                    }
                                    i2++;
                                } else {
                                    if (c3733iO != null && (c0729q2A = C0729q2.a(this.a.c(c0767v2))) != null && !c3733iO.b(c0729q2A)) {
                                        c1507XlG.a(new C2969s6(c0767v2, c3332x5));
                                        break;
                                    }
                                    break;
                                }
                            }
                        }
                        AbstractC1639am<C2969s6> abstractC1639amA = c1507XlG.a();
                        if (!abstractC1639amA.isEmpty()) {
                            for (C2969s6 c2969s6 : abstractC1639amA) {
                                ((C3332x4) c2969s6.b).b(c2969s6.a);
                            }
                            z = true;
                        }
                    }
                } else {
                    Iterator it = c3332x4.n().c().iterator();
                    while (it.hasNext()) {
                        ((C3332x4) it.next()).c0();
                        z = true;
                    }
                }
            }
        }
        if (z) {
            c2409kl.z();
        }
        if (!c) {
            c2409kl.b(this.a, false);
        }
        return z;
    }
}
