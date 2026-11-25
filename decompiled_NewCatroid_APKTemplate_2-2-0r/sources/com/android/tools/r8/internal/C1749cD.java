package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.utils.InterfaceC3986x0;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.internal.cD, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1749cD {
    static final /* synthetic */ boolean e = true;
    private final IntFunction a;
    private final IntFunction b;
    private AbstractC1476Wj c;
    private Set d;

    private C1749cD(AbstractC1476Wj abstractC1476Wj, IntFunction intFunction, IntFunction intFunction2) {
        this.c = abstractC1476Wj;
        this.a = intFunction;
        this.b = intFunction2;
        this.d = (Set) intFunction2.apply(2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(AbstractC1476Wj abstractC1476Wj, DH dh) {
        return dh == abstractC1476Wj;
    }

    public static C1749cD b(AbstractC1476Wj abstractC1476Wj) {
        return new C1749cD(abstractC1476Wj, new cD$$ExternalSyntheticLambda0(), new ZC$$ExternalSyntheticLambda0());
    }

    public final C1749cD c(AbstractC1476Wj abstractC1476Wj) {
        if (abstractC1476Wj == this.c) {
            return this;
        }
        Set set = (Set) this.b.apply(this.d.size());
        Iterator it = this.d.iterator();
        while (it.hasNext()) {
            set.add(abstractC1476Wj.c(this.c, (C0684j2) it.next()));
        }
        this.d = set;
        this.c = abstractC1476Wj;
        return this;
    }

    public final void d(final AbstractC1476Wj abstractC1476Wj) {
        boolean z = e;
        if (!z && this.c == abstractC1476Wj) {
            throw new AssertionError();
        }
        if (!z && !this.c.i()) {
            throw new AssertionError();
        }
        if (!z && !abstractC1476Wj.h() && this.c.d().b(new Predicate() { // from class: com.android.tools.r8.internal.cD$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return C1749cD.a(abstractC1476Wj, (DH) obj);
            }
        }) == null) {
            throw new AssertionError();
        }
    }

    public static C1749cD a(AbstractC1476Wj abstractC1476Wj) {
        return new C1749cD(abstractC1476Wj, new cD$$ExternalSyntheticLambda0(), new ZC$$ExternalSyntheticLambda1());
    }

    public final boolean b(AbstractC1476Wj abstractC1476Wj, com.android.tools.r8.graph.o5 o5Var) {
        boolean z = e;
        if (z || z || this.c == abstractC1476Wj) {
            return this.d.contains(o5Var.getReference());
        }
        throw new AssertionError();
    }

    public final void a(com.android.tools.r8.graph.o5 o5Var) {
        this.d.add(o5Var.getReference());
    }

    public final void a(AbstractC1476Wj abstractC1476Wj, com.android.tools.r8.graph.o5 o5Var) {
        boolean z = e;
        if (!z && !z && this.c != abstractC1476Wj) {
            throw new AssertionError();
        }
        this.d.add(o5Var.getReference());
    }

    public final boolean b() {
        return this.d.isEmpty();
    }

    public final void a() {
        this.d.clear();
    }

    public final void a(C1749cD c1749cD) {
        AbstractC1476Wj abstractC1476Wj = c1749cD.c;
        if (this.c == abstractC1476Wj) {
            this.d.addAll(c1749cD.d);
            return;
        }
        if (!e) {
            d(abstractC1476Wj);
        }
        Iterator it = c1749cD.d.iterator();
        while (it.hasNext()) {
            this.d.add(this.c.c(c1749cD.c, (C0684j2) it.next()));
        }
    }

    public final void a(C0684j2 c0684j2) {
        this.d.remove(c0684j2);
    }

    public final void a(AbstractC1476Wj abstractC1476Wj, C0684j2 c0684j2) {
        boolean z = e;
        if (!z && !this.d.isEmpty() && !z && this.c != abstractC1476Wj) {
            throw new AssertionError();
        }
        this.d.remove(c0684j2);
    }

    public final C1749cD a(Set set) {
        set.forEach(new Consumer() { // from class: com.android.tools.r8.internal.cD$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((C0684j2) obj);
            }
        });
        return this;
    }

    public final C1749cD a(final C0702m c0702m, final Predicate predicate) {
        this.d.removeIf(new Predicate() { // from class: com.android.tools.r8.internal.cD$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return C1749cD.a(c0702m, predicate, (C0684j2) obj);
            }
        });
        return this;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(com.android.tools.r8.graph.Q0 q0, Predicate predicate, C0684j2 c0684j2) {
        com.android.tools.r8.graph.o5 o5VarA = c0684j2.a(C0729q2.a(q0.c(c0684j2.B0())));
        if (o5VarA == null) {
            if (e) {
                return true;
            }
            throw new AssertionError();
        }
        return predicate.test(o5VarA);
    }

    public final CN a(C0702m c0702m) {
        CN cn = (CN) this.a.apply(this.d.size());
        Iterator it = this.d.iterator();
        while (it.hasNext()) {
            C0684j2 c0684j2C = c0702m.E().c(this.c, (C0684j2) it.next());
            C0729q2 c0729q2A0 = c0702m.c(c0684j2C.e).a0();
            com.android.tools.r8.graph.W0 w0C = c0729q2A0.c(c0684j2C);
            cn.getClass();
            cn.add(new com.android.tools.r8.graph.o5(c0729q2A0, w0C));
        }
        return cn;
    }

    public final void a(InterfaceC3986x0 interfaceC3986x0) {
        ArrayList arrayList = new ArrayList(this.d);
        arrayList.sort(new JV$$ExternalSyntheticLambda3());
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            interfaceC3986x0.a(((C0684j2) it.next()).r0());
        }
    }
}
