package org.jetbrains.kotlin.com.intellij.psi.search.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import org.jetbrains.kotlin.com.intellij.openapi.util.ModificationTracker;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.CompactVirtualFileSet;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VfsUtilCore;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileSet;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileWithId;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtil;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;
import org.jetbrains.kotlin.com.intellij.util.containers.IntArrayList;

/* JADX INFO: loaded from: classes3.dex */
public final class IntersectionFileEnumeration implements VirtualFileEnumeration {
    private final Collection<VirtualFileEnumeration> myHints;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 2 || i == 3) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 2 || i == 3) ? 2 : 3];
        if (i == 1 || i == 2 || i == 3) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/search/impl/IntersectionFileEnumeration";
        } else {
            objArr[0] = "hints";
        }
        if (i == 1 || i == 2 || i == 3) {
            objArr[1] = "asIterable";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/search/impl/IntersectionFileEnumeration";
        }
        if (i != 1 && i != 2 && i != 3) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 2 && i != 3) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public IntersectionFileEnumeration(Collection<VirtualFileEnumeration> collection) {
        if (collection == null) {
            $$$reportNull$$$0(0);
        }
        this.myHints = collection;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.search.impl.VirtualFileEnumeration
    public boolean contains(int i) {
        if (this.myHints.isEmpty()) {
            return false;
        }
        Iterator<VirtualFileEnumeration> it = this.myHints.iterator();
        while (it.hasNext()) {
            if (!it.next().contains(i)) {
                return false;
            }
        }
        return true;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.search.impl.VirtualFileEnumeration
    public int[] asInts() {
        Iterable<VirtualFile> iterableAsIterable = asIterable();
        if ((iterableAsIterable instanceof Collection) && ((Collection) iterableAsIterable).isEmpty()) {
            return ArrayUtil.EMPTY_INT_ARRAY;
        }
        if (iterableAsIterable instanceof CompactVirtualFileSet) {
            return ((CompactVirtualFileSet) iterableAsIterable).onlyInternalFileIds();
        }
        IntArrayList intArrayList = new IntArrayList();
        for (ModificationTracker modificationTracker : iterableAsIterable) {
            if (modificationTracker instanceof VirtualFileWithId) {
                intArrayList.add(((VirtualFileWithId) modificationTracker).getId());
            }
        }
        return intArrayList.toArray();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.search.impl.VirtualFileEnumeration
    public Iterable<VirtualFile> asIterable() {
        if (this.myHints.isEmpty()) {
            Set setEmptySet = Collections.emptySet();
            if (setEmptySet == null) {
                $$$reportNull$$$0(1);
            }
            return setEmptySet;
        }
        if (this.myHints.size() == 1) {
            Iterable<VirtualFile> iterableAsIterable = this.myHints.iterator().next().asIterable();
            if (iterableAsIterable == null) {
                $$$reportNull$$$0(2);
            }
            return iterableAsIterable;
        }
        Iterator<VirtualFileEnumeration> it = this.myHints.iterator();
        VirtualFileSet virtualFileSetCreateCompactVirtualFileSet = null;
        while (it.hasNext()) {
            Collection<?> collection = ContainerUtil.toCollection(it.next().asIterable());
            if (virtualFileSetCreateCompactVirtualFileSet == null) {
                virtualFileSetCreateCompactVirtualFileSet = VfsUtilCore.createCompactVirtualFileSet(collection);
            } else {
                virtualFileSetCreateCompactVirtualFileSet.retainAll(collection);
            }
        }
        if (virtualFileSetCreateCompactVirtualFileSet == null) {
            $$$reportNull$$$0(3);
        }
        return virtualFileSetCreateCompactVirtualFileSet;
    }
}
