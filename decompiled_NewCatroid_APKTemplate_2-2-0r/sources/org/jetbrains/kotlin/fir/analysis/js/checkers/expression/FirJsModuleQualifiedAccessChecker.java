package org.jetbrains.kotlin.fir.analysis.js.checkers.expression;

import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.AbstractKtSourceElement;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.fir.analysis.checkers.FirHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.checkers.expression.FirExpressionChecker;
import org.jetbrains.kotlin.fir.analysis.js.checkers.FirJsModuleCheckUtilsKt;
import org.jetbrains.kotlin.fir.expressions.FirCallableReferenceAccess;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirFunctionCall;
import org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression;
import org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier;
import org.jetbrains.kotlin.fir.expressions.impl.FirNoReceiverExpression;
import org.jetbrains.kotlin.fir.references.FirReferenceUtilsKt;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeParameterSymbol;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.FirTypeProjection;
import org.jetbrains.kotlin.fir.types.FirTypeProjectionWithVariance;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;

/* JADX INFO: compiled from: FirJsModuleQualifiedAccessChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J \u0010\f\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0002J(\u0010\u000e\u001a\u001a\u0012\u0016\u0012\u0014\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0011\u0012\u0006\u0012\u0004\u0018\u00010\u00120\u00100\u000f2\u0006\u0010\u0007\u001a\u00020\u0002H\u0002¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/js/checkers/expression/FirJsModuleQualifiedAccessChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirExpressionChecker;", "Lorg/jetbrains/kotlin/fir/expressions/FirQualifiedAccessExpression;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirQualifiedAccessExpressionChecker;", "()V", "check", "", "expression", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkReifiedTypeParameters", "expr", "extractModuleCalleeSymbols", "", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "Lorg/jetbrains/kotlin/AbstractKtSourceElement;", "checkers.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirJsModuleQualifiedAccessChecker extends FirExpressionChecker<FirQualifiedAccessExpression> {
    public static final FirJsModuleQualifiedAccessChecker INSTANCE = new FirJsModuleQualifiedAccessChecker();

    private FirJsModuleQualifiedAccessChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.FirExpressionChecker
    public void check(FirQualifiedAccessExpression expression, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        checkReifiedTypeParameters(expression, context, reporter);
        for (Pair<FirBasedSymbol<?>, AbstractKtSourceElement> pair : extractModuleCalleeSymbols(expression)) {
            FirBasedSymbol<?> firBasedSymbolComponent1 = pair.component1();
            KtSourceElement ktSourceElementComponent2 = pair.component2();
            if (ktSourceElementComponent2 == null) {
                ktSourceElementComponent2 = expression.getSource();
            }
            FirJsModuleCheckUtilsKt.checkJsModuleUsage(firBasedSymbolComponent1, context, reporter, ktSourceElementComponent2);
        }
    }

    private final List<Pair<FirBasedSymbol<?>, AbstractKtSourceElement>> extractModuleCalleeSymbols(FirQualifiedAccessExpression expression) {
        FirBasedSymbol resolvedBaseSymbol$default = FirReferenceUtilsKt.toResolvedBaseSymbol$default(expression.getCalleeReference(), false, 1, null);
        if (resolvedBaseSymbol$default != null && FirHelpersKt.getContainingClassSymbol((FirBasedSymbol<?>) resolvedBaseSymbol$default, resolvedBaseSymbol$default.getModuleData().getSession()) == null) {
            return CollectionsKt.listOf(TuplesKt.to(resolvedBaseSymbol$default, expression.getCalleeReference().getSource()));
        }
        FirExpression dispatchReceiver = expression.getDispatchReceiver();
        if (dispatchReceiver instanceof FirNoReceiverExpression) {
            return CollectionsKt.listOfNotNull(resolvedBaseSymbol$default != null ? TuplesKt.to(resolvedBaseSymbol$default, expression.getCalleeReference().getSource()) : null);
        }
        if (dispatchReceiver instanceof FirResolvedQualifier) {
            FirClassLikeSymbol<?> symbol = ((FirResolvedQualifier) dispatchReceiver).getSymbol();
            if (!(expression instanceof FirCallableReferenceAccess)) {
                return CollectionsKt.listOfNotNull(symbol != null ? TuplesKt.to(symbol, expression.getCalleeReference().getSource()) : null);
            }
            Pair[] pairArr = new Pair[2];
            pairArr[0] = symbol != null ? TuplesKt.to(symbol, dispatchReceiver.getSource()) : null;
            pairArr[1] = resolvedBaseSymbol$default != null ? TuplesKt.to(resolvedBaseSymbol$default, ((FirCallableReferenceAccess) expression).getCalleeReference().getSource()) : null;
            return CollectionsKt.listOfNotNull((Object[]) pairArr);
        }
        return CollectionsKt.emptyList();
    }

    private final void checkReifiedTypeParameters(FirQualifiedAccessExpression expr, CheckerContext context, DiagnosticReporter reporter) {
        FirNamedFunctionSymbol resolvedNamedFunctionSymbol$default;
        FirRegularClassSymbol regularClassSymbol;
        FirFunctionCall firFunctionCall = expr instanceof FirFunctionCall ? (FirFunctionCall) expr : null;
        if (firFunctionCall == null || (resolvedNamedFunctionSymbol$default = FirReferenceUtilsKt.toResolvedNamedFunctionSymbol$default(firFunctionCall.getCalleeReference(), false, 1, null)) == null) {
            return;
        }
        for (Pair pair : CollectionsKt.zip(resolvedNamedFunctionSymbol$default.getTypeParameterSymbols(), firFunctionCall.getTypeArguments())) {
            FirTypeParameterSymbol firTypeParameterSymbol = (FirTypeParameterSymbol) pair.component1();
            FirTypeProjection firTypeProjection = (FirTypeProjection) pair.component2();
            if (firTypeParameterSymbol.isReified() && (firTypeProjection instanceof FirTypeProjectionWithVariance)) {
                FirTypeProjectionWithVariance firTypeProjectionWithVariance = (FirTypeProjectionWithVariance) firTypeProjection;
                ConeKotlinType coneTypeOrNull = FirTypeUtilsKt.getConeTypeOrNull(firTypeProjectionWithVariance.getTypeRef());
                if (coneTypeOrNull != null && (regularClassSymbol = TypeUtilsKt.toRegularClassSymbol(coneTypeOrNull, context.getSession())) != null) {
                    KtSourceElement source = firTypeProjectionWithVariance.getSource();
                    if (source == null && (source = ((FirFunctionCall) expr).getCalleeReference().getSource()) == null) {
                        source = expr.getSource();
                    }
                    FirJsModuleCheckUtilsKt.checkJsModuleUsage(regularClassSymbol, context, reporter, source);
                }
            }
        }
    }
}
