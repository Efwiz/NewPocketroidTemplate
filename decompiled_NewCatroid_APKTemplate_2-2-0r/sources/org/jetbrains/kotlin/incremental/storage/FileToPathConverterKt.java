package org.jetbrains.kotlin.incremental.storage;

import com.reandroid.apk.UncompressedFiles;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: FileToPathConverter.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0010\u000e\n\u0002\b\u0003\u001a\u001e\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u001a\u001e\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00060\u0001*\u00020\u00032\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00020\u0005¨\u0006\t"}, d2 = {"toFiles", "", "Ljava/io/File;", "Lorg/jetbrains/kotlin/incremental/storage/FileToPathConverter;", UncompressedFiles.NAME_paths, "", "", "toPaths", "files", "kotlin-build-common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FileToPathConverterKt {
    public static final List<String> toPaths(FileToPathConverter fileToPathConverter, Collection<? extends File> files) {
        Intrinsics.checkNotNullParameter(fileToPathConverter, "<this>");
        Intrinsics.checkNotNullParameter(files, "files");
        Collection<? extends File> collection = files;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collection, 10));
        Iterator<T> it = collection.iterator();
        while (it.hasNext()) {
            arrayList.add(fileToPathConverter.toPath((File) it.next()));
        }
        return arrayList;
    }

    public static final List<File> toFiles(FileToPathConverter fileToPathConverter, Collection<String> paths) {
        Intrinsics.checkNotNullParameter(fileToPathConverter, "<this>");
        Intrinsics.checkNotNullParameter(paths, "paths");
        Collection<String> collection = paths;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collection, 10));
        Iterator<T> it = collection.iterator();
        while (it.hasNext()) {
            arrayList.add(fileToPathConverter.toFile((String) it.next()));
        }
        return arrayList;
    }
}
