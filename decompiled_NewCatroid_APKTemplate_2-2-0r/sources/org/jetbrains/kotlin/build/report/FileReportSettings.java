package org.jetbrains.kotlin.build.report;

import java.io.File;
import java.io.Serializable;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: reportSettings.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\b\u0018\u0000 \u00152\u00020\u0001:\u0001\u0015B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u00052\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/build/report/FileReportSettings;", "Ljava/io/Serializable;", "buildReportDir", "Ljava/io/File;", "includeMetricsInReport", "", "(Ljava/io/File;Z)V", "getBuildReportDir", "()Ljava/io/File;", "getIncludeMetricsInReport", "()Z", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "other", "", "hashCode", "", "toString", "", "Companion", "kotlin-build-statistics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class FileReportSettings implements Serializable {
    public static final long serialVersionUID = 0;
    private final File buildReportDir;
    private final boolean includeMetricsInReport;

    public static /* synthetic */ FileReportSettings copy$default(FileReportSettings fileReportSettings, File file, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            file = fileReportSettings.buildReportDir;
        }
        if ((i & 2) != 0) {
            z = fileReportSettings.includeMetricsInReport;
        }
        return fileReportSettings.copy(file, z);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final File getBuildReportDir() {
        return this.buildReportDir;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final boolean getIncludeMetricsInReport() {
        return this.includeMetricsInReport;
    }

    public final FileReportSettings copy(File buildReportDir, boolean includeMetricsInReport) {
        Intrinsics.checkNotNullParameter(buildReportDir, "buildReportDir");
        return new FileReportSettings(buildReportDir, includeMetricsInReport);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof FileReportSettings)) {
            return false;
        }
        FileReportSettings fileReportSettings = (FileReportSettings) other;
        return Intrinsics.areEqual(this.buildReportDir, fileReportSettings.buildReportDir) && this.includeMetricsInReport == fileReportSettings.includeMetricsInReport;
    }

    public int hashCode() {
        return (this.buildReportDir.hashCode() * 31) + Boolean.hashCode(this.includeMetricsInReport);
    }

    public String toString() {
        return "FileReportSettings(buildReportDir=" + this.buildReportDir + ", includeMetricsInReport=" + this.includeMetricsInReport + ')';
    }

    public FileReportSettings(File buildReportDir, boolean z) {
        Intrinsics.checkNotNullParameter(buildReportDir, "buildReportDir");
        this.buildReportDir = buildReportDir;
        this.includeMetricsInReport = z;
    }

    public /* synthetic */ FileReportSettings(File file, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(file, (i & 2) != 0 ? false : z);
    }

    public final File getBuildReportDir() {
        return this.buildReportDir;
    }

    public final boolean getIncludeMetricsInReport() {
        return this.includeMetricsInReport;
    }
}
