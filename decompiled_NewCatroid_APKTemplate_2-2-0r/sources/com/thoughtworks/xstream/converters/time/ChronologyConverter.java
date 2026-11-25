package com.thoughtworks.xstream.converters.time;

import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.converters.SingleValueConverter;
import java.time.DateTimeException;
import java.time.chrono.Chronology;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/converters/time/ChronologyConverter.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class ChronologyConverter implements SingleValueConverter {
    @Override // com.thoughtworks.xstream.converters.ConverterMatcher
    public boolean canConvert(Class cls) {
        return cls != null && Chronology.class.isAssignableFrom(cls);
    }

    @Override // com.thoughtworks.xstream.converters.SingleValueConverter
    public Chronology fromString(String str) {
        if (str == null) {
            return null;
        }
        try {
            return Chronology.of(str);
        } catch (DateTimeException e) {
            ConversionException conversionException = new ConversionException("Cannot parse value as chronology", e);
            conversionException.add("value", str);
            throw conversionException;
        }
    }

    @Override // com.thoughtworks.xstream.converters.SingleValueConverter
    public String toString(Object obj) {
        if (obj == null) {
            return null;
        }
        return ((Chronology) obj).getId();
    }
}
