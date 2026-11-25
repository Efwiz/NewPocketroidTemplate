package org.apache.xerces.impl.dv.xs;

import java.math.BigInteger;
import javax.xml.datatype.XMLGregorianCalendar;
import org.apache.xerces.impl.dv.InvalidDatatypeValueException;
import org.apache.xerces.impl.dv.ValidationContext;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.eclipse.jdt.internal.compiler.util.Util;

/* JADX INFO: loaded from: classes13.dex */
public class DateTimeDV extends AbstractDateTimeDV {
    @Override // org.apache.xerces.impl.dv.xs.TypeValidator
    public Object getActualValue(String str, ValidationContext validationContext) throws InvalidDatatypeValueException {
        try {
            return parse(str);
        } catch (Exception unused) {
            throw new InvalidDatatypeValueException("cvc-datatype-valid.1.2.1", new Object[]{str, SchemaSymbols.ATTVAL_DATETIME});
        }
    }

    @Override // org.apache.xerces.impl.dv.xs.AbstractDateTimeDV
    protected XMLGregorianCalendar getXMLGregorianCalendar(AbstractDateTimeDV.DateTimeData dateTimeData) {
        return datatypeFactory.newXMLGregorianCalendar(BigInteger.valueOf(dateTimeData.unNormYear), dateTimeData.unNormMonth, dateTimeData.unNormDay, dateTimeData.unNormHour, dateTimeData.unNormMinute, (int) dateTimeData.unNormSecond, dateTimeData.unNormSecond != 0.0d ? getFractionalSecondsAsBigDecimal(dateTimeData) : null, dateTimeData.hasTimeZone() ? (dateTimeData.timezoneHr * 60) + dateTimeData.timezoneMin : Integer.MIN_VALUE);
    }

    protected AbstractDateTimeDV.DateTimeData parse(String str) throws SchemaDateTimeException {
        AbstractDateTimeDV.DateTimeData dateTimeData = new AbstractDateTimeDV.DateTimeData(str, this);
        int length = str.length();
        int iIndexOf = indexOf(str, 0, length, Util.C_TYPE_VARIABLE);
        int date = getDate(str, 0, iIndexOf, dateTimeData);
        getTime(str, iIndexOf + 1, length, dateTimeData);
        if (date != iIndexOf) {
            throw new RuntimeException(str + " is an invalid dateTime dataype value. Invalid character(s) seprating date and time values.");
        }
        validateDateTime(dateTimeData);
        saveUnnormalized(dateTimeData);
        if (dateTimeData.utc != 0 && dateTimeData.utc != 90) {
            normalize(dateTimeData);
        }
        return dateTimeData;
    }
}
