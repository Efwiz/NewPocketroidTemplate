package org.jetbrains.kotlin.com.intellij.psi.search;

import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.lang.LanguageMatcher;
import org.jetbrains.kotlin.com.intellij.notebook.editor.BackedVirtualFile;
import org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileType;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;

/* JADX INFO: loaded from: classes3.dex */
public final class PsiSearchScopeUtil {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 8 || i == 9 || i == 13) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 8 || i == 9 || i == 13) ? 2 : 3];
        switch (i) {
            case 1:
            case 3:
            case 5:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
            case 2:
                objArr[0] = "globalScope";
                break;
            case 4:
                objArr[0] = "local";
                break;
            case 6:
            case 11:
                objArr[0] = "originalScope";
                break;
            case 7:
                objArr[0] = "fileTypes";
                break;
            case 8:
            case 9:
            case 13:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/search/PsiSearchScopeUtil";
                break;
            case 10:
                objArr[0] = "project";
                break;
            case 12:
                objArr[0] = "matcher";
                break;
            default:
                objArr[0] = "scope";
                break;
        }
        if (i == 8 || i == 9) {
            objArr[1] = "restrictScopeTo";
        } else if (i != 13) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/search/PsiSearchScopeUtil";
        } else {
            objArr[1] = "restrictScopeToFileLanguage";
        }
        switch (i) {
            case 6:
            case 7:
                objArr[2] = "restrictScopeTo";
                break;
            case 8:
            case 9:
            case 13:
                break;
            case 10:
            case 11:
            case 12:
                objArr[2] = "restrictScopeToFileLanguage";
                break;
            default:
                objArr[2] = "isInScope";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 8 && i != 9 && i != 13) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public static SearchScope union(SearchScope searchScope, SearchScope searchScope2) {
        if (searchScope == null) {
            return searchScope2;
        }
        return searchScope2 == null ? searchScope : searchScope.union(searchScope2);
    }

    public static boolean isInScope(SearchScope searchScope, PsiElement psiElement) {
        if (searchScope == null) {
            $$$reportNull$$$0(0);
        }
        if (psiElement == null) {
            $$$reportNull$$$0(1);
        }
        if (searchScope instanceof LocalSearchScope) {
            return isInScope((LocalSearchScope) searchScope, psiElement);
        }
        return isInScope((GlobalSearchScope) searchScope, psiElement);
    }

    public static boolean isInScope(GlobalSearchScope globalSearchScope, PsiElement psiElement) {
        if (globalSearchScope == null) {
            $$$reportNull$$$0(2);
        }
        if (psiElement == null) {
            $$$reportNull$$$0(3);
        }
        PsiFile containingFile = psiElement.getContainingFile();
        if (containingFile == null) {
            return true;
        }
        while (containingFile != null) {
            if (globalSearchScope.contains(BackedVirtualFile.getOriginFileIfBacked(containingFile.getOriginalFile().getViewProvider().getVirtualFile()))) {
                return true;
            }
            PsiElement context = containingFile.getContext();
            containingFile = context == null ? null : context.getContainingFile();
        }
        return false;
    }

    public static boolean isInScope(LocalSearchScope localSearchScope, PsiElement psiElement) {
        if (localSearchScope == null) {
            $$$reportNull$$$0(4);
        }
        if (psiElement == null) {
            $$$reportNull$$$0(5);
        }
        for (PsiElement psiElement2 : localSearchScope.getScope()) {
            if (PsiTreeUtil.isAncestor(psiElement2, psiElement, false)) {
                return true;
            }
        }
        return false;
    }

    public static SearchScope restrictScopeTo(SearchScope searchScope, FileType... fileTypeArr) {
        if (searchScope == null) {
            $$$reportNull$$$0(6);
        }
        if (fileTypeArr == null) {
            $$$reportNull$$$0(7);
        }
        if (searchScope instanceof GlobalSearchScope) {
            GlobalSearchScope scopeRestrictedByFileTypes = GlobalSearchScope.getScopeRestrictedByFileTypes((GlobalSearchScope) searchScope, fileTypeArr);
            if (scopeRestrictedByFileTypes == null) {
                $$$reportNull$$$0(8);
            }
            return scopeRestrictedByFileTypes;
        }
        LocalSearchScope scopeRestrictedByFileTypes2 = LocalSearchScope.getScopeRestrictedByFileTypes((LocalSearchScope) searchScope, fileTypeArr);
        if (scopeRestrictedByFileTypes2 == null) {
            $$$reportNull$$$0(9);
        }
        return scopeRestrictedByFileTypes2;
    }

    public static SearchScope restrictScopeToFileLanguage(Project project, SearchScope searchScope, LanguageMatcher languageMatcher) {
        if (project == null) {
            $$$reportNull$$$0(10);
        }
        if (searchScope == null) {
            $$$reportNull$$$0(11);
        }
        if (languageMatcher == null) {
            $$$reportNull$$$0(12);
        }
        if (searchScope instanceof GlobalSearchScope) {
            return new FileLanguageGlobalScope(project, (GlobalSearchScope) searchScope, languageMatcher);
        }
        LocalSearchScope scopeRestrictedByFileLanguage = LocalSearchScope.getScopeRestrictedByFileLanguage((LocalSearchScope) searchScope, languageMatcher);
        if (scopeRestrictedByFileLanguage == null) {
            $$$reportNull$$$0(13);
        }
        return scopeRestrictedByFileLanguage;
    }
}
