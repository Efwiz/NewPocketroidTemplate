package org.apache.xerces.impl.dv.xs;

import java.math.BigInteger;
import javax.xml.datatype.XMLGregorianCalendar;
import org.apache.xerces.impl.dv.InvalidDatatypeValueException;
import org.apache.xerces.impl.dv.ValidationContext;

/* JADX INFO: loaded from: classes13.dex */
public class TimeDV extends AbstractDateTimeDV {
    @Override // org.apache.xerces.impl.dv.xs.AbstractDateTimeDV
    protected String dateToString(AbstractDateTimeDV.DateTimeData dateTimeData) {
        StringBuffer stringBuffer = new StringBuffer(16);
        append(stringBuffer, dateTimeData.hour, 2);
        stringBuffer.append(':');
        append(stringBuffer, dateTimeData.minute, 2);
        stringBuffer.append(':');
        append(stringBuffer, dateTimeData.second);
        append(stringBuffer, (char) dateTimeData.utc, 0);
        return stringBuffer.toString();
    }

    @Override // org.apache.xerces.impl.dv.xs.TypeValidator
    public Object getActualValue(String str, ValidationContext validationContext) throws InvalidDatatypeValueException {
        try {
            return parse(str);
        } catch (Exception unused) {
            throw new InvalidDatatypeValueException("cvc-datatype-valid.1.2.1", new Object[]{str, "time"});
        }
    }

    @Override // org.apache.xerces.impl.dv.xs.AbstractDateTimeDV
    protected XMLGregorianCalendar getXMLGregorianCalendar(AbstractDateTimeDV.DateTimeData dateTimeData) {
        return datatypeFactory.newXMLGregorianCalendar((BigInteger) null, Integer.MIN_VALUE, Integer.MIN_VALUE, dateTimeData.unNormHour, dateTimeData.unNormMinute, (int) dateTimeData.unNormSecond, dateTimeData.unNormSecond != 0.0d ? getFractionalSecondsAsBigDecimal(dateTimeData) : null, dateTimeData.hasTimeZone() ? (dateTimeData.timezoneHr * 60) + dateTimeData.timezoneMin : Integer.MIN_VALUE);
    }

    protected AbstractDateTimeDV.DateTimeData parse(String str) throws SchemaDateTimeException {
        AbstractDateTimeDV.DateTimeData dateTimeData = new AbstractDateTimeDV.DateTimeData(str, this);
        int length = str.length();
        dateTimeData.year = 2000;
        dateTimeData.month = 1;
        dateTimeData.day = 15;
        getTime(str, 0, length, dateTimeData);
        validateDateTime(dateTimeData);
        saveUnnormalized(dateTimeData);
        if (dateTimeData.utc != 0 && dateTimeData.utc != 90) {
            normalize(dateTimeData);
            dateTimeData.day = 15;
        }
        dateTimeData.position = 2;
        return dateTimeData;
    }
}
