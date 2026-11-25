package org.jetbrains.kotlin.fir.analysis.checkers.expression;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirFunctionCall;
import org.jetbrains.kotlin.fir.expressions.FirSpreadArgumentExpression;
import org.jetbrains.kotlin.fir.expressions.FirVarargArgumentsExpression;
import org.jetbrains.kotlin.fir.types.ConeFlexibleType;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;

/* JADX INFO: compiled from: FirSpreadOfNullableChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirSpreadOfNullableChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirExpressionChecker;", "Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCall;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirFunctionCallChecker;", "()V", "check", "", "expression", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirSpreadOfNullableChecker extends FirExpressionChecker<FirFunctionCall> {
    public static final FirSpreadOfNullableChecker INSTANCE = new FirSpreadOfNullableChecker();

    private FirSpreadOfNullableChecker() {
    }

    private static final void check$checkAndReport(DiagnosticReporter diagnosticReporter, CheckerContext checkerContext, FirExpression firExpression, KtSourceElement ktSourceElement) {
        FirTypeRef typeRef = firExpression.getTypeRef();
        if ((firExpression instanceof FirSpreadArgumentExpression) && !(FirTypeUtilsKt.getConeType(typeRef) instanceof ConeFlexibleType) && FirTypeUtilsKt.getCanBeNull(typeRef)) {
            KtDiagnosticReportHelpersKt.reportOn$default(diagnosticReporter, ktSourceElement, FirErrors.INSTANCE.getSPREAD_OF_NULLABLE(), checkerContext, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
        }
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.FirExpressionChecker
    public void check(FirFunctionCall expression, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        for (FirExpression firExpression : expression.getArgumentList().getArguments()) {
            if (firExpression instanceof FirVarargArgumentsExpression) {
                Iterator<FirExpression> it = ((FirVarargArgumentsExpression) firExpression).getArguments().iterator();
                while (it.hasNext()) {
                    check$checkAndReport(reporter, context, it.next(), firExpression.getSource());
                }
            } else {
                check$checkAndReport(reporter, context, firExpression, firExpression.getSource());
            }
        }
    }
}
