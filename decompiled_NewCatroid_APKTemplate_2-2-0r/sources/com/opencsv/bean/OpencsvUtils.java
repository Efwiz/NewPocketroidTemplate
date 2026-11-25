package com.opencsv.bean;

import com.opencsv.ICSVParser;
import com.opencsv.exceptions.CsvBadConverterException;
import java.lang.reflect.Field;
import java.util.IllegalFormatException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.BlockingQueue;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import java.util.stream.Stream;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;

/* JADX INFO: loaded from: classes10.dex */
public final class OpencsvUtils {
    private OpencsvUtils() {
    }

    static <T> MappingStrategy<T> determineMappingStrategy(Class<? extends T> cls, Locale locale) {
        MappingStrategy<T> columnPositionMappingStrategy = Stream.of((Object[]) FieldUtils.getAllFields(cls)).anyMatch(new Predicate() { // from class: com.opencsv.bean.OpencsvUtils$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return OpencsvUtils.lambda$determineMappingStrategy$0((Field) obj);
            }
        }) ? new ColumnPositionMappingStrategy<>() : new HeaderColumnNameMappingStrategy<>();
        columnPositionMappingStrategy.setErrorLocale(locale);
        columnPositionMappingStrategy.setType(cls);
        return columnPositionMappingStrategy;
    }

    static /* synthetic */ boolean lambda$determineMappingStrategy$0(Field field) {
        return field.isAnnotationPresent(CsvBindByPosition.class) || field.isAnnotationPresent(CsvBindAndSplitByPosition.class) || field.isAnnotationPresent(CsvBindAndJoinByPosition.class) || field.isAnnotationPresent(CsvCustomBindByPosition.class);
    }

    public static <E> void queueRefuseToAcceptDefeat(BlockingQueue<E> blockingQueue, E e) {
        boolean z = true;
        while (z) {
            try {
                blockingQueue.put(e);
                z = false;
            } catch (InterruptedException unused) {
            }
        }
    }

    static Pattern compilePatternAtLeastOneGroup(String str, int i, Class<?> cls, Locale locale) throws CsvBadConverterException {
        Pattern patternCompilePattern = compilePattern(str, i, cls, locale);
        if (locale == null) {
            locale = Locale.getDefault();
        }
        if (patternCompilePattern == null || patternCompilePattern.matcher("").groupCount() >= 1) {
            return patternCompilePattern;
        }
        throw new CsvBadConverterException(cls, String.format(ResourceBundle.getBundle(ICSVParser.DEFAULT_BUNDLE_NAME, locale).getString("regex.without.capture.group"), str));
    }

    static Pattern compilePattern(String str, int i, Class<?> cls, Locale locale) throws CsvBadConverterException {
        if (locale == null) {
            locale = Locale.getDefault();
        }
        if (!StringUtils.isNotEmpty(str)) {
            return null;
        }
        try {
            return Pattern.compile(str, i);
        } catch (PatternSyntaxException e) {
            CsvBadConverterException csvBadConverterException = new CsvBadConverterException(cls, String.format(ResourceBundle.getBundle(ICSVParser.DEFAULT_BUNDLE_NAME, locale).getString("invalid.regex"), str));
            csvBadConverterException.initCause(e);
            throw csvBadConverterException;
        }
    }

    static void verifyFormatString(String str, Class<?> cls, Locale locale) {
        if (locale == null) {
            locale = Locale.getDefault();
        }
        try {
            if (StringUtils.isNotEmpty(str)) {
                String.format(str, " ");
            }
        } catch (IllegalFormatException e) {
            CsvBadConverterException csvBadConverterException = new CsvBadConverterException(cls, String.format(ResourceBundle.getBundle(ICSVParser.DEFAULT_BUNDLE_NAME, locale).getString("invalid.one.parameter.format.string"), str));
            csvBadConverterException.initCause(e);
            throw csvBadConverterException;
        }
    }
}
