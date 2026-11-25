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
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
import org.jetbrains.kotlin.fir.expressions.FirBlock;
import org.jetbrains.kotlin.fir.expressions.FirExpressionUtilKt;
import org.jetbrains.kotlin.fir.expressions.FirFunctionCall;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.expressions.impl.FirContractCallBlock;
import org.jetbrains.kotlin.fir.expressions.impl.FirSingleExpressionBlock;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.name.CallableId;
import org.jetbrains.kotlin.name.StandardClassIds;

/* JADX INFO: compiled from: FirContractNotFirstStatementChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0014\u0010\f\u001a\u00020\r*\u00020\u00022\u0006\u0010\u000e\u001a\u00020\u000fH\u0002¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirContractNotFirstStatementChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirExpressionChecker;", "Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCall;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirFunctionCallChecker;", "()V", "check", "", "expression", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "isCorrectlyPlacedIn", "", "functionDeclaration", "Lorg/jetbrains/kotlin/fir/declarations/FirFunction;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirContractNotFirstStatementChecker extends FirExpressionChecker<FirFunctionCall> {
    public static final FirContractNotFirstStatementChecker INSTANCE = new FirContractNotFirstStatementChecker();

    private FirContractNotFirstStatementChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.FirExpressionChecker
    public void check(FirFunctionCall expression, CheckerContext context, DiagnosticReporter reporter) {
        String str;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        CallableId contract = StandardClassIds.Callables.INSTANCE.getContract();
        FirCallableSymbol<?> resolvedCallableSymbol = FirExpressionUtilKt.toResolvedCallableSymbol(expression);
        if (Intrinsics.areEqual(contract, resolvedCallableSymbol != null ? resolvedCallableSymbol.getCallableId() : null)) {
            FirDeclaration firDeclaration = (FirDeclaration) CollectionsKt.last((List) context.getContainingDeclarations());
            boolean z = firDeclaration instanceof FirFunction;
            if (z && isCorrectlyPlacedIn(expression, (FirFunction) firDeclaration)) {
                return;
            }
            if (z && (((FirFunction) firDeclaration).getBody() instanceof FirSingleExpressionBlock)) {
                str = "Contracts are only allowed in function body blocks";
            } else {
                str = "Contract should be the first statement";
            }
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, expression.getSource(), FirErrors.INSTANCE.getCONTRACT_NOT_ALLOWED(), str, context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
        }
    }

    private final boolean isCorrectlyPlacedIn(FirFunctionCall firFunctionCall, FirFunction firFunction) {
        List<FirStatement> statements;
        FirBlock body = firFunction.getBody();
        FirStatement firStatement = (body == null || (statements = body.getStatements()) == null) ? null : (FirStatement) CollectionsKt.first((List) statements);
        return (firStatement instanceof FirContractCallBlock) && Intrinsics.areEqual(((FirContractCallBlock) firStatement).getCall(), firFunctionCall);
    }
}
