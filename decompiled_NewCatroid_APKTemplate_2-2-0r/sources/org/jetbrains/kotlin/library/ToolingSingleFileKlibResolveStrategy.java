package org.jetbrains.kotlin.library;

import androidx.exifinterface.media.ExifInterface;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.konan.file.File;
import org.jetbrains.kotlin.konan.file.ZipUtilKt;
import org.jetbrains.kotlin.library.impl.KotlinLibraryImplKt;
import org.jetbrains.kotlin.util.Logger;

/* JADX INFO: compiled from: ToolingResolve.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u0007H\u0002J\u0018\u0010\r\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u00072\u0006\u0010\u000e\u001a\u00020\u000fH\u0016JF\u0010\u0010\u001a\u0004\u0018\u0001H\u0011\"\b\b\u0000\u0010\u0011*\u00020\u00122\u0006\u0010\f\u001a\u00020\u00072#\u0010\u0013\u001a\u001f\u0012\u0013\u0012\u00110\u0007¢\u0006\f\b\u0015\u0012\b\b\u0016\u0012\u0004\b\b(\u0017\u0012\u0006\u0012\u0004\u0018\u0001H\u00110\u0014H\u0002¢\u0006\u0002\u0010\u0018R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u0018\u0010\u0005\u001a\u00020\u0006*\u00020\u00078BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\t¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/library/ToolingSingleFileKlibResolveStrategy;", "Lorg/jetbrains/kotlin/library/SingleFileKlibResolveStrategy;", "()V", "NONEXISTENT_COMPONENT_NAME", "", "looksLikeKlibComponent", "", "Lorg/jetbrains/kotlin/konan/file/File;", "getLooksLikeKlibComponent", "(Lorg/jetbrains/kotlin/konan/file/File;)Z", "fakeLibrary", "Lorg/jetbrains/kotlin/library/KotlinLibrary;", "libraryFile", "resolve", "logger", "Lorg/jetbrains/kotlin/util/Logger;", "withSafeAccess", ExifInterface.GPS_DIRECTION_TRUE, "", "action", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "localRoot", "(Lorg/jetbrains/kotlin/konan/file/File;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "kotlin-util-klib"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class ToolingSingleFileKlibResolveStrategy implements SingleFileKlibResolveStrategy {
    public static final ToolingSingleFileKlibResolveStrategy INSTANCE = new ToolingSingleFileKlibResolveStrategy();

    private ToolingSingleFileKlibResolveStrategy() {
    }

    @Override // org.jetbrains.kotlin.library.SingleFileKlibResolveStrategy
    public KotlinLibrary resolve(final File libraryFile, final Logger logger) {
        Intrinsics.checkNotNullParameter(libraryFile, "libraryFile");
        Intrinsics.checkNotNullParameter(logger, "logger");
        KotlinLibrary kotlinLibrary = (KotlinLibrary) withSafeAccess(libraryFile, new Function1<File, KotlinLibrary>() { // from class: org.jetbrains.kotlin.library.ToolingSingleFileKlibResolveStrategy.resolve.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final KotlinLibrary invoke(File localRoot) throws Exception {
                Intrinsics.checkNotNullParameter(localRoot, "localRoot");
                if (ToolingSingleFileKlibResolveStrategy.INSTANCE.getLooksLikeKlibComponent(localRoot)) {
                    return null;
                }
                List<File> listFiles = localRoot.getListFiles();
                ArrayList arrayList = new ArrayList();
                for (Object obj : listFiles) {
                    if (ToolingSingleFileKlibResolveStrategy.INSTANCE.getLooksLikeKlibComponent((File) obj)) {
                        arrayList.add(obj);
                    }
                }
                ArrayList arrayList2 = arrayList;
                int size = arrayList2.size();
                if (size == 0) {
                    return null;
                }
                if (size == 1) {
                    return KotlinLibraryImplKt.createKotlinLibrary$default(libraryFile, ((File) CollectionsKt.single((List) arrayList2)).getName(), false, false, null, 28, null);
                }
                Logger logger2 = logger;
                StringBuilder sbAppend = new StringBuilder("Library ").append(libraryFile).append(" can not be read. Multiple components found: ");
                ArrayList arrayList3 = arrayList2;
                ArrayList arrayList4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList3, 10));
                Iterator it = arrayList3.iterator();
                while (it.hasNext()) {
                    arrayList4.add(StringsKt.substringAfter$default(((File) it.next()).getPath(), localRoot.getPath(), (String) null, 2, (Object) null));
                }
                logger2.warning(sbAppend.append(arrayList4).toString());
                return null;
            }
        });
        return kotlinLibrary == null ? fakeLibrary(libraryFile) : kotlinLibrary;
    }

    private final KotlinLibrary fakeLibrary(File libraryFile) {
        return KotlinLibraryImplKt.createKotlinLibrary$default(libraryFile, "__nonexistent_component_name__", false, false, null, 28, null);
    }

    private final <T> T withSafeAccess(final File libraryFile, final Function1<? super File, ? extends T> action) {
        Function0<T> function0;
        String extension = libraryFile.getExtension();
        if (libraryFile.isDirectory()) {
            function0 = new Function0<T>() { // from class: org.jetbrains.kotlin.library.ToolingSingleFileKlibResolveStrategy$withSafeAccess$wrappedAction$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                /* JADX WARN: Multi-variable type inference failed */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final T invoke() {
                    return action.invoke(libraryFile);
                }
            };
        } else {
            if (!libraryFile.isFile() || !Intrinsics.areEqual(extension, "klib")) {
                return null;
            }
            function0 = new Function0<T>() { // from class: org.jetbrains.kotlin.library.ToolingSingleFileKlibResolveStrategy$withSafeAccess$wrappedAction$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                /* JADX WARN: Multi-variable type inference failed */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final T invoke() {
                    File file = libraryFile;
                    final Function1<File, T> function1 = action;
                    return (T) ZipUtilKt.withZipFileSystem(file, new Function1<FileSystem, T>() { // from class: org.jetbrains.kotlin.library.ToolingSingleFileKlibResolveStrategy$withSafeAccess$wrappedAction$2.1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        /* JADX WARN: Multi-variable type inference failed */
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public final T invoke(FileSystem fs) {
                            Intrinsics.checkNotNullParameter(fs, "fs");
                            return function1.invoke(ZipUtilKt.file(fs, "/"));
                        }
                    });
                }
            };
        }
        try {
            return function0.invoke();
        } catch (IOException unused) {
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean getLooksLikeKlibComponent(File file) {
        return file.child(KotlinLibraryLayoutKt.KLIB_MANIFEST_FILE_NAME).isFile();
    }
}
