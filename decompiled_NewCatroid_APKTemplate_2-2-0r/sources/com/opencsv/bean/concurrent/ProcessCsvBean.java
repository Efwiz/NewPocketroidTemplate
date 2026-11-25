package com.opencsv.bean.concurrent;

import com.opencsv.bean.MappingStrategy;
import com.opencsv.bean.OpencsvUtils;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvRuntimeException;
import java.util.concurrent.BlockingQueue;

/* JADX INFO: loaded from: classes10.dex */
public class ProcessCsvBean<T> implements Runnable {
    private final T bean;
    private final long lineNumber;
    private final MappingStrategy<T> mappingStrategy;
    private final BlockingQueue<OrderedObject<String[]>> resultantLineQueue;
    private final boolean throwExceptions;
    private final BlockingQueue<OrderedObject<CsvException>> thrownExceptionsQueue;

    public ProcessCsvBean(long j, MappingStrategy<T> mappingStrategy, T t, BlockingQueue<OrderedObject<String[]>> blockingQueue, BlockingQueue<OrderedObject<CsvException>> blockingQueue2, boolean z) {
        this.lineNumber = j;
        this.mappingStrategy = mappingStrategy;
        this.bean = t;
        this.resultantLineQueue = blockingQueue;
        this.thrownExceptionsQueue = blockingQueue2;
        this.throwExceptions = z;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            OpencsvUtils.queueRefuseToAcceptDefeat(this.resultantLineQueue, new OrderedObject(this.lineNumber, this.mappingStrategy.transmuteBean(this.bean)));
        } catch (CsvException e) {
            e.setLineNumber(this.lineNumber);
            if (this.throwExceptions) {
                throw new RuntimeException(e);
            }
            OpencsvUtils.queueRefuseToAcceptDefeat(this.thrownExceptionsQueue, new OrderedObject(this.lineNumber, e));
        } catch (CsvRuntimeException e2) {
            throw e2;
        } catch (Exception e3) {
            throw new RuntimeException(e3);
        }
    }
}
