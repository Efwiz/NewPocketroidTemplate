package com.opencsv;

import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvMalformedLineException;
import com.opencsv.exceptions.CsvMultilineLimitBrokenException;
import com.opencsv.exceptions.CsvRuntimeException;
import com.opencsv.exceptions.CsvValidationException;
import com.opencsv.processor.RowProcessor;
import com.opencsv.stream.reader.LineReader;
import com.opencsv.validators.LineValidatorAggregator;
import com.opencsv.validators.RowValidatorAggregator;
import java.io.BufferedReader;
import java.io.CharConversionException;
import java.io.Closeable;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.io.UTFDataFormatException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.CharacterCodingException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.zip.ZipException;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes10.dex */
public class CSVReader implements Closeable, Iterable<String[]> {
    static final int CONTEXT_MULTILINE_EXCEPTION_MESSAGE_SIZE = 100;
    public static final boolean DEFAULT_KEEP_CR = false;
    public static final int DEFAULT_MULTILINE_LIMIT = 0;
    public static final int DEFAULT_SKIP_LINES = 0;
    public static final boolean DEFAULT_VERIFY_READER = true;
    private static final int MAX_WIDTH = 100;
    private static final List<Class<? extends IOException>> PASSTHROUGH_EXCEPTIONS = Arrays.asList(CharacterCodingException.class, CharConversionException.class, UnsupportedEncodingException.class, UTFDataFormatException.class, ZipException.class, FileNotFoundException.class);
    public static final int READ_AHEAD_LIMIT = 2;
    protected BufferedReader br;
    protected Locale errorLocale;
    protected boolean hasNext;
    protected boolean keepCR;
    protected LineReader lineReader;
    private final LineValidatorAggregator lineValidatorAggregator;
    protected long linesRead;
    protected boolean linesSkipped;
    protected int multilineLimit;
    protected ICSVParser parser;
    protected String[] peekedLine;
    protected long recordsRead;
    private final RowProcessor rowProcessor;
    private final RowValidatorAggregator rowValidatorAggregator;
    protected int skipLines;
    protected boolean verifyReader;

    public CSVReader(Reader reader) {
        this(reader, 0, new CSVParser(',', '\"', '\\', false, true, false, ICSVParser.DEFAULT_NULL_FIELD_INDICATOR, Locale.getDefault()), false, true, 0, Locale.getDefault(), new LineValidatorAggregator(), new RowValidatorAggregator(), null);
    }

    CSVReader(Reader reader, int i, ICSVParser iCSVParser, boolean z, boolean z2, int i2, Locale locale, LineValidatorAggregator lineValidatorAggregator, RowValidatorAggregator rowValidatorAggregator, RowProcessor rowProcessor) {
        this.hasNext = true;
        this.multilineLimit = 0;
        this.linesRead = 0L;
        this.recordsRead = 0L;
        this.peekedLine = null;
        this.br = reader instanceof BufferedReader ? (BufferedReader) reader : new BufferedReader(reader);
        this.lineReader = new LineReader(this.br, z);
        this.skipLines = i;
        this.parser = iCSVParser;
        this.keepCR = z;
        this.verifyReader = z2;
        this.multilineLimit = i2;
        this.errorLocale = (Locale) ObjectUtils.defaultIfNull(locale, Locale.getDefault());
        this.lineValidatorAggregator = lineValidatorAggregator;
        this.rowValidatorAggregator = rowValidatorAggregator;
        this.rowProcessor = rowProcessor;
    }

    public ICSVParser getParser() {
        return this.parser;
    }

    public int getSkipLines() {
        return this.skipLines;
    }

    public boolean keepCarriageReturns() {
        return this.keepCR;
    }

    public List<String[]> readAll() throws CsvException, IOException {
        LinkedList linkedList = new LinkedList();
        while (this.hasNext) {
            String[] next = readNext();
            if (next != null) {
                linkedList.add(next);
            }
        }
        return linkedList;
    }

    public String[] readNext() throws CsvValidationException, IOException {
        return readNext(true);
    }

    public String[] readNextSilently() throws IOException {
        try {
            return readNext(false);
        } catch (CsvValidationException e) {
            throw new CsvRuntimeException("A CSValidationException was thrown from the runNextSilently method which should not happen", e);
        }
    }

    private String[] readNext(boolean z) throws CsvValidationException, IOException {
        String[] strArr = this.peekedLine;
        String[] strArrCombineResultsFromMultipleReads = null;
        if (strArr != null) {
            this.peekedLine = null;
            return strArr;
        }
        long j = this.linesRead;
        int i = 0;
        do {
            String nextLine = getNextLine();
            validateLine(z, j, nextLine);
            i++;
            if (!this.hasNext) {
                if (this.parser.isPending()) {
                    throw new CsvMalformedLineException(String.format(ResourceBundle.getBundle(ICSVParser.DEFAULT_BUNDLE_NAME, this.errorLocale).getString("unterminated.quote"), StringUtils.abbreviate(this.parser.getPendingText(), 100)), j + 1, this.parser.getPendingText());
                }
                return validateResult(strArrCombineResultsFromMultipleReads, j + 1, z);
            }
            int i2 = this.multilineLimit;
            if (i2 > 0 && i > i2) {
                long j2 = this.recordsRead + 1;
                String pendingText = this.parser.getPendingText();
                if (pendingText.length() > 100) {
                    pendingText = pendingText.substring(0, 100);
                }
                throw new CsvMultilineLimitBrokenException(String.format(this.errorLocale, ResourceBundle.getBundle(ICSVParser.DEFAULT_BUNDLE_NAME, this.errorLocale).getString("multiline.limit.broken"), Integer.valueOf(this.multilineLimit), Long.valueOf(j2), pendingText), j2, this.parser.getPendingText(), this.multilineLimit);
            }
            String[] lineMulti = this.parser.parseLineMulti(nextLine);
            if (lineMulti.length > 0) {
                strArrCombineResultsFromMultipleReads = strArrCombineResultsFromMultipleReads == null ? lineMulti : combineResultsFromMultipleReads(strArrCombineResultsFromMultipleReads, lineMulti);
            }
        } while (this.parser.isPending());
        return validateResult(strArrCombineResultsFromMultipleReads, j + 1, z);
    }

    private void validateLine(boolean z, long j, String str) throws CsvValidationException {
        if (z) {
            try {
                this.lineValidatorAggregator.validate(str);
            } catch (CsvValidationException e) {
                e.setLineNumber(j + 1);
                throw e;
            }
        }
    }

    protected String[] validateResult(String[] strArr, long j, boolean z) throws CsvValidationException {
        if (strArr != null) {
            if (z) {
                RowProcessor rowProcessor = this.rowProcessor;
                if (rowProcessor != null) {
                    rowProcessor.processRow(strArr);
                }
                try {
                    this.rowValidatorAggregator.validate(strArr);
                } catch (CsvValidationException e) {
                    e.setLineNumber(j);
                    throw e;
                }
            }
            this.recordsRead++;
        }
        return strArr;
    }

    protected String[] combineResultsFromMultipleReads(String[] strArr, String[] strArr2) {
        String[] strArr3 = new String[strArr.length + strArr2.length];
        System.arraycopy(strArr, 0, strArr3, 0, strArr.length);
        System.arraycopy(strArr2, 0, strArr3, strArr.length, strArr2.length);
        return strArr3;
    }

    protected String getNextLine() throws IOException {
        if (isClosed()) {
            this.hasNext = false;
            return null;
        }
        if (!this.linesSkipped) {
            for (int i = 0; i < this.skipLines; i++) {
                this.lineReader.readLine();
                this.linesRead++;
            }
            this.linesSkipped = true;
        }
        String line = this.lineReader.readLine();
        if (line == null) {
            this.hasNext = false;
        } else {
            this.linesRead++;
        }
        if (this.hasNext) {
            return line;
        }
        return null;
    }

    public int getMultilineLimit() {
        return this.multilineLimit;
    }

    protected boolean isClosed() throws IOException {
        if (!this.verifyReader) {
            return false;
        }
        try {
            this.br.mark(2);
            int i = this.br.read();
            this.br.reset();
            return i == -1;
        } catch (IOException e) {
            if (PASSTHROUGH_EXCEPTIONS.contains(e.getClass())) {
                throw e;
            }
            return true;
        }
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.br.close();
    }

    @Override // java.lang.Iterable
    public Iterator<String[]> iterator() {
        try {
            CSVIterator cSVIterator = new CSVIterator(this);
            cSVIterator.setErrorLocale(this.errorLocale);
            return cSVIterator;
        } catch (CsvValidationException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean verifyReader() {
        return this.verifyReader;
    }

    public long getLinesRead() {
        return this.linesRead;
    }

    public long getRecordsRead() {
        return this.recordsRead;
    }

    public void skip(int i) throws IOException {
        for (int i2 = 0; i2 < i; i2++) {
            readNextSilently();
        }
    }

    public void setErrorLocale(Locale locale) {
        Locale locale2 = (Locale) ObjectUtils.defaultIfNull(locale, Locale.getDefault());
        this.errorLocale = locale2;
        ICSVParser iCSVParser = this.parser;
        if (iCSVParser != null) {
            iCSVParser.setErrorLocale(locale2);
        }
    }

    public String[] peek() throws IOException {
        if (this.peekedLine == null) {
            this.peekedLine = readNextSilently();
        }
        return this.peekedLine;
    }
}
