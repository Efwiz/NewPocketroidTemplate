package org.jetbrains.kotlin.build.report.statistics.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.Grouping;
import kotlin.collections.GroupingKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.io.CloseableKt;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import kotlin.text.Charsets;
import org.catrobat.catroid.common.Constants;
import org.jetbrains.kotlin.build.report.metrics.BuildAttribute;
import org.jetbrains.kotlin.build.report.metrics.BuildAttributeKind;
import org.jetbrains.kotlin.build.report.metrics.BuildPerformanceMetric;
import org.jetbrains.kotlin.build.report.metrics.BuildTime;
import org.jetbrains.kotlin.build.report.metrics.ValueType;
import org.jetbrains.kotlin.build.report.statistics.BuildStartParameters;
import org.jetbrains.kotlin.build.report.statistics.CompileStatisticsData;
import org.jetbrains.kotlin.build.report.statistics.FormattingUtilsKt;
import org.jetbrains.kotlin.buildtools.api.KotlinLogger;

/* JADX INFO: compiled from: FileReportService.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000h\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0012\u0018\u0000 1*\b\b\u0000\u0010\u0001*\u00020\u0002*\b\b\u0001\u0010\u0003*\u00020\u00042\u00020\u0005:\u00011B\u001d\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b¢\u0006\u0002\u0010\fJ\u0016\u0010\u000f\u001a\u00020\u00102\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012H\u0002J\u001e\u0010\u0014\u001a\u00020\u00102\u0006\u0010\u0015\u001a\u00020\u00162\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00190\u0018H\u0002J\u001e\u0010\u001a\u001a\u00020\u00102\u0014\u0010\u001b\u001a\u0010\u0012\u0006\b\u0001\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u001d0\u001cH\u0002J8\u0010\u001e\u001a\u00020\u00102\u0018\u0010\u001f\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010 0\u00182\u0006\u0010\u0015\u001a\u00020\u00162\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00190\u0018H\u0002J\u001e\u0010!\u001a\u00020\u00102\u0014\u0010\"\u001a\u0010\u0012\u0006\b\u0001\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u001d0\u001cH\u0002J4\u0010#\u001a\u00020\u00102\u0014\u0010$\u001a\u0010\u0012\u0004\u0012\u00020\u0019\u0012\u0004\u0012\u00020\u001d\u0018\u00010\u001c2\u0014\u0010%\u001a\u0010\u0012\u0004\u0012\u00020\u0019\u0012\u0004\u0012\u00020\u001d\u0018\u00010\u001cH\u0002J|\u0010\b\u001a\u00020\u00102\u0014\u0010&\u001a\u0010\u0012\u0006\b\u0001\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u001d0\u001c2\u0014\u0010'\u001a\u0010\u0012\u0006\b\u0001\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u001d0\u001c2\f\u0010(\u001a\b\u0012\u0004\u0012\u00020\u00130\u00122\u0016\b\u0002\u0010$\u001a\u0010\u0012\u0004\u0012\u00020\u0019\u0012\u0004\u0012\u00020\u001d\u0018\u00010\u001c2\u0016\b\u0002\u0010%\u001a\u0010\u0012\u0004\u0012\u00020\u0019\u0012\u0004\u0012\u00020\u001d\u0018\u00010\u001c2\b\b\u0002\u0010)\u001a\u00020\tH\u0002J\u0018\u0010*\u001a\u00020\u00102\u0006\u0010+\u001a\u00020\u00042\u0006\u0010,\u001a\u00020\u001dH\u0002J0\u0010-\u001a\u00020\u0010\"\b\b\u0002\u0010\u0001*\u00020\u0002\"\b\b\u0003\u0010\u0003*\u00020\u00042\u0012\u0010\u001f\u001a\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00030 H\u0002J\"\u0010.\u001a\u00020\u00102\u0018\u0010\u001f\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010 0\u0012H\u0002J\"\u0010/\u001a\u00020\u00102\u0018\u0010\u001f\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010 0\u0018H\u0002J8\u00100\u001a\u00020\u00102\u0018\u0010\u001f\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010 0\u00182\u0006\u0010\u0015\u001a\u00020\u00162\u000e\b\u0002\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00190\u0018R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000¨\u00062"}, d2 = {"Lorg/jetbrains/kotlin/build/report/statistics/file/FileReportService;", "B", "Lorg/jetbrains/kotlin/build/report/metrics/BuildTime;", "P", "Lorg/jetbrains/kotlin/build/report/metrics/BuildPerformanceMetric;", "Ljava/io/Serializable;", "outputFile", "Ljava/io/File;", "printMetrics", "", "logger", "Lorg/jetbrains/kotlin/buildtools/api/KotlinLogger;", "(Ljava/io/File;ZLorg/jetbrains/kotlin/buildtools/api/KotlinLogger;)V", "p", "Lorg/jetbrains/kotlin/build/report/statistics/file/Printer;", "printBuildAttributes", "", "buildAttributes", "", "Lorg/jetbrains/kotlin/build/report/metrics/BuildAttribute;", "printBuildInfo", "startParameters", "Lorg/jetbrains/kotlin/build/report/statistics/BuildStartParameters;", "failureMessages", "", "", "printBuildPerformanceMetrics", "buildMetrics", "", "", "printBuildReport", "statisticsData", "Lorg/jetbrains/kotlin/build/report/statistics/CompileStatisticsData;", "printBuildTimes", "buildTimes", "printGcMetrics", "gcTimeMetrics", "gcCountMetrics", "buildTimesMetrics", "performanceMetrics", "nonIncrementalAttributes", "aggregatedMetric", "printSizeMetric", "sizeMetric", "value", "printTaskLog", "printTaskOverview", "printTasksLog", "process", "Companion", "kotlin-build-statistics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FileReportService<B extends BuildTime, P extends BuildPerformanceMetric> implements Serializable {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final SimpleDateFormat formatter;
    private final KotlinLogger logger;
    private final File outputFile;
    private Printer p;
    private final boolean printMetrics;

    /* JADX INFO: compiled from: FileReportService.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ValueType.values().length];
            try {
                iArr[ValueType.BYTES.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ValueType.NUMBER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[ValueType.NANOSECONDS.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[ValueType.MILLISECONDS.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[ValueType.TIME.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public FileReportService(File outputFile, boolean z, KotlinLogger logger) {
        Intrinsics.checkNotNullParameter(outputFile, "outputFile");
        Intrinsics.checkNotNullParameter(logger, "logger");
        this.outputFile = outputFile;
        this.printMetrics = z;
        this.logger = logger;
    }

    /* JADX INFO: compiled from: FileReportService.kt */
    @Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002Jj\u0010\u0005\u001a\u00020\u0006\"\b\b\u0002\u0010\u0007*\u00020\b\"\b\b\u0003\u0010\t*\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0018\u0010\u0011\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0007\u0012\u0004\u0012\u0002H\t0\u00130\u00122\u0006\u0010\u0014\u001a\u00020\u00152\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u000e0\u00122\u0006\u0010\u0017\u001a\u00020\u0018R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/build/report/statistics/file/FileReportService$Companion;", "", "()V", "formatter", "Ljava/text/SimpleDateFormat;", "reportBuildStatInFile", "", "B", "Lorg/jetbrains/kotlin/build/report/metrics/BuildTime;", "P", "Lorg/jetbrains/kotlin/build/report/metrics/BuildPerformanceMetric;", "buildReportDir", "Ljava/io/File;", "projectName", "", "includeMetricsInReport", "", "buildData", "", "Lorg/jetbrains/kotlin/build/report/statistics/CompileStatisticsData;", "startParameters", "Lorg/jetbrains/kotlin/build/report/statistics/BuildStartParameters;", "failureMessages", "logger", "Lorg/jetbrains/kotlin/buildtools/api/KotlinLogger;", "kotlin-build-statistics"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final <B extends BuildTime, P extends BuildPerformanceMetric> void reportBuildStatInFile(File buildReportDir, String projectName, boolean includeMetricsInReport, List<? extends CompileStatisticsData<B, P>> buildData, BuildStartParameters startParameters, List<String> failureMessages, KotlinLogger logger) {
            Intrinsics.checkNotNullParameter(buildReportDir, "buildReportDir");
            Intrinsics.checkNotNullParameter(projectName, "projectName");
            Intrinsics.checkNotNullParameter(buildData, "buildData");
            Intrinsics.checkNotNullParameter(startParameters, "startParameters");
            Intrinsics.checkNotNullParameter(failureMessages, "failureMessages");
            Intrinsics.checkNotNullParameter(logger, "logger");
            new FileReportService(FilesKt.resolve(buildReportDir, projectName + "-build-" + new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(Calendar.getInstance().getTime()) + Constants.TEXT_FILE_EXTENSION), includeMetricsInReport, logger).process(buildData, startParameters, failureMessages);
        }
    }

    static {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        formatter = simpleDateFormat;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ void process$default(FileReportService fileReportService, List list, BuildStartParameters buildStartParameters, List list2, int i, Object obj) {
        if ((i & 4) != 0) {
            list2 = CollectionsKt.emptyList();
        }
        fileReportService.process(list, buildStartParameters, list2);
    }

    public final void process(List<? extends CompileStatisticsData<B, P>> statisticsData, BuildStartParameters startParameters, List<String> failureMessages) {
        Intrinsics.checkNotNullParameter(statisticsData, "statisticsData");
        Intrinsics.checkNotNullParameter(startParameters, "startParameters");
        Intrinsics.checkNotNullParameter(failureMessages, "failureMessages");
        String string = this.outputFile.toPath().toUri().toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        try {
            this.outputFile.getParentFile().mkdirs();
            if (this.outputFile.getParentFile().exists() && this.outputFile.getParentFile().isDirectory()) {
                Writer outputStreamWriter = new OutputStreamWriter(new FileOutputStream(this.outputFile), Charsets.UTF_8);
                BufferedWriter bufferedWriter = outputStreamWriter instanceof BufferedWriter ? (BufferedWriter) outputStreamWriter : new BufferedWriter(outputStreamWriter, 8192);
                try {
                    this.p = new Printer(bufferedWriter, null, null, 6, null);
                    printBuildReport(statisticsData, startParameters, failureMessages);
                    Unit unit = Unit.INSTANCE;
                    CloseableKt.closeFinally(bufferedWriter, null);
                    this.logger.lifecycle("Kotlin build report is written to " + string);
                    return;
                } catch (Throwable th) {
                    try {
                        throw th;
                    } catch (Throwable th2) {
                        CloseableKt.closeFinally(bufferedWriter, th);
                        throw th2;
                    }
                }
            }
            KotlinLogger.error$default(this.logger, "Kotlin build report cannot be created: '" + this.outputFile + ".parentFile' is a file or do not have permissions to create", null, 2, null);
        } catch (Exception e) {
            this.logger.error("Could not write Kotlin build report to " + string, e);
        }
    }

    private final void printBuildReport(List<? extends CompileStatisticsData<B, P>> statisticsData, BuildStartParameters startParameters, List<String> failureMessages) {
        printBuildInfo(startParameters, failureMessages);
        if (this.printMetrics && !statisticsData.isEmpty()) {
            List<? extends CompileStatisticsData<B, P>> list = statisticsData;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                arrayList.add(((CompileStatisticsData) it.next()).getBuildTimesMetrics());
            }
            Iterator it2 = arrayList.iterator();
            if (!it2.hasNext()) {
                throw new UnsupportedOperationException("Empty collection can't be reduced.");
            }
            Object next = it2.next();
            while (it2.hasNext()) {
                Map map = (Map) it2.next();
                Map map2 = (Map) next;
                Set setPlus = SetsKt.plus(map2.keySet(), (Iterable) map.keySet());
                Object linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(setPlus, 10)), 16));
                for (Object obj : setPlus) {
                    LinkedHashMap linkedHashMap2 = (Map) linkedHashMap;
                    BuildTime buildTime = (BuildTime) obj;
                    Long l = (Long) map2.get(buildTime);
                    long jLongValue = l != null ? l.longValue() : 0L;
                    Long l2 = (Long) map.get(buildTime);
                    linkedHashMap2.put(obj, Long.valueOf(jLongValue + (l2 != null ? l2.longValue() : 0L)));
                }
                next = (Map) linkedHashMap;
            }
            Map map3 = (Map) next;
            ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
            Iterator<T> it3 = list.iterator();
            while (it3.hasNext()) {
                arrayList2.add(((CompileStatisticsData) it3.next()).getPerformanceMetrics());
            }
            Iterator it4 = arrayList2.iterator();
            if (!it4.hasNext()) {
                throw new UnsupportedOperationException("Empty collection can't be reduced.");
            }
            Object next2 = it4.next();
            while (it4.hasNext()) {
                Map map4 = (Map) it4.next();
                Map map5 = (Map) next2;
                Set setPlus2 = SetsKt.plus(map5.keySet(), (Iterable) map4.keySet());
                Object linkedHashMap3 = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(setPlus2, 10)), 16));
                for (Object obj2 : setPlus2) {
                    LinkedHashMap linkedHashMap4 = (Map) linkedHashMap3;
                    BuildPerformanceMetric buildPerformanceMetric = (BuildPerformanceMetric) obj2;
                    Long l3 = (Long) map5.get(buildPerformanceMetric);
                    long jLongValue2 = l3 != null ? l3.longValue() : 0L;
                    Long l4 = (Long) map4.get(buildPerformanceMetric);
                    linkedHashMap4.put(obj2, Long.valueOf(jLongValue2 + (l4 != null ? l4.longValue() : 0L)));
                }
                next2 = (Map) linkedHashMap3;
            }
            Map map6 = (Map) next2;
            ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
            Iterator<T> it5 = list.iterator();
            while (it5.hasNext()) {
                arrayList3.add(CollectionsKt.asSequence(((CompileStatisticsData) it5.next()).getNonIncrementalAttributes()));
            }
            Iterator it6 = arrayList3.iterator();
            if (!it6.hasNext()) {
                throw new UnsupportedOperationException("Empty collection can't be reduced.");
            }
            Object next3 = it6.next();
            while (it6.hasNext()) {
                next3 = SequencesKt.plus((Sequence) next3, (Sequence) it6.next());
            }
            printMetrics$default(this, map3, map6, SequencesKt.toList((Sequence) next3), null, null, true, 24, null);
            Printer printer = this.p;
            if (printer == null) {
                Intrinsics.throwUninitializedPropertyAccessException("p");
                printer = null;
            }
            printer.println(new String[0]);
        }
        printTaskOverview(statisticsData);
        printTasksLog(statisticsData);
    }

    private final void printBuildInfo(BuildStartParameters startParameters, List<String> failureMessages) {
        Printer printer = this.p;
        Printer printer2 = null;
        if (printer == null) {
            Intrinsics.throwUninitializedPropertyAccessException("p");
            printer = null;
        }
        printer.println("Gradle start parameters:");
        printer.pushIndent();
        try {
            Printer printer3 = this.p;
            if (printer3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("p");
                printer3 = null;
            }
            printer3.println("tasks = " + startParameters.getTasks());
            Printer printer4 = this.p;
            if (printer4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("p");
                printer4 = null;
            }
            printer4.println("excluded tasks = " + startParameters.getExcludedTasks());
            Printer printer5 = this.p;
            if (printer5 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("p");
                printer5 = null;
            }
            printer5.println("current dir = " + startParameters.getCurrentDir());
            Printer printer6 = this.p;
            if (printer6 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("p");
                printer6 = null;
            }
            printer6.println("project properties args = " + startParameters.getProjectProperties());
            Printer printer7 = this.p;
            if (printer7 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("p");
                printer7 = null;
            }
            printer7.println("system properties args = " + startParameters.getSystemProperties());
            Unit unit = Unit.INSTANCE;
            printer.popIndent();
            Printer printer8 = this.p;
            if (printer8 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("p");
                printer8 = null;
            }
            printer8.println(new String[0]);
            if (failureMessages.isEmpty()) {
                return;
            }
            Printer printer9 = this.p;
            if (printer9 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("p");
                printer9 = null;
            }
            printer9.println("Build failed: " + failureMessages);
            Printer printer10 = this.p;
            if (printer10 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("p");
            } else {
                printer2 = printer10;
            }
            printer2.println(new String[0]);
        } catch (Throwable th) {
            printer.popIndent();
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    static /* synthetic */ void printMetrics$default(FileReportService fileReportService, Map map, Map map2, Collection collection, Map map3, Map map4, boolean z, int i, Object obj) {
        if ((i & 8) != 0) {
            map3 = MapsKt.emptyMap();
        }
        Map map5 = map3;
        if ((i & 16) != 0) {
            map4 = MapsKt.emptyMap();
        }
        Map map6 = map4;
        if ((i & 32) != 0) {
            z = false;
        }
        fileReportService.printMetrics(map, map2, collection, map5, map6, z);
    }

    private final void printMetrics(Map<? extends BuildTime, Long> buildTimesMetrics, Map<? extends BuildPerformanceMetric, Long> performanceMetrics, Collection<? extends BuildAttribute> nonIncrementalAttributes, Map<String, Long> gcTimeMetrics, Map<String, Long> gcCountMetrics, boolean aggregatedMetric) {
        printBuildTimes(buildTimesMetrics);
        Printer printer = null;
        if (aggregatedMetric) {
            Printer printer2 = this.p;
            if (printer2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("p");
                printer2 = null;
            }
            printer2.println(new String[0]);
        }
        printBuildPerformanceMetrics(performanceMetrics);
        if (aggregatedMetric) {
            Printer printer3 = this.p;
            if (printer3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("p");
            } else {
                printer = printer3;
            }
            printer.println(new String[0]);
        }
        printBuildAttributes(nonIncrementalAttributes);
        if (aggregatedMetric) {
            return;
        }
        printGcMetrics(gcTimeMetrics, gcCountMetrics);
    }

    private final void printGcMetrics(Map<String, Long> gcTimeMetrics, Map<String, Long> gcCountMetrics) {
        Long l;
        Set<String> setKeySet;
        Set<String> setKeySet2;
        HashSet<String> hashSet = new HashSet();
        if (gcCountMetrics != null && (setKeySet2 = gcCountMetrics.keySet()) != null) {
            hashSet.addAll(setKeySet2);
        }
        if (gcTimeMetrics != null && (setKeySet = gcTimeMetrics.keySet()) != null) {
            hashSet.addAll(setKeySet);
        }
        if (hashSet.isEmpty()) {
            return;
        }
        Printer printer = this.p;
        if (printer == null) {
            Intrinsics.throwUninitializedPropertyAccessException("p");
            printer = null;
        }
        printer.println("GC metrics:");
        printer.pushIndent();
        try {
            for (String str : hashSet) {
                Printer printer2 = this.p;
                if (printer2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("p");
                    printer2 = null;
                }
                printer2.println(str + ':');
                Printer printer3 = this.p;
                if (printer3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("p");
                    printer3 = null;
                }
                printer3.pushIndent();
                if (gcCountMetrics != null) {
                    try {
                        Long l2 = gcCountMetrics.get(str);
                        if (l2 != null) {
                            long jLongValue = l2.longValue();
                            Printer printer4 = this.p;
                            if (printer4 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("p");
                                printer4 = null;
                            }
                            printer4.println("GC count: " + jLongValue);
                        }
                    } catch (Throwable th) {
                        printer3.popIndent();
                        throw th;
                    }
                }
                if (gcTimeMetrics != null && (l = gcTimeMetrics.get(str)) != null) {
                    long jLongValue2 = l.longValue();
                    Printer printer5 = this.p;
                    if (printer5 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("p");
                        printer5 = null;
                    }
                    printer5.println("GC time: " + FormattingUtilsKt.formatTime(jLongValue2));
                }
                printer3.popIndent();
            }
            Unit unit = Unit.INSTANCE;
            printer.popIndent();
        } catch (Throwable th2) {
            printer.popIndent();
            throw th2;
        }
    }

    private final void printBuildTimes(Map<? extends BuildTime, Long> buildTimes) {
        if (buildTimes.isEmpty()) {
            return;
        }
        Printer printer = this.p;
        Printer printer2 = null;
        if (printer == null) {
            Intrinsics.throwUninitializedPropertyAccessException("p");
            printer = null;
        }
        printer.println("Time metrics:");
        Printer printer3 = this.p;
        if (printer3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("p");
        } else {
            printer2 = printer3;
        }
        printer2.pushIndent();
        try {
            HashSet hashSet = new HashSet();
            for (BuildTime buildTime : ((BuildTime) CollectionsKt.first(buildTimes.keySet())).getAllMetrics()) {
                if (buildTime.getParent() == null) {
                    printBuildTimes$lambda$20$printBuildTime(hashSet, buildTimes, this, buildTime);
                }
            }
            Unit unit = Unit.INSTANCE;
        } finally {
            printer2.popIndent();
        }
    }

    private static final <B extends BuildTime, P extends BuildPerformanceMetric> void printBuildTimes$lambda$20$printBuildTime(HashSet<BuildTime> hashSet, Map<? extends BuildTime, Long> map, FileReportService<B, P> fileReportService, BuildTime buildTime) {
        if (hashSet.add(buildTime)) {
            Long l = map.get(buildTime);
            if (l != null) {
                Printer printer = ((FileReportService) fileReportService).p;
                Printer printer2 = null;
                if (printer == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("p");
                    printer = null;
                }
                printer.println(buildTime.getReadableString() + ": " + FormattingUtilsKt.formatTime(l.longValue()));
                Printer printer3 = ((FileReportService) fileReportService).p;
                if (printer3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("p");
                } else {
                    printer2 = printer3;
                }
                printer2.pushIndent();
                try {
                    List<BuildTime> listChildren = buildTime.children();
                    if (listChildren != null) {
                        Iterator<T> it = listChildren.iterator();
                        while (it.hasNext()) {
                            printBuildTimes$lambda$20$printBuildTime(hashSet, map, fileReportService, (BuildTime) it.next());
                        }
                        Unit unit = Unit.INSTANCE;
                    }
                    return;
                } finally {
                    printer2.popIndent();
                }
            }
            List<BuildTime> listChildren2 = buildTime.children();
            if (listChildren2 != null) {
                Iterator<T> it2 = listChildren2.iterator();
                while (it2.hasNext()) {
                    printBuildTimes$lambda$20$printBuildTime(hashSet, map, fileReportService, (BuildTime) it2.next());
                }
            }
        }
    }

    private final void printBuildPerformanceMetrics(Map<? extends BuildPerformanceMetric, Long> buildMetrics) {
        if (buildMetrics.isEmpty()) {
            return;
        }
        Printer printer = this.p;
        if (printer == null) {
            Intrinsics.throwUninitializedPropertyAccessException("p");
            printer = null;
        }
        printer.println("Size metrics:");
        printer.pushIndent();
        try {
            for (BuildPerformanceMetric buildPerformanceMetric : ((BuildPerformanceMetric) CollectionsKt.first(buildMetrics.keySet())).getAllMetrics()) {
                Long l = buildMetrics.get(buildPerformanceMetric);
                if (l != null) {
                    printSizeMetric(buildPerformanceMetric, l.longValue());
                }
            }
            Unit unit = Unit.INSTANCE;
        } finally {
            printer.popIndent();
        }
    }

    private static final int printSizeMetric$numberOfAncestors(BuildPerformanceMetric buildPerformanceMetric) {
        int i = 0;
        for (BuildPerformanceMetric parent = buildPerformanceMetric.getParent(); parent != null; parent = parent.getParent()) {
            i++;
        }
        return i;
    }

    private final void printSizeMetric(BuildPerformanceMetric sizeMetric, long value) {
        int iPrintSizeMetric$numberOfAncestors = printSizeMetric$numberOfAncestors(sizeMetric);
        int i = 0;
        while (true) {
            Printer printer = null;
            if (i >= iPrintSizeMetric$numberOfAncestors) {
                break;
            }
            Printer printer2 = this.p;
            if (printer2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("p");
            } else {
                printer = printer2;
            }
            printer.pushIndent();
            i++;
        }
        int i2 = WhenMappings.$EnumSwitchMapping$0[sizeMetric.getType().ordinal()];
        if (i2 == 1) {
            Printer printer3 = this.p;
            if (printer3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("p");
                printer3 = null;
            }
            printer3.println(sizeMetric.getReadableString() + ": " + FormattingUtilsKt.formatSize(value));
        } else if (i2 == 2) {
            Printer printer4 = this.p;
            if (printer4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("p");
                printer4 = null;
            }
            printer4.println(sizeMetric.getReadableString() + ": " + value);
        } else if (i2 == 3) {
            Printer printer5 = this.p;
            if (printer5 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("p");
                printer5 = null;
            }
            printer5.println(sizeMetric.getReadableString() + ": " + value);
        } else if (i2 == 4) {
            Printer printer6 = this.p;
            if (printer6 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("p");
                printer6 = null;
            }
            printer6.println(sizeMetric.getReadableString() + ": " + FormattingUtilsKt.formatTime(value));
        } else if (i2 == 5) {
            Printer printer7 = this.p;
            if (printer7 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("p");
                printer7 = null;
            }
            printer7.println(sizeMetric.getReadableString() + ": " + formatter.format(Long.valueOf(value)));
        }
        for (int i3 = 0; i3 < iPrintSizeMetric$numberOfAncestors; i3++) {
            Printer printer8 = this.p;
            if (printer8 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("p");
                printer8 = null;
            }
            printer8.popIndent();
        }
    }

    private final void printBuildAttributes(Collection<? extends BuildAttribute> buildAttributes) {
        if (buildAttributes.isEmpty()) {
            return;
        }
        final Collection<? extends BuildAttribute> collection = buildAttributes;
        Map mapEachCount = GroupingKt.eachCount(new Grouping<BuildAttribute, BuildAttribute>() { // from class: org.jetbrains.kotlin.build.report.statistics.file.FileReportService$printBuildAttributes$$inlined$groupingBy$1
            @Override // kotlin.collections.Grouping
            public Iterator<BuildAttribute> sourceIterator() {
                return collection.iterator();
            }

            @Override // kotlin.collections.Grouping
            public BuildAttribute keyOf(BuildAttribute element) {
                return element;
            }
        });
        Printer printer = this.p;
        if (printer == null) {
            Intrinsics.throwUninitializedPropertyAccessException("p");
            printer = null;
        }
        printer.println("Build attributes:");
        printer.pushIndent();
        try {
            Set setEntrySet = mapEachCount.entrySet();
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            for (Object obj : setEntrySet) {
                BuildAttributeKind kind = ((BuildAttribute) ((Map.Entry) obj).getKey()).getKind();
                Object obj2 = linkedHashMap.get(kind);
                if (obj2 == null) {
                    obj2 = (List) new ArrayList();
                    linkedHashMap.put(kind, obj2);
                }
                ((List) obj2).add(obj);
            }
            for (Map.Entry entry : MapsKt.toSortedMap(linkedHashMap).entrySet()) {
                BuildAttributeKind buildAttributeKind = (BuildAttributeKind) entry.getKey();
                List list = (List) entry.getValue();
                Printer printer2 = this.p;
                if (printer2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("p");
                    printer2 = null;
                }
                String strName = buildAttributeKind.name();
                Intrinsics.checkNotNull(list);
                List<Map.Entry> list2 = list;
                LinkedHashMap linkedHashMap2 = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(list2, 10)), 16));
                for (Map.Entry entry2 : list2) {
                    Pair pair = TuplesKt.to(((BuildAttribute) entry2.getKey()).getReadableString(), Integer.valueOf(((Number) entry2.getValue()).intValue()));
                    linkedHashMap2.put(pair.getFirst(), pair.getSecond());
                }
                PlainTextBuildReportWriterKt.printMap(printer2, strName, linkedHashMap2);
            }
            Unit unit = Unit.INSTANCE;
        } finally {
            printer.popIndent();
        }
    }

    private final void printTaskOverview(Collection<? extends CompileStatisticsData<B, P>> statisticsData) {
        Printer printer;
        Printer printer2;
        ArrayList arrayList = new ArrayList();
        long j = 0;
        long j2 = 0;
        for (CompileStatisticsData<B, P> compileStatisticsData : statisticsData) {
            long durationMs = compileStatisticsData.getDurationMs();
            j += durationMs;
            if (Intrinsics.areEqual((Object) compileStatisticsData.getFromKotlinPlugin(), (Object) true)) {
                j2 += durationMs;
                arrayList.add(compileStatisticsData);
            }
        }
        if (arrayList.isEmpty()) {
            Printer printer3 = this.p;
            if (printer3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("p");
                printer2 = null;
            } else {
                printer2 = printer3;
            }
            printer2.println("No Kotlin task was run");
            return;
        }
        double d = j2;
        double d2 = 100;
        String strAsString = FormattingUtilsKt.asString((d / j) * d2, 1);
        Printer printer4 = this.p;
        if (printer4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("p");
            printer4 = null;
        }
        printer4.println("Total time for Kotlin tasks: " + FormattingUtilsKt.formatTime(j2) + " (" + strAsString + " % of all tasks time)");
        TextTable textTable = new TextTable("Time", "% of Kotlin time", "Task");
        for (CompileStatisticsData compileStatisticsData2 : CollectionsKt.sortedWith(arrayList, ComparisonsKt.compareBy(new Function1<CompileStatisticsData<B, P>, Comparable<?>>() { // from class: org.jetbrains.kotlin.build.report.statistics.file.FileReportService.printTaskOverview.1
            @Override // kotlin.jvm.functions.Function1
            public final Comparable<?> invoke(CompileStatisticsData<B, P> it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Long.valueOf(-it.getDurationMs());
            }
        }, new Function1<CompileStatisticsData<B, P>, Comparable<?>>() { // from class: org.jetbrains.kotlin.build.report.statistics.file.FileReportService.printTaskOverview.2
            @Override // kotlin.jvm.functions.Function1
            public final Comparable<?> invoke(CompileStatisticsData<B, P> it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Long.valueOf(it.getStartTimeMs());
            }
        }))) {
            long durationMs2 = compileStatisticsData2.getDurationMs();
            textTable.addRow(FormattingUtilsKt.formatTime(durationMs2), FormattingUtilsKt.asString((durationMs2 / d) * d2, 1) + " %", compileStatisticsData2.getTaskName());
        }
        Printer printer5 = this.p;
        if (printer5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("p");
            printer5 = null;
        }
        textTable.printTo(printer5);
        Printer printer6 = this.p;
        if (printer6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("p");
            printer = null;
        } else {
            printer = printer6;
        }
        printer.println(new String[0]);
    }

    private final void printTasksLog(List<? extends CompileStatisticsData<B, P>> statisticsData) {
        Iterator it = CollectionsKt.sortedWith(statisticsData, ComparisonsKt.compareBy(new Function1<CompileStatisticsData<B, P>, Comparable<?>>() { // from class: org.jetbrains.kotlin.build.report.statistics.file.FileReportService.printTasksLog.1
            @Override // kotlin.jvm.functions.Function1
            public final Comparable<?> invoke(CompileStatisticsData<B, P> it2) {
                Intrinsics.checkNotNullParameter(it2, "it");
                return Long.valueOf(-it2.getDurationMs());
            }
        }, new Function1<CompileStatisticsData<B, P>, Comparable<?>>() { // from class: org.jetbrains.kotlin.build.report.statistics.file.FileReportService.printTasksLog.2
            @Override // kotlin.jvm.functions.Function1
            public final Comparable<?> invoke(CompileStatisticsData<B, P> it2) {
                Intrinsics.checkNotNullParameter(it2, "it");
                return Long.valueOf(it2.getStartTimeMs());
            }
        })).iterator();
        while (it.hasNext()) {
            printTaskLog((CompileStatisticsData) it.next());
            Printer printer = this.p;
            if (printer == null) {
                Intrinsics.throwUninitializedPropertyAccessException("p");
                printer = null;
            }
            printer.println(new String[0]);
        }
    }

    private final <B extends BuildTime, P extends BuildPerformanceMetric> void printTaskLog(CompileStatisticsData<B, P> statisticsData) {
        String skipMessage = statisticsData.getSkipMessage();
        if (skipMessage != null) {
            Printer printer = this.p;
            if (printer == null) {
                Intrinsics.throwUninitializedPropertyAccessException("p");
                printer = null;
            }
            printer.println("Task '" + statisticsData.getTaskName() + "' was skipped: " + skipMessage);
        } else {
            Printer printer2 = this.p;
            if (printer2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("p");
                printer2 = null;
            }
            printer2.println("Task '" + statisticsData.getTaskName() + "' finished in " + FormattingUtilsKt.formatTime(statisticsData.getDurationMs()));
        }
        String kotlinLanguageVersion = statisticsData.getKotlinLanguageVersion();
        if (kotlinLanguageVersion != null) {
            Printer printer3 = this.p;
            if (printer3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("p");
                printer3 = null;
            }
            printer3.println("Task info:");
            printer3.pushIndent();
            try {
                Printer printer4 = this.p;
                if (printer4 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("p");
                    printer4 = null;
                }
                printer4.println("Kotlin language version: " + kotlinLanguageVersion);
                Unit unit = Unit.INSTANCE;
                printer3.popIndent();
            } catch (Throwable th) {
                printer3.popIndent();
                throw th;
            }
        }
        if (!statisticsData.getIcLogLines().isEmpty()) {
            Printer printer5 = this.p;
            if (printer5 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("p");
                printer5 = null;
            }
            String str = "Compilation log for task '" + statisticsData.getTaskName() + "':";
            if (str != null) {
                printer5.println(str);
            }
            printer5.pushIndent();
            try {
                for (String str2 : statisticsData.getIcLogLines()) {
                    Printer printer6 = this.p;
                    if (printer6 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("p");
                        printer6 = null;
                    }
                    printer6.println(str2);
                }
                Unit unit2 = Unit.INSTANCE;
                printer5.popIndent();
            } catch (Throwable th2) {
                printer5.popIndent();
                throw th2;
            }
        }
        if (this.printMetrics) {
            printMetrics$default(this, statisticsData.getBuildTimesMetrics(), statisticsData.getPerformanceMetrics(), statisticsData.getNonIncrementalAttributes(), statisticsData.getGcTimeMetrics(), statisticsData.getGcCountMetrics(), false, 32, null);
        }
    }
}
