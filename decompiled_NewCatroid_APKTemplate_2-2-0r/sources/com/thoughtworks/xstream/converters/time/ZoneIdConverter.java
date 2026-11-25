package com.thoughtworks.xstream.converters.time;

import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.converters.SingleValueConverter;
import java.time.DateTimeException;
import java.time.ZoneId;
import java.time.zone.ZoneRulesException;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/converters/time/ZoneIdConverter.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class ZoneIdConverter implements SingleValueConverter {
    @Override // com.thoughtworks.xstream.converters.ConverterMatcher
    public boolean canConvert(Class cls) {
        return cls != null && ZoneId.class.isAssignableFrom(cls);
    }

    @Override // com.thoughtworks.xstream.converters.SingleValueConverter
    public ZoneId fromString(String str) {
        ConversionException conversionException;
        try {
            return ZoneId.of(str);
        } catch (ZoneRulesException e) {
            conversionException = new ConversionException("Not a valid zone id", e);
            conversionException.add("value", str);
            throw conversionException;
        } catch (DateTimeException e2) {
            conversionException = new ConversionException("Cannot parse value as zone id", e2);
            conversionException.add("value", str);
            throw conversionException;
        }
    }

    @Override // com.thoughtworks.xstream.converters.SingleValueConverter
    public String toString(Object obj) {
        if (obj == null) {
            return null;
        }
        return ((ZoneId) obj).getId();
    }
}
