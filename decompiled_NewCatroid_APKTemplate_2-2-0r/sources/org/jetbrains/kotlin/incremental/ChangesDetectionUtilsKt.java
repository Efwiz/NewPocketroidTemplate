package org.jetbrains.kotlin.incremental;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.build.report.BuildReporter;
import org.jetbrains.kotlin.build.report.ICReporterKt;
import org.jetbrains.kotlin.build.report.metrics.BuildAttribute;
import org.jetbrains.kotlin.build.report.metrics.GradleBuildPerformanceMetric;
import org.jetbrains.kotlin.build.report.metrics.GradleBuildTime;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.incremental.multiproject.ModulesApiHistory;
import org.jetbrains.kotlin.incremental.util.Either;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: changesDetectionUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000R\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0000\u001at\u0010\u0000\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u000e0\f2\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u00120\u00102\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00110\u0018H\u0000¨\u0006\u0019"}, d2 = {"getClasspathChanges", "Lorg/jetbrains/kotlin/incremental/ChangesEither;", ModuleXmlParser.CLASSPATH, "", "Ljava/io/File;", "changedFiles", "Lorg/jetbrains/kotlin/incremental/ChangedFiles$Known;", "lastBuildInfo", "Lorg/jetbrains/kotlin/incremental/BuildInfo;", "modulesApiHistory", "Lorg/jetbrains/kotlin/incremental/multiproject/ModulesApiHistory;", "reporter", "Lorg/jetbrains/kotlin/build/report/BuildReporter;", "Lorg/jetbrains/kotlin/build/report/metrics/GradleBuildTime;", "Lorg/jetbrains/kotlin/build/report/metrics/GradleBuildPerformanceMetric;", "abiSnapshots", "", "", "Lorg/jetbrains/kotlin/incremental/AbiSnapshot;", "withSnapshot", "", "caches", "Lorg/jetbrains/kotlin/incremental/IncrementalCacheCommon;", "scopes", "", "incremental-compilation-impl"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ChangesDetectionUtilsKt {
    public static final ChangesEither getClasspathChanges(List<? extends File> classpath, ChangedFiles.Known changedFiles, BuildInfo lastBuildInfo, ModulesApiHistory modulesApiHistory, BuildReporter<GradleBuildTime, GradleBuildPerformanceMetric> reporter, Map<String, ? extends AbiSnapshot> abiSnapshots, boolean z, IncrementalCacheCommon caches, Collection<String> scopes) {
        Intrinsics.checkNotNullParameter(classpath, "classpath");
        Intrinsics.checkNotNullParameter(changedFiles, "changedFiles");
        Intrinsics.checkNotNullParameter(lastBuildInfo, "lastBuildInfo");
        Intrinsics.checkNotNullParameter(modulesApiHistory, "modulesApiHistory");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        Intrinsics.checkNotNullParameter(abiSnapshots, "abiSnapshots");
        Intrinsics.checkNotNullParameter(caches, "caches");
        Intrinsics.checkNotNullParameter(scopes, "scopes");
        HashSet hashSet = new HashSet();
        for (File file : classpath) {
            if (file.isFile()) {
                hashSet.add(file);
            } else if (file.isDirectory()) {
                for (File file2 : FilesKt.walk$default(file, null, 1, null)) {
                    if (file2.isFile()) {
                        hashSet.add(file2);
                    }
                }
            }
        }
        List<File> modified = changedFiles.getModified();
        HashSet hashSet2 = new HashSet();
        for (Object obj : modified) {
            if (hashSet.contains((File) obj)) {
                hashSet2.add(obj);
            }
        }
        HashSet hashSet3 = hashSet2;
        List<File> removed = changedFiles.getRemoved();
        HashSet hashSet4 = new HashSet();
        for (Object obj2 : removed) {
            if (hashSet.contains((File) obj2)) {
                hashSet4.add(obj2);
            }
        }
        final HashSet hashSet5 = hashSet4;
        if (!hashSet5.isEmpty()) {
            ICReporterKt.info(reporter, new Function0<String>() { // from class: org.jetbrains.kotlin.incremental.ChangesDetectionUtilsKt.getClasspathChanges.2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final String invoke() {
                    return "Some files are removed from classpath: " + hashSet5;
                }
            });
            return new ChangesEither.Unknown(BuildAttribute.DEP_CHANGE_REMOVED_ENTRY);
        }
        if (hashSet3.isEmpty()) {
            return new ChangesEither.Known(null, null, 3, null);
        }
        if (z) {
            BuildReporter<GradleBuildTime, GradleBuildPerformanceMetric> buildReporter = reporter;
            GradleBuildTime gradleBuildTime = GradleBuildTime.IC_ANALYZE_JAR_FILES;
            buildReporter.startMeasure(gradleBuildTime);
            try {
                return getClasspathChanges$analyzeJarFiles(abiSnapshots, lastBuildInfo, reporter, caches, scopes);
            } finally {
                buildReporter.endMeasure(gradleBuildTime);
            }
        }
        long startTS = lastBuildInfo.getStartTS();
        HashSet hashSet6 = new HashSet();
        HashSet hashSet7 = new HashSet();
        BuildReporter<GradleBuildTime, GradleBuildPerformanceMetric> buildReporter2 = reporter;
        GradleBuildTime gradleBuildTime2 = GradleBuildTime.IC_FIND_HISTORY_FILES;
        buildReporter2.startMeasure(gradleBuildTime2);
        try {
            final Either<Set<File>> eitherHistoryFilesForChangedFiles = modulesApiHistory.historyFilesForChangedFiles(hashSet3);
            buildReporter2.endMeasure(gradleBuildTime2);
            if (!(eitherHistoryFilesForChangedFiles instanceof Either.Success)) {
                if (!(eitherHistoryFilesForChangedFiles instanceof Either.Error)) {
                    throw new NoWhenBranchMatchedException();
                }
                ICReporterKt.info(reporter, new Function0<String>() { // from class: org.jetbrains.kotlin.incremental.ChangesDetectionUtilsKt$getClasspathChanges$historyFiles$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    /* JADX WARN: Multi-variable type inference failed */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final String invoke() {
                        return "Could not find history files: " + ((Either.Error) eitherHistoryFilesForChangedFiles).getReason();
                    }
                });
                return new ChangesEither.Unknown(BuildAttribute.DEP_CHANGE_HISTORY_IS_NOT_FOUND);
            }
            Set set = (Set) ((Either.Success) eitherHistoryFilesForChangedFiles).getValue();
            GradleBuildTime gradleBuildTime3 = GradleBuildTime.IC_ANALYZE_HISTORY_FILES;
            buildReporter2.startMeasure(gradleBuildTime3);
            try {
                return getClasspathChanges$analyzeHistoryFiles(set, reporter, hashSet6, hashSet7, startTS);
            } finally {
                buildReporter2.endMeasure(gradleBuildTime3);
            }
        } catch (Throwable th) {
            buildReporter2.endMeasure(gradleBuildTime2);
            throw th;
        }
    }

    private static final ChangesEither getClasspathChanges$analyzeJarFiles(Map<String, ? extends AbiSnapshot> map, BuildInfo buildInfo, BuildReporter<GradleBuildTime, GradleBuildPerformanceMetric> buildReporter, IncrementalCacheCommon incrementalCacheCommon, Collection<String> collection) {
        HashSet hashSet = new HashSet();
        HashSet hashSet2 = new HashSet();
        for (Map.Entry<String, ? extends AbiSnapshot> entry : map.entrySet()) {
            final String key = entry.getKey();
            AbiSnapshot value = entry.getValue();
            AbiSnapshot abiSnapshot = buildInfo.getDependencyToAbiSnapshot().get(key);
            if (abiSnapshot == null) {
                ICReporterKt.info(buildReporter, new Function0<String>() { // from class: org.jetbrains.kotlin.incremental.ChangesDetectionUtilsKt$getClasspathChanges$analyzeJarFiles$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final String invoke() {
                        return "Some jar are removed from classpath " + key;
                    }
                });
                return new ChangesEither.Unknown(BuildAttribute.DEP_CHANGE_REMOVED_ENTRY);
            }
            DirtyData dirtyDataDoCompute = AbiSnapshotDiffService.INSTANCE.doCompute(value, abiSnapshot, incrementalCacheCommon, collection);
            hashSet.addAll(dirtyDataDoCompute.getDirtyLookupSymbols());
            hashSet2.addAll(dirtyDataDoCompute.getDirtyClassesFqNames());
        }
        return new ChangesEither.Known(hashSet, hashSet2);
    }

    private static final ChangesEither getClasspathChanges$analyzeHistoryFiles(Set<? extends File> set, BuildReporter<GradleBuildTime, GradleBuildPerformanceMetric> buildReporter, HashSet<LookupSymbol> hashSet, HashSet<FqName> hashSet2, long j) {
        for (final File file : set) {
            BuildReporter<GradleBuildTime, GradleBuildPerformanceMetric> buildReporter2 = buildReporter;
            List<BuildDifference> diffsFromFile = BuildDiffsStorage.INSTANCE.readDiffsFromFile(file, buildReporter2);
            if (diffsFromFile == null) {
                ICReporterKt.info(buildReporter2, new Function0<String>() { // from class: org.jetbrains.kotlin.incremental.ChangesDetectionUtilsKt$getClasspathChanges$analyzeHistoryFiles$allBuilds$1$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final String invoke() {
                        return "Could not read diffs from " + file;
                    }
                });
                return new ChangesEither.Unknown(BuildAttribute.DEP_CHANGE_HISTORY_CANNOT_BE_READ);
            }
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            for (Object obj : diffsFromFile) {
                if (((BuildDifference) obj).getTs() <= j) {
                    arrayList.add(obj);
                } else {
                    arrayList2.add(obj);
                }
            }
            Pair pair = new Pair(arrayList, arrayList2);
            List list = (List) pair.component1();
            List<BuildDifference> list2 = (List) pair.component2();
            if (list.isEmpty()) {
                ICReporterKt.info(buildReporter2, new Function0<String>() { // from class: org.jetbrains.kotlin.incremental.ChangesDetectionUtilsKt$getClasspathChanges$analyzeHistoryFiles$2
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final String invoke() {
                        return "No previously known builds for " + file;
                    }
                });
                return new ChangesEither.Unknown(BuildAttribute.DEP_CHANGE_HISTORY_NO_KNOWN_BUILDS);
            }
            for (BuildDifference buildDifference : list2) {
                if (!buildDifference.isIncremental()) {
                    ICReporterKt.info(buildReporter2, new Function0<String>() { // from class: org.jetbrains.kotlin.incremental.ChangesDetectionUtilsKt$getClasspathChanges$analyzeHistoryFiles$3
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        public final String invoke() {
                            return "Non-incremental build from dependency " + file;
                        }
                    });
                    return new ChangesEither.Unknown(BuildAttribute.DEP_CHANGE_NON_INCREMENTAL_BUILD_IN_DEP);
                }
                DirtyData dirtyData = buildDifference.getDirtyData();
                hashSet.addAll(dirtyData.getDirtyLookupSymbols());
                hashSet2.addAll(dirtyData.getDirtyClassesFqNames());
            }
        }
        return new ChangesEither.Known(hashSet, hashSet2);
    }
}
