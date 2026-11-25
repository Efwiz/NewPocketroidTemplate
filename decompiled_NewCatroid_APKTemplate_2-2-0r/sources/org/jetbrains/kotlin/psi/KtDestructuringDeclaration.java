package org.jetbrains.kotlin.psi;

import java.util.List;
import org.jetbrains.kotlin.KtNodeTypes;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.psi.psiUtil.KtPsiUtilKt;

/* JADX INFO: loaded from: classes6.dex */
public class KtDestructuringDeclaration extends KtDeclarationImpl implements KtDeclarationWithInitializer, KtValVarKeywordOwner {
    private static final TokenSet VAL_VAR_KEYWORDS = TokenSet.create(KtTokens.VAL_KEYWORD, KtTokens.VAR_KEYWORD);

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 2 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 2 ? 3 : 2];
        if (i == 1) {
            objArr[0] = "visitor";
        } else if (i != 2) {
            objArr[0] = "node";
        } else {
            objArr[0] = "org/jetbrains/kotlin/psi/KtDestructuringDeclaration";
        }
        if (i != 2) {
            objArr[1] = "org/jetbrains/kotlin/psi/KtDestructuringDeclaration";
        } else {
            objArr[1] = "getEntries";
        }
        if (i == 1) {
            objArr[2] = "accept";
        } else if (i != 2) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i == 2) {
            throw new IllegalStateException(str2);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtDestructuringDeclaration(ASTNode aSTNode) {
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
        return ktVisitor.visitDestructuringDeclaration(this, d);
    }

    public List<KtDestructuringDeclarationEntry> getEntries() {
        List<KtDestructuringDeclarationEntry> listFindChildrenByType = findChildrenByType(KtNodeTypes.DESTRUCTURING_DECLARATION_ENTRY);
        if (listFindChildrenByType == null) {
            $$$reportNull$$$0(2);
        }
        return listFindChildrenByType;
    }

    @Override // org.jetbrains.kotlin.psi.KtDeclarationWithInitializer
    public KtExpression getInitializer() {
        ASTNode aSTNodeFindChildByType = getNode().findChildByType(KtTokens.EQ);
        if (aSTNodeFindChildByType == null) {
            return null;
        }
        return (KtExpression) PsiTreeUtil.getNextSiblingOfType(aSTNodeFindChildByType.getPsi(), KtExpression.class);
    }

    @Override // org.jetbrains.kotlin.psi.KtDeclarationWithInitializer
    public boolean hasInitializer() {
        return getInitializer() != null;
    }

    public boolean isVar() {
        return getNode().findChildByType(KtTokens.VAR_KEYWORD) != null;
    }

    @Override // org.jetbrains.kotlin.psi.KtValVarKeywordOwner
    public PsiElement getValOrVarKeyword() {
        return findChildByType(VAL_VAR_KEYWORDS);
    }

    public PsiElement getRPar() {
        return findChildByType(KtTokens.RPAR);
    }

    public PsiElement getLPar() {
        return findChildByType(KtTokens.LPAR);
    }

    public PsiElement getTrailingComma() {
        return KtPsiUtilKt.getTrailingCommaByClosingElement(getRPar());
    }
}
