package org.jetbrains.kotlin.com.intellij.openapi.project;

import org.jetbrains.kotlin.com.intellij.openapi.util.Disposer;

/* JADX INFO: loaded from: classes3.dex */
public final class ProjectCoreUtil {
    private static volatile Project theOnlyProject;

    @Deprecated
    public static Project theOnlyOpenProject() {
        if (Disposer.isDebugMode()) {
            return null;
        }
        return theOnlyProject;
    }
}
