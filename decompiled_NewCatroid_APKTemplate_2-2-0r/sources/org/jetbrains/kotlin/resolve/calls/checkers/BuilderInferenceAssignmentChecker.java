package org.jetbrains.kotlin.resolve.calls.checkers;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.psi.KtBinaryExpression;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtNameReferenceExpression;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.util.CallUtilKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.KotlinTypeKt;
import org.jetbrains.kotlin.types.StubTypeForBuilderInference;
import org.jetbrains.kotlin.types.expressions.BasicExpressionTypingVisitor;

/* JADX INFO: compiled from: BuilderInferenceAssignmentChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J$\u0010\u0003\u001a\u00020\u00042\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/checkers/BuilderInferenceAssignmentChecker;", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallChecker;", "()V", "check", "", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "reportOn", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "context", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallCheckerContext;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class BuilderInferenceAssignmentChecker implements CallChecker {
    public static final BuilderInferenceAssignmentChecker INSTANCE = new BuilderInferenceAssignmentChecker();

    private BuilderInferenceAssignmentChecker() {
    }

    @Override // org.jetbrains.kotlin.resolve.calls.checkers.CallChecker
    public void check(ResolvedCall<?> resolvedCall, PsiElement reportOn, CallCheckerContext context) {
        KtBinaryExpression ktBinaryExpression;
        KotlinType returnType;
        KtExpression right;
        Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
        Intrinsics.checkNotNullParameter(reportOn, "reportOn");
        Intrinsics.checkNotNullParameter(context, "context");
        CallableDescriptor resultingDescriptor = resolvedCall.getResultingDescriptor();
        if ((resultingDescriptor instanceof PropertyDescriptor) && !context.getLanguageVersionSettings().supportsFeature(LanguageFeature.NoBuilderInferenceWithoutAnnotationRestriction) && (resolvedCall.get$suspendPropertyDescriptor().getReturnType() instanceof StubTypeForBuilderInference)) {
            KtElement callElement = resolvedCall.getCall().getCallElement();
            Intrinsics.checkNotNullExpressionValue(callElement, "getCallElement(...)");
            if ((callElement instanceof KtNameReferenceExpression) && (ktBinaryExpression = (KtBinaryExpression) PsiTreeUtil.getParentOfType(callElement, KtBinaryExpression.class, true)) != null && Intrinsics.areEqual(ktBinaryExpression.getOperationToken(), KtTokens.EQ) && BasicExpressionTypingVisitor.isLValue((KtSimpleNameExpression) callElement, ktBinaryExpression) && (returnType = ((PropertyDescriptor) resultingDescriptor).getReturnType()) != null) {
                if (KotlinTypeKt.isError(returnType)) {
                    returnType = null;
                }
                if (returnType == null || (right = ktBinaryExpression.getRight()) == null) {
                    return;
                }
                BindingContext context2 = context.getTrace().getContext();
                Intrinsics.checkNotNullExpressionValue(context2, "getBindingContext(...)");
                KotlinType type = CallUtilKt.getType(right, context2);
                if (type == null || AssignmentCheckUtilsKt.isAssignmentCorrectWithDataFlowInfo(returnType, right, type, context)) {
                    return;
                }
                context.getTrace().report(Errors.TYPE_MISMATCH.on(right, returnType, type));
            }
        }
    }
}
