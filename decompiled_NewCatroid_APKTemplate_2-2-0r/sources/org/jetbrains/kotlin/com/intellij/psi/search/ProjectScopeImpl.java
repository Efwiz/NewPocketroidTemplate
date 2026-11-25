package org.jetbrains.kotlin.com.intellij.psi.search;

import java.util.Collection;
import java.util.Objects;
import org.jetbrains.kotlin.com.intellij.openapi.module.Module;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.roots.FileIndexFacade;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;

/* JADX INFO: loaded from: classes3.dex */
public class ProjectScopeImpl extends GlobalSearchScope {
    private final FileIndexFacade myFileIndex;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 4:
            case 5:
            case 7:
            case 8:
            case 10:
            case 11:
            case 12:
                str = "@NotNull method %s.%s must not return null";
                break;
            case 6:
            case 9:
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 4:
            case 5:
            case 7:
            case 8:
            case 10:
            case 11:
            case 12:
                i2 = 2;
                break;
            case 6:
            case 9:
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
                objArr[0] = "fileIndex";
                break;
            case 2:
                objArr[0] = "file";
                break;
            case 3:
                objArr[0] = "aModule";
                break;
            case 4:
            case 5:
            case 7:
            case 8:
            case 10:
            case 11:
            case 12:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/search/ProjectScopeImpl";
                break;
            case 6:
            case 9:
                objArr[0] = "scope";
                break;
            default:
                objArr[0] = "project";
                break;
        }
        switch (i) {
            case 4:
                objArr[1] = "getDisplayName";
                break;
            case 5:
                objArr[1] = "getUnloadedModulesBelongingToScope";
                break;
            case 6:
            case 9:
            default:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/search/ProjectScopeImpl";
                break;
            case 7:
            case 8:
                objArr[1] = "uniteWith";
                break;
            case 10:
            case 11:
            case 12:
                objArr[1] = "intersectWith";
                break;
        }
        switch (i) {
            case 2:
                objArr[2] = "contains";
                break;
            case 3:
                objArr[2] = "isSearchInModuleContent";
                break;
            case 4:
            case 5:
            case 7:
            case 8:
            case 10:
            case 11:
            case 12:
                break;
            case 6:
                objArr[2] = "uniteWith";
                break;
            case 9:
                objArr[2] = "intersectWith";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
            case 4:
            case 5:
            case 7:
            case 8:
            case 10:
            case 11:
            case 12:
                throw new IllegalStateException(str2);
            case 6:
            case 9:
            default:
                throw new IllegalArgumentException(str2);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope
    public boolean isSearchInLibraries() {
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope
    public boolean isSearchInModuleContent(Module module) {
        if (module != null) {
            return true;
        }
        $$$reportNull$$$0(3);
        return true;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ProjectScopeImpl(Project project, FileIndexFacade fileIndexFacade) {
        super(project);
        if (project == null) {
            $$$reportNull$$$0(0);
        }
        if (fileIndexFacade == null) {
            $$$reportNull$$$0(1);
        }
        this.myFileIndex = fileIndexFacade;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.com.intellij.psi.search.SearchScope
    public boolean contains(VirtualFile virtualFile) {
        if (virtualFile == 0) {
            $$$reportNull$$$0(2);
        }
        if (virtualFile instanceof ProjectAwareVirtualFile) {
            return ((ProjectAwareVirtualFile) virtualFile).isInProject((Project) Objects.requireNonNull(getProject()));
        }
        return this.myFileIndex.isInProjectScope(virtualFile);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.search.SearchScope
    public String getDisplayName() {
        String projectFilesScopeName = ProjectScope.getProjectFilesScopeName();
        if (projectFilesScopeName == null) {
            $$$reportNull$$$0(4);
        }
        return projectFilesScopeName;
    }

    public String toString() {
        return getDisplayName();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope
    public Collection<Object> getUnloadedModulesBelongingToScope() {
        Collection<Object> unloadedModuleDescriptions = this.myFileIndex.getUnloadedModuleDescriptions();
        if (unloadedModuleDescriptions == null) {
            $$$reportNull$$$0(5);
        }
        return unloadedModuleDescriptions;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope
    public GlobalSearchScope uniteWith(GlobalSearchScope globalSearchScope) {
        if (globalSearchScope == null) {
            $$$reportNull$$$0(6);
        }
        if (globalSearchScope == this || !globalSearchScope.isSearchInLibraries()) {
            return this;
        }
        GlobalSearchScope globalSearchScopeUniteWith = super.uniteWith(globalSearchScope);
        if (globalSearchScopeUniteWith == null) {
            $$$reportNull$$$0(8);
        }
        return globalSearchScopeUniteWith;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope
    public GlobalSearchScope intersectWith(GlobalSearchScope globalSearchScope) {
        if (globalSearchScope == null) {
            $$$reportNull$$$0(9);
        }
        if (globalSearchScope == this) {
            return this;
        }
        if (!globalSearchScope.isSearchInLibraries()) {
            if (globalSearchScope == null) {
                $$$reportNull$$$0(11);
            }
            return globalSearchScope;
        }
        GlobalSearchScope globalSearchScopeIntersectWith = super.intersectWith(globalSearchScope);
        if (globalSearchScopeIntersectWith == null) {
            $$$reportNull$$$0(12);
        }
        return globalSearchScopeIntersectWith;
    }
}
