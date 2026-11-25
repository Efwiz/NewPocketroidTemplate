package org.jetbrains.kotlin.com.intellij.openapi.util;

import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
class ExecutionCallback {
    private final int myCountToExecution;
    private int myCurrentCount;
    private List<Runnable> myRunnables;

    ExecutionCallback() {
        this(1);
    }

    ExecutionCallback(int i) {
        this.myCountToExecution = i;
    }

    boolean setExecuted() {
        if (!signalExecution()) {
            return false;
        }
        synchronized (this) {
            List<Runnable> list = this.myRunnables;
            if (list == null) {
                return true;
            }
            this.myRunnables = null;
            Iterator<Runnable> it = list.iterator();
            while (it.hasNext()) {
                it.next().run();
            }
            return true;
        }
    }

    synchronized void clear() {
        this.myRunnables = null;
    }

    private synchronized boolean signalExecution() {
        int i;
        i = this.myCurrentCount + 1;
        this.myCurrentCount = i;
        return i >= this.myCountToExecution;
    }

    public synchronized String toString() {
        return "current=" + this.myCurrentCount + " countToExecution=" + this.myCountToExecution;
    }
}
