package org.jetbrains.kotlin.resolve.checkers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory4;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtLambdaExpression;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.calls.KotlinCallResolver;
import org.jetbrains.kotlin.resolve.calls.checkers.CallCheckerWithAdditionalResolve;
import org.jetbrains.kotlin.resolve.calls.components.ArgumentsUtilsKt;
import org.jetbrains.kotlin.resolve.calls.components.InferenceSession;
import org.jetbrains.kotlin.resolve.calls.components.KotlinResolutionCallbacks;
import org.jetbrains.kotlin.resolve.calls.context.BasicCallResolutionContext;
import org.jetbrains.kotlin.resolve.calls.inference.BuilderInferenceSession;
import org.jetbrains.kotlin.resolve.calls.inference.components.NewTypeSubstitutor;
import org.jetbrains.kotlin.resolve.calls.inference.components.NewTypeSubstitutorByConstructorMap;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.model.LambdaKotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.model.ReceiverExpressionKotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.model.ReceiverKotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.model.SimpleKotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.results.OverloadResolutionResults;
import org.jetbrains.kotlin.resolve.calls.tower.ImplicitScopeTower;
import org.jetbrains.kotlin.resolve.calls.tower.NewAbstractResolvedCall;
import org.jetbrains.kotlin.resolve.calls.tower.NewCallArgumentsKt;
import org.jetbrains.kotlin.resolve.calls.tower.PSIErrorCallInfo;
import org.jetbrains.kotlin.resolve.calls.tower.PSIKotlinCall;
import org.jetbrains.kotlin.resolve.calls.tower.PSIKotlinCallsKt;
import org.jetbrains.kotlin.resolve.calls.util.BuilderLambdaLabelingInfo;
import org.jetbrains.kotlin.resolve.calls.util.CallResolverUtilKt;
import org.jetbrains.kotlin.resolve.scopes.LexicalScope;
import org.jetbrains.kotlin.resolve.scopes.receivers.ExpressionReceiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.ExtensionReceiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValueWithSmartCastInfo;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.StubTypeForBuilderInference;
import org.jetbrains.kotlin.types.TypeConstructor;
import org.jetbrains.kotlin.types.UnwrappedType;
import org.jetbrains.kotlin.types.checker.NewTypeVariableConstructor;

/* JADX INFO: compiled from: ResolutionWithStubTypesChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0080\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J6\u0010\u0005\u001a\u00020\u00062\n\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J>\u0010\u0011\u001a\u00020\u00062\u0006\u0010\u000f\u001a\u00020\u00102\n\u0010\u0012\u001a\u0006\u0012\u0002\b\u00030\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\t\u001a\u00020\nH\u0002JF\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00180\u00172\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001a2\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001a2\u0006\u0010\u000f\u001a\u00020\u00102\u0012\u0010\u001d\u001a\u000e\u0012\u0004\u0012\u00020\u001f\u0012\u0004\u0012\u00020\u000e0\u001eH\u0002JJ\u0010 \u001a\b\u0012\u0004\u0012\u00020\u00180\u00172\u0006\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020\"2\u0006\u0010$\u001a\u00020%2\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u000f\u001a\u00020\u00102\u0012\u0010\u001d\u001a\u000e\u0012\u0004\u0012\u00020\u001f\u0012\u0004\u0012\u00020\u000e0\u001eH\u0002J\u001e\u0010&\u001a\u0004\u0018\u00010'*\u00020\"2\u0006\u0010(\u001a\u00020)2\u0006\u0010\u000f\u001a\u00020\u0010H\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006*"}, d2 = {"Lorg/jetbrains/kotlin/resolve/checkers/ResolutionWithStubTypesChecker;", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallCheckerWithAdditionalResolve;", "kotlinCallResolver", "Lorg/jetbrains/kotlin/resolve/calls/KotlinCallResolver;", "(Lorg/jetbrains/kotlin/resolve/calls/KotlinCallResolver;)V", "check", "", "overloadResolutionResults", "Lorg/jetbrains/kotlin/resolve/calls/results/OverloadResolutionResults;", "scopeTower", "Lorg/jetbrains/kotlin/resolve/calls/tower/ImplicitScopeTower;", "resolutionCallbacks", "Lorg/jetbrains/kotlin/resolve/calls/components/KotlinResolutionCallbacks;", "expectedType", "Lorg/jetbrains/kotlin/types/UnwrappedType;", "context", "Lorg/jetbrains/kotlin/resolve/calls/context/BasicCallResolutionContext;", "processResolutionAmbiguityError", "firstResolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/tower/NewAbstractResolvedCall;", "lambda", "Lorg/jetbrains/kotlin/resolve/calls/model/LambdaKotlinCallArgument;", "reportStubTypeCausesAmbiguityOnArgumentsIfNeeded", "", "Lorg/jetbrains/kotlin/types/checker/NewTypeVariableConstructor;", "valueArguments", "", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallArgument;", "newArguments", "substitutionMap", "", "Lorg/jetbrains/kotlin/types/TypeConstructor;", "reportStubTypeCausesAmbiguityOnReceiverIfNeeded", "receiver", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValue;", "newReceiver", "kotlinCall", "Lorg/jetbrains/kotlin/resolve/calls/tower/PSIKotlinCall;", "buildSubstitutedReceiverArgument", "Lorg/jetbrains/kotlin/resolve/calls/model/ReceiverExpressionKotlinCallArgument;", "substitutor", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/NewTypeSubstitutor;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ResolutionWithStubTypesChecker implements CallCheckerWithAdditionalResolve {
    private final KotlinCallResolver kotlinCallResolver;

    public ResolutionWithStubTypesChecker(KotlinCallResolver kotlinCallResolver) {
        Intrinsics.checkNotNullParameter(kotlinCallResolver, "kotlinCallResolver");
        this.kotlinCallResolver = kotlinCallResolver;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.checkers.CallCheckerWithAdditionalResolve
    public void check(OverloadResolutionResults<?> overloadResolutionResults, ImplicitScopeTower scopeTower, KotlinResolutionCallbacks resolutionCallbacks, UnwrappedType expectedType, BasicCallResolutionContext context) {
        Intrinsics.checkNotNullParameter(overloadResolutionResults, "overloadResolutionResults");
        Intrinsics.checkNotNullParameter(scopeTower, "scopeTower");
        Intrinsics.checkNotNullParameter(resolutionCallbacks, "resolutionCallbacks");
        Intrinsics.checkNotNullParameter(context, "context");
        if (overloadResolutionResults.isSingleResult()) {
            Object objMo7436getResultingCall = overloadResolutionResults.mo7436getResultingCall();
            NewAbstractResolvedCall newAbstractResolvedCall = objMo7436getResultingCall instanceof NewAbstractResolvedCall ? (NewAbstractResolvedCall) objMo7436getResultingCall : null;
            if (newAbstractResolvedCall == null) {
                return;
            }
            List listPlus = CollectionsKt.plus((Collection<? extends KotlinCallArgument>) newAbstractResolvedCall.getPsiKotlinCall().getArgumentsInParenthesis(), newAbstractResolvedCall.getPsiKotlinCall().getExternalArgument());
            ArrayList arrayList = new ArrayList();
            for (Object obj : listPlus) {
                if (obj instanceof LambdaKotlinCallArgument) {
                    arrayList.add(obj);
                }
            }
            ArrayList<LambdaKotlinCallArgument> arrayList2 = new ArrayList();
            for (Object obj2 : arrayList) {
                if (((LambdaKotlinCallArgument) obj2).getHasBuilderInferenceAnnotation()) {
                    arrayList2.add(obj2);
                }
            }
            for (LambdaKotlinCallArgument lambdaKotlinCallArgument : arrayList2) {
                InferenceSession builderInferenceSession = lambdaKotlinCallArgument.getBuilderInferenceSession();
                BuilderInferenceSession builderInferenceSession2 = builderInferenceSession instanceof BuilderInferenceSession ? (BuilderInferenceSession) builderInferenceSession : null;
                if (builderInferenceSession2 != null) {
                    Iterator<PSIErrorCallInfo<CallableDescriptor>> it = builderInferenceSession2.getErrorCallsInfo().iterator();
                    while (it.hasNext()) {
                        OverloadResolutionResults<D> result = it.next().getResult();
                        if (result.isAmbiguity()) {
                            Collection resultingCalls = result.getResultingCalls();
                            Intrinsics.checkNotNullExpressionValue(resultingCalls, "getResultingCalls(...)");
                            Object objFirst = CollectionsKt.first(resultingCalls);
                            NewAbstractResolvedCall<?> newAbstractResolvedCall2 = objFirst instanceof NewAbstractResolvedCall ? (NewAbstractResolvedCall) objFirst : null;
                            if (newAbstractResolvedCall2 != null) {
                                processResolutionAmbiguityError(context, newAbstractResolvedCall2, lambdaKotlinCallArgument, resolutionCallbacks, expectedType, scopeTower);
                            }
                        }
                    }
                }
            }
        }
    }

    private final void processResolutionAmbiguityError(BasicCallResolutionContext context, NewAbstractResolvedCall<?> firstResolvedCall, LambdaKotlinCallArgument lambda, KotlinResolutionCallbacks resolutionCallbacks, UnwrappedType expectedType, ImplicitScopeTower scopeTower) {
        PSIKotlinCall psiKotlinCall = firstResolvedCall.getPsiKotlinCall();
        KtExpression calleeExpression = psiKotlinCall.getPsiCall().getCalleeExpression();
        KtExpression calleeExpression2 = context.call.getCalleeExpression();
        if (calleeExpression == null || calleeExpression2 == null) {
            return;
        }
        ReceiverValue extensionReceiver = firstResolvedCall.getExtensionReceiver();
        List<KotlinCallArgument> argumentsInParenthesis = psiKotlinCall.getArgumentsInParenthesis();
        InferenceSession builderInferenceSession = lambda.getBuilderInferenceSession();
        Intrinsics.checkNotNull(builderInferenceSession, "null cannot be cast to non-null type org.jetbrains.kotlin.resolve.calls.inference.BuilderInferenceSession");
        BuilderInferenceSession builderInferenceSession2 = (BuilderInferenceSession) builderInferenceSession;
        final NewTypeSubstitutor notFixedToInferredTypesSubstitutor = builderInferenceSession2.getNotFixedToInferredTypesSubstitutor();
        Set<StubTypeForBuilderInference> usedStubTypes = builderInferenceSession2.getUsedStubTypes();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(usedStubTypes, 10));
        Iterator<T> it = usedStubTypes.iterator();
        while (it.hasNext()) {
            arrayList.add(((StubTypeForBuilderInference) it.next()).getOriginalTypeVariable());
        }
        ArrayList arrayList2 = arrayList;
        NewTypeSubstitutor currentSubstitutor = builderInferenceSession2.getCurrentSubstitutor();
        NewTypeSubstitutorByConstructorMap newTypeSubstitutorByConstructorMap = currentSubstitutor instanceof NewTypeSubstitutorByConstructorMap ? (NewTypeSubstitutorByConstructorMap) currentSubstitutor : null;
        if (newTypeSubstitutorByConstructorMap == null) {
            return;
        }
        Map<TypeConstructor, UnwrappedType> map = newTypeSubstitutorByConstructorMap.getMap();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Map.Entry<TypeConstructor, UnwrappedType> entry : map.entrySet()) {
            if (CollectionsKt.contains(arrayList2, entry.getKey())) {
                linkedHashMap.put(entry.getKey(), entry.getValue());
            }
        }
        LinkedHashMap linkedHashMap2 = linkedHashMap;
        ReceiverExpressionKotlinCallArgument receiverExpressionKotlinCallArgumentBuildSubstitutedReceiverArgument = extensionReceiver != null ? buildSubstitutedReceiverArgument(extensionReceiver, notFixedToInferredTypesSubstitutor, context) : null;
        List<KotlinCallArgument> listReplaceTypes = CallResolverUtilKt.replaceTypes(argumentsInParenthesis, context, resolutionCallbacks, new Function2<Integer, UnwrappedType, UnwrappedType>() { // from class: org.jetbrains.kotlin.resolve.checkers.ResolutionWithStubTypesChecker$processResolutionAmbiguityError$newArguments$1
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ UnwrappedType invoke(Integer num, UnwrappedType unwrappedType) {
                return invoke(num.intValue(), unwrappedType);
            }

            public final UnwrappedType invoke(int i, UnwrappedType type) {
                Intrinsics.checkNotNullParameter(type, "type");
                return notFixedToInferredTypesSubstitutor.safeSubstitute(type);
            }
        });
        if (receiverExpressionKotlinCallArgumentBuildSubstitutedReceiverArgument == null && Intrinsics.areEqual(argumentsInParenthesis, listReplaceTypes)) {
            return;
        }
        ReceiverExpressionKotlinCallArgument receiverExpressionKotlinCallArgument = receiverExpressionKotlinCallArgumentBuildSubstitutedReceiverArgument;
        if (this.kotlinCallResolver.resolveCall(scopeTower, resolutionCallbacks, CallResolverUtilKt.replaceArguments(psiKotlinCall, listReplaceTypes, receiverExpressionKotlinCallArgument), expectedType, context.collectAllCandidates).size() != 1) {
            return;
        }
        Set mutableSet = CollectionsKt.toMutableSet(reportStubTypeCausesAmbiguityOnArgumentsIfNeeded(argumentsInParenthesis, listReplaceTypes, context, linkedHashMap2));
        ReceiverValue receiverValue = receiverExpressionKotlinCallArgumentBuildSubstitutedReceiverArgument != null ? PSIKotlinCallsKt.getReceiverValue(receiverExpressionKotlinCallArgument) : null;
        if (extensionReceiver != null && receiverValue != null) {
            mutableSet.addAll(reportStubTypeCausesAmbiguityOnReceiverIfNeeded(extensionReceiver, receiverValue, psiKotlinCall, lambda, context, linkedHashMap2));
        }
        if (mutableSet.isEmpty()) {
            return;
        }
        context.trace.report(Errors.OVERLOAD_RESOLUTION_AMBIGUITY_BECAUSE_OF_STUB_TYPES.on(calleeExpression, calleeExpression2.getText(), CollectionsKt.joinToString$default(mutableSet, null, null, null, 0, null, new Function1<NewTypeVariableConstructor, CharSequence>() { // from class: org.jetbrains.kotlin.resolve.checkers.ResolutionWithStubTypesChecker.processResolutionAmbiguityError.1
            @Override // kotlin.jvm.functions.Function1
            public final CharSequence invoke(NewTypeVariableConstructor it2) {
                Intrinsics.checkNotNullParameter(it2, "it");
                return String.valueOf(it2.getOriginalTypeParameter());
            }
        }, 31, null), calleeExpression.getText()));
    }

    private final Set<NewTypeVariableConstructor> reportStubTypeCausesAmbiguityOnReceiverIfNeeded(ReceiverValue receiver, ReceiverValue newReceiver, PSIKotlinCall kotlinCall, LambdaKotlinCallArgument lambda, BasicCallResolutionContext context, Map<TypeConstructor, ? extends UnwrappedType> substitutionMap) {
        KtExpression psiExpression;
        Set setCreateSetBuilder = SetsKt.createSetBuilder();
        KotlinType type = receiver.getType();
        Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
        KotlinType type2 = newReceiver.getType();
        Intrinsics.checkNotNullExpressionValue(type2, "getType(...)");
        KtExpression psiExpression2 = NewCallArgumentsKt.getPsiExpression(lambda);
        KtLambdaExpression ktLambdaExpression = null;
        KtLambdaExpression ktLambdaExpression2 = psiExpression2 instanceof KtLambdaExpression ? (KtLambdaExpression) psiExpression2 : null;
        if (ktLambdaExpression2 != null) {
            LexicalScope lexicalScope = (LexicalScope) context.trace.getContext().get(BindingContext.LEXICAL_SCOPE, kotlinCall.getPsiCall().getCallElement());
            DeclarationDescriptor ownerDescriptor = lexicalScope != null ? lexicalScope.getOwnerDescriptor() : null;
            if (ownerDescriptor != null) {
                ExtensionReceiver extensionReceiver = receiver instanceof ExtensionReceiver ? (ExtensionReceiver) receiver : null;
                if (!Intrinsics.areEqual(ownerDescriptor, extensionReceiver != null ? extensionReceiver.getDeclarationDescriptor() : null)) {
                    ktLambdaExpression = ktLambdaExpression2;
                }
            }
        }
        if (!Intrinsics.areEqual(type, type2)) {
            ArrayList arrayList = new ArrayList(substitutionMap.size());
            Iterator<Map.Entry<TypeConstructor, ? extends UnwrappedType>> it = substitutionMap.entrySet().iterator();
            while (it.hasNext()) {
                TypeConstructor key = it.next().getKey();
                Intrinsics.checkNotNull(key, "null cannot be cast to non-null type org.jetbrains.kotlin.types.checker.NewTypeVariableConstructor");
                arrayList.add((NewTypeVariableConstructor) key);
            }
            ArrayList arrayList2 = arrayList;
            String strJoinToString$default = CollectionsKt.joinToString$default(arrayList2, null, null, null, 0, null, new Function1<NewTypeVariableConstructor, CharSequence>() { // from class: org.jetbrains.kotlin.resolve.checkers.ResolutionWithStubTypesChecker$reportStubTypeCausesAmbiguityOnReceiverIfNeeded$1$typeParameters$1
                @Override // kotlin.jvm.functions.Function1
                public final CharSequence invoke(NewTypeVariableConstructor it2) {
                    Name name2;
                    Intrinsics.checkNotNullParameter(it2, "it");
                    TypeParameterDescriptor originalTypeParameter = it2.getOriginalTypeParameter();
                    if (originalTypeParameter != null && (name2 = originalTypeParameter.getShortName()) != null) {
                        it2 = name2;
                    }
                    return it2.toString();
                }
            }, 31, null);
            Collection<? extends UnwrappedType> collectionValues = substitutionMap.values();
            setCreateSetBuilder.addAll(arrayList2);
            BindingTrace bindingTrace = context.trace;
            DiagnosticFactory4<PsiElement, KotlinType, String, String, BuilderLambdaLabelingInfo> diagnosticFactory4 = Errors.STUB_TYPE_IN_RECEIVER_CAUSES_AMBIGUITY;
            ReceiverKotlinCallArgument explicitReceiver = kotlinCall.getExplicitReceiver();
            bindingTrace.report(diagnosticFactory4.on((explicitReceiver == null || (psiExpression = NewCallArgumentsKt.getPsiExpression(explicitReceiver)) == null) ? kotlinCall.getPsiCall().getCallElement() : psiExpression, type2, strJoinToString$default, CollectionsKt.joinToString$default(collectionValues, null, null, null, 0, null, null, 63, null), ktLambdaExpression != null ? new BuilderLambdaLabelingInfo(ktLambdaExpression) : BuilderLambdaLabelingInfo.INSTANCE.getEMPTY()));
        }
        return SetsKt.build(setCreateSetBuilder);
    }

    private final Set<NewTypeVariableConstructor> reportStubTypeCausesAmbiguityOnArgumentsIfNeeded(List<? extends KotlinCallArgument> valueArguments, List<? extends KotlinCallArgument> newArguments, BasicCallResolutionContext context, Map<TypeConstructor, ? extends UnwrappedType> substitutionMap) {
        KtExpression psiExpression;
        Set setCreateSetBuilder = SetsKt.createSetBuilder();
        int i = 0;
        for (KotlinCallArgument kotlinCallArgument : valueArguments) {
            int i2 = i + 1;
            if (kotlinCallArgument instanceof SimpleKotlinCallArgument) {
                KotlinCallArgument kotlinCallArgument2 = newArguments.get(i);
                SimpleKotlinCallArgument simpleKotlinCallArgument = kotlinCallArgument2 instanceof SimpleKotlinCallArgument ? (SimpleKotlinCallArgument) kotlinCallArgument2 : null;
                if (simpleKotlinCallArgument != null) {
                    UnwrappedType stableType = ArgumentsUtilsKt.getStableType(((SimpleKotlinCallArgument) kotlinCallArgument).getReceiver());
                    UnwrappedType stableType2 = ArgumentsUtilsKt.getStableType(simpleKotlinCallArgument.getReceiver());
                    if (!Intrinsics.areEqual(stableType, stableType2) && (psiExpression = NewCallArgumentsKt.getPsiExpression(kotlinCallArgument)) != null) {
                        ArrayList arrayList = new ArrayList(substitutionMap.size());
                        Iterator<Map.Entry<TypeConstructor, ? extends UnwrappedType>> it = substitutionMap.entrySet().iterator();
                        while (it.hasNext()) {
                            TypeConstructor key = it.next().getKey();
                            Intrinsics.checkNotNull(key, "null cannot be cast to non-null type org.jetbrains.kotlin.types.checker.NewTypeVariableConstructor");
                            arrayList.add((NewTypeVariableConstructor) key);
                        }
                        ArrayList arrayList2 = arrayList;
                        String strJoinToString$default = CollectionsKt.joinToString$default(arrayList2, null, null, null, 0, null, new Function1<NewTypeVariableConstructor, CharSequence>() { // from class: org.jetbrains.kotlin.resolve.checkers.ResolutionWithStubTypesChecker$reportStubTypeCausesAmbiguityOnArgumentsIfNeeded$1$typeParameters$1
                            @Override // kotlin.jvm.functions.Function1
                            public final CharSequence invoke(NewTypeVariableConstructor it2) {
                                Name name2;
                                Intrinsics.checkNotNullParameter(it2, "it");
                                TypeParameterDescriptor originalTypeParameter = it2.getOriginalTypeParameter();
                                if (originalTypeParameter != null && (name2 = originalTypeParameter.getShortName()) != null) {
                                    it2 = name2;
                                }
                                return it2.toString();
                            }
                        }, 31, null);
                        Collection<? extends UnwrappedType> collectionValues = substitutionMap.values();
                        setCreateSetBuilder.addAll(arrayList2);
                        context.trace.report(Errors.STUB_TYPE_IN_ARGUMENT_CAUSES_AMBIGUITY.on(psiExpression, stableType2, strJoinToString$default, CollectionsKt.joinToString$default(collectionValues, null, null, null, 0, null, null, 63, null)));
                    }
                }
                i = i2;
            }
            i = i2;
        }
        return SetsKt.build(setCreateSetBuilder);
    }

    private final ReceiverExpressionKotlinCallArgument buildSubstitutedReceiverArgument(ReceiverValue receiverValue, NewTypeSubstitutor newTypeSubstitutor, BasicCallResolutionContext basicCallResolutionContext) {
        ExtensionReceiver extensionReceiver;
        UnwrappedType unwrappedTypeSafeSubstitute = newTypeSubstitutor.safeSubstitute(receiverValue.getType().unwrap());
        if (receiverValue instanceof ExpressionReceiver) {
            BindingContext context = basicCallResolutionContext.trace.getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getBindingContext(...)");
            extensionReceiver = ExpressionReceiver.INSTANCE.create(((ExpressionReceiver) receiverValue).getExpression(), unwrappedTypeSafeSubstitute, context);
        } else {
            if (!(receiverValue instanceof ExtensionReceiver)) {
                return null;
            }
            ExtensionReceiver extensionReceiver2 = (ExtensionReceiver) receiverValue;
            extensionReceiver = new ExtensionReceiver(extensionReceiver2.getDeclarationDescriptor(), unwrappedTypeSafeSubstitute, extensionReceiver2.getOriginal());
        }
        return ReceiverExpressionKotlinCallArgument.Companion.invoke$default(ReceiverExpressionKotlinCallArgument.INSTANCE, new ReceiverValueWithSmartCastInfo(extensionReceiver, SetsKt.emptySet(), true, null, 8, null), false, false, 6, null);
    }
}
