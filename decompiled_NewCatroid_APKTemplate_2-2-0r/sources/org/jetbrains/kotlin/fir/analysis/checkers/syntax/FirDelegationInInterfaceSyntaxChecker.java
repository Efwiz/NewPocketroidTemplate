package org.jetbrains.kotlin.fir.analysis.checkers.syntax;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtLightSourceElement;
import org.jetbrains.kotlin.KtNodeTypes;
import org.jetbrains.kotlin.KtPsiSourceElement;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.KtSourceElementKt;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.lang.LighterASTNode;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.psi.KtClass;
import org.jetbrains.kotlin.psi.KtDelegatedSuperTypeEntry;

/* JADX INFO: compiled from: FirDelegationInInterfaceSyntaxChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J(\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016J0\u0010\u000e\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016J\u0018\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\u0013H\u0016¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/syntax/FirDelegationInInterfaceSyntaxChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/syntax/FirDeclarationSyntaxChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "Lorg/jetbrains/kotlin/psi/KtClass;", "()V", "checkLightTree", "", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtLightSourceElement;", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkPsi", "Lorg/jetbrains/kotlin/KtPsiSourceElement;", "psi", "isApplicable", "", "Lorg/jetbrains/kotlin/KtSourceElement;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirDelegationInInterfaceSyntaxChecker extends FirDeclarationSyntaxChecker<FirRegularClass, KtClass> {
    public static final FirDelegationInInterfaceSyntaxChecker INSTANCE = new FirDelegationInInterfaceSyntaxChecker();

    private FirDelegationInInterfaceSyntaxChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.syntax.FirSyntaxChecker
    public boolean isApplicable(FirRegularClass element, KtSourceElement source) {
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(source, "source");
        return element.getClassKind() == ClassKind.INTERFACE;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.syntax.FirSyntaxChecker
    public void checkPsi(FirRegularClass element, KtPsiSourceElement source, KtClass psi, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(source, "source");
        Intrinsics.checkNotNullParameter(psi, "psi");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        Iterator<FirTypeRef> it = element.getSuperTypeRefs().iterator();
        while (it.hasNext()) {
            KtSourceElement source2 = it.next().getSource();
            if (source2 != null) {
                KtSourceElement ktSourceElement = source2;
                PsiElement psi2 = KtSourceElementKt.getPsi(ktSourceElement);
                if ((psi2 != null ? psi2.getLightParent() : null) instanceof KtDelegatedSuperTypeEntry) {
                    KtDiagnosticReportHelpersKt.reportOn$default(reporter, ktSourceElement, FirErrors.INSTANCE.getDELEGATION_IN_INTERFACE(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                }
            }
        }
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.syntax.FirSyntaxChecker
    public void checkLightTree(FirRegularClass element, KtLightSourceElement source, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(source, "source");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        Iterator<FirTypeRef> it = element.getSuperTypeRefs().iterator();
        while (it.hasNext()) {
            KtSourceElement source2 = it.next().getSource();
            if (source2 != null) {
                LighterASTNode parent = source2.getTreeStructure().getParent(source2.getLighterASTNode());
                if (Intrinsics.areEqual(parent != null ? parent.getTokenType() : null, KtNodeTypes.DELEGATED_SUPER_TYPE_ENTRY)) {
                    KtDiagnosticReportHelpersKt.reportOn$default(reporter, source2, FirErrors.INSTANCE.getDELEGATION_IN_INTERFACE(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                }
            }
        }
    }
}
