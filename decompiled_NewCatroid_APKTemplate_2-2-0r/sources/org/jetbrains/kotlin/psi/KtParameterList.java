package org.jetbrains.kotlin.psi;

import java.util.List;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.psi.psiUtil.KtPsiUtilKt;
import org.jetbrains.kotlin.psi.stubs.KotlinPlaceHolderStub;
import org.jetbrains.kotlin.psi.stubs.elements.KtStubElementTypes;

/* JADX INFO: loaded from: classes6.dex */
public class KtParameterList extends KtElementImplStub<KotlinPlaceHolderStub<KtParameterList>> {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 3 || i == 5 || i == 7 || i == 9) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 3 || i == 5 || i == 7 || i == 9) ? 2 : 3];
        switch (i) {
            case 1:
                objArr[0] = "stub";
                break;
            case 2:
                objArr[0] = "visitor";
                break;
            case 3:
            case 5:
            case 7:
            case 9:
                objArr[0] = "org/jetbrains/kotlin/psi/KtParameterList";
                break;
            case 4:
            case 6:
            case 8:
            case 10:
                objArr[0] = "parameter";
                break;
            default:
                objArr[0] = "node";
                break;
        }
        if (i == 3) {
            objArr[1] = "getParameters";
        } else if (i == 5) {
            objArr[1] = "addParameter";
        } else if (i == 7) {
            objArr[1] = "addParameterBefore";
        } else if (i != 9) {
            objArr[1] = "org/jetbrains/kotlin/psi/KtParameterList";
        } else {
            objArr[1] = "addParameterAfter";
        }
        switch (i) {
            case 2:
                objArr[2] = "accept";
                break;
            case 3:
            case 5:
            case 7:
            case 9:
                break;
            case 4:
                objArr[2] = "addParameter";
                break;
            case 6:
                objArr[2] = "addParameterBefore";
                break;
            case 8:
                objArr[2] = "addParameterAfter";
                break;
            case 10:
                objArr[2] = "removeParameter";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 3 && i != 5 && i != 7 && i != 9) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtParameterList(ASTNode aSTNode) {
        super(aSTNode);
        if (aSTNode == null) {
            $$$reportNull$$$0(0);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtParameterList(KotlinPlaceHolderStub<KtParameterList> kotlinPlaceHolderStub) {
        super(kotlinPlaceHolderStub, KtStubElementTypes.VALUE_PARAMETER_LIST);
        if (kotlinPlaceHolderStub == null) {
            $$$reportNull$$$0(1);
        }
    }

    @Override // org.jetbrains.kotlin.psi.KtElementImplStub, org.jetbrains.kotlin.psi.KtElement, org.jetbrains.kotlin.psi.KtExpression
    public <R, D> R accept(KtVisitor<R, D> ktVisitor, D d) {
        if (ktVisitor == null) {
            $$$reportNull$$$0(2);
        }
        return ktVisitor.visitParameterList(this, d);
    }

    @Override // org.jetbrains.kotlin.psi.KtElementImplStub, org.jetbrains.kotlin.com.intellij.extapi.psi.StubBasedPsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    /* JADX INFO: renamed from: getParent */
    public PsiElement getMyParent() {
        KotlinPlaceHolderStub kotlinPlaceHolderStub = (KotlinPlaceHolderStub) getStub();
        return kotlinPlaceHolderStub != null ? kotlinPlaceHolderStub.getParentStub().getPsi() : super.getMyParent();
    }

    public List<KtParameter> getParameters() {
        List stubOrPsiChildrenAsList = getStubOrPsiChildrenAsList(KtStubElementTypes.VALUE_PARAMETER);
        if (stubOrPsiChildrenAsList == null) {
            $$$reportNull$$$0(3);
        }
        return stubOrPsiChildrenAsList;
    }

    public KtParameter addParameter(KtParameter ktParameter) {
        if (ktParameter == null) {
            $$$reportNull$$$0(4);
        }
        KtParameter ktParameter2 = (KtParameter) EditCommaSeparatedListHelper.INSTANCE.addItem(this, getParameters(), ktParameter);
        if (ktParameter2 == null) {
            $$$reportNull$$$0(5);
        }
        return ktParameter2;
    }

    public KtParameter addParameterBefore(KtParameter ktParameter, KtParameter ktParameter2) {
        if (ktParameter == null) {
            $$$reportNull$$$0(6);
        }
        KtParameter ktParameter3 = (KtParameter) EditCommaSeparatedListHelper.INSTANCE.addItemBefore(this, getParameters(), ktParameter, ktParameter2);
        if (ktParameter3 == null) {
            $$$reportNull$$$0(7);
        }
        return ktParameter3;
    }

    public KtParameter addParameterAfter(KtParameter ktParameter, KtParameter ktParameter2) {
        if (ktParameter == null) {
            $$$reportNull$$$0(8);
        }
        KtParameter ktParameter3 = (KtParameter) EditCommaSeparatedListHelper.INSTANCE.addItemAfter(this, getParameters(), ktParameter, ktParameter2);
        if (ktParameter3 == null) {
            $$$reportNull$$$0(9);
        }
        return ktParameter3;
    }

    public void removeParameter(KtParameter ktParameter) {
        if (ktParameter == null) {
            $$$reportNull$$$0(10);
        }
        EditCommaSeparatedListHelper.INSTANCE.removeItem(ktParameter);
    }

    public void removeParameter(int i) {
        removeParameter(getParameters().get(i));
    }

    public KtDeclarationWithBody getOwnerFunction() {
        PsiElement parentByStub = getParentByStub();
        if (parentByStub instanceof KtDeclarationWithBody) {
            return (KtDeclarationWithBody) parentByStub;
        }
        return null;
    }

    public PsiElement getRightParenthesis() {
        return findChildByType(KtTokens.RPAR);
    }

    public PsiElement getLeftParenthesis() {
        return findChildByType(KtTokens.LPAR);
    }

    public PsiElement getFirstComma() {
        return findChildByType(KtTokens.COMMA);
    }

    public PsiElement getTrailingComma() {
        PsiElement parent = getMyParent();
        if ((parent instanceof KtFunctionLiteral) || (parent instanceof KtPropertyAccessor)) {
            return KtPsiUtilKt.getTrailingCommaByElementsList(this);
        }
        return KtPsiUtilKt.getTrailingCommaByClosingElement(getRightParenthesis());
    }
}
