package org.jetbrains.kotlin.incremental.storage;

import java.io.File;
import kotlin.Metadata;
import kotlin.io.FilesKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: RelocatableFileToPathConverter.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u0010\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\u0003H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/incremental/storage/RelocatableFileToPathConverter;", "Lorg/jetbrains/kotlin/incremental/storage/FileToPathConverter;", "baseDir", "Ljava/io/File;", "(Ljava/io/File;)V", "toFile", "path", "", "toPath", "file", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class RelocatableFileToPathConverter implements FileToPathConverter {
    private final File baseDir;

    public RelocatableFileToPathConverter(File baseDir) {
        Intrinsics.checkNotNullParameter(baseDir, "baseDir");
        this.baseDir = baseDir;
    }

    @Override // org.jetbrains.kotlin.incremental.storage.FileToPathConverter
    public String toPath(File file) {
        Intrinsics.checkNotNullParameter(file, "file");
        return FilesKt.getInvariantSeparatorsPath(FilesKt.relativeTo(file, this.baseDir));
    }

    @Override // org.jetbrains.kotlin.incremental.storage.FileToPathConverter
    public File toFile(String path) {
        Intrinsics.checkNotNullParameter(path, "path");
        return FilesKt.normalize(FilesKt.resolve(this.baseDir, path));
    }
}
