package javaslang.collection;

import java.io.Serializable;
import java.util.Comparator;

/* JADX INFO: compiled from: Comparators.java */
/* JADX INFO: loaded from: classes11.dex */
final class NaturalComparator<T> implements Serializable, Comparator<T> {
    private static final NaturalComparator<?> INSTANCE = new NaturalComparator<>();
    private static final long serialVersionUID = 1;

    private NaturalComparator() {
    }

    static <T> NaturalComparator<T> instance() {
        return (NaturalComparator<T>) INSTANCE;
    }

    @Override // java.util.Comparator
    public int compare(T t, T t2) {
        return ((Comparable) t).compareTo(t2);
    }

    private Object readResolve() {
        return INSTANCE;
    }
}
