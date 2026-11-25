package org.jetbrains.kotlin.com.intellij.psi.impl.compiled;

import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationMemberValue;
import org.jetbrains.kotlin.com.intellij.psi.PsiArrayInitializerMemberValue;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.SourceTreeToPsiMap;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement;

/* JADX INFO: loaded from: classes3.dex */
public class ClsArrayInitializerMemberValueImpl extends ClsElementImpl implements PsiArrayInitializerMemberValue {
    private final PsiAnnotationMemberValue[] myInitializers;
    private final ClsElementImpl myParent;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 4 || i == 6) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 4 || i == 6) ? 2 : 3];
        switch (i) {
            case 1:
                objArr[0] = "initializers";
                break;
            case 2:
                objArr[0] = "buffer";
                break;
            case 3:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
            case 4:
            case 6:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/compiled/ClsArrayInitializerMemberValueImpl";
                break;
            case 5:
                objArr[0] = "visitor";
                break;
            default:
                objArr[0] = "parent";
                break;
        }
        if (i == 4) {
            objArr[1] = "getChildren";
        } else if (i != 6) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/compiled/ClsArrayInitializerMemberValueImpl";
        } else {
            objArr[1] = "getInitializers";
        }
        if (i == 2) {
            objArr[2] = "appendMirrorText";
        } else if (i == 3) {
            objArr[2] = "setMirror";
        } else if (i != 4) {
            if (i == 5) {
                objArr[2] = "accept";
            } else if (i != 6) {
                objArr[2] = "<init>";
            }
        }
        String str2 = String.format(str, objArr);
        if (i != 4 && i != 6) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public ClsArrayInitializerMemberValueImpl(ClsElementImpl clsElementImpl, PsiAnnotationMemberValue[] psiAnnotationMemberValueArr) {
        if (clsElementImpl == null) {
            $$$reportNull$$$0(0);
        }
        if (psiAnnotationMemberValueArr == null) {
            $$$reportNull$$$0(1);
        }
        this.myParent = clsElementImpl;
        this.myInitializers = psiAnnotationMemberValueArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsElementImpl, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String getText() {
        StringBuilder sb = new StringBuilder();
        appendMirrorText(0, sb);
        return sb.toString();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsElementImpl
    public void appendMirrorText(int i, StringBuilder sb) {
        if (sb == null) {
            $$$reportNull$$$0(2);
        }
        sb.append('{');
        for (int i2 = 0; i2 < this.myInitializers.length; i2++) {
            if (i2 > 0) {
                sb.append(", ");
            }
            appendText(this.myInitializers[i2], 0, sb);
        }
        sb.append('}');
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsElementImpl
    public void setMirror(TreeElement treeElement) throws ClsElementImpl.InvalidMirrorException {
        if (treeElement == null) {
            $$$reportNull$$$0(3);
        }
        setMirrorCheckingType(treeElement, null);
        setMirrors(getInitializers(), ((PsiArrayInitializerMemberValue) SourceTreeToPsiMap.treeToPsiNotNull(treeElement)).getInitializers());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement[] getChildren() {
        PsiAnnotationMemberValue[] psiAnnotationMemberValueArr = this.myInitializers;
        if (psiAnnotationMemberValueArr == null) {
            $$$reportNull$$$0(4);
        }
        return psiAnnotationMemberValueArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    /* JADX INFO: renamed from: getParent */
    public PsiElement getLightParent() {
        return this.myParent;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(5);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitAnnotationArrayInitializer(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiArrayInitializerMemberValue
    public PsiAnnotationMemberValue[] getInitializers() {
        PsiAnnotationMemberValue[] psiAnnotationMemberValueArr = this.myInitializers;
        if (psiAnnotationMemberValueArr == null) {
            $$$reportNull$$$0(6);
        }
        return psiAnnotationMemberValueArr;
    }
}
