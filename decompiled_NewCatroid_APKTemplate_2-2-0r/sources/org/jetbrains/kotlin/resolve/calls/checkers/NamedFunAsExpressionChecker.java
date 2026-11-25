package org.jetbrains.kotlin.resolve.calls.checkers;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.diagnostics.Diagnostic;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory0;
import org.jetbrains.kotlin.diagnostics.DiagnosticUtilsKt;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtNamedFunction;
import org.jetbrains.kotlin.psi.KtPsiUtil;
import org.jetbrains.kotlin.psi.ValueArgument;
import org.jetbrains.kotlin.psi.psiUtil.KtPsiUtilKt;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.calls.model.ExpressionValueArgument;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;

/* JADX INFO: compiled from: NamedFunAsExpressionChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J$\u0010\u0003\u001a\u00020\u00042\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/checkers/NamedFunAsExpressionChecker;", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallChecker;", "()V", "check", "", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "reportOn", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "context", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallCheckerContext;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NamedFunAsExpressionChecker implements CallChecker {
    public static final NamedFunAsExpressionChecker INSTANCE = new NamedFunAsExpressionChecker();

    private NamedFunAsExpressionChecker() {
    }

    @Override // org.jetbrains.kotlin.resolve.calls.checkers.CallChecker
    public void check(ResolvedCall<?> resolvedCall, PsiElement reportOn, CallCheckerContext context) {
        Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
        Intrinsics.checkNotNullParameter(reportOn, "reportOn");
        Intrinsics.checkNotNullParameter(context, "context");
        if (context.getLanguageVersionSettings().supportsFeature(LanguageFeature.NewInference)) {
            Iterator it = CollectionsKt.filterIsInstance(resolvedCall.getValueArguments().values(), ExpressionValueArgument.class).iterator();
            while (it.hasNext()) {
                ValueArgument valueArgument = ((ExpressionValueArgument) it.next()).getValueArgument();
                KtExpression ktExpressionDeparenthesize = KtPsiUtil.deparenthesize(valueArgument != null ? valueArgument.getArgumentExpression() : null);
                KtNamedFunction ktNamedFunction = ktExpressionDeparenthesize instanceof KtNamedFunction ? (KtNamedFunction) ktExpressionDeparenthesize : null;
                if (ktNamedFunction != null && !KtPsiUtilKt.isFunctionalExpression(ktNamedFunction)) {
                    BindingTrace trace = context.getTrace();
                    DiagnosticFactory0<PsiElement> diagnosticFactory0 = Errors.ANONYMOUS_FUNCTION_WITH_NAME;
                    PsiElement nameIdentifier = ktNamedFunction.mo7392getNameIdentifier();
                    Intrinsics.checkNotNull(nameIdentifier);
                    Diagnostic diagnosticOn = diagnosticFactory0.on(nameIdentifier);
                    Intrinsics.checkNotNullExpressionValue(diagnosticOn, "on(...)");
                    DiagnosticUtilsKt.reportDiagnosticOnce(trace, diagnosticOn);
                }
            }
        }
    }
}
