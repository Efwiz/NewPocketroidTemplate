package org.jetbrains.kotlin.cli.js;

import androidx.exifinterface.media.ExifInterface;
import java.nio.file.FileSystem;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.openapi.Disposable;
import org.jetbrains.kotlin.konan.file.File;
import org.jetbrains.kotlin.konan.file.ZipFileSystemAccessor;
import org.jetbrains.kotlin.konan.file.ZipFileSystemCacheableAccessor;

/* JADX INFO: compiled from: K2JsIrCompiler.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u00012\u00020\u0002B\u000f\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005B\u000f\b\u0002\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\b\u0010\t\u001a\u00020\nH\u0016J0\u0010\u000b\u001a\u0002H\f\"\u0004\b\u0000\u0010\f2\u0006\u0010\r\u001a\u00020\u000e2\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u0002H\f0\u0010H\u0096\u0001¢\u0006\u0002\u0010\u0012R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/cli/js/DisposableZipFileSystemAccessor;", "Lorg/jetbrains/kotlin/com/intellij/openapi/Disposable;", "Lorg/jetbrains/kotlin/konan/file/ZipFileSystemAccessor;", "cacheLimit", "", "(I)V", "zipAccessor", "Lorg/jetbrains/kotlin/konan/file/ZipFileSystemCacheableAccessor;", "(Lorg/jetbrains/kotlin/konan/file/ZipFileSystemCacheableAccessor;)V", "dispose", "", "withZipFileSystem", ExifInterface.GPS_DIRECTION_TRUE, "zipFile", "Lorg/jetbrains/kotlin/konan/file/File;", "action", "Lkotlin/Function1;", "Ljava/nio/file/FileSystem;", "(Lorg/jetbrains/kotlin/konan/file/File;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "cli-js"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class DisposableZipFileSystemAccessor implements Disposable, ZipFileSystemAccessor {
    private final ZipFileSystemCacheableAccessor zipAccessor;

    @Override // org.jetbrains.kotlin.konan.file.ZipFileSystemAccessor
    public <T> T withZipFileSystem(File zipFile, Function1<? super FileSystem, ? extends T> action) {
        Intrinsics.checkNotNullParameter(zipFile, "zipFile");
        Intrinsics.checkNotNullParameter(action, "action");
        return (T) this.zipAccessor.withZipFileSystem(zipFile, action);
    }

    private DisposableZipFileSystemAccessor(ZipFileSystemCacheableAccessor zipFileSystemCacheableAccessor) {
        this.zipAccessor = zipFileSystemCacheableAccessor;
    }

    public DisposableZipFileSystemAccessor(int i) {
        this(new ZipFileSystemCacheableAccessor(i));
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.Disposable
    public void dispose() throws Exception {
        this.zipAccessor.reset();
    }
}
