package org.jf.util;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;
import java.util.SortedSet;
import java.util.function.Predicate;
import org.jf.util.collection.ArraySet;

/* JADX INFO: loaded from: classes6.dex */
public class CollectionUtils {
    public static <T> int listHashCode(Iterable<T> iterable) {
        Iterator<T> it = iterable.iterator();
        int iHashCode = 1;
        while (it.hasNext()) {
            iHashCode = (iHashCode * 31) + it.next().hashCode();
        }
        return iHashCode;
    }

    public static <T> int lastIndexOf(Iterable<T> iterable, Predicate<? super T> predicate) {
        Iterator<T> it = iterable.iterator();
        int i = 0;
        int i2 = -1;
        while (it.hasNext()) {
            if (predicate.test(it.next())) {
                i2 = i;
            }
            i++;
        }
        return i2;
    }

    public static <T extends Comparable<? super T>> int compareAsList(Collection<? extends T> collection, Collection<? extends T> collection2) {
        int iCompare = Integer.compare(collection.size(), collection2.size());
        if (iCompare != 0) {
            return iCompare;
        }
        Iterator<? extends T> it = collection2.iterator();
        Iterator<? extends T> it2 = collection.iterator();
        while (it2.hasNext()) {
            int iCompareTo = it2.next().compareTo(it.next());
            if (iCompareTo != 0) {
                return iCompareTo;
            }
        }
        return 0;
    }

    public static <T> int compareAsIterable(Comparator<? super T> comparator, Iterable<? extends T> iterable, Iterable<? extends T> iterable2) {
        Iterator<? extends T> it = iterable2.iterator();
        for (T t : iterable) {
            if (!it.hasNext()) {
                return 1;
            }
            int iCompare = comparator.compare(t, it.next());
            if (iCompare != 0) {
                return iCompare;
            }
        }
        return it.hasNext() ? -1 : 0;
    }

    public static <T extends Comparable<? super T>> int compareAsIterable(Iterable<? extends T> iterable, Iterable<? extends T> iterable2) {
        Iterator<? extends T> it = iterable2.iterator();
        for (T t : iterable) {
            if (!it.hasNext()) {
                return 1;
            }
            int iCompareTo = t.compareTo(it.next());
            if (iCompareTo != 0) {
                return iCompareTo;
            }
        }
        return it.hasNext() ? -1 : 0;
    }

    public static <T> int compareAsList(Comparator<? super T> comparator, Collection<? extends T> collection, Collection<? extends T> collection2) {
        int iCompare = Integer.compare(collection.size(), collection2.size());
        if (iCompare != 0) {
            return iCompare;
        }
        Iterator<? extends T> it = collection2.iterator();
        Iterator<? extends T> it2 = collection.iterator();
        while (it2.hasNext()) {
            int iCompare2 = comparator.compare(it2.next(), it.next());
            if (iCompare2 != 0) {
                return iCompare2;
            }
        }
        return 0;
    }

    public static <T> Comparator<Collection<? extends T>> listComparator(final Comparator<? super T> comparator) {
        return new Comparator<Collection<? extends T>>() { // from class: org.jf.util.CollectionUtils.1
            @Override // java.util.Comparator
            public int compare(Collection<? extends T> collection, Collection<? extends T> collection2) {
                return CollectionUtils.compareAsList(comparator, collection, collection2);
            }
        };
    }

    public static <T> boolean isNaturalSortedSet(Iterable<? extends T> iterable) {
        return iterable instanceof ArraySet;
    }

    public static <T> boolean isSortedSet(Comparator<? extends T> comparator, Iterable<? extends T> iterable) {
        return iterable instanceof ArraySet;
    }

    private static <T> Set<? extends T> toNaturalSortedSet(Collection<? extends T> collection) {
        if (isNaturalSortedSet(collection)) {
            return (SortedSet) collection;
        }
        return ArraySet.sortedCopy(collection.iterator());
    }

    private static <T> Set<? extends T> toSortedSet(Comparator<? super T> comparator, Collection<? extends T> collection) {
        SortedSet sortedSet;
        Comparator comparator2;
        if ((collection instanceof SortedSet) && (comparator2 = (sortedSet = (SortedSet) collection).comparator()) != null && comparator2.equals(comparator)) {
            return sortedSet;
        }
        ArraySet arraySet = new ArraySet(collection);
        arraySet.sort(comparator);
        return arraySet;
    }

    public static <T> Comparator<Collection<? extends T>> setComparator(final Comparator<? super T> comparator) {
        return new Comparator<Collection<? extends T>>() { // from class: org.jf.util.CollectionUtils.2
            @Override // java.util.Comparator
            public int compare(Collection<? extends T> collection, Collection<? extends T> collection2) {
                return CollectionUtils.compareAsSet(comparator, collection, collection2);
            }
        };
    }

    public static <T extends Comparable<T>> int compareAsSet(Collection<? extends T> collection, Collection<? extends T> collection2) {
        int iCompare = Integer.compare(collection.size(), collection2.size());
        if (iCompare != 0) {
            return iCompare;
        }
        Iterator<? extends T> it = collection2.iterator();
        Iterator<? extends T> it2 = collection.iterator();
        while (it2.hasNext()) {
            int iCompareTo = it2.next().compareTo(it.next());
            if (iCompareTo != 0) {
                return iCompareTo;
            }
        }
        return 0;
    }

    public static <T> int compareAsSet(Comparator<? super T> comparator, Collection<? extends T> collection, Collection<? extends T> collection2) {
        int iCompare = Integer.compare(collection.size(), collection2.size());
        if (iCompare != 0) {
            return iCompare;
        }
        Set sortedSet = toSortedSet(comparator, collection);
        Iterator it = toSortedSet(comparator, collection2).iterator();
        Iterator it2 = sortedSet.iterator();
        while (it2.hasNext()) {
            int iCompare2 = comparator.compare((Object) it2.next(), (Object) it.next());
            if (iCompare2 != 0) {
                return iCompare2;
            }
        }
        return 0;
    }
}
