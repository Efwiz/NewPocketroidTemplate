package org.jetbrains.kotlin.com.intellij.psi.impl;

import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;

/* JADX INFO: loaded from: classes3.dex */
public abstract class JavaPsiFacadeEx extends JavaPsiFacade {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i != 1) {
            objArr[0] = "project";
        } else {
            objArr[0] = "qualifiedName";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/JavaPsiFacadeEx";
        if (i != 1) {
            objArr[2] = "getInstanceEx";
        } else {
            objArr[2] = "findClass";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public static JavaPsiFacadeEx getInstanceEx(Project project) {
        if (project == null) {
            $$$reportNull$$$0(0);
        }
        return (JavaPsiFacadeEx) getInstance(project);
    }

    public PsiClass findClass(String str) {
        if (str == null) {
            $$$reportNull$$$0(1);
        }
        return findClass(str, GlobalSearchScope.allScope(getProject()));
    }
}
