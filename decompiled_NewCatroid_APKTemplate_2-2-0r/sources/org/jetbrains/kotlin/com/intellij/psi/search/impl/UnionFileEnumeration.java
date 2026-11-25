package org.jetbrains.kotlin.com.intellij.psi.search.impl;

import java.util.Collection;
import java.util.Iterator;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.CompactVirtualFileSet;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VfsUtilCore;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileSet;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;

/* JADX INFO: loaded from: classes3.dex */
public final class UnionFileEnumeration implements VirtualFileEnumeration {
    private final Collection<VirtualFileEnumeration> myHints;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 1 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 1 ? 3 : 2];
        if (i != 1) {
            objArr[0] = "hints";
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/search/impl/UnionFileEnumeration";
        }
        if (i != 1) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/search/impl/UnionFileEnumeration";
        } else {
            objArr[1] = "asIterable";
        }
        if (i != 1) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i == 1) {
            throw new IllegalStateException(str2);
        }
    }

    public UnionFileEnumeration(Collection<VirtualFileEnumeration> collection) {
        if (collection == null) {
            $$$reportNull$$$0(0);
        }
        this.myHints = collection;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.search.impl.VirtualFileEnumeration
    public boolean contains(int i) {
        Iterator<VirtualFileEnumeration> it = this.myHints.iterator();
        while (it.hasNext()) {
            if (it.next().contains(i)) {
                return true;
            }
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.search.impl.VirtualFileEnumeration
    public int[] asInts() {
        return ((CompactVirtualFileSet) asIterable()).onlyInternalFileIds();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.search.impl.VirtualFileEnumeration
    public Iterable<VirtualFile> asIterable() {
        VirtualFileSet virtualFileSetCreateCompactVirtualFileSet = VfsUtilCore.createCompactVirtualFileSet();
        Iterator<VirtualFileEnumeration> it = this.myHints.iterator();
        while (it.hasNext()) {
            virtualFileSetCreateCompactVirtualFileSet.addAll(ContainerUtil.toCollection(it.next().asIterable()));
        }
        if (virtualFileSetCreateCompactVirtualFileSet == null) {
            $$$reportNull$$$0(1);
        }
        return virtualFileSetCreateCompactVirtualFileSet;
    }
}
