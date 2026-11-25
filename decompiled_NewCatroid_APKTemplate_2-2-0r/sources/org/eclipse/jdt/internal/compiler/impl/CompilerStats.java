package org.eclipse.jdt.internal.compiler.impl;

/* JADX INFO: loaded from: classes14.dex */
public class CompilerStats implements Comparable {
    public long analyzeTime;
    public long endTime;
    public long generateTime;
    public long lineCount;
    public long parseTime;
    public long resolveTime;
    public long startTime;

    public long elapsedTime() {
        return this.endTime - this.startTime;
    }

    @Override // java.lang.Comparable
    public int compareTo(Object obj) {
        long jElapsedTime = elapsedTime();
        long jElapsedTime2 = ((CompilerStats) obj).elapsedTime();
        if (jElapsedTime < jElapsedTime2) {
            return -1;
        }
        return jElapsedTime == jElapsedTime2 ? 0 : 1;
    }
}
