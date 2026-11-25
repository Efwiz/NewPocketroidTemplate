package org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects;

import java.util.function.ToIntFunction;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function;

/* JADX INFO: loaded from: classes5.dex */
@FunctionalInterface
public interface Object2CharFunction<K> extends Function<K, Character>, ToIntFunction<K> {
    default char defaultReturnValue() {
        return (char) 0;
    }

    char getChar(Object obj);

    @Override // java.util.function.ToIntFunction
    default int applyAsInt(K k) {
        return getChar(k);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function
    @Deprecated
    default Character get(Object obj) {
        char c = getChar(obj);
        if (c != defaultReturnValue() || containsKey(obj)) {
            return Character.valueOf(c);
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.function.Function
    @Deprecated
    default <T> java.util.function.Function<K, T> andThen(java.util.function.Function<? super Character, ? extends T> function) {
        return super.andThen(function);
    }
}
