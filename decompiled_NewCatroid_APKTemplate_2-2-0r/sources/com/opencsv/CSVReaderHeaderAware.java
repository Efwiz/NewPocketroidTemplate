package com.opencsv;

import com.opencsv.exceptions.CsvValidationException;
import com.opencsv.validators.LineValidatorAggregator;
import com.opencsv.validators.RowValidatorAggregator;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/* JADX INFO: loaded from: classes10.dex */
public class CSVReaderHeaderAware extends CSVReader {
    private final Map<String, Integer> headerIndex;

    public CSVReaderHeaderAware(Reader reader) throws IOException {
        super(reader);
        this.headerIndex = new HashMap();
        initializeHeader();
    }

    CSVReaderHeaderAware(Reader reader, int i, ICSVParser iCSVParser, boolean z, boolean z2, int i2, Locale locale, LineValidatorAggregator lineValidatorAggregator, RowValidatorAggregator rowValidatorAggregator) throws IOException {
        super(reader, i, iCSVParser, z, z2, i2, locale, lineValidatorAggregator, rowValidatorAggregator, null);
        this.headerIndex = new HashMap();
        initializeHeader();
    }

    public String[] readNext(String... strArr) throws CsvValidationException, IOException {
        if (strArr == null) {
            return super.readNextSilently();
        }
        String[] next = readNext();
        if (next == null) {
            return null;
        }
        if (next.length != this.headerIndex.size()) {
            throw new IOException(String.format(ResourceBundle.getBundle(ICSVParser.DEFAULT_BUNDLE_NAME, this.errorLocale).getString("header.data.mismatch.with.line.number"), Long.valueOf(getRecordsRead())));
        }
        String[] strArr2 = new String[strArr.length];
        for (int i = 0; i < strArr.length; i++) {
            String str = strArr[i];
            Integer num = this.headerIndex.get(str);
            if (num == null) {
                throw new IllegalArgumentException(String.format(ResourceBundle.getBundle(ICSVParser.DEFAULT_BUNDLE_NAME, this.errorLocale).getString("header.nonexistant"), str));
            }
            strArr2[i] = next[num.intValue()];
        }
        return strArr2;
    }

    public Map<String, String> readMap() throws CsvValidationException, IOException {
        final String[] next = readNext();
        if (next == null) {
            return null;
        }
        if (next.length != this.headerIndex.size()) {
            throw new IOException(String.format(ResourceBundle.getBundle(ICSVParser.DEFAULT_BUNDLE_NAME, this.errorLocale).getString("header.data.mismatch.with.line.number"), Long.valueOf(getRecordsRead())));
        }
        return (Map) this.headerIndex.entrySet().stream().filter(new Predicate() { // from class: com.opencsv.CSVReaderHeaderAware$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return CSVReaderHeaderAware.lambda$readMap$0(next, (Map.Entry) obj);
            }
        }).collect(Collectors.toMap(new Function() { // from class: com.opencsv.CSVReaderHeaderAware$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return CSVReaderHeaderAware.lambda$readMap$1((Map.Entry) obj);
            }
        }, new Function() { // from class: com.opencsv.CSVReaderHeaderAware$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return CSVReaderHeaderAware.lambda$readMap$2(next, (Map.Entry) obj);
            }
        }));
    }

    static /* synthetic */ boolean lambda$readMap$0(String[] strArr, Map.Entry entry) {
        return ((Integer) entry.getValue()).intValue() < strArr.length;
    }

    static /* synthetic */ String lambda$readMap$1(Map.Entry entry) {
        return (String) entry.getKey();
    }

    static /* synthetic */ String lambda$readMap$2(String[] strArr, Map.Entry entry) {
        return strArr[((Integer) entry.getValue()).intValue()];
    }

    private void initializeHeader() throws IOException {
        String[] nextSilently = super.readNextSilently();
        for (int i = 0; i < nextSilently.length; i++) {
            this.headerIndex.put(nextSilently[i], Integer.valueOf(i));
        }
    }
}
