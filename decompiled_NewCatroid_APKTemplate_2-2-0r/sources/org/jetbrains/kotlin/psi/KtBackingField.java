package org.jetbrains.kotlin.psi;

import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.psi.stubs.KotlinBackingFieldStub;
import org.jetbrains.kotlin.psi.stubs.elements.KtStubElementTypes;

/* JADX INFO: loaded from: classes6.dex */
public class KtBackingField extends KtDeclarationStub<KotlinBackingFieldStub> implements KtDeclarationWithInitializer, KtModifierListOwner {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 3 || i == 4) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 3 || i == 4) ? 2 : 3];
        if (i == 1) {
            objArr[0] = "stub";
        } else if (i == 2) {
            objArr[0] = "visitor";
        } else if (i == 3 || i == 4) {
            objArr[0] = "org/jetbrains/kotlin/psi/KtBackingField";
        } else {
            objArr[0] = "node";
        }
        if (i == 3 || i == 4) {
            objArr[1] = "getNamePlaceholder";
        } else {
            objArr[1] = "org/jetbrains/kotlin/psi/KtBackingField";
        }
        if (i == 2) {
            objArr[2] = "accept";
        } else if (i != 3 && i != 4) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i != 3 && i != 4) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtBackingField(ASTNode aSTNode) {
        super(aSTNode);
        if (aSTNode == null) {
            $$$reportNull$$$0(0);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtBackingField(KotlinBackingFieldStub kotlinBackingFieldStub) {
        super(kotlinBackingFieldStub, KtStubElementTypes.BACKING_FIELD);
        if (kotlinBackingFieldStub == null) {
            $$$reportNull$$$0(1);
        }
    }

    @Override // org.jetbrains.kotlin.psi.KtElementImplStub, org.jetbrains.kotlin.psi.KtElement, org.jetbrains.kotlin.psi.KtExpression
    public <R, D> R accept(KtVisitor<R, D> ktVisitor, D d) {
        if (ktVisitor == null) {
            $$$reportNull$$$0(2);
        }
        return ktVisitor.visitBackingField(this, d);
    }

    public PsiElement getEqualsToken() {
        return findChildByType(KtTokens.EQ);
    }

    public KtTypeReference getReturnTypeReference() {
        return (KtTypeReference) getStubOrPsiChild(KtStubElementTypes.TYPE_REFERENCE);
    }

    public PsiElement getNamePlaceholder() {
        PsiElement fieldKeyword = getFieldKeyword();
        if (fieldKeyword != null) {
            if (fieldKeyword == null) {
                $$$reportNull$$$0(3);
            }
            return fieldKeyword;
        }
        PsiElement psi = getNode().getPsi();
        if (psi == null) {
            $$$reportNull$$$0(4);
        }
        return psi;
    }

    @Override // org.jetbrains.kotlin.psi.KtDeclarationWithInitializer
    public KtExpression getInitializer() {
        KotlinBackingFieldStub kotlinBackingFieldStub = (KotlinBackingFieldStub) getStub();
        if (kotlinBackingFieldStub == null || kotlinBackingFieldStub.hasInitializer()) {
            return (KtExpression) PsiTreeUtil.getNextSiblingOfType(getEqualsToken(), KtExpression.class);
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtDeclarationWithInitializer
    public boolean hasInitializer() {
        KotlinBackingFieldStub kotlinBackingFieldStub = (KotlinBackingFieldStub) getStub();
        if (kotlinBackingFieldStub != null) {
            return kotlinBackingFieldStub.hasInitializer();
        }
        return getInitializer() != null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.extapi.psi.ASTDelegatePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public int getTextOffset() {
        return getNamePlaceholder().getTextRange().getStartOffset();
    }

    public PsiElement getFieldKeyword() {
        return findChildByType(KtTokens.FIELD_KEYWORD);
    }
}
