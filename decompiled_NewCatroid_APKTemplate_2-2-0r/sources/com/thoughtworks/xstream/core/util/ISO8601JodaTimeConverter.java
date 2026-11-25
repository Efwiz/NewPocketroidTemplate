package com.thoughtworks.xstream.core.util;

import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/core/util/ISO8601JodaTimeConverter.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class ISO8601JodaTimeConverter extends AbstractSingleValueConverter {
    private static final DateTimeFormatter[] formattersUTC = {ISODateTimeFormat.dateTime(), ISODateTimeFormat.dateTimeNoMillis(), ISODateTimeFormat.basicDateTime(), ISODateTimeFormat.basicDateTimeNoMillis(), ISODateTimeFormat.basicOrdinalDateTime(), ISODateTimeFormat.basicOrdinalDateTimeNoMillis(), ISODateTimeFormat.basicTime(), ISODateTimeFormat.basicTimeNoMillis(), ISODateTimeFormat.basicTTime(), ISODateTimeFormat.basicTTimeNoMillis(), ISODateTimeFormat.basicWeekDateTime(), ISODateTimeFormat.basicWeekDateTimeNoMillis(), ISODateTimeFormat.ordinalDateTime(), ISODateTimeFormat.ordinalDateTimeNoMillis(), ISODateTimeFormat.time(), ISODateTimeFormat.timeNoMillis(), ISODateTimeFormat.tTime(), ISODateTimeFormat.tTimeNoMillis(), ISODateTimeFormat.weekDateTime(), ISODateTimeFormat.weekDateTimeNoMillis()};
    private static final DateTimeFormatter[] formattersNoUTC = {ISODateTimeFormat.basicDate(), ISODateTimeFormat.basicOrdinalDate(), ISODateTimeFormat.basicWeekDate(), ISODateTimeFormat.date(), ISODateTimeFormat.dateHour(), ISODateTimeFormat.dateHourMinute(), ISODateTimeFormat.dateHourMinuteSecond(), ISODateTimeFormat.dateHourMinuteSecondFraction(), ISODateTimeFormat.dateHourMinuteSecondMillis(), ISODateTimeFormat.hour(), ISODateTimeFormat.hourMinute(), ISODateTimeFormat.hourMinuteSecond(), ISODateTimeFormat.hourMinuteSecondFraction(), ISODateTimeFormat.hourMinuteSecondMillis(), ISODateTimeFormat.ordinalDate(), ISODateTimeFormat.weekDate(), ISODateTimeFormat.year(), ISODateTimeFormat.yearMonth(), ISODateTimeFormat.yearMonthDay(), ISODateTimeFormat.weekyear(), ISODateTimeFormat.weekyearWeek(), ISODateTimeFormat.weekyearWeekDay()};

    @Override // com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter, com.thoughtworks.xstream.converters.ConverterMatcher
    public boolean canConvert(Class cls) {
        return false;
    }

    @Override // com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter, com.thoughtworks.xstream.converters.SingleValueConverter
    public Object fromString(String str) {
        int i = 0;
        int i2 = 0;
        while (true) {
            DateTimeFormatter[] dateTimeFormatterArr = formattersUTC;
            if (i2 < dateTimeFormatterArr.length) {
                try {
                    GregorianCalendar gregorianCalendar = dateTimeFormatterArr[i2].parseDateTime(str).toGregorianCalendar();
                    gregorianCalendar.setTimeZone(TimeZone.getDefault());
                    return gregorianCalendar;
                } catch (IllegalArgumentException unused) {
                    i2++;
                }
            } else {
                DateTimeZone dateTimeZoneForTimeZone = DateTimeZone.forTimeZone(TimeZone.getDefault());
                while (true) {
                    DateTimeFormatter[] dateTimeFormatterArr2 = formattersNoUTC;
                    if (i < dateTimeFormatterArr2.length) {
                        try {
                            GregorianCalendar gregorianCalendar2 = dateTimeFormatterArr2[i].withZone(dateTimeZoneForTimeZone).parseDateTime(str).toGregorianCalendar();
                            gregorianCalendar2.setTimeZone(TimeZone.getDefault());
                            return gregorianCalendar2;
                        } catch (IllegalArgumentException unused2) {
                            i++;
                        }
                    } else {
                        ConversionException conversionException = new ConversionException("Cannot parse date");
                        conversionException.add(SchemaSymbols.ATTVAL_DATE, str);
                        throw conversionException;
                    }
                }
            }
        }
    }

    @Override // com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter, com.thoughtworks.xstream.converters.SingleValueConverter
    public String toString(Object obj) {
        return new DateTime(obj).toString(formattersUTC[0]);
    }
}
