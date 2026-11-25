package org.jetbrains.kotlin.com.intellij.psi;

import org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionPointName;

/* JADX INFO: loaded from: classes3.dex */
public interface ElementDescriptionProvider {
    public static final ExtensionPointName<ElementDescriptionProvider> EP_NAME = ExtensionPointName.create("org.jetbrains.kotlin.com.intellij.elementDescriptionProvider");

    String getElementDescription(PsiElement psiElement, ElementDescriptionLocation elementDescriptionLocation);
}
