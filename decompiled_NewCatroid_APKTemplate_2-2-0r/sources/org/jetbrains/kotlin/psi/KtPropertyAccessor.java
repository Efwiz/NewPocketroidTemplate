package org.jetbrains.kotlin.psi;

import java.util.Collections;
import java.util.List;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.psi.stubs.KotlinPropertyAccessorStub;
import org.jetbrains.kotlin.psi.stubs.elements.KtStubElementTypes;

/* JADX INFO: loaded from: classes6.dex */
public class KtPropertyAccessor extends KtDeclarationStub<KotlinPropertyAccessorStub> implements KtDeclarationWithBody, KtDeclarationWithInitializer, KtModifierListOwner {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 3 || i == 4 || i == 5 || i == 6 || i == 7) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 3 || i == 4 || i == 5 || i == 6 || i == 7) ? 2 : 3];
        switch (i) {
            case 1:
                objArr[0] = "stub";
                break;
            case 2:
                objArr[0] = "visitor";
                break;
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                objArr[0] = "org/jetbrains/kotlin/psi/KtPropertyAccessor";
                break;
            default:
                objArr[0] = "node";
                break;
        }
        if (i == 3 || i == 4) {
            objArr[1] = "getValueParameters";
        } else if (i == 5 || i == 6) {
            objArr[1] = "getNamePlaceholder";
        } else if (i != 7) {
            objArr[1] = "org/jetbrains/kotlin/psi/KtPropertyAccessor";
        } else {
            objArr[1] = "getProperty";
        }
        switch (i) {
            case 2:
                objArr[2] = "accept";
                break;
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 3 && i != 4 && i != 5 && i != 6 && i != 7) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    @Override // org.jetbrains.kotlin.psi.KtDeclarationWithBody
    public boolean hasDeclaredReturnType() {
        return true;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtPropertyAccessor(ASTNode aSTNode) {
        super(aSTNode);
        if (aSTNode == null) {
            $$$reportNull$$$0(0);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtPropertyAccessor(KotlinPropertyAccessorStub kotlinPropertyAccessorStub) {
        super(kotlinPropertyAccessorStub, KtStubElementTypes.PROPERTY_ACCESSOR);
        if (kotlinPropertyAccessorStub == null) {
            $$$reportNull$$$0(1);
        }
    }

    @Override // org.jetbrains.kotlin.psi.KtElementImplStub, org.jetbrains.kotlin.psi.KtElement, org.jetbrains.kotlin.psi.KtExpression
    public <R, D> R accept(KtVisitor<R, D> ktVisitor, D d) {
        if (ktVisitor == null) {
            $$$reportNull$$$0(2);
        }
        return ktVisitor.visitPropertyAccessor(this, d);
    }

    public boolean isSetter() {
        KotlinPropertyAccessorStub kotlinPropertyAccessorStub = (KotlinPropertyAccessorStub) getStub();
        if (kotlinPropertyAccessorStub != null) {
            return !kotlinPropertyAccessorStub.isGetter();
        }
        return findChildByType(KtTokens.SET_KEYWORD) != null;
    }

    public boolean isGetter() {
        KotlinPropertyAccessorStub kotlinPropertyAccessorStub = (KotlinPropertyAccessorStub) getStub();
        if (kotlinPropertyAccessorStub != null) {
            return kotlinPropertyAccessorStub.isGetter();
        }
        return findChildByType(KtTokens.GET_KEYWORD) != null;
    }

    public KtParameterList getParameterList() {
        return (KtParameterList) getStubOrPsiChild(KtStubElementTypes.VALUE_PARAMETER_LIST);
    }

    public KtParameter getParameter() {
        KtParameterList parameterList = getParameterList();
        if (parameterList == null) {
            return null;
        }
        List<KtParameter> parameters = parameterList.getParameters();
        if (parameters.isEmpty()) {
            return null;
        }
        return parameters.get(0);
    }

    @Override // org.jetbrains.kotlin.psi.KtDeclarationWithBody
    public List<KtParameter> getValueParameters() {
        KtParameter parameter = getParameter();
        if (parameter == null) {
            List<KtParameter> listEmptyList = Collections.emptyList();
            if (listEmptyList == null) {
                $$$reportNull$$$0(3);
            }
            return listEmptyList;
        }
        List<KtParameter> listSingletonList = Collections.singletonList(parameter);
        if (listSingletonList == null) {
            $$$reportNull$$$0(4);
        }
        return listSingletonList;
    }

    @Override // org.jetbrains.kotlin.psi.KtDeclarationWithBody
    public KtExpression getBodyExpression() {
        KotlinPropertyAccessorStub kotlinPropertyAccessorStub = (KotlinPropertyAccessorStub) getStub();
        if (kotlinPropertyAccessorStub == null || (kotlinPropertyAccessorStub.hasBody() && !getContainingKtFile().getIsCompiled())) {
            return (KtExpression) findChildByClass(KtExpression.class);
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtDeclarationWithBody
    public KtBlockExpression getBodyBlockExpression() {
        KotlinPropertyAccessorStub kotlinPropertyAccessorStub = (KotlinPropertyAccessorStub) getStub();
        if (kotlinPropertyAccessorStub != null && (!kotlinPropertyAccessorStub.hasBlockBody() || !kotlinPropertyAccessorStub.hasBody() || getContainingKtFile().getIsCompiled())) {
            return null;
        }
        KtExpression ktExpression = (KtExpression) findChildByClass(KtExpression.class);
        if (ktExpression instanceof KtBlockExpression) {
            return (KtBlockExpression) ktExpression;
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtDeclarationWithBody
    public boolean hasBlockBody() {
        KotlinPropertyAccessorStub kotlinPropertyAccessorStub = (KotlinPropertyAccessorStub) getStub();
        if (kotlinPropertyAccessorStub != null) {
            return kotlinPropertyAccessorStub.hasBlockBody();
        }
        return mo7390getEqualsToken() == null;
    }

    @Override // org.jetbrains.kotlin.psi.KtDeclarationWithBody
    public boolean hasBody() {
        KotlinPropertyAccessorStub kotlinPropertyAccessorStub = (KotlinPropertyAccessorStub) getStub();
        if (kotlinPropertyAccessorStub != null) {
            return kotlinPropertyAccessorStub.hasBody();
        }
        return getBodyExpression() != null;
    }

    @Override // org.jetbrains.kotlin.psi.KtDeclarationWithBody
    /* JADX INFO: renamed from: getEqualsToken */
    public PsiElement mo7390getEqualsToken() {
        return findChildByType(KtTokens.EQ);
    }

    @Override // org.jetbrains.kotlin.psi.KtDeclarationWithBody
    public KtContractEffectList getContractDescription() {
        return (KtContractEffectList) getStubOrPsiChild(KtStubElementTypes.CONTRACT_EFFECT_LIST);
    }

    public KtTypeReference getReturnTypeReference() {
        return (KtTypeReference) getStubOrPsiChild(KtStubElementTypes.TYPE_REFERENCE);
    }

    public PsiElement getNamePlaceholder() {
        PsiElement psiElementFindChildByType = findChildByType(KtTokens.GET_KEYWORD);
        if (psiElementFindChildByType != null) {
            if (psiElementFindChildByType == null) {
                $$$reportNull$$$0(5);
            }
            return psiElementFindChildByType;
        }
        PsiElement psiElementFindChildByType2 = findChildByType(KtTokens.SET_KEYWORD);
        if (psiElementFindChildByType2 == null) {
            $$$reportNull$$$0(6);
        }
        return psiElementFindChildByType2;
    }

    public PsiElement getRightParenthesis() {
        return findChildByType(KtTokens.RPAR);
    }

    public PsiElement getLeftParenthesis() {
        return findChildByType(KtTokens.LPAR);
    }

    @Override // org.jetbrains.kotlin.psi.KtDeclarationWithInitializer
    public KtExpression getInitializer() {
        return (KtExpression) PsiTreeUtil.getNextSiblingOfType(mo7390getEqualsToken(), KtExpression.class);
    }

    @Override // org.jetbrains.kotlin.psi.KtDeclarationWithInitializer
    public boolean hasInitializer() {
        return getInitializer() != null;
    }

    public KtProperty getProperty() {
        KtProperty ktProperty = (KtProperty) getParent();
        if (ktProperty == null) {
            $$$reportNull$$$0(7);
        }
        return ktProperty;
    }

    @Override // org.jetbrains.kotlin.com.intellij.extapi.psi.ASTDelegatePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public int getTextOffset() {
        return getNamePlaceholder().getTextRange().getStartOffset();
    }
}
