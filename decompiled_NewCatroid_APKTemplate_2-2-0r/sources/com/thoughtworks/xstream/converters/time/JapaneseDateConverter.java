package com.thoughtworks.xstream.converters.time;

import java.time.chrono.ChronoLocalDate;
import java.time.chrono.JapaneseChronology;
import java.time.chrono.JapaneseDate;
import java.time.chrono.JapaneseEra;
import java.util.Collections;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/converters/time/JapaneseDateConverter.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class JapaneseDateConverter extends AbstractChronoLocalDateConverter<JapaneseEra> {
    @Override // com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter, com.thoughtworks.xstream.converters.ConverterMatcher
    public boolean canConvert(Class cls) {
        return JapaneseDate.class == cls;
    }

    @Override // com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter, com.thoughtworks.xstream.converters.SingleValueConverter
    public Object fromString(String str) {
        return parseChronoLocalDate(str, "Japanese", Collections.singleton(JapaneseChronology.INSTANCE));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.thoughtworks.xstream.converters.time.AbstractChronoLocalDateConverter
    public ChronoLocalDate chronoLocalDateOf(JapaneseEra japaneseEra, int i, int i2, int i3) {
        return JapaneseDate.of(japaneseEra, i, i2, i3);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.thoughtworks.xstream.converters.time.AbstractChronoLocalDateConverter
    public JapaneseEra eraOf(String str) {
        return JapaneseEra.valueOf(str);
    }
}
