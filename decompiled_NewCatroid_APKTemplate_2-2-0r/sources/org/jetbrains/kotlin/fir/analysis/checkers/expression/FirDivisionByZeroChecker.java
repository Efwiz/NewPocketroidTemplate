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
import org.jetbrains.kotlin.fir.expressions.FirConstExpression;
import org.jetbrains.kotlin.fir.expressions.FirFunctionCall;
import org.jetbrains.kotlin.fir.references.FirReferenceUtilsKt;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.incremental.IncrementalJvmCacheKt;
import org.jetbrains.kotlin.name.CallableId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: FirDivisionByZeroChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u00022\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirDivisionByZeroChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirExpressionChecker;", "Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCall;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirFunctionCallChecker;", "()V", "defaultDivName", "Lorg/jetbrains/kotlin/name/Name;", "defaultPackageName", "Lorg/jetbrains/kotlin/name/FqName;", "check", "", "expression", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirDivisionByZeroChecker extends FirExpressionChecker<FirFunctionCall> {
    private static final Name defaultDivName;
    public static final FirDivisionByZeroChecker INSTANCE = new FirDivisionByZeroChecker();
    private static final FqName defaultPackageName = new FqName(IncrementalJvmCacheKt.KOTLIN_CACHE_DIRECTORY_NAME);

    private FirDivisionByZeroChecker() {
    }

    static {
        Name nameIdentifier = Name.identifier("div");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        defaultDivName = nameIdentifier;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.FirExpressionChecker
    public void check(FirFunctionCall expression, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        Object objSingleOrNull = CollectionsKt.singleOrNull((List<? extends Object>) expression.getArgumentList().getArguments());
        FirConstExpression firConstExpression = objSingleOrNull instanceof FirConstExpression ? (FirConstExpression) objSingleOrNull : null;
        Object value = firConstExpression != null ? firConstExpression.getValue() : null;
        if (value != null) {
            if (Intrinsics.areEqual(value, (Object) 0L) || Intrinsics.areEqual(value, Float.valueOf(0.0f)) || Intrinsics.areEqual(value, Double.valueOf(0.0d))) {
                FirNamedFunctionSymbol resolvedNamedFunctionSymbol$default = FirReferenceUtilsKt.toResolvedNamedFunctionSymbol$default(expression.getCalleeReference(), false, 1, null);
                CallableId callableId = resolvedNamedFunctionSymbol$default != null ? resolvedNamedFunctionSymbol$default.getCallableId() : null;
                if (callableId != null && Intrinsics.areEqual(callableId.getPackageName(), defaultPackageName) && Intrinsics.areEqual(callableId.getCallableName(), defaultDivName)) {
                    KtDiagnosticReportHelpersKt.reportOn$default(reporter, expression.getSource(), FirErrors.INSTANCE.getDIVISION_BY_ZERO(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                }
            }
        }
    }
}
