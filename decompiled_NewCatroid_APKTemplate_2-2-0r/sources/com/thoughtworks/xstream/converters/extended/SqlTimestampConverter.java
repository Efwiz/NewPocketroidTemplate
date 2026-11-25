package com.thoughtworks.xstream.converters.extended;

import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter;
import com.thoughtworks.xstream.core.util.ThreadSafeSimpleDateFormat;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.TimeZone;
import okhttp3.internal.http2.Http2Connection;
import org.catrobat.catroid.common.Constants;
import org.eclipse.jdt.internal.compiler.classfmt.ExternalAnnotationProvider;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/converters/extended/SqlTimestampConverter.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class SqlTimestampConverter extends AbstractSingleValueConverter {
    static /* synthetic */ Class class$java$sql$Timestamp;
    private final ThreadSafeSimpleDateFormat format;

    public SqlTimestampConverter() {
        this(TimeZone.getTimeZone("UTC"));
    }

    public SqlTimestampConverter(TimeZone timeZone) {
        this.format = new ThreadSafeSimpleDateFormat(Constants.DATE_FORMAT_DEFAULT, timeZone, 0, 5, false);
    }

    static /* synthetic */ Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    @Override // com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter, com.thoughtworks.xstream.converters.ConverterMatcher
    public boolean canConvert(Class cls) throws Throwable {
        Class clsClass$ = class$java$sql$Timestamp;
        if (clsClass$ == null) {
            clsClass$ = class$("java.sql.Timestamp");
            class$java$sql$Timestamp = clsClass$;
        }
        return cls == clsClass$;
    }

    @Override // com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter, com.thoughtworks.xstream.converters.SingleValueConverter
    public String toString(Object obj) {
        Timestamp timestamp = (Timestamp) obj;
        StringBuffer stringBuffer = new StringBuffer(this.format.format(timestamp));
        if (timestamp.getNanos() != 0) {
            stringBuffer.append('.');
            String strValueOf = String.valueOf(timestamp.getNanos() + Http2Connection.DEGRADED_PONG_TIMEOUT_NS);
            int i = 10;
            while (i > 2 && strValueOf.charAt(i - 1) == '0') {
                i--;
            }
            stringBuffer.append(strValueOf.subSequence(1, i));
        }
        return stringBuffer.toString();
    }

    @Override // com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter, com.thoughtworks.xstream.converters.SingleValueConverter
    public Object fromString(String str) {
        int iLastIndexOf = str.lastIndexOf(46);
        if (iLastIndexOf > 0 && (str.length() - iLastIndexOf < 2 || str.length() - iLastIndexOf > 10)) {
            throw new ConversionException("Timestamp format must be yyyy-mm-dd hh:mm:ss[.fffffffff]");
        }
        try {
            Timestamp timestamp = new Timestamp(this.format.parse(iLastIndexOf < 0 ? str : str.substring(0, iLastIndexOf)).getTime());
            if (iLastIndexOf > 0) {
                StringBuffer stringBuffer = new StringBuffer(str.substring(iLastIndexOf + 1));
                while (stringBuffer.length() != 9) {
                    stringBuffer.append(ExternalAnnotationProvider.NULLABLE);
                }
                timestamp.setNanos(Integer.parseInt(stringBuffer.toString()));
            }
            return timestamp;
        } catch (NumberFormatException e) {
            throw new ConversionException("Timestamp format must be yyyy-mm-dd hh:mm:ss[.fffffffff]", e);
        } catch (ParseException e2) {
            throw new ConversionException("Timestamp format must be yyyy-mm-dd hh:mm:ss[.fffffffff]", e2);
        }
    }
}
