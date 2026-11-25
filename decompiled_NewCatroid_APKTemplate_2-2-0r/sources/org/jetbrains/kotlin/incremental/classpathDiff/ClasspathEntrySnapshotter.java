package org.jetbrains.kotlin.incremental.classpathDiff;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.build.report.metrics.BuildMetricsReporter;
import org.jetbrains.kotlin.build.report.metrics.DoNothingBuildMetricsReporter;
import org.jetbrains.kotlin.build.report.metrics.GradleBuildPerformanceMetric;
import org.jetbrains.kotlin.build.report.metrics.GradleBuildTime;
import org.jetbrains.kotlin.buildtools.api.jvm.ClassSnapshotGranularity;

/* JADX INFO: compiled from: ClasspathSnapshotter.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J,\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0014\b\u0002\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00100\u000eR \u0010\u0003\u001a\u0014\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/incremental/classpathDiff/ClasspathEntrySnapshotter;", "", "()V", "DEFAULT_CLASS_FILTER", "Lkotlin/Function2;", "", "", "snapshot", "Lorg/jetbrains/kotlin/incremental/classpathDiff/ClasspathEntrySnapshot;", "classpathEntry", "Ljava/io/File;", "granularity", "Lorg/jetbrains/kotlin/buildtools/api/jvm/ClassSnapshotGranularity;", "metrics", "Lorg/jetbrains/kotlin/build/report/metrics/BuildMetricsReporter;", "Lorg/jetbrains/kotlin/build/report/metrics/GradleBuildTime;", "Lorg/jetbrains/kotlin/build/report/metrics/GradleBuildPerformanceMetric;", "incremental-compilation-impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ClasspathEntrySnapshotter {
    public static final ClasspathEntrySnapshotter INSTANCE = new ClasspathEntrySnapshotter();
    private static final Function2<String, Boolean, Boolean> DEFAULT_CLASS_FILTER = new Function2<String, Boolean, Boolean>() { // from class: org.jetbrains.kotlin.incremental.classpathDiff.ClasspathEntrySnapshotter$DEFAULT_CLASS_FILTER$1
        @Override // kotlin.jvm.functions.Function2
        public /* bridge */ /* synthetic */ Boolean invoke(String str, Boolean bool) {
            return invoke(str, bool.booleanValue());
        }

        /* JADX WARN: Code duplicated, block: B:11:0x0021  */
        public final Boolean invoke(String unixStyleRelativePath, boolean z) {
            boolean z2;
            Intrinsics.checkNotNullParameter(unixStyleRelativePath, "unixStyleRelativePath");
            if (!z) {
                z2 = (!StringsKt.endsWith(unixStyleRelativePath, ".class", true) || StringsKt.equals(unixStyleRelativePath, "module-info.class", true) || StringsKt.startsWith(unixStyleRelativePath, "meta-inf/", true)) ? false : true;
            }
            return Boolean.valueOf(z2);
        }
    };

    private ClasspathEntrySnapshotter() {
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ ClasspathEntrySnapshot snapshot$default(ClasspathEntrySnapshotter classpathEntrySnapshotter, File file, ClassSnapshotGranularity classSnapshotGranularity, BuildMetricsReporter buildMetricsReporter, int i, Object obj) {
        if ((i & 4) != 0) {
            buildMetricsReporter = DoNothingBuildMetricsReporter.INSTANCE;
        }
        return classpathEntrySnapshotter.snapshot(file, classSnapshotGranularity, buildMetricsReporter);
    }

    public final ClasspathEntrySnapshot snapshot(File classpathEntry, ClassSnapshotGranularity granularity, BuildMetricsReporter<GradleBuildTime, GradleBuildPerformanceMetric> metrics) throws Exception {
        Intrinsics.checkNotNullParameter(classpathEntry, "classpathEntry");
        Intrinsics.checkNotNullParameter(granularity, "granularity");
        Intrinsics.checkNotNullParameter(metrics, "metrics");
        DirectoryOrJarReader directoryOrJarReaderCreate = DirectoryOrJarReader.INSTANCE.create(classpathEntry);
        try {
            try {
                final DirectoryOrJarReader directoryOrJarReader = directoryOrJarReaderCreate;
                GradleBuildTime gradleBuildTime = GradleBuildTime.LOAD_CLASSES_PATHS_ONLY;
                metrics.startMeasure(gradleBuildTime);
                try {
                    List<String> unixStyleRelativePaths = directoryOrJarReader.getUnixStyleRelativePaths(DEFAULT_CLASS_FILTER);
                    ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(unixStyleRelativePaths, 10));
                    for (final String str : unixStyleRelativePaths) {
                        arrayList.add(new ClassFileWithContentsProvider(new ClassFile(classpathEntry, str), new Function0<byte[]>() { // from class: org.jetbrains.kotlin.incremental.classpathDiff.ClasspathEntrySnapshotter$snapshot$1$classes$1$1$1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            public final byte[] invoke() {
                                return directoryOrJarReader.readBytes(str);
                            }
                        }));
                    }
                    ArrayList arrayList2 = arrayList;
                    metrics.endMeasure(gradleBuildTime);
                    GradleBuildTime gradleBuildTime2 = GradleBuildTime.SNAPSHOT_CLASSES;
                    metrics.startMeasure(gradleBuildTime2);
                    try {
                        List<ClassSnapshot> listSnapshot = ClassSnapshotter.INSTANCE.snapshot(arrayList2, granularity, metrics);
                        metrics.endMeasure(gradleBuildTime2);
                        ArrayList arrayList3 = arrayList2;
                        ArrayList arrayList4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList3, 10));
                        Iterator it = arrayList3.iterator();
                        while (it.hasNext()) {
                            arrayList4.add(((ClassFileWithContentsProvider) it.next()).getClassFile().getUnixStyleRelativePath());
                        }
                        ClasspathEntrySnapshot classpathEntrySnapshot = new ClasspathEntrySnapshot((LinkedHashMap) MapsKt.toMap(CollectionsKt.zip(arrayList4, listSnapshot), new LinkedHashMap()));
                        if (directoryOrJarReaderCreate != null) {
                            directoryOrJarReaderCreate.close();
                        }
                        return classpathEntrySnapshot;
                    } catch (Throwable th) {
                        metrics.endMeasure(gradleBuildTime2);
                        throw th;
                    }
                } catch (Throwable th2) {
                    metrics.endMeasure(gradleBuildTime);
                    throw th2;
                }
            } catch (Exception e) {
                if (directoryOrJarReaderCreate != null) {
                    try {
                        directoryOrJarReaderCreate.close();
                    } catch (Exception unused) {
                    }
                }
                throw e;
            }
        } catch (Throwable th3) {
            if (0 == 0 && directoryOrJarReaderCreate != null) {
                directoryOrJarReaderCreate.close();
            }
            throw th3;
        }
    }
}
