package org.jetbrains.kotlin.com.intellij.psi.impl.compiled;

import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiRecordComponent;
import org.jetbrains.kotlin.com.intellij.psi.PsiRecordHeader;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.JavaStubElementTypes;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiRecordHeaderStub;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.SourceTreeToPsiMap;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement;

/* JADX INFO: loaded from: classes3.dex */
public class ClsRecordHeaderImpl extends ClsRepositoryPsiElement<PsiRecordHeaderStub> implements PsiRecordHeader {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 1 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 1 ? 3 : 2];
        if (i == 1) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/compiled/ClsRecordHeaderImpl";
        } else if (i == 2) {
            objArr[0] = "buffer";
        } else if (i == 3) {
            objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
        } else if (i != 4) {
            objArr[0] = "stub";
        } else {
            objArr[0] = "visitor";
        }
        if (i != 1) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/compiled/ClsRecordHeaderImpl";
        } else {
            objArr[1] = "getRecordComponents";
        }
        if (i != 1) {
            if (i == 2) {
                objArr[2] = "appendMirrorText";
            } else if (i == 3) {
                objArr[2] = "setMirror";
            } else if (i != 4) {
                objArr[2] = "<init>";
            } else {
                objArr[2] = "accept";
            }
        }
        String str2 = String.format(str, objArr);
        if (i == 1) {
            throw new IllegalStateException(str2);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ClsRecordHeaderImpl(PsiRecordHeaderStub psiRecordHeaderStub) {
        super(psiRecordHeaderStub);
        if (psiRecordHeaderStub == null) {
            $$$reportNull$$$0(0);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiRecordHeader
    public PsiRecordComponent[] getRecordComponents() {
        PsiRecordComponent[] psiRecordComponentArr = (PsiRecordComponent[]) getStub().getChildrenByType(JavaStubElementTypes.RECORD_COMPONENT, PsiRecordComponent.ARRAY_FACTORY);
        if (psiRecordComponentArr == null) {
            $$$reportNull$$$0(1);
        }
        return psiRecordComponentArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiRecordHeader
    public PsiClass getContainingClass() {
        return (PsiClass) getMyParent();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsElementImpl
    public void appendMirrorText(int i, StringBuilder sb) {
        if (sb == null) {
            $$$reportNull$$$0(2);
        }
        sb.append('(');
        PsiRecordComponent[] recordComponents = getRecordComponents();
        for (int i2 = 0; i2 < recordComponents.length; i2++) {
            if (i2 > 0) {
                sb.append(", ");
            }
            appendText(recordComponents[i2], i, sb);
        }
        sb.append(')');
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsElementImpl, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String getText() {
        StringBuilder sb = new StringBuilder();
        appendMirrorText(0, sb);
        return sb.toString();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsElementImpl
    public void setMirror(TreeElement treeElement) throws ClsElementImpl.InvalidMirrorException {
        if (treeElement == null) {
            $$$reportNull$$$0(3);
        }
        setMirrorCheckingType(treeElement, null);
        setMirrors(getRecordComponents(), ((PsiRecordHeader) SourceTreeToPsiMap.treeToPsiNotNull(treeElement)).getRecordComponents());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(4);
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
}
