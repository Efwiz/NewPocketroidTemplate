package org.jetbrains.kotlin.js.translate.callTranslator;

import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.FunctionTypesKt;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsStatement;
import org.jetbrains.kotlin.js.backend.ast.metadata.MetadataProperties;
import org.jetbrains.kotlin.js.backend.ast.metadata.RangeKind;
import org.jetbrains.kotlin.js.backend.ast.metadata.RangeType;
import org.jetbrains.kotlin.js.translate.context.TranslationContext;
import org.jetbrains.kotlin.js.translate.general.Translation;
import org.jetbrains.kotlin.js.translate.reference.CallExpressionTranslator;
import org.jetbrains.kotlin.js.translate.utils.InlineUtils;
import org.jetbrains.kotlin.js.translate.utils.JsAstUtils;
import org.jetbrains.kotlin.js.translate.utils.TranslationUtils;
import org.jetbrains.kotlin.js.translate.utils.UtilsKt;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.FqNameUnsafe;
import org.jetbrains.kotlin.psi.Call;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.model.VariableAsFunctionResolvedCall;
import org.jetbrains.kotlin.resolve.calls.tasks.ExplicitReceiverKind;
import org.jetbrains.kotlin.resolve.calls.util.CallResolverUtilKt;
import org.jetbrains.kotlin.resolve.calls.util.CallUtilKt;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.scopes.receivers.ExpressionReceiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeConstructor;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: CallTranslator.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000B\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a&\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u000e\u0010\b\u001a\n\u0012\u0006\b\u0001\u0012\u00020\n0\t2\u0006\u0010\u000b\u001a\u00020\u0005\u001a \u0010\f\u001a\u00020\r2\u000e\u0010\b\u001a\n\u0012\u0006\b\u0001\u0012\u00020\n0\t2\u0006\u0010\u000e\u001a\u00020\u000fH\u0002\u001a(\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0006\u001a\u00020\u00072\u000e\u0010\b\u001a\n\u0012\u0006\b\u0001\u0012\u00020\n0\t2\u0006\u0010\u000b\u001a\u00020\u0005H\u0002\u001a8\u0010\u0011\u001a\u00020\u000f2\u0006\u0010\u0006\u001a\u00020\u00072\u000e\u0010\b\u001a\n\u0012\u0006\b\u0001\u0012\u00020\n0\t2\u000e\u0010\u0012\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00130\t2\u0006\u0010\u000b\u001a\u00020\u0005H\u0002\u001a\u0014\u0010\u0014\u001a\u00020\u0015*\n\u0012\u0006\b\u0001\u0012\u00020\u00130\tH\u0002\u001a\u0012\u0010\u0016\u001a\u00020\u0017*\n\u0012\u0006\b\u0001\u0012\u00020\u00130\t\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\u0003\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0018"}, d2 = {"intRangeToFqName", "Lorg/jetbrains/kotlin/name/FqName;", "longRangeToFqName", "untilFqName", "computeExplicitReceiversForInvoke", "Lorg/jetbrains/kotlin/js/translate/callTranslator/ExplicitReceivers;", "context", "Lorg/jetbrains/kotlin/js/translate/context/TranslationContext;", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "explicitReceivers", "mayBeMarkByRangeMetadata", "", "callExpression", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "translateCall", "translateFunctionCall", "inlineResolvedCall", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "expectedReceivers", "", "getReturnType", "Lorg/jetbrains/kotlin/types/KotlinType;", "js.translator"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class CallTranslatorKt {
    private static final FqName intRangeToFqName = new FqName("kotlin.Int.rangeTo");
    private static final FqName longRangeToFqName = new FqName("kotlin.Long.rangeTo");
    private static final FqName untilFqName = new FqName("kotlin.ranges.until");

    /* JADX INFO: compiled from: CallTranslator.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ExplicitReceiverKind.values().length];
            try {
                iArr[ExplicitReceiverKind.NO_EXPLICIT_RECEIVER.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ExplicitReceiverKind.DISPATCH_RECEIVER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[ExplicitReceiverKind.EXTENSION_RECEIVER.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[ExplicitReceiverKind.BOTH_RECEIVERS.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    private static final boolean expectedReceivers(ResolvedCall<? extends CallableDescriptor> resolvedCall) {
        return resolvedCall.getExplicitReceiverKind() != ExplicitReceiverKind.NO_EXPLICIT_RECEIVER;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final JsExpression translateCall(TranslationContext translationContext, ResolvedCall<? extends FunctionDescriptor> resolvedCall, ExplicitReceivers explicitReceivers) {
        ExplicitReceivers explicitReceivers2;
        if (resolvedCall instanceof VariableAsFunctionResolvedCall) {
            explicitReceivers.getExtensionReceiver();
            VariableAsFunctionResolvedCall variableAsFunctionResolvedCall = (VariableAsFunctionResolvedCall) resolvedCall;
            ResolvedCall<VariableDescriptor> variableCall = variableAsFunctionResolvedCall.getVariableCall();
            KotlinType type = ((VariableDescriptor) variableCall.getResultingDescriptor()).getType();
            Intrinsics.checkNotNull(type);
            if (FunctionTypesKt.isFunctionTypeOrSubtype(type) || FunctionTypesKt.isSuspendFunctionTypeOrSubtype(type)) {
                resolvedCall = variableCall;
            }
            if (expectedReceivers(variableCall)) {
                explicitReceivers2 = new ExplicitReceivers(CallTranslator.INSTANCE.translateGet(translationContext, variableCall, explicitReceivers.getExtensionOrDispatchReceiver()), null, 2, null);
            } else {
                JsExpression jsExpressionTranslateGet = CallTranslator.INSTANCE.translateGet(translationContext, variableCall, null);
                if (explicitReceivers.getExtensionOrDispatchReceiver() == null) {
                    explicitReceivers2 = new ExplicitReceivers(jsExpressionTranslateGet, null, 2, null);
                } else {
                    explicitReceivers2 = new ExplicitReceivers(jsExpressionTranslateGet, explicitReceivers.getExtensionOrDispatchReceiver());
                }
            }
            return translateFunctionCall(translationContext, variableAsFunctionResolvedCall.getFunctionCall(), resolvedCall, explicitReceivers2);
        }
        Call call = resolvedCall.getCall();
        Intrinsics.checkNotNullExpressionValue(call, "getCall(...)");
        if (call.getCallType() == Call.CallType.INVOKE && !CallResolverUtilKt.isInvokeCallOnVariable(call)) {
            return translateFunctionCall(translationContext, resolvedCall, resolvedCall, computeExplicitReceiversForInvoke(translationContext, resolvedCall, explicitReceivers));
        }
        return translateFunctionCall(translationContext, resolvedCall, resolvedCall, explicitReceivers);
    }

    private static final JsExpression translateFunctionCall(TranslationContext translationContext, ResolvedCall<? extends FunctionDescriptor> resolvedCall, ResolvedCall<? extends CallableDescriptor> resolvedCall2, ExplicitReceivers explicitReceivers) {
        JsExpression jsExpressionTranslateAsRangeCheck = new RangeCheckTranslator(translationContext).translateAsRangeCheck(resolvedCall, explicitReceivers);
        if (jsExpressionTranslateAsRangeCheck != null) {
            return jsExpressionTranslateAsRangeCheck;
        }
        FunctionCallInfo callInfo = CallInfoKt.getCallInfo(translationContext, resolvedCall, explicitReceivers);
        JsExpression jsExpressionTranslateFunctionCall = FunctionCallCasesKt.translateFunctionCall(callInfo);
        if (CallExpressionTranslator.shouldBeInlined(resolvedCall2.getResultingDescriptor(), translationContext)) {
            KtElement callElement = resolvedCall.getCall().getCallElement();
            Intrinsics.checkNotNullExpressionValue(callElement, "getCallElement(...)");
            InlineUtils.setInlineCallMetadata(jsExpressionTranslateFunctionCall, callElement, resolvedCall2.getResultingDescriptor(), translationContext);
        }
        if (((FunctionDescriptor) resolvedCall.getResultingDescriptor()).isSuspend()) {
            MetadataProperties.setSuspend(jsExpressionTranslateFunctionCall, true);
            jsExpressionTranslateFunctionCall.setSource(resolvedCall.getCall().getCallElement());
            JsStatement jsStatementAsSyntheticStatement = JsAstUtils.asSyntheticStatement(jsExpressionTranslateFunctionCall);
            Intrinsics.checkNotNullExpressionValue(jsStatementAsSyntheticStatement, "asSyntheticStatement(...)");
            JsStatement jsStatementConstructSuspendSafeCallIfNeeded = callInfo.constructSuspendSafeCallIfNeeded(jsStatementAsSyntheticStatement);
            List<JsStatement> statements = translationContext.getCurrentBlock().getStatements();
            Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
            statements.add(jsStatementConstructSuspendSafeCallIfNeeded);
            return UtilsKt.createCoroutineResult(translationContext, resolvedCall);
        }
        JsExpression jsExpressionConstructSafeCallIfNeeded = callInfo.constructSafeCallIfNeeded(jsExpressionTranslateFunctionCall);
        KotlinType returnType = getReturnType(resolvedCall);
        Call call = resolvedCall.getCall();
        Intrinsics.checkNotNullExpressionValue(call, "getCall(...)");
        if (CallUtilKt.isSafeCall(call)) {
            returnType = TypeUtilsKt.makeNullable(returnType);
        }
        MetadataProperties.setType(jsExpressionConstructSafeCallIfNeeded, returnType);
        mayBeMarkByRangeMetadata(resolvedCall, jsExpressionConstructSafeCallIfNeeded);
        return jsExpressionConstructSafeCallIfNeeded;
    }

    private static final void mayBeMarkByRangeMetadata(ResolvedCall<? extends FunctionDescriptor> resolvedCall, JsExpression jsExpression) {
        TypeConstructor constructor;
        ClassifierDescriptor this$0;
        FqName fqNameSafe = DescriptorUtilsKt.getFqNameSafe(resolvedCall.getResultingDescriptor());
        if (Intrinsics.areEqual(fqNameSafe, intRangeToFqName)) {
            MetadataProperties.setRange(jsExpression, new Pair(RangeType.INT, RangeKind.RANGE_TO));
            return;
        }
        if (Intrinsics.areEqual(fqNameSafe, longRangeToFqName)) {
            MetadataProperties.setRange(jsExpression, new Pair(RangeType.LONG, RangeKind.RANGE_TO));
            return;
        }
        if (Intrinsics.areEqual(fqNameSafe, untilFqName)) {
            KotlinType returnType = ((FunctionDescriptor) resolvedCall.getResultingDescriptor()).getReturnType();
            FqNameUnsafe fqNameUnsafe = (returnType == null || (constructor = returnType.getConstructor()) == null || (this$0 = constructor.getThis$0()) == null) ? null : DescriptorUtilsKt.getFqNameUnsafe(this$0);
            if (Intrinsics.areEqual(fqNameUnsafe, StandardNames.FqNames.intRange)) {
                MetadataProperties.setRange(jsExpression, new Pair(RangeType.INT, RangeKind.UNTIL));
            } else if (Intrinsics.areEqual(fqNameUnsafe, StandardNames.FqNames.longRange)) {
                MetadataProperties.setRange(jsExpression, new Pair(RangeType.LONG, RangeKind.UNTIL));
            }
        }
    }

    public static final KotlinType getReturnType(ResolvedCall<? extends CallableDescriptor> resolvedCall) {
        Intrinsics.checkNotNullParameter(resolvedCall, "<this>");
        KotlinType returnTypeForCoercion = TranslationUtils.getReturnTypeForCoercion(resolvedCall.getResultingDescriptor());
        Intrinsics.checkNotNullExpressionValue(returnTypeForCoercion, "getReturnTypeForCoercion(...)");
        return returnTypeForCoercion;
    }

    public static final ExplicitReceivers computeExplicitReceiversForInvoke(TranslationContext context, ResolvedCall<? extends FunctionDescriptor> resolvedCall, ExplicitReceivers explicitReceivers) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
        Intrinsics.checkNotNullParameter(explicitReceivers, "explicitReceivers");
        Intrinsics.checkNotNullExpressionValue(resolvedCall.getCall().getCallElement(), "getCallElement(...)");
        explicitReceivers.getExtensionReceiver();
        ReceiverValue receiverValueMo7304getDispatchReceiver = resolvedCall.getDispatchReceiver();
        ReceiverValue receiverValueMo7305getExtensionReceiver = resolvedCall.getExtensionReceiver();
        if (receiverValueMo7304getDispatchReceiver == null || receiverValueMo7305getExtensionReceiver == null || resolvedCall.getExplicitReceiverKind() == ExplicitReceiverKind.BOTH_RECEIVERS) {
            explicitReceivers.getExtensionOrDispatchReceiver();
        } else {
            explicitReceivers.getExtensionOrDispatchReceiver();
        }
        int i = WhenMappings.$EnumSwitchMapping$0[resolvedCall.getExplicitReceiverKind().ordinal()];
        if (i == 1) {
            return new ExplicitReceivers(null, null, 2, null);
        }
        if (i == 2) {
            return new ExplicitReceivers(computeExplicitReceiversForInvoke$translateReceiverAsExpression(context, receiverValueMo7304getDispatchReceiver), null, 2, null);
        }
        if (i == 3) {
            return new ExplicitReceivers(computeExplicitReceiversForInvoke$translateReceiverAsExpression(context, receiverValueMo7305getExtensionReceiver), null, 2, null);
        }
        if (i == 4) {
            return new ExplicitReceivers(computeExplicitReceiversForInvoke$translateReceiverAsExpression(context, receiverValueMo7304getDispatchReceiver), computeExplicitReceiversForInvoke$translateReceiverAsExpression(context, receiverValueMo7305getExtensionReceiver));
        }
        throw new NoWhenBranchMatchedException();
    }

    private static final JsExpression computeExplicitReceiversForInvoke$translateReceiverAsExpression(TranslationContext translationContext, ReceiverValue receiverValue) {
        ExpressionReceiver expressionReceiver = receiverValue instanceof ExpressionReceiver ? (ExpressionReceiver) receiverValue : null;
        if (expressionReceiver != null) {
            return Translation.translateAsExpression(expressionReceiver.getExpression(), translationContext);
        }
        return null;
    }
}
