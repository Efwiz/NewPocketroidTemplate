package org.jetbrains.kotlin.com.intellij.patterns;

import org.jetbrains.kotlin.com.intellij.psi.PsiElement;

/* JADX INFO: loaded from: classes3.dex */
public class PsiJavaPatterns extends StandardPatterns {
    public static PsiJavaElementPattern.Capture<PsiElement> psiElement() {
        return new PsiJavaElementPattern.Capture<>(PsiElement.class);
    }

    public static <T extends PsiElement> PsiJavaElementPattern.Capture<T> psiElement(Class<T> cls) {
        return new PsiJavaElementPattern.Capture<>(cls);
    }
}
