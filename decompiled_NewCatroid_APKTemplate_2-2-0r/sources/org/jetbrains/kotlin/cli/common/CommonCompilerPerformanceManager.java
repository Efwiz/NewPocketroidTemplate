package org.jetbrains.kotlin.cli.common;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.File;
import java.lang.management.CompilationMXBean;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.util.PerformanceCounter;

/* JADX INFO: compiled from: CommonCompilerPerformanceManager.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\b\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0017\b&\u0018\u00002\u00020\u0001:\u0001GB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010&\u001a\u00020'2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0018\u001a\u00020\bH\u0016J\b\u0010(\u001a\u00020)H\u0002J\u0010\u0010*\u001a\u00020\u00062\u0006\u0010+\u001a\u00020\u0006H\u0002J\u000e\u0010,\u001a\u00020'2\u0006\u0010-\u001a\u00020.J\u0006\u0010/\u001a\u00020'J\f\u00100\u001a\b\u0012\u0004\u0012\u00020\u001d01J\u0006\u00102\u001a\u00020\u0003J\b\u00103\u001a\u00020'H\u0016J\b\u00104\u001a\u00020'H\u0016J\b\u00105\u001a\u00020'H\u0016J \u00106\u001a\u00020'2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0018\u001a\u00020\b2\u0006\u0010%\u001a\u00020\u0003H\u0016J\b\u00107\u001a\u00020'H\u0016J\b\u00108\u001a\u00020'H\u0016J\b\u00109\u001a\u00020'H\u0016J\b\u0010:\u001a\u00020'H\u0016J\b\u0010;\u001a\u00020'H\u0016J\b\u0010<\u001a\u00020'H\u0016J\b\u0010=\u001a\u00020'H\u0016J\b\u0010>\u001a\u00020'H\u0016J\u0018\u0010?\u001a\u00020'2\u0006\u0010@\u001a\u00020\b2\u0006\u0010A\u001a\u00020\bH\u0016J\b\u0010B\u001a\u00020'H\u0002J\b\u0010C\u001a\u00020'H\u0002J\b\u0010D\u001a\u00020'H\u0002J\b\u0010E\u001a\u00020'H\u0002J\u0006\u0010F\u001a\u00020\u0003R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u001e\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0084\u000e¢\u0006\u0010\n\u0002\u0010\r\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u000e\u0010\u000e\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0013\u001a\u00020\u0014X\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u001e\u0010\u0018\u001a\u0004\u0018\u00010\bX\u0084\u000e¢\u0006\u0010\n\u0002\u0010\r\u001a\u0004\b\u0019\u0010\n\"\u0004\b\u001a\u0010\fR \u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001d0\u001cX\u0084\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b\u001e\u0010\u001f\u001a\u0004\b \u0010!R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\"\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020$0#X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010%\u001a\u0004\u0018\u00010\u0003X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006H"}, d2 = {"Lorg/jetbrains/kotlin/cli/common/CommonCompilerPerformanceManager;", "", "presentableName", "", "(Ljava/lang/String;)V", "analysisStart", "", "files", "", "getFiles", "()Ljava/lang/Integer;", "setFiles", "(Ljava/lang/Integer;)V", "Ljava/lang/Integer;", "generationStart", "initStartNanos", "irGenerationStart", "irLoweringStart", "irTranslationStart", "isEnabled", "", "()Z", "setEnabled", "(Z)V", "lines", "getLines", "setLines", "measurements", "", "Lorg/jetbrains/kotlin/cli/common/PerformanceMeasurement;", "getMeasurements$annotations", "()V", "getMeasurements", "()Ljava/util/List;", "startGCData", "", "Lorg/jetbrains/kotlin/cli/common/CommonCompilerPerformanceManager$GCData;", "targetDescription", "addSourcesStats", "", "createPerformanceReport", "", "deltaTime", "start", "dumpPerformanceReport", FirebaseAnalytics.Param.DESTINATION, "Ljava/io/File;", "enableCollectingPerformanceStatistics", "getMeasurementResults", "", "getTargetInfo", "notifyAnalysisFinished", "notifyAnalysisStarted", "notifyCompilationFinished", "notifyCompilerInitialized", "notifyGenerationFinished", "notifyGenerationStarted", "notifyIRGenerationFinished", "notifyIRGenerationStarted", "notifyIRLoweringFinished", "notifyIRLoweringStarted", "notifyIRTranslationFinished", "notifyIRTranslationStarted", "notifyRepeat", "total", "number", "recordGcTime", "recordInitializationTime", "recordJitCompilationTime", "recordPerfCountersMeasurements", "renderCompilerPerformance", "GCData", "cli-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class CommonCompilerPerformanceManager {
    private long analysisStart;
    private Integer files;
    private long generationStart;
    private long initStartNanos;
    private long irGenerationStart;
    private long irLoweringStart;
    private long irTranslationStart;
    private boolean isEnabled;
    private Integer lines;
    private final List<PerformanceMeasurement> measurements;
    private final String presentableName;
    private Map<String, GCData> startGCData;
    private String targetDescription;

    protected static /* synthetic */ void getMeasurements$annotations() {
    }

    public void notifyRepeat(int total, int number) {
    }

    public CommonCompilerPerformanceManager(String presentableName) {
        Intrinsics.checkNotNullParameter(presentableName, "presentableName");
        this.presentableName = presentableName;
        this.measurements = new ArrayList();
        this.initStartNanos = PerformanceCounter.INSTANCE.currentTime();
        this.startGCData = new LinkedHashMap();
    }

    protected final List<PerformanceMeasurement> getMeasurements() {
        return this.measurements;
    }

    /* JADX INFO: renamed from: isEnabled, reason: from getter */
    protected final boolean getIsEnabled() {
        return this.isEnabled;
    }

    protected final void setEnabled(boolean z) {
        this.isEnabled = z;
    }

    protected final Integer getFiles() {
        return this.files;
    }

    protected final void setFiles(Integer num) {
        this.files = num;
    }

    protected final Integer getLines() {
        return this.lines;
    }

    protected final void setLines(Integer num) {
        this.lines = num;
    }

    public final String getTargetInfo() {
        return this.targetDescription + ", " + this.files + " files (" + this.lines + " lines)";
    }

    public final List<PerformanceMeasurement> getMeasurementResults() {
        return this.measurements;
    }

    public final void enableCollectingPerformanceStatistics() {
        this.isEnabled = true;
        PerformanceCounter.INSTANCE.setTimeCounterEnabled(true);
        List<GarbageCollectorMXBean> garbageCollectorMXBeans = ManagementFactory.getGarbageCollectorMXBeans();
        Intrinsics.checkNotNullExpressionValue(garbageCollectorMXBeans, "getGarbageCollectorMXBeans(...)");
        Map<String, GCData> map = this.startGCData;
        for (GarbageCollectorMXBean garbageCollectorMXBean : garbageCollectorMXBeans) {
            String name2 = garbageCollectorMXBean.getName();
            Intrinsics.checkNotNull(garbageCollectorMXBean);
            Pair pair = TuplesKt.to(name2, new GCData(garbageCollectorMXBean));
            map.put((String) pair.getFirst(), (GCData) pair.getSecond());
        }
    }

    private final long deltaTime(long start) {
        return PerformanceCounter.INSTANCE.currentTime() - start;
    }

    public void notifyCompilerInitialized(int files, int lines, String targetDescription) {
        Intrinsics.checkNotNullParameter(targetDescription, "targetDescription");
        if (this.isEnabled) {
            recordInitializationTime();
            this.files = Integer.valueOf(files);
            this.lines = Integer.valueOf(lines);
            this.targetDescription = targetDescription;
        }
    }

    public void notifyCompilationFinished() {
        if (this.isEnabled) {
            recordGcTime();
            recordJitCompilationTime();
            recordPerfCountersMeasurements();
        }
    }

    public void addSourcesStats(int files, int lines) {
        if (this.isEnabled) {
            Integer num = this.files;
            this.files = num != null ? Integer.valueOf(num.intValue() + files) : Integer.valueOf(files);
            Integer num2 = this.lines;
            this.lines = num2 != null ? Integer.valueOf(num2.intValue() + lines) : Integer.valueOf(lines);
        }
    }

    public void notifyAnalysisStarted() {
        this.analysisStart = PerformanceCounter.INSTANCE.currentTime();
    }

    public void notifyAnalysisFinished() {
        this.measurements.add(new CodeAnalysisMeasurement(this.lines, TimeUnit.NANOSECONDS.toMillis(PerformanceCounter.INSTANCE.currentTime() - this.analysisStart)));
    }

    public void notifyGenerationStarted() {
        this.generationStart = PerformanceCounter.INSTANCE.currentTime();
    }

    public void notifyGenerationFinished() {
        this.measurements.add(new CodeGenerationMeasurement(this.lines, TimeUnit.NANOSECONDS.toMillis(PerformanceCounter.INSTANCE.currentTime() - this.generationStart)));
    }

    public void notifyIRTranslationStarted() {
        this.irTranslationStart = PerformanceCounter.INSTANCE.currentTime();
    }

    public void notifyIRTranslationFinished() {
        this.measurements.add(new IRMeasurement(this.lines, TimeUnit.NANOSECONDS.toMillis(deltaTime(this.irTranslationStart)), IRMeasurement.Kind.TRANSLATION));
    }

    public void notifyIRLoweringStarted() {
        this.irLoweringStart = PerformanceCounter.INSTANCE.currentTime();
    }

    public void notifyIRLoweringFinished() {
        this.measurements.add(new IRMeasurement(this.lines, TimeUnit.NANOSECONDS.toMillis(deltaTime(this.irLoweringStart)), IRMeasurement.Kind.LOWERING));
    }

    public void notifyIRGenerationStarted() {
        this.irGenerationStart = PerformanceCounter.INSTANCE.currentTime();
    }

    public void notifyIRGenerationFinished() {
        this.measurements.add(new IRMeasurement(this.lines, TimeUnit.NANOSECONDS.toMillis(deltaTime(this.irGenerationStart)), IRMeasurement.Kind.GENERATION));
    }

    public final void dumpPerformanceReport(File destination) {
        Intrinsics.checkNotNullParameter(destination, "destination");
        FilesKt.writeBytes(destination, createPerformanceReport());
    }

    private final void recordGcTime() {
        if (this.isEnabled) {
            List<GarbageCollectorMXBean> garbageCollectorMXBeans = ManagementFactory.getGarbageCollectorMXBeans();
            Intrinsics.checkNotNullExpressionValue(garbageCollectorMXBeans, "getGarbageCollectorMXBeans(...)");
            for (GarbageCollectorMXBean garbageCollectorMXBean : garbageCollectorMXBeans) {
                GCData gCData = this.startGCData.get(garbageCollectorMXBean.getName());
                long collectionCount = 0;
                long collectionTime = gCData != null ? gCData.getCollectionTime() : 0L;
                if (gCData != null) {
                    collectionCount = gCData.getCollectionCount();
                }
                List<PerformanceMeasurement> list = this.measurements;
                String name2 = garbageCollectorMXBean.getName();
                Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                list.add(new GarbageCollectionMeasurement(name2, garbageCollectorMXBean.getCollectionTime() - collectionTime, garbageCollectorMXBean.getCollectionCount() - collectionCount));
            }
        }
    }

    private final void recordJitCompilationTime() {
        CompilationMXBean compilationMXBean;
        if (this.isEnabled && (compilationMXBean = ManagementFactory.getCompilationMXBean()) != null) {
            this.measurements.add(new JitCompilationMeasurement(compilationMXBean.getTotalCompilationTime()));
        }
    }

    private final void recordInitializationTime() {
        this.measurements.add(new CompilerInitializationMeasurement(TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - this.initStartNanos)));
    }

    private final void recordPerfCountersMeasurements() {
        PerformanceCounter.INSTANCE.report(new Function1<String, Unit>() { // from class: org.jetbrains.kotlin.cli.common.CommonCompilerPerformanceManager.recordPerfCountersMeasurements.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(String str) {
                invoke2(str);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(String s) {
                Intrinsics.checkNotNullParameter(s, "s");
                CommonCompilerPerformanceManager.this.getMeasurements().add(new PerformanceCounterMeasurement(s));
            }
        });
    }

    private final byte[] createPerformanceReport() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.presentableName + " performance report\n");
        List<PerformanceMeasurement> list = this.measurements;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(((PerformanceMeasurement) it.next()).getCounterReport());
        }
        Iterator it2 = CollectionsKt.sorted(arrayList).iterator();
        while (it2.hasNext()) {
            sb.append(((String) it2.next()) + '\n');
        }
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        byte[] bytes = string.getBytes(Charsets.UTF_8);
        Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
        return bytes;
    }

    /* JADX INFO: compiled from: CommonCompilerPerformanceManager.kt */
    @Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0082\b\u0018\u00002\u00020\u0001B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B\u001d\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\b¢\u0006\u0002\u0010\nJ\t\u0010\u0010\u001a\u00020\u0006HÆ\u0003J\t\u0010\u0011\u001a\u00020\bHÆ\u0003J\t\u0010\u0012\u001a\u00020\bHÆ\u0003J'\u0010\u0013\u001a\u00020\u00002\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\bHÆ\u0001J\u0013\u0010\u0014\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0017\u001a\u00020\u0018HÖ\u0001J\t\u0010\u0019\u001a\u00020\u0006HÖ\u0001R\u0011\u0010\t\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\fR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/cli/common/CommonCompilerPerformanceManager$GCData;", "", "bean", "Ljava/lang/management/GarbageCollectorMXBean;", "(Ljava/lang/management/GarbageCollectorMXBean;)V", "name", "", "collectionTime", "", "collectionCount", "(Ljava/lang/String;JJ)V", "getCollectionCount", "()J", "getCollectionTime", "getName", "()Ljava/lang/String;", "component1", "component2", "component3", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "cli-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final /* data */ class GCData {
        private final long collectionCount;
        private final long collectionTime;
        private final String name;

        public static /* synthetic */ GCData copy$default(GCData gCData, String str, long j, long j2, int i, Object obj) {
            if ((i & 1) != 0) {
                str = gCData.name;
            }
            if ((i & 2) != 0) {
                j = gCData.collectionTime;
            }
            long j3 = j;
            if ((i & 4) != 0) {
                j2 = gCData.collectionCount;
            }
            return gCData.copy(str, j3, j2);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final String getName() {
            return this.name;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final long getCollectionTime() {
            return this.collectionTime;
        }

        /* JADX INFO: renamed from: component3, reason: from getter */
        public final long getCollectionCount() {
            return this.collectionCount;
        }

        public final GCData copy(String name2, long collectionTime, long collectionCount) {
            Intrinsics.checkNotNullParameter(name2, "name");
            return new GCData(name2, collectionTime, collectionCount);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof GCData)) {
                return false;
            }
            GCData gCData = (GCData) other;
            return Intrinsics.areEqual(this.name, gCData.name) && this.collectionTime == gCData.collectionTime && this.collectionCount == gCData.collectionCount;
        }

        public int hashCode() {
            return (((this.name.hashCode() * 31) + Long.hashCode(this.collectionTime)) * 31) + Long.hashCode(this.collectionCount);
        }

        public String toString() {
            return "GCData(name=" + this.name + ", collectionTime=" + this.collectionTime + ", collectionCount=" + this.collectionCount + ')';
        }

        public GCData(String name2, long j, long j2) {
            Intrinsics.checkNotNullParameter(name2, "name");
            this.name = name2;
            this.collectionTime = j;
            this.collectionCount = j2;
        }

        public final long getCollectionCount() {
            return this.collectionCount;
        }

        public final long getCollectionTime() {
            return this.collectionTime;
        }

        public final String getName() {
            return this.name;
        }

        /* JADX WARN: Illegal instructions before constructor call */
        public GCData(GarbageCollectorMXBean bean) {
            Intrinsics.checkNotNullParameter(bean, "bean");
            String name2 = bean.getName();
            Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
            this(name2, bean.getCollectionTime(), bean.getCollectionCount());
        }
    }

    public final String renderCompilerPerformance() {
        List<PerformanceMeasurement> measurementResults = getMeasurementResults();
        ArrayList arrayList = new ArrayList();
        for (Object obj : measurementResults) {
            PerformanceMeasurement performanceMeasurement = (PerformanceMeasurement) obj;
            if ((performanceMeasurement instanceof CompilerInitializationMeasurement) || (performanceMeasurement instanceof CodeAnalysisMeasurement) || (performanceMeasurement instanceof CodeGenerationMeasurement) || (performanceMeasurement instanceof PerformanceCounterMeasurement)) {
                arrayList.add(obj);
            }
        }
        return "Compiler perf stats:\n" + CollectionsKt.joinToString$default(arrayList, "\n", null, null, 0, null, new Function1<PerformanceMeasurement, CharSequence>() { // from class: org.jetbrains.kotlin.cli.common.CommonCompilerPerformanceManager.renderCompilerPerformance.1
            @Override // kotlin.jvm.functions.Function1
            public final CharSequence invoke(PerformanceMeasurement it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return "  " + it.getCounterReport();
            }
        }, 30, null);
    }
}
