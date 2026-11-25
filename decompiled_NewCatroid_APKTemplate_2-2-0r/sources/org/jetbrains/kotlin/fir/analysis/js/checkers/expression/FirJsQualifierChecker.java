package org.jetbrains.kotlin.fir.analysis.js.checkers.expression;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.checkers.expression.FirExpressionChecker;
import org.jetbrains.kotlin.fir.analysis.diagnostics.js.FirJsErrors;
import org.jetbrains.kotlin.fir.declarations.FirAnnotationUtilsKt;
import org.jetbrains.kotlin.fir.expressions.FirAnnotationCall;
import org.jetbrains.kotlin.fir.expressions.FirConstExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.js.JsHelpersKt;
import org.jetbrains.kotlin.name.JsStandardClassIds;

/* JADX INFO: compiled from: FirJsQualifierChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/js/checkers/expression/FirJsQualifierChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirExpressionChecker;", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotationCall;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirAnnotationCallChecker;", "()V", "check", "", "expression", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkers.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirJsQualifierChecker extends FirExpressionChecker<FirAnnotationCall> {
    public static final FirJsQualifierChecker INSTANCE = new FirJsQualifierChecker();

    private FirJsQualifierChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.FirExpressionChecker
    public void check(FirAnnotationCall expression, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        if (Intrinsics.areEqual(FirAnnotationUtilsKt.toAnnotationClassId(expression, context.getSession()), JsStandardClassIds.Annotations.JsQualifier)) {
            Object objFirstOrNull = CollectionsKt.firstOrNull(expression.getArgumentMapping().getMapping().values());
            FirConstExpression firConstExpression = objFirstOrNull instanceof FirConstExpression ? (FirConstExpression) objFirstOrNull : null;
            Object value = firConstExpression != null ? firConstExpression.getValue() : null;
            String str = value instanceof String ? (String) value : null;
            if (str == null || JsHelpersKt.validateQualifier(str)) {
                return;
            }
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, ((FirExpression) CollectionsKt.first((List) expression.getArgumentList().getArguments())).getSource(), FirJsErrors.INSTANCE.getWRONG_JS_QUALIFIER(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
        }
    }
}
