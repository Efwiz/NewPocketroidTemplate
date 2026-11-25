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
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;

/* JADX INFO: compiled from: BuildTime.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\bK\b\u0086\u0081\u0002\u0018\u0000 R2\b\u0012\u0004\u0012\u00020\u00000\u00012\u00020\u0002:\u0001RB\u001b\b\u0002\u0012\n\b\u0002\u0010\u0003\u001a\u0004\u0018\u00010\u0000\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010\u0007\u001a\n\u0012\u0004\u0012\u00020\u0002\u0018\u00010\bH\u0016J\u000e\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00020\bH\u0016J\b\u0010\n\u001a\u00020\u0005H\u0016J\n\u0010\u000b\u001a\u0004\u0018\u00010\u0002H\u0016J\b\u0010\f\u001a\u00020\u0005H\u0016R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0000X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000j\u0002\b\rj\u0002\b\u000ej\u0002\b\u000fj\u0002\b\u0010j\u0002\b\u0011j\u0002\b\u0012j\u0002\b\u0013j\u0002\b\u0014j\u0002\b\u0015j\u0002\b\u0016j\u0002\b\u0017j\u0002\b\u0018j\u0002\b\u0019j\u0002\b\u001aj\u0002\b\u001bj\u0002\b\u001cj\u0002\b\u001dj\u0002\b\u001ej\u0002\b\u001fj\u0002\b j\u0002\b!j\u0002\b\"j\u0002\b#j\u0002\b$j\u0002\b%j\u0002\b&j\u0002\b'j\u0002\b(j\u0002\b)j\u0002\b*j\u0002\b+j\u0002\b,j\u0002\b-j\u0002\b.j\u0002\b/j\u0002\b0j\u0002\b1j\u0002\b2j\u0002\b3j\u0002\b4j\u0002\b5j\u0002\b6j\u0002\b7j\u0002\b8j\u0002\b9j\u0002\b:j\u0002\b;j\u0002\b<j\u0002\b=j\u0002\b>j\u0002\b?j\u0002\b@j\u0002\bAj\u0002\bBj\u0002\bCj\u0002\bDj\u0002\bEj\u0002\bFj\u0002\bGj\u0002\bHj\u0002\bIj\u0002\bJj\u0002\bKj\u0002\bLj\u0002\bMj\u0002\bNj\u0002\bOj\u0002\bPj\u0002\bQ¨\u0006S"}, d2 = {"Lorg/jetbrains/kotlin/build/report/metrics/GradleBuildTime;", "", "Lorg/jetbrains/kotlin/build/report/metrics/BuildTime;", "parent", "readableString", "", "(Ljava/lang/String;ILorg/jetbrains/kotlin/build/report/metrics/GradleBuildTime;Ljava/lang/String;)V", "children", "", "getAllMetrics", "getName", "getParent", "getReadableString", "GRADLE_TASK", "GRADLE_TASK_PREPARATION", "GRADLE_TASK_ACTION", "OUT_OF_WORKER_TASK_ACTION", "BACKUP_OUTPUT", "RUN_WORKER_DELAY", "RUN_COMPILATION_IN_WORKER", "CLEAR_JAR_CACHE", "CLEAR_OUTPUT", "PRECISE_BACKUP_OUTPUT", "RESTORE_OUTPUT_FROM_BACKUP", "CLEAN_BACKUP_STASH", "CONNECT_TO_DAEMON", "CALCULATE_OUTPUT_SIZE", "RUN_COMPILATION", "NATIVE_IN_PROCESS", "RUN_ENTRY_POINT", "NATIVE_IN_EXECUTOR", "NON_INCREMENTAL_COMPILATION_IN_PROCESS", "NON_INCREMENTAL_COMPILATION_OUT_OF_PROCESS", "NON_INCREMENTAL_COMPILATION_DAEMON", "INCREMENTAL_COMPILATION_DAEMON", "STORE_BUILD_INFO", "JAR_SNAPSHOT", "SET_UP_ABI_SNAPSHOTS", "IC_ANALYZE_JAR_FILES", "IC_CALCULATE_INITIAL_DIRTY_SET", "COMPUTE_CLASSPATH_CHANGES", "LOAD_CURRENT_CLASSPATH_SNAPSHOT", "REMOVE_DUPLICATE_CLASSES", "SHRINK_CURRENT_CLASSPATH_SNAPSHOT", "GET_LOOKUP_SYMBOLS", "FIND_REFERENCED_CLASSES", "FIND_TRANSITIVELY_REFERENCED_CLASSES", "LOAD_SHRUNK_PREVIOUS_CLASSPATH_SNAPSHOT", "COMPUTE_CHANGED_AND_IMPACTED_SET", "COMPUTE_CLASS_CHANGES", "COMPUTE_KOTLIN_CLASS_CHANGES", "COMPUTE_JAVA_CLASS_CHANGES", "COMPUTE_IMPACTED_SET", "IC_ANALYZE_CHANGES_IN_DEPENDENCIES", "IC_FIND_HISTORY_FILES", "IC_ANALYZE_HISTORY_FILES", "IC_ANALYZE_CHANGES_IN_JAVA_SOURCES", "IC_ANALYZE_CHANGES_IN_ANDROID_LAYOUTS", "IC_DETECT_REMOVED_CLASSES", "CLEAR_OUTPUT_ON_REBUILD", "IC_UPDATE_CACHES", "COMPILATION_ROUND", "COMPILER_PERFORMANCE", "COMPILER_INITIALIZATION", "CODE_ANALYSIS", "CODE_GENERATION", "IC_WRITE_HISTORY_FILE", "SHRINK_AND_SAVE_CURRENT_CLASSPATH_SNAPSHOT_AFTER_COMPILATION", "INCREMENTAL_SHRINK_CURRENT_CLASSPATH_SNAPSHOT", "INCREMENTAL_LOAD_CURRENT_CLASSPATH_SNAPSHOT", "INCREMENTAL_LOAD_SHRUNK_CURRENT_CLASSPATH_SNAPSHOT_AGAINST_PREVIOUS_LOOKUPS", "NON_INCREMENTAL_SHRINK_CURRENT_CLASSPATH_SNAPSHOT", "NON_INCREMENTAL_LOAD_CURRENT_CLASSPATH_SNAPSHOT", "SAVE_SHRUNK_CURRENT_CLASSPATH_SNAPSHOT", "TASK_FINISH_LISTENER_NOTIFICATION", "CLASSPATH_ENTRY_SNAPSHOT_TRANSFORM", "LOAD_CLASSES_PATHS_ONLY", "SNAPSHOT_CLASSES", "LOAD_CONTENTS_OF_CLASSES", "SNAPSHOT_KOTLIN_CLASSES", "SNAPSHOT_JAVA_CLASSES", "SAVE_CLASSPATH_ENTRY_SNAPSHOT", "Companion", "kotlin-build-statistics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public enum GradleBuildTime implements BuildTime {
    GRADLE_TASK(null, "Total Gradle task time", 1, null),
    GRADLE_TASK_PREPARATION(null, "Spent time before task action", 1, null),
    GRADLE_TASK_ACTION(null, "Task action", 1, null),
    OUT_OF_WORKER_TASK_ACTION(GRADLE_TASK_ACTION, "Task action before worker execution"),
    BACKUP_OUTPUT(OUT_OF_WORKER_TASK_ACTION, "Backup output"),
    RUN_WORKER_DELAY(null, "Start gradle worker", 1, null),
    RUN_COMPILATION_IN_WORKER(GRADLE_TASK_ACTION, "Run compilation in Gradle worker"),
    CLEAR_JAR_CACHE(RUN_COMPILATION_IN_WORKER, "Clear jar cache"),
    CLEAR_OUTPUT(RUN_COMPILATION_IN_WORKER, "Clear output"),
    PRECISE_BACKUP_OUTPUT(RUN_COMPILATION_IN_WORKER, "Precise backup output"),
    RESTORE_OUTPUT_FROM_BACKUP(RUN_COMPILATION_IN_WORKER, "Restore output"),
    CLEAN_BACKUP_STASH(RUN_COMPILATION_IN_WORKER, "Cleaning up the backup stash"),
    CONNECT_TO_DAEMON(RUN_COMPILATION_IN_WORKER, "Connect to Kotlin daemon"),
    CALCULATE_OUTPUT_SIZE(RUN_COMPILATION_IN_WORKER, "Calculate output size"),
    RUN_COMPILATION(RUN_COMPILATION_IN_WORKER, "Run compilation"),
    NATIVE_IN_PROCESS(RUN_COMPILATION, "Run native in process"),
    RUN_ENTRY_POINT(NATIVE_IN_PROCESS, "Run entry point"),
    NATIVE_IN_EXECUTOR(RUN_COMPILATION, "Run native in executor"),
    NON_INCREMENTAL_COMPILATION_IN_PROCESS(RUN_COMPILATION, "Non incremental inprocess compilation"),
    NON_INCREMENTAL_COMPILATION_OUT_OF_PROCESS(RUN_COMPILATION, "Non incremental out of process compilation"),
    NON_INCREMENTAL_COMPILATION_DAEMON(RUN_COMPILATION, "Non incremental compilation in daemon"),
    INCREMENTAL_COMPILATION_DAEMON(RUN_COMPILATION, "Incremental compilation in daemon"),
    STORE_BUILD_INFO(INCREMENTAL_COMPILATION_DAEMON, "Store build info"),
    JAR_SNAPSHOT(INCREMENTAL_COMPILATION_DAEMON, "ABI JAR Snapshot support"),
    SET_UP_ABI_SNAPSHOTS(JAR_SNAPSHOT, "Set up ABI snapshot"),
    IC_ANALYZE_JAR_FILES(JAR_SNAPSHOT, "Analyze jar files"),
    IC_CALCULATE_INITIAL_DIRTY_SET(INCREMENTAL_COMPILATION_DAEMON, "Calculate initial dirty sources set"),
    COMPUTE_CLASSPATH_CHANGES(IC_CALCULATE_INITIAL_DIRTY_SET, "Compute classpath changes"),
    LOAD_CURRENT_CLASSPATH_SNAPSHOT(COMPUTE_CLASSPATH_CHANGES, "Load current classpath snapshot"),
    REMOVE_DUPLICATE_CLASSES(LOAD_CURRENT_CLASSPATH_SNAPSHOT, "Remove duplicate classes"),
    SHRINK_CURRENT_CLASSPATH_SNAPSHOT(COMPUTE_CLASSPATH_CHANGES, "Shrink current classpath snapshot"),
    GET_LOOKUP_SYMBOLS(SHRINK_CURRENT_CLASSPATH_SNAPSHOT, "Get lookup symbols"),
    FIND_REFERENCED_CLASSES(SHRINK_CURRENT_CLASSPATH_SNAPSHOT, "Find referenced classes"),
    FIND_TRANSITIVELY_REFERENCED_CLASSES(SHRINK_CURRENT_CLASSPATH_SNAPSHOT, "Find transitively referenced classes"),
    LOAD_SHRUNK_PREVIOUS_CLASSPATH_SNAPSHOT(COMPUTE_CLASSPATH_CHANGES, "Load shrunk previous classpath snapshot"),
    COMPUTE_CHANGED_AND_IMPACTED_SET(COMPUTE_CLASSPATH_CHANGES, "Compute changed and impacted set"),
    COMPUTE_CLASS_CHANGES(COMPUTE_CHANGED_AND_IMPACTED_SET, "Compute class changes"),
    COMPUTE_KOTLIN_CLASS_CHANGES(COMPUTE_CLASS_CHANGES, "Compute Kotlin class changes"),
    COMPUTE_JAVA_CLASS_CHANGES(COMPUTE_CLASS_CHANGES, "Compute Java class changes"),
    COMPUTE_IMPACTED_SET(COMPUTE_CHANGED_AND_IMPACTED_SET, "Compute impacted set"),
    IC_ANALYZE_CHANGES_IN_DEPENDENCIES(IC_CALCULATE_INITIAL_DIRTY_SET, "Analyze dependency changes"),
    IC_FIND_HISTORY_FILES(IC_ANALYZE_CHANGES_IN_DEPENDENCIES, "Find history files"),
    IC_ANALYZE_HISTORY_FILES(IC_ANALYZE_CHANGES_IN_DEPENDENCIES, "Analyze history files"),
    IC_ANALYZE_CHANGES_IN_JAVA_SOURCES(IC_CALCULATE_INITIAL_DIRTY_SET, "Analyze Java file changes"),
    IC_ANALYZE_CHANGES_IN_ANDROID_LAYOUTS(IC_CALCULATE_INITIAL_DIRTY_SET, "Analyze Android layouts"),
    IC_DETECT_REMOVED_CLASSES(IC_CALCULATE_INITIAL_DIRTY_SET, "Detect removed classes"),
    CLEAR_OUTPUT_ON_REBUILD(INCREMENTAL_COMPILATION_DAEMON, "Clear outputs on rebuild"),
    IC_UPDATE_CACHES(INCREMENTAL_COMPILATION_DAEMON, "Update caches"),
    COMPILATION_ROUND(INCREMENTAL_COMPILATION_DAEMON, "Sources compilation round"),
    COMPILER_PERFORMANCE(COMPILATION_ROUND, "Compiler time"),
    COMPILER_INITIALIZATION(COMPILER_PERFORMANCE, "Compiler initialization time"),
    CODE_ANALYSIS(COMPILER_PERFORMANCE, "Compiler code analysis"),
    CODE_GENERATION(COMPILER_PERFORMANCE, "Compiler code generation"),
    IC_WRITE_HISTORY_FILE(INCREMENTAL_COMPILATION_DAEMON, "Write history file"),
    SHRINK_AND_SAVE_CURRENT_CLASSPATH_SNAPSHOT_AFTER_COMPILATION(INCREMENTAL_COMPILATION_DAEMON, "Shrink and save current classpath snapshot after compilation"),
    INCREMENTAL_SHRINK_CURRENT_CLASSPATH_SNAPSHOT(SHRINK_AND_SAVE_CURRENT_CLASSPATH_SNAPSHOT_AFTER_COMPILATION, "Shrink current classpath snapshot incrementally"),
    INCREMENTAL_LOAD_CURRENT_CLASSPATH_SNAPSHOT(INCREMENTAL_SHRINK_CURRENT_CLASSPATH_SNAPSHOT, "Load current classpath snapshot"),
    INCREMENTAL_LOAD_SHRUNK_CURRENT_CLASSPATH_SNAPSHOT_AGAINST_PREVIOUS_LOOKUPS(INCREMENTAL_SHRINK_CURRENT_CLASSPATH_SNAPSHOT, "Load shrunk current classpath snapshot against previous lookups"),
    NON_INCREMENTAL_SHRINK_CURRENT_CLASSPATH_SNAPSHOT(SHRINK_AND_SAVE_CURRENT_CLASSPATH_SNAPSHOT_AFTER_COMPILATION, "Shrink current classpath snapshot non-incrementally"),
    NON_INCREMENTAL_LOAD_CURRENT_CLASSPATH_SNAPSHOT(NON_INCREMENTAL_SHRINK_CURRENT_CLASSPATH_SNAPSHOT, "Load current classpath snapshot"),
    SAVE_SHRUNK_CURRENT_CLASSPATH_SNAPSHOT(SHRINK_AND_SAVE_CURRENT_CLASSPATH_SNAPSHOT_AFTER_COMPILATION, "Save shrunk current classpath snapshot"),
    TASK_FINISH_LISTENER_NOTIFICATION(null, "Task finish event notification", 1, null),
    CLASSPATH_ENTRY_SNAPSHOT_TRANSFORM(null, "Classpath entry snapshot transform", 1, null),
    LOAD_CLASSES_PATHS_ONLY(CLASSPATH_ENTRY_SNAPSHOT_TRANSFORM, "Load classes (paths only)"),
    SNAPSHOT_CLASSES(CLASSPATH_ENTRY_SNAPSHOT_TRANSFORM, "Snapshot classes"),
    LOAD_CONTENTS_OF_CLASSES(SNAPSHOT_CLASSES, "Load contents of classes"),
    SNAPSHOT_KOTLIN_CLASSES(SNAPSHOT_CLASSES, "Snapshot Kotlin classes"),
    SNAPSHOT_JAVA_CLASSES(SNAPSHOT_CLASSES, "Snapshot Java classes"),
    SAVE_CLASSPATH_ENTRY_SNAPSHOT(CLASSPATH_ENTRY_SNAPSHOT_TRANSFORM, "Save classpath entry snapshot");

    public static final long serialVersionUID = 1;
    private final GradleBuildTime parent;
    private final String readableString;
    private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final Lazy<Map<GradleBuildTime, List<GradleBuildTime>>> children$delegate = LazyKt.lazy(new Function0<Map<GradleBuildTime, ? extends List<? extends GradleBuildTime>>>() { // from class: org.jetbrains.kotlin.build.report.metrics.GradleBuildTime$Companion$children$2
        @Override // kotlin.jvm.functions.Function0
        public final Map<GradleBuildTime, ? extends List<? extends GradleBuildTime>> invoke() {
            EnumEntries<GradleBuildTime> entries = GradleBuildTime.getEntries();
            ArrayList arrayList = new ArrayList();
            for (GradleBuildTime gradleBuildTime : entries) {
                if (gradleBuildTime.parent != null) {
                    arrayList.add(gradleBuildTime);
                }
            }
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            for (Object obj : arrayList) {
                GradleBuildTime gradleBuildTime2 = ((GradleBuildTime) obj).parent;
                Object obj2 = linkedHashMap.get(gradleBuildTime2);
                if (obj2 == null) {
                    obj2 = (List) new ArrayList();
                    linkedHashMap.put(gradleBuildTime2, obj2);
                }
                ((List) obj2).add(obj);
            }
            return linkedHashMap;
        }
    });

    public static EnumEntries<GradleBuildTime> getEntries() {
        return $ENTRIES;
    }

    GradleBuildTime(GradleBuildTime gradleBuildTime, String str) {
        this.parent = gradleBuildTime;
        this.readableString = str;
    }

    /* synthetic */ GradleBuildTime(GradleBuildTime gradleBuildTime, String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : gradleBuildTime, str);
    }

    @Override // org.jetbrains.kotlin.build.report.metrics.BuildTime
    public String getReadableString() {
        return this.readableString;
    }

    @Override // org.jetbrains.kotlin.build.report.metrics.BuildTime
    public BuildTime getParent() {
        return this.parent;
    }

    @Override // org.jetbrains.kotlin.build.report.metrics.BuildTime
    public List<BuildTime> children() {
        return INSTANCE.getChildren().get(this);
    }

    @Override // org.jetbrains.kotlin.build.report.metrics.BuildTime
    public List<BuildTime> getAllMetrics() {
        return getEntries();
    }

    @Override // org.jetbrains.kotlin.build.report.metrics.BuildTime
    public String getName() {
        return name();
    }

    /* JADX INFO: compiled from: BuildTime.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0005\n\u0002\u0010\t\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R/\u0010\u0003\u001a\u0016\u0012\u0006\u0012\u0004\u0018\u00010\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00060\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u0007\u0010\bR\u000e\u0010\u000b\u001a\u00020\fX\u0086T¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/build/report/metrics/GradleBuildTime$Companion;", "", "()V", "children", "", "Lorg/jetbrains/kotlin/build/report/metrics/GradleBuildTime;", "", "getChildren", "()Ljava/util/Map;", "children$delegate", "Lkotlin/Lazy;", CommonClassNames.SERIAL_VERSION_UID_FIELD_NAME, "", "kotlin-build-statistics"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final Map<GradleBuildTime, List<GradleBuildTime>> getChildren() {
            return (Map) GradleBuildTime.children$delegate.getValue();
        }
    }
}
