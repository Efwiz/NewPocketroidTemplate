package org.eclipse.jgit.lib.internal;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;

/* JADX INFO: loaded from: classes13.dex */
public class WorkQueue {
    private static final ScheduledThreadPoolExecutor executor;
    static final Object executorKiller;

    static {
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1, new ThreadFactory() { // from class: org.eclipse.jgit.lib.internal.WorkQueue.1
            private final ThreadFactory baseFactory = Executors.defaultThreadFactory();

            @Override // java.util.concurrent.ThreadFactory
            public Thread newThread(Runnable runnable) {
                Thread threadNewThread = this.baseFactory.newThread(runnable);
                threadNewThread.setName("JGit-WorkQueue");
                threadNewThread.setContextClassLoader(null);
                threadNewThread.setDaemon(true);
                return threadNewThread;
            }
        });
        executor = scheduledThreadPoolExecutor;
        scheduledThreadPoolExecutor.setRemoveOnCancelPolicy(true);
        scheduledThreadPoolExecutor.setContinueExistingPeriodicTasksAfterShutdownPolicy(false);
        scheduledThreadPoolExecutor.setExecuteExistingDelayedTasksAfterShutdownPolicy(false);
        scheduledThreadPoolExecutor.prestartAllCoreThreads();
        scheduledThreadPoolExecutor.setThreadFactory(Executors.defaultThreadFactory());
        executorKiller = new Object() { // from class: org.eclipse.jgit.lib.internal.WorkQueue.2
            protected void finalize() {
                WorkQueue.executor.shutdownNow();
            }
        };
    }

    public static ScheduledThreadPoolExecutor getExecutor() {
        return executor;
    }
}
