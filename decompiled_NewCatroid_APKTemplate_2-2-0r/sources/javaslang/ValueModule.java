package javaslang;

import java.util.Collection;
import java.util.function.Function;
import javaslang.collection.Traversable;

/* JADX INFO: compiled from: Value.java */
/* JADX INFO: loaded from: classes11.dex */
interface ValueModule {
    static <T extends Traversable<V>, V> T toTraversable(Value<V> value, T t, Function<V, T> function, Function<Iterable<V>, T> function2) {
        if (value.isEmpty()) {
            return t;
        }
        if (value.isSingleValued()) {
            return function.apply(value.get());
        }
        return function2.apply(value);
    }

    static <T extends Collection<V>, V> T toJavaCollection(Value<V> value, T t) {
        if (!value.isEmpty()) {
            if (value.isSingleValued()) {
                t.add(value.get());
            } else {
                t.getClass();
                value.forEach(new ValueModule$$ExternalSyntheticLambda0(t));
            }
        }
        return t;
    }
}
