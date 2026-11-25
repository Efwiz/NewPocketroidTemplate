package org.jetbrains.kotlin.com.intellij.psi.impl.compiled;

import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaToken;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;

/* JADX INFO: loaded from: classes3.dex */
class ClsJavaTokenImpl extends ClsElementImpl implements PsiJavaToken {
    private final ClsElementImpl myParent;
    private final String myTokenText;
    private final short myTokenTypeIndex;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 2 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 2 ? 3 : 2];
        if (i == 1) {
            objArr[0] = "tokenText";
        } else if (i == 2) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/compiled/ClsJavaTokenImpl";
        } else if (i == 3) {
            objArr[0] = "buffer";
        } else if (i == 4) {
            objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
        } else if (i != 5) {
            objArr[0] = "tokenType";
        } else {
            objArr[0] = "visitor";
        }
        if (i != 2) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/compiled/ClsJavaTokenImpl";
        } else {
            objArr[1] = "getChildren";
        }
        if (i != 2) {
            if (i == 3) {
                objArr[2] = "appendMirrorText";
            } else if (i == 4) {
                objArr[2] = "setMirror";
            } else if (i != 5) {
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

    ClsJavaTokenImpl(ClsElementImpl clsElementImpl, IElementType iElementType, String str) {
        if (iElementType == null) {
            $$$reportNull$$$0(0);
        }
        if (str == null) {
            $$$reportNull$$$0(1);
        }
        this.myParent = clsElementImpl;
        this.myTokenTypeIndex = iElementType.getIndex();
        this.myTokenText = str;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaToken, org.jetbrains.kotlin.com.intellij.psi.PsiComment
    public IElementType getTokenType() {
        return IElementType.find(this.myTokenTypeIndex);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsElementImpl, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String getText() {
        return this.myTokenText;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement[] getChildren() {
        PsiElement[] psiElementArr = EMPTY_ARRAY;
        if (psiElementArr == null) {
            $$$reportNull$$$0(2);
        }
        return psiElementArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    /* JADX INFO: renamed from: getParent */
    public PsiElement getLightParent() {
        return this.myParent;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsElementImpl
    public void appendMirrorText(int i, StringBuilder sb) {
        if (sb == null) {
            $$$reportNull$$$0(3);
        }
        sb.append(getText());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsElementImpl
    public void setMirror(TreeElement treeElement) throws ClsElementImpl.InvalidMirrorException {
        if (treeElement == null) {
            $$$reportNull$$$0(4);
        }
        setMirrorCheckingType(treeElement, getTokenType());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(5);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitJavaToken(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }
}
