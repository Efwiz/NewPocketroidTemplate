package org.jetbrains.kotlin.com.intellij.openapi.roots;

import java.util.Objects;
import java.util.function.Predicate;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.kotlin.com.intellij.util.Query;

/* JADX INFO: loaded from: classes3.dex */
public abstract class PackageIndex {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1) {
            objArr[0] = "packageName";
        } else if (i != 2) {
            objArr[0] = "project";
        } else {
            objArr[0] = "scope";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/roots/PackageIndex";
        if (i == 1 || i == 2) {
            objArr[2] = "getDirsByPackageName";
        } else {
            objArr[2] = "getInstance";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public abstract Query<VirtualFile> getDirsByPackageName(String str, boolean z);

    public static PackageIndex getInstance(Project project) {
        if (project == null) {
            $$$reportNull$$$0(0);
        }
        return (PackageIndex) project.getService(PackageIndex.class);
    }

    public Query<VirtualFile> getDirsByPackageName(String str, final GlobalSearchScope globalSearchScope) {
        if (str == null) {
            $$$reportNull$$$0(1);
        }
        if (globalSearchScope == null) {
            $$$reportNull$$$0(2);
        }
        Query<VirtualFile> dirsByPackageName = getDirsByPackageName(str, true);
        Objects.requireNonNull(globalSearchScope);
        return dirsByPackageName.filtering(new Predicate() { // from class: org.jetbrains.kotlin.com.intellij.openapi.roots.PackageIndex$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return globalSearchScope.contains((VirtualFile) obj);
            }
        });
    }
}
