package org.jetbrains.kotlin.incremental;

import java.io.File;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.sequences.Sequence;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: AbstractIncrementalCache.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u001c\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b2\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0003H&J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH&J\b\u0010\u0010\u001a\u00020\u0011H&J\u001c\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u000b0\b2\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u000b0\bH&J\u0012\u0010\u0014\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\u0015\u001a\u00020\tH&J\u0016\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\t0\u00172\u0006\u0010\u0018\u001a\u00020\tH&J\u0016\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\t0\u00172\u0006\u0010\u0018\u001a\u00020\tH&J\u0017\u0010\u001a\u001a\u0004\u0018\u00010\u001b2\u0006\u0010\u0018\u001a\u00020\tH&¢\u0006\u0002\u0010\u001cJ\u0016\u0010\u001d\u001a\u00020\r2\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u000b0\bH&J\u001e\u0010\u001f\u001a\u00020\r2\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u000b0\b2\u0006\u0010 \u001a\u00020!H&R\u001c\u0010\u0002\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00040\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\"À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/incremental/IncrementalCacheCommon;", "", "thisWithDependentCaches", "", "Lorg/jetbrains/kotlin/incremental/AbstractIncrementalCache;", "getThisWithDependentCaches", "()Ljava/lang/Iterable;", "classesFqNamesBySources", "", "Lorg/jetbrains/kotlin/name/FqName;", "files", "Ljava/io/File;", "clearCacheForRemovedClasses", "", "changesCollector", "Lorg/jetbrains/kotlin/incremental/ChangesCollector;", ArchiveStreamFactory.DUMP, "", "getComplementaryFilesRecursive", "dirtyFiles", "getSourceFileIfClass", "fqName", "getSubtypesOf", "Lkotlin/sequences/Sequence;", "className", "getSupertypesOf", "isSealed", "", "(Lorg/jetbrains/kotlin/name/FqName;)Ljava/lang/Boolean;", "markDirty", "removedAndCompiledSources", "updateComplementaryFiles", "expectActualTracker", "Lorg/jetbrains/kotlin/incremental/ExpectActualTrackerImpl;", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface IncrementalCacheCommon {
    Collection<FqName> classesFqNamesBySources(Iterable<? extends File> files);

    void clearCacheForRemovedClasses(ChangesCollector changesCollector);

    String dump();

    Collection<File> getComplementaryFilesRecursive(Collection<? extends File> dirtyFiles);

    File getSourceFileIfClass(FqName fqName);

    Sequence<FqName> getSubtypesOf(FqName className);

    Sequence<FqName> getSupertypesOf(FqName className);

    Iterable<AbstractIncrementalCache<?>> getThisWithDependentCaches();

    Boolean isSealed(FqName className);

    void markDirty(Collection<? extends File> removedAndCompiledSources);

    void updateComplementaryFiles(Collection<? extends File> dirtyFiles, ExpectActualTrackerImpl expectActualTracker);
}
