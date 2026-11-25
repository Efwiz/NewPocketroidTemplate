package org.jetbrains.kotlin.com.intellij.psi.impl;

import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;

/* JADX INFO: loaded from: classes3.dex */
public abstract class ResolveScopeManager {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 3 || i == 5 || i == 6) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 3 || i == 5 || i == 6) ? 2 : 3];
        switch (i) {
            case 1:
            case 3:
            case 5:
            case 6:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/ResolveScopeManager";
                break;
            case 2:
            case 4:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
            default:
                objArr[0] = "project";
                break;
        }
        if (i == 1) {
            objArr[1] = "getInstance";
        } else if (i == 3) {
            objArr[1] = "getElementUseScope";
        } else if (i == 5 || i == 6) {
            objArr[1] = "getElementResolveScope";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/ResolveScopeManager";
        }
        switch (i) {
            case 1:
            case 3:
            case 5:
            case 6:
                break;
            case 2:
                objArr[2] = "getElementUseScope";
                break;
            case 4:
                objArr[2] = "getElementResolveScope";
                break;
            default:
                objArr[2] = "getInstance";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 3 && i != 5 && i != 6) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public abstract GlobalSearchScope getDefaultResolveScope(VirtualFile virtualFile);

    public abstract GlobalSearchScope getResolveScope(PsiElement psiElement);

    public abstract GlobalSearchScope getUseScope(PsiElement psiElement);

    public static ResolveScopeManager getInstance(Project project) {
        if (project == null) {
            $$$reportNull$$$0(0);
        }
        ResolveScopeManager resolveScopeManager = (ResolveScopeManager) project.getService(ResolveScopeManager.class);
        if (resolveScopeManager == null) {
            $$$reportNull$$$0(1);
        }
        return resolveScopeManager;
    }

    public static GlobalSearchScope getElementUseScope(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(2);
        }
        GlobalSearchScope useScope = getInstance(psiElement.getProject()).getUseScope(psiElement);
        if (useScope == null) {
            $$$reportNull$$$0(3);
        }
        return useScope;
    }

    public static GlobalSearchScope getElementResolveScope(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(4);
        }
        PsiFile containingFile = psiElement.getContainingFile();
        if (containingFile != null) {
            GlobalSearchScope resolveScope = getInstance(containingFile.getProject()).getResolveScope(containingFile);
            if (resolveScope == null) {
                $$$reportNull$$$0(5);
            }
            return resolveScope;
        }
        GlobalSearchScope resolveScope2 = getInstance(psiElement.getProject()).getResolveScope(psiElement);
        if (resolveScope2 == null) {
            $$$reportNull$$$0(6);
        }
        return resolveScope2;
    }
}
