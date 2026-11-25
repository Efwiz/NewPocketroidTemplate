package org.jetbrains.kotlin.psi;

import java.util.List;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.psi.psiUtil.KtPsiUtilKt;
import org.jetbrains.kotlin.psi.stubs.KotlinPlaceHolderStub;
import org.jetbrains.kotlin.psi.stubs.elements.KtStubElementTypes;

/* JADX INFO: loaded from: classes6.dex */
public class KtTypeParameterList extends KtElementImplStub<KotlinPlaceHolderStub<KtTypeParameterList>> {
    /* JADX WARN: Code duplicated, block: B:21:0x0033  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 4) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 4) ? 2 : 3];
        if (i == 1) {
            objArr[0] = "stub";
        } else if (i == 2) {
            objArr[0] = "org/jetbrains/kotlin/psi/KtTypeParameterList";
        } else if (i == 3) {
            objArr[0] = "typeParameter";
        } else if (i == 4) {
            objArr[0] = "org/jetbrains/kotlin/psi/KtTypeParameterList";
        } else if (i != 5) {
            objArr[0] = "node";
        } else {
            objArr[0] = "visitor";
        }
        if (i == 2) {
            objArr[1] = "getParameters";
        } else if (i != 4) {
            objArr[1] = "org/jetbrains/kotlin/psi/KtTypeParameterList";
        } else {
            objArr[1] = "addParameter";
        }
        if (i != 2) {
            if (i == 3) {
                objArr[2] = "addParameter";
            } else if (i != 4) {
                if (i != 5) {
                    objArr[2] = "<init>";
                } else {
                    objArr[2] = "accept";
                }
            }
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 4) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtTypeParameterList(ASTNode aSTNode) {
        super(aSTNode);
        if (aSTNode == null) {
            $$$reportNull$$$0(0);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtTypeParameterList(KotlinPlaceHolderStub<KtTypeParameterList> kotlinPlaceHolderStub) {
        super(kotlinPlaceHolderStub, KtStubElementTypes.TYPE_PARAMETER_LIST);
        if (kotlinPlaceHolderStub == null) {
            $$$reportNull$$$0(1);
        }
    }

    public List<KtTypeParameter> getParameters() {
        List stubOrPsiChildrenAsList = getStubOrPsiChildrenAsList(KtStubElementTypes.TYPE_PARAMETER);
        if (stubOrPsiChildrenAsList == null) {
            $$$reportNull$$$0(2);
        }
        return stubOrPsiChildrenAsList;
    }

    public KtTypeParameter addParameter(KtTypeParameter ktTypeParameter) {
        if (ktTypeParameter == null) {
            $$$reportNull$$$0(3);
        }
        KtTypeParameter ktTypeParameter2 = (KtTypeParameter) EditCommaSeparatedListHelper.INSTANCE.addItem(this, getParameters(), ktTypeParameter, KtTokens.LT);
        if (ktTypeParameter2 == null) {
            $$$reportNull$$$0(4);
        }
        return ktTypeParameter2;
    }

    @Override // org.jetbrains.kotlin.psi.KtElementImplStub, org.jetbrains.kotlin.psi.KtElement, org.jetbrains.kotlin.psi.KtExpression
    public <R, D> R accept(KtVisitor<R, D> ktVisitor, D d) {
        if (ktVisitor == null) {
            $$$reportNull$$$0(5);
        }
        return ktVisitor.visitTypeParameterList(this, d);
    }

    public PsiElement getTrailingComma() {
        return KtPsiUtilKt.getTrailingCommaByClosingElement(findChildByType(KtTokens.GT));
    }
}
