package org.jetbrains.kotlin.fir.analysis.jvm.checkers.expression;

import java.util.List;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.FirLanguageSettingsComponentKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.checkers.expression.FirExpressionChecker;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.expressions.FirCallableReferenceAccess;
import org.jetbrains.kotlin.fir.expressions.FirExpressionUtilKt;
import org.jetbrains.kotlin.fir.java.symbols.FirJavaOverriddenSyntheticPropertySymbol;
import org.jetbrains.kotlin.fir.references.FirResolvedNamedReference;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirSyntheticPropertySymbol;

/* JADX INFO: compiled from: FirUnsupportedSyntheticCallableReferenceChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003J \u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/jvm/checkers/expression/FirUnsupportedSyntheticCallableReferenceChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirExpressionChecker;", "Lorg/jetbrains/kotlin/fir/expressions/FirCallableReferenceAccess;", "()V", "check", "", "expression", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkers.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirUnsupportedSyntheticCallableReferenceChecker extends FirExpressionChecker<FirCallableReferenceAccess> {
    public static final FirUnsupportedSyntheticCallableReferenceChecker INSTANCE = new FirUnsupportedSyntheticCallableReferenceChecker();

    private FirUnsupportedSyntheticCallableReferenceChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.FirExpressionChecker
    public void check(FirCallableReferenceAccess expression, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        List<FirElement> containingElements = context.getContainingElements();
        if (CollectionsKt.last((List) containingElements) != expression) {
            throw new IllegalStateException("Check failed.".toString());
        }
        FirElement firElement = containingElements.get(CollectionsKt.getLastIndex(containingElements) - 1);
        if ((firElement instanceof FirProperty) && ((FirProperty) firElement).getDelegate() == expression) {
            return;
        }
        FirResolvedNamedReference resolvedCallableReference = FirExpressionUtilKt.toResolvedCallableReference(expression);
        FirBasedSymbol<?> resolvedSymbol = resolvedCallableReference != null ? resolvedCallableReference.getResolvedSymbol() : null;
        if (FirLanguageSettingsComponentKt.getLanguageVersionSettings(context.getSession()).supportsFeature(LanguageFeature.ReferencesToSyntheticJavaProperties) || !(resolvedSymbol instanceof FirSyntheticPropertySymbol) || (resolvedSymbol instanceof FirJavaOverriddenSyntheticPropertySymbol)) {
            return;
        }
        KtDiagnosticReportHelpersKt.reportOn$default(reporter, expression.getCalleeReference().getSource(), FirErrors.INSTANCE.getUNSUPPORTED_FEATURE(), TuplesKt.to(LanguageFeature.ReferencesToSyntheticJavaProperties, FirLanguageSettingsComponentKt.getLanguageVersionSettings(context.getSession())), context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
    }
}
