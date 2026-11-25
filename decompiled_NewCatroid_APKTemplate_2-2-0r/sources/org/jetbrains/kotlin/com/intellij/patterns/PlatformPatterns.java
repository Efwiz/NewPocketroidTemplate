package org.jetbrains.kotlin.com.intellij.patterns;

import org.jetbrains.kotlin.com.intellij.psi.PsiElement;

/* JADX INFO: loaded from: classes3.dex */
public class PlatformPatterns extends StandardPatterns {
    public static PsiElementPattern.Capture<PsiElement> psiElement() {
        return new PsiElementPattern.Capture<>(PsiElement.class);
    }

    public static <T extends PsiElement> PsiElementPattern.Capture<T> psiElement(Class<T> cls) {
        return new PsiElementPattern.Capture<>(cls);
    }

    public static IElementTypePattern elementType() {
        return new IElementTypePattern();
    }
}
