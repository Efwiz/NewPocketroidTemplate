package org.apache.commons.io.function;

import java.util.Objects;
import java.util.Spliterator;
import net.bytebuddy.implementation.MethodDelegation;

/* JADX INFO: loaded from: classes13.dex */
final class IOSpliteratorAdapter<T> implements IOSpliterator<T> {
    private final Spliterator<T> delegate;

    static <E> IOSpliteratorAdapter<E> adapt(Spliterator<E> spliterator) {
        return new IOSpliteratorAdapter<>(spliterator);
    }

    IOSpliteratorAdapter(Spliterator<T> spliterator) {
        this.delegate = (Spliterator) Objects.requireNonNull(spliterator, MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX);
    }

    @Override // org.apache.commons.io.function.IOSpliterator
    public Spliterator<T> unwrap() {
        return this.delegate;
    }
}
