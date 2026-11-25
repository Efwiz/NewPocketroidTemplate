package com.opencsv;

import com.opencsv.enums.CSVReaderNullFieldIndicator;
import java.io.IOException;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes10.dex */
public abstract class AbstractCSVParser implements ICSVParser {
    protected final CSVReaderNullFieldIndicator nullFieldIndicator;
    protected String pending;
    protected final char quotechar;
    protected final char separator;

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX INFO: renamed from: convertToCsvValue, reason: merged with bridge method [inline-methods] */
    public abstract String m2728lambda$parseToLine$0$comopencsvAbstractCSVParser(String str, boolean z);

    protected abstract String[] parseLine(String str, boolean z) throws IOException;

    public AbstractCSVParser(char c, char c2, CSVReaderNullFieldIndicator cSVReaderNullFieldIndicator) {
        this.separator = c;
        this.quotechar = c2;
        this.nullFieldIndicator = cSVReaderNullFieldIndicator;
    }

    @Override // com.opencsv.ICSVParser
    public char getSeparator() {
        return this.separator;
    }

    @Override // com.opencsv.ICSVParser
    public char getQuotechar() {
        return this.quotechar;
    }

    @Override // com.opencsv.ICSVParser
    public boolean isPending() {
        return this.pending != null;
    }

    @Override // com.opencsv.ICSVParser
    public String[] parseLineMulti(String str) throws IOException {
        return parseLine(str, true);
    }

    @Override // com.opencsv.ICSVParser
    public String[] parseLine(String str) throws IOException {
        return parseLine(str, false);
    }

    @Override // com.opencsv.ICSVParser
    public String parseToLine(String[] strArr, final boolean z) {
        return (String) Stream.of((Object[]) strArr).map(new Function() { // from class: com.opencsv.AbstractCSVParser$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.m2728lambda$parseToLine$0$comopencsvAbstractCSVParser(z, (String) obj);
            }
        }).collect(Collectors.joining(Character.toString(getSeparator())));
    }

    protected boolean isSurroundWithQuotes(String str, boolean z) {
        if (str == null) {
            return this.nullFieldIndicator.equals(CSVReaderNullFieldIndicator.EMPTY_QUOTES);
        }
        return z || str.contains(Character.toString(getSeparator())) || str.contains("\n");
    }

    @Override // com.opencsv.ICSVParser
    public CSVReaderNullFieldIndicator nullFieldIndicator() {
        return this.nullFieldIndicator;
    }

    @Override // com.opencsv.ICSVParser
    public String getPendingText() {
        return StringUtils.defaultString(this.pending);
    }
}
