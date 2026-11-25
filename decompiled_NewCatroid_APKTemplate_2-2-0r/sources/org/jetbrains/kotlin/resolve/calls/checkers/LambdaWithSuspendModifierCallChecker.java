package org.jetbrains.kotlin.resolve.calls.checkers;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.psi.Call;
import org.jetbrains.kotlin.psi.KtBinaryExpression;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtLambdaExpression;
import org.jetbrains.kotlin.psi.KtNamedFunction;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;
import org.jetbrains.kotlin.psi.KtValueArgumentList;
import org.jetbrains.kotlin.psi.LambdaArgument;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.model.VariableAsFunctionResolvedCall;
import org.jetbrains.kotlin.resolve.calls.util.CallResolverUtilKt;
import org.jetbrains.kotlin.resolve.calls.util.CallUtilKt;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.serialization.deserialization.SuspendFunctionTypeUtilKt;

/* JADX INFO: compiled from: LambdaWithSuspendModifierCallChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J$\u0010\u0003\u001a\u00020\u00042\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\f\u0010\u000b\u001a\u00020\f*\u00020\rH\u0002J\f\u0010\u000e\u001a\u00020\f*\u00020\rH\u0002J\f\u0010\u000f\u001a\u00020\f*\u00020\rH\u0002J\f\u0010\u0010\u001a\u00020\f*\u00020\rH\u0002J\u000e\u0010\u0011\u001a\u0004\u0018\u00010\u0012*\u00020\rH\u0002¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/checkers/LambdaWithSuspendModifierCallChecker;", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallChecker;", "()V", "check", "", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "reportOn", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "context", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallCheckerContext;", "hasFormOfSuspendModifierForLambdaOrFun", "", "Lorg/jetbrains/kotlin/psi/Call;", "hasNoArgumentListButDanglingLambdas", "isInfixWithRightFun", "isInfixWithRightLambda", "referencedName", "", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LambdaWithSuspendModifierCallChecker implements CallChecker {
    public static final LambdaWithSuspendModifierCallChecker INSTANCE = new LambdaWithSuspendModifierCallChecker();

    private LambdaWithSuspendModifierCallChecker() {
    }

    @Override // org.jetbrains.kotlin.resolve.calls.checkers.CallChecker
    public void check(ResolvedCall<?> resolvedCall, PsiElement reportOn, CallCheckerContext context) {
        ResolvedCall<VariableDescriptor> variableCall;
        Call call;
        Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
        Intrinsics.checkNotNullParameter(reportOn, "reportOn");
        Intrinsics.checkNotNullParameter(context, "context");
        CallableDescriptor callableDescriptor = resolvedCall.get$suspendPropertyDescriptor();
        Call call2 = resolvedCall.getCall();
        Intrinsics.checkNotNullExpressionValue(call2, "getCall(...)");
        String strReferencedName = referencedName(call2);
        String strReferencedName2 = null;
        VariableAsFunctionResolvedCall variableAsFunctionResolvedCall = resolvedCall instanceof VariableAsFunctionResolvedCall ? (VariableAsFunctionResolvedCall) resolvedCall : null;
        if (variableAsFunctionResolvedCall != null && (variableCall = variableAsFunctionResolvedCall.getVariableCall()) != null && (call = variableCall.getCall()) != null) {
            strReferencedName2 = referencedName(call);
        }
        if (Intrinsics.areEqual(strReferencedName, "suspend") || Intrinsics.areEqual(strReferencedName2, "suspend") || Intrinsics.areEqual(callableDescriptor.getShortName().asString(), "suspend")) {
            Intrinsics.checkNotNull(callableDescriptor);
            if (Intrinsics.areEqual(DescriptorUtilsKt.fqNameOrNull(callableDescriptor), SuspendFunctionTypeUtilKt.KOTLIN_SUSPEND_BUILT_IN_FUNCTION_FQ_NAME)) {
                if (Intrinsics.areEqual(strReferencedName, "suspend") && hasFormOfSuspendModifierForLambdaOrFun(call2) && call2.getExplicitReceiver() == null) {
                    return;
                }
                context.getTrace().report(Errors.NON_MODIFIER_FORM_FOR_BUILT_IN_SUSPEND.on(reportOn));
                return;
            }
            if ((Intrinsics.areEqual(strReferencedName, "suspend") || Intrinsics.areEqual(strReferencedName2, "suspend")) && hasFormOfSuspendModifierForLambdaOrFun(call2)) {
                if (hasNoArgumentListButDanglingLambdas(call2) || isInfixWithRightLambda(call2)) {
                    context.getTrace().report(Errors.MODIFIER_FORM_FOR_NON_BUILT_IN_SUSPEND.on(reportOn));
                } else {
                    if (!isInfixWithRightFun(call2)) {
                        throw new IllegalArgumentException("Failed requirement.".toString());
                    }
                    context.getTrace().report(Errors.MODIFIER_FORM_FOR_NON_BUILT_IN_SUSPEND_FUN.on(context.getLanguageVersionSettings(), reportOn));
                }
            }
        }
    }

    private final boolean hasFormOfSuspendModifierForLambdaOrFun(Call call) {
        return !CallUtilKt.isCallableReference(call) && call.getTypeArguments().isEmpty() && (hasNoArgumentListButDanglingLambdas(call) || isInfixWithRightLambda(call) || isInfixWithRightFun(call));
    }

    private final String referencedName(Call call) {
        KtExpression calleeExpression = call.getCalleeExpression();
        KtSimpleNameExpression ktSimpleNameExpression = calleeExpression instanceof KtSimpleNameExpression ? (KtSimpleNameExpression) calleeExpression : null;
        if (ktSimpleNameExpression != null) {
            return ktSimpleNameExpression.getReferencedName();
        }
        return null;
    }

    private final boolean hasNoArgumentListButDanglingLambdas(Call call) {
        KtValueArgumentList valueArgumentList = call.getValueArgumentList();
        if ((valueArgumentList != null ? valueArgumentList.getLeftParenthesis() : null) == null) {
            List<? extends LambdaArgument> functionLiteralArguments = call.getFunctionLiteralArguments();
            Intrinsics.checkNotNullExpressionValue(functionLiteralArguments, "getFunctionLiteralArguments(...)");
            if (!functionLiteralArguments.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private final boolean isInfixWithRightLambda(Call call) {
        if (CallResolverUtilKt.isInfixCall(call)) {
            KtElement callElement = call.getCallElement();
            KtBinaryExpression ktBinaryExpression = callElement instanceof KtBinaryExpression ? (KtBinaryExpression) callElement : null;
            if ((ktBinaryExpression != null ? ktBinaryExpression.getRight() : null) instanceof KtLambdaExpression) {
                return true;
            }
        }
        return false;
    }

    private final boolean isInfixWithRightFun(Call call) {
        if (CallResolverUtilKt.isInfixCall(call)) {
            KtElement callElement = call.getCallElement();
            KtBinaryExpression ktBinaryExpression = callElement instanceof KtBinaryExpression ? (KtBinaryExpression) callElement : null;
            if ((ktBinaryExpression != null ? ktBinaryExpression.getRight() : null) instanceof KtNamedFunction) {
                return true;
            }
        }
        return false;
    }
}
