package javaslang.collection;

import java.util.NoSuchElementException;
import java.util.function.Function;

/* JADX INFO: loaded from: classes11.dex */
public interface IndexedSeq<T> extends Seq<T> {
    public static final long serialVersionUID = 1;

    @Override // javaslang.collection.Seq
    <U> IndexedSeq<U> map(Function<? super T, ? extends U> function);

    @Override // javaslang.collection.Seq, javaslang.collection.Traversable
    IndexedSeq<T> tail();

    @Override // javaslang.collection.Traversable
    default T last() {
        if (isEmpty()) {
            throw new NoSuchElementException("last of empty IndexedSeq");
        }
        return get(length() - 1);
    }
}
