package org.jetbrains.kotlin.fir.analysis.js.checkers.expression;

import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.checkers.expression.FirExpressionChecker;
import org.jetbrains.kotlin.fir.analysis.diagnostics.js.FirJsErrors;
import org.jetbrains.kotlin.fir.analysis.js.checkers.FirJsHelpersKt;
import org.jetbrains.kotlin.fir.expressions.FirFunctionCall;
import org.jetbrains.kotlin.fir.references.FirReferenceUtilsKt;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassifierSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeParameterSymbol;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.FirTypeProjection;
import org.jetbrains.kotlin.fir.types.FirTypeProjectionWithVariance;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;

/* JADX INFO: compiled from: FirJsReifiedExternalChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/js/checkers/expression/FirJsReifiedExternalChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirExpressionChecker;", "Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCall;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirFunctionCallChecker;", "()V", "check", "", "expression", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkers.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirJsReifiedExternalChecker extends FirExpressionChecker<FirFunctionCall> {
    public static final FirJsReifiedExternalChecker INSTANCE = new FirJsReifiedExternalChecker();

    private FirJsReifiedExternalChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.FirExpressionChecker
    public void check(FirFunctionCall expression, CheckerContext context, DiagnosticReporter reporter) {
        FirClassifierSymbol<?> symbol;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        FirNamedFunctionSymbol resolvedNamedFunctionSymbol$default = FirReferenceUtilsKt.toResolvedNamedFunctionSymbol$default(expression.getCalleeReference(), false, 1, null);
        if (resolvedNamedFunctionSymbol$default == null) {
            return;
        }
        for (Pair pair : CollectionsKt.zip(resolvedNamedFunctionSymbol$default.getTypeParameterSymbols(), expression.getTypeArguments())) {
            FirTypeParameterSymbol firTypeParameterSymbol = (FirTypeParameterSymbol) pair.component1();
            FirTypeProjection firTypeProjection = (FirTypeProjection) pair.component2();
            if (firTypeParameterSymbol.isReified() && (firTypeProjection instanceof FirTypeProjectionWithVariance)) {
                FirTypeProjectionWithVariance firTypeProjectionWithVariance = (FirTypeProjectionWithVariance) firTypeProjection;
                ConeKotlinType coneTypeOrNull = FirTypeUtilsKt.getConeTypeOrNull(firTypeProjectionWithVariance.getTypeRef());
                if (coneTypeOrNull != null && (symbol = TypeUtilsKt.toSymbol(coneTypeOrNull, context.getSession())) != null && FirJsHelpersKt.isNativeInterface(symbol, context)) {
                    KtSourceElement source = firTypeProjectionWithVariance.getSource();
                    if (source == null) {
                        source = expression.getSource();
                    }
                    KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, FirJsErrors.INSTANCE.getEXTERNAL_INTERFACE_AS_REIFIED_TYPE_ARGUMENT(), coneTypeOrNull, context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
                }
            }
        }
    }
}
