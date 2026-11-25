package org.jetbrains.kotlin.konan.file;

import androidx.exifinterface.media.ExifInterface;
import java.nio.file.FileSystem;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ZipFileSystemAccessor.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J/\u0010\u0003\u001a\u0002H\u0004\"\u0004\b\u0000\u0010\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0012\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u0002H\u00040\bH\u0016¢\u0006\u0002\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/konan/file/ZipFileSystemInPlaceAccessor;", "Lorg/jetbrains/kotlin/konan/file/ZipFileSystemAccessor;", "()V", "withZipFileSystem", ExifInterface.GPS_DIRECTION_TRUE, "zipFile", "Lorg/jetbrains/kotlin/konan/file/File;", "action", "Lkotlin/Function1;", "Ljava/nio/file/FileSystem;", "(Lorg/jetbrains/kotlin/konan/file/File;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "kotlin-util-io"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class ZipFileSystemInPlaceAccessor implements ZipFileSystemAccessor {
    public static final ZipFileSystemInPlaceAccessor INSTANCE = new ZipFileSystemInPlaceAccessor();

    private ZipFileSystemInPlaceAccessor() {
    }

    @Override // org.jetbrains.kotlin.konan.file.ZipFileSystemAccessor
    public <T> T withZipFileSystem(File zipFile, Function1<? super FileSystem, ? extends T> action) {
        Intrinsics.checkNotNullParameter(zipFile, "zipFile");
        Intrinsics.checkNotNullParameter(action, "action");
        return (T) ZipUtilKt.withZipFileSystem(zipFile, action);
    }
}
