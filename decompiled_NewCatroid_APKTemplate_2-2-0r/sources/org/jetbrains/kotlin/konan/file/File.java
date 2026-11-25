package org.jetbrains.kotlin.konan.file;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.Writer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.DirectoryStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileAttribute;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import kotlin.text.StringsKt;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.util.UtilKt;

/* JADX INFO: compiled from: File.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000|\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0013\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0010 \n\u0002\b\r\n\u0002\u0010\u0012\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u001c\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\b\t\b\u0086\b\u0018\u0000 g2\u00020\u0001:\u0001gB\u0017\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006B\u0017\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0000\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0007B\u0017\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0000\u0012\u0006\u0010\u0004\u001a\u00020\u0000¢\u0006\u0002\u0010\bB\u000f\b\u0016\u0012\u0006\u0010\t\u001a\u00020\u0005¢\u0006\u0002\u0010\nB\u0017\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0005\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u000bB\r\u0012\u0006\u0010\f\u001a\u00020\u0003¢\u0006\u0002\u0010\rJ\u0016\u0010/\u001a\n 0*\u0004\u0018\u00010\u00030\u00032\u0006\u00101\u001a\u000202J\u0014\u00103\u001a\u0002042\f\u00105\u001a\b\u0012\u0004\u0012\u00020\u000506J\u000e\u00107\u001a\u0002042\u0006\u00108\u001a\u00020\u0005J\u000e\u00109\u001a\n 0*\u0004\u0018\u00010:0:J\u000e\u0010\u0004\u001a\u00020\u00002\u0006\u0010)\u001a\u00020\u0005J\u000e\u0010;\u001a\u00020\u0003HÀ\u0003¢\u0006\u0002\b<J\u0013\u0010=\u001a\u00020\u00002\b\b\u0002\u0010\f\u001a\u00020\u0003HÆ\u0001J\u000e\u0010>\u001a\u0002042\u0006\u0010?\u001a\u00020\u0000J\u000e\u0010@\u001a\u0002042\u0006\u0010A\u001a\u00020\u0005J\u0006\u0010B\u001a\u00020\u0019J\u0006\u0010C\u001a\u00020\u0019J\u0006\u0010D\u001a\u00020\u0000J\u0006\u0010E\u001a\u000204J\u0006\u0010F\u001a\u000204J\u0013\u0010G\u001a\u00020\u00192\b\u0010H\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\u001a\u0010I\u001a\u0002042\u0012\u0010J\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002040KJ\b\u0010L\u001a\u00020MH\u0016J$\u0010N\u001a\u00020O2\b\b\u0002\u0010P\u001a\u00020Q2\b\b\u0002\u0010R\u001a\u00020S2\b\b\u0002\u0010T\u001a\u00020SJ\u000e\u0010U\u001a\n 0*\u0004\u0018\u00010\u00030\u0003J\u000e\u0010V\u001a\n 0*\u0004\u0018\u00010W0WJ\u001a\u0010X\u001a\u0002042\u0012\u0010Y\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u0002040KJ\u001a\u0010Z\u001a\u0002042\u0012\u0010Y\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u0002040KJ\u0006\u0010[\u001a\u00020\\J\u000e\u0010]\u001a\n 0*\u0004\u0018\u00010202J\f\u0010^\u001a\b\u0012\u0004\u0012\u00020\u00050_J\u000e\u0010`\u001a\u00020\u00192\u0006\u0010?\u001a\u00020\u0000J\u000e\u0010a\u001a\u00020\u00192\u0006\u0010b\u001a\u00020\u0000J\b\u0010c\u001a\u00020\u0005H\u0016J\u0016\u0010d\u001a\n 0*\u0004\u0018\u00010\u00030\u00032\u0006\u00101\u001a\u000202J\u0014\u0010e\u001a\u0002042\f\u00105\u001a\b\u0012\u0004\u0012\u00020\u000506J\u000e\u0010f\u001a\u0002042\u0006\u00108\u001a\u00020\u0005R\u0011\u0010\u000e\u001a\u00020\u00008F¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0011\u001a\u00020\u00058F¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\u0014\u001a\u00020\u00008F¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0010R\u0011\u0010\u0016\u001a\u00020\u00058F¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0013R\u0011\u0010\u0018\u001a\u00020\u00198F¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u001bR\u0011\u0010\u001c\u001a\u00020\u00058F¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u0013R\u0011\u0010\u001e\u001a\u00020\u00198F¢\u0006\u0006\u001a\u0004\b\u001e\u0010\u001bR\u0011\u0010\u001f\u001a\u00020\u00198F¢\u0006\u0006\u001a\u0004\b\u001f\u0010\u001bR\u0011\u0010 \u001a\u00020\u00198F¢\u0006\u0006\u001a\u0004\b \u0010\u001bR\u0014\u0010\f\u001a\u00020\u0003X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\"R\u0017\u0010#\u001a\b\u0012\u0004\u0012\u00020\u00000$8F¢\u0006\u0006\u001a\u0004\b%\u0010&R\u0017\u0010'\u001a\b\u0012\u0004\u0012\u00020\u00000$8F¢\u0006\u0006\u001a\u0004\b(\u0010&R\u0011\u0010)\u001a\u00020\u00058F¢\u0006\u0006\u001a\u0004\b*\u0010\u0013R\u0011\u0010\u0002\u001a\u00020\u00058F¢\u0006\u0006\u001a\u0004\b+\u0010\u0013R\u0011\u0010,\u001a\u00020\u00008F¢\u0006\u0006\u001a\u0004\b-\u0010\u0010R\u0011\u0010\t\u001a\u00020\u00058F¢\u0006\u0006\u001a\u0004\b.\u0010\u0013¨\u0006h"}, d2 = {"Lorg/jetbrains/kotlin/konan/file/File;", "", "parent", "Ljava/nio/file/Path;", "child", "", "(Ljava/nio/file/Path;Ljava/lang/String;)V", "(Lorg/jetbrains/kotlin/konan/file/File;Ljava/lang/String;)V", "(Lorg/jetbrains/kotlin/konan/file/File;Lorg/jetbrains/kotlin/konan/file/File;)V", "path", "(Ljava/lang/String;)V", "(Ljava/lang/String;Ljava/lang/String;)V", "javaPath", "(Ljava/nio/file/Path;)V", "absoluteFile", "getAbsoluteFile", "()Lorg/jetbrains/kotlin/konan/file/File;", "absolutePath", "getAbsolutePath", "()Ljava/lang/String;", "canonicalFile", "getCanonicalFile", "canonicalPath", "getCanonicalPath", "exists", "", "getExists", "()Z", SchemaSymbols.ATTVAL_EXTENSION, "getExtension", "isAbsolute", "isDirectory", "isFile", "getJavaPath$kotlin_util_io", "()Ljava/nio/file/Path;", "listFiles", "", "getListFiles", "()Ljava/util/List;", "listFilesOrEmpty", "getListFilesOrEmpty", "name", "getName", "getParent", "parentFile", "getParentFile", "getPath", "appendBytes", JvmProtoBufUtil.PLATFORM_TYPE_ID, "bytes", "", "appendLines", "", "lines", "", "appendText", "text", "bufferedReader", "Ljava/io/BufferedReader;", "component1", "component1$kotlin_util_io", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "copyTo", FirebaseAnalytics.Param.DESTINATION, "createAsSymlink", "target", "createNew", "delete", "deleteOnExit", "deleteOnExitRecursively", "deleteRecursively", Namer.EQUALS_METHOD_NAME, "other", "forEachLine", "action", "Lkotlin/Function1;", "hashCode", "", "map", "Ljava/nio/MappedByteBuffer;", "mode", "Ljava/nio/channels/FileChannel$MapMode;", "start", "", "size", "mkdirs", "outputStream", "Ljava/io/OutputStream;", "postorder", "task", "preorder", "printWriter", "Ljava/io/PrintWriter;", "readBytes", "readStrings", "", "renameTo", "startsWith", "another", "toString", "writeBytes", "writeLines", "writeText", "Companion", "kotlin-util-io"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final /* data */ class File {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final String pathSeparator = java.io.File.pathSeparator;
    private static final String separator = java.io.File.separator;
    private static final char separatorChar = java.io.File.separatorChar;
    private final Path javaPath;

    public static /* synthetic */ File copy$default(File file, Path path, int i, Object obj) {
        if ((i & 1) != 0) {
            path = file.javaPath;
        }
        return file.copy(path);
    }

    /* JADX INFO: renamed from: component1$kotlin_util_io, reason: from getter */
    public final Path getJavaPath() {
        return this.javaPath;
    }

    public final File copy(Path javaPath) {
        Intrinsics.checkNotNullParameter(javaPath, "javaPath");
        return new File(javaPath);
    }

    public File(Path javaPath) {
        Intrinsics.checkNotNullParameter(javaPath, "javaPath");
        this.javaPath = javaPath;
    }

    public final Path getJavaPath$kotlin_util_io() {
        return this.javaPath;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public File(Path parent, String child) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        Intrinsics.checkNotNullParameter(child, "child");
        Path pathResolve = parent.resolve(child);
        Intrinsics.checkNotNullExpressionValue(pathResolve, "parent.resolve(child)");
        this(pathResolve);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public File(File parent, String child) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        Intrinsics.checkNotNullParameter(child, "child");
        Path pathResolve = parent.javaPath.resolve(child);
        Intrinsics.checkNotNullExpressionValue(pathResolve, "parent.javaPath.resolve(child)");
        this(pathResolve);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public File(File parent, File child) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        Intrinsics.checkNotNullParameter(child, "child");
        Path pathResolve = parent.javaPath.resolve(child.javaPath);
        Intrinsics.checkNotNullExpressionValue(pathResolve, "parent.javaPath.resolve(child.javaPath)");
        this(pathResolve);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public File(String path) {
        Intrinsics.checkNotNullParameter(path, "path");
        Path path2 = Paths.get(path, new String[0]);
        Intrinsics.checkNotNullExpressionValue(path2, "get(path)");
        this(path2);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public File(String parent, String child) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        Intrinsics.checkNotNullParameter(child, "child");
        Path path = Paths.get(parent, child);
        Intrinsics.checkNotNullExpressionValue(path, "get(parent, child)");
        this(path);
    }

    public final String getPath() {
        return this.javaPath.toString();
    }

    public final String getAbsolutePath() {
        return this.javaPath.toAbsolutePath().toString();
    }

    public final File getAbsoluteFile() {
        return new File(getAbsolutePath());
    }

    public final String getCanonicalPath() throws IOException {
        String canonicalPath = this.javaPath.toFile().getCanonicalPath();
        Intrinsics.checkNotNullExpressionValue(canonicalPath, "javaPath.toFile().canonicalPath");
        return canonicalPath;
    }

    public final File getCanonicalFile() {
        return new File(getCanonicalPath());
    }

    public final String getName() {
        String string = this.javaPath.getFileName().toString();
        String separator2 = separator;
        Intrinsics.checkNotNullExpressionValue(separator2, "separator");
        return UtilKt.removeSuffixIfPresent(string, separator2);
    }

    public final String getExtension() {
        return StringsKt.substringAfterLast(getName(), '.', "");
    }

    public final String getParent() {
        return this.javaPath.getParent().toString();
    }

    public final File getParentFile() {
        Path parent = this.javaPath.getParent();
        Intrinsics.checkNotNullExpressionValue(parent, "javaPath.parent");
        return new File(parent);
    }

    public final boolean getExists() {
        return Files.exists(this.javaPath, new LinkOption[0]);
    }

    public final boolean isDirectory() {
        return Files.isDirectory(this.javaPath, new LinkOption[0]);
    }

    public final boolean isFile() {
        return Files.isRegularFile(this.javaPath, new LinkOption[0]);
    }

    public final boolean isAbsolute() {
        return this.javaPath.isAbsolute();
    }

    public final List<File> getListFiles() throws Exception {
        DirectoryStream<Path> directoryStreamNewDirectoryStream = Files.newDirectoryStream(this.javaPath);
        boolean z = false;
        try {
            DirectoryStream<Path> stream = directoryStreamNewDirectoryStream;
            Intrinsics.checkNotNullExpressionValue(stream, "stream");
            DirectoryStream<Path> directoryStream = stream;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(directoryStream, 10));
            Iterator<Path> it = directoryStream.iterator();
            while (it.hasNext()) {
                arrayList.add(new File(it.next()));
            }
            ArrayList arrayList2 = arrayList;
            if (directoryStreamNewDirectoryStream != null) {
                directoryStreamNewDirectoryStream.close();
            }
            return arrayList2;
        } catch (Exception e) {
            if (directoryStreamNewDirectoryStream != null) {
                try {
                    try {
                        directoryStreamNewDirectoryStream.close();
                    } catch (Exception unused) {
                    }
                } catch (Throwable th) {
                    th = th;
                    z = true;
                    if (!z && directoryStreamNewDirectoryStream != null) {
                        directoryStreamNewDirectoryStream.close();
                    }
                    throw th;
                }
            }
            throw e;
        } catch (Throwable th2) {
            th = th2;
            if (!z) {
                directoryStreamNewDirectoryStream.close();
            }
            throw th;
        }
    }

    public final List<File> getListFilesOrEmpty() {
        return getExists() ? getListFiles() : CollectionsKt.emptyList();
    }

    public final File child(String name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        return new File(this, name2);
    }

    public final boolean startsWith(File another) {
        Intrinsics.checkNotNullParameter(another, "another");
        return this.javaPath.startsWith(another.javaPath);
    }

    public final void copyTo(File destination) throws IOException {
        Intrinsics.checkNotNullParameter(destination, "destination");
        Files.copy(this.javaPath, destination.javaPath, StandardCopyOption.REPLACE_EXISTING);
    }

    public final boolean renameTo(File destination) {
        Intrinsics.checkNotNullParameter(destination, "destination");
        return this.javaPath.toFile().renameTo(destination.javaPath.toFile());
    }

    public final Path mkdirs() {
        return Files.createDirectories(this.javaPath, new FileAttribute[0]);
    }

    public final boolean delete() {
        return Files.deleteIfExists(this.javaPath);
    }

    public final void deleteRecursively() throws IOException {
        postorder(new Function1<Path, Unit>() { // from class: org.jetbrains.kotlin.konan.file.File.deleteRecursively.1
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Path path) throws IOException {
                invoke2(path);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Path it) throws IOException {
                Intrinsics.checkNotNullParameter(it, "it");
                Files.delete(it);
            }
        });
    }

    public final void deleteOnExitRecursively() throws IOException {
        preorder(new Function1<Path, Unit>() { // from class: org.jetbrains.kotlin.konan.file.File.deleteOnExitRecursively.1
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Path path) {
                invoke2(path);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Path it) {
                Intrinsics.checkNotNullParameter(it, "it");
                new File(it).deleteOnExit();
            }
        });
    }

    public final void preorder(final Function1<? super Path, Unit> task) throws IOException {
        Intrinsics.checkNotNullParameter(task, "task");
        if (getExists()) {
            Files.walkFileTree(this.javaPath, new SimpleFileVisitor<Path>() { // from class: org.jetbrains.kotlin.konan.file.File.preorder.1
                @Override // java.nio.file.SimpleFileVisitor, java.nio.file.FileVisitor
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    Function1<Path, Unit> function1 = task;
                    Intrinsics.checkNotNull(file);
                    function1.invoke(file);
                    return FileVisitResult.CONTINUE;
                }

                @Override // java.nio.file.SimpleFileVisitor, java.nio.file.FileVisitor
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                    Function1<Path, Unit> function1 = task;
                    Intrinsics.checkNotNull(dir);
                    function1.invoke(dir);
                    return FileVisitResult.CONTINUE;
                }
            });
        }
    }

    public final void postorder(final Function1<? super Path, Unit> task) throws IOException {
        Intrinsics.checkNotNullParameter(task, "task");
        if (getExists()) {
            Files.walkFileTree(this.javaPath, new SimpleFileVisitor<Path>() { // from class: org.jetbrains.kotlin.konan.file.File.postorder.1
                @Override // java.nio.file.SimpleFileVisitor, java.nio.file.FileVisitor
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    Function1<Path, Unit> function1 = task;
                    Intrinsics.checkNotNull(file);
                    function1.invoke(file);
                    return FileVisitResult.CONTINUE;
                }

                @Override // java.nio.file.SimpleFileVisitor, java.nio.file.FileVisitor
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
                    Function1<Path, Unit> function1 = task;
                    Intrinsics.checkNotNull(dir);
                    function1.invoke(dir);
                    return FileVisitResult.CONTINUE;
                }
            });
        }
    }

    public static /* synthetic */ MappedByteBuffer map$default(File file, FileChannel.MapMode READ_ONLY, long j, long j2, int i, Object obj) {
        if ((i & 1) != 0) {
            READ_ONLY = FileChannel.MapMode.READ_ONLY;
            Intrinsics.checkNotNullExpressionValue(READ_ONLY, "READ_ONLY");
        }
        FileChannel.MapMode mapMode = READ_ONLY;
        if ((i & 2) != 0) {
            j = 0;
        }
        long j3 = j;
        if ((i & 4) != 0) {
            j2 = -1;
        }
        return file.map(mapMode, j3, j2);
    }

    public final MappedByteBuffer map(FileChannel.MapMode mode, long start, long size) throws IOException {
        Intrinsics.checkNotNullParameter(mode, "mode");
        RandomAccessFile randomAccessFile = new RandomAccessFile(getPath(), Intrinsics.areEqual(mode, FileChannel.MapMode.READ_ONLY) ? "r" : "rw");
        if (Intrinsics.areEqual(mode, FileChannel.MapMode.READ_ONLY)) {
            size = randomAccessFile.length();
        }
        long j = size;
        FileChannel channel = randomAccessFile.getChannel();
        MappedByteBuffer map = channel.map(mode, start, j);
        channel.close();
        Intrinsics.checkNotNullExpressionValue(map, "channel.map(mode, start,….also { channel.close() }");
        return map;
    }

    public final File deleteOnExit() {
        this.javaPath.toFile().deleteOnExit();
        return this;
    }

    public final boolean createNew() {
        return this.javaPath.toFile().createNewFile();
    }

    public final byte[] readBytes() {
        return Files.readAllBytes(this.javaPath);
    }

    public final Path writeBytes(byte[] bytes) {
        Intrinsics.checkNotNullParameter(bytes, "bytes");
        return Files.write(this.javaPath, bytes, new OpenOption[0]);
    }

    public final Path appendBytes(byte[] bytes) {
        Intrinsics.checkNotNullParameter(bytes, "bytes");
        return Files.write(this.javaPath, bytes, StandardOpenOption.APPEND);
    }

    public final void writeLines(Iterable<String> lines) throws IOException {
        Intrinsics.checkNotNullParameter(lines, "lines");
        Files.write(this.javaPath, lines, new OpenOption[0]);
    }

    public final void writeText(String text) throws IOException {
        Intrinsics.checkNotNullParameter(text, "text");
        writeLines(CollectionsKt.listOf(text));
    }

    public final void appendLines(Iterable<String> lines) throws IOException {
        Intrinsics.checkNotNullParameter(lines, "lines");
        Files.write(this.javaPath, lines, StandardOpenOption.APPEND);
    }

    public final void appendText(String text) throws IOException {
        Intrinsics.checkNotNullParameter(text, "text");
        appendLines(CollectionsKt.listOf(text));
    }

    public final void forEachLine(final Function1<? super String, Unit> action) throws Exception {
        Intrinsics.checkNotNullParameter(action, "action");
        Stream<String> streamLines = Files.lines(this.javaPath);
        try {
            try {
                streamLines.forEach(new Consumer() { // from class: org.jetbrains.kotlin.konan.file.File$forEachLine$1$1
                    @Override // java.util.function.Consumer
                    public final void accept(String it) {
                        Function1<String, Unit> function1 = action;
                        Intrinsics.checkNotNullExpressionValue(it, "it");
                        function1.invoke(it);
                    }
                });
                Unit unit = Unit.INSTANCE;
                if (streamLines != null) {
                    streamLines.close();
                }
            } catch (Throwable th) {
                if (0 == 0 && streamLines != null) {
                    streamLines.close();
                }
                throw th;
            }
        } catch (Exception e) {
            if (streamLines != null) {
                try {
                    streamLines.close();
                } catch (Exception unused) {
                }
            }
            throw e;
        }
    }

    public final void createAsSymlink(String target) throws IOException {
        Intrinsics.checkNotNullParameter(target, "target");
        Path path = Paths.get(target, new String[0]);
        if (Files.isSymbolicLink(this.javaPath) && Intrinsics.areEqual(Files.readSymbolicLink(this.javaPath), path)) {
            return;
        }
        Files.createSymbolicLink(this.javaPath, path, new FileAttribute[0]);
    }

    public String toString() {
        return getPath();
    }

    public final BufferedReader bufferedReader() {
        return Files.newBufferedReader(this.javaPath);
    }

    public final OutputStream outputStream() {
        return Files.newOutputStream(this.javaPath, new OpenOption[0]);
    }

    public final PrintWriter printWriter() {
        java.io.File file = this.javaPath.toFile();
        Intrinsics.checkNotNullExpressionValue(file, "javaPath.toFile()");
        Writer outputStreamWriter = new OutputStreamWriter(new FileOutputStream(file), Charsets.UTF_8);
        return new PrintWriter(outputStreamWriter instanceof BufferedWriter ? (BufferedWriter) outputStreamWriter : new BufferedWriter(outputStreamWriter, 8192));
    }

    /* JADX INFO: compiled from: File.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\f\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u00048F¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0019\u0010\u0007\u001a\n \t*\u0004\u0018\u00010\b0\b¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0019\u0010\f\u001a\n \t*\u0004\u0018\u00010\b0\b¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000bR\u0011\u0010\u000e\u001a\u00020\u000f¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0012\u001a\u00020\u00048F¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0006R\u0011\u0010\u0014\u001a\u00020\u00048F¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0006¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/konan/file/File$Companion;", "", "()V", "javaHome", "Lorg/jetbrains/kotlin/konan/file/File;", "getJavaHome", "()Lorg/jetbrains/kotlin/konan/file/File;", "pathSeparator", "", JvmProtoBufUtil.PLATFORM_TYPE_ID, "getPathSeparator", "()Ljava/lang/String;", "separator", "getSeparator", "separatorChar", "", "getSeparatorChar", "()C", "userDir", "getUserDir", "userHome", "getUserHome", "kotlin-util-io"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final File getUserDir() {
            String property = System.getProperty("user.dir");
            Intrinsics.checkNotNullExpressionValue(property, "getProperty(\"user.dir\")");
            return new File(property);
        }

        public final File getUserHome() {
            String property = System.getProperty("user.home");
            Intrinsics.checkNotNullExpressionValue(property, "getProperty(\"user.home\")");
            return new File(property);
        }

        public final File getJavaHome() {
            String property = System.getProperty("java.home");
            Intrinsics.checkNotNullExpressionValue(property, "getProperty(\"java.home\")");
            return new File(property);
        }

        public final String getPathSeparator() {
            return File.pathSeparator;
        }

        public final String getSeparator() {
            return File.separator;
        }

        public final char getSeparatorChar() {
            return File.separatorChar;
        }
    }

    public final List<String> readStrings() throws Exception {
        final ArrayList arrayList = new ArrayList();
        forEachLine(new Function1<String, Unit>() { // from class: org.jetbrains.kotlin.konan.file.File$readStrings$1$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(String str) {
                invoke2(str);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(String it) {
                Intrinsics.checkNotNullParameter(it, "it");
                arrayList.add(it);
            }
        });
        return arrayList;
    }

    public boolean equals(Object other) {
        File file = other instanceof File ? (File) other : null;
        if (file == null) {
            return false;
        }
        return Intrinsics.areEqual(file.javaPath.toAbsolutePath(), this.javaPath.toAbsolutePath());
    }

    public int hashCode() {
        return this.javaPath.toAbsolutePath().hashCode();
    }
}
