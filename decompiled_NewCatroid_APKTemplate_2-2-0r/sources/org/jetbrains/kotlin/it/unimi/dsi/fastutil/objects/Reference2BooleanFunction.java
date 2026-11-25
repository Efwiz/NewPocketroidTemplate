package org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects;

import java.util.function.Predicate;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function;

/* JADX INFO: loaded from: classes5.dex */
@FunctionalInterface
public interface Reference2BooleanFunction<K> extends Function<K, Boolean>, Predicate<K> {
    default boolean defaultReturnValue() {
        return false;
    }

    boolean getBoolean(Object obj);

    @Override // java.util.function.Predicate
    default boolean test(K k) {
        return getBoolean(k);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function
    @Deprecated
    default Boolean get(Object obj) {
        boolean z = getBoolean(obj);
        if (z != defaultReturnValue() || containsKey(obj)) {
            return Boolean.valueOf(z);
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.function.Function
    @Deprecated
    default <T> java.util.function.Function<K, T> andThen(java.util.function.Function<? super Boolean, ? extends T> function) {
        return super.andThen(function);
    }
}
