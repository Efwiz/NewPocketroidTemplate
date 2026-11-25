package org.jetbrains.kotlin.fir.resolve.providers;

import androidx.exifinterface.media.ExifInterface;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: FirCachedSymbolNamesProvider.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0010\"\n\u0002\b\u0002\n\u0002\u0010\u001c\n\u0000\n\u0002\u0018\u0002\n\u0000\u001aF\u0010\u0000\u001a\n\u0012\u0004\u0012\u0002H\u0002\u0018\u00010\u0001\"\u0004\b\u0000\u0010\u0003\"\u0004\b\u0001\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00030\u00042\u001a\u0010\u0005\u001a\u0016\u0012\u0004\u0012\u0002H\u0003\u0012\f\u0012\n\u0012\u0004\u0012\u0002H\u0002\u0018\u00010\u00040\u0006H\u0086\bø\u0001\u0000\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u0007"}, d2 = {"flatMapToNullableSet", "", "R", ExifInterface.GPS_DIRECTION_TRUE, "", "transform", "Lkotlin/Function1;", "providers"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirCachedSymbolNamesProviderKt {
    public static final <T, R> Set<R> flatMapToNullableSet(Iterable<? extends T> iterable, Function1<? super T, ? extends Iterable<? extends R>> transform) {
        Intrinsics.checkNotNullParameter(iterable, "<this>");
        Intrinsics.checkNotNullParameter(transform, "transform");
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        Iterator<? extends T> it = iterable.iterator();
        while (it.hasNext()) {
            Iterable<? extends R> iterableInvoke = transform.invoke(it.next());
            if (iterableInvoke == null) {
                return null;
            }
            CollectionsKt.addAll(linkedHashSet, iterableInvoke);
        }
        return linkedHashSet;
    }
}
