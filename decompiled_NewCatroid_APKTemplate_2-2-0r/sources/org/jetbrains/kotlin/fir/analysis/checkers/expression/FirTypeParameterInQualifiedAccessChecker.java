package org.jetbrains.kotlin.fir.analysis.checkers.expression;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic;
import org.jetbrains.kotlin.fir.expressions.FirCallableReferenceAccess;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirGetClassCall;
import org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression;
import org.jetbrains.kotlin.fir.expressions.FirResolvedReifiedParameterReference;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConeTypeParameterInQualifiedAccess;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeParameterSymbol;
import org.jetbrains.kotlin.fir.types.FirErrorTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;

/* JADX INFO: compiled from: FirTypeParameterInQualifiedAccessChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J \u0010\u0011\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0002J \u0010\u0012\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0002R\u001a\u0010\u0005\u001a\u0004\u0018\u00010\u0006*\u00020\u00078BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\t¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirTypeParameterInQualifiedAccessChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirExpressionChecker;", "Lorg/jetbrains/kotlin/fir/expressions/FirQualifiedAccessExpression;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirQualifiedAccessExpressionChecker;", "()V", "coneTypeParameterInQualifiedAccess", "Lorg/jetbrains/kotlin/fir/resolve/diagnostics/ConeTypeParameterInQualifiedAccess;", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "getConeTypeParameterInQualifiedAccess", "(Lorg/jetbrains/kotlin/fir/types/FirTypeRef;)Lorg/jetbrains/kotlin/fir/resolve/diagnostics/ConeTypeParameterInQualifiedAccess;", "check", "", "expression", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkExplicitReceiver", "checkExpressionItself", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirTypeParameterInQualifiedAccessChecker extends FirExpressionChecker<FirQualifiedAccessExpression> {
    public static final FirTypeParameterInQualifiedAccessChecker INSTANCE = new FirTypeParameterInQualifiedAccessChecker();

    private FirTypeParameterInQualifiedAccessChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.FirExpressionChecker
    public void check(FirQualifiedAccessExpression expression, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        checkExplicitReceiver(expression, context, reporter);
        checkExpressionItself(expression, context, reporter);
    }

    private final void checkExpressionItself(FirQualifiedAccessExpression expression, CheckerContext context, DiagnosticReporter reporter) {
        ConeTypeParameterInQualifiedAccess coneTypeParameterInQualifiedAccess;
        KtSourceElement source;
        FirGetClassCall firGetClassCall = (FirGetClassCall) CollectionsKt.lastOrNull((List) context.getGetClassCalls());
        if (Intrinsics.areEqual(firGetClassCall != null ? firGetClassCall.getArgument() : null, expression)) {
            return;
        }
        FirStatement firStatement = (FirStatement) CollectionsKt.getOrNull(context.getCallsOrAssignments(), context.getCallsOrAssignments().size() - 2);
        if (((firStatement instanceof FirQualifiedAccessExpression) && Intrinsics.areEqual(((FirQualifiedAccessExpression) firStatement).getExplicitReceiver(), expression)) || (coneTypeParameterInQualifiedAccess = getConeTypeParameterInQualifiedAccess(expression.getTypeRef())) == null || (source = expression.getSource()) == null) {
            return;
        }
        KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, FirErrors.INSTANCE.getTYPE_PARAMETER_IS_NOT_AN_EXPRESSION(), coneTypeParameterInQualifiedAccess.getSymbol(), context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
    }

    private final void checkExplicitReceiver(FirQualifiedAccessExpression expression, CheckerContext context, DiagnosticReporter reporter) {
        FirTypeRef typeRef;
        ConeTypeParameterInQualifiedAccess coneTypeParameterInQualifiedAccess;
        FirTypeParameterSymbol symbol;
        FirExpression explicitReceiver = expression.getExplicitReceiver();
        FirResolvedReifiedParameterReference firResolvedReifiedParameterReference = explicitReceiver instanceof FirResolvedReifiedParameterReference ? (FirResolvedReifiedParameterReference) explicitReceiver : null;
        if (firResolvedReifiedParameterReference == null || (symbol = firResolvedReifiedParameterReference.getSymbol()) == null) {
            if (explicitReceiver == null || (typeRef = explicitReceiver.getTypeRef()) == null || (coneTypeParameterInQualifiedAccess = getConeTypeParameterInQualifiedAccess(typeRef)) == null) {
                return;
            } else {
                symbol = coneTypeParameterInQualifiedAccess.getSymbol();
            }
        }
        FirTypeParameterSymbol firTypeParameterSymbol = symbol;
        if (expression instanceof FirCallableReferenceAccess) {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, expression.getSource(), FirErrors.INSTANCE.getCALLABLE_REFERENCE_LHS_NOT_A_CLASS(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
        } else {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, explicitReceiver != null ? explicitReceiver.getSource() : null, FirErrors.INSTANCE.getTYPE_PARAMETER_ON_LHS_OF_DOT(), firTypeParameterSymbol, context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
        }
    }

    private final ConeTypeParameterInQualifiedAccess getConeTypeParameterInQualifiedAccess(FirTypeRef firTypeRef) {
        FirErrorTypeRef firErrorTypeRef = firTypeRef instanceof FirErrorTypeRef ? (FirErrorTypeRef) firTypeRef : null;
        ConeDiagnostic diagnostic = firErrorTypeRef != null ? firErrorTypeRef.getDiagnostic() : null;
        if (diagnostic instanceof ConeTypeParameterInQualifiedAccess) {
            return (ConeTypeParameterInQualifiedAccess) diagnostic;
        }
        return null;
    }
}
