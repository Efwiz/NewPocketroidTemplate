package org.jetbrains.kotlin.fir.analysis.checkers.expression;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.expressions.FirInaccessibleReceiverExpression;
import org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression;

/* JADX INFO: compiled from: FirReceiverAccessBeforeSuperCallChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirReceiverAccessBeforeSuperCallChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirExpressionChecker;", "Lorg/jetbrains/kotlin/fir/expressions/FirInaccessibleReceiverExpression;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirInaccessibleReceiverChecker;", "()V", "check", "", "expression", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirReceiverAccessBeforeSuperCallChecker extends FirExpressionChecker<FirInaccessibleReceiverExpression> {
    public static final FirReceiverAccessBeforeSuperCallChecker INSTANCE = new FirReceiverAccessBeforeSuperCallChecker();

    private FirReceiverAccessBeforeSuperCallChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.FirExpressionChecker
    public void check(FirInaccessibleReceiverExpression expression, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        Object objLast = CollectionsKt.last((List<? extends Object>) context.getCallsOrAssignments());
        Intrinsics.checkNotNull(objLast, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression");
        FirQualifiedAccessExpression firQualifiedAccessExpression = (FirQualifiedAccessExpression) objLast;
        if (!Intrinsics.areEqual(expression, firQualifiedAccessExpression.getDispatchReceiver()) && !Intrinsics.areEqual(expression, firQualifiedAccessExpression.getExtensionReceiver()) && !firQualifiedAccessExpression.getContextReceiverArguments().contains(expression)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        KtDiagnosticReportHelpersKt.reportOn$default(reporter, firQualifiedAccessExpression.getCalleeReference().getSource(), FirErrors.INSTANCE.getINSTANCE_ACCESS_BEFORE_SUPER_CALL(), "<this>", context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
    }
}
