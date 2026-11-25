package org.jetbrains.kotlin.com.intellij.openapi.roots;

import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;

/* JADX INFO: loaded from: classes3.dex */
@FunctionalInterface
public interface ContentIteratorEx extends ContentIterator {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "fileOrDir", "org/jetbrains/kotlin/com/intellij/openapi/roots/ContentIteratorEx", "processFile"));
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.roots.ContentIterator
    default boolean processFile(VirtualFile virtualFile) {
        if (virtualFile == null) {
            $$$reportNull$$$0(0);
        }
        throw new IllegalStateException("Call com.intellij.openapi.roots.ContentIteratorEx#processFileEx instead");
    }
}
