package org.jetbrains.kotlin.resolve.calls.tower;

import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ScopeTowerProcessors.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u001e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u0000*\u0006\b\u0000\u0010\u0001 \u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002J\u001c\u0010\u0003\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u0016\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00000\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\tÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/tower/SimpleScopeTowerProcessor;", "C", "Lorg/jetbrains/kotlin/resolve/calls/tower/ScopeTowerProcessor;", "process", "", "", "data", "Lorg/jetbrains/kotlin/resolve/calls/tower/TowerData;", "simpleProcess", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface SimpleScopeTowerProcessor<C> extends ScopeTowerProcessor<C> {
    Collection<C> simpleProcess(TowerData data);

    @Override // org.jetbrains.kotlin.resolve.calls.tower.ScopeTowerProcessor
    default List<Collection<C>> process(TowerData data) {
        Intrinsics.checkNotNullParameter(data, "data");
        Collection<C> collectionSimpleProcess = simpleProcess(data);
        if (collectionSimpleProcess.isEmpty()) {
            collectionSimpleProcess = null;
        }
        return CollectionsKt.listOfNotNull(collectionSimpleProcess);
    }
}
