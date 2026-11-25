package com.android.tools.r8.retrace;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class RetraceFailedException extends Exception {
    public RetraceFailedException() {
        super("Retrace failed to complete");
    }

    public RetraceFailedException(String str, Throwable th) {
        super(str, th);
    }
}
