package org.jetbrains.kotlin.resolve.calls.checkers;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.diagnostics.Diagnostic;
import org.jetbrains.kotlin.diagnostics.DiagnosticUtilsKt;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.psi.KtBinaryExpression;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtPsiUtil;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.calls.inference.model.TypeVariableTypeConstructor;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.smartcasts.Nullability;
import org.jetbrains.kotlin.types.FlexibleTypesKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.KotlinTypeKt;
import org.jetbrains.kotlin.types.TypeUtils;
import org.jetbrains.kotlin.types.UnwrappedType;
import org.jetbrains.kotlin.types.expressions.ControlStructureTypingUtils;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: UselessElvisCallChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J$\u0010\u0003\u001a\u00020\u00042\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/checkers/UselessElvisCallChecker;", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallChecker;", "()V", "check", "", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "reportOn", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "context", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallCheckerContext;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class UselessElvisCallChecker implements CallChecker {
    @Override // org.jetbrains.kotlin.resolve.calls.checkers.CallChecker
    public void check(ResolvedCall<?> resolvedCall, PsiElement reportOn, CallCheckerContext context) {
        KtExpression left;
        KtExpression right;
        KotlinType type;
        Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
        Intrinsics.checkNotNullParameter(reportOn, "reportOn");
        Intrinsics.checkNotNullParameter(context, "context");
        if (Intrinsics.areEqual(resolvedCall.getResultingDescriptor().getShortName(), ControlStructureTypingUtils.ResolveConstruct.ELVIS.getSpecialFunctionName())) {
            KtElement callElement = resolvedCall.getCall().getCallElement();
            KtBinaryExpression ktBinaryExpression = callElement instanceof KtBinaryExpression ? (KtBinaryExpression) callElement : null;
            if (ktBinaryExpression == null || (left = ktBinaryExpression.getLeft()) == null || (right = ktBinaryExpression.getRight()) == null || (type = context.getTrace().getType(left)) == null || KotlinTypeKt.isError(type) || TypeUtilsKt.contains(type, new Function1<UnwrappedType, Boolean>() { // from class: org.jetbrains.kotlin.resolve.calls.checkers.UselessElvisCallChecker.check.1
                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(UnwrappedType it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return Boolean.valueOf(it.getConstructor() instanceof TypeVariableTypeConstructor);
                }
            })) {
                return;
            }
            if (!TypeUtils.isNullableType(type)) {
                BindingTrace trace = context.getTrace();
                Diagnostic diagnosticOn = Errors.USELESS_ELVIS.on(ktBinaryExpression, type);
                Intrinsics.checkNotNullExpressionValue(diagnosticOn, "on(...)");
                DiagnosticUtilsKt.reportDiagnosticOnce(trace, diagnosticOn);
                return;
            }
            if (context.getDataFlowInfo().getStableNullability(context.getDataFlowValueFactory().createDataFlowValue(left, type, context.getResolutionContext())) == Nullability.NOT_NULL) {
                BindingTrace trace2 = context.getTrace();
                Diagnostic diagnosticOn2 = Errors.USELESS_ELVIS.on(ktBinaryExpression, type);
                Intrinsics.checkNotNullExpressionValue(diagnosticOn2, "on(...)");
                DiagnosticUtilsKt.reportDiagnosticOnce(trace2, diagnosticOn2);
                return;
            }
            if (!KtPsiUtil.isNullConstant(right) || FlexibleTypesKt.isNullabilityFlexible(type)) {
                return;
            }
            BindingTrace trace3 = context.getTrace();
            Diagnostic diagnosticOn3 = Errors.USELESS_ELVIS_RIGHT_IS_NULL.on(ktBinaryExpression);
            Intrinsics.checkNotNullExpressionValue(diagnosticOn3, "on(...)");
            DiagnosticUtilsKt.reportDiagnosticOnce(trace3, diagnosticOn3);
        }
    }
}
