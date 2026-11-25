package org.jetbrains.kotlin.cli.common;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: performanceMeasurements.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0002\b\t\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005¢\u0006\u0002\u0010\u0007J\b\u0010\r\u001a\u00020\u0003H\u0016R\u0011\u0010\u0006\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\t¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/cli/common/GarbageCollectionMeasurement;", "Lorg/jetbrains/kotlin/cli/common/PerformanceMeasurement;", "garbageCollectionKind", "", "milliseconds", "", "count", "(Ljava/lang/String;JJ)V", "getCount", "()J", "getGarbageCollectionKind", "()Ljava/lang/String;", "getMilliseconds", "render", "cli-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class GarbageCollectionMeasurement implements PerformanceMeasurement {
    private final long count;
    private final String garbageCollectionKind;
    private final long milliseconds;

    public GarbageCollectionMeasurement(String garbageCollectionKind, long j, long j2) {
        Intrinsics.checkNotNullParameter(garbageCollectionKind, "garbageCollectionKind");
        this.garbageCollectionKind = garbageCollectionKind;
        this.milliseconds = j;
        this.count = j2;
    }

    public final long getCount() {
        return this.count;
    }

    public final String getGarbageCollectionKind() {
        return this.garbageCollectionKind;
    }

    public final long getMilliseconds() {
        return this.milliseconds;
    }

    @Override // org.jetbrains.kotlin.cli.common.PerformanceMeasurement
    public String render() {
        return "GC time for " + this.garbageCollectionKind + " is " + this.milliseconds + " ms, " + this.count + " collections";
    }
}
