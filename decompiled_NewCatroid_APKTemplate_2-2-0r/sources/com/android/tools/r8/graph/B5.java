package com.android.tools.r8.graph;

import com.android.tools.r8.internal.AbstractC0984Fi;
import com.android.tools.r8.internal.AbstractC1048Hm;
import com.android.tools.r8.internal.AbstractC1639am;
import com.android.tools.r8.internal.AbstractC2734ot;
import com.android.tools.r8.internal.InterfaceC3205vM;
import com.android.tools.r8.internal.OU;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class B5 {
    private static final Set e = AbstractC1048Hm.k();
    static final /* synthetic */ boolean f = true;
    private final Map a;
    private final Map b;
    private final Q0 c;
    private final C0722p1 d;

    private B5(ConcurrentHashMap concurrentHashMap, IdentityHashMap identityHashMap, Q0 q0) {
        this.b = concurrentHashMap;
        this.a = identityHashMap;
        this.c = q0;
        this.d = q0.a();
    }

    public static B5 a(C0702m<? extends C0680j> c0702m) {
        return a((C0680j) c0702m.f());
    }

    private static void b(Map map, Map map2, C0767v2 c0767v2, AbstractC0727q0 abstractC0727q0, Q0 q0) {
        if (c0767v2 == null || !((Set) map.computeIfAbsent(c0767v2, new Function() { // from class: com.android.tools.r8.graph.B5$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return B5.i((C0767v2) obj);
            }
        })).add(abstractC0727q0.d)) {
            return;
        }
        a(map, map2, c0767v2, abstractC0727q0, q0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean e(C0767v2 c0767v2) {
        return a(c0767v2, this.b).b();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean f(C0767v2 c0767v2) {
        return !a(c0767v2, this.b).b();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean g(C0767v2 c0767v2) {
        return !a(c0767v2, this.b).b();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean h(C0767v2 c0767v2) {
        return a(c0767v2, this.b).b();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Set i(C0767v2 c0767v2) {
        return new HashSet();
    }

    /* JADX WARN: Type inference failed for: r2v2, types: [java.util.AbstractCollection, java.util.Set] */
    public final Set c(C0767v2 c0767v2) {
        return a(c0767v2, this.b).c;
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [java.util.AbstractCollection, java.util.Set] */
    public final C0767v2 d(C0767v2 c0767v2) {
        A5 a5A = a(c0767v2, this.b);
        if (!f && a5A.b == -1) {
            throw new AssertionError();
        }
        if (a5A.c.size() != 1) {
            return null;
        }
        Iterator it = a5A.c.iterator();
        return (C0767v2) (it.hasNext() ? it.next() : null);
    }

    public Set<C0767v2> j(C0767v2 c0767v2) {
        if (!f && !c0767v2.O0()) {
            throw new AssertionError();
        }
        Set<C0767v2> set = (Set) this.a.get(c0767v2);
        return set == null ? AbstractC1048Hm.k() : set;
    }

    public static B5 a(C0680j c0680j) {
        N2 n2B = c0680j.b().b();
        return a(AbstractC0984Fi.a(n2B.h(), n2B.k(), n2B.l()), c0680j);
    }

    public final Iterable b(C0767v2 c0767v2) {
        A5 a5A = a(c0767v2, this.b);
        if (a5A.b == -2) {
            return AbstractC2734ot.c(a5A.c, new InterfaceC3205vM() { // from class: com.android.tools.r8.graph.B5$$ExternalSyntheticLambda3
                @Override // com.android.tools.r8.internal.InterfaceC3205vM
                public final boolean apply(Object obj) {
                    return this.f$0.g((C0767v2) obj);
                }
            });
        }
        return AbstractC1639am.i();
    }

    public static B5 a(Iterable iterable, Q0 q0) {
        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
        IdentityHashMap identityHashMap = new IdentityHashMap();
        a(iterable, identityHashMap, concurrentHashMap, q0);
        return new B5(concurrentHashMap, identityHashMap, q0);
    }

    public final Iterable a(C0767v2 c0767v2) {
        A5 a5A = a(c0767v2, this.b);
        if (!f && a5A.b == -1) {
            throw new AssertionError();
        }
        int i = a5A.b;
        if (i == -2) {
            return AbstractC2734ot.c(a5A.c, new InterfaceC3205vM() { // from class: com.android.tools.r8.graph.B5$$ExternalSyntheticLambda7
                @Override // com.android.tools.r8.internal.InterfaceC3205vM
                public final boolean apply(Object obj) {
                    return this.f$0.e((C0767v2) obj);
                }
            });
        }
        if (i == 0) {
            return AbstractC2734ot.c(a5A.c, new InterfaceC3205vM() { // from class: com.android.tools.r8.graph.B5$$ExternalSyntheticLambda8
                @Override // com.android.tools.r8.internal.InterfaceC3205vM
                public final boolean apply(Object obj) {
                    return this.f$0.f((C0767v2) obj);
                }
            });
        }
        return a5A.c;
    }

    private static A5 a(C0767v2 c0767v2, Map map) {
        if (f || c0767v2 != null) {
            return (A5) map.computeIfAbsent(c0767v2, new Function() { // from class: com.android.tools.r8.graph.B5$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return new A5((C0767v2) obj);
                }
            });
        }
        throw new AssertionError();
    }

    /* JADX WARN: Type inference failed for: r11v2, types: [java.util.AbstractCollection, java.util.Set] */
    private static void a(final Map map, final Map map2, final C0767v2 c0767v2, final AbstractC0727q0 abstractC0727q0, final Q0 q0) {
        AbstractC0727q0 abstractC0727q0A = q0.a(c0767v2);
        final A5 a5A = a(c0767v2, map2);
        if (abstractC0727q0A != null) {
            abstractC0727q0A.b(new BiConsumer() { // from class: com.android.tools.r8.graph.B5$$ExternalSyntheticLambda6
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    B5.a(map, map2, abstractC0727q0, q0, c0767v2, a5A, (C0767v2) obj, (Boolean) obj2);
                }
            });
            if (abstractC0727q0A.isInterface()) {
                a5A.a(-2);
                return;
            }
            return;
        }
        C0767v2 c0767v3 = q0.a().Z1;
        if (c0767v2 != c0767v3) {
            A5 a5A2 = a(c0767v3, map2);
            if (!A5.d && a5A2.b == -1) {
                throw new AssertionError();
            }
            a5A2.a();
            a5A2.c.add(a5A.a);
            a5A.a(a5A2.b + 1);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Type inference failed for: r0v5, types: [java.util.AbstractCollection, java.util.Set] */
    /* JADX WARN: Type inference failed for: r1v3, types: [java.util.AbstractCollection, java.util.Set] */
    public static void a(Map map, Map map2, AbstractC0727q0 abstractC0727q0, Q0 q0, C0767v2 c0767v2, A5 a5, C0767v2 c0767v3, Boolean bool) {
        b(map, map2, c0767v3, abstractC0727q0, q0);
        A5 a5A = a(c0767v3, map2);
        if (bool.booleanValue()) {
            a5A.a(-2);
            a5A.a();
            a5A.c.add(c0767v2);
        } else {
            if (!A5.d && a5A.b == -1) {
                throw new AssertionError();
            }
            a5A.a();
            a5A.c.add(a5.a);
            a5.a(a5A.b + 1);
        }
    }

    /* JADX WARN: Type inference failed for: r4v3, types: [java.util.AbstractCollection, java.util.Set] */
    /* JADX WARN: Type inference failed for: r9v1, types: [java.util.AbstractCollection, java.util.Set] */
    private static void a(Iterable iterable, IdentityHashMap identityHashMap, ConcurrentHashMap concurrentHashMap, Q0 q0) {
        C0767v2 c0767v2;
        a(q0.a().Z1, concurrentHashMap).a(0);
        Iterator it = iterable.iterator();
        while (it.hasNext()) {
            AbstractC0727q0 abstractC0727q0 = (AbstractC0727q0) it.next();
            a(identityHashMap, concurrentHashMap, abstractC0727q0.d, abstractC0727q0, q0);
        }
        identityHashMap.replaceAll(new BiFunction() { // from class: com.android.tools.r8.graph.B5$$ExternalSyntheticLambda1
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return B5.a((C0767v2) obj, (Set) obj2);
            }
        });
        if (f) {
            return;
        }
        Set setC = OU.c();
        ArrayDeque arrayDeque = new ArrayDeque();
        C0767v2 c0767v3 = q0.a().Z1;
        arrayDeque.add(c0767v3);
        while (!arrayDeque.isEmpty()) {
            C0767v2 c0767v4 = (C0767v2) arrayDeque.pop();
            AbstractC0727q0 abstractC0727q0A = q0.a(c0767v4);
            if (abstractC0727q0A == null) {
                c0767v2 = c0767v4 == c0767v3 ? null : c0767v3;
            } else {
                c0767v2 = abstractC0727q0A.f;
            }
            boolean z = f;
            if (!z && setC.contains(c0767v4)) {
                throw new AssertionError();
            }
            setC.add(c0767v4);
            A5 a5A = a(c0767v4, concurrentHashMap);
            if (c0767v2 == null) {
                if (!z && a5A.b != 0) {
                    throw new AssertionError();
                }
            } else {
                A5 a5A2 = a(c0767v2, concurrentHashMap);
                if (!z) {
                    int i = a5A2.b;
                    int i2 = a5A.b;
                    if (i != i2 - 1 && (i != 0 || i2 != -2)) {
                        throw new AssertionError();
                    }
                }
                if (!z && !a5A2.c.contains(c0767v4)) {
                    throw new AssertionError();
                }
            }
            if (a5A.b != -2) {
                arrayDeque.addAll(a5A.c);
            } else if (abstractC0727q0A != null) {
                for (C0767v2 c0767v5 : abstractC0727q0A.g.a) {
                    A5 a5A3 = a(c0767v5, concurrentHashMap);
                    boolean z2 = f;
                    if (!z2 && !a5A3.c.contains(c0767v4)) {
                        throw new AssertionError();
                    }
                    if (!z2 && a5A3.b != -2) {
                        throw new AssertionError();
                    }
                }
            } else {
                continue;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Set a(C0767v2 c0767v2, Set set) {
        return AbstractC1048Hm.a(set);
    }

    public final void a(C0767v2 c0767v2, Consumer consumer) {
        a(c0767v2).forEach(consumer);
    }

    public final void a(Consumer consumer) {
        AbstractC2734ot.c(a(this.d.Z1, this.b).c, new InterfaceC3205vM() { // from class: com.android.tools.r8.graph.B5$$ExternalSyntheticLambda5
            @Override // com.android.tools.r8.internal.InterfaceC3205vM
            public final boolean apply(Object obj) {
                return this.f$0.h((C0767v2) obj);
            }
        }).forEach(consumer);
    }

    public final List a() {
        final ArrayList arrayList = new ArrayList();
        a(new Consumer() { // from class: com.android.tools.r8.graph.B5$$ExternalSyntheticLambda9
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(arrayList, (C0767v2) obj);
            }
        });
        return AbstractC0681j0.a(arrayList);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(final List list, C0767v2 c0767v2) {
        O oF = this.c.f(c0767v2);
        Objects.requireNonNull(list);
        oF.b(new Consumer() { // from class: com.android.tools.r8.graph.B5$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                list.add((AbstractC0727q0) obj);
            }
        });
    }
}
