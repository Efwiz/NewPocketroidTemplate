package org.jetbrains.kotlin.fir.analysis.checkers.syntax;

import com.google.firebase.analytics.FirebaseAnalytics;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtLightSourceElement;
import org.jetbrains.kotlin.KtPsiSourceElement;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;

/* JADX INFO: compiled from: FirSyntaxChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0002\bf\u0018\u0000*\n\b\u0000\u0010\u0001 \u0000*\u00020\u0002*\b\b\u0001\u0010\u0003*\u00020\u00042\u00020\u0005J-\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00028\u00002\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016¢\u0006\u0002\u0010\u000fJ5\u0010\u0010\u001a\u00020\u00072\u0006\u0010\b\u001a\u00028\u00002\u0006\u0010\t\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00028\u00012\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016¢\u0006\u0002\u0010\u0013J-\u0010\u0014\u001a\u00020\u00072\u0006\u0010\b\u001a\u00028\u00002\u0006\u0010\t\u001a\u00020\u00152\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016¢\u0006\u0002\u0010\u0016J%\u0010\u0017\u001a\u00020\u00072\u0006\u0010\b\u001a\u00028\u00002\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016¢\u0006\u0002\u0010\u0018J\u001d\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\b\u001a\u00028\u00002\u0006\u0010\t\u001a\u00020\u0015H\u0016¢\u0006\u0002\u0010\u001bø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u001cÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/syntax/FirSyntaxChecker;", "D", "Lorg/jetbrains/kotlin/fir/FirElement;", "P", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "", "checkLightTree", "", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtLightSourceElement;", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "(Lorg/jetbrains/kotlin/fir/FirElement;Lorg/jetbrains/kotlin/KtLightSourceElement;Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;)V", "checkPsi", "Lorg/jetbrains/kotlin/KtPsiSourceElement;", "psi", "(Lorg/jetbrains/kotlin/fir/FirElement;Lorg/jetbrains/kotlin/KtPsiSourceElement;Lcom/intellij/psi/PsiElement;Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;)V", "checkPsiOrLightTree", "Lorg/jetbrains/kotlin/KtSourceElement;", "(Lorg/jetbrains/kotlin/fir/FirElement;Lorg/jetbrains/kotlin/KtSourceElement;Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;)V", "checkSyntax", "(Lorg/jetbrains/kotlin/fir/FirElement;Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;)V", "isApplicable", "", "(Lorg/jetbrains/kotlin/fir/FirElement;Lorg/jetbrains/kotlin/KtSourceElement;)Z", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface FirSyntaxChecker<D extends FirElement, P extends PsiElement> {
    default void checkPsiOrLightTree(D element, KtSourceElement source, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(source, "source");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
    }

    default boolean isApplicable(D element, KtSourceElement source) {
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(source, "source");
        return true;
    }

    default void checkSyntax(D element, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        KtSourceElement source = element.getSource();
        if (source != null && isApplicable(element, source)) {
            if (!(source instanceof KtPsiSourceElement)) {
                if (source instanceof KtLightSourceElement) {
                    checkLightTree(element, (KtLightSourceElement) source, context, reporter);
                }
            } else {
                KtPsiSourceElement ktPsiSourceElement = (KtPsiSourceElement) source;
                PsiElement psi = ktPsiSourceElement.getPsi();
                Intrinsics.checkNotNull(psi, "null cannot be cast to non-null type P of org.jetbrains.kotlin.fir.analysis.checkers.syntax.FirSyntaxChecker");
                checkPsi(element, ktPsiSourceElement, psi, context, reporter);
            }
        }
    }

    default void checkPsi(D element, KtPsiSourceElement source, P psi, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(source, "source");
        Intrinsics.checkNotNullParameter(psi, "psi");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        checkPsiOrLightTree(element, source, context, reporter);
    }

    default void checkLightTree(D element, KtLightSourceElement source, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(source, "source");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        checkPsiOrLightTree(element, source, context, reporter);
    }
}
