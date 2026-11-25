package org.jetbrains.kotlin.com.intellij.util.concurrency;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Delayed;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RunnableScheduledFuture;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import kotlin.time.DurationKt;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;

/* JADX INFO: loaded from: classes3.dex */
class SchedulingWrapper implements ScheduledExecutorService {
    private static final AtomicLong sequencer = new AtomicLong();
    final ExecutorService backendExecutorService;
    final AppDelayQueue delayQueue;
    private final AtomicBoolean shutdown;

    /* JADX WARN: Code duplicated, block: B:69:0x00d0  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 3 || i == 4 || i == 5 || i == 13 || i == 15 || i == 18 || i == 23 || i == 25 || i == 27 || i == 29 || i == 31 || i == 34 || i == 36) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 3 || i == 4 || i == 5 || i == 13 || i == 15 || i == 18 || i == 23 || i == 25 || i == 27 || i == 29 || i == 31 || i == 34 || i == 36) ? 2 : 3];
        switch (i) {
            case 1:
                objArr[0] = "delayQueue";
                break;
            case 2:
            case 3:
            case 4:
            case 5:
            case 13:
            case 15:
            case 18:
            case 23:
            case 25:
            case 27:
            case 29:
            case 31:
            case 34:
            case 36:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/util/concurrency/SchedulingWrapper";
                break;
            case 6:
            case 12:
            case 17:
            case 20:
            case 22:
            case 33:
            case 38:
                objArr[0] = "unit";
                break;
            case 7:
            case 24:
            case 26:
            case 28:
                objArr[0] = "task";
                break;
            case 8:
            case 9:
            case 10:
                objArr[0] = "queue";
                break;
            case 11:
            case 19:
            case 21:
            case 39:
                objArr[0] = "command";
                break;
            case 14:
                objArr[0] = "t";
                break;
            case 16:
                objArr[0] = "callable";
                break;
            case 30:
            case 32:
            case 35:
            case 37:
                objArr[0] = "tasks";
                break;
            default:
                objArr[0] = "backendExecutorService";
                break;
        }
        if (i == 2) {
            objArr[1] = "shutdownNow";
        } else if (i == 3) {
            objArr[1] = "doShutdownNow";
        } else if (i == 4 || i == 5) {
            objArr[1] = "cancelAndRemoveTasksFromQueue";
        } else if (i == 13) {
            objArr[1] = "schedule";
        } else if (i == 15) {
            objArr[1] = "delayedExecute";
        } else if (i == 18) {
            objArr[1] = "schedule";
        } else if (i == 23) {
            objArr[1] = "scheduleWithFixedDelay";
        } else if (i == 25 || i == 27 || i == 29) {
            objArr[1] = "submit";
        } else if (i == 31 || i == 34) {
            objArr[1] = "invokeAll";
        } else if (i != 36) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/util/concurrency/SchedulingWrapper";
        } else {
            objArr[1] = "invokeAny";
        }
        switch (i) {
            case 2:
            case 3:
            case 4:
            case 5:
            case 13:
            case 15:
            case 18:
            case 23:
            case 25:
            case 27:
            case 29:
            case 31:
            case 34:
            case 36:
                break;
            case 6:
                objArr[2] = "awaitTermination";
                break;
            case 7:
                objArr[2] = "futureDone";
                break;
            case 8:
            case 9:
                objArr[2] = "triggerTime";
                break;
            case 10:
                objArr[2] = "overflowFree";
                break;
            case 11:
            case 12:
            case 16:
            case 17:
                objArr[2] = "schedule";
                break;
            case 14:
                objArr[2] = "delayedExecute";
                break;
            case 19:
            case 20:
                objArr[2] = "scheduleAtFixedRate";
                break;
            case 21:
            case 22:
                objArr[2] = "scheduleWithFixedDelay";
                break;
            case 24:
            case 26:
            case 28:
                objArr[2] = "submit";
                break;
            case 30:
            case 32:
            case 33:
                objArr[2] = "invokeAll";
                break;
            case 35:
            case 37:
            case 38:
                objArr[2] = "invokeAny";
                break;
            case 39:
                objArr[2] = "execute";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 3 && i != 4 && i != 5 && i != 13 && i != 15 && i != 18 && i != 23 && i != 25 && i != 27 && i != 29 && i != 31 && i != 34 && i != 36) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    void futureDone(Future<?> future) {
        if (future == null) {
            $$$reportNull$$$0(7);
        }
    }

    SchedulingWrapper(ExecutorService executorService, AppDelayQueue appDelayQueue) {
        if (executorService == null) {
            $$$reportNull$$$0(0);
        }
        if (appDelayQueue == null) {
            $$$reportNull$$$0(1);
        }
        this.shutdown = new AtomicBoolean();
        this.delayQueue = appDelayQueue;
        if (executorService instanceof ScheduledExecutorService) {
            throw new IllegalArgumentException("backendExecutorService: " + executorService + " is already ScheduledExecutorService");
        }
        this.backendExecutorService = executorService;
    }

    @Override // java.util.concurrent.ExecutorService
    public List<Runnable> shutdownNow() {
        List<Runnable> listDoShutdownNow = doShutdownNow();
        if (listDoShutdownNow == null) {
            $$$reportNull$$$0(2);
        }
        return listDoShutdownNow;
    }

    @Override // java.util.concurrent.ExecutorService
    public void shutdown() {
        doShutdown();
    }

    void doShutdown() {
        if (!this.shutdown.compareAndSet(false, true)) {
            throw new IllegalStateException("Already shutdown");
        }
    }

    List<Runnable> doShutdownNow() {
        doShutdown();
        List<Runnable> listCancelAndRemoveTasksFromQueue = cancelAndRemoveTasksFromQueue();
        if (listCancelAndRemoveTasksFromQueue == null) {
            $$$reportNull$$$0(3);
        }
        return listCancelAndRemoveTasksFromQueue;
    }

    List<Runnable> cancelAndRemoveTasksFromQueue() {
        ArrayList arrayList = new ArrayList();
        for (MyScheduledFutureTask<?> myScheduledFutureTask : this.delayQueue) {
            if (myScheduledFutureTask.getBackendExecutorService() == this.backendExecutorService) {
                myScheduledFutureTask.cancel(false);
                arrayList.add(myScheduledFutureTask);
            }
        }
        if (arrayList.isEmpty()) {
            List<Runnable> listEmptyList = Collections.emptyList();
            if (listEmptyList == null) {
                $$$reportNull$$$0(4);
            }
            return listEmptyList;
        }
        this.delayQueue.removeAll(arrayList);
        return arrayList;
    }

    @Override // java.util.concurrent.ExecutorService
    public boolean isShutdown() {
        return this.shutdown.get();
    }

    @Override // java.util.concurrent.ExecutorService
    public boolean isTerminated() {
        return isShutdown();
    }

    @Override // java.util.concurrent.ExecutorService
    public boolean awaitTermination(long j, TimeUnit timeUnit) throws InterruptedException {
        if (timeUnit == null) {
            $$$reportNull$$$0(6);
        }
        if (!isShutdown()) {
            throw new IllegalStateException("must await termination after shutdown() or shutdownNow() only");
        }
        for (MyScheduledFutureTask myScheduledFutureTask : new ArrayList(this.delayQueue)) {
            if (myScheduledFutureTask.getBackendExecutorService() == this.backendExecutorService) {
                try {
                    myScheduledFutureTask.get(j, timeUnit);
                } catch (ExecutionException unused) {
                } catch (TimeoutException unused2) {
                    return false;
                }
            }
        }
        return this.backendExecutorService.awaitTermination(j, timeUnit);
    }

    class MyScheduledFutureTask<V> extends FutureTask<V> implements RunnableScheduledFuture<V> {
        private final long period;
        private final long sequenceNumber;
        final /* synthetic */ SchedulingWrapper this$0;
        private long time;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = i != 5 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
            Object[] objArr = new Object[i != 5 ? 3 : 2];
            if (i == 2) {
                objArr[0] = "callable";
            } else if (i == 3) {
                objArr[0] = "unit";
            } else if (i == 4) {
                objArr[0] = "other";
            } else if (i != 5) {
                objArr[0] = "r";
            } else {
                objArr[0] = "org/jetbrains/kotlin/com/intellij/util/concurrency/SchedulingWrapper$MyScheduledFutureTask";
            }
            if (i != 5) {
                objArr[1] = "org/jetbrains/kotlin/com/intellij/util/concurrency/SchedulingWrapper$MyScheduledFutureTask";
            } else {
                objArr[1] = "getBackendExecutorService";
            }
            if (i == 3) {
                objArr[2] = "getDelay";
            } else if (i == 4) {
                objArr[2] = "compareTo";
            } else if (i != 5) {
                objArr[2] = "<init>";
            }
            String str2 = String.format(str, objArr);
            if (i == 5) {
                throw new IllegalStateException(str2);
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        MyScheduledFutureTask(SchedulingWrapper schedulingWrapper, Runnable runnable, V v, long j) {
            super(runnable, v);
            if (runnable == null) {
                $$$reportNull$$$0(0);
            }
            this.this$0 = schedulingWrapper;
            this.time = j;
            this.period = 0L;
            this.sequenceNumber = SchedulingWrapper.sequencer.getAndIncrement();
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        private MyScheduledFutureTask(SchedulingWrapper schedulingWrapper, Runnable runnable, V v, long j, long j2) {
            super(runnable, v);
            if (runnable == null) {
                $$$reportNull$$$0(1);
            }
            this.this$0 = schedulingWrapper;
            this.time = j;
            this.period = j2;
            this.sequenceNumber = SchedulingWrapper.sequencer.getAndIncrement();
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        private MyScheduledFutureTask(SchedulingWrapper schedulingWrapper, Callable<V> callable, long j) {
            super(callable);
            if (callable == null) {
                $$$reportNull$$$0(2);
            }
            this.this$0 = schedulingWrapper;
            this.time = j;
            this.period = 0L;
            this.sequenceNumber = SchedulingWrapper.sequencer.getAndIncrement();
        }

        @Override // java.util.concurrent.FutureTask, java.util.concurrent.Future
        public boolean cancel(boolean z) {
            boolean zCancel = super.cancel(z);
            this.this$0.delayQueue.remove(this);
            return zCancel;
        }

        @Override // java.util.concurrent.Delayed
        public long getDelay(TimeUnit timeUnit) {
            if (timeUnit == null) {
                $$$reportNull$$$0(3);
            }
            return timeUnit.convert(this.time - SchedulingWrapper.now(), TimeUnit.NANOSECONDS);
        }

        @Override // java.lang.Comparable
        public int compareTo(Delayed delayed) {
            if (delayed == null) {
                $$$reportNull$$$0(4);
            }
            if (delayed == this) {
                return 0;
            }
            if (delayed instanceof MyScheduledFutureTask) {
                MyScheduledFutureTask myScheduledFutureTask = (MyScheduledFutureTask) delayed;
                long j = this.time - myScheduledFutureTask.time;
                if (j < 0) {
                    return -1;
                }
                return (j <= 0 && this.sequenceNumber < myScheduledFutureTask.sequenceNumber) ? -1 : 1;
            }
            long delay = getDelay(TimeUnit.NANOSECONDS) - delayed.getDelay(TimeUnit.NANOSECONDS);
            if (delay < 0) {
                return -1;
            }
            return delay > 0 ? 1 : 0;
        }

        @Override // java.util.concurrent.RunnableScheduledFuture
        public boolean isPeriodic() {
            return this.period != 0;
        }

        private void setNextRunTime() {
            long j = this.period;
            if (j <= 0) {
                this.time = SchedulingWrapper.triggerTime(this.this$0.delayQueue, -j);
            } else {
                this.time += j;
            }
        }

        @Override // java.util.concurrent.FutureTask, java.util.concurrent.RunnableFuture, java.lang.Runnable
        public void run() {
            if (!isPeriodic()) {
                super.run();
                this.this$0.futureDone(this);
            } else if (runAndReset()) {
                setNextRunTime();
                this.this$0.delayQueue.offer(this);
            }
        }

        @Override // java.util.concurrent.FutureTask
        public String toString() {
            Object objInfo = BoundedTaskExecutor.info(this);
            StringBuilder sbAppend = new StringBuilder("Delay: ").append(getDelay(TimeUnit.MILLISECONDS)).append("ms; ");
            if (objInfo == this) {
                objInfo = super.toString();
            }
            return sbAppend.append(objInfo).append(" backendExecutorService: ").append(this.this$0.backendExecutorService).toString();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public ExecutorService getBackendExecutorService() {
            ExecutorService executorService = this.this$0.backendExecutorService;
            if (executorService == null) {
                $$$reportNull$$$0(5);
            }
            return executorService;
        }

        void executeMeInBackendExecutor() {
            this.this$0.backendExecutorService.execute(this);
        }
    }

    static long triggerTime(AppDelayQueue appDelayQueue, long j, TimeUnit timeUnit) {
        if (appDelayQueue == null) {
            $$$reportNull$$$0(8);
        }
        if (j < 0) {
            j = 0;
        }
        return triggerTime(appDelayQueue, timeUnit.toNanos(j));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static long now() {
        return System.nanoTime();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static long triggerTime(AppDelayQueue appDelayQueue, long j) {
        if (appDelayQueue == null) {
            $$$reportNull$$$0(9);
        }
        long jNow = now();
        if (j >= DurationKt.MAX_MILLIS) {
            j = overflowFree(appDelayQueue, j);
        }
        return jNow + j;
    }

    private static long overflowFree(AppDelayQueue appDelayQueue, long j) {
        if (appDelayQueue == null) {
            $$$reportNull$$$0(10);
        }
        MyScheduledFutureTask<?> myScheduledFutureTaskPeek = appDelayQueue.peek();
        if (myScheduledFutureTaskPeek == null) {
            return j;
        }
        long delay = myScheduledFutureTaskPeek.getDelay(TimeUnit.NANOSECONDS);
        return (delay >= 0 || j - delay >= 0) ? j : delay + Long.MAX_VALUE;
    }

    @Override // java.util.concurrent.ScheduledExecutorService
    public ScheduledFuture<?> schedule(Runnable runnable, long j, TimeUnit timeUnit) {
        if (runnable == null) {
            $$$reportNull$$$0(11);
        }
        if (timeUnit == null) {
            $$$reportNull$$$0(12);
        }
        MyScheduledFutureTask myScheduledFutureTaskDelayedExecute = delayedExecute(new MyScheduledFutureTask(this, runnable, (Object) null, triggerTime(this.delayQueue, j, timeUnit)));
        if (myScheduledFutureTaskDelayedExecute == null) {
            $$$reportNull$$$0(13);
        }
        return myScheduledFutureTaskDelayedExecute;
    }

    <T> MyScheduledFutureTask<T> delayedExecute(MyScheduledFutureTask<T> myScheduledFutureTask) {
        if (myScheduledFutureTask == null) {
            $$$reportNull$$$0(14);
        }
        if (isShutdown()) {
            throw new RejectedExecutionException("Already shutdown");
        }
        this.delayQueue.add(myScheduledFutureTask);
        if (myScheduledFutureTask.getDelay(TimeUnit.DAYS) > 31 && !myScheduledFutureTask.isPeriodic()) {
            throw new IllegalArgumentException("Unsupported crazy delay " + myScheduledFutureTask.getDelay(TimeUnit.DAYS) + " days: " + BoundedTaskExecutor.info(myScheduledFutureTask));
        }
        if (myScheduledFutureTask == null) {
            $$$reportNull$$$0(15);
        }
        return myScheduledFutureTask;
    }

    @Override // java.util.concurrent.ScheduledExecutorService
    public <V> ScheduledFuture<V> schedule(Callable<V> callable, long j, TimeUnit timeUnit) {
        if (callable == null) {
            $$$reportNull$$$0(16);
        }
        if (timeUnit == null) {
            $$$reportNull$$$0(17);
        }
        MyScheduledFutureTask myScheduledFutureTaskDelayedExecute = delayedExecute(new MyScheduledFutureTask(callable, triggerTime(this.delayQueue, j, timeUnit)));
        if (myScheduledFutureTaskDelayedExecute == null) {
            $$$reportNull$$$0(18);
        }
        return myScheduledFutureTaskDelayedExecute;
    }

    @Override // java.util.concurrent.ScheduledExecutorService
    public ScheduledFuture<?> scheduleAtFixedRate(Runnable runnable, long j, long j2, TimeUnit timeUnit) {
        if (runnable == null) {
            $$$reportNull$$$0(19);
        }
        if (timeUnit == null) {
            $$$reportNull$$$0(20);
        }
        throw new IncorrectOperationException("Not supported because it's bad for hibernation; use scheduleWithFixedDelay() with the same parameters instead.");
    }

    @Override // java.util.concurrent.ScheduledExecutorService
    public ScheduledFuture<?> scheduleWithFixedDelay(Runnable runnable, long j, long j2, TimeUnit timeUnit) {
        if (runnable == null) {
            $$$reportNull$$$0(21);
        }
        if (timeUnit == null) {
            $$$reportNull$$$0(22);
        }
        if (j2 <= 0) {
            throw new IllegalArgumentException("delay must be positive but got: " + j2);
        }
        MyScheduledFutureTask myScheduledFutureTaskDelayedExecute = delayedExecute(new MyScheduledFutureTask(runnable, null, triggerTime(this.delayQueue, j, timeUnit), timeUnit.toNanos(-j2)));
        if (myScheduledFutureTaskDelayedExecute == null) {
            $$$reportNull$$$0(23);
        }
        return myScheduledFutureTaskDelayedExecute;
    }

    @Override // java.util.concurrent.ExecutorService
    public <T> Future<T> submit(Callable<T> callable) {
        if (callable == null) {
            $$$reportNull$$$0(24);
        }
        Future<T> futureSubmit = this.backendExecutorService.submit(callable);
        if (futureSubmit == null) {
            $$$reportNull$$$0(25);
        }
        return futureSubmit;
    }

    @Override // java.util.concurrent.ExecutorService
    public <T> Future<T> submit(Runnable runnable, T t) {
        if (runnable == null) {
            $$$reportNull$$$0(26);
        }
        Future<T> futureSubmit = this.backendExecutorService.submit(runnable, t);
        if (futureSubmit == null) {
            $$$reportNull$$$0(27);
        }
        return futureSubmit;
    }

    @Override // java.util.concurrent.ExecutorService
    public Future<?> submit(Runnable runnable) {
        if (runnable == null) {
            $$$reportNull$$$0(28);
        }
        Future<?> futureSubmit = this.backendExecutorService.submit(runnable);
        if (futureSubmit == null) {
            $$$reportNull$$$0(29);
        }
        return futureSubmit;
    }

    @Override // java.util.concurrent.ExecutorService
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> collection) throws InterruptedException {
        if (collection == null) {
            $$$reportNull$$$0(30);
        }
        List<Future<T>> listInvokeAll = this.backendExecutorService.invokeAll(collection);
        if (listInvokeAll == null) {
            $$$reportNull$$$0(31);
        }
        return listInvokeAll;
    }

    @Override // java.util.concurrent.ExecutorService
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> collection, long j, TimeUnit timeUnit) throws InterruptedException {
        if (collection == null) {
            $$$reportNull$$$0(32);
        }
        if (timeUnit == null) {
            $$$reportNull$$$0(33);
        }
        List<Future<T>> listInvokeAll = this.backendExecutorService.invokeAll(collection, j, timeUnit);
        if (listInvokeAll == null) {
            $$$reportNull$$$0(34);
        }
        return listInvokeAll;
    }

    @Override // java.util.concurrent.ExecutorService
    public <T> T invokeAny(Collection<? extends Callable<T>> collection) throws ExecutionException, InterruptedException {
        if (collection == null) {
            $$$reportNull$$$0(35);
        }
        T t = (T) this.backendExecutorService.invokeAny(collection);
        if (t == null) {
            $$$reportNull$$$0(36);
        }
        return t;
    }

    @Override // java.util.concurrent.ExecutorService
    public <T> T invokeAny(Collection<? extends Callable<T>> collection, long j, TimeUnit timeUnit) throws ExecutionException, InterruptedException, TimeoutException {
        if (collection == null) {
            $$$reportNull$$$0(37);
        }
        if (timeUnit == null) {
            $$$reportNull$$$0(38);
        }
        return (T) this.backendExecutorService.invokeAny(collection, j, timeUnit);
    }

    @Override // java.util.concurrent.Executor
    public void execute(Runnable runnable) {
        if (runnable == null) {
            $$$reportNull$$$0(39);
        }
        this.backendExecutorService.execute(runnable);
    }
}
