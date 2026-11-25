package org.jetbrains.kotlin.js.inline.util;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;

/* JADX INFO: compiled from: collectionUtils.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000&\n\u0000\n\u0002\u0010#\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001c\n\u0002\b\u0004\u001a\u0012\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002\u001a\u001c\u0010\u0003\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0004\u001a\u001c\u0010\u0003\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0005\u001aI\u0010\u0006\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\t0\b0\u0007\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\t*\b\u0012\u0004\u0012\u0002H\u00020\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\t0\n2\u0006\u0010\f\u001a\u0002H\u0002¢\u0006\u0002\u0010\r¨\u0006\u000e"}, d2 = {"IdentitySet", "", ExifInterface.GPS_DIRECTION_TRUE, "toIdentitySet", "", "Lkotlin/sequences/Sequence;", "zipWithDefault", "", "Lkotlin/Pair;", "R", "", "other", "defaultT", "(Ljava/lang/Iterable;Ljava/lang/Iterable;Ljava/lang/Object;)Ljava/util/List;", "js.translator"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class CollectionUtilsKt {
    public static final <T> Set<T> IdentitySet() {
        Set<T> setNewSetFromMap = Collections.newSetFromMap(new IdentityHashMap());
        Intrinsics.checkNotNullExpressionValue(setNewSetFromMap, "newSetFromMap(...)");
        return setNewSetFromMap;
    }

    public static final <T> Set<T> toIdentitySet(Collection<? extends T> collection) {
        Intrinsics.checkNotNullParameter(collection, "<this>");
        Set<T> setIdentitySet = IdentitySet();
        Iterator<? extends T> it = collection.iterator();
        while (it.hasNext()) {
            setIdentitySet.add(it.next());
        }
        return setIdentitySet;
    }

    public static final <T> Set<T> toIdentitySet(Sequence<? extends T> sequence) {
        Intrinsics.checkNotNullParameter(sequence, "<this>");
        Set<T> setIdentitySet = IdentitySet();
        Iterator<? extends T> it = sequence.iterator();
        while (it.hasNext()) {
            setIdentitySet.add(it.next());
        }
        return setIdentitySet;
    }

    public static final <T, R> List<Pair<T, R>> zipWithDefault(Iterable<? extends T> iterable, Iterable<? extends R> other, T t) {
        Intrinsics.checkNotNullParameter(iterable, "<this>");
        Intrinsics.checkNotNullParameter(other, "other");
        Iterator<? extends T> it = iterable.iterator();
        Iterator<? extends R> it2 = other.iterator();
        ArrayList arrayList = new ArrayList();
        while (it.hasNext() && it2.hasNext()) {
            arrayList.add(TuplesKt.to(it.next(), it2.next()));
        }
        it.hasNext();
        while (it2.hasNext()) {
            arrayList.add(TuplesKt.to(t, it2.next()));
        }
        return arrayList;
    }
}
