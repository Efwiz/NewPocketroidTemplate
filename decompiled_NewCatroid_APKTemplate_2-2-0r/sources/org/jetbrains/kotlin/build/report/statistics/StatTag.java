package org.jetbrains.kotlin.build.report.statistics;

import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;

/* JADX INFO: compiled from: CompileStatisticsData.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0010\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\fj\u0002\b\rj\u0002\b\u000ej\u0002\b\u000fj\u0002\b\u0010j\u0002\b\u0011j\u0002\b\u0012¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/build/report/statistics/StatTag;", "", "readableString", "", "(Ljava/lang/String;ILjava/lang/String;)V", "getReadableString", "()Ljava/lang/String;", "ABI_SNAPSHOT", "ARTIFACT_TRANSFORM", "INCREMENTAL", "NON_INCREMENTAL", "INCREMENTAL_AND_NON_INCREMENTAL", "GRADLE_DEBUG", "KOTLIN_DEBUG", "CONFIGURATION_CACHE", "BUILD_CACHE", "KOTLIN_1", "KOTLIN_2", "KOTLIN_1_AND_2", "kotlin-build-statistics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public enum StatTag {
    ABI_SNAPSHOT("ABI Snapshot"),
    ARTIFACT_TRANSFORM("Classpath Snapshot"),
    INCREMENTAL("Incremental compilation"),
    NON_INCREMENTAL("Non incremental compilation"),
    INCREMENTAL_AND_NON_INCREMENTAL("Incremental and Non incremental compilation"),
    GRADLE_DEBUG("Gradle debug enabled"),
    KOTLIN_DEBUG("Kotlin debug enabled"),
    CONFIGURATION_CACHE("Configuration cache enabled"),
    BUILD_CACHE("Build cache enabled"),
    KOTLIN_1("Kotlin language version 1"),
    KOTLIN_2("Kotlin language version 2"),
    KOTLIN_1_AND_2("Kotlin language version 1 and 2");

    private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());
    private final String readableString;

    public static EnumEntries<StatTag> getEntries() {
        return $ENTRIES;
    }

    StatTag(String str) {
        this.readableString = str;
    }

    public final String getReadableString() {
        return this.readableString;
    }
}
