package org.jetbrains.kotlin.psi;

import java.util.Arrays;
import java.util.List;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.psi.psiUtil.KtPsiUtilKt;
import org.jetbrains.kotlin.psi.stubs.KotlinCollectionLiteralExpressionStub;
import org.jetbrains.kotlin.psi.stubs.elements.KtStubElementTypes;

/* JADX INFO: loaded from: classes6.dex */
public class KtCollectionLiteralExpression extends KtElementImplStub<KotlinCollectionLiteralExpressionStub> implements KtReferenceExpression {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1) {
            objArr[0] = "node";
        } else if (i != 2) {
            objArr[0] = "stub";
        } else {
            objArr[0] = "visitor";
        }
        objArr[1] = "org/jetbrains/kotlin/psi/KtCollectionLiteralExpression";
        if (i != 2) {
            objArr[2] = "<init>";
        } else {
            objArr[2] = "accept";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtCollectionLiteralExpression(KotlinCollectionLiteralExpressionStub kotlinCollectionLiteralExpressionStub) {
        super(kotlinCollectionLiteralExpressionStub, KtStubElementTypes.COLLECTION_LITERAL_EXPRESSION);
        if (kotlinCollectionLiteralExpressionStub == null) {
            $$$reportNull$$$0(0);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtCollectionLiteralExpression(ASTNode aSTNode) {
        super(aSTNode);
        if (aSTNode == null) {
            $$$reportNull$$$0(1);
        }
    }

    @Override // org.jetbrains.kotlin.psi.KtElementImplStub, org.jetbrains.kotlin.psi.KtElement, org.jetbrains.kotlin.psi.KtExpression
    public <R, D> R accept(KtVisitor<R, D> ktVisitor, D d) {
        if (ktVisitor == null) {
            $$$reportNull$$$0(2);
        }
        return ktVisitor.visitCollectionLiteralExpression(this, d);
    }

    public PsiElement getLeftBracket() {
        ASTNode aSTNodeFindChildByType = getNode().findChildByType(KtTokens.LBRACKET);
        if (aSTNodeFindChildByType != null) {
            return aSTNodeFindChildByType.getPsi();
        }
        return null;
    }

    public PsiElement getRightBracket() {
        ASTNode aSTNodeFindChildByType = getNode().findChildByType(KtTokens.RBRACKET);
        if (aSTNodeFindChildByType != null) {
            return aSTNodeFindChildByType.getPsi();
        }
        return null;
    }

    public PsiElement getTrailingComma() {
        return KtPsiUtilKt.getTrailingCommaByClosingElement(getRightBracket());
    }

    public List<KtExpression> getInnerExpressions() {
        KotlinCollectionLiteralExpressionStub kotlinCollectionLiteralExpressionStub = (KotlinCollectionLiteralExpressionStub) getStub();
        if (kotlinCollectionLiteralExpressionStub != null) {
            return Arrays.asList(kotlinCollectionLiteralExpressionStub.getChildrenByType(KtStubElementTypes.CONSTANT_EXPRESSIONS_TYPES, KtExpression.EMPTY_ARRAY));
        }
        return PsiTreeUtil.getChildrenOfTypeAsList(this, KtExpression.class);
    }
}
