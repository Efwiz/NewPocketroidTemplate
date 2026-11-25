package org.jetbrains.kotlin.fir.analysis.checkers.expression;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticFactory0;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.FirLanguageSettingsComponentKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirArgumentList;
import org.jetbrains.kotlin.fir.expressions.FirArrayLiteral;
import org.jetbrains.kotlin.fir.expressions.FirCall;
import org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirFunctionCall;
import org.jetbrains.kotlin.fir.expressions.FirNamedArgumentExpression;
import org.jetbrains.kotlin.fir.expressions.FirResolvable;
import org.jetbrains.kotlin.fir.expressions.FirVarargArgumentsExpression;
import org.jetbrains.kotlin.fir.expressions.impl.FirResolvedArgumentList;
import org.jetbrains.kotlin.fir.references.FirErrorNamedReference;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.FirErrorTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;

/* JADX INFO: compiled from: FirNamedVarargChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirNamedVarargChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirExpressionChecker;", "Lorg/jetbrains/kotlin/fir/expressions/FirCall;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirCallChecker;", "()V", "check", "", "expression", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirNamedVarargChecker extends FirExpressionChecker<FirCall> {
    public static final FirNamedVarargChecker INSTANCE = new FirNamedVarargChecker();

    private FirNamedVarargChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.FirExpressionChecker
    public void check(FirCall expression, CheckerContext context, DiagnosticReporter reporter) {
        KtDiagnosticFactory0 redundant_spread_operator_in_named_form_in_function;
        LanguageFeature languageFeature;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        if ((expression instanceof FirFunctionCall) || (expression instanceof FirAnnotation) || (expression instanceof FirDelegatedConstructorCall) || (expression instanceof FirArrayLiteral)) {
            boolean z = expression instanceof FirAnnotation;
            if (z) {
                redundant_spread_operator_in_named_form_in_function = FirErrors.INSTANCE.getREDUNDANT_SPREAD_OPERATOR_IN_NAMED_FORM_IN_ANNOTATION();
            } else {
                redundant_spread_operator_in_named_form_in_function = FirErrors.INSTANCE.getREDUNDANT_SPREAD_OPERATOR_IN_NAMED_FORM_IN_FUNCTION();
            }
            KtDiagnosticFactory0 ktDiagnosticFactory0 = redundant_spread_operator_in_named_form_in_function;
            LanguageVersionSettings languageVersionSettings = FirLanguageSettingsComponentKt.getLanguageVersionSettings(context.getSession());
            if (z) {
                languageFeature = LanguageFeature.AssigningArraysToVarargsInNamedFormInAnnotations;
            } else {
                languageFeature = LanguageFeature.AllowAssigningArrayElementsToVarargsInNamedFormForFunctions;
            }
            boolean zSupportsFeature = languageVersionSettings.supportsFeature(languageFeature);
            if (expression instanceof FirArrayLiteral) {
                for (FirExpression firExpression : expression.getArgumentList().getArguments()) {
                    check$checkArgument(expression, reporter, ktDiagnosticFactory0, context, zSupportsFeature, z, firExpression, firExpression instanceof FirNamedArgumentExpression, FirTypeUtilsKt.getConeTypeOrNull(((FirArrayLiteral) expression).getTypeRef()));
                }
                return;
            }
            FirArgumentList argumentList = expression.getArgumentList();
            LinkedHashMap<FirExpression, FirValueParameter> mapping = argumentList instanceof FirResolvedArgumentList ? ((FirResolvedArgumentList) argumentList).getMapping() : null;
            if (mapping == null) {
                return;
            }
            for (Map.Entry<FirExpression, FirValueParameter> entry : mapping.entrySet()) {
                FirExpression key = entry.getKey();
                FirValueParameter value = entry.getValue();
                if (value.getIsVararg()) {
                    if (!(key instanceof FirVarargArgumentsExpression)) {
                        check$checkArgument(expression, reporter, ktDiagnosticFactory0, context, zSupportsFeature, z, key, false, FirTypeUtilsKt.getConeType(value.getReturnTypeRef()));
                    } else {
                        Iterator<T> it = ((FirVarargArgumentsExpression) key).getArguments().iterator();
                        while (it.hasNext()) {
                            check$checkArgument(expression, reporter, ktDiagnosticFactory0, context, zSupportsFeature, z, (FirExpression) it.next(), true, FirTypeUtilsKt.getConeType(value.getReturnTypeRef()));
                        }
                    }
                }
            }
        }
    }

    private static final void check$checkArgument(FirCall firCall, DiagnosticReporter diagnosticReporter, KtDiagnosticFactory0 ktDiagnosticFactory0, CheckerContext checkerContext, boolean z, boolean z2, FirExpression firExpression, boolean z3, ConeKotlinType coneKotlinType) {
        if (firExpression instanceof FirNamedArgumentExpression) {
            FirNamedArgumentExpression firNamedArgumentExpression = (FirNamedArgumentExpression) firExpression;
            if (firNamedArgumentExpression.getIsSpread()) {
                if (z3) {
                    FirResolvable firResolvable = firCall instanceof FirResolvable ? (FirResolvable) firCall : null;
                    if ((firResolvable != null ? firResolvable.getCalleeReference() : null) instanceof FirErrorNamedReference) {
                        return;
                    }
                    KtDiagnosticReportHelpersKt.reportOn$default(diagnosticReporter, firNamedArgumentExpression.getExpression().getSource(), ktDiagnosticFactory0, checkerContext, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                    return;
                }
                return;
            }
            FirTypeRef typeRef = firNamedArgumentExpression.getExpression().getTypeRef();
            if ((typeRef instanceof FirErrorTypeRef) || (firNamedArgumentExpression.getExpression() instanceof FirArrayLiteral)) {
                return;
            }
            if (z && FirTypeUtilsKt.isArrayType(typeRef)) {
                return;
            }
            if (z2) {
                KtDiagnosticReportHelpersKt.reportOn$default(diagnosticReporter, firNamedArgumentExpression.getExpression().getSource(), FirErrors.INSTANCE.getASSIGNING_SINGLE_ELEMENT_TO_VARARG_IN_NAMED_FORM_ANNOTATION(), checkerContext, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
            } else {
                if (coneKotlinType == null) {
                    throw new IllegalArgumentException("expectedArrayType must be passed for function call".toString());
                }
                KtDiagnosticReportHelpersKt.reportOn$default(diagnosticReporter, firNamedArgumentExpression.getExpression().getSource(), FirErrors.INSTANCE.getASSIGNING_SINGLE_ELEMENT_TO_VARARG_IN_NAMED_FORM_FUNCTION(), coneKotlinType, checkerContext, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
            }
        }
    }
}
