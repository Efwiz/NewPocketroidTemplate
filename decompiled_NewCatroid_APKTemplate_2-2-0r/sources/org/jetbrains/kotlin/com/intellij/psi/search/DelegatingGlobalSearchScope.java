package org.jetbrains.kotlin.com.intellij.psi.search;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import javax.swing.Icon;
import org.jetbrains.kotlin.com.intellij.model.ModelBranch;
import org.jetbrains.kotlin.com.intellij.openapi.module.Module;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtilRt;

/* JADX INFO: loaded from: classes3.dex */
public class DelegatingGlobalSearchScope extends GlobalSearchScope {
    protected final GlobalSearchScope myBaseScope;
    private final Object myEquality;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
                str = "@NotNull method %s.%s must not return null";
                break;
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
                i2 = 2;
                break;
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 2:
                objArr[0] = "equality";
                break;
            case 3:
                objArr[0] = "project";
                break;
            case 4:
            default:
                objArr[0] = "baseScope";
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
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/search/DelegatingGlobalSearchScope";
                break;
        }
        switch (i) {
            case 10:
                objArr[1] = "getUnloadedModulesBelongingToScope";
                break;
            case 11:
                objArr[1] = "getModelBranchesAffectingScope";
                break;
            case 12:
                objArr[1] = "getDisplayName";
                break;
            case 13:
                objArr[1] = "getDelegate";
                break;
            case 14:
                objArr[1] = "unwrap";
                break;
            default:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/search/DelegatingGlobalSearchScope";
                break;
        }
        switch (i) {
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
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
                throw new IllegalStateException(str2);
            default:
                throw new IllegalArgumentException(str2);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope, org.jetbrains.kotlin.com.intellij.psi.search.SearchScope
    public /* bridge */ /* synthetic */ SearchScope union(SearchScope searchScope) {
        return super.union(searchScope);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public DelegatingGlobalSearchScope(GlobalSearchScope globalSearchScope) {
        this(globalSearchScope, ArrayUtilRt.EMPTY_OBJECT_ARRAY);
        if (globalSearchScope == null) {
            $$$reportNull$$$0(0);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DelegatingGlobalSearchScope(GlobalSearchScope globalSearchScope, Object... objArr) {
        super(globalSearchScope.getProject());
        if (globalSearchScope == null) {
            $$$reportNull$$$0(1);
        }
        if (objArr == null) {
            $$$reportNull$$$0(2);
        }
        this.myBaseScope = globalSearchScope;
        this.myEquality = objArr.length == 0 ? Collections.emptyList() : Arrays.asList(objArr);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DelegatingGlobalSearchScope(Project project, GlobalSearchScope globalSearchScope) {
        super(project);
        if (project == null) {
            $$$reportNull$$$0(3);
        }
        if (globalSearchScope == null) {
            $$$reportNull$$$0(4);
        }
        this.myBaseScope = globalSearchScope;
        this.myEquality = Collections.emptyList();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.search.SearchScope
    public boolean contains(VirtualFile virtualFile) {
        if (virtualFile == null) {
            $$$reportNull$$$0(5);
        }
        return this.myBaseScope.contains(virtualFile);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope
    public int compare(VirtualFile virtualFile, VirtualFile virtualFile2) {
        if (virtualFile == null) {
            $$$reportNull$$$0(6);
        }
        if (virtualFile2 == null) {
            $$$reportNull$$$0(7);
        }
        return this.myBaseScope.compare(virtualFile, virtualFile2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope
    public boolean isSearchInModuleContent(Module module) {
        if (module == null) {
            $$$reportNull$$$0(8);
        }
        return this.myBaseScope.isSearchInModuleContent(module);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope
    public boolean isSearchInModuleContent(Module module, boolean z) {
        if (module == null) {
            $$$reportNull$$$0(9);
        }
        return this.myBaseScope.isSearchInModuleContent(module, z);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope
    public boolean isSearchInLibraries() {
        return this.myBaseScope.isSearchInLibraries();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope
    public Collection<Object> getUnloadedModulesBelongingToScope() {
        Collection<Object> unloadedModulesBelongingToScope = this.myBaseScope.getUnloadedModulesBelongingToScope();
        if (unloadedModulesBelongingToScope == null) {
            $$$reportNull$$$0(10);
        }
        return unloadedModulesBelongingToScope;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope
    public Collection<ModelBranch> getModelBranchesAffectingScope() {
        Collection<ModelBranch> modelBranchesAffectingScope = this.myBaseScope.getModelBranchesAffectingScope();
        if (modelBranchesAffectingScope == null) {
            $$$reportNull$$$0(11);
        }
        return modelBranchesAffectingScope;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.search.SearchScope
    public String getDisplayName() {
        String displayName = this.myBaseScope.getDisplayName();
        if (displayName == null) {
            $$$reportNull$$$0(12);
        }
        return displayName;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.search.SearchScope
    public Icon getIcon() {
        return this.myBaseScope.getIcon();
    }

    public String toString() {
        return getClass().getName() + "[" + this.myBaseScope + "]";
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        DelegatingGlobalSearchScope delegatingGlobalSearchScope = (DelegatingGlobalSearchScope) obj;
        return this.myBaseScope.equals(delegatingGlobalSearchScope.myBaseScope) && this.myEquality.equals(delegatingGlobalSearchScope.myEquality);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.search.SearchScope
    public int calcHashCode() {
        return (this.myBaseScope.calcHashCode() * 31) + this.myEquality.hashCode();
    }

    public GlobalSearchScope getDelegate() {
        GlobalSearchScope globalSearchScope = this.myBaseScope;
        if (globalSearchScope == null) {
            $$$reportNull$$$0(13);
        }
        return globalSearchScope;
    }

    public GlobalSearchScope unwrap() {
        GlobalSearchScope globalSearchScopeUnwrap = this.myBaseScope;
        if (globalSearchScopeUnwrap instanceof DelegatingGlobalSearchScope) {
            globalSearchScopeUnwrap = ((DelegatingGlobalSearchScope) globalSearchScopeUnwrap).unwrap();
        }
        if (globalSearchScopeUnwrap == null) {
            $$$reportNull$$$0(14);
        }
        return globalSearchScopeUnwrap;
    }
}
