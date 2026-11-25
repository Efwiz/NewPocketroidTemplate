package org.jetbrains.kotlin.com.intellij.psi;

import org.jetbrains.kotlin.com.intellij.openapi.project.Project;

/* JADX INFO: loaded from: classes3.dex */
public abstract class SmartTypePointerManager {
    public abstract SmartTypePointer createSmartTypePointer(PsiType psiType);

    public static SmartTypePointerManager getInstance(Project project) {
        return (SmartTypePointerManager) project.getService(SmartTypePointerManager.class);
    }
}
