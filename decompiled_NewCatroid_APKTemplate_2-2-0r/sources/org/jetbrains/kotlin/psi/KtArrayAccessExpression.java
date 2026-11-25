package org.jetbrains.kotlin.psi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.jetbrains.kotlin.KtNodeTypes;
import org.jetbrains.kotlin.com.google.common.collect.Lists;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.psi.psiUtil.KtPsiUtilKt;

/* JADX INFO: loaded from: classes6.dex */
public class KtArrayAccessExpression extends KtExpressionImpl implements KtReferenceExpression {
    static final /* synthetic */ boolean $assertionsDisabled = false;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 3 || i == 4 || i == 5) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 3 || i == 4 || i == 5) ? 2 : 3];
        if (i == 1) {
            objArr[0] = "visitor";
        } else if (i == 2 || i == 3 || i == 4 || i == 5) {
            objArr[0] = "org/jetbrains/kotlin/psi/KtArrayAccessExpression";
        } else {
            objArr[0] = "node";
        }
        if (i == 2) {
            objArr[1] = "getIndexExpressions";
        } else if (i == 3) {
            objArr[1] = "getIndicesNode";
        } else if (i == 4 || i == 5) {
            objArr[1] = "getBracketRanges";
        } else {
            objArr[1] = "org/jetbrains/kotlin/psi/KtArrayAccessExpression";
        }
        if (i == 1) {
            objArr[2] = "accept";
        } else if (i != 2 && i != 3 && i != 4 && i != 5) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 3 && i != 4 && i != 5) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtArrayAccessExpression(ASTNode aSTNode) {
        super(aSTNode);
        if (aSTNode == null) {
            $$$reportNull$$$0(0);
        }
    }

    @Override // org.jetbrains.kotlin.psi.KtExpressionImpl, org.jetbrains.kotlin.psi.KtElementImpl, org.jetbrains.kotlin.psi.KtElement, org.jetbrains.kotlin.psi.KtExpression
    public <R, D> R accept(KtVisitor<R, D> ktVisitor, D d) {
        if (ktVisitor == null) {
            $$$reportNull$$$0(1);
        }
        return ktVisitor.visitArrayAccessExpression(this, d);
    }

    @IfNotParsed
    public KtExpression getArrayExpression() {
        return (KtExpression) findChildByClass(KtExpression.class);
    }

    public List<KtExpression> getIndexExpressions() {
        List<KtExpression> childrenOfTypeAsList = PsiTreeUtil.getChildrenOfTypeAsList(getIndicesNode(), KtExpression.class);
        if (childrenOfTypeAsList == null) {
            $$$reportNull$$$0(2);
        }
        return childrenOfTypeAsList;
    }

    public KtContainerNode getIndicesNode() {
        KtContainerNode ktContainerNode = (KtContainerNode) findChildByType(KtNodeTypes.INDICES);
        if (ktContainerNode == null) {
            $$$reportNull$$$0(3);
        }
        return ktContainerNode;
    }

    public List<TextRange> getBracketRanges() {
        PsiElement leftBracket = getLeftBracket();
        PsiElement rightBracket = getRightBracket();
        if (leftBracket == null || rightBracket == null) {
            List<TextRange> listEmptyList = Collections.emptyList();
            if (listEmptyList == null) {
                $$$reportNull$$$0(4);
            }
            return listEmptyList;
        }
        ArrayList arrayListNewArrayList = Lists.newArrayList(leftBracket.getTextRange(), rightBracket.getTextRange());
        if (arrayListNewArrayList == null) {
            $$$reportNull$$$0(5);
        }
        return arrayListNewArrayList;
    }

    public PsiElement getLeftBracket() {
        return getIndicesNode().findChildByType(KtTokens.LBRACKET);
    }

    public PsiElement getRightBracket() {
        return getIndicesNode().findChildByType(KtTokens.RBRACKET);
    }

    public PsiElement getTrailingComma() {
        return KtPsiUtilKt.getTrailingCommaByClosingElement(getRightBracket());
    }
}
