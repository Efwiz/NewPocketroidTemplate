package org.jetbrains.kotlin.fir.analysis.js.checkers.expression;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.checkers.expression.FirExpressionChecker;
import org.jetbrains.kotlin.fir.analysis.diagnostics.js.FirJsErrors;
import org.jetbrains.kotlin.fir.analysis.js.checkers.FirJsHelpersKt;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.expressions.FirCall;
import org.jetbrains.kotlin.fir.expressions.FirComponentCall;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpressionUtilKt;
import org.jetbrains.kotlin.fir.expressions.FirFunctionCall;
import org.jetbrains.kotlin.fir.expressions.FirFunctionCallOrigin;
import org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression;
import org.jetbrains.kotlin.fir.expressions.FirSpreadArgumentExpression;
import org.jetbrains.kotlin.fir.expressions.FirVarargArgumentsExpression;
import org.jetbrains.kotlin.fir.references.FirNamedReference;
import org.jetbrains.kotlin.fir.references.FirReference;
import org.jetbrains.kotlin.fir.references.FirReferenceUtilsKt;
import org.jetbrains.kotlin.fir.references.FirResolvedNamedReference;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.types.ConeDynamicType;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.util.OperatorNameConventions;

/* JADX INFO: compiled from: FirJsDynamicCallChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00022\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J \u0010\u0014\u001a\u00020\u000e2\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0010\u001a\u00020\u0011H\u0002J \u0010\u0017\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00022\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0002J%\u0010\u0018\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00022\u0012\u0010\u0019\u001a\u000e\u0012\u0004\u0012\u00020\u001b\u0012\u0004\u0012\u00020\u000e0\u001aH\u0082\bR\u0018\u0010\u0005\u001a\u00020\u0006*\u00020\u00078BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\bR\u0018\u0010\t\u001a\u00020\u0006*\u00020\n8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\u000bR\u0018\u0010\f\u001a\u00020\u0006*\u00020\n8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\u000b¨\u0006\u001c"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/js/checkers/expression/FirJsDynamicCallChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirExpressionChecker;", "Lorg/jetbrains/kotlin/fir/expressions/FirQualifiedAccessExpression;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirQualifiedAccessExpressionChecker;", "()V", "isArrayAccessWithMultipleIndices", "", "Lorg/jetbrains/kotlin/fir/expressions/FirCall;", "(Lorg/jetbrains/kotlin/fir/expressions/FirCall;)Z", "isInOperator", "Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCall;", "(Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCall;)Z", "isRangeOperator", "check", "", "expression", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkIdentifier", "namedReference", "Lorg/jetbrains/kotlin/fir/references/FirResolvedNamedReference;", "checkSpreadOperator", "forAllSpreadArgumentsOf", "callback", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "checkers.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirJsDynamicCallChecker extends FirExpressionChecker<FirQualifiedAccessExpression> {
    public static final FirJsDynamicCallChecker INSTANCE = new FirJsDynamicCallChecker();

    private FirJsDynamicCallChecker() {
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.FirExpressionChecker
    public void check(FirQualifiedAccessExpression expression, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        FirResolvedNamedReference resolved = FirReferenceUtilsKt.getResolved(expression.getCalleeReference());
        if (resolved == null) {
            return;
        }
        if (!(resolved.getResolvedSymbol().getOrigin() instanceof FirDeclarationOrigin.DynamicScope)) {
            checkSpreadOperator(expression, context, reporter);
            return;
        }
        FirCallableSymbol resolvedCallableSymbol$default = FirReferenceUtilsKt.toResolvedCallableSymbol$default(resolved, false, 1, null);
        if (resolvedCallableSymbol$default == null) {
            throw new IllegalStateException("Resolved call callee without a callable symbol".toString());
        }
        boolean z = expression instanceof FirCall;
        if (z && isArrayAccessWithMultipleIndices((FirCall) expression)) {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, expression.getSource(), FirJsErrors.INSTANCE.getWRONG_OPERATION_WITH_DYNAMIC(), "indexed access with more than one index", context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
        } else {
            boolean z2 = expression instanceof FirFunctionCall;
            if (z2 && isInOperator((FirFunctionCall) expression)) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, expression.getSource(), FirJsErrors.INSTANCE.getWRONG_OPERATION_WITH_DYNAMIC(), "`in` operation", context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
            } else if (z2 && isRangeOperator((FirFunctionCall) expression)) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, expression.getSource(), FirJsErrors.INSTANCE.getWRONG_OPERATION_WITH_DYNAMIC(), "`" + ((String) FirJsDynamicCallCheckerKt.nameToOperator.get(resolvedCallableSymbol$default.getName())) + "` operation", context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
            } else if (expression instanceof FirComponentCall) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, expression.getSource(), FirJsErrors.INSTANCE.getWRONG_OPERATION_WITH_DYNAMIC(), "`destructuring declaration", context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
            } else {
                checkIdentifier(resolved, reporter, context);
            }
        }
        FirCall firCall = z ? (FirCall) expression : null;
        if (firCall == null) {
            return;
        }
        for (FirExpression firExpression : firCall.getArgumentList().getArguments()) {
            if (firExpression instanceof FirVarargArgumentsExpression) {
                for (FirExpression firExpression2 : ((FirVarargArgumentsExpression) firExpression).getArguments()) {
                    if (firExpression2 instanceof FirSpreadArgumentExpression) {
                        KtDiagnosticReportHelpersKt.reportOn$default(reporter, firExpression2.getSource(), FirJsErrors.INSTANCE.getSPREAD_OPERATOR_IN_DYNAMIC_CALL(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                    }
                }
            }
        }
    }

    private final boolean isArrayAccessWithMultipleIndices(FirCall firCall) {
        List<FirExpression> arguments;
        FirReference calleeReference = FirExpressionUtilKt.getCalleeReference(firCall);
        FirNamedReference firNamedReference = calleeReference instanceof FirNamedReference ? (FirNamedReference) calleeReference : null;
        if (firNamedReference == null) {
            return false;
        }
        KtSourceElement source = firNamedReference.getSource();
        if (!Intrinsics.areEqual(source != null ? source.getKind() : null, KtFakeSourceElementKind.ArrayAccessNameReference.INSTANCE)) {
            return false;
        }
        Object objSingleOrNull = CollectionsKt.singleOrNull((List<? extends Object>) firCall.getArgumentList().getArguments());
        FirVarargArgumentsExpression firVarargArgumentsExpression = objSingleOrNull instanceof FirVarargArgumentsExpression ? (FirVarargArgumentsExpression) objSingleOrNull : null;
        if (firVarargArgumentsExpression == null || (arguments = firVarargArgumentsExpression.getArguments()) == null) {
            return false;
        }
        return (Intrinsics.areEqual(firNamedReference.getName(), OperatorNameConventions.GET) && arguments.size() >= 2) || (Intrinsics.areEqual(firNamedReference.getName(), OperatorNameConventions.SET) && arguments.size() >= 3);
    }

    private final boolean isInOperator(FirFunctionCall firFunctionCall) {
        FirResolvedNamedReference resolved = FirReferenceUtilsKt.getResolved(firFunctionCall.getCalleeReference());
        return Intrinsics.areEqual(resolved != null ? resolved.getName() : null, OperatorNameConventions.CONTAINS) && firFunctionCall.getOrigin() == FirFunctionCallOrigin.Operator;
    }

    private final boolean isRangeOperator(FirFunctionCall firFunctionCall) {
        FirResolvedNamedReference resolved = FirReferenceUtilsKt.getResolved(firFunctionCall.getCalleeReference());
        Name name2 = resolved != null ? resolved.getName() : null;
        return (Intrinsics.areEqual(name2, OperatorNameConventions.RANGE_TO) || Intrinsics.areEqual(name2, OperatorNameConventions.RANGE_UNTIL)) && firFunctionCall.getOrigin() == FirFunctionCallOrigin.Operator;
    }

    private final void checkIdentifier(FirResolvedNamedReference namedReference, DiagnosticReporter reporter, CheckerContext context) {
        String identifierOrNullIfSpecial;
        if (context.get$languageVersionSettings().supportsFeature(LanguageFeature.JsAllowInvalidCharsIdentifiersEscaping) || (identifierOrNullIfSpecial = namedReference.getName().getIdentifierOrNullIfSpecial()) == null || Intrinsics.areEqual(FirJsHelpersKt.sanitizeName(identifierOrNullIfSpecial), identifierOrNullIfSpecial)) {
            return;
        }
        KtDiagnosticReportHelpersKt.reportOn$default(reporter, namedReference.getSource(), FirJsErrors.INSTANCE.getNAME_CONTAINS_ILLEGAL_CHARS(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final void checkSpreadOperator(FirQualifiedAccessExpression expression, CheckerContext context, DiagnosticReporter reporter) {
        FirCall firCall = expression instanceof FirCall ? (FirCall) expression : null;
        if (firCall == null) {
            return;
        }
        for (FirExpression firExpression : firCall.getArgumentList().getArguments()) {
            if (firExpression instanceof FirVarargArgumentsExpression) {
                for (FirExpression firExpression2 : ((FirVarargArgumentsExpression) firExpression).getArguments()) {
                    if ((firExpression2 instanceof FirSpreadArgumentExpression) && (FirTypeUtilsKt.getConeType(firExpression2.getTypeRef()) instanceof ConeDynamicType)) {
                        KtDiagnosticReportHelpersKt.reportOn$default(reporter, firExpression2.getSource(), FirJsErrors.INSTANCE.getWRONG_OPERATION_WITH_DYNAMIC(), "spread operator", context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
                    }
                }
            }
        }
    }
}
