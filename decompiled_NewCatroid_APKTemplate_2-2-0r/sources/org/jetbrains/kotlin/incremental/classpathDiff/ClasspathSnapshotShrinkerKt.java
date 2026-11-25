package org.jetbrains.kotlin.incremental.classpathDiff;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import org.jetbrains.kotlin.build.report.ICReporterKt;
import org.jetbrains.kotlin.build.report.metrics.GradleBuildPerformanceMetric;
import org.jetbrains.kotlin.build.report.metrics.GradleBuildTime;
import org.jetbrains.kotlin.incremental.ClasspathChanges;
import org.jetbrains.kotlin.incremental.LookupStorage;
import org.jetbrains.kotlin.incremental.storage.ExternalizersKt;
import org.jetbrains.kotlin.incremental.storage.ListExternalizer;
import org.jetbrains.kotlin.incremental.storage.LookupSymbolKey;

/* JADX INFO: compiled from: ClasspathSnapshotShrinker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00008\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001aH\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u000e\u0010\b\u001a\n\u0012\u0004\u0012\u00020\n\u0018\u00010\t2\u000e\u0010\u000b\u001a\n\u0012\u0004\u0012\u00020\n\u0018\u00010\t2\u0006\u0010\f\u001a\u00020\rH\u0000\u001a\u0012\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\t*\u00020\u0010H\u0002\u001a\u0012\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\n0\t*\u00020\u0010H\u0000¨\u0006\u0012"}, d2 = {"shrinkAndSaveClasspathSnapshot", "", "compilationWasIncremental", "", "classpathChanges", "Lorg/jetbrains/kotlin/incremental/ClasspathChanges$ClasspathSnapshotEnabled;", "lookupStorage", "Lorg/jetbrains/kotlin/incremental/LookupStorage;", "currentClasspathSnapshot", "", "Lorg/jetbrains/kotlin/incremental/classpathDiff/AccessibleClassSnapshot;", "shrunkCurrentClasspathAgainstPreviousLookups", "reporter", "Lorg/jetbrains/kotlin/incremental/classpathDiff/ClasspathSnapshotBuildReporter;", "getNonDuplicateClassSnapshots", "Lorg/jetbrains/kotlin/incremental/classpathDiff/ClassSnapshot;", "Lorg/jetbrains/kotlin/incremental/classpathDiff/ClasspathSnapshot;", "removeDuplicateAndInaccessibleClasses", "incremental-compilation-impl"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ClasspathSnapshotShrinkerKt {
    public static final List<AccessibleClassSnapshot> removeDuplicateAndInaccessibleClasses(ClasspathSnapshot classpathSnapshot) {
        Intrinsics.checkNotNullParameter(classpathSnapshot, "<this>");
        List<ClassSnapshot> nonDuplicateClassSnapshots = getNonDuplicateClassSnapshots(classpathSnapshot);
        ArrayList arrayList = new ArrayList();
        for (Object obj : nonDuplicateClassSnapshots) {
            if (obj instanceof AccessibleClassSnapshot) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }

    private static final List<ClassSnapshot> getNonDuplicateClassSnapshots(ClasspathSnapshot classpathSnapshot) {
        Iterator<T> it = classpathSnapshot.getClasspathEntrySnapshots().iterator();
        int size = 0;
        while (it.hasNext()) {
            size += ((ClasspathEntrySnapshot) it.next()).getClassSnapshots().size();
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap(size);
        Iterator<ClasspathEntrySnapshot> it2 = classpathSnapshot.getClasspathEntrySnapshots().iterator();
        while (it2.hasNext()) {
            for (Map.Entry<String, ClassSnapshot> entry : it2.next().getClassSnapshots().entrySet()) {
                linkedHashMap.putIfAbsent(entry.getKey(), entry.getValue());
            }
        }
        Collection collectionValues = linkedHashMap.values();
        Intrinsics.checkNotNullExpressionValue(collectionValues, "<get-values>(...)");
        return CollectionsKt.toList(collectionValues);
    }

    public static final void shrinkAndSaveClasspathSnapshot(boolean z, ClasspathChanges.ClasspathSnapshotEnabled classpathChanges, LookupStorage lookupStorage, List<? extends AccessibleClassSnapshot> list, List<? extends AccessibleClassSnapshot> list2, ClasspathSnapshotBuildReporter reporter) {
        final ShrinkMode.NonIncremental changedLookupsChangedClasspath;
        Pair pair;
        List<AccessibleClassSnapshot> currentClasspathSnapshot;
        List<AccessibleClassSnapshot> shrunkCurrentClasspathAgainstPreviousLookups;
        Intrinsics.checkNotNullParameter(classpathChanges, "classpathChanges");
        Intrinsics.checkNotNullParameter(lookupStorage, "lookupStorage");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        if (!z) {
            changedLookupsChangedClasspath = ShrinkMode.NonIncremental.INSTANCE;
        } else if (classpathChanges instanceof ClasspathChanges.ClasspathSnapshotEnabled.IncrementalRun.NoChanges) {
            Set<LookupSymbolKey> addedLookupSymbols = lookupStorage.getAddedLookupSymbols();
            if (addedLookupSymbols.isEmpty()) {
                changedLookupsChangedClasspath = ShrinkMode.UnchangedLookupsUnchangedClasspath.INSTANCE;
            } else {
                changedLookupsChangedClasspath = new ShrinkMode.ChangedLookupsUnchangedClasspath(addedLookupSymbols);
            }
        } else if (classpathChanges instanceof ClasspathChanges.ClasspathSnapshotEnabled.IncrementalRun.ToBeComputedByIncrementalCompiler) {
            Set<LookupSymbolKey> addedLookupSymbols2 = lookupStorage.getAddedLookupSymbols();
            if (addedLookupSymbols2.isEmpty()) {
                Intrinsics.checkNotNull(list);
                Intrinsics.checkNotNull(list2);
                changedLookupsChangedClasspath = new ShrinkMode.UnchangedLookupsChangedClasspath(list, list2);
            } else {
                Intrinsics.checkNotNull(list);
                Intrinsics.checkNotNull(list2);
                changedLookupsChangedClasspath = new ShrinkMode.ChangedLookupsChangedClasspath(addedLookupSymbols2, list, list2);
            }
        } else {
            if (!(classpathChanges instanceof ClasspathChanges.ClasspathSnapshotEnabled.NotAvailableDueToMissingClasspathSnapshot)) {
                if (!(classpathChanges instanceof ClasspathChanges.ClasspathSnapshotEnabled.NotAvailableForNonIncrementalRun)) {
                    throw new NoWhenBranchMatchedException();
                }
                throw new IllegalStateException("NotAvailableForNonIncrementalRun is not expected as compilationWasIncremental==true".toString());
            }
            changedLookupsChangedClasspath = ShrinkMode.NonIncremental.INSTANCE;
        }
        if (changedLookupsChangedClasspath instanceof ShrinkMode.UnchangedLookupsUnchangedClasspath) {
            pair = TuplesKt.to(null, null);
        } else if (changedLookupsChangedClasspath instanceof ShrinkMode.UnchangedLookupsChangedClasspath) {
            ShrinkMode.UnchangedLookupsChangedClasspath unchangedLookupsChangedClasspath = (ShrinkMode.UnchangedLookupsChangedClasspath) changedLookupsChangedClasspath;
            pair = TuplesKt.to(unchangedLookupsChangedClasspath.getCurrentClasspathSnapshot(), unchangedLookupsChangedClasspath.getShrunkCurrentClasspathAgainstPreviousLookups());
        } else if (!(changedLookupsChangedClasspath instanceof ShrinkMode.ChangedLookups)) {
            if (!(changedLookupsChangedClasspath instanceof ShrinkMode.NonIncremental)) {
                throw new NoWhenBranchMatchedException();
            }
            ClasspathSnapshotBuildReporter classpathSnapshotBuildReporter = reporter;
            GradleBuildTime gradleBuildTime = GradleBuildTime.NON_INCREMENTAL_SHRINK_CURRENT_CLASSPATH_SNAPSHOT;
            classpathSnapshotBuildReporter.startMeasure(gradleBuildTime);
            try {
                ClasspathSnapshotBuildReporter classpathSnapshotBuildReporter2 = reporter;
                GradleBuildTime gradleBuildTime2 = GradleBuildTime.NON_INCREMENTAL_LOAD_CURRENT_CLASSPATH_SNAPSHOT;
                classpathSnapshotBuildReporter2.startMeasure(gradleBuildTime2);
                try {
                    List<AccessibleClassSnapshot> listRemoveDuplicateAndInaccessibleClasses = removeDuplicateAndInaccessibleClasses(CachedClasspathSnapshotSerializer.INSTANCE.load(classpathChanges.getClasspathSnapshotFiles().getCurrentClasspathEntrySnapshotFiles(), reporter));
                    classpathSnapshotBuildReporter2.endMeasure(gradleBuildTime2);
                    pair = TuplesKt.to(listRemoveDuplicateAndInaccessibleClasses, ClasspathSnapshotShrinker.shrinkClasspath$default(ClasspathSnapshotShrinker.INSTANCE, listRemoveDuplicateAndInaccessibleClasses, lookupStorage, null, 4, null));
                    classpathSnapshotBuildReporter.endMeasure(gradleBuildTime);
                } catch (Throwable th) {
                    classpathSnapshotBuildReporter2.endMeasure(gradleBuildTime2);
                    throw th;
                }
            } catch (Throwable th2) {
                classpathSnapshotBuildReporter.endMeasure(gradleBuildTime);
                throw th2;
            }
        } else {
            ClasspathSnapshotBuildReporter classpathSnapshotBuildReporter3 = reporter;
            GradleBuildTime gradleBuildTime3 = GradleBuildTime.INCREMENTAL_SHRINK_CURRENT_CLASSPATH_SNAPSHOT;
            classpathSnapshotBuildReporter3.startMeasure(gradleBuildTime3);
            try {
                ClasspathSnapshotBuildReporter classpathSnapshotBuildReporter4 = reporter;
                GradleBuildTime gradleBuildTime4 = GradleBuildTime.INCREMENTAL_LOAD_CURRENT_CLASSPATH_SNAPSHOT;
                classpathSnapshotBuildReporter4.startMeasure(gradleBuildTime4);
                try {
                    ShrinkMode.ChangedLookups changedLookups = (ShrinkMode.ChangedLookups) changedLookupsChangedClasspath;
                    if (changedLookups instanceof ShrinkMode.ChangedLookupsUnchangedClasspath) {
                        currentClasspathSnapshot = removeDuplicateAndInaccessibleClasses(CachedClasspathSnapshotSerializer.INSTANCE.load(classpathChanges.getClasspathSnapshotFiles().getCurrentClasspathEntrySnapshotFiles(), reporter));
                    } else {
                        if (!(changedLookups instanceof ShrinkMode.ChangedLookupsChangedClasspath)) {
                            throw new NoWhenBranchMatchedException();
                        }
                        currentClasspathSnapshot = ((ShrinkMode.ChangedLookupsChangedClasspath) changedLookupsChangedClasspath).getCurrentClasspathSnapshot();
                    }
                    classpathSnapshotBuildReporter4.endMeasure(gradleBuildTime4);
                    ClasspathSnapshotBuildReporter classpathSnapshotBuildReporter5 = reporter;
                    GradleBuildTime gradleBuildTime5 = GradleBuildTime.INCREMENTAL_LOAD_SHRUNK_CURRENT_CLASSPATH_SNAPSHOT_AGAINST_PREVIOUS_LOOKUPS;
                    classpathSnapshotBuildReporter5.startMeasure(gradleBuildTime5);
                    try {
                        ShrinkMode.ChangedLookups changedLookups2 = (ShrinkMode.ChangedLookups) changedLookupsChangedClasspath;
                        if (changedLookups2 instanceof ShrinkMode.ChangedLookupsUnchangedClasspath) {
                            shrunkCurrentClasspathAgainstPreviousLookups = (List) ExternalizersKt.loadFromFile(new ListExternalizer(AccessibleClassSnapshotExternalizer.INSTANCE), classpathChanges.getClasspathSnapshotFiles().getShrunkPreviousClasspathSnapshotFile());
                        } else {
                            if (!(changedLookups2 instanceof ShrinkMode.ChangedLookupsChangedClasspath)) {
                                throw new NoWhenBranchMatchedException();
                            }
                            shrunkCurrentClasspathAgainstPreviousLookups = ((ShrinkMode.ChangedLookupsChangedClasspath) changedLookupsChangedClasspath).getShrunkCurrentClasspathAgainstPreviousLookups();
                        }
                        classpathSnapshotBuildReporter5.endMeasure(gradleBuildTime5);
                        LinkedHashSet linkedHashSet = new LinkedHashSet();
                        Iterator<T> it = shrunkCurrentClasspathAgainstPreviousLookups.iterator();
                        while (it.hasNext()) {
                            linkedHashSet.add(((AccessibleClassSnapshot) it.next()).getClassId());
                        }
                        LinkedHashSet linkedHashSet2 = linkedHashSet;
                        ArrayList arrayList = new ArrayList();
                        for (Object obj : currentClasspathSnapshot) {
                            if (!linkedHashSet2.contains(((AccessibleClassSnapshot) obj).getClassId())) {
                                arrayList.add(obj);
                            }
                        }
                        Pair pair2 = TuplesKt.to(currentClasspathSnapshot, CollectionsKt.plus((Collection) shrunkCurrentClasspathAgainstPreviousLookups, (Iterable) ClasspathSnapshotShrinker.shrinkClasses$default(ClasspathSnapshotShrinker.INSTANCE, arrayList, ((ShrinkMode.ChangedLookups) changedLookupsChangedClasspath).getAddedLookupSymbols(), null, 4, null)));
                        classpathSnapshotBuildReporter3.endMeasure(gradleBuildTime3);
                        pair = pair2;
                    } catch (Throwable th3) {
                        classpathSnapshotBuildReporter5.endMeasure(gradleBuildTime5);
                        throw th3;
                    }
                } catch (Throwable th4) {
                    classpathSnapshotBuildReporter4.endMeasure(gradleBuildTime4);
                    throw th4;
                }
            } catch (Throwable th5) {
                classpathSnapshotBuildReporter3.endMeasure(gradleBuildTime3);
                throw th5;
            }
        }
        final List list3 = (List) pair.component1();
        final List list4 = (List) pair.component2();
        if (Intrinsics.areEqual(changedLookupsChangedClasspath, ShrinkMode.UnchangedLookupsUnchangedClasspath.INSTANCE)) {
            if (!classpathChanges.getClasspathSnapshotFiles().getShrunkPreviousClasspathSnapshotFile().isFile()) {
                throw new IllegalStateException(("File '" + classpathChanges.getClasspathSnapshotFiles().getShrunkPreviousClasspathSnapshotFile().getPath() + "' does not exist").toString());
            }
        } else {
            ClasspathSnapshotBuildReporter classpathSnapshotBuildReporter6 = reporter;
            GradleBuildTime gradleBuildTime6 = GradleBuildTime.SAVE_SHRUNK_CURRENT_CLASSPATH_SNAPSHOT;
            classpathSnapshotBuildReporter6.startMeasure(gradleBuildTime6);
            try {
                ListExternalizer listExternalizer = new ListExternalizer(AccessibleClassSnapshotExternalizer.INSTANCE);
                File shrunkPreviousClasspathSnapshotFile = classpathChanges.getClasspathSnapshotFiles().getShrunkPreviousClasspathSnapshotFile();
                Intrinsics.checkNotNull(list4);
                ExternalizersKt.saveToFile(listExternalizer, shrunkPreviousClasspathSnapshotFile, list4);
                Unit unit = Unit.INSTANCE;
                classpathSnapshotBuildReporter6.endMeasure(gradleBuildTime6);
            } catch (Throwable th6) {
                classpathSnapshotBuildReporter6.endMeasure(gradleBuildTime6);
                throw th6;
            }
        }
        ICReporterKt.debug(reporter, new Function0<String>() { // from class: org.jetbrains.kotlin.incremental.classpathDiff.ClasspathSnapshotShrinkerKt.shrinkAndSaveClasspathSnapshot.5
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final String invoke() {
                String string;
                StringBuilder sbAppend = new StringBuilder("Shrunk current classpath snapshot after compilation (shrink mode = ").append(Reflection.getOrCreateKotlinClass(changedLookupsChangedClasspath.getClass()).getSimpleName()).append(')');
                if (changedLookupsChangedClasspath instanceof ShrinkMode.UnchangedLookupsUnchangedClasspath) {
                    string = ", no updates since previous run";
                } else {
                    StringBuilder sb = new StringBuilder(", retained ");
                    List<AccessibleClassSnapshot> list5 = list4;
                    Intrinsics.checkNotNull(list5);
                    StringBuilder sbAppend2 = sb.append(list5.size()).append(" / ");
                    List<AccessibleClassSnapshot> list6 = list3;
                    Intrinsics.checkNotNull(list6);
                    string = sbAppend2.append(list6.size()).append(" classes").toString();
                }
                return sbAppend.append(string).toString();
            }
        });
        reporter.addMetric(GradleBuildPerformanceMetric.SHRINK_AND_SAVE_CLASSPATH_SNAPSHOT_EXECUTION_COUNT, 1L);
        reporter.addMetric(GradleBuildPerformanceMetric.CLASSPATH_ENTRY_COUNT, classpathChanges.getClasspathSnapshotFiles().getCurrentClasspathEntrySnapshotFiles().size());
        GradleBuildPerformanceMetric gradleBuildPerformanceMetric = GradleBuildPerformanceMetric.CLASSPATH_SNAPSHOT_SIZE;
        Iterator<T> it2 = classpathChanges.getClasspathSnapshotFiles().getCurrentClasspathEntrySnapshotFiles().iterator();
        long length = 0;
        while (it2.hasNext()) {
            length += ((File) it2.next()).length();
        }
        reporter.addMetric(gradleBuildPerformanceMetric, length);
        reporter.addMetric(GradleBuildPerformanceMetric.SHRUNK_CLASSPATH_SNAPSHOT_SIZE, classpathChanges.getClasspathSnapshotFiles().getShrunkPreviousClasspathSnapshotFile().length());
    }
}
