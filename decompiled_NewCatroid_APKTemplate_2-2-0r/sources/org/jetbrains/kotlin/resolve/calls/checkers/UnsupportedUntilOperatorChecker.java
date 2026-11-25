package org.jetbrains.kotlin.resolve.calls.checkers;

import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtOperationReferenceExpression;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;

/* JADX INFO: compiled from: UnsupportedUntilOperatorChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J$\u0010\u0003\u001a\u00020\u00042\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/checkers/UnsupportedUntilOperatorChecker;", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallChecker;", "()V", "check", "", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "reportOn", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "context", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallCheckerContext;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class UnsupportedUntilOperatorChecker implements CallChecker {
    public static final UnsupportedUntilOperatorChecker INSTANCE = new UnsupportedUntilOperatorChecker();

    private UnsupportedUntilOperatorChecker() {
    }

    @Override // org.jetbrains.kotlin.resolve.calls.checkers.CallChecker
    public void check(ResolvedCall<?> resolvedCall, PsiElement reportOn, CallCheckerContext context) {
        Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
        Intrinsics.checkNotNullParameter(reportOn, "reportOn");
        Intrinsics.checkNotNullParameter(context, "context");
        KtExpression calleeExpression = resolvedCall.getCall().getCalleeExpression();
        KtOperationReferenceExpression ktOperationReferenceExpression = calleeExpression instanceof KtOperationReferenceExpression ? (KtOperationReferenceExpression) calleeExpression : null;
        if (ktOperationReferenceExpression == null) {
            return;
        }
        boolean zSupportsFeature = context.getLanguageVersionSettings().supportsFeature(LanguageFeature.RangeUntilOperator);
        if (!Intrinsics.areEqual(ktOperationReferenceExpression.getOperationSignTokenType(), KtTokens.RANGE_UNTIL) || zSupportsFeature) {
            return;
        }
        context.getTrace().report(Errors.UNSUPPORTED_FEATURE.on(reportOn, TuplesKt.to(LanguageFeature.RangeUntilOperator, context.getLanguageVersionSettings())));
    }
}
