package org.jetbrains.kotlin.com.intellij.psi.impl;

import org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionPointName;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.ProjectExtensionPointName;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiTreeChangePreprocessor {
    public static final ProjectExtensionPointName<PsiTreeChangePreprocessor> EP = new ProjectExtensionPointName<>("org.jetbrains.kotlin.com.intellij.psi.treeChangePreprocessor");

    @Deprecated
    public static final ExtensionPointName<PsiTreeChangePreprocessor> EP_NAME = ExtensionPointName.create("org.jetbrains.kotlin.com.intellij.psi.treeChangePreprocessor");

    void treeChanged(PsiTreeChangeEventImpl psiTreeChangeEventImpl);
}
