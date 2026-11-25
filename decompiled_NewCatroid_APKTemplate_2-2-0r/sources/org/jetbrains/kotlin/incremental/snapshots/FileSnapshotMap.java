package org.jetbrains.kotlin.incremental.snapshots;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.incremental.ChangedFiles;
import org.jetbrains.kotlin.incremental.IncrementalCompilationContext;
import org.jetbrains.kotlin.incremental.storage.BasicStringMap;
import org.jetbrains.kotlin.incremental.storage.FileToPathConverter;
import org.jetbrains.kotlin.incremental.storage.PathStringDescriptor;

/* JADX INFO: compiled from: FileSnapshotMap.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001c\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0014\u0010\b\u001a\u00020\t2\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00040\u000bJ\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u0002H\u0014¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/incremental/snapshots/FileSnapshotMap;", "Lorg/jetbrains/kotlin/incremental/storage/BasicStringMap;", "Lorg/jetbrains/kotlin/incremental/snapshots/FileSnapshot;", "storageFile", "Ljava/io/File;", "icContext", "Lorg/jetbrains/kotlin/incremental/IncrementalCompilationContext;", "(Ljava/io/File;Lorg/jetbrains/kotlin/incremental/IncrementalCompilationContext;)V", "compareAndUpdate", "Lorg/jetbrains/kotlin/incremental/ChangedFiles$Known;", "newFiles", "", "dumpValue", "", "value", "incremental-compilation-impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FileSnapshotMap extends BasicStringMap<FileSnapshot> {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FileSnapshotMap(File storageFile, IncrementalCompilationContext icContext) {
        super(storageFile, PathStringDescriptor.INSTANCE, FileSnapshotExternalizer.INSTANCE, icContext);
        Intrinsics.checkNotNullParameter(storageFile, "storageFile");
        Intrinsics.checkNotNullParameter(icContext, "icContext");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.incremental.storage.BasicMap
    public String dumpValue(FileSnapshot value) {
        Intrinsics.checkNotNullParameter(value, "value");
        return value.toString();
    }

    public final synchronized ChangedFiles.Known compareAndUpdate(Iterable<? extends File> newFiles) {
        ArrayList arrayList;
        ArrayList arrayList2;
        Intrinsics.checkNotNullParameter(newFiles, "newFiles");
        SimpleFileSnapshotProviderImpl simpleFileSnapshotProviderImpl = new SimpleFileSnapshotProviderImpl();
        arrayList = new ArrayList();
        arrayList2 = new ArrayList();
        HashSet hashSet = new HashSet();
        FileToPathConverter pathConverter = getPathConverter();
        Iterator<? extends File> it = newFiles.iterator();
        while (it.hasNext()) {
            hashSet.add(pathConverter.toPath(it.next()));
        }
        HashSet<String> hashSet2 = hashSet;
        for (String str : getStorage().getKeys()) {
            if (!hashSet2.contains(str)) {
                getStorage().remove(str);
                arrayList2.add(getPathConverter().toFile(str));
            }
        }
        for (String str2 : hashSet2) {
            FileToPathConverter pathConverter2 = getPathConverter();
            Intrinsics.checkNotNull(str2);
            File file = pathConverter2.toFile(str2);
            FileSnapshot fileSnapshot = (FileSnapshot) getStorage().get(str2);
            FileSnapshot fileSnapshot2 = simpleFileSnapshotProviderImpl.get(file);
            if (fileSnapshot == null || !Intrinsics.areEqual(fileSnapshot, fileSnapshot2)) {
                arrayList.add(file);
                getStorage().set(str2, fileSnapshot2);
            }
        }
        return new ChangedFiles.Known(arrayList, arrayList2, false, 4, null);
    }
}
