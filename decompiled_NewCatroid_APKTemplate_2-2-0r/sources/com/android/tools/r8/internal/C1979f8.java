package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.C0771w;
import com.android.tools.r8.utils.UnverifiableCfCodeDiagnostic;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.Deque;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.f8, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1979f8 {
    static final /* synthetic */ boolean g = true;
    private final C0702m a;
    private final C0722p1 b;
    private final com.android.tools.r8.graph.o5 c;
    private final com.android.tools.r8.utils.A1 d;
    private final Set e = OU.c();
    private final AN f;

    C1979f8(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var, AN an) {
        this.a = c0702m;
        this.b = c0702m.a();
        this.c = o5Var;
        this.d = c0702m.Y();
        this.f = an;
    }

    final Set a() {
        return this.e;
    }

    final void a(B7 b7, AbstractC2359k7 abstractC2359k7) {
        boolean z = g;
        if (!z) {
            abstractC2359k7.getClass();
            if (abstractC2359k7 instanceof C2325jh) {
                throw new AssertionError();
            }
        }
        abstractC2359k7.getClass();
        if (abstractC2359k7 instanceof C3258w5) {
            return;
        }
        if (!z && !(abstractC2359k7 instanceof C1523Yb)) {
            throw new AssertionError();
        }
        C1523Yb c1523YbE = abstractC2359k7.e();
        b7.getClass();
        if (b7 instanceof D6) {
            a(b7.a(), c1523YbE);
            return;
        }
        if (b7 instanceof C3482z7) {
            a(b7.j(), c1523YbE);
            return;
        }
        if (b7.H()) {
            a(b7.l(), c1523YbE);
            return;
        }
        if (b7.Q() && !(b7 instanceof C3186v8)) {
            a(c1523YbE);
        } else if (b7 instanceof P8) {
            a(b7.u(), c1523YbE);
        }
    }

    private void a(D6 d6, C1523Yb c1523Yb) {
        EnumC2523mE enumC2523mET = d6.T();
        enumC2523mET.getClass();
        if (enumC2523mET == EnumC2523mE.OBJECT) {
            c1523Yb.a(3, new Consumer() { // from class: com.android.tools.r8.internal.f8$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.a((Deque) obj);
                }
            }, this.d);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Deque deque) {
        InterfaceC1348Si interfaceC1348Si = (InterfaceC1348Si) deque.peekFirst();
        InterfaceC1348Si interfaceC1348Si2 = (InterfaceC1348Si) deque.peekLast();
        if (interfaceC1348Si.m()) {
            BS bsA = interfaceC1348Si.S().a(this.a);
            if (bsA.r()) {
                AbstractC3472z00 abstractC3472z00S = bsA.a().S();
                if (interfaceC1348Si2.m()) {
                    a(interfaceC1348Si2.S().a(this.a), abstractC3472z00S);
                    return;
                }
                return;
            }
            if (!g) {
                throw new AssertionError();
            }
            return;
        }
        if (!g) {
            throw new AssertionError();
        }
    }

    private void a(final C3482z7 c3482z7, C1523Yb c1523Yb) {
        c1523Yb.a(new Consumer() { // from class: com.android.tools.r8.internal.f8$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(c3482z7, (InterfaceC3054tM) obj);
            }
        }, this.d);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C3482z7 c3482z7, InterfaceC3054tM interfaceC3054tM) {
        C0767v2 type = c3482z7.getField().getType();
        if (interfaceC3054tM.m()) {
            a(interfaceC3054tM.S().a(this.a), type.c(this.a));
        }
    }

    private void a(final I7 i7, C1523Yb c1523Yb) {
        final C0684j2 c0684j2T = i7.T();
        c1523Yb.a(c0684j2T.a(i7.K()), new Consumer() { // from class: com.android.tools.r8.internal.f8$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(c0684j2T, i7, (Deque) obj);
            }
        }, this.d);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C0684j2 c0684j2, I7 i7, Deque deque) {
        Iterator it = deque.iterator();
        int i = 0;
        while (it.hasNext()) {
            InterfaceC1348Si interfaceC1348Si = (InterfaceC1348Si) it.next();
            C0767v2 c0767v2A = c0684j2.a(i, i7.K());
            if (interfaceC1348Si.m()) {
                a(interfaceC1348Si.S().a(this.a), c0767v2A.c(this.a));
            }
            i++;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(InterfaceC3054tM interfaceC3054tM) {
        C0767v2 c0767v2G = this.c.G();
        if (interfaceC3054tM.m()) {
            a(interfaceC3054tM.S().a(this.a), c0767v2G.c(this.a));
        }
    }

    private void a(C1523Yb c1523Yb) {
        c1523Yb.a(new Consumer() { // from class: com.android.tools.r8.internal.f8$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((InterfaceC3054tM) obj);
            }
        }, this.d);
    }

    private void a(final P8 p8, C1523Yb c1523Yb) {
        c1523Yb.a(new Consumer() { // from class: com.android.tools.r8.internal.f8$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(p8, (InterfaceC3054tM) obj);
            }
        }, this.d);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(P8 p8, InterfaceC3054tM interfaceC3054tM) {
        C0767v2 type = p8.getField().getType();
        if (interfaceC3054tM.m()) {
            a(interfaceC3054tM.S().a(this.a), type.c(this.a));
        }
    }

    private void a(final BS bs, final AbstractC3472z00 abstractC3472z00) {
        if (abstractC3472z00.w()) {
            C1229Oa c1229OaB = abstractC3472z00.b();
            if (c1229OaB.R() != this.b.Z1) {
                return;
            }
            c1229OaB.S().a(new Consumer() { // from class: com.android.tools.r8.internal.f8$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.a(bs, abstractC3472z00, (C0767v2) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(AbstractC3472z00 abstractC3472z00, AbstractC3472z00 abstractC3472z01, C0767v2 c0767v2) {
        AbstractC0727q0 abstractC0727q0C = this.a.c(c0767v2);
        if (abstractC0727q0C == null) {
            return;
        }
        boolean z = g;
        if (!z && !abstractC0727q0C.isInterface()) {
            throw new AssertionError();
        }
        if (abstractC3472z00.b(abstractC3472z01, this.a)) {
            return;
        }
        if (!z) {
            a(abstractC3472z00, abstractC0727q0C);
        }
        this.e.add(abstractC0727q0C);
    }

    final void a(com.android.tools.r8.graph.o5 o5Var, int i, C2325jh c2325jh) {
        if (this.d.r0().b()) {
            this.f.a(o5Var, new UnverifiableCfCodeDiagnostic(o5Var.y(), i, c2325jh.k(), o5Var.getOrigin()));
        }
        this.e.clear();
    }

    final void a(C0771w c0771w) {
        if (this.d.r0().b()) {
            AN an = this.f;
            com.android.tools.r8.graph.o5 o5Var = this.c;
            an.a(o5Var, new UnverifiableCfCodeDiagnostic(o5Var.y(), -1, c0771w.getDiagnosticMessage(), this.c.getOrigin()));
        }
        this.e.clear();
    }

    private void a(AbstractC3472z00 abstractC3472z00, AbstractC0727q0 abstractC0727q0) {
        if (g || this.d.C0().a(this.a, abstractC3472z00, abstractC0727q0)) {
            return;
        }
        throw new AssertionError("Unexpected open interface " + abstractC0727q0.o1() + " (assignment: " + abstractC3472z00 + Constant.AFTER_QUTO);
    }
}
