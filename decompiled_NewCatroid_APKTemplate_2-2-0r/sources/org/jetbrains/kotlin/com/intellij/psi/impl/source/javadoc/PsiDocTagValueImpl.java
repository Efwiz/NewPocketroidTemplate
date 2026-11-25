package org.jetbrains.kotlin.com.intellij.psi.impl.source.javadoc;

import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.psi.JavaDocTokenType;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiReference;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaDocElementType;
import org.jetbrains.kotlin.com.intellij.psi.javadoc.JavadocManager;
import org.jetbrains.kotlin.com.intellij.psi.javadoc.JavadocTagInfo;
import org.jetbrains.kotlin.com.intellij.psi.javadoc.PsiDocTag;
import org.jetbrains.kotlin.com.intellij.psi.javadoc.PsiDocTagValue;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;

/* JADX INFO: loaded from: classes3.dex */
public class PsiDocTagValueImpl extends CompositePsiElement implements PsiDocTagValue {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i != 1) {
            objArr[0] = "visitor";
        } else {
            objArr[0] = "child";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/javadoc/PsiDocTagValueImpl";
        if (i != 1) {
            objArr[2] = "accept";
        } else {
            objArr[2] = "getChildRole";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public PsiDocTagValueImpl() {
        super(JavaDocElementType.DOC_TAG_VALUE_ELEMENT);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(0);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitDocTagValue(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public int getChildRole(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(1);
        }
        if (aSTNode.getElementType() == JavaDocTokenType.DOC_TAG_VALUE_COMMA) {
            return 23;
        }
        if (aSTNode.getElementType() == JavaDocTokenType.DOC_TAG_VALUE_TOKEN && aSTNode.getTextLength() == 1 && aSTNode.getChars().charAt(0) == ',') {
            return 23;
        }
        return super.getChildRole(aSTNode);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiReference getReference() {
        JavadocTagInfo tagInfo;
        PsiDocTag psiDocTag = (PsiDocTag) PsiTreeUtil.getParentOfType(this, PsiDocTag.class);
        if (psiDocTag == null || (tagInfo = JavadocManager.SERVICE.getInstance(getProject()).getTagInfo(psiDocTag.getName())) == null) {
            return null;
        }
        return tagInfo.getReference(this);
    }
}
