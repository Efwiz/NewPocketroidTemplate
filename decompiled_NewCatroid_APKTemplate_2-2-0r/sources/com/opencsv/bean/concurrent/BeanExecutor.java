package com.opencsv.bean.concurrent;

import com.opencsv.bean.MappingStrategy;
import java.util.List;
import java.util.stream.Stream;

/* JADX INFO: loaded from: classes10.dex */
public class BeanExecutor<T> extends IntolerantThreadPoolExecutor<String[]> {
    @Override // com.opencsv.bean.concurrent.IntolerantThreadPoolExecutor
    public /* bridge */ /* synthetic */ void complete() throws InterruptedException {
        super.complete();
    }

    @Override // com.opencsv.bean.concurrent.IntolerantThreadPoolExecutor
    public /* bridge */ /* synthetic */ List getCapturedExceptions() {
        return super.getCapturedExceptions();
    }

    @Override // com.opencsv.bean.concurrent.IntolerantThreadPoolExecutor
    public /* bridge */ /* synthetic */ Throwable getTerminalException() {
        return super.getTerminalException();
    }

    @Override // com.opencsv.bean.concurrent.IntolerantThreadPoolExecutor
    public /* bridge */ /* synthetic */ void prepare() {
        super.prepare();
    }

    @Override // com.opencsv.bean.concurrent.IntolerantThreadPoolExecutor
    public /* bridge */ /* synthetic */ Stream<String[]> resultStream() {
        return super.resultStream();
    }

    @Override // com.opencsv.bean.concurrent.IntolerantThreadPoolExecutor, java.util.concurrent.ThreadPoolExecutor, java.util.concurrent.ExecutorService
    public /* bridge */ /* synthetic */ List shutdownNow() {
        return super.shutdownNow();
    }

    public BeanExecutor(boolean z) {
        super(z);
    }

    public void submitBean(long j, MappingStrategy<T> mappingStrategy, T t, boolean z) throws Exception {
        try {
            execute(new ProcessCsvBean(j, mappingStrategy, t, this.resultQueue, this.thrownExceptionsQueue, z));
        } catch (Exception e) {
            if (this.accumulateThread != null) {
                this.accumulateThread.setMustStop(true);
            }
            throw e;
        }
    }
}
