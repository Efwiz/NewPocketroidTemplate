package com.thoughtworks.xstream.core.util;

import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.MonthDay;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.Year;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.time.temporal.IsoFields;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalQuery;
import java.time.temporal.WeekFields;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import org.apache.xerces.impl.xs.SchemaSymbols;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/core/util/ISO8601JavaTimeConverter.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class ISO8601JavaTimeConverter extends AbstractSingleValueConverter {
    private static final DateTimeFormatter STD_DATE_TIME = new DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd'T'HH:mm:ss").appendFraction(ChronoField.NANO_OF_SECOND, 3, 9, true).appendOffsetId().toFormatter();
    private static final DateTimeFormatter STD_ORDINAL_DATE_TIME = new DateTimeFormatterBuilder().appendPattern("yyyy-DDD'T'HH:mm:ss").appendFraction(ChronoField.MILLI_OF_SECOND, 0, 3, true).appendOffsetId().toFormatter();
    private static final DateTimeFormatter BASIC_DATE_TIME = new DateTimeFormatterBuilder().appendPattern("yyyyMMdd'T'HHmmss").appendFraction(ChronoField.MILLI_OF_SECOND, 0, 3, true).appendOffsetId().toFormatter();
    private static final DateTimeFormatter BASIC_ORDINAL_DATE_TIME = new DateTimeFormatterBuilder().appendPattern("yyyyDDD'T'HHmmss").appendFraction(ChronoField.MILLI_OF_SECOND, 0, 3, true).appendOffsetId().toFormatter();
    private static final DateTimeFormatter BASIC_TIME = new DateTimeFormatterBuilder().appendPattern("HHmmss").appendFraction(ChronoField.MILLI_OF_SECOND, 0, 3, true).appendOffsetId().toFormatter();
    private static final DateTimeFormatter ISO_TTIME = new DateTimeFormatterBuilder().appendPattern("'T'HH:mm:ss").appendFraction(ChronoField.MILLI_OF_SECOND, 0, 3, true).appendOffsetId().toFormatter();
    private static final DateTimeFormatter BASIC_TTIME = new DateTimeFormatterBuilder().appendPattern("'T'HHmmss").appendFraction(ChronoField.MILLI_OF_SECOND, 0, 3, true).appendOffsetId().toFormatter();
    private static final DateTimeFormatter ISO_WEEK_DATE_TIME = new DateTimeFormatterBuilder().appendPattern("YYYY-'W'ww-e'T'HH:mm:ss").appendFraction(ChronoField.MILLI_OF_SECOND, 0, 3, true).appendOffsetId().toFormatter();
    private static final DateTimeFormatter BASIC_WEEK_DATE_TIME = new DateTimeFormatterBuilder().appendPattern("YYYY'W'wwe'T'HHmmss").appendFraction(ChronoField.MILLI_OF_SECOND, 0, 3, true).appendOffsetId().toFormatter();
    private static final DateTimeFormatter BASIC_ORDINAL_DATE = new DateTimeFormatterBuilder().appendPattern("yyyyDDD").toFormatter();
    private static final DateTimeFormatter BASIC_WEEK_DATE = new DateTimeFormatterBuilder().appendPattern("YYYY'W'wwe").toFormatter();
    private static final DateTimeFormatter STD_DATE_HOUR = new DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd'T'HH").toFormatter();
    private static final DateTimeFormatter STD_HOUR = new DateTimeFormatterBuilder().appendPattern("HH").toFormatter();
    private static final DateTimeFormatter STD_YEAR_WEEK = new DateTimeFormatterBuilder().appendPattern("YYYY-'W'ww").parseDefaulting(ChronoField.ALIGNED_DAY_OF_WEEK_IN_YEAR, 1).toFormatter();

    @Override // com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter, com.thoughtworks.xstream.converters.ConverterMatcher
    public boolean canConvert(Class cls) {
        return false;
    }

    /* JADX WARN: Type inference failed for: r0v14, types: [java.time.ZonedDateTime] */
    /* JADX WARN: Type inference failed for: r0v30, types: [java.time.ZonedDateTime] */
    /* JADX WARN: Type inference failed for: r0v9, types: [java.time.ZonedDateTime] */
    /* JADX WARN: Type inference failed for: r2v13, types: [java.time.ZonedDateTime] */
    /* JADX WARN: Type inference failed for: r2v9, types: [java.time.ZonedDateTime] */
    @Override // com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter, com.thoughtworks.xstream.converters.SingleValueConverter
    public Object fromString(String str) {
        try {
            try {
                try {
                    try {
                        try {
                            try {
                                return GregorianCalendar.from(OffsetDateTime.parse(str).atZoneSameInstant(ZoneId.systemDefault()));
                            } catch (DateTimeParseException unused) {
                                try {
                                    try {
                                        try {
                                            try {
                                                try {
                                                    try {
                                                        try {
                                                            try {
                                                                try {
                                                                    try {
                                                                        try {
                                                                            try {
                                                                                try {
                                                                                    try {
                                                                                        try {
                                                                                            return GregorianCalendar.from(OffsetTime.parse(str).atDate(LocalDate.ofEpochDay(0L)).atZoneSameInstant(ZoneId.systemDefault()));
                                                                                        } catch (DateTimeParseException unused2) {
                                                                                            return GregorianCalendar.from(((OffsetTime) BASIC_TIME.parse(str, new TemporalQuery() { // from class: com.thoughtworks.xstream.core.util.ISO8601JavaTimeConverter$$ExternalSyntheticLambda1
                                                                                                @Override // java.time.temporal.TemporalQuery
                                                                                                public final Object queryFrom(TemporalAccessor temporalAccessor) {
                                                                                                    return OffsetTime.from(temporalAccessor);
                                                                                                }
                                                                                            })).atDate(LocalDate.ofEpochDay(0L)).atZoneSameInstant(ZoneId.systemDefault()));
                                                                                        }
                                                                                    } catch (DateTimeParseException unused3) {
                                                                                        return GregorianCalendar.from(((OffsetTime) ISO_TTIME.parse(str, new TemporalQuery() { // from class: com.thoughtworks.xstream.core.util.ISO8601JavaTimeConverter$$ExternalSyntheticLambda1
                                                                                            @Override // java.time.temporal.TemporalQuery
                                                                                            public final Object queryFrom(TemporalAccessor temporalAccessor) {
                                                                                                return OffsetTime.from(temporalAccessor);
                                                                                            }
                                                                                        })).atDate(LocalDate.ofEpochDay(0L)).atZoneSameInstant(ZoneId.systemDefault()));
                                                                                    }
                                                                                } catch (DateTimeParseException unused4) {
                                                                                    return GregorianCalendar.from(((LocalDate) BASIC_ORDINAL_DATE.parse(str, new TemporalQuery() { // from class: com.thoughtworks.xstream.core.util.ISO8601JavaTimeConverter$$ExternalSyntheticLambda2
                                                                                        @Override // java.time.temporal.TemporalQuery
                                                                                        public final Object queryFrom(TemporalAccessor temporalAccessor) {
                                                                                            return LocalDate.from(temporalAccessor);
                                                                                        }
                                                                                    })).atStartOfDay(ZoneId.systemDefault()));
                                                                                }
                                                                            } catch (DateTimeParseException unused5) {
                                                                                return GregorianCalendar.from(LocalDate.parse(str, DateTimeFormatter.ISO_ORDINAL_DATE).atStartOfDay(ZoneId.systemDefault()));
                                                                            }
                                                                        } catch (DateTimeParseException unused6) {
                                                                            return GregorianCalendar.from(LocalDate.parse(str).atStartOfDay(ZoneId.systemDefault()));
                                                                        }
                                                                    } catch (DateTimeParseException unused7) {
                                                                        return GregorianCalendar.from(((OffsetTime) BASIC_TTIME.parse(str, new TemporalQuery() { // from class: com.thoughtworks.xstream.core.util.ISO8601JavaTimeConverter$$ExternalSyntheticLambda1
                                                                            @Override // java.time.temporal.TemporalQuery
                                                                            public final Object queryFrom(TemporalAccessor temporalAccessor) {
                                                                                return OffsetTime.from(temporalAccessor);
                                                                            }
                                                                        })).atDate(LocalDate.ofEpochDay(0L)).atZoneSameInstant(ZoneId.systemDefault()));
                                                                    }
                                                                } catch (DateTimeParseException unused8) {
                                                                    TemporalAccessor temporalAccessor = ISO_WEEK_DATE_TIME.withLocale(Locale.getDefault()).parse(str);
                                                                    return GregorianCalendar.from(OffsetTime.from(temporalAccessor).atDate(Year.from(temporalAccessor).atMonthDay(MonthDay.from(temporalAccessor))).atZoneSameInstant(ZoneId.systemDefault()));
                                                                }
                                                            } catch (DateTimeParseException unused9) {
                                                                TemporalAccessor temporalAccessor2 = BASIC_WEEK_DATE.withLocale(Locale.getDefault()).parse(str);
                                                                return GregorianCalendar.from(Year.from(temporalAccessor2).atMonthDay(MonthDay.from(temporalAccessor2)).atStartOfDay(ZoneId.systemDefault()));
                                                            }
                                                        } catch (DateTimeParseException unused10) {
                                                            return GregorianCalendar.from(((LocalTime) STD_HOUR.parse(str, new TemporalQuery() { // from class: com.thoughtworks.xstream.core.util.ISO8601JavaTimeConverter$$ExternalSyntheticLambda4
                                                                @Override // java.time.temporal.TemporalQuery
                                                                public final Object queryFrom(TemporalAccessor temporalAccessor3) {
                                                                    return LocalTime.from(temporalAccessor3);
                                                                }
                                                            })).atDate(LocalDate.ofEpochDay(0L)).atZone(ZoneId.systemDefault()));
                                                        }
                                                    } catch (DateTimeParseException unused11) {
                                                        try {
                                                            try {
                                                                try {
                                                                    return GregorianCalendar.from(YearMonth.parse(str).atDay(1).atStartOfDay(ZoneId.systemDefault()));
                                                                } catch (DateTimeParseException unused12) {
                                                                    return GregorianCalendar.from(Year.parse(str).atDay(1).atStartOfDay(ZoneId.systemDefault()));
                                                                }
                                                            } catch (DateTimeParseException unused13) {
                                                                ConversionException conversionException = new ConversionException("Cannot parse date");
                                                                conversionException.add(SchemaSymbols.ATTVAL_DATE, str);
                                                                throw conversionException;
                                                            }
                                                        } catch (DateTimeParseException unused14) {
                                                            TemporalAccessor temporalAccessor3 = STD_YEAR_WEEK.withLocale(Locale.getDefault()).parse(str);
                                                            return GregorianCalendar.from(LocalDateTime.from(temporalAccessor3).with(WeekFields.ISO.weekOfYear(), temporalAccessor3.get(WeekFields.ISO.weekBasedYear())).with(WeekFields.ISO.weekOfWeekBasedYear(), temporalAccessor3.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR)).atZone(ZoneId.systemDefault()));
                                                        }
                                                    }
                                                } catch (DateTimeParseException unused15) {
                                                    return GregorianCalendar.from(((LocalDateTime) STD_DATE_HOUR.parse(str, new TemporalQuery() { // from class: com.thoughtworks.xstream.core.util.ISO8601JavaTimeConverter$$ExternalSyntheticLambda3
                                                        @Override // java.time.temporal.TemporalQuery
                                                        public final Object queryFrom(TemporalAccessor temporalAccessor4) {
                                                            return LocalDateTime.from(temporalAccessor4);
                                                        }
                                                    })).atZone(ZoneId.systemDefault()));
                                                }
                                            } catch (DateTimeParseException unused16) {
                                                return GregorianCalendar.from(LocalTime.parse(str).atDate(LocalDate.ofEpochDay(0L)).atZone(ZoneId.systemDefault()));
                                            }
                                        } catch (DateTimeParseException unused17) {
                                            return GregorianCalendar.from(LocalDate.parse(str, DateTimeFormatter.ISO_WEEK_DATE.withLocale(Locale.getDefault())).atStartOfDay(ZoneId.systemDefault()));
                                        }
                                    } catch (DateTimeParseException unused18) {
                                        return GregorianCalendar.from(LocalDate.parse(str, DateTimeFormatter.BASIC_ISO_DATE).atStartOfDay(ZoneId.systemDefault()));
                                    }
                                } catch (DateTimeParseException unused19) {
                                    TemporalAccessor temporalAccessor4 = BASIC_WEEK_DATE_TIME.withLocale(Locale.getDefault()).parse(str);
                                    return GregorianCalendar.from(OffsetTime.from(temporalAccessor4).atDate(Year.from(temporalAccessor4).atMonthDay(MonthDay.from(temporalAccessor4))).atZoneSameInstant(ZoneId.systemDefault()));
                                }
                            }
                        } catch (DateTimeParseException unused20) {
                            return GregorianCalendar.from(Instant.parse(str).atZone(ZoneId.systemDefault()));
                        }
                    } catch (DateTimeParseException unused21) {
                        return GregorianCalendar.from(((OffsetDateTime) STD_ORDINAL_DATE_TIME.parse(str, new TemporalQuery() { // from class: com.thoughtworks.xstream.core.util.ISO8601JavaTimeConverter$$ExternalSyntheticLambda0
                            @Override // java.time.temporal.TemporalQuery
                            public final Object queryFrom(TemporalAccessor temporalAccessor5) {
                                return OffsetDateTime.from(temporalAccessor5);
                            }
                        })).atZoneSameInstant(ZoneId.systemDefault()));
                    }
                } catch (DateTimeParseException unused22) {
                    return GregorianCalendar.from(((OffsetDateTime) BASIC_ORDINAL_DATE_TIME.parse(str, new TemporalQuery() { // from class: com.thoughtworks.xstream.core.util.ISO8601JavaTimeConverter$$ExternalSyntheticLambda0
                        @Override // java.time.temporal.TemporalQuery
                        public final Object queryFrom(TemporalAccessor temporalAccessor5) {
                            return OffsetDateTime.from(temporalAccessor5);
                        }
                    })).atZoneSameInstant(ZoneId.systemDefault()));
                }
            } catch (DateTimeParseException unused23) {
                return GregorianCalendar.from(((OffsetDateTime) BASIC_DATE_TIME.parse(str, new TemporalQuery() { // from class: com.thoughtworks.xstream.core.util.ISO8601JavaTimeConverter$$ExternalSyntheticLambda0
                    @Override // java.time.temporal.TemporalQuery
                    public final Object queryFrom(TemporalAccessor temporalAccessor5) {
                        return OffsetDateTime.from(temporalAccessor5);
                    }
                })).atZoneSameInstant(ZoneId.systemDefault()));
            }
        } catch (DateTimeParseException unused24) {
            return GregorianCalendar.from(LocalDateTime.parse(str).atZone(ZoneId.systemDefault()));
        }
    }

    @Override // com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter, com.thoughtworks.xstream.converters.SingleValueConverter
    public String toString(Object obj) {
        Calendar calendar = (Calendar) obj;
        return STD_DATE_TIME.format(OffsetDateTime.ofInstant(Instant.ofEpochMilli(calendar.getTimeInMillis()), ZoneOffset.ofTotalSeconds(calendar.getTimeZone().getOffset(calendar.getTimeInMillis()) / 1000)));
    }
}
