package javaslang.control;

import java.io.Serializable;
import javaslang.Value;
import javaslang.collection.Iterator;

/* JADX INFO: loaded from: classes11.dex */
public interface Try<T> extends Serializable, Value<T> {
    public static final long serialVersionUID = 1;

    @Override // javaslang.Value
    T get();

    @Override // javaslang.Value
    boolean isEmpty();

    @Override // javaslang.Value
    default boolean isSingleValued() {
        return true;
    }

    boolean isSuccess();

    @Override // javaslang.Value
    String toString();

    @Override // javaslang.Value, java.lang.Iterable
    default Iterator<T> iterator() {
        return isSuccess() ? Iterator.of(get()) : Iterator.empty();
    }
}
