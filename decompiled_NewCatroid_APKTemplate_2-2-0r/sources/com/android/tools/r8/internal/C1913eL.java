package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.internal.eL, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1913eL extends AbstractC2148hL {
    static final /* synthetic */ boolean u = true;
    private final C2409kl p;
    private final Set q;
    private final Set r;
    int s;
    final /* synthetic */ C2225iL t;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    C1913eL(C2225iL c2225iL, C2409kl c2409kl, List list, Set set, Set set2) {
        super(c2225iL, c2409kl.h(), list);
        this.t = c2225iL;
        this.p = c2409kl;
        this.q = set;
        this.r = set2;
    }

    @Override // com.android.tools.r8.internal.AbstractC2148hL
    protected final void a(int i, int i2, ZK zk) {
        C0684j2 c0684j2 = (C0684j2) this.t.c.get(zk);
        if (c0684j2 != null) {
            if (!u) {
                a(zk);
            }
            ArrayList arrayList = new ArrayList();
            this.s = 0;
            C1607aM c1607aM = (C1607aM) ((C1607aM) C1687bM.x().a(this.t.d.E().a((AbstractC1476Wj) null, this.a.getReference()))).b(c0684j2).a(0);
            int i3 = 0;
            AbstractC2412ko abstractC2412ko = null;
            S10 s10B = null;
            for (int i4 = i; i4 < i2; i4++) {
                AbstractC2412ko abstractC2412ko2 = (AbstractC2412ko) this.b.get(i4);
                if (!abstractC2412ko2.y1()) {
                    int i5 = i3 + 1;
                    if (abstractC2412ko2.Y0() != null && abstractC2412ko2.i1()) {
                        c1607aM.a(i3, abstractC2412ko2.Y0());
                    }
                    List listH1 = abstractC2412ko2.h1();
                    if (abstractC2412ko2.v1() && abstractC2412ko2.x().K2()) {
                        ArrayList arrayList2 = (ArrayList) listH1;
                        if (arrayList2.get(1) == s10B) {
                            S10 s10 = (S10) arrayList2.get(0);
                            arrayList2.set(0, (S10) arrayList2.get(1));
                            arrayList2.set(1, s10);
                        }
                    }
                    for (S10 s11 : (ArrayList) listH1) {
                        s11.e(abstractC2412ko2);
                        List list = zk.b;
                        int i6 = this.s;
                        this.s = i6 + 1;
                        int iIntValue = ((Integer) list.get(i6)).intValue();
                        if (iIntValue >= arrayList.size()) {
                            if (!u && iIntValue != arrayList.size()) {
                                throw new AssertionError();
                            }
                            arrayList.add(s11);
                        }
                    }
                    if (abstractC2412ko2.b() != null) {
                        s10B = abstractC2412ko2.b();
                    }
                    if (i4 < i2 - 1) {
                        this.q.add(abstractC2412ko2);
                    }
                    i3 = i5;
                    abstractC2412ko = abstractC2412ko2;
                }
            }
            boolean z = u;
            if (!z && abstractC2412ko == null) {
                throw new AssertionError();
            }
            if (!z && c0684j2.h.d.toString().length() - 1 != arrayList.size()) {
                throw new AssertionError();
            }
            C1721bt c1721bt = new C1721bt(c0684j2, (s10B == null || s10B.S()) ? s10B : null, arrayList);
            c1721bt.a(abstractC2412ko.h());
            c1721bt.b(c1607aM.e() ? c1607aM.a() : AbstractC1915eM.v());
            A4 a4A = abstractC2412ko.h().a(this.p, abstractC2412ko);
            AbstractC2412ko abstractC2412koPrevious = a4A.previous();
            if (!z && abstractC2412koPrevious != abstractC2412ko) {
                throw new AssertionError();
            }
            a4A.set(c1721bt);
            this.r.add(c1721bt);
        }
    }

    private void a(ZK zk) {
        Optional optionalEmpty;
        synchronized (this.t.b) {
            if (!u) {
                List list = (List) this.t.b.get(zk);
                int iA = com.android.tools.r8.utils.V1.a(list, new Predicate() { // from class: com.android.tools.r8.internal.eL$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return this.f$0.a((com.android.tools.r8.graph.o5) obj);
                    }
                });
                if (iA >= 0) {
                    optionalEmpty = Optional.of(list.remove(iA));
                } else {
                    optionalEmpty = Optional.empty();
                }
                if (!optionalEmpty.isPresent()) {
                    throw new AssertionError();
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean a(com.android.tools.r8.graph.o5 o5Var) {
        return o5Var.e() == this.a.e();
    }
}
