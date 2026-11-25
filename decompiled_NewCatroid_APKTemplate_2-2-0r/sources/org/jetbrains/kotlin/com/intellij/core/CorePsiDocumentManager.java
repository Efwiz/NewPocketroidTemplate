package org.jetbrains.kotlin.com.intellij.core;

import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiDocumentManagerBase;

/* JADX INFO: loaded from: classes3.dex */
final class CorePsiDocumentManager extends PsiDocumentManagerBase {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "project", "org/jetbrains/kotlin/com/intellij/core/CorePsiDocumentManager", "<init>"));
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    CorePsiDocumentManager(Project project) {
        super(project);
        if (project == null) {
            $$$reportNull$$$0(0);
        }
    }
}
