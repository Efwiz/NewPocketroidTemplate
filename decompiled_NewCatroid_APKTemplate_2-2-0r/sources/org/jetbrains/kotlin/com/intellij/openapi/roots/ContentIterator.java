package org.jetbrains.kotlin.com.intellij.openapi.roots;

import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;

/* JADX INFO: loaded from: classes3.dex */
@FunctionalInterface
public interface ContentIterator {
    boolean processFile(VirtualFile virtualFile);
}
