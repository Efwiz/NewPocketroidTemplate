package org.jetbrains.kotlin.fir.analysis.checkers.declaration;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.checkers.expression.FirExpressionChecker;
import org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;

/* JADX INFO: compiled from: FirInlineBodyResolvedQualifierChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirInlineBodyResolvedQualifierChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirExpressionChecker;", "Lorg/jetbrains/kotlin/fir/expressions/FirResolvedQualifier;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirResolvedQualifierChecker;", "()V", "check", "", "expression", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirInlineBodyResolvedQualifierChecker extends FirExpressionChecker<FirResolvedQualifier> {
    public static final FirInlineBodyResolvedQualifierChecker INSTANCE = new FirInlineBodyResolvedQualifierChecker();

    private FirInlineBodyResolvedQualifierChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.FirExpressionChecker
    public void check(FirResolvedQualifier expression, CheckerContext context, DiagnosticReporter reporter) {
        FirClassLikeSymbol<?> symbol;
        KtSourceElement source;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        FirInlineDeclarationChecker.InlineFunctionBodyContext inlineFunctionBodyContext = context.getInlineFunctionBodyContext();
        if (inlineFunctionBodyContext == null || (symbol = expression.getSymbol()) == null || (source = expression.getSource()) == null || !symbol.getRawStatus().isCompanion()) {
            return;
        }
        inlineFunctionBodyContext.checkAccessedDeclaration$checkers(source, symbol, symbol.getResolvedStatus().getVisibility(), context, reporter);
    }
}
