package org.jetbrains.kotlin.fir.analysis.checkers.syntax;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.ElementTypeUtils;
import org.jetbrains.kotlin.KtLightSourceElement;
import org.jetbrains.kotlin.KtNodeTypes;
import org.jetbrains.kotlin.KtPsiSourceElement;
import org.jetbrains.kotlin.KtRealPsiSourceElement;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.lang.LighterASTNode;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet;
import org.jetbrains.kotlin.com.intellij.util.diff.FlyweightCapableTreeStructure;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.expressions.FirWhenExpression;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.psi.KtBinaryExpression;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtIsExpression;
import org.jetbrains.kotlin.psi.KtParenthesizedExpression;
import org.jetbrains.kotlin.psi.KtPsiUtil;
import org.jetbrains.kotlin.psi.KtWhenCondition;
import org.jetbrains.kotlin.psi.KtWhenConditionInRange;
import org.jetbrains.kotlin.psi.KtWhenConditionWithExpression;
import org.jetbrains.kotlin.psi.KtWhenEntry;
import org.jetbrains.kotlin.psi.KtWhenExpression;
import org.jetbrains.kotlin.util.LightTreeUtilsKt;

/* JADX INFO: compiled from: FirConfusingWhenBranchSyntaxChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0002J6\u0010\u0010\u001a\u00020\t2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00140\u00162\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0002J\"\u0010\u0010\u001a\u00020\t2\b\u0010\u0017\u001a\u0004\u0018\u00010\u00182\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0002J(\u0010\u0019\u001a\u00020\t2\u0006\u0010\u001a\u001a\u00020\u00022\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J0\u0010\u001d\u001a\u00020\t2\u0006\u0010\u001a\u001a\u00020\u00022\u0006\u0010\u001b\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u00032\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016R\u0013\u0010\u0005\u001a\u00070\u0006¢\u0006\u0002\b\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006 "}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/syntax/FirConfusingWhenBranchSyntaxChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/syntax/FirExpressionSyntaxChecker;", "Lorg/jetbrains/kotlin/fir/expressions/FirWhenExpression;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "()V", "prohibitedTokens", "Lorg/jetbrains/kotlin/com/intellij/psi/tree/TokenSet;", "Lorg/jetbrains/annotations/NotNull;", "checkCondition", "", "condition", "Lorg/jetbrains/kotlin/psi/KtWhenCondition;", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkConditionExpression", TypedValues.CycleType.S_WAVE_OFFSET, "", "expression", "Lorg/jetbrains/kotlin/com/intellij/lang/LighterASTNode;", Constants.TYPE_TREE, "Lorg/jetbrains/kotlin/com/intellij/util/diff/FlyweightCapableTreeStructure;", "rawExpression", "Lorg/jetbrains/kotlin/psi/KtExpression;", "checkLightTree", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtLightSourceElement;", "checkPsi", "Lorg/jetbrains/kotlin/KtPsiSourceElement;", "psi", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirConfusingWhenBranchSyntaxChecker extends FirExpressionSyntaxChecker<FirWhenExpression, PsiElement> {
    public static final FirConfusingWhenBranchSyntaxChecker INSTANCE = new FirConfusingWhenBranchSyntaxChecker();
    private static final TokenSet prohibitedTokens;

    private FirConfusingWhenBranchSyntaxChecker() {
    }

    static {
        TokenSet tokenSetCreate = TokenSet.create(KtTokens.IN_KEYWORD, KtTokens.NOT_IN, KtTokens.LT, KtTokens.LTEQ, KtTokens.GT, KtTokens.GTEQ, KtTokens.EQEQ, KtTokens.EXCLEQ, KtTokens.EQEQEQ, KtTokens.EXCLEQEQEQ, KtTokens.ANDAND, KtTokens.OROR);
        Intrinsics.checkNotNullExpressionValue(tokenSetCreate, "create(...)");
        prohibitedTokens = tokenSetCreate;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v7, types: [org.jetbrains.kotlin.com.intellij.lang.LighterASTNode] */
    /* JADX WARN: Type inference failed for: r8v0, types: [org.jetbrains.kotlin.fir.analysis.checkers.syntax.FirConfusingWhenBranchSyntaxChecker] */
    @Override // org.jetbrains.kotlin.fir.analysis.checkers.syntax.FirSyntaxChecker
    public void checkLightTree(FirWhenExpression element, KtLightSourceElement source, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(source, "source");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        if (element.getSubject() == null && element.getSubjectVariable() == null) {
            return;
        }
        FlyweightCapableTreeStructure<LighterASTNode> treeStructure = source.getTreeStructure();
        List<LighterASTNode> children = LightTreeUtilsKt.getChildren(source.getLighterASTNode(), treeStructure);
        ArrayList arrayList = new ArrayList();
        for (Object obj : children) {
            if (Intrinsics.areEqual(((LighterASTNode) obj).getTokenType(), KtNodeTypes.WHEN_ENTRY)) {
                arrayList.add(obj);
            }
        }
        int startOffset = source.getStartOffset() - source.getLighterASTNode().getStartOffset();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            for (LighterASTNode lighterASTNode : LightTreeUtilsKt.getChildren((LighterASTNode) it.next(), treeStructure)) {
                IElementType tokenType = lighterASTNode.getTokenType();
                Object obj2 = null;
                if (!Intrinsics.areEqual(tokenType, KtNodeTypes.WHEN_CONDITION_EXPRESSION)) {
                    if (Intrinsics.areEqual(tokenType, KtNodeTypes.WHEN_CONDITION_IN_RANGE)) {
                        for (Object obj3 : LightTreeUtilsKt.getChildren(lighterASTNode, treeStructure)) {
                            LighterASTNode lighterASTNode2 = (LighterASTNode) obj3;
                            if (!Intrinsics.areEqual(lighterASTNode2.getTokenType(), KtNodeTypes.OPERATION_REFERENCE) && ElementTypeUtils.INSTANCE.isExpression(lighterASTNode2)) {
                                obj2 = obj3;
                                break;
                            }
                        }
                        obj2 = (LighterASTNode) obj2;
                    }
                } else {
                    for (Object obj4 : LightTreeUtilsKt.getChildren(lighterASTNode, treeStructure)) {
                        if (ElementTypeUtils.INSTANCE.isExpression((LighterASTNode) obj4)) {
                            obj2 = obj4;
                            break;
                        }
                    }
                    obj2 = (LighterASTNode) obj2;
                }
                ?? r2 = obj2;
                if (r2 != 0) {
                    checkConditionExpression(startOffset, r2, treeStructure, context, reporter);
                }
            }
        }
    }

    private final void checkConditionExpression(int offset, LighterASTNode expression, FlyweightCapableTreeStructure<LighterASTNode> tree, CheckerContext context, DiagnosticReporter reporter) {
        boolean zContains;
        Object next;
        IElementType tokenType = expression.getTokenType();
        if (Intrinsics.areEqual(tokenType, KtNodeTypes.IS_EXPRESSION)) {
            zContains = true;
        } else if (Intrinsics.areEqual(tokenType, KtNodeTypes.BINARY_EXPRESSION)) {
            Iterator<T> it = LightTreeUtilsKt.getChildren(expression, tree).iterator();
            do {
                if (!it.hasNext()) {
                    throw new NoSuchElementException("Collection contains no element matching the predicate.");
                }
                next = it.next();
            } while (!Intrinsics.areEqual(((LighterASTNode) next).getTokenType(), KtNodeTypes.OPERATION_REFERENCE));
            zContains = prohibitedTokens.contains(ElementTypeUtils.INSTANCE.getOperationSymbol(next.toString()));
        } else {
            zContains = false;
        }
        if (zContains) {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, new KtLightSourceElement(expression, offset + expression.getStartOffset(), offset + expression.getEndOffset(), tree, null, 16, null), FirErrors.INSTANCE.getCONFUSING_BRANCH_CONDITION(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
        }
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.syntax.FirSyntaxChecker
    public void checkPsi(FirWhenExpression element, KtPsiSourceElement source, PsiElement psi, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(source, "source");
        Intrinsics.checkNotNullParameter(psi, "psi");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        if (element.getSubject() == null && element.getSubjectVariable() == null) {
            return;
        }
        KtWhenExpression ktWhenExpression = (KtWhenExpression) psi;
        if (ktWhenExpression.getSubjectExpression() == null && ktWhenExpression.getSubjectVariable() == null) {
            return;
        }
        Iterator<KtWhenEntry> it = ktWhenExpression.getEntries().iterator();
        while (it.hasNext()) {
            KtWhenCondition[] conditions = it.next().getConditions();
            Intrinsics.checkNotNullExpressionValue(conditions, "getConditions(...)");
            for (KtWhenCondition ktWhenCondition : conditions) {
                Intrinsics.checkNotNull(ktWhenCondition);
                checkCondition(ktWhenCondition, context, reporter);
            }
        }
    }

    private final void checkCondition(KtWhenCondition condition, CheckerContext context, DiagnosticReporter reporter) {
        if (condition instanceof KtWhenConditionWithExpression) {
            checkConditionExpression(((KtWhenConditionWithExpression) condition).getExpression(), context, reporter);
        } else if (condition instanceof KtWhenConditionInRange) {
            checkConditionExpression(((KtWhenConditionInRange) condition).getRangeExpression(), context, reporter);
        }
    }

    private final void checkConditionExpression(KtExpression rawExpression, CheckerContext context, DiagnosticReporter reporter) {
        boolean zContains;
        if (rawExpression == null || (rawExpression instanceof KtParenthesizedExpression)) {
            return;
        }
        KtExpression ktExpressionSafeDeparenthesize = KtPsiUtil.safeDeparenthesize(rawExpression);
        Intrinsics.checkNotNullExpressionValue(ktExpressionSafeDeparenthesize, "safeDeparenthesize(...)");
        if (ktExpressionSafeDeparenthesize instanceof KtIsExpression) {
            zContains = true;
        } else {
            zContains = ktExpressionSafeDeparenthesize instanceof KtBinaryExpression ? prohibitedTokens.contains(((KtBinaryExpression) ktExpressionSafeDeparenthesize).getOperationToken()) : false;
        }
        if (zContains) {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, new KtRealPsiSourceElement(rawExpression), FirErrors.INSTANCE.getCONFUSING_BRANCH_CONDITION(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
        }
    }
}
