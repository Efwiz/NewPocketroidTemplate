package org.jetbrains.kotlin.com.intellij.psi.search;

import javax.swing.Icon;
import org.jetbrains.kotlin.com.intellij.core.CoreBundle;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;

/* JADX INFO: loaded from: classes3.dex */
public abstract class SearchScope {
    private static int hashCodeCounter;
    private final int myDefaultHashCode;
    private transient int myHashCode;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 1 ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[i != 1 ? 2 : 3];
        if (i != 1) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/search/SearchScope";
        } else {
            objArr[0] = "scope";
        }
        if (i != 1) {
            objArr[1] = "getDisplayName";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/search/SearchScope";
        }
        if (i == 1) {
            objArr[2] = "isEmptyScope";
        }
        String str2 = String.format(str, objArr);
        if (i == 1) {
            throw new IllegalArgumentException(str2);
        }
    }

    public abstract boolean contains(VirtualFile virtualFile);

    public Icon getIcon() {
        return null;
    }

    public abstract SearchScope intersectWith(SearchScope searchScope);

    public abstract SearchScope union(SearchScope searchScope);

    public SearchScope() {
        int i = hashCodeCounter + 1;
        hashCodeCounter = i;
        this.myDefaultHashCode = i;
    }

    public int hashCode() {
        int i = this.myHashCode;
        if (i != 0) {
            return i;
        }
        int iCalcHashCode = calcHashCode();
        this.myHashCode = iCalcHashCode;
        return iCalcHashCode;
    }

    protected int calcHashCode() {
        return this.myDefaultHashCode;
    }

    public String getDisplayName() {
        String strMessage = CoreBundle.message("search.scope.unknown", new Object[0]);
        if (strMessage == null) {
            $$$reportNull$$$0(0);
        }
        return strMessage;
    }

    public static boolean isEmptyScope(SearchScope searchScope) {
        if (searchScope == null) {
            $$$reportNull$$$0(1);
        }
        return searchScope == GlobalSearchScope.EMPTY_SCOPE || searchScope == LocalSearchScope.EMPTY;
    }
}
