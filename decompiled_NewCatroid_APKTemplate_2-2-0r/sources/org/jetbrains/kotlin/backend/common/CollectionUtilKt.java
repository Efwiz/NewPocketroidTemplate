package org.jetbrains.kotlin.backend.common;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: CollectionUtil.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0000\n\u0002\u0010\u001c\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\u001a\u001d\u0010\u0000\u001a\u0004\u0018\u0001H\u0001\"\u0004\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u0002¢\u0006\u0002\u0010\u0003\u001a7\u0010\u0000\u001a\u0004\u0018\u0001H\u0001\"\u0004\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u00042\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u00020\u00070\u0006H\u0086\bø\u0001\u0000¢\u0006\u0002\u0010\b\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\t"}, d2 = {"atMostOne", ExifInterface.GPS_DIRECTION_TRUE, "", "(Ljava/util/Collection;)Ljava/lang/Object;", "", "predicate", "Lkotlin/Function1;", "", "(Ljava/lang/Iterable;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "backend-common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class CollectionUtilKt {
    public static final <T> T atMostOne(Collection<? extends T> collection) {
        Intrinsics.checkNotNullParameter(collection, "<this>");
        int size = collection.size();
        if (size == 0) {
            return null;
        }
        if (size == 1) {
            return collection.iterator().next();
        }
        throw new IllegalArgumentException("Collection has more than one element.");
    }

    public static final <T> T atMostOne(Iterable<? extends T> iterable, Function1<? super T, Boolean> predicate) {
        Intrinsics.checkNotNullParameter(iterable, "<this>");
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        ArrayList arrayList = new ArrayList();
        for (T t : iterable) {
            if (predicate.invoke(t).booleanValue()) {
                arrayList.add(t);
            }
        }
        return (T) atMostOne(arrayList);
    }
}
