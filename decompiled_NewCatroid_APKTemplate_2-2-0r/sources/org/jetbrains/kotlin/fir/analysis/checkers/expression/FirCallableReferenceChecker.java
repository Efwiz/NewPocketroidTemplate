package org.jetbrains.kotlin.fir.analysis.checkers.expression;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.FirHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.DeclarationUtilsKt;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.expressions.FirCallableReferenceAccess;
import org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression;
import org.jetbrains.kotlin.fir.references.FirReferenceUtilsKt;
import org.jetbrains.kotlin.fir.references.FirResolvedNamedReference;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirConstructorSymbol;

/* JADX INFO: compiled from: FirCallableReferenceChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J \u0010\f\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0002¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirCallableReferenceChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirExpressionChecker;", "Lorg/jetbrains/kotlin/fir/expressions/FirQualifiedAccessExpression;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirQualifiedAccessExpressionChecker;", "()V", "check", "", "expression", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkReferenceIsToAllowedMember", "callableReferenceAccess", "Lorg/jetbrains/kotlin/fir/expressions/FirCallableReferenceAccess;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirCallableReferenceChecker extends FirExpressionChecker<FirQualifiedAccessExpression> {
    public static final FirCallableReferenceChecker INSTANCE = new FirCallableReferenceChecker();

    private FirCallableReferenceChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.FirExpressionChecker
    public void check(FirQualifiedAccessExpression expression, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        if (expression instanceof FirCallableReferenceAccess) {
            checkReferenceIsToAllowedMember((FirCallableReferenceAccess) expression, context, reporter);
        }
    }

    private final void checkReferenceIsToAllowedMember(FirCallableReferenceAccess callableReferenceAccess, CheckerContext context, DiagnosticReporter reporter) {
        KtSourceElement source;
        FirResolvedNamedReference resolved = FirReferenceUtilsKt.getResolved(callableReferenceAccess.getCalleeReference());
        if (resolved == null || (source = resolved.getSource()) == null || (source.getKind() instanceof KtFakeSourceElementKind)) {
            return;
        }
        FirBasedSymbol<?> resolvedSymbol = resolved.getResolvedSymbol();
        if (resolvedSymbol instanceof FirConstructorSymbol) {
            FirClassLikeSymbol<?> containingClassSymbol = FirHelpersKt.getContainingClassSymbol(resolvedSymbol, context.getSession());
            if ((containingClassSymbol != null ? FirHelpersKt.getClassKind(containingClassSymbol) : null) == ClassKind.ANNOTATION_CLASS) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, FirErrors.INSTANCE.getCALLABLE_REFERENCE_TO_ANNOTATION_CONSTRUCTOR(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
            }
        }
        FirCallableSymbol firCallableSymbol = resolvedSymbol instanceof FirCallableSymbol ? (FirCallableSymbol) resolvedSymbol : null;
        if (firCallableSymbol == null || !DeclarationUtilsKt.isExtensionMember(firCallableSymbol) || DeclarationUtilsKt.isLocalMember(resolvedSymbol)) {
            return;
        }
        KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, FirErrors.INSTANCE.getEXTENSION_IN_CLASS_REFERENCE_NOT_ALLOWED(), resolvedSymbol, context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
    }
}
