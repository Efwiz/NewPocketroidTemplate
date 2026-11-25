package org.jetbrains.kotlin.fir.analysis.checkers.expression;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.FE10LikeConeSubstitutor;
import org.jetbrains.kotlin.fir.analysis.checkers.FirHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.FirUpperBoundViolatedHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.expressions.FirCallableReferenceAccess;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier;
import org.jetbrains.kotlin.fir.resolve.TypeExpansionUtilsKt;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeAliasSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeParameterSymbol;
import org.jetbrains.kotlin.fir.types.ConeAttributes;
import org.jetbrains.kotlin.fir.types.ConeTypeProjection;
import org.jetbrains.kotlin.fir.types.TypeConstructionUtilsKt;

/* JADX INFO: compiled from: FirTypeArgumentsOfQualifierOfCallableReferenceChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirTypeArgumentsOfQualifierOfCallableReferenceChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirExpressionChecker;", "Lorg/jetbrains/kotlin/fir/expressions/FirCallableReferenceAccess;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirCallableReferenceAccessChecker;", "()V", "check", "", "expression", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirTypeArgumentsOfQualifierOfCallableReferenceChecker extends FirExpressionChecker<FirCallableReferenceAccess> {
    public static final FirTypeArgumentsOfQualifierOfCallableReferenceChecker INSTANCE = new FirTypeArgumentsOfQualifierOfCallableReferenceChecker();

    private FirTypeArgumentsOfQualifierOfCallableReferenceChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.FirExpressionChecker
    public void check(FirCallableReferenceAccess expression, CheckerContext context, DiagnosticReporter reporter) {
        FirClassLikeSymbol<?> symbol;
        List<ConeTypeProjection> list;
        List<FirTypeParameterSymbol> typeParameterSymbols;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        FirExpression explicitReceiver = expression.getExplicitReceiver();
        FirResolvedQualifier firResolvedQualifier = explicitReceiver instanceof FirResolvedQualifier ? (FirResolvedQualifier) explicitReceiver : null;
        if (firResolvedQualifier == null || (symbol = firResolvedQualifier.getSymbol()) == null) {
            return;
        }
        List<ConeTypeProjection> typeArgumentsWithSourceInfo = FirUpperBoundViolatedHelpersKt.toTypeArgumentsWithSourceInfo(firResolvedQualifier.getTypeArguments());
        List<FirTypeParameterSymbol> typeParameterSymbols2 = symbol.getTypeParameterSymbols();
        if (typeParameterSymbols2.size() != typeArgumentsWithSourceInfo.size()) {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, firResolvedQualifier.getSource(), FirErrors.INSTANCE.getWRONG_NUMBER_OF_TYPE_ARGUMENTS(), Integer.valueOf(symbol.getTypeParameterSymbols().size()), symbol, context, (AbstractSourceElementPositioningStrategy) null, 32, (Object) null);
            return;
        }
        if (symbol instanceof FirTypeAliasSymbol) {
            List<ConeTypeProjection> list2 = ArraysKt.toList(TypeExpansionUtilsKt.fullyExpandedType$default(TypeConstructionUtilsKt.constructType$default((FirClassLikeSymbol) symbol, (ConeTypeProjection[]) typeArgumentsWithSourceInfo.toArray(new ConeTypeProjection[0]), false, (ConeAttributes) null, 4, (Object) null), context.getSession(), null, 2, null).getTypeArguments());
            FirRegularClassSymbol regularClassSymbol = FirHelpersKt.toRegularClassSymbol(((FirTypeAliasSymbol) symbol).getResolvedExpandedTypeRef(), context.getSession());
            if (regularClassSymbol == null) {
                return;
            }
            typeParameterSymbols = regularClassSymbol.getTypeParameterSymbols();
            list = list2;
        } else {
            list = typeArgumentsWithSourceInfo;
            typeParameterSymbols = typeParameterSymbols2;
        }
        FirUpperBoundViolatedHelpersKt.checkUpperBoundViolated(context, reporter, typeParameterSymbols, list, new FE10LikeConeSubstitutor(typeParameterSymbols, list, context.getSession()), (96 & 32) != 0 ? false : false, (96 & 64) != 0 ? false : false);
    }
}
