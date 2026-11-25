package org.jetbrains.kotlin.build.report.statistics;

import com.google.firebase.database.core.ServerValues;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: CompileStatisticsData.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u001e\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001Bq\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0003\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\b\u001a\u00020\t\u0012\b\b\u0002\u0010\n\u001a\u00020\u0003\u0012\u0006\u0010\u000b\u001a\u00020\t\u0012\b\b\u0002\u0010\f\u001a\u00020\u0003\u0012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u0003\u0012\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f\u0012\b\b\u0002\u0010\u0011\u001a\u00020\u0003¢\u0006\u0002\u0010\u0012J\t\u0010\"\u001a\u00020\u0003HÆ\u0003J\u000f\u0010#\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fHÆ\u0003J\t\u0010$\u001a\u00020\u0003HÆ\u0003J\t\u0010%\u001a\u00020\u0005HÆ\u0003J\t\u0010&\u001a\u00020\u0003HÆ\u0003J\u000b\u0010'\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\t\u0010(\u001a\u00020\tHÆ\u0003J\t\u0010)\u001a\u00020\u0003HÆ\u0003J\t\u0010*\u001a\u00020\tHÆ\u0003J\t\u0010+\u001a\u00020\u0003HÆ\u0003J\u000b\u0010,\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u0081\u0001\u0010-\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00032\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\u00032\b\b\u0002\u0010\u000b\u001a\u00020\t2\b\b\u0002\u0010\f\u001a\u00020\u00032\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u00032\u000e\b\u0002\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f2\b\b\u0002\u0010\u0011\u001a\u00020\u0003HÆ\u0001J\u0013\u0010.\u001a\u00020/2\b\u00100\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u00101\u001a\u000202HÖ\u0001J\t\u00103\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\u000b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\u0011\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0014R\u0013\u0010\r\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0014R\u0013\u0010\u0007\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0014R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0014R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u0017\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u0011\u0010\f\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u0014R\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b \u0010\u0016R\u0011\u0010\n\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\u0014¨\u00064"}, d2 = {"Lorg/jetbrains/kotlin/build/report/statistics/BuildFinishStatisticsData;", "", "projectName", "", "startParameters", "Lorg/jetbrains/kotlin/build/report/statistics/BuildStartParameters;", "buildUuid", CoroutineCodegenUtilKt.COROUTINE_LABEL_FIELD_NAME, "totalTime", "", ModuleXmlParser.TYPE, "finishTime", ServerValues.NAME_OP_TIMESTAMP, "hostName", "tags", "", "Lorg/jetbrains/kotlin/build/report/statistics/StatTag;", "gitBranch", "(Ljava/lang/String;Lorg/jetbrains/kotlin/build/report/statistics/BuildStartParameters;Ljava/lang/String;Ljava/lang/String;JLjava/lang/String;JLjava/lang/String;Ljava/lang/String;Ljava/util/Set;Ljava/lang/String;)V", "getBuildUuid", "()Ljava/lang/String;", "getFinishTime", "()J", "getGitBranch", "getHostName", "getLabel", "getProjectName", "getStartParameters", "()Lorg/jetbrains/kotlin/build/report/statistics/BuildStartParameters;", "getTags", "()Ljava/util/Set;", "getTimestamp", "getTotalTime", "getType", "component1", "component10", "component11", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "kotlin-build-statistics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class BuildFinishStatisticsData {
    private final String buildUuid;
    private final long finishTime;
    private final String gitBranch;
    private final String hostName;
    private final String label;
    private final String projectName;
    private final BuildStartParameters startParameters;
    private final Set<StatTag> tags;
    private final String timestamp;
    private final long totalTime;
    private final String type;

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getProjectName() {
        return this.projectName;
    }

    public final Set<StatTag> component10() {
        return this.tags;
    }

    /* JADX INFO: renamed from: component11, reason: from getter */
    public final String getGitBranch() {
        return this.gitBranch;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final BuildStartParameters getStartParameters() {
        return this.startParameters;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final String getBuildUuid() {
        return this.buildUuid;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final String getLabel() {
        return this.label;
    }

    /* JADX INFO: renamed from: component5, reason: from getter */
    public final long getTotalTime() {
        return this.totalTime;
    }

    /* JADX INFO: renamed from: component6, reason: from getter */
    public final String getType() {
        return this.type;
    }

    /* JADX INFO: renamed from: component7, reason: from getter */
    public final long getFinishTime() {
        return this.finishTime;
    }

    /* JADX INFO: renamed from: component8, reason: from getter */
    public final String getTimestamp() {
        return this.timestamp;
    }

    /* JADX INFO: renamed from: component9, reason: from getter */
    public final String getHostName() {
        return this.hostName;
    }

    public final BuildFinishStatisticsData copy(String projectName, BuildStartParameters startParameters, String buildUuid, String label, long totalTime, String type, long finishTime, String timestamp, String hostName, Set<? extends StatTag> tags, String gitBranch) {
        Intrinsics.checkNotNullParameter(projectName, "projectName");
        Intrinsics.checkNotNullParameter(startParameters, "startParameters");
        Intrinsics.checkNotNullParameter(buildUuid, "buildUuid");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(timestamp, "timestamp");
        Intrinsics.checkNotNullParameter(tags, "tags");
        Intrinsics.checkNotNullParameter(gitBranch, "gitBranch");
        return new BuildFinishStatisticsData(projectName, startParameters, buildUuid, label, totalTime, type, finishTime, timestamp, hostName, tags, gitBranch);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BuildFinishStatisticsData)) {
            return false;
        }
        BuildFinishStatisticsData buildFinishStatisticsData = (BuildFinishStatisticsData) other;
        return Intrinsics.areEqual(this.projectName, buildFinishStatisticsData.projectName) && Intrinsics.areEqual(this.startParameters, buildFinishStatisticsData.startParameters) && Intrinsics.areEqual(this.buildUuid, buildFinishStatisticsData.buildUuid) && Intrinsics.areEqual(this.label, buildFinishStatisticsData.label) && this.totalTime == buildFinishStatisticsData.totalTime && Intrinsics.areEqual(this.type, buildFinishStatisticsData.type) && this.finishTime == buildFinishStatisticsData.finishTime && Intrinsics.areEqual(this.timestamp, buildFinishStatisticsData.timestamp) && Intrinsics.areEqual(this.hostName, buildFinishStatisticsData.hostName) && Intrinsics.areEqual(this.tags, buildFinishStatisticsData.tags) && Intrinsics.areEqual(this.gitBranch, buildFinishStatisticsData.gitBranch);
    }

    public int hashCode() {
        int iHashCode = ((((this.projectName.hashCode() * 31) + this.startParameters.hashCode()) * 31) + this.buildUuid.hashCode()) * 31;
        String str = this.label;
        int iHashCode2 = (((((((((iHashCode + (str == null ? 0 : str.hashCode())) * 31) + Long.hashCode(this.totalTime)) * 31) + this.type.hashCode()) * 31) + Long.hashCode(this.finishTime)) * 31) + this.timestamp.hashCode()) * 31;
        String str2 = this.hostName;
        return ((((iHashCode2 + (str2 != null ? str2.hashCode() : 0)) * 31) + this.tags.hashCode()) * 31) + this.gitBranch.hashCode();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("BuildFinishStatisticsData(projectName=");
        sb.append(this.projectName).append(", startParameters=").append(this.startParameters).append(", buildUuid=").append(this.buildUuid).append(", label=").append(this.label).append(", totalTime=").append(this.totalTime).append(", type=").append(this.type).append(", finishTime=").append(this.finishTime).append(", timestamp=").append(this.timestamp).append(", hostName=").append(this.hostName).append(", tags=").append(this.tags).append(", gitBranch=").append(this.gitBranch).append(')');
        return sb.toString();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public BuildFinishStatisticsData(String projectName, BuildStartParameters startParameters, String buildUuid, String str, long j, String type, long j2, String timestamp, String str2, Set<? extends StatTag> tags, String gitBranch) {
        Intrinsics.checkNotNullParameter(projectName, "projectName");
        Intrinsics.checkNotNullParameter(startParameters, "startParameters");
        Intrinsics.checkNotNullParameter(buildUuid, "buildUuid");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(timestamp, "timestamp");
        Intrinsics.checkNotNullParameter(tags, "tags");
        Intrinsics.checkNotNullParameter(gitBranch, "gitBranch");
        this.projectName = projectName;
        this.startParameters = startParameters;
        this.buildUuid = buildUuid;
        this.label = str;
        this.totalTime = j;
        this.type = type;
        this.finishTime = j2;
        this.timestamp = timestamp;
        this.hostName = str2;
        this.tags = tags;
        this.gitBranch = gitBranch;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public /* synthetic */ BuildFinishStatisticsData(String str, BuildStartParameters buildStartParameters, String str2, String str3, long j, String str4, long j2, String str5, String str6, Set set, String str7, int i, DefaultConstructorMarker defaultConstructorMarker) {
        String str8;
        String str9 = (i & 4) != 0 ? "Unset" : str2;
        String str10 = (i & 32) != 0 ? "BUILD_DATA" : str4;
        if ((i & 128) != 0) {
            String str11 = CompileStatisticsDataKt.formatter.format(Long.valueOf(j2));
            Intrinsics.checkNotNullExpressionValue(str11, "format(...)");
            str8 = str11;
        } else {
            str8 = str5;
        }
        this(str, buildStartParameters, str9, str3, j, str10, j2, str8, (i & 256) != 0 ? "Unset" : str6, set, (i & 1024) != 0 ? "Unset" : str7);
    }

    public final String getProjectName() {
        return this.projectName;
    }

    public final BuildStartParameters getStartParameters() {
        return this.startParameters;
    }

    public final String getBuildUuid() {
        return this.buildUuid;
    }

    public final String getLabel() {
        return this.label;
    }

    public final long getTotalTime() {
        return this.totalTime;
    }

    public final String getType() {
        return this.type;
    }

    public final long getFinishTime() {
        return this.finishTime;
    }

    public final String getTimestamp() {
        return this.timestamp;
    }

    public final String getHostName() {
        return this.hostName;
    }

    public final Set<StatTag> getTags() {
        return this.tags;
    }

    public final String getGitBranch() {
        return this.gitBranch;
    }
}
