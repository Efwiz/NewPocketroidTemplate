package org.jetbrains.kotlin.com.intellij.codeInsight;

import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner;

/* JADX INFO: loaded from: classes3.dex */
public abstract class InferredAnnotationsManager {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "project", "org/jetbrains/kotlin/com/intellij/codeInsight/InferredAnnotationsManager", "getInstance"));
    }

    public abstract PsiAnnotation findInferredAnnotation(PsiModifierListOwner psiModifierListOwner, String str);

    public abstract PsiAnnotation[] findInferredAnnotations(PsiModifierListOwner psiModifierListOwner);

    public abstract boolean isInferredAnnotation(PsiAnnotation psiAnnotation);

    public static InferredAnnotationsManager getInstance(Project project) {
        if (project == null) {
            $$$reportNull$$$0(0);
        }
        return (InferredAnnotationsManager) project.getService(InferredAnnotationsManager.class);
    }
}
