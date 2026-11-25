package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0717o2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.shaking.C3733i;
import com.android.tools.r8.utils.AbstractC3938n2;
import com.android.tools.r8.utils.C3879c3;
import com.android.tools.r8.utils.InterfaceC3880d;
import com.android.tools.r8.utils.InterfaceC3947p1;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class L2 {
    static final /* synthetic */ boolean i = true;
    private final C0702m a;
    private final C3012sl b;
    private final C1678bF c;
    private final com.android.tools.r8.utils.A1 d;
    private final C2844qM e;
    private final com.android.tools.r8.graph.H3 f;
    private final List g;
    private final BiConsumer h;

    L2(C0702m c0702m, C3012sl c3012sl, com.android.tools.r8.graph.H3 h3, C1678bF c1678bF, C2844qM c2844qM, ArrayList arrayList, BiConsumer biConsumer) {
        this.a = c0702m;
        this.b = c3012sl;
        this.f = h3;
        this.c = c1678bF;
        this.d = c0702m.Y();
        this.e = c2844qM;
        this.g = arrayList;
        this.h = biConsumer;
    }

    final void a(ExecutorService executorService, com.android.tools.r8.utils.y3 y3Var) {
        y3Var.a("Propagate argument information for virtual methods");
        C3879c3.a(this.g, new Consumer() { // from class: com.android.tools.r8.internal.L2$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((Set) obj);
            }
        }, executorService);
        y3Var.c();
        y3Var.a("Solve flow constraints");
        new C1378Tm(this.a, this.b, this.c).b(executorService);
        y3Var.c();
        y3Var.a("Set optimization info");
        a(executorService);
        y3Var.c();
        if (!i && !this.c.a()) {
            throw new AssertionError();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(final Set set) {
        new C1645as(this.a, this.f, this.c, new Consumer() { // from class: com.android.tools.r8.internal.L2$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(set, (AbstractC0717o2) obj);
            }
        }).a(set);
        new C2965s20(this.a, this.f, this.c).a(set);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Set set, AbstractC0717o2 abstractC0717o2) {
        this.h.accept(set, abstractC0717o2);
    }

    private void a(ExecutorService executorService) {
        C3879c3.a(((C3733i) this.a.f()).d(), new Consumer() { // from class: com.android.tools.r8.internal.L2$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((C0729q2) obj);
            }
        }, executorService);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C0729q2 c0729q2) {
        c0729q2.l(new Consumer() { // from class: com.android.tools.r8.internal.L2$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((com.android.tools.r8.graph.o5) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:39:0x00c1  */
    public void a(com.android.tools.r8.graph.o5 o5Var) {
        YE yeA = this.c.a(o5Var, C3480z5.k());
        if (yeA.d()) {
            if (!o5Var.e().q1() || o5Var.e().u1()) {
                return;
            }
            o5Var.d(this.a);
            this.b.b(o5Var);
            this.e.d(this.a.E(), o5Var);
            return;
        }
        if (!this.a.a(o5Var).g(this.d)) {
            yeA = C2508m10.k();
        }
        YE yeA2 = a(o5Var, yeA);
        if (yeA2.isUnknown()) {
            return;
        }
        AbstractC1777cc abstractC1777ccE = yeA2.e();
        abstractC1777ccE.getClass();
        if (abstractC1777ccE instanceof C2247ic) {
            if (!i) {
                throw new AssertionError();
            }
            return;
        }
        C1853dc c1853dcB = abstractC1777ccE.b();
        for (int i2 = 0; i2 < c1853dcB.k().size(); i2++) {
            AbstractC2168hc abstractC2168hcA = c1853dcB.a(i2).a();
            if (abstractC2168hcA != null && (abstractC2168hcA instanceof C1624ac)) {
                AbstractC1154Lf abstractC1154LfQ = abstractC2168hcA.h().q();
                C0767v2 c0767v2A = o5Var.a(i2);
                if (!abstractC1154LfQ.m()) {
                    if (!F20.a(this.a, abstractC1154LfQ, c0767v2A).m()) {
                        AbstractC3472z00 abstractC3472z00C = c0767v2A.c(this.a);
                        if (!abstractC1154LfQ.a(abstractC3472z00C).a(abstractC3472z00C, this.a)) {
                            c1853dcB.a(i2, abstractC2168hcA.a(this.a, new C1624ac(H5.F(), AbstractC1154Lf.n(), Collections.emptySet()), c0767v2A, FW.b(), InterfaceC3880d.a));
                        }
                    } else {
                        c1853dcB.a(i2, abstractC2168hcA.a(this.a, new C1624ac(H5.F(), AbstractC1154Lf.n(), Collections.emptySet()), c0767v2A, FW.b(), InterfaceC3880d.a));
                    }
                }
            }
        }
        if (c1853dcB.m()) {
            return;
        }
        boolean z = i;
        if (!z && !c1853dcB.k().stream().noneMatch(new Predicate() { // from class: com.android.tools.r8.internal.L2$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((AbstractC3122uL) obj).c();
            }
        })) {
            throw new AssertionError();
        }
        if (!z && !c1853dcB.k().stream().filter(new Predicate() { // from class: com.android.tools.r8.internal.L2$$ExternalSyntheticLambda6
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((AbstractC3122uL) obj).d();
            }
        }).map(new Function() { // from class: com.android.tools.r8.internal.L2$$ExternalSyntheticLambda7
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((AbstractC3122uL) obj).a();
            }
        }).noneMatch(new Predicate() { // from class: com.android.tools.r8.internal.L2$$ExternalSyntheticLambda8
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((AbstractC2168hc) obj).n();
            }
        })) {
            throw new AssertionError();
        }
        if (c1853dcB.o() > 0) {
            com.android.tools.r8.ir.optimize.info.r rVarA = com.android.tools.r8.ir.optimize.info.n.a();
            com.android.tools.r8.ir.optimize.info.a aVarA = com.android.tools.r8.ir.optimize.info.b.a(this.a, o5Var, c1853dcB);
            rVarA.getClass();
            com.android.tools.r8.ir.optimize.info.r.a(o5Var, aVarA);
        }
        if (!c1853dcB.n()) {
            com.android.tools.r8.ir.optimize.info.r rVarA2 = com.android.tools.r8.ir.optimize.info.n.a();
            AbstractC3938n2 abstractC3938n2 = AbstractC3938n2.b;
            rVarA2.getClass();
            com.android.tools.r8.ir.optimize.info.r.a(abstractC3938n2, o5Var);
        }
        com.android.tools.r8.ir.optimize.info.h hVarB = o5Var.B();
        if (hVarB.E()) {
            AbstractC3122uL abstractC3122uLA = c1853dcB.a(hVarB.r());
            com.android.tools.r8.ir.optimize.info.r rVarB = com.android.tools.r8.ir.optimize.info.r.b();
            com.android.tools.r8.graph.W0 w0E = o5Var.e();
            C0702m c0702m = this.a;
            rVarB.a(w0E, c0702m, abstractC3122uLA.a(c0702m));
        }
    }

    private YE a(final com.android.tools.r8.graph.o5 o5Var, YE ye) {
        boolean zN;
        List listK;
        boolean z = i;
        if (!z && !ye.h() && !ye.isUnknown()) {
            throw new AssertionError();
        }
        if (!this.a.a(o5Var).g(this.d)) {
            return ye;
        }
        int iH1 = o5Var.e().h1();
        if (ye.h()) {
            C1853dc c1853dcB = ye.b();
            zN = c1853dcB.n();
            listK = c1853dcB.k();
        } else {
            if (!z && !ye.isUnknown()) {
                throw new AssertionError();
            }
            C2582n10 c2582n10G = C2582n10.g();
            ArrayList arrayList = new ArrayList(iH1);
            for (int i2 = 0; i2 < iH1; i2++) {
                arrayList.add(c2582n10G);
            }
            zN = true;
            listK = arrayList;
        }
        List listA = com.android.tools.r8.utils.V1.a(listK, new InterfaceC3947p1() { // from class: com.android.tools.r8.internal.L2$$ExternalSyntheticLambda4
            @Override // com.android.tools.r8.utils.InterfaceC3947p1
            public final Object a(int i3, Object obj) {
                return this.f$0.a(o5Var, i3, (AbstractC3122uL) obj);
            }
        }, (List) null);
        return listA != null ? new C1853dc(listA, zN) : ye;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public AbstractC3122uL a(com.android.tools.r8.graph.o5 o5Var, int i2, AbstractC3122uL abstractC3122uL) {
        return ((o5Var.e().E0() || i2 != 0) && o5Var.a(i2).b(this.a)) ? new C1624ac(this.a.b().a(), AbstractC1154Lf.c(), Collections.emptySet()) : abstractC3122uL;
    }
}
