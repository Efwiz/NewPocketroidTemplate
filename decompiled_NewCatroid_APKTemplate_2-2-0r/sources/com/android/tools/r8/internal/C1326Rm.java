package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0729q2;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Supplier;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Rm, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1326Rm extends H4 {
    static final /* synthetic */ boolean c = true;
    private final IdentityHashMap a = new IdentityHashMap();
    final /* synthetic */ C1378Tm b;

    public C1326Rm(C1378Tm c1378Tm, Collection collection) {
        this.b = c1378Tm;
        collection.forEach(new Consumer() { // from class: com.android.tools.r8.internal.Rm$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((C0729q2) obj);
            }
        });
    }

    @Override // com.android.tools.r8.internal.H4
    public final void a(Consumer consumer, Object obj) {
        C1352Sm c1352Sm = (C1352Sm) obj;
        c1352Sm.d.forEach(consumer);
        c1352Sm.e.forEach(consumer);
    }

    @Override // com.android.tools.r8.internal.H4
    public final void a(final Consumer consumer) {
        this.a.values().forEach(new Consumer() { // from class: com.android.tools.r8.internal.Rm$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C1326Rm.a(consumer, (InterfaceC0992Fq) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Consumer consumer, InterfaceC0992Fq interfaceC0992Fq) {
        interfaceC0992Fq.values().forEach(consumer);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C0729q2 c0729q2) {
        c0729q2.l(new Consumer() { // from class: com.android.tools.r8.internal.Rm$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((com.android.tools.r8.graph.o5) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(com.android.tools.r8.graph.o5 o5Var) {
        C1678bF c1678bF = this.b.c;
        c1678bF.getClass();
        YE yeA = c1678bF.a(o5Var.getReference());
        if (yeA.d() || yeA.isUnknown()) {
            return;
        }
        C1853dc c1853dcB = yeA.b();
        List listK = c1853dcB.k();
        for (int i = 0; i < listK.size(); i++) {
            AbstractC3122uL abstractC3122uL = (AbstractC3122uL) listK.get(i);
            if (!abstractC3122uL.c() && !(abstractC3122uL instanceof C2582n10)) {
                AbstractC2168hc abstractC2168hcA = abstractC3122uL.a();
                if (abstractC2168hcA.n()) {
                    C1352Sm c1352SmA = a(o5Var, i, c1853dcB);
                    for (RE re : abstractC2168hcA.m()) {
                        com.android.tools.r8.graph.o5 o5VarA = re.b().a(C0729q2.a(this.b.a.c(re.b().B0())));
                        if (o5VarA == null) {
                            if (!c) {
                                this.b.b.a().a(re.b());
                            }
                        } else {
                            C1678bF c1678bF2 = this.b.c;
                            c1678bF2.getClass();
                            YE yeA2 = c1678bF2.a(o5VarA.getReference());
                            if (yeA2.d()) {
                                continue;
                            } else {
                                if (yeA2.isUnknown()) {
                                    Iterator it = c1352SmA.d.iterator();
                                    while (it.hasNext()) {
                                        ((C1352Sm) it.next()).e.remove(c1352SmA);
                                    }
                                    c1352SmA.d.clear();
                                    c1352SmA.a.a(c1352SmA.b, C2582n10.g());
                                    break;
                                }
                                boolean z = c;
                                if (!z && !yeA2.f()) {
                                    throw new AssertionError();
                                }
                                if (!z) {
                                    AbstractC1777cc abstractC1777ccE = yeA2.e();
                                    abstractC1777ccE.getClass();
                                    if (!(abstractC1777ccE instanceof C1853dc)) {
                                        throw new AssertionError();
                                    }
                                }
                                C1352Sm c1352SmA2 = a(o5VarA, re.a(), yeA2.e().b());
                                c1352SmA.getClass();
                                c1352SmA2.e.add(c1352SmA);
                                c1352SmA.d.add(c1352SmA2);
                            }
                        }
                    }
                    AbstractC3122uL abstractC3122uLA = c1352SmA.a.a(c1352SmA.b);
                    abstractC3122uLA.getClass();
                    if (abstractC3122uLA instanceof C2582n10) {
                        continue;
                    } else {
                        if (!c && c1352SmA.a.a(c1352SmA.b) != abstractC2168hcA) {
                            throw new AssertionError();
                        }
                        c1352SmA.a.a(c1352SmA.b, abstractC2168hcA.k());
                    }
                } else {
                    continue;
                }
            }
        }
    }

    private C1352Sm a(final com.android.tools.r8.graph.o5 o5Var, final int i, final C1853dc c1853dc) {
        return (C1352Sm) ((InterfaceC0992Fq) this.a.computeIfAbsent(o5Var.getReference(), com.android.tools.r8.utils.Z1.a(new Supplier() { // from class: com.android.tools.r8.internal.Rm$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return new C1303Qq();
            }
        }))).compute(Integer.valueOf(i), new BiFunction() { // from class: com.android.tools.r8.internal.Rm$$ExternalSyntheticLambda1
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return C1326Rm.a(c1853dc, i, o5Var, (Integer) obj, (C1352Sm) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ C1352Sm a(C1853dc c1853dc, int i, com.android.tools.r8.graph.o5 o5Var, Integer num, C1352Sm c1352Sm) {
        return c1352Sm != null ? c1352Sm : new C1352Sm(c1853dc, i, o5Var.a(i));
    }
}
