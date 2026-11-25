package org.eclipse.jgit.lib;

/* JADX INFO: loaded from: classes13.dex */
public interface ProgressMonitor {
    public static final int UNKNOWN = 0;

    void beginTask(String str, int i);

    void endTask();

    boolean isCancelled();

    void start(int i);

    void update(int i);
}
