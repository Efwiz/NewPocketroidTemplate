package org.jetbrains.kotlin.fir.analysis.checkers.expression;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtRealSourceElementKind;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.KtSourceElementKind;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpressionUtilKt;
import org.jetbrains.kotlin.fir.expressions.FirFunctionCall;
import org.jetbrains.kotlin.fir.expressions.FirPropertyAccessExpression;
import org.jetbrains.kotlin.fir.references.FirErrorNamedReference;
import org.jetbrains.kotlin.fir.references.FirNamedReference;
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConePropertyAsOperator;
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConeUnresolvedNameError;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.util.OperatorNameConventions;

/* JADX INFO: compiled from: FirConventionFunctionCallChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J(\u0010\f\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u00022\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0002¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirConventionFunctionCallChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirExpressionChecker;", "Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCall;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirFunctionCallChecker;", "()V", "check", "", "expression", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkPropertyAsOperator", "callExpression", "receiver", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirConventionFunctionCallChecker extends FirExpressionChecker<FirFunctionCall> {
    public static final FirConventionFunctionCallChecker INSTANCE = new FirConventionFunctionCallChecker();

    private FirConventionFunctionCallChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.FirExpressionChecker
    public void check(FirFunctionCall expression, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        checkPropertyAsOperator(expression, expression.getDispatchReceiver(), context, reporter);
        checkPropertyAsOperator(expression, expression.getExtensionReceiver(), context, reporter);
        FirNamedReference calleeReference = expression.getCalleeReference();
        FirErrorNamedReference firErrorNamedReference = calleeReference instanceof FirErrorNamedReference ? (FirErrorNamedReference) calleeReference : null;
        if (firErrorNamedReference == null) {
            return;
        }
        ConeDiagnostic diagnostic = firErrorNamedReference.getDiagnostic();
        ConeUnresolvedNameError coneUnresolvedNameError = diagnostic instanceof ConeUnresolvedNameError ? (ConeUnresolvedNameError) diagnostic : null;
        if (coneUnresolvedNameError == null) {
            return;
        }
        KtSourceElement source = expression.getCalleeReference().getSource();
        if (Intrinsics.areEqual(source != null ? source.getKind() : null, KtFakeSourceElementKind.ArrayAccessNameReference.INSTANCE)) {
            Name name2 = coneUnresolvedNameError.getName();
            if (Intrinsics.areEqual(name2, OperatorNameConventions.GET)) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, firErrorNamedReference.getSource(), FirErrors.INSTANCE.getNO_GET_METHOD(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
            } else if (Intrinsics.areEqual(name2, OperatorNameConventions.SET)) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, firErrorNamedReference.getSource(), FirErrors.INSTANCE.getNO_SET_METHOD(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
            }
        }
    }

    private final void checkPropertyAsOperator(FirFunctionCall callExpression, FirExpression receiver, CheckerContext context, DiagnosticReporter reporter) {
        KtSourceElement source = callExpression.getSource();
        Object obj = null;
        KtSourceElementKind kind = source != null ? source.getKind() : null;
        if ((kind instanceof KtRealSourceElementKind) || (kind instanceof KtFakeSourceElementKind.GeneratedComparisonExpression) || (kind instanceof KtFakeSourceElementKind.DesugaredCompoundAssignment)) {
            FirExpression firExpressionUnwrapSmartcastExpression = FirExpressionUtilKt.unwrapSmartcastExpression(receiver);
            if (firExpressionUnwrapSmartcastExpression instanceof FirPropertyAccessExpression) {
                for (Object obj2 : ((FirPropertyAccessExpression) firExpressionUnwrapSmartcastExpression).getNonFatalDiagnostics()) {
                    if (obj2 instanceof ConePropertyAsOperator) {
                        obj = obj2;
                        break;
                    }
                }
                ConePropertyAsOperator conePropertyAsOperator = (ConePropertyAsOperator) obj;
                if (conePropertyAsOperator == null) {
                    return;
                }
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, callExpression.getCalleeReference().getSource(), FirErrors.INSTANCE.getPROPERTY_AS_OPERATOR(), conePropertyAsOperator.getSymbol(), context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
            }
        }
    }
}
