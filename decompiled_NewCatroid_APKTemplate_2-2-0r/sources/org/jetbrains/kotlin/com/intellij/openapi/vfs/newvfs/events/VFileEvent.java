package org.jetbrains.kotlin.com.intellij.openapi.vfs.newvfs.events;

import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileSystem;

/* JADX INFO: loaded from: classes3.dex */
public abstract class VFileEvent {
    private final boolean myIsFromRefresh;
    private final Object myRequestor;

    public abstract VirtualFile getFile();

    public abstract VirtualFileSystem getFileSystem();

    public VFileEvent(Object obj, boolean z) {
        this.myRequestor = obj;
        this.myIsFromRefresh = z;
    }

    public Object getRequestor() {
        return this.myRequestor;
    }
}
