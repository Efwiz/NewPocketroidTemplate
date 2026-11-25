package org.jetbrains.kotlin.incremental.classpathDiff;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.build.report.DoNothingICReporter;
import org.jetbrains.kotlin.build.report.ICReporterKt;
import org.jetbrains.kotlin.build.report.metrics.BuildMetricsReporter;
import org.jetbrains.kotlin.build.report.metrics.GradleBuildPerformanceMetric;
import org.jetbrains.kotlin.build.report.metrics.GradleBuildTime;
import org.jetbrains.kotlin.com.intellij.openapi.util.io.FileUtil;
import org.jetbrains.kotlin.incremental.BuildUtilKt;
import org.jetbrains.kotlin.incremental.ChangesCollector;
import org.jetbrains.kotlin.incremental.ChangesEither;
import org.jetbrains.kotlin.incremental.ClasspathSnapshotFiles;
import org.jetbrains.kotlin.incremental.DirtyData;
import org.jetbrains.kotlin.incremental.IncrementalCompilationContext;
import org.jetbrains.kotlin.incremental.IncrementalJvmCache;
import org.jetbrains.kotlin.incremental.KotlinClassInfo;
import org.jetbrains.kotlin.incremental.LookupStorage;
import org.jetbrains.kotlin.incremental.LookupSymbol;
import org.jetbrains.kotlin.incremental.storage.ExternalizersKt;
import org.jetbrains.kotlin.incremental.storage.ListExternalizer;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.sam.SamConversionResolverKt;

/* JADX INFO: compiled from: ClasspathChangesComputer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000h\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001c\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J*\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u0006\u0010\t\u001a\u00020\nJ8\u0010\u000b\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000f0\rH\u0002Jb\u0010\u0010\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142B\u0010\u0015\u001a>\u0012\u0019\u0012\u0017\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\f\b\u0017\u0012\b\b\u0018\u0012\u0004\b\b(\u0019\u0012\u0019\u0012\u0017\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\f\b\u0017\u0012\b\b\u0018\u0012\u0004\b\b(\u001a\u0012\u0004\u0012\u00020\u001b0\u00162\u0006\u0010\t\u001a\u00020\nJ$\u0010\u001c\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u001d0\u00062\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u001d0\u0006H\u0002J$\u0010\u001e\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u001d0\u00062\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u001d0\u0006H\u0002J$\u0010\u001f\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u001d0\u00062\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u001d0\u0006H\u0002J\u001a\u0010 \u001a\u00020\u0004*\u00020!2\f\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00070#H\u0002¨\u0006$"}, d2 = {"Lorg/jetbrains/kotlin/incremental/classpathDiff/ClasspathChangesComputer;", "", "()V", "computeChangedAndImpactedSet", "Lorg/jetbrains/kotlin/incremental/classpathDiff/ProgramSymbolSet;", "currentClassSnapshots", "", "Lorg/jetbrains/kotlin/incremental/classpathDiff/AccessibleClassSnapshot;", "previousClassSnapshots", "reporter", "Lorg/jetbrains/kotlin/incremental/classpathDiff/ClasspathSnapshotBuildReporter;", "computeClassChanges", "metrics", "Lorg/jetbrains/kotlin/build/report/metrics/BuildMetricsReporter;", "Lorg/jetbrains/kotlin/build/report/metrics/GradleBuildTime;", "Lorg/jetbrains/kotlin/build/report/metrics/GradleBuildPerformanceMetric;", "computeClasspathChanges", "classpathSnapshotFiles", "Lorg/jetbrains/kotlin/incremental/ClasspathSnapshotFiles;", "lookupStorage", "Lorg/jetbrains/kotlin/incremental/LookupStorage;", "storeCurrentClasspathSnapshotForReuse", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "currentClasspathSnapshot", "shrunkCurrentClasspathAgainstPreviousLookups", "", "computeCoarseGrainedKotlinClassChanges", "Lorg/jetbrains/kotlin/incremental/classpathDiff/KotlinClassSnapshot;", "computeFineGrainedKotlinClassChanges", "computeKotlinClassChanges", "toProgramSymbols", "Lorg/jetbrains/kotlin/incremental/DirtyData;", "allClasses", "", "incremental-compilation-impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ClasspathChangesComputer {
    public static final ClasspathChangesComputer INSTANCE = new ClasspathChangesComputer();

    private ClasspathChangesComputer() {
    }

    public final ProgramSymbolSet computeClasspathChanges(ClasspathSnapshotFiles classpathSnapshotFiles, LookupStorage lookupStorage, Function2<? super List<? extends AccessibleClassSnapshot>, ? super List<? extends AccessibleClassSnapshot>, Unit> storeCurrentClasspathSnapshotForReuse, ClasspathSnapshotBuildReporter reporter) {
        Intrinsics.checkNotNullParameter(classpathSnapshotFiles, "classpathSnapshotFiles");
        Intrinsics.checkNotNullParameter(lookupStorage, "lookupStorage");
        Intrinsics.checkNotNullParameter(storeCurrentClasspathSnapshotForReuse, "storeCurrentClasspathSnapshotForReuse");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        ClasspathSnapshotBuildReporter classpathSnapshotBuildReporter = reporter;
        GradleBuildTime gradleBuildTime = GradleBuildTime.LOAD_CURRENT_CLASSPATH_SNAPSHOT;
        classpathSnapshotBuildReporter.startMeasure(gradleBuildTime);
        try {
            ClasspathSnapshot classpathSnapshotLoad = CachedClasspathSnapshotSerializer.INSTANCE.load(classpathSnapshotFiles.getCurrentClasspathEntrySnapshotFiles(), reporter);
            ClasspathSnapshotBuildReporter classpathSnapshotBuildReporter2 = reporter;
            GradleBuildTime gradleBuildTime2 = GradleBuildTime.REMOVE_DUPLICATE_CLASSES;
            classpathSnapshotBuildReporter2.startMeasure(gradleBuildTime2);
            try {
                final List<AccessibleClassSnapshot> listRemoveDuplicateAndInaccessibleClasses = ClasspathSnapshotShrinkerKt.removeDuplicateAndInaccessibleClasses(classpathSnapshotLoad);
                classpathSnapshotBuildReporter2.endMeasure(gradleBuildTime2);
                classpathSnapshotBuildReporter.endMeasure(gradleBuildTime);
                GradleBuildTime gradleBuildTime3 = GradleBuildTime.SHRINK_CURRENT_CLASSPATH_SNAPSHOT;
                classpathSnapshotBuildReporter.startMeasure(gradleBuildTime3);
                try {
                    final List<AccessibleClassSnapshot> listShrinkClasspath = ClasspathSnapshotShrinker.INSTANCE.shrinkClasspath(listRemoveDuplicateAndInaccessibleClasses, lookupStorage, new ClasspathSnapshotShrinker.MetricsReporter(reporter, GradleBuildTime.GET_LOOKUP_SYMBOLS, GradleBuildTime.FIND_REFERENCED_CLASSES, GradleBuildTime.FIND_TRANSITIVELY_REFERENCED_CLASSES));
                    classpathSnapshotBuildReporter.endMeasure(gradleBuildTime3);
                    ClasspathSnapshotBuildReporter classpathSnapshotBuildReporter3 = reporter;
                    ICReporterKt.debug(classpathSnapshotBuildReporter3, new Function0<String>() { // from class: org.jetbrains.kotlin.incremental.classpathDiff.ClasspathChangesComputer.computeClasspathChanges.1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        /* JADX WARN: Multi-variable type inference failed */
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        public final String invoke() {
                            return "Shrunk current classpath snapshot for diffing, retained " + listShrinkClasspath.size() + " / " + listRemoveDuplicateAndInaccessibleClasses.size() + " classes";
                        }
                    });
                    storeCurrentClasspathSnapshotForReuse.invoke(listRemoveDuplicateAndInaccessibleClasses, listShrinkClasspath);
                    GradleBuildTime gradleBuildTime4 = GradleBuildTime.LOAD_SHRUNK_PREVIOUS_CLASSPATH_SNAPSHOT;
                    classpathSnapshotBuildReporter.startMeasure(gradleBuildTime4);
                    try {
                        final List<? extends AccessibleClassSnapshot> list = (List) ExternalizersKt.loadFromFile(new ListExternalizer(AccessibleClassSnapshotExternalizer.INSTANCE), classpathSnapshotFiles.getShrunkPreviousClasspathSnapshotFile());
                        classpathSnapshotBuildReporter.endMeasure(gradleBuildTime4);
                        ICReporterKt.debug(classpathSnapshotBuildReporter3, new Function0<String>() { // from class: org.jetbrains.kotlin.incremental.classpathDiff.ClasspathChangesComputer.computeClasspathChanges.2
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            /* JADX WARN: Multi-variable type inference failed */
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            public final String invoke() {
                                return "Loaded shrunk previous classpath snapshot for diffing, found " + list.size() + " classes";
                            }
                        });
                        GradleBuildTime gradleBuildTime5 = GradleBuildTime.COMPUTE_CHANGED_AND_IMPACTED_SET;
                        classpathSnapshotBuildReporter.startMeasure(gradleBuildTime5);
                        try {
                            return INSTANCE.computeChangedAndImpactedSet(listShrinkClasspath, list, reporter);
                        } finally {
                            classpathSnapshotBuildReporter.endMeasure(gradleBuildTime5);
                        }
                    } catch (Throwable th) {
                        classpathSnapshotBuildReporter.endMeasure(gradleBuildTime4);
                        throw th;
                    }
                } catch (Throwable th2) {
                    classpathSnapshotBuildReporter.endMeasure(gradleBuildTime3);
                    throw th2;
                }
            } catch (Throwable th3) {
                classpathSnapshotBuildReporter2.endMeasure(gradleBuildTime2);
                throw th3;
            }
        } catch (Throwable th4) {
            classpathSnapshotBuildReporter.endMeasure(gradleBuildTime);
            throw th4;
        }
    }

    public final ProgramSymbolSet computeChangedAndImpactedSet(List<? extends AccessibleClassSnapshot> currentClassSnapshots, List<? extends AccessibleClassSnapshot> previousClassSnapshots, ClasspathSnapshotBuildReporter reporter) {
        Intrinsics.checkNotNullParameter(currentClassSnapshots, "currentClassSnapshots");
        Intrinsics.checkNotNullParameter(previousClassSnapshots, "previousClassSnapshots");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        List<? extends AccessibleClassSnapshot> list = currentClassSnapshots;
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(list, 10)), 16));
        for (Object obj : list) {
            linkedHashMap.put(((AccessibleClassSnapshot) obj).getClassId(), obj);
        }
        List<? extends AccessibleClassSnapshot> list2 = previousClassSnapshots;
        LinkedHashMap linkedHashMap2 = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(list2, 10)), 16));
        for (Object obj2 : list2) {
            linkedHashMap2.put(((AccessibleClassSnapshot) obj2).getClassId(), obj2);
        }
        ArrayList arrayList = new ArrayList();
        Iterator it = linkedHashMap.entrySet().iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            Map.Entry entry = (Map.Entry) it.next();
            ClassId classId = (ClassId) entry.getKey();
            AccessibleClassSnapshot accessibleClassSnapshot = (AccessibleClassSnapshot) entry.getValue();
            AccessibleClassSnapshot accessibleClassSnapshot2 = (AccessibleClassSnapshot) linkedHashMap2.get(classId);
            AccessibleClassSnapshot accessibleClassSnapshot3 = (accessibleClassSnapshot2 == null || accessibleClassSnapshot.getClassAbiHash() != accessibleClassSnapshot2.getClassAbiHash()) ? accessibleClassSnapshot : null;
            if (accessibleClassSnapshot3 != null) {
                arrayList.add(accessibleClassSnapshot3);
            }
        }
        ArrayList arrayList2 = arrayList;
        ArrayList arrayList3 = new ArrayList();
        for (Map.Entry entry2 : linkedHashMap2.entrySet()) {
            ClassId classId2 = (ClassId) entry2.getKey();
            AccessibleClassSnapshot accessibleClassSnapshot4 = (AccessibleClassSnapshot) entry2.getValue();
            AccessibleClassSnapshot accessibleClassSnapshot5 = (AccessibleClassSnapshot) linkedHashMap.get(classId2);
            if (accessibleClassSnapshot5 != null && accessibleClassSnapshot5.getClassAbiHash() == accessibleClassSnapshot4.getClassAbiHash()) {
                accessibleClassSnapshot4 = null;
            }
            if (accessibleClassSnapshot4 != null) {
                arrayList3.add(accessibleClassSnapshot4);
            }
        }
        ArrayList arrayList4 = arrayList3;
        ClasspathSnapshotBuildReporter classpathSnapshotBuildReporter = reporter;
        GradleBuildTime gradleBuildTime = GradleBuildTime.COMPUTE_CLASS_CHANGES;
        classpathSnapshotBuildReporter.startMeasure(gradleBuildTime);
        try {
            final ProgramSymbolSet programSymbolSetComputeClassChanges = INSTANCE.computeClassChanges(arrayList2, arrayList4, reporter);
            classpathSnapshotBuildReporter.endMeasure(gradleBuildTime);
            ClasspathSnapshotBuildReporter.reportVerboseWithLimit$default(reporter, 0, new Function0<String>() { // from class: org.jetbrains.kotlin.incremental.classpathDiff.ClasspathChangesComputer.computeChangedAndImpactedSet.1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final String invoke() {
                    return "Changed set = " + programSymbolSetComputeClassChanges.toDebugString();
                }
            }, 1, null);
            if (programSymbolSetComputeClassChanges.isEmpty()) {
                return programSymbolSetComputeClassChanges;
            }
            GradleBuildTime gradleBuildTime2 = GradleBuildTime.COMPUTE_IMPACTED_SET;
            classpathSnapshotBuildReporter.startMeasure(gradleBuildTime2);
            try {
                final ProgramSymbolSet programSymbolSetComputeImpactedSymbols = ImpactedSymbolsComputer.INSTANCE.computeImpactedSymbols(programSymbolSetComputeClassChanges, SequencesKt.asIterable(SequencesKt.plus(CollectionsKt.asSequence(previousClassSnapshots), CollectionsKt.asSequence(arrayList2))));
                classpathSnapshotBuildReporter.endMeasure(gradleBuildTime2);
                ClasspathSnapshotBuildReporter.reportVerboseWithLimit$default(reporter, 0, new Function0<String>() { // from class: org.jetbrains.kotlin.incremental.classpathDiff.ClasspathChangesComputer.computeChangedAndImpactedSet.2
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final String invoke() {
                        StringBuilder sb = new StringBuilder("Impacted classes = ");
                        ProgramSymbolSet programSymbolSet = programSymbolSetComputeImpactedSymbols;
                        Set setPlus = SetsKt.plus((Set) programSymbolSet.getClasses(), (Iterable) programSymbolSet.getClassMembers().keySet());
                        ProgramSymbolSet programSymbolSet2 = programSymbolSetComputeClassChanges;
                        return sb.append(SetsKt.minus(setPlus, (Iterable) SetsKt.plus((Set) programSymbolSet2.getClasses(), (Iterable) programSymbolSet2.getClassMembers().keySet()))).toString();
                    }
                }, 1, null);
                return programSymbolSetComputeImpactedSymbols;
            } catch (Throwable th) {
                classpathSnapshotBuildReporter.endMeasure(gradleBuildTime2);
                throw th;
            }
        } catch (Throwable th2) {
            classpathSnapshotBuildReporter.endMeasure(gradleBuildTime);
            throw th2;
        }
    }

    private final ProgramSymbolSet computeClassChanges(List<? extends AccessibleClassSnapshot> currentClassSnapshots, List<? extends AccessibleClassSnapshot> previousClassSnapshots, BuildMetricsReporter<GradleBuildTime, GradleBuildPerformanceMetric> metrics) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (Object obj : currentClassSnapshots) {
            if (((AccessibleClassSnapshot) obj) instanceof KotlinClassSnapshot) {
                arrayList.add(obj);
            } else {
                arrayList2.add(obj);
            }
        }
        Pair pair = new Pair(arrayList, arrayList2);
        List<? extends KotlinClassSnapshot> list = (List) pair.component1();
        List<JavaClassSnapshot> list2 = (List) pair.component2();
        ArrayList arrayList3 = new ArrayList();
        ArrayList arrayList4 = new ArrayList();
        for (Object obj2 : previousClassSnapshots) {
            if (((AccessibleClassSnapshot) obj2) instanceof KotlinClassSnapshot) {
                arrayList3.add(obj2);
            } else {
                arrayList4.add(obj2);
            }
        }
        Pair pair2 = new Pair(arrayList3, arrayList4);
        List<? extends KotlinClassSnapshot> list3 = (List) pair2.component1();
        List<JavaClassSnapshot> list4 = (List) pair2.component2();
        GradleBuildTime gradleBuildTime = GradleBuildTime.COMPUTE_KOTLIN_CLASS_CHANGES;
        metrics.startMeasure(gradleBuildTime);
        try {
            ClasspathChangesComputer classpathChangesComputer = INSTANCE;
            Intrinsics.checkNotNull(list, "null cannot be cast to non-null type kotlin.collections.List<org.jetbrains.kotlin.incremental.classpathDiff.KotlinClassSnapshot>");
            Intrinsics.checkNotNull(list3, "null cannot be cast to non-null type kotlin.collections.List<org.jetbrains.kotlin.incremental.classpathDiff.KotlinClassSnapshot>");
            ProgramSymbolSet programSymbolSetComputeKotlinClassChanges = classpathChangesComputer.computeKotlinClassChanges(list, list3);
            metrics.endMeasure(gradleBuildTime);
            GradleBuildTime gradleBuildTime2 = GradleBuildTime.COMPUTE_JAVA_CLASS_CHANGES;
            metrics.startMeasure(gradleBuildTime2);
            try {
                JavaClassChangesComputer javaClassChangesComputer = JavaClassChangesComputer.INSTANCE;
                Intrinsics.checkNotNull(list2, "null cannot be cast to non-null type kotlin.collections.List<org.jetbrains.kotlin.incremental.classpathDiff.JavaClassSnapshot>");
                Intrinsics.checkNotNull(list4, "null cannot be cast to non-null type kotlin.collections.List<org.jetbrains.kotlin.incremental.classpathDiff.JavaClassSnapshot>");
                return programSymbolSetComputeKotlinClassChanges.plus(javaClassChangesComputer.compute(list2, list4));
            } finally {
                metrics.endMeasure(gradleBuildTime2);
            }
        } catch (Throwable th) {
            metrics.endMeasure(gradleBuildTime);
            throw th;
        }
    }

    private final ProgramSymbolSet computeKotlinClassChanges(List<? extends KotlinClassSnapshot> currentClassSnapshots, List<? extends KotlinClassSnapshot> previousClassSnapshots) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (Object obj : currentClassSnapshots) {
            if (((KotlinClassSnapshot) obj).getClassMemberLevelSnapshot() == null) {
                arrayList.add(obj);
            } else {
                arrayList2.add(obj);
            }
        }
        Pair pair = new Pair(arrayList, arrayList2);
        List<? extends KotlinClassSnapshot> list = (List) pair.component1();
        List<? extends KotlinClassSnapshot> list2 = (List) pair.component2();
        ArrayList arrayList3 = new ArrayList();
        ArrayList arrayList4 = new ArrayList();
        for (Object obj2 : previousClassSnapshots) {
            if (((KotlinClassSnapshot) obj2).getClassMemberLevelSnapshot() == null) {
                arrayList3.add(obj2);
            } else {
                arrayList4.add(obj2);
            }
        }
        Pair pair2 = new Pair(arrayList3, arrayList4);
        return computeCoarseGrainedKotlinClassChanges(list, (List) pair2.component1()).plus(computeFineGrainedKotlinClassChanges(list2, (List) pair2.component2()));
    }

    private final ProgramSymbolSet computeCoarseGrainedKotlinClassChanges(List<? extends KotlinClassSnapshot> currentClassSnapshots, List<? extends KotlinClassSnapshot> previousClassSnapshots) {
        ProgramSymbolSet.Collector collector = new ProgramSymbolSet.Collector();
        for (KotlinClassSnapshot kotlinClassSnapshot : CollectionsKt.plus((Collection) currentClassSnapshots, (Iterable) previousClassSnapshots)) {
            if (kotlinClassSnapshot instanceof RegularKotlinClassSnapshot) {
                collector.addClass(kotlinClassSnapshot.getClassId());
            } else if (kotlinClassSnapshot instanceof PackageFacadeKotlinClassSnapshot) {
                FqName packageFqName = kotlinClassSnapshot.getClassId().getPackageFqName();
                Intrinsics.checkNotNullExpressionValue(packageFqName, "getPackageFqName(...)");
                collector.addPackageMembers(packageFqName, ((PackageFacadeKotlinClassSnapshot) kotlinClassSnapshot).getPackageMemberNames());
            } else if (kotlinClassSnapshot instanceof MultifileClassKotlinClassSnapshot) {
                FqName packageFqName2 = kotlinClassSnapshot.getClassId().getPackageFqName();
                Intrinsics.checkNotNullExpressionValue(packageFqName2, "getPackageFqName(...)");
                collector.addPackageMembers(packageFqName2, ((MultifileClassKotlinClassSnapshot) kotlinClassSnapshot).getConstantNames());
            }
        }
        return collector.getResult();
    }

    private final ProgramSymbolSet computeFineGrainedKotlinClassChanges(List<? extends KotlinClassSnapshot> currentClassSnapshots, List<? extends KotlinClassSnapshot> previousClassSnapshots) throws IOException {
        File fileCreateTempDirectory = FileUtil.createTempDirectory(getClass().getSimpleName(), "_WorkingDir_" + UUID.randomUUID(), true);
        Intrinsics.checkNotNullExpressionValue(fileCreateTempDirectory, "createTempDirectory(...)");
        IncrementalJvmCache incrementalJvmCache = new IncrementalJvmCache(fileCreateTempDirectory, new IncrementalCompilationContext(null, null, false, null, null, false, false, 127, null), null);
        ChangesCollector changesCollector = new ChangesCollector();
        List<? extends KotlinClassSnapshot> list = previousClassSnapshots;
        for (KotlinClassSnapshot kotlinClassSnapshot : list) {
            KotlinClassInfo classMemberLevelSnapshot = kotlinClassSnapshot.getClassMemberLevelSnapshot();
            Intrinsics.checkNotNull(classMemberLevelSnapshot);
            incrementalJvmCache.saveClassToCache(classMemberLevelSnapshot, null, changesCollector);
            KotlinClassInfo classMemberLevelSnapshot2 = kotlinClassSnapshot.getClassMemberLevelSnapshot();
            Intrinsics.checkNotNull(classMemberLevelSnapshot2);
            incrementalJvmCache.markDirty(classMemberLevelSnapshot2.getClassName());
        }
        ChangesCollector changesCollector2 = new ChangesCollector();
        List<? extends KotlinClassSnapshot> list2 = currentClassSnapshots;
        Iterator<T> it = list2.iterator();
        while (it.hasNext()) {
            KotlinClassInfo classMemberLevelSnapshot3 = ((KotlinClassSnapshot) it.next()).getClassMemberLevelSnapshot();
            Intrinsics.checkNotNull(classMemberLevelSnapshot3);
            incrementalJvmCache.saveClassToCache(classMemberLevelSnapshot3, null, changesCollector2);
        }
        incrementalJvmCache.clearCacheForRemovedClasses(changesCollector2);
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(list2, 10)), 16));
        for (KotlinClassSnapshot kotlinClassSnapshot2 : list2) {
            ClassId classId = kotlinClassSnapshot2.getClassId();
            KotlinClassInfo classMemberLevelSnapshot4 = kotlinClassSnapshot2.getClassMemberLevelSnapshot();
            Intrinsics.checkNotNull(classMemberLevelSnapshot4);
            Pair pair = TuplesKt.to(classId, classMemberLevelSnapshot4.getExtraInfo().getClassSnapshotExcludingMembers());
            linkedHashMap.put(pair.getFirst(), pair.getSecond());
        }
        LinkedHashMap linkedHashMap2 = new LinkedHashMap();
        for (Map.Entry entry : linkedHashMap.entrySet()) {
            if (entry.getValue() != null) {
                linkedHashMap2.put(entry.getKey(), entry.getValue());
            }
        }
        for (KotlinClassSnapshot kotlinClassSnapshot3 : list) {
            ClassId classId2 = kotlinClassSnapshot3.getClassId();
            Long l = (Long) linkedHashMap2.get(classId2);
            KotlinClassInfo classMemberLevelSnapshot5 = kotlinClassSnapshot3.getClassMemberLevelSnapshot();
            Intrinsics.checkNotNull(classMemberLevelSnapshot5);
            Long classSnapshotExcludingMembers = classMemberLevelSnapshot5.getExtraInfo().getClassSnapshotExcludingMembers();
            if (l != null && classSnapshotExcludingMembers != null && !Intrinsics.areEqual(l, classSnapshotExcludingMembers)) {
                FqName fqNameAsSingleFqName = classId2.asSingleFqName();
                Intrinsics.checkNotNullExpressionValue(fqNameAsSingleFqName, "asSingleFqName(...)");
                changesCollector2.collectSignature(fqNameAsSingleFqName, false);
            }
        }
        DirtyData changedSymbols = BuildUtilKt.getChangedSymbols(changesCollector2, DoNothingICReporter.INSTANCE);
        FilesKt.deleteRecursively(fileCreateTempDirectory);
        return toProgramSymbols(changedSymbols, SequencesKt.asIterable(SequencesKt.plus(CollectionsKt.asSequence(list), CollectionsKt.asSequence(list2))));
    }

    private final ProgramSymbolSet toProgramSymbols(DirtyData dirtyData, Iterable<? extends AccessibleClassSnapshot> iterable) {
        String companionObjectName;
        ProgramSymbolSet programSymbolSet = ProgramSymbolKt.toProgramSymbolSet(dirtyData.getDirtyLookupSymbols(), iterable);
        ChangesEither.Known changesEither = ProgramSymbolKt.toChangesEither(programSymbolSet);
        Pair pair = TuplesKt.to(CollectionsKt.toSet(changesEither.getLookupSymbols()), CollectionsKt.toSet(changesEither.getFqNames()));
        Set set = (Set) pair.component1();
        Set set2 = (Set) pair.component2();
        Set mutableSet = CollectionsKt.toMutableSet(dirtyData.getDirtyLookupSymbols());
        mutableSet.removeAll(set);
        Set mutableSet2 = CollectionsKt.toMutableSet(dirtyData.getDirtyClassesFqNames());
        mutableSet2.addAll(dirtyData.getDirtyClassesFqNamesForceRecompile());
        mutableSet2.removeAll(set2);
        if (mutableSet.isEmpty() && mutableSet2.isEmpty()) {
            return programSymbolSet;
        }
        Set<ClassId> classes = programSymbolSet.getClasses();
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        Iterator<T> it = classes.iterator();
        while (it.hasNext()) {
            linkedHashSet.add(((ClassId) it.next()).asSingleFqName());
        }
        final LinkedHashSet linkedHashSet2 = linkedHashSet;
        Set set3 = mutableSet;
        CollectionsKt.removeAll(set3, new Function1<LookupSymbol, Boolean>() { // from class: org.jetbrains.kotlin.incremental.classpathDiff.ClasspathChangesComputer.toProgramSymbols.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(LookupSymbol it2) {
                Intrinsics.checkNotNullParameter(it2, "it");
                return Boolean.valueOf(linkedHashSet2.contains(new FqName(it2.getScope())));
            }
        });
        LinkedHashSet linkedHashSet3 = new LinkedHashSet();
        for (AccessibleClassSnapshot accessibleClassSnapshot : iterable) {
            FqName fqNameAsSingleFqName = null;
            RegularKotlinClassSnapshot regularKotlinClassSnapshot = accessibleClassSnapshot instanceof RegularKotlinClassSnapshot ? (RegularKotlinClassSnapshot) accessibleClassSnapshot : null;
            if (regularKotlinClassSnapshot != null && (companionObjectName = regularKotlinClassSnapshot.getCompanionObjectName()) != null) {
                fqNameAsSingleFqName = accessibleClassSnapshot.getClassId().createNestedClassId(Name.identifier(companionObjectName)).asSingleFqName();
            }
            if (fqNameAsSingleFqName != null) {
                linkedHashSet3.add(fqNameAsSingleFqName);
            }
        }
        final LinkedHashSet linkedHashSet4 = linkedHashSet3;
        CollectionsKt.removeAll(set3, new Function1<LookupSymbol, Boolean>() { // from class: org.jetbrains.kotlin.incremental.classpathDiff.ClasspathChangesComputer.toProgramSymbols.3
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(LookupSymbol it2) {
                Intrinsics.checkNotNullParameter(it2, "it");
                return Boolean.valueOf(linkedHashSet4.contains(new FqName(it2.getScope())));
            }
        });
        mutableSet2.removeAll(linkedHashSet4);
        ArrayList arrayList = new ArrayList();
        for (AccessibleClassSnapshot accessibleClassSnapshot2 : iterable) {
            AccessibleClassSnapshot accessibleClassSnapshot3 = accessibleClassSnapshot2;
            if ((accessibleClassSnapshot3 instanceof RegularKotlinClassSnapshot) || (accessibleClassSnapshot3 instanceof JavaClassSnapshot)) {
                arrayList.add(accessibleClassSnapshot2);
            }
        }
        LinkedHashSet linkedHashSet5 = new LinkedHashSet();
        Iterator it2 = arrayList.iterator();
        while (it2.hasNext()) {
            linkedHashSet5.add(((AccessibleClassSnapshot) it2.next()).getClassId().asSingleFqName());
        }
        final LinkedHashSet linkedHashSet6 = linkedHashSet5;
        CollectionsKt.removeAll(set3, new Function1<LookupSymbol, Boolean>() { // from class: org.jetbrains.kotlin.incremental.classpathDiff.ClasspathChangesComputer.toProgramSymbols.4
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(LookupSymbol it3) {
                Intrinsics.checkNotNullParameter(it3, "it");
                return Boolean.valueOf(Intrinsics.areEqual(it3.getName(), SamConversionResolverKt.getSAM_LOOKUP_NAME().asString()) && !linkedHashSet6.contains(new FqName(it3.getScope())));
            }
        });
        ArrayList arrayList2 = new ArrayList();
        for (AccessibleClassSnapshot accessibleClassSnapshot4 : iterable) {
            AccessibleClassSnapshot accessibleClassSnapshot5 = accessibleClassSnapshot4;
            if ((accessibleClassSnapshot5 instanceof KotlinClassSnapshot) && !(accessibleClassSnapshot5 instanceof RegularKotlinClassSnapshot)) {
                arrayList2.add(accessibleClassSnapshot4);
            }
        }
        LinkedHashSet linkedHashSet7 = new LinkedHashSet();
        Iterator it3 = arrayList2.iterator();
        while (it3.hasNext()) {
            linkedHashSet7.add(((AccessibleClassSnapshot) it3.next()).getClassId().asSingleFqName());
        }
        final LinkedHashSet linkedHashSet8 = linkedHashSet7;
        CollectionsKt.removeAll(set3, new Function1<LookupSymbol, Boolean>() { // from class: org.jetbrains.kotlin.incremental.classpathDiff.ClasspathChangesComputer.toProgramSymbols.5
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(LookupSymbol it4) {
                Intrinsics.checkNotNullParameter(it4, "it");
                return Boolean.valueOf(linkedHashSet8.contains(new FqName(it4.getScope()).child(Name.identifier(it4.getName()))));
            }
        });
        mutableSet2.removeAll(linkedHashSet8);
        if (!mutableSet.isEmpty()) {
            throw new IllegalStateException(("The following LookupSymbols are not yet converted to ProgramSymbols: " + CollectionsKt.joinToString$default(set3, ", ", null, null, 0, null, null, 62, null)).toString());
        }
        if (mutableSet2.isEmpty()) {
            return programSymbolSet;
        }
        throw new IllegalStateException(("The following FqNames can't be derived from DirtyData.dirtyLookupSymbols: " + CollectionsKt.joinToString$default(mutableSet2, ", ", null, null, 0, null, null, 62, null) + ".\nDirtyData = " + dirtyData).toString());
    }
}
