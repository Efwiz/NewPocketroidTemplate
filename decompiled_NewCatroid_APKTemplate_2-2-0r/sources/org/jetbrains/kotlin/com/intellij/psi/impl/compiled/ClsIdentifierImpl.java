package org.jetbrains.kotlin.com.intellij.psi.impl.compiled;

import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.JavaTokenType;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiIdentifier;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaToken;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;

/* JADX INFO: loaded from: classes3.dex */
class ClsIdentifierImpl extends ClsElementImpl implements PsiIdentifier, PsiJavaToken {
    private final PsiElement myParent;
    private final String myText;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 1 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 1 ? 3 : 2];
        if (i == 1) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/compiled/ClsIdentifierImpl";
        } else if (i == 2) {
            objArr[0] = "buffer";
        } else if (i == 3) {
            objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
        } else if (i != 4) {
            objArr[0] = "parent";
        } else {
            objArr[0] = "visitor";
        }
        if (i != 1) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/compiled/ClsIdentifierImpl";
        } else {
            objArr[1] = "getChildren";
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

    ClsIdentifierImpl(PsiElement psiElement, String str) {
        if (psiElement == null) {
            $$$reportNull$$$0(0);
        }
        this.myParent = psiElement;
        this.myText = str;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaToken, org.jetbrains.kotlin.com.intellij.psi.PsiComment
    public IElementType getTokenType() {
        return JavaTokenType.IDENTIFIER;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsElementImpl, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String getText() {
        return this.myText;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement[] getChildren() {
        PsiElement[] psiElementArr = PsiElement.EMPTY_ARRAY;
        if (psiElementArr == null) {
            $$$reportNull$$$0(1);
        }
        return psiElementArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    /* JADX INFO: renamed from: getParent */
    public PsiElement getLightParent() {
        return this.myParent;
    }

    private boolean isCorrectName(String str) {
        return str != null && ClsParsingUtil.isJavaIdentifier(str, ((PsiJavaFile) getContainingFile()).getLanguageLevel());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsElementImpl
    public void appendMirrorText(int i, StringBuilder sb) {
        if (sb == null) {
            $$$reportNull$$$0(2);
        }
        String text = getText();
        if (isCorrectName(text)) {
            sb.append(text);
        } else {
            sb.append("$$").append(text).append(" /* Real name is '").append(text).append("' */");
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsElementImpl
    public void setMirror(TreeElement treeElement) throws ClsElementImpl.InvalidMirrorException {
        if (treeElement == null) {
            $$$reportNull$$$0(3);
        }
        setMirrorCheckingType(treeElement, JavaTokenType.IDENTIFIER);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(4);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitIdentifier(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiIdentifier:" + getText();
    }
}
