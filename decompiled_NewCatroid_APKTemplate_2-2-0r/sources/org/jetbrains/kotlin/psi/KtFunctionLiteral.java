package org.jetbrains.kotlin.psi;

import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.search.LocalSearchScope;
import org.jetbrains.kotlin.com.intellij.psi.search.SearchScope;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX INFO: loaded from: classes6.dex */
public class KtFunctionLiteral extends KtFunctionNotStubbed {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 1 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 1 ? 3 : 2];
        if (i != 1) {
            objArr[0] = "node";
        } else {
            objArr[0] = "org/jetbrains/kotlin/psi/KtFunctionLiteral";
        }
        if (i != 1) {
            objArr[1] = "org/jetbrains/kotlin/psi/KtFunctionLiteral";
        } else {
            objArr[1] = "getLBrace";
        }
        if (i != 1) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i == 1) {
            throw new IllegalStateException(str2);
        }
    }

    @Override // org.jetbrains.kotlin.psi.KtDeclarationWithBody
    /* JADX INFO: renamed from: getEqualsToken */
    public PsiElement mo7390getEqualsToken() {
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtNamedDeclaration
    /* JADX INFO: renamed from: getFqName */
    public FqName mo7391getFqName() {
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtFunctionNotStubbed, org.jetbrains.kotlin.psi.KtNamedDeclarationNotStubbed, org.jetbrains.kotlin.com.intellij.psi.PsiNameIdentifierOwner
    /* JADX INFO: renamed from: getNameIdentifier */
    public PsiElement mo7392getNameIdentifier() {
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtDeclarationWithBody
    public boolean hasBlockBody() {
        return false;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtFunctionLiteral(ASTNode aSTNode) {
        super(aSTNode);
        if (aSTNode == null) {
            $$$reportNull$$$0(0);
        }
    }

    @Override // org.jetbrains.kotlin.psi.KtFunctionNotStubbed, org.jetbrains.kotlin.psi.KtNamedDeclarationNotStubbed, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.navigation.NavigationItem, org.jetbrains.kotlin.com.intellij.pom.PomNamedTarget, org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement, org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedNamedElement
    public String getName() {
        return SpecialNames.ANONYMOUS_STRING;
    }

    public boolean hasParameterSpecification() {
        return findChildByType(KtTokens.ARROW) != null;
    }

    @Override // org.jetbrains.kotlin.psi.KtFunctionNotStubbed, org.jetbrains.kotlin.psi.KtDeclarationWithBody
    public KtBlockExpression getBodyExpression() {
        return (KtBlockExpression) super.getBodyExpression();
    }

    public PsiElement getLBrace() {
        PsiElement psiElementFindChildByType = findChildByType(KtTokens.LBRACE);
        if (psiElementFindChildByType == null) {
            $$$reportNull$$$0(1);
        }
        return psiElementFindChildByType;
    }

    @IfNotParsed
    public PsiElement getRBrace() {
        return findChildByType(KtTokens.RBRACE);
    }

    public PsiElement getArrow() {
        return findChildByType(KtTokens.ARROW);
    }

    @Override // org.jetbrains.kotlin.psi.KtDeclarationWithBody
    public boolean hasBody() {
        return getBodyExpression() != null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public SearchScope getUseScope() {
        return new LocalSearchScope(this);
    }
}
