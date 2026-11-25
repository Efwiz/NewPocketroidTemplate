package org.jetbrains.kotlin.resolve.calls.tower;

import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: ScopeTowerProcessors.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\b\u0004\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u0000*\u0006\b\u0000\u0010\u0001 \u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u0013\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004¢\u0006\u0002\u0010\u0005J\u001c\u0010\b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00040\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u001e\u0010\f\u001a\u00020\r2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000b0\u00042\u0006\u0010\u000f\u001a\u00020\u0010H\u0016R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/tower/KnownResultProcessor;", "C", "Lorg/jetbrains/kotlin/resolve/calls/tower/ScopeTowerProcessor;", "result", "", "(Ljava/util/Collection;)V", "getResult", "()Ljava/util/Collection;", "process", "", "data", "Lorg/jetbrains/kotlin/resolve/calls/tower/TowerData;", "recordLookups", "", "skippedData", "name", "Lorg/jetbrains/kotlin/name/Name;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KnownResultProcessor<C> implements ScopeTowerProcessor<C> {
    private final Collection<C> result;

    @Override // org.jetbrains.kotlin.resolve.calls.tower.ScopeTowerProcessor
    public void recordLookups(Collection<? extends TowerData> skippedData, Name name2) {
        Intrinsics.checkNotNullParameter(skippedData, "skippedData");
        Intrinsics.checkNotNullParameter(name2, "name");
    }

    /* JADX WARN: Multi-variable type inference failed */
    public KnownResultProcessor(Collection<? extends C> result) {
        Intrinsics.checkNotNullParameter(result, "result");
        this.result = result;
    }

    public final Collection<C> getResult() {
        return this.result;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.ScopeTowerProcessor
    public List<Collection<C>> process(TowerData data) {
        Intrinsics.checkNotNullParameter(data, "data");
        if (!Intrinsics.areEqual(data, TowerData.Empty.INSTANCE)) {
            return CollectionsKt.emptyList();
        }
        Collection<C> collection = this.result;
        if (collection.isEmpty()) {
            collection = null;
        }
        return CollectionsKt.listOfNotNull(collection);
    }
}
