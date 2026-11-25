package org.jetbrains.kotlin.com.intellij.psi.search;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import name.antonsmirnov.firmata.FormatHelper;
import org.jetbrains.kotlin.com.intellij.core.CoreBundle;
import org.jetbrains.kotlin.com.intellij.openapi.module.Module;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VfsUtilCore;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiTreeChangeEvent;
import org.jetbrains.kotlin.com.intellij.util.Function;

/* JADX INFO: loaded from: classes3.dex */
public class NonClasspathDirectoriesScope extends GlobalSearchScope {
    private final Set<VirtualFile> myRoots;

    /* JADX WARN: Code duplicated, block: B:24:0x0037  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 5 || i == 6) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 5 || i == 6) ? 2 : 3];
        if (i == 2) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/search/NonClasspathDirectoriesScope";
        } else if (i == 3) {
            objArr[0] = "file";
        } else if (i == 4) {
            objArr[0] = "aModule";
        } else if (i == 5 || i == 6) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/search/NonClasspathDirectoriesScope";
        } else {
            objArr[0] = PsiTreeChangeEvent.PROP_ROOTS;
        }
        if (i == 2) {
            objArr[1] = "compose";
        } else if (i == 5 || i == 6) {
            objArr[1] = "getDisplayName";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/search/NonClasspathDirectoriesScope";
        }
        switch (i) {
            case 1:
                objArr[2] = "compose";
                break;
            case 2:
            case 5:
            case 6:
                break;
            case 3:
                objArr[2] = "contains";
                break;
            case 4:
                objArr[2] = "isSearchInModuleContent";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 5 && i != 6) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
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
        $$$reportNull$$$0(4);
        return false;
    }

    public NonClasspathDirectoriesScope(Collection<? extends VirtualFile> collection) {
        if (collection == null) {
            $$$reportNull$$$0(0);
        }
        this.myRoots = new HashSet(collection);
    }

    public static GlobalSearchScope compose(List<? extends VirtualFile> list) {
        if (list == null) {
            $$$reportNull$$$0(1);
        }
        if (list.isEmpty()) {
            GlobalSearchScope globalSearchScope = EMPTY_SCOPE;
            if (globalSearchScope == null) {
                $$$reportNull$$$0(2);
            }
            return globalSearchScope;
        }
        return new NonClasspathDirectoriesScope(list);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.search.SearchScope
    public boolean contains(VirtualFile virtualFile) {
        if (virtualFile == null) {
            $$$reportNull$$$0(3);
        }
        return VfsUtilCore.isUnder(virtualFile, this.myRoots);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof NonClasspathDirectoriesScope) && this.myRoots.equals(((NonClasspathDirectoriesScope) obj).myRoots);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.search.SearchScope
    public int calcHashCode() {
        return this.myRoots.hashCode();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.search.SearchScope
    public String getDisplayName() {
        if (this.myRoots.size() == 1) {
            String strMessage = CoreBundle.message("scope.display.name.directory.0", this.myRoots.iterator().next().getName());
            if (strMessage == null) {
                $$$reportNull$$$0(5);
            }
            return strMessage;
        }
        String strMessage2 = CoreBundle.message("scope.display.name.directories.0", StringUtil.join((Collection) this.myRoots, new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.search.NonClasspathDirectoriesScope$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.util.Function
            public final Object fun(Object obj) {
                return NonClasspathDirectoriesScope.lambda$getDisplayName$0((VirtualFile) obj);
            }
        }, ", "));
        if (strMessage2 == null) {
            $$$reportNull$$$0(6);
        }
        return strMessage2;
    }

    static /* synthetic */ String lambda$getDisplayName$0(VirtualFile virtualFile) {
        return FormatHelper.QUOTE + virtualFile.getName() + FormatHelper.QUOTE;
    }
}
