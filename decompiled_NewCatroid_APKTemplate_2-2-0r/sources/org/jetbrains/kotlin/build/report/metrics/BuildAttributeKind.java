package org.jetbrains.kotlin.build.report.metrics;

import java.io.Serializable;
import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;

/* JADX INFO: compiled from: BuildAttribute.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0086\u0081\u0002\u0018\u0000 \u00052\b\u0012\u0004\u0012\u00020\u00000\u00012\u00020\u0002:\u0001\u0005B\u0007\b\u0002¢\u0006\u0002\u0010\u0003j\u0002\b\u0004¨\u0006\u0006"}, d2 = {"Lorg/jetbrains/kotlin/build/report/metrics/BuildAttributeKind;", "", "Ljava/io/Serializable;", "(Ljava/lang/String;I)V", "REBUILD_REASON", "Companion", "kotlin-build-statistics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public enum BuildAttributeKind implements Serializable {
    REBUILD_REASON;

    public static final long serialVersionUID = 0;
    private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

    public static EnumEntries<BuildAttributeKind> getEntries() {
        return $ENTRIES;
    }
}
