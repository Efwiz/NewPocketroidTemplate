package org.jetbrains.kotlin.fir.analysis.checkers.syntax;

import com.google.firebase.analytics.FirebaseAnalytics;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtLightSourceElement;
import org.jetbrains.kotlin.KtPsiSourceElement;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.lang.LighterASTNode;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategiesKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.psi.KtFunction;
import org.jetbrains.kotlin.psi.KtTypeParameterList;
import org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt;

/* JADX INFO: compiled from: FirFunctionTypeParametersSyntaxChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J(\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016J0\u0010\u000e\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016J\u0018\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\u0013H\u0016¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/syntax/FirFunctionTypeParametersSyntaxChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/syntax/FirDeclarationSyntaxChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;", "Lorg/jetbrains/kotlin/psi/KtFunction;", "()V", "checkLightTree", "", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtLightSourceElement;", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkPsi", "Lorg/jetbrains/kotlin/KtPsiSourceElement;", "psi", "isApplicable", "", "Lorg/jetbrains/kotlin/KtSourceElement;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirFunctionTypeParametersSyntaxChecker extends FirDeclarationSyntaxChecker<FirSimpleFunction, KtFunction> {
    public static final FirFunctionTypeParametersSyntaxChecker INSTANCE = new FirFunctionTypeParametersSyntaxChecker();

    private FirFunctionTypeParametersSyntaxChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.syntax.FirSyntaxChecker
    public boolean isApplicable(FirSimpleFunction element, KtSourceElement source) {
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(source, "source");
        return !(source.getKind() instanceof KtFakeSourceElementKind);
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.syntax.FirSyntaxChecker
    public void checkPsi(FirSimpleFunction element, KtPsiSourceElement source, KtFunction psi, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(source, "source");
        Intrinsics.checkNotNullParameter(psi, "psi");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        KtTypeParameterList typeParameterList = psi.mo7395getTypeParameterList();
        PsiElement nameIdentifier = psi.mo7392getNameIdentifier();
        if (typeParameterList == null || nameIdentifier == null || PsiUtilsKt.getStartOffset(typeParameterList) <= PsiUtilsKt.getStartOffset(nameIdentifier)) {
            return;
        }
        KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, FirErrors.INSTANCE.getDEPRECATED_TYPE_PARAMETER_SYNTAX(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.syntax.FirSyntaxChecker
    public void checkLightTree(FirSimpleFunction element, KtLightSourceElement source, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(source, "source");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        LighterASTNode lighterASTNodeTypeParametersList = LightTreePositioningStrategiesKt.typeParametersList(source.getTreeStructure(), source.getLighterASTNode());
        LighterASTNode lighterASTNodeNameIdentifier = LightTreePositioningStrategiesKt.nameIdentifier(source.getTreeStructure(), source.getLighterASTNode());
        if (lighterASTNodeTypeParametersList == null || lighterASTNodeNameIdentifier == null || lighterASTNodeTypeParametersList.getStartOffset() <= lighterASTNodeNameIdentifier.getStartOffset()) {
            return;
        }
        KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, FirErrors.INSTANCE.getDEPRECATED_TYPE_PARAMETER_SYNTAX(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
    }
}
