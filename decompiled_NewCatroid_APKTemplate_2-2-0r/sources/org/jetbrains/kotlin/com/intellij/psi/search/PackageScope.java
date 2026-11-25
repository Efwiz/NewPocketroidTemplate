package org.jetbrains.kotlin.com.intellij.psi.search;

import java.util.HashSet;
import java.util.Set;
import org.jetbrains.kotlin.com.intellij.openapi.module.Module;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.roots.PackageIndex;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassOwner;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiPackage;

/* JADX INFO: loaded from: classes3.dex */
public class PackageScope extends GlobalSearchScope {
    private final Set<VirtualFile> myDirs;
    private final boolean myIncludeLibraries;
    private final boolean myIncludeSubpackages;
    private final PsiPackage myPackage;
    private final String myPackageQNamePrefix;
    private final String myPackageQualifiedName;
    private final boolean myPartOfPackagePrefix;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1) {
            objArr[0] = "file";
        } else if (i != 2) {
            objArr[0] = "aPackage";
        } else {
            objArr[0] = "aModule";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/search/PackageScope";
        if (i == 1) {
            objArr[2] = "contains";
        } else if (i == 2) {
            objArr[2] = "isSearchInModuleContent";
        } else if (i == 3) {
            objArr[2] = "packageScope";
        } else if (i != 4) {
            objArr[2] = "<init>";
        } else {
            objArr[2] = "packageScopeWithoutLibraries";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope
    public boolean isSearchInModuleContent(Module module) {
        if (module != null) {
            return true;
        }
        $$$reportNull$$$0(2);
        return true;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PackageScope(PsiPackage psiPackage, boolean z, boolean z2) {
        super(psiPackage.getProject());
        if (psiPackage == null) {
            $$$reportNull$$$0(0);
        }
        this.myPackage = psiPackage;
        this.myIncludeSubpackages = z;
        Project project = psiPackage.getProject();
        String qualifiedName = psiPackage.getQualifiedName();
        this.myPackageQualifiedName = qualifiedName;
        this.myDirs = new HashSet(PackageIndex.getInstance(project).getDirsByPackageName(qualifiedName, true).findAll());
        this.myIncludeLibraries = z2;
        this.myPartOfPackagePrefix = JavaPsiFacade.getInstance(getProject()).isPartOfPackagePrefix(qualifiedName);
        this.myPackageQNamePrefix = qualifiedName + ".";
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.search.SearchScope
    public boolean contains(VirtualFile virtualFile) {
        if (virtualFile == null) {
            $$$reportNull$$$0(1);
        }
        VirtualFile parent = virtualFile.isDirectory() ? virtualFile : virtualFile.getParent();
        if (this.myIncludeSubpackages) {
            while (parent != null) {
                if (this.myDirs.contains(parent)) {
                    return true;
                }
                parent = parent.getParent();
            }
        } else if (this.myDirs.contains(parent)) {
            return true;
        }
        if (!this.myPartOfPackagePrefix || !this.myIncludeSubpackages) {
            return false;
        }
        PsiFile psiFileFindFile = this.myPackage.getManager().findFile(virtualFile);
        if (!(psiFileFindFile instanceof PsiClassOwner)) {
            return false;
        }
        String packageName = ((PsiClassOwner) psiFileFindFile).getPackageName();
        return this.myPackageQualifiedName.equals(packageName) || packageName.startsWith(this.myPackageQNamePrefix);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope
    public boolean isSearchInLibraries() {
        return this.myIncludeLibraries;
    }

    public String toString() {
        return "package scope: " + this.myPackage + ", includeSubpackages = " + this.myIncludeSubpackages;
    }

    public static GlobalSearchScope packageScope(PsiPackage psiPackage, boolean z) {
        if (psiPackage == null) {
            $$$reportNull$$$0(3);
        }
        return new PackageScope(psiPackage, z, true);
    }

    public static GlobalSearchScope packageScopeWithoutLibraries(PsiPackage psiPackage, boolean z) {
        if (psiPackage == null) {
            $$$reportNull$$$0(4);
        }
        return new PackageScope(psiPackage, z, false);
    }
}
