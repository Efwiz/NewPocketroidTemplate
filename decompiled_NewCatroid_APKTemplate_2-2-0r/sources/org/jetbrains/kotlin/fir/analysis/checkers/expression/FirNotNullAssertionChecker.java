package org.jetbrains.kotlin.fir.analysis.checkers.expression;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.expressions.FirAnonymousFunctionExpression;
import org.jetbrains.kotlin.fir.expressions.FirCallableReferenceAccess;
import org.jetbrains.kotlin.fir.expressions.FirCheckNotNullCall;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier;
import org.jetbrains.kotlin.fir.resolve.TypeExpansionUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;

/* JADX INFO: compiled from: FirNotNullAssertionChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirNotNullAssertionChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirExpressionChecker;", "Lorg/jetbrains/kotlin/fir/expressions/FirCheckNotNullCall;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirCheckNotNullCallChecker;", "()V", "check", "", "expression", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirNotNullAssertionChecker extends FirExpressionChecker<FirCheckNotNullCall> {
    public static final FirNotNullAssertionChecker INSTANCE = new FirNotNullAssertionChecker();

    private FirNotNullAssertionChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.FirExpressionChecker
    public void check(FirCheckNotNullCall expression, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        FirExpression firExpression = (FirExpression) CollectionsKt.singleOrNull((List) expression.getArgumentList().getArguments());
        if (firExpression == null) {
            return;
        }
        if ((firExpression instanceof FirAnonymousFunctionExpression) && ((FirAnonymousFunctionExpression) firExpression).getAnonymousFunction().getIsLambda()) {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, expression.getSource(), FirErrors.INSTANCE.getNOT_NULL_ASSERTION_ON_LAMBDA_EXPRESSION(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
            return;
        }
        if (firExpression instanceof FirCallableReferenceAccess) {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, expression.getSource(), FirErrors.INSTANCE.getNOT_NULL_ASSERTION_ON_CALLABLE_REFERENCE(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
            return;
        }
        if ((firExpression instanceof FirResolvedQualifier) && FirTypeUtilsKt.isUnit(firExpression.getTypeRef())) {
            return;
        }
        ConeKotlinType coneKotlinTypeFullyExpandedType = TypeExpansionUtilsKt.fullyExpandedType(FirTypeUtilsKt.getConeType(firExpression.getTypeRef()), context.getSession());
        if (FirTypeUtilsKt.getCanBeNull(coneKotlinTypeFullyExpandedType) || !context.get$languageVersionSettings().supportsFeature(LanguageFeature.EnableDfaWarningsInK2)) {
            return;
        }
        KtDiagnosticReportHelpersKt.reportOn$default(reporter, expression.getSource(), FirErrors.INSTANCE.getUNNECESSARY_NOT_NULL_ASSERTION(), coneKotlinTypeFullyExpandedType, context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
    }
}
