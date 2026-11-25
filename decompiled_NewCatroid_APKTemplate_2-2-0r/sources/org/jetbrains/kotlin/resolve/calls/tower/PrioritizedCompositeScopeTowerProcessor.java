package org.jetbrains.kotlin.resolve.calls.tower;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: ScopeTowerProcessors.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0010\u001e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u0000*\u0006\b\u0000\u0010\u0001 \u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B%\u0012\u001e\u0010\u0003\u001a\u0010\u0012\f\b\u0001\u0012\b\u0012\u0004\u0012\u00028\u00000\u00020\u0004\"\b\u0012\u0004\u0012\u00028\u00000\u0002¢\u0006\u0002\u0010\u0005J\u001c\u0010\t\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u000b0\n2\u0006\u0010\f\u001a\u00020\rH\u0016J\u001e\u0010\u000e\u001a\u00020\u000f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\r0\u000b2\u0006\u0010\u0011\u001a\u00020\u0012H\u0016R!\u0010\u0003\u001a\u0010\u0012\f\b\u0001\u0012\b\u0012\u0004\u0012\u00028\u00000\u00020\u0004¢\u0006\n\n\u0002\u0010\b\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/tower/PrioritizedCompositeScopeTowerProcessor;", "C", "Lorg/jetbrains/kotlin/resolve/calls/tower/ScopeTowerProcessor;", "processors", "", "([Lorg/jetbrains/kotlin/resolve/calls/tower/ScopeTowerProcessor;)V", "getProcessors", "()[Lorg/jetbrains/kotlin/resolve/calls/tower/ScopeTowerProcessor;", "[Lorg/jetbrains/kotlin/resolve/calls/tower/ScopeTowerProcessor;", "process", "", "", "data", "Lorg/jetbrains/kotlin/resolve/calls/tower/TowerData;", "recordLookups", "", "skippedData", "name", "Lorg/jetbrains/kotlin/name/Name;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PrioritizedCompositeScopeTowerProcessor<C> implements ScopeTowerProcessor<C> {
    private final ScopeTowerProcessor<C>[] processors;

    /* JADX WARN: Multi-variable type inference failed */
    public PrioritizedCompositeScopeTowerProcessor(ScopeTowerProcessor<? extends C>... processors) {
        Intrinsics.checkNotNullParameter(processors, "processors");
        this.processors = processors;
    }

    public final ScopeTowerProcessor<C>[] getProcessors() {
        return this.processors;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.ScopeTowerProcessor
    public List<Collection<C>> process(TowerData data) {
        Intrinsics.checkNotNullParameter(data, "data");
        ScopeTowerProcessor<C>[] scopeTowerProcessorArr = this.processors;
        ArrayList arrayList = new ArrayList();
        for (ScopeTowerProcessor<C> scopeTowerProcessor : scopeTowerProcessorArr) {
            CollectionsKt.addAll(arrayList, scopeTowerProcessor.process(data));
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.ScopeTowerProcessor
    public void recordLookups(Collection<? extends TowerData> skippedData, Name name2) {
        Intrinsics.checkNotNullParameter(skippedData, "skippedData");
        Intrinsics.checkNotNullParameter(name2, "name");
        for (ScopeTowerProcessor<C> scopeTowerProcessor : this.processors) {
            scopeTowerProcessor.recordLookups(skippedData, name2);
        }
    }
}
