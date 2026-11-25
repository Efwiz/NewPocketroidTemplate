package org.jetbrains.kotlin.psi;

import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.psi.stubs.KotlinClassLiteralExpressionStub;
import org.jetbrains.kotlin.psi.stubs.elements.KtStubElementTypes;

/* JADX INFO: loaded from: classes6.dex */
public class KtClassLiteralExpression extends KtElementImplStub<KotlinClassLiteralExpressionStub> implements KtDoubleColonExpression {
    private static final TokenSet CLASS_REFS = TokenSet.create(KtStubElementTypes.REFERENCE_EXPRESSION, KtStubElementTypes.DOT_QUALIFIED_EXPRESSION);

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i != 1) {
            objArr[0] = "node";
        } else {
            objArr[0] = "visitor";
        }
        objArr[1] = "org/jetbrains/kotlin/psi/KtClassLiteralExpression";
        if (i != 1) {
            objArr[2] = "<init>";
        } else {
            objArr[2] = "accept";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public KtClassLiteralExpression(KotlinClassLiteralExpressionStub kotlinClassLiteralExpressionStub) {
        super(kotlinClassLiteralExpressionStub, KtStubElementTypes.CLASS_LITERAL_EXPRESSION);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtClassLiteralExpression(ASTNode aSTNode) {
        super(aSTNode);
        if (aSTNode == null) {
            $$$reportNull$$$0(0);
        }
    }

    @Override // org.jetbrains.kotlin.psi.KtDoubleColonExpression
    public KtExpression getReceiverExpression() {
        KotlinClassLiteralExpressionStub kotlinClassLiteralExpressionStub = (KotlinClassLiteralExpressionStub) getStub();
        if (kotlinClassLiteralExpressionStub != null) {
            KtExpression[] ktExpressionArr = (KtExpression[]) kotlinClassLiteralExpressionStub.getChildrenByType(CLASS_REFS, KtExpression.EMPTY_ARRAY);
            if (ktExpressionArr.length == 1) {
                return ktExpressionArr[0];
            }
        }
        return super.getReceiverExpression();
    }

    @Override // org.jetbrains.kotlin.psi.KtElementImplStub, org.jetbrains.kotlin.psi.KtElement, org.jetbrains.kotlin.psi.KtExpression
    public <R, D> R accept(KtVisitor<R, D> ktVisitor, D d) {
        if (ktVisitor == null) {
            $$$reportNull$$$0(1);
        }
        return ktVisitor.visitClassLiteralExpression(this, d);
    }

    @Override // org.jetbrains.kotlin.psi.KtDoubleColonExpression
    public PsiElement findColonColon() {
        ASTNode aSTNodeFindChildByType = getNode().findChildByType(KtTokens.COLONCOLON);
        if (aSTNodeFindChildByType != null) {
            return aSTNodeFindChildByType.getPsi();
        }
        return null;
    }
}
