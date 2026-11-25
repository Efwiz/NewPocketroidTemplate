package org.jetbrains.kotlin.utils;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.MapsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.sequences.SequenceScope;
import org.antlr.stringtemplate.language.ASTExpr;

/* JADX INFO: compiled from: collections.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0084\u0001\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\u0010\u001f\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0002\b\u0003\n\u0002\u0010$\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u001c\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0010\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\u0002\u001a&\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u0002H\u0005\u0012\u0004\u0012\u0002H\u00060\u0004\"\u0004\b\u0000\u0010\u0005\"\u0004\b\u0001\u0010\u00062\u0006\u0010\u0002\u001a\u00020\u0001\u001a\u001a\u0010\u0007\u001a\b\u0012\u0004\u0012\u0002H\t0\b\"\u0004\b\u0000\u0010\t2\u0006\u0010\u0002\u001a\u00020\u0001\u001a&\u0010\n\u001a\u000e\u0012\u0004\u0012\u0002H\u0005\u0012\u0004\u0012\u0002H\u00060\u000b\"\u0004\b\u0000\u0010\u0005\"\u0004\b\u0001\u0010\u00062\u0006\u0010\u0002\u001a\u00020\u0001\u001a\u001a\u0010\f\u001a\b\u0012\u0004\u0012\u0002H\t0\r\"\u0004\b\u0000\u0010\t2\u0006\u0010\u0002\u001a\u00020\u0001\u001a)\u0010\u000e\u001a\u00020\u000f\"\b\b\u0000\u0010\u0010*\u00020\u0011*\b\u0012\u0004\u0012\u0002H\u00100\u00122\b\u0010\u0013\u001a\u0004\u0018\u0001H\u0010¢\u0006\u0002\u0010\u0014\u001a\u001c\u0010\u0015\u001a\b\u0012\u0004\u0012\u0002H\u00100\u0016\"\u0004\b\u0000\u0010\u0010*\b\u0012\u0004\u0012\u0002H\u00100\u0017\u001a\u001c\u0010\u0018\u001a\b\u0012\u0004\u0012\u0002H\u00100\u0016\"\u0004\b\u0000\u0010\u0010*\b\u0012\u0004\u0012\u0002H\u00100\u0016\u001aC\u0010\u0019\u001a\u0002H\u0006\"\u0004\b\u0000\u0010\u0005\"\u0004\b\u0001\u0010\u0006*\u000e\u0012\u0004\u0012\u0002H\u0005\u0012\u0004\u0012\u0002H\u00060\u001a2\u0006\u0010\u001b\u001a\u0002H\u00052\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u0002H\u00060\u001dH\u0086\bø\u0001\u0000¢\u0006\u0002\u0010\u001e\u001a9\u0010\u001f\u001a\u0002H \"\u0004\b\u0000\u0010\u0010\"\u000e\b\u0001\u0010 *\b\u0012\u0004\u0012\u0002H\u00100!*\u0002H 2\f\u0010\"\u001a\b\u0012\u0004\u0012\u0002H 0\u001dH\u0086\bø\u0001\u0000¢\u0006\u0002\u0010#\u001aE\u0010\u001f\u001a\u0002H$\"\u0004\b\u0000\u0010\u0005\"\u0004\b\u0001\u0010\u0006\"\u0014\b\u0002\u0010$*\u000e\u0012\u0004\u0012\u0002H\u0005\u0012\u0004\u0012\u0002H\u00060%*\u0002H$2\f\u0010\"\u001a\b\u0012\u0004\u0012\u0002H$0\u001dH\u0086\bø\u0001\u0000¢\u0006\u0002\u0010&\u001aA\u0010\u001f\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00100'\"\u0004\b\u0000\u0010\u0010*\n\u0012\u0006\b\u0001\u0012\u0002H\u00100'2\u0014\u0010\"\u001a\u0010\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u0002H\u00100'0\u001dH\u0086\bø\u0001\u0000¢\u0006\u0002\u0010(\u001a2\u0010)\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0010*\b\u0012\u0004\u0012\u0002H\u00100\u00162\u0006\u0010*\u001a\u00020\u00012\u0012\u0010+\u001a\u000e\u0012\u0004\u0012\u0002H\u0010\u0012\u0004\u0012\u00020-0,\u001a<\u0010.\u001a\u000e\u0012\u0004\u0012\u0002H\u0005\u0012\u0004\u0012\u0002H\u00060%\"\u0004\b\u0000\u0010\u0005\"\u0004\b\u0001\u0010\u0006*\b\u0012\u0004\u0012\u0002H\u00050/2\u0012\u00100\u001a\u000e\u0012\u0004\u0012\u0002H\u0005\u0012\u0004\u0012\u0002H\u00060,\u001aB\u00101\u001a\u000e\u0012\u0004\u0012\u0002H\u0005\u0012\u0004\u0012\u0002H\u00060%\"\u0004\b\u0000\u0010\u0005\"\b\b\u0001\u0010\u0006*\u00020\u0011*\b\u0012\u0004\u0012\u0002H\u00050/2\u0014\u00100\u001a\u0010\u0012\u0004\u0012\u0002H\u0005\u0012\u0006\u0012\u0004\u0018\u0001H\u00060,\u001a\"\u00102\u001a\u000e\u0012\u0004\u0012\u0002H\u0005\u0012\u0004\u0012\u00020\u00010%\"\u0004\b\u0000\u0010\u0005*\b\u0012\u0004\u0012\u0002H\u00050/\u001a,\u00103\u001a\u00020\u000f\"\b\b\u0000\u0010\u0010*\u00020\u0011*\b\u0012\u0004\u0012\u0002H\u0010042\b\u0010\u0013\u001a\u0004\u0018\u0001H\u0010H\u0086@¢\u0006\u0002\u00105\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u00066"}, d2 = {"capacity", "", "expectedSize", "newHashMapWithExpectedSize", "Ljava/util/HashMap;", "K", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "newHashSetWithExpectedSize", "Ljava/util/HashSet;", ExifInterface.LONGITUDE_EAST, "newLinkedHashMapWithExpectedSize", "Ljava/util/LinkedHashMap;", "newLinkedHashSetWithExpectedSize", "Ljava/util/LinkedHashSet;", "addIfNotNull", "", ExifInterface.GPS_DIRECTION_TRUE, "", "", "t", "(Ljava/util/Collection;Ljava/lang/Object;)V", "compact", "", "Ljava/util/ArrayList;", "compactIfPossible", "getOrPutNullable", "", ASTExpr.DEFAULT_MAP_KEY_NAME, "defaultValue", "Lkotlin/Function0;", "(Ljava/util/Map;Ljava/lang/Object;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "ifEmpty", "C", "", "body", "(Ljava/util/Collection;Lkotlin/jvm/functions/Function0;)Ljava/util/Collection;", "M", "", "(Ljava/util/Map;Lkotlin/jvm/functions/Function0;)Ljava/util/Map;", "", "([Ljava/lang/Object;Lkotlin/jvm/functions/Function0;)[Ljava/lang/Object;", "indexOfFirst", "startFrom", "predicate", "Lkotlin/Function1;", "", "keysToMap", "", "value", "keysToMapExceptNulls", "mapToIndex", "yieldIfNotNull", "Lkotlin/sequences/SequenceScope;", "(Lkotlin/sequences/SequenceScope;Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "util.runtime"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class CollectionsKt {
    public static final <K, V> Map<K, V> keysToMapExceptNulls(Iterable<? extends K> iterable, Function1<? super K, ? extends V> value) {
        Intrinsics.checkNotNullParameter(iterable, "<this>");
        Intrinsics.checkNotNullParameter(value, "value");
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (K k : iterable) {
            V vInvoke = value.invoke(k);
            if (vInvoke != null) {
                linkedHashMap.put(k, vInvoke);
            }
        }
        return linkedHashMap;
    }

    public static final <K> Map<K, Integer> mapToIndex(Iterable<? extends K> iterable) {
        Intrinsics.checkNotNullParameter(iterable, "<this>");
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        Iterator<? extends K> it = iterable.iterator();
        int i = 0;
        while (it.hasNext()) {
            linkedHashMap.put(it.next(), Integer.valueOf(i));
            i++;
        }
        return linkedHashMap;
    }

    public static final <K, V> V getOrPutNullable(Map<K, V> map, K k, Function0<? extends V> defaultValue) {
        Intrinsics.checkNotNullParameter(map, "<this>");
        Intrinsics.checkNotNullParameter(defaultValue, "defaultValue");
        if (!map.containsKey(k)) {
            V vInvoke = defaultValue.invoke();
            map.put(k, vInvoke);
            return vInvoke;
        }
        return map.get(k);
    }

    public static final <T, C extends Collection<? extends T>> C ifEmpty(C c, Function0<? extends C> body) {
        Intrinsics.checkNotNullParameter(c, "<this>");
        Intrinsics.checkNotNullParameter(body, "body");
        return c.isEmpty() ? body.invoke() : c;
    }

    public static final <K, V, M extends Map<K, ? extends V>> M ifEmpty(M m, Function0<? extends M> body) {
        Intrinsics.checkNotNullParameter(m, "<this>");
        Intrinsics.checkNotNullParameter(body, "body");
        return m.isEmpty() ? body.invoke() : m;
    }

    public static final <T> T[] ifEmpty(T[] tArr, Function0<? extends T[]> body) {
        Intrinsics.checkNotNullParameter(tArr, "<this>");
        Intrinsics.checkNotNullParameter(body, "body");
        return tArr.length == 0 ? body.invoke() : tArr;
    }

    public static final <T> void addIfNotNull(Collection<T> collection, T t) {
        Intrinsics.checkNotNullParameter(collection, "<this>");
        if (t != null) {
            collection.add(t);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final <T> Object yieldIfNotNull(SequenceScope<? super T> sequenceScope, T t, Continuation<? super Unit> continuation) {
        Object objYield;
        return (t == 0 || (objYield = sequenceScope.yield(t, continuation)) != IntrinsicsKt.getCOROUTINE_SUSPENDED()) ? Unit.INSTANCE : objYield;
    }

    public static final <K, V> HashMap<K, V> newHashMapWithExpectedSize(int i) {
        return new HashMap<>(capacity(i));
    }

    public static final <E> HashSet<E> newHashSetWithExpectedSize(int i) {
        return new HashSet<>(capacity(i));
    }

    public static final <K, V> LinkedHashMap<K, V> newLinkedHashMapWithExpectedSize(int i) {
        return new LinkedHashMap<>(capacity(i));
    }

    public static final <E> LinkedHashSet<E> newLinkedHashSetWithExpectedSize(int i) {
        return new LinkedHashSet<>(capacity(i));
    }

    private static final int capacity(int i) {
        if (i < 3) {
            return 3;
        }
        return i + (i / 3) + 1;
    }

    public static final <T> List<T> compact(ArrayList<T> arrayList) {
        Intrinsics.checkNotNullParameter(arrayList, "<this>");
        int size = arrayList.size();
        if (size == 0) {
            return kotlin.collections.CollectionsKt.emptyList();
        }
        if (size == 1) {
            return kotlin.collections.CollectionsKt.listOf(kotlin.collections.CollectionsKt.first((List) arrayList));
        }
        arrayList.trimToSize();
        return arrayList;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final <T> List<T> compactIfPossible(List<? extends T> list) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        int size = list.size();
        if (size == 0) {
            return kotlin.collections.CollectionsKt.emptyList();
        }
        if (size == 1) {
            return kotlin.collections.CollectionsKt.listOf(kotlin.collections.CollectionsKt.first((List) list));
        }
        if (!(list instanceof ArrayList)) {
            return list;
        }
        ((ArrayList) list).trimToSize();
        return list;
    }

    public static final <T> int indexOfFirst(List<? extends T> list, int i, Function1<? super T, Boolean> predicate) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        int lastIndex = kotlin.collections.CollectionsKt.getLastIndex(list);
        if (i > lastIndex) {
            return -1;
        }
        while (!predicate.invoke(list.get(i)).booleanValue()) {
            if (i == lastIndex) {
                return -1;
            }
            i++;
        }
        return i;
    }

    public static final <K, V> Map<K, V> keysToMap(Iterable<? extends K> iterable, Function1<? super K, ? extends V> value) {
        Intrinsics.checkNotNullParameter(iterable, "<this>");
        Intrinsics.checkNotNullParameter(value, "value");
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(kotlin.collections.CollectionsKt.collectionSizeOrDefault(iterable, 10)), 16));
        for (K k : iterable) {
            linkedHashMap.put(k, value.invoke(k));
        }
        return linkedHashMap;
    }
}
