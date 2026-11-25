package org.jetbrains.kotlin.fir.analysis.checkers.type;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Iterator;
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
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.lang.LighterASTNode;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategiesKt;
import org.jetbrains.kotlin.fir.FirFunctionTypeParameter;
import org.jetbrains.kotlin.fir.analysis.checkers.FirKeywordUtilsKt;
import org.jetbrains.kotlin.fir.analysis.checkers.FirModifier;
import org.jetbrains.kotlin.fir.analysis.checkers.FirModifierList;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.psi.KtValVarKeywordOwner;

/* JADX INFO: compiled from: FirUnsupportedModifiersInFunctionTypeParameterChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0002J(\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u0007\u001a\u00020\bH\u0016J \u0010\u000f\u001a\u00020\f2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0002¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/type/FirUnsupportedModifiersInFunctionTypeParameterChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/type/FirFunctionalTypeParameterSyntaxChecker;", "()V", "checkModifiers", "", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "checkPsiOrLightTree", "", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/fir/FirFunctionTypeParameter;", "checkValOrVarKeyword", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirUnsupportedModifiersInFunctionTypeParameterChecker extends FirFunctionalTypeParameterSyntaxChecker {
    public static final FirUnsupportedModifiersInFunctionTypeParameterChecker INSTANCE = new FirUnsupportedModifiersInFunctionTypeParameterChecker();

    private FirUnsupportedModifiersInFunctionTypeParameterChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.syntax.FirSyntaxChecker
    public void checkPsiOrLightTree(FirFunctionTypeParameter element, KtSourceElement source, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(source, "source");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        checkModifiers(source, reporter, context);
        checkValOrVarKeyword(source, reporter, context);
    }

    private final void checkValOrVarKeyword(KtSourceElement source, DiagnosticReporter reporter, CheckerContext context) {
        KtSourceElement ktLightSourceElement;
        PsiElement valOrVarKeyword;
        KtFakeSourceElement ktFakeSourceElement = null;
        if (source instanceof KtPsiSourceElement) {
            PsiElement psi = ((KtPsiSourceElement) source).getPsi();
            KtValVarKeywordOwner ktValVarKeywordOwner = psi instanceof KtValVarKeywordOwner ? (KtValVarKeywordOwner) psi : null;
            if (ktValVarKeywordOwner != null && (valOrVarKeyword = ktValVarKeywordOwner.getValOrVarKeyword()) != null) {
                Object obj = (KtSourceElementKind) KtRealSourceElementKind.INSTANCE;
                if (obj instanceof KtRealSourceElementKind) {
                    ktFakeSourceElement = new KtRealPsiSourceElement(valOrVarKeyword);
                } else {
                    if (!(obj instanceof KtFakeSourceElementKind)) {
                        throw new NoWhenBranchMatchedException();
                    }
                    ktFakeSourceElement = new KtFakeSourceElement(valOrVarKeyword, (KtFakeSourceElementKind) obj);
                }
            }
            ktLightSourceElement = ktFakeSourceElement;
        } else if (source instanceof KtLightSourceElement) {
            LighterASTNode lighterASTNodeValOrVarKeyword = LightTreePositioningStrategiesKt.valOrVarKeyword(source.getTreeStructure(), source.getLighterASTNode());
            ktLightSourceElement = lighterASTNodeValOrVarKeyword != null ? new KtLightSourceElement(lighterASTNodeValOrVarKeyword, lighterASTNodeValOrVarKeyword.getStartOffset(), lighterASTNodeValOrVarKeyword.getEndOffset(), source.getTreeStructure(), KtRealSourceElementKind.INSTANCE) : null;
        } else {
            throw new NoWhenBranchMatchedException();
        }
        if (ktLightSourceElement == null) {
            return;
        }
        KtDiagnosticReportHelpersKt.reportOn$default(reporter, ktLightSourceElement, FirErrors.INSTANCE.getUNSUPPORTED(), "val or var on parameter in function type", context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
    }

    private final boolean checkModifiers(KtSourceElement source, DiagnosticReporter reporter, CheckerContext context) {
        FirModifierList modifierList = FirKeywordUtilsKt.getModifierList(source);
        if (modifierList == null) {
            return true;
        }
        Iterator<FirModifier<?>> it = modifierList.getModifiers().iterator();
        while (it.hasNext()) {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, it.next().getSource(), FirErrors.INSTANCE.getUNSUPPORTED(), "modifier on parameter in function type", context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
        }
        return false;
    }
}
