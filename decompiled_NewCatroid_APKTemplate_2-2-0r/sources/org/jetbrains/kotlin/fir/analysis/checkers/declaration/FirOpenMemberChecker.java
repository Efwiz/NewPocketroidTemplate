package org.jetbrains.kotlin.fir.analysis.checkers.declaration;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtRealSourceElementKind;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.KtSourceElementKind;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.FirKeywordUtilsKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration;
import org.jetbrains.kotlin.lexer.KtModifierKeywordToken;
import org.jetbrains.kotlin.lexer.KtTokens;

/* JADX INFO: compiled from: FirOpenMemberChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016R\u0018\u0010\u0005\u001a\u00020\u0006*\u00020\u00078BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\t¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirOpenMemberChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirClassChecker;", "()V", "shouldReportOpenFromSource", "", "Lorg/jetbrains/kotlin/KtSourceElement;", "getShouldReportOpenFromSource", "(Lorg/jetbrains/kotlin/KtSourceElement;)Z", "check", "", "declaration", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirOpenMemberChecker extends FirDeclarationChecker<FirClass> {
    public static final FirOpenMemberChecker INSTANCE = new FirOpenMemberChecker();

    private FirOpenMemberChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker
    public void check(FirClass declaration, CheckerContext context, DiagnosticReporter reporter) {
        KtSourceElement source;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        if (DeclarationUtilsKt.getCanHaveOpenMembers(declaration)) {
            return;
        }
        for (FirDeclaration firDeclaration : declaration.getDeclarations()) {
            if ((firDeclaration instanceof FirCallableDeclaration) && !(firDeclaration instanceof FirConstructor) && (source = firDeclaration.getSource()) != null) {
                FirMemberDeclaration firMemberDeclaration = (FirMemberDeclaration) firDeclaration;
                if (firMemberDeclaration.getStatus().getModality() != Modality.OPEN || firMemberDeclaration.getStatus().isOverride() || declaration.getClassKind() != ClassKind.ANNOTATION_CLASS) {
                    KtModifierKeywordToken OPEN_KEYWORD = KtTokens.OPEN_KEYWORD;
                    Intrinsics.checkNotNullExpressionValue(OPEN_KEYWORD, "OPEN_KEYWORD");
                    if (!FirKeywordUtilsKt.hasModifier(firDeclaration, OPEN_KEYWORD) || !getShouldReportOpenFromSource(source)) {
                    }
                }
                if (declaration.getClassKind() == ClassKind.OBJECT) {
                    KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, FirErrors.INSTANCE.getNON_FINAL_MEMBER_IN_OBJECT(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                } else {
                    KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, FirErrors.INSTANCE.getNON_FINAL_MEMBER_IN_FINAL_CLASS(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                }
            }
        }
    }

    private final boolean getShouldReportOpenFromSource(KtSourceElement ktSourceElement) {
        KtSourceElementKind kind = ktSourceElement.getKind();
        if (Intrinsics.areEqual(kind, KtRealSourceElementKind.INSTANCE)) {
            return true;
        }
        return Intrinsics.areEqual(kind, KtFakeSourceElementKind.PropertyFromParameter.INSTANCE);
    }
}
