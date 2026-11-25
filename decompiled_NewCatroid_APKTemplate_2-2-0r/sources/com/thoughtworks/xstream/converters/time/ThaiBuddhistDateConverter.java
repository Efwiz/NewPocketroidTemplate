package com.thoughtworks.xstream.converters.time;

import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ThaiBuddhistChronology;
import java.time.chrono.ThaiBuddhistDate;
import java.time.chrono.ThaiBuddhistEra;
import java.util.Collections;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/converters/time/ThaiBuddhistDateConverter.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class ThaiBuddhistDateConverter extends AbstractChronoLocalDateConverter<ThaiBuddhistEra> {
    @Override // com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter, com.thoughtworks.xstream.converters.ConverterMatcher
    public boolean canConvert(Class cls) {
        return ThaiBuddhistDate.class == cls;
    }

    @Override // com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter, com.thoughtworks.xstream.converters.SingleValueConverter
    public Object fromString(String str) {
        return parseChronoLocalDate(str, "Thai Buddhist", Collections.singleton(ThaiBuddhistChronology.INSTANCE));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.thoughtworks.xstream.converters.time.AbstractChronoLocalDateConverter
    public ChronoLocalDate chronoLocalDateOf(ThaiBuddhistEra thaiBuddhistEra, int i, int i2, int i3) {
        return ThaiBuddhistDate.of(i, i2, i3);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.thoughtworks.xstream.converters.time.AbstractChronoLocalDateConverter
    public ThaiBuddhistEra eraOf(String str) {
        return ThaiBuddhistEra.valueOf(str);
    }
}
