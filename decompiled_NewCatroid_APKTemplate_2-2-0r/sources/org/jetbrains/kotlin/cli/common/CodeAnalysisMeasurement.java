package org.jetbrains.kotlin.cli.common;

import kotlin.Metadata;

/* JADX INFO: compiled from: performanceMeasurements.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B\u0017\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\f\u001a\u00020\rH\u0016R\u0015\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\n\n\u0002\u0010\t\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/cli/common/CodeAnalysisMeasurement;", "Lorg/jetbrains/kotlin/cli/common/PerformanceMeasurement;", "lines", "", "milliseconds", "", "(Ljava/lang/Integer;J)V", "getLines", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "getMilliseconds", "()J", "render", "", "cli-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CodeAnalysisMeasurement implements PerformanceMeasurement {
    private final Integer lines;
    private final long milliseconds;

    public CodeAnalysisMeasurement(Integer num, long j) {
        this.lines = num;
        this.milliseconds = j;
    }

    public final Integer getLines() {
        return this.lines;
    }

    public final long getMilliseconds() {
        return this.milliseconds;
    }

    @Override // org.jetbrains.kotlin.cli.common.PerformanceMeasurement
    public String render() {
        return PerformanceMeasurementsKt.formatMeasurement("ANALYZE", this.milliseconds, this.lines);
    }
}
