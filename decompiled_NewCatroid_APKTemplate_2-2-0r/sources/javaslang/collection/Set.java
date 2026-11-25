package javaslang.collection;

import java.io.Serializable;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Function;
import javaslang.Function1;

/* JADX INFO: loaded from: classes11.dex */
public interface Set<T> extends Serializable, Function1<T, Boolean>, Traversable<T> {
    public static final long serialVersionUID = 1;

    Set<T> add(T t);

    boolean contains(T t);

    Set<T> intersect(Set<? extends T> set);

    @Override // javaslang.collection.Traversable, javaslang.Value, java.lang.Iterable
    Iterator<T> iterator();

    int length();

    <U> Set<U> map(Function<? super T, ? extends U> function);

    @Override // javaslang.collection.Traversable
    Set<T> tail();

    java.util.Set<T> toJavaSet();

    @Override // javaslang.Function1, java.util.function.Function
    default Boolean apply(T t) {
        return Boolean.valueOf(contains(t));
    }

    @Override // java.lang.Iterable
    default Spliterator<T> spliterator() {
        return Spliterators.spliterator(iterator(), length(), 1040);
    }
}
