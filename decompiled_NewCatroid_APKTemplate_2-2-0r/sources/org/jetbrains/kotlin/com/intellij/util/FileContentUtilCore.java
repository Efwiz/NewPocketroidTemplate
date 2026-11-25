package org.jetbrains.kotlin.com.intellij.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileManager;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.newvfs.BulkFileListener;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.newvfs.events.VFileEvent;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.newvfs.events.VFilePropertyChangeEvent;

/* JADX INFO: loaded from: classes3.dex */
public final class FileContentUtilCore {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "files", "org/jetbrains/kotlin/com/intellij/util/FileContentUtilCore", "reparseFiles"));
    }

    public static void reparseFiles(VirtualFile... virtualFileArr) {
        if (virtualFileArr == null) {
            $$$reportNull$$$0(0);
        }
        reparseFiles(Arrays.asList(virtualFileArr));
    }

    public static void reparseFiles(final Collection<? extends VirtualFile> collection) {
        if (collection == null) {
            $$$reportNull$$$0(1);
        }
        ApplicationManager.getApplication().runWriteAction(new Runnable() { // from class: org.jetbrains.kotlin.com.intellij.util.FileContentUtilCore$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                FileContentUtilCore.lambda$reparseFiles$0(collection);
            }
        });
    }

    static /* synthetic */ void lambda$reparseFiles$0(Collection collection) {
        HashSet hashSet = new HashSet();
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            VirtualFile virtualFile = (VirtualFile) it.next();
            if (virtualFile != null && !virtualFile.isDirectory() && virtualFile.isValid()) {
                hashSet.add(new VFilePropertyChangeEvent("FileContentUtilCore.saveOrReload", virtualFile, "name", virtualFile.getName(), virtualFile.getName(), false));
            }
        }
        BulkFileListener bulkFileListener = (BulkFileListener) ApplicationManager.getApplication().getMessageBus().syncPublisher(VirtualFileManager.VFS_CHANGES);
        List<? extends VFileEvent> listUnmodifiableList = Collections.unmodifiableList(new ArrayList(hashSet));
        bulkFileListener.before(listUnmodifiableList);
        bulkFileListener.after(listUnmodifiableList);
    }
}
