package com.android.tools.r8.retrace;

import com.android.tools.r8.retrace.RetraceElement;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public interface RetraceResult<E extends RetraceElement<?>> {
    default <T> Stream<T> flatMap(Function<? super E, Stream<T>> function) {
        return (Stream<T>) stream().flatMap(function);
    }

    /* JADX WARN: Multi-variable type inference failed */
    default void forEach(Consumer<E> consumer) {
        stream().forEach(consumer);
    }

    default boolean isAmbiguous() {
        return stream().findFirst().isPresent() && stream().skip(1L).findFirst().isPresent();
    }

    boolean isEmpty();

    Stream<E> stream();
}
