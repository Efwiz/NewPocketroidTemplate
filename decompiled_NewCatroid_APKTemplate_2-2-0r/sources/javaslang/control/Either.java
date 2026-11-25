package javaslang.control;

import java.io.Serializable;
import javaslang.Value;
import javaslang.collection.Iterator;

/* JADX INFO: loaded from: classes11.dex */
public interface Either<L, R> extends Serializable, Value<R> {
    public static final long serialVersionUID = 1;

    @Override // javaslang.Value
    R get();

    boolean isLeft();

    boolean isRight();

    @Override // javaslang.Value
    default boolean isSingleValued() {
        return true;
    }

    @Override // javaslang.Value
    String toString();

    @Override // javaslang.Value
    default boolean isEmpty() {
        return isLeft();
    }

    @Override // javaslang.Value, java.lang.Iterable
    default Iterator<R> iterator() {
        if (isRight()) {
            return Iterator.of(get());
        }
        return Iterator.empty();
    }
}
