package org.jetbrains.kotlin.com.intellij.psi.search;

import java.util.LinkedHashSet;
import java.util.Set;
import org.jetbrains.kotlin.com.intellij.openapi.application.ReadAction;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.ThrowableComputable;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;

/* JADX INFO: loaded from: classes3.dex */
public final class GlobalSearchScopeUtil {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 3 || i == 5) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 3 || i == 5) ? 2 : 3];
        if (i == 1) {
            objArr[0] = "project";
        } else if (i == 2 || i == 3 || i == 5) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/search/GlobalSearchScopeUtil";
        } else {
            objArr[0] = "scope";
        }
        if (i == 2 || i == 3) {
            objArr[1] = "toGlobalSearchScope";
        } else if (i != 5) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/search/GlobalSearchScopeUtil";
        } else {
            objArr[1] = "getLocalScopeFiles";
        }
        if (i != 2 && i != 3) {
            if (i == 4) {
                objArr[2] = "getLocalScopeFiles";
            } else if (i != 5) {
                objArr[2] = "toGlobalSearchScope";
            }
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 3 && i != 5) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public static GlobalSearchScope toGlobalSearchScope(final SearchScope searchScope, final Project project) {
        if (searchScope == null) {
            $$$reportNull$$$0(0);
        }
        if (project == null) {
            $$$reportNull$$$0(1);
        }
        if (searchScope instanceof GlobalSearchScope) {
            GlobalSearchScope globalSearchScope = (GlobalSearchScope) searchScope;
            if (globalSearchScope == null) {
                $$$reportNull$$$0(2);
            }
            return globalSearchScope;
        }
        GlobalSearchScope globalSearchScope2 = (GlobalSearchScope) ReadAction.compute(new ThrowableComputable() { // from class: org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScopeUtil$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.ThrowableComputable
            public final Object compute() {
                return GlobalSearchScope.filesScope(project, GlobalSearchScopeUtil.getLocalScopeFiles((LocalSearchScope) searchScope));
            }
        });
        if (globalSearchScope2 == null) {
            $$$reportNull$$$0(3);
        }
        return globalSearchScope2;
    }

    public static Set<VirtualFile> getLocalScopeFiles(final LocalSearchScope localSearchScope) {
        if (localSearchScope == null) {
            $$$reportNull$$$0(4);
        }
        Set<VirtualFile> set = (Set) ReadAction.compute(new ThrowableComputable() { // from class: org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScopeUtil$$ExternalSyntheticLambda1
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.ThrowableComputable
            public final Object compute() {
                return GlobalSearchScopeUtil.lambda$getLocalScopeFiles$1(localSearchScope);
            }
        });
        if (set == null) {
            $$$reportNull$$$0(5);
        }
        return set;
    }

    static /* synthetic */ Set lambda$getLocalScopeFiles$1(LocalSearchScope localSearchScope) throws RuntimeException {
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        for (PsiElement psiElement : localSearchScope.getScope()) {
            PsiFile containingFile = psiElement.getContainingFile();
            if (containingFile != null) {
                ContainerUtil.addIfNotNull(linkedHashSet, containingFile.getVirtualFile());
                ContainerUtil.addIfNotNull(linkedHashSet, containingFile.getNavigationElement().getContainingFile().getVirtualFile());
            }
        }
        return linkedHashSet;
    }
}
