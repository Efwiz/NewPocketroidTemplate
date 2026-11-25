package com.thoughtworks.xstream.converters.extended;

import java.sql.Timestamp;
import java.util.Date;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/converters/extended/ISO8601SqlTimestampConverter.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class ISO8601SqlTimestampConverter extends ISO8601DateConverter {
    private static final String PADDING = "000000000";
    static /* synthetic */ Class class$java$sql$Timestamp;
    static /* synthetic */ Class class$java$util$Date;

    static /* synthetic */ Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    @Override // com.thoughtworks.xstream.converters.extended.ISO8601DateConverter, com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter, com.thoughtworks.xstream.converters.ConverterMatcher
    public boolean canConvert(Class cls) throws Throwable {
        Class clsClass$ = class$java$sql$Timestamp;
        if (clsClass$ == null) {
            clsClass$ = class$("java.sql.Timestamp");
            class$java$sql$Timestamp = clsClass$;
        }
        if (cls == clsClass$) {
            Class clsClass$2 = class$java$util$Date;
            if (clsClass$2 == null) {
                clsClass$2 = class$(CommonClassNames.JAVA_UTIL_DATE);
                class$java$util$Date = clsClass$2;
            }
            if (super.canConvert(clsClass$2)) {
                return true;
            }
        }
        return false;
    }

    @Override // com.thoughtworks.xstream.converters.extended.ISO8601DateConverter, com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter, com.thoughtworks.xstream.converters.SingleValueConverter
    public Object fromString(String str) {
        int iLastIndexOf = str.lastIndexOf(46);
        int i = 0;
        if (iLastIndexOf > 0) {
            int i2 = iLastIndexOf + 1;
            int i3 = i2;
            while (Character.isDigit(str.charAt(i3))) {
                i3++;
            }
            int i4 = Integer.parseInt(str.substring(i2, i3));
            str = new StringBuffer().append(str.substring(0, iLastIndexOf)).append(str.substring(i3)).toString();
            i = i4;
        }
        Timestamp timestamp = new Timestamp(((Date) super.fromString(str)).getTime());
        timestamp.setNanos(i);
        return timestamp;
    }

    @Override // com.thoughtworks.xstream.converters.extended.ISO8601DateConverter, com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter, com.thoughtworks.xstream.converters.SingleValueConverter
    public String toString(Object obj) {
        Timestamp timestamp = (Timestamp) obj;
        String string = super.toString(new Date((timestamp.getTime() / 1000) * 1000));
        String strValueOf = String.valueOf(timestamp.getNanos());
        int iLastIndexOf = string.lastIndexOf(46);
        return new StringBuffer().append(string.substring(0, iLastIndexOf + 1)).append(PADDING.substring(strValueOf.length())).append(strValueOf).append(string.substring(iLastIndexOf + 4)).toString();
    }
}
