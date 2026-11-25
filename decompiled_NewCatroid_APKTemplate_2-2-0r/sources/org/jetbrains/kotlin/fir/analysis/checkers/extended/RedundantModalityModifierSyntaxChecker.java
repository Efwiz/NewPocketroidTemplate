package org.jetbrains.kotlin.fir.analysis.checkers.extended;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtRealSourceElementKind;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategiesKt;
import org.jetbrains.kotlin.fir.analysis.checkers.FirHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.checkers.syntax.FirDeclarationSyntaxChecker;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.psi.KtDeclaration;

/* JADX INFO: compiled from: RedundantModalityModifierSyntaxChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J(\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u00022\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J\u0018\u0010\u0011\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u00022\u0006\u0010\u000b\u001a\u00020\fH\u0016R\u0018\u0010\u0005\u001a\u00020\u0006*\u00020\u00028BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0007¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/extended/RedundantModalityModifierSyntaxChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/syntax/FirDeclarationSyntaxChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "()V", "isMemberWithRealSource", "", "(Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;)Z", "checkPsiOrLightTree", "", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "isApplicable", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class RedundantModalityModifierSyntaxChecker extends FirDeclarationSyntaxChecker<FirDeclaration, KtDeclaration> {
    public static final RedundantModalityModifierSyntaxChecker INSTANCE = new RedundantModalityModifierSyntaxChecker();

    private RedundantModalityModifierSyntaxChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.syntax.FirSyntaxChecker
    public boolean isApplicable(FirDeclaration element, KtSourceElement source) {
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(source, "source");
        if (!isMemberWithRealSource(element) || (element instanceof FirValueParameter)) {
            KtSourceElement source2 = element.getSource();
            if (!Intrinsics.areEqual(source2 != null ? source2.getKind() : null, KtFakeSourceElementKind.PropertyFromParameter.INSTANCE)) {
                return false;
            }
        }
        return true;
    }

    private final boolean isMemberWithRealSource(FirDeclaration firDeclaration) {
        if (firDeclaration instanceof FirMemberDeclaration) {
            KtSourceElement source = firDeclaration.getSource();
            if ((source != null ? source.getKind() : null) instanceof KtRealSourceElementKind) {
                return true;
            }
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.syntax.FirSyntaxChecker
    public void checkPsiOrLightTree(FirDeclaration element, KtSourceElement source, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(source, "source");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        if (!(element instanceof FirMemberDeclaration)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        FirMemberDeclaration firMemberDeclaration = (FirMemberDeclaration) element;
        Modality modality = firMemberDeclaration.getStatus().getModality();
        if (modality == null) {
            return;
        }
        if (modality == Modality.FINAL) {
            Object objLast = CollectionsKt.last((List<? extends Object>) context.getContainingDeclarations());
            FirClass firClass = objLast instanceof FirClass ? (FirClass) objLast : null;
            if ((firClass != null ? firClass.getClassKind() : null) == ClassKind.INTERFACE) {
                return;
            }
        }
        if (LightTreePositioningStrategiesKt.modalityModifier(source.getTreeStructure(), source.getLighterASTNode()) != null && FirHelpersKt.redundantModalities(firMemberDeclaration, context).contains(modality)) {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, FirErrors.INSTANCE.getREDUNDANT_MODALITY_MODIFIER(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
        }
    }
}
