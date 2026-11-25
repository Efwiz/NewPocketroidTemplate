package org.jetbrains.kotlin.incremental;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.io.FilesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;

/* JADX INFO: compiled from: fileUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000 \n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0000\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0002\u001a\n\u0010\u0003\u001a\u00020\u0001*\u00020\u0002\u001a\n\u0010\u0004\u001a\u00020\u0001*\u00020\u0002\u001a\n\u0010\u0005\u001a\u00020\u0006*\u00020\u0002\u001a\n\u0010\u0007\u001a\u00020\u0006*\u00020\u0002\u001a\u0018\u0010\b\u001a\u00020\u0006*\u00020\u00022\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n¨\u0006\f"}, d2 = {"createDirectory", "", "Ljava/io/File;", "deleteDirectoryContents", "deleteRecursivelyOrThrow", "isClassFile", "", "isJavaFile", "isKotlinFile", "sourceFilesExtensions", "", "", "kotlin-build-common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FileUtilsKt {
    public static final boolean isJavaFile(File file) {
        Intrinsics.checkNotNullParameter(file, "<this>");
        return StringsKt.equals(FilesKt.getExtension(file), SuffixConstants.EXTENSION_java, true);
    }

    public static final boolean isKotlinFile(File file, List<String> sourceFilesExtensions) {
        Intrinsics.checkNotNullParameter(file, "<this>");
        Intrinsics.checkNotNullParameter(sourceFilesExtensions, "sourceFilesExtensions");
        if (!isJavaFile(file)) {
            List<String> list = sourceFilesExtensions;
            if (!(list instanceof Collection) || !list.isEmpty()) {
                Iterator<T> it = list.iterator();
                while (it.hasNext()) {
                    if (StringsKt.equals((String) it.next(), FilesKt.getExtension(file), true)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static final boolean isClassFile(File file) {
        Intrinsics.checkNotNullParameter(file, "<this>");
        return StringsKt.equals(FilesKt.getExtension(file), "class", true);
    }

    public static final void deleteDirectoryContents(File file) throws IOException {
        Intrinsics.checkNotNullParameter(file, "<this>");
        if (!file.isDirectory()) {
            if (!file.isFile()) {
                throw new IllegalStateException(("Directory does not exist: " + file.getPath()).toString());
            }
            throw new IllegalStateException(("Expected a directory but found a regular file: " + file.getPath()).toString());
        }
        File[] fileArrListFiles = file.listFiles();
        Intrinsics.checkNotNull(fileArrListFiles);
        for (File file2 : fileArrListFiles) {
            Intrinsics.checkNotNull(file2);
            deleteRecursivelyOrThrow(file2);
        }
    }

    public static final void deleteRecursivelyOrThrow(File file) throws IOException {
        Intrinsics.checkNotNullParameter(file, "<this>");
        if (!FilesKt.deleteRecursively(file)) {
            throw new IOException("Could not delete '" + file.getPath() + '\'');
        }
    }

    public static final void createDirectory(File file) throws IOException {
        Intrinsics.checkNotNullParameter(file, "<this>");
        if (file.isDirectory()) {
            return;
        }
        if (file.isFile()) {
            throw new IllegalStateException(("A regular file already exists at this path: " + file.getPath()).toString());
        }
        if (!file.mkdirs() && !file.isDirectory()) {
            throw new IOException("Could not create directory '" + file.getPath() + '\'');
        }
    }
}
