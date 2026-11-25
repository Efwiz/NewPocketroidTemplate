package org.jetbrains.kotlin.fir.analysis.checkers.expression;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticFactory2;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.expressions.FirFunctionCall;
import org.jetbrains.kotlin.fir.expressions.FirFunctionCallOrigin;
import org.jetbrains.kotlin.fir.expressions.FirOperation;
import org.jetbrains.kotlin.fir.expressions.FirOperationNameConventions;
import org.jetbrains.kotlin.fir.references.FirReferenceUtilsKt;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.types.ConeBuiltinTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: FirAssignmentOperatorCallChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirAssignmentOperatorCallChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirExpressionChecker;", "Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCall;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirFunctionCallChecker;", "()V", "check", "", "expression", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirAssignmentOperatorCallChecker extends FirExpressionChecker<FirFunctionCall> {
    public static final FirAssignmentOperatorCallChecker INSTANCE = new FirAssignmentOperatorCallChecker();

    private FirAssignmentOperatorCallChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.FirExpressionChecker
    public void check(FirFunctionCall expression, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        FirNamedFunctionSymbol resolvedNamedFunctionSymbol$default = FirReferenceUtilsKt.toResolvedNamedFunctionSymbol$default(expression.getCalleeReference(), false, 1, null);
        if (resolvedNamedFunctionSymbol$default == null) {
            return;
        }
        Name name2 = resolvedNamedFunctionSymbol$default.getName();
        if (expression.getOrigin() == FirFunctionCallOrigin.Operator && FirOperationNameConventions.INSTANCE.getASSIGNMENT_NAMES().containsKey(name2) && !ConeBuiltinTypeUtilsKt.isUnit(FirTypeUtilsKt.getConeType(expression.getTypeRef()))) {
            KtSourceElement source = expression.getSource();
            KtDiagnosticFactory2<FirNamedFunctionSymbol, String> assignment_operator_should_return_unit = FirErrors.INSTANCE.getASSIGNMENT_OPERATOR_SHOULD_RETURN_UNIT();
            FirOperation firOperation = FirOperationNameConventions.INSTANCE.getASSIGNMENT_NAMES().get(name2);
            Intrinsics.checkNotNull(firOperation);
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, assignment_operator_should_return_unit, resolvedNamedFunctionSymbol$default, firOperation.getOperator(), context, (AbstractSourceElementPositioningStrategy) null, 32, (Object) null);
        }
    }
}
