package javaslang.collection;

import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.function.Supplier;

/* JADX INFO: loaded from: classes11.dex */
public interface SortedSet<T> extends Set<T> {
    public static final long serialVersionUID = 1;

    @Override // javaslang.collection.Set
    SortedSet<T> add(T t);

    @Override // javaslang.collection.Set
    SortedSet<T> intersect(Set<? extends T> set);

    @Override // javaslang.collection.Set
    <U> SortedSet<U> map(Function<? super T, ? extends U> function);

    @Override // javaslang.collection.Set, javaslang.collection.Traversable
    SortedSet<T> tail();

    @Override // javaslang.collection.Set
    java.util.SortedSet<T> toJavaSet();

    static /* synthetic */ NoSuchElementException lambda$last$0() {
        return new NoSuchElementException("last on empty SortedSet");
    }

    @Override // javaslang.collection.Traversable
    default T last() {
        return max().getOrElseThrow(new Supplier() { // from class: javaslang.collection.SortedSet$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return SortedSet.lambda$last$0();
            }
        });
    }
}
