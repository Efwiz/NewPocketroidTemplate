package org.jetbrains.kotlin.com.intellij.mock;

import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.ResolveScopeManager;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;

/* JADX INFO: loaded from: classes3.dex */
public class MockResolveScopeManager extends ResolveScopeManager {
    private final Project myProject;

    /* JADX WARN: Code duplicated, block: B:21:0x002f  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 3 || i == 5) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 3 || i == 5) ? 2 : 3];
        if (i == 1) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/mock/MockResolveScopeManager";
        } else if (i == 2) {
            objArr[0] = "vFile";
        } else if (i == 3 || i == 5) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/mock/MockResolveScopeManager";
        } else {
            objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
        }
        if (i == 1) {
            objArr[1] = "getResolveScope";
        } else if (i == 3) {
            objArr[1] = "getDefaultResolveScope";
        } else if (i != 5) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/mock/MockResolveScopeManager";
        } else {
            objArr[1] = "getUseScope";
        }
        if (i != 1) {
            if (i == 2) {
                objArr[2] = "getDefaultResolveScope";
            } else if (i != 3) {
                if (i == 4) {
                    objArr[2] = "getUseScope";
                } else if (i != 5) {
                    objArr[2] = "getResolveScope";
                }
            }
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 3 && i != 5) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public MockResolveScopeManager(Project project) {
        this.myProject = project;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.ResolveScopeManager
    public GlobalSearchScope getResolveScope(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(0);
        }
        GlobalSearchScope globalSearchScopeAllScope = GlobalSearchScope.allScope(psiElement.getProject());
        if (globalSearchScopeAllScope == null) {
            $$$reportNull$$$0(1);
        }
        return globalSearchScopeAllScope;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.ResolveScopeManager
    public GlobalSearchScope getDefaultResolveScope(VirtualFile virtualFile) {
        if (virtualFile == null) {
            $$$reportNull$$$0(2);
        }
        GlobalSearchScope globalSearchScopeAllScope = GlobalSearchScope.allScope(this.myProject);
        if (globalSearchScopeAllScope == null) {
            $$$reportNull$$$0(3);
        }
        return globalSearchScopeAllScope;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.ResolveScopeManager
    public GlobalSearchScope getUseScope(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(4);
        }
        GlobalSearchScope globalSearchScopeAllScope = GlobalSearchScope.allScope(psiElement.getProject());
        if (globalSearchScopeAllScope == null) {
            $$$reportNull$$$0(5);
        }
        return globalSearchScopeAllScope;
    }
}
