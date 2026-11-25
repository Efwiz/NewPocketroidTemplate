package org.jetbrains.kotlin.fir.resolve.calls.tower;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.kotlin.fir.resolve.calls.CallInfo;
import org.jetbrains.kotlin.fir.resolve.calls.CandidateCollector;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX INFO: compiled from: FirInvokeResolveTowerExtension.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\n¢\u0006\u0002\b\u0004"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "towerGroup", "Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerGroup;", "invoke"}, k = 3, mv = {1, 9, 0}, xi = 48)
public final class FirInvokeResolveTowerExtension$enqueueInvokeReceiverTask$invokeReceiverProcessor$1 extends Lambda implements Function1<TowerGroup, Unit> {
    final /* synthetic */ CandidateCollector $collector;
    final /* synthetic */ CallInfo $info;
    final /* synthetic */ boolean $invokeBuiltinExtensionMode;
    final /* synthetic */ FirInvokeResolveTowerExtension this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FirInvokeResolveTowerExtension$enqueueInvokeReceiverTask$invokeReceiverProcessor$1(FirInvokeResolveTowerExtension firInvokeResolveTowerExtension, boolean z, CallInfo callInfo, CandidateCollector candidateCollector) {
        super(1);
        this.this$0 = firInvokeResolveTowerExtension;
        this.$invokeBuiltinExtensionMode = z;
        this.$info = callInfo;
        this.$collector = candidateCollector;
    }

    @Override // kotlin.jvm.functions.Function1
    public /* bridge */ /* synthetic */ Unit invoke(TowerGroup towerGroup) {
        invoke2(towerGroup);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
    public final void invoke2(TowerGroup towerGroup) {
        Intrinsics.checkNotNullParameter(towerGroup, "towerGroup");
        this.this$0.enqueueResolverTasksForInvokeReceiverCandidates(this.$invokeBuiltinExtensionMode, this.$info, towerGroup, this.$collector);
        this.$collector.newDataSet();
    }
}
