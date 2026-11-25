package org.jetbrains.kotlin.resolve.calls.tower;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.OverridingUtilsKt;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.resolve.scopes.ResolutionScope;
import org.jetbrains.kotlin.resolve.scopes.SyntheticScopes;
import org.jetbrains.kotlin.resolve.scopes.SyntheticScopesKt;
import org.jetbrains.kotlin.resolve.scopes.receivers.CastImplicitClassReceiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.ImplicitClassReceiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValueWithSmartCastInfo;
import org.jetbrains.kotlin.types.AbstractStubType;
import org.jetbrains.kotlin.types.DynamicTypesKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeApproximator;
import org.jetbrains.kotlin.types.TypeApproximatorConfiguration;
import org.jetbrains.kotlin.types.TypeProjection;
import org.jetbrains.kotlin.types.TypeSubstitution;
import org.jetbrains.kotlin.types.TypeSubstitutor;
import org.jetbrains.kotlin.types.UnwrappedType;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.kotlin.types.error.ErrorScope;
import org.jetbrains.kotlin.types.error.ThrowingScope;

/* JADX INFO: compiled from: TowerLevels.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0005\b\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J5\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u00102%\u0010\u0012\u001a!\u0012\u0004\u0012\u00020\u0014\u0012\u0006\u0012\u0004\u0018\u00010\u0015\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00160\u00100\u0013¢\u0006\u0002\b\u0017H\u0002J \u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00110\u00102\u0006\u0010\u0019\u001a\u00020\u001a2\b\u0010\u001b\u001a\u0004\u0018\u00010\u0005H\u0016J \u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00110\u00102\u0006\u0010\u0019\u001a\u00020\u001a2\b\u0010\u001b\u001a\u0004\u0018\u00010\u0005H\u0016J \u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00110\u00102\u0006\u0010\u0019\u001a\u00020\u001a2\b\u0010\u001b\u001a\u0004\u0018\u00010\u0005H\u0016J\u0010\u0010\u001e\u001a\u00020\u001f2\u0006\u0010\u0019\u001a\u00020\u001aH\u0016J\u0014\u0010 \u001a\u00020\u0016*\u00020\u00162\u0006\u0010!\u001a\u00020\u000eH\u0002J\u0014\u0010\"\u001a\u00020\u0005*\u00020\u00052\u0006\u0010#\u001a\u00020\u0015H\u0002R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006$"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/tower/MemberScopeTowerLevel;", "Lorg/jetbrains/kotlin/resolve/calls/tower/AbstractScopeTowerLevel;", "scopeTower", "Lorg/jetbrains/kotlin/resolve/calls/tower/ImplicitScopeTower;", "dispatchReceiver", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValueWithSmartCastInfo;", "(Lorg/jetbrains/kotlin/resolve/calls/tower/ImplicitScopeTower;Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValueWithSmartCastInfo;)V", "getDispatchReceiver", "()Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValueWithSmartCastInfo;", "isNewInferenceEnabled", "", "syntheticScopes", "Lorg/jetbrains/kotlin/resolve/scopes/SyntheticScopes;", "typeApproximator", "Lorg/jetbrains/kotlin/types/TypeApproximator;", "collectMembers", "", "Lorg/jetbrains/kotlin/resolve/calls/tower/CandidateWithBoundDispatchReceiver;", "getMembers", "Lkotlin/Function2;", "Lorg/jetbrains/kotlin/resolve/scopes/ResolutionScope;", "Lorg/jetbrains/kotlin/types/KotlinType;", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "Lkotlin/ExtensionFunctionType;", "getFunctions", "name", "Lorg/jetbrains/kotlin/name/Name;", "extensionReceiver", "getObjects", "getVariables", "recordLookup", "", "approximateCapturedTypes", "approximator", "smartCastReceiver", "targetType", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class MemberScopeTowerLevel extends AbstractScopeTowerLevel {
    private final ReceiverValueWithSmartCastInfo dispatchReceiver;
    private final boolean isNewInferenceEnabled;
    private final SyntheticScopes syntheticScopes;
    private final TypeApproximator typeApproximator;

    public final ReceiverValueWithSmartCastInfo getDispatchReceiver() {
        return this.dispatchReceiver;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MemberScopeTowerLevel(ImplicitScopeTower scopeTower, ReceiverValueWithSmartCastInfo dispatchReceiver) {
        super(scopeTower);
        Intrinsics.checkNotNullParameter(scopeTower, "scopeTower");
        Intrinsics.checkNotNullParameter(dispatchReceiver, "dispatchReceiver");
        this.dispatchReceiver = dispatchReceiver;
        this.syntheticScopes = scopeTower.getSyntheticScopes();
        this.isNewInferenceEnabled = scopeTower.isNewInferenceEnabled();
        this.typeApproximator = scopeTower.getTypeApproximator();
    }

    private final Collection<CandidateWithBoundDispatchReceiver> collectMembers(Function2<? super ResolutionScope, ? super KotlinType, ? extends Collection<? extends CallableDescriptor>> getMembers) {
        ReceiverValue receiverValue = this.dispatchReceiver.getReceiverValue();
        MemberScope memberScope = receiverValue.getType().getMemberScope();
        if ((receiverValue.getType() instanceof AbstractStubType) && (memberScope instanceof ErrorScope) && !(memberScope instanceof ThrowingScope)) {
            return new ArrayList();
        }
        ArrayList arrayList = new ArrayList(0);
        Iterator<T> it = getMembers.invoke(receiverValue.getType().getMemberScope(), receiverValue.getType()).iterator();
        while (it.hasNext()) {
            arrayList.add(AbstractScopeTowerLevel.createCandidateDescriptor$default(this, (CallableDescriptor) it.next(), this.dispatchReceiver, null, null, 12, null));
        }
        ArrayList arrayList2 = arrayList;
        UnstableSmartCastDiagnostic unstableSmartCastDiagnostic = this.dispatchReceiver.getIsStable() ? null : UnstableSmartCastDiagnostic.INSTANCE;
        ArrayList arrayList3 = unstableSmartCastDiagnostic != null ? new ArrayList(0) : null;
        for (KotlinType kotlinType : this.dispatchReceiver.getTypesFromSmartCasts()) {
            Collection<? extends CallableDescriptor> collectionInvoke = getMembers.invoke(kotlinType.getMemberScope(), kotlinType);
            ArrayList arrayList4 = arrayList3 == null ? arrayList : arrayList3;
            Iterator<T> it2 = collectionInvoke.iterator();
            while (it2.hasNext()) {
                arrayList4.add(createCandidateDescriptor((CallableDescriptor) it2.next(), smartCastReceiver(this.dispatchReceiver, kotlinType), unstableSmartCastDiagnostic, kotlinType));
            }
        }
        if (this.dispatchReceiver.hasTypesFromSmartCasts()) {
            if (arrayList3 == null) {
                arrayList.retainAll(OverridingUtilsKt.selectMostSpecificInEachOverridableGroup(arrayList2, new Function1<CandidateWithBoundDispatchReceiver, CallableDescriptor>() { // from class: org.jetbrains.kotlin.resolve.calls.tower.MemberScopeTowerLevel.collectMembers.3
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final CallableDescriptor invoke(CandidateWithBoundDispatchReceiver selectMostSpecificInEachOverridableGroup) {
                        Intrinsics.checkNotNullParameter(selectMostSpecificInEachOverridableGroup, "$this$selectMostSpecificInEachOverridableGroup");
                        return MemberScopeTowerLevel.this.approximateCapturedTypes(selectMostSpecificInEachOverridableGroup.getDescriptor(), MemberScopeTowerLevel.this.typeApproximator);
                    }
                }));
            } else {
                arrayList.addAll(OverridingUtilsKt.selectMostSpecificInEachOverridableGroup(arrayList3, new Function1<CandidateWithBoundDispatchReceiver, CallableDescriptor>() { // from class: org.jetbrains.kotlin.resolve.calls.tower.MemberScopeTowerLevel.collectMembers.4
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final CallableDescriptor invoke(CandidateWithBoundDispatchReceiver selectMostSpecificInEachOverridableGroup) {
                        Intrinsics.checkNotNullParameter(selectMostSpecificInEachOverridableGroup, "$this$selectMostSpecificInEachOverridableGroup");
                        return MemberScopeTowerLevel.this.approximateCapturedTypes(selectMostSpecificInEachOverridableGroup.getDescriptor(), MemberScopeTowerLevel.this.typeApproximator);
                    }
                }));
            }
        }
        KotlinType type = receiverValue.getType();
        Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
        if (DynamicTypesKt.isDynamic(type)) {
            Iterator<T> it3 = getMembers.invoke(getScopeTower().getDynamicScope(), null).iterator();
            while (it3.hasNext()) {
                arrayList2.add(AbstractScopeTowerLevel.createCandidateDescriptor$default(this, (CallableDescriptor) it3.next(), this.dispatchReceiver, DynamicDescriptorDiagnostic.INSTANCE, null, 8, null));
            }
        }
        return arrayList2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final CallableDescriptor approximateCapturedTypes(CallableDescriptor callableDescriptor, final TypeApproximator typeApproximator) {
        if (!this.isNewInferenceEnabled) {
            return callableDescriptor;
        }
        TypeSubstitutor typeSubstitutorCreate = TypeSubstitutor.create(new TypeSubstitution() { // from class: org.jetbrains.kotlin.resolve.calls.tower.MemberScopeTowerLevel$approximateCapturedTypes$wrappedSubstitution$1

            /* JADX INFO: compiled from: TowerLevels.kt */
            @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
            public /* synthetic */ class WhenMappings {
                public static final /* synthetic */ int[] $EnumSwitchMapping$0;

                static {
                    int[] iArr = new int[Variance.values().length];
                    try {
                        iArr[Variance.INVARIANT.ordinal()] = 1;
                    } catch (NoSuchFieldError unused) {
                    }
                    try {
                        iArr[Variance.OUT_VARIANCE.ordinal()] = 2;
                    } catch (NoSuchFieldError unused2) {
                    }
                    try {
                        iArr[Variance.IN_VARIANCE.ordinal()] = 3;
                    } catch (NoSuchFieldError unused3) {
                    }
                    $EnumSwitchMapping$0 = iArr;
                }
            }

            @Override // org.jetbrains.kotlin.types.TypeSubstitution
            /* JADX INFO: renamed from: get */
            public TypeProjection mo7475get(KotlinType key) {
                Intrinsics.checkNotNullParameter(key, "key");
                return null;
            }

            @Override // org.jetbrains.kotlin.types.TypeSubstitution
            public KotlinType prepareTopLevelType(KotlinType topLevelType, Variance position) {
                UnwrappedType unwrappedTypeApproximateToSuperType;
                Intrinsics.checkNotNullParameter(topLevelType, "topLevelType");
                Intrinsics.checkNotNullParameter(position, "position");
                int i = WhenMappings.$EnumSwitchMapping$0[position.ordinal()];
                if (i == 1) {
                    unwrappedTypeApproximateToSuperType = null;
                } else if (i == 2) {
                    unwrappedTypeApproximateToSuperType = typeApproximator.approximateToSuperType(topLevelType.unwrap(), (TypeApproximatorConfiguration) TypeApproximatorConfiguration.InternalTypesApproximation.INSTANCE);
                } else {
                    if (i != 3) {
                        throw new NoWhenBranchMatchedException();
                    }
                    unwrappedTypeApproximateToSuperType = typeApproximator.approximateToSubType(topLevelType.unwrap(), (TypeApproximatorConfiguration) TypeApproximatorConfiguration.InternalTypesApproximation.INSTANCE);
                }
                return unwrappedTypeApproximateToSuperType != null ? unwrappedTypeApproximateToSuperType : topLevelType;
            }
        });
        Intrinsics.checkNotNullExpressionValue(typeSubstitutorCreate, "create(...)");
        CallableDescriptor callableDescriptorSubstitute = callableDescriptor.substitute(typeSubstitutorCreate);
        Intrinsics.checkNotNullExpressionValue(callableDescriptorSubstitute, "substitute(...)");
        return callableDescriptorSubstitute;
    }

    private final ReceiverValueWithSmartCastInfo smartCastReceiver(ReceiverValueWithSmartCastInfo receiverValueWithSmartCastInfo, KotlinType kotlinType) {
        return !(receiverValueWithSmartCastInfo.getReceiverValue() instanceof ImplicitClassReceiver) ? receiverValueWithSmartCastInfo : new ReceiverValueWithSmartCastInfo(new CastImplicitClassReceiver(((ImplicitClassReceiver) receiverValueWithSmartCastInfo.getReceiverValue()).getClassDescriptor(), kotlinType), receiverValueWithSmartCastInfo.getTypesFromSmartCasts(), receiverValueWithSmartCastInfo.getIsStable(), null, 8, null);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.ScopeTowerLevel
    public Collection<CandidateWithBoundDispatchReceiver> getVariables(final Name name2, final ReceiverValueWithSmartCastInfo extensionReceiver) {
        Intrinsics.checkNotNullParameter(name2, "name");
        return collectMembers(new Function2<ResolutionScope, KotlinType, Collection<? extends CallableDescriptor>>() { // from class: org.jetbrains.kotlin.resolve.calls.tower.MemberScopeTowerLevel.getVariables.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Collection<CallableDescriptor> invoke(ResolutionScope collectMembers, KotlinType kotlinType) {
                Intrinsics.checkNotNullParameter(collectMembers, "$this$collectMembers");
                return TowerLevelsKt.getContributedVariablesAndIntercept(collectMembers, name2, this.getLocation(), this.getDispatchReceiver(), extensionReceiver, this.getScopeTower());
            }
        });
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.ScopeTowerLevel
    public Collection<CandidateWithBoundDispatchReceiver> getObjects(Name name2, ReceiverValueWithSmartCastInfo extensionReceiver) {
        Intrinsics.checkNotNullParameter(name2, "name");
        return CollectionsKt.emptyList();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.ScopeTowerLevel
    public Collection<CandidateWithBoundDispatchReceiver> getFunctions(final Name name2, final ReceiverValueWithSmartCastInfo extensionReceiver) {
        Intrinsics.checkNotNullParameter(name2, "name");
        return collectMembers(new Function2<ResolutionScope, KotlinType, Collection<? extends CallableDescriptor>>() { // from class: org.jetbrains.kotlin.resolve.calls.tower.MemberScopeTowerLevel.getFunctions.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Collection<CallableDescriptor> invoke(ResolutionScope collectMembers, KotlinType kotlinType) {
                Intrinsics.checkNotNullParameter(collectMembers, "$this$collectMembers");
                return CollectionsKt.plus((Collection) CollectionsKt.plus(TowerLevelsKt.getContributedFunctionsAndIntercept(collectMembers, name2, this.getLocation(), this.getDispatchReceiver(), extensionReceiver, this.getScopeTower()), (Iterable) TowerLevelsKt.getInnerConstructors(kotlinType, name2, this.getLocation())), (Iterable) SyntheticScopesKt.collectSyntheticMemberFunctions(this.syntheticScopes, CollectionsKt.listOfNotNull(kotlinType), name2, this.getLocation()));
            }
        });
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.ScopeTowerLevel
    public void recordLookup(Name name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Iterator<KotlinType> it = this.dispatchReceiver.getAllOriginalTypes().iterator();
        while (it.hasNext()) {
            it.next().getMemberScope().mo7479recordLookup(name2, getLocation());
        }
    }
}
