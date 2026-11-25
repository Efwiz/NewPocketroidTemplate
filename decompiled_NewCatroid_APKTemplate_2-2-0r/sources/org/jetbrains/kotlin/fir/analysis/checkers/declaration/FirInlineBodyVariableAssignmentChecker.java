package org.jetbrains.kotlin.fir.analysis.checkers.declaration;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.checkers.expression.FirExpressionChecker;
import org.jetbrains.kotlin.fir.expressions.FirExpressionUtilKt;
import org.jetbrains.kotlin.fir.expressions.FirVariableAssignment;
import org.jetbrains.kotlin.fir.references.FirReference;
import org.jetbrains.kotlin.fir.references.FirReferenceUtilsKt;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertyAccessorSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;

/* JADX INFO: compiled from: FirInlineBodyVariableAssignmentChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirInlineBodyVariableAssignmentChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirExpressionChecker;", "Lorg/jetbrains/kotlin/fir/expressions/FirVariableAssignment;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirVariableAssignmentChecker;", "()V", "check", "", "expression", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirInlineBodyVariableAssignmentChecker extends FirExpressionChecker<FirVariableAssignment> {
    public static final FirInlineBodyVariableAssignmentChecker INSTANCE = new FirInlineBodyVariableAssignmentChecker();

    private FirInlineBodyVariableAssignmentChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.FirExpressionChecker
    public void check(FirVariableAssignment expression, CheckerContext context, DiagnosticReporter reporter) {
        FirPropertyAccessorSymbol setterSymbol;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        FirInlineDeclarationChecker.InlineFunctionBodyContext inlineFunctionBodyContext = context.getInlineFunctionBodyContext();
        if (inlineFunctionBodyContext == null) {
            return;
        }
        FirReference calleeReference = FirExpressionUtilKt.getCalleeReference(expression);
        FirCallableSymbol resolvedCallableSymbol$default = calleeReference != null ? FirReferenceUtilsKt.toResolvedCallableSymbol$default(calleeReference, false, 1, null) : null;
        FirPropertySymbol firPropertySymbol = resolvedCallableSymbol$default instanceof FirPropertySymbol ? (FirPropertySymbol) resolvedCallableSymbol$default : null;
        if (firPropertySymbol == null || (setterSymbol = firPropertySymbol.getSetterSymbol()) == null) {
            return;
        }
        inlineFunctionBodyContext.checkQualifiedAccess$checkers(expression, setterSymbol, context, reporter);
    }
}
