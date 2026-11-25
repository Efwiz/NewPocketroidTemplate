package org.jetbrains.kotlin.com.intellij.psi.filters;

import com.huawei.hms.ml.language.common.utils.Constant;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.util.ReflectionUtil;

/* JADX INFO: loaded from: classes3.dex */
public class ClassFilter implements ElementFilter {
    private final boolean myAcceptableFlag;
    private final Class myFilter;

    public ClassFilter(Class cls) {
        this(cls, true);
    }

    public ClassFilter(Class cls, boolean z) {
        this.myFilter = cls;
        this.myAcceptableFlag = z;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.filters.ElementFilter
    public boolean isClassAcceptable(Class cls) {
        return this.myAcceptableFlag == filterMatches(cls);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.filters.ElementFilter
    public boolean isAcceptable(Object obj, PsiElement psiElement) {
        return obj != null && this.myAcceptableFlag == filterMatches(obj.getClass());
    }

    private boolean filterMatches(Class cls) {
        return ReflectionUtil.isAssignable(this.myFilter, cls);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.filters.ElementFilter
    public String toString() {
        return "class(" + this.myFilter.getName() + Constant.AFTER_QUTO;
    }
}
