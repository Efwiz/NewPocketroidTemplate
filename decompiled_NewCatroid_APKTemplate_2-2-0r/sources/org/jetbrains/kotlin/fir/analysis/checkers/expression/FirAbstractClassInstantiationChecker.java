package org.jetbrains.kotlin.fir.analysis.checkers.expression;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.expressions.FirCallableReferenceAccess;
import org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression;
import org.jetbrains.kotlin.fir.references.FirReferenceUtilsKt;
import org.jetbrains.kotlin.fir.symbols.impl.FirConstructorSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;

/* JADX INFO: compiled from: FirAbstractClassInstantiationChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirAbstractClassInstantiationChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirExpressionChecker;", "Lorg/jetbrains/kotlin/fir/expressions/FirQualifiedAccessExpression;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirQualifiedAccessExpressionChecker;", "()V", "check", "", "expression", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirAbstractClassInstantiationChecker extends FirExpressionChecker<FirQualifiedAccessExpression> {
    public static final FirAbstractClassInstantiationChecker INSTANCE = new FirAbstractClassInstantiationChecker();

    private FirAbstractClassInstantiationChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.FirExpressionChecker
    public void check(FirQualifiedAccessExpression expression, CheckerContext context, DiagnosticReporter reporter) {
        FirRegularClassSymbol regularClassSymbol;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        FirConstructorSymbol resolvedConstructorSymbol$default = FirReferenceUtilsKt.toResolvedConstructorSymbol$default(expression.getCalleeReference(), false, 1, null);
        if (resolvedConstructorSymbol$default != null && (regularClassSymbol = TypeUtilsKt.toRegularClassSymbol(FirTypeUtilsKt.getConeType(resolvedConstructorSymbol$default.getResolvedReturnTypeRef()), context.getSession())) != null && regularClassSymbol.getResolvedStatus().getModality() == Modality.ABSTRACT && regularClassSymbol.getClassKind() == ClassKind.CLASS) {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, expression instanceof FirCallableReferenceAccess ? ((FirCallableReferenceAccess) expression).getCalleeReference().getSource() : expression.getSource(), FirErrors.INSTANCE.getCREATING_AN_INSTANCE_OF_ABSTRACT_CLASS(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
        }
    }
}
