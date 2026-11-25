package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0753u;
import com.android.tools.r8.graph.C0767v2;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

/* JADX INFO: renamed from: com.android.tools.r8.internal.f7, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1974f7 {
    static final /* synthetic */ boolean c = true;
    private final IdentityHashMap a = new IdentityHashMap();
    private final C0753u b;

    /* JADX INFO: renamed from: $r8$lambda$wBZ5N9SAmxJLRX-vx8hIUeo_fgc, reason: not valid java name */
    public static /* synthetic */ ArrayList m1406$r8$lambda$wBZ5N9SAmxJLRXvx8hIUeo_fgc() {
        return new ArrayList();
    }

    C1974f7(C0753u c0753u) {
        this.b = c0753u;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean a(M7 m7, W8 w8) {
        return w8.b == m7;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean b(M7 m7, W8 w8) {
        return w8.b != m7;
    }

    private int a(B7 b7, int i, final I6 i6, ArrayDeque arrayDeque, IdentityHashMap identityHashMap) {
        i6.a = i;
        b7.getClass();
        if (b7 instanceof M7) {
            a(b7.q(), arrayDeque, identityHashMap);
        }
        final LinkedHashMap linkedHashMap = new LinkedHashMap();
        Iterator itDescendingIterator = arrayDeque.descendingIterator();
        while (itDescendingIterator.hasNext()) {
            ((W8) itDescendingIterator.next()).a(new BiConsumer() { // from class: com.android.tools.r8.internal.f7$$ExternalSyntheticLambda4
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    linkedHashMap.putIfAbsent((C0767v2) obj, (M7) obj2);
                }
            });
        }
        while (true) {
            if (!c) {
                b7.getClass();
                if (b7 instanceof M7) {
                    b(b7.q(), arrayDeque, identityHashMap);
                }
            }
            boolean zContainsKey = true;
            if (b7.x()) {
                if (!(i6.b >= 0)) {
                    i6.b = i;
                }
            }
            if (i != this.b.I0().size() - 1) {
                zContainsKey = this.a.containsKey(this.b.l(i + 1));
            }
            if (zContainsKey) {
                break;
            }
            i++;
            b7 = this.b.l(i);
        }
        i6.c = i;
        C0753u c0753u = this.b;
        int i2 = i + 1;
        b7.a(new Consumer() { // from class: com.android.tools.r8.internal.f7$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(i6, (B7) obj);
            }
        }, i2 < c0753u.I0().size() ? c0753u.I0().get(i2) : null);
        linkedHashMap.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.f7$$ExternalSyntheticLambda6
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.a(i6, (C0767v2) obj, (M7) obj2);
            }
        });
        return i;
    }

    private void b() {
        ArrayDeque arrayDeque = new ArrayDeque();
        IdentityHashMap identityHashMap = new IdentityHashMap();
        for (W8 w8 : this.b.Q0()) {
            ((List) identityHashMap.computeIfAbsent(w8.a, com.android.tools.r8.utils.Z1.a(new Supplier() { // from class: com.android.tools.r8.internal.f7$$ExternalSyntheticLambda0
                @Override // java.util.function.Supplier
                public final Object get() {
                    return C1974f7.m1406$r8$lambda$wBZ5N9SAmxJLRXvx8hIUeo_fgc();
                }
            }))).add(w8);
        }
        List<B7> listI0 = this.b.I0();
        int iA = 0;
        while (iA < listI0.size()) {
            B7 b7 = listI0.get(iA);
            I6 i6 = (I6) this.a.get(b7);
            if (i6 != null) {
                iA = a(b7, iA, i6, arrayDeque, identityHashMap);
            }
            iA++;
        }
        boolean z = c;
        if (!z && !arrayDeque.isEmpty()) {
            throw new AssertionError();
        }
        if (!z && !identityHashMap.isEmpty()) {
            throw new AssertionError();
        }
    }

    private static void b(final M7 m7, ArrayDeque arrayDeque, IdentityHashMap identityHashMap) {
        boolean z = c;
        if (!z && !arrayDeque.stream().allMatch(new Predicate() { // from class: com.android.tools.r8.internal.f7$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return C1974f7.b(m7, (W8) obj);
            }
        })) {
            throw new AssertionError();
        }
        if (!z && identityHashMap.containsKey(m7)) {
            throw new AssertionError();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean a(C2050g7 c2050g7, com.android.tools.r8.utils.A1 a1, I6 i6) {
        int i;
        int i2;
        boolean z = I6.g;
        if (!z && i6.a < 0) {
            throw new AssertionError();
        }
        if (!z && i6.a > i6.c) {
            throw new AssertionError();
        }
        if (!z && (i2 = i6.b) >= 0 && i6.a > i2) {
            throw new AssertionError();
        }
        if (!z && (i = i6.b) >= 0 && i > i6.c) {
            throw new AssertionError();
        }
        if (!z) {
            if (com.android.tools.r8.utils.T2.a((Iterable) i6.d).size() != i6.d.size()) {
                throw new AssertionError();
            }
        } else {
            i6.getClass();
        }
        if (z || i6 == c2050g7.a() || !i6.d.isEmpty() || !i6.e.isEmpty() || a1.r0().a()) {
            return true;
        }
        throw new AssertionError();
    }

    final C2050g7 a(final com.android.tools.r8.utils.A1 a1) {
        a();
        b();
        C0753u c0753u = this.b;
        B7 b7L = c0753u.l(c0753u.I0().size() - 1);
        b7L.getClass();
        if ((b7L instanceof M7) && this.a.containsKey(b7L)) {
            this.a.remove(b7L);
        }
        IdentityHashMap identityHashMap = this.a;
        final C2050g7 c2050g7 = new C2050g7(identityHashMap, this.b);
        if (c || identityHashMap.values().stream().allMatch(new Predicate() { // from class: com.android.tools.r8.internal.f7$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return C1974f7.a(c2050g7, a1, (I6) obj);
            }
        })) {
            return c2050g7;
        }
        throw new AssertionError();
    }

    private void a() {
        List<B7> listI0 = this.b.I0();
        a(listI0.get(0));
        for (int i = 0; i < listI0.size(); i++) {
            B7 b7 = listI0.get(i);
            if (b7.M()) {
                int i2 = i + 1;
                B7 b8 = i2 < listI0.size() ? listI0.get(i2) : null;
                b7.a(new Consumer() { // from class: com.android.tools.r8.internal.f7$$ExternalSyntheticLambda8
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        this.f$0.a((B7) obj);
                    }
                }, b8);
                if (!b7.p().T() && b8 != null) {
                    a(b8);
                }
            }
        }
        for (W8 w8 : this.b.Q0()) {
            a(w8.a);
            a(w8.b);
            w8.a(new Consumer() { // from class: com.android.tools.r8.internal.f7$$ExternalSyntheticLambda9
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.a((M7) obj);
                }
            });
        }
    }

    private static void a(final M7 m7, ArrayDeque arrayDeque, IdentityHashMap identityHashMap) {
        arrayDeque.removeIf(new Predicate() { // from class: com.android.tools.r8.internal.f7$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return C1974f7.a(m7, (W8) obj);
            }
        });
        List list = (List) identityHashMap.remove(m7);
        if (list != null) {
            for (int size = list.size() - 1; size >= 0; size--) {
                W8 w8 = (W8) list.get(size);
                if (!c && w8.b == w8.a) {
                    throw new AssertionError();
                }
                arrayDeque.addLast(w8);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(B7 b7) {
        this.a.computeIfAbsent(b7, com.android.tools.r8.utils.Z1.a(new Supplier() { // from class: com.android.tools.r8.internal.f7$$ExternalSyntheticLambda7
            @Override // java.util.function.Supplier
            public final Object get() {
                return new I6();
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(I6 i6, B7 b7) {
        if (!c && !this.a.containsKey(b7)) {
            throw new AssertionError();
        }
        ((I6) this.a.get(b7)).d.add(i6);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(I6 i6, C0767v2 c0767v2, M7 m7) {
        if (!c && !this.a.containsKey(m7)) {
            throw new AssertionError();
        }
        I6 i7 = (I6) this.a.get(m7);
        if (!I6.g && i6.f.containsKey(c0767v2)) {
            throw new AssertionError();
        }
        i6.f.put(c0767v2, i7);
        i7.e.add(i6);
    }
}
