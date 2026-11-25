package org.jf.util.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

/* JADX INFO: loaded from: classes6.dex */
public class ListUtil {
    private static final NaturalSort NATURAL_SORT = new NaturalSort(false);
    private static final NaturalSort INVERT_SORT = new NaturalSort(false);
    private static final Comparator<?> TO_STRING_COMPARATOR = new Comparator<Object>() { // from class: org.jf.util.collection.ListUtil.1
        @Override // java.util.Comparator
        public int compare(Object obj, Object obj2) {
            return String.valueOf(obj).compareTo(String.valueOf(obj2));
        }
    };

    public static <E, T> List<T> transform(Collection<E> collection, Function<? super E, ? extends T> function, int i) {
        ArrayList arrayList = new ArrayList(collection.size());
        for (Object obj : collection) {
            if (arrayList.size() >= i) {
                break;
            }
            arrayList.add(function.apply(obj));
        }
        return arrayList;
    }

    public static <E, T> List<T> transform(Collection<E> collection, Function<? super E, ? extends T> function) {
        ArrayList arrayList = new ArrayList(collection.size());
        Iterator<E> it = collection.iterator();
        while (it.hasNext()) {
            arrayList.add(function.apply(it.next()));
        }
        return arrayList;
    }

    public static <E> ArrayList<E> of() {
        return of(null);
    }

    public static <E> ArrayList<E> of(E... eArr) {
        return newArrayList(eArr);
    }

    public static <E> ArrayList<E> newArrayList(Iterable<? extends E> iterable) {
        if (iterable == null) {
            return new ArrayList<>(1);
        }
        if (iterable instanceof Collection) {
            return newArrayList((Collection) iterable);
        }
        return newArrayList(iterable.iterator());
    }

    public static <E> ArrayList<E> newArrayList(Collection<? extends E> collection) {
        if (collection == null) {
            return new ArrayList<>(1);
        }
        return new ArrayList<>(collection);
    }

    public static <E> ArrayList<E> newArrayList(Iterator<? extends E> it) {
        if (it == null) {
            return new ArrayList<>(1);
        }
        ArrayList<E> arrayList = new ArrayList<>();
        while (it.hasNext()) {
            arrayList.add(it.next());
        }
        return arrayList;
    }

    public static <T> List<T> reverse(List<T> list) {
        ArrayList arrayList = new ArrayList(list.size());
        for (int size = list.size() - 1; size >= 0; size--) {
            arrayList.add(list.get(size));
        }
        return arrayList;
    }

    public static <T> void addAll(List<T> list, Iterable<T> iterable) {
        if (iterable == null || iterable == list) {
            return;
        }
        addAll(list, iterable.iterator());
    }

    public static <T> void addAll(List<T> list, Iterator<T> it) {
        while (it.hasNext()) {
            list.add(it.next());
        }
    }

    public static <E> ArrayList<E> newArrayList() {
        return new ArrayList<>(1);
    }

    public static <E> ArrayList<E> newArrayList(E... eArr) {
        int length = eArr != null ? eArr.length : 0;
        if (length == 0) {
            length = 1;
        }
        ArrayList<E> arrayList = new ArrayList<>(length);
        if (eArr != null) {
            Collections.addAll(arrayList, eArr);
        }
        return arrayList;
    }

    public static <T> List<T> sortedCopy(Collection<T> collection, Comparator<? super T> comparator) {
        ArrayList arrayList = new ArrayList(collection);
        arrayList.sort(comparator);
        return arrayList;
    }

    public static <T> List<T> nullToEmptySet(List<T> list) {
        if (list != null) {
            return list;
        }
        return of(null);
    }

    public static <T> List<T> nullToEmptyList(List<T> list) {
        if (list != null) {
            return list;
        }
        return of(null);
    }

    public static <T> Set<T> immutableSetOf(Iterable<? extends T> iterable) {
        if (iterable == null) {
            return ArraySet.of();
        }
        return ArraySet.copyOf(iterable.iterator());
    }

    public static <T> List<T> copyOf(Collection<T> collection) {
        if (collection == null) {
            return new ArrayList(1);
        }
        return new ArrayList(collection);
    }

    public static <T> List<T> copyOf(Iterable<? extends T> iterable) {
        if (iterable == null) {
            return new ArrayList(1);
        }
        if (iterable instanceof Collection) {
            return copyOf((Collection) iterable);
        }
        return copyOf(iterable.iterator());
    }

    public static <T> List<T> copyOf(Iterator<? extends T> it) {
        ArrayList arrayList = new ArrayList();
        while (it.hasNext()) {
            arrayList.add(it.next());
        }
        return arrayList;
    }

    public static <T extends Comparable<?>> Collection<T> sortedCopy(Iterator<? extends T> it) {
        return ArraySet.sortedCopy(it);
    }

    public static <T extends Comparable<?>> List<T> sortedCopy(Collection<? extends T> collection) {
        return sortedCopy((Collection) collection, false);
    }

    public static <T extends Comparable<?>> List<T> sortedCopy(Collection<? extends T> collection, boolean z) {
        ArrayList arrayList = new ArrayList(collection);
        if (z) {
            arrayList.sort(INVERT_SORT);
        } else {
            arrayList.sort(NATURAL_SORT);
        }
        return arrayList;
    }

    public static <T> Comparator<T> toStringComparator() {
        return (Comparator<T>) TO_STRING_COMPARATOR;
    }

    private static class NaturalSort<T extends Comparable<T>> implements Comparator<T> {
        private final boolean isInvert;

        NaturalSort(boolean z) {
            this.isInvert = z;
        }

        @Override // java.util.Comparator
        public int compare(T t, T t2) {
            if (this.isInvert) {
                return t2.compareTo(t);
            }
            return t.compareTo(t2);
        }
    }
}
