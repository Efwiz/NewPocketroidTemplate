package org.jetbrains.kotlin.fir.analysis.checkers.expression;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.analysis.checkers.FirHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.FirKeywordUtilsKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.expressions.FirCatch;
import org.jetbrains.kotlin.fir.expressions.FirTryExpression;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeParameterType;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.InferenceUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeComponentsKt;
import org.jetbrains.kotlin.lexer.KtKeywordToken;

/* JADX INFO: compiled from: FirCatchParameterChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirCatchParameterChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirExpressionChecker;", "Lorg/jetbrains/kotlin/fir/expressions/FirTryExpression;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirTryExpressionChecker;", "()V", "check", "", "expression", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirCatchParameterChecker extends FirExpressionChecker<FirTryExpression> {
    public static final FirCatchParameterChecker INSTANCE = new FirCatchParameterChecker();

    private FirCatchParameterChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.FirExpressionChecker
    public void check(FirTryExpression expression, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        Iterator<FirCatch> it = expression.getCatches().iterator();
        while (it.hasNext()) {
            FirProperty parameter = it.next().getParameter();
            KtSourceElement source = parameter.getSource();
            if (source != null) {
                KtSourceElement source2 = parameter.getSource();
                if ((source2 != null ? FirHelpersKt.getDefaultValueForParameter(source2) : null) != null) {
                    KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, FirErrors.INSTANCE.getCATCH_PARAMETER_WITH_DEFAULT_VALUE(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                }
                KtKeywordToken valOrVarKeyword = FirKeywordUtilsKt.getValOrVarKeyword(source);
                if (valOrVarKeyword != null) {
                    KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, FirErrors.INSTANCE.getVAL_OR_VAR_ON_CATCH_PARAMETER(), valOrVarKeyword, context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
                }
                ConeKotlinType coneType = FirTypeUtilsKt.getConeType(parameter.getReturnTypeRef());
                if (coneType instanceof ConeTypeParameterType) {
                    if (((ConeTypeParameterType) coneType).getLookupTag().getTypeParameterSymbol().isReified()) {
                        KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, FirErrors.INSTANCE.getREIFIED_TYPE_IN_CATCH_CLAUSE(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                    } else {
                        KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, FirErrors.INSTANCE.getTYPE_PARAMETER_IN_CATCH_CLAUSE(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                    }
                }
                FirSession session = context.getSession();
                if (!FirHelpersKt.isSubtypeOfThrowable(coneType, session)) {
                    KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, FirErrors.INSTANCE.getTHROWABLE_TYPE_MISMATCH(), coneType, Boolean.valueOf(InferenceUtilsKt.isTypeMismatchDueToNullability(TypeComponentsKt.getTypeContext(context.getSession()), coneType, session.getBuiltinTypes().getThrowableType().getType())), context, (AbstractSourceElementPositioningStrategy) null, 32, (Object) null);
                }
            }
        }
    }
}
