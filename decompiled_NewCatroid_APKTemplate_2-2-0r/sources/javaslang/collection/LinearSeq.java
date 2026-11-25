package javaslang.collection;

import java.util.function.Function;

/* JADX INFO: loaded from: classes11.dex */
public interface LinearSeq<T> extends Seq<T> {
    public static final long serialVersionUID = 1;

    @Override // javaslang.collection.Seq
    <U> LinearSeq<U> map(Function<? super T, ? extends U> function);

    @Override // javaslang.collection.Seq, javaslang.collection.Traversable
    LinearSeq<T> tail();
}
