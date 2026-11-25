package com.android.tools.r8.dex;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.internal.C1367Tb;
import com.android.tools.r8.internal.RD;
import com.android.tools.r8.utils.C3879c3;
import com.android.tools.r8.utils.C3932m1;
import com.gaurav.avnc.vnc.XKeySym;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.function.Predicate;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class P {
    private static final Comparator j = new Comparator() { // from class: com.android.tools.r8.dex.P$$ExternalSyntheticLambda1
        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            return P.a((C0729q2) obj, (C0729q2) obj2);
        }
    };
    static final /* synthetic */ boolean k = true;
    private final ExecutorService a;
    private final r0 b;
    private final List c;
    private final List d;
    private final BitSet e = new BitSet();
    private final Set f;
    private final C0702m g;
    private final C3932m1 h;
    private final O i;

    public P(r0 r0Var, ArrayList arrayList, List list, Set set, C3932m1 c3932m1, C0702m c0702m, ExecutorService executorService) {
        this.b = r0Var;
        this.c = arrayList;
        this.d = list;
        this.f = set;
        this.h = c3932m1;
        this.g = c0702m;
        this.a = executorService;
        this.i = new O(c0702m, set);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ int a(C0729q2 c0729q2, C0729q2 c0729q3) {
        return c0729q2.d.e.a(c0729q3.d.e);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean b(List list, r0 r0Var) {
        return (!r0Var.m() || list.contains(r0Var) || this.e.get(r0Var.g())) ? false : true;
    }

    private List a(N n, p0 p0Var, final List list) {
        r0 r0VarA;
        if (n.a.isEmpty()) {
            return Collections.emptyList();
        }
        p0Var.c();
        if (n.b < 65536 && n.c < 65536) {
            do {
                r0VarA = p0Var.a(new Predicate() { // from class: com.android.tools.r8.dex.P$$ExternalSyntheticLambda2
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return this.f$0.a(list, (r0) obj);
                    }
                });
            } while (!a(r0VarA, n.a));
            return Collections.singletonList(r0VarA);
        }
        r0 r0VarA2 = p0Var.a(new Predicate() { // from class: com.android.tools.r8.dex.P$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return this.f$0.b(list, (r0) obj);
            }
        });
        HashSet hashSetB = b(r0VarA2, n.a);
        HashSet hashSet = new HashSet(list);
        hashSet.add(r0VarA2);
        ArrayList arrayListA = a(hashSetB, p0Var, hashSet);
        arrayListA.add(r0VarA2);
        return arrayListA;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean b(Set set, r0 r0Var) {
        return (set.contains(r0Var) || this.e.get(r0Var.g())) ? false : true;
    }

    private HashSet b(r0 r0Var, Set set) {
        int size = set.size();
        int size2 = r0Var.b().size();
        HashSet hashSet = new HashSet(set);
        boolean z = true;
        int i = 0;
        while (z && !hashSet.isEmpty()) {
            HashSet hashSet2 = new HashSet();
            ArrayList<C0729q2> arrayList = new ArrayList(hashSet);
            Collections.sort(arrayList, j);
            boolean z2 = false;
            for (C0729q2 c0729q2 : arrayList) {
                if (!z2 && !hashSet.contains(C0729q2.a(this.g.b(c0729q2, c0729q2.f)))) {
                    C0767v2[] c0767v2Arr = c0729q2.g.a;
                    int length = c0767v2Arr.length;
                    int i2 = 0;
                    while (true) {
                        if (i2 < length) {
                            if (!hashSet.contains(C0729q2.a(this.g.b(c0729q2, c0767v2Arr[i2])))) {
                                i2++;
                            }
                        } else {
                            r0Var.a(c0729q2);
                            if (r0Var.n()) {
                                r0Var.a();
                                if (!r0Var.m()) {
                                    hashSet2.add(c0729q2);
                                    if (!r0Var.a(XKeySym.XK_KP_End)) {
                                        z = false;
                                        break;
                                    }
                                    this.e.set(r0Var.g());
                                    z = false;
                                    z2 = true;
                                    break;
                                }
                                throw new C1367Tb("Class '" + c0729q2.r0() + "' from " + c0729q2.getOrigin().toString() + " is too big to fit in a dex.");
                            }
                            i++;
                            r0Var.c();
                            break;
                        }
                    }
                }
                hashSet2.add(c0729q2);
            }
            hashSet = hashSet2;
        }
        if (k || (size == hashSet.size() + i && size2 + i == r0Var.b().size())) {
            return hashSet;
        }
        throw new AssertionError();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a(List list, r0 r0Var) {
        return (list.contains(r0Var) || this.e.get(r0Var.g())) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a(Set set, r0 r0Var) {
        return (set.contains(r0Var) || this.e.get(r0Var.g())) ? false : true;
    }

    private ArrayList a(HashSet hashSet, p0 p0Var, Collection collection) {
        int size;
        int size2 = hashSet.size();
        ArrayList arrayList = new ArrayList();
        while (!hashSet.isEmpty()) {
            C0729q2 c0729q2A = a((C0729q2) hashSet.iterator().next(), hashSet);
            hashSet.remove(c0729q2A);
            arrayList.add(c0729q2A);
        }
        if (!k && arrayList.size() != size2) {
            throw new AssertionError();
        }
        ArrayList<N> arrayList2 = new ArrayList();
        HashMap mapB = RD.b(hashSet.size());
        int size3 = arrayList.size();
        while (true) {
            size3--;
            size = 0;
            if (size3 < 0) {
                break;
            }
            C0729q2 c0729q2 = (C0729q2) arrayList.get(size3);
            Collection collectionA = this.i.a(c0729q2);
            BitSet bitSet = new BitSet();
            Iterator it = collectionA.iterator();
            while (it.hasNext()) {
                bitSet.set(((Integer) mapB.get((C0729q2) it.next())).intValue());
            }
            int iNextClearBit = bitSet.nextClearBit(0);
            mapB.put(c0729q2, Integer.valueOf(iNextClearBit));
            if (arrayList2.size() == iNextClearBit) {
                arrayList2.add(new N(this));
            }
            ((N) arrayList2.get(iNextClearBit)).a.add(c0729q2);
        }
        a(arrayList2);
        if (!k) {
            Iterator it2 = arrayList2.iterator();
            while (it2.hasNext()) {
                size += ((N) it2.next()).a.size();
            }
            if (size2 != size) {
                throw new AssertionError();
            }
        }
        Collections.sort(arrayList2);
        ArrayList arrayList3 = new ArrayList();
        final HashSet hashSet2 = new HashSet(collection);
        hashSet2.add(this.b);
        for (N n : arrayList2) {
            p0Var.c();
            r0 r0VarA = p0Var.a(new Predicate() { // from class: com.android.tools.r8.dex.P$$ExternalSyntheticLambda4
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return this.f$0.a(hashSet2, (r0) obj);
                }
            });
            hashSet2.add(r0VarA);
            arrayList3.add(r0VarA);
            ArrayList<C0729q2> arrayList4 = new ArrayList(n.a);
            Collections.sort(arrayList4, j);
            for (C0729q2 c0729q3 : arrayList4) {
                while (true) {
                    r0VarA.a(c0729q3);
                    if (r0VarA.n()) {
                        r0VarA.a();
                        if (!r0VarA.m()) {
                            if (r0VarA.a(XKeySym.XK_KP_End)) {
                                this.e.set(r0VarA.g());
                            }
                            r0VarA = p0Var.a(new Predicate() { // from class: com.android.tools.r8.dex.P$$ExternalSyntheticLambda5
                                @Override // java.util.function.Predicate
                                public final boolean test(Object obj) {
                                    return this.f$0.b(hashSet2, (r0) obj);
                                }
                            });
                            hashSet2.add(r0VarA);
                            arrayList3.add(r0VarA);
                        } else {
                            throw new C1367Tb("Class '" + c0729q3.r0() + "' from " + c0729q3.getOrigin().toString() + " is too big to fit in a dex.");
                        }
                    }
                }
                r0VarA.c();
            }
        }
        return arrayList3;
    }

    public final void a() {
        HashSet hashSet = new HashSet(this.f);
        LinkedList<N> linkedList = new LinkedList();
        while (!hashSet.isEmpty()) {
            C0729q2 c0729q2 = (C0729q2) hashSet.iterator().next();
            N n = new N(this);
            linkedList.add(n);
            a(hashSet, n, c0729q2);
            if (!k && n.a.isEmpty()) {
                throw new AssertionError();
            }
        }
        a(linkedList);
        if (!k) {
            int size = this.f.size();
            Iterator it = linkedList.iterator();
            int size2 = 0;
            while (it.hasNext()) {
                size2 += ((N) it.next()).a.size();
            }
            if (size != size2) {
                throw new AssertionError();
            }
        }
        linkedList.sort(null);
        Collections.reverse(linkedList);
        p0 p0Var = new p0(this.c, this.d, this.g, this.h);
        Iterator it2 = linkedList.iterator();
        while (it2.hasNext()) {
            N n2 = (N) it2.next();
            if (n2.d) {
                it2.remove();
                if (n2.b < 65536 && n2.c < 65536) {
                    if (this.e.get(this.b.g()) || !a(this.b, n2.a)) {
                    }
                }
                M m = new M(this, n2);
                HashSet hashSetB = b(this.b, m.b);
                List listA = a(new N(this, m.a), p0Var, Collections.singletonList(this.b));
                HashSet hashSet2 = new HashSet(m.c);
                hashSet2.addAll(hashSetB);
                a(hashSet2, p0Var, listA);
            }
        }
        for (N n3 : linkedList) {
            if (!n3.d) {
                a(n3, p0Var, Collections.emptyList());
            }
        }
    }

    private void a(AbstractList abstractList) {
        ArrayList arrayList = new ArrayList(abstractList.size());
        Iterator it = abstractList.iterator();
        while (it.hasNext()) {
            final N n = (N) it.next();
            ExecutorService executorService = this.a;
            Objects.requireNonNull(n);
            arrayList.add(executorService.submit(new Runnable() { // from class: com.android.tools.r8.dex.P$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    n.a();
                }
            }));
        }
        try {
            C3879c3.a(arrayList);
        } catch (ExecutionException e) {
            Throwable cause = e.getCause();
            if (!(cause instanceof RuntimeException)) {
                if (cause instanceof Error) {
                    throw ((Error) cause);
                }
                throw new AssertionError(e);
            }
            throw ((RuntimeException) cause);
        }
    }

    private void a(HashSet hashSet, N n, C0729q2 c0729q2) {
        if (c0729q2 == null) {
            return;
        }
        if (!hashSet.remove(c0729q2)) {
            if (n.d) {
                return;
            }
            n.d = this.b.b().contains(c0729q2);
            return;
        }
        n.a.add(c0729q2);
        C0702m c0702m = this.g;
        C0767v2 c0767v2 = c0729q2.f;
        c0702m.getClass();
        a(hashSet, n, C0729q2.a(c0767v2 == c0729q2.d ? c0729q2 : c0702m.a(c0767v2)));
        C0767v2[] c0767v2Arr = c0729q2.g.a;
        int length = c0767v2Arr.length;
        for (int i = 0; i < length; i++) {
            C0767v2 c0767v3 = c0767v2Arr[i];
            C0702m c0702m2 = this.g;
            c0702m2.getClass();
            a(hashSet, n, C0729q2.a(c0767v3 == c0729q2.d ? c0729q2 : c0702m2.a(c0767v3)));
        }
        Iterator it = this.i.a(c0729q2).iterator();
        while (it.hasNext()) {
            a(hashSet, n, (C0729q2) it.next());
        }
    }

    private boolean a(r0 r0Var, Set set) {
        int size = set.size();
        int size2 = r0Var.b().size();
        Iterator it = set.iterator();
        int i = 0;
        while (it.hasNext()) {
            r0Var.a((C0729q2) it.next());
            i++;
            if (r0Var.a(65536)) {
                r0Var.a();
                if (r0Var.a(XKeySym.XK_KP_End)) {
                    this.e.set(r0Var.g());
                }
                if (k || size2 == r0Var.b().size()) {
                    return false;
                }
                throw new AssertionError();
            }
        }
        r0Var.c();
        if (k) {
            return true;
        }
        if (size == i && size2 + i == r0Var.b().size()) {
            return true;
        }
        throw new AssertionError();
    }

    private C0729q2 a(C0729q2 c0729q2, HashSet hashSet) {
        C0702m c0702m = this.g;
        C0767v2 c0767v2 = c0729q2.f;
        c0702m.getClass();
        C0729q2 c0729q2A = C0729q2.a(c0767v2 == c0729q2.d ? c0729q2 : c0702m.a(c0767v2));
        if (hashSet.contains(c0729q2A)) {
            return a(c0729q2A, hashSet);
        }
        for (C0767v2 c0767v3 : c0729q2.g.a) {
            AbstractC0727q0 abstractC0727q0C = this.g.c(c0767v3);
            if (abstractC0727q0C.e0() && hashSet.contains(abstractC0727q0C.a0())) {
                return a((C0729q2) abstractC0727q0C, hashSet);
            }
        }
        return c0729q2;
    }
}
