package org.jetbrains.kotlin.fir.analysis.checkers.declaration;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticFactory2;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.FirKeywordUtilsKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.declarations.CheckResult;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.OperatorFunctionChecks;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.lexer.KtModifierKeywordToken;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.name.FqNamesUtilKt;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.StandardClassIds;
import org.jetbrains.kotlin.util.OperatorNameConventions;

/* JADX INFO: compiled from: FirOperatorModifierChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J \u0010\f\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0002¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirOperatorModifierChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirSimpleFunctionChecker;", "()V", "check", "", "declaration", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkReplaceableLegacyOperators", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirOperatorModifierChecker extends FirDeclarationChecker<FirSimpleFunction> {
    public static final FirOperatorModifierChecker INSTANCE = new FirOperatorModifierChecker();

    private FirOperatorModifierChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker
    public void check(FirSimpleFunction declaration, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        if (declaration.getStatus().isOperator()) {
            KtModifierKeywordToken OPERATOR_KEYWORD = KtTokens.OPERATOR_KEYWORD;
            Intrinsics.checkNotNullExpressionValue(OPERATOR_KEYWORD, "OPERATOR_KEYWORD");
            if (FirKeywordUtilsKt.hasModifier(declaration, OPERATOR_KEYWORD)) {
                CheckResult checkResultIsOperator = OperatorFunctionChecks.INSTANCE.isOperator(declaration, context.getSession(), context.getScopeSession());
                if (!Intrinsics.areEqual(checkResultIsOperator, CheckResult.SuccessCheck.INSTANCE)) {
                    if (Intrinsics.areEqual(checkResultIsOperator, CheckResult.IllegalFunctionName.INSTANCE)) {
                        KtDiagnosticReportHelpersKt.reportOn$default(reporter, declaration.getSource(), FirErrors.INSTANCE.getINAPPLICABLE_OPERATOR_MODIFIER(), "illegal function name", context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
                        return;
                    } else if (checkResultIsOperator instanceof CheckResult.IllegalSignature) {
                        KtDiagnosticReportHelpersKt.reportOn$default(reporter, declaration.getSource(), FirErrors.INSTANCE.getINAPPLICABLE_OPERATOR_MODIFIER(), ((CheckResult.IllegalSignature) checkResultIsOperator).getError(), context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
                        return;
                    }
                }
                checkReplaceableLegacyOperators(declaration, context, reporter);
            }
        }
    }

    private final void checkReplaceableLegacyOperators(FirSimpleFunction declaration, CheckerContext context, DiagnosticReporter reporter) {
        KtDiagnosticFactory2<FirBasedSymbol<?>, String> deprecated_binary_mod;
        Name name2 = OperatorNameConventions.MOD_OPERATORS_REPLACEMENT.get(declaration.getName());
        if (name2 == null) {
            return;
        }
        if (FqNamesUtilKt.isSubpackageOf(declaration.getSymbol().getCallableId().getPackageName(), StandardClassIds.INSTANCE.getBASE_KOTLIN_PACKAGE()) || !context.get$languageVersionSettings().supportsFeature(LanguageFeature.ProhibitOperatorMod)) {
            deprecated_binary_mod = FirErrors.INSTANCE.getDEPRECATED_BINARY_MOD();
        } else {
            deprecated_binary_mod = FirErrors.INSTANCE.getFORBIDDEN_BINARY_MOD();
        }
        KtSourceElement source = declaration.getSource();
        FirNamedFunctionSymbol symbol = declaration.getSymbol();
        String strAsString = name2.asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, deprecated_binary_mod, symbol, strAsString, context, (AbstractSourceElementPositioningStrategy) null, 32, (Object) null);
    }
}
