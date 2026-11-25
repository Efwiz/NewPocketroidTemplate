package org.jetbrains.kotlin.com.intellij.psi.javadoc;

import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;

/* JADX INFO: loaded from: classes3.dex */
public interface JavadocManager {
    JavadocTagInfo getTagInfo(String str);

    JavadocTagInfo[] getTagInfos(PsiElement psiElement);

    public static final class SERVICE {
        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "project", "org/jetbrains/kotlin/com/intellij/psi/javadoc/JavadocManager$SERVICE", "getInstance"));
        }

        private SERVICE() {
        }

        public static JavadocManager getInstance(Project project) {
            if (project == null) {
                $$$reportNull$$$0(0);
            }
            return (JavadocManager) project.getService(JavadocManager.class);
        }
    }
}
