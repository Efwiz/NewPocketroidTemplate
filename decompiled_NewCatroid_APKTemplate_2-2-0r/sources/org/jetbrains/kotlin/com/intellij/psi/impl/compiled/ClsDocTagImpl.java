package org.jetbrains.kotlin.com.intellij.psi.impl.compiled;

import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaDocElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement;
import org.jetbrains.kotlin.com.intellij.psi.javadoc.PsiDocComment;
import org.jetbrains.kotlin.com.intellij.psi.javadoc.PsiDocTag;
import org.jetbrains.kotlin.com.intellij.psi.javadoc.PsiDocTagValue;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;

/* JADX INFO: loaded from: classes3.dex */
class ClsDocTagImpl extends ClsElementImpl implements PsiDocTag {
    private final ClsDocCommentImpl myDocComment;
    private final PsiElement myNameElement;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 3 || i == 6 || i == 7) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 3 || i == 6 || i == 7) ? 2 : 3];
        switch (i) {
            case 1:
            case 5:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
            case 2:
            case 3:
            case 6:
            case 7:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/compiled/ClsDocTagImpl";
                break;
            case 4:
                objArr[0] = "text";
                break;
            case 8:
                objArr[0] = "visitor";
                break;
            case 9:
                objArr[0] = "name";
                break;
            default:
                objArr[0] = "buffer";
                break;
        }
        if (i == 2) {
            objArr[1] = "textToCharArray";
        } else if (i == 3) {
            objArr[1] = "getName";
        } else if (i == 6) {
            objArr[1] = "getChildren";
        } else if (i != 7) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/compiled/ClsDocTagImpl";
        } else {
            objArr[1] = "getDataElements";
        }
        switch (i) {
            case 1:
                objArr[2] = "setMirror";
                break;
            case 2:
            case 3:
            case 6:
            case 7:
                break;
            case 4:
            case 5:
                objArr[2] = "textMatches";
                break;
            case 8:
                objArr[2] = "accept";
                break;
            case 9:
                objArr[2] = "setName";
                break;
            default:
                objArr[2] = "appendMirrorText";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 3 && i != 6 && i != 7) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.javadoc.PsiDocTag
    public PsiDocTagValue getValueElement() {
        return null;
    }

    ClsDocTagImpl(ClsDocCommentImpl clsDocCommentImpl, String str) {
        this.myDocComment = clsDocCommentImpl;
        this.myNameElement = new NameElement(this, str);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsElementImpl
    public void appendMirrorText(int i, StringBuilder sb) {
        if (sb == null) {
            $$$reportNull$$$0(0);
        }
        sb.append(this.myNameElement.getText());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsElementImpl
    public void setMirror(TreeElement treeElement) throws ClsElementImpl.InvalidMirrorException {
        if (treeElement == null) {
            $$$reportNull$$$0(1);
        }
        setMirrorCheckingType(treeElement, JavaDocElementType.DOC_TAG);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsElementImpl, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String getText() {
        return this.myNameElement.getText();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsElementImpl, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public char[] textToCharArray() {
        char[] cArrTextToCharArray = this.myNameElement.textToCharArray();
        if (cArrTextToCharArray == null) {
            $$$reportNull$$$0(2);
        }
        return cArrTextToCharArray;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.navigation.NavigationItem, org.jetbrains.kotlin.com.intellij.pom.PomNamedTarget, org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement, org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedNamedElement
    /* JADX INFO: renamed from: getName */
    public String getMyName() {
        String strSubstring = getNameElement().getText().substring(1);
        if (strSubstring == null) {
            $$$reportNull$$$0(3);
        }
        return strSubstring;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsElementImpl, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean textMatches(CharSequence charSequence) {
        if (charSequence == null) {
            $$$reportNull$$$0(4);
        }
        return this.myNameElement.textMatches(charSequence);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean textMatches(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(5);
        }
        return this.myNameElement.textMatches(psiElement);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsElementImpl, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public int getTextLength() {
        return this.myNameElement.getTextLength();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement[] getChildren() {
        return new PsiElement[]{this.myNameElement};
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    /* JADX INFO: renamed from: getParent */
    public PsiElement getMyParent() {
        return getContainingComment();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.javadoc.PsiDocTag
    public PsiDocComment getContainingComment() {
        return this.myDocComment;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.javadoc.PsiDocTag
    public PsiElement getNameElement() {
        return this.myNameElement;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.javadoc.PsiDocTag
    public PsiElement[] getDataElements() {
        PsiElement[] psiElementArr = PsiElement.EMPTY_ARRAY;
        if (psiElementArr == null) {
            $$$reportNull$$$0(7);
        }
        return psiElementArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(8);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitDocTag(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement, org.jetbrains.kotlin.com.intellij.psi.PsiMethod, org.jetbrains.kotlin.com.intellij.pom.PomRenameableTarget
    public PsiElement setName(String str) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(9);
        }
        PsiImplUtil.setName(getNameElement(), str);
        return this;
    }

    private static class NameElement extends ClsElementImpl {
        private final ClsDocTagImpl myParent;
        private final String myText;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = (i == 2 || i == 3) ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
            Object[] objArr = new Object[(i == 2 || i == 3) ? 3 : 2];
            if (i == 2) {
                objArr[0] = "buffer";
            } else if (i != 3) {
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/compiled/ClsDocTagImpl$NameElement";
            } else {
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
            }
            if (i == 1) {
                objArr[1] = "getChildren";
            } else if (i == 2 || i == 3) {
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/compiled/ClsDocTagImpl$NameElement";
            } else {
                objArr[1] = "textToCharArray";
            }
            if (i == 2) {
                objArr[2] = "appendMirrorText";
            } else if (i == 3) {
                objArr[2] = "setMirror";
            }
            String str2 = String.format(str, objArr);
            if (i != 2 && i != 3) {
                throw new IllegalStateException(str2);
            }
            throw new IllegalArgumentException(str2);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsElementImpl
        public void appendMirrorText(int i, StringBuilder sb) {
            if (sb == null) {
                $$$reportNull$$$0(2);
            }
        }

        NameElement(ClsDocTagImpl clsDocTagImpl, String str) {
            this.myParent = clsDocTagImpl;
            this.myText = str;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsElementImpl, org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public String getText() {
            return this.myText;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsElementImpl, org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public char[] textToCharArray() {
            char[] charArray = this.myText.toCharArray();
            if (charArray == null) {
                $$$reportNull$$$0(0);
            }
            return charArray;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public PsiElement[] getChildren() {
            PsiElement[] psiElementArr = PsiElement.EMPTY_ARRAY;
            if (psiElementArr == null) {
                $$$reportNull$$$0(1);
            }
            return psiElementArr;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsElementImpl
        public void setMirror(TreeElement treeElement) throws ClsElementImpl.InvalidMirrorException {
            if (treeElement == null) {
                $$$reportNull$$$0(3);
            }
            setMirrorCheckingType(treeElement, null);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        /* JADX INFO: renamed from: getParent */
        public PsiElement getMyParent() {
            return this.myParent;
        }
    }
}
