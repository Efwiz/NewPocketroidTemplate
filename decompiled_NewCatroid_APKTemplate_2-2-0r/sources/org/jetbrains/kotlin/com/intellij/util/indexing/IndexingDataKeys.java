package org.jetbrains.kotlin.com.intellij.util.indexing;

import org.jetbrains.kotlin.com.intellij.openapi.util.Key;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;

/* JADX INFO: loaded from: classes3.dex */
public final class IndexingDataKeys {
    public static final Key<VirtualFile> VIRTUAL_FILE = new Key<>("Context virtual file");
    public static final Key<PsiFile> PSI_FILE = new Key<>("PSI for stubs");
    public static final Key<CharSequence> FILE_TEXT_CONTENT_KEY = Key.create("file text content cached by stub indexer");
    public static final Key<Boolean> REBUILD_REQUESTED = Key.create("index.rebuild.requested");
}
