package org.jetbrains.kotlin.konan.file;

import androidx.exifinterface.media.ExifInterface;
import java.nio.file.FileSystem;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ZipFileSystemAccessor.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00009\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0000\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002*\u0001\t\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0006\u0010\u000b\u001a\u00020\fJ/\u0010\r\u001a\u0002H\u000e\"\u0004\b\u0000\u0010\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0012\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u0002H\u000e0\u0012H\u0016¢\u0006\u0002\u0010\u0014R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082D¢\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\n¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/konan/file/ZipFileSystemCacheableAccessor;", "Lorg/jetbrains/kotlin/konan/file/ZipFileSystemAccessor;", "cacheLimit", "", "(I)V", "initialCapacity", "loadFactor", "", "openedFileSystems", "org/jetbrains/kotlin/konan/file/ZipFileSystemCacheableAccessor$openedFileSystems$1", "Lorg/jetbrains/kotlin/konan/file/ZipFileSystemCacheableAccessor$openedFileSystems$1;", "reset", "", "withZipFileSystem", ExifInterface.GPS_DIRECTION_TRUE, "zipFile", "Lorg/jetbrains/kotlin/konan/file/File;", "action", "Lkotlin/Function1;", "Ljava/nio/file/FileSystem;", "(Lorg/jetbrains/kotlin/konan/file/File;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "kotlin-util-io"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class ZipFileSystemCacheableAccessor implements ZipFileSystemAccessor {
    private final int cacheLimit;
    private final int initialCapacity;
    private final float loadFactor = 0.75f;
    private final ZipFileSystemCacheableAccessor$openedFileSystems$1 openedFileSystems;

    public ZipFileSystemCacheableAccessor(int i) {
        this.cacheLimit = i;
        int i2 = (int) ((i / 0.75f) + 1.0f);
        this.initialCapacity = i2;
        this.openedFileSystems = new ZipFileSystemCacheableAccessor$openedFileSystems$1(this, i2, 0.75f);
    }

    @Override // org.jetbrains.kotlin.konan.file.ZipFileSystemAccessor
    public <T> T withZipFileSystem(File zipFile, Function1<? super FileSystem, ? extends T> action) {
        Intrinsics.checkNotNullParameter(zipFile, "zipFile");
        Intrinsics.checkNotNullParameter(action, "action");
        ZipFileSystemCacheableAccessor$openedFileSystems$1 zipFileSystemCacheableAccessor$openedFileSystems$1 = this.openedFileSystems;
        FileSystem fileSystemZipFileSystem$default = zipFileSystemCacheableAccessor$openedFileSystems$1.get((Object) zipFile);
        if (fileSystemZipFileSystem$default == null) {
            fileSystemZipFileSystem$default = ZipUtilKt.zipFileSystem$default(zipFile, false, 1, null);
            zipFileSystemCacheableAccessor$openedFileSystems$1.put(zipFile, fileSystemZipFileSystem$default);
        }
        return action.invoke(fileSystemZipFileSystem$default);
    }

    public final void reset() throws Exception {
        Iterator<FileSystem> it = this.openedFileSystems.values().iterator();
        Exception e = null;
        while (it.hasNext()) {
            try {
                it.next().close();
            } catch (Exception e2) {
                e = e2;
            }
        }
        this.openedFileSystems.clear();
        if (e != null) {
            throw e;
        }
    }
}
