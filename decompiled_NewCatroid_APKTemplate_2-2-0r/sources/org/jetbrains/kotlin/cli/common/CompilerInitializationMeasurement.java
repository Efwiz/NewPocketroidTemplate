package org.jetbrains.kotlin.cli.common;

import kotlin.Metadata;

/* JADX INFO: compiled from: performanceMeasurements.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0007\u001a\u00020\bH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/cli/common/CompilerInitializationMeasurement;", "Lorg/jetbrains/kotlin/cli/common/PerformanceMeasurement;", "milliseconds", "", "(J)V", "getMilliseconds", "()J", "render", "", "cli-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CompilerInitializationMeasurement implements PerformanceMeasurement {
    private final long milliseconds;

    public CompilerInitializationMeasurement(long j) {
        this.milliseconds = j;
    }

    public final long getMilliseconds() {
        return this.milliseconds;
    }

    @Override // org.jetbrains.kotlin.cli.common.PerformanceMeasurement
    public String render() {
        return "INIT: Compiler initialized in " + this.milliseconds + " ms";
    }
}
