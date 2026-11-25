package org.jetbrains.kotlin.resolve.calls.checkers;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.diagnostics.Diagnostic;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory1;
import org.jetbrains.kotlin.diagnostics.DiagnosticUtilsKt;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtBinaryExpression;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.checkers.ResultClassInReturnTypeCheckerKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.expressions.ControlStructureTypingUtils;

/* JADX INFO: compiled from: ResultTypeWithNullableOperatorsChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J$\u0010\u0003\u001a\u00020\u00042\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/checkers/ResultTypeWithNullableOperatorsChecker;", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallChecker;", "()V", "check", "", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "reportOn", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "context", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallCheckerContext;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ResultTypeWithNullableOperatorsChecker implements CallChecker {
    @Override // org.jetbrains.kotlin.resolve.calls.checkers.CallChecker
    public void check(ResolvedCall<?> resolvedCall, PsiElement reportOn, CallCheckerContext context) {
        KtExpression left;
        KotlinType type;
        KtExpression right;
        KotlinType type2;
        Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
        Intrinsics.checkNotNullParameter(reportOn, "reportOn");
        Intrinsics.checkNotNullParameter(context, "context");
        if (context.getLanguageVersionSettings().supportsFeature(LanguageFeature.AllowNullOperatorsForResult) || context.getLanguageVersionSettings().supportsFeature(LanguageFeature.AllowNullOperatorsForResultAndResultReturnTypeByDefault)) {
            return;
        }
        Name name2 = resolvedCall.getResultingDescriptor().getShortName();
        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
        ASTNode callOperationNode = resolvedCall.getCall().getCallOperationNode();
        if (Intrinsics.areEqual(callOperationNode != null ? callOperationNode.getElementType() : null, KtTokens.SAFE_ACCESS)) {
            CallableDescriptor resultingDescriptor = resolvedCall.getResultingDescriptor();
            ReceiverParameterDescriptor extensionReceiverParameter = resultingDescriptor.getExtensionReceiverParameter();
            if (extensionReceiverParameter == null && (extensionReceiverParameter = resultingDescriptor.mo6753getDispatchReceiverParameter()) == null) {
                return;
            }
            KotlinType type3 = extensionReceiverParameter.getType();
            Intrinsics.checkNotNullExpressionValue(type3, "getType(...)");
            if (ResultClassInReturnTypeCheckerKt.isResultType(type3)) {
                BindingTrace trace = context.getTrace();
                DiagnosticFactory1<PsiElement, String> diagnosticFactory1 = Errors.RESULT_CLASS_WITH_NULLABLE_OPERATOR;
                Intrinsics.checkNotNull(callOperationNode);
                trace.report(diagnosticFactory1.on(callOperationNode.getPsi(), "?."));
                return;
            }
            return;
        }
        if (Intrinsics.areEqual(name2, ControlStructureTypingUtils.ResolveConstruct.EXCL_EXCL.getSpecialFunctionName())) {
            KotlinType returnType = resolvedCall.getResultingDescriptor().getReturnType();
            if (returnType == null || !ResultClassInReturnTypeCheckerKt.isResultType(returnType)) {
                return;
            }
            context.getTrace().report(Errors.RESULT_CLASS_WITH_NULLABLE_OPERATOR.on(reportOn, "!!"));
            return;
        }
        if (Intrinsics.areEqual(name2, ControlStructureTypingUtils.ResolveConstruct.ELVIS.getSpecialFunctionName())) {
            KtElement callElement = resolvedCall.getCall().getCallElement();
            KtBinaryExpression ktBinaryExpression = callElement instanceof KtBinaryExpression ? (KtBinaryExpression) callElement : null;
            if (ktBinaryExpression == null || (left = ktBinaryExpression.getLeft()) == null || (type = context.getTrace().getType(left)) == null) {
                return;
            }
            if (ResultClassInReturnTypeCheckerKt.isResultType(type)) {
                BindingTrace trace2 = context.getTrace();
                Diagnostic diagnosticOn = Errors.RESULT_CLASS_WITH_NULLABLE_OPERATOR.on(reportOn, "?:");
                Intrinsics.checkNotNullExpressionValue(diagnosticOn, "on(...)");
                DiagnosticUtilsKt.reportDiagnosticOnce(trace2, diagnosticOn);
            }
            if (!(left instanceof KtBinaryExpression) || (right = ((KtBinaryExpression) left).getRight()) == null || (type2 = context.getTrace().getType(right)) == null || !ResultClassInReturnTypeCheckerKt.isResultType(type2)) {
                return;
            }
            BindingTrace trace3 = context.getTrace();
            Diagnostic diagnosticOn2 = Errors.RESULT_CLASS_WITH_NULLABLE_OPERATOR.on(reportOn, "?:");
            Intrinsics.checkNotNullExpressionValue(diagnosticOn2, "on(...)");
            DiagnosticUtilsKt.reportDiagnosticOnce(trace3, diagnosticOn2);
        }
    }
}
