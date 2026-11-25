package org.jetbrains.kotlin.psi;

import java.util.List;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.psi.psiUtil.KtPsiUtilKt;
import org.jetbrains.kotlin.psi.stubs.KotlinPlaceHolderStub;
import org.jetbrains.kotlin.psi.stubs.elements.KtStubElementTypes;

/* JADX INFO: loaded from: classes6.dex */
public class KtValueArgumentList extends KtElementImplStub<KotlinPlaceHolderStub<KtValueArgumentList>> {
    static final /* synthetic */ boolean $assertionsDisabled = false;

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
                objArr[0] = "org/jetbrains/kotlin/psi/KtValueArgumentList";
                break;
            case 4:
            case 6:
            case 8:
            case 10:
                objArr[0] = "argument";
                break;
            default:
                objArr[0] = "node";
                break;
        }
        if (i == 3) {
            objArr[1] = "getArguments";
        } else if (i == 5) {
            objArr[1] = "addArgument";
        } else if (i == 7) {
            objArr[1] = "addArgumentAfter";
        } else if (i != 9) {
            objArr[1] = "org/jetbrains/kotlin/psi/KtValueArgumentList";
        } else {
            objArr[1] = "addArgumentBefore";
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
                objArr[2] = "addArgument";
                break;
            case 6:
                objArr[2] = "addArgumentAfter";
                break;
            case 8:
                objArr[2] = "addArgumentBefore";
                break;
            case 10:
                objArr[2] = "removeArgument";
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
    public KtValueArgumentList(ASTNode aSTNode) {
        super(aSTNode);
        if (aSTNode == null) {
            $$$reportNull$$$0(0);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtValueArgumentList(KotlinPlaceHolderStub<KtValueArgumentList> kotlinPlaceHolderStub) {
        super(kotlinPlaceHolderStub, KtStubElementTypes.VALUE_ARGUMENT_LIST);
        if (kotlinPlaceHolderStub == null) {
            $$$reportNull$$$0(1);
        }
    }

    @Override // org.jetbrains.kotlin.psi.KtElementImplStub, org.jetbrains.kotlin.psi.KtElement, org.jetbrains.kotlin.psi.KtExpression
    public <R, D> R accept(KtVisitor<R, D> ktVisitor, D d) {
        if (ktVisitor == null) {
            $$$reportNull$$$0(2);
        }
        return ktVisitor.visitValueArgumentList(this, d);
    }

    public List<KtValueArgument> getArguments() {
        List stubOrPsiChildrenAsList = getStubOrPsiChildrenAsList(KtStubElementTypes.VALUE_ARGUMENT);
        if (stubOrPsiChildrenAsList == null) {
            $$$reportNull$$$0(3);
        }
        return stubOrPsiChildrenAsList;
    }

    public PsiElement getRightParenthesis() {
        return findChildByType(KtTokens.RPAR);
    }

    public PsiElement getLeftParenthesis() {
        return findChildByType(KtTokens.LPAR);
    }

    public KtValueArgument addArgument(KtValueArgument ktValueArgument) {
        if (ktValueArgument == null) {
            $$$reportNull$$$0(4);
        }
        KtValueArgument ktValueArgument2 = (KtValueArgument) EditCommaSeparatedListHelper.INSTANCE.addItem(this, getArguments(), ktValueArgument);
        if (ktValueArgument2 == null) {
            $$$reportNull$$$0(5);
        }
        return ktValueArgument2;
    }

    public KtValueArgument addArgumentAfter(KtValueArgument ktValueArgument, KtValueArgument ktValueArgument2) {
        if (ktValueArgument == null) {
            $$$reportNull$$$0(6);
        }
        KtValueArgument ktValueArgument3 = (KtValueArgument) EditCommaSeparatedListHelper.INSTANCE.addItemAfter(this, getArguments(), ktValueArgument, ktValueArgument2);
        if (ktValueArgument3 == null) {
            $$$reportNull$$$0(7);
        }
        return ktValueArgument3;
    }

    public KtValueArgument addArgumentBefore(KtValueArgument ktValueArgument, KtValueArgument ktValueArgument2) {
        if (ktValueArgument == null) {
            $$$reportNull$$$0(8);
        }
        KtValueArgument ktValueArgument3 = (KtValueArgument) EditCommaSeparatedListHelper.INSTANCE.addItemBefore(this, getArguments(), ktValueArgument, ktValueArgument2);
        if (ktValueArgument3 == null) {
            $$$reportNull$$$0(9);
        }
        return ktValueArgument3;
    }

    public void removeArgument(KtValueArgument ktValueArgument) {
        if (ktValueArgument == null) {
            $$$reportNull$$$0(10);
        }
        EditCommaSeparatedListHelper.INSTANCE.removeItem(ktValueArgument);
    }

    public void removeArgument(int i) {
        removeArgument(getArguments().get(i));
    }

    public PsiElement getTrailingComma() {
        return KtPsiUtilKt.getTrailingCommaByClosingElement(getRightParenthesis());
    }
}
