package org.jetbrains.kotlin.fir.analysis.checkers.expression;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtRealSourceElementKind;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.FirLabel;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirErrorFunction;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic;
import org.jetbrains.kotlin.fir.diagnostics.ConeSimpleDiagnostic;
import org.jetbrains.kotlin.fir.diagnostics.DiagnosticKind;
import org.jetbrains.kotlin.fir.expressions.FirAnonymousFunctionExpression;
import org.jetbrains.kotlin.fir.expressions.FirCall;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirFunctionCall;
import org.jetbrains.kotlin.fir.expressions.FirLambdaArgumentExpression;
import org.jetbrains.kotlin.fir.expressions.FirReturnExpression;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.expressions.impl.FirSingleExpressionBlock;
import org.jetbrains.kotlin.fir.references.FirReferenceUtilsKt;
import org.jetbrains.kotlin.fir.symbols.impl.FirAnonymousFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;

/* JADX INFO: compiled from: FirReturnSyntaxAndLabelChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u001c\u0010\f\u001a\u00020\r2\n\u0010\u000e\u001a\u0006\u0012\u0002\b\u00030\u000f2\u0006\u0010\b\u001a\u00020\tH\u0002¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirReturnSyntaxAndLabelChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirExpressionChecker;", "Lorg/jetbrains/kotlin/fir/expressions/FirReturnExpression;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirReturnExpressionChecker;", "()V", "check", "", "expression", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "isReturnAllowed", "", "targetSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirFunctionSymbol;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirReturnSyntaxAndLabelChecker extends FirExpressionChecker<FirReturnExpression> {
    public static final FirReturnSyntaxAndLabelChecker INSTANCE = new FirReturnSyntaxAndLabelChecker();

    private FirReturnSyntaxAndLabelChecker() {
    }

    /* JADX WARN: Code duplicated, block: B:21:0x0062  */
    /* JADX WARN: Code duplicated, block: B:23:0x0068  */
    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.FirExpressionChecker
    public void check(FirReturnExpression expression, CheckerContext context, DiagnosticReporter reporter) {
        Object next;
        FirAnonymousFunction anonymousFunction;
        KtSourceElement source;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        KtSourceElement source2 = expression.getSource();
        if ((source2 != null ? source2.getKind() : null) instanceof KtFakeSourceElementKind.ImplicitReturn) {
            return;
        }
        FirFunction firFunction = (FirFunction) expression.getTarget().getLabeledElement();
        FirFunctionSymbol<? extends FirFunction> symbol = firFunction.getSymbol();
        if (firFunction instanceof FirErrorFunction) {
            ConeDiagnostic diagnostic = ((FirErrorFunction) firFunction).getDiagnostic();
            ConeSimpleDiagnostic coneSimpleDiagnostic = diagnostic instanceof ConeSimpleDiagnostic ? (ConeSimpleDiagnostic) diagnostic : null;
            if ((coneSimpleDiagnostic != null ? coneSimpleDiagnostic.getKind() : null) == DiagnosticKind.NotAFunctionLabel) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, source2, FirErrors.INSTANCE.getNOT_A_FUNCTION_LABEL(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
            } else if (!isReturnAllowed(symbol, context)) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, source2, FirErrors.INSTANCE.getRETURN_NOT_ALLOWED(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
            }
        } else if (!isReturnAllowed(symbol, context)) {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, source2, FirErrors.INSTANCE.getRETURN_NOT_ALLOWED(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
        }
        if (symbol instanceof FirAnonymousFunctionSymbol) {
            FirLabel label = ((FirAnonymousFunctionSymbol) symbol).getLabel();
            if (!(((label == null || (source = label.getSource()) == null) ? null : source.getKind()) instanceof KtRealSourceElementKind)) {
                Iterator it = CollectionsKt.asReversed(context.getCallsOrAssignments()).iterator();
                while (true) {
                    if (!it.hasNext()) {
                        next = null;
                        break;
                    }
                    next = it.next();
                    FirStatement firStatement = (FirStatement) next;
                    if (firStatement instanceof FirFunctionCall) {
                        FirNamedFunctionSymbol resolvedNamedFunctionSymbol$default = FirReferenceUtilsKt.toResolvedNamedFunctionSymbol$default(((FirFunctionCall) firStatement).getCalleeReference(), false, 1, null);
                        if (Intrinsics.areEqual(resolvedNamedFunctionSymbol$default != null ? resolvedNamedFunctionSymbol$default.getCallableId() : null, FirSuspendCallChecker.INSTANCE.getKOTLIN_SUSPEND_BUILT_IN_FUNCTION_CALLABLE_ID$checkers())) {
                            break;
                        }
                    }
                }
                FirStatement firStatement2 = (FirStatement) next;
                if (firStatement2 instanceof FirFunctionCall) {
                    List<FirExpression> arguments = ((FirCall) firStatement2).getArgumentList().getArguments();
                    if (!(arguments instanceof Collection) || !arguments.isEmpty()) {
                        for (FirExpression firExpression : arguments) {
                            if (firExpression instanceof FirLambdaArgumentExpression) {
                                FirExpression expression2 = ((FirLambdaArgumentExpression) firExpression).getExpression();
                                FirAnonymousFunctionExpression firAnonymousFunctionExpression = expression2 instanceof FirAnonymousFunctionExpression ? (FirAnonymousFunctionExpression) expression2 : null;
                                if (Intrinsics.areEqual((firAnonymousFunctionExpression == null || (anonymousFunction = firAnonymousFunctionExpression.getAnonymousFunction()) == null) ? null : anonymousFunction.getSymbol(), symbol)) {
                                    KtDiagnosticReportHelpersKt.reportOn$default(reporter, source2, FirErrors.INSTANCE.getRETURN_FOR_BUILT_IN_SUSPEND(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
        FirDeclaration firDeclaration = (FirDeclaration) CollectionsKt.last((List) context.getContainingDeclarations());
        if ((firDeclaration instanceof FirFunction) && (((FirFunction) firDeclaration).getBody() instanceof FirSingleExpressionBlock)) {
            KtSourceElement source3 = firDeclaration.getSource();
            if (Intrinsics.areEqual(source3 != null ? source3.getKind() : null, KtFakeSourceElementKind.DelegatedPropertyAccessor.INSTANCE)) {
                return;
            }
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, source2, FirErrors.INSTANCE.getRETURN_IN_FUNCTION_WITH_EXPRESSION_BODY(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
        }
    }

    private final boolean isReturnAllowed(FirFunctionSymbol<?> targetSymbol, CheckerContext context) {
        for (FirDeclaration firDeclaration : CollectionsKt.asReversed(context.getContainingDeclarations())) {
            if (firDeclaration instanceof FirClass) {
                return false;
            }
            if (firDeclaration instanceof FirFunction) {
                if (Intrinsics.areEqual(((FirFunction) firDeclaration).getSymbol(), targetSymbol)) {
                    return true;
                }
                if (!(firDeclaration instanceof FirAnonymousFunction) || !((FirAnonymousFunction) firDeclaration).getInlineStatus().getReturnAllowed()) {
                    return false;
                }
            } else if (!(firDeclaration instanceof FirProperty)) {
                if (firDeclaration instanceof FirValueParameter) {
                    break;
                }
            } else {
                if (!((FirProperty) firDeclaration).getIsLocal()) {
                    return false;
                }
            }
        }
        return true;
    }
}
