package org.jetbrains.kotlin.build.report;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.build.report.metrics.BuildPerformanceMetric;
import org.jetbrains.kotlin.build.report.metrics.BuildTime;
import org.jetbrains.kotlin.build.report.metrics.RemoteBuildMetricsReporter;

/* JADX INFO: compiled from: BuildReporter.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u0002*\b\b\u0001\u0010\u0003*\u00020\u00042\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00030\u00052\u00020\u0006B!\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0012\u0010\t\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\n¢\u0006\u0002\u0010\u000bJ\b\u0010\u0010\u001a\u00020\u0011H\u0016R \u0010\t\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\nX\u0094\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0014\u0010\u0007\u001a\u00020\bX\u0094\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/build/report/RemoteBuildReporter;", "B", "Lorg/jetbrains/kotlin/build/report/metrics/BuildTime;", "P", "Lorg/jetbrains/kotlin/build/report/metrics/BuildPerformanceMetric;", "Lorg/jetbrains/kotlin/build/report/BuildReporter;", "Lorg/jetbrains/kotlin/build/report/RemoteReporter;", "icReporter", "Lorg/jetbrains/kotlin/build/report/RemoteICReporter;", "buildMetricsReporter", "Lorg/jetbrains/kotlin/build/report/metrics/RemoteBuildMetricsReporter;", "(Lorg/jetbrains/kotlin/build/report/RemoteICReporter;Lorg/jetbrains/kotlin/build/report/metrics/RemoteBuildMetricsReporter;)V", "getBuildMetricsReporter", "()Lorg/jetbrains/kotlin/build/report/metrics/RemoteBuildMetricsReporter;", "getIcReporter", "()Lorg/jetbrains/kotlin/build/report/RemoteICReporter;", "flush", "", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class RemoteBuildReporter<B extends BuildTime, P extends BuildPerformanceMetric> extends BuildReporter<B, P> implements RemoteReporter {
    private final RemoteBuildMetricsReporter<B, P> buildMetricsReporter;
    private final RemoteICReporter icReporter;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.build.report.BuildReporter
    public RemoteICReporter getIcReporter() {
        return this.icReporter;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.build.report.BuildReporter
    public RemoteBuildMetricsReporter<B, P> getBuildMetricsReporter() {
        return this.buildMetricsReporter;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public RemoteBuildReporter(RemoteICReporter icReporter, RemoteBuildMetricsReporter<B, P> buildMetricsReporter) {
        super(icReporter, buildMetricsReporter);
        Intrinsics.checkNotNullParameter(icReporter, "icReporter");
        Intrinsics.checkNotNullParameter(buildMetricsReporter, "buildMetricsReporter");
        this.icReporter = icReporter;
        this.buildMetricsReporter = buildMetricsReporter;
    }

    @Override // org.jetbrains.kotlin.build.report.RemoteReporter
    public void flush() {
        getIcReporter().flush();
        getBuildMetricsReporter().flush();
    }
}
