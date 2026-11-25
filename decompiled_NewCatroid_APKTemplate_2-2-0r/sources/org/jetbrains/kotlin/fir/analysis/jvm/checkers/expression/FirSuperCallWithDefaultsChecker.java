package org.jetbrains.kotlin.fir.analysis.jvm.checkers.expression;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticFactory1;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.FirHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.checkers.expression.FirExpressionChecker;
import org.jetbrains.kotlin.fir.analysis.diagnostics.jvm.FirJvmErrors;
import org.jetbrains.kotlin.fir.expressions.FirArgumentList;
import org.jetbrains.kotlin.fir.expressions.FirFunctionCall;
import org.jetbrains.kotlin.fir.expressions.impl.FirResolvedArgumentList;
import org.jetbrains.kotlin.fir.references.FirReferenceUtilsKt;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirValueParameterSymbol;

/* JADX INFO: compiled from: FirSuperCallWithDefaultsChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/jvm/checkers/expression/FirSuperCallWithDefaultsChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirExpressionChecker;", "Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCall;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirFunctionCallChecker;", "()V", "check", "", "expression", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkers.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirSuperCallWithDefaultsChecker extends FirExpressionChecker<FirFunctionCall> {
    public static final FirSuperCallWithDefaultsChecker INSTANCE = new FirSuperCallWithDefaultsChecker();

    private FirSuperCallWithDefaultsChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.FirExpressionChecker
    public void check(FirFunctionCall expression, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        if (FirHelpersKt.explicitReceiverIsNotSuperReference(expression)) {
            return;
        }
        FirNamedFunctionSymbol resolvedNamedFunctionSymbol$default = FirReferenceUtilsKt.toResolvedNamedFunctionSymbol$default(expression.getCalleeReference(), false, 1, null);
        if (resolvedNamedFunctionSymbol$default == null) {
            return;
        }
        List<FirValueParameterSymbol> valueParameterSymbols = resolvedNamedFunctionSymbol$default.getValueParameterSymbols();
        if ((valueParameterSymbols instanceof Collection) && valueParameterSymbols.isEmpty()) {
            return;
        }
        Iterator<T> it = valueParameterSymbols.iterator();
        while (it.hasNext()) {
            if (((FirValueParameterSymbol) it.next()).getHasDefaultValue()) {
                FirArgumentList argumentList = expression.getArgumentList();
                FirResolvedArgumentList firResolvedArgumentList = argumentList instanceof FirResolvedArgumentList ? (FirResolvedArgumentList) argumentList : null;
                if (firResolvedArgumentList != null && firResolvedArgumentList.getArguments().size() < resolvedNamedFunctionSymbol$default.getValueParameterSymbols().size()) {
                    KtSourceElement source = expression.getCalleeReference().getSource();
                    KtDiagnosticFactory1<String> super_call_with_default_parameters = FirJvmErrors.INSTANCE.getSUPER_CALL_WITH_DEFAULT_PARAMETERS();
                    String strAsString = resolvedNamedFunctionSymbol$default.getName().asString();
                    Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                    KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, super_call_with_default_parameters, strAsString, context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
                    return;
                }
                return;
            }
        }
    }
}
