package com.android.tools.r8.graph;

import com.android.tools.r8.internal.AbstractC1476Wj;
import com.android.tools.r8.utils.AbstractC3899g2;
import java.util.AbstractMap;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class T2 implements S2 {
    static final /* synthetic */ boolean b = true;
    private final Map a;

    public T2() {
        this(new IdentityHashMap());
    }

    public final void a() {
        this.a.values().forEach(new Consumer() { // from class: com.android.tools.r8.graph.T2$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((U2) obj).c();
            }
        });
    }

    /* JADX INFO: renamed from: c, reason: merged with bridge method [inline-methods] */
    public final U2 b(Y0 y0) {
        return (U2) this.a.get(y0);
    }

    public final void d(Y0 y0) {
        this.a.remove(y0);
    }

    public T2(AbstractMap abstractMap) {
        this.a = abstractMap;
    }

    public final U2 a(Y0 y0, Function function) {
        return (U2) this.a.computeIfAbsent(y0, function);
    }

    public final void b() {
        this.a.values().forEach(new Consumer() { // from class: com.android.tools.r8.graph.T2$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((U2) obj).d();
            }
        });
    }

    public final void c() {
        if (!b && this.a.values().size() != com.android.tools.r8.utils.T2.a((Iterable) this.a.values()).size()) {
            throw new AssertionError();
        }
    }

    public final boolean a(Y0 y0) {
        return this.a.containsKey(y0);
    }

    public final U2 a(Y0 y0, U2 u2) {
        if (!b && this.a.containsKey(y0)) {
            throw new AssertionError();
        }
        this.a.put(y0, u2);
        return u2;
    }

    public final void a(Consumer consumer) {
        if (!b) {
            c();
        }
        this.a.values().forEach(consumer);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(BiPredicate biPredicate, Map.Entry entry) {
        return biPredicate.test((Y0) entry.getKey(), (U2) entry.getValue());
    }

    public final void a(final BiPredicate biPredicate) {
        this.a.entrySet().removeIf(new Predicate() { // from class: com.android.tools.r8.graph.T2$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return T2.a(biPredicate, (Map.Entry) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(Q0 q0, Y0 y0, U2 u2) {
        return !q0.a(y0).e0();
    }

    public final void a(final C0702m c0702m) {
        a(new BiPredicate() { // from class: com.android.tools.r8.graph.T2$$ExternalSyntheticLambda1
            @Override // java.util.function.BiPredicate
            public final boolean test(Object obj, Object obj2) {
                return T2.a(c0702m, (Y0) obj, (U2) obj2);
            }
        });
    }

    public final T2 a(final Q0 q0, final AbstractC1476Wj abstractC1476Wj) {
        final T2 t2 = new T2();
        this.a.values().forEach(new Consumer() { // from class: com.android.tools.r8.graph.T2$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                T2.a(q0, abstractC1476Wj, t2, (U2) obj);
            }
        });
        return t2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Q0 q0, AbstractC1476Wj abstractC1476Wj, T2 t2, U2 u2) {
        final U2 u2A = u2.a(q0, abstractC1476Wj);
        t2.a.compute(u2A.e(), new BiFunction() { // from class: com.android.tools.r8.graph.T2$$ExternalSyntheticLambda2
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return T2.a(u2A, (Y0) obj, (U2) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ U2 a(final U2 u2, Y0 y0, U2 u3) {
        Objects.requireNonNull(u2);
        return (U2) AbstractC3899g2.a(new Function() { // from class: com.android.tools.r8.graph.T2$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return u2.a((U2) obj);
            }
        }, u3, u2);
    }
}
