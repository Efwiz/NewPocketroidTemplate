package org.jetbrains.kotlin.library;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.konan.file.File;
import org.jetbrains.kotlin.konan.file.ZipUtilKt;
import org.jetbrains.kotlin.library.impl.KotlinLibraryLayoutImplKt;

/* JADX INFO: compiled from: KotlinLibraryUtils.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\"\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0012\u0010\n\u001a\u00020\u000b*\u00020\f2\u0006\u0010\r\u001a\u00020\f\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0003\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0004\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"!\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006*\b\u0012\u0004\u0012\u00020\u00010\u00068F¢\u0006\u0006\u001a\u0004\b\b\u0010\t¨\u0006\u000e"}, d2 = {"KLIB_FILE_EXTENSION", "", "KLIB_FILE_EXTENSION_WITH_DOT", "KLIB_METADATA_FILE_EXTENSION", "KLIB_METADATA_FILE_EXTENSION_WITH_DOT", "toUnresolvedLibraries", "", "Lorg/jetbrains/kotlin/library/RequiredUnresolvedLibrary;", "getToUnresolvedLibraries", "(Ljava/util/List;)Ljava/util/List;", "unpackZippedKonanLibraryTo", "", "Lorg/jetbrains/kotlin/konan/file/File;", "newDir", "kotlin-util-klib"}, k = 2, mv = {1, 5, 1}, xi = 48)
public final class KotlinLibraryUtilsKt {
    public static final String KLIB_FILE_EXTENSION = "klib";
    public static final String KLIB_FILE_EXTENSION_WITH_DOT = ".klib";
    public static final String KLIB_METADATA_FILE_EXTENSION = "knm";
    public static final String KLIB_METADATA_FILE_EXTENSION_WITH_DOT = ".knm";

    public static final void unpackZippedKonanLibraryTo(File file, File newDir) throws IOException {
        Intrinsics.checkNotNullParameter(file, "<this>");
        Intrinsics.checkNotNullParameter(newDir, "newDir");
        KotlinLibraryLayoutImplKt.zippedKotlinLibraryChecks(file);
        if (newDir.getExists()) {
            if (newDir.isDirectory()) {
                newDir.deleteRecursively();
            } else {
                newDir.delete();
            }
        }
        ZipUtilKt.unzipTo$default(file, newDir, (File) null, false, 6, (Object) null);
        if (!newDir.getExists()) {
            throw new IllegalStateException(("Could not unpack " + file + " as " + newDir + '.').toString());
        }
    }

    public static final List<RequiredUnresolvedLibrary> getToUnresolvedLibraries(List<String> list) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        List<String> list2 = list;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
        Iterator<T> it = list2.iterator();
        while (it.hasNext()) {
            arrayList.add(UnresolvedLibraryKt.UnresolvedLibrary((String) it.next(), null));
        }
        return arrayList;
    }
}
