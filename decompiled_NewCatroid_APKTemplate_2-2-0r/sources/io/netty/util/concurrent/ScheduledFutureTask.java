package io.netty.util.concurrent;

import io.netty.util.internal.DefaultPriorityQueue;
import io.netty.util.internal.PriorityQueueNode;
import java.util.concurrent.Callable;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes11.dex */
final class ScheduledFutureTask<V> extends PromiseTask<V> implements ScheduledFuture<V>, PriorityQueueNode {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final long START_TIME = System.nanoTime();
    private long deadlineNanos;

    /* JADX INFO: renamed from: id, reason: collision with root package name */
    private long f287id;
    private final long periodNanos;
    private int queueIndex;

    static long nanoTime() {
        return System.nanoTime() - START_TIME;
    }

    static long deadlineNanos(long j) {
        long jNanoTime = nanoTime() + j;
        if (jNanoTime < 0) {
            return Long.MAX_VALUE;
        }
        return jNanoTime;
    }

    static long initialNanoTime() {
        return START_TIME;
    }

    ScheduledFutureTask(AbstractScheduledEventExecutor abstractScheduledEventExecutor, Runnable runnable, long j) {
        super(abstractScheduledEventExecutor, runnable);
        this.queueIndex = -1;
        this.deadlineNanos = j;
        this.periodNanos = 0L;
    }

    ScheduledFutureTask(AbstractScheduledEventExecutor abstractScheduledEventExecutor, Runnable runnable, long j, long j2) {
        super(abstractScheduledEventExecutor, runnable);
        this.queueIndex = -1;
        this.deadlineNanos = j;
        this.periodNanos = validatePeriod(j2);
    }

    ScheduledFutureTask(AbstractScheduledEventExecutor abstractScheduledEventExecutor, Callable<V> callable, long j, long j2) {
        super(abstractScheduledEventExecutor, callable);
        this.queueIndex = -1;
        this.deadlineNanos = j;
        this.periodNanos = validatePeriod(j2);
    }

    ScheduledFutureTask(AbstractScheduledEventExecutor abstractScheduledEventExecutor, Callable<V> callable, long j) {
        super(abstractScheduledEventExecutor, callable);
        this.queueIndex = -1;
        this.deadlineNanos = j;
        this.periodNanos = 0L;
    }

    private static long validatePeriod(long j) {
        if (j != 0) {
            return j;
        }
        throw new IllegalArgumentException("period: 0 (expected: != 0)");
    }

    ScheduledFutureTask<V> setId(long j) {
        if (this.f287id == 0) {
            this.f287id = j;
        }
        return this;
    }

    @Override // io.netty.util.concurrent.DefaultPromise
    protected EventExecutor executor() {
        return super.executor();
    }

    public long deadlineNanos() {
        return this.deadlineNanos;
    }

    void setConsumed() {
        if (this.periodNanos == 0) {
            this.deadlineNanos = 0L;
        }
    }

    public long delayNanos() {
        return deadlineToDelayNanos(deadlineNanos());
    }

    static long deadlineToDelayNanos(long j) {
        if (j == 0) {
            return 0L;
        }
        return Math.max(0L, j - nanoTime());
    }

    public long delayNanos(long j) {
        if (this.deadlineNanos == 0) {
            return 0L;
        }
        return Math.max(0L, deadlineNanos() - (j - START_TIME));
    }

    @Override // java.util.concurrent.Delayed
    public long getDelay(TimeUnit timeUnit) {
        return timeUnit.convert(delayNanos(), TimeUnit.NANOSECONDS);
    }

    @Override // java.lang.Comparable
    public int compareTo(Delayed delayed) {
        if (this == delayed) {
            return 0;
        }
        ScheduledFutureTask scheduledFutureTask = (ScheduledFutureTask) delayed;
        long jDeadlineNanos = deadlineNanos() - scheduledFutureTask.deadlineNanos();
        if (jDeadlineNanos < 0) {
            return -1;
        }
        return (jDeadlineNanos <= 0 && this.f287id < scheduledFutureTask.f287id) ? -1 : 1;
    }

    @Override // io.netty.util.concurrent.PromiseTask, java.util.concurrent.RunnableFuture, java.lang.Runnable
    public void run() {
        try {
            if (delayNanos() > 0) {
                if (isCancelled()) {
                    scheduledExecutor().scheduledTaskQueue().removeTyped(this);
                    return;
                } else {
                    scheduledExecutor().scheduleFromEventLoop(this);
                    return;
                }
            }
            if (this.periodNanos == 0) {
                if (setUncancellableInternal()) {
                    setSuccessInternal(runTask());
                }
            } else {
                if (isCancelled()) {
                    return;
                }
                runTask();
                if (executor().isShutdown()) {
                    return;
                }
                long j = this.periodNanos;
                if (j > 0) {
                    this.deadlineNanos += j;
                } else {
                    this.deadlineNanos = nanoTime() - this.periodNanos;
                }
                if (isCancelled()) {
                    return;
                }
                scheduledExecutor().scheduledTaskQueue().add(this);
            }
        } catch (Throwable th) {
            setFailureInternal(th);
        }
    }

    private AbstractScheduledEventExecutor scheduledExecutor() {
        return (AbstractScheduledEventExecutor) executor();
    }

    @Override // io.netty.util.concurrent.PromiseTask, io.netty.util.concurrent.DefaultPromise, io.netty.util.concurrent.Future, java.util.concurrent.Future
    public boolean cancel(boolean z) {
        boolean zCancel = super.cancel(z);
        if (zCancel) {
            scheduledExecutor().removeScheduled(this);
        }
        return zCancel;
    }

    boolean cancelWithoutRemove(boolean z) {
        return super.cancel(z);
    }

    @Override // io.netty.util.concurrent.PromiseTask, io.netty.util.concurrent.DefaultPromise
    protected StringBuilder toStringBuilder() {
        StringBuilder stringBuilder = super.toStringBuilder();
        stringBuilder.setCharAt(stringBuilder.length() - 1, ',');
        return stringBuilder.append(" deadline: ").append(this.deadlineNanos).append(", period: ").append(this.periodNanos).append(')');
    }

    @Override // io.netty.util.internal.PriorityQueueNode
    public int priorityQueueIndex(DefaultPriorityQueue<?> defaultPriorityQueue) {
        return this.queueIndex;
    }

    @Override // io.netty.util.internal.PriorityQueueNode
    public void priorityQueueIndex(DefaultPriorityQueue<?> defaultPriorityQueue, int i) {
        this.queueIndex = i;
    }
}
