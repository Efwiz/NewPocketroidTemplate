package org.jetbrains.kotlin.com.intellij.util.concurrency;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.LongUnaryOperator;
import name.antonsmirnov.firmata.FormatHelper;
import org.jetbrains.kotlin.com.intellij.diagnostic.LoadingState;
import org.jetbrains.kotlin.com.intellij.diagnostic.StartUpMeasurer;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.ControlFlowException;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.progress.JobFutureTask;
import org.jetbrains.kotlin.com.intellij.openapi.progress.JobRunnable;
import org.jetbrains.kotlin.com.intellij.openapi.util.registry.Registry;
import org.jetbrains.kotlin.com.intellij.util.ConcurrencyUtil;
import org.jetbrains.kotlin.com.intellij.util.Function;
import org.jetbrains.kotlin.com.intellij.util.ObjectUtils;
import org.jetbrains.kotlin.com.intellij.util.ReflectionUtil;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;

/* JADX INFO: loaded from: classes3.dex */
public final class BoundedTaskExecutor extends AbstractExecutorService {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final Executor myBackendExecutor;
    private final boolean myChangeThreadName;
    private final int myMaxThreads;
    private final String myName;
    private volatile boolean myShutdown;
    private final AtomicLong myStatus;
    private final BlockingQueue<Runnable> myTaskQueue;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 5 || i == 11) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 5 || i == 11) ? 2 : 3];
        switch (i) {
            case 1:
            case 3:
                objArr[0] = "backendExecutor";
                break;
            case 2:
            default:
                objArr[0] = "name";
                break;
            case 4:
                objArr[0] = "queue";
                break;
            case 5:
            case 11:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/util/concurrency/BoundedTaskExecutor";
                break;
            case 6:
            case 10:
                objArr[0] = "unit";
                break;
            case 7:
                objArr[0] = "command";
                break;
            case 8:
                objArr[0] = "task";
                break;
            case 9:
                objArr[0] = "firstTask";
                break;
        }
        if (i == 5) {
            objArr[1] = "shutdownNow";
        } else if (i != 11) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/util/concurrency/BoundedTaskExecutor";
        } else {
            objArr[1] = "clearAndCancelAll";
        }
        switch (i) {
            case 5:
            case 11:
                break;
            case 6:
                objArr[2] = "awaitTermination";
                break;
            case 7:
                objArr[2] = "execute";
                break;
            case 8:
                objArr[2] = "executeRaw";
                break;
            case 9:
                objArr[2] = "wrapAndExecute";
                break;
            case 10:
                objArr[2] = "waitAllTasksExecuted";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 5 && i != 11) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    static /* synthetic */ long lambda$incrementCounterAndTimestamp$0(long j) {
        return (j + 4294967297L) & Long.MAX_VALUE;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    BoundedTaskExecutor(String str, Executor executor, int i, boolean z) {
        this(str, executor, i, z, new LinkedBlockingQueue());
        if (str == null) {
            $$$reportNull$$$0(0);
        }
        if (executor == null) {
            $$$reportNull$$$0(1);
        }
        if (str.isEmpty() || !Character.isUpperCase(str.charAt(0))) {
            Logger.getInstance(getClass()).warn("Pool name must be capitalized but got: '" + str + FormatHelper.QUOTE, new IllegalArgumentException());
        }
    }

    BoundedTaskExecutor(String str, Executor executor, int i, boolean z, BlockingQueue<Runnable> blockingQueue) {
        if (str == null) {
            $$$reportNull$$$0(2);
        }
        if (executor == null) {
            $$$reportNull$$$0(3);
        }
        if (blockingQueue == null) {
            $$$reportNull$$$0(4);
        }
        this.myStatus = new AtomicLong();
        this.myName = str;
        this.myBackendExecutor = executor;
        if (i < 1) {
            throw new IllegalArgumentException("maxThreads must be >=1 but got: " + i);
        }
        if (executor instanceof BoundedTaskExecutor) {
            throw new IllegalArgumentException("backendExecutor is already BoundedTaskExecutor: " + executor);
        }
        this.myMaxThreads = i;
        this.myChangeThreadName = z;
        this.myTaskQueue = blockingQueue;
    }

    static Object info(Runnable runnable) {
        String str;
        boolean z = runnable instanceof FutureTask;
        String str2 = null;
        Object objChooseNotNull = runnable;
        if (z) {
            FutureTask futureTask = (FutureTask) runnable;
            if (!futureTask.isCancelled()) {
                str = futureTask.isDone() ? " (future done)" : " (future cancelled)";
                objChooseNotNull = ObjectUtils.chooseNotNull(ReflectionUtil.getField(runnable.getClass(), runnable, Callable.class, "callable"), runnable);
            }
            str2 = str;
            objChooseNotNull = ObjectUtils.chooseNotNull(ReflectionUtil.getField(runnable.getClass(), runnable, Callable.class, "callable"), runnable);
        }
        boolean z2 = objChooseNotNull instanceof Callable;
        Object objChooseNotNull2 = objChooseNotNull;
        if (z2 && objChooseNotNull.getClass().getName().equals("java.util.concurrent.Executors$RunnableAdapter")) {
            objChooseNotNull2 = objChooseNotNull;
            objChooseNotNull2 = ObjectUtils.chooseNotNull(ReflectionUtil.getField(objChooseNotNull.getClass(), objChooseNotNull, Runnable.class, "task"), objChooseNotNull);
        }
        return str2 == null ? objChooseNotNull2 : objChooseNotNull2.getClass() + str2;
    }

    @Override // java.util.concurrent.ExecutorService
    public void shutdown() {
        if (this.myShutdown) {
            throw new IllegalStateException("Already shut down: " + this);
        }
        this.myShutdown = true;
    }

    @Override // java.util.concurrent.ExecutorService
    public List<Runnable> shutdownNow() {
        shutdown();
        List<Runnable> listClearAndCancelAll = clearAndCancelAll();
        if (listClearAndCancelAll == null) {
            $$$reportNull$$$0(5);
        }
        return listClearAndCancelAll;
    }

    @Override // java.util.concurrent.ExecutorService
    public boolean isShutdown() {
        return this.myShutdown;
    }

    @Override // java.util.concurrent.ExecutorService
    public boolean isTerminated() {
        return this.myShutdown;
    }

    private static class LastTask extends FutureTask<Void> {
        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "runnable", "org/jetbrains/kotlin/com/intellij/util/concurrency/BoundedTaskExecutor$LastTask", "<init>"));
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        LastTask(Runnable runnable) {
            super(runnable, null);
            if (runnable == null) {
                $$$reportNull$$$0(0);
            }
        }
    }

    @Override // java.util.concurrent.ExecutorService
    public boolean awaitTermination(long j, TimeUnit timeUnit) throws InterruptedException {
        if (timeUnit == null) {
            $$$reportNull$$$0(6);
        }
        if (!isShutdown()) {
            throw new IllegalStateException("you must call shutdown() or shutdownNow() first");
        }
        try {
            waitAllTasksExecuted(j, timeUnit);
            return true;
        } catch (ExecutionException e) {
            throw new RuntimeException(e.getCause());
        } catch (TimeoutException unused) {
            return false;
        }
    }

    @Override // java.util.concurrent.AbstractExecutorService
    protected <T> RunnableFuture<T> newTaskFor(Runnable runnable, T t) {
        return newTaskFor(Executors.callable(runnable, t));
    }

    @Override // java.util.concurrent.AbstractExecutorService
    protected <T> RunnableFuture<T> newTaskFor(Callable<T> callable) {
        if (LoadingState.APP_STARTED.isOccurred() && Registry.is("ide.cancellation.propagate")) {
            return JobFutureTask.jobRunnableFuture(callable);
        }
        return super.newTaskFor(callable);
    }

    @Override // java.util.concurrent.Executor
    public void execute(Runnable runnable) {
        if (runnable == null) {
            $$$reportNull$$$0(7);
        }
        if (LoadingState.APP_STARTED.isOccurred() && Registry.is("ide.cancellation.propagate")) {
            executeRaw(JobRunnable.jobRunnable(runnable));
        } else {
            executeRaw(runnable);
        }
    }

    private void executeRaw(Runnable runnable) {
        if (runnable == null) {
            $$$reportNull$$$0(8);
        }
        if (isShutdown() && !(runnable instanceof LastTask)) {
            throw new RejectedExecutionException("Already shutdown");
        }
        long jIncrementCounterAndTimestamp = incrementCounterAndTimestamp();
        if (((int) jIncrementCounterAndTimestamp) <= this.myMaxThreads) {
            wrapAndExecute(runnable, jIncrementCounterAndTimestamp);
        } else {
            if (!this.myTaskQueue.offer(runnable)) {
                throw new RejectedExecutionException();
            }
            Runnable runnablePollOrGiveUp = pollOrGiveUp(jIncrementCounterAndTimestamp);
            if (runnablePollOrGiveUp != null) {
                wrapAndExecute(runnablePollOrGiveUp, jIncrementCounterAndTimestamp);
            }
        }
    }

    private long incrementCounterAndTimestamp() {
        return this.myStatus.updateAndGet(new LongUnaryOperator() { // from class: org.jetbrains.kotlin.com.intellij.util.concurrency.BoundedTaskExecutor$$ExternalSyntheticLambda2
            @Override // java.util.function.LongUnaryOperator
            public final long applyAsLong(long j) {
                return BoundedTaskExecutor.lambda$incrementCounterAndTimestamp$0(j);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Runnable pollOrGiveUp(long j) {
        Runnable runnablePoll;
        while (true) {
            if (((int) j) <= this.myMaxThreads && (runnablePoll = this.myTaskQueue.poll()) != null) {
                return runnablePoll;
            }
            if (this.myStatus.compareAndSet(j, j - 1)) {
                return null;
            }
            j = this.myStatus.get();
        }
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.com.intellij.util.concurrency.BoundedTaskExecutor$1, reason: invalid class name */
    class AnonymousClass1 implements Runnable {
        final /* synthetic */ AtomicReference val$currentTask;
        final /* synthetic */ long val$status;

        AnonymousClass1(AtomicReference atomicReference, long j) {
            this.val$currentTask = atomicReference;
            this.val$status = j;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (BoundedTaskExecutor.this.myChangeThreadName) {
                String str = BoundedTaskExecutor.this.myName;
                if (StartUpMeasurer.isEnabled()) {
                    str = str + "[" + Thread.currentThread().getName() + "]";
                }
                ConcurrencyUtil.runUnderThreadName(str, new Runnable() { // from class: org.jetbrains.kotlin.com.intellij.util.concurrency.BoundedTaskExecutor$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.execute();
                    }
                });
                return;
            }
            execute();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void execute() {
            Runnable runnablePollOrGiveUp = (Runnable) this.val$currentTask.get();
            do {
                this.val$currentTask.set(runnablePollOrGiveUp);
                BoundedTaskExecutor.doRun(runnablePollOrGiveUp);
                runnablePollOrGiveUp = BoundedTaskExecutor.this.pollOrGiveUp(this.val$status);
            } while (runnablePollOrGiveUp != null);
        }

        public String toString() {
            return String.valueOf(BoundedTaskExecutor.info((Runnable) this.val$currentTask.get()));
        }
    }

    private void wrapAndExecute(Runnable runnable, long j) {
        if (runnable == null) {
            $$$reportNull$$$0(9);
        }
        try {
            this.myBackendExecutor.execute(new AnonymousClass1(new AtomicReference(runnable), j));
        } catch (Error | RuntimeException e) {
            this.myStatus.decrementAndGet();
            throw e;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void doRun(Runnable runnable) {
        try {
            runnable.run();
        } catch (Throwable th) {
            if (th instanceof ControlFlowException) {
                return;
            }
            try {
                Logger.getInstance((Class<?>) BoundedTaskExecutor.class).error(th);
            } catch (Throwable unused) {
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:10:0x0038 A[Catch: all -> 0x0071, TRY_ENTER, TryCatch #1 {, blocks: (B:6:0x0007, B:7:0x000a, B:10:0x0038, B:11:0x003f, B:13:0x0045, B:24:0x006d, B:25:0x0070, B:8:0x0032, B:17:0x0051, B:18:0x0063, B:22:0x0067, B:23:0x006c), top: B:31:0x0007, inners: #2 }] */
    /* JADX WARN: Code duplicated, block: B:13:0x0045 A[Catch: all -> 0x0071, LOOP:0: B:11:0x003f->B:13:0x0045, LOOP_END, TRY_LEAVE, TryCatch #1 {, blocks: (B:6:0x0007, B:7:0x000a, B:10:0x0038, B:11:0x003f, B:13:0x0045, B:24:0x006d, B:25:0x0070, B:8:0x0032, B:17:0x0051, B:18:0x0063, B:22:0x0067, B:23:0x006c), top: B:31:0x0007, inners: #2 }] */
    /* JADX WARN: Code duplicated, block: B:17:0x0051 A[Catch: all -> 0x0064, InterruptedException -> 0x0066, TRY_ENTER, TryCatch #0 {InterruptedException -> 0x0066, blocks: (B:8:0x0032, B:17:0x0051, B:18:0x0063), top: B:29:0x0032, outer: #2 }] */
    public synchronized void waitAllTasksExecuted(final long j, final TimeUnit timeUnit) throws ExecutionException, InterruptedException, TimeoutException {
        final CountDownLatch countDownLatch;
        final CountDownLatch countDownLatch2;
        List map;
        Iterator it;
        if (timeUnit == null) {
            $$$reportNull$$$0(10);
            countDownLatch = new CountDownLatch(this.myMaxThreads);
            countDownLatch2 = new CountDownLatch(1);
            final Runnable runnable = new Runnable() { // from class: org.jetbrains.kotlin.com.intellij.util.concurrency.BoundedTaskExecutor.2
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        countDownLatch.countDown();
                        countDownLatch2.await();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

                public String toString() {
                    return "LastTask to waitAllTasksExecuted for " + j + " " + timeUnit + " (" + System.identityHashCode(this) + Constant.AFTER_QUTO;
                }
            };
            map = ContainerUtil.map((Collection) Collections.nCopies(this.myMaxThreads, null), new Function() { // from class: org.jetbrains.kotlin.com.intellij.util.concurrency.BoundedTaskExecutor$$ExternalSyntheticLambda0
                @Override // org.jetbrains.kotlin.com.intellij.util.Function
                public final Object fun(Object obj) {
                    return this.f$0.m6705x67325a37(runnable, obj);
                }
            });
            try {
                try {
                    if (countDownLatch.await(j, timeUnit)) {
                        throw new TimeoutException("Interrupted by timeout. " + this);
                    }
                    countDownLatch2.countDown();
                    it = map.iterator();
                    while (it.hasNext()) {
                        ((Future) it.next()).get(j, timeUnit);
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } catch (Throwable th) {
                countDownLatch2.countDown();
                throw th;
            }
        } else {
            countDownLatch = new CountDownLatch(this.myMaxThreads);
            countDownLatch2 = new CountDownLatch(1);
            final Runnable runnable2 = new Runnable() { // from class: org.jetbrains.kotlin.com.intellij.util.concurrency.BoundedTaskExecutor.2
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        countDownLatch.countDown();
                        countDownLatch2.await();
                    } catch (InterruptedException e2) {
                        throw new RuntimeException(e2);
                    }
                }

                public String toString() {
                    return "LastTask to waitAllTasksExecuted for " + j + " " + timeUnit + " (" + System.identityHashCode(this) + Constant.AFTER_QUTO;
                }
            };
            map = ContainerUtil.map((Collection) Collections.nCopies(this.myMaxThreads, null), new Function() { // from class: org.jetbrains.kotlin.com.intellij.util.concurrency.BoundedTaskExecutor$$ExternalSyntheticLambda0
                @Override // org.jetbrains.kotlin.com.intellij.util.Function
                public final Object fun(Object obj) {
                    return this.f$0.m6705x67325a37(runnable2, obj);
                }
            });
            if (countDownLatch.await(j, timeUnit)) {
                throw new TimeoutException("Interrupted by timeout. " + this);
            }
            countDownLatch2.countDown();
            it = map.iterator();
            while (it.hasNext()) {
                ((Future) it.next()).get(j, timeUnit);
            }
        }
        throw th;
    }

    /* JADX INFO: renamed from: lambda$waitAllTasksExecuted$1$org-jetbrains-kotlin-com-intellij-util-concurrency-BoundedTaskExecutor, reason: not valid java name */
    /* synthetic */ Future m6705x67325a37(Runnable runnable, Object obj) {
        LastTask lastTask = new LastTask(runnable);
        execute(lastTask);
        return lastTask;
    }

    public boolean isEmpty() {
        return ((int) this.myStatus.get()) == 0;
    }

    public List<Runnable> clearAndCancelAll() {
        ArrayList<Runnable> arrayList = new ArrayList();
        this.myTaskQueue.drainTo(arrayList);
        for (Runnable runnable : arrayList) {
            if (runnable instanceof FutureTask) {
                ((FutureTask) runnable).cancel(false);
            }
        }
        return arrayList;
    }

    public String toString() {
        return "BoundedExecutor(" + this.myMaxThreads + Constant.AFTER_QUTO + (isShutdown() ? " SHUTDOWN " : "") + "; inProgress: " + ((int) this.myStatus.get()) + (this.myTaskQueue.isEmpty() ? "" : "; queue: " + this.myTaskQueue.size() + "[" + ContainerUtil.map((Collection) this.myTaskQueue, new Function() { // from class: org.jetbrains.kotlin.com.intellij.util.concurrency.BoundedTaskExecutor$$ExternalSyntheticLambda1
            @Override // org.jetbrains.kotlin.com.intellij.util.Function
            public final Object fun(Object obj) {
                return BoundedTaskExecutor.info((Runnable) obj);
            }
        }) + "]") + "; name: " + this.myName;
    }
}
