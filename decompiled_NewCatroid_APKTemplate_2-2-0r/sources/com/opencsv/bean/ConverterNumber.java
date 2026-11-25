package com.opencsv.bean;

import com.opencsv.ICSVParser;
import com.opencsv.exceptions.CsvBadConverterException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes10.dex */
public class ConverterNumber extends AbstractCsvConverter {
    private final UnaryOperator<Number> readConversionFunction;
    private final DecimalFormat readFormatter;
    private final DecimalFormat writeFormatter;

    static /* synthetic */ Number lambda$new$1(Number number) {
        return number;
    }

    public ConverterNumber(Class<?> cls, String str, String str2, Locale locale, String str3, String str4) throws CsvBadConverterException {
        super(cls, str, str2, locale);
        if (!Number.class.isAssignableFrom(this.type.isPrimitive() ? ClassUtils.primitiveToWrapper(this.type) : this.type)) {
            throw new CsvBadConverterException(ConverterNumber.class, ResourceBundle.getBundle(ICSVParser.DEFAULT_BUNDLE_NAME, this.errorLocale).getString("csvnumber.not.number"));
        }
        DecimalFormat decimalFormatCreateDecimalFormat = createDecimalFormat(str3, this.locale);
        this.readFormatter = decimalFormatCreateDecimalFormat;
        if (this.type == BigInteger.class || this.type == BigDecimal.class) {
            decimalFormatCreateDecimalFormat.setParseBigDecimal(true);
        }
        if (this.type == Byte.class || this.type == Byte.TYPE) {
            this.readConversionFunction = new UnaryOperator() { // from class: com.opencsv.bean.ConverterNumber$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return Byte.valueOf(((Number) obj).byteValue());
                }
            };
        } else if (this.type == Short.class || this.type == Short.TYPE) {
            this.readConversionFunction = new UnaryOperator() { // from class: com.opencsv.bean.ConverterNumber$$ExternalSyntheticLambda1
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return Short.valueOf(((Number) obj).shortValue());
                }
            };
        } else if (this.type == Integer.class || this.type == Integer.TYPE) {
            this.readConversionFunction = new UnaryOperator() { // from class: com.opencsv.bean.ConverterNumber$$ExternalSyntheticLambda2
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return Integer.valueOf(((Number) obj).intValue());
                }
            };
        } else if (this.type == Long.class || this.type == Long.TYPE) {
            this.readConversionFunction = new UnaryOperator() { // from class: com.opencsv.bean.ConverterNumber$$ExternalSyntheticLambda3
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return Long.valueOf(((Number) obj).longValue());
                }
            };
        } else if (this.type == Float.class || this.type == Float.TYPE) {
            this.readConversionFunction = new UnaryOperator() { // from class: com.opencsv.bean.ConverterNumber$$ExternalSyntheticLambda4
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return Float.valueOf(((Number) obj).floatValue());
                }
            };
        } else if (this.type == Double.class || this.type == Double.TYPE) {
            this.readConversionFunction = new UnaryOperator() { // from class: com.opencsv.bean.ConverterNumber$$ExternalSyntheticLambda5
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return Double.valueOf(((Number) obj).doubleValue());
                }
            };
        } else if (this.type == BigInteger.class) {
            this.readConversionFunction = new UnaryOperator() { // from class: com.opencsv.bean.ConverterNumber$$ExternalSyntheticLambda6
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return ((BigDecimal) ((Number) obj)).toBigInteger();
                }
            };
        } else {
            this.readConversionFunction = new UnaryOperator() { // from class: com.opencsv.bean.ConverterNumber$$ExternalSyntheticLambda7
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return ConverterNumber.lambda$new$1((Number) obj);
                }
            };
        }
        this.writeFormatter = createDecimalFormat(str4, this.writeLocale);
    }

    private DecimalFormat createDecimalFormat(String str, Locale locale) {
        NumberFormat numberFormat = NumberFormat.getInstance((Locale) ObjectUtils.defaultIfNull(locale, Locale.getDefault(Locale.Category.FORMAT)));
        if (!(numberFormat instanceof DecimalFormat)) {
            throw new CsvBadConverterException(ConverterNumber.class, ResourceBundle.getBundle(ICSVParser.DEFAULT_BUNDLE_NAME, this.errorLocale).getString("numberformat.not.decimalformat"));
        }
        DecimalFormat decimalFormat = (DecimalFormat) numberFormat;
        try {
            decimalFormat.applyLocalizedPattern(str);
            return decimalFormat;
        } catch (IllegalArgumentException e) {
            CsvBadConverterException csvBadConverterException = new CsvBadConverterException(ConverterNumber.class, String.format(ResourceBundle.getBundle(ICSVParser.DEFAULT_BUNDLE_NAME, this.errorLocale).getString("invalid.number.pattern"), str));
            csvBadConverterException.initCause(e);
            throw csvBadConverterException;
        }
    }

    @Override // com.opencsv.bean.CsvConverter
    public Object convertToRead(String str) throws CsvDataTypeMismatchException {
        Number number;
        if (!StringUtils.isNotEmpty(str)) {
            return null;
        }
        try {
            synchronized (this.readFormatter) {
                try {
                    number = this.readFormatter.parse(str);
                } catch (Throwable th) {
                    throw th;
                }
            }
            return (Number) this.readConversionFunction.apply(number);
        } catch (ParseException e) {
            CsvDataTypeMismatchException csvDataTypeMismatchException = new CsvDataTypeMismatchException(str, this.type, String.format(ResourceBundle.getBundle(ICSVParser.DEFAULT_BUNDLE_NAME, this.errorLocale).getString("unparsable.number"), str, this.readFormatter.toPattern()));
            csvDataTypeMismatchException.initCause(e);
            throw csvDataTypeMismatchException;
        }
    }

    @Override // com.opencsv.bean.AbstractCsvConverter, com.opencsv.bean.CsvConverter
    public String convertToWrite(Object obj) {
        String str;
        synchronized (this.writeFormatter) {
            if (obj != null) {
                try {
                    str = this.writeFormatter.format(obj);
                } catch (Throwable th) {
                    throw th;
                }
            } else {
                str = null;
            }
        }
        return str;
    }
}
