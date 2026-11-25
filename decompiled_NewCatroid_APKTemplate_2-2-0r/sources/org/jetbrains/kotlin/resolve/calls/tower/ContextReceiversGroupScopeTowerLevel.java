package org.jetbrains.kotlin.resolve.calls.tower;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.resolve.scopes.ResolutionScope;
import org.jetbrains.kotlin.resolve.scopes.SyntheticScopes;
import org.jetbrains.kotlin.resolve.scopes.SyntheticScopesKt;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValueWithSmartCastInfo;
import org.jetbrains.kotlin.types.AbstractStubType;
import org.jetbrains.kotlin.types.DynamicTypesKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.error.ErrorScope;
import org.jetbrains.kotlin.types.error.ThrowingScope;

/* JADX INFO: compiled from: TowerLevels.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u0001B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007J5\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\r2%\u0010\u000f\u001a!\u0012\u0004\u0012\u00020\u0011\u0012\u0006\u0012\u0004\u0018\u00010\u0012\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00130\r0\u0010¢\u0006\u0002\b\u0014H\u0002J \u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u000e0\r2\u0006\u0010\u0016\u001a\u00020\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u0006H\u0016J \u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u000e0\r2\u0006\u0010\u0016\u001a\u00020\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u0006H\u0016J \u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u000e0\r2\u0006\u0010\u0016\u001a\u00020\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u0006H\u0016J\u0010\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u0016\u001a\u00020\u0017H\u0016R\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/tower/ContextReceiversGroupScopeTowerLevel;", "Lorg/jetbrains/kotlin/resolve/calls/tower/AbstractScopeTowerLevel;", "scopeTower", "Lorg/jetbrains/kotlin/resolve/calls/tower/ImplicitScopeTower;", "contextReceiversGroup", "", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValueWithSmartCastInfo;", "(Lorg/jetbrains/kotlin/resolve/calls/tower/ImplicitScopeTower;Ljava/util/List;)V", "getContextReceiversGroup", "()Ljava/util/List;", "syntheticScopes", "Lorg/jetbrains/kotlin/resolve/scopes/SyntheticScopes;", "collectMembers", "", "Lorg/jetbrains/kotlin/resolve/calls/tower/CandidateWithBoundDispatchReceiver;", "getMembers", "Lkotlin/Function2;", "Lorg/jetbrains/kotlin/resolve/scopes/ResolutionScope;", "Lorg/jetbrains/kotlin/types/KotlinType;", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "Lkotlin/ExtensionFunctionType;", "getFunctions", "name", "Lorg/jetbrains/kotlin/name/Name;", "extensionReceiver", "getObjects", "getVariables", "recordLookup", "", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ContextReceiversGroupScopeTowerLevel extends AbstractScopeTowerLevel {
    private final List<ReceiverValueWithSmartCastInfo> contextReceiversGroup;
    private final SyntheticScopes syntheticScopes;

    public final List<ReceiverValueWithSmartCastInfo> getContextReceiversGroup() {
        return this.contextReceiversGroup;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ContextReceiversGroupScopeTowerLevel(ImplicitScopeTower scopeTower, List<ReceiverValueWithSmartCastInfo> contextReceiversGroup) {
        super(scopeTower);
        Intrinsics.checkNotNullParameter(scopeTower, "scopeTower");
        Intrinsics.checkNotNullParameter(contextReceiversGroup, "contextReceiversGroup");
        this.contextReceiversGroup = contextReceiversGroup;
        this.syntheticScopes = scopeTower.getSyntheticScopes();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Collection<CandidateWithBoundDispatchReceiver> collectMembers(Function2<? super ResolutionScope, ? super KotlinType, ? extends Collection<? extends CallableDescriptor>> getMembers) {
        ArrayList arrayList = new ArrayList(0);
        for (ReceiverValueWithSmartCastInfo receiverValueWithSmartCastInfo : this.contextReceiversGroup) {
            ReceiverValue receiverValue = receiverValueWithSmartCastInfo.getReceiverValue();
            MemberScope memberScope = receiverValue.getType().getMemberScope();
            if ((receiverValue.getType() instanceof AbstractStubType) && (memberScope instanceof ErrorScope) && !(memberScope instanceof ThrowingScope)) {
                return new ArrayList();
            }
            Iterator<T> it = getMembers.invoke(receiverValue.getType().getMemberScope(), receiverValue.getType()).iterator();
            while (it.hasNext()) {
                arrayList.add(AbstractScopeTowerLevel.createCandidateDescriptor$default(this, (CallableDescriptor) it.next(), receiverValueWithSmartCastInfo, null, null, 12, null));
            }
            ArrayList arrayList2 = arrayList;
            KotlinType type = receiverValue.getType();
            Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
            if (DynamicTypesKt.isDynamic(type)) {
                Iterator<T> it2 = getMembers.invoke(getScopeTower().getDynamicScope(), null).iterator();
                while (it2.hasNext()) {
                    arrayList2.add(AbstractScopeTowerLevel.createCandidateDescriptor$default(this, (CallableDescriptor) it2.next(), receiverValueWithSmartCastInfo, DynamicDescriptorDiagnostic.INSTANCE, null, 8, null));
                }
            }
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.ScopeTowerLevel
    public Collection<CandidateWithBoundDispatchReceiver> getVariables(final Name name2, final ReceiverValueWithSmartCastInfo extensionReceiver) {
        Intrinsics.checkNotNullParameter(name2, "name");
        List<ReceiverValueWithSmartCastInfo> list = this.contextReceiversGroup;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        for (final ReceiverValueWithSmartCastInfo receiverValueWithSmartCastInfo : list) {
            arrayList.add(collectMembers(new Function2<ResolutionScope, KotlinType, Collection<? extends CallableDescriptor>>() { // from class: org.jetbrains.kotlin.resolve.calls.tower.ContextReceiversGroupScopeTowerLevel$getVariables$1$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Collection<CallableDescriptor> invoke(ResolutionScope collectMembers, KotlinType kotlinType) {
                    Intrinsics.checkNotNullParameter(collectMembers, "$this$collectMembers");
                    return TowerLevelsKt.getContributedVariablesAndIntercept(collectMembers, name2, this.getLocation(), receiverValueWithSmartCastInfo, extensionReceiver, this.getScopeTower());
                }
            }));
        }
        return CollectionsKt.flatten(arrayList);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.ScopeTowerLevel
    public Collection<CandidateWithBoundDispatchReceiver> getObjects(Name name2, ReceiverValueWithSmartCastInfo extensionReceiver) {
        Intrinsics.checkNotNullParameter(name2, "name");
        return CollectionsKt.emptyList();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.ScopeTowerLevel
    public Collection<CandidateWithBoundDispatchReceiver> getFunctions(final Name name2, final ReceiverValueWithSmartCastInfo extensionReceiver) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Function1 function1 = new Function1<ReceiverValueWithSmartCastInfo, Collection<? extends CandidateWithBoundDispatchReceiver>>() { // from class: org.jetbrains.kotlin.resolve.calls.tower.ContextReceiversGroupScopeTowerLevel$getFunctions$collectMembers$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Collection<CandidateWithBoundDispatchReceiver> invoke(final ReceiverValueWithSmartCastInfo contextReceiver) {
                Intrinsics.checkNotNullParameter(contextReceiver, "contextReceiver");
                ContextReceiversGroupScopeTowerLevel contextReceiversGroupScopeTowerLevel = this.this$0;
                final Name name3 = name2;
                final ContextReceiversGroupScopeTowerLevel contextReceiversGroupScopeTowerLevel2 = this.this$0;
                final ReceiverValueWithSmartCastInfo receiverValueWithSmartCastInfo = extensionReceiver;
                return contextReceiversGroupScopeTowerLevel.collectMembers(new Function2<ResolutionScope, KotlinType, Collection<? extends CallableDescriptor>>() { // from class: org.jetbrains.kotlin.resolve.calls.tower.ContextReceiversGroupScopeTowerLevel$getFunctions$collectMembers$1.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(2);
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public final Collection<CallableDescriptor> invoke(ResolutionScope collectMembers, KotlinType kotlinType) {
                        Intrinsics.checkNotNullParameter(collectMembers, "$this$collectMembers");
                        return CollectionsKt.plus((Collection) CollectionsKt.plus(TowerLevelsKt.getContributedFunctionsAndIntercept(collectMembers, name3, contextReceiversGroupScopeTowerLevel2.getLocation(), contextReceiver, receiverValueWithSmartCastInfo, contextReceiversGroupScopeTowerLevel2.getScopeTower()), (Iterable) TowerLevelsKt.getInnerConstructors(kotlinType, name3, contextReceiversGroupScopeTowerLevel2.getLocation())), (Iterable) SyntheticScopesKt.collectSyntheticMemberFunctions(contextReceiversGroupScopeTowerLevel2.syntheticScopes, CollectionsKt.listOfNotNull(kotlinType), name3, contextReceiversGroupScopeTowerLevel2.getLocation()));
                    }
                });
            }
        };
        List<ReceiverValueWithSmartCastInfo> list = this.contextReceiversGroup;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(function1.invoke(it.next()));
        }
        return CollectionsKt.flatten(arrayList);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.ScopeTowerLevel
    public void recordLookup(Name name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        List<ReceiverValueWithSmartCastInfo> list = this.contextReceiversGroup;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(((ReceiverValueWithSmartCastInfo) it.next()).getAllOriginalTypes());
        }
        Iterator it2 = CollectionsKt.flatten(arrayList).iterator();
        while (it2.hasNext()) {
            ((KotlinType) it2.next()).getMemberScope().mo7479recordLookup(name2, getLocation());
        }
    }
}
