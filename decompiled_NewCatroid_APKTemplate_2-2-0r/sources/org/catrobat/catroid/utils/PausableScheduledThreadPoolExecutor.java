package org.catrobat.catroid.utils;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/PausableScheduledThreadPoolExecutor.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class PausableScheduledThreadPoolExecutor extends ScheduledThreadPoolExecutor {
    private boolean isPaused;
    private ReentrantLock pauseLock;
    private Condition unpaused;

    public PausableScheduledThreadPoolExecutor(int corePoolSize) {
        super(corePoolSize);
        ReentrantLock reentrantLock = new ReentrantLock();
        this.pauseLock = reentrantLock;
        this.unpaused = reentrantLock.newCondition();
    }

    @Override // java.util.concurrent.ThreadPoolExecutor
    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
        this.pauseLock.lock();
        while (this.isPaused) {
            try {
                try {
                    this.unpaused.await();
                } catch (InterruptedException unused) {
                    t.interrupt();
                }
            } catch (Throwable th) {
                this.pauseLock.unlock();
                throw th;
            }
        }
        this.pauseLock.unlock();
    }

    public void pause() {
        this.pauseLock.lock();
        try {
            this.isPaused = true;
        } finally {
            this.pauseLock.unlock();
        }
    }

    public void resume() {
        this.pauseLock.lock();
        try {
            this.isPaused = false;
            this.unpaused.signalAll();
        } finally {
            this.pauseLock.unlock();
        }
    }
}
