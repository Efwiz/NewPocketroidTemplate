package org.jetbrains.kotlin.psi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.jetbrains.kotlin.com.google.common.collect.Lists;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.psi.stubs.KotlinUserTypeStub;
import org.jetbrains.kotlin.psi.stubs.elements.KtStubElementTypes;

/* JADX INFO: loaded from: classes6.dex */
public class KtUserType extends KtElementImplStub<KotlinUserTypeStub> implements KtTypeElement {
    static final /* synthetic */ boolean $assertionsDisabled = false;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 3 || i == 4) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 3 || i == 4) ? 2 : 3];
        if (i == 1) {
            objArr[0] = "stub";
        } else if (i == 2) {
            objArr[0] = "visitor";
        } else if (i == 3 || i == 4) {
            objArr[0] = "org/jetbrains/kotlin/psi/KtUserType";
        } else {
            objArr[0] = "node";
        }
        if (i == 3) {
            objArr[1] = "getTypeArguments";
        } else if (i != 4) {
            objArr[1] = "org/jetbrains/kotlin/psi/KtUserType";
        } else {
            objArr[1] = "getTypeArgumentsAsTypes";
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
    public KtUserType(ASTNode aSTNode) {
        super(aSTNode);
        if (aSTNode == null) {
            $$$reportNull$$$0(0);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtUserType(KotlinUserTypeStub kotlinUserTypeStub) {
        super(kotlinUserTypeStub, KtStubElementTypes.USER_TYPE);
        if (kotlinUserTypeStub == null) {
            $$$reportNull$$$0(1);
        }
    }

    @Override // org.jetbrains.kotlin.psi.KtElementImplStub, org.jetbrains.kotlin.psi.KtElement, org.jetbrains.kotlin.psi.KtExpression
    public <R, D> R accept(KtVisitor<R, D> ktVisitor, D d) {
        if (ktVisitor == null) {
            $$$reportNull$$$0(2);
        }
        return ktVisitor.visitUserType(this, d);
    }

    public KtTypeArgumentList getTypeArgumentList() {
        return (KtTypeArgumentList) getStubOrPsiChild(KtStubElementTypes.TYPE_ARGUMENT_LIST);
    }

    public List<KtTypeProjection> getTypeArguments() {
        KtTypeArgumentList typeArgumentList = getTypeArgumentList();
        List<KtTypeProjection> listEmptyList = typeArgumentList == null ? Collections.emptyList() : typeArgumentList.getArguments();
        if (listEmptyList == null) {
            $$$reportNull$$$0(3);
        }
        return listEmptyList;
    }

    @Override // org.jetbrains.kotlin.psi.KtTypeElement
    public List<KtTypeReference> getTypeArgumentsAsTypes() {
        ArrayList arrayListNewArrayList = Lists.newArrayList();
        Iterator<KtTypeProjection> it = getTypeArguments().iterator();
        while (it.hasNext()) {
            arrayListNewArrayList.add(it.next().getTypeReference());
        }
        if (arrayListNewArrayList == null) {
            $$$reportNull$$$0(4);
        }
        return arrayListNewArrayList;
    }

    @IfNotParsed
    public KtSimpleNameExpression getReferenceExpression() {
        KtNameReferenceExpression ktNameReferenceExpression = (KtNameReferenceExpression) getStubOrPsiChild(KtStubElementTypes.REFERENCE_EXPRESSION);
        return ktNameReferenceExpression != null ? ktNameReferenceExpression : (KtSimpleNameExpression) getStubOrPsiChild(KtStubElementTypes.ENUM_ENTRY_SUPERCLASS_REFERENCE_EXPRESSION);
    }

    public KtUserType getQualifier() {
        return (KtUserType) getStubOrPsiChild(KtStubElementTypes.USER_TYPE);
    }

    public void deleteQualifier() {
        KtUserType qualifier = getQualifier();
        PsiElement psiElementFindChildByType = findChildByType(KtTokens.DOT);
        qualifier.delete();
        psiElementFindChildByType.delete();
    }

    public String getReferencedName() {
        KtSimpleNameExpression referenceExpression = getReferenceExpression();
        if (referenceExpression == null) {
            return null;
        }
        return referenceExpression.getReferencedName();
    }
}
