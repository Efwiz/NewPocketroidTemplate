package org.jetbrains.kotlin.com.intellij.psi.impl.source;

import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassType;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementFactory;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceList;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.JavaClassReferenceListElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiClassReferenceListStub;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType;

/* JADX INFO: loaded from: classes3.dex */
public class PsiReferenceListImpl extends JavaStubPsiElement<PsiClassReferenceListStub> implements PsiReferenceList {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 3 || i == 4) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 3 || i == 4) ? 2 : 3];
        if (i == 1) {
            objArr[0] = "node";
        } else if (i == 2 || i == 3 || i == 4) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/PsiReferenceListImpl";
        } else if (i != 5) {
            objArr[0] = "stub";
        } else {
            objArr[0] = "visitor";
        }
        if (i == 2) {
            objArr[1] = "getReferenceElements";
        } else if (i == 3 || i == 4) {
            objArr[1] = "getReferencedTypes";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/PsiReferenceListImpl";
        }
        if (i != 2 && i != 3 && i != 4) {
            if (i != 5) {
                objArr[2] = "<init>";
            } else {
                objArr[2] = "accept";
            }
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 3 && i != 4) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PsiReferenceListImpl(PsiClassReferenceListStub psiClassReferenceListStub) {
        super(psiClassReferenceListStub, psiClassReferenceListStub.getStubType());
        if (psiClassReferenceListStub == null) {
            $$$reportNull$$$0(0);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PsiReferenceListImpl(ASTNode aSTNode) {
        super(aSTNode);
        if (aSTNode == null) {
            $$$reportNull$$$0(1);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReferenceList
    public PsiJavaCodeReferenceElement[] getReferenceElements() {
        PsiJavaCodeReferenceElement[] psiJavaCodeReferenceElementArr = (PsiJavaCodeReferenceElement[]) calcTreeElement().getChildrenAsPsiElements(JavaElementType.JAVA_CODE_REFERENCE, PsiJavaCodeReferenceElement.ARRAY_FACTORY);
        if (psiJavaCodeReferenceElementArr == null) {
            $$$reportNull$$$0(2);
        }
        return psiJavaCodeReferenceElementArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReferenceList
    public PsiClassType[] getReferencedTypes() {
        PsiClassReferenceListStub psiClassReferenceListStub = (PsiClassReferenceListStub) getGreenStub();
        if (psiClassReferenceListStub != null) {
            PsiClassType[] referencedTypes = psiClassReferenceListStub.getReferencedTypes();
            if (referencedTypes == null) {
                $$$reportNull$$$0(3);
            }
            return referencedTypes;
        }
        PsiJavaCodeReferenceElement[] referenceElements = getReferenceElements();
        PsiElementFactory elementFactory = JavaPsiFacade.getElementFactory(getProject());
        int length = referenceElements.length;
        PsiClassType[] psiClassTypeArr = new PsiClassType[length];
        for (int i = 0; i < length; i++) {
            psiClassTypeArr[i] = elementFactory.createType(referenceElements[i]);
        }
        return psiClassTypeArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReferenceList
    public PsiReferenceList.Role getRole() {
        return JavaClassReferenceListElementType.elementTypeToRole(getElementType());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(5);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitReferenceList(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiReferenceList";
    }
}
