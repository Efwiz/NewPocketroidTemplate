package javaslang.collection;

import javaslang.Tuple2;

/* JADX INFO: loaded from: classes11.dex */
public interface Stack<T> {
    boolean isEmpty();

    Tuple2<T, ? extends Stack<T>> pop2();

    Stack<T> push(T t);
}
