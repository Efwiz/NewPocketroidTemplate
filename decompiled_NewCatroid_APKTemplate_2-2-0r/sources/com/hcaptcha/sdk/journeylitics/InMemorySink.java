package com.hcaptcha.sdk.journeylitics;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/* JADX INFO: compiled from: r8-map-id-071b77f92f575d77028d4852bce4d6e5f9d65cb60a14a6342bd357f80a49e8cf */
/* JADX INFO: loaded from: classes10.dex */
public class InMemorySink implements JLSink {
    private static final int MAX_EVENTS = 50;
    private final List<JLEvent> events = new ArrayList();
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public void clearEvents() {
        this.lock.writeLock().lock();
        try {
            this.events.clear();
        } finally {
            this.lock.writeLock().unlock();
        }
    }

    @Override // com.hcaptcha.sdk.journeylitics.JLSink
    public void emit(JLEvent jLEvent) {
        this.lock.writeLock().lock();
        try {
            this.events.add(jLEvent);
            if (this.events.size() > 50) {
                this.events.remove(0);
            }
        } finally {
            this.lock.writeLock().unlock();
        }
    }

    public List<JLEvent> getAndClearEvents() {
        this.lock.writeLock().lock();
        try {
            ArrayList arrayList = new ArrayList(this.events);
            this.events.clear();
            return arrayList;
        } finally {
            this.lock.writeLock().unlock();
        }
    }

    public List<JLEvent> getEvents() {
        this.lock.writeLock().lock();
        try {
            return new ArrayList(this.events);
        } finally {
            this.lock.writeLock().unlock();
        }
    }
}
