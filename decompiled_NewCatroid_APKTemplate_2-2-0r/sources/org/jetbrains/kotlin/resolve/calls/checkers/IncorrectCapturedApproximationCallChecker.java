package org.jetbrains.kotlin.resolve.calls.checkers;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.FunctionTypesKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.ValueArgument;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.calls.inference.CapturedTypeConstructorKt;
import org.jetbrains.kotlin.resolve.calls.model.ArgumentMappingKt;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedValueArgument;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowValue;
import org.jetbrains.kotlin.resolve.calls.util.CallResolverUtilKt;
import org.jetbrains.kotlin.resolve.calls.util.CallUtilKt;
import org.jetbrains.kotlin.resolve.sam.SamConversionResolverImplKt;
import org.jetbrains.kotlin.resolve.scopes.receivers.ExpressionReceiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeProjection;
import org.jetbrains.kotlin.types.TypeSubstitution;
import org.jetbrains.kotlin.types.TypeSubstitutor;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: IncorrectCapturedApproximationCallChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J$\u0010\u0003\u001a\u00020\u00042\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J(\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\nH\u0002¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/checkers/IncorrectCapturedApproximationCallChecker;", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallChecker;", "()V", "check", "", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "reportOn", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "context", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallCheckerContext;", "shouldWarningBeReported", "", "expressionType", "Lorg/jetbrains/kotlin/types/KotlinType;", "expectedType", "dataFlowValue", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowValue;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IncorrectCapturedApproximationCallChecker implements CallChecker {
    public static final IncorrectCapturedApproximationCallChecker INSTANCE = new IncorrectCapturedApproximationCallChecker();

    private IncorrectCapturedApproximationCallChecker() {
    }

    @Override // org.jetbrains.kotlin.resolve.calls.checkers.CallChecker
    public void check(ResolvedCall<?> resolvedCall, PsiElement reportOn, CallCheckerContext context) {
        FunctionDescriptor functionDescriptorSubstitute;
        List<ResolvedValueArgument> valueArgumentsByIndex;
        ReceiverValue extensionReceiver;
        KtExpression expression;
        KotlinType nullableAnyType;
        Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
        KtExpression reportOn2 = reportOn;
        Intrinsics.checkNotNullParameter(reportOn2, "reportOn");
        Intrinsics.checkNotNullParameter(context, "context");
        if (ArgumentMappingKt.isReallySuccess(resolvedCall)) {
            KotlinType smartCastDispatchReceiverType = resolvedCall.getSmartCastDispatchReceiverType();
            if (smartCastDispatchReceiverType == null) {
                ReceiverValue dispatchReceiver = resolvedCall.getDispatchReceiver();
                smartCastDispatchReceiverType = dispatchReceiver != null ? dispatchReceiver.getType() : null;
                if (smartCastDispatchReceiverType == null) {
                    return;
                }
            }
            List<TypeProjection> arguments = smartCastDispatchReceiverType.getArguments();
            if (!(arguments instanceof Collection) || !arguments.isEmpty()) {
                Iterator<T> it = arguments.iterator();
                do {
                    if (it.hasNext()) {
                        TypeProjection typeProjection = (TypeProjection) it.next();
                        if (typeProjection.getProjectionKind() != Variance.INVARIANT) {
                            break;
                        }
                        nullableAnyType = typeProjection.getNullableAnyType();
                        Intrinsics.checkNotNullExpressionValue(nullableAnyType, "getType(...)");
                    }
                } while (!CapturedTypeConstructorKt.isCaptured(nullableAnyType));
                TypeSubstitution substitution = TypeSubstitutor.create(smartCastDispatchReceiverType).getSubstitution();
                Intrinsics.checkNotNullExpressionValue(substitution, "getSubstitution(...)");
                int i = 0;
                TypeSubstitutor typeSubstitutorBuildSubstitutor = CapturedTypeConstructorKt.wrapWithCapturingSubstitution(substitution, false).buildSubstitutor();
                CallableDescriptor original = resolvedCall.getResultingDescriptor().getOriginal();
                FunctionDescriptor functionDescriptor = original instanceof FunctionDescriptor ? (FunctionDescriptor) original : null;
                if (functionDescriptor == null || (functionDescriptorSubstitute = functionDescriptor.substitute(typeSubstitutorBuildSubstitutor)) == null || (valueArgumentsByIndex = resolvedCall.getValueArgumentsByIndex()) == null) {
                    return;
                }
                for (ValueParameterDescriptor valueParameterDescriptor : functionDescriptorSubstitute.getValueParameters()) {
                    int i2 = i + 1;
                    for (ValueArgument valueArgument : valueArgumentsByIndex.get(i).getArguments()) {
                        Intrinsics.checkNotNull(valueParameterDescriptor);
                        Intrinsics.checkNotNull(valueArgument);
                        KotlinType effectiveExpectedType = CallResolverUtilKt.getEffectiveExpectedType(valueParameterDescriptor, valueArgument, context.getResolutionContext());
                        KtExpression argumentExpression = valueArgument.getArgumentExpression();
                        if (argumentExpression != null) {
                            BindingContext context2 = context.getTrace().getContext();
                            Intrinsics.checkNotNullExpressionValue(context2, "getBindingContext(...)");
                            KotlinType type = CallUtilKt.getType(argumentExpression, context2);
                            if (type != null && shouldWarningBeReported(type, effectiveExpectedType, context.getDataFlowValueFactory().createDataFlowValue(argumentExpression, type, context.getResolutionContext()), context)) {
                                context.getTrace().report(Errors.TYPE_MISMATCH_WARNING_FOR_INCORRECT_CAPTURE_APPROXIMATION.on(argumentExpression, effectiveExpectedType, type));
                            }
                        }
                    }
                    i = i2;
                }
                ReceiverParameterDescriptor extensionReceiverParameter = functionDescriptorSubstitute.getExtensionReceiverParameter();
                if (extensionReceiverParameter == null || (extensionReceiver = resolvedCall.getExtensionReceiver()) == null) {
                    return;
                }
                Intrinsics.checkNotNull(extensionReceiver);
                DataFlowValue dataFlowValueCreateDataFlowValue = context.getDataFlowValueFactory().createDataFlowValue(extensionReceiver, context.getResolutionContext());
                IncorrectCapturedApproximationCallChecker incorrectCapturedApproximationCallChecker = INSTANCE;
                KotlinType type2 = extensionReceiver.getType();
                Intrinsics.checkNotNullExpressionValue(type2, "getType(...)");
                KotlinType type3 = extensionReceiverParameter.getType();
                Intrinsics.checkNotNullExpressionValue(type3, "getType(...)");
                if (incorrectCapturedApproximationCallChecker.shouldWarningBeReported(type2, type3, dataFlowValueCreateDataFlowValue, context)) {
                    ExpressionReceiver expressionReceiver = extensionReceiver instanceof ExpressionReceiver ? (ExpressionReceiver) extensionReceiver : null;
                    if (expressionReceiver != null && (expression = expressionReceiver.getExpression()) != null) {
                        reportOn2 = expression;
                    }
                    context.getTrace().report(Errors.RECEIVER_TYPE_MISMATCH_WARNING_FOR_INCORRECT_CAPTURE_APPROXIMATION.on(reportOn2, extensionReceiverParameter.getType(), extensionReceiver.getType()));
                }
            }
        }
    }

    private final boolean shouldWarningBeReported(KotlinType expressionType, KotlinType expectedType, DataFlowValue dataFlowValue, CallCheckerContext context) {
        List<TypeProjection> arguments = expectedType.getArguments();
        if ((arguments instanceof Collection) && arguments.isEmpty()) {
            return false;
        }
        for (TypeProjection typeProjection : arguments) {
            if (!typeProjection.isStarProjection()) {
                KotlinType nullableAnyType = typeProjection.getNullableAnyType();
                Intrinsics.checkNotNullExpressionValue(nullableAnyType, "getType(...)");
                if (TypeUtilsKt.contains(nullableAnyType, IncorrectCapturedApproximationCallChecker$shouldWarningBeReported$1$1.INSTANCE)) {
                    if (TypeUtilsKt.isSubtypeOf(expressionType, expectedType)) {
                        return false;
                    }
                    KotlinType functionTypeForSamType = SamConversionResolverImplKt.getFunctionTypeForSamType(expectedType, context.getCallComponents().getSamConversionResolver(), context.getCallComponents().getSamConversionOracle());
                    if (FunctionTypesKt.isFunctionOrSuspendFunctionType(expectedType)) {
                        return false;
                    }
                    if (functionTypeForSamType != null && FunctionTypesKt.isFunctionOrSuspendFunctionType(functionTypeForSamType)) {
                        return false;
                    }
                    Set<KotlinType> collectedTypes = context.getDataFlowInfo().getCollectedTypes(dataFlowValue, context.getLanguageVersionSettings());
                    if (!(collectedTypes instanceof Collection) || !collectedTypes.isEmpty()) {
                        Iterator<T> it = collectedTypes.iterator();
                        while (it.hasNext()) {
                            if (TypeUtilsKt.isSubtypeOf((KotlinType) it.next(), expectedType)) {
                                return false;
                            }
                        }
                    }
                    return true;
                }
            }
        }
        return false;
    }
}
