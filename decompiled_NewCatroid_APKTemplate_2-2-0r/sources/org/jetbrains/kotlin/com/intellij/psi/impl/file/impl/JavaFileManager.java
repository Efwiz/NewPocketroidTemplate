package org.jetbrains.kotlin.com.intellij.psi.impl.file.impl;

import java.util.Collection;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaModule;
import org.jetbrains.kotlin.com.intellij.psi.PsiPackage;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;

/* JADX INFO: loaded from: classes3.dex */
public interface JavaFileManager {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "project", "org/jetbrains/kotlin/com/intellij/psi/impl/file/impl/JavaFileManager", "getInstance"));
    }

    PsiClass findClass(String str, GlobalSearchScope globalSearchScope);

    PsiClass[] findClasses(String str, GlobalSearchScope globalSearchScope);

    Collection<PsiJavaModule> findModules(String str, GlobalSearchScope globalSearchScope);

    PsiPackage findPackage(String str);

    Collection<String> getNonTrivialPackagePrefixes();

    static JavaFileManager getInstance(Project project) {
        if (project == null) {
            $$$reportNull$$$0(0);
        }
        return (JavaFileManager) project.getService(JavaFileManager.class);
    }
}
