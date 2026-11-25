package org.jetbrains.kotlin.fir.resolve.calls.tower;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.resolve.calls.CallInfo;
import org.jetbrains.kotlin.fir.resolve.calls.CandidateCollector;
import org.jetbrains.kotlin.fir.resolve.calls.CandidateFactory;
import org.jetbrains.kotlin.fir.scopes.FirScope;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.resolve.calls.tasks.ExplicitReceiverKind;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: TowerLevelHandler.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\b\u0002\u0018\u00002\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00020\u0001B-\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\f¢\u0006\u0002\u0010\rJD\u0010\u0018\u001a\u00020\u00192\n\u0010\u001a\u001a\u0006\u0012\u0002\b\u00030\u00022\b\u0010\u001b\u001a\u0004\u0018\u00010\u001c2\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u001c0\u001e2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020\"H\u0016R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\t\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\u000b\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017¨\u0006$"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerScopeLevelProcessor;", "Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerScopeLevel$TowerScopeLevelProcessor;", "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "callInfo", "Lorg/jetbrains/kotlin/fir/resolve/calls/CallInfo;", "explicitReceiverKind", "Lorg/jetbrains/kotlin/resolve/calls/tasks/ExplicitReceiverKind;", "resultCollector", "Lorg/jetbrains/kotlin/fir/resolve/calls/CandidateCollector;", "candidateFactory", "Lorg/jetbrains/kotlin/fir/resolve/calls/CandidateFactory;", "group", "Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerGroup;", "(Lorg/jetbrains/kotlin/fir/resolve/calls/CallInfo;Lorg/jetbrains/kotlin/resolve/calls/tasks/ExplicitReceiverKind;Lorg/jetbrains/kotlin/fir/resolve/calls/CandidateCollector;Lorg/jetbrains/kotlin/fir/resolve/calls/CandidateFactory;Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerGroup;)V", "getCallInfo", "()Lorg/jetbrains/kotlin/fir/resolve/calls/CallInfo;", "getCandidateFactory", "()Lorg/jetbrains/kotlin/fir/resolve/calls/CandidateFactory;", "getExplicitReceiverKind", "()Lorg/jetbrains/kotlin/resolve/calls/tasks/ExplicitReceiverKind;", "getGroup", "()Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerGroup;", "getResultCollector", "()Lorg/jetbrains/kotlin/fir/resolve/calls/CandidateCollector;", "consumeCandidate", "", NativeSymbol.TYPE_NAME, "dispatchReceiver", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "givenExtensionReceiverOptions", "", "scope", "Lorg/jetbrains/kotlin/fir/scopes/FirScope;", "objectsByName", "", "isFromOriginalTypeInPresenceOfSmartCast", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class TowerScopeLevelProcessor implements TowerScopeLevel.TowerScopeLevelProcessor<FirBasedSymbol<?>> {
    private final CallInfo callInfo;
    private final CandidateFactory candidateFactory;
    private final ExplicitReceiverKind explicitReceiverKind;
    private final TowerGroup group;
    private final CandidateCollector resultCollector;

    public TowerScopeLevelProcessor(CallInfo callInfo, ExplicitReceiverKind explicitReceiverKind, CandidateCollector resultCollector, CandidateFactory candidateFactory, TowerGroup group) {
        Intrinsics.checkNotNullParameter(callInfo, "callInfo");
        Intrinsics.checkNotNullParameter(explicitReceiverKind, "explicitReceiverKind");
        Intrinsics.checkNotNullParameter(resultCollector, "resultCollector");
        Intrinsics.checkNotNullParameter(candidateFactory, "candidateFactory");
        Intrinsics.checkNotNullParameter(group, "group");
        this.callInfo = callInfo;
        this.explicitReceiverKind = explicitReceiverKind;
        this.resultCollector = resultCollector;
        this.candidateFactory = candidateFactory;
        this.group = group;
    }

    public final CallInfo getCallInfo() {
        return this.callInfo;
    }

    public final ExplicitReceiverKind getExplicitReceiverKind() {
        return this.explicitReceiverKind;
    }

    public final CandidateCollector getResultCollector() {
        return this.resultCollector;
    }

    public final CandidateFactory getCandidateFactory() {
        return this.candidateFactory;
    }

    public final TowerGroup getGroup() {
        return this.group;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.calls.tower.TowerScopeLevel.TowerScopeLevelProcessor
    public void consumeCandidate(FirBasedSymbol<?> symbol, FirExpression dispatchReceiver, List<? extends FirExpression> givenExtensionReceiverOptions, FirScope scope, boolean objectsByName, boolean isFromOriginalTypeInPresenceOfSmartCast) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        Intrinsics.checkNotNullParameter(givenExtensionReceiverOptions, "givenExtensionReceiverOptions");
        Intrinsics.checkNotNullParameter(scope, "scope");
        this.resultCollector.consumeCandidate(this.group, this.candidateFactory.createCandidate(this.callInfo, symbol, this.explicitReceiverKind, scope, dispatchReceiver, givenExtensionReceiverOptions, objectsByName, isFromOriginalTypeInPresenceOfSmartCast), this.candidateFactory.getContext());
    }
}
