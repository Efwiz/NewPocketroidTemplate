package org.jf.util.collection;

import java.util.AbstractList;
import java.util.List;
import java.util.function.Function;

/* JADX INFO: loaded from: classes6.dex */
public class ComputingList<E, T> extends AbstractList<T> {
    private final Function<E, T> function;
    private final List<? extends E> list;

    public ComputingList(List<? extends E> list, Function<E, T> function) {
        this.list = list;
        this.function = function;
    }

    @Override // java.util.AbstractList, java.util.List
    public T get(int i) {
        return (T) this.function.apply(this.list.get(i));
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public int size() {
        return this.list.size();
    }
}
