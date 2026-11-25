package org.jetbrains.kotlin.build.report.metrics;

import java.io.Serializable;
import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: BuildAttribute.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u001c\b\u0086\u0081\u0002\u0018\u0000 !2\b\u0012\u0004\u0012\u00020\u00000\u00012\u00020\u0002:\u0001!B\u0017\b\u0002\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bj\u0002\b\fj\u0002\b\rj\u0002\b\u000ej\u0002\b\u000fj\u0002\b\u0010j\u0002\b\u0011j\u0002\b\u0012j\u0002\b\u0013j\u0002\b\u0014j\u0002\b\u0015j\u0002\b\u0016j\u0002\b\u0017j\u0002\b\u0018j\u0002\b\u0019j\u0002\b\u001aj\u0002\b\u001bj\u0002\b\u001cj\u0002\b\u001dj\u0002\b\u001ej\u0002\b\u001fj\u0002\b ¨\u0006\""}, d2 = {"Lorg/jetbrains/kotlin/build/report/metrics/BuildAttribute;", "", "Ljava/io/Serializable;", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/build/report/metrics/BuildAttributeKind;", "readableString", "", "(Ljava/lang/String;ILorg/jetbrains/kotlin/build/report/metrics/BuildAttributeKind;Ljava/lang/String;)V", "getKind", "()Lorg/jetbrains/kotlin/build/report/metrics/BuildAttributeKind;", "getReadableString", "()Ljava/lang/String;", "NO_BUILD_HISTORY", "NO_ABI_SNAPSHOT", "NO_LAST_BUILD_INFO", "INVALID_LAST_BUILD_INFO", "CLASSPATH_SNAPSHOT_NOT_FOUND", "IC_FAILED_TO_GET_CHANGED_FILES", "IC_FAILED_TO_COMPUTE_FILES_TO_RECOMPILE", "IC_FAILED_TO_COMPILE_INCREMENTALLY", "IC_FAILED_TO_CLOSE_CACHES", "UNKNOWN_CHANGES_IN_GRADLE_INPUTS", "JAVA_CHANGE_UNTRACKED_FILE_IS_REMOVED", "JAVA_CHANGE_UNEXPECTED_PSI", "JAVA_CHANGE_UNKNOWN_QUALIFIER", "DEP_CHANGE_REMOVED_ENTRY", "DEP_CHANGE_HISTORY_IS_NOT_FOUND", "DEP_CHANGE_HISTORY_CANNOT_BE_READ", "DEP_CHANGE_HISTORY_NO_KNOWN_BUILDS", "DEP_CHANGE_NON_INCREMENTAL_BUILD_IN_DEP", "IN_PROCESS_EXECUTION", "OUT_OF_PROCESS_EXECUTION", "IC_IS_NOT_ENABLED", "Companion", "kotlin-build-statistics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public enum BuildAttribute implements Serializable {
    NO_BUILD_HISTORY(BuildAttributeKind.REBUILD_REASON, "Build history file not found"),
    NO_ABI_SNAPSHOT(BuildAttributeKind.REBUILD_REASON, "ABI snapshot not found"),
    NO_LAST_BUILD_INFO(BuildAttributeKind.REBUILD_REASON, "Last build info not found"),
    INVALID_LAST_BUILD_INFO(BuildAttributeKind.REBUILD_REASON, "Last build info corrupted"),
    CLASSPATH_SNAPSHOT_NOT_FOUND(BuildAttributeKind.REBUILD_REASON, "Classpath snapshot not found"),
    IC_FAILED_TO_GET_CHANGED_FILES(BuildAttributeKind.REBUILD_REASON, "Failed to get changed files"),
    IC_FAILED_TO_COMPUTE_FILES_TO_RECOMPILE(BuildAttributeKind.REBUILD_REASON, "Failed to compute files to recompile"),
    IC_FAILED_TO_COMPILE_INCREMENTALLY(BuildAttributeKind.REBUILD_REASON, "Failed to compile incrementally"),
    IC_FAILED_TO_CLOSE_CACHES(BuildAttributeKind.REBUILD_REASON, "Failed to close caches"),
    UNKNOWN_CHANGES_IN_GRADLE_INPUTS(BuildAttributeKind.REBUILD_REASON, "Unknown Gradle changes"),
    JAVA_CHANGE_UNTRACKED_FILE_IS_REMOVED(BuildAttributeKind.REBUILD_REASON, "Untracked Java file is removed"),
    JAVA_CHANGE_UNEXPECTED_PSI(BuildAttributeKind.REBUILD_REASON, "Java PSI file is expected"),
    JAVA_CHANGE_UNKNOWN_QUALIFIER(BuildAttributeKind.REBUILD_REASON, "Unknown Java qualifier name"),
    DEP_CHANGE_REMOVED_ENTRY(BuildAttributeKind.REBUILD_REASON, "Jar file is removed form dependency"),
    DEP_CHANGE_HISTORY_IS_NOT_FOUND(BuildAttributeKind.REBUILD_REASON, "Dependency history not found"),
    DEP_CHANGE_HISTORY_CANNOT_BE_READ(BuildAttributeKind.REBUILD_REASON, "Dependency history can not be read"),
    DEP_CHANGE_HISTORY_NO_KNOWN_BUILDS(BuildAttributeKind.REBUILD_REASON, "Dependency history id not available"),
    DEP_CHANGE_NON_INCREMENTAL_BUILD_IN_DEP(BuildAttributeKind.REBUILD_REASON, "Non incremental build in history"),
    IN_PROCESS_EXECUTION(BuildAttributeKind.REBUILD_REASON, "In-process execution"),
    OUT_OF_PROCESS_EXECUTION(BuildAttributeKind.REBUILD_REASON, "Out of process execution"),
    IC_IS_NOT_ENABLED(BuildAttributeKind.REBUILD_REASON, "Incremental compilation is not enabled");

    public static final long serialVersionUID = 0;
    private final BuildAttributeKind kind;
    private final String readableString;
    private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

    public static EnumEntries<BuildAttribute> getEntries() {
        return $ENTRIES;
    }

    BuildAttribute(BuildAttributeKind buildAttributeKind, String str) {
        this.kind = buildAttributeKind;
        this.readableString = str;
    }

    public final BuildAttributeKind getKind() {
        return this.kind;
    }

    public final String getReadableString() {
        return this.readableString;
    }
}
