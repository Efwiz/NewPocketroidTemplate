package io.netty.util.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.RunnableFuture;

/* JADX INFO: loaded from: classes11.dex */
class PromiseTask<V> extends DefaultPromise<V> implements RunnableFuture<V> {
    private Object task;
    private static final Runnable COMPLETED = new SentinelRunnable("COMPLETED");
    private static final Runnable CANCELLED = new SentinelRunnable("CANCELLED");
    private static final Runnable FAILED = new SentinelRunnable("FAILED");

    public final boolean equals(Object obj) {
        return this == obj;
    }

    @Override // io.netty.util.concurrent.DefaultPromise, io.netty.util.concurrent.Promise
    public final boolean tryFailure(Throwable th) {
        return false;
    }

    @Override // io.netty.util.concurrent.DefaultPromise, io.netty.util.concurrent.Promise
    public final boolean trySuccess(V v) {
        return false;
    }

    private static final class RunnableAdapter<T> implements Callable<T> {
        final T result;
        final Runnable task;

        RunnableAdapter(Runnable runnable, T t) {
            this.task = runnable;
            this.result = t;
        }

        @Override // java.util.concurrent.Callable
        public T call() {
            this.task.run();
            return this.result;
        }

        public String toString() {
            return "Callable(task: " + this.task + ", result: " + this.result + ')';
        }
    }

    private static class SentinelRunnable implements Runnable {

        /* JADX INFO: renamed from: name, reason: collision with root package name */
        private final String f286name;

        @Override // java.lang.Runnable
        public void run() {
        }

        SentinelRunnable(String str) {
            this.f286name = str;
        }

        public String toString() {
            return this.f286name;
        }
    }

    PromiseTask(EventExecutor eventExecutor, Runnable runnable, V v) {
        super(eventExecutor);
        this.task = v != null ? new RunnableAdapter(runnable, v) : runnable;
    }

    PromiseTask(EventExecutor eventExecutor, Runnable runnable) {
        super(eventExecutor);
        this.task = runnable;
    }

    PromiseTask(EventExecutor eventExecutor, Callable<V> callable) {
        super(eventExecutor);
        this.task = callable;
    }

    public final int hashCode() {
        return System.identityHashCode(this);
    }

    final V runTask() throws Exception {
        Object obj = this.task;
        if (obj instanceof Callable) {
            return (V) ((Callable) obj).call();
        }
        ((Runnable) obj).run();
        return null;
    }

    @Override // java.util.concurrent.RunnableFuture, java.lang.Runnable
    public void run() {
        try {
            if (setUncancellableInternal()) {
                setSuccessInternal(runTask());
            }
        } catch (Throwable th) {
            setFailureInternal(th);
        }
    }

    private boolean clearTaskAfterCompletion(boolean z, Runnable runnable) {
        if (z) {
            this.task = runnable;
        }
        return z;
    }

    @Override // io.netty.util.concurrent.DefaultPromise, io.netty.util.concurrent.Promise, io.netty.channel.ChannelPromise
    public final Promise<V> setFailure(Throwable th) {
        throw new IllegalStateException();
    }

    protected final Promise<V> setFailureInternal(Throwable th) {
        super.setFailure(th);
        clearTaskAfterCompletion(true, FAILED);
        return this;
    }

    protected final boolean tryFailureInternal(Throwable th) {
        return clearTaskAfterCompletion(super.tryFailure(th), FAILED);
    }

    @Override // io.netty.util.concurrent.DefaultPromise, io.netty.util.concurrent.Promise, io.netty.util.concurrent.ProgressivePromise
    public final Promise<V> setSuccess(V v) {
        throw new IllegalStateException();
    }

    protected final Promise<V> setSuccessInternal(V v) {
        super.setSuccess(v);
        clearTaskAfterCompletion(true, COMPLETED);
        return this;
    }

    protected final boolean trySuccessInternal(V v) {
        return clearTaskAfterCompletion(super.trySuccess(v), COMPLETED);
    }

    @Override // io.netty.util.concurrent.DefaultPromise, io.netty.util.concurrent.Promise
    public final boolean setUncancellable() {
        throw new IllegalStateException();
    }

    protected final boolean setUncancellableInternal() {
        return super.setUncancellable();
    }

    @Override // io.netty.util.concurrent.DefaultPromise, io.netty.util.concurrent.Future, java.util.concurrent.Future
    public boolean cancel(boolean z) {
        return clearTaskAfterCompletion(super.cancel(z), CANCELLED);
    }

    @Override // io.netty.util.concurrent.DefaultPromise
    protected StringBuilder toStringBuilder() {
        StringBuilder stringBuilder = super.toStringBuilder();
        stringBuilder.setCharAt(stringBuilder.length() - 1, ',');
        return stringBuilder.append(" task: ").append(this.task).append(')');
    }
}
