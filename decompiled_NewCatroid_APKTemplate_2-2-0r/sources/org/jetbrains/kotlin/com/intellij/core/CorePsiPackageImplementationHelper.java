package org.jetbrains.kotlin.com.intellij.core;

import org.jetbrains.kotlin.com.intellij.openapi.util.ModificationTracker;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiPackage;
import org.jetbrains.kotlin.com.intellij.psi.impl.file.PsiPackageImplementationHelper;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;

/* JADX INFO: loaded from: classes3.dex */
public class CorePsiPackageImplementationHelper extends PsiPackageImplementationHelper {
    private static final ModificationTracker[] EMPTY_DEPENDENCY = {ModificationTracker.NEVER_CHANGED};

    /* JADX WARN: Code duplicated, block: B:25:0x003c  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 4 || i == 10) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 4 || i == 10) ? 2 : 3];
        if (i == 1) {
            objArr[0] = "globalSearchScope";
        } else if (i == 2 || i == 4) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/core/CorePsiPackageImplementationHelper";
        } else if (i == 6) {
            objArr[0] = "newQualifiedName";
        } else if (i == 9) {
            objArr[0] = "cachedValueProvider";
        } else if (i != 10) {
            objArr[0] = "psiPackage";
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/core/CorePsiPackageImplementationHelper";
        }
        if (i == 2) {
            objArr[1] = "adjustAllScope";
        } else if (i == 4) {
            objArr[1] = "occursInPackagePrefixes";
        } else if (i != 10) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/core/CorePsiPackageImplementationHelper";
        } else {
            objArr[1] = "getDirectoryCachedValueDependencies";
        }
        switch (i) {
            case 2:
            case 4:
            case 10:
                break;
            case 3:
                objArr[2] = "occursInPackagePrefixes";
                break;
            case 5:
            case 6:
                objArr[2] = "handleQualifiedNameChange";
                break;
            case 7:
                objArr[2] = "navigate";
                break;
            case 8:
                objArr[2] = "packagePrefixExists";
                break;
            case 9:
                objArr[2] = "getDirectoryCachedValueDependencies";
                break;
            default:
                objArr[2] = "adjustAllScope";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 4 && i != 10) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.file.PsiPackageImplementationHelper
    public void handleQualifiedNameChange(PsiPackage psiPackage, String str) {
        if (psiPackage == null) {
            $$$reportNull$$$0(5);
        }
        if (str == null) {
            $$$reportNull$$$0(6);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.file.PsiPackageImplementationHelper
    public void navigate(PsiPackage psiPackage, boolean z) {
        if (psiPackage == null) {
            $$$reportNull$$$0(7);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.file.PsiPackageImplementationHelper
    public boolean packagePrefixExists(PsiPackage psiPackage) {
        if (psiPackage != null) {
            return false;
        }
        $$$reportNull$$$0(8);
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.file.PsiPackageImplementationHelper
    public GlobalSearchScope adjustAllScope(PsiPackage psiPackage, GlobalSearchScope globalSearchScope) {
        if (psiPackage == null) {
            $$$reportNull$$$0(0);
        }
        if (globalSearchScope == null) {
            $$$reportNull$$$0(1);
        }
        if (globalSearchScope == null) {
            $$$reportNull$$$0(2);
        }
        return globalSearchScope;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.file.PsiPackageImplementationHelper
    public VirtualFile[] occursInPackagePrefixes(PsiPackage psiPackage) {
        if (psiPackage == null) {
            $$$reportNull$$$0(3);
        }
        VirtualFile[] virtualFileArr = VirtualFile.EMPTY_ARRAY;
        if (virtualFileArr == null) {
            $$$reportNull$$$0(4);
        }
        return virtualFileArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.file.PsiPackageImplementationHelper
    public Object[] getDirectoryCachedValueDependencies(PsiPackage psiPackage) {
        if (psiPackage == null) {
            $$$reportNull$$$0(9);
        }
        ModificationTracker[] modificationTrackerArr = EMPTY_DEPENDENCY;
        if (modificationTrackerArr == null) {
            $$$reportNull$$$0(10);
        }
        return modificationTrackerArr;
    }
}
