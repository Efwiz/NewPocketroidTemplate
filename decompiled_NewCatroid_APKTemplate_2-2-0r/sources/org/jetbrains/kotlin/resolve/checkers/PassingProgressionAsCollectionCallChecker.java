package org.jetbrains.kotlin.resolve.checkers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.resolve.calls.KotlinCallResolver;
import org.jetbrains.kotlin.resolve.calls.checkers.CallCheckerWithAdditionalResolve;
import org.jetbrains.kotlin.resolve.calls.components.KotlinResolutionCallbacks;
import org.jetbrains.kotlin.resolve.calls.components.candidate.ResolutionCandidate;
import org.jetbrains.kotlin.resolve.calls.context.BasicCallResolutionContext;
import org.jetbrains.kotlin.resolve.calls.inference.model.TypeVariableFromCallableDescriptor;
import org.jetbrains.kotlin.resolve.calls.inference.model.TypeVariableTypeConstructor;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.model.MutableResolvedCallAtom;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.model.SimpleKotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.results.OverloadResolutionResults;
import org.jetbrains.kotlin.resolve.calls.tower.ImplicitScopeTower;
import org.jetbrains.kotlin.resolve.calls.tower.NewAbstractResolvedCall;
import org.jetbrains.kotlin.resolve.calls.tower.NewCallArgumentsKt;
import org.jetbrains.kotlin.resolve.calls.tower.NewResolvedCallImpl;
import org.jetbrains.kotlin.resolve.calls.tower.PSIKotlinCall;
import org.jetbrains.kotlin.resolve.calls.util.CallResolverUtilKt;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.types.AbstractTypeChecker;
import org.jetbrains.kotlin.types.FlexibleTypesKt;
import org.jetbrains.kotlin.types.IntersectionTypeConstructor;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.KotlinTypeFactory;
import org.jetbrains.kotlin.types.RangeUtilKt;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeAttributes;
import org.jetbrains.kotlin.types.TypeCheckerState;
import org.jetbrains.kotlin.types.TypeConstructor;
import org.jetbrains.kotlin.types.TypeProjectionImpl;
import org.jetbrains.kotlin.types.UnwrappedType;
import org.jetbrains.kotlin.types.checker.ClassicTypeCheckerState;
import org.jetbrains.kotlin.types.checker.IntersectionTypeKt;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: PassingProgressionAsCollectionCallChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J6\u0010\n\u001a\u00020\u000b2\n\u0010\f\u001a\u0006\u0012\u0002\b\u00030\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0002J6\u0010\n\u001a\u00020\u000b2\n\u0010\u0016\u001a\u0006\u0012\u0002\b\u00030\u00172\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J\u0010\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bH\u0002R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001c"}, d2 = {"Lorg/jetbrains/kotlin/resolve/checkers/PassingProgressionAsCollectionCallChecker;", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallCheckerWithAdditionalResolve;", "kotlinCallResolver", "Lorg/jetbrains/kotlin/resolve/calls/KotlinCallResolver;", "(Lorg/jetbrains/kotlin/resolve/calls/KotlinCallResolver;)V", "iterableProgressions", "", "", "typeCheckerState", "Lorg/jetbrains/kotlin/types/checker/ClassicTypeCheckerState;", "check", "", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "scopeTower", "Lorg/jetbrains/kotlin/resolve/calls/tower/ImplicitScopeTower;", "resolutionCallbacks", "Lorg/jetbrains/kotlin/resolve/calls/components/KotlinResolutionCallbacks;", "expectedType", "Lorg/jetbrains/kotlin/types/UnwrappedType;", "context", "Lorg/jetbrains/kotlin/resolve/calls/context/BasicCallResolutionContext;", "overloadResolutionResults", "Lorg/jetbrains/kotlin/resolve/calls/results/OverloadResolutionResults;", "makeCollectionOfAnyType", "Lorg/jetbrains/kotlin/types/KotlinType;", "builtIns", "Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PassingProgressionAsCollectionCallChecker implements CallCheckerWithAdditionalResolve {
    private final List<String> iterableProgressions;
    private final KotlinCallResolver kotlinCallResolver;
    private final ClassicTypeCheckerState typeCheckerState;

    public PassingProgressionAsCollectionCallChecker(KotlinCallResolver kotlinCallResolver) {
        Intrinsics.checkNotNullParameter(kotlinCallResolver, "kotlinCallResolver");
        this.kotlinCallResolver = kotlinCallResolver;
        this.typeCheckerState = new ClassicTypeCheckerState(false, false, null, null, null, 30, null);
        this.iterableProgressions = CollectionsKt.listOf((Object[]) new String[]{RangeUtilKt.CHAR_RANGE_FQN, RangeUtilKt.CHAR_PROGRESSION_FQN, RangeUtilKt.INT_RANGE_FQN, RangeUtilKt.INT_PROGRESSION_FQN, RangeUtilKt.LONG_RANGE_FQN, RangeUtilKt.LONG_PROGRESSION_FQN, RangeUtilKt.UINT_RANGE_FQN, RangeUtilKt.UINT_PROGRESSION_FQN, RangeUtilKt.ULONG_RANGE_FQN, RangeUtilKt.ULONG_PROGRESSION_FQN});
    }

    /* JADX WARN: Code duplicated, block: B:119:0x024b A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:85:0x01d6  */
    /* JADX WARN: Code duplicated, block: B:99:0x0249  */
    private final void check(ResolvedCall<?> resolvedCall, ImplicitScopeTower scopeTower, KotlinResolutionCallbacks resolutionCallbacks, UnwrappedType expectedType, BasicCallResolutionContext context) {
        Iterator<KotlinCallArgument> it;
        KotlinType type;
        KtExpression psiExpression;
        ValueParameterDescriptor valueParameterDescriptor;
        KotlinType type2;
        SimpleType simpleTypeUpperIfFlexible;
        Object next;
        TypeVariableTypeConstructor freshTypeConstructor;
        KotlinType rangeOrProgressionElementType;
        if (context.languageVersionSettings.supportsFeature(LanguageFeature.DisableCheckingChangedProgressionsResolve) || !(resolvedCall instanceof NewResolvedCallImpl)) {
            return;
        }
        NewResolvedCallImpl newResolvedCallImpl = (NewResolvedCallImpl) resolvedCall;
        PSIKotlinCall psiKotlinCall = newResolvedCallImpl.getPsiKotlinCall();
        List<KotlinCallArgument> argumentsInParenthesis = psiKotlinCall.getArgumentsInParenthesis();
        if (argumentsInParenthesis.isEmpty()) {
            argumentsInParenthesis = null;
        }
        if (argumentsInParenthesis == null) {
            return;
        }
        List<KotlinCallArgument> list = argumentsInParenthesis;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        for (KotlinCallArgument kotlinCallArgument : list) {
            if (kotlinCallArgument instanceof SimpleKotlinCallArgument) {
                KotlinType type3 = ((SimpleKotlinCallArgument) kotlinCallArgument).getReceiver().getReceiverValue().getType();
                Intrinsics.checkNotNullExpressionValue(type3, "getType(...)");
                rangeOrProgressionElementType = RangeUtilKt.getRangeOrProgressionElementType(type3, this.iterableProgressions);
            } else {
                rangeOrProgressionElementType = null;
            }
            arrayList.add(rangeOrProgressionElementType);
        }
        final ArrayList arrayList2 = arrayList;
        ArrayList arrayList3 = arrayList2;
        if ((arrayList3 instanceof Collection) && arrayList3.isEmpty()) {
            return;
        }
        Iterator it2 = arrayList3.iterator();
        while (it2.hasNext()) {
            if (((KotlinType) it2.next()) != null) {
                final KotlinBuiltIns builtIns = DescriptorUtilsKt.getBuiltIns(newResolvedCallImpl.get$suspendPropertyDescriptor());
                PSIKotlinCall pSIKotlinCallReplaceArguments = CallResolverUtilKt.replaceArguments(psiKotlinCall, CallResolverUtilKt.replaceTypes(argumentsInParenthesis, context, resolutionCallbacks, new Function2<Integer, UnwrappedType, UnwrappedType>() { // from class: org.jetbrains.kotlin.resolve.checkers.PassingProgressionAsCollectionCallChecker$check$newArguments$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    /* JADX WARN: Multi-variable type inference failed */
                    {
                        super(2);
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public /* bridge */ /* synthetic */ UnwrappedType invoke(Integer num, UnwrappedType unwrappedType) {
                        return invoke(num.intValue(), unwrappedType);
                    }

                    public final UnwrappedType invoke(int i, UnwrappedType type4) {
                        Intrinsics.checkNotNullParameter(type4, "type");
                        KotlinType kotlinType = arrayList2.get(i);
                        if (kotlinType == null) {
                            return null;
                        }
                        TypeAttributes empty = TypeAttributes.INSTANCE.getEmpty();
                        ClassDescriptor collection = builtIns.getCollection();
                        Intrinsics.checkNotNullExpressionValue(collection, "getCollection(...)");
                        return IntersectionTypeKt.m7477intersectTypes((List<? extends UnwrappedType>) CollectionsKt.listOf((Object[]) new UnwrappedType[]{KotlinTypeFactory.simpleNotNullType(empty, collection, CollectionsKt.listOf(new TypeProjectionImpl(kotlinType))), type4}));
                    }
                }), psiKotlinCall.getExplicitReceiver());
                ResolutionCandidate resolutionCandidate = (ResolutionCandidate) CollectionsKt.singleOrNull(this.kotlinCallResolver.resolveCall(scopeTower, resolutionCallbacks, pSIKotlinCallReplaceArguments, expectedType, context.collectAllCandidates));
                if (resolutionCandidate == null || Intrinsics.areEqual(resolutionCandidate.getDescriptor(), newResolvedCallImpl.get$suspendPropertyDescriptor()) || !resolutionCandidate.getIsSuccessful()) {
                    return;
                }
                KotlinType kotlinTypeMakeCollectionOfAnyType = makeCollectionOfAnyType(builtIns);
                Iterator<KotlinCallArgument> it3 = pSIKotlinCallReplaceArguments.getArgumentsInParenthesis().iterator();
                int i = 0;
                while (it3.hasNext()) {
                    int i2 = i + 1;
                    KotlinCallArgument next2 = it3.next();
                    if (CollectionsKt.getOrNull(arrayList2, i) != null) {
                        MutableResolvedCallAtom resolvedCall2 = resolutionCandidate.getResolvedCall();
                        ValueParameterDescriptor valueParameterDescriptor2 = resolvedCall2.getArgumentToCandidateParameter().get(next2);
                        if (valueParameterDescriptor2 == null || (type = valueParameterDescriptor2.getType()) == null) {
                            it = it3;
                        } else {
                            if (TypeUtilsKt.isTypeParameter(type)) {
                                Iterator<T> it4 = resolvedCall2.getFreshVariablesSubstitutor().getFreshVariables().iterator();
                                while (true) {
                                    if (!it4.hasNext()) {
                                        it = it3;
                                        next = null;
                                        break;
                                    } else {
                                        next = it4.next();
                                        it = it3;
                                        if (Intrinsics.areEqual(((TypeVariableFromCallableDescriptor) next).getOriginalTypeParameter(), type.getConstructor().getThis$0())) {
                                            break;
                                        } else {
                                            it3 = it;
                                        }
                                    }
                                }
                                TypeVariableFromCallableDescriptor typeVariableFromCallableDescriptor = (TypeVariableFromCallableDescriptor) next;
                                type = (typeVariableFromCallableDescriptor == null || (freshTypeConstructor = typeVariableFromCallableDescriptor.getFreshTypeConstructor()) == null) ? null : resolutionCallbacks.findResultType(resolutionCandidate.getSystem(), freshTypeConstructor);
                            } else {
                                it = it3;
                            }
                            if (type != null) {
                                TypeConstructor constructor = FlexibleTypesKt.upperIfFlexible(type).getConstructor();
                                if (Intrinsics.areEqual(constructor.getThis$0(), builtIns.getCollection())) {
                                    psiExpression = NewCallArgumentsKt.getPsiExpression(next2);
                                    if (psiExpression == null) {
                                        List<ValueParameterDescriptor> valueParameters = newResolvedCallImpl.get$suspendPropertyDescriptor().getValueParameters();
                                        Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
                                        valueParameterDescriptor = (ValueParameterDescriptor) CollectionsKt.getOrNull(valueParameters, i);
                                        if (valueParameterDescriptor == null && (type2 = valueParameterDescriptor.getType()) != null && (simpleTypeUpperIfFlexible = FlexibleTypesKt.upperIfFlexible(type2)) != null && !Intrinsics.areEqual(simpleTypeUpperIfFlexible.getConstructor().getThis$0(), builtIns.getIterable()) && (AbstractTypeChecker.isSubtypeOf$default(AbstractTypeChecker.INSTANCE, (TypeCheckerState) this.typeCheckerState, (KotlinTypeMarker) kotlinTypeMakeCollectionOfAnyType, (KotlinTypeMarker) simpleTypeUpperIfFlexible, false, 8, (Object) null) || TypeUtilsKt.isTypeParameter(simpleTypeUpperIfFlexible))) {
                                            context.trace.report(Errors.PROGRESSIONS_CHANGING_RESOLVE.on(resolutionCandidate.getCallComponents().getLanguageVersionSettings(), psiExpression, resolvedCall2.get_candidateDescriptor()));
                                        }
                                    }
                                } else if (constructor instanceof IntersectionTypeConstructor) {
                                    Collection<KotlinType> collectionMo7438getSupertypes = ((IntersectionTypeConstructor) constructor).mo7438getSupertypes();
                                    if (!(collectionMo7438getSupertypes instanceof Collection) || !collectionMo7438getSupertypes.isEmpty()) {
                                        Iterator<T> it5 = collectionMo7438getSupertypes.iterator();
                                        while (true) {
                                            if (it5.hasNext()) {
                                                if (Intrinsics.areEqual(((KotlinType) it5.next()).getConstructor().getThis$0(), builtIns.getCollection())) {
                                                    psiExpression = NewCallArgumentsKt.getPsiExpression(next2);
                                                    if (psiExpression == null) {
                                                        List<ValueParameterDescriptor> valueParameters2 = newResolvedCallImpl.get$suspendPropertyDescriptor().getValueParameters();
                                                        Intrinsics.checkNotNullExpressionValue(valueParameters2, "getValueParameters(...)");
                                                        valueParameterDescriptor = (ValueParameterDescriptor) CollectionsKt.getOrNull(valueParameters2, i);
                                                        if (valueParameterDescriptor == null) {
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        it = it3;
                    }
                    it3 = it;
                    i = i2;
                }
                return;
            }
        }
    }

    @Override // org.jetbrains.kotlin.resolve.calls.checkers.CallCheckerWithAdditionalResolve
    public void check(OverloadResolutionResults<?> overloadResolutionResults, ImplicitScopeTower scopeTower, KotlinResolutionCallbacks resolutionCallbacks, UnwrappedType expectedType, BasicCallResolutionContext context) {
        Intrinsics.checkNotNullParameter(overloadResolutionResults, "overloadResolutionResults");
        Intrinsics.checkNotNullParameter(scopeTower, "scopeTower");
        Intrinsics.checkNotNullParameter(resolutionCallbacks, "resolutionCallbacks");
        Intrinsics.checkNotNullParameter(context, "context");
        if (overloadResolutionResults.isSingleResult()) {
            ResolvedCall<?> resolvedCallMo7436getResultingCall = overloadResolutionResults.mo7436getResultingCall();
            NewAbstractResolvedCall newAbstractResolvedCall = resolvedCallMo7436getResultingCall instanceof NewAbstractResolvedCall ? (NewAbstractResolvedCall) resolvedCallMo7436getResultingCall : null;
            if (newAbstractResolvedCall == null) {
                return;
            }
            check(newAbstractResolvedCall, scopeTower, resolutionCallbacks, expectedType, context);
        }
    }

    private final KotlinType makeCollectionOfAnyType(KotlinBuiltIns builtIns) {
        TypeAttributes empty = TypeAttributes.INSTANCE.getEmpty();
        ClassDescriptor collection = builtIns.getCollection();
        Intrinsics.checkNotNullExpressionValue(collection, "getCollection(...)");
        return KotlinTypeFactory.simpleNotNullType(empty, collection, CollectionsKt.listOf(new TypeProjectionImpl(builtIns.getNullableAnyType())));
    }
}
