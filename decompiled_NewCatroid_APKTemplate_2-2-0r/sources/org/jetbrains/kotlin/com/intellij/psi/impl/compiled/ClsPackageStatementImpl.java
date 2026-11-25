package org.jetbrains.kotlin.com.intellij.psi.impl.compiled;

import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierList;
import org.jetbrains.kotlin.com.intellij.psi.PsiPackageStatement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement;

/* JADX INFO: loaded from: classes3.dex */
class ClsPackageStatementImpl extends ClsElementImpl implements PsiPackageStatement {
    static ClsPackageStatementImpl NULL_PACKAGE = new ClsPackageStatementImpl();
    private final ClsFileImpl myFile;
    private final String myPackageName;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1) {
            objArr[0] = "buffer";
        } else if (i == 2) {
            objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
        } else if (i != 3) {
            objArr[0] = "file";
        } else {
            objArr[0] = "visitor";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/compiled/ClsPackageStatementImpl";
        if (i == 1) {
            objArr[2] = "appendMirrorText";
        } else if (i == 2) {
            objArr[2] = "setMirror";
        } else if (i != 3) {
            objArr[2] = "<init>";
        } else {
            objArr[2] = "accept";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    private ClsPackageStatementImpl() {
        this.myFile = null;
        this.myPackageName = null;
    }

    ClsPackageStatementImpl(ClsFileImpl clsFileImpl, String str) {
        if (clsFileImpl == null) {
            $$$reportNull$$$0(0);
        }
        this.myFile = clsFileImpl;
        this.myPackageName = str;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    /* JADX INFO: renamed from: getParent */
    public PsiElement getMyParent() {
        return this.myFile;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiPackageStatement
    public PsiJavaCodeReferenceElement getPackageReference() {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiPackageStatement
    public PsiModifierList getAnnotationList() {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement[] getChildren() {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiPackageStatement
    public String getPackageName() {
        return this.myPackageName;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsElementImpl
    public void appendMirrorText(int i, StringBuilder sb) {
        if (sb == null) {
            $$$reportNull$$$0(1);
        }
        if (this.myPackageName != null) {
            sb.append("package ").append(getPackageName()).append(';');
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsElementImpl
    public void setMirror(TreeElement treeElement) throws ClsElementImpl.InvalidMirrorException {
        if (treeElement == null) {
            $$$reportNull$$$0(2);
        }
        setMirrorCheckingType(treeElement, JavaElementType.PACKAGE_STATEMENT);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(3);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitPackageStatement(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiPackageStatement:" + getPackageName();
    }
}
