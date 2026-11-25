package org.jetbrains.kotlin.utils;

import android.Manifest;
import androidx.exifinterface.media.ExifInterface;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: CollectionUtil.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000&\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010\u001e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u001f\n\u0002\u0010\u001c\n\u0002\b\b\u001a6\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0006\b\u0000\u0010\u0002\u0018\u0001*\u0006\u0012\u0002\b\u00030\u00032\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\u00060\u0005H\u0086\bø\u0001\u0000\u001aO\u0010\u0007\u001a\u0002H\b\"\u0006\b\u0000\u0010\u0002\u0018\u0001\"\u0010\b\u0001\u0010\b*\n\u0012\u0006\b\u0000\u0012\u0002H\u00020\t*\u0006\u0012\u0002\b\u00030\n2\u0006\u0010\u000b\u001a\u0002H\b2\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\u00060\u0005H\u0086\bø\u0001\u0000¢\u0006\u0002\u0010\f\u001a@\u0010\r\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0003\"\u0006\b\u0000\u0010\u000e\u0018\u0001\"\u0006\b\u0001\u0010\u0002\u0018\u0001*\u0006\u0012\u0002\b\u00030\u00032\u0014\u0010\u000f\u001a\u0010\u0012\u0004\u0012\u0002H\u000e\u0012\u0006\u0012\u0004\u0018\u0001H\u00020\u0005H\u0086\bø\u0001\u0000\u001aY\u0010\u0010\u001a\u0002H\b\"\u0006\b\u0000\u0010\u000e\u0018\u0001\"\u0006\b\u0001\u0010\u0002\u0018\u0001\"\u0010\b\u0002\u0010\b*\n\u0012\u0006\b\u0000\u0012\u0002H\u00020\t*\u0006\u0012\u0002\b\u00030\n2\u0006\u0010\u000b\u001a\u0002H\b2\u0014\u0010\u000f\u001a\u0010\u0012\u0004\u0012\u0002H\u000e\u0012\u0006\u0012\u0004\u0018\u0001H\u00020\u0005H\u0086\bø\u0001\u0000¢\u0006\u0002\u0010\f\u001aW\u0010\u0011\u001a\u0002H\b\"\u0006\b\u0000\u0010\u000e\u0018\u0001\"\u0006\b\u0001\u0010\u0002\u0018\u0001\"\u0010\b\u0002\u0010\b*\n\u0012\u0006\b\u0000\u0012\u0002H\u00020\t*\u0006\u0012\u0002\b\u00030\n2\u0006\u0010\u000b\u001a\u0002H\b2\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u0002H\u000e\u0012\u0004\u0012\u0002H\u00020\u0005H\u0086\bø\u0001\u0000¢\u0006\u0002\u0010\f\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u0012"}, d2 = {"filterIsInstanceAnd", "", "R", "", "predicate", "Lkotlin/Function1;", "", "filterIsInstanceAndTo", "C", "", "", FirebaseAnalytics.Param.DESTINATION, "(Ljava/lang/Iterable;Ljava/util/Collection;Lkotlin/jvm/functions/Function1;)Ljava/util/Collection;", "filterIsInstanceMapNotNull", ExifInterface.GPS_DIRECTION_TRUE, "transform", "filterIsInstanceMapNotNullTo", "filterIsInstanceMapTo", "util.runtime"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class CollectionUtilKt {
    public static final /* synthetic */ <R, C extends Collection<? super R>> C filterIsInstanceAndTo(Iterable<?> iterable, C destination, Function1<? super R, Boolean> predicate) {
        Intrinsics.checkNotNullParameter(iterable, "<this>");
        Intrinsics.checkNotNullParameter(destination, "destination");
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        Iterator<?> it = iterable.iterator();
        while (it.hasNext()) {
            Manifest manifest = (Object) it.next();
            Intrinsics.reifiedOperationMarker(3, "R");
            if ((manifest instanceof Object) && predicate.invoke(manifest).booleanValue()) {
                destination.add(manifest);
            }
        }
        return destination;
    }

    public static final /* synthetic */ <T, R, C extends Collection<? super R>> C filterIsInstanceMapTo(Iterable<?> iterable, C destination, Function1<? super T, ? extends R> transform) {
        Intrinsics.checkNotNullParameter(iterable, "<this>");
        Intrinsics.checkNotNullParameter(destination, "destination");
        Intrinsics.checkNotNullParameter(transform, "transform");
        Iterator<?> it = iterable.iterator();
        while (it.hasNext()) {
            Manifest manifest = (Object) it.next();
            Intrinsics.reifiedOperationMarker(3, ExifInterface.GPS_DIRECTION_TRUE);
            if (manifest instanceof Object) {
                destination.add(transform.invoke(manifest));
            }
        }
        return destination;
    }

    public static final /* synthetic */ <T, R> Collection<R> filterIsInstanceMapNotNull(Collection<?> collection, Function1<? super T, ? extends R> transform) {
        R rInvoke;
        Intrinsics.checkNotNullParameter(collection, "<this>");
        Intrinsics.checkNotNullParameter(transform, "transform");
        if (collection.isEmpty()) {
            return kotlin.collections.CollectionsKt.emptyList();
        }
        SmartList smartList = new SmartList();
        for (T t : collection) {
            Intrinsics.reifiedOperationMarker(3, ExifInterface.GPS_DIRECTION_TRUE);
            if ((t instanceof Object) && (rInvoke = transform.invoke(t)) != null) {
                smartList.add(rInvoke);
            }
        }
        return smartList;
    }

    public static final /* synthetic */ <R> List<R> filterIsInstanceAnd(Collection<?> collection, Function1<? super R, Boolean> predicate) {
        Intrinsics.checkNotNullParameter(collection, "<this>");
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        if (collection.isEmpty()) {
            return kotlin.collections.CollectionsKt.emptyList();
        }
        SmartList smartList = new SmartList();
        Iterator<T> it = collection.iterator();
        while (it.hasNext()) {
            Manifest manifest = (Object) it.next();
            Intrinsics.reifiedOperationMarker(3, "R");
            if ((manifest instanceof Object) && predicate.invoke(manifest).booleanValue()) {
                smartList.add(manifest);
            }
        }
        return smartList;
    }

    public static final /* synthetic */ <T, R, C extends Collection<? super R>> C filterIsInstanceMapNotNullTo(Iterable<?> iterable, C destination, Function1<? super T, ? extends R> transform) {
        R rInvoke;
        Intrinsics.checkNotNullParameter(iterable, "<this>");
        Intrinsics.checkNotNullParameter(destination, "destination");
        Intrinsics.checkNotNullParameter(transform, "transform");
        Iterator<?> it = iterable.iterator();
        while (it.hasNext()) {
            Manifest manifest = (Object) it.next();
            Intrinsics.reifiedOperationMarker(3, ExifInterface.GPS_DIRECTION_TRUE);
            if ((manifest instanceof Object) && (rInvoke = transform.invoke(manifest)) != null) {
                destination.add(rInvoke);
            }
        }
        return destination;
    }
}
