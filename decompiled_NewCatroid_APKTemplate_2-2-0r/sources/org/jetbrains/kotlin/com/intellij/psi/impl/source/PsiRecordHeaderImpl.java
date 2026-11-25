package org.jetbrains.kotlin.com.intellij.psi.impl.source;

import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiRecordComponent;
import org.jetbrains.kotlin.com.intellij.psi.PsiRecordHeader;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.JavaStubElementTypes;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiRecordHeaderStub;

/* JADX INFO: loaded from: classes3.dex */
public class PsiRecordHeaderImpl extends JavaStubPsiElement<PsiRecordHeaderStub> implements PsiRecordHeader {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 3 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 3 ? 3 : 2];
        if (i == 1) {
            objArr[0] = "node";
        } else if (i == 2) {
            objArr[0] = "visitor";
        } else if (i != 3) {
            objArr[0] = "stub";
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/PsiRecordHeaderImpl";
        }
        if (i != 3) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/PsiRecordHeaderImpl";
        } else {
            objArr[1] = "getRecordComponents";
        }
        if (i == 2) {
            objArr[2] = "accept";
        } else if (i != 3) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i == 3) {
            throw new IllegalStateException(str2);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PsiRecordHeaderImpl(PsiRecordHeaderStub psiRecordHeaderStub) {
        super(psiRecordHeaderStub, JavaStubElementTypes.RECORD_HEADER);
        if (psiRecordHeaderStub == null) {
            $$$reportNull$$$0(0);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PsiRecordHeaderImpl(ASTNode aSTNode) {
        super(aSTNode);
        if (aSTNode == null) {
            $$$reportNull$$$0(1);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiRecordHeader
    public PsiClass getContainingClass() {
        PsiElement parent = getMyParent();
        if (parent instanceof PsiClass) {
            return (PsiClass) parent;
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(2);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitRecordHeader(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiRecordHeader";
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiRecordHeader
    public PsiRecordComponent[] getRecordComponents() {
        PsiRecordComponent[] psiRecordComponentArr = (PsiRecordComponent[]) getStubOrPsiChildren(JavaStubElementTypes.RECORD_COMPONENT, PsiRecordComponent.EMPTY_ARRAY);
        if (psiRecordComponentArr == null) {
            $$$reportNull$$$0(3);
        }
        return psiRecordComponentArr;
    }
}
