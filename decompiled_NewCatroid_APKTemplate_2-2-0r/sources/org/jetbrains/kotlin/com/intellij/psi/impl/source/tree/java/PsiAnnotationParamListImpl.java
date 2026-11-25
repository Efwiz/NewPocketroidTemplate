package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java;

import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationParameterList;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiNameValuePair;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.JavaStubElementTypes;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiAnnotationParameterListStub;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.JavaStubPsiElement;

/* JADX INFO: loaded from: classes3.dex */
public class PsiAnnotationParamListImpl extends JavaStubPsiElement<PsiAnnotationParameterListStub> implements PsiAnnotationParameterList {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 2 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 2 ? 3 : 2];
        if (i == 1) {
            objArr[0] = "node";
        } else if (i == 2) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/PsiAnnotationParamListImpl";
        } else if (i != 3) {
            objArr[0] = "stub";
        } else {
            objArr[0] = "visitor";
        }
        if (i != 2) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/PsiAnnotationParamListImpl";
        } else {
            objArr[1] = "getAttributes";
        }
        if (i != 2) {
            if (i != 3) {
                objArr[2] = "<init>";
            } else {
                objArr[2] = "accept";
            }
        }
        String str2 = String.format(str, objArr);
        if (i == 2) {
            throw new IllegalStateException(str2);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PsiAnnotationParamListImpl(PsiAnnotationParameterListStub psiAnnotationParameterListStub) {
        super(psiAnnotationParameterListStub, JavaStubElementTypes.ANNOTATION_PARAMETER_LIST);
        if (psiAnnotationParameterListStub == null) {
            $$$reportNull$$$0(0);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PsiAnnotationParamListImpl(ASTNode aSTNode) {
        super(aSTNode);
        if (aSTNode == null) {
            $$$reportNull$$$0(1);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationParameterList
    public PsiNameValuePair[] getAttributes() {
        PsiNameValuePair[] psiNameValuePairArr = (PsiNameValuePair[]) getStubOrPsiChildren(JavaStubElementTypes.NAME_VALUE_PAIR, PsiNameValuePair.ARRAY_FACTORY);
        if (psiNameValuePairArr == null) {
            $$$reportNull$$$0(2);
        }
        return psiNameValuePairArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(3);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitAnnotationParameterList(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiAnnotationParameterList";
    }
}
