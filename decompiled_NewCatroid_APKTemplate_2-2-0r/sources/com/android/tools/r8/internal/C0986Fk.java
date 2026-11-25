package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0642e2;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Fk, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C0986Fk {
    static final /* synthetic */ boolean h = true;
    private final M4 a = M4.d();
    final K4 b = K4.b();
    private final M4 c = M4.d();
    private final IdentityHashMap d = new IdentityHashMap();
    private final K4 e = K4.b();
    private final M4 f = M4.d();
    private final M4 g = M4.d();

    C0986Fk() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean b(com.android.tools.r8.graph.Y0 y0, com.android.tools.r8.graph.Y0 y1) {
        return y1 != y0;
    }

    final C1013Gk a(C0702m c0702m, C1105Jk c1105Jk) {
        boolean z = h;
        if (!z && !this.e.isEmpty()) {
            throw new AssertionError();
        }
        if (!z && !this.f.isEmpty()) {
            throw new AssertionError();
        }
        if (!z && !this.g.isEmpty()) {
            throw new AssertionError();
        }
        if (z || this.c.mo1078values().stream().allMatch(new Predicate() { // from class: com.android.tools.r8.internal.Fk$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return this.f$0.b((C0684j2) obj);
            }
        })) {
            return new C1013Gk(c0702m, c1105Jk, this.d, this.a, this.b.e(), this.c);
        }
        throw new AssertionError();
    }

    final void c(com.android.tools.r8.graph.Y0 y0, com.android.tools.r8.graph.Y0 y1) {
        this.a.a(y0, y1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean b(C0684j2 c0684j2) {
        if (h || this.c.a((Object) c0684j2).size() == 1 || this.c.f(c0684j2)) {
            return true;
        }
        throw new AssertionError();
    }

    final void c(C0684j2 c0684j2, C0684j2 c0684j3) {
        a(c0684j2, c0684j3, false);
    }

    final void b(C0684j2 c0684j2, C0684j2 c0684j3) {
        this.b.a(c0684j2, c0684j3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ List b(List list, C0684j2 c0684j2) {
        return new ArrayList(list.size());
    }

    final C0684j2 a(C0684j2 c0684j2) {
        if (h || this.c.containsKey(c0684j2)) {
            return (C0684j2) this.c.get(c0684j2);
        }
        throw new AssertionError();
    }

    final void a(Iterable iterable, final com.android.tools.r8.graph.Y0 y0, com.android.tools.r8.graph.Y0 y1) {
        boolean z = h;
        if (!z && !AbstractC1699bX.a(iterable).anyMatch(new Predicate() { // from class: com.android.tools.r8.internal.Fk$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return C0986Fk.b(y0, (com.android.tools.r8.graph.Y0) obj);
            }
        })) {
            throw new AssertionError();
        }
        if (!z) {
            Stream streamA = AbstractC1699bX.a(iterable);
            final M4 m4 = this.a;
            Objects.requireNonNull(m4);
            if (!streamA.noneMatch(new Predicate() { // from class: com.android.tools.r8.internal.Fk$$ExternalSyntheticLambda1
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return m4.containsValue((com.android.tools.r8.graph.Y0) obj);
                }
            })) {
                throw new AssertionError();
            }
        }
        if (!z && !AbstractC2734ot.a(iterable, y1)) {
            throw new AssertionError();
        }
        Iterator it = iterable.iterator();
        while (it.hasNext()) {
            this.a.a((com.android.tools.r8.graph.Y0) it.next(), y0);
        }
        this.a.b(y0, y1);
    }

    final void a(com.android.tools.r8.graph.Y0 y0, com.android.tools.r8.graph.Y0 y1) {
        M4 m4 = this.a;
        M4 m5 = this.f;
        Set setA = m4.a(y0);
        if (setA.isEmpty()) {
            m5.a(y0, y1);
            return;
        }
        Iterator it = setA.iterator();
        while (it.hasNext()) {
            m5.a((AbstractC0642e2) it.next(), y1);
        }
        AbstractC0642e2 abstractC0642e2 = (AbstractC0642e2) m4.c(y0);
        if (abstractC0642e2 != null) {
            m5.b(y1, abstractC0642e2);
        }
    }

    final void a(C0684j2 c0684j2, C0684j2 c0684j3, ArrayList arrayList) {
        this.b.a(c0684j2, c0684j3);
        if (arrayList.size() > 0) {
            this.d.put(c0684j2, arrayList);
        }
    }

    final void a(C0684j2 c0684j2, C0684j2 c0684j3, boolean z) {
        this.c.a(c0684j2, c0684j3);
        if (z) {
            this.c.b(c0684j3, c0684j2);
        }
    }

    final void a(C0684j2 c0684j2, C0684j2 c0684j3) {
        Set setA = this.b.a((Object) c0684j2);
        if (setA.isEmpty()) {
            this.e.a(c0684j2, c0684j3);
        } else {
            Iterator it = setA.iterator();
            while (it.hasNext()) {
                this.e.a((C0684j2) it.next(), c0684j3);
            }
        }
        M4 m4 = this.c;
        M4 m5 = this.g;
        Set setA2 = m4.a((Object) c0684j2);
        if (setA2.isEmpty()) {
            m5.a(c0684j2, c0684j3);
            return;
        }
        Iterator it2 = setA2.iterator();
        while (it2.hasNext()) {
            m5.a((AbstractC0642e2) it2.next(), c0684j3);
        }
        AbstractC0642e2 abstractC0642e2 = (AbstractC0642e2) m4.c(c0684j2);
        if (abstractC0642e2 != null) {
            m5.b(c0684j3, abstractC0642e2);
        }
    }

    final void a() {
        this.b.a(this.e.keySet());
        K4 k4 = this.e;
        final K4 k5 = this.b;
        Objects.requireNonNull(k5);
        k4.a(new BiConsumer() { // from class: com.android.tools.r8.internal.Fk$$ExternalSyntheticLambda4
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                k5.a((Set) obj, (C0684j2) obj2);
            }
        });
        this.e.a();
        M4 m4 = this.a;
        M4 m5 = this.f;
        m4.a(m5.keySet());
        m4.a(m5);
        m5.a();
        M4 m6 = this.c;
        M4 m7 = this.g;
        m6.a(m7.keySet());
        m6.a(m7);
        m7.a();
    }

    final void a(C0684j2 c0684j2, final List list) {
        Set setA = this.b.a((Object) c0684j2);
        if (setA.isEmpty()) {
            ((List) this.d.computeIfAbsent(c0684j2, new Function() { // from class: com.android.tools.r8.internal.Fk$$ExternalSyntheticLambda2
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return C0986Fk.a(list, (C0684j2) obj);
                }
            })).addAll(list);
            return;
        }
        Iterator it = setA.iterator();
        while (it.hasNext()) {
            ((List) this.d.computeIfAbsent((C0684j2) it.next(), new Function() { // from class: com.android.tools.r8.internal.Fk$$ExternalSyntheticLambda3
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return C0986Fk.b(list, (C0684j2) obj);
                }
            })).addAll(list);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ List a(List list, C0684j2 c0684j2) {
        return new ArrayList(list.size());
    }
}
