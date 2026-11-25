package org.jetbrains.kotlin.library.impl;

import java.io.IOException;
import java.nio.file.FileSystem;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.konan.file.File;
import org.jetbrains.kotlin.konan.file.FileKt;
import org.jetbrains.kotlin.konan.file.ZipUtilKt;

/* JADX INFO: compiled from: KotlinLibraryLayoutImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0018\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0001H\u0002\u001a\u0018\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0002\u001a\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0001H\u0000\u001a\u0012\u0010\u0000\u001a\u00020\u0001*\u00020\t2\u0006\u0010\u0003\u001a\u00020\u0001\u001a\u0012\u0010\u0004\u001a\u00020\u0001*\u00020\t2\u0006\u0010\u0005\u001a\u00020\u0001¨\u0006\n"}, d2 = {"extract", "Lorg/jetbrains/kotlin/konan/file/File;", "zipFile", "file", "extractDir", "directory", "zippedKotlinLibraryChecks", "", "klibFile", "Lorg/jetbrains/kotlin/library/impl/KotlinLibraryLayoutImpl;", "kotlin-util-klib"}, k = 2, mv = {1, 5, 1}, xi = 48)
public final class KotlinLibraryLayoutImplKt {
    public static final File extract(KotlinLibraryLayoutImpl kotlinLibraryLayoutImpl, File file) {
        Intrinsics.checkNotNullParameter(kotlinLibraryLayoutImpl, "<this>");
        Intrinsics.checkNotNullParameter(file, "file");
        return extract(kotlinLibraryLayoutImpl.getKlib(), file);
    }

    private static final File extract(File file, final File file2) {
        return (File) ZipUtilKt.withZipFileSystem(file, new Function1<FileSystem, File>() { // from class: org.jetbrains.kotlin.library.impl.KotlinLibraryLayoutImplKt.extract.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final File invoke(FileSystem zipFileSystem) throws IOException {
                Intrinsics.checkNotNullParameter(zipFileSystem, "zipFileSystem");
                File fileCreateTempFile$default = FileKt.createTempFile$default(file2.getName(), null, 2, null);
                ZipUtilKt.file(zipFileSystem, file2).copyTo(fileCreateTempFile$default);
                fileCreateTempFile$default.deleteOnExit();
                return fileCreateTempFile$default;
            }
        });
    }

    public static final File extractDir(KotlinLibraryLayoutImpl kotlinLibraryLayoutImpl, File directory) {
        Intrinsics.checkNotNullParameter(kotlinLibraryLayoutImpl, "<this>");
        Intrinsics.checkNotNullParameter(directory, "directory");
        return extractDir(kotlinLibraryLayoutImpl.getKlib(), directory);
    }

    private static final File extractDir(File file, File file2) throws IOException {
        File fileCreateTempDir = FileKt.createTempDir(file2.getName());
        fileCreateTempDir.deleteOnExitRecursively();
        ZipUtilKt.unzipTo$default(file, fileCreateTempDir, file2, false, 4, (Object) null);
        return fileCreateTempDir;
    }

    public static final void zippedKotlinLibraryChecks(File klibFile) {
        Intrinsics.checkNotNullParameter(klibFile, "klibFile");
        if (!klibFile.getExists()) {
            throw new IllegalStateException(("Could not find " + klibFile + '.').toString());
        }
        if (!klibFile.isFile()) {
            throw new IllegalStateException(("Expected " + klibFile + " to be a regular file.").toString());
        }
        String extension = klibFile.getExtension();
        if (extension.length() != 0 && !Intrinsics.areEqual(extension, "klib") && !Intrinsics.areEqual(extension, "jar")) {
            throw new IllegalStateException(("KLIB path has unexpected extension: " + klibFile).toString());
        }
    }
}
