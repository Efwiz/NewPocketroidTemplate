package javaslang.collection;

import java.io.Serializable;
import java.util.function.Function;
import javaslang.Function1;

/* JADX INFO: loaded from: classes11.dex */
public interface Seq<T> extends Serializable, Function1<Integer, T>, Traversable<T> {
    public static final long serialVersionUID = 1;

    T get(int i);

    <U> Seq<U> map(Function<? super T, ? extends U> function);

    Seq<T> tail();

    @Override // javaslang.Function1, java.util.function.Function
    default T apply(Integer num) {
        return get(num.intValue());
    }
}
