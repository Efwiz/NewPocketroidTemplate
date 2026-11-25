package com.opencsv.bean;

import com.opencsv.ICSVParser;
import com.opencsv.ICSVWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.util.Locale;
import java.util.ResourceBundle;
import org.apache.commons.collections4.ListValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.apache.commons.lang3.ObjectUtils;

/* JADX INFO: loaded from: classes10.dex */
public class StatefulBeanToCsvBuilder<T> {
    private boolean applyQuotesToAll;
    private final ICSVWriter csvWriter;
    private Locale errorLocale;
    private char escapechar;
    private final ListValuedMap<Class<?>, Field> ignoredFields;
    private String lineEnd;
    private MappingStrategy<T> mappingStrategy;
    private boolean orderedResults;
    private char quotechar;
    private char separator;
    private boolean throwExceptions;
    private final Writer writer;

    public StatefulBeanToCsvBuilder(Writer writer) {
        this.separator = ',';
        this.quotechar = '\"';
        this.escapechar = '\"';
        this.lineEnd = "\n";
        this.mappingStrategy = null;
        this.throwExceptions = true;
        this.orderedResults = true;
        this.errorLocale = Locale.getDefault();
        this.applyQuotesToAll = true;
        this.ignoredFields = new ArrayListValuedHashMap();
        this.writer = writer;
        this.csvWriter = null;
    }

    public StatefulBeanToCsvBuilder(ICSVWriter iCSVWriter) {
        this.separator = ',';
        this.quotechar = '\"';
        this.escapechar = '\"';
        this.lineEnd = "\n";
        this.mappingStrategy = null;
        this.throwExceptions = true;
        this.orderedResults = true;
        this.errorLocale = Locale.getDefault();
        this.applyQuotesToAll = true;
        this.ignoredFields = new ArrayListValuedHashMap();
        this.writer = null;
        this.csvWriter = iCSVWriter;
    }

    public StatefulBeanToCsvBuilder<T> withMappingStrategy(MappingStrategy<T> mappingStrategy) {
        this.mappingStrategy = mappingStrategy;
        return this;
    }

    public StatefulBeanToCsvBuilder<T> withSeparator(char c) {
        this.separator = c;
        return this;
    }

    public StatefulBeanToCsvBuilder<T> withQuotechar(char c) {
        this.quotechar = c;
        return this;
    }

    public StatefulBeanToCsvBuilder<T> withEscapechar(char c) {
        this.escapechar = c;
        return this;
    }

    public StatefulBeanToCsvBuilder<T> withLineEnd(String str) {
        this.lineEnd = str;
        return this;
    }

    public StatefulBeanToCsvBuilder<T> withThrowExceptions(boolean z) {
        this.throwExceptions = z;
        return this;
    }

    public StatefulBeanToCsvBuilder<T> withOrderedResults(boolean z) {
        this.orderedResults = z;
        return this;
    }

    public StatefulBeanToCsvBuilder<T> withErrorLocale(Locale locale) {
        this.errorLocale = (Locale) ObjectUtils.defaultIfNull(locale, Locale.getDefault());
        return this;
    }

    public StatefulBeanToCsvBuilder<T> withApplyQuotesToAll(boolean z) {
        this.applyQuotesToAll = z;
        return this;
    }

    public StatefulBeanToCsvBuilder<T> withIgnoreField(Class<?> cls, Field field) throws IllegalArgumentException {
        if (cls != null && field != null && field.getDeclaringClass().isAssignableFrom(cls)) {
            this.ignoredFields.put(cls, field);
            return this;
        }
        throw new IllegalArgumentException(ResourceBundle.getBundle(ICSVParser.DEFAULT_BUNDLE_NAME, this.errorLocale).getString("ignore.field.inconsistent"));
    }

    public StatefulBeanToCsv<T> build() {
        StatefulBeanToCsv<T> statefulBeanToCsv;
        if (this.writer != null) {
            statefulBeanToCsv = new StatefulBeanToCsv<>(this.escapechar, this.lineEnd, this.mappingStrategy, this.quotechar, this.separator, this.throwExceptions, this.writer, this.applyQuotesToAll, this.ignoredFields);
        } else {
            statefulBeanToCsv = new StatefulBeanToCsv<>(this.mappingStrategy, this.throwExceptions, this.applyQuotesToAll, this.csvWriter, this.ignoredFields);
        }
        statefulBeanToCsv.setOrderedResults(this.orderedResults);
        statefulBeanToCsv.setErrorLocale(this.errorLocale);
        return statefulBeanToCsv;
    }
}
