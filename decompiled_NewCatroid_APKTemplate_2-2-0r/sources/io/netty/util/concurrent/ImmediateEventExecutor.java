package io.netty.util.concurrent;

import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes11.dex */
public final class ImmediateEventExecutor extends AbstractEventExecutor {
    private final Future<?> terminationFuture = new FailedFuture(GlobalEventExecutor.INSTANCE, new UnsupportedOperationException());
    private static final InternalLogger logger = InternalLoggerFactory.getInstance((Class<?>) ImmediateEventExecutor.class);
    public static final ImmediateEventExecutor INSTANCE = new ImmediateEventExecutor();
    private static final FastThreadLocal<Queue<Runnable>> DELAYED_RUNNABLES = new FastThreadLocal<Queue<Runnable>>() { // from class: io.netty.util.concurrent.ImmediateEventExecutor.1
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // io.netty.util.concurrent.FastThreadLocal
        public Queue<Runnable> initialValue() throws Exception {
            return new ArrayDeque();
        }
    };
    private static final FastThreadLocal<Boolean> RUNNING = new FastThreadLocal<Boolean>() { // from class: io.netty.util.concurrent.ImmediateEventExecutor.2
        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // io.netty.util.concurrent.FastThreadLocal
        public Boolean initialValue() throws Exception {
            return false;
        }
    };

    @Override // java.util.concurrent.ExecutorService
    public boolean awaitTermination(long j, TimeUnit timeUnit) {
        return false;
    }

    @Override // io.netty.util.concurrent.AbstractEventExecutor, io.netty.util.concurrent.EventExecutor
    public boolean inEventLoop() {
        return true;
    }

    @Override // io.netty.util.concurrent.EventExecutor
    public boolean inEventLoop(Thread thread) {
        return true;
    }

    @Override // java.util.concurrent.ExecutorService
    public boolean isShutdown() {
        return false;
    }

    @Override // io.netty.util.concurrent.EventExecutorGroup
    public boolean isShuttingDown() {
        return false;
    }

    @Override // java.util.concurrent.ExecutorService
    public boolean isTerminated() {
        return false;
    }

    @Override // io.netty.util.concurrent.AbstractEventExecutor, java.util.concurrent.ExecutorService, io.netty.util.concurrent.EventExecutorGroup
    @Deprecated
    public void shutdown() {
    }

    private ImmediateEventExecutor() {
    }

    @Override // io.netty.util.concurrent.EventExecutorGroup
    public Future<?> shutdownGracefully(long j, long j2, TimeUnit timeUnit) {
        return terminationFuture();
    }

    @Override // io.netty.util.concurrent.EventExecutorGroup
    public Future<?> terminationFuture() {
        return this.terminationFuture;
    }

    @Override // java.util.concurrent.Executor
    public void execute(Runnable runnable) throws Throwable {
        ObjectUtil.checkNotNull(runnable, "command");
        FastThreadLocal<Boolean> fastThreadLocal = RUNNING;
        if (!fastThreadLocal.get().booleanValue()) {
            fastThreadLocal.set(true);
            try {
                runnable.run();
                Queue<Runnable> queue = DELAYED_RUNNABLES.get();
                while (true) {
                    Runnable runnablePoll = queue.poll();
                    if (runnablePoll == null) {
                        break;
                    }
                    try {
                        runnablePoll.run();
                    } catch (Throwable th) {
                        logger.info("Throwable caught while executing Runnable {}", runnablePoll, th);
                    }
                }
            } catch (Throwable th2) {
                try {
                    logger.info("Throwable caught while executing Runnable {}", runnable, th2);
                    while (true) {
                    }
                } finally {
                    Queue<Runnable> queue2 = DELAYED_RUNNABLES.get();
                    while (true) {
                        Runnable runnablePoll2 = queue2.poll();
                        if (runnablePoll2 != null) {
                            try {
                                runnablePoll2.run();
                            } catch (Throwable th3) {
                                logger.info("Throwable caught while executing Runnable {}", runnablePoll2, th3);
                            }
                        } else {
                            RUNNING.set(false);
                        }
                    }
                }
            }
            return;
        }
        DELAYED_RUNNABLES.get().add(runnable);
    }

    @Override // io.netty.util.concurrent.AbstractEventExecutor, io.netty.util.concurrent.EventExecutor
    public <V> Promise<V> newPromise() {
        return new ImmediatePromise(this);
    }

    @Override // io.netty.util.concurrent.AbstractEventExecutor, io.netty.util.concurrent.EventExecutor
    public <V> ProgressivePromise<V> newProgressivePromise() {
        return new ImmediateProgressivePromise(this);
    }

    static class ImmediatePromise<V> extends DefaultPromise<V> {
        @Override // io.netty.util.concurrent.DefaultPromise
        protected void checkDeadLock() {
        }

        ImmediatePromise(EventExecutor eventExecutor) {
            super(eventExecutor);
        }
    }

    static class ImmediateProgressivePromise<V> extends DefaultProgressivePromise<V> {
        @Override // io.netty.util.concurrent.DefaultPromise
        protected void checkDeadLock() {
        }

        ImmediateProgressivePromise(EventExecutor eventExecutor) {
            super(eventExecutor);
        }
    }
}
