package org.catrobat.catroid.ui;

import android.util.Log;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/ViewSwitchLock.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class ViewSwitchLock implements Lock {
    private static final String TAG = "ViewSwitchLock";
    private static final long UNLOCK_TIMEOUT = 200;
    private boolean locked = false;

    @Override // java.util.concurrent.locks.Lock
    public synchronized boolean tryLock() {
        if (this.locked) {
            return false;
        }
        this.locked = true;
        new Thread(new Runnable() { // from class: org.catrobat.catroid.ui.ViewSwitchLock.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    Thread.sleep(ViewSwitchLock.UNLOCK_TIMEOUT);
                } catch (InterruptedException e) {
                    Log.e(ViewSwitchLock.TAG, Log.getStackTraceString(e));
                }
                ViewSwitchLock.this.unlock();
            }
        }).start();
        return true;
    }

    @Override // java.util.concurrent.locks.Lock
    public synchronized void unlock() {
        this.locked = false;
    }

    @Override // java.util.concurrent.locks.Lock
    public void lock() {
        throw new UnsupportedOperationException("Unsupported Method");
    }

    @Override // java.util.concurrent.locks.Lock
    public void lockInterruptibly() throws InterruptedException {
        throw new UnsupportedOperationException("Unsupported Method");
    }

    @Override // java.util.concurrent.locks.Lock
    public Condition newCondition() {
        throw new UnsupportedOperationException("Unsupported Method");
    }

    @Override // java.util.concurrent.locks.Lock
    public boolean tryLock(long arg0, TimeUnit arg1) throws InterruptedException {
        throw new UnsupportedOperationException("Unsupported Method");
    }
}
