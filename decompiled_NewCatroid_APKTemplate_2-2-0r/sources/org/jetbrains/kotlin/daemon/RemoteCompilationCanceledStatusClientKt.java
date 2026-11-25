package org.jetbrains.kotlin.daemon;

import java.util.concurrent.TimeUnit;
import kotlin.Metadata;

/* JADX INFO: compiled from: RemoteCompilationCanceledStatusClient.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\"\u0011\u0010\u0000\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"CANCELED_STATUS_CHECK_THRESHOLD_NS", "", "getCANCELED_STATUS_CHECK_THRESHOLD_NS", "()J", "kotlin-daemon"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class RemoteCompilationCanceledStatusClientKt {
    private static final long CANCELED_STATUS_CHECK_THRESHOLD_NS = TimeUnit.MILLISECONDS.toNanos(100);

    public static final long getCANCELED_STATUS_CHECK_THRESHOLD_NS() {
        return CANCELED_STATUS_CHECK_THRESHOLD_NS;
    }
}
