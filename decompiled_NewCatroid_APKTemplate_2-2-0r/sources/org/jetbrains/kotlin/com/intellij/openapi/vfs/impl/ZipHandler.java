package org.jetbrains.kotlin.com.intellij.openapi.vfs.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.zip.ZipFile;
import org.jetbrains.kotlin.com.intellij.util.io.FileAccessorCache;
import org.jetbrains.kotlin.com.intellij.util.io.ResourceHandle;

/* JADX INFO: loaded from: classes3.dex */
public class ZipHandler extends ZipHandlerBase {
    private static final FileAccessorCache<ZipHandler, ZipFile> ourZipFileFileAccessorCache = new FileAccessorCache<ZipHandler, ZipFile>(20, 10) { // from class: org.jetbrains.kotlin.com.intellij.openapi.vfs.impl.ZipHandler.1
        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "fileAccessor", "org/jetbrains/kotlin/com/intellij/openapi/vfs/impl/ZipHandler$1", "disposeAccessor"));
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.io.FileAccessorCache, org.jetbrains.kotlin.com.intellij.util.containers.hash.EqualityPolicy
        public boolean isEqual(ZipHandler zipHandler, ZipHandler zipHandler2) {
            return zipHandler == zipHandler2;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // org.jetbrains.kotlin.com.intellij.util.io.FileAccessorCache
        public ZipFile createAccessor(ZipHandler zipHandler) throws IOException {
            File file = zipHandler.getFile();
            BasicFileAttributes attributes = Files.readAttributes(file.toPath(), (Class<BasicFileAttributes>) BasicFileAttributes.class, new LinkOption[0]);
            zipHandler.myFileStamp = attributes.lastModifiedTime().toMillis();
            zipHandler.myFileLength = attributes.size();
            return new ZipFile(file);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // org.jetbrains.kotlin.com.intellij.util.io.FileAccessorCache
        public void disposeAccessor(ZipFile zipFile) throws IOException {
            if (zipFile == null) {
                $$$reportNull$$$0(0);
            }
            zipFile.close();
        }
    };
    private volatile long myFileLength;
    private volatile long myFileStamp;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 1 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 1 ? 3 : 2];
        if (i != 1) {
            objArr[0] = "path";
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/vfs/impl/ZipHandler";
        }
        if (i != 1) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/vfs/impl/ZipHandler";
        } else {
            objArr[1] = "acquireZipHandle";
        }
        if (i != 1) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i == 1) {
            throw new IllegalStateException(str2);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ZipHandler(String str) {
        super(str);
        if (str == null) {
            $$$reportNull$$$0(0);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.impl.ZipHandlerBase
    protected ResourceHandle<ZipFile> acquireZipHandle() throws IOException {
        try {
            FileAccessorCache<ZipHandler, ZipFile> fileAccessorCache = ourZipFileFileAccessorCache;
            FileAccessorCache.Handle<ZipFile> handle = fileAccessorCache.get(this);
            BasicFileAttributes attributes = Files.readAttributes(getFile().toPath(), (Class<BasicFileAttributes>) BasicFileAttributes.class, new LinkOption[0]);
            if (attributes.lastModifiedTime().toMillis() != this.myFileStamp || attributes.size() != this.myFileLength) {
                clearCaches();
                handle.release();
                handle = fileAccessorCache.get(this);
            }
            if (handle == null) {
                $$$reportNull$$$0(1);
            }
            return handle;
        } catch (RuntimeException e) {
            Throwable cause = e.getCause();
            if (cause instanceof IOException) {
                throw ((IOException) cause);
            }
            throw e;
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.impl.ArchiveHandler
    public void clearCaches() {
        ourZipFileFileAccessorCache.remove(this);
        super.clearCaches();
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.impl.ZipHandlerBase
    protected long getEntryFileStamp() {
        return this.myFileStamp;
    }

    public static void clearFileAccessorCache() {
        ourZipFileFileAccessorCache.clear();
    }
}
