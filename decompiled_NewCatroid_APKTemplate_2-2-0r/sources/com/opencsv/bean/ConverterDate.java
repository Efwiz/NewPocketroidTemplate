package com.opencsv.bean;

import com.opencsv.ICSVParser;
import com.opencsv.exceptions.CsvBadConverterException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.MonthDay;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.Year;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoLocalDateTime;
import java.time.chrono.ChronoZonedDateTime;
import java.time.chrono.Chronology;
import java.time.chrono.Era;
import java.time.chrono.HijrahEra;
import java.time.chrono.IsoEra;
import java.time.chrono.JapaneseEra;
import java.time.chrono.MinguoEra;
import java.time.chrono.ThaiBuddhistEra;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalQuery;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.function.BiFunction;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes10.dex */
public class ConverterDate extends AbstractCsvConverter {
    private static final String CSVDATE_NOT_DATE = "csvdate.not.date";
    private final DateTimeFormatter readDtf;
    private final SimpleDateFormat readSdf;
    private final BiFunction<DateTimeFormatter, String, TemporalAccessor> readTemporalConversionFunction;
    private final DateTimeFormatter writeDtf;
    private final SimpleDateFormat writeSdf;
    private final BiFunction<DateTimeFormatter, TemporalAccessor, String> writeTemporalConversionFunction;

    public ConverterDate(Class<?> cls, String str, String str2, Locale locale, String str3, String str4, String str5, String str6) {
        super(cls, str, str2, locale);
        Chronology chronology = getChronology(str5, this.locale);
        Chronology chronology2 = getChronology(str6, this.writeLocale);
        try {
            if (TemporalAccessor.class.isAssignableFrom(cls)) {
                this.readSdf = null;
                this.readDtf = setDateTimeFormatter(str3, this.locale).withChronology(chronology);
                this.readTemporalConversionFunction = determineReadTemporalConversionFunction(cls);
            } else {
                this.readDtf = null;
                this.readTemporalConversionFunction = null;
                this.readSdf = setDateFormat(str3, this.locale);
            }
            try {
                if (TemporalAccessor.class.isAssignableFrom(cls)) {
                    this.writeSdf = null;
                    this.writeDtf = setDateTimeFormatter(str4, this.writeLocale).withChronology(chronology2);
                    this.writeTemporalConversionFunction = determineWriteTemporalConversionFunction(cls);
                } else {
                    this.writeDtf = null;
                    this.writeTemporalConversionFunction = null;
                    this.writeSdf = setDateFormat(str4, this.writeLocale);
                }
            } catch (IllegalArgumentException e) {
                CsvBadConverterException csvBadConverterException = new CsvBadConverterException(getClass(), String.format(ResourceBundle.getBundle(ICSVParser.DEFAULT_BUNDLE_NAME, this.errorLocale).getString("invalid.date.format.string"), str4));
                csvBadConverterException.initCause(e);
                throw csvBadConverterException;
            }
        } catch (IllegalArgumentException e2) {
            CsvBadConverterException csvBadConverterException2 = new CsvBadConverterException(getClass(), String.format(ResourceBundle.getBundle(ICSVParser.DEFAULT_BUNDLE_NAME, this.errorLocale).getString("invalid.date.format.string"), str3));
            csvBadConverterException2.initCause(e2);
            throw csvBadConverterException2;
        }
    }

    private BiFunction<DateTimeFormatter, TemporalAccessor, String> determineWriteTemporalConversionFunction(Class<?> cls) {
        if (Instant.class.equals(cls)) {
            return new BiFunction() { // from class: com.opencsv.bean.ConverterDate$$ExternalSyntheticLambda7
                @Override // java.util.function.BiFunction
                public final Object apply(Object obj, Object obj2) {
                    return ((DateTimeFormatter) obj).format(LocalDateTime.ofInstant((Instant) ((TemporalAccessor) obj2), ZoneId.of("UTC")));
                }
            };
        }
        return new BiFunction() { // from class: com.opencsv.bean.ConverterDate$$ExternalSyntheticLambda8
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return ((DateTimeFormatter) obj).format((TemporalAccessor) obj2);
            }
        };
    }

    private BiFunction<DateTimeFormatter, String, TemporalAccessor> determineReadTemporalConversionFunction(Class<?> cls) {
        if (TemporalAccessor.class.equals(cls)) {
            return new BiFunction() { // from class: com.opencsv.bean.ConverterDate$$ExternalSyntheticLambda13
                @Override // java.util.function.BiFunction
                public final Object apply(Object obj, Object obj2) {
                    return ((DateTimeFormatter) obj).parse((String) obj2);
                }
            };
        }
        if (ChronoLocalDateTime.class.equals(cls) || LocalDateTime.class.equals(cls)) {
            return new BiFunction() { // from class: com.opencsv.bean.ConverterDate$$ExternalSyntheticLambda25
                @Override // java.util.function.BiFunction
                public final Object apply(Object obj, Object obj2) {
                    return ConverterDate.lambda$determineReadTemporalConversionFunction$2((DateTimeFormatter) obj, (String) obj2);
                }
            };
        }
        if (ChronoZonedDateTime.class.equals(cls) || ZonedDateTime.class.equals(cls)) {
            return new BiFunction() { // from class: com.opencsv.bean.ConverterDate$$ExternalSyntheticLambda26
                @Override // java.util.function.BiFunction
                public final Object apply(Object obj, Object obj2) {
                    return ConverterDate.lambda$determineReadTemporalConversionFunction$3((DateTimeFormatter) obj, (String) obj2);
                }
            };
        }
        if (Temporal.class.equals(cls)) {
            return new BiFunction() { // from class: com.opencsv.bean.ConverterDate$$ExternalSyntheticLambda27
                @Override // java.util.function.BiFunction
                public final Object apply(Object obj, Object obj2) {
                    return ((DateTimeFormatter) obj).parseBest((String) obj2, new TemporalQuery() { // from class: com.opencsv.bean.ConverterDate$$ExternalSyntheticLambda40
                        @Override // java.time.temporal.TemporalQuery
                        public final Object queryFrom(TemporalAccessor temporalAccessor) {
                            return ZonedDateTime.from(temporalAccessor);
                        }
                    }, new TemporalQuery() { // from class: com.opencsv.bean.ConverterDate$$ExternalSyntheticLambda41
                        @Override // java.time.temporal.TemporalQuery
                        public final Object queryFrom(TemporalAccessor temporalAccessor) {
                            return OffsetDateTime.from(temporalAccessor);
                        }
                    }, new TemporalQuery() { // from class: com.opencsv.bean.ConverterDate$$ExternalSyntheticLambda1
                        @Override // java.time.temporal.TemporalQuery
                        public final Object queryFrom(TemporalAccessor temporalAccessor) {
                            return Instant.from(temporalAccessor);
                        }
                    }, new TemporalQuery() { // from class: com.opencsv.bean.ConverterDate$$ExternalSyntheticLambda2
                        @Override // java.time.temporal.TemporalQuery
                        public final Object queryFrom(TemporalAccessor temporalAccessor) {
                            return LocalDateTime.from(temporalAccessor);
                        }
                    }, new TemporalQuery() { // from class: com.opencsv.bean.ConverterDate$$ExternalSyntheticLambda3
                        @Override // java.time.temporal.TemporalQuery
                        public final Object queryFrom(TemporalAccessor temporalAccessor) {
                            return LocalDate.from(temporalAccessor);
                        }
                    }, new TemporalQuery() { // from class: com.opencsv.bean.ConverterDate$$ExternalSyntheticLambda4
                        @Override // java.time.temporal.TemporalQuery
                        public final Object queryFrom(TemporalAccessor temporalAccessor) {
                            return OffsetTime.from(temporalAccessor);
                        }
                    }, new TemporalQuery() { // from class: com.opencsv.bean.ConverterDate$$ExternalSyntheticLambda5
                        @Override // java.time.temporal.TemporalQuery
                        public final Object queryFrom(TemporalAccessor temporalAccessor) {
                            return LocalTime.from(temporalAccessor);
                        }
                    });
                }
            };
        }
        if (Era.class.equals(cls) || IsoEra.class.equals(cls)) {
            return new BiFunction() { // from class: com.opencsv.bean.ConverterDate$$ExternalSyntheticLambda28
                @Override // java.util.function.BiFunction
                public final Object apply(Object obj, Object obj2) {
                    return IsoEra.of(((DateTimeFormatter) obj).parse((String) obj2).get(ChronoField.ERA));
                }
            };
        }
        if (DayOfWeek.class.equals(cls)) {
            return new BiFunction() { // from class: com.opencsv.bean.ConverterDate$$ExternalSyntheticLambda29
                @Override // java.util.function.BiFunction
                public final Object apply(Object obj, Object obj2) {
                    return ConverterDate.lambda$determineReadTemporalConversionFunction$6((DateTimeFormatter) obj, (String) obj2);
                }
            };
        }
        if (HijrahEra.class.equals(cls)) {
            return new BiFunction() { // from class: com.opencsv.bean.ConverterDate$$ExternalSyntheticLambda30
                @Override // java.util.function.BiFunction
                public final Object apply(Object obj, Object obj2) {
                    return HijrahEra.of(((DateTimeFormatter) obj).parse((String) obj2).get(ChronoField.ERA));
                }
            };
        }
        if (Instant.class.equals(cls)) {
            return new BiFunction() { // from class: com.opencsv.bean.ConverterDate$$ExternalSyntheticLambda31
                @Override // java.util.function.BiFunction
                public final Object apply(Object obj, Object obj2) {
                    return ConverterDate.lambda$determineReadTemporalConversionFunction$8((DateTimeFormatter) obj, (String) obj2);
                }
            };
        }
        if (ChronoLocalDate.class.isAssignableFrom(cls)) {
            return new BiFunction() { // from class: com.opencsv.bean.ConverterDate$$ExternalSyntheticLambda32
                @Override // java.util.function.BiFunction
                public final Object apply(Object obj, Object obj2) {
                    return ConverterDate.lambda$determineReadTemporalConversionFunction$9((DateTimeFormatter) obj, (String) obj2);
                }
            };
        }
        if (JapaneseEra.class.equals(cls)) {
            return new BiFunction() { // from class: com.opencsv.bean.ConverterDate$$ExternalSyntheticLambda34
                @Override // java.util.function.BiFunction
                public final Object apply(Object obj, Object obj2) {
                    return JapaneseEra.of(((DateTimeFormatter) obj).parse((String) obj2).get(ChronoField.ERA));
                }
            };
        }
        if (LocalTime.class.equals(cls)) {
            return new BiFunction() { // from class: com.opencsv.bean.ConverterDate$$ExternalSyntheticLambda14
                @Override // java.util.function.BiFunction
                public final Object apply(Object obj, Object obj2) {
                    return ConverterDate.lambda$determineReadTemporalConversionFunction$11((DateTimeFormatter) obj, (String) obj2);
                }
            };
        }
        if (MinguoEra.class.equals(cls)) {
            return new BiFunction() { // from class: com.opencsv.bean.ConverterDate$$ExternalSyntheticLambda15
                @Override // java.util.function.BiFunction
                public final Object apply(Object obj, Object obj2) {
                    return MinguoEra.of(((DateTimeFormatter) obj).parse((String) obj2).get(ChronoField.ERA));
                }
            };
        }
        if (Month.class.equals(cls)) {
            return new BiFunction() { // from class: com.opencsv.bean.ConverterDate$$ExternalSyntheticLambda16
                @Override // java.util.function.BiFunction
                public final Object apply(Object obj, Object obj2) {
                    return ConverterDate.lambda$determineReadTemporalConversionFunction$13((DateTimeFormatter) obj, (String) obj2);
                }
            };
        }
        if (MonthDay.class.equals(cls)) {
            return new BiFunction() { // from class: com.opencsv.bean.ConverterDate$$ExternalSyntheticLambda17
                @Override // java.util.function.BiFunction
                public final Object apply(Object obj, Object obj2) {
                    return ConverterDate.lambda$determineReadTemporalConversionFunction$14((DateTimeFormatter) obj, (String) obj2);
                }
            };
        }
        if (OffsetDateTime.class.equals(cls)) {
            return new BiFunction() { // from class: com.opencsv.bean.ConverterDate$$ExternalSyntheticLambda18
                @Override // java.util.function.BiFunction
                public final Object apply(Object obj, Object obj2) {
                    return ConverterDate.lambda$determineReadTemporalConversionFunction$15((DateTimeFormatter) obj, (String) obj2);
                }
            };
        }
        if (OffsetTime.class.equals(cls)) {
            return new BiFunction() { // from class: com.opencsv.bean.ConverterDate$$ExternalSyntheticLambda19
                @Override // java.util.function.BiFunction
                public final Object apply(Object obj, Object obj2) {
                    return ConverterDate.lambda$determineReadTemporalConversionFunction$16((DateTimeFormatter) obj, (String) obj2);
                }
            };
        }
        if (ThaiBuddhistEra.class.equals(cls)) {
            return new BiFunction() { // from class: com.opencsv.bean.ConverterDate$$ExternalSyntheticLambda20
                @Override // java.util.function.BiFunction
                public final Object apply(Object obj, Object obj2) {
                    return ThaiBuddhistEra.of(((DateTimeFormatter) obj).parse((String) obj2).get(ChronoField.ERA));
                }
            };
        }
        if (Year.class.equals(cls)) {
            return new BiFunction() { // from class: com.opencsv.bean.ConverterDate$$ExternalSyntheticLambda21
                @Override // java.util.function.BiFunction
                public final Object apply(Object obj, Object obj2) {
                    return ConverterDate.lambda$determineReadTemporalConversionFunction$18((DateTimeFormatter) obj, (String) obj2);
                }
            };
        }
        if (YearMonth.class.equals(cls)) {
            return new BiFunction() { // from class: com.opencsv.bean.ConverterDate$$ExternalSyntheticLambda23
                @Override // java.util.function.BiFunction
                public final Object apply(Object obj, Object obj2) {
                    return ConverterDate.lambda$determineReadTemporalConversionFunction$19((DateTimeFormatter) obj, (String) obj2);
                }
            };
        }
        if (ZoneOffset.class.equals(cls)) {
            return new BiFunction() { // from class: com.opencsv.bean.ConverterDate$$ExternalSyntheticLambda24
                @Override // java.util.function.BiFunction
                public final Object apply(Object obj, Object obj2) {
                    return ConverterDate.lambda$determineReadTemporalConversionFunction$20((DateTimeFormatter) obj, (String) obj2);
                }
            };
        }
        throw new CsvBadConverterException(getClass(), String.format(ResourceBundle.getBundle(ICSVParser.DEFAULT_BUNDLE_NAME, this.errorLocale).getString(CSVDATE_NOT_DATE), cls));
    }

    static /* synthetic */ TemporalAccessor lambda$determineReadTemporalConversionFunction$2(DateTimeFormatter dateTimeFormatter, String str) {
        return (LocalDateTime) dateTimeFormatter.parse(str, new TemporalQuery() { // from class: com.opencsv.bean.ConverterDate$$ExternalSyntheticLambda11
            @Override // java.time.temporal.TemporalQuery
            public final Object queryFrom(TemporalAccessor temporalAccessor) {
                return LocalDateTime.from(temporalAccessor);
            }
        });
    }

    static /* synthetic */ TemporalAccessor lambda$determineReadTemporalConversionFunction$3(DateTimeFormatter dateTimeFormatter, String str) {
        return (ZonedDateTime) dateTimeFormatter.parse(str, new TemporalQuery() { // from class: com.opencsv.bean.ConverterDate$$ExternalSyntheticLambda39
            @Override // java.time.temporal.TemporalQuery
            public final Object queryFrom(TemporalAccessor temporalAccessor) {
                return ZonedDateTime.from(temporalAccessor);
            }
        });
    }

    static /* synthetic */ TemporalAccessor lambda$determineReadTemporalConversionFunction$6(DateTimeFormatter dateTimeFormatter, String str) {
        return (DayOfWeek) dateTimeFormatter.parse(str, new TemporalQuery() { // from class: com.opencsv.bean.ConverterDate$$ExternalSyntheticLambda12
            @Override // java.time.temporal.TemporalQuery
            public final Object queryFrom(TemporalAccessor temporalAccessor) {
                return DayOfWeek.from(temporalAccessor);
            }
        });
    }

    static /* synthetic */ TemporalAccessor lambda$determineReadTemporalConversionFunction$8(DateTimeFormatter dateTimeFormatter, String str) {
        return (Instant) dateTimeFormatter.parse(str, new TemporalQuery() { // from class: com.opencsv.bean.ConverterDate$$ExternalSyntheticLambda9
            @Override // java.time.temporal.TemporalQuery
            public final Object queryFrom(TemporalAccessor temporalAccessor) {
                return Instant.from(temporalAccessor);
            }
        });
    }

    static /* synthetic */ TemporalAccessor lambda$determineReadTemporalConversionFunction$9(DateTimeFormatter dateTimeFormatter, String str) {
        return (ChronoLocalDate) dateTimeFormatter.parse(str, new TemporalQuery() { // from class: com.opencsv.bean.ConverterDate$$ExternalSyntheticLambda38
            @Override // java.time.temporal.TemporalQuery
            public final Object queryFrom(TemporalAccessor temporalAccessor) {
                return ChronoLocalDate.from(temporalAccessor);
            }
        });
    }

    static /* synthetic */ TemporalAccessor lambda$determineReadTemporalConversionFunction$11(DateTimeFormatter dateTimeFormatter, String str) {
        return (LocalTime) dateTimeFormatter.parse(str, new TemporalQuery() { // from class: com.opencsv.bean.ConverterDate$$ExternalSyntheticLambda35
            @Override // java.time.temporal.TemporalQuery
            public final Object queryFrom(TemporalAccessor temporalAccessor) {
                return LocalTime.from(temporalAccessor);
            }
        });
    }

    static /* synthetic */ TemporalAccessor lambda$determineReadTemporalConversionFunction$13(DateTimeFormatter dateTimeFormatter, String str) {
        return (Month) dateTimeFormatter.parse(str, new TemporalQuery() { // from class: com.opencsv.bean.ConverterDate$$ExternalSyntheticLambda33
            @Override // java.time.temporal.TemporalQuery
            public final Object queryFrom(TemporalAccessor temporalAccessor) {
                return Month.from(temporalAccessor);
            }
        });
    }

    static /* synthetic */ TemporalAccessor lambda$determineReadTemporalConversionFunction$14(DateTimeFormatter dateTimeFormatter, String str) {
        return (MonthDay) dateTimeFormatter.parse(str, new TemporalQuery() { // from class: com.opencsv.bean.ConverterDate$$ExternalSyntheticLambda0
            @Override // java.time.temporal.TemporalQuery
            public final Object queryFrom(TemporalAccessor temporalAccessor) {
                return MonthDay.from(temporalAccessor);
            }
        });
    }

    static /* synthetic */ TemporalAccessor lambda$determineReadTemporalConversionFunction$15(DateTimeFormatter dateTimeFormatter, String str) {
        return (OffsetDateTime) dateTimeFormatter.parse(str, new TemporalQuery() { // from class: com.opencsv.bean.ConverterDate$$ExternalSyntheticLambda6
            @Override // java.time.temporal.TemporalQuery
            public final Object queryFrom(TemporalAccessor temporalAccessor) {
                return OffsetDateTime.from(temporalAccessor);
            }
        });
    }

    static /* synthetic */ TemporalAccessor lambda$determineReadTemporalConversionFunction$16(DateTimeFormatter dateTimeFormatter, String str) {
        return (OffsetTime) dateTimeFormatter.parse(str, new TemporalQuery() { // from class: com.opencsv.bean.ConverterDate$$ExternalSyntheticLambda36
            @Override // java.time.temporal.TemporalQuery
            public final Object queryFrom(TemporalAccessor temporalAccessor) {
                return OffsetTime.from(temporalAccessor);
            }
        });
    }

    static /* synthetic */ TemporalAccessor lambda$determineReadTemporalConversionFunction$18(DateTimeFormatter dateTimeFormatter, String str) {
        return (Year) dateTimeFormatter.parse(str, new TemporalQuery() { // from class: com.opencsv.bean.ConverterDate$$ExternalSyntheticLambda37
            @Override // java.time.temporal.TemporalQuery
            public final Object queryFrom(TemporalAccessor temporalAccessor) {
                return Year.from(temporalAccessor);
            }
        });
    }

    static /* synthetic */ TemporalAccessor lambda$determineReadTemporalConversionFunction$19(DateTimeFormatter dateTimeFormatter, String str) {
        return (YearMonth) dateTimeFormatter.parse(str, new TemporalQuery() { // from class: com.opencsv.bean.ConverterDate$$ExternalSyntheticLambda10
            @Override // java.time.temporal.TemporalQuery
            public final Object queryFrom(TemporalAccessor temporalAccessor) {
                return YearMonth.from(temporalAccessor);
            }
        });
    }

    static /* synthetic */ TemporalAccessor lambda$determineReadTemporalConversionFunction$20(DateTimeFormatter dateTimeFormatter, String str) {
        return (ZoneOffset) dateTimeFormatter.parse(str, new TemporalQuery() { // from class: com.opencsv.bean.ConverterDate$$ExternalSyntheticLambda22
            @Override // java.time.temporal.TemporalQuery
            public final Object queryFrom(TemporalAccessor temporalAccessor) {
                return ZoneOffset.from(temporalAccessor);
            }
        });
    }

    private SimpleDateFormat setDateFormat(String str, Locale locale) {
        if (locale != null) {
            return new SimpleDateFormat(str, locale);
        }
        return new SimpleDateFormat(str);
    }

    private DateTimeFormatter setDateTimeFormatter(String str, Locale locale) {
        if (this.writeLocale != null) {
            return DateTimeFormatter.ofPattern(str, locale);
        }
        return DateTimeFormatter.ofPattern(str);
    }

    private Chronology getChronology(String str, Locale locale) {
        try {
            if (StringUtils.isNotBlank(str)) {
                return Chronology.of(str);
            }
            return Chronology.ofLocale(locale);
        } catch (DateTimeException e) {
            CsvBadConverterException csvBadConverterException = new CsvBadConverterException(getClass(), String.format(ResourceBundle.getBundle(ICSVParser.DEFAULT_BUNDLE_NAME, this.errorLocale).getString("chronology.not.found"), str));
            csvBadConverterException.initCause(e);
            throw csvBadConverterException;
        }
    }

    @Override // com.opencsv.bean.CsvConverter
    public Object convertToRead(String str) throws CsvDataTypeMismatchException {
        Date date;
        Date date2;
        if (!StringUtils.isNotBlank(str)) {
            return null;
        }
        if (Date.class.isAssignableFrom(this.type)) {
            try {
                synchronized (this.readSdf) {
                    date = this.readSdf.parse(str);
                }
                return this.type.getConstructor(Long.TYPE).newInstance(Long.valueOf(date.getTime()));
            } catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException | ParseException e) {
                CsvDataTypeMismatchException csvDataTypeMismatchException = new CsvDataTypeMismatchException(str, this.type);
                csvDataTypeMismatchException.initCause(e);
                throw csvDataTypeMismatchException;
            }
        }
        if (TemporalAccessor.class.isAssignableFrom(this.type)) {
            try {
                return this.type.cast(this.readTemporalConversionFunction.apply(this.readDtf, str));
            } catch (ArithmeticException | DateTimeException e2) {
                CsvDataTypeMismatchException csvDataTypeMismatchException2 = new CsvDataTypeMismatchException(str, this.type);
                csvDataTypeMismatchException2.initCause(e2);
                throw csvDataTypeMismatchException2;
            }
        }
        if (Calendar.class.isAssignableFrom(this.type) || XMLGregorianCalendar.class.isAssignableFrom(this.type)) {
            try {
                synchronized (this.readSdf) {
                    try {
                        date2 = this.readSdf.parse(str);
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                GregorianCalendar gregorianCalendar = new GregorianCalendar();
                gregorianCalendar.setTime(date2);
                if (this.type == XMLGregorianCalendar.class) {
                    try {
                        return this.type.cast(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar));
                    } catch (DatatypeConfigurationException e3) {
                        CsvDataTypeMismatchException csvDataTypeMismatchException3 = new CsvDataTypeMismatchException(ResourceBundle.getBundle(ICSVParser.DEFAULT_BUNDLE_NAME, this.errorLocale).getString("xmlgregoriancalendar.impossible"));
                        csvDataTypeMismatchException3.initCause(e3);
                        throw csvDataTypeMismatchException3;
                    }
                }
                return this.type.cast(gregorianCalendar);
            } catch (ParseException e4) {
                CsvDataTypeMismatchException csvDataTypeMismatchException4 = new CsvDataTypeMismatchException(str, this.type);
                csvDataTypeMismatchException4.initCause(e4);
                throw csvDataTypeMismatchException4;
            }
        }
        throw new CsvDataTypeMismatchException(str, this.type, String.format(ResourceBundle.getBundle(ICSVParser.DEFAULT_BUNDLE_NAME, this.errorLocale).getString(CSVDATE_NOT_DATE), this.type));
    }

    @Override // com.opencsv.bean.AbstractCsvConverter, com.opencsv.bean.CsvConverter
    public String convertToWrite(Object obj) throws CsvDataTypeMismatchException {
        Calendar gregorianCalendar;
        String str;
        String str2;
        if (obj == null) {
            return null;
        }
        if (Date.class.isAssignableFrom(this.type)) {
            synchronized (this.writeSdf) {
                str2 = this.writeSdf.format((Date) obj);
            }
            return str2;
        }
        if (TemporalAccessor.class.isAssignableFrom(this.type)) {
            try {
                return this.writeTemporalConversionFunction.apply(this.writeDtf, (TemporalAccessor) obj);
            } catch (ArithmeticException | DateTimeException e) {
                CsvDataTypeMismatchException csvDataTypeMismatchException = new CsvDataTypeMismatchException(obj, this.type);
                csvDataTypeMismatchException.initCause(e);
                throw csvDataTypeMismatchException;
            }
        }
        if (Calendar.class.isAssignableFrom(this.type) || XMLGregorianCalendar.class.isAssignableFrom(this.type)) {
            if (obj instanceof XMLGregorianCalendar) {
                gregorianCalendar = ((XMLGregorianCalendar) obj).toGregorianCalendar();
            } else {
                gregorianCalendar = (Calendar) obj;
            }
            synchronized (this.writeSdf) {
                str = this.writeSdf.format(gregorianCalendar.getTime());
            }
            return str;
        }
        throw new CsvDataTypeMismatchException(obj, this.type, String.format(ResourceBundle.getBundle(ICSVParser.DEFAULT_BUNDLE_NAME, this.errorLocale).getString(CSVDATE_NOT_DATE), this.type));
    }
}
