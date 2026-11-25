package org.jetbrains.kotlin.com.intellij.ide;

import javax.swing.Icon;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionPointName;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;

/* JADX INFO: loaded from: classes3.dex */
public abstract class IconProvider {
    public static final ExtensionPointName<IconProvider> EXTENSION_POINT_NAME = new ExtensionPointName<>("org.jetbrains.kotlin.com.intellij.iconProvider");

    public abstract Icon getIcon(PsiElement psiElement, int i);
}
