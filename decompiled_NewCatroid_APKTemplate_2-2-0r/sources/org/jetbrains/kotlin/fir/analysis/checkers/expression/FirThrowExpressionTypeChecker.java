package org.jetbrains.kotlin.fir.analysis.checkers.expression;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.AbstractKtSourceElement;
import org.jetbrains.kotlin.diagnostics.DiagnosticContext;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticFactory3;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.expressions.FirThrowExpression;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.InferenceUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeComponentsKt;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;

/* JADX INFO: compiled from: FirThrowExpressionTypeChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirThrowExpressionTypeChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirExpressionChecker;", "Lorg/jetbrains/kotlin/fir/expressions/FirThrowExpression;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirThrowExpressionChecker;", "()V", "check", "", "expression", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirThrowExpressionTypeChecker extends FirExpressionChecker<FirThrowExpression> {
    public static final FirThrowExpressionTypeChecker INSTANCE = new FirThrowExpressionTypeChecker();

    private FirThrowExpressionTypeChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.FirExpressionChecker
    public void check(FirThrowExpression expression, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        ConeKotlinType coneType = FirTypeUtilsKt.getConeType(context.getSession().getBuiltinTypes().getThrowableType());
        ConeKotlinType coneType2 = FirTypeUtilsKt.getConeType(expression.getException().getTypeRef());
        if (TypeUtilsKt.isSubtypeOf$default(coneType2, coneType, context.getSession(), false, 4, null)) {
            return;
        }
        KtDiagnosticReportHelpersKt.reportOn(reporter, (AbstractKtSourceElement) expression.getException().getSource(), (KtDiagnosticFactory3<ConeKotlinType, ConeKotlinType, Boolean>) ((KtDiagnosticFactory3<Object, Object, Object>) FirErrors.INSTANCE.getTYPE_MISMATCH()), coneType, coneType2, Boolean.valueOf(InferenceUtilsKt.isTypeMismatchDueToNullability(TypeComponentsKt.getTypeContext(context.getSession()), coneType, coneType2)), (DiagnosticContext) context, (64 & 64) != 0 ? null : null);
    }
}
