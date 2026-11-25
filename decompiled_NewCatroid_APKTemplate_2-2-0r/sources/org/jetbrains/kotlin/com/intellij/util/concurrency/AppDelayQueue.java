package org.jetbrains.kotlin.com.intellij.util.concurrency;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;

/* JADX INFO: loaded from: classes3.dex */
final class AppDelayQueue extends DelayQueue<SchedulingWrapper.MyScheduledFutureTask<?>> {
    private static final Logger LOG = Logger.getInstance((Class<?>) AppDelayQueue.class);
    private final Thread scheduledToPooledTransferrer;
    private final AtomicBoolean shutdown = new AtomicBoolean();

    AppDelayQueue() {
        Thread thread = new Thread(new Runnable() { // from class: org.jetbrains.kotlin.com.intellij.util.concurrency.AppDelayQueue$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m6704xdf24333d();
            }
        }, "Periodic tasks thread");
        this.scheduledToPooledTransferrer = thread;
        thread.setDaemon(true);
        thread.start();
    }

    /* JADX INFO: renamed from: lambda$new$0$org-jetbrains-kotlin-com-intellij-util-concurrency-AppDelayQueue, reason: not valid java name */
    /* synthetic */ void m6704xdf24333d() {
        while (!this.shutdown.get()) {
            try {
                SchedulingWrapper.MyScheduledFutureTask<?> myScheduledFutureTaskTake = take();
                Logger logger = LOG;
                if (logger.isTraceEnabled()) {
                    logger.trace("Took " + BoundedTaskExecutor.info(myScheduledFutureTaskTake));
                }
                if (!myScheduledFutureTaskTake.isDone()) {
                    try {
                        myScheduledFutureTaskTake.executeMeInBackendExecutor();
                    } catch (Throwable th) {
                        LOG.error("Error executing " + myScheduledFutureTaskTake, th);
                    }
                }
            } catch (InterruptedException e) {
                if (!this.shutdown.get()) {
                    LOG.error((Throwable) e);
                }
            }
        }
        LOG.debug("scheduledToPooledTransferrer Stopped");
    }
}
