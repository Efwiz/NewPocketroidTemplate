package com.thoughtworks.xstream.converters.time;

import java.time.chrono.ChronoLocalDate;
import java.time.chrono.Chronology;
import java.time.chrono.HijrahChronology;
import java.time.chrono.HijrahDate;
import java.time.chrono.HijrahEra;
import java.util.HashSet;
import java.util.Set;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/converters/time/HijrahDateConverter.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class HijrahDateConverter extends AbstractChronoLocalDateConverter<HijrahEra> {
    private final Set<Chronology> hijrahChronologies = new HashSet();

    public HijrahDateConverter() {
        for (Chronology chronology : Chronology.getAvailableChronologies()) {
            if (chronology instanceof HijrahChronology) {
                this.hijrahChronologies.add(chronology);
            }
        }
    }

    @Override // com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter, com.thoughtworks.xstream.converters.ConverterMatcher
    public boolean canConvert(Class cls) {
        return HijrahDate.class == cls;
    }

    @Override // com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter, com.thoughtworks.xstream.converters.SingleValueConverter
    public Object fromString(String str) {
        return parseChronoLocalDate(str, "Hijrah", this.hijrahChronologies);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.thoughtworks.xstream.converters.time.AbstractChronoLocalDateConverter
    public ChronoLocalDate chronoLocalDateOf(HijrahEra hijrahEra, int i, int i2, int i3) {
        if (hijrahEra != null) {
            return HijrahDate.of(i, i2, i3);
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.thoughtworks.xstream.converters.time.AbstractChronoLocalDateConverter
    public HijrahEra eraOf(String str) {
        return HijrahEra.valueOf(str);
    }
}
