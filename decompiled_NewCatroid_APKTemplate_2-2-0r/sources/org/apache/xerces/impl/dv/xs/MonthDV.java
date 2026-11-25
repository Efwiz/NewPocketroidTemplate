package org.apache.xerces.impl.dv.xs;

import javax.xml.datatype.XMLGregorianCalendar;
import org.apache.xerces.impl.dv.InvalidDatatypeValueException;
import org.apache.xerces.impl.dv.ValidationContext;
import org.apache.xerces.impl.xs.SchemaSymbols;

/* JADX INFO: loaded from: classes13.dex */
public class MonthDV extends AbstractDateTimeDV {
    @Override // org.apache.xerces.impl.dv.xs.AbstractDateTimeDV
    protected String dateToString(AbstractDateTimeDV.DateTimeData dateTimeData) {
        StringBuffer stringBuffer = new StringBuffer(5);
        stringBuffer.append("--");
        append(stringBuffer, dateTimeData.month, 2);
        append(stringBuffer, (char) dateTimeData.utc, 0);
        return stringBuffer.toString();
    }

    @Override // org.apache.xerces.impl.dv.xs.TypeValidator
    public Object getActualValue(String str, ValidationContext validationContext) throws InvalidDatatypeValueException {
        try {
            return parse(str);
        } catch (Exception unused) {
            throw new InvalidDatatypeValueException("cvc-datatype-valid.1.2.1", new Object[]{str, SchemaSymbols.ATTVAL_MONTH});
        }
    }

    @Override // org.apache.xerces.impl.dv.xs.AbstractDateTimeDV
    protected XMLGregorianCalendar getXMLGregorianCalendar(AbstractDateTimeDV.DateTimeData dateTimeData) {
        return datatypeFactory.newXMLGregorianCalendar(Integer.MIN_VALUE, dateTimeData.unNormMonth, Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE, dateTimeData.hasTimeZone() ? (dateTimeData.timezoneHr * 60) + dateTimeData.timezoneMin : Integer.MIN_VALUE);
    }

    protected AbstractDateTimeDV.DateTimeData parse(String str) throws SchemaDateTimeException {
        AbstractDateTimeDV.DateTimeData dateTimeData = new AbstractDateTimeDV.DateTimeData(str, this);
        int length = str.length();
        dateTimeData.year = 2000;
        dateTimeData.day = 1;
        if (str.charAt(0) != '-' || str.charAt(1) != '-') {
            throw new SchemaDateTimeException("Invalid format for gMonth: " + str);
        }
        int i = 4;
        dateTimeData.month = parseInt(str, 2, 4);
        if (str.length() >= 6 && str.charAt(4) == '-' && str.charAt(5) == '-') {
            i = 6;
        }
        if (i < length) {
            if (!isNextCharUTCSign(str, i, length)) {
                throw new SchemaDateTimeException("Error in month parsing: " + str);
            }
            getTimeZone(str, dateTimeData, i, length);
        }
        validateDateTime(dateTimeData);
        saveUnnormalized(dateTimeData);
        if (dateTimeData.utc != 0 && dateTimeData.utc != 90) {
            normalize(dateTimeData);
        }
        dateTimeData.position = 1;
        return dateTimeData;
    }
}
