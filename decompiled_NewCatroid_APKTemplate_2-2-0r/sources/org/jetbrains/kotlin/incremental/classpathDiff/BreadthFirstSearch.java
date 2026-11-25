package org.jetbrains.kotlin.incremental.classpathDiff;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: Impact.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\b\u0002\n\u0002\u0010\u001c\n\u0000\n\u0002\u0018\u0002\n\u0000\bÀ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J:\u0010\u0003\u001a\b\u0012\u0004\u0012\u0002H\u00050\u0004\"\u0004\b\u0000\u0010\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\u00050\u00072\u0018\u0010\b\u001a\u0014\u0012\u0004\u0012\u0002H\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00050\u00070\t¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/incremental/classpathDiff/BreadthFirstSearch;", "", "()V", "findReachableNodes", "", ExifInterface.GPS_DIRECTION_TRUE, "nodes", "", "edgesProvider", "Lkotlin/Function1;", "incremental-compilation-impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class BreadthFirstSearch {
    public static final BreadthFirstSearch INSTANCE = new BreadthFirstSearch();

    private BreadthFirstSearch() {
    }

    public final <T> Set<T> findReachableNodes(Iterable<? extends T> nodes, Function1<? super T, ? extends Iterable<? extends T>> edgesProvider) {
        Intrinsics.checkNotNullParameter(nodes, "nodes");
        Intrinsics.checkNotNullParameter(edgesProvider, "edgesProvider");
        Set<T> mutableSet = CollectionsKt.toMutableSet(nodes);
        ArrayDeque arrayDeque = new ArrayDeque(CollectionsKt.toSet(nodes));
        while (!arrayDeque.isEmpty()) {
            List listMinus = CollectionsKt.minus((Iterable) edgesProvider.invoke((Object) arrayDeque.removeFirst()), (Iterable) mutableSet);
            mutableSet.addAll(listMinus);
            arrayDeque.addAll(listMinus);
        }
        return mutableSet;
    }
}
