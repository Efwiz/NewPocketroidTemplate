package org.jetbrains.kotlin.com.intellij.psi.search;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.Collection;
import java.util.Collections;
import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.lang.LanguageMatcher;
import org.jetbrains.kotlin.com.intellij.lang.LanguageUtil;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.Condition;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.FileViewProvider;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.templateLanguages.TemplateLanguage;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;

/* JADX INFO: loaded from: classes3.dex */
class FileLanguageGlobalScope extends DelegatingGlobalSearchScope {
    private final Collection<? extends LanguageMatcher> myLanguageMatchers;
    private final Project myProject;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 6 || i == 7 || i == 9) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 6 || i == 7 || i == 9) ? 2 : 3];
        switch (i) {
            case 1:
                objArr[0] = "baseScope";
                break;
            case 2:
                objArr[0] = "languageMatchers";
                break;
            case 3:
            case 4:
                objArr[0] = "file";
                break;
            case 5:
            case 8:
                objArr[0] = "scope";
                break;
            case 6:
            case 7:
            case 9:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/search/FileLanguageGlobalScope";
                break;
            default:
                objArr[0] = "project";
                break;
        }
        if (i == 6 || i == 7) {
            objArr[1] = "intersectWith";
        } else if (i != 9) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/search/FileLanguageGlobalScope";
        } else {
            objArr[1] = "uniteWith";
        }
        switch (i) {
            case 3:
                objArr[2] = "contains";
                break;
            case 4:
                objArr[2] = "matchesLanguage";
                break;
            case 5:
                objArr[2] = "intersectWith";
                break;
            case 6:
            case 7:
            case 9:
                break;
            case 8:
                objArr[2] = "uniteWith";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 6 && i != 7 && i != 9) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    FileLanguageGlobalScope(Project project, GlobalSearchScope globalSearchScope, Collection<? extends LanguageMatcher> collection) {
        super(globalSearchScope);
        if (project == null) {
            $$$reportNull$$$0(0);
        }
        if (globalSearchScope == null) {
            $$$reportNull$$$0(1);
        }
        if (collection == null) {
            $$$reportNull$$$0(2);
        }
        this.myProject = project;
        this.myLanguageMatchers = collection;
    }

    FileLanguageGlobalScope(Project project, GlobalSearchScope globalSearchScope, LanguageMatcher languageMatcher) {
        this(project, globalSearchScope, Collections.singleton(languageMatcher));
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.search.DelegatingGlobalSearchScope, org.jetbrains.kotlin.com.intellij.psi.search.SearchScope
    public boolean contains(VirtualFile virtualFile) {
        if (virtualFile == null) {
            $$$reportNull$$$0(3);
        }
        return super.contains(virtualFile) && matchesLanguage(virtualFile);
    }

    private boolean matchesLanguage(VirtualFile virtualFile) {
        if (virtualFile == null) {
            $$$reportNull$$$0(4);
        }
        final Language languageForPsi = LanguageUtil.getLanguageForPsi(this.myProject, virtualFile);
        if (!(languageForPsi instanceof TemplateLanguage)) {
            if (languageForPsi != null) {
                return ContainerUtil.or(this.myLanguageMatchers, new Condition() { // from class: org.jetbrains.kotlin.com.intellij.psi.search.FileLanguageGlobalScope$$ExternalSyntheticLambda1
                    @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Condition
                    public final boolean value(Object obj) {
                        return ((LanguageMatcher) obj).matchesLanguage(languageForPsi);
                    }
                });
            }
            return false;
        }
        FileViewProvider fileViewProviderFindViewProvider = PsiManager.getInstance(this.myProject).findViewProvider(virtualFile);
        if (fileViewProviderFindViewProvider == null) {
            return false;
        }
        return ContainerUtil.or(fileViewProviderFindViewProvider.getLanguages(), new Condition() { // from class: org.jetbrains.kotlin.com.intellij.psi.search.FileLanguageGlobalScope$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Condition
            public final boolean value(Object obj) {
                return this.f$0.m6695x2459be86((Language) obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$matchesLanguage$1$org-jetbrains-kotlin-com-intellij-psi-search-FileLanguageGlobalScope, reason: not valid java name */
    /* synthetic */ boolean m6695x2459be86(final Language language) {
        return ContainerUtil.or(this.myLanguageMatchers, new Condition() { // from class: org.jetbrains.kotlin.com.intellij.psi.search.FileLanguageGlobalScope$$ExternalSyntheticLambda2
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Condition
            public final boolean value(Object obj) {
                return ((LanguageMatcher) obj).matchesLanguage(language);
            }
        });
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope
    public GlobalSearchScope intersectWith(GlobalSearchScope globalSearchScope) {
        if (globalSearchScope == null) {
            $$$reportNull$$$0(5);
        }
        if (globalSearchScope instanceof FileLanguageGlobalScope) {
            FileLanguageGlobalScope fileLanguageGlobalScope = (FileLanguageGlobalScope) globalSearchScope;
            if (this.myBaseScope == fileLanguageGlobalScope.myBaseScope) {
                Collection collectionIntersection = ContainerUtil.intersection(this.myLanguageMatchers, fileLanguageGlobalScope.myLanguageMatchers);
                if (collectionIntersection.isEmpty()) {
                    GlobalSearchScope globalSearchScope2 = GlobalSearchScope.EMPTY_SCOPE;
                    if (globalSearchScope2 == null) {
                        $$$reportNull$$$0(6);
                    }
                    return globalSearchScope2;
                }
                return new FileLanguageGlobalScope(this.myProject, this.myBaseScope, (Collection<? extends LanguageMatcher>) collectionIntersection);
            }
        }
        GlobalSearchScope globalSearchScopeIntersectWith = super.intersectWith(globalSearchScope);
        if (globalSearchScopeIntersectWith == null) {
            $$$reportNull$$$0(7);
        }
        return globalSearchScopeIntersectWith;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope
    public GlobalSearchScope uniteWith(GlobalSearchScope globalSearchScope) {
        if (globalSearchScope == null) {
            $$$reportNull$$$0(8);
        }
        if (globalSearchScope instanceof FileLanguageGlobalScope) {
            FileLanguageGlobalScope fileLanguageGlobalScope = (FileLanguageGlobalScope) globalSearchScope;
            if (this.myBaseScope == fileLanguageGlobalScope.myBaseScope) {
                return new FileLanguageGlobalScope(this.myProject, this.myBaseScope, ContainerUtil.union(this.myLanguageMatchers, fileLanguageGlobalScope.myLanguageMatchers));
            }
        }
        GlobalSearchScope globalSearchScopeUniteWith = super.uniteWith(globalSearchScope);
        if (globalSearchScopeUniteWith == null) {
            $$$reportNull$$$0(9);
        }
        return globalSearchScopeUniteWith;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.search.DelegatingGlobalSearchScope
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass() || !super.equals(obj)) {
            return false;
        }
        FileLanguageGlobalScope fileLanguageGlobalScope = (FileLanguageGlobalScope) obj;
        return this.myProject.equals(fileLanguageGlobalScope.myProject) && this.myLanguageMatchers.equals(fileLanguageGlobalScope.myLanguageMatchers);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.search.DelegatingGlobalSearchScope, org.jetbrains.kotlin.com.intellij.psi.search.SearchScope
    public int calcHashCode() {
        return (((super.calcHashCode() * 31) + this.myProject.hashCode()) * 31) + this.myLanguageMatchers.hashCode();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.search.DelegatingGlobalSearchScope
    public String toString() {
        return "(restricted by file languages: " + this.myLanguageMatchers + " in " + this.myBaseScope + Constant.AFTER_QUTO;
    }
}
