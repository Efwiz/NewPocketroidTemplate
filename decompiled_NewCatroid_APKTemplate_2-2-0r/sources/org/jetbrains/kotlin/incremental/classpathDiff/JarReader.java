package org.jetbrains.kotlin.incremental.classpathDiff;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.io.ByteStreamsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;

/* JADX INFO: compiled from: ClasspathSnapshotter.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0007\u001a\u00020\bH\u0016JF\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n26\u0010\f\u001a2\u0012\u0013\u0012\u00110\u000b¢\u0006\f\b\u000e\u0012\b\b\u000f\u0012\u0004\b\b(\u0010\u0012\u0013\u0012\u00110\u0011¢\u0006\f\b\u000e\u0012\b\b\u000f\u0012\u0004\b\b(\u0012\u0012\u0004\u0012\u00020\u00110\rH\u0016J\u0010\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0010\u001a\u00020\u000bH\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/incremental/classpathDiff/JarReader;", "Lorg/jetbrains/kotlin/incremental/classpathDiff/DirectoryOrJarReader;", "jar", "Ljava/io/File;", "(Ljava/io/File;)V", "zipFile", "Ljava/util/zip/ZipFile;", "close", "", "getUnixStyleRelativePaths", "", "", "filter", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "unixStyleRelativePath", "", "isDirectory", "readBytes", "", "incremental-compilation-impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class JarReader implements DirectoryOrJarReader {
    private final ZipFile zipFile;

    public JarReader(File jar) {
        Intrinsics.checkNotNullParameter(jar, "jar");
        this.zipFile = new ZipFile(jar);
    }

    @Override // org.jetbrains.kotlin.incremental.classpathDiff.DirectoryOrJarReader
    public List<String> getUnixStyleRelativePaths(final Function2<? super String, ? super Boolean, Boolean> filter) {
        Intrinsics.checkNotNullParameter(filter, "filter");
        Enumeration<? extends ZipEntry> enumerationEntries = this.zipFile.entries();
        Intrinsics.checkNotNullExpressionValue(enumerationEntries, "entries(...)");
        Sequence sequenceFilter = SequencesKt.filter(SequencesKt.asSequence(CollectionsKt.iterator(enumerationEntries)), new Function1<ZipEntry, Boolean>() { // from class: org.jetbrains.kotlin.incremental.classpathDiff.JarReader.getUnixStyleRelativePaths.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(ZipEntry zipEntry) {
                Function2<String, Boolean, Boolean> function2 = filter;
                String name2 = zipEntry.getName();
                Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                return function2.invoke(name2, Boolean.valueOf(zipEntry.isDirectory()));
            }
        });
        TreeSet treeSetSortedSetOf = SetsKt.sortedSetOf(new String[0]);
        Iterator it = sequenceFilter.iterator();
        while (it.hasNext()) {
            treeSetSortedSetOf.add(((ZipEntry) it.next()).getName());
        }
        return CollectionsKt.toList(treeSetSortedSetOf);
    }

    @Override // org.jetbrains.kotlin.incremental.classpathDiff.DirectoryOrJarReader
    public byte[] readBytes(String unixStyleRelativePath) throws Exception {
        Intrinsics.checkNotNullParameter(unixStyleRelativePath, "unixStyleRelativePath");
        ZipFile zipFile = this.zipFile;
        InputStream inputStream = zipFile.getInputStream(zipFile.getEntry(unixStyleRelativePath));
        boolean z = false;
        try {
            InputStream inputStream2 = inputStream;
            Intrinsics.checkNotNull(inputStream2);
            byte[] bytes = ByteStreamsKt.readBytes(inputStream2);
            if (inputStream != null) {
                inputStream.close();
            }
            return bytes;
        } catch (Exception e) {
            if (inputStream != null) {
                try {
                    try {
                        inputStream.close();
                    } catch (Throwable th) {
                        th = th;
                        z = true;
                        if (!z && inputStream != null) {
                            inputStream.close();
                        }
                        throw th;
                    }
                } catch (Exception unused) {
                }
            }
            throw e;
        } catch (Throwable th2) {
            th = th2;
            if (!z) {
                inputStream.close();
            }
            throw th;
        }
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.zipFile.close();
    }
}
