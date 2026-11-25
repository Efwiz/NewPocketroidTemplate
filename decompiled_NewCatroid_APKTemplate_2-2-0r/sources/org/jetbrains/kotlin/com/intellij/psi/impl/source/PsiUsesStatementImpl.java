package org.jetbrains.kotlin.com.intellij.psi.impl.source;

import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.pom.java.LanguageLevel;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassType;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiUsesStatement;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.JavaStubElementTypes;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiUsesStatementStub;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;

/* JADX INFO: loaded from: classes3.dex */
public class PsiUsesStatementImpl extends JavaStubPsiElement<PsiUsesStatementStub> implements PsiUsesStatement {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1) {
            objArr[0] = "node";
        } else if (i != 2) {
            objArr[0] = "stub";
        } else {
            objArr[0] = "visitor";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/PsiUsesStatementImpl";
        if (i != 2) {
            objArr[2] = "<init>";
        } else {
            objArr[2] = "accept";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PsiUsesStatementImpl(PsiUsesStatementStub psiUsesStatementStub) {
        super(psiUsesStatementStub, JavaStubElementTypes.USES_STATEMENT);
        if (psiUsesStatementStub == null) {
            $$$reportNull$$$0(0);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PsiUsesStatementImpl(ASTNode aSTNode) {
        super(aSTNode);
        if (aSTNode == null) {
            $$$reportNull$$$0(1);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiUsesStatement
    public PsiJavaCodeReferenceElement getClassReference() {
        return (PsiJavaCodeReferenceElement) PsiTreeUtil.getChildOfType(this, PsiJavaCodeReferenceElement.class);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiUsesStatement
    public PsiClassType getClassType() {
        PsiUsesStatementStub psiUsesStatementStub = (PsiUsesStatementStub) getStub();
        PsiJavaCodeReferenceElement psiJavaCodeReferenceElementCreateReferenceFromText = psiUsesStatementStub != null ? JavaPsiFacade.getElementFactory(getProject()).createReferenceFromText(psiUsesStatementStub.getClassName(), this) : getClassReference();
        if (psiJavaCodeReferenceElementCreateReferenceFromText != null) {
            return new PsiClassReferenceType(psiJavaCodeReferenceElementCreateReferenceFromText, (LanguageLevel) null, PsiAnnotation.EMPTY_ARRAY);
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(2);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitUsesStatement(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiUsesStatement";
    }
}
