package com.opencsv;

import com.opencsv.enums.CSVReaderNullFieldIndicator;
import com.opencsv.processor.RowProcessor;
import com.opencsv.validators.LineValidator;
import com.opencsv.validators.LineValidatorAggregator;
import com.opencsv.validators.RowValidator;
import com.opencsv.validators.RowValidatorAggregator;
import java.io.Reader;
import java.util.Locale;
import java.util.ResourceBundle;
import org.apache.commons.lang3.ObjectUtils;

/* JADX INFO: loaded from: classes10.dex */
public class CSVReaderBuilder {
    private boolean keepCR;
    private final Reader reader;
    private final CSVParserBuilder parserBuilder = new CSVParserBuilder();
    private int skipLines = 0;
    private ICSVParser icsvParser = null;
    private boolean verifyReader = true;
    private CSVReaderNullFieldIndicator nullFieldIndicator = CSVReaderNullFieldIndicator.NEITHER;
    private int multilineLimit = 0;
    private Locale errorLocale = Locale.getDefault();
    private LineValidatorAggregator lineValidatorAggregator = new LineValidatorAggregator();
    private RowValidatorAggregator rowValidatorAggregator = new RowValidatorAggregator();
    private RowProcessor rowProcessor = null;

    public CSVReaderBuilder(Reader reader) {
        if (reader == null) {
            throw new IllegalArgumentException(ResourceBundle.getBundle(ICSVParser.DEFAULT_BUNDLE_NAME).getString("reader.null"));
        }
        this.reader = reader;
    }

    protected Reader getReader() {
        return this.reader;
    }

    protected int getSkipLines() {
        return this.skipLines;
    }

    protected ICSVParser getCsvParser() {
        return this.icsvParser;
    }

    protected int getMultilineLimit() {
        return this.multilineLimit;
    }

    public CSVReaderBuilder withSkipLines(int i) {
        this.skipLines = Math.max(i, 0);
        return this;
    }

    public CSVReaderBuilder withCSVParser(ICSVParser iCSVParser) {
        this.icsvParser = iCSVParser;
        return this;
    }

    public CSVReader build() {
        return new CSVReader(this.reader, this.skipLines, getOrCreateCsvParser(), this.keepCR, this.verifyReader, this.multilineLimit, this.errorLocale, this.lineValidatorAggregator, this.rowValidatorAggregator, this.rowProcessor);
    }

    public CSVReaderBuilder withKeepCarriageReturn(boolean z) {
        this.keepCR = z;
        return this;
    }

    protected boolean keepCarriageReturn() {
        return this.keepCR;
    }

    protected ICSVParser getOrCreateCsvParser() {
        return (ICSVParser) ObjectUtils.defaultIfNull(this.icsvParser, this.parserBuilder.withFieldAsNull(this.nullFieldIndicator).withErrorLocale(this.errorLocale).build());
    }

    public CSVReaderBuilder withVerifyReader(boolean z) {
        this.verifyReader = z;
        return this;
    }

    public boolean isVerifyReader() {
        return this.verifyReader;
    }

    public CSVReaderBuilder withFieldAsNull(CSVReaderNullFieldIndicator cSVReaderNullFieldIndicator) {
        this.nullFieldIndicator = cSVReaderNullFieldIndicator;
        return this;
    }

    public CSVReaderBuilder withMultilineLimit(int i) {
        this.multilineLimit = i;
        return this;
    }

    public CSVReaderBuilder withErrorLocale(Locale locale) {
        this.errorLocale = (Locale) ObjectUtils.defaultIfNull(locale, Locale.getDefault());
        return this;
    }

    public Locale getErrorLocale() {
        return this.errorLocale;
    }

    public LineValidatorAggregator getLineValidatorAggregator() {
        return this.lineValidatorAggregator;
    }

    public RowValidatorAggregator getRowValidatorAggregator() {
        return this.rowValidatorAggregator;
    }

    public CSVReaderBuilder withLineValidator(LineValidator lineValidator) {
        this.lineValidatorAggregator.addValidator(lineValidator);
        return this;
    }

    public CSVReaderBuilder withRowValidator(RowValidator rowValidator) {
        this.rowValidatorAggregator.addValidator(rowValidator);
        return this;
    }

    public CSVReaderBuilder withRowProcessor(RowProcessor rowProcessor) {
        this.rowProcessor = rowProcessor;
        return this;
    }
}
