package org.jetbrains.kotlin.fir.analysis.checkers.expression;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.AbstractKtSourceElement;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtRealSourceElementKind;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.KtSourceElementKind;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticContext;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticFactory4;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.FirHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.DeclarationUtilsKt;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirErrorFunction;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
import org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor;
import org.jetbrains.kotlin.fir.expressions.ExhaustivenessStatusKt;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirReturnExpression;
import org.jetbrains.kotlin.fir.expressions.FirSmartCastExpression;
import org.jetbrains.kotlin.fir.expressions.FirWhenExpression;
import org.jetbrains.kotlin.fir.types.ConeBuiltinTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeInferenceContext;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeNullability;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.InferenceUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeComponentsKt;

/* JADX INFO: compiled from: FirFunctionReturnTypeMismatchChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirFunctionReturnTypeMismatchChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirExpressionChecker;", "Lorg/jetbrains/kotlin/fir/expressions/FirReturnExpression;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirReturnExpressionChecker;", "()V", "check", "", "expression", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirFunctionReturnTypeMismatchChecker extends FirExpressionChecker<FirReturnExpression> {
    public static final FirFunctionReturnTypeMismatchChecker INSTANCE = new FirFunctionReturnTypeMismatchChecker();

    private FirFunctionReturnTypeMismatchChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.FirExpressionChecker
    public void check(FirReturnExpression expression, CheckerContext context, DiagnosticReporter reporter) {
        ConeKotlinType coneType;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        KtSourceElement source = expression.getSource();
        if (Intrinsics.areEqual(source != null ? source.getKind() : null, KtFakeSourceElementKind.DelegatedPropertyAccessor.INSTANCE)) {
            return;
        }
        FirFunction firFunction = (FirFunction) expression.getTarget().getLabeledElement();
        if (firFunction instanceof FirErrorFunction) {
            return;
        }
        boolean z = firFunction instanceof FirAnonymousFunction;
        if (z && ((FirAnonymousFunction) firFunction).getIsLambda()) {
            return;
        }
        KtSourceElement source2 = expression.getSource();
        KtSourceElementKind kind = source2 != null ? source2.getKind() : null;
        if (DeclarationUtilsKt.getHasExplicitReturnType(firFunction.getSymbol()) || Intrinsics.areEqual(kind, KtRealSourceElementKind.INSTANCE) || (firFunction instanceof FirPropertyAccessor)) {
            FirExpression result = expression.getResult();
            if (!(result instanceof FirWhenExpression) || ExhaustivenessStatusKt.isExhaustive((FirWhenExpression) result)) {
                if (firFunction instanceof FirConstructor) {
                    coneType = FirTypeUtilsKt.getConeType(context.getSession().getBuiltinTypes().getUnitType());
                } else {
                    coneType = FirTypeUtilsKt.getConeType(firFunction.getReturnTypeRef());
                }
                ConeKotlinType coneKotlinType = coneType;
                if (z && (kind instanceof KtFakeSourceElementKind.ImplicitReturn.FromLastStatement) && ConeBuiltinTypeUtilsKt.isUnit(coneKotlinType)) {
                    return;
                }
                ConeInferenceContext typeContext = TypeComponentsKt.getTypeContext(context.getSession());
                ConeKotlinType coneType2 = FirTypeUtilsKt.getConeType(result.getTypeRef());
                if (!FirHelpersKt.isSubtypeForTypeMismatch(typeContext, coneType2, coneKotlinType)) {
                    if (FirTypeUtilsKt.isNullLiteral(result) && coneKotlinType.getNullability() == ConeNullability.NOT_NULL) {
                        KtDiagnosticReportHelpersKt.reportOn$default(reporter, result.getSource(), FirErrors.INSTANCE.getNULL_FOR_NONNULL_TYPE(), coneKotlinType, context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
                        return;
                    }
                    boolean zIsTypeMismatchDueToNullability = InferenceUtilsKt.isTypeMismatchDueToNullability(TypeComponentsKt.getTypeContext(context.getSession()), coneType2, coneKotlinType);
                    if (result instanceof FirSmartCastExpression) {
                        FirSmartCastExpression firSmartCastExpression = (FirSmartCastExpression) result;
                        if (!firSmartCastExpression.isStable() && FirHelpersKt.isSubtypeForTypeMismatch(typeContext, FirTypeUtilsKt.getConeType(firSmartCastExpression.getSmartcastType()), coneKotlinType)) {
                            KtDiagnosticReportHelpersKt.reportOn(reporter, (AbstractKtSourceElement) result.getSource(), (KtDiagnosticFactory4<ConeKotlinType, FirExpression, String, Boolean>) ((KtDiagnosticFactory4<Object, Object, Object, Object>) FirErrors.INSTANCE.getSMARTCAST_IMPOSSIBLE()), coneKotlinType, result, firSmartCastExpression.getSmartcastStability().getDescription(), Boolean.valueOf(zIsTypeMismatchDueToNullability), (DiagnosticContext) context, (128 & 128) != 0 ? null : null);
                            return;
                        }
                    }
                    KtDiagnosticReportHelpersKt.reportOn(reporter, (AbstractKtSourceElement) result.getSource(), (KtDiagnosticFactory4<ConeKotlinType, ConeKotlinType, FirFunction, Boolean>) ((KtDiagnosticFactory4<Object, Object, Object, Object>) FirErrors.INSTANCE.getRETURN_TYPE_MISMATCH()), coneKotlinType, coneType2, firFunction, Boolean.valueOf(zIsTypeMismatchDueToNullability), (DiagnosticContext) context, (128 & 128) != 0 ? null : null);
                    return;
                }
                KtSourceElement source3 = result.getSource();
                if (!((source3 != null ? source3.getKind() : null) instanceof KtFakeSourceElementKind.ImplicitUnit) || ConeBuiltinTypeUtilsKt.isUnit(coneKotlinType)) {
                    return;
                }
                KtDiagnosticReportHelpersKt.reportOn(reporter, (AbstractKtSourceElement) result.getSource(), (KtDiagnosticFactory4<ConeKotlinType, ConeKotlinType, FirFunction, boolean>) ((KtDiagnosticFactory4<Object, Object, Object, Object>) FirErrors.INSTANCE.getRETURN_TYPE_MISMATCH()), coneKotlinType, coneType2, firFunction, false, (DiagnosticContext) context, (128 & 128) != 0 ? null : null);
            }
        }
    }
}
