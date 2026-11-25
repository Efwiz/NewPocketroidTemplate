package org.jetbrains.kotlin.resolve.calls;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.builtins.FunctionTypesKt;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.impl.TypeAliasConstructorDescriptor;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.psi.Call;
import org.jetbrains.kotlin.psi.KtBinaryExpressionWithTypeRHS;
import org.jetbrains.kotlin.psi.KtCallableReferenceExpression;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtFunction;
import org.jetbrains.kotlin.psi.KtPsiUtil;
import org.jetbrains.kotlin.psi.KtTypeReference;
import org.jetbrains.kotlin.psi.ValueArgument;
import org.jetbrains.kotlin.psi.psiUtil.KtPsiUtilKt;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.FunctionDescriptorUtil;
import org.jetbrains.kotlin.resolve.KtDescriptorUtilKt;
import org.jetbrains.kotlin.resolve.TemporaryBindingTrace;
import org.jetbrains.kotlin.resolve.calls.context.CallCandidateResolutionContext;
import org.jetbrains.kotlin.resolve.calls.context.ContextDependency;
import org.jetbrains.kotlin.resolve.calls.context.ResolutionContext;
import org.jetbrains.kotlin.resolve.calls.context.ResolutionResultsCache;
import org.jetbrains.kotlin.resolve.calls.context.TemporaryTraceAndCache;
import org.jetbrains.kotlin.resolve.calls.inference.BuilderInferenceSupport;
import org.jetbrains.kotlin.resolve.calls.inference.BuilderInferenceUtilKt;
import org.jetbrains.kotlin.resolve.calls.inference.CallHandle;
import org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystem;
import org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystemBuilderImpl;
import org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystemUtilsKt;
import org.jetbrains.kotlin.resolve.calls.inference.TypeVariable;
import org.jetbrains.kotlin.resolve.calls.inference.TypeVariableKt;
import org.jetbrains.kotlin.resolve.calls.inference.constraintPosition.ConstraintPosition;
import org.jetbrains.kotlin.resolve.calls.inference.constraintPosition.ConstraintPositionKind;
import org.jetbrains.kotlin.resolve.calls.inference.constraintPosition.ValidityConstraintForConstituentType;
import org.jetbrains.kotlin.resolve.calls.model.MutableDataFlowInfoForArguments;
import org.jetbrains.kotlin.resolve.calls.model.MutableResolvedCall;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedValueArgument;
import org.jetbrains.kotlin.resolve.calls.model.VariableAsFunctionResolvedCall;
import org.jetbrains.kotlin.resolve.calls.results.OverloadResolutionResultsImpl;
import org.jetbrains.kotlin.resolve.calls.results.ResolutionStatus;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowInfo;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowValue;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowValueFactory;
import org.jetbrains.kotlin.resolve.calls.util.CallResolverUtilKt;
import org.jetbrains.kotlin.resolve.calls.util.CallUtilKt;
import org.jetbrains.kotlin.resolve.calls.util.ResolveArgumentsMode;
import org.jetbrains.kotlin.resolve.calls.util.ResolvedCallUtilKt;
import org.jetbrains.kotlin.resolve.scopes.receivers.ExpressionReceiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.kotlin.types.DelegatedTypeSubstitution;
import org.jetbrains.kotlin.types.FlexibleTypesKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeConstructor;
import org.jetbrains.kotlin.types.TypeIntersector;
import org.jetbrains.kotlin.types.TypeProjection;
import org.jetbrains.kotlin.types.TypeSubstitution;
import org.jetbrains.kotlin.types.TypeSubstitutor;
import org.jetbrains.kotlin.types.TypeUtils;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.kotlin.types.expressions.ExpressionTypingUtils;
import org.jetbrains.kotlin.types.expressions.KotlinTypeInfo;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;
import org.jetbrains.kotlin.util.slicedMap.WritableSlice;

/* JADX INFO: compiled from: GenericCandidateResolver.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000 \u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ@\u0010\u000b\u001a\u00020\f\"\b\b\u0000\u0010\r*\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u0002H\r0\u0018H\u0002JJ\u0010\u0019\u001a\u00020\f\"\b\b\u0000\u0010\r*\u00020\u000e2\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u0002H\r0\u00182\b\u0010\u001c\u001a\u0004\u0018\u00010\u001dH\u0002J6\u0010\u001e\u001a\u00020\u001f2\b\u0010 \u001a\u0004\u0018\u00010!2\u0006\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020\u00162\n\u0010\u0017\u001a\u0006\u0012\u0002\b\u00030\u00182\u0006\u0010%\u001a\u00020\u001dH\u0002J<\u0010&\u001a\u00020\f2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010'\u001a\u00020(2\u0006\u0010$\u001a\u00020\u00162\n\u0010\u0017\u001a\u0006\u0012\u0002\b\u00030\u00182\u0006\u0010)\u001a\u00020*H\u0002J\u001c\u0010+\u001a\u00020\f2\n\u0010\u0017\u001a\u0006\u0012\u0002\b\u00030\u00182\u0006\u0010$\u001a\u00020\u0016H\u0002J\u0018\u0010,\u001a\u00020\f2\u0006\u0010$\u001a\u00020\u00162\u0006\u0010-\u001a\u00020\u001dH\u0002J(\u0010.\u001a\u00020\f2\u0006\u0010$\u001a\u00020\u00162\u0006\u0010/\u001a\u0002002\u0006\u00101\u001a\u0002022\u0006\u00103\u001a\u00020(H\u0002J\u001e\u00104\u001a\u00020\f\"\b\b\u0000\u0010\r*\u00020\u000e2\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u0002H\r0\u0018J.\u00105\u001a\n 6*\u0004\u0018\u00010\u001d0\u001d2\n\u0010\u0017\u001a\u0006\u0012\u0002\b\u00030\u00182\u0006\u00107\u001a\u00020\u001d2\b\u00108\u001a\u0004\u0018\u00010\u001dH\u0002J:\u00109\u001a\u0004\u0018\u00010\u001d\"\b\b\u0000\u0010\r*\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0015\u001a\u00020\u00162\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u0002H\r0\u00182\u0006\u0010%\u001a\u00020\u001dH\u0002J:\u0010:\u001a\u0004\u0018\u00010\u001d\"\b\b\u0000\u0010\r*\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u0002H\r0\u00182\u0006\u0010;\u001a\u00020\u001d2\u0006\u0010\u0011\u001a\u00020\u0012H\u0002J\u001e\u0010<\u001a\u00020=\"\b\b\u0000\u0010\r*\u00020\u000e2\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u0002H\r0\u0018J*\u0010>\u001a\u0004\u0018\u00010\u001d2\b\u0010-\u001a\u0004\u0018\u00010\u001d2\b\u0010 \u001a\u0004\u0018\u00010!2\n\u0010\u0017\u001a\u0006\u0012\u0002\b\u00030?H\u0002J\f\u0010@\u001a\u00020(*\u00020\u0016H\u0002J \u0010A\u001a\u0004\u0018\u00010\u001d*\u00020\u00162\u0006\u0010B\u001a\u00020C2\b\u0010-\u001a\u0004\u0018\u00010\u001dH\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006D"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/GenericCandidateResolver;", "", "argumentTypeResolver", "Lorg/jetbrains/kotlin/resolve/calls/ArgumentTypeResolver;", "builderInferenceSupport", "Lorg/jetbrains/kotlin/resolve/calls/inference/BuilderInferenceSupport;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "dataFlowValueFactory", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowValueFactory;", "(Lorg/jetbrains/kotlin/resolve/calls/ArgumentTypeResolver;Lorg/jetbrains/kotlin/resolve/calls/inference/BuilderInferenceSupport;Lorg/jetbrains/kotlin/config/LanguageVersionSettings;Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowValueFactory;)V", "addConstraintForCallableReference", "", "D", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "callableReference", "Lorg/jetbrains/kotlin/psi/KtCallableReferenceExpression;", "valueArgument", "Lorg/jetbrains/kotlin/psi/ValueArgument;", "valueParameterDescriptor", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "constraintSystem", "Lorg/jetbrains/kotlin/resolve/calls/inference/ConstraintSystem$Builder;", "context", "Lorg/jetbrains/kotlin/resolve/calls/context/CallCandidateResolutionContext;", "addConstraintForFunctionLiteralArgument", "functionLiteral", "Lorg/jetbrains/kotlin/psi/KtFunction;", "argumentOwnerReturnType", "Lorg/jetbrains/kotlin/types/KotlinType;", "addConstraintForNestedCall", "", "argumentExpression", "Lorg/jetbrains/kotlin/psi/KtExpression;", "constraintPosition", "Lorg/jetbrains/kotlin/resolve/calls/inference/constraintPosition/ConstraintPosition;", "builder", "effectiveExpectedType", "addConstraintForValueArgument", "substitutor", "Lorg/jetbrains/kotlin/types/TypeSubstitutor;", "resolveFunctionArgumentBodies", "Lorg/jetbrains/kotlin/resolve/calls/util/ResolveArgumentsMode;", "addExpectedTypeForExplicitCast", "addValidityConstraintsForConstituentTypes", ModuleXmlParser.TYPE, "addValidityConstraintsForTypeArgument", "substitutedArgument", "Lorg/jetbrains/kotlin/types/TypeProjection;", "typeParameter", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "boundsSubstitutor", "completeTypeInferenceDependentOnFunctionArgumentsForCall", "estimateLiteralReturnType", JvmProtoBufUtil.PLATFORM_TYPE_ID, "literalExpectedType", "ownerReturnType", "getExpectedTypeForCallableReference", "getResolvedTypeForCallableReference", "expectedType", "inferTypeArguments", "Lorg/jetbrains/kotlin/resolve/calls/results/ResolutionStatus;", "updateResultTypeForSmartCasts", "Lorg/jetbrains/kotlin/resolve/calls/context/ResolutionContext;", "compositeSubstitutor", "typeInSystem", "call", "Lorg/jetbrains/kotlin/psi/Call;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class GenericCandidateResolver {
    private final ArgumentTypeResolver argumentTypeResolver;
    private final BuilderInferenceSupport builderInferenceSupport;
    private final DataFlowValueFactory dataFlowValueFactory;
    private final LanguageVersionSettings languageVersionSettings;

    public GenericCandidateResolver(ArgumentTypeResolver argumentTypeResolver, BuilderInferenceSupport builderInferenceSupport, LanguageVersionSettings languageVersionSettings, DataFlowValueFactory dataFlowValueFactory) {
        Intrinsics.checkNotNullParameter(argumentTypeResolver, "argumentTypeResolver");
        Intrinsics.checkNotNullParameter(builderInferenceSupport, "builderInferenceSupport");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(dataFlowValueFactory, "dataFlowValueFactory");
        this.argumentTypeResolver = argumentTypeResolver;
        this.builderInferenceSupport = builderInferenceSupport;
        this.languageVersionSettings = languageVersionSettings;
        this.dataFlowValueFactory = dataFlowValueFactory;
    }

    public final <D extends CallableDescriptor> ResolutionStatus inferTypeArguments(CallCandidateResolutionContext<D> context) {
        Intrinsics.checkNotNullParameter(context, "context");
        MutableResolvedCall<D> candidateCall = context.candidateCall;
        Intrinsics.checkNotNullExpressionValue(candidateCall, "candidateCall");
        D candidateDescriptor = candidateCall.get$suspendPropertyDescriptor();
        Intrinsics.checkNotNullExpressionValue(candidateDescriptor, "getCandidateDescriptor(...)");
        ConstraintSystemBuilderImpl constraintSystemBuilderImpl = new ConstraintSystemBuilderImpl(null, 1, null);
        ConstraintSystemBuilderImpl constraintSystemBuilderImpl2 = constraintSystemBuilderImpl;
        Call call = candidateCall.get$fakeCall();
        String str = "getCall(...)";
        Intrinsics.checkNotNullExpressionValue(call, "getCall(...)");
        CallHandle handle = TypeVariableKt.toHandle(call);
        List<TypeParameterDescriptor> typeParameters = candidateDescriptor.getTypeParameters();
        Intrinsics.checkNotNullExpressionValue(typeParameters, "getTypeParameters(...)");
        ConstraintSystem.Builder.registerTypeVariables$default(constraintSystemBuilderImpl2, handle, typeParameters, false, 4, null);
        List<TypeParameterDescriptor> typeParameters2 = candidateDescriptor.getTypeParameters();
        Intrinsics.checkNotNullExpressionValue(typeParameters2, "getTypeParameters(...)");
        SimpleType DONT_CARE = TypeUtils.DONT_CARE;
        Intrinsics.checkNotNullExpressionValue(DONT_CARE, "DONT_CARE");
        TypeSubstitutor typeSubstitutorMakeConstantSubstitutor = GenericCandidateResolverKt.makeConstantSubstitutor(typeParameters2, DONT_CARE);
        Map<ValueParameterDescriptor, ResolvedValueArgument> valueArguments = candidateCall.getValueArguments();
        Intrinsics.checkNotNullExpressionValue(valueArguments, "getValueArguments(...)");
        Iterator<Map.Entry<ValueParameterDescriptor, ResolvedValueArgument>> it = valueArguments.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<ValueParameterDescriptor, ResolvedValueArgument> next = it.next();
            ValueParameterDescriptor key = next.getKey();
            ResolvedValueArgument value = next.getValue();
            ValueParameterDescriptor valueParameterDescriptor = candidateDescriptor.getValueParameters().get(key.getIndex());
            for (ValueArgument valueArgument : value.getArguments()) {
                Intrinsics.checkNotNull(valueArgument);
                Intrinsics.checkNotNull(valueParameterDescriptor);
                addConstraintForValueArgument(valueArgument, valueParameterDescriptor, typeSubstitutorMakeConstantSubstitutor, constraintSystemBuilderImpl2, context, ResolveArgumentsMode.SHAPE_FUNCTION_ARGUMENTS);
                str = str;
                constraintSystemBuilderImpl2 = constraintSystemBuilderImpl2;
                typeSubstitutorMakeConstantSubstitutor = typeSubstitutorMakeConstantSubstitutor;
                it = it;
            }
        }
        String str2 = str;
        ConstraintSystemBuilderImpl constraintSystemBuilderImpl3 = constraintSystemBuilderImpl2;
        if (candidateDescriptor instanceof TypeAliasConstructorDescriptor) {
            KotlinType kotlinTypeSafeSubstitute = compositeSubstitutor(constraintSystemBuilderImpl3).safeSubstitute(((TypeAliasConstructorDescriptor) candidateDescriptor).getReturnType(), Variance.INVARIANT);
            Intrinsics.checkNotNullExpressionValue(kotlinTypeSafeSubstitute, "safeSubstitute(...)");
            addValidityConstraintsForConstituentTypes(constraintSystemBuilderImpl3, kotlinTypeSafeSubstitute);
        }
        ReceiverValue extensionReceiver = candidateCall.getExtensionReceiver();
        ReceiverParameterDescriptor extensionReceiverParameter = candidateDescriptor.getExtensionReceiverParameter();
        if (extensionReceiver != null && extensionReceiverParameter != null) {
            KotlinType type = extensionReceiver.getType();
            Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
            Call call2 = context.candidateCall.get$fakeCall();
            Intrinsics.checkNotNullExpressionValue(call2, str2);
            if (CallUtilKt.isSafeCall(call2)) {
                type = TypeUtils.makeNotNullable(type);
            }
            if (extensionReceiver instanceof ExpressionReceiver) {
                type = updateResultTypeForSmartCasts(type, ((ExpressionReceiver) extensionReceiver).getExpression(), context);
            }
            constraintSystemBuilderImpl.addSubtypeConstraint(type, compositeSubstitutor(constraintSystemBuilderImpl3).substitute(extensionReceiverParameter.getType(), Variance.INVARIANT), ConstraintPositionKind.RECEIVER_POSITION.position());
        }
        ConstraintSystem constraintSystemBuild = constraintSystemBuilderImpl.build();
        candidateCall.setConstraintSystem(constraintSystemBuild);
        if (!constraintSystemBuild.getStatus().hasContradiction()) {
            addExpectedTypeForExplicitCast(context, constraintSystemBuilderImpl3);
            return ResolutionStatus.INCOMPLETE_TYPE_INFERENCE;
        }
        return ResolutionStatus.OTHER_ERROR;
    }

    private final KotlinType typeInSystem(ConstraintSystem.Builder builder, Call call, KotlinType kotlinType) {
        TypeSubstitutor typeSubstitutor;
        if (kotlinType == null || (typeSubstitutor = builder.getTypeVariableSubstitutors().get(TypeVariableKt.toHandle(call))) == null) {
            return null;
        }
        return typeSubstitutor.substitute(kotlinType, Variance.INVARIANT);
    }

    private final void addExpectedTypeForExplicitCast(CallCandidateResolutionContext<?> context, ConstraintSystem.Builder builder) {
        KtExpression calleeExpression;
        KtBinaryExpressionWithTypeRHS binaryWithTypeParent;
        KotlinType kotlinTypeMakeNullable;
        KotlinType kotlinTypeTypeInSystem;
        if (this.languageVersionSettings.supportsFeature(LanguageFeature.ExpectedTypeFromCast) && !(context.candidateCall instanceof VariableAsFunctionResolvedCall)) {
            CallableDescriptor candidateDescriptor = context.candidateCall.get$suspendPropertyDescriptor();
            FunctionDescriptor functionDescriptor = candidateDescriptor instanceof FunctionDescriptor ? (FunctionDescriptor) candidateDescriptor : null;
            if (functionDescriptor == null || (calleeExpression = context.call.getCalleeExpression()) == null || (binaryWithTypeParent = KtPsiUtilKt.getBinaryWithTypeParent(calleeExpression)) == null) {
                return;
            }
            IElementType referencedNameElementType = binaryWithTypeParent.getOperationReference().getReferencedNameElementType();
            IElementType iElementType = (Intrinsics.areEqual(referencedNameElementType, KtTokens.AS_KEYWORD) || Intrinsics.areEqual(referencedNameElementType, KtTokens.AS_SAFE)) ? referencedNameElementType : null;
            if (iElementType == null) {
                return;
            }
            BindingTrace bindingTrace = context.trace;
            WritableSlice<KtTypeReference, KotlinType> writableSlice = BindingContext.TYPE;
            KtTypeReference right = binaryWithTypeParent.getRight();
            if (right == null || (kotlinTypeMakeNullable = (KotlinType) bindingTrace.get(writableSlice, right)) == null) {
                return;
            }
            if (Intrinsics.areEqual(iElementType, KtTokens.AS_SAFE)) {
                kotlinTypeMakeNullable = TypeUtilsKt.makeNullable(kotlinTypeMakeNullable);
            }
            if (context.candidateCall.get$fakeCall().getTypeArgumentList() == null && KtDescriptorUtilKt.isFunctionForExpectTypeFromCastFeature(functionDescriptor)) {
                Call call = context.call;
                Intrinsics.checkNotNullExpressionValue(call, "call");
                KotlinType returnType = functionDescriptor.getReturnType();
                if (returnType == null || (kotlinTypeTypeInSystem = typeInSystem(builder, call, returnType)) == null) {
                    return;
                }
                context.trace.record(BindingContext.CAST_TYPE_USED_AS_EXPECTED_TYPE, binaryWithTypeParent);
                builder.addSubtypeConstraint(kotlinTypeTypeInSystem, kotlinTypeMakeNullable, ConstraintPositionKind.SPECIAL.position());
            }
        }
    }

    private final void addValidityConstraintsForConstituentTypes(ConstraintSystem.Builder builder, KotlinType type) {
        TypeConstructor constructor = type.getConstructor();
        if (constructor.getThis$0() instanceof TypeParameterDescriptor) {
            return;
        }
        TypeSubstitutor typeSubstitutorCreate = TypeSubstitutor.create(type);
        Intrinsics.checkNotNullExpressionValue(typeSubstitutorCreate, "create(...)");
        int i = 0;
        for (Object obj : type.getArguments()) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            TypeProjection typeProjection = (TypeProjection) obj;
            if (!typeProjection.isStarProjection()) {
                TypeParameterDescriptor typeParameterDescriptor = constructor.getParameters().get(i);
                Intrinsics.checkNotNull(typeParameterDescriptor);
                addValidityConstraintsForTypeArgument(builder, typeProjection, typeParameterDescriptor, typeSubstitutorCreate);
                KotlinType nullableAnyType = typeProjection.getNullableAnyType();
                Intrinsics.checkNotNullExpressionValue(nullableAnyType, "getType(...)");
                addValidityConstraintsForConstituentTypes(builder, nullableAnyType);
            }
            i = i2;
        }
    }

    private final void addValidityConstraintsForTypeArgument(ConstraintSystem.Builder builder, TypeProjection substitutedArgument, TypeParameterDescriptor typeParameter, TypeSubstitutor boundsSubstitutor) {
        KotlinType nullableAnyType = substitutedArgument.getNullableAnyType();
        Intrinsics.checkNotNullExpressionValue(nullableAnyType, "getType(...)");
        Iterator<KotlinType> it = typeParameter.getUpperBounds().iterator();
        while (it.hasNext()) {
            KotlinType kotlinTypeSafeSubstitute = boundsSubstitutor.safeSubstitute(it.next(), Variance.INVARIANT);
            Intrinsics.checkNotNullExpressionValue(kotlinTypeSafeSubstitute, "safeSubstitute(...)");
            SimpleType simpleTypeUpperIfFlexible = FlexibleTypesKt.upperIfFlexible(kotlinTypeSafeSubstitute);
            ValidityConstraintForConstituentType validityConstraintForConstituentType = new ValidityConstraintForConstituentType(nullableAnyType, typeParameter, simpleTypeUpperIfFlexible);
            if (!KotlinBuiltIns.isNullableAny(simpleTypeUpperIfFlexible)) {
                builder.addSubtypeConstraint(nullableAnyType, simpleTypeUpperIfFlexible, validityConstraintForConstituentType);
            }
        }
    }

    private final TypeSubstitutor compositeSubstitutor(final ConstraintSystem.Builder builder) {
        TypeSubstitutor typeSubstitutorCreate = TypeSubstitutor.create(new TypeSubstitution() { // from class: org.jetbrains.kotlin.resolve.calls.GenericCandidateResolver.compositeSubstitutor.1
            @Override // org.jetbrains.kotlin.types.TypeSubstitution
            /* JADX INFO: renamed from: get */
            public TypeProjection mo7475get(final KotlinType key) {
                Intrinsics.checkNotNullParameter(key, "key");
                return (TypeProjection) SequencesKt.firstOrNull(SequencesKt.mapNotNull(CollectionsKt.asSequence(CollectionsKt.reversed(builder.getTypeVariableSubstitutors().values())), new Function1<TypeSubstitutor, TypeProjection>() { // from class: org.jetbrains.kotlin.resolve.calls.GenericCandidateResolver$compositeSubstitutor$1$get$1
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final TypeProjection invoke(TypeSubstitutor it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        return it.getSubstitution().mo7475get(key);
                    }
                }));
            }
        });
        Intrinsics.checkNotNullExpressionValue(typeSubstitutorCreate, "create(...)");
        return typeSubstitutorCreate;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final void addConstraintForValueArgument(ValueArgument valueArgument, ValueParameterDescriptor valueParameterDescriptor, TypeSubstitutor substitutor, ConstraintSystem.Builder builder, CallCandidateResolutionContext<?> context, ResolveArgumentsMode resolveFunctionArgumentBodies) {
        KotlinType effectiveExpectedType = CallResolverUtilKt.getEffectiveExpectedType(valueParameterDescriptor, valueArgument, context);
        KtExpression argumentExpression = valueArgument.getArgumentExpression();
        KotlinType kotlinTypeSubstitute = substitutor.substitute(effectiveExpectedType, Variance.INVARIANT);
        DataFlowInfo info = context.candidateCall.getDataFlowInfoForArguments().getInfo(valueArgument);
        Intrinsics.checkNotNullExpressionValue(info, "getInfo(...)");
        Context contextReplaceDataFlowInfo = ((CallCandidateResolutionContext) context.replaceExpectedType(kotlinTypeSubstitute)).replaceDataFlowInfo(info);
        Intrinsics.checkNotNullExpressionValue(contextReplaceDataFlowInfo, "replaceDataFlowInfo(...)");
        CallCandidateResolutionContext<?> callCandidateResolutionContext = (CallCandidateResolutionContext) contextReplaceDataFlowInfo;
        ArgumentTypeResolver argumentTypeResolver = this.argumentTypeResolver;
        CallCandidateResolutionContext<?> callCandidateResolutionContext2 = callCandidateResolutionContext;
        boolean z = false;
        if (kotlinTypeSubstitute != null && FunctionTypesKt.isSuspendFunctionType(kotlinTypeSubstitute)) {
            z = true;
        }
        KotlinTypeInfo argumentTypeInfo = argumentTypeResolver.getArgumentTypeInfo(argumentExpression, callCandidateResolutionContext2, resolveFunctionArgumentBodies, z);
        Intrinsics.checkNotNullExpressionValue(argumentTypeInfo, "getArgumentTypeInfo(...)");
        context.candidateCall.getDataFlowInfoForArguments().updateInfo(valueArgument, argumentTypeInfo.getDataFlowInfo());
        ConstraintPosition constraintPositionPosition = ConstraintPositionKind.VALUE_PARAMETER_POSITION.position(valueParameterDescriptor.getIndex());
        if (addConstraintForNestedCall(argumentExpression, constraintPositionPosition, builder, callCandidateResolutionContext, effectiveExpectedType)) {
            return;
        }
        KotlinType type = argumentTypeInfo.getType();
        ResolutionContext<?> resolutionContextReplaceDataFlowInfo = context.replaceDataFlowInfo(info);
        Intrinsics.checkNotNullExpressionValue(resolutionContextReplaceDataFlowInfo, "replaceDataFlowInfo(...)");
        KotlinType kotlinTypeUpdateResultTypeForSmartCasts = updateResultTypeForSmartCasts(type, argumentExpression, resolutionContextReplaceDataFlowInfo);
        if ((argumentExpression instanceof KtCallableReferenceExpression) && kotlinTypeUpdateResultTypeForSmartCasts == null) {
            return;
        }
        builder.addSubtypeConstraint(kotlinTypeUpdateResultTypeForSmartCasts, compositeSubstitutor(builder).substitute(effectiveExpectedType, Variance.INVARIANT), constraintPositionPosition);
    }

    private final boolean addConstraintForNestedCall(KtExpression argumentExpression, ConstraintPosition constraintPosition, ConstraintSystem.Builder builder, CallCandidateResolutionContext<?> context, KotlinType effectiveExpectedType) {
        ConstraintSystem constraintSystem;
        CallableDescriptor candidateDescriptor;
        KotlinType returnType;
        ResolutionResultsCache.CachedData resolutionResultsCachedData = GenericCandidateResolverKt.getResolutionResultsCachedData(argumentExpression, context);
        OverloadResolutionResultsImpl<?> resolutionResults = resolutionResultsCachedData != null ? resolutionResultsCachedData.getResolutionResults() : null;
        if (resolutionResults == null || !resolutionResults.isSingleResult()) {
            return false;
        }
        MutableResolvedCall<D> mutableResolvedCallMo7436getResultingCall = resolutionResults.mo7436getResultingCall();
        Intrinsics.checkNotNullExpressionValue(mutableResolvedCallMo7436getResultingCall, "getResultingCall(...)");
        if (mutableResolvedCallMo7436getResultingCall.isCompleted() || (constraintSystem = mutableResolvedCallMo7436getResultingCall.getConstraintSystem()) == null || (returnType = (candidateDescriptor = mutableResolvedCallMo7436getResultingCall.get$suspendPropertyDescriptor()).getReturnType()) == null) {
            return false;
        }
        List<TypeVariable> nestedTypeVariables = ConstraintSystemUtilsKt.getNestedTypeVariables(constraintSystem, returnType);
        if (!(nestedTypeVariables instanceof Collection) || !nestedTypeVariables.isEmpty()) {
            Iterator<T> it = nestedTypeVariables.iterator();
            while (it.hasNext()) {
                if (!constraintSystem.getTypeBounds((TypeVariable) it.next()).getBounds().isEmpty()) {
                    return false;
                }
            }
        }
        CallableDescriptor callableDescriptorAlphaConvertTypeParameters = FunctionDescriptorUtil.alphaConvertTypeParameters(candidateDescriptor);
        List<TypeParameterDescriptor> typeParameters = candidateDescriptor.getTypeParameters();
        Intrinsics.checkNotNullExpressionValue(typeParameters, "getTypeParameters(...)");
        List<TypeParameterDescriptor> typeParameters2 = callableDescriptorAlphaConvertTypeParameters.getTypeParameters();
        Intrinsics.checkNotNullExpressionValue(typeParameters2, "getTypeParameters(...)");
        Map map = MapsKt.toMap(CollectionsKt.zip(typeParameters, typeParameters2));
        List<TypeParameterDescriptor> nestedTypeParameters = ConstraintSystemUtilsKt.getNestedTypeParameters(returnType);
        ArrayList arrayList = new ArrayList();
        Iterator<T> it2 = nestedTypeParameters.iterator();
        while (it2.hasNext()) {
            TypeParameterDescriptor typeParameterDescriptor = (TypeParameterDescriptor) map.get((TypeParameterDescriptor) it2.next());
            if (typeParameterDescriptor != null) {
                arrayList.add(typeParameterDescriptor);
            }
        }
        Call call = mutableResolvedCallMo7436getResultingCall.get$fakeCall();
        Intrinsics.checkNotNullExpressionValue(call, "getCall(...)");
        builder.registerTypeVariables(TypeVariableKt.toHandle(call), arrayList, true);
        builder.addSubtypeConstraint(ResolvedCallUtilKt.makeNullableTypeIfSafeReceiver(mutableResolvedCallMo7436getResultingCall, callableDescriptorAlphaConvertTypeParameters.getReturnType(), context), compositeSubstitutor(builder).substitute(effectiveExpectedType, Variance.INVARIANT), constraintPosition);
        return true;
    }

    private final KotlinType updateResultTypeForSmartCasts(KotlinType type, KtExpression argumentExpression, ResolutionContext<?> context) {
        KtExpression lastElementDeparenthesized = KtPsiUtil.getLastElementDeparenthesized(argumentExpression, context.statementFilter);
        if (lastElementDeparenthesized == null || type == null) {
            return type;
        }
        DataFlowValue dataFlowValueCreateDataFlowValue = this.dataFlowValueFactory.createDataFlowValue(lastElementDeparenthesized, type, context);
        if (!dataFlowValueCreateDataFlowValue.getIsStable()) {
            return type;
        }
        DataFlowInfo dataFlowInfo = context.dataFlowInfo;
        LanguageVersionSettings languageVersionSettings = context.languageVersionSettings;
        Intrinsics.checkNotNullExpressionValue(languageVersionSettings, "languageVersionSettings");
        Set<KotlinType> collectedTypes = dataFlowInfo.getCollectedTypes(dataFlowValueCreateDataFlowValue, languageVersionSettings);
        return collectedTypes.isEmpty() ? type : TypeIntersector.intersectTypes(SetsKt.plus(collectedTypes, type));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final <D extends CallableDescriptor> void completeTypeInferenceDependentOnFunctionArgumentsForCall(CallCandidateResolutionContext<D> context) {
        ConstraintSystem.Builder builder$default;
        TypeSubstitutor resultingSubstitutor;
        CallCandidateResolutionContext<D> callCandidateResolutionContext;
        CallCandidateResolutionContext<D> context2 = context;
        Intrinsics.checkNotNullParameter(context2, "context");
        MutableResolvedCall<D> candidateCall = context2.candidateCall;
        Intrinsics.checkNotNullExpressionValue(candidateCall, "candidateCall");
        ConstraintSystem constraintSystem = candidateCall.getConstraintSystem();
        if (constraintSystem == null || (builder$default = ConstraintSystem.toBuilder$default(constraintSystem, null, 1, null)) == null) {
            return;
        }
        if (candidateCall instanceof VariableAsFunctionResolvedCall) {
            context2 = CallCandidateResolutionContext.create(candidateCall, context2, context2.trace, context2.tracing, ((VariableAsFunctionResolvedCall) candidateCall).getFunctionCall().get$fakeCall(), context2.candidateResolveMode);
        }
        Map<ValueParameterDescriptor, ResolvedValueArgument> valueArguments = candidateCall.getValueArguments();
        Intrinsics.checkNotNullExpressionValue(valueArguments, "getValueArguments(...)");
        for (Map.Entry<ValueParameterDescriptor, ResolvedValueArgument> entry : valueArguments.entrySet()) {
            ValueParameterDescriptor key = entry.getKey();
            for (ValueArgument valueArgument : entry.getValue().getArguments()) {
                KtExpression argumentExpression = valueArgument.getArgumentExpression();
                if (argumentExpression != null) {
                    CallCandidateResolutionContext<D> callCandidateResolutionContext2 = context2;
                    KtFunction functionLiteralArgumentIfAny = ArgumentTypeResolver.getFunctionLiteralArgumentIfAny(argumentExpression, callCandidateResolutionContext2);
                    if (functionLiteralArgumentIfAny != null) {
                        Intrinsics.checkNotNull(functionLiteralArgumentIfAny);
                        Intrinsics.checkNotNull(valueArgument);
                        Intrinsics.checkNotNull(key);
                        Intrinsics.checkNotNull(context2);
                        addConstraintForFunctionLiteralArgument(functionLiteralArgumentIfAny, valueArgument, key, builder$default, context2, candidateCall.get$suspendPropertyDescriptor().getReturnType());
                    }
                    if (GenericCandidateResolverKt.getSPECIAL_FUNCTION_NAMES().contains(candidateCall.get$suspendPropertyDescriptor().getShortName())) {
                        callCandidateResolutionContext = context2;
                    } else {
                        TemporaryBindingTrace temporaryBindingTraceCreate = TemporaryBindingTrace.create(context2.trace, "Trace to complete argument for call that might be not resulting call");
                        Intrinsics.checkNotNullExpressionValue(temporaryBindingTraceCreate, "create(...)");
                        callCandidateResolutionContext = (CallCandidateResolutionContext) context2.replaceBindingTrace(temporaryBindingTraceCreate);
                    }
                    KtCallableReferenceExpression callableReferenceExpressionIfAny = ArgumentTypeResolver.getCallableReferenceExpressionIfAny(argumentExpression, callCandidateResolutionContext2);
                    if (callableReferenceExpressionIfAny != null) {
                        Intrinsics.checkNotNull(callableReferenceExpressionIfAny);
                        Intrinsics.checkNotNull(valueArgument);
                        Intrinsics.checkNotNull(key);
                        Intrinsics.checkNotNull(callCandidateResolutionContext);
                        addConstraintForCallableReference(callableReferenceExpressionIfAny, valueArgument, key, builder$default, callCandidateResolutionContext);
                    }
                }
            }
        }
        ConstraintSystem constraintSystemBuild = builder$default.build();
        candidateCall.setConstraintSystem(constraintSystemBuild);
        if (this.languageVersionSettings.supportsFeature(LanguageFeature.NewInference)) {
            resultingSubstitutor = constraintSystemBuild.getResultingSubstitutor().replaceWithContravariantApproximatingSubstitution();
        } else {
            resultingSubstitutor = constraintSystemBuild.getResultingSubstitutor();
        }
        Intrinsics.checkNotNull(resultingSubstitutor);
        candidateCall.setSubstitutor(resultingSubstitutor);
    }

    private final KotlinType estimateLiteralReturnType(CallCandidateResolutionContext<?> context, KotlinType literalExpectedType, KotlinType ownerReturnType) {
        if (!TypeUtils.noExpectedType(context.expectedType) && ownerReturnType != null && TypeUtils.isTypeParameter(ownerReturnType) && FunctionTypesKt.isFunctionTypeOrSubtype(literalExpectedType) && Intrinsics.areEqual(CallResolverUtilKt.getReturnTypeForCallable(literalExpectedType), ownerReturnType)) {
            return context.expectedType;
        }
        return TypeUtils.DONT_CARE;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final <D extends CallableDescriptor> void addConstraintForFunctionLiteralArgument(KtFunction functionLiteral, ValueArgument valueArgument, ValueParameterDescriptor valueParameterDescriptor, ConstraintSystem.Builder constraintSystem, CallCandidateResolutionContext<D> context, KotlinType argumentOwnerReturnType) {
        KotlinType kotlinType;
        KtExpression argumentExpression = valueArgument.getArgumentExpression();
        if (argumentExpression == null) {
            return;
        }
        CallCandidateResolutionContext<D> callCandidateResolutionContext = context;
        KotlinType effectiveExpectedType = CallResolverUtilKt.getEffectiveExpectedType(valueParameterDescriptor, valueArgument, callCandidateResolutionContext);
        if (BuilderInferenceUtilKt.isBuilderInferenceCall(valueParameterDescriptor, valueArgument, this.languageVersionSettings)) {
            this.builderInferenceSupport.analyzeBuilderInferenceCall(functionLiteral, valueArgument, constraintSystem, context, effectiveExpectedType);
        }
        final TypeSubstitution substitution = constraintSystem.build().getCurrentSubstitutor().getSubstitution();
        KotlinType kotlinTypeSubstitute = new DelegatedTypeSubstitution(substitution) { // from class: org.jetbrains.kotlin.resolve.calls.GenericCandidateResolver$addConstraintForFunctionLiteralArgument$newSubstitution$1
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
        }.buildSubstitutor().substitute(effectiveExpectedType, Variance.IN_VARIANCE);
        if (kotlinTypeSubstitute == null || TypeUtils.isDontCarePlaceholder(kotlinTypeSubstitute)) {
            kotlinTypeSubstitute = this.argumentTypeResolver.getShapeTypeOfFunctionLiteral(functionLiteral, context.scope, context.trace, false, kotlinTypeSubstitute != null && FunctionTypesKt.isSuspendFunctionType(kotlinTypeSubstitute));
        }
        KotlinType kotlinType2 = kotlinTypeSubstitute;
        if (kotlinType2 == null || !FunctionTypesKt.isBuiltinFunctionalType(kotlinType2) || CallResolverUtilKt.hasUnknownFunctionParameter(kotlinType2)) {
            return;
        }
        MutableDataFlowInfoForArguments dataFlowInfoForArguments = context.candidateCall.getDataFlowInfoForArguments();
        Intrinsics.checkNotNullExpressionValue(dataFlowInfoForArguments, "getDataFlowInfoForArguments(...)");
        DataFlowInfo info = dataFlowInfoForArguments.getInfo(valueArgument);
        Intrinsics.checkNotNullExpressionValue(info, "getInfo(...)");
        Map<CallHandle, TypeSubstitutor> typeVariableSubstitutors = constraintSystem.getTypeVariableSubstitutors();
        Call call = context.call;
        Intrinsics.checkNotNullExpressionValue(call, "call");
        TypeSubstitutor typeSubstitutor = typeVariableSubstitutors.get(TypeVariableKt.toHandle(call));
        KotlinType kotlinTypeSubstitute2 = typeSubstitutor != null ? typeSubstitutor.substitute(effectiveExpectedType, Variance.INVARIANT) : null;
        boolean zHasUnknownReturnType = CallResolverUtilKt.hasUnknownReturnType(kotlinType2);
        ConstraintPosition constraintPositionPosition = ConstraintPositionKind.VALUE_PARAMETER_POSITION.position(valueParameterDescriptor.getIndex());
        if (zHasUnknownReturnType) {
            kotlinType = kotlinTypeSubstitute2;
        } else {
            TemporaryTraceAndCache temporaryTraceAndCacheCreate = TemporaryTraceAndCache.create(callCandidateResolutionContext, "trace to resolve function literal with expected return type", argumentExpression);
            KtExpression expressionOrLastStatementInBlock = KtPsiUtil.getExpressionOrLastStatementInBlock(functionLiteral.getBodyExpression());
            if (expressionOrLastStatementInBlock == null) {
                return;
            }
            boolean[] zArr = new boolean[1];
            Context contextReplaceContextDependency = ((CallCandidateResolutionContext) ((CallCandidateResolutionContext) ((CallCandidateResolutionContext) ((CallCandidateResolutionContext) context.replaceBindingTrace(ExpressionTypingUtils.makeTraceInterceptingTypeMismatch(temporaryTraceAndCacheCreate.trace, expressionOrLastStatementInBlock, zArr))).replaceExpectedType(kotlinType2)).replaceDataFlowInfo(info)).replaceResolutionResultsCache(temporaryTraceAndCacheCreate.cache)).replaceContextDependency(ContextDependency.INDEPENDENT);
            Intrinsics.checkNotNullExpressionValue(contextReplaceContextDependency, "replaceContextDependency(...)");
            KotlinType type = this.argumentTypeResolver.getFunctionLiteralTypeInfo(argumentExpression, functionLiteral, (CallCandidateResolutionContext) contextReplaceContextDependency, ResolveArgumentsMode.RESOLVE_FUNCTION_ARGUMENTS, FunctionTypesKt.isSuspendFunctionType(kotlinType2)).getType();
            kotlinType = kotlinTypeSubstitute2;
            if (!zArr[0]) {
                constraintSystem.addSubtypeConstraint(type, kotlinType, constraintPositionPosition);
                temporaryTraceAndCacheCreate.commit();
                return;
            }
        }
        KotlinType kotlinTypeEstimateLiteralReturnType = estimateLiteralReturnType(context, effectiveExpectedType, argumentOwnerReturnType);
        Intrinsics.checkNotNull(kotlinTypeEstimateLiteralReturnType);
        Context contextReplaceContextDependency2 = ((CallCandidateResolutionContext) ((CallCandidateResolutionContext) context.replaceExpectedType(CallResolverUtilKt.replaceReturnTypeForCallable(kotlinType2, kotlinTypeEstimateLiteralReturnType))).replaceDataFlowInfo(info)).replaceContextDependency(ContextDependency.INDEPENDENT);
        Intrinsics.checkNotNullExpressionValue(contextReplaceContextDependency2, "replaceContextDependency(...)");
        constraintSystem.addSubtypeConstraint(this.argumentTypeResolver.getFunctionLiteralTypeInfo(argumentExpression, functionLiteral, (CallCandidateResolutionContext) contextReplaceContextDependency2, ResolveArgumentsMode.RESOLVE_FUNCTION_ARGUMENTS, FunctionTypesKt.isSuspendFunctionType(kotlinType2)).getType(), kotlinType, constraintPositionPosition);
    }

    private final <D extends CallableDescriptor> void addConstraintForCallableReference(KtCallableReferenceExpression callableReference, ValueArgument valueArgument, ValueParameterDescriptor valueParameterDescriptor, ConstraintSystem.Builder constraintSystem, CallCandidateResolutionContext<D> context) {
        KotlinType resolvedTypeForCallableReference;
        KotlinType effectiveExpectedType = CallResolverUtilKt.getEffectiveExpectedType(valueParameterDescriptor, valueArgument, context);
        KotlinType expectedTypeForCallableReference = getExpectedTypeForCallableReference(callableReference, constraintSystem, context, effectiveExpectedType);
        if (expectedTypeForCallableReference == null || !GenericCandidateResolverKt.isApplicableExpectedTypeForCallableReference(expectedTypeForCallableReference) || (resolvedTypeForCallableReference = getResolvedTypeForCallableReference(callableReference, context, expectedTypeForCallableReference, valueArgument)) == null) {
            return;
        }
        ConstraintPosition constraintPositionPosition = ConstraintPositionKind.VALUE_PARAMETER_POSITION.position(valueParameterDescriptor.getIndex());
        Map<CallHandle, TypeSubstitutor> typeVariableSubstitutors = constraintSystem.getTypeVariableSubstitutors();
        Call call = context.call;
        Intrinsics.checkNotNullExpressionValue(call, "call");
        TypeSubstitutor typeSubstitutor = typeVariableSubstitutors.get(TypeVariableKt.toHandle(call));
        constraintSystem.addSubtypeConstraint(resolvedTypeForCallableReference, typeSubstitutor != null ? typeSubstitutor.substitute(effectiveExpectedType, Variance.INVARIANT) : null, constraintPositionPosition);
    }

    private final <D extends CallableDescriptor> KotlinType getExpectedTypeForCallableReference(KtCallableReferenceExpression callableReference, ConstraintSystem.Builder constraintSystem, CallCandidateResolutionContext<D> context, KotlinType effectiveExpectedType) {
        KotlinType kotlinTypeSubstitute = constraintSystem.build().getCurrentSubstitutor().substitute(effectiveExpectedType, Variance.INVARIANT);
        if (kotlinTypeSubstitute != null && !TypeUtils.isDontCarePlaceholder(kotlinTypeSubstitute)) {
            return kotlinTypeSubstitute;
        }
        KotlinType shapeTypeOfCallableReference = this.argumentTypeResolver.getShapeTypeOfCallableReference(callableReference, context, false);
        if (shapeTypeOfCallableReference == null || !FunctionTypesKt.isFunctionTypeOrSubtype(shapeTypeOfCallableReference) || CallResolverUtilKt.hasUnknownFunctionParameter(shapeTypeOfCallableReference)) {
            return null;
        }
        return shapeTypeOfCallableReference;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final <D extends CallableDescriptor> KotlinType getResolvedTypeForCallableReference(KtCallableReferenceExpression callableReference, CallCandidateResolutionContext<D> context, KotlinType expectedType, ValueArgument valueArgument) {
        DataFlowInfo info = context.candidateCall.getDataFlowInfoForArguments().getInfo(valueArgument);
        Intrinsics.checkNotNullExpressionValue(info, "getInfo(...)");
        if (!CallResolverUtilKt.hasUnknownReturnType(expectedType)) {
            expectedType = CallResolverUtilKt.replaceReturnTypeByUnknown(expectedType);
        }
        Context contextReplaceContextDependency = ((CallCandidateResolutionContext) ((CallCandidateResolutionContext) context.replaceExpectedType(expectedType)).replaceDataFlowInfo(info)).replaceContextDependency(ContextDependency.INDEPENDENT);
        Intrinsics.checkNotNullExpressionValue(contextReplaceContextDependency, "replaceContextDependency(...)");
        return this.argumentTypeResolver.getCallableReferenceTypeInfo(callableReference, callableReference, (CallCandidateResolutionContext) contextReplaceContextDependency, ResolveArgumentsMode.RESOLVE_FUNCTION_ARGUMENTS).getType();
    }
}
