package org.jetbrains.kotlin.com.intellij.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.roots.PackageIndex;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.util.CollectionQuery;
import org.jetbrains.kotlin.com.intellij.util.Query;

/* JADX INFO: loaded from: classes3.dex */
public class CorePackageIndex extends PackageIndex {
    private static final Logger LOG = Logger.getInstance((Class<?>) CorePackageIndex.class);
    private final List<VirtualFile> myClasspath = new ArrayList();

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 1 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 1 ? 3 : 2];
        if (i != 1) {
            objArr[0] = "packageName";
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/core/CorePackageIndex";
        }
        if (i != 1) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/core/CorePackageIndex";
        } else {
            objArr[1] = "getDirectoriesByPackageName";
        }
        if (i != 1) {
            if (i != 2) {
                objArr[2] = "getDirectoriesByPackageName";
            } else {
                objArr[2] = "getDirsByPackageName";
            }
        }
        String str2 = String.format(str, objArr);
        if (i == 1) {
            throw new IllegalStateException(str2);
        }
    }

    private List<VirtualFile> roots() {
        return this.myClasspath;
    }

    private List<VirtualFile> findDirectoriesByPackageName(String str) {
        ArrayList arrayList = new ArrayList();
        String strReplace = str.replace(".", "/");
        Iterator<VirtualFile> it = roots().iterator();
        while (it.hasNext()) {
            VirtualFile virtualFileFindFileByRelativePath = it.next().findFileByRelativePath(strReplace);
            if (virtualFileFindFileByRelativePath != null) {
                arrayList.add(virtualFileFindFileByRelativePath);
            }
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.roots.PackageIndex
    public Query<VirtualFile> getDirsByPackageName(String str, boolean z) {
        if (str == null) {
            $$$reportNull$$$0(2);
        }
        return new CollectionQuery(findDirectoriesByPackageName(str));
    }

    public void addToClasspath(VirtualFile virtualFile) {
        this.myClasspath.add(virtualFile);
    }
}
