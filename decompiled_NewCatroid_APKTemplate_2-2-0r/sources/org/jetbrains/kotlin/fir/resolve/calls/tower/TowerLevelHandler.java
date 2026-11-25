package org.jetbrains.kotlin.fir.resolve.calls.tower;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.fir.resolve.calls.CallInfo;
import org.jetbrains.kotlin.fir.resolve.calls.CallKind;
import org.jetbrains.kotlin.fir.resolve.calls.CandidateCollector;
import org.jetbrains.kotlin.fir.resolve.calls.CandidateFactory;
import org.jetbrains.kotlin.resolve.calls.tasks.ExplicitReceiverKind;

/* JADX INFO: compiled from: TowerLevelHandler.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J6\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerLevelHandler;", "", "()V", "processResult", "Lorg/jetbrains/kotlin/fir/resolve/calls/tower/ProcessResult;", "handleLevel", "collector", "Lorg/jetbrains/kotlin/fir/resolve/calls/CandidateCollector;", "candidateFactory", "Lorg/jetbrains/kotlin/fir/resolve/calls/CandidateFactory;", CompilerOptions.INFO, "Lorg/jetbrains/kotlin/fir/resolve/calls/CallInfo;", "explicitReceiverKind", "Lorg/jetbrains/kotlin/resolve/calls/tasks/ExplicitReceiverKind;", "group", "Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerGroup;", "towerLevel", "Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerScopeLevel;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TowerLevelHandler {
    private ProcessResult processResult = ProcessResult.SCOPE_EMPTY;

    public final ProcessResult handleLevel(CandidateCollector collector, CandidateFactory candidateFactory, CallInfo info, ExplicitReceiverKind explicitReceiverKind, TowerGroup group, TowerScopeLevel towerLevel) {
        Intrinsics.checkNotNullParameter(collector, "collector");
        Intrinsics.checkNotNullParameter(candidateFactory, "candidateFactory");
        Intrinsics.checkNotNullParameter(info, "info");
        Intrinsics.checkNotNullParameter(explicitReceiverKind, "explicitReceiverKind");
        Intrinsics.checkNotNullParameter(group, "group");
        Intrinsics.checkNotNullParameter(towerLevel, "towerLevel");
        this.processResult = ProcessResult.SCOPE_EMPTY;
        TowerScopeLevelProcessor towerScopeLevelProcessor = new TowerScopeLevelProcessor(info, explicitReceiverKind, collector, candidateFactory, group);
        CallKind callKind = info.getCallKind();
        if (Intrinsics.areEqual(callKind, CallKind.VariableAccess.INSTANCE)) {
            TowerScopeLevelProcessor towerScopeLevelProcessor2 = towerScopeLevelProcessor;
            this.processResult = this.processResult.plus(towerLevel.processPropertiesByName(info, towerScopeLevelProcessor2));
            if (!collector.getShouldStopResolve() && (towerLevel instanceof ScopeTowerLevel) && !((ScopeTowerLevel) towerLevel).areThereExtensionReceiverOptions()) {
                this.processResult = this.processResult.plus(towerLevel.processObjectsByName(info, towerScopeLevelProcessor2));
            }
        } else if (Intrinsics.areEqual(callKind, CallKind.Function.INSTANCE)) {
            this.processResult = this.processResult.plus(towerLevel.processFunctionsByName(info, towerScopeLevelProcessor));
        } else if (Intrinsics.areEqual(callKind, CallKind.CallableReference.INSTANCE)) {
            TowerScopeLevelProcessor towerScopeLevelProcessor3 = towerScopeLevelProcessor;
            ProcessResult processResultPlus = this.processResult.plus(towerLevel.processFunctionsByName(info, towerScopeLevelProcessor3));
            this.processResult = processResultPlus;
            this.processResult = processResultPlus.plus(towerLevel.processPropertiesByName(info, towerScopeLevelProcessor3));
        } else {
            throw new AssertionError("Unsupported call kind in tower resolver: " + info.getCallKind());
        }
        return this.processResult;
    }
}
