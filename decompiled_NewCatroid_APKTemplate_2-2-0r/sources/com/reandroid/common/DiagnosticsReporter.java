package com.reandroid.common;

/* JADX INFO: loaded from: classes10.dex */
public interface DiagnosticsReporter {
    default boolean isDebugEnabled() {
        return false;
    }

    default boolean isReportEnabled() {
        return true;
    }

    default boolean isVerboseEnabled() {
        return false;
    }

    void report(DiagnosticMessage diagnosticMessage);
}
