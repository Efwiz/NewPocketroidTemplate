package org.jetbrains.kotlin.com.intellij.psi.impl.source.javadoc;

import org.jetbrains.kotlin.com.intellij.core.JavaPsiBundle;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.psi.JavaDocTokenType;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiReference;
import org.jetbrains.kotlin.com.intellij.psi.javadoc.JavadocTagInfo;
import org.jetbrains.kotlin.com.intellij.psi.javadoc.PsiDocTagValue;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;

/* JADX INFO: loaded from: classes3.dex */
class ParamDocTagInfo implements JavadocTagInfo {
    @Override // org.jetbrains.kotlin.com.intellij.psi.javadoc.JavadocTagInfo
    public boolean isInline() {
        return false;
    }

    ParamDocTagInfo() {
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.javadoc.JavadocTagInfo
    public String getName() {
        return "param";
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.javadoc.JavadocTagInfo
    public boolean isValidInContext(PsiElement psiElement) {
        return (psiElement instanceof PsiMethod) || ((psiElement instanceof PsiClass) && PsiUtil.isLanguageLevel5OrHigher(psiElement));
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.javadoc.JavadocTagInfo
    public String checkTagValue(PsiDocTagValue psiDocTagValue) {
        if (psiDocTagValue == null) {
            return JavaPsiBundle.message("javadoc.param.tag.parameter.name.expected", new Object[0]);
        }
        ASTNode firstChildNode = psiDocTagValue.getNode().getFirstChildNode();
        if (firstChildNode == null || !firstChildNode.getElementType().equals(JavaDocTokenType.DOC_TAG_VALUE_LT)) {
            return null;
        }
        if (psiDocTagValue.getNode().findChildByType(JavaDocTokenType.DOC_TAG_VALUE_TOKEN) == null) {
            return JavaPsiBundle.message("javadoc.param.tag.type.parameter.name.expected", new Object[0]);
        }
        if (psiDocTagValue.getNode().findChildByType(JavaDocTokenType.DOC_TAG_VALUE_GT) == null) {
            return JavaPsiBundle.message("javadoc.param.tag.type.parameter.gt.expected", new Object[0]);
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.javadoc.JavadocTagInfo
    public PsiReference getReference(PsiDocTagValue psiDocTagValue) {
        if (psiDocTagValue instanceof PsiDocParamRef) {
            return psiDocTagValue.getReference();
        }
        return null;
    }
}
