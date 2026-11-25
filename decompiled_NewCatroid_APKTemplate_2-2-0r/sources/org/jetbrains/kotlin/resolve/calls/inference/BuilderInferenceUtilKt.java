package org.jetbrains.kotlin.resolve.calls.inference;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import org.jetbrains.kotlin.builtins.FunctionTypesKt;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.coroutines.CoroutineUtilKt;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtLabeledExpression;
import org.jetbrains.kotlin.psi.KtLambdaExpression;
import org.jetbrains.kotlin.psi.ValueArgument;
import org.jetbrains.kotlin.resolve.calls.results.OverloadResolutionResultsImpl;
import org.jetbrains.kotlin.resolve.descriptorUtil.AnnotationsForResolveUtilsKt;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.StubTypeForBuilderInference;
import org.jetbrains.kotlin.types.UnwrappedType;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: BuilderInferenceUtil.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00006\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0016\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005\u001a\u001e\u0010\u0006\u001a\u00020\u00012\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u0004\u001a\u00020\u0005\u001a\u0010\u0010\u000b\u001a\u00020\u00012\u0006\u0010\f\u001a\u00020\u0003H\u0002\u001a\f\u0010\r\u001a\u00020\u0001*\u00020\u000eH\u0002\u001a\u0012\u0010\u000f\u001a\u0004\u0018\u00010\u0010*\u0006\u0012\u0002\b\u00030\u0011H\u0002\u001a\u000e\u0010\u0012\u001a\u00020\u0001*\u0006\u0012\u0002\b\u00030\u0011¨\u0006\u0013"}, d2 = {"isApplicableCallForBuilderInference", "", "descriptor", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "isBuilderInferenceCall", "parameterDescriptor", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "argument", "Lorg/jetbrains/kotlin/psi/ValueArgument;", "isGoodCallForOldBuilderInference", "resultingDescriptor", "containsTypeTemplate", "Lorg/jetbrains/kotlin/types/KotlinType;", "getBuilderInferenceData", "Lorg/jetbrains/kotlin/resolve/calls/inference/BuilderInferenceData;", "Lorg/jetbrains/kotlin/resolve/calls/results/OverloadResolutionResultsImpl;", "isResultWithBuilderInference", "frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class BuilderInferenceUtilKt {
    private static final boolean containsTypeTemplate(KotlinType kotlinType) {
        return TypeUtilsKt.contains(kotlinType, new Function1<UnwrappedType, Boolean>() { // from class: org.jetbrains.kotlin.resolve.calls.inference.BuilderInferenceUtilKt.containsTypeTemplate.1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(UnwrappedType it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf((it instanceof TypeTemplate) || (it instanceof StubTypeForBuilderInference));
            }
        });
    }

    public static final boolean isApplicableCallForBuilderInference(CallableDescriptor descriptor, LanguageVersionSettings languageVersionSettings) {
        KotlinType type;
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        if (languageVersionSettings.supportsFeature(LanguageFeature.UnrestrictedBuilderInference)) {
            return true;
        }
        if (!languageVersionSettings.supportsFeature(LanguageFeature.ExperimentalBuilderInference)) {
            return isGoodCallForOldBuilderInference(descriptor);
        }
        if (DescriptorUtilsKt.isExtension(descriptor) && !AnnotationsForResolveUtilsKt.hasBuilderInferenceAnnotation(descriptor)) {
            ReceiverParameterDescriptor extensionReceiverParameter = descriptor.getExtensionReceiverParameter();
            return (extensionReceiverParameter == null || (type = extensionReceiverParameter.getType()) == null || containsTypeTemplate(type)) ? false : true;
        }
        KotlinType returnType = descriptor.getReturnType();
        if (returnType == null) {
            return false;
        }
        return !containsTypeTemplate(returnType);
    }

    private static final boolean isGoodCallForOldBuilderInference(CallableDescriptor callableDescriptor) {
        KotlinType returnType = callableDescriptor.getReturnType();
        if (returnType == null || containsTypeTemplate(returnType)) {
            return false;
        }
        if (callableDescriptor instanceof FunctionDescriptor) {
            FunctionDescriptor functionDescriptor = (FunctionDescriptor) callableDescriptor;
            if (!functionDescriptor.isSuspend()) {
                List<ValueParameterDescriptor> valueParameters = functionDescriptor.getValueParameters();
                Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
                List<ValueParameterDescriptor> list = valueParameters;
                if (!(list instanceof Collection) || !list.isEmpty()) {
                    Iterator<T> it = list.iterator();
                    while (it.hasNext()) {
                        KotlinType type = ((ValueParameterDescriptor) it.next()).getType();
                        Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
                        if (containsTypeTemplate(type)) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    public static final boolean isBuilderInferenceCall(ValueParameterDescriptor parameterDescriptor, ValueArgument argument, LanguageVersionSettings languageVersionSettings) {
        boolean hasSuspendFunctionType;
        Intrinsics.checkNotNullParameter(parameterDescriptor, "parameterDescriptor");
        Intrinsics.checkNotNullParameter(argument, "argument");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        if (languageVersionSettings.supportsFeature(LanguageFeature.ExperimentalBuilderInference)) {
            hasSuspendFunctionType = AnnotationsForResolveUtilsKt.hasBuilderInferenceAnnotation(parameterDescriptor) && CoroutineUtilKt.getHasFunctionOrSuspendFunctionType(parameterDescriptor);
        } else {
            hasSuspendFunctionType = CoroutineUtilKt.getHasSuspendFunctionType(parameterDescriptor);
        }
        KtExpression argumentExpression = argument.getArgumentExpression();
        if (argumentExpression instanceof KtLabeledExpression) {
            argumentExpression = ((KtLabeledExpression) argumentExpression).getBaseExpression();
        }
        if (hasSuspendFunctionType && (argumentExpression instanceof KtLambdaExpression)) {
            KotlinType type = parameterDescriptor.getType();
            Intrinsics.checkNotNull(type);
            if (FunctionTypesKt.isBuiltinFunctionalType(type) && FunctionTypesKt.getReceiverTypeFromFunctionType(type) != null) {
                return true;
            }
        }
        return false;
    }

    public static final boolean isResultWithBuilderInference(OverloadResolutionResultsImpl<?> overloadResolutionResultsImpl) {
        Intrinsics.checkNotNullParameter(overloadResolutionResultsImpl, "<this>");
        return getBuilderInferenceData(overloadResolutionResultsImpl) != null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final BuilderInferenceData getBuilderInferenceData(OverloadResolutionResultsImpl<?> overloadResolutionResultsImpl) {
        if (!overloadResolutionResultsImpl.isSingleResult()) {
            return null;
        }
        BuilderInferenceData builderInferenceData$getData = getBuilderInferenceData$getData(overloadResolutionResultsImpl.mo7436getResultingCall().getDispatchReceiver());
        return builderInferenceData$getData == null ? getBuilderInferenceData$getData(overloadResolutionResultsImpl.mo7436getResultingCall().getExtensionReceiver()) : builderInferenceData$getData;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static final BuilderInferenceData getBuilderInferenceData$getData(ReceiverValue receiverValue) {
        KotlinType type;
        final Ref.ObjectRef objectRef = new Ref.ObjectRef();
        if (receiverValue != null && (type = receiverValue.getType()) != null) {
            TypeUtilsKt.contains(type, new Function1<UnwrappedType, Boolean>() { // from class: org.jetbrains.kotlin.resolve.calls.inference.BuilderInferenceUtilKt$getBuilderInferenceData$getData$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                /* JADX WARN: Type inference failed for: r2v5, types: [T, org.jetbrains.kotlin.resolve.calls.inference.BuilderInferenceData] */
                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(UnwrappedType it) {
                    ?? builderInferenceData;
                    Intrinsics.checkNotNullParameter(it, "it");
                    TypeTemplate typeTemplate = it instanceof TypeTemplate ? (TypeTemplate) it : null;
                    if (typeTemplate != null && (builderInferenceData = typeTemplate.getBuilderInferenceData()) != 0) {
                        objectRef.element = builderInferenceData;
                    }
                    return false;
                }
            });
        }
        return (BuilderInferenceData) objectRef.element;
    }
}
