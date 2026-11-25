package javaslang.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

/* JADX INFO: loaded from: classes11.dex */
final class Collections {
    public static <T, C, R extends Iterable<T>> Map<C, R> groupBy(Traversable<T> traversable, Function<? super T, ? extends C> function, Function<? super Iterable<T>, R> function2) {
        Objects.requireNonNull(traversable, "collection is null");
        Objects.requireNonNull(function, "classifier is null");
        Objects.requireNonNull(function2, "mapper is null");
        java.util.HashMap map = new java.util.HashMap();
        Iterator<T> it = traversable.iterator();
        while (it.hasNext()) {
            T next = it.next();
            ((Collection) map.computeIfAbsent(function.apply(next), new Function() { // from class: javaslang.collection.Collections$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return Collections.lambda$groupBy$1(obj);
                }
            })).add(next);
        }
        HashMap hashMapEmpty = HashMap.empty();
        for (java.util.Map.Entry entry : map.entrySet()) {
            hashMapEmpty = hashMapEmpty.put(entry.getKey(), function2.apply((Object) entry.getValue()));
        }
        return hashMapEmpty;
    }

    static /* synthetic */ Collection lambda$groupBy$1(Object obj) {
        return new ArrayList();
    }

    static boolean equals(Iterable<?> iterable, Iterable<?> iterable2) {
        java.util.Iterator<?> it = iterable.iterator();
        java.util.Iterator<?> it2 = iterable2.iterator();
        while (it.hasNext() && it2.hasNext()) {
            if (!Objects.equals(it.next(), it2.next())) {
                return false;
            }
        }
        return it.hasNext() == it2.hasNext();
    }

    static int hash(Iterable<?> iterable) {
        java.util.Iterator<?> it = iterable.iterator();
        int iHashCode = 1;
        while (it.hasNext()) {
            iHashCode = (iHashCode * 31) + Objects.hashCode(it.next());
        }
        return iHashCode;
    }

    static <T, U, C extends Iterable<U>, R extends Traversable<U>> R scanLeft(Iterable<? extends T> iterable, U u, BiFunction<? super U, ? super T, ? extends U> biFunction, C c, BiFunction<C, U, C> biFunction2, Function<C, R> function) {
        C cApply = biFunction2.apply(c, u);
        java.util.Iterator<? extends T> it = iterable.iterator();
        while (it.hasNext()) {
            u = biFunction.apply(u, it.next());
            cApply = biFunction2.apply(cApply, u);
        }
        return function.apply(cApply);
    }
}
