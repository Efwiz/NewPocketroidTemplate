package com.opencsv.bean;

import com.opencsv.CSVReader;
import com.opencsv.ICSVParser;
import com.opencsv.bean.concurrent.LineExecutor;
import com.opencsv.bean.concurrent.OrderedObject;
import com.opencsv.bean.concurrent.ProcessCsvLine;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvMalformedLineException;
import com.opencsv.exceptions.CsvValidationException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.lang3.ObjectUtils;

/* JADX INFO: loaded from: classes10.dex */
public class CsvToBean<T> implements Iterable<T> {
    private CSVReader csvReader;
    private LineExecutor<T> executor;
    private String[] line;
    private long lineProcessed;
    private MappingStrategy<? extends T> mappingStrategy;
    private final List<CsvException> capturedExceptions = new LinkedList();
    private CsvToBeanFilter filter = null;
    private boolean throwExceptions = true;
    private boolean orderedResults = true;
    private Locale errorLocale = Locale.getDefault();
    private List<BeanVerifier<T>> verifiers = Collections.emptyList();

    private void submitAllBeans() throws Exception {
        while (true) {
            String[] next = this.csvReader.readNext();
            this.line = next;
            if (next != null) {
                long linesRead = this.csvReader.getLinesRead();
                this.lineProcessed = linesRead;
                this.executor.submitLine(linesRead, this.mappingStrategy, this.filter, this.verifiers, this.line, this.throwExceptions);
            } else {
                this.executor.complete();
                return;
            }
        }
    }

    public List<T> parse() throws IllegalStateException {
        return (List) stream().collect(Collectors.toList());
    }

    public Stream<T> stream() throws IllegalStateException {
        prepareToReadInput();
        LineExecutor<T> lineExecutor = new LineExecutor<>(this.orderedResults);
        this.executor = lineExecutor;
        lineExecutor.prepare();
        try {
            submitAllBeans();
            return this.executor.resultStream();
        } catch (CsvMalformedLineException e) {
            this.executor.shutdownNow();
            throw new RuntimeException(String.format(ResourceBundle.getBundle(ICSVParser.DEFAULT_BUNDLE_NAME, this.errorLocale).getString("parsing.error.full"), Long.valueOf(e.getLineNumber()), e.getContext()), e);
        } catch (RejectedExecutionException unused) {
            if (this.executor.getTerminalException() instanceof CsvException) {
                CsvException csvException = (CsvException) this.executor.getTerminalException();
                throw new RuntimeException(String.format(ResourceBundle.getBundle(ICSVParser.DEFAULT_BUNDLE_NAME, this.errorLocale).getString("parsing.error.linenumber"), Long.valueOf(csvException.getLineNumber()), String.join(",", csvException.getLine())), csvException);
            }
            throw new RuntimeException(ResourceBundle.getBundle(ICSVParser.DEFAULT_BUNDLE_NAME, this.errorLocale).getString("parsing.error"), this.executor.getTerminalException());
        } catch (Exception e2) {
            this.executor.shutdownNow();
            throw new RuntimeException(String.format(ResourceBundle.getBundle(ICSVParser.DEFAULT_BUNDLE_NAME, this.errorLocale).getString("parsing.error.full"), Long.valueOf(this.lineProcessed), Arrays.toString(this.line)), e2);
        }
    }

    public List<CsvException> getCapturedExceptions() {
        LineExecutor<T> lineExecutor = this.executor;
        return lineExecutor != null ? lineExecutor.getCapturedExceptions() : this.capturedExceptions;
    }

    public void setMappingStrategy(MappingStrategy<? extends T> mappingStrategy) {
        this.mappingStrategy = mappingStrategy;
    }

    public void setCsvReader(CSVReader cSVReader) {
        this.csvReader = cSVReader;
    }

    @Deprecated
    public void setFilter(CsvToBeanFilter csvToBeanFilter) {
        this.filter = csvToBeanFilter;
    }

    public void setThrowExceptions(boolean z) {
        this.throwExceptions = z;
    }

    public void setOrderedResults(boolean z) {
        this.orderedResults = z;
    }

    public void setErrorLocale(Locale locale) {
        Locale locale2 = (Locale) ObjectUtils.defaultIfNull(locale, Locale.getDefault());
        this.errorLocale = locale2;
        CSVReader cSVReader = this.csvReader;
        if (cSVReader != null) {
            cSVReader.setErrorLocale(locale2);
        }
        MappingStrategy<? extends T> mappingStrategy = this.mappingStrategy;
        if (mappingStrategy != null) {
            mappingStrategy.setErrorLocale(this.errorLocale);
        }
    }

    public void setVerifiers(List<BeanVerifier<T>> list) {
        this.verifiers = (List) ObjectUtils.defaultIfNull(list, Collections.emptyList());
    }

    private void prepareToReadInput() throws IllegalStateException {
        CSVReader cSVReader;
        MappingStrategy<? extends T> mappingStrategy = this.mappingStrategy;
        if (mappingStrategy == null || (cSVReader = this.csvReader) == null) {
            throw new IllegalStateException(ResourceBundle.getBundle(ICSVParser.DEFAULT_BUNDLE_NAME, this.errorLocale).getString("specify.strategy.reader"));
        }
        try {
            mappingStrategy.captureHeader(cSVReader);
            this.lineProcessed = 0L;
            this.line = null;
        } catch (Exception e) {
            throw new RuntimeException(ResourceBundle.getBundle(ICSVParser.DEFAULT_BUNDLE_NAME, this.errorLocale).getString("header.error"), e);
        }
    }

    @Override // java.lang.Iterable
    public Iterator<T> iterator() {
        prepareToReadInput();
        return new CsvToBeanIterator();
    }

    private class CsvToBeanIterator implements Iterator<T> {
        private T bean;
        private BlockingQueue<OrderedObject<T>> resultantBeansQueue = new ArrayBlockingQueue(1);
        private BlockingQueue<OrderedObject<CsvException>> thrownExceptionsQueue = new ArrayBlockingQueue(1);

        CsvToBeanIterator() {
            readSingleLine();
        }

        private void processException() {
            OrderedObject<CsvException> orderedObjectPoll = this.thrownExceptionsQueue.poll();
            if (orderedObjectPoll == null || orderedObjectPoll.getElement() == null) {
                return;
            }
            CsvToBean.this.capturedExceptions.add(orderedObjectPoll.getElement());
        }

        private void readLineWithPossibleError() throws CsvValidationException, IOException {
            this.bean = null;
            while (this.bean == null) {
                CsvToBean csvToBean = CsvToBean.this;
                if (csvToBean.line = csvToBean.csvReader.readNext() == null) {
                    break;
                }
                CsvToBean csvToBean2 = CsvToBean.this;
                csvToBean2.lineProcessed = csvToBean2.csvReader.getLinesRead();
                new ProcessCsvLine(CsvToBean.this.lineProcessed, CsvToBean.this.mappingStrategy, CsvToBean.this.filter, CsvToBean.this.verifiers, CsvToBean.this.line, this.resultantBeansQueue, this.thrownExceptionsQueue, CsvToBean.this.throwExceptions).run();
                if (!this.thrownExceptionsQueue.isEmpty()) {
                    processException();
                } else {
                    OrderedObject<T> orderedObjectPoll = this.resultantBeansQueue.poll();
                    this.bean = orderedObjectPoll == null ? null : orderedObjectPoll.getElement();
                }
            }
            if (CsvToBean.this.line == null) {
                this.bean = null;
            }
        }

        private void readSingleLine() {
            try {
                readLineWithPossibleError();
            } catch (CsvValidationException | IOException e) {
                CsvToBean.this.line = null;
                throw new RuntimeException(String.format(ResourceBundle.getBundle(ICSVParser.DEFAULT_BUNDLE_NAME, CsvToBean.this.errorLocale).getString("parsing.error"), Long.valueOf(CsvToBean.this.lineProcessed), Arrays.toString(CsvToBean.this.line)), e);
            }
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.bean != null;
        }

        @Override // java.util.Iterator
        public T next() {
            T t = this.bean;
            if (t == null) {
                throw new NoSuchElementException();
            }
            readSingleLine();
            return t;
        }

        @Override // java.util.Iterator
        public void remove() {
            throw new UnsupportedOperationException(ResourceBundle.getBundle(ICSVParser.DEFAULT_BUNDLE_NAME, CsvToBean.this.errorLocale).getString("read.only.iterator"));
        }
    }
}
