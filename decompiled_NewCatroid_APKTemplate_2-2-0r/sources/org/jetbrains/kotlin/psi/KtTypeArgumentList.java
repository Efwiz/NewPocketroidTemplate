package org.jetbrains.kotlin.psi;

import java.util.List;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.psi.psiUtil.KtPsiUtilKt;
import org.jetbrains.kotlin.psi.stubs.KotlinPlaceHolderStub;
import org.jetbrains.kotlin.psi.stubs.elements.KtStubElementTypes;

/* JADX INFO: loaded from: classes6.dex */
public class KtTypeArgumentList extends KtElementImplStub<KotlinPlaceHolderStub<KtTypeArgumentList>> {
    /* JADX WARN: Code duplicated, block: B:20:0x002e  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 3 || i == 5) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 3 || i == 5) ? 2 : 3];
        if (i == 1) {
            objArr[0] = "stub";
        } else if (i == 2) {
            objArr[0] = "visitor";
        } else if (i == 3) {
            objArr[0] = "org/jetbrains/kotlin/psi/KtTypeArgumentList";
        } else if (i == 4) {
            objArr[0] = "typeArgument";
        } else if (i != 5) {
            objArr[0] = "node";
        } else {
            objArr[0] = "org/jetbrains/kotlin/psi/KtTypeArgumentList";
        }
        if (i == 3) {
            objArr[1] = "getArguments";
        } else if (i != 5) {
            objArr[1] = "org/jetbrains/kotlin/psi/KtTypeArgumentList";
        } else {
            objArr[1] = "addArgument";
        }
        if (i == 2) {
            objArr[2] = "accept";
        } else if (i != 3) {
            if (i == 4) {
                objArr[2] = "addArgument";
            } else if (i != 5) {
                objArr[2] = "<init>";
            }
        }
        String str2 = String.format(str, objArr);
        if (i != 3 && i != 5) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtTypeArgumentList(ASTNode aSTNode) {
        super(aSTNode);
        if (aSTNode == null) {
            $$$reportNull$$$0(0);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtTypeArgumentList(KotlinPlaceHolderStub<KtTypeArgumentList> kotlinPlaceHolderStub) {
        super(kotlinPlaceHolderStub, KtStubElementTypes.TYPE_ARGUMENT_LIST);
        if (kotlinPlaceHolderStub == null) {
            $$$reportNull$$$0(1);
        }
    }

    @Override // org.jetbrains.kotlin.psi.KtElementImplStub, org.jetbrains.kotlin.psi.KtElement, org.jetbrains.kotlin.psi.KtExpression
    public <R, D> R accept(KtVisitor<R, D> ktVisitor, D d) {
        if (ktVisitor == null) {
            $$$reportNull$$$0(2);
        }
        return ktVisitor.visitTypeArgumentList(this, d);
    }

    public List<KtTypeProjection> getArguments() {
        List stubOrPsiChildrenAsList = getStubOrPsiChildrenAsList(KtStubElementTypes.TYPE_PROJECTION);
        if (stubOrPsiChildrenAsList == null) {
            $$$reportNull$$$0(3);
        }
        return stubOrPsiChildrenAsList;
    }

    public KtTypeProjection addArgument(KtTypeProjection ktTypeProjection) {
        if (ktTypeProjection == null) {
            $$$reportNull$$$0(4);
        }
        KtTypeProjection ktTypeProjection2 = (KtTypeProjection) EditCommaSeparatedListHelper.INSTANCE.addItem(this, getArguments(), ktTypeProjection, KtTokens.LT);
        if (ktTypeProjection2 == null) {
            $$$reportNull$$$0(5);
        }
        return ktTypeProjection2;
    }

    public PsiElement getTrailingComma() {
        return KtPsiUtilKt.getTrailingCommaByClosingElement(findChildByType(KtTokens.GT));
    }
}
