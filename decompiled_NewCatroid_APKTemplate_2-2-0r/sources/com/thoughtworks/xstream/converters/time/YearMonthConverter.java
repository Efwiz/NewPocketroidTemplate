package com.thoughtworks.xstream.converters.time;

import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter;
import java.time.YearMonth;
import java.time.format.DateTimeParseException;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/converters/time/YearMonthConverter.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class YearMonthConverter extends AbstractSingleValueConverter {
    @Override // com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter, com.thoughtworks.xstream.converters.ConverterMatcher
    public boolean canConvert(Class cls) {
        return YearMonth.class == cls;
    }

    @Override // com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter, com.thoughtworks.xstream.converters.SingleValueConverter
    public YearMonth fromString(String str) {
        try {
            return YearMonth.parse(str);
        } catch (DateTimeParseException e) {
            ConversionException conversionException = new ConversionException("Cannot parse value as year month", e);
            conversionException.add("value", str);
            throw conversionException;
        }
    }
}
