package com.android.tools.r8.synthesis;

import com.android.tools.r8.graph.AbstractC0681j0;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.u5;
import com.android.tools.r8.internal.AbstractC0984Fi;
import com.android.tools.r8.internal.AbstractC1048Hm;
import com.android.tools.r8.internal.AbstractC2178hm;
import com.android.tools.r8.internal.AbstractC2963s10;
import com.android.tools.r8.internal.C0862Bm;
import com.android.tools.r8.internal.DH;
import com.android.tools.r8.utils.I1;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.synthesis.c, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3844c {
    static final /* synthetic */ boolean f = true;
    private final L a;
    private final AbstractC2178hm b;
    private final AbstractC2178hm c;
    private final AbstractC2178hm d;
    public final AbstractC1048Hm e;

    public C3844c(L l, AbstractC2178hm abstractC2178hm, AbstractC2178hm abstractC2178hm2, AbstractC2178hm abstractC2178hm3, AbstractC1048Hm abstractC1048Hm) {
        this.a = l;
        this.b = abstractC2178hm;
        this.c = abstractC2178hm2;
        this.d = abstractC2178hm3;
        this.e = abstractC1048Hm;
        if (f) {
            return;
        }
        f();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static AbstractC2178hm a(final IdentityHashMap identityHashMap, AbstractC2178hm abstractC2178hm) {
        if (identityHashMap == null) {
            return abstractC2178hm;
        }
        abstractC2178hm.forEach(new BiConsumer() { // from class: com.android.tools.r8.synthesis.c$$ExternalSyntheticLambda5
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                C3844c.a(identityHashMap, (C0767v2) obj, (List) obj2);
            }
        });
        return AbstractC2178hm.a(identityHashMap);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static AbstractC2178hm b(final IdentityHashMap identityHashMap, AbstractC2178hm abstractC2178hm) {
        if (identityHashMap == null) {
            return abstractC2178hm;
        }
        abstractC2178hm.forEach(new BiConsumer() { // from class: com.android.tools.r8.synthesis.c$$ExternalSyntheticLambda1
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                C3844c.a(identityHashMap, (C0767v2) obj, (Set) obj2);
            }
        });
        return AbstractC2178hm.a(identityHashMap);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ List c(C0767v2 c0767v2) {
        return new ArrayList();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Set d(C0767v2 c0767v2) {
        return new HashSet();
    }

    private void f() {
        final AbstractC1048Hm abstractC1048HmA = AbstractC1048Hm.g().a((Set) this.b.keySet()).a((Set) this.c.keySet()).a();
        this.e.forEach(new Consumer() { // from class: com.android.tools.r8.synthesis.c$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C3844c.a(abstractC1048HmA, (C0767v2) obj);
            }
        });
    }

    final boolean e() {
        boolean z = this.b.isEmpty() && this.c.isEmpty();
        if (f || !z || this.e.isEmpty()) {
            return z;
        }
        throw new AssertionError();
    }

    public final AbstractC2178hm c() {
        return this.b;
    }

    final L d() {
        return this.a;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ List e(C0767v2 c0767v2) {
        return new ArrayList();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Map map, C0767v2 c0767v2, List list) {
        ((List) map.computeIfAbsent(c0767v2, new Function() { // from class: com.android.tools.r8.synthesis.c$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C3844c.c((C0767v2) obj);
            }
        })).addAll(list);
    }

    public final AbstractC2178hm b() {
        return this.d;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Map map, C0767v2 c0767v2, Set set) {
        ((Set) map.computeIfAbsent(c0767v2, new Function() { // from class: com.android.tools.r8.synthesis.c$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C3844c.d((C0767v2) obj);
            }
        })).addAll(set);
    }

    public final Iterable b(C0767v2 c0767v2) {
        AbstractC2178hm abstractC2178hm = this.c;
        Object objEmptyList = Collections.emptyList();
        Object obj = abstractC2178hm.get(c0767v2);
        if (obj != null) {
            objEmptyList = obj;
        }
        Iterable iterable = (Iterable) objEmptyList;
        AbstractC2178hm abstractC2178hm2 = this.b;
        Object objEmptyList2 = Collections.emptyList();
        Object obj2 = abstractC2178hm2.get(c0767v2);
        if (obj2 != null) {
            objEmptyList2 = obj2;
        }
        return AbstractC0984Fi.a(iterable, (Iterable) objEmptyList2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Set set, C0767v2 c0767v2) {
        if (f || set.contains(c0767v2)) {
            return;
        }
        throw new AssertionError("Expected " + c0767v2.r0() + " to be a synthetic");
    }

    public final boolean a(C0767v2 c0767v2) {
        return this.b.containsKey(c0767v2) || this.c.containsKey(c0767v2);
    }

    final boolean a(C0767v2 c0767v2, L.b bVar) {
        List list = (List) this.c.get(c0767v2);
        if (list == null) {
            List list2 = (List) this.b.get(c0767v2);
            if (list2 == null) {
                return false;
            }
            Iterator it = list2.iterator();
            while (it.hasNext()) {
                if (((J) it.next()).c() == bVar) {
                    return true;
                }
            }
            return false;
        }
        Iterator it2 = list.iterator();
        while (it2.hasNext()) {
            if (((S) it2.next()).c() == bVar) {
                return true;
            }
        }
        return false;
    }

    public final void b(Consumer consumer) {
        this.e.forEach(consumer);
    }

    public final AbstractC2178hm a() {
        return this.c;
    }

    public final void a(final Consumer consumer) {
        this.b.values().forEach(new Consumer() { // from class: com.android.tools.r8.synthesis.c$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((List) obj).forEach(consumer);
            }
        });
        this.c.values().forEach(new Consumer() { // from class: com.android.tools.r8.synthesis.c$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((List) obj).forEach(consumer);
            }
        });
    }

    final C3844c a(u5 u5Var) {
        Set setC = u5Var.c();
        if (setC.isEmpty()) {
            return this;
        }
        C3843b c3843b = new C3843b(new C3844c(this.a, AbstractC2178hm.s(), AbstractC2178hm.s(), AbstractC2178hm.s(), AbstractC1048Hm.k()));
        boolean z = false;
        for (J j : I1.a(this.b.values())) {
            if (setC.contains(j.d.e)) {
                z = true;
            } else {
                c3843b.a(j);
            }
        }
        for (S s : I1.a(this.c.values())) {
            if (setC.contains(s.d)) {
                z = true;
            } else {
                c3843b.a(s);
            }
        }
        AbstractC2963s10 it = this.e.iterator();
        while (it.hasNext()) {
            C0767v2 c0767v2 = (C0767v2) it.next();
            if (setC.contains(c0767v2)) {
                z = true;
            } else {
                if (c3843b.d == null) {
                    c3843b.d = AbstractC1048Hm.g();
                }
                c3843b.d.a(c0767v2);
            }
        }
        if (f || this.d.isEmpty() || u5Var.c().size() == u5Var.e().size()) {
            return z ? c3843b.a() : this;
        }
        throw new AssertionError();
    }

    final C3844c a(DH dh) {
        C0862Bm c0862BmG = AbstractC1048Hm.g();
        return new C3844c(this.a, a(this.b, dh, c0862BmG), a(this.c, dh, c0862BmG), this.d, c0862BmG.a());
    }

    private AbstractC2178hm a(AbstractC2178hm abstractC2178hm, DH dh, C0862Bm c0862Bm) {
        IdentityHashMap identityHashMap = new IdentityHashMap();
        for (InterfaceC3845d interfaceC3845d : I1.a(abstractC2178hm.values(), Function.identity())) {
            InterfaceC3845d interfaceC3845dA = interfaceC3845d.a(dh);
            if (interfaceC3845dA != null) {
                ((List) identityHashMap.computeIfAbsent(interfaceC3845dA.a(), new Function() { // from class: com.android.tools.r8.synthesis.c$$ExternalSyntheticLambda7
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return C3844c.e((C0767v2) obj);
                    }
                })).add(interfaceC3845dA);
                if (this.e.contains(interfaceC3845d.a())) {
                    c0862Bm.a(interfaceC3845dA.a());
                }
            }
        }
        return AbstractC2178hm.a(identityHashMap);
    }

    final void a(AbstractC0681j0 abstractC0681j0) {
        boolean z = f;
        if (!z) {
            a(abstractC0681j0, this.b.keySet());
        }
        if (!z) {
            a(abstractC0681j0, this.c.keySet());
        }
        if (z) {
            return;
        }
        a(abstractC0681j0, this.e);
    }

    private static void a(AbstractC0681j0 abstractC0681j0, AbstractC1048Hm abstractC1048Hm) {
        Iterator<E> it = abstractC1048Hm.iterator();
        while (it.hasNext()) {
            C0767v2 c0767v2 = (C0767v2) it.next();
            if (!f && abstractC0681j0.d(c0767v2) == null) {
                throw new AssertionError("Missing synthetic: " + c0767v2);
            }
        }
    }
}
