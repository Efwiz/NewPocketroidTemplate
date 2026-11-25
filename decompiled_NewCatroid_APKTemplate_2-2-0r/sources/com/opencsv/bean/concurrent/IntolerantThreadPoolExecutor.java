package com.opencsv.bean.concurrent;

import com.opencsv.exceptions.CsvException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/* JADX INFO: loaded from: classes10.dex */
class IntolerantThreadPoolExecutor<T> extends ThreadPoolExecutor {
    protected AccumulateCsvResults<T> accumulateThread;
    private final boolean orderedResults;
    protected final BlockingQueue<OrderedObject<T>> resultQueue;
    private ConcurrentNavigableMap<Long, T> resultantBeansMap;
    private Throwable terminalException;
    private ConcurrentNavigableMap<Long, CsvException> thrownExceptionsMap;
    protected final BlockingQueue<OrderedObject<CsvException>> thrownExceptionsQueue;

    IntolerantThreadPoolExecutor(boolean z) {
        super(Runtime.getRuntime().availableProcessors(), Runtime.getRuntime().availableProcessors(), Long.MAX_VALUE, TimeUnit.NANOSECONDS, new LinkedBlockingQueue());
        this.resultQueue = new LinkedBlockingQueue();
        this.thrownExceptionsQueue = new LinkedBlockingQueue();
        this.resultantBeansMap = null;
        this.thrownExceptionsMap = null;
        this.accumulateThread = null;
        this.orderedResults = z;
    }

    public void prepare() {
        prestartAllCoreThreads();
        if (this.orderedResults) {
            this.resultantBeansMap = new ConcurrentSkipListMap();
            this.thrownExceptionsMap = new ConcurrentSkipListMap();
            AccumulateCsvResults<T> accumulateCsvResults = new AccumulateCsvResults<>(this.resultQueue, this.thrownExceptionsQueue, this.resultantBeansMap, this.thrownExceptionsMap);
            this.accumulateThread = accumulateCsvResults;
            accumulateCsvResults.start();
        }
    }

    public void complete() throws InterruptedException {
        super.shutdown();
        awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        AccumulateCsvResults<T> accumulateCsvResults = this.accumulateThread;
        if (accumulateCsvResults != null) {
            accumulateCsvResults.setMustStop(true);
            this.accumulateThread.join();
        }
        if (this.terminalException != null) {
            throw new RejectedExecutionException();
        }
    }

    public Stream<T> resultStream() {
        ConcurrentNavigableMap<Long, T> concurrentNavigableMap = this.resultantBeansMap;
        if (concurrentNavigableMap != null) {
            return concurrentNavigableMap.values().stream();
        }
        return (Stream<T>) this.resultQueue.stream().filter(new IntolerantThreadPoolExecutor$$ExternalSyntheticLambda0()).map(new Function() { // from class: com.opencsv.bean.concurrent.IntolerantThreadPoolExecutor$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((OrderedObject) obj).getElement();
            }
        });
    }

    public List<CsvException> getCapturedExceptions() {
        if (this.thrownExceptionsMap == null) {
            return (List) this.thrownExceptionsQueue.stream().filter(new IntolerantThreadPoolExecutor$$ExternalSyntheticLambda0()).map(new Function() { // from class: com.opencsv.bean.concurrent.IntolerantThreadPoolExecutor$$ExternalSyntheticLambda1
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return (CsvException) ((OrderedObject) obj).getElement();
                }
            }).collect(Collectors.toList());
        }
        return new ArrayList(this.thrownExceptionsMap.values());
    }

    @Override // java.util.concurrent.ThreadPoolExecutor, java.util.concurrent.ExecutorService
    public List<Runnable> shutdownNow() {
        AccumulateCsvResults<T> accumulateCsvResults = this.accumulateThread;
        if (accumulateCsvResults != null) {
            accumulateCsvResults.setMustStop(true);
        }
        return super.shutdownNow();
    }

    @Override // java.util.concurrent.ThreadPoolExecutor
    protected void afterExecute(Runnable runnable, Throwable th) {
        super.afterExecute(runnable, th);
        if (th != null) {
            if (th.getCause() != null) {
                this.terminalException = th.getCause();
            } else {
                this.terminalException = th;
            }
            shutdownNow();
        }
    }

    public Throwable getTerminalException() {
        return this.terminalException;
    }
}
