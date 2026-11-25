package org.jetbrains.kotlin.diagnostics;

import com.google.android.gms.cast.MediaError;
import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;

/* JADX INFO: compiled from: Severity.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005¨\u0006\u0006"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/Severity;", "", "(Ljava/lang/String;I)V", "INFO", MediaError.ERROR_TYPE_ERROR, "WARNING", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public enum Severity {
    INFO,
    ERROR,
    WARNING;

    private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

    public static EnumEntries<Severity> getEntries() {
        return $ENTRIES;
    }
}
