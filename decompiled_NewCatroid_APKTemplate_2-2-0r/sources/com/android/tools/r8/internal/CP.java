package com.android.tools.r8.internal;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.function.Predicate;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class CP {
    static final /* synthetic */ boolean f = true;
    private final com.android.tools.r8.graph.o5 a;
    private int b;
    private LinkedList c;
    private LinkedList d;
    private LinkedList e;

    private CP(com.android.tools.r8.graph.o5 o5Var) {
        this.a = o5Var;
    }

    private void b() {
        if (this.c != null) {
            this.c.removeIf(new Predicate() { // from class: com.android.tools.r8.internal.CP$$ExternalSyntheticLambda2
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return this.f$0.b((C3355xP) obj);
                }
            });
        }
    }

    private void c() throws DP {
        LinkedList linkedList = this.c;
        if (linkedList == null || linkedList.isEmpty()) {
            this.e = null;
            this.d = null;
            return;
        }
        Lr lr = new Lr();
        Lr lr2 = new Lr();
        for (C3355xP c3355xP : this.c) {
            if (c3355xP.e()) {
                C3503zP c3503zPD = c3355xP.d();
                c3503zPD.getClass();
                if (c3503zPD instanceof OP) {
                    lr.a(c3355xP.a());
                } else {
                    lr2.a(c3355xP.a() / 32);
                }
            }
        }
        int i = 0;
        if (this.d != null) {
            for (int i2 = 0; i2 < this.d.size(); i2++) {
                AbstractC3433yP abstractC3433yP = (AbstractC3433yP) this.d.get(i2);
                abstractC3433yP.getClass();
                if (abstractC3433yP instanceof C2975sC) {
                    lr2.a(i2);
                }
            }
        }
        C3085to c3085to = new C3085to();
        LinkedList linkedList2 = this.e;
        if (linkedList2 != null) {
            Iterator it = linkedList2.iterator();
            int i3 = 0;
            int i4 = 0;
            while (it.hasNext()) {
                it.next();
                if (lr.f(i3)) {
                    c3085to.b(i3, i3 - i4);
                } else {
                    it.remove();
                    i4++;
                }
                i3++;
            }
            if (!f && !this.e.stream().allMatch(new Predicate() { // from class: com.android.tools.r8.internal.CP$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return CP.a((PP) obj);
                }
            })) {
                throw new AssertionError();
            }
        }
        C3085to c3085to2 = new C3085to();
        LinkedList linkedList3 = this.d;
        if (linkedList3 != null) {
            Iterator it2 = linkedList3.iterator();
            int i5 = 0;
            while (it2.hasNext()) {
                it2.next();
                if (lr2.f(i)) {
                    c3085to2.b(i, i - i5);
                } else {
                    it2.remove();
                    i5++;
                }
                i++;
            }
            for (AbstractC3433yP abstractC3433yP2 : this.d) {
                abstractC3433yP2.getClass();
                if (abstractC3433yP2 instanceof C1205Nd) {
                    throw new DP();
                }
            }
            if (!f && !this.d.stream().noneMatch(new CP$$ExternalSyntheticLambda1())) {
                throw new AssertionError();
            }
        }
        for (C3355xP c3355xP2 : this.c) {
            if (c3355xP2.e()) {
                C3503zP c3503zPD2 = c3355xP2.d();
                c3503zPD2.getClass();
                if (c3503zPD2 instanceof OP) {
                    c3355xP2.a(c3085to.get(c3355xP2.a()));
                } else {
                    int iA = c3355xP2.a();
                    int i6 = iA / 32;
                    int i7 = iA % 32;
                    if (!f && !c3085to2.a(i6)) {
                        throw new AssertionError();
                    }
                    c3355xP2.a((c3085to2.get(i6) * 32) + i7);
                }
            }
        }
    }

    public final void a(int i) {
        this.b = i;
    }

    public final void a(C3355xP c3355xP) {
        if (this.c == null) {
            this.c = new LinkedList();
        }
        this.c.add(c3355xP);
    }

    public final void a(AbstractC3433yP abstractC3433yP) {
        if (this.d == null) {
            this.d = new LinkedList();
        }
        this.d.add(abstractC3433yP);
    }

    public final void a(AbstractC3433yP abstractC3433yP, AbstractC3433yP abstractC3433yP2) {
        if (this.e == null) {
            this.e = new LinkedList();
        }
        this.e.add(new PP(abstractC3433yP, abstractC3433yP2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean b(C3355xP c3355xP) {
        Object objB;
        C3503zP c3503zPD = c3355xP.d();
        c3503zPD.getClass();
        if (c3503zPD instanceof OP) {
            objB = ((PP) this.e.get(c3355xP.a())).b();
        } else {
            objB = (LP) c3355xP.c().get(0);
        }
        objB.getClass();
        return objB instanceof C1205Nd;
    }

    public final EP a() throws DP {
        b();
        c();
        return new EP(this.a, this.b, this.c, this.d, this.e);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(PP pp) {
        return pp.c().noneMatch(new CP$$ExternalSyntheticLambda1());
    }
}
