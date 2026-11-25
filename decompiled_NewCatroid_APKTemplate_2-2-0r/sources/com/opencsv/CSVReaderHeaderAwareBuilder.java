package com.opencsv;

import java.io.IOException;
import java.io.Reader;

/* JADX INFO: loaded from: classes10.dex */
public class CSVReaderHeaderAwareBuilder extends CSVReaderBuilder {
    public CSVReaderHeaderAwareBuilder(Reader reader) {
        super(reader);
    }

    @Override // com.opencsv.CSVReaderBuilder
    public CSVReaderHeaderAware build() throws RuntimeException {
        try {
            return new CSVReaderHeaderAware(getReader(), getSkipLines(), getOrCreateCsvParser(), keepCarriageReturn(), isVerifyReader(), getMultilineLimit(), getErrorLocale(), getLineValidatorAggregator(), getRowValidatorAggregator());
        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize CSVReaderHeaderAware", e);
        }
    }
}
