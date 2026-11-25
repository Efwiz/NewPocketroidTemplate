package org.jetbrains.kotlin.cli.common;

import kotlin.Metadata;

/* JADX INFO: compiled from: performanceMeasurements.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0005\u001a\u00020\u0006H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/cli/common/JitCompilationMeasurement;", "Lorg/jetbrains/kotlin/cli/common/PerformanceMeasurement;", "milliseconds", "", "(J)V", "render", "", "cli-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JitCompilationMeasurement implements PerformanceMeasurement {
    private final long milliseconds;

    public JitCompilationMeasurement(long j) {
        this.milliseconds = j;
    }

    @Override // org.jetbrains.kotlin.cli.common.PerformanceMeasurement
    public String render() {
        return "JIT time is " + this.milliseconds + " ms";
    }
}
