package org.jetbrains.kotlin.incremental.classpathDiff;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.build.report.metrics.BuildMetricsReporter;
import org.jetbrains.kotlin.build.report.metrics.GradleBuildPerformanceMetric;
import org.jetbrains.kotlin.build.report.metrics.GradleBuildTime;
import org.jetbrains.kotlin.incremental.LookupStorage;
import org.jetbrains.kotlin.incremental.LookupSymbol;
import org.jetbrains.kotlin.incremental.storage.LookupSymbolKey;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: ClasspathSnapshotShrinker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001:\u0001\u0013B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J*\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bH\u0002J*\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004H\u0002J2\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\t0\b2\b\b\u0002\u0010\u000e\u001a\u00020\u000fJ,\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0011\u001a\u00020\u00122\b\b\u0002\u0010\u000e\u001a\u00020\u000f¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/incremental/classpathDiff/ClasspathSnapshotShrinker;", "", "()V", "findReferencedClasses", "", "Lorg/jetbrains/kotlin/incremental/classpathDiff/AccessibleClassSnapshot;", "allClasses", "lookupSymbolKeys", "", "Lorg/jetbrains/kotlin/incremental/storage/LookupSymbolKey;", "findTransitivelyReferencedClasses", "referencedClasses", "shrinkClasses", "lookupSymbols", "metrics", "Lorg/jetbrains/kotlin/incremental/classpathDiff/ClasspathSnapshotShrinker$MetricsReporter;", "shrinkClasspath", "lookupStorage", "Lorg/jetbrains/kotlin/incremental/LookupStorage;", "MetricsReporter", "incremental-compilation-impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ClasspathSnapshotShrinker {
    public static final ClasspathSnapshotShrinker INSTANCE = new ClasspathSnapshotShrinker();

    private ClasspathSnapshotShrinker() {
    }

    public static /* synthetic */ List shrinkClasspath$default(ClasspathSnapshotShrinker classpathSnapshotShrinker, List list, LookupStorage lookupStorage, MetricsReporter metricsReporter, int i, Object obj) {
        if ((i & 4) != 0) {
            metricsReporter = new MetricsReporter(null, null, null, null, 15, null);
        }
        return classpathSnapshotShrinker.shrinkClasspath(list, lookupStorage, metricsReporter);
    }

    public final List<AccessibleClassSnapshot> shrinkClasspath(List<? extends AccessibleClassSnapshot> allClasses, final LookupStorage lookupStorage, MetricsReporter metrics) {
        Intrinsics.checkNotNullParameter(allClasses, "allClasses");
        Intrinsics.checkNotNullParameter(lookupStorage, "lookupStorage");
        Intrinsics.checkNotNullParameter(metrics, "metrics");
        return shrinkClasses(allClasses, (Collection) metrics.getLookupSymbols(new Function0<Collection<? extends LookupSymbolKey>>() { // from class: org.jetbrains.kotlin.incremental.classpathDiff.ClasspathSnapshotShrinker$shrinkClasspath$lookupSymbols$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Collection<? extends LookupSymbolKey> invoke() {
                return lookupStorage.getLookupSymbols();
            }
        }), metrics);
    }

    public static /* synthetic */ List shrinkClasses$default(ClasspathSnapshotShrinker classpathSnapshotShrinker, List list, Collection collection, MetricsReporter metricsReporter, int i, Object obj) {
        if ((i & 4) != 0) {
            metricsReporter = new MetricsReporter(null, null, null, null, 15, null);
        }
        return classpathSnapshotShrinker.shrinkClasses(list, collection, metricsReporter);
    }

    public final List<AccessibleClassSnapshot> shrinkClasses(final List<? extends AccessibleClassSnapshot> allClasses, final Collection<LookupSymbolKey> lookupSymbols, MetricsReporter metrics) {
        Intrinsics.checkNotNullParameter(allClasses, "allClasses");
        Intrinsics.checkNotNullParameter(lookupSymbols, "lookupSymbols");
        Intrinsics.checkNotNullParameter(metrics, "metrics");
        final List list = (List) metrics.findReferencedClasses(new Function0<List<? extends AccessibleClassSnapshot>>() { // from class: org.jetbrains.kotlin.incremental.classpathDiff.ClasspathSnapshotShrinker$shrinkClasses$referencedClasses$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<? extends AccessibleClassSnapshot> invoke() {
                return ClasspathSnapshotShrinker.INSTANCE.findReferencedClasses(allClasses, lookupSymbols);
            }
        });
        return (List) metrics.findTransitivelyReferencedClasses(new Function0<List<? extends AccessibleClassSnapshot>>() { // from class: org.jetbrains.kotlin.incremental.classpathDiff.ClasspathSnapshotShrinker.shrinkClasses.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<? extends AccessibleClassSnapshot> invoke() {
                return ClasspathSnapshotShrinker.INSTANCE.findTransitivelyReferencedClasses(allClasses, list);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:16:0x006c A[EDGE_INSN: B:16:0x006c->B:38:0x00ca BREAK  A[LOOP:1: B:34:0x00b8->B:53:?]] */
    public final List<AccessibleClassSnapshot> findReferencedClasses(List<? extends AccessibleClassSnapshot> allClasses, Collection<LookupSymbolKey> lookupSymbolKeys) {
        Set<String> constantNames;
        LookupSymbolSet lookupSymbolSet = new LookupSymbolSet(SequencesKt.asIterable(SequencesKt.map(CollectionsKt.asSequence(lookupSymbolKeys), new Function1<LookupSymbolKey, LookupSymbol>() { // from class: org.jetbrains.kotlin.incremental.classpathDiff.ClasspathSnapshotShrinker$findReferencedClasses$lookupSymbols$1
            @Override // kotlin.jvm.functions.Function1
            public final LookupSymbol invoke(LookupSymbolKey it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return new LookupSymbol(it.getName(), it.getScope());
            }
        })));
        ArrayList arrayList = new ArrayList();
        for (Object obj : allClasses) {
            AccessibleClassSnapshot accessibleClassSnapshot = (AccessibleClassSnapshot) obj;
            boolean z = true;
            boolean z2 = false;
            if (accessibleClassSnapshot instanceof RegularKotlinClassSnapshot ? true : accessibleClassSnapshot instanceof JavaClassSnapshot) {
                if (!lookupSymbolSet.contains(ProgramSymbolKt.toLookupSymbol(new ClassSymbol(accessibleClassSnapshot.getClassId())))) {
                    FqName fqNameAsSingleFqName = accessibleClassSnapshot.getClassId().asSingleFqName();
                    Intrinsics.checkNotNullExpressionValue(fqNameAsSingleFqName, "asSingleFqName(...)");
                    if (lookupSymbolSet.getLookupNamesInScope(fqNameAsSingleFqName).isEmpty()) {
                        z = false;
                        break;
                    }
                }
                z2 = z;
            } else {
                boolean z3 = accessibleClassSnapshot instanceof PackageFacadeKotlinClassSnapshot;
                if (z3 ? true : accessibleClassSnapshot instanceof MultifileClassKotlinClassSnapshot) {
                    FqName packageFqName = accessibleClassSnapshot.getClassId().getPackageFqName();
                    Intrinsics.checkNotNullExpressionValue(packageFqName, "getPackageFqName(...)");
                    Set<String> lookupNamesInScope = lookupSymbolSet.getLookupNamesInScope(packageFqName);
                    if (!lookupNamesInScope.isEmpty()) {
                        if (z3) {
                            constantNames = ((PackageFacadeKotlinClassSnapshot) accessibleClassSnapshot).getPackageMemberNames();
                        } else {
                            Intrinsics.checkNotNull(accessibleClassSnapshot, "null cannot be cast to non-null type org.jetbrains.kotlin.incremental.classpathDiff.MultifileClassKotlinClassSnapshot");
                            constantNames = ((MultifileClassKotlinClassSnapshot) accessibleClassSnapshot).getConstantNames();
                        }
                        Set<String> set = constantNames;
                        if (!(set instanceof Collection) || !set.isEmpty()) {
                            Iterator<T> it = set.iterator();
                            do {
                                if (!it.hasNext()) {
                                    z = false;
                                    break;
                                }
                            } while (!lookupNamesInScope.contains((String) it.next()));
                        } else {
                            z = false;
                            break;
                        }
                        z2 = z;
                    }
                } else {
                    throw new NoWhenBranchMatchedException();
                }
            }
            if (z2) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final List<AccessibleClassSnapshot> findTransitivelyReferencedClasses(List<? extends AccessibleClassSnapshot> allClasses, List<? extends AccessibleClassSnapshot> referencedClasses) {
        List<? extends AccessibleClassSnapshot> list = referencedClasses;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(((AccessibleClassSnapshot) it.next()).getClassId());
        }
        List<? extends AccessibleClassSnapshot> list2 = allClasses;
        Set setFindReachableNodes = BreadthFirstSearch.INSTANCE.findReachableNodes(arrayList, new ClasspathSnapshotShrinker$findTransitivelyReferencedClasses$transitivelyReferencedClassIds$1(AllImpacts.INSTANCE.getReverseResolver(list2)));
        ArrayList arrayList2 = new ArrayList();
        for (AccessibleClassSnapshot accessibleClassSnapshot : list2) {
            if (setFindReachableNodes.contains(accessibleClassSnapshot.getClassId())) {
                arrayList2.add(accessibleClassSnapshot);
            }
        }
        return arrayList2;
    }

    /* JADX INFO: compiled from: ClasspathSnapshotShrinker.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001BA\u0012\u0016\b\u0002\u0010\u0002\u001a\u0010\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0004\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0004\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0004¢\u0006\u0002\u0010\tJ\u001f\u0010\u0007\u001a\u0002H\n\"\u0004\b\u0000\u0010\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\n0\f¢\u0006\u0002\u0010\rJ\u001f\u0010\b\u001a\u0002H\n\"\u0004\b\u0000\u0010\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\n0\f¢\u0006\u0002\u0010\rJ\u001f\u0010\u0006\u001a\u0002H\n\"\u0004\b\u0000\u0010\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\n0\f¢\u0006\u0002\u0010\rR\u0010\u0010\u0007\u001a\u0004\u0018\u00010\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\u0002\u001a\u0010\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/incremental/classpathDiff/ClasspathSnapshotShrinker$MetricsReporter;", "", "metrics", "Lorg/jetbrains/kotlin/build/report/metrics/BuildMetricsReporter;", "Lorg/jetbrains/kotlin/build/report/metrics/GradleBuildTime;", "Lorg/jetbrains/kotlin/build/report/metrics/GradleBuildPerformanceMetric;", "getLookupSymbols", "findReferencedClasses", "findTransitivelyReferencedClasses", "(Lorg/jetbrains/kotlin/build/report/metrics/BuildMetricsReporter;Lorg/jetbrains/kotlin/build/report/metrics/GradleBuildTime;Lorg/jetbrains/kotlin/build/report/metrics/GradleBuildTime;Lorg/jetbrains/kotlin/build/report/metrics/GradleBuildTime;)V", ExifInterface.GPS_DIRECTION_TRUE, "fn", "Lkotlin/Function0;", "(Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "incremental-compilation-impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class MetricsReporter {
        private final GradleBuildTime findReferencedClasses;
        private final GradleBuildTime findTransitivelyReferencedClasses;
        private final GradleBuildTime getLookupSymbols;
        private final BuildMetricsReporter<GradleBuildTime, GradleBuildPerformanceMetric> metrics;

        public MetricsReporter() {
            this(null, null, null, null, 15, null);
        }

        public MetricsReporter(BuildMetricsReporter<GradleBuildTime, GradleBuildPerformanceMetric> buildMetricsReporter, GradleBuildTime gradleBuildTime, GradleBuildTime gradleBuildTime2, GradleBuildTime gradleBuildTime3) {
            this.metrics = buildMetricsReporter;
            this.getLookupSymbols = gradleBuildTime;
            this.findReferencedClasses = gradleBuildTime2;
            this.findTransitivelyReferencedClasses = gradleBuildTime3;
        }

        public /* synthetic */ MetricsReporter(BuildMetricsReporter buildMetricsReporter, GradleBuildTime gradleBuildTime, GradleBuildTime gradleBuildTime2, GradleBuildTime gradleBuildTime3, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this((i & 1) != 0 ? null : buildMetricsReporter, (i & 2) != 0 ? null : gradleBuildTime, (i & 4) != 0 ? null : gradleBuildTime2, (i & 8) != 0 ? null : gradleBuildTime3);
        }

        public final <T> T getLookupSymbols(Function0<? extends T> fn) {
            Intrinsics.checkNotNullParameter(fn, "fn");
            BuildMetricsReporter<GradleBuildTime, GradleBuildPerformanceMetric> buildMetricsReporter = this.metrics;
            if (buildMetricsReporter != null) {
                GradleBuildTime gradleBuildTime = this.getLookupSymbols;
                Intrinsics.checkNotNull(gradleBuildTime);
                GradleBuildTime gradleBuildTime2 = gradleBuildTime;
                buildMetricsReporter.startMeasure(gradleBuildTime2);
                try {
                    T tInvoke = fn.invoke();
                    buildMetricsReporter.endMeasure(gradleBuildTime2);
                    if (tInvoke != null) {
                        return tInvoke;
                    }
                } catch (Throwable th) {
                    buildMetricsReporter.endMeasure(gradleBuildTime2);
                    throw th;
                }
            }
            return fn.invoke();
        }

        public final <T> T findReferencedClasses(Function0<? extends T> fn) {
            Intrinsics.checkNotNullParameter(fn, "fn");
            BuildMetricsReporter<GradleBuildTime, GradleBuildPerformanceMetric> buildMetricsReporter = this.metrics;
            if (buildMetricsReporter != null) {
                GradleBuildTime gradleBuildTime = this.findReferencedClasses;
                Intrinsics.checkNotNull(gradleBuildTime);
                GradleBuildTime gradleBuildTime2 = gradleBuildTime;
                buildMetricsReporter.startMeasure(gradleBuildTime2);
                try {
                    T tInvoke = fn.invoke();
                    buildMetricsReporter.endMeasure(gradleBuildTime2);
                    if (tInvoke != null) {
                        return tInvoke;
                    }
                } catch (Throwable th) {
                    buildMetricsReporter.endMeasure(gradleBuildTime2);
                    throw th;
                }
            }
            return fn.invoke();
        }

        public final <T> T findTransitivelyReferencedClasses(Function0<? extends T> fn) {
            Intrinsics.checkNotNullParameter(fn, "fn");
            BuildMetricsReporter<GradleBuildTime, GradleBuildPerformanceMetric> buildMetricsReporter = this.metrics;
            if (buildMetricsReporter != null) {
                GradleBuildTime gradleBuildTime = this.findTransitivelyReferencedClasses;
                Intrinsics.checkNotNull(gradleBuildTime);
                GradleBuildTime gradleBuildTime2 = gradleBuildTime;
                buildMetricsReporter.startMeasure(gradleBuildTime2);
                try {
                    T tInvoke = fn.invoke();
                    buildMetricsReporter.endMeasure(gradleBuildTime2);
                    if (tInvoke != null) {
                        return tInvoke;
                    }
                } catch (Throwable th) {
                    buildMetricsReporter.endMeasure(gradleBuildTime2);
                    throw th;
                }
            }
            return fn.invoke();
        }
    }
}
