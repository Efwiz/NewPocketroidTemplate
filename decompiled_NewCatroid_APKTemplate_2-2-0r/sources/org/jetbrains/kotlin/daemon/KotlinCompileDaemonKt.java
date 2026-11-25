package org.jetbrains.kotlin.daemon;

import kotlin.Metadata;

/* JADX INFO: compiled from: KotlinCompileDaemon.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\t\n\u0002\b\u0005\"\u0014\u0010\u0000\u001a\u00020\u0001X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0003\"\u0014\u0010\u0004\u001a\u00020\u0001X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0003¨\u0006\u0006"}, d2 = {"DAEMON_PERIODIC_CHECK_INTERVAL_MS", "", "getDAEMON_PERIODIC_CHECK_INTERVAL_MS", "()J", "DAEMON_PERIODIC_SELDOM_CHECK_INTERVAL_MS", "getDAEMON_PERIODIC_SELDOM_CHECK_INTERVAL_MS", "kotlin-daemon"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class KotlinCompileDaemonKt {
    private static final long DAEMON_PERIODIC_CHECK_INTERVAL_MS = 1000;
    private static final long DAEMON_PERIODIC_SELDOM_CHECK_INTERVAL_MS = 60000;

    public static final long getDAEMON_PERIODIC_CHECK_INTERVAL_MS() {
        return DAEMON_PERIODIC_CHECK_INTERVAL_MS;
    }

    public static final long getDAEMON_PERIODIC_SELDOM_CHECK_INTERVAL_MS() {
        return DAEMON_PERIODIC_SELDOM_CHECK_INTERVAL_MS;
    }
}
