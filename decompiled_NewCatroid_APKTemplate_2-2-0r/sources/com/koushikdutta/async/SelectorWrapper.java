package com.koushikdutta.async;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Set;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes10.dex */
public class SelectorWrapper {
    boolean isWaking;
    private Selector selector;
    Semaphore semaphore = new Semaphore(0);

    public Selector getSelector() {
        return this.selector;
    }

    public SelectorWrapper(Selector selector) {
        this.selector = selector;
    }

    public int selectNow() throws IOException {
        return this.selector.selectNow();
    }

    public void select() throws IOException {
        select(0L);
    }

    public void select(long j) throws IOException {
        try {
            this.semaphore.drainPermits();
            this.selector.select(j);
        } finally {
            this.semaphore.release(Integer.MAX_VALUE);
        }
    }

    public Set<SelectionKey> keys() {
        return this.selector.keys();
    }

    public Set<SelectionKey> selectedKeys() {
        return this.selector.selectedKeys();
    }

    public void close() throws IOException {
        this.selector.close();
    }

    public boolean isOpen() {
        return this.selector.isOpen();
    }

    public void wakeupOnce() {
        boolean zTryAcquire = this.semaphore.tryAcquire();
        this.selector.wakeup();
        if (zTryAcquire) {
            synchronized (this) {
                if (this.isWaking) {
                    return;
                }
                this.isWaking = true;
                for (int i = 0; i < 100; i++) {
                    try {
                        try {
                            if (this.semaphore.tryAcquire(10L, TimeUnit.MILLISECONDS)) {
                                synchronized (this) {
                                    this.isWaking = false;
                                }
                                return;
                            }
                        } catch (InterruptedException unused) {
                        }
                        this.selector.wakeup();
                    } catch (Throwable th) {
                        synchronized (this) {
                            this.isWaking = false;
                            throw th;
                        }
                    }
                }
                synchronized (this) {
                    this.isWaking = false;
                }
            }
        }
    }
}
