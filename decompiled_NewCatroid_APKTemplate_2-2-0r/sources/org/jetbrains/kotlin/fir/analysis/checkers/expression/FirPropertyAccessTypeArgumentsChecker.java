package org.jetbrains.kotlin.fir.analysis.checkers.expression;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.expressions.FirPropertyAccessExpression;
import org.jetbrains.kotlin.fir.references.FirErrorNamedReference;
import org.jetbrains.kotlin.fir.types.FirTypeProjection;

/* JADX INFO: compiled from: FirPropertyAccessTypeArgumentsChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirPropertyAccessTypeArgumentsChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirExpressionChecker;", "Lorg/jetbrains/kotlin/fir/expressions/FirPropertyAccessExpression;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirPropertyAccessExpressionChecker;", "()V", "check", "", "expression", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirPropertyAccessTypeArgumentsChecker extends FirExpressionChecker<FirPropertyAccessExpression> {
    public static final FirPropertyAccessTypeArgumentsChecker INSTANCE = new FirPropertyAccessTypeArgumentsChecker();

    private FirPropertyAccessTypeArgumentsChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.FirExpressionChecker
    public void check(FirPropertyAccessExpression expression, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        if (expression.getCalleeReference() instanceof FirErrorNamedReference) {
            return;
        }
        List<FirTypeProjection> typeArguments = expression.getTypeArguments();
        if ((typeArguments instanceof Collection) && typeArguments.isEmpty()) {
            return;
        }
        Iterator<T> it = typeArguments.iterator();
        while (it.hasNext()) {
            if (((FirTypeProjection) it.next()).getSource() != null) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, expression.getSource(), FirErrors.INSTANCE.getEXPLICIT_TYPE_ARGUMENTS_IN_PROPERTY_ACCESS(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                return;
            }
        }
    }
}
