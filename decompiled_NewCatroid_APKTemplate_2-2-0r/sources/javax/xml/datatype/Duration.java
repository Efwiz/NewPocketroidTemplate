package javax.xml.datatype;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.xml.namespace.QName;
import org.eclipse.jdt.internal.compiler.classfmt.ExternalAnnotationProvider;
import org.eclipse.jdt.internal.compiler.util.Util;

/* JADX INFO: loaded from: classes11.dex */
public abstract class Duration {
    private static long getCalendarTimeInMillis(Calendar calendar) {
        return calendar.getTime().getTime();
    }

    private int getFieldValueAsInt(DatatypeConstants.Field field) {
        Number field2 = getField(field);
        if (field2 != null) {
            return field2.intValue();
        }
        return 0;
    }

    private String toString(BigDecimal bigDecimal) {
        StringBuffer stringBuffer;
        String string = bigDecimal.unscaledValue().toString();
        int iScale = bigDecimal.scale();
        if (iScale == 0) {
            return string;
        }
        int length = string.length() - iScale;
        if (length == 0) {
            return new StringBuffer("0.").append(string).toString();
        }
        if (length > 0) {
            stringBuffer = new StringBuffer(string);
            stringBuffer.insert(length, '.');
        } else {
            StringBuffer stringBuffer2 = new StringBuffer((3 - length) + string.length());
            stringBuffer2.append("0.");
            for (int i = 0; i < (-length); i++) {
                stringBuffer2.append(ExternalAnnotationProvider.NULLABLE);
            }
            stringBuffer2.append(string);
            stringBuffer = stringBuffer2;
        }
        return stringBuffer.toString();
    }

    public abstract Duration add(Duration duration);

    public abstract void addTo(Calendar calendar);

    public void addTo(Date date) {
        if (date == null) {
            throw new NullPointerException(new StringBuffer("Cannot call ").append(getClass().getName()).append("#addTo(Date date) with date == null.").toString());
        }
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(date);
        addTo(gregorianCalendar);
        date.setTime(getCalendarTimeInMillis(gregorianCalendar));
    }

    public abstract int compare(Duration duration);

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        return (obj instanceof Duration) && compare((Duration) obj) == 0;
    }

    public int getDays() {
        return getFieldValueAsInt(DatatypeConstants.DAYS);
    }

    public abstract Number getField(DatatypeConstants.Field field);

    public int getHours() {
        return getFieldValueAsInt(DatatypeConstants.HOURS);
    }

    public int getMinutes() {
        return getFieldValueAsInt(DatatypeConstants.MINUTES);
    }

    public int getMonths() {
        return getFieldValueAsInt(DatatypeConstants.MONTHS);
    }

    public int getSeconds() {
        return getFieldValueAsInt(DatatypeConstants.SECONDS);
    }

    public abstract int getSign();

    public long getTimeInMillis(Calendar calendar) {
        Calendar calendar2 = (Calendar) calendar.clone();
        addTo(calendar2);
        return getCalendarTimeInMillis(calendar2) - getCalendarTimeInMillis(calendar);
    }

    public long getTimeInMillis(Date date) {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(date);
        addTo(gregorianCalendar);
        return getCalendarTimeInMillis(gregorianCalendar) - date.getTime();
    }

    public QName getXMLSchemaType() {
        boolean zIsSet = isSet(DatatypeConstants.YEARS);
        boolean zIsSet2 = isSet(DatatypeConstants.MONTHS);
        boolean zIsSet3 = isSet(DatatypeConstants.DAYS);
        boolean zIsSet4 = isSet(DatatypeConstants.HOURS);
        boolean zIsSet5 = isSet(DatatypeConstants.MINUTES);
        boolean zIsSet6 = isSet(DatatypeConstants.SECONDS);
        if (zIsSet && zIsSet2 && zIsSet3 && zIsSet4 && zIsSet5 && zIsSet6) {
            return DatatypeConstants.DURATION;
        }
        if (!zIsSet && !zIsSet2 && zIsSet3 && zIsSet4 && zIsSet5 && zIsSet6) {
            return DatatypeConstants.DURATION_DAYTIME;
        }
        if (!zIsSet || !zIsSet2 || zIsSet3 || zIsSet4 || zIsSet5 || zIsSet6) {
            throw new IllegalStateException(new StringBuffer("javax.xml.datatype.Duration#getXMLSchemaType(): this Duration does not match one of the XML Schema date/time datatypes: year set = ").append(zIsSet).append(" month set = ").append(zIsSet2).append(" day set = ").append(zIsSet3).append(" hour set = ").append(zIsSet4).append(" minute set = ").append(zIsSet5).append(" second set = ").append(zIsSet6).toString());
        }
        return DatatypeConstants.DURATION_YEARMONTH;
    }

    public int getYears() {
        return getFieldValueAsInt(DatatypeConstants.YEARS);
    }

    public abstract int hashCode();

    public boolean isLongerThan(Duration duration) {
        return compare(duration) == 1;
    }

    public abstract boolean isSet(DatatypeConstants.Field field);

    public boolean isShorterThan(Duration duration) {
        return compare(duration) == -1;
    }

    public Duration multiply(int i) {
        return multiply(BigDecimal.valueOf(i));
    }

    public abstract Duration multiply(BigDecimal bigDecimal);

    public abstract Duration negate();

    public abstract Duration normalizeWith(Calendar calendar);

    public Duration subtract(Duration duration) {
        return add(duration.negate());
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        if (getSign() < 0) {
            stringBuffer.append('-');
        }
        stringBuffer.append('P');
        BigInteger bigInteger = (BigInteger) getField(DatatypeConstants.YEARS);
        if (bigInteger != null) {
            stringBuffer.append(bigInteger).append('Y');
        }
        BigInteger bigInteger2 = (BigInteger) getField(DatatypeConstants.MONTHS);
        if (bigInteger2 != null) {
            stringBuffer.append(bigInteger2).append('M');
        }
        BigInteger bigInteger3 = (BigInteger) getField(DatatypeConstants.DAYS);
        if (bigInteger3 != null) {
            stringBuffer.append(bigInteger3).append(Util.C_DOUBLE);
        }
        BigInteger bigInteger4 = (BigInteger) getField(DatatypeConstants.HOURS);
        BigInteger bigInteger5 = (BigInteger) getField(DatatypeConstants.MINUTES);
        BigDecimal bigDecimal = (BigDecimal) getField(DatatypeConstants.SECONDS);
        if (bigInteger4 != null || bigInteger5 != null || bigDecimal != null) {
            stringBuffer.append(Util.C_TYPE_VARIABLE);
            if (bigInteger4 != null) {
                stringBuffer.append(bigInteger4).append('H');
            }
            if (bigInteger5 != null) {
                stringBuffer.append(bigInteger5).append('M');
            }
            if (bigDecimal != null) {
                stringBuffer.append(toString(bigDecimal)).append(Util.C_SHORT);
            }
        }
        return stringBuffer.toString();
    }
}
