package org.jetbrains.kotlin.build.report.metrics;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;

/* JADX WARN: Enum visitor error
jadx.core.utils.exceptions.JadxRuntimeException: Init of enum field 'BUNDLE_SIZE' uses external variables
	at jadx.core.dex.visitors.EnumVisitor.createEnumFieldByConstructor(EnumVisitor.java:485)
	at jadx.core.dex.visitors.EnumVisitor.processEnumFieldByField(EnumVisitor.java:399)
	at jadx.core.dex.visitors.EnumVisitor.processEnumFieldByWrappedInsn(EnumVisitor.java:364)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromFilledArray(EnumVisitor.java:349)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInsn(EnumVisitor.java:284)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInvoke(EnumVisitor.java:315)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInsn(EnumVisitor.java:288)
	at jadx.core.dex.visitors.EnumVisitor.convertToEnum(EnumVisitor.java:160)
	at jadx.core.dex.visitors.EnumVisitor.visit(EnumVisitor.java:102)
 */
/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX INFO: compiled from: BuildPerformanceMetric.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b&\b\u0086\u0081\u0002\u0018\u0000 /2\b\u0012\u0004\u0012\u00020\u00000\u00012\u00020\u0002:\u0001/B#\b\u0002\u0012\n\b\u0002\u0010\u0003\u001a\u0004\u0018\u00010\u0000\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0010\u0010\t\u001a\n\u0012\u0004\u0012\u00020\u0002\u0018\u00010\nH\u0016J\u000e\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00020\nH\u0016J\b\u0010\f\u001a\u00020\u0005H\u0016J\n\u0010\r\u001a\u0004\u0018\u00010\u0002H\u0016J\b\u0010\u000e\u001a\u00020\u0005H\u0016J\b\u0010\u000f\u001a\u00020\u0007H\u0016R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0000X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000j\u0002\b\u0010j\u0002\b\u0011j\u0002\b\u0012j\u0002\b\u0013j\u0002\b\u0014j\u0002\b\u0015j\u0002\b\u0016j\u0002\b\u0017j\u0002\b\u0018j\u0002\b\u0019j\u0002\b\u001aj\u0002\b\u001bj\u0002\b\u001cj\u0002\b\u001dj\u0002\b\u001ej\u0002\b\u001fj\u0002\b j\u0002\b!j\u0002\b\"j\u0002\b#j\u0002\b$j\u0002\b%j\u0002\b&j\u0002\b'j\u0002\b(j\u0002\b)j\u0002\b*j\u0002\b+j\u0002\b,j\u0002\b-j\u0002\b.¨\u00060"}, d2 = {"Lorg/jetbrains/kotlin/build/report/metrics/GradleBuildPerformanceMetric;", "", "Lorg/jetbrains/kotlin/build/report/metrics/BuildPerformanceMetric;", "parent", "readableString", "", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/build/report/metrics/ValueType;", "(Ljava/lang/String;ILorg/jetbrains/kotlin/build/report/metrics/GradleBuildPerformanceMetric;Ljava/lang/String;Lorg/jetbrains/kotlin/build/report/metrics/ValueType;)V", "children", "", "getAllMetrics", "getName", "getParent", "getReadableString", "getType", "CACHE_DIRECTORY_SIZE", "LOOKUP_SIZE", "SNAPSHOT_SIZE", "BUNDLE_SIZE", "DAEMON_INCREASED_MEMORY", "DAEMON_MEMORY_USAGE", "DAEMON_GC_TIME", "DAEMON_GC_COUNT", "COMPILE_ITERATION", "ANALYZED_LINES_NUMBER", "CODE_GENERATED_LINES_NUMBER", "ANALYSIS_LPS", "CODE_GENERATION_LPS", "CLASSPATH_ENTRY_SNAPSHOT_TRANSFORM_EXECUTION_COUNT", "JAR_CLASSPATH_ENTRY_SIZE", "JAR_CLASSPATH_ENTRY_SNAPSHOT_SIZE", "DIRECTORY_CLASSPATH_ENTRY_SNAPSHOT_SIZE", "COMPUTE_CLASSPATH_CHANGES_EXECUTION_COUNT", "SHRINK_AND_SAVE_CLASSPATH_SNAPSHOT_EXECUTION_COUNT", "CLASSPATH_ENTRY_COUNT", "CLASSPATH_SNAPSHOT_SIZE", "SHRUNK_CLASSPATH_SNAPSHOT_SIZE", "LOAD_CLASSPATH_SNAPSHOT_EXECUTION_COUNT", "LOAD_CLASSPATH_ENTRY_SNAPSHOT_CACHE_HITS", "LOAD_CLASSPATH_ENTRY_SNAPSHOT_CACHE_MISSES", "START_TASK_ACTION_EXECUTION", "FINISH_KOTLIN_DAEMON_EXECUTION", "CALL_KOTLIN_DAEMON", "CALL_WORKER", "START_WORKER_EXECUTION", "START_KOTLIN_DAEMON_EXECUTION", "Companion", "kotlin-build-statistics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class GradleBuildPerformanceMetric implements BuildPerformanceMetric {
    private static final /* synthetic */ EnumEntries $ENTRIES;
    private static final /* synthetic */ GradleBuildPerformanceMetric[] $VALUES;
    public static final GradleBuildPerformanceMetric ANALYSIS_LPS;
    public static final GradleBuildPerformanceMetric ANALYZED_LINES_NUMBER;
    public static final GradleBuildPerformanceMetric BUNDLE_SIZE;
    public static final GradleBuildPerformanceMetric CACHE_DIRECTORY_SIZE;
    public static final GradleBuildPerformanceMetric CALL_KOTLIN_DAEMON;
    public static final GradleBuildPerformanceMetric CALL_WORKER;
    public static final GradleBuildPerformanceMetric CLASSPATH_ENTRY_COUNT;
    public static final GradleBuildPerformanceMetric CLASSPATH_ENTRY_SNAPSHOT_TRANSFORM_EXECUTION_COUNT;
    public static final GradleBuildPerformanceMetric CLASSPATH_SNAPSHOT_SIZE;
    public static final GradleBuildPerformanceMetric CODE_GENERATED_LINES_NUMBER;
    public static final GradleBuildPerformanceMetric CODE_GENERATION_LPS;
    public static final GradleBuildPerformanceMetric COMPILE_ITERATION;
    public static final GradleBuildPerformanceMetric COMPUTE_CLASSPATH_CHANGES_EXECUTION_COUNT;

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE;
    public static final GradleBuildPerformanceMetric DAEMON_GC_COUNT;
    public static final GradleBuildPerformanceMetric DAEMON_GC_TIME;
    public static final GradleBuildPerformanceMetric DAEMON_INCREASED_MEMORY;
    public static final GradleBuildPerformanceMetric DAEMON_MEMORY_USAGE;
    public static final GradleBuildPerformanceMetric DIRECTORY_CLASSPATH_ENTRY_SNAPSHOT_SIZE;
    public static final GradleBuildPerformanceMetric FINISH_KOTLIN_DAEMON_EXECUTION;
    public static final GradleBuildPerformanceMetric JAR_CLASSPATH_ENTRY_SIZE;
    public static final GradleBuildPerformanceMetric JAR_CLASSPATH_ENTRY_SNAPSHOT_SIZE;
    public static final GradleBuildPerformanceMetric LOAD_CLASSPATH_ENTRY_SNAPSHOT_CACHE_HITS;
    public static final GradleBuildPerformanceMetric LOAD_CLASSPATH_ENTRY_SNAPSHOT_CACHE_MISSES;
    public static final GradleBuildPerformanceMetric LOAD_CLASSPATH_SNAPSHOT_EXECUTION_COUNT;
    public static final GradleBuildPerformanceMetric LOOKUP_SIZE;
    public static final GradleBuildPerformanceMetric SHRINK_AND_SAVE_CLASSPATH_SNAPSHOT_EXECUTION_COUNT;
    public static final GradleBuildPerformanceMetric SHRUNK_CLASSPATH_SNAPSHOT_SIZE;
    public static final GradleBuildPerformanceMetric SNAPSHOT_SIZE;
    public static final GradleBuildPerformanceMetric START_KOTLIN_DAEMON_EXECUTION;
    public static final GradleBuildPerformanceMetric START_TASK_ACTION_EXECUTION;
    public static final GradleBuildPerformanceMetric START_WORKER_EXECUTION;
    private static final Lazy<Map<GradleBuildPerformanceMetric, List<GradleBuildPerformanceMetric>>> children$delegate;
    public static final long serialVersionUID = 1;
    private final GradleBuildPerformanceMetric parent;
    private final String readableString;
    private final ValueType type;

    private static final /* synthetic */ GradleBuildPerformanceMetric[] $values() {
        return new GradleBuildPerformanceMetric[]{CACHE_DIRECTORY_SIZE, LOOKUP_SIZE, SNAPSHOT_SIZE, BUNDLE_SIZE, DAEMON_INCREASED_MEMORY, DAEMON_MEMORY_USAGE, DAEMON_GC_TIME, DAEMON_GC_COUNT, COMPILE_ITERATION, ANALYZED_LINES_NUMBER, CODE_GENERATED_LINES_NUMBER, ANALYSIS_LPS, CODE_GENERATION_LPS, CLASSPATH_ENTRY_SNAPSHOT_TRANSFORM_EXECUTION_COUNT, JAR_CLASSPATH_ENTRY_SIZE, JAR_CLASSPATH_ENTRY_SNAPSHOT_SIZE, DIRECTORY_CLASSPATH_ENTRY_SNAPSHOT_SIZE, COMPUTE_CLASSPATH_CHANGES_EXECUTION_COUNT, SHRINK_AND_SAVE_CLASSPATH_SNAPSHOT_EXECUTION_COUNT, CLASSPATH_ENTRY_COUNT, CLASSPATH_SNAPSHOT_SIZE, SHRUNK_CLASSPATH_SNAPSHOT_SIZE, LOAD_CLASSPATH_SNAPSHOT_EXECUTION_COUNT, LOAD_CLASSPATH_ENTRY_SNAPSHOT_CACHE_HITS, LOAD_CLASSPATH_ENTRY_SNAPSHOT_CACHE_MISSES, START_TASK_ACTION_EXECUTION, FINISH_KOTLIN_DAEMON_EXECUTION, CALL_KOTLIN_DAEMON, CALL_WORKER, START_WORKER_EXECUTION, START_KOTLIN_DAEMON_EXECUTION};
    }

    public static EnumEntries<GradleBuildPerformanceMetric> getEntries() {
        return $ENTRIES;
    }

    public static GradleBuildPerformanceMetric valueOf(String str) {
        return (GradleBuildPerformanceMetric) Enum.valueOf(GradleBuildPerformanceMetric.class, str);
    }

    public static GradleBuildPerformanceMetric[] values() {
        return (GradleBuildPerformanceMetric[]) $VALUES.clone();
    }

    private GradleBuildPerformanceMetric(String str, int i, GradleBuildPerformanceMetric gradleBuildPerformanceMetric, String str2, ValueType valueType) {
        super(str, i);
        this.parent = gradleBuildPerformanceMetric;
        this.readableString = str2;
        this.type = valueType;
    }

    /* synthetic */ GradleBuildPerformanceMetric(String str, int i, GradleBuildPerformanceMetric gradleBuildPerformanceMetric, String str2, ValueType valueType, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, i, (i2 & 1) != 0 ? null : gradleBuildPerformanceMetric, str2, valueType);
    }

    static {
        GradleBuildPerformanceMetric gradleBuildPerformanceMetric = new GradleBuildPerformanceMetric("CACHE_DIRECTORY_SIZE", 0, null, "Total size of the cache directory", ValueType.BYTES, 1, null);
        CACHE_DIRECTORY_SIZE = gradleBuildPerformanceMetric;
        LOOKUP_SIZE = new GradleBuildPerformanceMetric("LOOKUP_SIZE", 1, gradleBuildPerformanceMetric, "Lookups size", ValueType.BYTES);
        SNAPSHOT_SIZE = new GradleBuildPerformanceMetric("SNAPSHOT_SIZE", 2, gradleBuildPerformanceMetric, "ABI snapshot size", ValueType.BYTES);
        int i = 1;
        DefaultConstructorMarker defaultConstructorMarker = null;
        GradleBuildPerformanceMetric gradleBuildPerformanceMetric2 = null;
        BUNDLE_SIZE = new GradleBuildPerformanceMetric("BUNDLE_SIZE", 3, gradleBuildPerformanceMetric2, "Total size of the final bundle", ValueType.BYTES, i, defaultConstructorMarker);
        int i2 = 1;
        DefaultConstructorMarker defaultConstructorMarker2 = null;
        GradleBuildPerformanceMetric gradleBuildPerformanceMetric3 = null;
        DAEMON_INCREASED_MEMORY = new GradleBuildPerformanceMetric("DAEMON_INCREASED_MEMORY", 4, gradleBuildPerformanceMetric3, "Increase memory usage", ValueType.BYTES, i2, defaultConstructorMarker2);
        DAEMON_MEMORY_USAGE = new GradleBuildPerformanceMetric("DAEMON_MEMORY_USAGE", 5, gradleBuildPerformanceMetric2, "Total memory usage at the end of build", ValueType.BYTES, i, defaultConstructorMarker);
        DAEMON_GC_TIME = new GradleBuildPerformanceMetric("DAEMON_GC_TIME", 6, gradleBuildPerformanceMetric3, "Time spent in GC", ValueType.NANOSECONDS, i2, defaultConstructorMarker2);
        DAEMON_GC_COUNT = new GradleBuildPerformanceMetric("DAEMON_GC_COUNT", 7, gradleBuildPerformanceMetric2, "Count of GC", ValueType.NUMBER, i, defaultConstructorMarker);
        GradleBuildPerformanceMetric gradleBuildPerformanceMetric4 = new GradleBuildPerformanceMetric("COMPILE_ITERATION", 8, null, "Total compiler iteration", ValueType.NUMBER);
        COMPILE_ITERATION = gradleBuildPerformanceMetric4;
        ANALYZED_LINES_NUMBER = new GradleBuildPerformanceMetric("ANALYZED_LINES_NUMBER", 9, gradleBuildPerformanceMetric4, "Number of lines analyzed", ValueType.NUMBER);
        CODE_GENERATED_LINES_NUMBER = new GradleBuildPerformanceMetric("CODE_GENERATED_LINES_NUMBER", 10, gradleBuildPerformanceMetric4, "Number of lines for code generation", ValueType.NUMBER);
        ANALYSIS_LPS = new GradleBuildPerformanceMetric("ANALYSIS_LPS", 11, gradleBuildPerformanceMetric4, "Analysis lines per second", ValueType.NUMBER);
        CODE_GENERATION_LPS = new GradleBuildPerformanceMetric("CODE_GENERATION_LPS", 12, gradleBuildPerformanceMetric4, "Code generation lines per second", ValueType.NUMBER);
        GradleBuildPerformanceMetric gradleBuildPerformanceMetric5 = new GradleBuildPerformanceMetric("CLASSPATH_ENTRY_SNAPSHOT_TRANSFORM_EXECUTION_COUNT", 13, null, "Number of times 'ClasspathEntrySnapshotTransform' ran", ValueType.NUMBER);
        CLASSPATH_ENTRY_SNAPSHOT_TRANSFORM_EXECUTION_COUNT = gradleBuildPerformanceMetric5;
        JAR_CLASSPATH_ENTRY_SIZE = new GradleBuildPerformanceMetric("JAR_CLASSPATH_ENTRY_SIZE", 14, gradleBuildPerformanceMetric5, "Size of jar classpath entry", ValueType.BYTES);
        JAR_CLASSPATH_ENTRY_SNAPSHOT_SIZE = new GradleBuildPerformanceMetric("JAR_CLASSPATH_ENTRY_SNAPSHOT_SIZE", 15, gradleBuildPerformanceMetric5, "Size of jar classpath entry's snapshot", ValueType.BYTES);
        DIRECTORY_CLASSPATH_ENTRY_SNAPSHOT_SIZE = new GradleBuildPerformanceMetric("DIRECTORY_CLASSPATH_ENTRY_SNAPSHOT_SIZE", 16, gradleBuildPerformanceMetric5, "Size of directory classpath entry's snapshot", ValueType.BYTES);
        COMPUTE_CLASSPATH_CHANGES_EXECUTION_COUNT = new GradleBuildPerformanceMetric("COMPUTE_CLASSPATH_CHANGES_EXECUTION_COUNT", 17, null, "Number of times classpath changes are computed", ValueType.NUMBER);
        GradleBuildPerformanceMetric gradleBuildPerformanceMetric6 = new GradleBuildPerformanceMetric("SHRINK_AND_SAVE_CLASSPATH_SNAPSHOT_EXECUTION_COUNT", 18, null, "Number of times classpath snapshot is shrunk and saved after compilation", ValueType.NUMBER);
        SHRINK_AND_SAVE_CLASSPATH_SNAPSHOT_EXECUTION_COUNT = gradleBuildPerformanceMetric6;
        CLASSPATH_ENTRY_COUNT = new GradleBuildPerformanceMetric("CLASSPATH_ENTRY_COUNT", 19, gradleBuildPerformanceMetric6, "Number of classpath entries", ValueType.NUMBER);
        CLASSPATH_SNAPSHOT_SIZE = new GradleBuildPerformanceMetric("CLASSPATH_SNAPSHOT_SIZE", 20, gradleBuildPerformanceMetric6, "Size of classpath snapshot", ValueType.BYTES);
        SHRUNK_CLASSPATH_SNAPSHOT_SIZE = new GradleBuildPerformanceMetric("SHRUNK_CLASSPATH_SNAPSHOT_SIZE", 21, gradleBuildPerformanceMetric6, "Size of shrunk classpath snapshot", ValueType.BYTES);
        GradleBuildPerformanceMetric gradleBuildPerformanceMetric7 = new GradleBuildPerformanceMetric("LOAD_CLASSPATH_SNAPSHOT_EXECUTION_COUNT", 22, null, "Number of times classpath snapshot is loaded", ValueType.NUMBER);
        LOAD_CLASSPATH_SNAPSHOT_EXECUTION_COUNT = gradleBuildPerformanceMetric7;
        LOAD_CLASSPATH_ENTRY_SNAPSHOT_CACHE_HITS = new GradleBuildPerformanceMetric("LOAD_CLASSPATH_ENTRY_SNAPSHOT_CACHE_HITS", 23, gradleBuildPerformanceMetric7, "Number of cache hits when loading classpath entry snapshots", ValueType.NUMBER);
        LOAD_CLASSPATH_ENTRY_SNAPSHOT_CACHE_MISSES = new GradleBuildPerformanceMetric("LOAD_CLASSPATH_ENTRY_SNAPSHOT_CACHE_MISSES", 24, gradleBuildPerformanceMetric7, "Number of cache misses when loading classpath entry snapshots", ValueType.NUMBER);
        START_TASK_ACTION_EXECUTION = new GradleBuildPerformanceMetric("START_TASK_ACTION_EXECUTION", 25, null, "Start time of task action", ValueType.TIME, 1, null);
        int i3 = 1;
        DefaultConstructorMarker defaultConstructorMarker3 = null;
        GradleBuildPerformanceMetric gradleBuildPerformanceMetric8 = null;
        FINISH_KOTLIN_DAEMON_EXECUTION = new GradleBuildPerformanceMetric("FINISH_KOTLIN_DAEMON_EXECUTION", 26, gradleBuildPerformanceMetric8, "Finish time of kotlin daemon execution", ValueType.TIME, i3, defaultConstructorMarker3);
        int i4 = 1;
        GradleBuildPerformanceMetric gradleBuildPerformanceMetric9 = null;
        CALL_KOTLIN_DAEMON = new GradleBuildPerformanceMetric("CALL_KOTLIN_DAEMON", 27, gradleBuildPerformanceMetric9, "Finish gradle part of task execution", ValueType.NANOSECONDS, i4, defaultConstructorMarker);
        CALL_WORKER = new GradleBuildPerformanceMetric("CALL_WORKER", 28, gradleBuildPerformanceMetric8, "Worker submit time", ValueType.NANOSECONDS, i3, defaultConstructorMarker3);
        START_WORKER_EXECUTION = new GradleBuildPerformanceMetric("START_WORKER_EXECUTION", 29, gradleBuildPerformanceMetric9, "Start time of worker execution", ValueType.NANOSECONDS, i4, defaultConstructorMarker);
        START_KOTLIN_DAEMON_EXECUTION = new GradleBuildPerformanceMetric("START_KOTLIN_DAEMON_EXECUTION", 30, gradleBuildPerformanceMetric8, "Start time of kotlin daemon task execution", ValueType.NANOSECONDS, i3, defaultConstructorMarker3);
        GradleBuildPerformanceMetric[] gradleBuildPerformanceMetricArr$values = $values();
        $VALUES = gradleBuildPerformanceMetricArr$values;
        $ENTRIES = EnumEntriesKt.enumEntries(gradleBuildPerformanceMetricArr$values);
        INSTANCE = new Companion(null);
        children$delegate = LazyKt.lazy(new Function0<Map<GradleBuildPerformanceMetric, ? extends List<? extends GradleBuildPerformanceMetric>>>() { // from class: org.jetbrains.kotlin.build.report.metrics.GradleBuildPerformanceMetric$Companion$children$2
            @Override // kotlin.jvm.functions.Function0
            public final Map<GradleBuildPerformanceMetric, ? extends List<? extends GradleBuildPerformanceMetric>> invoke() {
                EnumEntries<GradleBuildPerformanceMetric> entries = GradleBuildPerformanceMetric.getEntries();
                ArrayList arrayList = new ArrayList();
                for (GradleBuildPerformanceMetric gradleBuildPerformanceMetric10 : entries) {
                    if (gradleBuildPerformanceMetric10.parent != null) {
                        arrayList.add(gradleBuildPerformanceMetric10);
                    }
                }
                LinkedHashMap linkedHashMap = new LinkedHashMap();
                for (Object obj : arrayList) {
                    GradleBuildPerformanceMetric gradleBuildPerformanceMetric11 = ((GradleBuildPerformanceMetric) obj).parent;
                    Object obj2 = linkedHashMap.get(gradleBuildPerformanceMetric11);
                    if (obj2 == null) {
                        obj2 = (List) new ArrayList();
                        linkedHashMap.put(gradleBuildPerformanceMetric11, obj2);
                    }
                    ((List) obj2).add(obj);
                }
                return linkedHashMap;
            }
        });
    }

    @Override // org.jetbrains.kotlin.build.report.metrics.BuildTime
    public String getReadableString() {
        return this.readableString;
    }

    @Override // org.jetbrains.kotlin.build.report.metrics.BuildTime
    public BuildPerformanceMetric getParent() {
        return this.parent;
    }

    @Override // org.jetbrains.kotlin.build.report.metrics.BuildPerformanceMetric
    public ValueType getType() {
        return this.type;
    }

    @Override // org.jetbrains.kotlin.build.report.metrics.BuildTime
    public String getName() {
        return name();
    }

    @Override // org.jetbrains.kotlin.build.report.metrics.BuildPerformanceMetric, org.jetbrains.kotlin.build.report.metrics.BuildTime
    public List<BuildPerformanceMetric> children() {
        return INSTANCE.getChildren().get(this);
    }

    @Override // org.jetbrains.kotlin.build.report.metrics.BuildPerformanceMetric, org.jetbrains.kotlin.build.report.metrics.BuildTime
    public List<BuildPerformanceMetric> getAllMetrics() {
        return getEntries();
    }

    /* JADX INFO: compiled from: BuildPerformanceMetric.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0005\n\u0002\u0010\t\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R/\u0010\u0003\u001a\u0016\u0012\u0006\u0012\u0004\u0018\u00010\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00060\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u0007\u0010\bR\u000e\u0010\u000b\u001a\u00020\fX\u0086T¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/build/report/metrics/GradleBuildPerformanceMetric$Companion;", "", "()V", "children", "", "Lorg/jetbrains/kotlin/build/report/metrics/GradleBuildPerformanceMetric;", "", "getChildren", "()Ljava/util/Map;", "children$delegate", "Lkotlin/Lazy;", CommonClassNames.SERIAL_VERSION_UID_FIELD_NAME, "", "kotlin-build-statistics"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final Map<GradleBuildPerformanceMetric, List<GradleBuildPerformanceMetric>> getChildren() {
            return (Map) GradleBuildPerformanceMetric.children$delegate.getValue();
        }
    }
}
