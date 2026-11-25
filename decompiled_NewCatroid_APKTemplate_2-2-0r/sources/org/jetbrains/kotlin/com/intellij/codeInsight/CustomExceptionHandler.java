package org.jetbrains.kotlin.com.intellij.codeInsight;

import org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionPointName;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassType;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;

/* JADX INFO: loaded from: classes3.dex */
public abstract class CustomExceptionHandler {
    public static final ExtensionPointName<CustomExceptionHandler> KEY = ExtensionPointName.create("org.jetbrains.kotlin.com.intellij.custom.exception.handler");

    public abstract boolean isHandled(PsiElement psiElement, PsiClassType psiClassType, PsiElement psiElement2);
}
