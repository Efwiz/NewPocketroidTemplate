package org.jetbrains.kotlin.com.intellij.psi;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiAnnotationMethod extends PsiMethod {
    public static final PsiAnnotationMethod[] EMPTY_ARRAY = new PsiAnnotationMethod[0];

    PsiAnnotationMemberValue getDefaultValue();
}
