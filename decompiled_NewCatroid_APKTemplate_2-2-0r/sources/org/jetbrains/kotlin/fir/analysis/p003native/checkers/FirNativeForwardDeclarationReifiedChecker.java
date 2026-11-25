package org.jetbrains.kotlin.fir.analysis.p003native.checkers;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.checkers.expression.FirExpressionChecker;
import org.jetbrains.kotlin.fir.analysis.diagnostics.p002native.FirNativeErrors;
import org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression;
import org.jetbrains.kotlin.fir.references.FirReference;
import org.jetbrains.kotlin.fir.references.FirReferenceUtilsKt;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeParameterSymbol;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeProjectionKt;
import org.jetbrains.kotlin.fir.types.FirTypeProjection;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;

/* JADX INFO: compiled from: FirNativeForwardDeclarationReifiedChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/native/checkers/FirNativeForwardDeclarationReifiedChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirExpressionChecker;", "Lorg/jetbrains/kotlin/fir/expressions/FirQualifiedAccessExpression;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirQualifiedAccessExpressionChecker;", "()V", "check", "", "expression", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkers.native"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirNativeForwardDeclarationReifiedChecker extends FirExpressionChecker<FirQualifiedAccessExpression> {
    public static final FirNativeForwardDeclarationReifiedChecker INSTANCE = new FirNativeForwardDeclarationReifiedChecker();

    private FirNativeForwardDeclarationReifiedChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.FirExpressionChecker
    public void check(FirQualifiedAccessExpression expression, CheckerContext context, DiagnosticReporter reporter) {
        List<FirTypeParameterSymbol> typeParameterSymbols;
        ConeKotlinType type;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        FirReference calleeReference = expression.getCalleeReference();
        List<FirTypeProjection> typeArguments = expression.getTypeArguments();
        FirCallableSymbol resolvedCallableSymbol$default = FirReferenceUtilsKt.toResolvedCallableSymbol$default(calleeReference, false, 1, null);
        if (resolvedCallableSymbol$default == null || (typeParameterSymbols = resolvedCallableSymbol$default.getTypeParameterSymbols()) == null) {
            return;
        }
        int iMin = Math.min(typeArguments.size(), typeParameterSymbols.size());
        for (int i = 0; i < iMin; i++) {
            FirTypeProjection firTypeProjection = typeArguments.get(i);
            KtSourceElement source = firTypeProjection.getSource();
            if ((source != null || (source = calleeReference.getSource()) != null) && (type = ConeTypeProjectionKt.getType(FirTypeUtilsKt.toConeTypeProjection(firTypeProjection))) != null && typeParameterSymbols.get(i).isReified()) {
                FirRegularClassSymbol regularClassSymbol = TypeUtilsKt.toRegularClassSymbol(type, context.getSession());
                if ((regularClassSymbol != null ? FirNativeHelpersKt.forwardDeclarationKindOrNull(regularClassSymbol) : null) != null) {
                    KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, FirNativeErrors.INSTANCE.getFORWARD_DECLARATION_AS_REIFIED_TYPE_ARGUMENT(), type, context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
                }
            }
        }
    }
}
