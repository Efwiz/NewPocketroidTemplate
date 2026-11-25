package org.jetbrains.kotlin.fir.analysis.checkers.syntax;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtFakeSourceElement;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtLightSourceElement;
import org.jetbrains.kotlin.KtPsiSourceElement;
import org.jetbrains.kotlin.KtRealPsiSourceElement;
import org.jetbrains.kotlin.KtRealSourceElementKind;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.KtSourceElementKind;
import org.jetbrains.kotlin.KtSourceElementKt;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.lang.LighterASTNode;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategiesKt;
import org.jetbrains.kotlin.fir.analysis.FirSourceUtilsKt;
import org.jetbrains.kotlin.fir.analysis.checkers.FirUnderscoreHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.expressions.FirFunctionCall;
import org.jetbrains.kotlin.fir.types.FirTypeProjection;
import org.jetbrains.kotlin.psi.KtAnnotationEntry;
import org.jetbrains.kotlin.psi.KtTypeProjection;
import org.jetbrains.kotlin.psi.KtTypeReference;

/* JADX INFO: compiled from: FirUnderscoredTypeArgumentSyntaxChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J(\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016J0\u0010\u000e\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016J\u0018\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\u0013H\u0016¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/syntax/FirUnderscoredTypeArgumentSyntaxChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/syntax/FirExpressionSyntaxChecker;", "Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCall;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "()V", "checkLightTree", "", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtLightSourceElement;", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkPsi", "Lorg/jetbrains/kotlin/KtPsiSourceElement;", "psi", "isApplicable", "", "Lorg/jetbrains/kotlin/KtSourceElement;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirUnderscoredTypeArgumentSyntaxChecker extends FirExpressionSyntaxChecker<FirFunctionCall, PsiElement> {
    public static final FirUnderscoredTypeArgumentSyntaxChecker INSTANCE = new FirUnderscoredTypeArgumentSyntaxChecker();

    private FirUnderscoredTypeArgumentSyntaxChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.syntax.FirSyntaxChecker
    public boolean isApplicable(FirFunctionCall element, KtSourceElement source) {
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(source, "source");
        return !element.getTypeArguments().isEmpty();
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.syntax.FirSyntaxChecker
    public void checkPsi(FirFunctionCall element, KtPsiSourceElement source, PsiElement psi, CheckerContext context, DiagnosticReporter reporter) {
        KtTypeReference typeReference;
        KtPsiSourceElement ktFakeSourceElement;
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(source, "source");
        Intrinsics.checkNotNullParameter(psi, "psi");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        Iterator<FirTypeProjection> it = element.getTypeArguments().iterator();
        while (it.hasNext()) {
            KtSourceElement source2 = it.next().getSource();
            PsiElement psi2 = source2 != null ? KtSourceElementKt.getPsi(source2) : null;
            KtTypeProjection ktTypeProjection = psi2 instanceof KtTypeProjection ? (KtTypeProjection) psi2 : null;
            if (ktTypeProjection != null && (typeReference = ktTypeProjection.getTypeReference()) != null && typeReference.isPlaceholder()) {
                for (KtAnnotationEntry ktAnnotationEntry : typeReference.getAnnotationEntries()) {
                    Object obj = (KtSourceElementKind) KtRealSourceElementKind.INSTANCE;
                    if (obj instanceof KtRealSourceElementKind) {
                        ktFakeSourceElement = new KtRealPsiSourceElement(ktAnnotationEntry);
                    } else {
                        if (!(obj instanceof KtFakeSourceElementKind)) {
                            throw new NoWhenBranchMatchedException();
                        }
                        ktFakeSourceElement = new KtFakeSourceElement(ktAnnotationEntry, (KtFakeSourceElementKind) obj);
                    }
                    KtDiagnosticReportHelpersKt.reportOn$default(reporter, ktFakeSourceElement, FirErrors.INSTANCE.getUNSUPPORTED(), "annotations on an underscored type argument", context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
                }
            }
        }
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.syntax.FirSyntaxChecker
    public void checkLightTree(FirFunctionCall element, KtLightSourceElement source, CheckerContext context, DiagnosticReporter reporter) {
        LighterASTNode lighterASTNode;
        List<LighterASTNode> listAnnotations;
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(source, "source");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        Iterator<FirTypeProjection> it = element.getTypeArguments().iterator();
        while (it.hasNext()) {
            KtSourceElement source2 = it.next().getSource();
            if (source2 != null && (lighterASTNode = source2.getLighterASTNode()) != null && FirUnderscoreHelpersKt.isUnderscore(String.valueOf(LightTreePositioningStrategiesKt.userType(source.getTreeStructure(), lighterASTNode))) && (listAnnotations = LightTreePositioningStrategiesKt.annotations(source.getTreeStructure(), lighterASTNode)) != null) {
                Iterator<LighterASTNode> it2 = listAnnotations.iterator();
                while (it2.hasNext()) {
                    KtDiagnosticReportHelpersKt.reportOn$default(reporter, FirSourceUtilsKt.buildChildSourceElement(source, it2.next()), FirErrors.INSTANCE.getUNSUPPORTED(), "annotations on an underscored type argument", context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
                }
            }
        }
    }
}
