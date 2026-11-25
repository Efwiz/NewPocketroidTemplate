package org.jetbrains.kotlin.com.intellij.psi.impl.source;

import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.pom.java.LanguageLevel;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassType;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiProvidesStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceList;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.JavaStubElementTypes;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiProvidesStatementStub;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;

/* JADX INFO: loaded from: classes3.dex */
public class PsiProvidesStatementImpl extends JavaStubPsiElement<PsiProvidesStatementStub> implements PsiProvidesStatement {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1) {
            objArr[0] = "node";
        } else if (i != 2) {
            objArr[0] = "stub";
        } else {
            objArr[0] = "visitor";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/PsiProvidesStatementImpl";
        if (i != 2) {
            objArr[2] = "<init>";
        } else {
            objArr[2] = "accept";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PsiProvidesStatementImpl(PsiProvidesStatementStub psiProvidesStatementStub) {
        super(psiProvidesStatementStub, JavaStubElementTypes.PROVIDES_STATEMENT);
        if (psiProvidesStatementStub == null) {
            $$$reportNull$$$0(0);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PsiProvidesStatementImpl(ASTNode aSTNode) {
        super(aSTNode);
        if (aSTNode == null) {
            $$$reportNull$$$0(1);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiProvidesStatement
    public PsiJavaCodeReferenceElement getInterfaceReference() {
        return (PsiJavaCodeReferenceElement) PsiTreeUtil.getChildOfType(this, PsiJavaCodeReferenceElement.class);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiProvidesStatement
    public PsiClassType getInterfaceType() {
        PsiProvidesStatementStub psiProvidesStatementStub = (PsiProvidesStatementStub) getStub();
        PsiJavaCodeReferenceElement psiJavaCodeReferenceElementCreateReferenceFromText = psiProvidesStatementStub != null ? JavaPsiFacade.getElementFactory(getProject()).createReferenceFromText(psiProvidesStatementStub.getInterface(), this) : getInterfaceReference();
        if (psiJavaCodeReferenceElementCreateReferenceFromText != null) {
            return new PsiClassReferenceType(psiJavaCodeReferenceElementCreateReferenceFromText, (LanguageLevel) null, PsiAnnotation.EMPTY_ARRAY);
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiProvidesStatement
    public PsiReferenceList getImplementationList() {
        return (PsiReferenceList) getStubOrPsiChild(JavaStubElementTypes.PROVIDES_WITH_LIST);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(2);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitProvidesStatement(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiProvidesStatement";
    }
}
