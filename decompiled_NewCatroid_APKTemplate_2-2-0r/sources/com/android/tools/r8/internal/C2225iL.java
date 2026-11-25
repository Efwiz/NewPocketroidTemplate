package com.android.tools.r8.internal;

import com.android.tools.r8.ClassFileConsumer;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0725p4;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.ir.optimize.C3550d0;
import com.android.tools.r8.utils.C3879c3;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.internal.iL, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2225iL extends VK {
    static final /* synthetic */ boolean g = true;
    private QK a;
    private final HashMap b = new HashMap();
    private final HashMap c = new HashMap();
    private final C0702m d;
    private final C0722p1 e;
    private final C3550d0 f;

    public C2225iL(C0702m c0702m) {
        this.d = c0702m;
        this.e = c0702m.a();
        this.f = new C3550d0(c0702m, AbstractC1476Wj.e());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void c(com.android.tools.r8.graph.o5 o5Var) {
        o5Var.e().T1();
    }

    @Override // com.android.tools.r8.internal.VK
    public final void a(com.android.tools.r8.graph.o5 o5Var) {
        this.a.a(this.d, o5Var);
    }

    @Override // com.android.tools.r8.internal.VK
    public final void b(com.android.tools.r8.graph.o5 o5Var) {
        a(o5Var);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(C3012sl c3012sl, C2409kl c2409kl) {
        a(c2409kl);
        c3012sl.a(c2409kl, "IR after outlining (SSA)", (String) null);
        c3012sl.n.a(c2409kl);
        c3012sl.k.j(c2409kl);
        c3012sl.a(c2409kl, com.android.tools.r8.ir.optimize.info.q.b(), com.android.tools.r8.utils.y3.b());
    }

    @Override // com.android.tools.r8.internal.VK
    public final void a(AbstractC1476Wj abstractC1476Wj) {
        boolean z = g;
        if (!z && this.d.E() != abstractC1476Wj) {
            throw new AssertionError();
        }
        if (!z && this.a != null) {
            throw new AssertionError();
        }
        this.a = new QK(abstractC1476Wj);
    }

    @Override // com.android.tools.r8.internal.VK
    public final void a(final C3012sl c3012sl, com.android.tools.r8.ir.optimize.info.o oVar, ExecutorService executorService, com.android.tools.r8.utils.y3 y3Var) {
        boolean z = g;
        if (!z) {
            oVar.b();
        }
        c3012sl.b("Outlining");
        y3Var.a("IR conversion phase 3");
        CN cnB = this.a.b(this.d);
        this.a = null;
        if (!cnB.isEmpty()) {
            TK tkA = TK.a(this.d);
            a(c3012sl, cnB, new Consumer() { // from class: com.android.tools.r8.internal.iL$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.a(c3012sl, (C2409kl) obj);
                }
            }, executorService);
            ArrayList arrayListA = a(tkA);
            c3012sl.a(arrayListA, VE.a(), executorService);
            oVar.c();
            a(c3012sl, cnB, new Consumer() { // from class: com.android.tools.r8.internal.iL$$ExternalSyntheticLambda3
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.b(c3012sl, (C2409kl) obj);
                }
            }, executorService);
            oVar.c();
            if (!z) {
                for (ZK zk : this.c.keySet()) {
                    if (!g && !((List) this.b.get(zk)).isEmpty()) {
                        throw new AssertionError((List) this.b.get(zk));
                    }
                }
            }
            arrayListA.forEach(new Consumer() { // from class: com.android.tools.r8.internal.iL$$ExternalSyntheticLambda4
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    C2225iL.c((com.android.tools.r8.graph.o5) obj);
                }
            });
            tkA.b(this.d);
        }
        y3Var.c();
    }

    @Override // com.android.tools.r8.internal.VK
    public final void b() {
        this.a.a(this.d.E());
    }

    public final void b(final C2409kl c2409kl) {
        final com.android.tools.r8.graph.o5 o5VarH = c2409kl.h();
        boolean z = g;
        if (!z) {
            com.android.tools.r8.graph.W wA1 = o5VarH.e().a1();
            wA1.getClass();
            if (wA1 instanceof C1606aL) {
                throw new AssertionError();
            }
        }
        if (!z) {
            if (C1121Ka.a(this.d, o5VarH.a())) {
                throw new AssertionError();
            }
        }
        a(this.d, c2409kl, new Consumer() { // from class: com.android.tools.r8.internal.iL$$ExternalSyntheticLambda8
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(o5VarH, c2409kl, (List) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(C3012sl c3012sl, C2409kl c2409kl) {
        c3012sl.a(c2409kl, "IR before outlining (SSA)", (String) null);
        b(c2409kl);
    }

    private void a(final C3012sl c3012sl, CN cn, final Consumer consumer, ExecutorService executorService) {
        if (!g && this.d.Y().V0) {
            throw new AssertionError();
        }
        C3879c3.a(cn, new Consumer() { // from class: com.android.tools.r8.internal.iL$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(c3012sl, consumer, (com.android.tools.r8.graph.o5) obj);
            }
        }, executorService);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C3012sl c3012sl, Consumer consumer, com.android.tools.r8.graph.o5 o5Var) {
        C2409kl c2409klA = o5Var.a(this.d);
        boolean z = g;
        if (!z && c2409klA == null) {
            throw new AssertionError();
        }
        if (!z) {
            com.android.tools.r8.graph.W wA1 = o5Var.e().a1();
            wA1.getClass();
            if (wA1 instanceof C1606aL) {
                throw new AssertionError();
            }
        }
        c3012sl.k.j(c2409klA);
        c3012sl.D.a(c2409klA, com.android.tools.r8.utils.y3.b());
        com.android.tools.r8.ir.optimize.F.b((C0702m<?>) this.d, c2409klA);
        consumer.accept(c2409klA);
    }

    @Override // com.android.tools.r8.internal.VK
    public final void a(final C2409kl c2409kl, com.android.tools.r8.utils.y3 y3Var) {
        if (this.a == null) {
            return;
        }
        final com.android.tools.r8.graph.o5 o5VarH = c2409kl.h();
        if (!g) {
            com.android.tools.r8.graph.W wA1 = o5VarH.e().a1();
            wA1.getClass();
            if (wA1 instanceof C1606aL) {
                throw new AssertionError();
            }
        }
        if (C1121Ka.a(this.d, o5VarH.a())) {
            return;
        }
        y3Var.a("Collect outlines");
        final ArrayList arrayList = new ArrayList();
        a(this.d, c2409kl, new Consumer() { // from class: com.android.tools.r8.internal.iL$$ExternalSyntheticLambda7
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(o5VarH, c2409kl, arrayList, (List) obj);
            }
        });
        this.a.a(this.d, o5VarH, arrayList);
        y3Var.c();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(com.android.tools.r8.graph.o5 o5Var, C2409kl c2409kl, List list, List list2) {
        new C1834dL(this, o5Var, list2, list).a();
    }

    /* JADX WARN: Code duplicated, block: B:21:0x006b  */
    public static void a(C0702m c0702m, C2409kl c2409kl, Consumer consumer) {
        c0702m.Y().T.getClass();
        int i = c0702m.Y().T.b;
        Set setC = OU.c();
        for (C3332x4 c3332x4 : c2409kl.d) {
            if (setC.add(c3332x4)) {
                C1507Xl c1507XlG = AbstractC1639am.g();
                C3486zB c3486zB = new C3486zB(c2409kl, c3332x4);
                boolean z = false;
                C3332x4 c3332x4H = c3332x4;
                int i2 = 0;
                while (c3486zB.hasNext()) {
                    AbstractC2412ko next = c3486zB.next();
                    if (next.h() != c3332x4) {
                        if (!c3332x4.D() && !next.h().D()) {
                            if (setC.contains(next.h())) {
                                break;
                            }
                            c1507XlG.a(next);
                            i2++;
                            if (i2 <= 100) {
                            }
                            c3332x4H = next.h();
                        } else {
                            c3332x4H = next.h();
                            z = true;
                            break;
                        }
                    } else {
                        c1507XlG.a(next);
                        i2++;
                        if (i2 <= 100 && next.h() != c3332x4H) {
                            break;
                        } else {
                            c3332x4H = next.h();
                        }
                    }
                }
                setC.addAll(c3486zB.a());
                if (z) {
                    if (!g && c3332x4H == c3332x4) {
                        throw new AssertionError();
                    }
                    setC.remove(c3332x4H);
                }
                if (i2 >= i) {
                    consumer.accept(c1507XlG.a());
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(com.android.tools.r8.graph.o5 o5Var, C2409kl c2409kl, List list) {
        new C1994fL(this, o5Var, list).a();
    }

    public final ArrayList a(TK tk) {
        final C1257Pb c1257PbP = this.d.p();
        IdentityHashMap identityHashMap = new IdentityHashMap();
        ArrayList arrayList = new ArrayList();
        if (!g && this.b.isEmpty()) {
            throw new AssertionError();
        }
        ArrayList<ZK> arrayList2 = new ArrayList();
        for (Map.Entry entry : this.b.entrySet()) {
            if (((List) entry.getValue()).size() >= this.d.Y().T.d) {
                arrayList2.add((ZK) entry.getKey());
            }
        }
        arrayList2.sort(Comparator.naturalOrder());
        for (final ZK zk : arrayList2) {
            List list = (List) this.b.get(zk);
            if (!g && list.isEmpty()) {
                throw new AssertionError();
            }
            final com.android.tools.r8.graph.o5 o5Var = (com.android.tools.r8.graph.o5) list.get(0);
            for (int i = 1; i < list.size(); i++) {
                com.android.tools.r8.graph.o5 o5Var2 = (com.android.tools.r8.graph.o5) list.get(i);
                if (o5Var2.getReference().compareTo(o5Var.getReference()) < 0) {
                    o5Var = o5Var2;
                }
            }
            com.android.tools.r8.graph.o5 o5VarB = this.d.D().b(new com.android.tools.r8.synthesis.C() { // from class: com.android.tools.r8.internal.iL$$ExternalSyntheticLambda10
                @Override // com.android.tools.r8.synthesis.C
                public final com.android.tools.r8.synthesis.L.b a(com.android.tools.r8.synthesis.L l) {
                    return l.M;
                }
            }, ((C1230Ob) identityHashMap.computeIfAbsent(o5Var.getReference(), new Function() { // from class: com.android.tools.r8.internal.iL$$ExternalSyntheticLambda9
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return C2225iL.a(c1257PbP, o5Var, (C0684j2) obj);
                }
            })).a(), this.d, new Consumer() { // from class: com.android.tools.r8.internal.iL$$ExternalSyntheticLambda11
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.a(zk, o5Var, (com.android.tools.r8.synthesis.H) obj);
                }
            });
            tk.a(o5VarB, list);
            this.c.put(zk, o5VarB.getReference());
            arrayList.add(o5VarB);
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ C1230Ob a(C1257Pb c1257Pb, com.android.tools.r8.graph.o5 o5Var, C0684j2 c0684j2) {
        return c1257Pb.a(o5Var);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(final ZK zk, com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.synthesis.H h) {
        com.android.tools.r8.synthesis.H hA = h.a(C0725p4.b(9, false));
        if (zk.e == null) {
            zk.e = zk.f.e.b(zk.d, (C0767v2[]) zk.a.toArray(C0767v2.g));
        }
        hA.a(zk.e).b(com.android.tools.r8.androidapi.h.f()).a(com.android.tools.r8.androidapi.h.f()).a(new com.android.tools.r8.synthesis.G() { // from class: com.android.tools.r8.internal.iL$$ExternalSyntheticLambda6
            @Override // com.android.tools.r8.synthesis.G
            public final com.android.tools.r8.graph.W a(C0684j2 c0684j2) {
                return this.f$0.a(zk, c0684j2);
            }
        });
        if (this.d.Y().f instanceof ClassFileConsumer) {
            h.a(o5Var.e().Z0());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ com.android.tools.r8.graph.W a(ZK zk, C0684j2 c0684j2) {
        return new C1606aL(this, zk);
    }

    public final void a(final C2409kl c2409kl) {
        boolean z = g;
        if (!z) {
            com.android.tools.r8.graph.W wA1 = c2409kl.h().e().a1();
            wA1.getClass();
            if (wA1 instanceof C1606aL) {
                throw new AssertionError();
            }
        }
        final Set setC = OU.c();
        final Set setC2 = OU.c();
        a(this.d, c2409kl, new Consumer() { // from class: com.android.tools.r8.internal.iL$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(c2409kl, setC, setC2, (List) obj);
            }
        });
        if (setC.isEmpty()) {
            return;
        }
        if (!z && setC2.isEmpty()) {
            throw new AssertionError();
        }
        final B4 b4S = c2409kl.s();
        while (b4S.hasNext()) {
            final C3332x4 next = b4S.next();
            final InterfaceC2641no interfaceC2641noA = next.a(c2409kl);
            interfaceC2641noA.forEachRemaining(new Consumer() { // from class: com.android.tools.r8.internal.iL$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    C2225iL.a(setC, interfaceC2641noA, setC2, next, c2409kl, b4S, (AbstractC2412ko) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(C2409kl c2409kl, Set set, Set set2, List list) {
        new C1913eL(this, c2409kl, list, set, set2).a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Set set, InterfaceC2641no interfaceC2641no, Set set2, C3332x4 c3332x4, C2409kl c2409kl, ListIterator listIterator, AbstractC2412ko abstractC2412ko) {
        if (set.contains(abstractC2412ko)) {
            interfaceC2641no.v();
        } else if (set2.contains(abstractC2412ko) && c3332x4.D()) {
            interfaceC2641no.a(c2409kl, (ListIterator<C3332x4>) listIterator);
        }
    }
}
