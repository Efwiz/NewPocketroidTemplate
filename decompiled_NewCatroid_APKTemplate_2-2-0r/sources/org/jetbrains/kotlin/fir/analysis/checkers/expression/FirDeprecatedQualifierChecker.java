package org.jetbrains.kotlin.fir.analysis.checkers.expression;

import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.declarations.DeclarationUtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic;
import org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier;
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConeDeprecated;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;

/* JADX INFO: compiled from: FirDeprecatedQualifierChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirDeprecatedQualifierChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirExpressionChecker;", "Lorg/jetbrains/kotlin/fir/expressions/FirResolvedQualifier;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirResolvedQualifierChecker;", "()V", "check", "", "expression", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirDeprecatedQualifierChecker extends FirExpressionChecker<FirResolvedQualifier> {
    public static final FirDeprecatedQualifierChecker INSTANCE = new FirDeprecatedQualifierChecker();

    private FirDeprecatedQualifierChecker() {
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.FirExpressionChecker
    public void check(FirResolvedQualifier expression, CheckerContext context, DiagnosticReporter reporter) {
        FirClassLikeSymbol<?> symbol;
        FirRegularClassSymbol firRegularClassSymbolFullyExpandedClass;
        FirRegularClass firRegularClass;
        FirRegularClassSymbol companionObjectSymbol;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        List<ConeDiagnostic> nonFatalDiagnostics = expression.getNonFatalDiagnostics();
        ArrayList<ConeDeprecated> arrayList = new ArrayList();
        for (Object obj : nonFatalDiagnostics) {
            if (obj instanceof ConeDeprecated) {
                arrayList.add(obj);
            }
        }
        for (ConeDeprecated coneDeprecated : arrayList) {
            FirDeprecationChecker.INSTANCE.reportApiStatus$checkers(coneDeprecated.getSource(), coneDeprecated.getSymbol(), false, coneDeprecated.getDeprecationInfo(), reporter, context);
        }
        if (!expression.getResolvedToCompanionObject() || (symbol = expression.getSymbol()) == null || (firRegularClassSymbolFullyExpandedClass = DeclarationUtilsKt.fullyExpandedClass(symbol, context.getSession())) == null || (firRegularClass = (FirRegularClass) firRegularClassSymbolFullyExpandedClass.getFir()) == null || (companionObjectSymbol = firRegularClass.getCompanionObjectSymbol()) == null) {
            return;
        }
        FirDeprecationChecker.reportApiStatusIfNeeded$checkers$default(FirDeprecationChecker.INSTANCE, expression.getSource(), companionObjectSymbol, context, reporter, null, 16, null);
    }
}
