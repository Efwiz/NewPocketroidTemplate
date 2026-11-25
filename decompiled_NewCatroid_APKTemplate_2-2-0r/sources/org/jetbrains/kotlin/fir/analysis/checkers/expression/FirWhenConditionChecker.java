package org.jetbrains.kotlin.fir.analysis.checkers.expression;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.FirAnnotationContainer;
import org.jetbrains.kotlin.fir.analysis.checkers.FirHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.expressions.FirCall;
import org.jetbrains.kotlin.fir.expressions.FirConstExpression;
import org.jetbrains.kotlin.fir.expressions.FirEqualityOperatorCall;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpressionUtilKt;
import org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression;
import org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier;
import org.jetbrains.kotlin.fir.expressions.FirTypeOperatorCall;
import org.jetbrains.kotlin.fir.expressions.FirWhenBranch;
import org.jetbrains.kotlin.fir.expressions.FirWhenExpression;
import org.jetbrains.kotlin.fir.expressions.FirWhenSubjectExpression;
import org.jetbrains.kotlin.fir.expressions.impl.FirElseIfTrueCondition;
import org.jetbrains.kotlin.fir.references.FirReferenceUtilsKt;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirEnumEntrySymbol;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;

/* JADX INFO: compiled from: FirWhenConditionChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J \u0010\f\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0002¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirWhenConditionChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirExpressionChecker;", "Lorg/jetbrains/kotlin/fir/expressions/FirWhenExpression;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirWhenExpressionChecker;", "()V", "check", "", "expression", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkDuplicatedLabels", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirWhenConditionChecker extends FirExpressionChecker<FirWhenExpression> {
    public static final FirWhenConditionChecker INSTANCE = new FirWhenConditionChecker();

    private FirWhenConditionChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.FirExpressionChecker
    public void check(FirWhenExpression expression, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        Iterator<FirWhenBranch> it = expression.getBranches().iterator();
        while (it.hasNext()) {
            FirExpression condition = it.next().getCondition();
            if (!(condition instanceof FirElseIfTrueCondition)) {
                FirHelpersKt.checkCondition(condition, context, reporter);
            }
        }
        if (expression.getSubject() != null) {
            checkDuplicatedLabels(expression, context, reporter);
        }
    }

    private final void checkDuplicatedLabels(FirWhenExpression expression, CheckerContext context, DiagnosticReporter reporter) {
        Object value;
        FirClassLikeSymbol<?> symbol;
        HashSet hashSet = new HashSet();
        HashSet hashSet2 = new HashSet();
        Iterator<FirWhenBranch> it = expression.getBranches().iterator();
        while (it.hasNext()) {
            FirAnnotationContainer condition = it.next().getCondition();
            if (!(condition instanceof FirEqualityOperatorCall)) {
                if (condition instanceof FirTypeOperatorCall) {
                    FirTypeOperatorCall firTypeOperatorCall = (FirTypeOperatorCall) condition;
                    if (!hashSet.add(TuplesKt.to(FirTypeUtilsKt.getConeType(firTypeOperatorCall.getConversionTypeRef()), firTypeOperatorCall.getOperation()))) {
                        KtDiagnosticReportHelpersKt.reportOn$default(reporter, firTypeOperatorCall.getConversionTypeRef().getSource(), FirErrors.INSTANCE.getDUPLICATE_LABEL_IN_WHEN(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                    }
                }
            } else {
                List<FirExpression> arguments = ((FirCall) condition).getArgumentList().getArguments();
                if (arguments.size() == 2 && (FirExpressionUtilKt.unwrapSmartcastExpression(arguments.get(0)) instanceof FirWhenSubjectExpression)) {
                    FirExpression firExpression = arguments.get(1);
                    if (firExpression instanceof FirConstExpression) {
                        value = ((FirConstExpression) firExpression).getValue();
                    } else if (firExpression instanceof FirQualifiedAccessExpression) {
                        Object resolvedCallableSymbol$default = FirReferenceUtilsKt.toResolvedCallableSymbol$default(((FirQualifiedAccessExpression) firExpression).getCalleeReference(), false, 1, null);
                        value = resolvedCallableSymbol$default instanceof FirEnumEntrySymbol ? (FirEnumEntrySymbol) resolvedCallableSymbol$default : null;
                        if (value == null) {
                        }
                    } else if ((firExpression instanceof FirResolvedQualifier) && (symbol = ((FirResolvedQualifier) firExpression).getSymbol()) != null && FirHelpersKt.getClassKind(symbol) == ClassKind.OBJECT) {
                        value = symbol.getClassId();
                    }
                    if (!hashSet2.add(value)) {
                        KtDiagnosticReportHelpersKt.reportOn$default(reporter, condition.getSource(), FirErrors.INSTANCE.getDUPLICATE_LABEL_IN_WHEN(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                    }
                }
            }
        }
    }
}
