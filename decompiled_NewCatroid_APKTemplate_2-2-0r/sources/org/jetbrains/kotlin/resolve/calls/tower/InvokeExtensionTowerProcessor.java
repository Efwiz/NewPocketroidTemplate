package org.jetbrains.kotlin.resolve.calls.tower;

import java.util.Collection;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.calls.tower.Candidate;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValueWithSmartCastInfo;

/* JADX INFO: compiled from: InvokeProcessors.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u001e\n\u0000\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003B-\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00000\t\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u000b¢\u0006\u0002\u0010\fJ\u001d\u0010\u0011\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u00122\u0006\u0010\u0013\u001a\u00028\u0000H\u0014¢\u0006\u0002\u0010\u0014J\u0010\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0014J\u001e\u0010\u0019\u001a\u00020\u001a2\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00180\u001c2\u0006\u0010\u0006\u001a\u00020\u0007H\u0016R\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/tower/InvokeExtensionTowerProcessor;", "C", "Lorg/jetbrains/kotlin/resolve/calls/tower/Candidate;", "Lorg/jetbrains/kotlin/resolve/calls/tower/AbstractInvokeTowerProcessor;", "scopeTower", "Lorg/jetbrains/kotlin/resolve/calls/tower/ImplicitScopeTower;", "name", "Lorg/jetbrains/kotlin/name/Name;", "factoryProviderForInvoke", "Lorg/jetbrains/kotlin/resolve/calls/tower/CandidateFactoryProviderForInvoke;", "explicitReceiver", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValueWithSmartCastInfo;", "(Lorg/jetbrains/kotlin/resolve/calls/tower/ImplicitScopeTower;Lorg/jetbrains/kotlin/name/Name;Lorg/jetbrains/kotlin/resolve/calls/tower/CandidateFactoryProviderForInvoke;Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValueWithSmartCastInfo;)V", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "getScopeTower", "()Lorg/jetbrains/kotlin/resolve/calls/tower/ImplicitScopeTower;", "createInvokeProcessor", "Lorg/jetbrains/kotlin/resolve/calls/tower/ScopeTowerProcessor;", "variableCandidate", "(Lorg/jetbrains/kotlin/resolve/calls/tower/Candidate;)Lorg/jetbrains/kotlin/resolve/calls/tower/ScopeTowerProcessor;", "mayDataBeApplicable", "", "data", "Lorg/jetbrains/kotlin/resolve/calls/tower/TowerData;", "recordLookups", "", "skippedData", "", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class InvokeExtensionTowerProcessor<C extends Candidate> extends AbstractInvokeTowerProcessor<C> {
    private final ReceiverValueWithSmartCastInfo explicitReceiver;
    private final Name name;
    private final ImplicitScopeTower scopeTower;

    public final ImplicitScopeTower getScopeTower() {
        return this.scopeTower;
    }

    public final Name getName() {
        return this.name;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public InvokeExtensionTowerProcessor(ImplicitScopeTower scopeTower, Name name2, CandidateFactoryProviderForInvoke<C> factoryProviderForInvoke, ReceiverValueWithSmartCastInfo receiverValueWithSmartCastInfo) {
        super(factoryProviderForInvoke, ScopeTowerProcessorsKt.createVariableAndObjectProcessor$default(scopeTower, name2, factoryProviderForInvoke.factoryForVariable(true), null, false, 16, null));
        Intrinsics.checkNotNullParameter(scopeTower, "scopeTower");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(factoryProviderForInvoke, "factoryProviderForInvoke");
        this.scopeTower = scopeTower;
        this.name = name2;
        this.explicitReceiver = receiverValueWithSmartCastInfo;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.AbstractInvokeTowerProcessor
    protected ScopeTowerProcessor<C> createInvokeProcessor(C variableCandidate) {
        Intrinsics.checkNotNullParameter(variableCandidate, "variableCandidate");
        Pair<ReceiverValueWithSmartCastInfo, CandidateFactory<C>> pairFactoryForInvoke = getFactoryProviderForInvoke().factoryForInvoke(variableCandidate, true);
        if (pairFactoryForInvoke == null) {
            return null;
        }
        ReceiverValueWithSmartCastInfo receiverValueWithSmartCastInfoComponent1 = pairFactoryForInvoke.component1();
        CandidateFactory<C> candidateFactoryComponent2 = pairFactoryForInvoke.component2();
        CandidateWithBoundDispatchReceiver extensionInvokeCandidateDescriptor = InvokeProcessorsKt.getExtensionInvokeCandidateDescriptor(this.scopeTower, receiverValueWithSmartCastInfoComponent1);
        if (extensionInvokeCandidateDescriptor == null) {
            return null;
        }
        return new InvokeExtensionScopeTowerProcessor(candidateFactoryComponent2, extensionInvokeCandidateDescriptor, this.explicitReceiver);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.AbstractInvokeTowerProcessor
    protected boolean mayDataBeApplicable(TowerData data) {
        Intrinsics.checkNotNullParameter(data, "data");
        return Intrinsics.areEqual(data, TowerData.Empty.INSTANCE) || (data instanceof TowerData.OnlyImplicitReceiver);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.ScopeTowerProcessor
    public void recordLookups(Collection<? extends TowerData> skippedData, Name name2) {
        Intrinsics.checkNotNullParameter(skippedData, "skippedData");
        Intrinsics.checkNotNullParameter(name2, "name");
        getVariableProcessor().recordLookups(skippedData, name2);
    }
}
