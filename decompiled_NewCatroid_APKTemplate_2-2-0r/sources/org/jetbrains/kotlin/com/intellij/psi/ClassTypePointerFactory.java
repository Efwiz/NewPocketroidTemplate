package org.jetbrains.kotlin.com.intellij.psi;

import org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionPointName;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;

/* JADX INFO: loaded from: classes3.dex */
public interface ClassTypePointerFactory {
    public static final ExtensionPointName<ClassTypePointerFactory> EP_NAME = ExtensionPointName.create("org.jetbrains.kotlin.com.intellij.classTypePointerFactory");

    SmartTypePointer createClassTypePointer(PsiClassType psiClassType, Project project);
}
