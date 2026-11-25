package org.jetbrains.kotlin.com.intellij.psi.search;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.com.intellij.core.CoreBundle;
import org.jetbrains.kotlin.com.intellij.model.ModelBranch;
import org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileType;
import org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileTypeRegistry;
import org.jetbrains.kotlin.com.intellij.openapi.module.Module;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.roots.FileIndexFacade;
import org.jetbrains.kotlin.com.intellij.openapi.util.Comparing;
import org.jetbrains.kotlin.com.intellij.openapi.util.Condition;
import org.jetbrains.kotlin.com.intellij.openapi.util.ModificationTracker;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.CompactVirtualFileSet;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VfsUtilCore;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileSet;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileWithId;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.search.impl.IntersectionFileEnumeration;
import org.jetbrains.kotlin.com.intellij.psi.search.impl.UnionFileEnumeration;
import org.jetbrains.kotlin.com.intellij.psi.search.impl.VirtualFileEnumeration;
import org.jetbrains.kotlin.com.intellij.psi.search.impl.VirtualFileEnumerationAware;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtil;
import org.jetbrains.kotlin.com.intellij.util.ObjectUtils;
import org.jetbrains.kotlin.com.intellij.util.Processor;
import org.jetbrains.kotlin.com.intellij.util.SmartList;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;

/* JADX INFO: loaded from: classes3.dex */
public abstract class GlobalSearchScope extends SearchScope implements ProjectAwareFileFilter {
    public static final GlobalSearchScope[] EMPTY_ARRAY = new GlobalSearchScope[0];
    public static final GlobalSearchScope EMPTY_SCOPE = new EmptyScope();
    private final Project myProject;

    /* JADX WARN: Code duplicated, block: B:17:0x0035  */
    /* JADX WARN: Code duplicated, block: B:80:0x0111  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 4 && i != 5 && i != 7 && i != 8 && i != 10 && i != 11 && i != 14 && i != 15 && i != 21 && i != 22 && i != 24 && i != 25) {
            switch (i) {
                case 17:
                case 19:
                case 27:
                case 29:
                case 31:
                case 34:
                case 36:
                case 38:
                case 40:
                case 43:
                case 45:
                case 47:
                case 57:
                case 60:
                case 63:
                case 66:
                    str = "@NotNull method %s.%s must not return null";
                    break;
                default:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
            }
        } else {
            str = "@NotNull method %s.%s must not return null";
        }
        if (i != 4 && i != 5 && i != 7 && i != 8 && i != 10 && i != 11 && i != 14 && i != 15 && i != 21 && i != 22 && i != 24 && i != 25) {
            switch (i) {
                case 17:
                case 19:
                case 27:
                case 29:
                case 31:
                case 34:
                case 36:
                case 38:
                case 40:
                case 43:
                case 45:
                case 47:
                case 57:
                case 60:
                case 63:
                case 66:
                    i2 = 2;
                    break;
                default:
                    i2 = 3;
                    break;
            }
        } else {
            i2 = 2;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
                objArr[0] = "file2";
                break;
            case 2:
                objArr[0] = "aModule";
                break;
            case 3:
                objArr[0] = "file";
                break;
            case 4:
            case 5:
            case 7:
            case 8:
            case 10:
            case 11:
            case 14:
            case 15:
            case 17:
            case 19:
            case 21:
            case 22:
            case 24:
            case 25:
            case 27:
            case 29:
            case 31:
            case 34:
            case 36:
            case 38:
            case 40:
            case 43:
            case 45:
            case 47:
            case 57:
            case 60:
            case 63:
            case 66:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/search/GlobalSearchScope";
                break;
            case 6:
            case 13:
            case 16:
            case 18:
            case 32:
            case 64:
                objArr[0] = "scope";
                break;
            case 9:
                objArr[0] = "scope2";
                break;
            case 12:
                objArr[0] = "localScope2";
                break;
            case 20:
            case 23:
                objArr[0] = "scopes";
                break;
            case 26:
            case 28:
            case 30:
            case 49:
            case 50:
            case 51:
            case 53:
            case 55:
            case 58:
            case 61:
                objArr[0] = "project";
                break;
            case 33:
            case 35:
            case 37:
            case 39:
            case 41:
            case 42:
            case 44:
            case 46:
                objArr[0] = "module";
                break;
            case 48:
                objArr[0] = "psiFile";
                break;
            case 52:
            case 54:
            case 56:
            case 59:
            case 62:
                objArr[0] = "files";
                break;
            case 65:
                objArr[0] = "fileTypes";
                break;
            default:
                objArr[0] = "file1";
                break;
        }
        if (i == 4) {
            objArr[1] = "getUnloadedModulesBelongingToScope";
        } else if (i == 5) {
            objArr[1] = "getModelBranchesAffectingScope";
        } else if (i != 7 && i != 8 && i != 10 && i != 11) {
            if (i != 14 && i != 15 && i != 21 && i != 22 && i != 24 && i != 25) {
                switch (i) {
                    case 17:
                        objArr[1] = SchemaSymbols.ATTVAL_UNION;
                        break;
                    case 19:
                        objArr[1] = "uniteWith";
                        break;
                    case 27:
                        objArr[1] = "allScope";
                        break;
                    case 29:
                        objArr[1] = "projectScope";
                        break;
                    case 31:
                        objArr[1] = "everythingScope";
                        break;
                    case 34:
                        objArr[1] = "moduleScope";
                        break;
                    case 36:
                        objArr[1] = "moduleWithLibrariesScope";
                        break;
                    case 38:
                        objArr[1] = "moduleWithDependenciesScope";
                        break;
                    case 40:
                        objArr[1] = "moduleRuntimeScope";
                        break;
                    case 43:
                        objArr[1] = "moduleWithDependenciesAndLibrariesScope";
                        break;
                    case 45:
                        objArr[1] = "moduleWithDependentsScope";
                        break;
                    case 47:
                        objArr[1] = "moduleTestsWithDependentsScope";
                        break;
                    case 57:
                        objArr[1] = "filesWithoutLibrariesScope";
                        break;
                    case 60:
                        objArr[1] = "filesWithLibrariesScope";
                        break;
                    case 63:
                        objArr[1] = "filesScope";
                        break;
                    case 66:
                        objArr[1] = "getScopeRestrictedByFileTypes";
                        break;
                    default:
                        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/search/GlobalSearchScope";
                        break;
                }
            } else {
                objArr[1] = SchemaSymbols.ATTVAL_UNION;
            }
        } else {
            objArr[1] = "intersectWith";
        }
        switch (i) {
            case 2:
                objArr[2] = "isSearchInModuleContent";
                break;
            case 3:
                objArr[2] = "accept";
                break;
            case 4:
            case 5:
            case 7:
            case 8:
            case 10:
            case 11:
            case 14:
            case 15:
            case 17:
            case 19:
            case 21:
            case 22:
            case 24:
            case 25:
            case 27:
            case 29:
            case 31:
            case 34:
            case 36:
            case 38:
            case 40:
            case 43:
            case 45:
            case 47:
            case 57:
            case 60:
            case 63:
            case 66:
                break;
            case 6:
            case 9:
            case 12:
                objArr[2] = "intersectWith";
                break;
            case 13:
            case 16:
            case 20:
            case 23:
                objArr[2] = SchemaSymbols.ATTVAL_UNION;
                break;
            case 18:
                objArr[2] = "uniteWith";
                break;
            case 26:
                objArr[2] = "allScope";
                break;
            case 28:
                objArr[2] = "projectScope";
                break;
            case 30:
                objArr[2] = "everythingScope";
                break;
            case 32:
                objArr[2] = "notScope";
                break;
            case 33:
                objArr[2] = "moduleScope";
                break;
            case 35:
                objArr[2] = "moduleWithLibrariesScope";
                break;
            case 37:
                objArr[2] = "moduleWithDependenciesScope";
                break;
            case 39:
                objArr[2] = "moduleRuntimeScope";
                break;
            case 41:
            case 42:
                objArr[2] = "moduleWithDependenciesAndLibrariesScope";
                break;
            case 44:
                objArr[2] = "moduleWithDependentsScope";
                break;
            case 46:
                objArr[2] = "moduleTestsWithDependentsScope";
                break;
            case 48:
            case 49:
            case 50:
                objArr[2] = "fileScope";
                break;
            case 51:
            case 52:
            case 53:
            case 54:
            case 61:
            case 62:
                objArr[2] = "filesScope";
                break;
            case 55:
            case 56:
                objArr[2] = "filesWithoutLibrariesScope";
                break;
            case 58:
            case 59:
                objArr[2] = "filesWithLibrariesScope";
                break;
            case 64:
            case 65:
                objArr[2] = "getScopeRestrictedByFileTypes";
                break;
            default:
                objArr[2] = "compare";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 4 && i != 5 && i != 7 && i != 8 && i != 10 && i != 11 && i != 14 && i != 15 && i != 21 && i != 22 && i != 24 && i != 25) {
            switch (i) {
                case 17:
                case 19:
                case 27:
                case 29:
                case 31:
                case 34:
                case 36:
                case 38:
                case 40:
                case 43:
                case 45:
                case 47:
                case 57:
                case 60:
                case 63:
                case 66:
                    break;
                default:
                    throw new IllegalArgumentException(str2);
            }
        }
        throw new IllegalStateException(str2);
    }

    public int compare(VirtualFile virtualFile, VirtualFile virtualFile2) {
        if (virtualFile == null) {
            $$$reportNull$$$0(0);
        }
        if (virtualFile2 == null) {
            $$$reportNull$$$0(1);
        }
        return 0;
    }

    public boolean isForceSearchingInLibrarySources() {
        return false;
    }

    public abstract boolean isSearchInLibraries();

    public abstract boolean isSearchInModuleContent(Module module);

    protected GlobalSearchScope(Project project) {
        this.myProject = project;
    }

    protected GlobalSearchScope() {
        this(null);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.search.ProjectAwareFileFilter
    public Project getProject() {
        return this.myProject;
    }

    public boolean isSearchInModuleContent(Module module, boolean z) {
        if (module == null) {
            $$$reportNull$$$0(2);
        }
        return isSearchInModuleContent(module);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileFilter
    public final boolean accept(VirtualFile virtualFile) {
        if (virtualFile == null) {
            $$$reportNull$$$0(3);
        }
        return contains(virtualFile);
    }

    public Collection<Object> getUnloadedModulesBelongingToScope() {
        Set setEmptySet = Collections.emptySet();
        if (setEmptySet == null) {
            $$$reportNull$$$0(4);
        }
        return setEmptySet;
    }

    public Collection<ModelBranch> getModelBranchesAffectingScope() {
        Set setEmptySet = Collections.emptySet();
        if (setEmptySet == null) {
            $$$reportNull$$$0(5);
        }
        return setEmptySet;
    }

    public GlobalSearchScope intersectWith(GlobalSearchScope globalSearchScope) {
        if (globalSearchScope == null) {
            $$$reportNull$$$0(6);
        }
        if (globalSearchScope == this) {
            return this;
        }
        if (!(globalSearchScope instanceof IntersectionScope) || !((IntersectionScope) globalSearchScope).containsScope(this)) {
            return new IntersectionScope(globalSearchScope);
        }
        if (globalSearchScope == null) {
            $$$reportNull$$$0(8);
        }
        return globalSearchScope;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.search.SearchScope
    public SearchScope intersectWith(SearchScope searchScope) {
        if (searchScope == null) {
            $$$reportNull$$$0(9);
        }
        if (searchScope instanceof LocalSearchScope) {
            LocalSearchScope localSearchScopeIntersectWith = intersectWith((LocalSearchScope) searchScope);
            if (localSearchScopeIntersectWith == null) {
                $$$reportNull$$$0(10);
            }
            return localSearchScopeIntersectWith;
        }
        GlobalSearchScope globalSearchScopeIntersectWith = intersectWith((GlobalSearchScope) searchScope);
        if (globalSearchScopeIntersectWith == null) {
            $$$reportNull$$$0(11);
        }
        return globalSearchScopeIntersectWith;
    }

    public LocalSearchScope intersectWith(LocalSearchScope localSearchScope) {
        if (localSearchScope == null) {
            $$$reportNull$$$0(12);
        }
        PsiElement[] scope = localSearchScope.getScope();
        ArrayList arrayList = new ArrayList(scope.length);
        for (PsiElement psiElement : scope) {
            if (PsiSearchScopeUtil.isInScope(this, psiElement)) {
                arrayList.add(psiElement);
            }
        }
        return arrayList.isEmpty() ? LocalSearchScope.EMPTY : new LocalSearchScope((PsiElement[]) arrayList.toArray(PsiElement.EMPTY_ARRAY), null, localSearchScope.isIgnoreInjectedPsi());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.search.SearchScope
    public GlobalSearchScope union(SearchScope searchScope) {
        if (searchScope == null) {
            $$$reportNull$$$0(13);
        }
        if (searchScope instanceof GlobalSearchScope) {
            GlobalSearchScope globalSearchScopeUniteWith = uniteWith((GlobalSearchScope) searchScope);
            if (globalSearchScopeUniteWith == null) {
                $$$reportNull$$$0(14);
            }
            return globalSearchScopeUniteWith;
        }
        GlobalSearchScope globalSearchScopeUnion = union((LocalSearchScope) searchScope);
        if (globalSearchScopeUnion == null) {
            $$$reportNull$$$0(15);
        }
        return globalSearchScopeUnion;
    }

    public GlobalSearchScope union(final LocalSearchScope localSearchScope) {
        if (localSearchScope == null) {
            $$$reportNull$$$0(16);
        }
        PsiElement[] scope = localSearchScope.getScope();
        return scope.length == 0 ? this : new GlobalSearchScope(scope[0].getProject()) { // from class: org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope.1
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                String str = (i == 4 || i == 5) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                Object[] objArr = new Object[(i == 4 || i == 5) ? 2 : 3];
                if (i == 1) {
                    objArr[0] = "file1";
                } else if (i == 2) {
                    objArr[0] = "file2";
                } else if (i == 3) {
                    objArr[0] = "aModule";
                } else if (i == 4 || i == 5) {
                    objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/search/GlobalSearchScope$1";
                } else {
                    objArr[0] = "file";
                }
                if (i == 4) {
                    objArr[1] = "getUnloadedModulesBelongingToScope";
                } else if (i != 5) {
                    objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/search/GlobalSearchScope$1";
                } else {
                    objArr[1] = "getModelBranchesAffectingScope";
                }
                if (i == 1 || i == 2) {
                    objArr[2] = "compare";
                } else if (i == 3) {
                    objArr[2] = "isSearchInModuleContent";
                } else if (i != 4 && i != 5) {
                    objArr[2] = "contains";
                }
                String str2 = String.format(str, objArr);
                if (i != 4 && i != 5) {
                    throw new IllegalArgumentException(str2);
                }
                throw new IllegalStateException(str2);
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope, org.jetbrains.kotlin.com.intellij.psi.search.SearchScope
            public /* bridge */ /* synthetic */ SearchScope union(SearchScope searchScope) {
                return super.union(searchScope);
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.search.SearchScope
            public boolean contains(VirtualFile virtualFile) {
                if (virtualFile == null) {
                    $$$reportNull$$$0(0);
                }
                return GlobalSearchScope.this.contains(virtualFile) || localSearchScope.isInScope(virtualFile);
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope
            public int compare(VirtualFile virtualFile, VirtualFile virtualFile2) {
                if (virtualFile == null) {
                    $$$reportNull$$$0(1);
                }
                if (virtualFile2 == null) {
                    $$$reportNull$$$0(2);
                }
                if (GlobalSearchScope.this.contains(virtualFile) && GlobalSearchScope.this.contains(virtualFile2)) {
                    return GlobalSearchScope.this.compare(virtualFile, virtualFile2);
                }
                return 0;
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope
            public boolean isSearchInModuleContent(Module module) {
                if (module == null) {
                    $$$reportNull$$$0(3);
                }
                return GlobalSearchScope.this.isSearchInModuleContent(module);
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope
            public boolean isSearchInLibraries() {
                return GlobalSearchScope.this.isSearchInLibraries();
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope
            public Collection<Object> getUnloadedModulesBelongingToScope() {
                Collection<Object> unloadedModulesBelongingToScope = GlobalSearchScope.this.getUnloadedModulesBelongingToScope();
                if (unloadedModulesBelongingToScope == null) {
                    $$$reportNull$$$0(4);
                }
                return unloadedModulesBelongingToScope;
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope
            public Collection<ModelBranch> getModelBranchesAffectingScope() {
                Collection<ModelBranch> modelBranchesAffectingScope = GlobalSearchScope.this.getModelBranchesAffectingScope();
                if (modelBranchesAffectingScope == null) {
                    $$$reportNull$$$0(5);
                }
                return modelBranchesAffectingScope;
            }

            public String toString() {
                return "UnionToLocal: (" + GlobalSearchScope.this + ", " + localSearchScope + Constant.AFTER_QUTO;
            }
        };
    }

    public GlobalSearchScope uniteWith(GlobalSearchScope globalSearchScope) {
        if (globalSearchScope == null) {
            $$$reportNull$$$0(18);
        }
        GlobalSearchScope globalSearchScopeCreate = UnionScope.create(new GlobalSearchScope[]{this, globalSearchScope});
        if (globalSearchScopeCreate == null) {
            $$$reportNull$$$0(19);
        }
        return globalSearchScopeCreate;
    }

    public static GlobalSearchScope union(Collection<? extends GlobalSearchScope> collection) {
        if (collection == null) {
            $$$reportNull$$$0(20);
        }
        if (collection.isEmpty()) {
            throw new IllegalArgumentException("Empty scope collection");
        }
        if (collection.size() == 1) {
            GlobalSearchScope next = collection.iterator().next();
            if (next == null) {
                $$$reportNull$$$0(21);
            }
            return next;
        }
        GlobalSearchScope globalSearchScopeCreate = UnionScope.create((GlobalSearchScope[]) collection.toArray(EMPTY_ARRAY));
        if (globalSearchScopeCreate == null) {
            $$$reportNull$$$0(22);
        }
        return globalSearchScopeCreate;
    }

    public static GlobalSearchScope union(GlobalSearchScope[] globalSearchScopeArr) {
        if (globalSearchScopeArr == null) {
            $$$reportNull$$$0(23);
        }
        if (globalSearchScopeArr.length == 0) {
            throw new IllegalArgumentException("Empty scope array");
        }
        if (globalSearchScopeArr.length == 1) {
            GlobalSearchScope globalSearchScope = globalSearchScopeArr[0];
            if (globalSearchScope == null) {
                $$$reportNull$$$0(24);
            }
            return globalSearchScope;
        }
        GlobalSearchScope globalSearchScopeCreate = UnionScope.create(globalSearchScopeArr);
        if (globalSearchScopeCreate == null) {
            $$$reportNull$$$0(25);
        }
        return globalSearchScopeCreate;
    }

    public static GlobalSearchScope allScope(Project project) {
        if (project == null) {
            $$$reportNull$$$0(26);
        }
        GlobalSearchScope allScope = ProjectScope.getAllScope(project);
        if (allScope == null) {
            $$$reportNull$$$0(27);
        }
        return allScope;
    }

    public static GlobalSearchScope projectScope(Project project) {
        if (project == null) {
            $$$reportNull$$$0(28);
        }
        GlobalSearchScope projectScope = ProjectScope.getProjectScope(project);
        if (projectScope == null) {
            $$$reportNull$$$0(29);
        }
        return projectScope;
    }

    public static GlobalSearchScope everythingScope(Project project) {
        if (project == null) {
            $$$reportNull$$$0(30);
        }
        GlobalSearchScope everythingScope = ProjectScope.getEverythingScope(project);
        if (everythingScope == null) {
            $$$reportNull$$$0(31);
        }
        return everythingScope;
    }

    public static GlobalSearchScope notScope(GlobalSearchScope globalSearchScope) {
        if (globalSearchScope == null) {
            $$$reportNull$$$0(32);
        }
        return new NotScope();
    }

    private static final class NotScope extends DelegatingGlobalSearchScope {
        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            if (i == 1) {
                objArr[0] = "file";
            } else if (i == 2 || i == 3) {
                objArr[0] = "aModule";
            } else {
                objArr[0] = "scope";
            }
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/search/GlobalSearchScope$NotScope";
            if (i == 1) {
                objArr[2] = "contains";
            } else if (i == 2 || i == 3) {
                objArr[2] = "isSearchInModuleContent";
            } else {
                objArr[2] = "<init>";
            }
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.search.DelegatingGlobalSearchScope, org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope
        public boolean isSearchInLibraries() {
            return true;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.search.DelegatingGlobalSearchScope, org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope
        public boolean isSearchInModuleContent(Module module) {
            if (module != null) {
                return true;
            }
            $$$reportNull$$$0(3);
            return true;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.search.DelegatingGlobalSearchScope, org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope
        public boolean isSearchInModuleContent(Module module, boolean z) {
            if (module != null) {
                return true;
            }
            $$$reportNull$$$0(2);
            return true;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        private NotScope(GlobalSearchScope globalSearchScope) {
            super(globalSearchScope);
            if (globalSearchScope == null) {
                $$$reportNull$$$0(0);
            }
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.search.DelegatingGlobalSearchScope, org.jetbrains.kotlin.com.intellij.psi.search.SearchScope
        public boolean contains(VirtualFile virtualFile) {
            if (virtualFile == null) {
                $$$reportNull$$$0(1);
            }
            return !this.myBaseScope.contains(virtualFile);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.search.DelegatingGlobalSearchScope
        public String toString() {
            return "NOT: (" + this.myBaseScope + Constant.AFTER_QUTO;
        }
    }

    public static GlobalSearchScope moduleScope(Module module) {
        if (module == null) {
            $$$reportNull$$$0(33);
        }
        GlobalSearchScope moduleScope = module.getModuleScope();
        if (moduleScope == null) {
            $$$reportNull$$$0(34);
        }
        return moduleScope;
    }

    public static GlobalSearchScope moduleWithLibrariesScope(Module module) {
        if (module == null) {
            $$$reportNull$$$0(35);
        }
        GlobalSearchScope moduleWithLibrariesScope = module.getModuleWithLibrariesScope();
        if (moduleWithLibrariesScope == null) {
            $$$reportNull$$$0(36);
        }
        return moduleWithLibrariesScope;
    }

    public static GlobalSearchScope moduleWithDependenciesScope(Module module) {
        if (module == null) {
            $$$reportNull$$$0(37);
        }
        GlobalSearchScope moduleWithDependenciesScope = module.getModuleWithDependenciesScope();
        if (moduleWithDependenciesScope == null) {
            $$$reportNull$$$0(38);
        }
        return moduleWithDependenciesScope;
    }

    public static GlobalSearchScope moduleRuntimeScope(Module module, boolean z) {
        if (module == null) {
            $$$reportNull$$$0(39);
        }
        GlobalSearchScope moduleRuntimeScope = module.getModuleRuntimeScope(z);
        if (moduleRuntimeScope == null) {
            $$$reportNull$$$0(40);
        }
        return moduleRuntimeScope;
    }

    public static GlobalSearchScope moduleWithDependenciesAndLibrariesScope(Module module) {
        if (module == null) {
            $$$reportNull$$$0(41);
        }
        return moduleWithDependenciesAndLibrariesScope(module, true);
    }

    public static GlobalSearchScope moduleWithDependenciesAndLibrariesScope(Module module, boolean z) {
        if (module == null) {
            $$$reportNull$$$0(42);
        }
        GlobalSearchScope moduleWithDependenciesAndLibrariesScope = module.getModuleWithDependenciesAndLibrariesScope(z);
        if (moduleWithDependenciesAndLibrariesScope == null) {
            $$$reportNull$$$0(43);
        }
        return moduleWithDependenciesAndLibrariesScope;
    }

    public static GlobalSearchScope moduleWithDependentsScope(Module module) {
        if (module == null) {
            $$$reportNull$$$0(44);
        }
        GlobalSearchScope moduleWithDependentsScope = module.getModuleWithDependentsScope();
        if (moduleWithDependentsScope == null) {
            $$$reportNull$$$0(45);
        }
        return moduleWithDependentsScope;
    }

    public static GlobalSearchScope moduleTestsWithDependentsScope(Module module) {
        if (module == null) {
            $$$reportNull$$$0(46);
        }
        GlobalSearchScope moduleTestsWithDependentsScope = module.getModuleTestsWithDependentsScope();
        if (moduleTestsWithDependentsScope == null) {
            $$$reportNull$$$0(47);
        }
        return moduleTestsWithDependentsScope;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static GlobalSearchScope fileScope(PsiFile psiFile) {
        if (psiFile == null) {
            $$$reportNull$$$0(48);
        }
        return new FileScope(psiFile.getProject(), psiFile.getVirtualFile(), null);
    }

    public static GlobalSearchScope fileScope(Project project, VirtualFile virtualFile) {
        if (project == null) {
            $$$reportNull$$$0(49);
        }
        return fileScope(project, virtualFile, null);
    }

    public static GlobalSearchScope fileScope(Project project, VirtualFile virtualFile, String str) {
        if (project == null) {
            $$$reportNull$$$0(50);
        }
        return new FileScope(project, virtualFile, str);
    }

    public static GlobalSearchScope filesScope(Project project, Collection<? extends VirtualFile> collection) {
        if (project == null) {
            $$$reportNull$$$0(51);
        }
        if (collection == null) {
            $$$reportNull$$$0(52);
        }
        return filesScope(project, collection, null);
    }

    public static GlobalSearchScope filesScope(Project project, Supplier<? extends Collection<? extends VirtualFile>> supplier) {
        if (project == null) {
            $$$reportNull$$$0(53);
        }
        if (supplier == null) {
            $$$reportNull$$$0(54);
        }
        return new LazyFilesScope(project, supplier);
    }

    public static GlobalSearchScope filesWithoutLibrariesScope(Project project, Collection<? extends VirtualFile> collection) {
        if (project == null) {
            $$$reportNull$$$0(55);
        }
        if (collection == null) {
            $$$reportNull$$$0(56);
        }
        if (!collection.isEmpty()) {
            return new FilesScope(project, collection, false);
        }
        GlobalSearchScope globalSearchScope = EMPTY_SCOPE;
        if (globalSearchScope == null) {
            $$$reportNull$$$0(57);
        }
        return globalSearchScope;
    }

    public static GlobalSearchScope filesWithLibrariesScope(Project project, Collection<? extends VirtualFile> collection) {
        if (project == null) {
            $$$reportNull$$$0(58);
        }
        if (collection == null) {
            $$$reportNull$$$0(59);
        }
        if (!collection.isEmpty()) {
            return new FilesScope(project, collection, true);
        }
        GlobalSearchScope globalSearchScope = EMPTY_SCOPE;
        if (globalSearchScope == null) {
            $$$reportNull$$$0(60);
        }
        return globalSearchScope;
    }

    public static GlobalSearchScope filesScope(Project project, Collection<? extends VirtualFile> collection, final String str) {
        if (project == null) {
            $$$reportNull$$$0(61);
        }
        if (collection == null) {
            $$$reportNull$$$0(62);
        }
        if (!collection.isEmpty()) {
            return collection.size() == 1 ? fileScope(project, collection.iterator().next(), str) : new FilesScope(project, collection) { // from class: org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope.2
                private static /* synthetic */ void $$$reportNull$$$0(int i) {
                    throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/com/intellij/psi/search/GlobalSearchScope$2", "getDisplayName"));
                }

                @Override // org.jetbrains.kotlin.com.intellij.psi.search.SearchScope
                public String getDisplayName() {
                    String displayName = str;
                    if (displayName == null) {
                        displayName = super.getDisplayName();
                    }
                    if (displayName == null) {
                        $$$reportNull$$$0(0);
                    }
                    return displayName;
                }
            };
        }
        GlobalSearchScope globalSearchScope = EMPTY_SCOPE;
        if (globalSearchScope == null) {
            $$$reportNull$$$0(63);
        }
        return globalSearchScope;
    }

    private static final class IntersectionScope extends GlobalSearchScope implements VirtualFileEnumerationAware {
        private final GlobalSearchScope myScope1;
        private final GlobalSearchScope myScope2;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = (i == 4 || i == 10 || i == 11) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
            Object[] objArr = new Object[(i == 4 || i == 10 || i == 11) ? 2 : 3];
            switch (i) {
                case 1:
                    objArr[0] = "scope2";
                    break;
                case 2:
                case 3:
                    objArr[0] = "scope";
                    break;
                case 4:
                case 10:
                case 11:
                    objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/search/GlobalSearchScope$IntersectionScope";
                    break;
                case 5:
                    objArr[0] = "file";
                    break;
                case 6:
                    objArr[0] = "file1";
                    break;
                case 7:
                    objArr[0] = "file2";
                    break;
                case 8:
                case 9:
                    objArr[0] = "aModule";
                    break;
                default:
                    objArr[0] = "scope1";
                    break;
            }
            if (i == 4) {
                objArr[1] = "getDisplayName";
            } else if (i == 10) {
                objArr[1] = "getUnloadedModulesBelongingToScope";
            } else if (i != 11) {
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/search/GlobalSearchScope$IntersectionScope";
            } else {
                objArr[1] = "getModelBranchesAffectingScope";
            }
            switch (i) {
                case 2:
                    objArr[2] = "intersectWith";
                    break;
                case 3:
                    objArr[2] = "containsScope";
                    break;
                case 4:
                case 10:
                case 11:
                    break;
                case 5:
                    objArr[2] = "contains";
                    break;
                case 6:
                case 7:
                    objArr[2] = "compare";
                    break;
                case 8:
                case 9:
                    objArr[2] = "isSearchInModuleContent";
                    break;
                default:
                    objArr[2] = "<init>";
                    break;
            }
            String str2 = String.format(str, objArr);
            if (i != 4 && i != 10 && i != 11) {
                throw new IllegalArgumentException(str2);
            }
            throw new IllegalStateException(str2);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope, org.jetbrains.kotlin.com.intellij.psi.search.SearchScope
        public /* bridge */ /* synthetic */ SearchScope union(SearchScope searchScope) {
            return super.union(searchScope);
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        private IntersectionScope(GlobalSearchScope globalSearchScope, GlobalSearchScope globalSearchScope2) {
            super(globalSearchScope.getProject() == null ? globalSearchScope2.getProject() : globalSearchScope.getProject());
            if (globalSearchScope == null) {
                $$$reportNull$$$0(0);
            }
            if (globalSearchScope2 == null) {
                $$$reportNull$$$0(1);
            }
            this.myScope1 = globalSearchScope;
            this.myScope2 = globalSearchScope2;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope
        public GlobalSearchScope intersectWith(GlobalSearchScope globalSearchScope) {
            if (globalSearchScope == null) {
                $$$reportNull$$$0(2);
            }
            return containsScope(globalSearchScope) ? this : new IntersectionScope(this, globalSearchScope);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean containsScope(GlobalSearchScope globalSearchScope) {
            if (globalSearchScope == null) {
                $$$reportNull$$$0(3);
            }
            if (this.myScope1.equals(globalSearchScope) || this.myScope2.equals(globalSearchScope) || equals(globalSearchScope)) {
                return true;
            }
            GlobalSearchScope globalSearchScope2 = this.myScope1;
            if ((globalSearchScope2 instanceof IntersectionScope) && ((IntersectionScope) globalSearchScope2).containsScope(globalSearchScope)) {
                return true;
            }
            GlobalSearchScope globalSearchScope3 = this.myScope2;
            return (globalSearchScope3 instanceof IntersectionScope) && ((IntersectionScope) globalSearchScope3).containsScope(globalSearchScope);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.search.SearchScope
        public String getDisplayName() {
            String strMessage = CoreBundle.message("psi.search.scope.intersection", this.myScope1.getDisplayName(), this.myScope2.getDisplayName());
            if (strMessage == null) {
                $$$reportNull$$$0(4);
            }
            return strMessage;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.search.SearchScope
        public boolean contains(VirtualFile virtualFile) {
            if (virtualFile == null) {
                $$$reportNull$$$0(5);
            }
            return this.myScope1.contains(virtualFile) && this.myScope2.contains(virtualFile);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope
        public int compare(VirtualFile virtualFile, VirtualFile virtualFile2) {
            if (virtualFile == null) {
                $$$reportNull$$$0(6);
            }
            if (virtualFile2 == null) {
                $$$reportNull$$$0(7);
            }
            int iCompare = this.myScope1.compare(virtualFile, virtualFile2);
            int iCompare2 = this.myScope2.compare(virtualFile, virtualFile2);
            if (iCompare == 0) {
                return iCompare2;
            }
            if (iCompare2 == 0) {
                return iCompare;
            }
            if ((iCompare > 0) == (iCompare2 > 0)) {
                return iCompare;
            }
            return 0;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope
        public boolean isSearchInModuleContent(Module module) {
            if (module == null) {
                $$$reportNull$$$0(8);
            }
            return this.myScope1.isSearchInModuleContent(module) && this.myScope2.isSearchInModuleContent(module);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope
        public boolean isSearchInModuleContent(Module module, boolean z) {
            if (module == null) {
                $$$reportNull$$$0(9);
            }
            return this.myScope1.isSearchInModuleContent(module, z) && this.myScope2.isSearchInModuleContent(module, z);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope
        public boolean isSearchInLibraries() {
            return this.myScope1.isSearchInLibraries() && this.myScope2.isSearchInLibraries();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope
        public Collection<Object> getUnloadedModulesBelongingToScope() {
            Collection<Object> collectionIntersection = ContainerUtil.intersection(this.myScope1.getUnloadedModulesBelongingToScope(), this.myScope2.getUnloadedModulesBelongingToScope());
            if (collectionIntersection == null) {
                $$$reportNull$$$0(10);
            }
            return collectionIntersection;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope
        public Collection<ModelBranch> getModelBranchesAffectingScope() {
            Collection<ModelBranch> collectionIntersection = ContainerUtil.intersection(this.myScope1.getModelBranchesAffectingScope(), this.myScope2.getModelBranchesAffectingScope());
            if (collectionIntersection == null) {
                $$$reportNull$$$0(11);
            }
            return collectionIntersection;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof IntersectionScope)) {
                return false;
            }
            IntersectionScope intersectionScope = (IntersectionScope) obj;
            return this.myScope1.equals(intersectionScope.myScope1) && this.myScope2.equals(intersectionScope.myScope2);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.search.SearchScope
        public int calcHashCode() {
            return (this.myScope1.hashCode() * 31) + this.myScope2.hashCode();
        }

        public String toString() {
            return "Intersection: (" + this.myScope1 + ", " + this.myScope2 + Constant.AFTER_QUTO;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.search.impl.VirtualFileEnumerationAware
        public VirtualFileEnumeration extractFileEnumeration() {
            VirtualFileEnumeration virtualFileEnumerationExtract = VirtualFileEnumeration.extract(this.myScope1);
            VirtualFileEnumeration virtualFileEnumerationExtract2 = VirtualFileEnumeration.extract(this.myScope2);
            if (virtualFileEnumerationExtract == null) {
                return virtualFileEnumerationExtract2;
            }
            return virtualFileEnumerationExtract2 == null ? virtualFileEnumerationExtract : new IntersectionFileEnumeration(Arrays.asList(virtualFileEnumerationExtract, virtualFileEnumerationExtract2));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class UnionScope extends GlobalSearchScope implements VirtualFileEnumerationAware {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private final GlobalSearchScope[] myScopes;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = (i == 1 || i == 2 || i == 3 || i == 5 || i == 7 || i == 16 || i == 9 || i == 10) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
            Object[] objArr = new Object[(i == 1 || i == 2 || i == 3 || i == 5 || i == 7 || i == 16 || i == 9 || i == 10) ? 2 : 3];
            switch (i) {
                case 1:
                case 2:
                case 3:
                case 5:
                case 7:
                case 9:
                case 10:
                case 16:
                    objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/search/GlobalSearchScope$UnionScope";
                    break;
                case 4:
                case 6:
                default:
                    objArr[0] = "scopes";
                    break;
                case 8:
                    objArr[0] = "file";
                    break;
                case 11:
                    objArr[0] = "file1";
                    break;
                case 12:
                    objArr[0] = "file2";
                    break;
                case 13:
                case 14:
                    objArr[0] = "module";
                    break;
                case 15:
                    objArr[0] = "scope";
                    break;
            }
            if (i == 1 || i == 2 || i == 3) {
                objArr[1] = CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME;
            } else if (i == 5) {
                objArr[1] = "unionWithUnionScope";
            } else if (i == 7) {
                objArr[1] = "getDisplayName";
            } else if (i == 16) {
                objArr[1] = "uniteWith";
            } else if (i == 9) {
                objArr[1] = "getUnloadedModulesBelongingToScope";
            } else if (i != 10) {
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/search/GlobalSearchScope$UnionScope";
            } else {
                objArr[1] = "getModelBranchesAffectingScope";
            }
            switch (i) {
                case 1:
                case 2:
                case 3:
                case 5:
                case 7:
                case 9:
                case 10:
                case 16:
                    break;
                case 4:
                    objArr[2] = "tryCreateUnionFor2Scopes";
                    break;
                case 6:
                    objArr[2] = "<init>";
                    break;
                case 8:
                    objArr[2] = "contains";
                    break;
                case 11:
                case 12:
                    objArr[2] = "compare";
                    break;
                case 13:
                case 14:
                    objArr[2] = "isSearchInModuleContent";
                    break;
                case 15:
                    objArr[2] = "uniteWith";
                    break;
                default:
                    objArr[2] = CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME;
                    break;
            }
            String str2 = String.format(str, objArr);
            if (i != 1 && i != 2 && i != 3 && i != 5 && i != 7 && i != 16 && i != 9 && i != 10) {
                throw new IllegalArgumentException(str2);
            }
            throw new IllegalStateException(str2);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope, org.jetbrains.kotlin.com.intellij.psi.search.SearchScope
        public /* bridge */ /* synthetic */ SearchScope union(SearchScope searchScope) {
            return super.union(searchScope);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.search.impl.VirtualFileEnumerationAware
        public VirtualFileEnumeration extractFileEnumeration() {
            SmartList smartList = new SmartList();
            for (GlobalSearchScope globalSearchScope : this.myScopes) {
                VirtualFileEnumeration virtualFileEnumerationExtract = VirtualFileEnumeration.extract(globalSearchScope);
                if (virtualFileEnumerationExtract == null) {
                    return null;
                }
                smartList.add(virtualFileEnumerationExtract);
            }
            return new UnionFileEnumeration(smartList);
        }

        static GlobalSearchScope create(GlobalSearchScope[] globalSearchScopeArr) {
            GlobalSearchScope globalSearchScopeTryCreateUnionFor2Scopes;
            if (globalSearchScopeArr == null) {
                $$$reportNull$$$0(0);
            }
            if (globalSearchScopeArr.length == 2 && (globalSearchScopeTryCreateUnionFor2Scopes = tryCreateUnionFor2Scopes(globalSearchScopeArr)) != null) {
                if (globalSearchScopeTryCreateUnionFor2Scopes == null) {
                    $$$reportNull$$$0(1);
                }
                return globalSearchScopeTryCreateUnionFor2Scopes;
            }
            HashSet hashSet = new HashSet(globalSearchScopeArr.length);
            Project project = null;
            for (GlobalSearchScope globalSearchScope : globalSearchScopeArr) {
                if (globalSearchScope != EMPTY_SCOPE) {
                    Project project2 = globalSearchScope.getProject();
                    if (project2 != null) {
                        project = project2;
                    }
                    if (globalSearchScope instanceof UnionScope) {
                        ContainerUtil.addAll(hashSet, ((UnionScope) globalSearchScope).myScopes);
                    } else {
                        hashSet.add(globalSearchScope);
                    }
                }
            }
            if (hashSet.isEmpty()) {
                GlobalSearchScope globalSearchScope2 = EMPTY_SCOPE;
                if (globalSearchScope2 == null) {
                    $$$reportNull$$$0(2);
                }
                return globalSearchScope2;
            }
            if (hashSet.size() != 1) {
                return new UnionScope(project, (GlobalSearchScope[]) hashSet.toArray(EMPTY_ARRAY));
            }
            GlobalSearchScope globalSearchScope3 = (GlobalSearchScope) hashSet.iterator().next();
            if (globalSearchScope3 == null) {
                $$$reportNull$$$0(3);
            }
            return globalSearchScope3;
        }

        private static GlobalSearchScope tryCreateUnionFor2Scopes(GlobalSearchScope[] globalSearchScopeArr) {
            if (globalSearchScopeArr == null) {
                $$$reportNull$$$0(4);
            }
            GlobalSearchScope globalSearchScope = globalSearchScopeArr[0];
            GlobalSearchScope globalSearchScope2 = globalSearchScopeArr[1];
            if (globalSearchScope == EMPTY_SCOPE) {
                return globalSearchScope2;
            }
            if (globalSearchScope2 == EMPTY_SCOPE) {
                return globalSearchScope;
            }
            boolean z = globalSearchScope instanceof UnionScope;
            if (z && (globalSearchScope2 instanceof UnionScope)) {
                return null;
            }
            Project project = (Project) ObjectUtils.chooseNotNull(globalSearchScope.getProject(), globalSearchScope2.getProject());
            if (z) {
                return unionWithUnionScope(globalSearchScope, globalSearchScope2, project);
            }
            if (globalSearchScope2 instanceof UnionScope) {
                return unionWithUnionScope(globalSearchScope2, globalSearchScope, project);
            }
            return new UnionScope(project, globalSearchScopeArr);
        }

        private static GlobalSearchScope unionWithUnionScope(GlobalSearchScope globalSearchScope, GlobalSearchScope globalSearchScope2, Project project) {
            GlobalSearchScope[] globalSearchScopeArr = ((UnionScope) globalSearchScope).myScopes;
            if (!ArrayUtil.contains(globalSearchScope2, globalSearchScopeArr)) {
                return new UnionScope(project, (GlobalSearchScope[]) ArrayUtil.append(globalSearchScopeArr, globalSearchScope2));
            }
            if (globalSearchScope == null) {
                $$$reportNull$$$0(5);
            }
            return globalSearchScope;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        private UnionScope(Project project, GlobalSearchScope[] globalSearchScopeArr) {
            super(project);
            if (globalSearchScopeArr == null) {
                $$$reportNull$$$0(6);
            }
            this.myScopes = globalSearchScopeArr;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.search.SearchScope
        public String getDisplayName() {
            String strMessage = CoreBundle.message("psi.search.scope.union", this.myScopes[0].getDisplayName(), this.myScopes[1].getDisplayName());
            if (strMessage == null) {
                $$$reportNull$$$0(7);
            }
            return strMessage;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.search.SearchScope
        public boolean contains(final VirtualFile virtualFile) {
            if (virtualFile == null) {
                $$$reportNull$$$0(8);
            }
            return ContainerUtil.find(this.myScopes, new Condition() { // from class: org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope$UnionScope$$ExternalSyntheticLambda4
                @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Condition
                public final boolean value(Object obj) {
                    return ((GlobalSearchScope) obj).contains(virtualFile);
                }
            }) != null;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope
        public Collection<Object> getUnloadedModulesBelongingToScope() {
            LinkedHashSet linkedHashSet = new LinkedHashSet();
            for (GlobalSearchScope globalSearchScope : this.myScopes) {
                linkedHashSet.addAll(globalSearchScope.getUnloadedModulesBelongingToScope());
            }
            return linkedHashSet;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope
        public Collection<ModelBranch> getModelBranchesAffectingScope() {
            LinkedHashSet linkedHashSet = new LinkedHashSet();
            for (GlobalSearchScope globalSearchScope : this.myScopes) {
                linkedHashSet.addAll(globalSearchScope.getModelBranchesAffectingScope());
            }
            return linkedHashSet;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope
        public int compare(final VirtualFile virtualFile, final VirtualFile virtualFile2) {
            if (virtualFile == null) {
                $$$reportNull$$$0(11);
            }
            if (virtualFile2 == null) {
                $$$reportNull$$$0(12);
            }
            final int[] iArr = {0};
            ContainerUtil.process(this.myScopes, new Processor() { // from class: org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope$UnionScope$$ExternalSyntheticLambda0
                @Override // org.jetbrains.kotlin.com.intellij.util.Processor
                public final boolean process(Object obj) {
                    return GlobalSearchScope.UnionScope.lambda$compare$1(virtualFile, virtualFile2, iArr, (GlobalSearchScope) obj);
                }
            });
            return iArr[0];
        }

        static /* synthetic */ boolean lambda$compare$1(VirtualFile virtualFile, VirtualFile virtualFile2, int[] iArr, GlobalSearchScope globalSearchScope) {
            if (!globalSearchScope.contains(virtualFile) || !globalSearchScope.contains(virtualFile2)) {
                return true;
            }
            int iCompare = globalSearchScope.compare(virtualFile, virtualFile2);
            int i = iArr[0];
            if (i == 0) {
                iArr[0] = iCompare;
                return true;
            }
            if (iCompare == 0) {
                return true;
            }
            if ((i > 0) == (iCompare > 0)) {
                return true;
            }
            iArr[0] = 0;
            return false;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope
        public boolean isSearchInModuleContent(final Module module) {
            if (module == null) {
                $$$reportNull$$$0(13);
            }
            return ContainerUtil.find(this.myScopes, new Condition() { // from class: org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope$UnionScope$$ExternalSyntheticLambda3
                @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Condition
                public final boolean value(Object obj) {
                    return ((GlobalSearchScope) obj).isSearchInModuleContent(module);
                }
            }) != null;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope
        public boolean isSearchInModuleContent(final Module module, final boolean z) {
            if (module == null) {
                $$$reportNull$$$0(14);
            }
            return ContainerUtil.find(this.myScopes, new Condition() { // from class: org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope$UnionScope$$ExternalSyntheticLambda1
                @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Condition
                public final boolean value(Object obj) {
                    return ((GlobalSearchScope) obj).isSearchInModuleContent(module, z);
                }
            }) != null;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope
        public boolean isSearchInLibraries() {
            return ContainerUtil.find(this.myScopes, new Condition() { // from class: org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope$UnionScope$$ExternalSyntheticLambda2
                @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Condition
                public final boolean value(Object obj) {
                    return ((GlobalSearchScope) obj).isSearchInLibraries();
                }
            }) != null;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj instanceof UnionScope) {
                return ContainerUtil.set(this.myScopes).equals(ContainerUtil.set(((UnionScope) obj).myScopes));
            }
            return false;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.search.SearchScope
        public int calcHashCode() {
            return Arrays.hashCode(this.myScopes);
        }

        public String toString() {
            return "Union: (" + StringUtil.join((Iterable<?>) Arrays.asList(this.myScopes), ",") + Constant.AFTER_QUTO;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope
        public GlobalSearchScope uniteWith(GlobalSearchScope globalSearchScope) {
            if (globalSearchScope == null) {
                $$$reportNull$$$0(15);
            }
            if (globalSearchScope instanceof UnionScope) {
                return create((GlobalSearchScope[]) ArrayUtil.mergeArrays(this.myScopes, ((UnionScope) globalSearchScope).myScopes));
            }
            GlobalSearchScope globalSearchScopeUniteWith = super.uniteWith(globalSearchScope);
            if (globalSearchScopeUniteWith == null) {
                $$$reportNull$$$0(16);
            }
            return globalSearchScopeUniteWith;
        }
    }

    public static GlobalSearchScope getScopeRestrictedByFileTypes(GlobalSearchScope globalSearchScope, FileType... fileTypeArr) {
        if (globalSearchScope == null) {
            $$$reportNull$$$0(64);
        }
        if (fileTypeArr == null) {
            $$$reportNull$$$0(65);
        }
        GlobalSearchScope globalSearchScope2 = EMPTY_SCOPE;
        if (globalSearchScope == globalSearchScope2) {
            if (globalSearchScope2 == null) {
                $$$reportNull$$$0(66);
            }
            return globalSearchScope2;
        }
        if (fileTypeArr.length == 0) {
            throw new IllegalArgumentException("empty fileTypes");
        }
        return new FileTypeRestrictionScope(fileTypeArr);
    }

    private static final class FileTypeRestrictionScope extends DelegatingGlobalSearchScope implements VirtualFileEnumerationAware {
        private final FileType[] myFileTypes;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = (i == 4 || i == 6) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
            Object[] objArr = new Object[(i == 4 || i == 6) ? 2 : 3];
            if (i == 1) {
                objArr[0] = "fileTypes";
            } else if (i == 2) {
                objArr[0] = "file";
            } else if (i == 4 || i == 6) {
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/search/GlobalSearchScope$FileTypeRestrictionScope";
            } else {
                objArr[0] = "scope";
            }
            if (i == 4) {
                objArr[1] = "intersectWith";
            } else if (i != 6) {
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/search/GlobalSearchScope$FileTypeRestrictionScope";
            } else {
                objArr[1] = "uniteWith";
            }
            if (i == 2) {
                objArr[2] = "contains";
            } else if (i == 3) {
                objArr[2] = "intersectWith";
            } else if (i != 4) {
                if (i == 5) {
                    objArr[2] = "uniteWith";
                } else if (i != 6) {
                    objArr[2] = "<init>";
                }
            }
            String str2 = String.format(str, objArr);
            if (i != 4 && i != 6) {
                throw new IllegalArgumentException(str2);
            }
            throw new IllegalStateException(str2);
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        private FileTypeRestrictionScope(GlobalSearchScope globalSearchScope, FileType[] fileTypeArr) {
            super(globalSearchScope);
            if (globalSearchScope == null) {
                $$$reportNull$$$0(0);
            }
            if (fileTypeArr == null) {
                $$$reportNull$$$0(1);
            }
            this.myFileTypes = fileTypeArr;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.search.DelegatingGlobalSearchScope, org.jetbrains.kotlin.com.intellij.psi.search.SearchScope
        public boolean contains(VirtualFile virtualFile) {
            if (virtualFile == null) {
                $$$reportNull$$$0(2);
            }
            if (!super.contains(virtualFile)) {
                return false;
            }
            for (FileType fileType : this.myFileTypes) {
                if (FileTypeRegistry.getInstance().isFileOfType(virtualFile, fileType)) {
                    return true;
                }
            }
            return false;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope
        public GlobalSearchScope intersectWith(GlobalSearchScope globalSearchScope) {
            if (globalSearchScope == null) {
                $$$reportNull$$$0(3);
            }
            if (globalSearchScope instanceof FileTypeRestrictionScope) {
                FileTypeRestrictionScope fileTypeRestrictionScope = (FileTypeRestrictionScope) globalSearchScope;
                if (fileTypeRestrictionScope.myBaseScope == this.myBaseScope) {
                    ArrayList arrayList = new ArrayList(Arrays.asList(fileTypeRestrictionScope.myFileTypes));
                    arrayList.retainAll(Arrays.asList(this.myFileTypes));
                    return new FileTypeRestrictionScope(this.myBaseScope, (FileType[]) arrayList.toArray(FileType.EMPTY_ARRAY));
                }
            }
            GlobalSearchScope globalSearchScopeIntersectWith = super.intersectWith(globalSearchScope);
            if (globalSearchScopeIntersectWith == null) {
                $$$reportNull$$$0(4);
            }
            return globalSearchScopeIntersectWith;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope
        public GlobalSearchScope uniteWith(GlobalSearchScope globalSearchScope) {
            if (globalSearchScope == null) {
                $$$reportNull$$$0(5);
            }
            if (globalSearchScope instanceof FileTypeRestrictionScope) {
                FileTypeRestrictionScope fileTypeRestrictionScope = (FileTypeRestrictionScope) globalSearchScope;
                if (fileTypeRestrictionScope.myBaseScope == this.myBaseScope) {
                    return new FileTypeRestrictionScope(this.myBaseScope, (FileType[]) ArrayUtil.mergeArrays(this.myFileTypes, fileTypeRestrictionScope.myFileTypes));
                }
            }
            GlobalSearchScope globalSearchScopeUniteWith = super.uniteWith(globalSearchScope);
            if (globalSearchScopeUniteWith == null) {
                $$$reportNull$$$0(6);
            }
            return globalSearchScopeUniteWith;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.search.DelegatingGlobalSearchScope
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if ((obj instanceof FileTypeRestrictionScope) && super.equals(obj)) {
                return Arrays.equals(this.myFileTypes, ((FileTypeRestrictionScope) obj).myFileTypes);
            }
            return false;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.search.DelegatingGlobalSearchScope, org.jetbrains.kotlin.com.intellij.psi.search.SearchScope
        public int calcHashCode() {
            return (super.calcHashCode() * 31) + Arrays.hashCode(this.myFileTypes);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.search.DelegatingGlobalSearchScope
        public String toString() {
            return "Restricted by file types: " + Arrays.asList(this.myFileTypes) + " in (" + this.myBaseScope + Constant.AFTER_QUTO;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.search.impl.VirtualFileEnumerationAware
        public VirtualFileEnumeration extractFileEnumeration() {
            if (this.myBaseScope instanceof VirtualFileEnumeration) {
                return (VirtualFileEnumeration) this.myBaseScope;
            }
            return null;
        }
    }

    private static class EmptyScope extends GlobalSearchScope {
        /* JADX WARN: Code duplicated, block: B:19:0x0029  */
        /* JADX WARN: Code duplicated, block: B:20:0x002c  */
        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = (i == 3 || i == 5) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
            Object[] objArr = new Object[(i == 3 || i == 5) ? 2 : 3];
            if (i == 1) {
                objArr[0] = "aModule";
            } else if (i == 2) {
                objArr[0] = "scope";
            } else if (i == 3) {
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/search/GlobalSearchScope$EmptyScope";
            } else if (i == 4) {
                objArr[0] = "scope";
            } else if (i != 5) {
                objArr[0] = "file";
            } else {
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/search/GlobalSearchScope$EmptyScope";
            }
            if (i == 3) {
                objArr[1] = "intersectWith";
            } else if (i != 5) {
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/search/GlobalSearchScope$EmptyScope";
            } else {
                objArr[1] = "uniteWith";
            }
            if (i == 1) {
                objArr[2] = "isSearchInModuleContent";
            } else if (i == 2) {
                objArr[2] = "intersectWith";
            } else if (i != 3) {
                if (i == 4) {
                    objArr[2] = "uniteWith";
                } else if (i != 5) {
                    objArr[2] = "contains";
                }
            }
            String str2 = String.format(str, objArr);
            if (i != 3 && i != 5) {
                throw new IllegalArgumentException(str2);
            }
            throw new IllegalStateException(str2);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.search.SearchScope
        public boolean contains(VirtualFile virtualFile) {
            if (virtualFile == null) {
                $$$reportNull$$$0(0);
            }
            return false;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope
        public GlobalSearchScope intersectWith(GlobalSearchScope globalSearchScope) {
            if (globalSearchScope == null) {
                $$$reportNull$$$0(2);
            }
            return this;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope
        public boolean isSearchInLibraries() {
            return false;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope
        public boolean isSearchInModuleContent(Module module) {
            if (module != null) {
                return false;
            }
            $$$reportNull$$$0(1);
            return false;
        }

        private EmptyScope() {
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope, org.jetbrains.kotlin.com.intellij.psi.search.SearchScope
        public /* bridge */ /* synthetic */ SearchScope union(SearchScope searchScope) {
            return super.union(searchScope);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope
        public GlobalSearchScope uniteWith(GlobalSearchScope globalSearchScope) {
            if (globalSearchScope == null) {
                $$$reportNull$$$0(4);
            }
            if (globalSearchScope == null) {
                $$$reportNull$$$0(5);
            }
            return globalSearchScope;
        }

        public String toString() {
            return "EMPTY";
        }
    }

    private static final class FileScope extends GlobalSearchScope implements VirtualFileEnumeration {
        private final String myDisplayName;
        private final Module myModule;
        private final VirtualFile myVirtualFile;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = (i == 3 || i == 4 || i == 5) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
            Object[] objArr = new Object[(i == 3 || i == 4 || i == 5) ? 2 : 3];
            if (i == 1) {
                objArr[0] = "file";
            } else if (i == 2) {
                objArr[0] = "aModule";
            } else if (i == 3 || i == 4 || i == 5) {
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/search/GlobalSearchScope$FileScope";
            } else {
                objArr[0] = "project";
            }
            if (i == 3) {
                objArr[1] = "getDisplayName";
            } else if (i == 4) {
                objArr[1] = "getModelBranchesAffectingScope";
            } else if (i != 5) {
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/search/GlobalSearchScope$FileScope";
            } else {
                objArr[1] = "asIterable";
            }
            if (i == 1) {
                objArr[2] = "contains";
            } else if (i == 2) {
                objArr[2] = "isSearchInModuleContent";
            } else if (i != 3 && i != 4 && i != 5) {
                objArr[2] = "<init>";
            }
            String str2 = String.format(str, objArr);
            if (i != 3 && i != 4 && i != 5) {
                throw new IllegalArgumentException(str2);
            }
            throw new IllegalStateException(str2);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope, org.jetbrains.kotlin.com.intellij.psi.search.SearchScope
        public /* bridge */ /* synthetic */ SearchScope union(SearchScope searchScope) {
            return super.union(searchScope);
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        private FileScope(Project project, VirtualFile virtualFile, String str) {
            super(project);
            if (project == null) {
                $$$reportNull$$$0(0);
            }
            this.myVirtualFile = virtualFile;
            this.myDisplayName = str;
            Module moduleForFile = null;
            FileIndexFacade fileIndexFacade = project.isDefault() ? null : FileIndexFacade.getInstance(project);
            if (virtualFile != null && fileIndexFacade != null) {
                moduleForFile = fileIndexFacade.getModuleForFile(virtualFile);
            }
            this.myModule = moduleForFile;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.search.SearchScope
        public boolean contains(VirtualFile virtualFile) {
            if (virtualFile == null) {
                $$$reportNull$$$0(1);
            }
            return Comparing.equal(this.myVirtualFile, virtualFile);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope
        public boolean isSearchInModuleContent(Module module) {
            if (module == null) {
                $$$reportNull$$$0(2);
            }
            return module == this.myModule;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope
        public boolean isSearchInLibraries() {
            return this.myModule == null;
        }

        public String toString() {
            return "File: " + this.myVirtualFile;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.search.SearchScope
        public String getDisplayName() {
            String displayName = this.myDisplayName;
            if (displayName == null) {
                displayName = super.getDisplayName();
            }
            if (displayName == null) {
                $$$reportNull$$$0(3);
            }
            return displayName;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope
        public Collection<ModelBranch> getModelBranchesAffectingScope() {
            List listCreateMaybeSingletonList;
            VirtualFile virtualFile = this.myVirtualFile;
            if (virtualFile == null) {
                listCreateMaybeSingletonList = Collections.emptyList();
            } else {
                listCreateMaybeSingletonList = ContainerUtil.createMaybeSingletonList(ModelBranch.getFileBranch(virtualFile));
            }
            if (listCreateMaybeSingletonList == null) {
                $$$reportNull$$$0(4);
            }
            return listCreateMaybeSingletonList;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || obj.getClass() != getClass()) {
                return false;
            }
            FileScope fileScope = (FileScope) obj;
            return Objects.equals(this.myVirtualFile, fileScope.myVirtualFile) && Objects.equals(this.myDisplayName, fileScope.myDisplayName) && Objects.equals(this.myModule, fileScope.myModule);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.search.SearchScope
        protected int calcHashCode() {
            return Objects.hash(this.myVirtualFile, this.myModule, this.myDisplayName);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.search.impl.VirtualFileEnumeration
        public boolean contains(int i) {
            ModificationTracker modificationTracker = this.myVirtualFile;
            return (modificationTracker instanceof VirtualFileWithId) && ((VirtualFileWithId) modificationTracker).getId() == i;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.search.impl.VirtualFileEnumeration
        public int[] asInts() {
            ModificationTracker modificationTracker = this.myVirtualFile;
            if (modificationTracker instanceof VirtualFileWithId) {
                return new int[]{((VirtualFileWithId) modificationTracker).getId()};
            }
            return ArrayUtil.EMPTY_INT_ARRAY;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.search.impl.VirtualFileEnumeration
        public Iterable<VirtualFile> asIterable() {
            List listSingletonList = Collections.singletonList(this.myVirtualFile);
            if (listSingletonList == null) {
                $$$reportNull$$$0(5);
            }
            return listSingletonList;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static abstract class AbstractFilesScope extends GlobalSearchScope implements VirtualFileEnumeration {
        volatile Boolean myHasFilesOutOfProjectRoots;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = i != 2 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
            Object[] objArr = new Object[i != 2 ? 3 : 2];
            if (i == 1) {
                objArr[0] = "aModule";
            } else if (i != 2) {
                objArr[0] = "file";
            } else {
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/search/GlobalSearchScope$AbstractFilesScope";
            }
            if (i != 2) {
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/search/GlobalSearchScope$AbstractFilesScope";
            } else {
                objArr[1] = "asIterable";
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

        abstract VirtualFileSet getFiles();

        @Override // org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope
        public boolean isSearchInModuleContent(Module module) {
            if (module == null) {
                $$$reportNull$$$0(1);
            }
            return true;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope, org.jetbrains.kotlin.com.intellij.psi.search.SearchScope
        public /* bridge */ /* synthetic */ SearchScope union(SearchScope searchScope) {
            return super.union(searchScope);
        }

        AbstractFilesScope(Project project, Boolean bool) {
            super(project);
            this.myHasFilesOutOfProjectRoots = bool;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.search.SearchScope
        public boolean contains(VirtualFile virtualFile) {
            if (virtualFile == null) {
                $$$reportNull$$$0(0);
            }
            return getFiles().contains(virtualFile);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope
        public boolean isSearchInLibraries() {
            return hasFilesOutOfProjectRoots();
        }

        public boolean equals(Object obj) {
            return this == obj || ((obj instanceof AbstractFilesScope) && getFiles().equals(((AbstractFilesScope) obj).getFiles()));
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.search.SearchScope
        public int calcHashCode() {
            return getFiles().hashCode();
        }

        private boolean hasFilesOutOfProjectRoots() {
            Boolean boolValueOf = this.myHasFilesOutOfProjectRoots;
            if (boolValueOf == null) {
                final Project project = getProject();
                boolValueOf = Boolean.valueOf((project == null || project.isDefault() || ContainerUtil.find(getFiles(), new Condition() { // from class: org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope$AbstractFilesScope$$ExternalSyntheticLambda0
                    @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Condition
                    public final boolean value(Object obj) {
                        return GlobalSearchScope.AbstractFilesScope.lambda$hasFilesOutOfProjectRoots$0(project, (VirtualFile) obj);
                    }
                }) != null) ? false : true);
                this.myHasFilesOutOfProjectRoots = boolValueOf;
            }
            return boolValueOf.booleanValue();
        }

        static /* synthetic */ boolean lambda$hasFilesOutOfProjectRoots$0(Project project, VirtualFile virtualFile) {
            return FileIndexFacade.getInstance(project).getModuleForFile(virtualFile) != null;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.search.impl.VirtualFileEnumeration
        public boolean contains(int i) {
            return ((CompactVirtualFileSet) getFiles()).containsId(i);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.search.impl.VirtualFileEnumeration
        public int[] asInts() {
            return ((CompactVirtualFileSet) getFiles()).onlyInternalFileIds();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.search.impl.VirtualFileEnumeration
        public Iterable<VirtualFile> asIterable() {
            Set setUnmodifiableSet = Collections.unmodifiableSet(getFiles());
            if (setUnmodifiableSet == null) {
                $$$reportNull$$$0(2);
            }
            return setUnmodifiableSet;
        }
    }

    public static class FilesScope extends AbstractFilesScope {
        private final VirtualFileSet myFiles;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = i != 2 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
            Object[] objArr = new Object[i != 2 ? 3 : 2];
            if (i != 2) {
                objArr[0] = "files";
            } else {
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/search/GlobalSearchScope$FilesScope";
            }
            if (i != 2) {
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/search/GlobalSearchScope$FilesScope";
            } else {
                objArr[1] = "getFiles";
            }
            if (i != 2) {
                objArr[2] = "<init>";
            }
            String str2 = String.format(str, objArr);
            if (i == 2) {
                throw new IllegalStateException(str2);
            }
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope.AbstractFilesScope, org.jetbrains.kotlin.com.intellij.psi.search.impl.VirtualFileEnumeration
        public /* bridge */ /* synthetic */ int[] asInts() {
            return super.asInts();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope.AbstractFilesScope, org.jetbrains.kotlin.com.intellij.psi.search.impl.VirtualFileEnumeration
        public /* bridge */ /* synthetic */ Iterable asIterable() {
            return super.asIterable();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope.AbstractFilesScope, org.jetbrains.kotlin.com.intellij.psi.search.SearchScope
        public /* bridge */ /* synthetic */ int calcHashCode() {
            return super.calcHashCode();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope.AbstractFilesScope, org.jetbrains.kotlin.com.intellij.psi.search.impl.VirtualFileEnumeration
        public /* bridge */ /* synthetic */ boolean contains(int i) {
            return super.contains(i);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope.AbstractFilesScope, org.jetbrains.kotlin.com.intellij.psi.search.SearchScope
        public /* bridge */ /* synthetic */ boolean contains(VirtualFile virtualFile) {
            return super.contains(virtualFile);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope.AbstractFilesScope
        public /* bridge */ /* synthetic */ boolean equals(Object obj) {
            return super.equals(obj);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope.AbstractFilesScope, org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope
        public /* bridge */ /* synthetic */ boolean isSearchInLibraries() {
            return super.isSearchInLibraries();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope.AbstractFilesScope, org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope
        public /* bridge */ /* synthetic */ boolean isSearchInModuleContent(Module module) {
            return super.isSearchInModuleContent(module);
        }

        /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
        private FilesScope(Project project, Collection<? extends VirtualFile> collection) {
            this(project, collection, (Boolean) null);
            if (collection == null) {
                $$$reportNull$$$0(0);
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        private FilesScope(Project project, Collection<? extends VirtualFile> collection, Boolean bool) {
            super(project, bool);
            if (collection == null) {
                $$$reportNull$$$0(1);
            }
            VirtualFileSet virtualFileSetCreateCompactVirtualFileSet = VfsUtilCore.createCompactVirtualFileSet(collection);
            this.myFiles = virtualFileSetCreateCompactVirtualFileSet;
            virtualFileSetCreateCompactVirtualFileSet.freeze();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope.AbstractFilesScope
        public VirtualFileSet getFiles() {
            VirtualFileSet virtualFileSet = this.myFiles;
            if (virtualFileSet == null) {
                $$$reportNull$$$0(2);
            }
            return virtualFileSet;
        }

        public String toString() {
            return "Files: [" + StringUtil.join((Iterable<?>) this.myFiles, ", ") + "]; search in libraries: " + (this.myHasFilesOutOfProjectRoots != null ? this.myHasFilesOutOfProjectRoots : "unknown");
        }
    }

    private static class LazyFilesScope extends AbstractFilesScope {
        private volatile VirtualFileSet myFiles;
        private Supplier<? extends Collection<? extends VirtualFile>> myFilesSupplier;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = i != 1 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
            Object[] objArr = new Object[i != 1 ? 3 : 2];
            if (i != 1) {
                objArr[0] = "files";
            } else {
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/search/GlobalSearchScope$LazyFilesScope";
            }
            if (i != 1) {
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/search/GlobalSearchScope$LazyFilesScope";
            } else {
                objArr[1] = "getFiles";
            }
            if (i != 1) {
                objArr[2] = "<init>";
            }
            String str2 = String.format(str, objArr);
            if (i == 1) {
                throw new IllegalStateException(str2);
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        private LazyFilesScope(Project project, Supplier<? extends Collection<? extends VirtualFile>> supplier) {
            super(project, null);
            if (supplier == null) {
                $$$reportNull$$$0(0);
            }
            this.myFilesSupplier = supplier;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope.AbstractFilesScope
        public VirtualFileSet getFiles() {
            if (this.myFiles == null) {
                synchronized (this) {
                    if (this.myFiles == null) {
                        VirtualFileSet virtualFileSetCreateCompactVirtualFileSet = VfsUtilCore.createCompactVirtualFileSet(this.myFilesSupplier.get());
                        virtualFileSetCreateCompactVirtualFileSet.freeze();
                        this.myFilesSupplier = null;
                        this.myFiles = virtualFileSetCreateCompactVirtualFileSet;
                    }
                }
            }
            VirtualFileSet virtualFileSet = this.myFiles;
            if (virtualFileSet == null) {
                $$$reportNull$$$0(1);
            }
            return virtualFileSet;
        }

        public String toString() {
            return "Files: [" + (this.myFiles == null ? "(not loaded yet)" : StringUtil.join((Iterable<?>) this.myFiles, ", ")) + "]; search in libraries: " + (this.myHasFilesOutOfProjectRoots != null ? this.myHasFilesOutOfProjectRoots : "unknown");
        }
    }
}
