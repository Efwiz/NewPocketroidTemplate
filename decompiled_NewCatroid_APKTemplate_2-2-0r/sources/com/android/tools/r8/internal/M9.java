package com.android.tools.r8.internal;

import com.android.tools.r8.errors.d$$ExternalSyntheticLambda0;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.utils.C3879c3;
import com.android.tools.r8.utils.InterfaceC3905h3;
import com.android.tools.r8.utils.InterfaceC3986x0;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class M9 {
    static final /* synthetic */ boolean e = true;
    protected final C0702m a;
    private final PM b;
    private final C0825Ad c;
    private final C2569ms d;

    M9(C0702m c0702m, PM pm, C0825Ad c0825Ad, C2569ms c2569ms) {
        this.a = c0702m;
        this.b = pm;
        this.c = c0825Ad;
        this.d = c2569ms;
    }

    private static ArrayDeque b(Collection collection) {
        IdentityHashMap identityHashMap = new IdentityHashMap();
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            C0729q2 c0729q2 = (C0729q2) it.next();
            if (c0729q2.D1()) {
                ((List) identityHashMap.computeIfAbsent(c0729q2.g1(), new Function() { // from class: com.android.tools.r8.internal.M9$$ExternalSyntheticLambda3
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return M9.a((C0767v2) obj);
                    }
                })).add(c0729q2);
            }
        }
        if (identityHashMap.isEmpty()) {
            return C1298Ql.a(new List[0]);
        }
        int iMax = 0;
        for (List list : identityHashMap.values()) {
            iMax = Math.max(iMax, list.size());
            list.sort(Comparator.comparing(new d$$ExternalSyntheticLambda0()));
        }
        ArrayDeque arrayDeque = new ArrayDeque(iMax);
        for (final int i = 0; i < iMax; i++) {
            final ArrayList arrayList = new ArrayList(identityHashMap.size());
            com.android.tools.r8.utils.Z1.a(identityHashMap, new BiPredicate() { // from class: com.android.tools.r8.internal.M9$$ExternalSyntheticLambda4
                @Override // java.util.function.BiPredicate
                public final boolean test(Object obj, Object obj2) {
                    return M9.a(arrayList, i, (C0767v2) obj, (List) obj2);
                }
            });
            arrayDeque.add(arrayList);
        }
        return arrayDeque;
    }

    public final O9 a(ExecutorService executorService) {
        N9 n9 = new N9();
        a(n9, executorService);
        a();
        return n9.b();
    }

    abstract void a();

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public abstract void b(G7 g7, C0729q2 c0729q2);

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ List a(C0767v2 c0767v2) {
        return new ArrayList();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(List list, int i, C0767v2 c0767v2, List list2) {
        list.add((C0729q2) list2.get(i));
        return i + 1 == list2.size();
    }

    private void a(N9 n9, ExecutorService executorService) {
        Collection<C0729q2> collectionD = this.a.f().d();
        AbstractC2151hN abstractC2151hNE = this.c.e();
        S6 s6A = S6.a(this.a, abstractC2151hNE);
        Q6.a(this.b.a).a(executorService, s6A);
        if (!s6A.a().isEmpty()) {
            collectionD = AbstractC1639am.g().a((Iterable) collectionD).a((Iterable) s6A.a()).a();
        }
        G7 g7A = G7.a(this.a, abstractC2151hNE, n9, this.c);
        this.b.a(g7A, executorService);
        if (!e) {
            g7A.b();
        }
        ArrayDeque arrayDequeB = b(collectionD);
        if (!arrayDequeB.isEmpty()) {
            ArrayList arrayList = new ArrayList(collectionD.size());
            for (C0729q2 c0729q2 : collectionD) {
                if (!c0729q2.D1()) {
                    arrayList.add(c0729q2);
                }
            }
            arrayList.addAll((Collection) arrayDequeB.removeFirst());
            collectionD = arrayList;
        }
        while (!collectionD.isEmpty()) {
            Iterator it = collectionD.iterator();
            while (it.hasNext()) {
                this.c.a((C0729q2) it.next());
            }
            final G7 g7A2 = G7.a(this.a, abstractC2151hNE, n9, this.c);
            this.c.f();
            a(collectionD);
            C3879c3.a(collectionD, new Consumer() { // from class: com.android.tools.r8.internal.M9$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.b(g7A2, (C0729q2) obj);
                }
            }, executorService);
            this.c.d();
            List listA = g7A2.a();
            if (!listA.isEmpty()) {
                this.c.f();
                C3879c3.a(listA, new Consumer() { // from class: com.android.tools.r8.internal.M9$$ExternalSyntheticLambda1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        this.f$0.a(g7A2, (com.android.tools.r8.graph.o5) obj);
                    }
                }, executorService);
                this.c.d();
                if (!e) {
                    g7A2.b();
                }
            }
            if (arrayDequeB.isEmpty()) {
                return;
            } else {
                collectionD = (Collection) arrayDequeB.removeFirst();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(G7 g7, com.android.tools.r8.graph.o5 o5Var) {
        com.android.tools.r8.graph.W0 w0E = o5Var.e();
        if (w0E.M1()) {
            w0E.T1();
        }
        this.c.b(o5Var, g7);
        C2569ms c2569ms = this.d;
        if (c2569ms != null) {
            c2569ms.a(o5Var, g7);
        }
    }

    private void a(final Collection collection) {
        this.a.Y().n1.a(new InterfaceC3905h3() { // from class: com.android.tools.r8.internal.M9$$ExternalSyntheticLambda2
            @Override // com.android.tools.r8.utils.InterfaceC3905h3
            public final void accept(Object obj) throws IOException {
                M9.a(collection, (com.android.tools.r8.utils.B0) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Collection collection, com.android.tools.r8.utils.B0 b0) throws IOException {
        final ArrayList arrayList = new ArrayList(collection);
        arrayList.sort(Comparator.comparing(new d$$ExternalSyntheticLambda0()));
        b0.a(new InterfaceC3905h3() { // from class: com.android.tools.r8.internal.M9$$ExternalSyntheticLambda5
            @Override // com.android.tools.r8.utils.InterfaceC3905h3
            public final void accept(Object obj) {
                M9.a(arrayList, (InterfaceC3986x0) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(List list, InterfaceC3986x0 interfaceC3986x0) {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            interfaceC3986x0.a(((C0729q2) it.next()).getType().b1());
        }
    }
}
