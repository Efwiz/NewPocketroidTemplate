package org.jetbrains.kotlin.fir.analysis.checkers.extended;

import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.checkers.expression.FirExpressionChecker;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.expressions.FirConstExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirFunctionCall;

/* JADX INFO: compiled from: EmptyRangeChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u00022\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016R\u001a\u0010\u0005\u001a\u0004\u0018\u00010\u0006*\u00020\u00028BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u0004\u0018\u00010\u0006*\u00020\u00028BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\b¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/extended/EmptyRangeChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirExpressionChecker;", "Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCall;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirFunctionCallChecker;", "()V", "rangeLeft", "", "getRangeLeft", "(Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCall;)Ljava/lang/Long;", "rangeRight", "getRangeRight", "check", "", "expression", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class EmptyRangeChecker extends FirExpressionChecker<FirFunctionCall> {
    public static final EmptyRangeChecker INSTANCE = new EmptyRangeChecker();

    private EmptyRangeChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.FirExpressionChecker
    public void check(FirFunctionCall expression, CheckerContext context, DiagnosticReporter reporter) {
        Long rangeLeft;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        KtSourceElement source = expression.getSource();
        if (((source != null ? source.getKind() : null) instanceof KtFakeSourceElementKind) || (rangeLeft = getRangeLeft(expression)) == null) {
            return;
        }
        long jLongValue = rangeLeft.longValue();
        Long rangeRight = getRangeRight(expression);
        if (rangeRight != null) {
            long jLongValue2 = rangeRight.longValue();
            String strAsString = expression.getCalleeReference().getName().asString();
            int iHashCode = strAsString.hashCode();
            if (iHashCode != -1325887811) {
                if (iHashCode != 111443806) {
                    if (iHashCode != 977987736 || !strAsString.equals("rangeTo") || jLongValue <= jLongValue2) {
                        return;
                    }
                } else if (!strAsString.equals("until") || jLongValue < jLongValue2) {
                    return;
                }
            } else if (!strAsString.equals("downTo") || jLongValue2 <= jLongValue) {
                return;
            }
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, expression.getSource(), FirErrors.INSTANCE.getEMPTY_RANGE(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
        }
    }

    private final Long getRangeLeft(FirFunctionCall firFunctionCall) {
        FirExpression explicitReceiver = firFunctionCall.getExplicitReceiver();
        FirConstExpression firConstExpression = explicitReceiver instanceof FirConstExpression ? (FirConstExpression) explicitReceiver : null;
        Object value = firConstExpression != null ? firConstExpression.getValue() : null;
        if (value instanceof Long) {
            return (Long) value;
        }
        return null;
    }

    private final Long getRangeRight(FirFunctionCall firFunctionCall) {
        Object orNull = CollectionsKt.getOrNull(firFunctionCall.getArgumentList().getArguments(), 0);
        FirConstExpression firConstExpression = orNull instanceof FirConstExpression ? (FirConstExpression) orNull : null;
        Object value = firConstExpression != null ? firConstExpression.getValue() : null;
        if (value instanceof Long) {
            return (Long) value;
        }
        return null;
    }
}
