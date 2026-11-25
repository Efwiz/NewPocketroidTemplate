package com.android.tools.r8.internal;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/* JADX INFO: renamed from: com.android.tools.r8.internal.zN, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC3501zN {
    private final Map a;

    AbstractC3501zN(Supplier supplier) {
        this.a = (Map) supplier.get();
    }

    public final void a() {
        this.a.clear();
    }

    public final Object b(com.android.tools.r8.graph.n5 n5Var) {
        return this.a.get(d(n5Var));
    }

    public final Object c(com.android.tools.r8.graph.n5 n5Var) {
        return this.a.remove(d(n5Var));
    }

    abstract C2173hh d(com.android.tools.r8.graph.n5 n5Var);

    public final void a(final com.android.tools.r8.graph.n5 n5Var, final BiFunction biFunction) {
        this.a.compute(d(n5Var), new BiFunction() { // from class: com.android.tools.r8.internal.zN$$ExternalSyntheticLambda2
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return AbstractC3501zN.a(biFunction, n5Var, (C2173hh) obj, obj2);
            }
        });
    }

    public final int b() {
        return this.a.size();
    }

    AbstractC3501zN(HashMap map) {
        this.a = map;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Object a(BiFunction biFunction, com.android.tools.r8.graph.n5 n5Var, C2173hh c2173hh, Object obj) {
        return biFunction.apply(n5Var, obj);
    }

    public final Object a(com.android.tools.r8.graph.n5 n5Var, final Function function) {
        return this.a.computeIfAbsent(d(n5Var), new Function() { // from class: com.android.tools.r8.internal.zN$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return AbstractC3501zN.a(function, (C2173hh) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Object a(Function function, C2173hh c2173hh) {
        return function.apply((com.android.tools.r8.graph.n5) c2173hh.a());
    }

    public final boolean a(com.android.tools.r8.graph.n5 n5Var) {
        return this.a.containsKey(d(n5Var));
    }

    public final void a(final BiConsumer biConsumer) {
        this.a.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.zN$$ExternalSyntheticLambda3
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                AbstractC3501zN.a(biConsumer, (C2173hh) obj, obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(BiConsumer biConsumer, C2173hh c2173hh, Object obj) {
        biConsumer.accept((com.android.tools.r8.graph.n5) c2173hh.a(), obj);
    }

    public final Object a(com.android.tools.r8.graph.n5 n5Var, Supplier supplier) {
        Object obj = this.a.get(d(n5Var));
        return obj != null ? obj : supplier.get();
    }

    public final Object a(com.android.tools.r8.graph.n5 n5Var, Object obj) {
        return this.a.put(d(n5Var), obj);
    }

    public final boolean a(final BiPredicate biPredicate) {
        return this.a.entrySet().removeIf(new Predicate() { // from class: com.android.tools.r8.internal.zN$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return AbstractC3501zN.a(biPredicate, (Map.Entry) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(BiPredicate biPredicate, Map.Entry entry) {
        return biPredicate.test((com.android.tools.r8.graph.n5) ((C2173hh) entry.getKey()).a(), entry.getValue());
    }
}
