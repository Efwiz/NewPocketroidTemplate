package org.apache.xerces.impl.dv.xs;

import com.gaurav.avnc.vnc.XKeySym;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.xml.datatype.Duration;
import name.antonsmirnov.firmata.FormatHelper;
import org.apache.xerces.impl.dv.InvalidDatatypeValueException;
import org.apache.xerces.impl.dv.ValidationContext;
import org.eclipse.jdt.internal.compiler.util.Util;

/* JADX INFO: loaded from: classes13.dex */
public class DurationDV extends AbstractDateTimeDV {
    private static final AbstractDateTimeDV.DateTimeData[] DATETIMES = {new AbstractDateTimeDV.DateTimeData(1696, 9, 1, 0, 0, 0.0d, 90, null, true, null), new AbstractDateTimeDV.DateTimeData(XKeySym.XK_Serbian_dje, 2, 1, 0, 0, 0.0d, 90, null, true, null), new AbstractDateTimeDV.DateTimeData(1903, 3, 1, 0, 0, 0.0d, 90, null, true, null), new AbstractDateTimeDV.DateTimeData(1903, 7, 1, 0, 0, 0.0d, 90, null, true, null)};
    public static final int DAYTIMEDURATION_TYPE = 2;
    public static final int DURATION_TYPE = 0;
    public static final int YEARMONTHDURATION_TYPE = 1;

    private AbstractDateTimeDV.DateTimeData addDuration(AbstractDateTimeDV.DateTimeData dateTimeData, AbstractDateTimeDV.DateTimeData dateTimeData2, AbstractDateTimeDV.DateTimeData dateTimeData3) {
        int i;
        resetDateObj(dateTimeData3);
        int i2 = dateTimeData2.month + dateTimeData.month;
        dateTimeData3.month = modulo(i2, 1, 13);
        dateTimeData3.year = dateTimeData2.year + dateTimeData.year + fQuotient(i2, 1, 13);
        double d = dateTimeData2.second + dateTimeData.second;
        int iFloor = (int) Math.floor(d / 60.0d);
        dateTimeData3.second = d - ((double) (iFloor * 60));
        int i3 = dateTimeData2.minute + dateTimeData.minute + iFloor;
        int iFQuotient = fQuotient(i3, 60);
        dateTimeData3.minute = mod(i3, 60, iFQuotient);
        int i4 = dateTimeData2.hour + dateTimeData.hour + iFQuotient;
        int iFQuotient2 = fQuotient(i4, 24);
        dateTimeData3.hour = mod(i4, 24, iFQuotient2);
        dateTimeData3.day = dateTimeData2.day + dateTimeData.day + iFQuotient2;
        while (true) {
            int iMaxDayInMonthFor = maxDayInMonthFor(dateTimeData3.year, dateTimeData3.month);
            if (dateTimeData3.day < 1) {
                dateTimeData3.day += maxDayInMonthFor(dateTimeData3.year, dateTimeData3.month - 1);
                i = -1;
            } else {
                if (dateTimeData3.day <= iMaxDayInMonthFor) {
                    dateTimeData3.utc = 90;
                    return dateTimeData3;
                }
                dateTimeData3.day -= iMaxDayInMonthFor;
                i = 1;
            }
            int i5 = dateTimeData3.month + i;
            dateTimeData3.month = modulo(i5, 1, 13);
            dateTimeData3.year += fQuotient(i5, 1, 13);
        }
    }

    private short compareResults(short s, short s2, boolean z) {
        if (s2 == 2) {
            return (short) 2;
        }
        if (s != s2 && z) {
            return (short) 2;
        }
        if (s == s2 || z) {
            return s;
        }
        if (s == 0 || s2 == 0) {
            return s != 0 ? s : s2;
        }
        return (short) 2;
    }

    @Override // org.apache.xerces.impl.dv.xs.AbstractDateTimeDV
    protected short compareDates(AbstractDateTimeDV.DateTimeData dateTimeData, AbstractDateTimeDV.DateTimeData dateTimeData2, boolean z) {
        short sCompareResults;
        short sCompareResults2;
        if (compareOrder(dateTimeData, dateTimeData2) == 0) {
            return (short) 0;
        }
        AbstractDateTimeDV.DateTimeData[] dateTimeDataArr = {new AbstractDateTimeDV.DateTimeData(null, this), new AbstractDateTimeDV.DateTimeData(null, this)};
        AbstractDateTimeDV.DateTimeData[] dateTimeDataArr2 = DATETIMES;
        short sCompareOrder = compareOrder(addDuration(dateTimeData, dateTimeDataArr2[0], dateTimeDataArr[0]), addDuration(dateTimeData2, dateTimeDataArr2[0], dateTimeDataArr[1]));
        if (sCompareOrder == 2 || (sCompareResults = compareResults(sCompareOrder, compareOrder(addDuration(dateTimeData, dateTimeDataArr2[1], dateTimeDataArr[0]), addDuration(dateTimeData2, dateTimeDataArr2[1], dateTimeDataArr[1])), z)) == 2 || (sCompareResults2 = compareResults(sCompareResults, compareOrder(addDuration(dateTimeData, dateTimeDataArr2[2], dateTimeDataArr[0]), addDuration(dateTimeData2, dateTimeDataArr2[2], dateTimeDataArr[1])), z)) == 2) {
            return (short) 2;
        }
        return compareResults(sCompareResults2, compareOrder(addDuration(dateTimeData, dateTimeDataArr2[3], dateTimeDataArr[0]), addDuration(dateTimeData2, dateTimeDataArr2[3], dateTimeDataArr[1])), z);
    }

    @Override // org.apache.xerces.impl.dv.xs.AbstractDateTimeDV
    protected String dateToString(AbstractDateTimeDV.DateTimeData dateTimeData) {
        StringBuffer stringBuffer = new StringBuffer(30);
        if (dateTimeData.year < 0 || dateTimeData.month < 0 || dateTimeData.day < 0 || dateTimeData.hour < 0 || dateTimeData.minute < 0 || dateTimeData.second < 0.0d) {
            stringBuffer.append('-');
        }
        stringBuffer.append('P');
        stringBuffer.append((dateTimeData.year < 0 ? -1 : 1) * dateTimeData.year);
        stringBuffer.append('Y');
        stringBuffer.append((dateTimeData.month < 0 ? -1 : 1) * dateTimeData.month);
        stringBuffer.append('M');
        stringBuffer.append((dateTimeData.day < 0 ? -1 : 1) * dateTimeData.day);
        stringBuffer.append("DT");
        stringBuffer.append((dateTimeData.hour < 0 ? -1 : 1) * dateTimeData.hour);
        stringBuffer.append('H');
        stringBuffer.append((dateTimeData.minute < 0 ? -1 : 1) * dateTimeData.minute);
        stringBuffer.append('M');
        append2(stringBuffer, ((double) (dateTimeData.second >= 0.0d ? 1 : -1)) * dateTimeData.second);
        stringBuffer.append(Util.C_SHORT);
        return stringBuffer.toString();
    }

    @Override // org.apache.xerces.impl.dv.xs.TypeValidator
    public Object getActualValue(String str, ValidationContext validationContext) throws InvalidDatatypeValueException {
        try {
            return parse(str, 0);
        } catch (Exception unused) {
            throw new InvalidDatatypeValueException("cvc-datatype-valid.1.2.1", new Object[]{str, "duration"});
        }
    }

    @Override // org.apache.xerces.impl.dv.xs.AbstractDateTimeDV
    protected Duration getDuration(AbstractDateTimeDV.DateTimeData dateTimeData) {
        int i = (dateTimeData.year < 0 || dateTimeData.month < 0 || dateTimeData.day < 0 || dateTimeData.hour < 0 || dateTimeData.minute < 0 || dateTimeData.second < 0.0d) ? -1 : 1;
        return datatypeFactory.newDuration(i == 1, dateTimeData.year != Integer.MIN_VALUE ? BigInteger.valueOf(dateTimeData.year * i) : null, dateTimeData.month != Integer.MIN_VALUE ? BigInteger.valueOf(dateTimeData.month * i) : null, dateTimeData.day != Integer.MIN_VALUE ? BigInteger.valueOf(dateTimeData.day * i) : null, dateTimeData.hour != Integer.MIN_VALUE ? BigInteger.valueOf(dateTimeData.hour * i) : null, dateTimeData.minute != Integer.MIN_VALUE ? BigInteger.valueOf(dateTimeData.minute * i) : null, dateTimeData.second != -2.147483648E9d ? new BigDecimal(String.valueOf(((double) i) * dateTimeData.second)) : null);
    }

    protected AbstractDateTimeDV.DateTimeData parse(String str, int i) throws SchemaDateTimeException {
        int i2;
        int length = str.length();
        AbstractDateTimeDV.DateTimeData dateTimeData = new AbstractDateTimeDV.DateTimeData(str, this);
        boolean z = false;
        char cCharAt = str.charAt(0);
        if (cCharAt != 'P' && cCharAt != '-') {
            throw new SchemaDateTimeException();
        }
        dateTimeData.utc = cCharAt == '-' ? 45 : 0;
        boolean z2 = true;
        if (cCharAt != '-') {
            i2 = 1;
        } else {
            if (str.charAt(1) != 'P') {
                throw new SchemaDateTimeException();
            }
            i2 = 2;
        }
        int i3 = dateTimeData.utc == 45 ? -1 : 1;
        int iIndexOf = indexOf(str, i2, length, Util.C_TYPE_VARIABLE);
        if (iIndexOf == -1) {
            iIndexOf = length;
        } else if (i == 1) {
            throw new SchemaDateTimeException();
        }
        int iIndexOf2 = indexOf(str, i2, iIndexOf, 'Y');
        if (iIndexOf2 != -1) {
            if (i == 2) {
                throw new SchemaDateTimeException();
            }
            dateTimeData.year = parseInt(str, i2, iIndexOf2) * i3;
            i2 = iIndexOf2 + 1;
            z = true;
        }
        int iIndexOf3 = indexOf(str, i2, iIndexOf, 'M');
        if (iIndexOf3 != -1) {
            if (i == 2) {
                throw new SchemaDateTimeException();
            }
            dateTimeData.month = parseInt(str, i2, iIndexOf3) * i3;
            i2 = iIndexOf3 + 1;
            z = true;
        }
        int iIndexOf4 = indexOf(str, i2, iIndexOf, Util.C_DOUBLE);
        if (iIndexOf4 != -1) {
            if (i == 1) {
                throw new SchemaDateTimeException();
            }
            dateTimeData.day = parseInt(str, i2, iIndexOf4) * i3;
            i2 = iIndexOf4 + 1;
            z = true;
        }
        if (length == iIndexOf && i2 != length) {
            throw new SchemaDateTimeException();
        }
        if (length != iIndexOf) {
            int i4 = i2 + 1;
            int iIndexOf5 = indexOf(str, i4, length, 'H');
            if (iIndexOf5 != -1) {
                dateTimeData.hour = parseInt(str, i4, iIndexOf5) * i3;
                i4 = iIndexOf5 + 1;
                z = true;
            }
            int iIndexOf6 = indexOf(str, i4, length, 'M');
            if (iIndexOf6 != -1) {
                dateTimeData.minute = parseInt(str, i4, iIndexOf6) * i3;
                i4 = iIndexOf6 + 1;
                z = true;
            }
            int iIndexOf7 = indexOf(str, i4, length, Util.C_SHORT);
            if (iIndexOf7 != -1) {
                dateTimeData.second = ((double) i3) * parseSecond(str, i4, iIndexOf7);
                i4 = iIndexOf7 + 1;
            } else {
                z2 = z;
            }
            if (i4 != length || str.charAt(i4 - 1) == 'T') {
                throw new SchemaDateTimeException();
            }
            z = z2;
        }
        if (z) {
            return dateTimeData;
        }
        throw new SchemaDateTimeException();
    }

    @Override // org.apache.xerces.impl.dv.xs.AbstractDateTimeDV
    protected double parseSecond(String str, int i, int i2) throws NumberFormatException {
        int i3 = -1;
        for (int i4 = i; i4 < i2; i4++) {
            char cCharAt = str.charAt(i4);
            if (cCharAt == '.') {
                i3 = i4;
            } else if (cCharAt > '9' || cCharAt < '0') {
                throw new NumberFormatException(FormatHelper.QUOTE + str + "' has wrong format");
            }
        }
        if (i3 + 1 == i2) {
            throw new NumberFormatException(FormatHelper.QUOTE + str + "' has wrong format");
        }
        double d = Double.parseDouble(str.substring(i, i2));
        if (d != Double.POSITIVE_INFINITY) {
            return d;
        }
        throw new NumberFormatException(FormatHelper.QUOTE + str + "' has wrong format");
    }
}
