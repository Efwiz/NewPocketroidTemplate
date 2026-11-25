package com.android.tools.r8.utils;

import com.android.tools.r8.internal.AbstractC2178hm;
import com.android.tools.r8.internal.C1946em;
import java.util.Collections;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public class Z1 {
    public static Map a(Map map, final HashMap map2, final Function function) {
        map.forEach(new BiConsumer() { // from class: com.android.tools.r8.utils.Z1$$ExternalSyntheticLambda5
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                Z1.a(map2, function, obj, obj2);
            }
        });
        return map2;
    }

    public static AbstractC2178hm b(Consumer consumer) {
        C1946em c1946emE = AbstractC2178hm.e();
        consumer.accept(c1946emE);
        return c1946emE.a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Map map, Function function, Object obj, Object obj2) {
        map.put(obj, function.apply(obj2));
    }

    public static <T, R> Function<T, R> a(final Supplier<R> supplier) {
        return new Function() { // from class: com.android.tools.r8.utils.Z1$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Z1.a(supplier, obj);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Object a(Supplier supplier, Object obj) {
        return supplier.get();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ String b(Map.Entry entry) {
        return entry.getKey() + ":" + entry.getValue();
    }

    public static IdentityHashMap a(Consumer consumer) {
        IdentityHashMap identityHashMap = new IdentityHashMap();
        consumer.accept(identityHashMap);
        return identityHashMap;
    }

    public static String b(Map map) {
        return Y2.a(",", map.entrySet(), new Function() { // from class: com.android.tools.r8.utils.Z1$$ExternalSyntheticLambda8
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Z1.b((Map.Entry) obj);
            }
        }, Y2.a.TUBORG);
    }

    public static IdentityHashMap a(F f, int i) {
        final IdentityHashMap identityHashMap = new IdentityHashMap(i);
        f.forEach(new BiConsumer() { // from class: com.android.tools.r8.utils.Z1$$ExternalSyntheticLambda6
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                identityHashMap.put(obj, obj2);
            }
        });
        return identityHashMap;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Object b(Function function, Object obj, Object obj2) {
        return function.apply(obj2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(Map.Entry entry) {
        return entry.getKey() == entry.getValue();
    }

    public static void a(Map map) {
        map.entrySet().removeIf(new Predicate() { // from class: com.android.tools.r8.utils.Z1$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return Z1.a((Map.Entry) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(BiPredicate biPredicate, Map.Entry entry) {
        return biPredicate.test(entry.getKey(), entry.getValue());
    }

    public static void a(Map map, final BiPredicate biPredicate) {
        map.entrySet().removeIf(new Predicate() { // from class: com.android.tools.r8.utils.Z1$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return Z1.a(biPredicate, (Map.Entry) obj);
            }
        });
    }

    public static <K1, V1, K2, V2> Map<K2, V2> a(Map<K1, V1> map, IntFunction<Map<K2, V2>> intFunction, final Function<K1, K2> function, final Function<V1, V2> function2, G3<K2, V2, V2, V2> g3) {
        return a(map, intFunction, new BiFunction() { // from class: com.android.tools.r8.utils.Z1$$ExternalSyntheticLambda3
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return Z1.a(function, obj, obj2);
            }
        }, new BiFunction() { // from class: com.android.tools.r8.utils.Z1$$ExternalSyntheticLambda4
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return Z1.b(function2, obj, obj2);
            }
        }, g3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Object a(Function function, Object obj, Object obj2) {
        return function.apply(obj);
    }

    public static Map a(Map map, IntFunction intFunction, final BiFunction biFunction, final BiFunction biFunction2, final G3 g3) {
        final Map map2 = (Map) intFunction.apply(map.size());
        map.forEach(new BiConsumer() { // from class: com.android.tools.r8.utils.Z1$$ExternalSyntheticLambda7
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                Z1.a(biFunction, biFunction2, map2, g3, obj, obj2);
            }
        });
        return map2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(BiFunction biFunction, BiFunction biFunction2, Map map, G3 g3, Object obj, Object obj2) {
        Object objApply;
        Object objPut;
        Object objApply2 = biFunction.apply(obj, obj2);
        if (objApply2 == null || (objPut = map.put(objApply2, (objApply = biFunction2.apply(obj, obj2)))) == null) {
            return;
        }
        map.put(objApply2, g3.a(objApply2, objPut, objApply));
    }

    public static Map a(LinkedHashMap linkedHashMap) {
        return A1.b() ? Collections.unmodifiableMap(linkedHashMap) : linkedHashMap;
    }
}
