package com.thoughtworks.xstream.converters.time;

import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.converters.SingleValueConverter;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/converters/time/OffsetDateTimeConverter.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class OffsetDateTimeConverter implements SingleValueConverter {
    private static final DateTimeFormatter FORMATTER = new DateTimeFormatterBuilder().appendPattern("uuuu-MM-dd'T'HH:mm:ss").appendFraction(ChronoField.NANO_OF_SECOND, 0, 9, true).appendOffsetId().toFormatter();

    @Override // com.thoughtworks.xstream.converters.ConverterMatcher
    public boolean canConvert(Class cls) {
        return OffsetDateTime.class == cls;
    }

    @Override // com.thoughtworks.xstream.converters.SingleValueConverter
    public Object fromString(String str) {
        try {
            return OffsetDateTime.parse(str);
        } catch (DateTimeParseException e) {
            ConversionException conversionException = new ConversionException("Cannot parse value as offset date time", e);
            conversionException.add("value", str);
            throw conversionException;
        }
    }

    @Override // com.thoughtworks.xstream.converters.SingleValueConverter
    public String toString(Object obj) {
        if (obj == null) {
            return null;
        }
        return FORMATTER.format((OffsetDateTime) obj);
    }
}
