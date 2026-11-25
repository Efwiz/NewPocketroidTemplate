package org.jetbrains.kotlin.load.java;

import kotlin.KotlinVersion;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: JavaNullabilityAnnotationsStatus.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\b\u0018\u0000 \u00182\u00020\u0001:\u0001\u0018B#\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0003¢\u0006\u0002\u0010\u0007J\t\u0010\r\u001a\u00020\u0003HÆ\u0003J\u000b\u0010\u000e\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J)\u0010\u0010\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0014\u001a\u00020\u0015HÖ\u0001J\t\u0010\u0016\u001a\u00020\u0017HÖ\u0001R\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\tR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/load/java/JavaNullabilityAnnotationsStatus;", "", "reportLevelBefore", "Lorg/jetbrains/kotlin/load/java/ReportLevel;", "sinceVersion", "Lkotlin/KotlinVersion;", "reportLevelAfter", "(Lorg/jetbrains/kotlin/load/java/ReportLevel;Lkotlin/KotlinVersion;Lorg/jetbrains/kotlin/load/java/ReportLevel;)V", "getReportLevelAfter", "()Lorg/jetbrains/kotlin/load/java/ReportLevel;", "getReportLevelBefore", "getSinceVersion", "()Lkotlin/KotlinVersion;", "component1", "component2", "component3", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "Companion", "compiler.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class JavaNullabilityAnnotationsStatus {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final JavaNullabilityAnnotationsStatus DEFAULT = new JavaNullabilityAnnotationsStatus(ReportLevel.STRICT, null, null, 6, null);
    private final ReportLevel reportLevelAfter;
    private final ReportLevel reportLevelBefore;
    private final KotlinVersion sinceVersion;

    public static /* synthetic */ JavaNullabilityAnnotationsStatus copy$default(JavaNullabilityAnnotationsStatus javaNullabilityAnnotationsStatus, ReportLevel reportLevel, KotlinVersion kotlinVersion, ReportLevel reportLevel2, int i, Object obj) {
        if ((i & 1) != 0) {
            reportLevel = javaNullabilityAnnotationsStatus.reportLevelBefore;
        }
        if ((i & 2) != 0) {
            kotlinVersion = javaNullabilityAnnotationsStatus.sinceVersion;
        }
        if ((i & 4) != 0) {
            reportLevel2 = javaNullabilityAnnotationsStatus.reportLevelAfter;
        }
        return javaNullabilityAnnotationsStatus.copy(reportLevel, kotlinVersion, reportLevel2);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final ReportLevel getReportLevelBefore() {
        return this.reportLevelBefore;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final KotlinVersion getSinceVersion() {
        return this.sinceVersion;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final ReportLevel getReportLevelAfter() {
        return this.reportLevelAfter;
    }

    public final JavaNullabilityAnnotationsStatus copy(ReportLevel reportLevelBefore, KotlinVersion sinceVersion, ReportLevel reportLevelAfter) {
        Intrinsics.checkNotNullParameter(reportLevelBefore, "reportLevelBefore");
        Intrinsics.checkNotNullParameter(reportLevelAfter, "reportLevelAfter");
        return new JavaNullabilityAnnotationsStatus(reportLevelBefore, sinceVersion, reportLevelAfter);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof JavaNullabilityAnnotationsStatus)) {
            return false;
        }
        JavaNullabilityAnnotationsStatus javaNullabilityAnnotationsStatus = (JavaNullabilityAnnotationsStatus) other;
        return this.reportLevelBefore == javaNullabilityAnnotationsStatus.reportLevelBefore && Intrinsics.areEqual(this.sinceVersion, javaNullabilityAnnotationsStatus.sinceVersion) && this.reportLevelAfter == javaNullabilityAnnotationsStatus.reportLevelAfter;
    }

    public int hashCode() {
        int iHashCode = this.reportLevelBefore.hashCode() * 31;
        KotlinVersion kotlinVersion = this.sinceVersion;
        return ((iHashCode + (kotlinVersion == null ? 0 : kotlinVersion.getVersion())) * 31) + this.reportLevelAfter.hashCode();
    }

    public String toString() {
        return "JavaNullabilityAnnotationsStatus(reportLevelBefore=" + this.reportLevelBefore + ", sinceVersion=" + this.sinceVersion + ", reportLevelAfter=" + this.reportLevelAfter + ')';
    }

    public JavaNullabilityAnnotationsStatus(ReportLevel reportLevelBefore, KotlinVersion kotlinVersion, ReportLevel reportLevelAfter) {
        Intrinsics.checkNotNullParameter(reportLevelBefore, "reportLevelBefore");
        Intrinsics.checkNotNullParameter(reportLevelAfter, "reportLevelAfter");
        this.reportLevelBefore = reportLevelBefore;
        this.sinceVersion = kotlinVersion;
        this.reportLevelAfter = reportLevelAfter;
    }

    public final ReportLevel getReportLevelBefore() {
        return this.reportLevelBefore;
    }

    public /* synthetic */ JavaNullabilityAnnotationsStatus(ReportLevel reportLevel, KotlinVersion kotlinVersion, ReportLevel reportLevel2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(reportLevel, (i & 2) != 0 ? new KotlinVersion(1, 0) : kotlinVersion, (i & 4) != 0 ? reportLevel : reportLevel2);
    }

    public final KotlinVersion getSinceVersion() {
        return this.sinceVersion;
    }

    public final ReportLevel getReportLevelAfter() {
        return this.reportLevelAfter;
    }

    /* JADX INFO: compiled from: JavaNullabilityAnnotationsStatus.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/load/java/JavaNullabilityAnnotationsStatus$Companion;", "", "()V", "DEFAULT", "Lorg/jetbrains/kotlin/load/java/JavaNullabilityAnnotationsStatus;", "getDEFAULT", "()Lorg/jetbrains/kotlin/load/java/JavaNullabilityAnnotationsStatus;", "compiler.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final JavaNullabilityAnnotationsStatus getDEFAULT() {
            return JavaNullabilityAnnotationsStatus.DEFAULT;
        }
    }
}
