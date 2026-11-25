package com.opencsv.bean.concurrent;

import com.opencsv.bean.BeanVerifier;
import com.opencsv.bean.CsvToBeanFilter;
import com.opencsv.bean.MappingStrategy;
import java.util.List;
import java.util.stream.Stream;

/* JADX INFO: loaded from: classes10.dex */
public class LineExecutor<T> extends IntolerantThreadPoolExecutor<T> {
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
    public /* bridge */ /* synthetic */ Stream resultStream() {
        return super.resultStream();
    }

    @Override // com.opencsv.bean.concurrent.IntolerantThreadPoolExecutor, java.util.concurrent.ThreadPoolExecutor, java.util.concurrent.ExecutorService
    public /* bridge */ /* synthetic */ List shutdownNow() {
        return super.shutdownNow();
    }

    public LineExecutor(boolean z) {
        super(z);
    }

    public void submitLine(long j, MappingStrategy<? extends T> mappingStrategy, CsvToBeanFilter csvToBeanFilter, List<BeanVerifier<T>> list, String[] strArr, boolean z) throws Exception {
        try {
            execute(new ProcessCsvLine(j, mappingStrategy, csvToBeanFilter, list, strArr, this.resultQueue, this.thrownExceptionsQueue, z));
        } catch (Exception e) {
            if (this.accumulateThread != null) {
                this.accumulateThread.setMustStop(true);
            }
            throw e;
        }
    }
}
