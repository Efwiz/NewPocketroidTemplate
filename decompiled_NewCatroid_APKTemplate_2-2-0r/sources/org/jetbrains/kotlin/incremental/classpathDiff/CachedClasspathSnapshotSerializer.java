package org.jetbrains.kotlin.incremental.classpathDiff;

import com.koushikdutta.async.http.cache.ResponseCacheMiddleware;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import org.jetbrains.kotlin.build.report.metrics.GradleBuildPerformanceMetric;
import org.jetbrains.kotlin.incremental.storage.ExternalizersKt;

/* JADX INFO: compiled from: ClasspathSnapshotSerializer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001c\u0010\u0007\u001a\u00020\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00050\n2\u0006\u0010\u000b\u001a\u00020\fR\u001a\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/incremental/classpathDiff/CachedClasspathSnapshotSerializer;", "", "()V", ResponseCacheMiddleware.CACHE, "Lorg/jetbrains/kotlin/incremental/classpathDiff/InMemoryCacheWithEviction;", "Ljava/io/File;", "Lorg/jetbrains/kotlin/incremental/classpathDiff/ClasspathEntrySnapshot;", "load", "Lorg/jetbrains/kotlin/incremental/classpathDiff/ClasspathSnapshot;", "classpathEntrySnapshotFiles", "", "reporter", "Lorg/jetbrains/kotlin/incremental/classpathDiff/ClasspathSnapshotBuildReporter;", "incremental-compilation-impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CachedClasspathSnapshotSerializer {
    public static final CachedClasspathSnapshotSerializer INSTANCE = new CachedClasspathSnapshotSerializer();
    private static final InMemoryCacheWithEviction<File, ClasspathEntrySnapshot> cache = new InMemoryCacheWithEviction<>(20, 1000, 0.8d, null, 8, null);

    private CachedClasspathSnapshotSerializer() {
    }

    public final ClasspathSnapshot load(List<? extends File> classpathEntrySnapshotFiles, ClasspathSnapshotBuildReporter reporter) {
        Intrinsics.checkNotNullParameter(classpathEntrySnapshotFiles, "classpathEntrySnapshotFiles");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        cache.newTimePeriod();
        final Ref.LongRef longRef = new Ref.LongRef();
        List<? extends File> list = classpathEntrySnapshotFiles;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(cache.computeIfAbsent((File) it.next(), new Function1<File, ClasspathEntrySnapshot>() { // from class: org.jetbrains.kotlin.incremental.classpathDiff.CachedClasspathSnapshotSerializer$load$classpathSnapshot$1$1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final ClasspathEntrySnapshot invoke(File it2) {
                    Intrinsics.checkNotNullParameter(it2, "it");
                    longRef.element++;
                    return (ClasspathEntrySnapshot) ExternalizersKt.loadFromFile(ClasspathEntrySnapshotExternalizer.INSTANCE, it2);
                }
            }));
        }
        ClasspathSnapshot classpathSnapshot = new ClasspathSnapshot(arrayList);
        cache.evictEntries();
        reporter.addMetric(GradleBuildPerformanceMetric.LOAD_CLASSPATH_SNAPSHOT_EXECUTION_COUNT, 1L);
        reporter.addMetric(GradleBuildPerformanceMetric.LOAD_CLASSPATH_ENTRY_SNAPSHOT_CACHE_HITS, ((long) classpathEntrySnapshotFiles.size()) - longRef.element);
        reporter.addMetric(GradleBuildPerformanceMetric.LOAD_CLASSPATH_ENTRY_SNAPSHOT_CACHE_MISSES, longRef.element);
        return classpathSnapshot;
    }
}
