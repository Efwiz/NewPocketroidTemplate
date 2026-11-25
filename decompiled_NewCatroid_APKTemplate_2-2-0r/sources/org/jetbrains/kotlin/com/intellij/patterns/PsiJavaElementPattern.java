package org.jetbrains.kotlin.com.intellij.patterns;

import org.jetbrains.kotlin.com.intellij.patterns.PsiJavaElementPattern;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;

/* JADX INFO: loaded from: classes3.dex */
public class PsiJavaElementPattern<T extends PsiElement, Self extends PsiJavaElementPattern<T, Self>> extends PsiElementPattern<T, Self> {
    public PsiJavaElementPattern(Class<T> cls) {
        super(cls);
    }

    public static class Capture<T extends PsiElement> extends PsiJavaElementPattern<T, Capture<T>> {
        public Capture(Class<T> cls) {
            super(cls);
        }
    }
}
