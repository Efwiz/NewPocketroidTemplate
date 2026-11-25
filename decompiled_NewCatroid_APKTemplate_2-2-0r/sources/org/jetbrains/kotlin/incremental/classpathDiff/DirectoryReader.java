package org.jetbrains.kotlin.incremental.classpathDiff;

import java.io.File;
import java.util.List;
import kotlin.Metadata;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;

/* JADX INFO: compiled from: ClasspathSnapshotter.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0005\u001a\u00020\u0006H\u0016JF\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b26\u0010\n\u001a2\u0012\u0013\u0012\u00110\t¢\u0006\f\b\f\u0012\b\b\r\u0012\u0004\b\b(\u000e\u0012\u0013\u0012\u00110\u000f¢\u0006\f\b\f\u0012\b\b\r\u0012\u0004\b\b(\u0010\u0012\u0004\u0012\u00020\u000f0\u000bH\u0016J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u000e\u001a\u00020\tH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/incremental/classpathDiff/DirectoryReader;", "Lorg/jetbrains/kotlin/incremental/classpathDiff/DirectoryOrJarReader;", "directory", "Ljava/io/File;", "(Ljava/io/File;)V", "close", "", "getUnixStyleRelativePaths", "", "", "filter", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "unixStyleRelativePath", "", "isDirectory", "readBytes", "", "incremental-compilation-impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class DirectoryReader implements DirectoryOrJarReader {
    private final File directory;

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
    }

    public DirectoryReader(File directory) {
        Intrinsics.checkNotNullParameter(directory, "directory");
        this.directory = directory;
    }

    @Override // org.jetbrains.kotlin.incremental.classpathDiff.DirectoryOrJarReader
    public List<String> getUnixStyleRelativePaths(final Function2<? super String, ? super Boolean, Boolean> filter) {
        Intrinsics.checkNotNullParameter(filter, "filter");
        return SequencesKt.toList(SequencesKt.sorted(SequencesKt.map(SequencesKt.filter(FilesKt.walk$default(this.directory, null, 1, null), new Function1<File, Boolean>() { // from class: org.jetbrains.kotlin.incremental.classpathDiff.DirectoryReader.getUnixStyleRelativePaths.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(File it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return filter.invoke(FilesKt.getInvariantSeparatorsPath(FilesKt.relativeTo(it, this.directory)), Boolean.valueOf(it.isDirectory()));
            }
        }), new Function1<File, String>() { // from class: org.jetbrains.kotlin.incremental.classpathDiff.DirectoryReader.getUnixStyleRelativePaths.2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final String invoke(File it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return FilesKt.getInvariantSeparatorsPath(FilesKt.relativeTo(it, DirectoryReader.this.directory));
            }
        })));
    }

    @Override // org.jetbrains.kotlin.incremental.classpathDiff.DirectoryOrJarReader
    public byte[] readBytes(String unixStyleRelativePath) {
        Intrinsics.checkNotNullParameter(unixStyleRelativePath, "unixStyleRelativePath");
        return FilesKt.readBytes(FilesKt.resolve(this.directory, unixStyleRelativePath));
    }
}
