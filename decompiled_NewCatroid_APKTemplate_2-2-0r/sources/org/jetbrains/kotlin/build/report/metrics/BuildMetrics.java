package org.jetbrains.kotlin.build.report.metrics;

import java.io.Serializable;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.build.report.metrics.BuildPerformanceMetric;
import org.jetbrains.kotlin.build.report.metrics.BuildTime;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: BuildMetrics.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\b\u0018\u0000 &*\b\b\u0000\u0010\u0001*\u00020\u0002*\b\b\u0001\u0010\u0003*\u00020\u00042\u00020\u0005:\u0001&B9\u0012\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00000\u0007\u0012\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00010\t\u0012\b\b\u0002\u0010\n\u001a\u00020\u000b\u0012\b\b\u0002\u0010\f\u001a\u00020\r¢\u0006\u0002\u0010\u000eJ\u001a\u0010\u0017\u001a\u00020\u00182\u0012\u0010\u0019\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0000J\u000f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00028\u00000\u0007HÆ\u0003J\u000f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00028\u00010\tHÆ\u0003J\t\u0010\u001c\u001a\u00020\u000bHÆ\u0003J\t\u0010\u001d\u001a\u00020\rHÆ\u0003JI\u0010\u001e\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u00002\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00000\u00072\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00010\t2\b\b\u0002\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00020\rHÆ\u0001J\u0013\u0010\u001f\u001a\u00020 2\b\u0010\u0019\u001a\u0004\u0018\u00010!HÖ\u0003J\t\u0010\"\u001a\u00020#HÖ\u0001J\t\u0010$\u001a\u00020%HÖ\u0001R\u0011\u0010\n\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00010\t¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00000\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\f\u001a\u00020\r¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016¨\u0006'"}, d2 = {"Lorg/jetbrains/kotlin/build/report/metrics/BuildMetrics;", "B", "Lorg/jetbrains/kotlin/build/report/metrics/BuildTime;", "P", "Lorg/jetbrains/kotlin/build/report/metrics/BuildPerformanceMetric;", "Ljava/io/Serializable;", "buildTimes", "Lorg/jetbrains/kotlin/build/report/metrics/BuildTimes;", "buildPerformanceMetrics", "Lorg/jetbrains/kotlin/build/report/metrics/BuildPerformanceMetrics;", "buildAttributes", "Lorg/jetbrains/kotlin/build/report/metrics/BuildAttributes;", "gcMetrics", "Lorg/jetbrains/kotlin/build/report/metrics/GcMetrics;", "(Lorg/jetbrains/kotlin/build/report/metrics/BuildTimes;Lorg/jetbrains/kotlin/build/report/metrics/BuildPerformanceMetrics;Lorg/jetbrains/kotlin/build/report/metrics/BuildAttributes;Lorg/jetbrains/kotlin/build/report/metrics/GcMetrics;)V", "getBuildAttributes", "()Lorg/jetbrains/kotlin/build/report/metrics/BuildAttributes;", "getBuildPerformanceMetrics", "()Lorg/jetbrains/kotlin/build/report/metrics/BuildPerformanceMetrics;", "getBuildTimes", "()Lorg/jetbrains/kotlin/build/report/metrics/BuildTimes;", "getGcMetrics", "()Lorg/jetbrains/kotlin/build/report/metrics/GcMetrics;", "addAll", "", "other", "component1", "component2", "component3", "component4", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "", "hashCode", "", "toString", "", "Companion", "kotlin-build-statistics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class BuildMetrics<B extends BuildTime, P extends BuildPerformanceMetric> implements Serializable {
    public static final long serialVersionUID = 0;
    private final BuildAttributes buildAttributes;
    private final BuildPerformanceMetrics<P> buildPerformanceMetrics;
    private final BuildTimes<B> buildTimes;
    private final GcMetrics gcMetrics;

    public BuildMetrics() {
        this(null, null, null, null, 15, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ BuildMetrics copy$default(BuildMetrics buildMetrics, BuildTimes buildTimes, BuildPerformanceMetrics buildPerformanceMetrics, BuildAttributes buildAttributes, GcMetrics gcMetrics, int i, Object obj) {
        if ((i & 1) != 0) {
            buildTimes = buildMetrics.buildTimes;
        }
        if ((i & 2) != 0) {
            buildPerformanceMetrics = buildMetrics.buildPerformanceMetrics;
        }
        if ((i & 4) != 0) {
            buildAttributes = buildMetrics.buildAttributes;
        }
        if ((i & 8) != 0) {
            gcMetrics = buildMetrics.gcMetrics;
        }
        return buildMetrics.copy(buildTimes, buildPerformanceMetrics, buildAttributes, gcMetrics);
    }

    public final BuildTimes<B> component1() {
        return this.buildTimes;
    }

    public final BuildPerformanceMetrics<P> component2() {
        return this.buildPerformanceMetrics;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final BuildAttributes getBuildAttributes() {
        return this.buildAttributes;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final GcMetrics getGcMetrics() {
        return this.gcMetrics;
    }

    public final BuildMetrics<B, P> copy(BuildTimes<B> buildTimes, BuildPerformanceMetrics<P> buildPerformanceMetrics, BuildAttributes buildAttributes, GcMetrics gcMetrics) {
        Intrinsics.checkNotNullParameter(buildTimes, "buildTimes");
        Intrinsics.checkNotNullParameter(buildPerformanceMetrics, "buildPerformanceMetrics");
        Intrinsics.checkNotNullParameter(buildAttributes, "buildAttributes");
        Intrinsics.checkNotNullParameter(gcMetrics, "gcMetrics");
        return new BuildMetrics<>(buildTimes, buildPerformanceMetrics, buildAttributes, gcMetrics);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BuildMetrics)) {
            return false;
        }
        BuildMetrics buildMetrics = (BuildMetrics) other;
        return Intrinsics.areEqual(this.buildTimes, buildMetrics.buildTimes) && Intrinsics.areEqual(this.buildPerformanceMetrics, buildMetrics.buildPerformanceMetrics) && Intrinsics.areEqual(this.buildAttributes, buildMetrics.buildAttributes) && Intrinsics.areEqual(this.gcMetrics, buildMetrics.gcMetrics);
    }

    public int hashCode() {
        return (((((this.buildTimes.hashCode() * 31) + this.buildPerformanceMetrics.hashCode()) * 31) + this.buildAttributes.hashCode()) * 31) + this.gcMetrics.hashCode();
    }

    public String toString() {
        return "BuildMetrics(buildTimes=" + this.buildTimes + ", buildPerformanceMetrics=" + this.buildPerformanceMetrics + ", buildAttributes=" + this.buildAttributes + ", gcMetrics=" + this.gcMetrics + ')';
    }

    public BuildMetrics(BuildTimes<B> buildTimes, BuildPerformanceMetrics<P> buildPerformanceMetrics, BuildAttributes buildAttributes, GcMetrics gcMetrics) {
        Intrinsics.checkNotNullParameter(buildTimes, "buildTimes");
        Intrinsics.checkNotNullParameter(buildPerformanceMetrics, "buildPerformanceMetrics");
        Intrinsics.checkNotNullParameter(buildAttributes, "buildAttributes");
        Intrinsics.checkNotNullParameter(gcMetrics, "gcMetrics");
        this.buildTimes = buildTimes;
        this.buildPerformanceMetrics = buildPerformanceMetrics;
        this.buildAttributes = buildAttributes;
        this.gcMetrics = gcMetrics;
    }

    public /* synthetic */ BuildMetrics(BuildTimes buildTimes, BuildPerformanceMetrics buildPerformanceMetrics, BuildAttributes buildAttributes, GcMetrics gcMetrics, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? new BuildTimes() : buildTimes, (i & 2) != 0 ? new BuildPerformanceMetrics() : buildPerformanceMetrics, (i & 4) != 0 ? new BuildAttributes() : buildAttributes, (i & 8) != 0 ? new GcMetrics() : gcMetrics);
    }

    public final BuildTimes<B> getBuildTimes() {
        return this.buildTimes;
    }

    public final BuildPerformanceMetrics<P> getBuildPerformanceMetrics() {
        return this.buildPerformanceMetrics;
    }

    public final BuildAttributes getBuildAttributes() {
        return this.buildAttributes;
    }

    public final GcMetrics getGcMetrics() {
        return this.gcMetrics;
    }

    public final void addAll(BuildMetrics<B, P> other) {
        Intrinsics.checkNotNullParameter(other, "other");
        this.buildTimes.addAll(other.buildTimes);
        this.buildPerformanceMetrics.addAll(other.buildPerformanceMetrics);
        this.buildAttributes.addAll(other.buildAttributes);
        this.gcMetrics.addAll(other.gcMetrics);
    }
}
