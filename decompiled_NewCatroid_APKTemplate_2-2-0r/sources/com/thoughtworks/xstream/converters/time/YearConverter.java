package com.thoughtworks.xstream.converters.time;

import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter;
import java.time.Year;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/converters/time/YearConverter.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class YearConverter extends AbstractSingleValueConverter {
    @Override // com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter, com.thoughtworks.xstream.converters.ConverterMatcher
    public boolean canConvert(Class cls) {
        return Year.class == cls;
    }

    @Override // com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter, com.thoughtworks.xstream.converters.SingleValueConverter
    public Year fromString(String str) {
        try {
            return Year.of(Integer.parseInt(str));
        } catch (NumberFormatException e) {
            ConversionException conversionException = new ConversionException("Cannot parse value as year", e);
            conversionException.add("value", str);
            throw conversionException;
        }
    }
}
