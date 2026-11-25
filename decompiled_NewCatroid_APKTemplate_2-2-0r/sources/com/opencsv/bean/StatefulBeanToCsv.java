package com.opencsv.bean;

import com.opencsv.CSVWriter;
import com.opencsv.ICSVParser;
import com.opencsv.ICSVWriter;
import com.opencsv.bean.concurrent.BeanExecutor;
import com.opencsv.bean.concurrent.OrderedObject;
import com.opencsv.bean.concurrent.ProcessCsvBean;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.opencsv.exceptions.CsvRuntimeException;
import java.io.Writer;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.function.Consumer;
import java.util.stream.Stream;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.iterators.PeekingIterator;
import org.apache.commons.lang3.ObjectUtils;

/* JADX INFO: loaded from: classes10.dex */
public class StatefulBeanToCsv<T> {
    private static final char NO_CHARACTER = 0;
    private boolean applyQuotesToAll;
    private List<CsvException> capturedExceptions;
    private ICSVWriter csvwriter;
    private Locale errorLocale;
    private final char escapechar;
    private BeanExecutor<T> executor;
    private boolean headerWritten;
    private final MultiValuedMap<Class<?>, Field> ignoredFields;
    private final String lineEnd;
    private int lineNumber;
    private MappingStrategy<T> mappingStrategy;
    private boolean orderedResults;
    private final char quotechar;
    private final char separator;
    private boolean throwExceptions;
    private final Writer writer;

    StatefulBeanToCsv(char c, String str, MappingStrategy<T> mappingStrategy, char c2, char c3, boolean z, Writer writer, boolean z2, MultiValuedMap<Class<?>, Field> multiValuedMap) {
        this.lineNumber = 0;
        this.headerWritten = false;
        this.capturedExceptions = new ArrayList();
        this.orderedResults = true;
        this.executor = null;
        this.errorLocale = Locale.getDefault();
        this.escapechar = c;
        this.lineEnd = str;
        this.mappingStrategy = mappingStrategy;
        this.quotechar = c2;
        this.separator = c3;
        this.throwExceptions = z;
        this.writer = writer;
        this.applyQuotesToAll = z2;
        this.ignoredFields = multiValuedMap;
    }

    public StatefulBeanToCsv(MappingStrategy<T> mappingStrategy, boolean z, boolean z2, ICSVWriter iCSVWriter, MultiValuedMap<Class<?>, Field> multiValuedMap) {
        this.lineNumber = 0;
        this.headerWritten = false;
        this.capturedExceptions = new ArrayList();
        this.orderedResults = true;
        this.executor = null;
        this.errorLocale = Locale.getDefault();
        this.mappingStrategy = mappingStrategy;
        this.throwExceptions = z;
        this.applyQuotesToAll = z2;
        this.csvwriter = iCSVWriter;
        this.escapechar = (char) 0;
        this.lineEnd = "";
        this.quotechar = (char) 0;
        this.separator = (char) 0;
        this.writer = null;
        this.ignoredFields = multiValuedMap;
    }

    private void beforeFirstWrite(T t) throws CsvRequiredFieldEmptyException {
        if (this.mappingStrategy == null) {
            this.mappingStrategy = OpencsvUtils.determineMappingStrategy(t.getClass(), this.errorLocale);
        }
        if (!this.ignoredFields.isEmpty()) {
            this.mappingStrategy.ignoreFields(this.ignoredFields);
        }
        if (this.csvwriter == null) {
            this.csvwriter = new CSVWriter(this.writer, this.separator, this.quotechar, this.escapechar, this.lineEnd);
        }
        String[] strArrGenerateHeader = this.mappingStrategy.generateHeader(t);
        if (strArrGenerateHeader.length > 0) {
            this.csvwriter.writeNext(strArrGenerateHeader, this.applyQuotesToAll);
        }
        this.headerWritten = true;
    }

    public void write(T t) throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        if (t != null) {
            if (!this.headerWritten) {
                beforeFirstWrite(t);
            }
            ArrayBlockingQueue arrayBlockingQueue = new ArrayBlockingQueue(1);
            ArrayBlockingQueue arrayBlockingQueue2 = new ArrayBlockingQueue(1);
            int i = this.lineNumber + 1;
            this.lineNumber = i;
            try {
                new ProcessCsvBean(i, this.mappingStrategy, t, arrayBlockingQueue, arrayBlockingQueue2, this.throwExceptions).run();
                if (!arrayBlockingQueue2.isEmpty()) {
                    OrderedObject orderedObject = (OrderedObject) arrayBlockingQueue2.poll();
                    if (orderedObject == null || orderedObject.getElement() == null) {
                        return;
                    }
                    this.capturedExceptions.add((CsvException) orderedObject.getElement());
                    return;
                }
                OrderedObject orderedObject2 = (OrderedObject) arrayBlockingQueue.poll();
                if (orderedObject2 == null || orderedObject2.getElement() == null) {
                    return;
                }
                this.csvwriter.writeNext((String[]) orderedObject2.getElement(), this.applyQuotesToAll);
            } catch (RuntimeException e) {
                if (e.getCause() != null) {
                    if (e.getCause() instanceof CsvRuntimeException) {
                        throw ((CsvRuntimeException) e.getCause());
                    }
                    if (e.getCause() instanceof CsvDataTypeMismatchException) {
                        throw ((CsvDataTypeMismatchException) e.getCause());
                    }
                    if (e.getCause() instanceof CsvRequiredFieldEmptyException) {
                        throw ((CsvRequiredFieldEmptyException) e.getCause());
                    }
                }
                throw e;
            }
        }
    }

    private void submitAllLines(Iterator<T> it) throws Exception {
        while (it.hasNext()) {
            T next = it.next();
            if (next != null) {
                BeanExecutor<T> beanExecutor = this.executor;
                int i = this.lineNumber + 1;
                this.lineNumber = i;
                beanExecutor.submitBean(i, this.mappingStrategy, next, this.throwExceptions);
            }
        }
        this.executor.complete();
    }

    public void write(List<T> list) throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        if (CollectionUtils.isNotEmpty(list)) {
            write((Iterator) list.iterator());
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void write(Iterator<T> it) throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        PeekingIterator peekingIterator = new PeekingIterator(it);
        Object objPeek = peekingIterator.peek();
        if (peekingIterator.hasNext()) {
            if (!this.headerWritten) {
                beforeFirstWrite(objPeek);
            }
            BeanExecutor<T> beanExecutor = new BeanExecutor<>(this.orderedResults);
            this.executor = beanExecutor;
            beanExecutor.prepare();
            try {
                submitAllLines(peekingIterator);
                this.capturedExceptions.addAll(this.executor.getCapturedExceptions());
                this.executor.resultStream().forEach(new Consumer() { // from class: com.opencsv.bean.StatefulBeanToCsv$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        this.f$0.m2754lambda$write$0$comopencsvbeanStatefulBeanToCsv((String[]) obj);
                    }
                });
            } catch (RejectedExecutionException unused) {
                if (this.executor.getTerminalException() instanceof RuntimeException) {
                    throw ((RuntimeException) this.executor.getTerminalException());
                }
                if (this.executor.getTerminalException() instanceof CsvDataTypeMismatchException) {
                    throw ((CsvDataTypeMismatchException) this.executor.getTerminalException());
                }
                if (this.executor.getTerminalException() instanceof CsvRequiredFieldEmptyException) {
                    throw ((CsvRequiredFieldEmptyException) this.executor.getTerminalException());
                }
                throw new RuntimeException(ResourceBundle.getBundle(ICSVParser.DEFAULT_BUNDLE_NAME, this.errorLocale).getString("error.writing.beans"), this.executor.getTerminalException());
            } catch (Exception e) {
                this.executor.shutdownNow();
                if (this.executor.getTerminalException() instanceof RuntimeException) {
                    throw ((RuntimeException) this.executor.getTerminalException());
                }
                throw new RuntimeException(ResourceBundle.getBundle(ICSVParser.DEFAULT_BUNDLE_NAME, this.errorLocale).getString("error.writing.beans"), e);
            }
        }
    }

    /* JADX INFO: renamed from: lambda$write$0$com-opencsv-bean-StatefulBeanToCsv, reason: not valid java name */
    /* synthetic */ void m2754lambda$write$0$comopencsvbeanStatefulBeanToCsv(String[] strArr) {
        this.csvwriter.writeNext(strArr, this.applyQuotesToAll);
    }

    public void write(Stream<T> stream) throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        write((Iterator) stream.iterator());
    }

    public void setOrderedResults(boolean z) {
        this.orderedResults = z;
    }

    public boolean isThrowExceptions() {
        return this.throwExceptions;
    }

    public List<CsvException> getCapturedExceptions() {
        List<CsvException> list = this.capturedExceptions;
        this.capturedExceptions = new ArrayList();
        return list;
    }

    public void setErrorLocale(Locale locale) {
        this.errorLocale = (Locale) ObjectUtils.defaultIfNull(locale, Locale.getDefault());
    }
}
