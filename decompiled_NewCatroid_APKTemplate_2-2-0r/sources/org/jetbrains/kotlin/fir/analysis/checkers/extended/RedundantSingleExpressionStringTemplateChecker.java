package org.jetbrains.kotlin.fir.analysis.checkers.extended;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtLightSourceElement;
import org.jetbrains.kotlin.KtNodeTypes;
import org.jetbrains.kotlin.KtPsiSourceElement;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.com.intellij.lang.LighterASTNode;
import org.jetbrains.kotlin.com.intellij.lang.PsiBuilder;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.checkers.expression.FirExpressionChecker;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.expressions.FirStringConcatenationCall;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.name.StandardClassIds;
import org.jetbrains.kotlin.psi.KtStringTemplateExpression;
import org.jetbrains.kotlin.util.LightTreeUtilsKt;

/* JADX INFO: compiled from: RedundantSingleExpressionStringTemplateChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u001b\u0010\f\u001a\u0004\u0018\u00010\r*\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0002¢\u0006\u0002\u0010\u0011J\u0013\u0010\f\u001a\u0004\u0018\u00010\r*\u00020\u0012H\u0002¢\u0006\u0002\u0010\u0013J\u0013\u0010\f\u001a\u0004\u0018\u00010\r*\u00020\u0014H\u0002¢\u0006\u0002\u0010\u0015¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/extended/RedundantSingleExpressionStringTemplateChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirExpressionChecker;", "Lorg/jetbrains/kotlin/fir/expressions/FirStringConcatenationCall;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirStringConcatenationCallChecker;", "()V", "check", "", "expression", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "stringParentChildrenCount", "", "Lorg/jetbrains/kotlin/com/intellij/lang/LighterASTNode;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtLightSourceElement;", "(Lcom/intellij/lang/LighterASTNode;Lorg/jetbrains/kotlin/KtLightSourceElement;)Ljava/lang/Integer;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "(Lcom/intellij/psi/PsiElement;)Ljava/lang/Integer;", "Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "(Lorg/jetbrains/kotlin/fir/expressions/FirStatement;)Ljava/lang/Integer;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class RedundantSingleExpressionStringTemplateChecker extends FirExpressionChecker<FirStringConcatenationCall> {
    public static final RedundantSingleExpressionStringTemplateChecker INSTANCE = new RedundantSingleExpressionStringTemplateChecker();

    private RedundantSingleExpressionStringTemplateChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.FirExpressionChecker
    public void check(FirStringConcatenationCall expression, CheckerContext context, DiagnosticReporter reporter) {
        Integer numStringParentChildrenCount;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        for (FirExpression firExpression : expression.getArgumentList().getArguments()) {
            if (Intrinsics.areEqual(ConeTypeUtilsKt.getClassId(FirTypeUtilsKt.getConeType(firExpression.getTypeRef())), StandardClassIds.INSTANCE.getString()) && (numStringParentChildrenCount = stringParentChildrenCount(firExpression)) != null && numStringParentChildrenCount.intValue() == 1) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, firExpression.getSource(), FirErrors.INSTANCE.getREDUNDANT_SINGLE_EXPRESSION_STRING_TEMPLATE(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
            }
        }
    }

    private final Integer stringParentChildrenCount(FirStatement firStatement) {
        KtSourceElement source = firStatement.getSource();
        if (source instanceof KtPsiSourceElement) {
            return stringParentChildrenCount(((KtPsiSourceElement) source).getPsi());
        }
        if (source instanceof KtLightSourceElement) {
            KtLightSourceElement ktLightSourceElement = (KtLightSourceElement) source;
            return stringParentChildrenCount(ktLightSourceElement.getLighterASTNode(), ktLightSourceElement);
        }
        if (source == null) {
            return null;
        }
        throw new NoWhenBranchMatchedException();
    }

    private final Integer stringParentChildrenCount(PsiElement psiElement) {
        PsiElement[] children;
        if (!(psiElement.getMyParent() instanceof KtStringTemplateExpression)) {
            PsiElement myParent = psiElement.getMyParent();
            Intrinsics.checkNotNullExpressionValue(myParent, "getParent(...)");
            return stringParentChildrenCount(myParent);
        }
        PsiElement myParent2 = psiElement.getMyParent();
        if (myParent2 == null || (children = myParent2.getChildren()) == null) {
            return null;
        }
        return Integer.valueOf(children.length);
    }

    private final Integer stringParentChildrenCount(LighterASTNode lighterASTNode, KtLightSourceElement ktLightSourceElement) {
        LighterASTNode parent = ktLightSourceElement.getTreeStructure().getParent(lighterASTNode);
        if (parent == null || !Intrinsics.areEqual(parent.getTokenType(), KtNodeTypes.STRING_TEMPLATE)) {
            if (parent != null) {
                return stringParentChildrenCount(parent, ktLightSourceElement);
            }
            return null;
        }
        List<LighterASTNode> children = LightTreeUtilsKt.getChildren(parent, ktLightSourceElement.getTreeStructure());
        ArrayList arrayList = new ArrayList();
        for (Object obj : children) {
            if (((LighterASTNode) obj) instanceof PsiBuilder.Marker) {
                arrayList.add(obj);
            }
        }
        return Integer.valueOf(arrayList.size());
    }
}
