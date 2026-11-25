package com.koushikdutta.async.future;

import com.koushikdutta.async.AsyncSemaphore;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* JADX INFO: loaded from: classes10.dex */
public class SimpleFuture<T> extends SimpleCancellable implements DependentFuture<T> {
    FutureCallback<T> callback;
    Exception exception;
    T result;
    boolean silent;
    AsyncSemaphore waiter;

    public SimpleFuture() {
    }

    public SimpleFuture(T t) {
        setComplete(t);
    }

    public SimpleFuture(Exception exc) {
        setComplete(exc);
    }

    @Override // java.util.concurrent.Future
    public boolean cancel(boolean z) {
        return cancel();
    }

    private boolean cancelInternal(boolean z) {
        FutureCallback<T> futureCallbackHandleCompleteLocked;
        if (!super.cancel()) {
            return false;
        }
        synchronized (this) {
            this.exception = new CancellationException();
            releaseWaiterLocked();
            futureCallbackHandleCompleteLocked = handleCompleteLocked();
            this.silent = z;
        }
        handleCallbackUnlocked(futureCallbackHandleCompleteLocked);
        return true;
    }

    public boolean cancelSilently() {
        return cancelInternal(true);
    }

    @Override // com.koushikdutta.async.future.SimpleCancellable, com.koushikdutta.async.future.Cancellable
    public boolean cancel() {
        return cancelInternal(this.silent);
    }

    @Override // java.util.concurrent.Future
    public T get() throws ExecutionException, InterruptedException {
        synchronized (this) {
            if (!isCancelled() && !isDone()) {
                ensureWaiterLocked().acquire();
                return getResultOrThrow();
            }
            return getResultOrThrow();
        }
    }

    private T getResultOrThrow() throws ExecutionException {
        if (this.exception != null) {
            throw new ExecutionException(this.exception);
        }
        return this.result;
    }

    @Override // java.util.concurrent.Future
    public T get(long j, TimeUnit timeUnit) throws ExecutionException, InterruptedException, TimeoutException {
        synchronized (this) {
            if (!isCancelled() && !isDone()) {
                AsyncSemaphore asyncSemaphoreEnsureWaiterLocked = ensureWaiterLocked();
                if (!asyncSemaphoreEnsureWaiterLocked.tryAcquire(j, timeUnit)) {
                    throw new TimeoutException();
                }
                return getResultOrThrow();
            }
            return getResultOrThrow();
        }
    }

    @Override // com.koushikdutta.async.future.SimpleCancellable
    public boolean setComplete() {
        return setComplete((Object) null);
    }

    private FutureCallback<T> handleCompleteLocked() {
        FutureCallback<T> futureCallback = this.callback;
        this.callback = null;
        return futureCallback;
    }

    private void handleCallbackUnlocked(FutureCallback<T> futureCallback) {
        if (futureCallback == null || this.silent) {
            return;
        }
        futureCallback.onCompleted(this.exception, this.result);
    }

    void releaseWaiterLocked() {
        AsyncSemaphore asyncSemaphore = this.waiter;
        if (asyncSemaphore != null) {
            asyncSemaphore.release();
            this.waiter = null;
        }
    }

    AsyncSemaphore ensureWaiterLocked() {
        if (this.waiter == null) {
            this.waiter = new AsyncSemaphore();
        }
        return this.waiter;
    }

    public boolean setComplete(Exception exc) {
        return setComplete(exc, null);
    }

    public boolean setComplete(T t) {
        return setComplete(null, t);
    }

    public boolean setComplete(Exception exc, T t) {
        synchronized (this) {
            if (!super.setComplete()) {
                return false;
            }
            this.result = t;
            this.exception = exc;
            releaseWaiterLocked();
            handleCallbackUnlocked(handleCompleteLocked());
            return true;
        }
    }

    public FutureCallback<T> getCompletionCallback() {
        return new FutureCallback<T>() { // from class: com.koushikdutta.async.future.SimpleFuture.1
            @Override // com.koushikdutta.async.future.FutureCallback
            public void onCompleted(Exception exc, T t) {
                SimpleFuture.this.setComplete(exc, t);
            }
        };
    }

    public SimpleFuture<T> setComplete(Future<T> future) {
        future.setCallback(getCompletionCallback());
        setParent((Cancellable) future);
        return this;
    }

    public FutureCallback<T> getCallback() {
        return this.callback;
    }

    @Override // com.koushikdutta.async.future.Future
    public SimpleFuture<T> setCallback(FutureCallback<T> futureCallback) {
        FutureCallback<T> futureCallbackHandleCompleteLocked;
        synchronized (this) {
            this.callback = futureCallback;
            futureCallbackHandleCompleteLocked = (isDone() || isCancelled()) ? handleCompleteLocked() : null;
        }
        handleCallbackUnlocked(futureCallbackHandleCompleteLocked);
        return this;
    }

    @Override // com.koushikdutta.async.future.Future
    public final <C extends FutureCallback<T>> C then(C c) {
        if (c instanceof DependentCancellable) {
            ((DependentCancellable) c).setParent(this);
        }
        setCallback((FutureCallback) c);
        return c;
    }

    @Override // com.koushikdutta.async.future.SimpleCancellable, com.koushikdutta.async.future.DependentCancellable
    public SimpleFuture<T> setParent(Cancellable cancellable) {
        super.setParent(cancellable);
        return this;
    }

    @Override // com.koushikdutta.async.future.SimpleCancellable
    public SimpleFuture<T> reset() {
        super.reset();
        this.result = null;
        this.exception = null;
        this.waiter = null;
        this.callback = null;
        this.silent = false;
        return this;
    }

    @Override // com.koushikdutta.async.future.Future
    public Exception tryGetException() {
        return this.exception;
    }

    @Override // com.koushikdutta.async.future.Future
    public T tryGet() {
        return this.result;
    }
}
