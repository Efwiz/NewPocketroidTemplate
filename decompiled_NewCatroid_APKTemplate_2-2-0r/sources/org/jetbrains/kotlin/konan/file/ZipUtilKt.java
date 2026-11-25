package org.jetbrains.kotlin.konan.file;

import androidx.exifinterface.media.ExifInterface;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.FileTime;
import java.nio.file.spi.FileSystemProvider;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.zip.ZipException;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;

/* JADX INFO: compiled from: ZipUtil.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0007\u001a\u0012\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004\u001a\u0012\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0000\u001a\u00020\u0001\u001a\u001e\u0010\u0005\u001a\u00020\u0006*\u00020\u00012\u0006\u0010\u0007\u001a\u00020\u00012\b\b\u0002\u0010\b\u001a\u00020\tH\u0002\u001a\u0014\u0010\n\u001a\n \f*\u0004\u0018\u00010\u000b0\u000b*\u00020\u0001H\u0002\u001a&\u0010\r\u001a\u00020\u0006*\u00020\u000b2\u0006\u0010\u000e\u001a\u00020\u000b2\b\b\u0002\u0010\u000f\u001a\u00020\u000b2\b\b\u0002\u0010\b\u001a\u00020\t\u001a&\u0010\r\u001a\u00020\u0006*\u00020\u00012\u0006\u0010\u000e\u001a\u00020\u00012\b\b\u0002\u0010\u000f\u001a\u00020\u00012\b\b\u0002\u0010\b\u001a\u00020\t\u001a)\u0010\u0010\u001a\u0002H\u0011\"\u0004\b\u0000\u0010\u0011*\u00020\u00012\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u0002H\u00110\u0013¢\u0006\u0002\u0010\u0014\u001a1\u0010\u0010\u001a\u0002H\u0011\"\u0004\b\u0000\u0010\u0011*\u00020\u00012\u0006\u0010\u0015\u001a\u00020\t2\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u0002H\u00110\u0013¢\u0006\u0002\u0010\u0016\u001a\u0012\u0010\u0017\u001a\u00020\u0006*\u00020\u00012\u0006\u0010\u0018\u001a\u00020\u0001\u001a\u0016\u0010\u0019\u001a\u00020\u0002*\u00020\u00012\b\b\u0002\u0010\u0015\u001a\u00020\tH\u0000¨\u0006\u001a"}, d2 = {"file", "Lorg/jetbrains/kotlin/konan/file/File;", "Ljava/nio/file/FileSystem;", "path", "", "recursiveCopyTo", "", FirebaseAnalytics.Param.DESTINATION, "resetTimeAttributes", "", "toPath", "Ljava/nio/file/Path;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "unzipTo", "destinationDirectory", "fromSubdirectory", "withZipFileSystem", ExifInterface.GPS_DIRECTION_TRUE, "action", "Lkotlin/Function1;", "(Lorg/jetbrains/kotlin/konan/file/File;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME, "(Lorg/jetbrains/kotlin/konan/file/File;ZLkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "zipDirAs", "unixFile", "zipFileSystem", "kotlin-util-io"}, k = 2, mv = {1, 5, 1}, xi = 48)
public final class ZipUtilKt {
    public static /* synthetic */ FileSystem zipFileSystem$default(File file, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        return zipFileSystem(file, z);
    }

    public static final FileSystem zipFileSystem(File file, boolean z) throws Exception {
        FileSystem fileSystemNewFileSystem;
        Intrinsics.checkNotNullParameter(file, "<this>");
        HashMap mapHashMapOf = MapsKt.hashMapOf(TuplesKt.to(CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME, String.valueOf(z)));
        List<FileSystemProvider> listInstalledProviders = FileSystemProvider.installedProviders();
        Intrinsics.checkNotNullExpressionValue(listInstalledProviders, "installedProviders()");
        ArrayList arrayList = new ArrayList();
        for (Object obj : listInstalledProviders) {
            if (Intrinsics.areEqual(((FileSystemProvider) obj).getScheme(), "jar")) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = new ArrayList();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            try {
                fileSystemNewFileSystem = ((FileSystemProvider) it.next()).newFileSystem(toPath(file), mapHashMapOf);
            } catch (Exception e) {
                if (!(e instanceof UnsupportedOperationException ? true : e instanceof IllegalArgumentException)) {
                    throw e;
                }
                fileSystemNewFileSystem = null;
            }
            if (fileSystemNewFileSystem != null) {
                arrayList2.add(fileSystemNewFileSystem);
            }
        }
        return (FileSystem) CollectionsKt.first((List) arrayList2);
    }

    public static final File file(FileSystem fileSystem, File file) {
        Intrinsics.checkNotNullParameter(fileSystem, "<this>");
        Intrinsics.checkNotNullParameter(file, "file");
        Path path = fileSystem.getPath(file.getPath(), new String[0]);
        Intrinsics.checkNotNullExpressionValue(path, "this.getPath(file.path)");
        return new File(path);
    }

    public static final File file(FileSystem fileSystem, String path) {
        Intrinsics.checkNotNullParameter(fileSystem, "<this>");
        Intrinsics.checkNotNullParameter(path, "path");
        Path path2 = fileSystem.getPath(path, new String[0]);
        Intrinsics.checkNotNullExpressionValue(path2, "this.getPath(path)");
        return new File(path2);
    }

    private static final Path toPath(File file) {
        return Paths.get(file.getPath(), new String[0]);
    }

    public static final void zipDirAs(final File file, File unixFile) throws Exception {
        Intrinsics.checkNotNullParameter(file, "<this>");
        Intrinsics.checkNotNullParameter(unixFile, "unixFile");
        withZipFileSystem(unixFile, true, new Function1<FileSystem, Unit>() { // from class: org.jetbrains.kotlin.konan.file.ZipUtilKt.zipDirAs.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(FileSystem fileSystem) {
                invoke2(fileSystem);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(FileSystem it) {
                Intrinsics.checkNotNullParameter(it, "it");
                ZipUtilKt.recursiveCopyTo(file, ZipUtilKt.file(it, "/"), true);
            }
        });
    }

    public static /* synthetic */ void unzipTo$default(File file, File file2, File file3, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            file3 = new File("/");
        }
        if ((i & 4) != 0) {
            z = false;
        }
        unzipTo(file, file2, file3, z);
    }

    public static final void unzipTo(File file, final File destinationDirectory, final File fromSubdirectory, final boolean z) {
        Intrinsics.checkNotNullParameter(file, "<this>");
        Intrinsics.checkNotNullParameter(destinationDirectory, "destinationDirectory");
        Intrinsics.checkNotNullParameter(fromSubdirectory, "fromSubdirectory");
        withZipFileSystem(file, new Function1<FileSystem, Unit>() { // from class: org.jetbrains.kotlin.konan.file.ZipUtilKt.unzipTo.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(FileSystem fileSystem) {
                invoke2(fileSystem);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(FileSystem it) {
                Intrinsics.checkNotNullParameter(it, "it");
                ZipUtilKt.recursiveCopyTo(ZipUtilKt.file(it, fromSubdirectory), destinationDirectory, z);
            }
        });
    }

    public static /* synthetic */ void unzipTo$default(Path path, Path path2, Path path3, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            path3 = Paths.get("/", new String[0]);
            Intrinsics.checkNotNullExpressionValue(path3, "get(\"/\")");
        }
        if ((i & 4) != 0) {
            z = false;
        }
        unzipTo(path, path2, path3, z);
    }

    public static final void unzipTo(Path path, Path destinationDirectory, Path fromSubdirectory, boolean z) {
        Intrinsics.checkNotNullParameter(path, "<this>");
        Intrinsics.checkNotNullParameter(destinationDirectory, "destinationDirectory");
        Intrinsics.checkNotNullParameter(fromSubdirectory, "fromSubdirectory");
        unzipTo(new File(path), new File(destinationDirectory), new File(fromSubdirectory), z);
    }

    public static final <T> T withZipFileSystem(File file, boolean z, Function1<? super FileSystem, ? extends T> action) throws Exception {
        boolean z2;
        Intrinsics.checkNotNullParameter(file, "<this>");
        Intrinsics.checkNotNullParameter(action, "action");
        FileSystem fileSystemZipFileSystem = zipFileSystem(file, z);
        try {
            T tInvoke = action.invoke(fileSystemZipFileSystem);
            if (fileSystemZipFileSystem != null) {
                fileSystemZipFileSystem.close();
            }
            return tInvoke;
        } catch (Exception e) {
            z2 = true;
            if (fileSystemZipFileSystem != null) {
                try {
                    try {
                        fileSystemZipFileSystem.close();
                    } catch (Throwable th) {
                        th = th;
                        if (!z2 && fileSystemZipFileSystem != null) {
                            fileSystemZipFileSystem.close();
                        }
                        throw th;
                    }
                } catch (Exception unused) {
                }
            }
            throw e;
        } catch (Throwable th2) {
            th = th2;
            z2 = false;
            if (!z2) {
                fileSystemZipFileSystem.close();
            }
            throw th;
        }
    }

    public static final <T> T withZipFileSystem(File file, Function1<? super FileSystem, ? extends T> action) {
        Intrinsics.checkNotNullParameter(file, "<this>");
        Intrinsics.checkNotNullParameter(action, "action");
        return (T) withZipFileSystem(file, false, action);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void recursiveCopyTo(File file, final File file2, final boolean z) {
        final Path javaPath$kotlin_util_io = file.getJavaPath$kotlin_util_io();
        final Path javaPath$kotlin_util_io2 = file2.getJavaPath$kotlin_util_io();
        final FileSystem fileSystem = javaPath$kotlin_util_io2.getFileSystem();
        final Path pathNormalize = javaPath$kotlin_util_io2.normalize();
        Files.walk(javaPath$kotlin_util_io, new FileVisitOption[0]).forEach(new Consumer() { // from class: org.jetbrains.kotlin.konan.file.ZipUtilKt.recursiveCopyTo.1
            @Override // java.util.function.Consumer
            public final void accept(Path path) throws IOException {
                Path pathRelativize = javaPath$kotlin_util_io.relativize(path);
                Path path2 = fileSystem.getPath(javaPath$kotlin_util_io2.toString(), pathRelativize.toString());
                if (!path2.normalize().startsWith(pathNormalize)) {
                    throw new ZipException(pathRelativize + " attempted to escape the destination directory " + file2);
                }
                if (Intrinsics.areEqual(path2, path2.getRoot())) {
                    return;
                }
                if (Files.isDirectory(path2, new LinkOption[0])) {
                    Files.createDirectories(path2, new FileAttribute[0]);
                } else {
                    Files.copy(path, path2, StandardCopyOption.REPLACE_EXISTING);
                }
                if (z) {
                    FileTime fileTimeFromMillis = FileTime.fromMillis(0L);
                    ((BasicFileAttributeView) Files.getFileAttributeView(path2, BasicFileAttributeView.class, new LinkOption[0])).setTimes(fileTimeFromMillis, fileTimeFromMillis, fileTimeFromMillis);
                }
            }
        });
    }
}
