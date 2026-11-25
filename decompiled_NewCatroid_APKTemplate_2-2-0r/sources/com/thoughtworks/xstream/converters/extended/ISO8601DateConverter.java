package com.thoughtworks.xstream.converters.extended;

import com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter;
import java.util.Calendar;
import java.util.Date;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/converters/extended/ISO8601DateConverter.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class ISO8601DateConverter extends AbstractSingleValueConverter {
    static /* synthetic */ Class class$java$util$Date;
    static /* synthetic */ Class class$java$util$GregorianCalendar;
    private final ISO8601GregorianCalendarConverter converter = new ISO8601GregorianCalendarConverter();

    static /* synthetic */ Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    @Override // com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter, com.thoughtworks.xstream.converters.ConverterMatcher
    public boolean canConvert(Class cls) throws Throwable {
        Class clsClass$ = class$java$util$Date;
        if (clsClass$ == null) {
            clsClass$ = class$(CommonClassNames.JAVA_UTIL_DATE);
            class$java$util$Date = clsClass$;
        }
        if (cls == clsClass$) {
            ISO8601GregorianCalendarConverter iSO8601GregorianCalendarConverter = this.converter;
            Class clsClass$2 = class$java$util$GregorianCalendar;
            if (clsClass$2 == null) {
                clsClass$2 = class$("java.util.GregorianCalendar");
                class$java$util$GregorianCalendar = clsClass$2;
            }
            if (iSO8601GregorianCalendarConverter.canConvert(clsClass$2)) {
                return true;
            }
        }
        return false;
    }

    @Override // com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter, com.thoughtworks.xstream.converters.SingleValueConverter
    public Object fromString(String str) {
        return ((Calendar) this.converter.fromString(str)).getTime();
    }

    @Override // com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter, com.thoughtworks.xstream.converters.SingleValueConverter
    public String toString(Object obj) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime((Date) obj);
        return this.converter.toString(calendar);
    }
}
