package com.thoughtworks.xstream.converters.time;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter;
import java.time.Period;
import java.time.format.DateTimeParseException;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/converters/time/PeriodConverter.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class PeriodConverter extends AbstractSingleValueConverter {
    @Override // com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter, com.thoughtworks.xstream.converters.ConverterMatcher
    public boolean canConvert(Class cls) {
        return Period.class == cls;
    }

    @Override // com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter, com.thoughtworks.xstream.converters.SingleValueConverter
    public Period fromString(String str) {
        try {
            return Period.parse(str);
        } catch (DateTimeParseException e) {
            ConversionException conversionException = new ConversionException("Cannot parse period value", e);
            conversionException.add(TypedValues.CycleType.S_WAVE_PERIOD, str);
            throw conversionException;
        }
    }
}
