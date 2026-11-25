package org.jetbrains.kotlin.com.intellij.psi.search.impl;

import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;

/* JADX INFO: loaded from: classes3.dex */
public interface VirtualFileEnumeration {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "scope", "org/jetbrains/kotlin/com/intellij/psi/search/impl/VirtualFileEnumeration", "extract"));
    }

    int[] asInts();

    Iterable<VirtualFile> asIterable();

    boolean contains(int i);

    /* JADX WARN: Multi-variable type inference failed */
    static VirtualFileEnumeration extract(GlobalSearchScope globalSearchScope) {
        if (globalSearchScope == 0) {
            $$$reportNull$$$0(0);
        }
        if (globalSearchScope instanceof VirtualFileEnumeration) {
            return (VirtualFileEnumeration) globalSearchScope;
        }
        if (globalSearchScope instanceof VirtualFileEnumerationAware) {
            return ((VirtualFileEnumerationAware) globalSearchScope).extractFileEnumeration();
        }
        return null;
    }
}
