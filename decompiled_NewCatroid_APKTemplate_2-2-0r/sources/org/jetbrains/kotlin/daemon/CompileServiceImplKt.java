package org.jetbrains.kotlin.daemon;

import java.util.concurrent.TimeUnit;
import kotlin.Metadata;

/* JADX INFO: compiled from: CompileServiceImpl.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0000\u001a\u0006\u0010\u0002\u001a\u00020\u0003\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u0004"}, d2 = {"REMOTE_STREAM_BUFFER_SIZE", "", "nowSeconds", "", "kotlin-daemon"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class CompileServiceImplKt {
    public static final int REMOTE_STREAM_BUFFER_SIZE = 4096;

    public static final long nowSeconds() {
        return TimeUnit.NANOSECONDS.toSeconds(System.nanoTime());
    }
}
