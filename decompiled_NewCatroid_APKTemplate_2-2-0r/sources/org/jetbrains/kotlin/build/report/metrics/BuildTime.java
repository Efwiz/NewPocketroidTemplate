package org.jetbrains.kotlin.build.report.metrics;

import java.io.Serializable;
import java.util.List;
import kotlin.Metadata;

/* JADX INFO: compiled from: BuildTime.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\n\u0012\u0004\u0012\u00020\u0000\u0018\u00010\u0003H&J\u000e\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00000\u0003H&J\b\u0010\u0005\u001a\u00020\u0006H&J\n\u0010\u0007\u001a\u0004\u0018\u00010\u0000H&J\b\u0010\b\u001a\u00020\u0006H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\tÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/build/report/metrics/BuildTime;", "Ljava/io/Serializable;", "children", "", "getAllMetrics", "getName", "", "getParent", "getReadableString", "kotlin-build-statistics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface BuildTime extends Serializable {
    List<BuildTime> children();

    List<BuildTime> getAllMetrics();

    String getName();

    BuildTime getParent();

    String getReadableString();
}
