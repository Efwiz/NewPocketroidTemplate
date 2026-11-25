package org.jetbrains.kotlin.com.intellij.core;

import java.util.Collection;
import org.jetbrains.kotlin.com.intellij.openapi.module.Module;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.roots.FileIndexFacade;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.search.EverythingGlobalScope;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.kotlin.com.intellij.psi.search.ProjectScopeBuilder;
import org.jetbrains.kotlin.com.intellij.psi.search.ProjectScopeImpl;

/* JADX INFO: loaded from: classes3.dex */
public class CoreProjectScopeBuilder extends ProjectScopeBuilder {
    private final FileIndexFacade myFileIndexFacade;
    private final CoreLibrariesScope myLibrariesScope = new CoreLibrariesScope();
    private final Project myProject;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/com/intellij/core/CoreProjectScopeBuilder", "buildLibrariesScope"));
    }

    public CoreProjectScopeBuilder(Project project, FileIndexFacade fileIndexFacade) {
        this.myFileIndexFacade = fileIndexFacade;
        this.myProject = project;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.search.ProjectScopeBuilder
    public GlobalSearchScope buildLibrariesScope() {
        CoreLibrariesScope coreLibrariesScope = this.myLibrariesScope;
        if (coreLibrariesScope == null) {
            $$$reportNull$$$0(0);
        }
        return coreLibrariesScope;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.search.ProjectScopeBuilder
    public GlobalSearchScope buildAllScope() {
        return new EverythingGlobalScope();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.search.ProjectScopeBuilder
    public GlobalSearchScope buildProjectScope() {
        return new ProjectScopeImpl(this.myProject, this.myFileIndexFacade);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.search.ProjectScopeBuilder
    public GlobalSearchScope buildContentScope() {
        return new ContentSearchScope(this.myProject, this.myFileIndexFacade);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.search.ProjectScopeBuilder
    public GlobalSearchScope buildEverythingScope() {
        return new EverythingGlobalScope(this.myProject);
    }

    private class CoreLibrariesScope extends GlobalSearchScope {
        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            if (i != 1) {
                objArr[0] = "file";
            } else {
                objArr[0] = "aModule";
            }
            objArr[1] = "org/jetbrains/kotlin/com/intellij/core/CoreProjectScopeBuilder$CoreLibrariesScope";
            if (i != 1) {
                objArr[2] = "contains";
            } else {
                objArr[2] = "isSearchInModuleContent";
            }
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope
        public boolean isSearchInLibraries() {
            return true;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope
        public boolean isSearchInModuleContent(Module module) {
            if (module != null) {
                return false;
            }
            $$$reportNull$$$0(1);
            return false;
        }

        private CoreLibrariesScope() {
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.search.SearchScope
        public boolean contains(VirtualFile virtualFile) {
            if (virtualFile == null) {
                $$$reportNull$$$0(0);
            }
            return CoreProjectScopeBuilder.this.myFileIndexFacade.isInLibraryClasses(virtualFile) || CoreProjectScopeBuilder.this.myFileIndexFacade.isInLibrarySource(virtualFile);
        }
    }

    public static class ContentSearchScope extends GlobalSearchScope {
        private final FileIndexFacade myFileIndexFacade;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = i != 2 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
            Object[] objArr = new Object[i != 2 ? 3 : 2];
            if (i == 1) {
                objArr[0] = "aModule";
            } else if (i != 2) {
                objArr[0] = "file";
            } else {
                objArr[0] = "org/jetbrains/kotlin/com/intellij/core/CoreProjectScopeBuilder$ContentSearchScope";
            }
            if (i != 2) {
                objArr[1] = "org/jetbrains/kotlin/com/intellij/core/CoreProjectScopeBuilder$ContentSearchScope";
            } else {
                objArr[1] = "getUnloadedModulesBelongingToScope";
            }
            if (i == 1) {
                objArr[2] = "isSearchInModuleContent";
            } else if (i != 2) {
                objArr[2] = "contains";
            }
            String str2 = String.format(str, objArr);
            if (i == 2) {
                throw new IllegalStateException(str2);
            }
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope
        public boolean isSearchInLibraries() {
            return false;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope
        public boolean isSearchInModuleContent(Module module) {
            if (module == null) {
                $$$reportNull$$$0(1);
            }
            return true;
        }

        public ContentSearchScope(Project project, FileIndexFacade fileIndexFacade) {
            super(project);
            this.myFileIndexFacade = fileIndexFacade;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.search.SearchScope
        public boolean contains(VirtualFile virtualFile) {
            if (virtualFile == null) {
                $$$reportNull$$$0(0);
            }
            return this.myFileIndexFacade.isInContent(virtualFile);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope
        public Collection<Object> getUnloadedModulesBelongingToScope() {
            Collection<Object> unloadedModuleDescriptions = this.myFileIndexFacade.getUnloadedModuleDescriptions();
            if (unloadedModuleDescriptions == null) {
                $$$reportNull$$$0(2);
            }
            return unloadedModuleDescriptions;
        }
    }
}
