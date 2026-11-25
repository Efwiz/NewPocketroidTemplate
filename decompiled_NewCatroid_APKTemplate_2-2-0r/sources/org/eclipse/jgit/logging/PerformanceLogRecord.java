package org.eclipse.jgit.logging;

/* JADX INFO: loaded from: classes13.dex */
public class PerformanceLogRecord {
    private long durationMs;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    private String f657name;

    public PerformanceLogRecord(String str, long j) {
        this.f657name = str;
        this.durationMs = j;
    }

    public String getName() {
        return this.f657name;
    }

    public long getDurationMs() {
        return this.durationMs;
    }
}
