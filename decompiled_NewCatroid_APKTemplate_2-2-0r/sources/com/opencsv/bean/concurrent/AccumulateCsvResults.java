package com.opencsv.bean.concurrent;

import com.opencsv.exceptions.CsvException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentMap;

/* JADX INFO: loaded from: classes10.dex */
class AccumulateCsvResults<T> extends Thread {
    private boolean mustStop = false;
    private final ConcurrentMap<Long, T> resultantBeanMap;
    private final BlockingQueue<OrderedObject<T>> resultantBeansQueue;
    private final ConcurrentMap<Long, CsvException> thrownExceptionsMap;
    private final BlockingQueue<OrderedObject<CsvException>> thrownExceptionsQueue;

    AccumulateCsvResults(BlockingQueue<OrderedObject<T>> blockingQueue, BlockingQueue<OrderedObject<CsvException>> blockingQueue2, ConcurrentMap<Long, T> concurrentMap, ConcurrentMap<Long, CsvException> concurrentMap2) {
        this.resultantBeansQueue = blockingQueue;
        this.thrownExceptionsQueue = blockingQueue2;
        this.resultantBeanMap = concurrentMap;
        this.thrownExceptionsMap = concurrentMap2;
    }

    private synchronized boolean isMustStop() {
        return this.mustStop;
    }

    synchronized void setMustStop(boolean z) {
        this.mustStop = z;
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        while (true) {
            if (isMustStop() && this.resultantBeansQueue.isEmpty() && this.thrownExceptionsQueue.isEmpty()) {
                return;
            }
            while (!this.resultantBeansQueue.isEmpty()) {
                OrderedObject<T> orderedObjectPoll = this.resultantBeansQueue.poll();
                if (orderedObjectPoll != null) {
                    this.resultantBeanMap.put(Long.valueOf(orderedObjectPoll.getOrdinal()), orderedObjectPoll.getElement());
                }
            }
            while (!this.thrownExceptionsQueue.isEmpty()) {
                OrderedObject<CsvException> orderedObjectPoll2 = this.thrownExceptionsQueue.poll();
                if (orderedObjectPoll2 != null) {
                    this.thrownExceptionsMap.put(Long.valueOf(orderedObjectPoll2.getOrdinal()), orderedObjectPoll2.getElement());
                }
            }
            Thread.yield();
        }
    }
}
