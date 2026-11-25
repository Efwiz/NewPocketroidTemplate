package com.android.tools.r8.internal;

import com.android.tools.r8.ClassFileConsumer;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.shaking.C3733i;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.internal.fa, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2010fa {
    static final /* synthetic */ boolean e = true;
    private final C0702m a;
    private final C3387xn b;
    private final C2244ia c;
    private int d = 0;

    C2010fa(C0702m c0702m, C3387xn c3387xn, C2244ia c2244ia) {
        this.a = c0702m;
        this.b = c3387xn;
        this.c = c2244ia;
    }

    final boolean a(C2409kl c2409kl, C0729q2 c0729q2, IdentityHashMap identityHashMap, ArrayList arrayList) {
        int i = 0;
        if (((C3733i) this.a.f()).D.b(c0729q2.d)) {
            return false;
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            int iX0 = this.d + ((com.android.tools.r8.graph.o5) it.next()).e().a1().x0();
            this.d = iX0;
            if (iX0 > this.a.Y().e0().a()) {
                return true;
            }
        }
        int size = identityHashMap.size();
        int i2 = 0;
        for (AbstractC1305Qs abstractC1305Qs : c2409kl.b((Predicate) new C2011fa$$ExternalSyntheticLambda0())) {
            com.android.tools.r8.graph.o5 o5Var = (com.android.tools.r8.graph.o5) identityHashMap.get(abstractC1305Qs);
            if (o5Var != null) {
                C2409kl c2409klA = this.b.a(o5Var, abstractC1305Qs);
                int iX1 = o5Var.e().a1().x0();
                Set set = null;
                int i3 = i;
                for (AbstractC2412ko abstractC2412ko : c2409klA.r()) {
                    int iG2 = abstractC2412ko.G2();
                    if (iG2 == 28 || iG2 == 30) {
                        S10 s10M = (abstractC2412ko instanceof C1189Mn ? abstractC2412ko.T().e() : abstractC2412ko.V().e()).m();
                        if (set == null) {
                            List<S10> listC = c2409klA.c();
                            Set setC = OU.c();
                            for (int i4 = i; i4 < ((ArrayList) abstractC1305Qs.h1()).size(); i4++) {
                                S10 s10 = (S10) ((ArrayList) abstractC1305Qs.h1()).get(i4);
                                if (this.c.d(s10)) {
                                    setC.add(listC.get(i4));
                                } else if (!e && this.c.d(s10.m())) {
                                    throw new AssertionError();
                                }
                            }
                            set = setC;
                        }
                        if (set.contains(s10M)) {
                            i3 = this.a.Y().f instanceof ClassFileConsumer ? i3 + 1 : i3 + 2;
                        }
                        i = 0;
                    } else if (iG2 == 56) {
                        boolean z = this.a.Y().f instanceof ClassFileConsumer;
                        i = 0;
                    }
                }
                int i5 = iX1 - i3;
                if (!e && i5 < 0) {
                    throw new AssertionError();
                }
                int i6 = this.d + i5;
                this.d = i6;
                if (i6 > this.a.Y().e0().a()) {
                    return true;
                }
                i2++;
                if (i2 == size) {
                    break;
                }
                i = 0;
            }
        }
        if (e || i2 == size) {
            return false;
        }
        throw new AssertionError();
    }
}
