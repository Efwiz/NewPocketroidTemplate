package org.jetbrains.kotlin.utils;

import androidx.exifinterface.media.ExifInterface;
import com.reandroid.archive.PathTree;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;

/* JADX INFO: compiled from: MemoryOptimizedCollectionUtil.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000J\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001c\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\u001e\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u001d\u0010\u0000\u001a\u0004\u0018\u0001H\u0001\"\u0004\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u0002¢\u0006\u0002\u0010\u0003\u001a7\u0010\u0004\u001a\u0004\u0018\u0001H\u0001\"\u0006\b\u0000\u0010\u0001\u0018\u0001*\u0006\u0012\u0002\b\u00030\u00052\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u00020\b0\u0007H\u0086\bø\u0001\u0000¢\u0006\u0002\u0010\t\u001a6\u0010\n\u001a\b\u0012\u0004\u0012\u0002H\u00010\u000b\"\u0004\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\f2\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u00020\b0\u0007H\u0086\bø\u0001\u0000\u001a\u001f\u0010\r\u001a\b\u0012\u0004\u0012\u0002H\u00010\u000b\"\u0006\b\u0000\u0010\u0001\u0018\u0001*\u0006\u0012\u0002\b\u00030\fH\u0086\b\u001a6\u0010\u000e\u001a\b\u0012\u0004\u0012\u0002H\u00010\u000b\"\u0004\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\f2\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u00020\b0\u0007H\u0086\bø\u0001\u0000\u001aB\u0010\u000f\u001a\b\u0012\u0004\u0012\u0002H\u00100\u000b\"\u0004\b\u0000\u0010\u0001\"\u0004\b\u0001\u0010\u0010*\b\u0012\u0004\u0012\u0002H\u00010\f2\u0018\u0010\u0011\u001a\u0014\u0012\u0004\u0012\u0002H\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00100\u00050\u0007H\u0086\bø\u0001\u0000\u001a<\u0010\u0012\u001a\b\u0012\u0004\u0012\u0002H\u00100\u000b\"\u0004\b\u0000\u0010\u0001\"\u0004\b\u0001\u0010\u0010*\b\u0012\u0004\u0012\u0002H\u00010\f2\u0012\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00100\u0007H\u0086\bø\u0001\u0000\u001aQ\u0010\u0013\u001a\b\u0012\u0004\u0012\u0002H\u00100\u000b\"\u0004\b\u0000\u0010\u0001\"\u0004\b\u0001\u0010\u0010*\b\u0012\u0004\u0012\u0002H\u00010\f2'\u0010\u0011\u001a#\u0012\u0013\u0012\u00110\u0015¢\u0006\f\b\u0016\u0012\b\b\u0017\u0012\u0004\b\b(\u0018\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00100\u0014H\u0086\bø\u0001\u0000\u001aB\u0010\u0019\u001a\b\u0012\u0004\u0012\u0002H\u00100\u000b\"\u0004\b\u0000\u0010\u0001\"\b\b\u0001\u0010\u0010*\u00020\u001a*\b\u0012\u0004\u0012\u0002H\u00010\f2\u0014\u0010\u0011\u001a\u0010\u0012\u0004\u0012\u0002H\u0001\u0012\u0006\u0012\u0004\u0018\u0001H\u00100\u0007H\u0086\bø\u0001\u0000\u001a,\u0010\u001b\u001a\b\u0012\u0004\u0012\u0002H\u00010\u000b\"\u0004\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u000b2\u0006\u0010\u001c\u001a\u0002H\u0001H\u0086\u0004¢\u0006\u0002\u0010\u001d\u001a-\u0010\u001b\u001a\b\u0012\u0004\u0012\u0002H\u00010\u000b\"\u0004\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u000b2\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u0002H\u00010\u000bH\u0086\u0004\u001a?\u0010\u001f\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00100 0\u000b\"\u0004\b\u0000\u0010\u0001\"\u0004\b\u0001\u0010\u0010*\b\u0012\u0004\u0012\u0002H\u00010\f2\f\u0010!\u001a\b\u0012\u0004\u0012\u0002H\u00100\fH\u0086\u0004\u001a\u001c\u0010\"\u001a\b\u0012\u0004\u0012\u0002H\u00010\u000b\"\u0004\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\f\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006#"}, d2 = {"atMostOne", ExifInterface.GPS_DIRECTION_TRUE, "Lkotlin/sequences/Sequence;", "(Lkotlin/sequences/Sequence;)Ljava/lang/Object;", "findIsInstanceAnd", "", "predicate", "Lkotlin/Function1;", "", "(Ljava/lang/Iterable;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "memoryOptimizedFilter", "", "", "memoryOptimizedFilterIsInstance", "memoryOptimizedFilterNot", "memoryOptimizedFlatMap", "R", "transform", "memoryOptimizedMap", "memoryOptimizedMapIndexed", "Lkotlin/Function2;", "", "Lkotlin/ParameterName;", "name", "index", "memoryOptimizedMapNotNull", "", "memoryOptimizedPlus", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "(Ljava/util/List;Ljava/lang/Object;)Ljava/util/List;", PathTree.NAME_elements, "memoryOptimizedZip", "Lkotlin/Pair;", "other", "toSmartList", "util.runtime"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class MemoryOptimizedCollectionUtilKt {
    public static final <T, R> List<R> memoryOptimizedMap(Collection<? extends T> collection, Function1<? super T, ? extends R> transform) {
        Intrinsics.checkNotNullParameter(collection, "<this>");
        Intrinsics.checkNotNullParameter(transform, "transform");
        ArrayList arrayList = new ArrayList(collection.size());
        Iterator<T> it = collection.iterator();
        while (it.hasNext()) {
            arrayList.add(transform.invoke(it.next()));
        }
        return CollectionsKt.compactIfPossible(arrayList);
    }

    public static final <T, R> List<R> memoryOptimizedMapIndexed(Collection<? extends T> collection, Function2<? super Integer, ? super T, ? extends R> transform) {
        Intrinsics.checkNotNullParameter(collection, "<this>");
        Intrinsics.checkNotNullParameter(transform, "transform");
        ArrayList arrayList = new ArrayList(collection.size());
        int i = 0;
        for (T t : collection) {
            int i2 = i + 1;
            if (i < 0) {
                kotlin.collections.CollectionsKt.throwIndexOverflow();
            }
            arrayList.add(transform.invoke(Integer.valueOf(i), t));
            i = i2;
        }
        return CollectionsKt.compactIfPossible(arrayList);
    }

    public static final <T, R> List<R> memoryOptimizedMapNotNull(Collection<? extends T> collection, Function1<? super T, ? extends R> transform) {
        Intrinsics.checkNotNullParameter(collection, "<this>");
        Intrinsics.checkNotNullParameter(transform, "transform");
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = collection.iterator();
        while (it.hasNext()) {
            R rInvoke = transform.invoke(it.next());
            if (rInvoke != null) {
                arrayList.add(rInvoke);
            }
        }
        return CollectionsKt.compactIfPossible(arrayList);
    }

    public static final <T, R> List<R> memoryOptimizedFlatMap(Collection<? extends T> collection, Function1<? super T, ? extends Iterable<? extends R>> transform) {
        Intrinsics.checkNotNullParameter(collection, "<this>");
        Intrinsics.checkNotNullParameter(transform, "transform");
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = collection.iterator();
        while (it.hasNext()) {
            kotlin.collections.CollectionsKt.addAll(arrayList, transform.invoke(it.next()));
        }
        return CollectionsKt.compactIfPossible(arrayList);
    }

    public static final <T> List<T> memoryOptimizedFilter(Collection<? extends T> collection, Function1<? super T, Boolean> predicate) {
        Intrinsics.checkNotNullParameter(collection, "<this>");
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        ArrayList arrayList = new ArrayList();
        for (T t : collection) {
            if (predicate.invoke(t).booleanValue()) {
                arrayList.add(t);
            }
        }
        return CollectionsKt.compactIfPossible(arrayList);
    }

    public static final <T> List<T> memoryOptimizedFilterNot(Collection<? extends T> collection, Function1<? super T, Boolean> predicate) {
        Intrinsics.checkNotNullParameter(collection, "<this>");
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        ArrayList arrayList = new ArrayList();
        for (T t : collection) {
            if (!predicate.invoke(t).booleanValue()) {
                arrayList.add(t);
            }
        }
        return CollectionsKt.compactIfPossible(arrayList);
    }

    public static final /* synthetic */ <T> List<T> memoryOptimizedFilterIsInstance(Collection<?> collection) {
        Intrinsics.checkNotNullParameter(collection, "<this>");
        ArrayList arrayList = new ArrayList();
        for (T t : collection) {
            Intrinsics.reifiedOperationMarker(3, ExifInterface.GPS_DIRECTION_TRUE);
            if (t instanceof Object) {
                arrayList.add(t);
            }
        }
        return CollectionsKt.compactIfPossible(arrayList);
    }

    public static final <T> List<T> memoryOptimizedPlus(List<? extends T> list, List<? extends T> elements) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        Intrinsics.checkNotNullParameter(elements, "elements");
        int size = list.size() + elements.size();
        if (size == 0) {
            return kotlin.collections.CollectionsKt.emptyList();
        }
        if (size == 1) {
            List<T> listSingletonList = Collections.singletonList(list.isEmpty() ? kotlin.collections.CollectionsKt.first((List) elements) : kotlin.collections.CollectionsKt.first((List) list));
            Intrinsics.checkNotNullExpressionValue(listSingletonList, "singletonList(...)");
            return listSingletonList;
        }
        ArrayList arrayList = new ArrayList(size);
        arrayList.addAll(list);
        arrayList.addAll(elements);
        return arrayList;
    }

    public static final <T> List<T> memoryOptimizedPlus(List<? extends T> list, T t) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        if (list.size() == 0) {
            List<T> listSingletonList = Collections.singletonList(t);
            Intrinsics.checkNotNullExpressionValue(listSingletonList, "singletonList(...)");
            return listSingletonList;
        }
        ArrayList arrayList = new ArrayList(list.size() + 1);
        arrayList.addAll(list);
        arrayList.add(t);
        return arrayList;
    }

    public static final <T, R> List<Pair<T, R>> memoryOptimizedZip(Collection<? extends T> collection, Collection<? extends R> other) {
        Intrinsics.checkNotNullParameter(collection, "<this>");
        Intrinsics.checkNotNullParameter(other, "other");
        if (collection.isEmpty() || other.isEmpty()) {
            return kotlin.collections.CollectionsKt.emptyList();
        }
        if (Math.min(collection.size(), other.size()) == 1) {
            return kotlin.collections.CollectionsKt.listOf(TuplesKt.to(kotlin.collections.CollectionsKt.first(collection), kotlin.collections.CollectionsKt.first(other)));
        }
        Collection<? extends T> collection2 = collection;
        Iterator<T> it = collection2.iterator();
        Collection<? extends R> collection3 = other;
        Iterator<T> it2 = collection3.iterator();
        ArrayList arrayList = new ArrayList(Math.min(kotlin.collections.CollectionsKt.collectionSizeOrDefault(collection2, 10), kotlin.collections.CollectionsKt.collectionSizeOrDefault(collection3, 10)));
        while (it.hasNext() && it2.hasNext()) {
            arrayList.add(TuplesKt.to(it.next(), it2.next()));
        }
        return arrayList;
    }

    public static final <T> T atMostOne(Sequence<? extends T> sequence) {
        Intrinsics.checkNotNullParameter(sequence, "<this>");
        Iterator<? extends T> it = sequence.iterator();
        if (!it.hasNext()) {
            return null;
        }
        T next = it.next();
        if (it.hasNext()) {
            throw new IllegalArgumentException("Sequence has more than one element.");
        }
        return next;
    }

    /* JADX WARN: Type inference failed for: r0v3, types: [T, java.lang.Object] */
    public static final /* synthetic */ <T> T findIsInstanceAnd(Iterable<?> iterable, Function1<? super T, Boolean> predicate) {
        Intrinsics.checkNotNullParameter(iterable, "<this>");
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        Iterator<?> it = iterable.iterator();
        while (it.hasNext()) {
            ?? r0 = (Object) it.next();
            Intrinsics.reifiedOperationMarker(3, ExifInterface.GPS_DIRECTION_TRUE);
            if ((r0 instanceof Object) && predicate.invoke(r0).booleanValue()) {
                return r0;
            }
        }
        return null;
    }

    public static final <T> List<T> toSmartList(Collection<? extends T> collection) {
        Intrinsics.checkNotNullParameter(collection, "<this>");
        return new SmartList((Collection) collection);
    }
}
