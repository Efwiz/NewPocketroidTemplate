package org.jetbrains.kotlin.com.intellij.psi.impl.compiled;

import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.JavaTokenType;
import org.jetbrains.kotlin.com.intellij.psi.PsiDocCommentOwner;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaDocumentedElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaToken;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaDocElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement;
import org.jetbrains.kotlin.com.intellij.psi.javadoc.PsiDocComment;
import org.jetbrains.kotlin.com.intellij.psi.javadoc.PsiDocTag;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.ILazyParseableElementType;

/* JADX INFO: loaded from: classes3.dex */
class ClsDocCommentImpl extends ClsElementImpl implements JavaTokenType, PsiJavaToken, PsiDocComment {
    private final PsiDocCommentOwner myParent;
    private final PsiDocTag[] myTags;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 3 || i == 4 || i == 5 || i == 6 || i == 7) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 3 || i == 4 || i == 5 || i == 6 || i == 7) ? 2 : 3];
        switch (i) {
            case 1:
                objArr[0] = "buffer";
                break;
            case 2:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/compiled/ClsDocCommentImpl";
                break;
            case 8:
                objArr[0] = "visitor";
                break;
            default:
                objArr[0] = "parent";
                break;
        }
        if (i == 3) {
            objArr[1] = "getChildren";
        } else if (i == 4) {
            objArr[1] = "getDescriptionElements";
        } else if (i == 5) {
            objArr[1] = "getTags";
        } else if (i == 6) {
            objArr[1] = "findTagsByName";
        } else if (i != 7) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/compiled/ClsDocCommentImpl";
        } else {
            objArr[1] = "getTokenType";
        }
        switch (i) {
            case 1:
                objArr[2] = "appendMirrorText";
                break;
            case 2:
                objArr[2] = "setMirror";
                break;
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                break;
            case 8:
                objArr[2] = "accept";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 3 && i != 4 && i != 5 && i != 6 && i != 7) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    ClsDocCommentImpl(PsiDocCommentOwner psiDocCommentOwner) {
        if (psiDocCommentOwner == null) {
            $$$reportNull$$$0(0);
        }
        this.myParent = psiDocCommentOwner;
        this.myTags = new PsiDocTag[]{new ClsDocTagImpl(this, "@deprecated")};
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsElementImpl
    public void appendMirrorText(int i, StringBuilder sb) {
        if (sb == null) {
            $$$reportNull$$$0(1);
        }
        sb.append("/**");
        for (PsiDocTag psiDocTag : getTags()) {
            goNextLine(i + 1, sb);
            sb.append("* ");
            sb.append(psiDocTag.getText());
        }
        goNextLine(i + 1, sb);
        sb.append("*/");
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsElementImpl
    public void setMirror(TreeElement treeElement) throws ClsElementImpl.InvalidMirrorException {
        if (treeElement == null) {
            $$$reportNull$$$0(2);
        }
        setMirrorCheckingType(treeElement, JavaDocElementType.DOC_COMMENT);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement[] getChildren() {
        PsiDocTag[] tags = getTags();
        if (tags == null) {
            $$$reportNull$$$0(3);
        }
        return tags;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    /* JADX INFO: renamed from: getParent */
    public PsiElement getLightParent() {
        return this.myParent;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.javadoc.PsiDocComment, org.jetbrains.kotlin.com.intellij.psi.PsiDocCommentBase
    public PsiJavaDocumentedElement getOwner() {
        return PsiImplUtil.findDocCommentOwner(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.javadoc.PsiDocComment
    public PsiElement[] getDescriptionElements() {
        PsiElement[] psiElementArr = EMPTY_ARRAY;
        if (psiElementArr == null) {
            $$$reportNull$$$0(4);
        }
        return psiElementArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.javadoc.PsiDocComment
    public PsiDocTag[] getTags() {
        PsiDocTag[] psiDocTagArr = this.myTags;
        if (psiDocTagArr == null) {
            $$$reportNull$$$0(5);
        }
        return psiDocTagArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.javadoc.PsiDocComment
    public PsiDocTag findTagByName(String str) {
        if (str.equals("deprecated")) {
            return getTags()[0];
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.javadoc.PsiDocComment
    public PsiDocTag[] findTagsByName(String str) {
        PsiDocTag[] tags = str.equals("deprecated") ? getTags() : PsiDocTag.EMPTY_ARRAY;
        if (tags == null) {
            $$$reportNull$$$0(6);
        }
        return tags;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaToken, org.jetbrains.kotlin.com.intellij.psi.PsiComment
    public IElementType getTokenType() {
        ILazyParseableElementType iLazyParseableElementType = JavaDocElementType.DOC_COMMENT;
        if (iLazyParseableElementType == null) {
            $$$reportNull$$$0(7);
        }
        return iLazyParseableElementType;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(8);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitDocComment(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }
}
