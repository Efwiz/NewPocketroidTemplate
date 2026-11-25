package org.jetbrains.kotlin.resolve.calls.tower;

import java.util.Collection;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.calls.tasks.ExplicitReceiverKind;
import org.jetbrains.kotlin.resolve.calls.tower.Candidate;
import org.jetbrains.kotlin.resolve.scopes.receivers.QualifierReceiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValueWithSmartCastInfo;

/* JADX INFO: compiled from: ScopeTowerProcessors.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0002\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003B]\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00000\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u00128\u0010\n\u001a4\u0012\u0004\u0012\u00020\f\u0012\u0015\u0012\u0013\u0018\u00010\r¢\u0006\f\b\u000e\u0012\b\b\u000f\u0012\u0004\b\b(\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00120\u00110\u000bj\u0002`\u0013¢\u0006\u0002\b\u0014¢\u0006\u0002\u0010\u0015J\u001e\u0010\u001c\u001a\u00020\u001d2\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u001f0\u00112\u0006\u0010\u000f\u001a\u00020 H\u0016J\u0016\u0010!\u001a\b\u0012\u0004\u0012\u00028\u00000\u00112\u0006\u0010\"\u001a\u00020\u001fH\u0016RC\u0010\n\u001a4\u0012\u0004\u0012\u00020\f\u0012\u0015\u0012\u0013\u0018\u00010\r¢\u0006\f\b\u000e\u0012\b\b\u000f\u0012\u0004\b\b(\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00120\u00110\u000bj\u0002`\u0013¢\u0006\u0002\b\u0014¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001b¨\u0006#"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/tower/QualifierScopeTowerProcessor;", "C", "Lorg/jetbrains/kotlin/resolve/calls/tower/Candidate;", "Lorg/jetbrains/kotlin/resolve/calls/tower/AbstractSimpleScopeTowerProcessor;", "scopeTower", "Lorg/jetbrains/kotlin/resolve/calls/tower/ImplicitScopeTower;", "context", "Lorg/jetbrains/kotlin/resolve/calls/tower/CandidateFactory;", "qualifier", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/QualifierReceiver;", "collectCandidates", "Lkotlin/Function2;", "Lorg/jetbrains/kotlin/resolve/calls/tower/ScopeTowerLevel;", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValueWithSmartCastInfo;", "Lkotlin/ParameterName;", "name", "extensionReceiver", "", "Lorg/jetbrains/kotlin/resolve/calls/tower/CandidateWithBoundDispatchReceiver;", "Lorg/jetbrains/kotlin/resolve/calls/tower/CandidatesCollector;", "Lkotlin/ExtensionFunctionType;", "(Lorg/jetbrains/kotlin/resolve/calls/tower/ImplicitScopeTower;Lorg/jetbrains/kotlin/resolve/calls/tower/CandidateFactory;Lorg/jetbrains/kotlin/resolve/scopes/receivers/QualifierReceiver;Lkotlin/jvm/functions/Function2;)V", "getCollectCandidates", "()Lkotlin/jvm/functions/Function2;", "getQualifier", "()Lorg/jetbrains/kotlin/resolve/scopes/receivers/QualifierReceiver;", "getScopeTower", "()Lorg/jetbrains/kotlin/resolve/calls/tower/ImplicitScopeTower;", "recordLookups", "", "skippedData", "Lorg/jetbrains/kotlin/resolve/calls/tower/TowerData;", "Lorg/jetbrains/kotlin/name/Name;", "simpleProcess", "data", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class QualifierScopeTowerProcessor<C extends Candidate> extends AbstractSimpleScopeTowerProcessor<C> {
    private final Function2<ScopeTowerLevel, ReceiverValueWithSmartCastInfo, Collection<CandidateWithBoundDispatchReceiver>> collectCandidates;
    private final QualifierReceiver qualifier;
    private final ImplicitScopeTower scopeTower;

    @Override // org.jetbrains.kotlin.resolve.calls.tower.ScopeTowerProcessor
    public void recordLookups(Collection<? extends TowerData> skippedData, Name name2) {
        Intrinsics.checkNotNullParameter(skippedData, "skippedData");
        Intrinsics.checkNotNullParameter(name2, "name");
    }

    public final ImplicitScopeTower getScopeTower() {
        return this.scopeTower;
    }

    public final QualifierReceiver getQualifier() {
        return this.qualifier;
    }

    public final Function2<ScopeTowerLevel, ReceiverValueWithSmartCastInfo, Collection<CandidateWithBoundDispatchReceiver>> getCollectCandidates() {
        return this.collectCandidates;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public QualifierScopeTowerProcessor(ImplicitScopeTower scopeTower, CandidateFactory<? extends C> context, QualifierReceiver qualifier, Function2<? super ScopeTowerLevel, ? super ReceiverValueWithSmartCastInfo, ? extends Collection<CandidateWithBoundDispatchReceiver>> collectCandidates) {
        super(context);
        Intrinsics.checkNotNullParameter(scopeTower, "scopeTower");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(qualifier, "qualifier");
        Intrinsics.checkNotNullParameter(collectCandidates, "collectCandidates");
        this.scopeTower = scopeTower;
        this.qualifier = qualifier;
        this.collectCandidates = collectCandidates;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.SimpleScopeTowerProcessor
    public Collection<C> simpleProcess(TowerData data) {
        Intrinsics.checkNotNullParameter(data, "data");
        return !Intrinsics.areEqual(data, TowerData.Empty.INSTANCE) ? CollectionsKt.emptyList() : createCandidates(this.collectCandidates.invoke(new QualifierScopeTowerLevel(this.scopeTower, this.qualifier), null), ExplicitReceiverKind.NO_EXPLICIT_RECEIVER, null);
    }
}
