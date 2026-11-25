package org.jetbrains.kotlin.build.report.statistics;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: CompileStatisticsData.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\"\n\u0002\b\u0013\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001BO\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0006\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0004\u0012\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u000e\b\u0002\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\nJ\u000f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003HÆ\u0003J\u000f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00040\u0006HÆ\u0003J\u000b\u0010\u0015\u001a\u0004\u0018\u00010\u0004HÆ\u0003J\u000f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003HÆ\u0003J\u000f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003HÆ\u0003JU\u0010\u0018\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u00062\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00042\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u000e\b\u0002\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003HÆ\u0001J\u0013\u0010\u0019\u001a\u00020\u001a2\b\u0010\u001b\u001a\u0004\u0018\u00010\u001cHÖ\u0003J\t\u0010\u001d\u001a\u00020\u001eHÖ\u0001J\t\u0010\u001f\u001a\u00020\u0004HÖ\u0001R\u0013\u0010\u0007\u001a\u0004\u0018\u00010\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0006¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0017\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0010R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0010¨\u0006 "}, d2 = {"Lorg/jetbrains/kotlin/build/report/statistics/BuildStartParameters;", "Ljava/io/Serializable;", "tasks", "", "", "excludedTasks", "", "currentDir", "projectProperties", "systemProperties", "(Ljava/util/List;Ljava/util/Set;Ljava/lang/String;Ljava/util/List;Ljava/util/List;)V", "getCurrentDir", "()Ljava/lang/String;", "getExcludedTasks", "()Ljava/util/Set;", "getProjectProperties", "()Ljava/util/List;", "getSystemProperties", "getTasks", "component1", "component2", "component3", "component4", "component5", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "kotlin-build-statistics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class BuildStartParameters implements Serializable {
    private final String currentDir;
    private final Set<String> excludedTasks;
    private final List<String> projectProperties;
    private final List<String> systemProperties;
    private final List<String> tasks;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ BuildStartParameters copy$default(BuildStartParameters buildStartParameters, List list, Set set, String str, List list2, List list3, int i, Object obj) {
        if ((i & 1) != 0) {
            list = buildStartParameters.tasks;
        }
        if ((i & 2) != 0) {
            set = buildStartParameters.excludedTasks;
        }
        Set set2 = set;
        if ((i & 4) != 0) {
            str = buildStartParameters.currentDir;
        }
        String str2 = str;
        if ((i & 8) != 0) {
            list2 = buildStartParameters.projectProperties;
        }
        List list4 = list2;
        if ((i & 16) != 0) {
            list3 = buildStartParameters.systemProperties;
        }
        return buildStartParameters.copy(list, set2, str2, list4, list3);
    }

    public final List<String> component1() {
        return this.tasks;
    }

    public final Set<String> component2() {
        return this.excludedTasks;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final String getCurrentDir() {
        return this.currentDir;
    }

    public final List<String> component4() {
        return this.projectProperties;
    }

    public final List<String> component5() {
        return this.systemProperties;
    }

    public final BuildStartParameters copy(List<String> tasks, Set<String> excludedTasks, String currentDir, List<String> projectProperties, List<String> systemProperties) {
        Intrinsics.checkNotNullParameter(tasks, "tasks");
        Intrinsics.checkNotNullParameter(excludedTasks, "excludedTasks");
        Intrinsics.checkNotNullParameter(projectProperties, "projectProperties");
        Intrinsics.checkNotNullParameter(systemProperties, "systemProperties");
        return new BuildStartParameters(tasks, excludedTasks, currentDir, projectProperties, systemProperties);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BuildStartParameters)) {
            return false;
        }
        BuildStartParameters buildStartParameters = (BuildStartParameters) other;
        return Intrinsics.areEqual(this.tasks, buildStartParameters.tasks) && Intrinsics.areEqual(this.excludedTasks, buildStartParameters.excludedTasks) && Intrinsics.areEqual(this.currentDir, buildStartParameters.currentDir) && Intrinsics.areEqual(this.projectProperties, buildStartParameters.projectProperties) && Intrinsics.areEqual(this.systemProperties, buildStartParameters.systemProperties);
    }

    public int hashCode() {
        int iHashCode = ((this.tasks.hashCode() * 31) + this.excludedTasks.hashCode()) * 31;
        String str = this.currentDir;
        return ((((iHashCode + (str == null ? 0 : str.hashCode())) * 31) + this.projectProperties.hashCode()) * 31) + this.systemProperties.hashCode();
    }

    public String toString() {
        return "BuildStartParameters(tasks=" + this.tasks + ", excludedTasks=" + this.excludedTasks + ", currentDir=" + this.currentDir + ", projectProperties=" + this.projectProperties + ", systemProperties=" + this.systemProperties + ')';
    }

    public BuildStartParameters(List<String> tasks, Set<String> excludedTasks, String str, List<String> projectProperties, List<String> systemProperties) {
        Intrinsics.checkNotNullParameter(tasks, "tasks");
        Intrinsics.checkNotNullParameter(excludedTasks, "excludedTasks");
        Intrinsics.checkNotNullParameter(projectProperties, "projectProperties");
        Intrinsics.checkNotNullParameter(systemProperties, "systemProperties");
        this.tasks = tasks;
        this.excludedTasks = excludedTasks;
        this.currentDir = str;
        this.projectProperties = projectProperties;
        this.systemProperties = systemProperties;
    }

    public final List<String> getTasks() {
        return this.tasks;
    }

    public /* synthetic */ BuildStartParameters(List list, Set set, String str, List list2, List list3, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(list, (i & 2) != 0 ? SetsKt.emptySet() : set, (i & 4) != 0 ? null : str, (i & 8) != 0 ? CollectionsKt.emptyList() : list2, (i & 16) != 0 ? CollectionsKt.emptyList() : list3);
    }

    public final Set<String> getExcludedTasks() {
        return this.excludedTasks;
    }

    public final String getCurrentDir() {
        return this.currentDir;
    }

    public final List<String> getProjectProperties() {
        return this.projectProperties;
    }

    public final List<String> getSystemProperties() {
        return this.systemProperties;
    }
}
