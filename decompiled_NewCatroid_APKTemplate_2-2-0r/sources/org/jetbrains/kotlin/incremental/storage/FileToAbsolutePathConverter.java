package org.jetbrains.kotlin.incremental.storage;

import java.io.File;
import kotlin.Metadata;
import kotlin.io.FilesKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: FileToPathConverter.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0010\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u0004H\u0016¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/incremental/storage/FileToAbsolutePathConverter;", "Lorg/jetbrains/kotlin/incremental/storage/FileToPathConverter;", "()V", "toFile", "Ljava/io/File;", "path", "", "toPath", "file", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FileToAbsolutePathConverter implements FileToPathConverter {
    public static final FileToAbsolutePathConverter INSTANCE = new FileToAbsolutePathConverter();

    private FileToAbsolutePathConverter() {
    }

    @Override // org.jetbrains.kotlin.incremental.storage.FileToPathConverter
    public String toPath(File file) {
        Intrinsics.checkNotNullParameter(file, "file");
        String absolutePath = FilesKt.normalize(file).getAbsolutePath();
        Intrinsics.checkNotNullExpressionValue(absolutePath, "getAbsolutePath(...)");
        return absolutePath;
    }

    @Override // org.jetbrains.kotlin.incremental.storage.FileToPathConverter
    public File toFile(String path) {
        Intrinsics.checkNotNullParameter(path, "path");
        return new File(path);
    }
}
