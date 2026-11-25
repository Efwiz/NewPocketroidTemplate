package com.android.tools.r8.internal;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.internal.ud, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C3144ud {
    static final /* synthetic */ boolean j = true;
    private ArrayDeque a = new ArrayDeque();
    private IdentityHashMap b = new IdentityHashMap();
    private ArrayDeque c = new ArrayDeque();
    private ArrayDeque d = new ArrayDeque();
    private Set e = OU.c();
    private IdentityHashMap f = new IdentityHashMap();
    private IdentityHashMap g = new IdentityHashMap();
    private IdentityHashMap h = new IdentityHashMap();
    private LinkedHashSet i = new LinkedHashSet();

    /* JADX INFO: renamed from: com.android.tools.r8.internal.ud$a */
    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class a {
        private Map a;

        a(IdentityHashMap identityHashMap) {
            this.a = identityHashMap;
        }

        public int a() {
            Iterator it = this.a.values().iterator();
            int size = 0;
            while (it.hasNext()) {
                size += ((CN) it.next()).size();
            }
            return size;
        }
    }

    private void b(Collection collection) {
        ArrayDeque arrayDeque = new ArrayDeque(collection.size());
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            arrayDeque.addLast(new C3300wd((C2768pH) it.next()));
        }
        while (!arrayDeque.isEmpty()) {
            AbstractC3447yd abstractC3447yd = (AbstractC3447yd) arrayDeque.removeFirst();
            abstractC3447yd.getClass();
            if (abstractC3447yd instanceof C3300wd) {
                C2768pH c2768pH = abstractC3447yd.b().a;
                if (this.e.contains(c2768pH)) {
                    continue;
                } else {
                    C2768pH c2768pH2 = this.a.isEmpty() ? null : (C2768pH) this.a.peek();
                    this.a.push(c2768pH);
                    if (!j && this.b.containsKey(c2768pH)) {
                        throw new AssertionError();
                    }
                    this.b.put(c2768pH, new C3377xd(this.a.size() - 1, c2768pH2));
                    if (c2768pH2 != null) {
                        if (c2768pH.a().u1() && c2768pH.c(c2768pH2)) {
                            this.c.push(c2768pH);
                        } else if (c2768pH2.h().contains(c2768pH)) {
                            this.d.push(c2768pH);
                        }
                    }
                    arrayDeque.addFirst(new C3223vd(c2768pH, AbstractC3533zt.a(c2768pH.e().iterator(), c2768pH.h().iterator())));
                }
            } else {
                boolean z = j;
                if (!z && !(abstractC3447yd instanceof C3223vd)) {
                    throw new AssertionError();
                }
                final C3223vd c3223vdA = abstractC3447yd.a();
                C2768pH c2768pHA = a(c3223vdA.a, c3223vdA.b);
                if (c2768pHA != null) {
                    arrayDeque.addFirst(c3223vdA);
                    arrayDeque.addFirst(new C3300wd(c2768pHA));
                } else {
                    if (!z && c3223vdA.b.hasNext()) {
                        throw new AssertionError();
                    }
                    C2768pH c2768pH3 = c3223vdA.a;
                    C2768pH c2768pH4 = (C2768pH) this.a.pop();
                    if (!z && c2768pH4 != c2768pH3) {
                        throw new AssertionError();
                    }
                    if (!z && !this.b.containsKey(c2768pH3)) {
                        throw new AssertionError();
                    }
                    this.b.remove(c2768pH3);
                    if (this.c.peek() == c2768pH4) {
                        if (!z && this.d.peek() == c2768pH4) {
                            throw new AssertionError();
                        }
                        this.c.pop();
                    } else if (this.d.peek() == c2768pH4) {
                        this.d.pop();
                    }
                    this.e.add(c3223vdA.a);
                    Collection collection2 = (Collection) this.f.remove(c3223vdA.a);
                    if (collection2 != null) {
                        collection2.forEach(new Consumer() { // from class: com.android.tools.r8.internal.ud$$ExternalSyntheticLambda4
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                this.f$0.a(c3223vdA, (C2768pH) obj);
                            }
                        });
                    }
                    Collection collection3 = (Collection) this.g.remove(c3223vdA.a);
                    if (collection3 != null) {
                        collection3.forEach(new Consumer() { // from class: com.android.tools.r8.internal.ud$$ExternalSyntheticLambda5
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                C3144ud.b(c3223vdA, (C2768pH) obj);
                            }
                        });
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c(C2768pH c2768pH, C2768pH c2768pH2) {
        ((Set) this.f.computeIfAbsent(c2768pH, new Function() { // from class: com.android.tools.r8.internal.ud$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C3144ud.a((C2768pH) obj);
            }
        })).add(c2768pH2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void d(C2768pH c2768pH, C2768pH c2768pH2) {
        ((Set) this.g.computeIfAbsent(c2768pH, new Function() { // from class: com.android.tools.r8.internal.ud$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C3144ud.b((C2768pH) obj);
            }
        })).add(c2768pH2);
    }

    public a a(Collection<C2768pH> collection) {
        boolean z;
        do {
            b(collection);
            collection = this.i;
            z = j;
            if (!z && !this.f.isEmpty()) {
                throw new AssertionError();
            }
            if (!z && !this.c.isEmpty()) {
                throw new AssertionError();
            }
            if (!z && !this.a.isEmpty()) {
                throw new AssertionError();
            }
            if (!z && !this.b.isEmpty()) {
                throw new AssertionError();
            }
            if (!z && !this.g.isEmpty()) {
                throw new AssertionError();
            }
            if (!z && !this.d.isEmpty()) {
                throw new AssertionError();
            }
            this.e.clear();
            this.i = new LinkedHashSet();
        } while (!collection.isEmpty());
        a aVar = new a(this.h);
        if (!z && !this.c.isEmpty()) {
            throw new AssertionError();
        }
        if (!z && !this.e.isEmpty()) {
            throw new AssertionError();
        }
        if (!z && !this.i.isEmpty()) {
            throw new AssertionError();
        }
        if (!z && !this.a.isEmpty()) {
            throw new AssertionError();
        }
        if (!z && !this.b.isEmpty()) {
            throw new AssertionError();
        }
        if (!z && !this.d.isEmpty()) {
            throw new AssertionError();
        }
        this.h = new IdentityHashMap();
        return aVar;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(C3223vd c3223vd, C2768pH c2768pH) {
        c2768pH.f(c3223vd.a);
        b(c3223vd.a, c2768pH);
    }

    private C2768pH a(C2768pH c2768pH, Iterator it) {
        while (true) {
            C3074td c3074td = null;
            if (!it.hasNext()) {
                return null;
            }
            C2768pH c2768pH2 = (C2768pH) it.next();
            C3377xd c3377xd = (C3377xd) this.b.get(c2768pH2);
            if (c3377xd == null) {
                return c2768pH2;
            }
            if (c2768pH2.d(c2768pH)) {
                d(c2768pH, c2768pH2);
            } else if (this.d.isEmpty() || !a((C2768pH) this.d.peek(), c2768pH2, c3377xd, new BiConsumer() { // from class: com.android.tools.r8.internal.ud$$ExternalSyntheticLambda6
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    this.f$0.d((C2768pH) obj, (C2768pH) obj2);
                }
            })) {
                if (c2768pH2.a().u1()) {
                    if (!j && !a(c2768pH, c2768pH2)) {
                        throw new AssertionError();
                    }
                    c(c2768pH, c2768pH2);
                } else if (this.c.isEmpty() || !a((C2768pH) this.c.peek(), c2768pH2, c3377xd, new BiConsumer() { // from class: com.android.tools.r8.internal.ud$$ExternalSyntheticLambda7
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        this.f$0.c((C2768pH) obj, (C2768pH) obj2);
                    }
                })) {
                    if (a(c2768pH, c2768pH2)) {
                        c(c2768pH, c2768pH2);
                    } else {
                        LinkedList linkedList = new LinkedList();
                        do {
                            if (!j && this.a.isEmpty()) {
                                throw new AssertionError();
                            }
                            linkedList.add((C2768pH) this.a.pop());
                        } while (linkedList.getLast() != c2768pH2);
                        C2768pH c2768pH3 = (C2768pH) linkedList.getLast();
                        Iterator it2 = linkedList.iterator();
                        while (true) {
                            if (it2.hasNext()) {
                                C2768pH c2768pH4 = (C2768pH) it2.next();
                                if (c2768pH4.e(c2768pH3)) {
                                    boolean z = j;
                                    if (!z && c2768pH4.b(c2768pH3)) {
                                        throw new AssertionError();
                                    }
                                    if (!z && c2768pH3.c(c2768pH4)) {
                                        throw new AssertionError();
                                    }
                                } else {
                                    if (!c2768pH4.b(c2768pH3)) {
                                        if (j || !c2768pH3.c(c2768pH4)) {
                                            break;
                                            break;
                                        }
                                        throw new AssertionError();
                                    }
                                    if (a(c2768pH4, c2768pH3)) {
                                        c3074td = new C3074td(c2768pH4, c2768pH3);
                                        break;
                                    }
                                }
                                c2768pH3 = c2768pH4;
                            } else {
                                throw new C1367Tb("Unable to satisfy force inlining constraints due to cyclic force inlining");
                            }
                        }
                        if (c3074td != null) {
                            if (!j && !a(c3074td.a, c3074td.b)) {
                                throw new AssertionError();
                            }
                            c(c3074td.a, c3074td.b);
                            this.i.add(c3074td.b);
                        }
                        Iterator itDescendingIterator = linkedList.descendingIterator();
                        while (itDescendingIterator.hasNext()) {
                            this.a.push((C2768pH) itDescendingIterator.next());
                        }
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void b(C3223vd c3223vd, C2768pH c2768pH) {
        c2768pH.g(c3223vd.a);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Set b(C2768pH c2768pH) {
        return OU.c();
    }

    private void b(C2768pH c2768pH, C2768pH c2768pH2) {
        ((CN) this.h.computeIfAbsent(c2768pH2.a(), new Function() { // from class: com.android.tools.r8.internal.ud$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C3144ud.a((com.android.tools.r8.graph.W0) obj);
            }
        })).add(c2768pH.b());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Set a(C2768pH c2768pH) {
        return OU.c();
    }

    private boolean a(final C2768pH c2768pH, C2768pH c2768pH2, C3377xd c3377xd, BiConsumer biConsumer) {
        final C3377xd c3377xd2 = (C3377xd) this.b.get(c2768pH);
        if (c3377xd2.a <= c3377xd.a) {
            return false;
        }
        boolean z = j;
        if (!z) {
            Predicate predicate = new Predicate() { // from class: com.android.tools.r8.internal.ud$$ExternalSyntheticLambda1
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return C3144ud.a(c2768pH, c3377xd2, (LinkedList) obj);
                }
            };
            LinkedList linkedList = new LinkedList();
            do {
                if (!j && this.a.isEmpty()) {
                    throw new AssertionError();
                }
                linkedList.add((C2768pH) this.a.pop());
            } while (linkedList.getLast() != c2768pH2);
            if (!z && !predicate.test(linkedList)) {
                throw new AssertionError();
            }
            Iterator itDescendingIterator = linkedList.descendingIterator();
            while (itDescendingIterator.hasNext()) {
                this.a.push((C2768pH) itDescendingIterator.next());
            }
        }
        if (!c3377xd2.c) {
            biConsumer.accept(c3377xd2.b, c2768pH);
            this.i.add(c2768pH);
            c3377xd2.c = true;
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(C2768pH c2768pH, C3377xd c3377xd, LinkedList linkedList) {
        return linkedList.contains(c2768pH) && linkedList.contains(c3377xd.b);
    }

    private static boolean a(C2768pH c2768pH, C2768pH c2768pH2) {
        if (j || c2768pH2.c(c2768pH)) {
            return !c2768pH2.a().i1().g();
        }
        throw new AssertionError();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ CN a(com.android.tools.r8.graph.W0 w0) {
        return CN.j(2);
    }
}
