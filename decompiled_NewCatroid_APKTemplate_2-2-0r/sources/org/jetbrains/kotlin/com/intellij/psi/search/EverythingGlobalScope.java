package org.jetbrains.kotlin.com.intellij.psi.search;

import java.util.Collection;
import java.util.Collections;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.com.intellij.core.CoreBundle;
import org.jetbrains.kotlin.com.intellij.openapi.module.Module;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.roots.FileIndexFacade;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;

/* JADX INFO: loaded from: classes3.dex */
public class EverythingGlobalScope extends GlobalSearchScope {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 2 || i == 4 || i == 6) ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[(i == 1 || i == 2 || i == 4 || i == 6) ? 3 : 2];
        if (i == 1) {
            objArr[0] = "file";
        } else if (i == 2) {
            objArr[0] = "aModule";
        } else if (i == 4) {
            objArr[0] = "scope";
        } else if (i != 6) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/search/EverythingGlobalScope";
        } else {
            objArr[0] = "scope2";
        }
        switch (i) {
            case 1:
            case 2:
            case 4:
            case 6:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/search/EverythingGlobalScope";
                break;
            case 3:
                objArr[1] = "getUnloadedModulesBelongingToScope";
                break;
            case 5:
                objArr[1] = SchemaSymbols.ATTVAL_UNION;
                break;
            case 7:
                objArr[1] = "intersectWith";
                break;
            default:
                objArr[1] = "getNameText";
                break;
        }
        if (i == 1) {
            objArr[2] = "contains";
        } else if (i == 2) {
            objArr[2] = "isSearchInModuleContent";
        } else if (i == 4) {
            objArr[2] = SchemaSymbols.ATTVAL_UNION;
        } else if (i == 6) {
            objArr[2] = "intersectWith";
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 2 && i != 4 && i != 6) {
            throw new IllegalStateException(str2);
        }
        throw new IllegalArgumentException(str2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.search.SearchScope
    public boolean contains(VirtualFile virtualFile) {
        if (virtualFile == null) {
            $$$reportNull$$$0(1);
        }
        return true;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope
    public boolean isForceSearchingInLibrarySources() {
        return true;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope
    public boolean isSearchInLibraries() {
        return true;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope
    public boolean isSearchInModuleContent(Module module) {
        if (module != null) {
            return true;
        }
        $$$reportNull$$$0(2);
        return true;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope, org.jetbrains.kotlin.com.intellij.psi.search.SearchScope
    public GlobalSearchScope union(SearchScope searchScope) {
        if (searchScope == null) {
            $$$reportNull$$$0(4);
        }
        return this;
    }

    @Deprecated
    public EverythingGlobalScope(Project project) {
        super(project);
    }

    public EverythingGlobalScope() {
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.search.SearchScope
    public String getDisplayName() {
        return getNameText();
    }

    public static String getNameText() {
        String strMessage = CoreBundle.message("scope.name.all.places", new Object[0]);
        if (strMessage == null) {
            $$$reportNull$$$0(0);
        }
        return strMessage;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope
    public Collection<Object> getUnloadedModulesBelongingToScope() {
        Project project = getProject();
        Collection<Object> unloadedModuleDescriptions = project != null ? FileIndexFacade.getInstance(project).getUnloadedModuleDescriptions() : Collections.emptySet();
        if (unloadedModuleDescriptions == null) {
            $$$reportNull$$$0(3);
        }
        return unloadedModuleDescriptions;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope, org.jetbrains.kotlin.com.intellij.psi.search.SearchScope
    public SearchScope intersectWith(SearchScope searchScope) {
        if (searchScope == null) {
            $$$reportNull$$$0(6);
        }
        if (searchScope == null) {
            $$$reportNull$$$0(7);
        }
        return searchScope;
    }
}
