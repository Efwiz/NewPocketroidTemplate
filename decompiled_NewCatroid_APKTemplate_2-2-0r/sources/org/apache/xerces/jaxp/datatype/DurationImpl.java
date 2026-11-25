package org.apache.xerces.jaxp.datatype;

import com.gaurav.avnc.vnc.XKeySym;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.xerces.util.DatatypeMessageFormatter;
import org.eclipse.jdt.internal.compiler.classfmt.ExternalAnnotationProvider;
import org.eclipse.jdt.internal.compiler.util.Util;

/* JADX INFO: loaded from: classes13.dex */
class DurationImpl extends Duration implements Serializable {
    private static final long serialVersionUID = -2650025807136350131L;
    private final BigInteger days;
    private final BigInteger hours;
    private final BigInteger minutes;
    private final BigInteger months;
    private final BigDecimal seconds;
    private final int signum;
    private final BigInteger years;
    private static final DatatypeConstants.Field[] FIELDS = {DatatypeConstants.YEARS, DatatypeConstants.MONTHS, DatatypeConstants.DAYS, DatatypeConstants.HOURS, DatatypeConstants.MINUTES, DatatypeConstants.SECONDS};
    private static final BigDecimal ZERO = BigDecimal.valueOf(0L);
    private static final XMLGregorianCalendar[] TEST_POINTS = {XMLGregorianCalendarImpl.parse("1696-09-01T00:00:00Z"), XMLGregorianCalendarImpl.parse("1697-02-01T00:00:00Z"), XMLGregorianCalendarImpl.parse("1903-03-01T00:00:00Z"), XMLGregorianCalendarImpl.parse("1903-07-01T00:00:00Z")};
    private static final BigDecimal[] FACTORS = {BigDecimal.valueOf(12L), null, BigDecimal.valueOf(24L), BigDecimal.valueOf(60L), BigDecimal.valueOf(60L)};

    protected DurationImpl(long j) {
        int i = 1;
        int i2 = 0;
        if (j > 0) {
            this.signum = 1;
        } else if (j < 0) {
            this.signum = -1;
            if (j == Long.MIN_VALUE) {
                j++;
            } else {
                i = 0;
            }
            j *= -1;
            i2 = i;
        } else {
            this.signum = 0;
        }
        this.years = null;
        this.months = null;
        this.seconds = BigDecimal.valueOf((j % DateUtils.MILLIS_PER_MINUTE) + ((long) i2), 3);
        long j2 = j / DateUtils.MILLIS_PER_MINUTE;
        this.minutes = j2 == 0 ? null : BigInteger.valueOf(j2 % 60);
        long j3 = j2 / 60;
        this.hours = j3 == 0 ? null : BigInteger.valueOf(j3 % 24);
        long j4 = j3 / 24;
        this.days = j4 != 0 ? BigInteger.valueOf(j4) : null;
    }

    protected DurationImpl(String str) throws IllegalArgumentException {
        boolean z;
        boolean z2;
        int length = str.length();
        str.getClass();
        int[] iArr = {0};
        if (length == 0 || str.charAt(0) != '-') {
            z = true;
        } else {
            iArr[0] = iArr[0] + 1;
            z = false;
        }
        int i = iArr[0];
        if (length != i) {
            iArr[0] = i + 1;
            if (str.charAt(i) != 'P') {
                throw new IllegalArgumentException(str);
            }
        }
        String[] strArr = new String[3];
        int[] iArr2 = new int[3];
        int i2 = 0;
        while (true) {
            int i3 = iArr[0];
            if (length == i3 || !isDigit(str.charAt(i3)) || i2 >= 3) {
                break;
            }
            iArr2[i2] = iArr[0];
            strArr[i2] = parsePiece(str, iArr);
            i2++;
        }
        int i4 = iArr[0];
        if (length != i4) {
            iArr[0] = i4 + 1;
            if (str.charAt(i4) != 'T') {
                throw new IllegalArgumentException(str);
            }
            z2 = true;
        } else {
            z2 = false;
        }
        String[] strArr2 = new String[3];
        int[] iArr3 = new int[3];
        int i5 = 0;
        while (true) {
            int i6 = iArr[0];
            if (length == i6 || !isDigitOrPeriod(str.charAt(i6)) || i5 >= 3) {
                break;
            }
            iArr3[i5] = iArr[0];
            strArr2[i5] = parsePiece(str, iArr);
            i5++;
        }
        if (z2 && i5 == 0) {
            throw new IllegalArgumentException(str);
        }
        if (length != iArr[0]) {
            throw new IllegalArgumentException(str);
        }
        if (i2 == 0 && i5 == 0) {
            throw new IllegalArgumentException(str);
        }
        organizeParts(str, strArr, iArr2, i2, "YMD");
        organizeParts(str, strArr2, iArr3, i5, "HMS");
        this.years = parseBigInteger(str, strArr[0], iArr2[0]);
        this.months = parseBigInteger(str, strArr[1], iArr2[1]);
        this.days = parseBigInteger(str, strArr[2], iArr2[2]);
        this.hours = parseBigInteger(str, strArr2[0], iArr3[0]);
        this.minutes = parseBigInteger(str, strArr2[1], iArr3[1]);
        this.seconds = parseBigDecimal(str, strArr2[2], iArr3[2]);
        this.signum = calcSignum(z);
    }

    protected DurationImpl(boolean z, int i, int i2, int i3, int i4, int i5, int i6) {
        this(z, wrap(i), wrap(i2), wrap(i3), wrap(i4), wrap(i5), i6 != 0 ? BigDecimal.valueOf(i6) : null);
    }

    protected DurationImpl(boolean z, BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, BigInteger bigInteger4, BigInteger bigInteger5, BigDecimal bigDecimal) {
        this.years = bigInteger;
        this.months = bigInteger2;
        this.days = bigInteger3;
        this.hours = bigInteger4;
        this.minutes = bigInteger5;
        this.seconds = bigDecimal;
        this.signum = calcSignum(z);
        if (bigInteger == null && bigInteger2 == null && bigInteger3 == null && bigInteger4 == null && bigInteger5 == null && bigDecimal == null) {
            throw new IllegalArgumentException(DatatypeMessageFormatter.formatMessage(null, "AllFieldsNull", null));
        }
        testNonNegative(bigInteger, DatatypeConstants.YEARS);
        testNonNegative(bigInteger2, DatatypeConstants.MONTHS);
        testNonNegative(bigInteger3, DatatypeConstants.DAYS);
        testNonNegative(bigInteger4, DatatypeConstants.HOURS);
        testNonNegative(bigInteger5, DatatypeConstants.MINUTES);
        testNonNegative(bigDecimal, DatatypeConstants.SECONDS);
    }

    private static void alignSigns(BigDecimal[] bigDecimalArr, int i, int i2) {
        boolean z;
        do {
            z = false;
            int iSignum = 0;
            for (int i3 = i; i3 < i2; i3++) {
                if (bigDecimalArr[i3].signum() * iSignum < 0) {
                    BigDecimal bigDecimalAbs = bigDecimalArr[i3].abs();
                    BigDecimal[] bigDecimalArr2 = FACTORS;
                    int i4 = i3 - 1;
                    BigDecimal bigDecimalDivide = bigDecimalAbs.divide(bigDecimalArr2[i4], 0);
                    if (bigDecimalArr[i3].signum() > 0) {
                        bigDecimalDivide = bigDecimalDivide.negate();
                    }
                    bigDecimalArr[i4] = bigDecimalArr[i4].subtract(bigDecimalDivide);
                    bigDecimalArr[i3] = bigDecimalArr[i3].add(bigDecimalDivide.multiply(bigDecimalArr2[i4]));
                    z = true;
                }
                if (bigDecimalArr[i3].signum() != 0) {
                    iSignum = bigDecimalArr[i3].signum();
                }
            }
        } while (z);
    }

    private int calcSignum(boolean z) {
        BigInteger bigInteger;
        BigInteger bigInteger2;
        BigInteger bigInteger3;
        BigInteger bigInteger4;
        BigDecimal bigDecimal;
        BigInteger bigInteger5 = this.years;
        if ((bigInteger5 == null || bigInteger5.signum() == 0) && (((bigInteger = this.months) == null || bigInteger.signum() == 0) && (((bigInteger2 = this.days) == null || bigInteger2.signum() == 0) && (((bigInteger3 = this.hours) == null || bigInteger3.signum() == 0) && (((bigInteger4 = this.minutes) == null || bigInteger4.signum() == 0) && ((bigDecimal = this.seconds) == null || bigDecimal.signum() == 0)))))) {
            return 0;
        }
        return z ? 1 : -1;
    }

    private int compareDates(Duration duration, Duration duration2) {
        XMLGregorianCalendar[] xMLGregorianCalendarArr = TEST_POINTS;
        XMLGregorianCalendar xMLGregorianCalendar = (XMLGregorianCalendar) xMLGregorianCalendarArr[0].clone();
        XMLGregorianCalendar xMLGregorianCalendar2 = (XMLGregorianCalendar) xMLGregorianCalendarArr[0].clone();
        xMLGregorianCalendar.add(duration);
        xMLGregorianCalendar2.add(duration2);
        int iCompare = xMLGregorianCalendar.compare(xMLGregorianCalendar2);
        if (iCompare == 2) {
            return 2;
        }
        XMLGregorianCalendar xMLGregorianCalendar3 = (XMLGregorianCalendar) xMLGregorianCalendarArr[1].clone();
        XMLGregorianCalendar xMLGregorianCalendar4 = (XMLGregorianCalendar) xMLGregorianCalendarArr[1].clone();
        xMLGregorianCalendar3.add(duration);
        xMLGregorianCalendar4.add(duration2);
        int iCompareResults = compareResults(iCompare, xMLGregorianCalendar3.compare(xMLGregorianCalendar4));
        if (iCompareResults == 2) {
            return 2;
        }
        XMLGregorianCalendar xMLGregorianCalendar5 = (XMLGregorianCalendar) xMLGregorianCalendarArr[2].clone();
        XMLGregorianCalendar xMLGregorianCalendar6 = (XMLGregorianCalendar) xMLGregorianCalendarArr[2].clone();
        xMLGregorianCalendar5.add(duration);
        xMLGregorianCalendar6.add(duration2);
        int iCompareResults2 = compareResults(iCompareResults, xMLGregorianCalendar5.compare(xMLGregorianCalendar6));
        if (iCompareResults2 == 2) {
            return 2;
        }
        XMLGregorianCalendar xMLGregorianCalendar7 = (XMLGregorianCalendar) xMLGregorianCalendarArr[3].clone();
        XMLGregorianCalendar xMLGregorianCalendar8 = (XMLGregorianCalendar) xMLGregorianCalendarArr[3].clone();
        xMLGregorianCalendar7.add(duration);
        xMLGregorianCalendar8.add(duration2);
        return compareResults(iCompareResults2, xMLGregorianCalendar7.compare(xMLGregorianCalendar8));
    }

    private int compareResults(int i, int i2) {
        if (i2 != 2 && i == i2) {
            return i;
        }
        return 2;
    }

    private static long getCalendarTimeInMillis(Calendar calendar) {
        return calendar.getTime().getTime();
    }

    private BigDecimal getFieldAsBigDecimal(DatatypeConstants.Field field) {
        if (field == DatatypeConstants.SECONDS) {
            BigDecimal bigDecimal = this.seconds;
            return bigDecimal != null ? bigDecimal : ZERO;
        }
        BigInteger bigInteger = (BigInteger) getField(field);
        return bigInteger == null ? ZERO : new BigDecimal(bigInteger);
    }

    private int getInt(DatatypeConstants.Field field) {
        Number field2 = getField(field);
        if (field2 == null) {
            return 0;
        }
        return field2.intValue();
    }

    private static boolean isDigit(char c) {
        return '0' <= c && c <= '9';
    }

    private static boolean isDigitOrPeriod(char c) {
        return isDigit(c) || c == '.';
    }

    private static void organizeParts(String str, String[] strArr, int[] iArr, int i, String str2) throws IllegalArgumentException {
        int length = str2.length();
        int i2 = i - 1;
        while (i2 >= 0) {
            String str3 = strArr[i2];
            if (str3 == null) {
                throw new IllegalArgumentException(str);
            }
            int iLastIndexOf = str2.lastIndexOf(str3.charAt(str3.length() - 1), length - 1);
            if (iLastIndexOf == -1) {
                throw new IllegalArgumentException(str);
            }
            for (int i3 = iLastIndexOf + 1; i3 < length; i3++) {
                strArr[i3] = null;
            }
            strArr[iLastIndexOf] = strArr[i2];
            iArr[iLastIndexOf] = iArr[i2];
            i2--;
            length = iLastIndexOf;
        }
        for (int i4 = length - 1; i4 >= 0; i4--) {
            strArr[i4] = null;
        }
    }

    private static BigDecimal parseBigDecimal(String str, String str2, int i) throws IllegalArgumentException {
        if (str2 == null) {
            return null;
        }
        return new BigDecimal(str2.substring(0, str2.length() - 1));
    }

    private static BigInteger parseBigInteger(String str, String str2, int i) throws IllegalArgumentException {
        if (str2 == null) {
            return null;
        }
        return new BigInteger(str2.substring(0, str2.length() - 1));
    }

    private static String parsePiece(String str, int[] iArr) throws IllegalArgumentException {
        int i = iArr[0];
        while (iArr[0] < str.length() && isDigitOrPeriod(str.charAt(iArr[0]))) {
            iArr[0] = iArr[0] + 1;
        }
        if (iArr[0] == str.length()) {
            throw new IllegalArgumentException(str);
        }
        int i2 = iArr[0] + 1;
        iArr[0] = i2;
        return str.substring(i, i2);
    }

    static BigDecimal sanitize(BigDecimal bigDecimal, int i) {
        if (i == 0 || bigDecimal == null) {
            return ZERO;
        }
        return i > 0 ? bigDecimal : bigDecimal.negate();
    }

    private static BigDecimal sanitize(BigInteger bigInteger, int i) {
        if (i == 0 || bigInteger == null) {
            return ZERO;
        }
        return i > 0 ? new BigDecimal(bigInteger) : new BigDecimal(bigInteger.negate());
    }

    private static void testNonNegative(BigDecimal bigDecimal, DatatypeConstants.Field field) {
        if (bigDecimal != null && bigDecimal.signum() < 0) {
            throw new IllegalArgumentException(DatatypeMessageFormatter.formatMessage(null, "NegativeField", new Object[]{field.toString()}));
        }
    }

    private static void testNonNegative(BigInteger bigInteger, DatatypeConstants.Field field) {
        if (bigInteger != null && bigInteger.signum() < 0) {
            throw new IllegalArgumentException(DatatypeMessageFormatter.formatMessage(null, "NegativeField", new Object[]{field.toString()}));
        }
    }

    private static BigInteger toBigInteger(BigDecimal bigDecimal, boolean z) {
        if (z && bigDecimal.signum() == 0) {
            return null;
        }
        return bigDecimal.unscaledValue();
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
            return "0." + string;
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

    private static BigInteger wrap(int i) {
        if (i == Integer.MIN_VALUE) {
            return null;
        }
        return BigInteger.valueOf(i);
    }

    private Object writeReplace() throws IOException {
        return new SerializedDuration(toString());
    }

    @Override // javax.xml.datatype.Duration
    public Duration add(Duration duration) {
        boolean z = false;
        BigDecimal[] bigDecimalArr = {sanitize((BigInteger) getField(DatatypeConstants.YEARS), getSign()).add(sanitize((BigInteger) duration.getField(DatatypeConstants.YEARS), duration.getSign())), sanitize((BigInteger) getField(DatatypeConstants.MONTHS), getSign()).add(sanitize((BigInteger) duration.getField(DatatypeConstants.MONTHS), duration.getSign())), sanitize((BigInteger) getField(DatatypeConstants.DAYS), getSign()).add(sanitize((BigInteger) duration.getField(DatatypeConstants.DAYS), duration.getSign())), sanitize((BigInteger) getField(DatatypeConstants.HOURS), getSign()).add(sanitize((BigInteger) duration.getField(DatatypeConstants.HOURS), duration.getSign())), sanitize((BigInteger) getField(DatatypeConstants.MINUTES), getSign()).add(sanitize((BigInteger) duration.getField(DatatypeConstants.MINUTES), duration.getSign())), sanitize((BigDecimal) getField(DatatypeConstants.SECONDS), getSign()).add(sanitize((BigDecimal) duration.getField(DatatypeConstants.SECONDS), duration.getSign()))};
        alignSigns(bigDecimalArr, 0, 2);
        alignSigns(bigDecimalArr, 2, 6);
        int iSignum = 0;
        for (int i = 0; i < 6; i++) {
            if (bigDecimalArr[i].signum() * iSignum < 0) {
                throw new IllegalStateException();
            }
            if (iSignum == 0) {
                iSignum = bigDecimalArr[i].signum();
            }
        }
        boolean z2 = iSignum >= 0;
        BigInteger bigInteger = toBigInteger(sanitize(bigDecimalArr[0], iSignum), getField(DatatypeConstants.YEARS) == null && duration.getField(DatatypeConstants.YEARS) == null);
        BigInteger bigInteger2 = toBigInteger(sanitize(bigDecimalArr[1], iSignum), getField(DatatypeConstants.MONTHS) == null && duration.getField(DatatypeConstants.MONTHS) == null);
        BigInteger bigInteger3 = toBigInteger(sanitize(bigDecimalArr[2], iSignum), getField(DatatypeConstants.DAYS) == null && duration.getField(DatatypeConstants.DAYS) == null);
        BigInteger bigInteger4 = toBigInteger(sanitize(bigDecimalArr[3], iSignum), getField(DatatypeConstants.HOURS) == null && duration.getField(DatatypeConstants.HOURS) == null);
        BigDecimal bigDecimalSanitize = sanitize(bigDecimalArr[4], iSignum);
        if (getField(DatatypeConstants.MINUTES) == null && duration.getField(DatatypeConstants.MINUTES) == null) {
            z = true;
        }
        return new DurationImpl(z2, bigInteger, bigInteger2, bigInteger3, bigInteger4, toBigInteger(bigDecimalSanitize, z), (bigDecimalArr[5].signum() == 0 && getField(DatatypeConstants.SECONDS) == null && duration.getField(DatatypeConstants.SECONDS) == null) ? null : sanitize(bigDecimalArr[5], iSignum));
    }

    @Override // javax.xml.datatype.Duration
    public void addTo(Calendar calendar) {
        calendar.add(1, getYears() * this.signum);
        calendar.add(2, getMonths() * this.signum);
        calendar.add(5, getDays() * this.signum);
        calendar.add(10, getHours() * this.signum);
        calendar.add(12, getMinutes() * this.signum);
        calendar.add(13, getSeconds() * this.signum);
        BigDecimal bigDecimal = this.seconds;
        if (bigDecimal != null) {
            calendar.add(14, bigDecimal.subtract(bigDecimal.setScale(0, 1)).movePointRight(3).intValue() * this.signum);
        }
    }

    @Override // javax.xml.datatype.Duration
    public void addTo(Date date) {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(date);
        addTo(gregorianCalendar);
        date.setTime(getCalendarTimeInMillis(gregorianCalendar));
    }

    @Override // javax.xml.datatype.Duration
    public int compare(Duration duration) {
        BigInteger bigIntegerValueOf = BigInteger.valueOf(2147483647L);
        BigInteger bigInteger = this.years;
        if (bigInteger != null && bigInteger.compareTo(bigIntegerValueOf) == 1) {
            throw new UnsupportedOperationException(DatatypeMessageFormatter.formatMessage(null, "TooLarge", new Object[]{getClass().getName() + "#compare(Duration duration)" + DatatypeConstants.YEARS.toString(), this.years.toString()}));
        }
        BigInteger bigInteger2 = this.months;
        if (bigInteger2 != null && bigInteger2.compareTo(bigIntegerValueOf) == 1) {
            throw new UnsupportedOperationException(DatatypeMessageFormatter.formatMessage(null, "TooLarge", new Object[]{getClass().getName() + "#compare(Duration duration)" + DatatypeConstants.MONTHS.toString(), this.months.toString()}));
        }
        BigInteger bigInteger3 = this.days;
        if (bigInteger3 != null && bigInteger3.compareTo(bigIntegerValueOf) == 1) {
            throw new UnsupportedOperationException(DatatypeMessageFormatter.formatMessage(null, "TooLarge", new Object[]{getClass().getName() + "#compare(Duration duration)" + DatatypeConstants.DAYS.toString(), this.days.toString()}));
        }
        BigInteger bigInteger4 = this.hours;
        if (bigInteger4 != null && bigInteger4.compareTo(bigIntegerValueOf) == 1) {
            throw new UnsupportedOperationException(DatatypeMessageFormatter.formatMessage(null, "TooLarge", new Object[]{getClass().getName() + "#compare(Duration duration)" + DatatypeConstants.HOURS.toString(), this.hours.toString()}));
        }
        BigInteger bigInteger5 = this.minutes;
        if (bigInteger5 != null && bigInteger5.compareTo(bigIntegerValueOf) == 1) {
            throw new UnsupportedOperationException(DatatypeMessageFormatter.formatMessage(null, "TooLarge", new Object[]{getClass().getName() + "#compare(Duration duration)" + DatatypeConstants.MINUTES.toString(), this.minutes.toString()}));
        }
        BigDecimal bigDecimal = this.seconds;
        if (bigDecimal != null && bigDecimal.toBigInteger().compareTo(bigIntegerValueOf) == 1) {
            throw new UnsupportedOperationException(DatatypeMessageFormatter.formatMessage(null, "TooLarge", new Object[]{getClass().getName() + "#compare(Duration duration)" + DatatypeConstants.SECONDS.toString(), toString(this.seconds)}));
        }
        BigInteger bigInteger6 = (BigInteger) duration.getField(DatatypeConstants.YEARS);
        if (bigInteger6 != null && bigInteger6.compareTo(bigIntegerValueOf) == 1) {
            throw new UnsupportedOperationException(DatatypeMessageFormatter.formatMessage(null, "TooLarge", new Object[]{getClass().getName() + "#compare(Duration duration)" + DatatypeConstants.YEARS.toString(), bigInteger6.toString()}));
        }
        BigInteger bigInteger7 = (BigInteger) duration.getField(DatatypeConstants.MONTHS);
        if (bigInteger7 != null && bigInteger7.compareTo(bigIntegerValueOf) == 1) {
            throw new UnsupportedOperationException(DatatypeMessageFormatter.formatMessage(null, "TooLarge", new Object[]{getClass().getName() + "#compare(Duration duration)" + DatatypeConstants.MONTHS.toString(), bigInteger7.toString()}));
        }
        BigInteger bigInteger8 = (BigInteger) duration.getField(DatatypeConstants.DAYS);
        if (bigInteger8 != null && bigInteger8.compareTo(bigIntegerValueOf) == 1) {
            throw new UnsupportedOperationException(DatatypeMessageFormatter.formatMessage(null, "TooLarge", new Object[]{getClass().getName() + "#compare(Duration duration)" + DatatypeConstants.DAYS.toString(), bigInteger8.toString()}));
        }
        BigInteger bigInteger9 = (BigInteger) duration.getField(DatatypeConstants.HOURS);
        if (bigInteger9 != null && bigInteger9.compareTo(bigIntegerValueOf) == 1) {
            throw new UnsupportedOperationException(DatatypeMessageFormatter.formatMessage(null, "TooLarge", new Object[]{getClass().getName() + "#compare(Duration duration)" + DatatypeConstants.HOURS.toString(), bigInteger9.toString()}));
        }
        BigInteger bigInteger10 = (BigInteger) duration.getField(DatatypeConstants.MINUTES);
        if (bigInteger10 != null && bigInteger10.compareTo(bigIntegerValueOf) == 1) {
            throw new UnsupportedOperationException(DatatypeMessageFormatter.formatMessage(null, "TooLarge", new Object[]{getClass().getName() + "#compare(Duration duration)" + DatatypeConstants.MINUTES.toString(), bigInteger10.toString()}));
        }
        BigDecimal bigDecimal2 = (BigDecimal) duration.getField(DatatypeConstants.SECONDS);
        BigInteger bigInteger11 = bigDecimal2 != null ? bigDecimal2.toBigInteger() : null;
        if (bigInteger11 != null && bigInteger11.compareTo(bigIntegerValueOf) == 1) {
            throw new UnsupportedOperationException(DatatypeMessageFormatter.formatMessage(null, "TooLarge", new Object[]{getClass().getName() + "#compare(Duration duration)" + DatatypeConstants.SECONDS.toString(), bigInteger11.toString()}));
        }
        GregorianCalendar gregorianCalendar = new GregorianCalendar(XKeySym.XK_Greek_epsilonaccent, 1, 1, 0, 0, 0);
        gregorianCalendar.add(1, getYears() * getSign());
        gregorianCalendar.add(2, getMonths() * getSign());
        gregorianCalendar.add(6, getDays() * getSign());
        gregorianCalendar.add(11, getHours() * getSign());
        gregorianCalendar.add(12, getMinutes() * getSign());
        gregorianCalendar.add(13, getSeconds() * getSign());
        GregorianCalendar gregorianCalendar2 = new GregorianCalendar(XKeySym.XK_Greek_epsilonaccent, 1, 1, 0, 0, 0);
        gregorianCalendar2.add(1, duration.getYears() * duration.getSign());
        gregorianCalendar2.add(2, duration.getMonths() * duration.getSign());
        gregorianCalendar2.add(6, duration.getDays() * duration.getSign());
        gregorianCalendar2.add(11, duration.getHours() * duration.getSign());
        gregorianCalendar2.add(12, duration.getMinutes() * duration.getSign());
        gregorianCalendar2.add(13, duration.getSeconds() * duration.getSign());
        if (gregorianCalendar.equals(gregorianCalendar2)) {
            return 0;
        }
        return compareDates(this, duration);
    }

    @Override // javax.xml.datatype.Duration
    public int getDays() {
        return getInt(DatatypeConstants.DAYS);
    }

    @Override // javax.xml.datatype.Duration
    public Number getField(DatatypeConstants.Field field) {
        if (field == null) {
            throw new NullPointerException(DatatypeMessageFormatter.formatMessage(null, "FieldCannotBeNull", new Object[]{"javax.xml.datatype.Duration#isSet(DatatypeConstants.Field field) "}));
        }
        if (field == DatatypeConstants.YEARS) {
            return this.years;
        }
        if (field == DatatypeConstants.MONTHS) {
            return this.months;
        }
        if (field == DatatypeConstants.DAYS) {
            return this.days;
        }
        if (field == DatatypeConstants.HOURS) {
            return this.hours;
        }
        if (field == DatatypeConstants.MINUTES) {
            return this.minutes;
        }
        if (field == DatatypeConstants.SECONDS) {
            return this.seconds;
        }
        throw new IllegalArgumentException(DatatypeMessageFormatter.formatMessage(null, "UnknownField", new Object[]{"javax.xml.datatype.Duration#(getSet(DatatypeConstants.Field field)", field.toString()}));
    }

    @Override // javax.xml.datatype.Duration
    public int getHours() {
        return getInt(DatatypeConstants.HOURS);
    }

    @Override // javax.xml.datatype.Duration
    public int getMinutes() {
        return getInt(DatatypeConstants.MINUTES);
    }

    @Override // javax.xml.datatype.Duration
    public int getMonths() {
        return getInt(DatatypeConstants.MONTHS);
    }

    @Override // javax.xml.datatype.Duration
    public int getSeconds() {
        return getInt(DatatypeConstants.SECONDS);
    }

    @Override // javax.xml.datatype.Duration
    public int getSign() {
        return this.signum;
    }

    @Override // javax.xml.datatype.Duration
    public long getTimeInMillis(Calendar calendar) {
        Calendar calendar2 = (Calendar) calendar.clone();
        addTo(calendar2);
        return getCalendarTimeInMillis(calendar2) - getCalendarTimeInMillis(calendar);
    }

    @Override // javax.xml.datatype.Duration
    public long getTimeInMillis(Date date) {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(date);
        addTo(gregorianCalendar);
        return getCalendarTimeInMillis(gregorianCalendar) - date.getTime();
    }

    @Override // javax.xml.datatype.Duration
    public int getYears() {
        return getInt(DatatypeConstants.YEARS);
    }

    @Override // javax.xml.datatype.Duration
    public int hashCode() {
        GregorianCalendar gregorianCalendar = TEST_POINTS[0].toGregorianCalendar();
        addTo(gregorianCalendar);
        return (int) getCalendarTimeInMillis(gregorianCalendar);
    }

    @Override // javax.xml.datatype.Duration
    public boolean isSet(DatatypeConstants.Field field) {
        if (field == null) {
            throw new NullPointerException(DatatypeMessageFormatter.formatMessage(null, "FieldCannotBeNull", new Object[]{"javax.xml.datatype.Duration#isSet(DatatypeConstants.Field field)"}));
        }
        if (field == DatatypeConstants.YEARS) {
            return this.years != null;
        }
        if (field == DatatypeConstants.MONTHS) {
            return this.months != null;
        }
        if (field == DatatypeConstants.DAYS) {
            return this.days != null;
        }
        if (field == DatatypeConstants.HOURS) {
            return this.hours != null;
        }
        if (field == DatatypeConstants.MINUTES) {
            return this.minutes != null;
        }
        if (field == DatatypeConstants.SECONDS) {
            return this.seconds != null;
        }
        throw new IllegalArgumentException(DatatypeMessageFormatter.formatMessage(null, "UnknownField", new Object[]{"javax.xml.datatype.Duration#isSet(DatatypeConstants.Field field)", field.toString()}));
    }

    @Override // javax.xml.datatype.Duration
    public Duration multiply(int i) {
        return multiply(BigDecimal.valueOf(i));
    }

    @Override // javax.xml.datatype.Duration
    public Duration multiply(BigDecimal bigDecimal) {
        BigDecimal bigDecimalMultiply = ZERO;
        int iSignum = bigDecimal.signum();
        BigDecimal bigDecimalAbs = bigDecimal.abs();
        BigDecimal[] bigDecimalArr = new BigDecimal[6];
        for (int i = 0; i < 5; i++) {
            BigDecimal bigDecimalAdd = getFieldAsBigDecimal(FIELDS[i]).multiply(bigDecimalAbs).add(bigDecimalMultiply);
            BigDecimal scale = bigDecimalAdd.setScale(0, 1);
            bigDecimalArr[i] = scale;
            BigDecimal bigDecimalSubtract = bigDecimalAdd.subtract(scale);
            if (i != 1) {
                bigDecimalMultiply = bigDecimalSubtract.multiply(FACTORS[i]);
            } else {
                if (bigDecimalSubtract.signum() != 0) {
                    throw new IllegalStateException();
                }
                bigDecimalMultiply = ZERO;
            }
        }
        BigDecimal bigDecimal2 = this.seconds;
        if (bigDecimal2 != null) {
            bigDecimalArr[5] = bigDecimal2.multiply(bigDecimalAbs).add(bigDecimalMultiply);
        } else {
            bigDecimalArr[5] = bigDecimalMultiply;
        }
        return new DurationImpl(this.signum * iSignum >= 0, toBigInteger(bigDecimalArr[0], this.years == null), toBigInteger(bigDecimalArr[1], this.months == null), toBigInteger(bigDecimalArr[2], this.days == null), toBigInteger(bigDecimalArr[3], this.hours == null), toBigInteger(bigDecimalArr[4], this.minutes == null), (bigDecimalArr[5].signum() == 0 && this.seconds == null) ? null : bigDecimalArr[5]);
    }

    @Override // javax.xml.datatype.Duration
    public Duration negate() {
        return new DurationImpl(this.signum <= 0, this.years, this.months, this.days, this.hours, this.minutes, this.seconds);
    }

    @Override // javax.xml.datatype.Duration
    public Duration normalizeWith(Calendar calendar) {
        Calendar calendar2 = (Calendar) calendar.clone();
        calendar2.add(1, getYears() * this.signum);
        calendar2.add(2, getMonths() * this.signum);
        calendar2.add(5, getDays() * this.signum);
        int calendarTimeInMillis = (int) ((getCalendarTimeInMillis(calendar2) - getCalendarTimeInMillis(calendar)) / DateUtils.MILLIS_PER_DAY);
        return new DurationImpl(calendarTimeInMillis >= 0, (BigInteger) null, (BigInteger) null, wrap(Math.abs(calendarTimeInMillis)), (BigInteger) getField(DatatypeConstants.HOURS), (BigInteger) getField(DatatypeConstants.MINUTES), (BigDecimal) getField(DatatypeConstants.SECONDS));
    }

    public int signum() {
        return this.signum;
    }

    @Override // javax.xml.datatype.Duration
    public Duration subtract(Duration duration) {
        return add(duration.negate());
    }

    @Override // javax.xml.datatype.Duration
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        if (this.signum < 0) {
            stringBuffer.append('-');
        }
        stringBuffer.append('P');
        BigInteger bigInteger = this.years;
        if (bigInteger != null) {
            stringBuffer.append(bigInteger).append('Y');
        }
        BigInteger bigInteger2 = this.months;
        if (bigInteger2 != null) {
            stringBuffer.append(bigInteger2).append('M');
        }
        BigInteger bigInteger3 = this.days;
        if (bigInteger3 != null) {
            stringBuffer.append(bigInteger3).append(Util.C_DOUBLE);
        }
        if (this.hours != null || this.minutes != null || this.seconds != null) {
            stringBuffer.append(Util.C_TYPE_VARIABLE);
            BigInteger bigInteger4 = this.hours;
            if (bigInteger4 != null) {
                stringBuffer.append(bigInteger4).append('H');
            }
            BigInteger bigInteger5 = this.minutes;
            if (bigInteger5 != null) {
                stringBuffer.append(bigInteger5).append('M');
            }
            BigDecimal bigDecimal = this.seconds;
            if (bigDecimal != null) {
                stringBuffer.append(toString(bigDecimal)).append(Util.C_SHORT);
            }
        }
        return stringBuffer.toString();
    }
}
