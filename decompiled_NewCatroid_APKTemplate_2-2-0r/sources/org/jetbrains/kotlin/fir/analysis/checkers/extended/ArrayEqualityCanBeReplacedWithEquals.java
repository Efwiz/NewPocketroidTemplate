package org.jetbrains.kotlin.fir.analysis.checkers.extended;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.checkers.expression.FirExpressionChecker;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.expressions.FirCall;
import org.jetbrains.kotlin.fir.expressions.FirEqualityOperatorCall;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirOperation;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.name.StandardClassIds;

/* JADX INFO: compiled from: ArrayEqualityCanBeReplacedWithEquals.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/extended/ArrayEqualityCanBeReplacedWithEquals;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirExpressionChecker;", "Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirBasicExpressionChecker;", "()V", "check", "", "expression", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ArrayEqualityCanBeReplacedWithEquals extends FirExpressionChecker<FirStatement> {
    public static final ArrayEqualityCanBeReplacedWithEquals INSTANCE = new ArrayEqualityCanBeReplacedWithEquals();

    private ArrayEqualityCanBeReplacedWithEquals() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.FirExpressionChecker
    public void check(FirStatement expression, CheckerContext context, DiagnosticReporter reporter) {
        FirExpression firExpression;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        if (expression instanceof FirEqualityOperatorCall) {
            FirEqualityOperatorCall firEqualityOperatorCall = (FirEqualityOperatorCall) expression;
            if (firEqualityOperatorCall.getOperation() == FirOperation.EQ || firEqualityOperatorCall.getOperation() == FirOperation.NOT_EQ) {
                List<FirExpression> arguments = ((FirCall) expression).getArgumentList().getArguments();
                FirExpression firExpression2 = (FirExpression) CollectionsKt.getOrNull(arguments, 0);
                if (firExpression2 != null && (firExpression = (FirExpression) CollectionsKt.getOrNull(arguments, 1)) != null && Intrinsics.areEqual(ConeTypeUtilsKt.getClassId(FirTypeUtilsKt.getConeType(firExpression2.getTypeRef())), StandardClassIds.INSTANCE.getArray()) && Intrinsics.areEqual(ConeTypeUtilsKt.getClassId(FirTypeUtilsKt.getConeType(firExpression.getTypeRef())), StandardClassIds.INSTANCE.getArray())) {
                    KtDiagnosticReportHelpersKt.reportOn$default(reporter, expression.getSource(), FirErrors.INSTANCE.getARRAY_EQUALITY_OPERATOR_CAN_BE_REPLACED_WITH_EQUALS(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                }
            }
        }
    }
}
