package org.jetbrains.kotlin.build.report.statistics;

import java.text.SimpleDateFormat;
import java.util.TimeZone;
import kotlin.Metadata;

/* JADX INFO: compiled from: CompileStatisticsData.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\b\n\u0000\n\u0002\u0018\u0002\n\u0000\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0002"}, d2 = {"formatter", "Ljava/text/SimpleDateFormat;", "kotlin-build-statistics"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class CompileStatisticsDataKt {
    private static final SimpleDateFormat formatter;

    static {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        formatter = simpleDateFormat;
    }
}
