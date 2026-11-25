package com.opencsv.bean.concurrent;

import com.opencsv.bean.BeanVerifier;
import com.opencsv.bean.CsvToBeanFilter;
import com.opencsv.bean.MappingStrategy;
import com.opencsv.bean.OpencsvUtils;
import com.opencsv.exceptions.CsvBadConverterException;
import com.opencsv.exceptions.CsvBeanIntrospectionException;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.opencsv.exceptions.CsvValidationException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.BlockingQueue;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;

/* JADX INFO: loaded from: classes10.dex */
public class ProcessCsvLine<T> implements Runnable {
    private final CsvToBeanFilter filter;
    private final String[] line;
    private final long lineNumber;
    private final MappingStrategy<? extends T> mapper;
    private final BlockingQueue<OrderedObject<T>> resultantBeanQueue;
    private final boolean throwExceptions;
    private final BlockingQueue<OrderedObject<CsvException>> thrownExceptionsQueue;
    private final List<BeanVerifier<T>> verifiers;

    public ProcessCsvLine(long j, MappingStrategy<? extends T> mappingStrategy, CsvToBeanFilter csvToBeanFilter, List<BeanVerifier<T>> list, String[] strArr, BlockingQueue<OrderedObject<T>> blockingQueue, BlockingQueue<OrderedObject<CsvException>> blockingQueue2, boolean z) {
        this.lineNumber = j;
        this.mapper = mappingStrategy;
        this.filter = csvToBeanFilter;
        this.verifiers = (List) ObjectUtils.defaultIfNull(new ArrayList(list), Collections.emptyList());
        this.line = (String[]) ArrayUtils.clone(strArr);
        this.resultantBeanQueue = blockingQueue;
        this.thrownExceptionsQueue = blockingQueue2;
        this.throwExceptions = z;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            CsvToBeanFilter csvToBeanFilter = this.filter;
            if (csvToBeanFilter == null || csvToBeanFilter.allowLine(this.line)) {
                T tProcessLine = processLine();
                ListIterator<BeanVerifier<T>> listIterator = this.verifiers.listIterator();
                boolean zVerifyBean = true;
                while (zVerifyBean && listIterator.hasNext()) {
                    zVerifyBean = listIterator.next().verifyBean(tProcessLine);
                }
                if (zVerifyBean) {
                    OpencsvUtils.queueRefuseToAcceptDefeat(this.resultantBeanQueue, new OrderedObject(this.lineNumber, tProcessLine));
                }
            }
        } catch (CsvException e) {
            e.setLineNumber(this.lineNumber);
            e.setLine(this.line);
            if (this.throwExceptions) {
                throw new RuntimeException(e);
            }
            OpencsvUtils.queueRefuseToAcceptDefeat(this.thrownExceptionsQueue, new OrderedObject(this.lineNumber, e));
        } catch (Exception e2) {
            throw new RuntimeException(e2);
        }
    }

    private T processLine() throws CsvBeanIntrospectionException, CsvValidationException, CsvBadConverterException, CsvRequiredFieldEmptyException, CsvConstraintViolationException, CsvDataTypeMismatchException {
        return this.mapper.populateNewBean(this.line);
    }
}
