package org.jetbrains.kotlin.incremental;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.incremental.components.ImportTracker;

/* JADX INFO: compiled from: ImportTrackerImpl.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0010#\n\u0000\n\u0002\u0010$\n\u0002\u0010\u001e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0018\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u00052\u0006\u0010\u000f\u001a\u00020\u0005H\u0016R \u0010\u0003\u001a\u0014\u0012\u0004\u0012\u00020\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00060\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R#\u0010\u0007\u001a\u0014\u0012\u0004\u0012\u00020\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\t0\b8F¢\u0006\u0006\u001a\u0004\b\n\u0010\u000b¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/incremental/ImportTrackerImpl;", "Lorg/jetbrains/kotlin/incremental/components/ImportTracker;", "()V", "filePathToImportedFqNames", "Ljava/util/concurrent/ConcurrentHashMap;", "", "", "filePathToImportedFqNamesMap", "", "", "getFilePathToImportedFqNamesMap", "()Ljava/util/Map;", "report", "", "filePath", "importedFqName", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ImportTrackerImpl implements ImportTracker {
    private final ConcurrentHashMap<String, Set<String>> filePathToImportedFqNames = new ConcurrentHashMap<>();

    public final Map<String, Collection<String>> getFilePathToImportedFqNamesMap() {
        return this.filePathToImportedFqNames;
    }

    @Override // org.jetbrains.kotlin.incremental.components.ImportTracker
    public void report(String filePath, String importedFqName) {
        Set<String> setPutIfAbsent;
        Intrinsics.checkNotNullParameter(filePath, "filePath");
        Intrinsics.checkNotNullParameter(importedFqName, "importedFqName");
        ConcurrentHashMap<String, Set<String>> concurrentHashMap = this.filePathToImportedFqNames;
        HashSet hashSet = concurrentHashMap.get(filePath);
        if (hashSet == null && (setPutIfAbsent = concurrentHashMap.putIfAbsent(filePath, (hashSet = new HashSet()))) != null) {
            hashSet = setPutIfAbsent;
        }
        hashSet.add(importedFqName);
    }
}
