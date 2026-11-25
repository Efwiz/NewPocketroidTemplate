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
import org.jetbrains.kotlin.fir.analysis.checkers.FirKeywordUtilsKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.utils.FirDeclarationUtilKt;
import org.jetbrains.kotlin.lexer.KtModifierKeywordToken;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.util.OperatorNameConventions;

/* JADX INFO: compiled from: FirDataObjectContentChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDataObjectContentChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirSimpleFunctionChecker;", "()V", "check", "", "declaration", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirDataObjectContentChecker extends FirDeclarationChecker<FirSimpleFunction> {
    public static final FirDataObjectContentChecker INSTANCE = new FirDataObjectContentChecker();

    private FirDataObjectContentChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker
    public void check(FirSimpleFunction declaration, CheckerContext context, DiagnosticReporter reporter) {
        KtSourceElement source;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        KtModifierKeywordToken OVERRIDE_KEYWORD = KtTokens.OVERRIDE_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(OVERRIDE_KEYWORD, "OVERRIDE_KEYWORD");
        if (!FirKeywordUtilsKt.hasModifier(declaration, OVERRIDE_KEYWORD) || (source = declaration.getSource()) == null || (source.getKind() instanceof KtFakeSourceElementKind)) {
            return;
        }
        Object objLastOrNull = CollectionsKt.lastOrNull((List<? extends Object>) context.getContainingDeclarations());
        FirClass firClass = objLastOrNull instanceof FirClass ? (FirClass) objLastOrNull : null;
        if (firClass != null && firClass.getClassKind() == ClassKind.OBJECT) {
            KtModifierKeywordToken DATA_KEYWORD = KtTokens.DATA_KEYWORD;
            Intrinsics.checkNotNullExpressionValue(DATA_KEYWORD, "DATA_KEYWORD");
            if (FirKeywordUtilsKt.hasModifier(firClass, DATA_KEYWORD) && FirDeclarationUtilKt.isMethodOfAny(declaration.getSymbol()) && !Intrinsics.areEqual(declaration.getName(), OperatorNameConventions.TO_STRING)) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, FirErrors.INSTANCE.getDATA_OBJECT_CUSTOM_EQUALS_OR_HASH_CODE(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
            }
        }
    }
}
