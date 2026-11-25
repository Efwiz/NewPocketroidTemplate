package org.jetbrains.kotlin.fir.resolve.calls.tower;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.resolve.BodyResolveComponents;
import org.jetbrains.kotlin.fir.resolve.calls.CandidateCollector;
import org.jetbrains.kotlin.fir.resolve.calls.CandidateFactory;

/* JADX INFO: compiled from: FirInvokeResolveTowerExtension.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\b\u0002\u0018\u00002\u00020\u0001BA\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000f0\r¢\u0006\u0002\u0010\u0010J\u0010\u0010\u0011\u001a\u00020\u000e2\u0006\u0010\u0012\u001a\u00020\u000eH\u0016J\u0010\u0010\f\u001a\u00020\u000f2\u0006\u0010\u0012\u001a\u00020\u000eH\u0016R\u001a\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000f0\rX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/tower/InvokeReceiverResolveTask;", "Lorg/jetbrains/kotlin/fir/resolve/calls/tower/FirTowerResolveTask;", "components", "Lorg/jetbrains/kotlin/fir/resolve/BodyResolveComponents;", "manager", "Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerResolveManager;", "towerDataElementsForName", "Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerDataElementsForName;", "collector", "Lorg/jetbrains/kotlin/fir/resolve/calls/CandidateCollector;", "candidateFactory", "Lorg/jetbrains/kotlin/fir/resolve/calls/CandidateFactory;", "onSuccessfulLevel", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerGroup;", "", "(Lorg/jetbrains/kotlin/fir/resolve/BodyResolveComponents;Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerResolveManager;Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerDataElementsForName;Lorg/jetbrains/kotlin/fir/resolve/calls/CandidateCollector;Lorg/jetbrains/kotlin/fir/resolve/calls/CandidateFactory;Lkotlin/jvm/functions/Function1;)V", "interceptTowerGroup", "towerGroup", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class InvokeReceiverResolveTask extends FirTowerResolveTask {
    private final Function1<TowerGroup, Unit> onSuccessfulLevel;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public InvokeReceiverResolveTask(BodyResolveComponents components, TowerResolveManager manager, TowerDataElementsForName towerDataElementsForName, CandidateCollector collector, CandidateFactory candidateFactory, Function1<? super TowerGroup, Unit> onSuccessfulLevel) {
        super(components, manager, towerDataElementsForName, collector, candidateFactory);
        Intrinsics.checkNotNullParameter(components, "components");
        Intrinsics.checkNotNullParameter(manager, "manager");
        Intrinsics.checkNotNullParameter(towerDataElementsForName, "towerDataElementsForName");
        Intrinsics.checkNotNullParameter(collector, "collector");
        Intrinsics.checkNotNullParameter(candidateFactory, "candidateFactory");
        Intrinsics.checkNotNullParameter(onSuccessfulLevel, "onSuccessfulLevel");
        this.onSuccessfulLevel = onSuccessfulLevel;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.calls.tower.FirBaseTowerResolveTask
    public TowerGroup interceptTowerGroup(TowerGroup towerGroup) {
        Intrinsics.checkNotNullParameter(towerGroup, "towerGroup");
        return towerGroup.InvokeResolvePriority(InvokeResolvePriority.INVOKE_RECEIVER);
    }

    @Override // org.jetbrains.kotlin.fir.resolve.calls.tower.FirBaseTowerResolveTask
    public void onSuccessfulLevel(TowerGroup towerGroup) {
        Intrinsics.checkNotNullParameter(towerGroup, "towerGroup");
        this.onSuccessfulLevel.invoke(towerGroup);
    }
}
