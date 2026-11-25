package org.eclipse.jgit.lib;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.eclipse.jgit.lib.internal.WorkQueue;

/* JADX INFO: loaded from: classes13.dex */
public abstract class BatchingProgressMonitor implements ProgressMonitor {
    private long delayStartTime;
    private TimeUnit delayStartUnit = TimeUnit.MILLISECONDS;
    private Task task;

    @Override // org.eclipse.jgit.lib.ProgressMonitor
    public boolean isCancelled() {
        return false;
    }

    protected abstract void onEndTask(String str, int i);

    protected abstract void onEndTask(String str, int i, int i2, int i3);

    protected abstract void onUpdate(String str, int i);

    protected abstract void onUpdate(String str, int i, int i2, int i3);

    @Override // org.eclipse.jgit.lib.ProgressMonitor
    public void start(int i) {
    }

    public void setDelayStart(long j, TimeUnit timeUnit) {
        this.delayStartTime = j;
        this.delayStartUnit = timeUnit;
    }

    @Override // org.eclipse.jgit.lib.ProgressMonitor
    public void beginTask(String str, int i) {
        endTask();
        Task task = new Task(str, i);
        this.task = task;
        long j = this.delayStartTime;
        if (j != 0) {
            task.delay(j, this.delayStartUnit);
        }
    }

    @Override // org.eclipse.jgit.lib.ProgressMonitor
    public void update(int i) {
        Task task = this.task;
        if (task != null) {
            task.update(this, i);
        }
    }

    @Override // org.eclipse.jgit.lib.ProgressMonitor
    public void endTask() {
        Task task = this.task;
        if (task != null) {
            task.end(this);
            this.task = null;
        }
    }

    private static class Task implements Runnable {
        private volatile boolean display = true;
        private int lastPercent;
        private int lastWork;
        private boolean output;
        private final String taskName;
        private Future<?> timerFuture;
        private final int totalWork;

        Task(String str, int i) {
            this.taskName = str;
            this.totalWork = i;
        }

        void delay(long j, TimeUnit timeUnit) {
            this.display = false;
            this.timerFuture = WorkQueue.getExecutor().schedule(this, j, timeUnit);
        }

        @Override // java.lang.Runnable
        public void run() {
            this.display = true;
        }

        void update(BatchingProgressMonitor batchingProgressMonitor, int i) {
            int i2 = this.lastWork + i;
            this.lastWork = i2;
            int i3 = this.totalWork;
            if (i3 == 0) {
                if (this.display) {
                    batchingProgressMonitor.onUpdate(this.taskName, this.lastWork);
                    this.output = true;
                    restartTimer();
                    return;
                }
                return;
            }
            int i4 = (i2 * 100) / i3;
            if (this.display) {
                batchingProgressMonitor.onUpdate(this.taskName, this.lastWork, this.totalWork, i4);
                this.output = true;
                restartTimer();
                this.lastPercent = i4;
                return;
            }
            if (i4 != this.lastPercent) {
                batchingProgressMonitor.onUpdate(this.taskName, this.lastWork, this.totalWork, i4);
                this.output = true;
                this.lastPercent = i4;
            }
        }

        private void restartTimer() {
            this.display = false;
            this.timerFuture = WorkQueue.getExecutor().schedule(this, 1L, TimeUnit.SECONDS);
        }

        void end(BatchingProgressMonitor batchingProgressMonitor) {
            if (this.output) {
                int i = this.totalWork;
                if (i == 0) {
                    batchingProgressMonitor.onEndTask(this.taskName, this.lastWork);
                } else {
                    int i2 = this.lastWork;
                    batchingProgressMonitor.onEndTask(this.taskName, i2, i, (i2 * 100) / i);
                }
            }
            Future<?> future = this.timerFuture;
            if (future != null) {
                future.cancel(false);
            }
        }
    }
}
