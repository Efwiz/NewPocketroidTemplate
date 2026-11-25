package org.jetbrains.kotlin.com.intellij.openapi.application.impl;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.com.intellij.codeWithMe.ClientId;
import org.jetbrains.kotlin.com.intellij.diagnostic.EventWatcher;
import org.jetbrains.kotlin.com.intellij.openapi.application.AccessToken;
import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.kotlin.com.intellij.openapi.application.ModalityState;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProcessCanceledException;
import org.jetbrains.kotlin.com.intellij.openapi.util.ActionCallback;
import org.jetbrains.kotlin.com.intellij.openapi.util.Condition;
import org.jetbrains.kotlin.com.intellij.util.ExceptionUtil;

/* JADX INFO: loaded from: classes3.dex */
final class FlushQueue {
    private static final boolean DEBUG;
    private static final Logger LOG;
    private final Object LOCK;
    private RunnableInfo myLastInfo;
    private volatile boolean myMayHaveItems;
    private final ArrayDeque<RunnableInfo> myQueue;
    private final Consumer<? super Runnable> myRunnableExecutor;
    private final List<RunnableInfo> mySkippedItems;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 2 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 2 ? 3 : 2];
        if (i == 1) {
            objArr[0] = "runnableInfo";
        } else if (i == 2) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/application/impl/FlushQueue";
        } else if (i != 3) {
            objArr[0] = "executor";
        } else {
            objArr[0] = CompilerOptions.INFO;
        }
        if (i != 2) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/application/impl/FlushQueue";
        } else {
            objArr[1] = "getQueue";
        }
        if (i == 1) {
            objArr[2] = "push";
        } else if (i != 2) {
            if (i != 3) {
                objArr[2] = "<init>";
            } else {
                objArr[2] = "doRun";
            }
        }
        String str2 = String.format(str, objArr);
        if (i == 2) {
            throw new IllegalStateException(str2);
        }
    }

    static {
        Logger logger = Logger.getInstance((Class<?>) LaterInvocator.class);
        LOG = logger;
        DEBUG = logger.isDebugEnabled();
    }

    FlushQueue(Consumer<? super Runnable> consumer) {
        if (consumer == null) {
            $$$reportNull$$$0(0);
        }
        this.LOCK = new Object();
        this.mySkippedItems = new ArrayList();
        this.myQueue = new ArrayDeque<>();
        this.myRunnableExecutor = consumer;
    }

    public void scheduleFlush() {
        this.myRunnableExecutor.accept(new FlushNow());
    }

    public void flushNow() {
        LaterInvocator.FLUSHER_SCHEDULED.set(false);
        this.myMayHaveItems = false;
        long jCurrentTimeMillis = System.currentTimeMillis();
        while (runNextEvent()) {
            if (System.currentTimeMillis() - jCurrentTimeMillis > 5) {
                this.myMayHaveItems = true;
                break;
            }
        }
        LaterInvocator.requestFlush();
    }

    boolean mayHaveItems() {
        return this.myMayHaveItems;
    }

    private static void doRun(RunnableInfo runnableInfo) {
        if (runnableInfo == null) {
            $$$reportNull$$$0(3);
        }
        AccessToken accessTokenWithClientId = ClientId.withClientId(runnableInfo.clientId);
        try {
            runnableInfo.runnable.run();
            if (accessTokenWithClientId != null) {
                accessTokenWithClientId.close();
            }
        } catch (Throwable th) {
            if (accessTokenWithClientId != null) {
                try {
                    accessTokenWithClientId.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public String toString() {
        return "LaterInvocator.FlushQueue" + (this.myLastInfo == null ? "" : " lastInfo=" + this.myLastInfo);
    }

    RunnableInfo getNextEvent(boolean z) {
        synchronized (this.LOCK) {
            ModalityStateEx currentModalityState = LaterInvocator.getCurrentModalityState();
            while (!this.myQueue.isEmpty()) {
                RunnableInfo first = this.myQueue.getFirst();
                if (first.expired.value(null)) {
                    this.myQueue.removeFirst();
                    first.markDone();
                } else {
                    if (!currentModalityState.dominates(first.modalityState)) {
                        if (z) {
                            this.myQueue.removeFirst();
                        }
                        return first;
                    }
                    this.mySkippedItems.add(this.myQueue.removeFirst());
                }
            }
            return null;
        }
    }

    private boolean runNextEvent() {
        long jCurrentTimeMillis = System.currentTimeMillis();
        RunnableInfo nextEvent = getNextEvent(true);
        this.myLastInfo = nextEvent;
        if (nextEvent != null) {
            EventWatcher instanceOrNull = EventWatcher.getInstanceOrNull();
            Runnable runnable = nextEvent.runnable;
            if (instanceOrNull != null) {
                instanceOrNull.runnableStarted(runnable, jCurrentTimeMillis);
            }
            try {
                doRun(nextEvent);
                nextEvent.markDone();
                if (!DEBUG) {
                    this.myLastInfo = null;
                }
                if (instanceOrNull != null) {
                    instanceOrNull.runnableFinished(runnable, System.currentTimeMillis());
                }
            } catch (ProcessCanceledException unused) {
                if (!DEBUG) {
                    this.myLastInfo = null;
                }
                if (instanceOrNull != null) {
                }
            } catch (Throwable th) {
                try {
                    if (ApplicationManager.getApplication().isUnitTestMode()) {
                        ExceptionUtil.rethrow(th);
                    }
                    LOG.error(th);
                    if (!DEBUG) {
                        this.myLastInfo = null;
                    }
                    if (instanceOrNull != null) {
                    }
                } catch (Throwable th2) {
                    if (!DEBUG) {
                        this.myLastInfo = null;
                    }
                    if (instanceOrNull != null) {
                        instanceOrNull.runnableFinished(runnable, System.currentTimeMillis());
                    }
                    throw th2;
                }
            }
        }
        return nextEvent != null;
    }

    void reincludeSkippedItems() {
        synchronized (this.LOCK) {
            for (int size = this.mySkippedItems.size() - 1; size >= 0; size--) {
                this.myQueue.addFirst(this.mySkippedItems.get(size));
                this.myMayHaveItems = true;
            }
            this.mySkippedItems.clear();
        }
    }

    final class FlushNow implements Runnable {
        FlushNow() {
        }

        @Override // java.lang.Runnable
        public void run() {
            FlushQueue.this.flushNow();
        }
    }

    static final class RunnableInfo {
        private final ActionCallback callback;
        private final ClientId clientId;
        private final Condition<?> expired;
        private final ModalityState modalityState;
        private final Runnable runnable;

        void markDone() {
            ActionCallback actionCallback = this.callback;
            if (actionCallback != null) {
                actionCallback.setDone();
            }
        }

        public String toString() {
            return "[runnable: " + this.runnable + "; state=" + this.modalityState + (this.expired.value(null) ? "; expired" : "") + "] ";
        }
    }
}
