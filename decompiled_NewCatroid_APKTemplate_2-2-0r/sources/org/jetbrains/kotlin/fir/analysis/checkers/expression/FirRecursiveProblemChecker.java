package org.jetbrains.kotlin.fir.analysis.checkers.expression;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic;
import org.jetbrains.kotlin.fir.diagnostics.ConeSimpleDiagnostic;
import org.jetbrains.kotlin.fir.diagnostics.DiagnosticKind;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.expressions.impl.FirNoReceiverExpression;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeErrorType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeProjection;
import org.jetbrains.kotlin.fir.types.ConeTypeProjectionKt;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;

/* JADX INFO: compiled from: FirRecursiveProblemChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u00022\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016R\u0018\u0010\u0005\u001a\u00020\u0006*\u00020\u00028BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0007¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirRecursiveProblemChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirExpressionChecker;", "Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirBasicExpressionChecker;", "()V", "isExpressionWithAlwaysImplicitTypeRef", "", "(Lorg/jetbrains/kotlin/fir/expressions/FirStatement;)Z", "check", "", "expression", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirRecursiveProblemChecker extends FirExpressionChecker<FirStatement> {
    public static final FirRecursiveProblemChecker INSTANCE = new FirRecursiveProblemChecker();

    private FirRecursiveProblemChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.FirExpressionChecker
    public void check(FirStatement expression, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        if (expression instanceof FirExpression) {
            KtSourceElement source = expression.getSource();
            if (((source != null ? source.getKind() : null) instanceof KtFakeSourceElementKind) || isExpressionWithAlwaysImplicitTypeRef(expression)) {
                return;
            }
            check$checkConeType(reporter, expression, context, FirTypeUtilsKt.getConeType(((FirExpression) expression).getTypeRef()));
        }
    }

    private static final void check$checkConeType(DiagnosticReporter diagnosticReporter, FirStatement firStatement, CheckerContext checkerContext, ConeKotlinType coneKotlinType) {
        if (coneKotlinType instanceof ConeErrorType) {
            ConeDiagnostic diagnostic = ((ConeErrorType) coneKotlinType).getDiagnostic();
            ConeSimpleDiagnostic coneSimpleDiagnostic = diagnostic instanceof ConeSimpleDiagnostic ? (ConeSimpleDiagnostic) diagnostic : null;
            if ((coneSimpleDiagnostic != null ? coneSimpleDiagnostic.getKind() : null) == DiagnosticKind.RecursionInImplicitTypes) {
                KtDiagnosticReportHelpersKt.reportOn$default(diagnosticReporter, firStatement.getSource(), FirErrors.INSTANCE.getTYPECHECKER_HAS_RUN_INTO_RECURSIVE_PROBLEM(), checkerContext, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                return;
            }
        }
        if (coneKotlinType instanceof ConeClassLikeType) {
            for (ConeTypeProjection coneTypeProjection : ((ConeClassLikeType) coneKotlinType).getTypeArguments()) {
                check$checkConeType(diagnosticReporter, firStatement, checkerContext, ConeTypeProjectionKt.getType(coneTypeProjection));
            }
        }
    }

    private final boolean isExpressionWithAlwaysImplicitTypeRef(FirStatement firStatement) {
        return firStatement instanceof FirNoReceiverExpression;
    }
}
