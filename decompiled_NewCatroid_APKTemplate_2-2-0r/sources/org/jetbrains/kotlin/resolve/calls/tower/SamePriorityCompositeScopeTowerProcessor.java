package org.jetbrains.kotlin.resolve.calls.tower;

import java.util.ArrayList;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: ScopeTowerProcessors.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000*\u0006\b\u0000\u0010\u0001 \u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B%\u0012\u001e\u0010\u0003\u001a\u0010\u0012\f\b\u0001\u0012\b\u0012\u0004\u0012\u00028\u00000\u00020\u0004\"\b\u0012\u0004\u0012\u00028\u00000\u0002¢\u0006\u0002\u0010\u0005J\u001e\u0010\u0007\u001a\u00020\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\u0006\u0010\f\u001a\u00020\rH\u0016J\u0016\u0010\u000e\u001a\b\u0012\u0004\u0012\u00028\u00000\n2\u0006\u0010\u000f\u001a\u00020\u000bH\u0016R\u001e\u0010\u0003\u001a\u0010\u0012\f\b\u0001\u0012\b\u0012\u0004\u0012\u00028\u00000\u00020\u0004X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0006¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/tower/SamePriorityCompositeScopeTowerProcessor;", "C", "Lorg/jetbrains/kotlin/resolve/calls/tower/SimpleScopeTowerProcessor;", "processors", "", "([Lorg/jetbrains/kotlin/resolve/calls/tower/SimpleScopeTowerProcessor;)V", "[Lorg/jetbrains/kotlin/resolve/calls/tower/SimpleScopeTowerProcessor;", "recordLookups", "", "skippedData", "", "Lorg/jetbrains/kotlin/resolve/calls/tower/TowerData;", "name", "Lorg/jetbrains/kotlin/name/Name;", "simpleProcess", "data", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SamePriorityCompositeScopeTowerProcessor<C> implements SimpleScopeTowerProcessor<C> {
    private final SimpleScopeTowerProcessor<C>[] processors;

    /* JADX WARN: Multi-variable type inference failed */
    public SamePriorityCompositeScopeTowerProcessor(SimpleScopeTowerProcessor<? extends C>... processors) {
        Intrinsics.checkNotNullParameter(processors, "processors");
        this.processors = processors;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.SimpleScopeTowerProcessor
    public Collection<C> simpleProcess(TowerData data) {
        Intrinsics.checkNotNullParameter(data, "data");
        SimpleScopeTowerProcessor<C>[] simpleScopeTowerProcessorArr = this.processors;
        ArrayList arrayList = new ArrayList();
        for (SimpleScopeTowerProcessor<C> simpleScopeTowerProcessor : simpleScopeTowerProcessorArr) {
            CollectionsKt.addAll(arrayList, simpleScopeTowerProcessor.simpleProcess(data));
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.ScopeTowerProcessor
    public void recordLookups(Collection<? extends TowerData> skippedData, Name name2) {
        Intrinsics.checkNotNullParameter(skippedData, "skippedData");
        Intrinsics.checkNotNullParameter(name2, "name");
        for (SimpleScopeTowerProcessor<C> simpleScopeTowerProcessor : this.processors) {
            simpleScopeTowerProcessor.recordLookups(skippedData, name2);
        }
    }
}
