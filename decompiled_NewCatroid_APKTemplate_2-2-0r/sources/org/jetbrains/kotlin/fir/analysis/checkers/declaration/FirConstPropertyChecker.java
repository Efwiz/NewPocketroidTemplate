package org.jetbrains.kotlin.fir.analysis.checkers.declaration;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.FirConstChecksKt;
import org.jetbrains.kotlin.fir.analysis.checkers.FirKeywordUtilsKt;
import org.jetbrains.kotlin.fir.analysis.checkers.FirModifier;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.resolve.TypeExpansionUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeErrorType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.lexer.KtModifierKeywordToken;
import org.jetbrains.kotlin.lexer.KtTokens;

/* JADX INFO: compiled from: FirConstPropertyChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirConstPropertyChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirPropertyChecker;", "()V", "check", "", "declaration", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirConstPropertyChecker extends FirDeclarationChecker<FirProperty> {
    public static final FirConstPropertyChecker INSTANCE = new FirConstPropertyChecker();

    private FirConstPropertyChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker
    public void check(FirProperty declaration, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        if (declaration.getStatus().isConst()) {
            if (declaration.getIsVar()) {
                KtModifierKeywordToken CONST_KEYWORD = KtTokens.CONST_KEYWORD;
                Intrinsics.checkNotNullExpressionValue(CONST_KEYWORD, "CONST_KEYWORD");
                FirModifier<?> modifier = FirKeywordUtilsKt.getModifier(declaration, CONST_KEYWORD);
                if (modifier != null) {
                    KtDiagnosticReportHelpersKt.reportOn$default(reporter, modifier.getSource(), FirErrors.INSTANCE.getWRONG_MODIFIER_TARGET(), modifier.getToken(), "vars", context, (AbstractSourceElementPositioningStrategy) null, 32, (Object) null);
                }
            }
            Object objLastOrNull = CollectionsKt.lastOrNull((List<? extends Object>) context.getContainingDeclarations());
            FirRegularClass firRegularClass = objLastOrNull instanceof FirRegularClass ? (FirRegularClass) objLastOrNull : null;
            if ((firRegularClass != null ? firRegularClass.getClassKind() : null) != ClassKind.OBJECT && context.getContainingDeclarations().size() > 1) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, declaration.getSource(), FirErrors.INSTANCE.getCONST_VAL_NOT_TOP_LEVEL_OR_OBJECT(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                return;
            }
            FirPropertyAccessor getter = declaration.getGetter();
            KtSourceElement source = getter != null ? getter.getSource() : null;
            if (source != null && !(source.getKind() instanceof KtFakeSourceElementKind)) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, FirErrors.INSTANCE.getCONST_VAL_WITH_GETTER(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                return;
            }
            if (declaration.getDelegate() != null) {
                FirExpression delegate = declaration.getDelegate();
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, delegate != null ? delegate.getSource() : null, FirErrors.INSTANCE.getCONST_VAL_WITH_DELEGATE(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                return;
            }
            FirExpression initializer = declaration.getInitializer();
            if (initializer == null) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, declaration.getSource(), FirErrors.INSTANCE.getCONST_VAL_WITHOUT_INITIALIZER(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                return;
            }
            ConeKotlinType coneKotlinTypeFullyExpandedType = TypeExpansionUtilsKt.fullyExpandedType(FirTypeUtilsKt.getConeType(declaration.getReturnTypeRef()), context.getSession());
            if (!(coneKotlinTypeFullyExpandedType instanceof ConeErrorType) && !FirConstChecksKt.canBeUsedForConstVal(coneKotlinTypeFullyExpandedType)) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, declaration.getSource(), FirErrors.INSTANCE.getTYPE_CANT_BE_USED_FOR_CONST_VAL(), FirTypeUtilsKt.getConeType(declaration.getReturnTypeRef()), context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
            } else if (FirConstChecksKt.checkConstantArguments(initializer, context.getSession()) != null) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, initializer.getSource(), FirErrors.INSTANCE.getCONST_VAL_WITH_NON_CONST_INITIALIZER(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
            }
        }
    }
}
