package org.jetbrains.kotlin.fir.analysis.checkers.expression;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.declarations.DeclarationUtilsKt;
import org.jetbrains.kotlin.fir.expressions.FirGetClassCall;
import org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression;
import org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeAliasSymbol;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;

/* JADX INFO: compiled from: FirStandaloneQualifierChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J,\u0010\f\u001a\u00020\u0006*\b\u0012\u0002\b\u0003\u0018\u00010\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0002¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirStandaloneQualifierChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirExpressionChecker;", "Lorg/jetbrains/kotlin/fir/expressions/FirResolvedQualifier;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirResolvedQualifierChecker;", "()V", "check", "", "expression", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "reportErrorOn", "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirStandaloneQualifierChecker extends FirExpressionChecker<FirResolvedQualifier> {
    public static final FirStandaloneQualifierChecker INSTANCE = new FirStandaloneQualifierChecker();

    private FirStandaloneQualifierChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.FirExpressionChecker
    public void check(FirResolvedQualifier expression, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        Object objLastOrNull = CollectionsKt.lastOrNull((List<? extends Object>) context.getCallsOrAssignments());
        FirQualifiedAccessExpression firQualifiedAccessExpression = objLastOrNull instanceof FirQualifiedAccessExpression ? (FirQualifiedAccessExpression) objLastOrNull : null;
        if ((firQualifiedAccessExpression != null ? firQualifiedAccessExpression.getExplicitReceiver() : null) != expression) {
            if ((firQualifiedAccessExpression != null ? firQualifiedAccessExpression.getDispatchReceiver() : null) == expression) {
                return;
            }
            FirGetClassCall firGetClassCall = (FirGetClassCall) CollectionsKt.lastOrNull((List) context.getGetClassCalls());
            if ((firGetClassCall != null ? firGetClassCall.getArgument() : null) != expression && FirTypeUtilsKt.isUnit(expression.getTypeRef())) {
                reportErrorOn(expression.getSymbol(), expression.getSource(), context, reporter);
            }
        }
    }

    private final void reportErrorOn(FirBasedSymbol<?> firBasedSymbol, KtSourceElement ktSourceElement, CheckerContext checkerContext, DiagnosticReporter diagnosticReporter) {
        if (firBasedSymbol instanceof FirRegularClassSymbol) {
            if (((FirRegularClassSymbol) firBasedSymbol).getClassKind() == ClassKind.OBJECT) {
                return;
            }
            KtDiagnosticReportHelpersKt.reportOn$default(diagnosticReporter, ktSourceElement, FirErrors.INSTANCE.getNO_COMPANION_OBJECT(), firBasedSymbol, checkerContext, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
        } else if (!(firBasedSymbol instanceof FirTypeAliasSymbol)) {
            if (firBasedSymbol == null) {
                KtDiagnosticReportHelpersKt.reportOn$default(diagnosticReporter, ktSourceElement, FirErrors.INSTANCE.getEXPRESSION_EXPECTED_PACKAGE_FOUND(), checkerContext, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
            }
        } else {
            FirRegularClassSymbol firRegularClassSymbolFullyExpandedClass = DeclarationUtilsKt.fullyExpandedClass((FirClassLikeSymbol) firBasedSymbol, checkerContext.getSession());
            if (firRegularClassSymbolFullyExpandedClass != null) {
                reportErrorOn(firRegularClassSymbolFullyExpandedClass, ktSourceElement, checkerContext, diagnosticReporter);
            }
        }
    }
}
