package org.jetbrains.kotlin.com.intellij.util.concurrency;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.BiConsumer;
import org.jetbrains.kotlin.com.intellij.diagnostic.LoadingState;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.progress.JobFutureTask;
import org.jetbrains.kotlin.com.intellij.openapi.progress.JobRunnable;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProcessCanceledException;
import org.jetbrains.kotlin.com.intellij.openapi.util.LowMemoryWatcherManager;
import org.jetbrains.kotlin.com.intellij.openapi.util.registry.Registry;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;

/* JADX INFO: loaded from: classes3.dex */
public final class AppScheduledExecutorService extends SchedulingWrapper {
    private final MyThreadFactory myCountingThreadFactory;
    private final LowMemoryWatcherManager myLowMemoryWatcherManager;
    private final String myName;

    /* JADX WARN: Code duplicated, block: B:22:0x0032  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 2 || i == 3 || i == 8) ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[(i == 1 || i == 2 || i == 3 || i == 8) ? 3 : 2];
        if (i == 1) {
            objArr[0] = "name";
        } else if (i == 2) {
            objArr[0] = "unit";
        } else if (i == 3) {
            objArr[0] = "threadListener";
        } else if (i != 8) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/util/concurrency/AppScheduledExecutorService";
        } else {
            objArr[0] = "unit";
        }
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 8:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/util/concurrency/AppScheduledExecutorService";
                break;
            case 4:
                objArr[1] = "shutdownNow";
                break;
            case 5:
                objArr[1] = "doShutdownNow";
                break;
            case 6:
                objArr[1] = "statistics";
                break;
            case 7:
                objArr[1] = "getPeriodicTasksThread";
                break;
            default:
                objArr[1] = "getInstance";
                break;
        }
        if (i == 1 || i == 2) {
            objArr[2] = "<init>";
        } else if (i == 3) {
            objArr[2] = "setNewThreadListener";
        } else if (i == 8) {
            objArr[2] = "waitForLowMemoryWatcherManagerInit";
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 2 && i != 3 && i != 8) {
            throw new IllegalStateException(str2);
        }
        throw new IllegalArgumentException(str2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.concurrency.SchedulingWrapper, java.util.concurrent.ExecutorService
    public /* bridge */ /* synthetic */ boolean awaitTermination(long j, TimeUnit timeUnit) throws InterruptedException {
        return super.awaitTermination(j, timeUnit);
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.concurrency.SchedulingWrapper, java.util.concurrent.Executor
    public /* bridge */ /* synthetic */ void execute(Runnable runnable) {
        super.execute(runnable);
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.concurrency.SchedulingWrapper, java.util.concurrent.ExecutorService
    public /* bridge */ /* synthetic */ List invokeAll(Collection collection) throws InterruptedException {
        return super.invokeAll(collection);
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.concurrency.SchedulingWrapper, java.util.concurrent.ExecutorService
    public /* bridge */ /* synthetic */ List invokeAll(Collection collection, long j, TimeUnit timeUnit) throws InterruptedException {
        return super.invokeAll(collection, j, timeUnit);
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.concurrency.SchedulingWrapper, java.util.concurrent.ExecutorService
    public /* bridge */ /* synthetic */ Object invokeAny(Collection collection) throws ExecutionException, InterruptedException {
        return super.invokeAny(collection);
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.concurrency.SchedulingWrapper, java.util.concurrent.ExecutorService
    public /* bridge */ /* synthetic */ Object invokeAny(Collection collection, long j, TimeUnit timeUnit) throws ExecutionException, InterruptedException, TimeoutException {
        return super.invokeAny(collection, j, timeUnit);
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.concurrency.SchedulingWrapper, java.util.concurrent.ExecutorService
    public /* bridge */ /* synthetic */ boolean isShutdown() {
        return super.isShutdown();
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.concurrency.SchedulingWrapper, java.util.concurrent.ExecutorService
    public /* bridge */ /* synthetic */ boolean isTerminated() {
        return super.isTerminated();
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.concurrency.SchedulingWrapper, java.util.concurrent.ScheduledExecutorService
    public /* bridge */ /* synthetic */ ScheduledFuture schedule(Runnable runnable, long j, TimeUnit timeUnit) {
        return super.schedule(runnable, j, timeUnit);
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.concurrency.SchedulingWrapper, java.util.concurrent.ScheduledExecutorService
    public /* bridge */ /* synthetic */ ScheduledFuture schedule(Callable callable, long j, TimeUnit timeUnit) {
        return super.schedule(callable, j, timeUnit);
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.concurrency.SchedulingWrapper, java.util.concurrent.ScheduledExecutorService
    public /* bridge */ /* synthetic */ ScheduledFuture scheduleAtFixedRate(Runnable runnable, long j, long j2, TimeUnit timeUnit) {
        return super.scheduleAtFixedRate(runnable, j, j2, timeUnit);
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.concurrency.SchedulingWrapper, java.util.concurrent.ScheduledExecutorService
    public /* bridge */ /* synthetic */ ScheduledFuture scheduleWithFixedDelay(Runnable runnable, long j, long j2, TimeUnit timeUnit) {
        return super.scheduleWithFixedDelay(runnable, j, j2, timeUnit);
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.concurrency.SchedulingWrapper, java.util.concurrent.ExecutorService
    public /* bridge */ /* synthetic */ Future submit(Runnable runnable) {
        return super.submit(runnable);
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.concurrency.SchedulingWrapper, java.util.concurrent.ExecutorService
    public /* bridge */ /* synthetic */ Future submit(Runnable runnable, Object obj) {
        return super.submit(runnable, obj);
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.concurrency.SchedulingWrapper, java.util.concurrent.ExecutorService
    public /* bridge */ /* synthetic */ Future submit(Callable callable) {
        return super.submit(callable);
    }

    private static final class Holder {
        private static final AppScheduledExecutorService INSTANCE = new AppScheduledExecutorService("Global instance", 1, TimeUnit.MINUTES);
    }

    static ScheduledExecutorService getInstance() {
        AppScheduledExecutorService appScheduledExecutorService = Holder.INSTANCE;
        if (appScheduledExecutorService == null) {
            $$$reportNull$$$0(0);
        }
        return appScheduledExecutorService;
    }

    private static final class MyThreadFactory extends CountingThreadFactory {
        private final ThreadFactory myThreadFactory;
        private BiConsumer<? super Thread, ? super Runnable> newThreadListener;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = i != 1 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
            Object[] objArr = new Object[i != 1 ? 3 : 2];
            if (i == 1) {
                objArr[0] = "org/jetbrains/kotlin/com/intellij/util/concurrency/AppScheduledExecutorService$MyThreadFactory";
            } else if (i != 2) {
                objArr[0] = "r";
            } else {
                objArr[0] = "threadListener";
            }
            if (i != 1) {
                objArr[1] = "org/jetbrains/kotlin/com/intellij/util/concurrency/AppScheduledExecutorService$MyThreadFactory";
            } else {
                objArr[1] = "newThread";
            }
            if (i != 1) {
                if (i != 2) {
                    objArr[2] = "newThread";
                } else {
                    objArr[2] = "setNewThreadListener";
                }
            }
            String str2 = String.format(str, objArr);
            if (i == 1) {
                throw new IllegalStateException(str2);
            }
        }

        private MyThreadFactory() {
            this.myThreadFactory = Executors.privilegedThreadFactory();
        }

        @Override // java.util.concurrent.ThreadFactory
        public Thread newThread(Runnable runnable) {
            if (runnable == null) {
                $$$reportNull$$$0(0);
            }
            Thread threadNewThread = this.myThreadFactory.newThread(runnable);
            threadNewThread.setName("ApplicationImpl pooled thread " + this.counter.incrementAndGet());
            threadNewThread.setPriority(4);
            BiConsumer<? super Thread, ? super Runnable> biConsumer = this.newThreadListener;
            if (biConsumer != null) {
                biConsumer.accept(threadNewThread, runnable);
            }
            if (threadNewThread == null) {
                $$$reportNull$$$0(1);
            }
            return threadNewThread;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    AppScheduledExecutorService(String str, long j, TimeUnit timeUnit) {
        super(new BackendThreadPoolExecutor(new MyThreadFactory(), j, timeUnit), new AppDelayQueue());
        if (str == null) {
            $$$reportNull$$$0(1);
        }
        if (timeUnit == null) {
            $$$reportNull$$$0(2);
        }
        this.myName = str;
        this.myCountingThreadFactory = (MyThreadFactory) ((BackendThreadPoolExecutor) this.backendExecutorService).getThreadFactory();
        this.myLowMemoryWatcherManager = new LowMemoryWatcherManager(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.concurrency.SchedulingWrapper, java.util.concurrent.ExecutorService
    public List<Runnable> shutdownNow() {
        List<Runnable> listError = error();
        if (listError == null) {
            $$$reportNull$$$0(4);
        }
        return listError;
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.concurrency.SchedulingWrapper, java.util.concurrent.ExecutorService
    public void shutdown() {
        error();
    }

    static List<Runnable> error() {
        throw new IncorrectOperationException("You must not call this method on the global app pool");
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.concurrency.SchedulingWrapper
    void doShutdown() {
        super.doShutdown();
        ((BackendThreadPoolExecutor) this.backendExecutorService).superShutdown();
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.concurrency.SchedulingWrapper
    List<Runnable> doShutdownNow() {
        List<Runnable> listConcat = ContainerUtil.concat((List) super.doShutdownNow(), ((BackendThreadPoolExecutor) this.backendExecutorService).superShutdownNow());
        if (listConcat == null) {
            $$$reportNull$$$0(5);
        }
        return listConcat;
    }

    static class BackendThreadPoolExecutor extends ThreadPoolExecutor {
        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = (i == 3 || i == 4) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
            Object[] objArr = new Object[(i == 3 || i == 4) ? 2 : 3];
            if (i == 1) {
                objArr[0] = "unit";
            } else if (i == 2) {
                objArr[0] = "command";
            } else if (i == 3 || i == 4) {
                objArr[0] = "org/jetbrains/kotlin/com/intellij/util/concurrency/AppScheduledExecutorService$BackendThreadPoolExecutor";
            } else {
                objArr[0] = "factory";
            }
            if (i == 3) {
                objArr[1] = "superShutdownNow";
            } else if (i != 4) {
                objArr[1] = "org/jetbrains/kotlin/com/intellij/util/concurrency/AppScheduledExecutorService$BackendThreadPoolExecutor";
            } else {
                objArr[1] = "shutdownNow";
            }
            if (i == 2) {
                objArr[2] = "execute";
            } else if (i != 3 && i != 4) {
                objArr[2] = "<init>";
            }
            String str2 = String.format(str, objArr);
            if (i != 3 && i != 4) {
                throw new IllegalArgumentException(str2);
            }
            throw new IllegalStateException(str2);
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        BackendThreadPoolExecutor(ThreadFactory threadFactory, long j, TimeUnit timeUnit) {
            super(1, Integer.MAX_VALUE, j, timeUnit, new SynchronousQueue(), threadFactory);
            if (threadFactory == null) {
                $$$reportNull$$$0(0);
            }
            if (timeUnit == null) {
                $$$reportNull$$$0(1);
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

        @Override // java.util.concurrent.ThreadPoolExecutor, java.util.concurrent.Executor
        public void execute(Runnable runnable) {
            if (runnable == null) {
                $$$reportNull$$$0(2);
            }
            if (LoadingState.APP_STARTED.isOccurred() && Registry.is("ide.cancellation.propagate")) {
                super.execute(JobRunnable.jobRunnable(runnable));
            } else {
                super.execute(runnable);
            }
        }

        @Override // java.util.concurrent.ThreadPoolExecutor
        protected void afterExecute(Runnable runnable, Throwable th) {
            if (th == null || (th instanceof ProcessCanceledException)) {
                return;
            }
            Logger.getInstance((Class<?>) SchedulingWrapper.class).error("Worker exited due to exception", th);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void superShutdown() {
            super.shutdown();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public List<Runnable> superShutdownNow() {
            List<Runnable> listShutdownNow = super.shutdownNow();
            if (listShutdownNow == null) {
                $$$reportNull$$$0(3);
            }
            return listShutdownNow;
        }

        @Override // java.util.concurrent.ThreadPoolExecutor, java.util.concurrent.ExecutorService
        public void shutdown() {
            AppScheduledExecutorService.error();
        }

        @Override // java.util.concurrent.ThreadPoolExecutor, java.util.concurrent.ExecutorService
        public List<Runnable> shutdownNow() {
            List<Runnable> listError = AppScheduledExecutorService.error();
            if (listError == null) {
                $$$reportNull$$$0(4);
            }
            return listError;
        }

        @Override // java.util.concurrent.ThreadPoolExecutor
        public void setCorePoolSize(int i) {
            AppScheduledExecutorService.error();
        }

        @Override // java.util.concurrent.ThreadPoolExecutor
        public void allowCoreThreadTimeOut(boolean z) {
            AppScheduledExecutorService.error();
        }

        @Override // java.util.concurrent.ThreadPoolExecutor
        public void setMaximumPoolSize(int i) {
            AppScheduledExecutorService.error();
        }

        @Override // java.util.concurrent.ThreadPoolExecutor
        public void setKeepAliveTime(long j, TimeUnit timeUnit) {
            AppScheduledExecutorService.error();
        }

        @Override // java.util.concurrent.ThreadPoolExecutor
        public void setThreadFactory(ThreadFactory threadFactory) {
            AppScheduledExecutorService.error();
        }
    }
}
