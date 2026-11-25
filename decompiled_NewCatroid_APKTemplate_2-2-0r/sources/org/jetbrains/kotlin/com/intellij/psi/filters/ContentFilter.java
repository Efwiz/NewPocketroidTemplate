package org.jetbrains.kotlin.com.intellij.psi.filters;

import com.huawei.hms.ml.language.common.utils.Constant;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.filters.position.PositionElementFilter;

/* JADX INFO: loaded from: classes3.dex */
public class ContentFilter extends PositionElementFilter {
    public ContentFilter(ElementFilter elementFilter) {
        setFilter(elementFilter);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.filters.ElementFilter
    public boolean isAcceptable(Object obj, PsiElement psiElement) {
        if (!(obj instanceof PsiElement)) {
            return false;
        }
        PsiElement psiElement2 = (PsiElement) obj;
        for (PsiElement firstChild = psiElement2.getFirstChild(); firstChild != null; firstChild = firstChild.getNextSibling()) {
            if (getFilter().isAcceptable(firstChild, psiElement2)) {
                return true;
            }
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.filters.ElementFilter
    public String toString() {
        return "content(" + getFilter().toString() + Constant.AFTER_QUTO;
    }
}
