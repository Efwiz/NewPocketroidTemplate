package io.netty.util.concurrent;

import androidx.concurrent.futures.AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import io.netty.util.internal.InternalThreadLocalMap;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.StringUtil;
import io.netty.util.internal.SystemPropertyUtil;
import io.netty.util.internal.ThrowableUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import org.apache.commons.compress.harmony.pack200.PackingOptions;

/* JADX INFO: loaded from: classes11.dex */
public class DefaultPromise<V> extends AbstractFuture<V> implements Promise<V> {
    private static final CauseHolder CANCELLATION_CAUSE_HOLDER;
    private static final StackTraceElement[] CANCELLATION_STACK;
    private final EventExecutor executor;
    private Object listeners;
    private boolean notifyingListeners;
    private volatile Object result;
    private short waiters;
    private static final InternalLogger logger = InternalLoggerFactory.getInstance((Class<?>) DefaultPromise.class);
    private static final InternalLogger rejectedExecutionLogger = InternalLoggerFactory.getInstance(DefaultPromise.class.getName() + ".rejectedExecution");
    private static final int MAX_LISTENER_STACK_DEPTH = Math.min(8, SystemPropertyUtil.getInt("io.netty.defaultPromise.maxListenerStackDepth", 8));
    private static final AtomicReferenceFieldUpdater<DefaultPromise, Object> RESULT_UPDATER = AtomicReferenceFieldUpdater.newUpdater(DefaultPromise.class, Object.class, "result");
    private static final Object SUCCESS = new Object();
    private static final Object UNCANCELLABLE = new Object();

    static {
        CauseHolder causeHolder = new CauseHolder(ThrowableUtil.unknownStackTrace(new CancellationException(), DefaultPromise.class, "cancel(...)"));
        CANCELLATION_CAUSE_HOLDER = causeHolder;
        CANCELLATION_STACK = causeHolder.cause.getStackTrace();
    }

    public DefaultPromise(EventExecutor eventExecutor) {
        this.executor = (EventExecutor) ObjectUtil.checkNotNull(eventExecutor, "executor");
    }

    protected DefaultPromise() {
        this.executor = null;
    }

    public Promise<V> setSuccess(V v) {
        if (setSuccess0(v)) {
            return this;
        }
        throw new IllegalStateException("complete already: " + this);
    }

    public boolean trySuccess(V v) {
        return setSuccess0(v);
    }

    public Promise<V> setFailure(Throwable th) {
        if (setFailure0(th)) {
            return this;
        }
        throw new IllegalStateException("complete already: " + this, th);
    }

    public boolean tryFailure(Throwable th) {
        return setFailure0(th);
    }

    @Override // io.netty.util.concurrent.Promise
    public boolean setUncancellable() {
        if (AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0.m(RESULT_UPDATER, this, null, UNCANCELLABLE)) {
            return true;
        }
        Object obj = this.result;
        return (isDone0(obj) && isCancelled0(obj)) ? false : true;
    }

    @Override // io.netty.util.concurrent.Future
    public boolean isSuccess() {
        Object obj = this.result;
        return (obj == null || obj == UNCANCELLABLE || (obj instanceof CauseHolder)) ? false : true;
    }

    @Override // io.netty.util.concurrent.Future
    public boolean isCancellable() {
        return this.result == null;
    }

    private static final class LeanCancellationException extends CancellationException {
        private static final long serialVersionUID = 2794674970981187807L;

        private LeanCancellationException() {
        }

        @Override // java.lang.Throwable
        public Throwable fillInStackTrace() {
            setStackTrace(DefaultPromise.CANCELLATION_STACK);
            return this;
        }

        @Override // java.lang.Throwable
        public String toString() {
            return CancellationException.class.getName();
        }
    }

    @Override // io.netty.util.concurrent.Future
    public Throwable cause() {
        return cause0(this.result);
    }

    private Throwable cause0(Object obj) {
        if (!(obj instanceof CauseHolder)) {
            return null;
        }
        CauseHolder causeHolder = CANCELLATION_CAUSE_HOLDER;
        if (obj == causeHolder) {
            LeanCancellationException leanCancellationException = new LeanCancellationException();
            if (AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0.m(RESULT_UPDATER, this, causeHolder, new CauseHolder(leanCancellationException))) {
                return leanCancellationException;
            }
            obj = this.result;
        }
        return ((CauseHolder) obj).cause;
    }

    @Override // io.netty.util.concurrent.Future
    public Promise<V> addListener(GenericFutureListener<? extends Future<? super V>> genericFutureListener) {
        ObjectUtil.checkNotNull(genericFutureListener, ServiceSpecificExtraArgs.CastExtraArgs.LISTENER);
        synchronized (this) {
            addListener0(genericFutureListener);
        }
        if (isDone()) {
            notifyListeners();
        }
        return this;
    }

    @Override // io.netty.util.concurrent.Future
    public Promise<V> addListeners(GenericFutureListener<? extends Future<? super V>>... genericFutureListenerArr) {
        ObjectUtil.checkNotNull(genericFutureListenerArr, "listeners");
        synchronized (this) {
            for (GenericFutureListener<? extends Future<? super V>> genericFutureListener : genericFutureListenerArr) {
                if (genericFutureListener == null) {
                    break;
                }
                addListener0(genericFutureListener);
            }
        }
        if (isDone()) {
            notifyListeners();
        }
        return this;
    }

    @Override // io.netty.util.concurrent.Future
    public Promise<V> removeListener(GenericFutureListener<? extends Future<? super V>> genericFutureListener) {
        ObjectUtil.checkNotNull(genericFutureListener, ServiceSpecificExtraArgs.CastExtraArgs.LISTENER);
        synchronized (this) {
            removeListener0(genericFutureListener);
        }
        return this;
    }

    @Override // io.netty.util.concurrent.Future
    public Promise<V> removeListeners(GenericFutureListener<? extends Future<? super V>>... genericFutureListenerArr) {
        ObjectUtil.checkNotNull(genericFutureListenerArr, "listeners");
        synchronized (this) {
            for (GenericFutureListener<? extends Future<? super V>> genericFutureListener : genericFutureListenerArr) {
                if (genericFutureListener == null) {
                    break;
                }
                removeListener0(genericFutureListener);
            }
        }
        return this;
    }

    @Override // io.netty.util.concurrent.Future
    public Promise<V> await() throws InterruptedException {
        if (isDone()) {
            return this;
        }
        if (Thread.interrupted()) {
            throw new InterruptedException(toString());
        }
        checkDeadLock();
        synchronized (this) {
            while (!isDone()) {
                incWaiters();
                try {
                    wait();
                    decWaiters();
                } catch (Throwable th) {
                    decWaiters();
                    throw th;
                }
            }
        }
        return this;
    }

    @Override // io.netty.util.concurrent.Future
    public Promise<V> awaitUninterruptibly() {
        boolean z;
        if (isDone()) {
            return this;
        }
        checkDeadLock();
        synchronized (this) {
            z = false;
            while (!isDone()) {
                try {
                    incWaiters();
                    try {
                        try {
                            wait();
                            decWaiters();
                        } catch (InterruptedException unused) {
                            decWaiters();
                            z = true;
                        }
                    } catch (Throwable th) {
                        decWaiters();
                        throw th;
                    }
                } catch (Throwable th2) {
                    throw th2;
                }
            }
        }
        if (z) {
            Thread.currentThread().interrupt();
        }
        return this;
    }

    @Override // io.netty.util.concurrent.Future
    public boolean await(long j, TimeUnit timeUnit) throws InterruptedException {
        return await0(timeUnit.toNanos(j), true);
    }

    @Override // io.netty.util.concurrent.Future
    public boolean await(long j) throws InterruptedException {
        return await0(TimeUnit.MILLISECONDS.toNanos(j), true);
    }

    @Override // io.netty.util.concurrent.Future
    public boolean awaitUninterruptibly(long j, TimeUnit timeUnit) {
        try {
            return await0(timeUnit.toNanos(j), false);
        } catch (InterruptedException unused) {
            throw new InternalError();
        }
    }

    @Override // io.netty.util.concurrent.Future
    public boolean awaitUninterruptibly(long j) {
        try {
            return await0(TimeUnit.MILLISECONDS.toNanos(j), false);
        } catch (InterruptedException unused) {
            throw new InternalError();
        }
    }

    @Override // io.netty.util.concurrent.Future
    public V getNow() {
        V v = (V) this.result;
        if ((v instanceof CauseHolder) || v == SUCCESS || v == UNCANCELLABLE) {
            return null;
        }
        return v;
    }

    @Override // io.netty.util.concurrent.AbstractFuture, java.util.concurrent.Future
    public V get() throws ExecutionException, InterruptedException {
        V v = (V) this.result;
        if (!isDone0(v)) {
            await();
            v = (V) this.result;
        }
        if (v == SUCCESS || v == UNCANCELLABLE) {
            return null;
        }
        Throwable thCause0 = cause0(v);
        if (thCause0 == null) {
            return v;
        }
        if (thCause0 instanceof CancellationException) {
            throw ((CancellationException) thCause0);
        }
        throw new ExecutionException(thCause0);
    }

    @Override // io.netty.util.concurrent.AbstractFuture, java.util.concurrent.Future
    public V get(long j, TimeUnit timeUnit) throws ExecutionException, InterruptedException, TimeoutException {
        V v = (V) this.result;
        if (!isDone0(v)) {
            if (!await(j, timeUnit)) {
                throw new TimeoutException();
            }
            v = (V) this.result;
        }
        if (v == SUCCESS || v == UNCANCELLABLE) {
            return null;
        }
        Throwable thCause0 = cause0(v);
        if (thCause0 == null) {
            return v;
        }
        if (thCause0 instanceof CancellationException) {
            throw ((CancellationException) thCause0);
        }
        throw new ExecutionException(thCause0);
    }

    @Override // io.netty.util.concurrent.Future, java.util.concurrent.Future
    public boolean cancel(boolean z) {
        if (!AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0.m(RESULT_UPDATER, this, null, CANCELLATION_CAUSE_HOLDER)) {
            return false;
        }
        if (!checkNotifyWaiters()) {
            return true;
        }
        notifyListeners();
        return true;
    }

    @Override // java.util.concurrent.Future
    public boolean isCancelled() {
        return isCancelled0(this.result);
    }

    @Override // java.util.concurrent.Future
    public boolean isDone() {
        return isDone0(this.result);
    }

    @Override // io.netty.util.concurrent.Future
    public Promise<V> sync() throws Throwable {
        await();
        rethrowIfFailed();
        return this;
    }

    @Override // io.netty.util.concurrent.Future
    public Promise<V> syncUninterruptibly() throws Throwable {
        awaitUninterruptibly();
        rethrowIfFailed();
        return this;
    }

    public String toString() {
        return toStringBuilder().toString();
    }

    protected StringBuilder toStringBuilder() {
        StringBuilder sbAppend = new StringBuilder(64).append(StringUtil.simpleClassName(this)).append('@').append(Integer.toHexString(hashCode()));
        Object obj = this.result;
        if (obj == SUCCESS) {
            sbAppend.append("(success)");
        } else if (obj == UNCANCELLABLE) {
            sbAppend.append("(uncancellable)");
        } else if (obj instanceof CauseHolder) {
            sbAppend.append("(failure: ").append(((CauseHolder) obj).cause).append(')');
        } else if (obj != null) {
            sbAppend.append("(success: ").append(obj).append(')');
        } else {
            sbAppend.append("(incomplete)");
        }
        return sbAppend;
    }

    protected EventExecutor executor() {
        return this.executor;
    }

    protected void checkDeadLock() {
        EventExecutor eventExecutorExecutor = executor();
        if (eventExecutorExecutor != null && eventExecutorExecutor.inEventLoop()) {
            throw new BlockingOperationException(toString());
        }
    }

    protected static void notifyListener(EventExecutor eventExecutor, Future<?> future, GenericFutureListener<?> genericFutureListener) {
        notifyListenerWithStackOverFlowProtection((EventExecutor) ObjectUtil.checkNotNull(eventExecutor, "eventExecutor"), (Future) ObjectUtil.checkNotNull(future, "future"), (GenericFutureListener) ObjectUtil.checkNotNull(genericFutureListener, ServiceSpecificExtraArgs.CastExtraArgs.LISTENER));
    }

    private void notifyListeners() {
        InternalThreadLocalMap internalThreadLocalMap;
        int iFutureListenerStackDepth;
        EventExecutor eventExecutorExecutor = executor();
        if (eventExecutorExecutor.inEventLoop() && (iFutureListenerStackDepth = (internalThreadLocalMap = InternalThreadLocalMap.get()).futureListenerStackDepth()) < MAX_LISTENER_STACK_DEPTH) {
            internalThreadLocalMap.setFutureListenerStackDepth(iFutureListenerStackDepth + 1);
            try {
                notifyListenersNow();
                return;
            } finally {
                internalThreadLocalMap.setFutureListenerStackDepth(iFutureListenerStackDepth);
            }
        }
        safeExecute(eventExecutorExecutor, new Runnable() { // from class: io.netty.util.concurrent.DefaultPromise.1
            @Override // java.lang.Runnable
            public void run() {
                DefaultPromise.this.notifyListenersNow();
            }
        });
    }

    private static void notifyListenerWithStackOverFlowProtection(EventExecutor eventExecutor, final Future<?> future, final GenericFutureListener<?> genericFutureListener) {
        InternalThreadLocalMap internalThreadLocalMap;
        int iFutureListenerStackDepth;
        if (eventExecutor.inEventLoop() && (iFutureListenerStackDepth = (internalThreadLocalMap = InternalThreadLocalMap.get()).futureListenerStackDepth()) < MAX_LISTENER_STACK_DEPTH) {
            internalThreadLocalMap.setFutureListenerStackDepth(iFutureListenerStackDepth + 1);
            try {
                notifyListener0(future, genericFutureListener);
                return;
            } finally {
                internalThreadLocalMap.setFutureListenerStackDepth(iFutureListenerStackDepth);
            }
        }
        safeExecute(eventExecutor, new Runnable() { // from class: io.netty.util.concurrent.DefaultPromise.2
            @Override // java.lang.Runnable
            public void run() {
                DefaultPromise.notifyListener0(future, genericFutureListener);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyListenersNow() {
        Object obj;
        synchronized (this) {
            if (!this.notifyingListeners && (obj = this.listeners) != null) {
                this.notifyingListeners = true;
                this.listeners = null;
                while (true) {
                    if (obj instanceof DefaultFutureListeners) {
                        notifyListeners0((DefaultFutureListeners) obj);
                    } else {
                        notifyListener0(this, (GenericFutureListener) obj);
                    }
                    synchronized (this) {
                        obj = this.listeners;
                        if (obj == null) {
                            this.notifyingListeners = false;
                            return;
                        }
                        this.listeners = null;
                    }
                }
            }
        }
    }

    private void notifyListeners0(DefaultFutureListeners defaultFutureListeners) {
        GenericFutureListener<? extends Future<?>>[] genericFutureListenerArrListeners = defaultFutureListeners.listeners();
        int size = defaultFutureListeners.size();
        for (int i = 0; i < size; i++) {
            notifyListener0(this, genericFutureListenerArrListeners[i]);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void notifyListener0(Future future, GenericFutureListener genericFutureListener) {
        try {
            genericFutureListener.operationComplete(future);
        } catch (Throwable th) {
            if (logger.isWarnEnabled()) {
                logger.warn("An exception was thrown by " + genericFutureListener.getClass().getName() + ".operationComplete()", th);
            }
        }
    }

    private void addListener0(GenericFutureListener<? extends Future<? super V>> genericFutureListener) {
        Object obj = this.listeners;
        if (obj == null) {
            this.listeners = genericFutureListener;
        } else if (obj instanceof DefaultFutureListeners) {
            ((DefaultFutureListeners) obj).add(genericFutureListener);
        } else {
            this.listeners = new DefaultFutureListeners((GenericFutureListener) this.listeners, genericFutureListener);
        }
    }

    private void removeListener0(GenericFutureListener<? extends Future<? super V>> genericFutureListener) {
        Object obj = this.listeners;
        if (obj instanceof DefaultFutureListeners) {
            ((DefaultFutureListeners) obj).remove(genericFutureListener);
        } else if (obj == genericFutureListener) {
            this.listeners = null;
        }
    }

    private boolean setSuccess0(V v) {
        if (v == null) {
            v = (V) SUCCESS;
        }
        return setValue0(v);
    }

    private boolean setFailure0(Throwable th) {
        return setValue0(new CauseHolder((Throwable) ObjectUtil.checkNotNull(th, "cause")));
    }

    private boolean setValue0(Object obj) {
        AtomicReferenceFieldUpdater<DefaultPromise, Object> atomicReferenceFieldUpdater = RESULT_UPDATER;
        if (!AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0.m(atomicReferenceFieldUpdater, this, null, obj) && !AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0.m(atomicReferenceFieldUpdater, this, UNCANCELLABLE, obj)) {
            return false;
        }
        if (!checkNotifyWaiters()) {
            return true;
        }
        notifyListeners();
        return true;
    }

    private synchronized boolean checkNotifyWaiters() {
        if (this.waiters > 0) {
            notifyAll();
        }
        return this.listeners != null;
    }

    private void incWaiters() {
        short s = this.waiters;
        if (s == Short.MAX_VALUE) {
            throw new IllegalStateException("too many waiters: " + this);
        }
        this.waiters = (short) (s + 1);
    }

    private void decWaiters() {
        this.waiters = (short) (this.waiters - 1);
    }

    private void rethrowIfFailed() throws Throwable {
        Throwable thCause = cause();
        if (thCause == null) {
            return;
        }
        PlatformDependent.throwException(thCause);
    }

    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:55:0x0091 -> B:51:0x008c). Please report as a decompilation issue!!! */
    private boolean await0(long j, boolean z) throws Throwable {
        boolean z2 = true;
        if (isDone()) {
            return true;
        }
        if (j <= 0) {
            return isDone();
        }
        if (z && Thread.interrupted()) {
            throw new InterruptedException(toString());
        }
        checkDeadLock();
        long jNanoTime = System.nanoTime();
        boolean z3 = false;
        long jNanoTime2 = j;
        do {
            try {
                synchronized (this) {
                    try {
                        if (isDone()) {
                            if (z3) {
                                Thread.currentThread().interrupt();
                            }
                            return true;
                        }
                        incWaiters();
                        try {
                            try {
                                wait(jNanoTime2 / PackingOptions.SEGMENT_LIMIT, (int) (jNanoTime2 % PackingOptions.SEGMENT_LIMIT));
                                decWaiters();
                            } catch (InterruptedException e) {
                                if (z) {
                                    throw e;
                                }
                                try {
                                    decWaiters();
                                    z3 = true;
                                } catch (Throwable th) {
                                }
                            }
                            if (isDone()) {
                                if (z3) {
                                    Thread.currentThread().interrupt();
                                }
                                return true;
                            }
                            jNanoTime2 = j - (System.nanoTime() - jNanoTime);
                        } catch (Throwable th2) {
                            decWaiters();
                            throw th2;
                        }
                        if (z3) {
                            Thread.currentThread().interrupt();
                        }
                        throw th;
                    } catch (Throwable th3) {
                        th = th3;
                        z2 = z3;
                        throw th;
                    }
                    th = th;
                    try {
                        throw th;
                    } catch (Throwable th4) {
                        th = th4;
                        z3 = z2;
                    }
                }
            } catch (Throwable th5) {
                th = th5;
            }
        } while (jNanoTime2 > 0);
        boolean zIsDone = isDone();
        if (z3) {
            Thread.currentThread().interrupt();
        }
        return zIsDone;
    }

    /* JADX WARN: Multi-variable type inference failed */
    void notifyProgressiveListeners(final long j, final long j2) {
        Object objProgressiveListeners = progressiveListeners();
        if (objProgressiveListeners == null) {
            return;
        }
        final ProgressiveFuture progressiveFuture = (ProgressiveFuture) this;
        EventExecutor eventExecutorExecutor = executor();
        if (eventExecutorExecutor.inEventLoop()) {
            if (objProgressiveListeners instanceof GenericProgressiveFutureListener[]) {
                notifyProgressiveListeners0(progressiveFuture, (GenericProgressiveFutureListener[]) objProgressiveListeners, j, j2);
                return;
            } else {
                notifyProgressiveListener0(progressiveFuture, (GenericProgressiveFutureListener) objProgressiveListeners, j, j2);
                return;
            }
        }
        if (objProgressiveListeners instanceof GenericProgressiveFutureListener[]) {
            final GenericProgressiveFutureListener[] genericProgressiveFutureListenerArr = (GenericProgressiveFutureListener[]) objProgressiveListeners;
            safeExecute(eventExecutorExecutor, new Runnable() { // from class: io.netty.util.concurrent.DefaultPromise.3
                @Override // java.lang.Runnable
                public void run() {
                    DefaultPromise.notifyProgressiveListeners0(progressiveFuture, genericProgressiveFutureListenerArr, j, j2);
                }
            });
        } else {
            final GenericProgressiveFutureListener genericProgressiveFutureListener = (GenericProgressiveFutureListener) objProgressiveListeners;
            safeExecute(eventExecutorExecutor, new Runnable() { // from class: io.netty.util.concurrent.DefaultPromise.4
                @Override // java.lang.Runnable
                public void run() {
                    DefaultPromise.notifyProgressiveListener0(progressiveFuture, genericProgressiveFutureListener, j, j2);
                }
            });
        }
    }

    private synchronized Object progressiveListeners() {
        Object obj = this.listeners;
        if (obj == null) {
            return null;
        }
        if (obj instanceof DefaultFutureListeners) {
            DefaultFutureListeners defaultFutureListeners = (DefaultFutureListeners) obj;
            int iProgressiveSize = defaultFutureListeners.progressiveSize();
            if (iProgressiveSize == 0) {
                return null;
            }
            int i = 0;
            if (iProgressiveSize == 1) {
                GenericFutureListener<? extends Future<?>>[] genericFutureListenerArrListeners = defaultFutureListeners.listeners();
                int length = genericFutureListenerArrListeners.length;
                while (i < length) {
                    GenericFutureListener<? extends Future<?>> genericFutureListener = genericFutureListenerArrListeners[i];
                    if (genericFutureListener instanceof GenericProgressiveFutureListener) {
                        return genericFutureListener;
                    }
                    i++;
                }
                return null;
            }
            GenericFutureListener<? extends Future<?>>[] genericFutureListenerArrListeners2 = defaultFutureListeners.listeners();
            GenericProgressiveFutureListener[] genericProgressiveFutureListenerArr = new GenericProgressiveFutureListener[iProgressiveSize];
            int i2 = 0;
            while (i < iProgressiveSize) {
                GenericFutureListener<? extends Future<?>> genericFutureListener2 = genericFutureListenerArrListeners2[i2];
                if (genericFutureListener2 instanceof GenericProgressiveFutureListener) {
                    int i3 = i + 1;
                    genericProgressiveFutureListenerArr[i] = (GenericProgressiveFutureListener) genericFutureListener2;
                    i = i3;
                }
                i2++;
            }
            return genericProgressiveFutureListenerArr;
        }
        if (obj instanceof GenericProgressiveFutureListener) {
            return obj;
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void notifyProgressiveListeners0(ProgressiveFuture<?> progressiveFuture, GenericProgressiveFutureListener<?>[] genericProgressiveFutureListenerArr, long j, long j2) {
        for (GenericProgressiveFutureListener<?> genericProgressiveFutureListener : genericProgressiveFutureListenerArr) {
            if (genericProgressiveFutureListener == null) {
                return;
            }
            notifyProgressiveListener0(progressiveFuture, genericProgressiveFutureListener, j, j2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void notifyProgressiveListener0(ProgressiveFuture progressiveFuture, GenericProgressiveFutureListener genericProgressiveFutureListener, long j, long j2) {
        try {
            genericProgressiveFutureListener.operationProgressed(progressiveFuture, j, j2);
        } catch (Throwable th) {
            if (logger.isWarnEnabled()) {
                logger.warn("An exception was thrown by " + genericProgressiveFutureListener.getClass().getName() + ".operationProgressed()", th);
            }
        }
    }

    private static boolean isCancelled0(Object obj) {
        return (obj instanceof CauseHolder) && (((CauseHolder) obj).cause instanceof CancellationException);
    }

    private static boolean isDone0(Object obj) {
        return (obj == null || obj == UNCANCELLABLE) ? false : true;
    }

    private static final class CauseHolder {
        final Throwable cause;

        CauseHolder(Throwable th) {
            this.cause = th;
        }
    }

    private static void safeExecute(EventExecutor eventExecutor, Runnable runnable) {
        try {
            eventExecutor.execute(runnable);
        } catch (Throwable th) {
            rejectedExecutionLogger.error("Failed to submit a listener notification task. Event loop shut down?", th);
        }
    }
}
