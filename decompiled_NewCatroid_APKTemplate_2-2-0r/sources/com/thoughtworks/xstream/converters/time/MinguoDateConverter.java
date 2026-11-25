package com.thoughtworks.xstream.converters.time;

import java.time.chrono.ChronoLocalDate;
import java.time.chrono.MinguoChronology;
import java.time.chrono.MinguoDate;
import java.time.chrono.MinguoEra;
import java.util.Collections;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/converters/time/MinguoDateConverter.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class MinguoDateConverter extends AbstractChronoLocalDateConverter<MinguoEra> {
    @Override // com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter, com.thoughtworks.xstream.converters.ConverterMatcher
    public boolean canConvert(Class cls) {
        return MinguoDate.class == cls;
    }

    @Override // com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter, com.thoughtworks.xstream.converters.SingleValueConverter
    public Object fromString(String str) {
        return parseChronoLocalDate(str, "Minguo", Collections.singleton(MinguoChronology.INSTANCE));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.thoughtworks.xstream.converters.time.AbstractChronoLocalDateConverter
    public ChronoLocalDate chronoLocalDateOf(MinguoEra minguoEra, int i, int i2, int i3) {
        return MinguoDate.of(i, i2, i3);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.thoughtworks.xstream.converters.time.AbstractChronoLocalDateConverter
    public MinguoEra eraOf(String str) {
        return MinguoEra.valueOf(str);
    }
}
