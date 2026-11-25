package org.jetbrains.kotlin.com.intellij.openapi.vfs;

import java.nio.file.Path;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.kotlin.com.intellij.openapi.application.CachedSingletonsRegistry;
import org.jetbrains.kotlin.com.intellij.openapi.util.ModificationTracker;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.newvfs.BulkFileListener;
import org.jetbrains.kotlin.com.intellij.util.io.URLUtil;
import org.jetbrains.kotlin.com.intellij.util.messages.Topic;

/* JADX INFO: loaded from: classes3.dex */
public abstract class VirtualFileManager implements ModificationTracker {
    public static final Topic<BulkFileListener> VFS_CHANGES = new Topic<>(BulkFileListener.class, Topic.BroadcastDirection.TO_DIRECT_CHILDREN);
    public static final ModificationTracker VFS_STRUCTURE_MODIFICATIONS = new ModificationTracker() { // from class: org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileManager$$ExternalSyntheticLambda0
        @Override // org.jetbrains.kotlin.com.intellij.openapi.util.ModificationTracker
        public final long getModificationCount() {
            return VirtualFileManager.getInstance().getStructureModificationCount();
        }
    };
    private static VirtualFileManager ourInstance = (VirtualFileManager) CachedSingletonsRegistry.markCachedField(VirtualFileManager.class);

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 8:
            case 9:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
            case 7:
            default:
                str = "@NotNull method %s.%s must not return null";
                break;
        }
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 8:
            case 9:
                i2 = 3;
                break;
            case 7:
            default:
                i2 = 2;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
            case 3:
            case 8:
            case 9:
                objArr[0] = "url";
                break;
            case 2:
            case 4:
            case 6:
                objArr[0] = "path";
                break;
            case 5:
                objArr[0] = ConfigConstants.CONFIG_PROTOCOL_SECTION;
                break;
            case 7:
            default:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFileManager";
                break;
        }
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 8:
            case 9:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFileManager";
                break;
            case 7:
                objArr[1] = "constructUrl";
                break;
            case 10:
                objArr[1] = "extractPath";
                break;
            default:
                objArr[1] = "getInstance";
                break;
        }
        switch (i) {
            case 1:
                objArr[2] = "findFileByUrl";
                break;
            case 2:
                objArr[2] = "findFileByNioPath";
                break;
            case 3:
                objArr[2] = "refreshAndFindFileByUrl";
                break;
            case 4:
                objArr[2] = "refreshAndFindFileByNioPath";
                break;
            case 5:
            case 6:
                objArr[2] = "constructUrl";
                break;
            case 8:
                objArr[2] = "extractProtocol";
                break;
            case 9:
                objArr[2] = "extractPath";
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 8:
            case 9:
                throw new IllegalArgumentException(str2);
            case 7:
            default:
                throw new IllegalStateException(str2);
        }
    }

    public VirtualFile findFileById(int i) {
        return null;
    }

    public VirtualFile findFileByNioPath(Path path) {
        if (path != null) {
            return null;
        }
        $$$reportNull$$$0(2);
        return null;
    }

    public VirtualFile findFileByUrl(String str) {
        if (str != null) {
            return null;
        }
        $$$reportNull$$$0(1);
        return null;
    }

    public abstract VirtualFileSystem getFileSystem(String str);

    public abstract long getStructureModificationCount();

    public abstract CharSequence getVFileName(int i);

    public abstract void notifyPropertyChanged(VirtualFile virtualFile, String str, Object obj, Object obj2);

    public static VirtualFileManager getInstance() {
        VirtualFileManager virtualFileManager = ourInstance;
        if (virtualFileManager == null) {
            virtualFileManager = (VirtualFileManager) ApplicationManager.getApplication().getService(VirtualFileManager.class);
            ourInstance = virtualFileManager;
        }
        if (virtualFileManager == null) {
            $$$reportNull$$$0(0);
        }
        return virtualFileManager;
    }

    public static String constructUrl(String str, String str2) {
        if (str == null) {
            $$$reportNull$$$0(5);
        }
        if (str2 == null) {
            $$$reportNull$$$0(6);
        }
        String str3 = str + "://" + str2;
        if (str3 == null) {
            $$$reportNull$$$0(7);
        }
        return str3;
    }

    public static String extractPath(String str) {
        if (str == null) {
            $$$reportNull$$$0(9);
        }
        String strExtractPath = URLUtil.extractPath(str);
        if (strExtractPath == null) {
            $$$reportNull$$$0(10);
        }
        return strExtractPath;
    }
}
