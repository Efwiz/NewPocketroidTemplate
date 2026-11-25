package org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers;

import org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionPointName;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;

/* JADX INFO: loaded from: classes3.dex */
public abstract class SmartPointerAnchorProvider {
    public static final ExtensionPointName<SmartPointerAnchorProvider> EP_NAME = ExtensionPointName.create("org.jetbrains.kotlin.com.intellij.smartPointer.anchorProvider");

    public abstract PsiElement getAnchor(PsiElement psiElement);

    public abstract PsiElement restoreElement(PsiElement psiElement);
}
