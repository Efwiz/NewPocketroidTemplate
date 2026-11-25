package com.android.tools.r8.internal;

import com.android.tools.r8.ProgramResource;
import com.android.tools.r8.graph.AbstractC0674i0;
import com.android.tools.r8.graph.C0658g0;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0706m3;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.C0792w4;
import com.android.tools.r8.graph.C0800x2;
import com.android.tools.r8.origin.Origin;
import java.util.ArrayList;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.internal.lg, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2478lg {
    static final /* synthetic */ boolean c = true;
    private final C0702m a;
    private final IdentityHashMap b = new IdentityHashMap();

    public C2478lg(C0702m c0702m) {
        this.a = c0702m;
        c0702m.Y().C1.m().forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.lg$$ExternalSyntheticLambda1
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.a((C0767v2) obj, (C2556mg) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(C0767v2 c0767v2, C2556mg c2556mg) {
        this.b.put(c0767v2, c2556mg.b());
    }

    public final void a(AbstractC0674i0 abstractC0674i0) {
        List<C0706m3.e> listR;
        if (!c && !this.a.Y().C1.d()) {
            throw new AssertionError();
        }
        ArrayList arrayList = new ArrayList();
        Iterator it = ((ArrayList) abstractC0674i0.c()).iterator();
        while (it.hasNext()) {
            C0729q2 c0729q2 = (C0729q2) it.next();
            if (this.b.containsKey(c0729q2.d)) {
                if (!this.a.b(c0729q2)) {
                    C0767v2 c0767v2 = (C0767v2) this.b.get(c0729q2.d);
                    boolean z = c;
                    if (!z && c0767v2 == null) {
                        throw new AssertionError();
                    }
                    com.android.tools.r8.graph.W0[] w0ArrA = a(c0729q2, c0767v2);
                    com.android.tools.r8.graph.W0[] w0Arr = com.android.tools.r8.graph.W0.s;
                    if (!z && c0729q2.n1() != this.a.a().Z1) {
                        throw new AssertionError();
                    }
                    if (!z && c0729q2.b2()) {
                        throw new AssertionError();
                    }
                    if (!z && c0729q2.g1() != null) {
                        throw new AssertionError();
                    }
                    if (!z && c0729q2.t1()) {
                        throw new AssertionError();
                    }
                    if (!z && c0729q2.b2()) {
                        throw new AssertionError();
                    }
                    ProgramResource.Kind kindW1 = c0729q2.W1();
                    Origin origin = c0729q2.c;
                    com.android.tools.r8.graph.E accessFlags = c0729q2.getAccessFlags();
                    C0767v2 c0767v3 = this.a.a().Z1;
                    C0800x2 c0800x2S0 = C0800x2.s0();
                    C0759u2 c0759u2M1 = c0729q2.m1();
                    List listEmptyList = Collections.emptyList();
                    List listEmptyList2 = Collections.emptyList();
                    List listEmptyList3 = Collections.emptyList();
                    List listEmptyList4 = Collections.emptyList();
                    C0706m3.b bVarA1 = c0729q2.a1();
                    C0658g0 c0658g0S0 = c0729q2.s0();
                    com.android.tools.r8.graph.T0[] t0Arr = com.android.tools.r8.graph.T0.m;
                    C0729q2 c0729q3 = new C0729q2(c0767v2, kindW1, origin, accessFlags, c0767v3, c0800x2S0, c0759u2M1, null, listEmptyList, listEmptyList2, listEmptyList3, null, listEmptyList4, bVarA1, c0658g0S0, t0Arr, t0Arr, C0792w4.a.a(w0Arr, w0ArrA), false, c0729q2.T1());
                    ArrayList arrayList2 = new ArrayList();
                    C0706m3.b bVarA2 = c0729q2.a1();
                    for (int i = 0; i < c0729q2.g.size(); i++) {
                        C0767v2 c0767v4 = c0729q2.g.a[i];
                        if (this.b.containsKey(c0767v4)) {
                            if (bVarA2 == null) {
                                listR = Collections.emptyList();
                            } else {
                                C0706m3.c cVar = (C0706m3.c) bVarA2.h().get(i);
                                if (!c && c0767v4 != cVar.q()) {
                                    throw new AssertionError();
                                }
                                listR = cVar.r();
                            }
                            arrayList2.add(new C0706m3.c((C0767v2) this.b.get(c0767v4), listR));
                        }
                    }
                    c0729q3.c((List) arrayList2);
                    c0729q2 = c0729q3;
                }
                arrayList.add(c0729q2);
                it = it;
            } else {
                arrayList.add(c0729q2);
            }
        }
        abstractC0674i0.a(arrayList);
    }

    private com.android.tools.r8.graph.W0[] a(C0729q2 c0729q2, C0767v2 c0767v2) {
        ArrayList arrayListC = com.android.tools.r8.utils.I1.c(c0729q2.o(new Predicate() { // from class: com.android.tools.r8.internal.lg$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return this.f$0.a((com.android.tools.r8.graph.W0) obj);
            }
        }));
        int size = arrayListC.size();
        com.android.tools.r8.graph.W0[] w0Arr = new com.android.tools.r8.graph.W0[size];
        for (int i = 0; i < size; i++) {
            w0Arr[i] = ((com.android.tools.r8.graph.o5) arrayListC.get(i)).e().a(this.a.a(), c0767v2);
        }
        return w0Arr;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean a(com.android.tools.r8.graph.W0 w0) {
        return this.a.Y().C1.a(w0.getReference()) != null;
    }
}
