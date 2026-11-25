package com.reandroid.utils.collection;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

/* JADX INFO: loaded from: classes11.dex */
public class CollectionUtil {
    private static final Comparator<? extends Comparable> COMPARABLE_COMPARATOR = new Comparator<Comparable<?>>() { // from class: com.reandroid.utils.collection.CollectionUtil.1
        @Override // java.util.Comparator
        public /* bridge */ /* synthetic */ int compare(Comparable<?> comparable, Comparable<?> comparable2) {
            return compare2((Comparable) comparable, (Comparable) comparable2);
        }

        /* JADX INFO: renamed from: compare, reason: avoid collision after fix types in other method */
        public int compare2(Comparable comparable, Comparable comparable2) {
            return comparable.compareTo(comparable2);
        }
    };
    private static final Predicate<?> ACCEPT_ALL = new Predicate() { // from class: com.reandroid.utils.collection.CollectionUtil$$ExternalSyntheticLambda1
        @Override // java.util.function.Predicate
        public final boolean test(Object obj) {
            return CollectionUtil.lambda$static$5(obj);
        }
    };
    private static final Predicate<?> REJECT_ALL = new Predicate() { // from class: com.reandroid.utils.collection.CollectionUtil$$ExternalSyntheticLambda2
        @Override // java.util.function.Predicate
        public final boolean test(Object obj) {
            return CollectionUtil.lambda$static$6(obj);
        }
    };

    static /* synthetic */ boolean lambda$static$5(Object obj) {
        return true;
    }

    static /* synthetic */ boolean lambda$static$6(Object obj) {
        return false;
    }

    public static void walk(Iterator<?> it) {
        if (it == null) {
            return;
        }
        while (it.hasNext()) {
            it.next();
        }
    }

    public static <T> List<T> toUniqueList(Iterator<? extends T> it) {
        return new ArrayCollection(toHashSet(it));
    }

    @SafeVarargs
    public static <T> HashSet<T> newHashSet(T... tArr) {
        if (tArr == null || tArr.length == 0) {
            return new HashSet<>();
        }
        HashSet<T> hashSet = new HashSet<>(tArr.length);
        for (T t : tArr) {
            hashSet.add(t);
        }
        return hashSet;
    }

    public static <T> HashSet<T> toHashSet(Iterator<? extends T> it) {
        HashSet<T> hashSet = new HashSet<>();
        while (it.hasNext()) {
            hashSet.add(it.next());
        }
        return hashSet;
    }

    public static <T extends Comparable<T>> void sort(List<T> list) {
        list.sort(getComparator());
    }

    public static <T> T getLast(Iterator<T> it) {
        T next = null;
        if (it == null) {
            return null;
        }
        while (it.hasNext()) {
            next = it.next();
        }
        return next;
    }

    public static boolean contains(Iterator<?> it, Object obj) {
        if (it == null) {
            return false;
        }
        while (it.hasNext()) {
            if (obj.equals(it.next())) {
                return true;
            }
        }
        return false;
    }

    public static <T> T getFirst(Iterator<T> it) {
        if (it == null || !it.hasNext()) {
            return null;
        }
        return it.next();
    }

    public static <T> T getSingle(Iterator<T> it) {
        if (it == null || !it.hasNext()) {
            return null;
        }
        T next = it.next();
        if (it.hasNext()) {
            return null;
        }
        return next;
    }

    public static int count(Iterable<?> iterable) {
        int i = 0;
        if (iterable != null && !(iterable instanceof EmptyItem)) {
            if (iterable instanceof Collection) {
                return ((Collection) iterable).size();
            }
            if ((iterable instanceof SizedItem) && ((SizedItem) iterable).size() == 0) {
                return 0;
            }
            Iterator<?> it = iterable.iterator();
            while (it.hasNext()) {
                it.next();
                i++;
            }
        }
        return i;
    }

    public static int count(Iterator<?> it) {
        int i = 0;
        if (it != null && !(it instanceof EmptyItem)) {
            if ((it instanceof SizedItem) && ((SizedItem) it).size() == 0) {
                return 0;
            }
            while (it.hasNext()) {
                it.next();
                i++;
            }
        }
        return i;
    }

    public static boolean isEmpty(Iterable<?> iterable) {
        if (iterable == null || (iterable instanceof EmptyItem)) {
            return true;
        }
        if (iterable instanceof Collection) {
            return ((Collection) iterable).isEmpty();
        }
        if ((iterable instanceof SizedItem) && ((SizedItem) iterable).size() == 0) {
            return true;
        }
        return !iterable.iterator().hasNext();
    }

    public static boolean isEmpty(Iterator<?> it) {
        if (it == null || (it instanceof EmptyItem)) {
            return true;
        }
        if ((it instanceof SizedItem) && ((SizedItem) it).size() == 0) {
            return true;
        }
        return !it.hasNext();
    }

    public static <T> Collection<T> collect(Iterator<? extends T> it) {
        if (!it.hasNext()) {
            return ArrayCollection.empty();
        }
        ArrayCollection arrayCollection = new ArrayCollection();
        arrayCollection.addAll(it);
        if (arrayCollection.size() > 1000) {
            arrayCollection.trimToSize();
        }
        return arrayCollection;
    }

    @SafeVarargs
    public static <T> List<T> asList(T... tArr) {
        return new ArrayCollection(tArr);
    }

    public static <T> List<T> toList(Iterator<? extends T> it) {
        boolean zHasNext = it.hasNext();
        if (!zHasNext) {
            return EmptyList.of();
        }
        ArrayCollection arrayCollection = new ArrayCollection(2);
        while (zHasNext) {
            arrayCollection.add(it.next());
            zHasNext = it.hasNext();
        }
        if (arrayCollection.size() > 1000) {
            arrayCollection.trimToSize();
        }
        return arrayCollection;
    }

    public static <T> Iterator<T> newIterator(Collection<? extends T> collection) {
        if (collection.size() == 0) {
            return EmptyIterator.of();
        }
        return ArrayIterator.of(collection.toArray());
    }

    public static <T> Iterator<T> copyOf(Iterator<? extends T> it) {
        if (!it.hasNext()) {
            return EmptyIterator.of();
        }
        return toList(it).iterator();
    }

    public static void shuffle(List<?> list) {
        if (list.isEmpty()) {
            return;
        }
        shuffle(Long.toString(System.currentTimeMillis()).hashCode(), list);
    }

    public static void shuffle(int i, List<?> list) {
        if (list.isEmpty()) {
            return;
        }
        if (list instanceof ArrayCollection) {
            shuffle(i, (ArrayCollection<?>) list);
            return;
        }
        ArrayCollection arrayCollection = new ArrayCollection(list);
        list.clear();
        shuffle(i, (ArrayCollection<?>) arrayCollection);
        list.addAll(arrayCollection);
    }

    private static void shuffle(int i, ArrayCollection<?> arrayCollection) {
        int size = arrayCollection.size();
        for (int i2 = 0; i2 < size; i2++) {
            int i3 = i % size;
            if (i3 < 0) {
                i3 = -i3;
            }
            arrayCollection.swap(i2, i3);
            i = (i * 31) + size + 1;
        }
    }

    public static <T> Predicate<T> getAcceptAll() {
        return (Predicate<T>) ACCEPT_ALL;
    }

    public static <T> Predicate<T> getRejectAll() {
        return (Predicate<T>) REJECT_ALL;
    }

    public static <T> Predicate<? super T> orFilter(final Predicate<? super T> predicate, final Predicate<? super T> predicate2) {
        if (predicate == null || predicate == getRejectAll()) {
            return predicate2;
        }
        return (predicate2 == null || predicate2 == getRejectAll()) ? predicate : new Predicate() { // from class: com.reandroid.utils.collection.CollectionUtil$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return CollectionUtil.lambda$orFilter$0(predicate, predicate2, obj);
            }
        };
    }

    static /* synthetic */ boolean lambda$orFilter$0(Predicate predicate, Predicate predicate2, Object obj) {
        return predicate.test(obj) || predicate2.test(obj);
    }

    public static <T> Predicate<T> andFilter(final Predicate<T> predicate, final Predicate<T> predicate2) {
        if (predicate == null || predicate == getAcceptAll()) {
            return predicate2;
        }
        return (predicate2 == null || predicate2 == getAcceptAll()) ? predicate : new Predicate() { // from class: com.reandroid.utils.collection.CollectionUtil$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return CollectionUtil.lambda$andFilter$1(predicate, predicate2, obj);
            }
        };
    }

    static /* synthetic */ boolean lambda$andFilter$1(Predicate predicate, Predicate predicate2, Object obj) {
        return predicate.test(obj) && predicate2.test(obj);
    }

    static /* synthetic */ boolean lambda$negateFilter$2(Predicate predicate, Object obj) {
        return !predicate.test(obj);
    }

    public static <T> Predicate<T> negateFilter(final Predicate<T> predicate) {
        return new Predicate() { // from class: com.reandroid.utils.collection.CollectionUtil$$ExternalSyntheticLambda6
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return CollectionUtil.lambda$negateFilter$2(predicate, obj);
            }
        };
    }

    public static <T> Predicate<T> diffFilter(final Predicate<T> predicate, final Predicate<T> predicate2) {
        return new Predicate() { // from class: com.reandroid.utils.collection.CollectionUtil$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return CollectionUtil.lambda$diffFilter$3(predicate, predicate2, obj);
            }
        };
    }

    static /* synthetic */ boolean lambda$diffFilter$3(Predicate predicate, Predicate predicate2, Object obj) {
        return predicate.test(obj) != predicate2.test(obj);
    }

    public static <T> Predicate<T> equalFilter(final Predicate<T> predicate, final Predicate<T> predicate2) {
        return new Predicate() { // from class: com.reandroid.utils.collection.CollectionUtil$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return CollectionUtil.lambda$equalFilter$4(predicate, predicate2, obj);
            }
        };
    }

    static /* synthetic */ boolean lambda$equalFilter$4(Predicate predicate, Predicate predicate2, Object obj) {
        return predicate.test(obj) == predicate2.test(obj);
    }

    public static <T extends Comparable<T>> Comparator<T> getComparator() {
        return (Comparator<T>) COMPARABLE_COMPARATOR;
    }
}
