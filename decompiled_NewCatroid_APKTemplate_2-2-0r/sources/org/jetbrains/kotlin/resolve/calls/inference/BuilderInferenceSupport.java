package org.jetbrains.kotlin.resolve.calls.inference;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.FunctionTypesKt;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.javax.inject.Inject;
import org.jetbrains.kotlin.psi.Call;
import org.jetbrains.kotlin.psi.KtCallableReferenceExpression;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtFunction;
import org.jetbrains.kotlin.psi.ValueArgument;
import org.jetbrains.kotlin.resolve.calls.ArgumentTypeResolver;
import org.jetbrains.kotlin.resolve.calls.CallCompleter;
import org.jetbrains.kotlin.resolve.calls.context.BasicCallResolutionContext;
import org.jetbrains.kotlin.resolve.calls.context.CallCandidateResolutionContext;
import org.jetbrains.kotlin.resolve.calls.context.CallResolutionContext;
import org.jetbrains.kotlin.resolve.calls.context.ContextDependency;
import org.jetbrains.kotlin.resolve.calls.context.TemporaryTraceAndCache;
import org.jetbrains.kotlin.resolve.calls.model.ArgumentMapping;
import org.jetbrains.kotlin.resolve.calls.model.ArgumentMappingKt;
import org.jetbrains.kotlin.resolve.calls.model.ArgumentMatch;
import org.jetbrains.kotlin.resolve.calls.model.MutableDataFlowInfoForArguments;
import org.jetbrains.kotlin.resolve.calls.model.MutableResolvedCall;
import org.jetbrains.kotlin.resolve.calls.results.OverloadResolutionResultsImpl;
import org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy;
import org.jetbrains.kotlin.resolve.calls.util.CallResolverUtilKt;
import org.jetbrains.kotlin.resolve.calls.util.ResolveArgumentsMode;
import org.jetbrains.kotlin.resolve.descriptorUtil.AnnotationsForResolveUtilsKt;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.kotlin.types.DelegatedTypeSubstitution;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeProjection;
import org.jetbrains.kotlin.types.TypeSubstitution;
import org.jetbrains.kotlin.types.TypeUtils;
import org.jetbrains.kotlin.types.UnwrappedType;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.kotlin.types.checker.ClassicTypeCheckerState;
import org.jetbrains.kotlin.types.checker.NewKotlinTypeChecker;
import org.jetbrains.kotlin.types.expressions.ExpressionTypingServices;
import org.jetbrains.kotlin.types.expressions.KotlinTypeInfo;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: BuilderInferenceUtil.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0086\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001:\u00017B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J2\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001d2\n\u0010\u001e\u001a\u0006\u0012\u0002\b\u00030\u001f2\u0006\u0010 \u001a\u00020!J\"\u0010\"\u001a\u00020\u00172\u0006\u0010\u001e\u001a\u00020#2\u0006\u0010$\u001a\u00020%2\n\u0010&\u001a\u0006\u0012\u0002\b\u00030'J\u0010\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020!H\u0002JL\u0010+\u001a\u00020\u00172\n\u0010\u001e\u001a\u0006\u0012\u0002\b\u00030,26\u0010-\u001a2\u0012\u0013\u0012\u00110\u001b¢\u0006\f\b/\u0012\b\b0\u0012\u0004\b\b(1\u0012\u0013\u0012\u00110!¢\u0006\f\b/\u0012\b\b0\u0012\u0004\b\b(2\u0012\u0004\u0012\u00020\u00170.H\u0002J\u001c\u00103\u001a\u0002042\u0006\u00105\u001a\u0002062\n\u0010\u001e\u001a\u0006\u0012\u0002\b\u00030,H\u0002R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR$\u0010\u000b\u001a\u00020\n2\u0006\u0010\t\u001a\u00020\n@GX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0014\u0010\u0012\u001a\u00020\u00138BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015¨\u00068"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/inference/BuilderInferenceSupport;", "", "argumentTypeResolver", "Lorg/jetbrains/kotlin/resolve/calls/ArgumentTypeResolver;", "expressionTypingServices", "Lorg/jetbrains/kotlin/types/expressions/ExpressionTypingServices;", "(Lorg/jetbrains/kotlin/resolve/calls/ArgumentTypeResolver;Lorg/jetbrains/kotlin/types/expressions/ExpressionTypingServices;)V", "getArgumentTypeResolver", "()Lorg/jetbrains/kotlin/resolve/calls/ArgumentTypeResolver;", "<set-?>", "Lorg/jetbrains/kotlin/resolve/calls/CallCompleter;", "callCompleter", "getCallCompleter", "()Lorg/jetbrains/kotlin/resolve/calls/CallCompleter;", "setCallCompleter", "(Lorg/jetbrains/kotlin/resolve/calls/CallCompleter;)V", "getExpressionTypingServices", "()Lorg/jetbrains/kotlin/types/expressions/ExpressionTypingServices;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "getLanguageVersionSettings", "()Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "analyzeBuilderInferenceCall", "", "functionLiteral", "Lorg/jetbrains/kotlin/psi/KtFunction;", "valueArgument", "Lorg/jetbrains/kotlin/psi/ValueArgument;", "csBuilder", "Lorg/jetbrains/kotlin/resolve/calls/inference/ConstraintSystem$Builder;", "context", "Lorg/jetbrains/kotlin/resolve/calls/context/CallCandidateResolutionContext;", "lambdaExpectedType", "Lorg/jetbrains/kotlin/types/KotlinType;", "checkBuilderInferenceCalls", "Lorg/jetbrains/kotlin/resolve/calls/context/BasicCallResolutionContext;", "tracingStrategy", "Lorg/jetbrains/kotlin/resolve/calls/tasks/TracingStrategy;", "overloadResults", "Lorg/jetbrains/kotlin/resolve/calls/results/OverloadResolutionResultsImpl;", "checkExpectedTypeForArgument", "", "expectedType", "forceInferenceForArguments", "Lorg/jetbrains/kotlin/resolve/calls/context/CallResolutionContext;", "callback", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "argument", "argumentType", "getArgumentTypeInfo", "Lorg/jetbrains/kotlin/types/expressions/KotlinTypeInfo;", "expression", "Lorg/jetbrains/kotlin/psi/KtExpression;", "BuilderInferenceTypeCheckerState", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class BuilderInferenceSupport {
    private final ArgumentTypeResolver argumentTypeResolver;
    public CallCompleter callCompleter;
    private final ExpressionTypingServices expressionTypingServices;

    public BuilderInferenceSupport(ArgumentTypeResolver argumentTypeResolver, ExpressionTypingServices expressionTypingServices) {
        Intrinsics.checkNotNullParameter(argumentTypeResolver, "argumentTypeResolver");
        Intrinsics.checkNotNullParameter(expressionTypingServices, "expressionTypingServices");
        this.argumentTypeResolver = argumentTypeResolver;
        this.expressionTypingServices = expressionTypingServices;
    }

    public final ArgumentTypeResolver getArgumentTypeResolver() {
        return this.argumentTypeResolver;
    }

    public final ExpressionTypingServices getExpressionTypingServices() {
        return this.expressionTypingServices;
    }

    public final CallCompleter getCallCompleter() {
        CallCompleter callCompleter = this.callCompleter;
        if (callCompleter != null) {
            return callCompleter;
        }
        Intrinsics.throwUninitializedPropertyAccessException("callCompleter");
        return null;
    }

    @Inject
    public final void setCallCompleter(CallCompleter callCompleter) {
        Intrinsics.checkNotNullParameter(callCompleter, "<set-?>");
        this.callCompleter = callCompleter;
    }

    private final LanguageVersionSettings getLanguageVersionSettings() {
        LanguageVersionSettings languageVersionSettings = this.expressionTypingServices.getLanguageVersionSettings();
        Intrinsics.checkNotNullExpressionValue(languageVersionSettings, "getLanguageVersionSettings(...)");
        return languageVersionSettings;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void analyzeBuilderInferenceCall(KtFunction functionLiteral, ValueArgument valueArgument, ConstraintSystem.Builder csBuilder, CallCandidateResolutionContext<?> context, KotlinType lambdaExpectedType) {
        KotlinType receiverTypeFromFunctionType;
        Intrinsics.checkNotNullParameter(functionLiteral, "functionLiteral");
        Intrinsics.checkNotNullParameter(valueArgument, "valueArgument");
        Intrinsics.checkNotNullParameter(csBuilder, "csBuilder");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(lambdaExpectedType, "lambdaExpectedType");
        KtExpression argumentExpression = valueArgument.getArgumentExpression();
        if (argumentExpression == null || !checkExpectedTypeForArgument(lambdaExpectedType) || (receiverTypeFromFunctionType = FunctionTypesKt.getReceiverTypeFromFunctionType(lambdaExpectedType)) == null) {
            return;
        }
        final BuilderInferenceData builderInferenceData = new BuilderInferenceData();
        final ConstraintSystem constraintSystemBuild = csBuilder.build();
        final TypeSubstitution substitution = constraintSystemBuild.getCurrentSubstitutor().getSubstitution();
        KotlinType kotlinTypeSubstitute = new DelegatedTypeSubstitution(builderInferenceData, substitution) { // from class: org.jetbrains.kotlin.resolve.calls.inference.BuilderInferenceSupport$analyzeBuilderInferenceCall$newSubstitution$1
            final /* synthetic */ BuilderInferenceData $inferenceData;

            @Override // org.jetbrains.kotlin.types.DelegatedTypeSubstitution, org.jetbrains.kotlin.types.TypeSubstitution
            /* JADX INFO: renamed from: approximateContravariantCapturedTypes */
            public boolean get$needApproximation() {
                return true;
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(substitution);
                Intrinsics.checkNotNull(substitution);
            }

            @Override // org.jetbrains.kotlin.types.DelegatedTypeSubstitution, org.jetbrains.kotlin.types.TypeSubstitution
            /* JADX INFO: renamed from: get */
            public TypeProjection mo7475get(KotlinType key) {
                Intrinsics.checkNotNullParameter(key, "key");
                TypeProjection typeProjectionMo7475get = super.mo7475get(key);
                Object obj = null;
                if (!Intrinsics.areEqual(typeProjectionMo7475get != null ? typeProjectionMo7475get.getNullableAnyType() : null, TypeUtils.DONT_CARE)) {
                    return typeProjectionMo7475get;
                }
                for (Object obj2 : this.$constraintSystem.getTypeVariables()) {
                    if (Intrinsics.areEqual(((TypeVariable) obj2).getOriginalTypeParameter().getDefaultType(), key)) {
                        obj = obj2;
                        break;
                    }
                }
                TypeVariable typeVariable = (TypeVariable) obj;
                return typeVariable == null ? typeProjectionMo7475get : TypeUtilsKt.asTypeProjection(this.$inferenceData.getTypeTemplate(typeVariable));
            }
        }.buildSubstitutor().substitute(receiverTypeFromFunctionType, Variance.INVARIANT);
        if (kotlinTypeSubstitute == null) {
            return;
        }
        final TypeSubstitution substitution2 = constraintSystemBuild.getCurrentSubstitutor().getSubstitution();
        KotlinType kotlinTypeSubstitute2 = new DelegatedTypeSubstitution(substitution2) { // from class: org.jetbrains.kotlin.resolve.calls.inference.BuilderInferenceSupport$analyzeBuilderInferenceCall$approximationSubstitutor$1
            @Override // org.jetbrains.kotlin.types.DelegatedTypeSubstitution, org.jetbrains.kotlin.types.TypeSubstitution
            /* JADX INFO: renamed from: approximateContravariantCapturedTypes */
            public boolean get$needApproximation() {
                return true;
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(substitution2);
                Intrinsics.checkNotNull(substitution2);
            }
        }.buildSubstitutor().substitute(lambdaExpectedType, Variance.IN_VARIANCE);
        if (kotlinTypeSubstitute2 == null) {
            return;
        }
        KotlinBuiltIns builtIns = TypeUtilsKt.getBuiltIns(kotlinTypeSubstitute);
        Annotations annotations = kotlinTypeSubstitute2.getAnnotations();
        List listEmptyList = CollectionsKt.emptyList();
        List<TypeProjection> valueParameterTypesFromFunctionType = FunctionTypesKt.getValueParameterTypesFromFunctionType(kotlinTypeSubstitute2);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(valueParameterTypesFromFunctionType, 10));
        Iterator<T> it = valueParameterTypesFromFunctionType.iterator();
        while (it.hasNext()) {
            arrayList.add(((TypeProjection) it.next()).getNullableAnyType());
        }
        SimpleType simpleTypeCreateFunctionType = FunctionTypesKt.createFunctionType(builtIns, annotations, kotlinTypeSubstitute, listEmptyList, arrayList, null, FunctionTypesKt.getReturnTypeFromFunctionType(kotlinTypeSubstitute2), true);
        if (CallResolverUtilKt.hasUnknownFunctionParameter(simpleTypeCreateFunctionType)) {
            return;
        }
        builderInferenceData.initSystem();
        Context contextReplaceTraceAndCache = ((CallCandidateResolutionContext) ((CallCandidateResolutionContext) ((CallCandidateResolutionContext) context.replaceExpectedType(simpleTypeCreateFunctionType)).replaceDataFlowInfo(context.candidateCall.getDataFlowInfoForArguments().getInfo(valueArgument))).replaceContextDependency(ContextDependency.INDEPENDENT)).replaceTraceAndCache(TemporaryTraceAndCache.create(context, "trace to infer a type argument using the builder inference", functionLiteral));
        Intrinsics.checkNotNullExpressionValue(contextReplaceTraceAndCache, "replaceTraceAndCache(...)");
        this.argumentTypeResolver.getFunctionLiteralTypeInfo(argumentExpression, functionLiteral, (CallCandidateResolutionContext) contextReplaceTraceAndCache, ResolveArgumentsMode.RESOLVE_FUNCTION_ARGUMENTS, true);
        builderInferenceData.reportInferenceResult(csBuilder);
    }

    private final boolean checkExpectedTypeForArgument(KotlinType expectedType) {
        if (getLanguageVersionSettings().supportsFeature(LanguageFeature.ExperimentalBuilderInference)) {
            return FunctionTypesKt.isFunctionOrSuspendFunctionType(expectedType);
        }
        return FunctionTypesKt.isSuspendFunctionType(expectedType);
    }

    public final void checkBuilderInferenceCalls(final BasicCallResolutionContext context, TracingStrategy tracingStrategy, OverloadResolutionResultsImpl<?> overloadResults) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(tracingStrategy, "tracingStrategy");
        Intrinsics.checkNotNullParameter(overloadResults, "overloadResults");
        BuilderInferenceData builderInferenceData = BuilderInferenceUtilKt.getBuilderInferenceData(overloadResults);
        if (builderInferenceData == null) {
            return;
        }
        final MutableResolvedCall<D> mutableResolvedCallMo7436getResultingCall = overloadResults.mo7436getResultingCall();
        Intrinsics.checkNotNullExpressionValue(mutableResolvedCallMo7436getResultingCall, "getResultingCall(...)");
        BasicCallResolutionContext basicCallResolutionContext = context;
        forceInferenceForArguments(basicCallResolutionContext, new Function2<ValueArgument, KotlinType, Unit>() { // from class: org.jetbrains.kotlin.resolve.calls.inference.BuilderInferenceSupport.checkBuilderInferenceCalls.1
            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(ValueArgument valueArgument, KotlinType kotlinType) {
                Intrinsics.checkNotNullParameter(valueArgument, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(kotlinType, "<anonymous parameter 1>");
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Unit invoke(ValueArgument valueArgument, KotlinType kotlinType) {
                invoke2(valueArgument, kotlinType);
                return Unit.INSTANCE;
            }
        });
        getCallCompleter().completeCall(context, overloadResults, tracingStrategy);
        if (ArgumentMappingKt.isReallySuccess(mutableResolvedCallMo7436getResultingCall)) {
            CallableDescriptor resultingDescriptor = mutableResolvedCallMo7436getResultingCall.getResultingDescriptor();
            Intrinsics.checkNotNull(resultingDescriptor);
            if (!BuilderInferenceUtilKt.isApplicableCallForBuilderInference(resultingDescriptor, getLanguageVersionSettings())) {
                builderInferenceData.badCallHappened();
            }
            forceInferenceForArguments(basicCallResolutionContext, new Function2<ValueArgument, KotlinType, Unit>() { // from class: org.jetbrains.kotlin.resolve.calls.inference.BuilderInferenceSupport.checkBuilderInferenceCalls.2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public /* bridge */ /* synthetic */ Unit invoke(ValueArgument valueArgument, KotlinType kotlinType) {
                    invoke2(valueArgument, kotlinType);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(ValueArgument valueArgument, KotlinType kotlinType) {
                    Intrinsics.checkNotNullParameter(valueArgument, "valueArgument");
                    Intrinsics.checkNotNullParameter(kotlinType, "kotlinType");
                    ArgumentMapping argumentMapping = mutableResolvedCallMo7436getResultingCall.getArgumentMapping(valueArgument);
                    ArgumentMatch argumentMatch = argumentMapping instanceof ArgumentMatch ? (ArgumentMatch) argumentMapping : null;
                    if (argumentMatch == null) {
                        return;
                    }
                    NewKotlinTypeChecker.INSTANCE.getDefault().isSubtypeOf(new BuilderInferenceTypeCheckerState(false), kotlinType.unwrap(), CallResolverUtilKt.getEffectiveExpectedType(argumentMatch.getValueParameter(), valueArgument, context).unwrap());
                }
            });
            ReceiverParameterDescriptor extensionReceiverParameter = resultingDescriptor.getExtensionReceiverParameter();
            if (extensionReceiverParameter == null) {
                return;
            }
            boolean z = false;
            if (getLanguageVersionSettings().supportsFeature(LanguageFeature.ExperimentalBuilderInference) && !AnnotationsForResolveUtilsKt.hasBuilderInferenceAnnotation(resultingDescriptor)) {
                z = true;
            }
            ReceiverValue extensionReceiver = mutableResolvedCallMo7436getResultingCall.getExtensionReceiver();
            if (extensionReceiver != null) {
                NewKotlinTypeChecker.INSTANCE.getDefault().isSubtypeOf(new BuilderInferenceTypeCheckerState(z), extensionReceiver.getType().unwrap(), extensionReceiverParameter.getValue().getType().unwrap());
            }
        }
    }

    /* JADX INFO: compiled from: BuilderInferenceUtil.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J'\u0010\u0005\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\u0003H\u0016¢\u0006\u0002\u0010\nR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/inference/BuilderInferenceSupport$BuilderInferenceTypeCheckerState;", "Lorg/jetbrains/kotlin/types/checker/ClassicTypeCheckerState;", "allowOnlyTrivialConstraints", "", "(Z)V", "addSubtypeConstraint", "subType", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "superType", "isFromNullabilityConstraint", "(Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;Z)Ljava/lang/Boolean;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class BuilderInferenceTypeCheckerState extends ClassicTypeCheckerState {
        private final boolean allowOnlyTrivialConstraints;

        public BuilderInferenceTypeCheckerState(boolean z) {
            super(true, false, null, null, null, 30, null);
            this.allowOnlyTrivialConstraints = z;
        }

        @Override // org.jetbrains.kotlin.types.TypeCheckerState
        public Boolean addSubtypeConstraint(KotlinTypeMarker subType, KotlinTypeMarker superType, boolean isFromNullabilityConstraint) {
            BuilderInferenceData builderInferenceData;
            Intrinsics.checkNotNullParameter(subType, "subType");
            Intrinsics.checkNotNullParameter(superType, "superType");
            if (!(subType instanceof UnwrappedType)) {
                throw new IllegalArgumentException("Failed requirement.".toString());
            }
            if (!(superType instanceof UnwrappedType)) {
                throw new IllegalArgumentException("Failed requirement.".toString());
            }
            TypeTemplate typeTemplate = subType instanceof TypeTemplate ? (TypeTemplate) subType : null;
            if (typeTemplate == null) {
                typeTemplate = superType instanceof TypeTemplate ? (TypeTemplate) superType : null;
            }
            if (typeTemplate != null && (builderInferenceData = typeTemplate.getBuilderInferenceData()) != null) {
                builderInferenceData.addConstraint$frontend((KotlinType) subType, (KotlinType) superType, this.allowOnlyTrivialConstraints);
            }
            return null;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final void forceInferenceForArguments(CallResolutionContext<?> context, Function2<? super ValueArgument, ? super KotlinType, Unit> callback) {
        MutableDataFlowInfoForArguments dataFlowInfoForArguments = context.dataFlowInfoForArguments;
        Intrinsics.checkNotNullExpressionValue(dataFlowInfoForArguments, "dataFlowInfoForArguments");
        Call call = context.call;
        Intrinsics.checkNotNullExpressionValue(call, "call");
        CallResolutionContext callResolutionContext = (CallResolutionContext) ((CallResolutionContext) context.replaceContextDependency(ContextDependency.INDEPENDENT)).replaceExpectedType(TypeUtils.NO_EXPECTED_TYPE);
        for (ValueArgument valueArgument : call.getValueArguments()) {
            KtExpression argumentExpression = valueArgument.getArgumentExpression();
            if (argumentExpression != null) {
                Context contextReplaceDataFlowInfo = callResolutionContext.replaceDataFlowInfo(dataFlowInfoForArguments.getInfo(valueArgument));
                Intrinsics.checkNotNullExpressionValue(contextReplaceDataFlowInfo, "replaceDataFlowInfo(...)");
                KotlinType type = getArgumentTypeInfo(argumentExpression, (CallResolutionContext) contextReplaceDataFlowInfo).getType();
                if (type != null) {
                    Intrinsics.checkNotNull(valueArgument);
                    callback.invoke(valueArgument, type);
                }
            }
        }
    }

    private final KotlinTypeInfo getArgumentTypeInfo(KtExpression expression, CallResolutionContext<?> context) {
        CallResolutionContext<?> callResolutionContext = context;
        KtFunction functionLiteralArgumentIfAny = ArgumentTypeResolver.getFunctionLiteralArgumentIfAny(expression, callResolutionContext);
        if (functionLiteralArgumentIfAny != null) {
            KotlinTypeInfo functionLiteralTypeInfo = this.argumentTypeResolver.getFunctionLiteralTypeInfo(expression, functionLiteralArgumentIfAny, context, ResolveArgumentsMode.RESOLVE_FUNCTION_ARGUMENTS, false);
            Intrinsics.checkNotNullExpressionValue(functionLiteralTypeInfo, "getFunctionLiteralTypeInfo(...)");
            return functionLiteralTypeInfo;
        }
        KtCallableReferenceExpression callableReferenceExpressionIfAny = ArgumentTypeResolver.getCallableReferenceExpressionIfAny(expression, callResolutionContext);
        if (callableReferenceExpressionIfAny != null) {
            KotlinTypeInfo callableReferenceTypeInfo = this.argumentTypeResolver.getCallableReferenceTypeInfo(expression, callableReferenceExpressionIfAny, context, ResolveArgumentsMode.RESOLVE_FUNCTION_ARGUMENTS);
            Intrinsics.checkNotNullExpressionValue(callableReferenceTypeInfo, "getCallableReferenceTypeInfo(...)");
            return callableReferenceTypeInfo;
        }
        KotlinTypeInfo typeInfo = this.expressionTypingServices.getTypeInfo(expression, callResolutionContext);
        Intrinsics.checkNotNullExpressionValue(typeInfo, "getTypeInfo(...)");
        return typeInfo;
    }
}
