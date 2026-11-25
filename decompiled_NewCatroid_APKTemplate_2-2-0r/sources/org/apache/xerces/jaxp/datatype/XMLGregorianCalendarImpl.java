package org.apache.xerces.jaxp.datatype;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.time.TimeZones;
import org.apache.xerces.util.DatatypeMessageFormatter;
import org.eclipse.jdt.internal.compiler.classfmt.ExternalAnnotationProvider;

/* JADX INFO: loaded from: classes13.dex */
class XMLGregorianCalendarImpl extends XMLGregorianCalendar implements Serializable, Cloneable {
    private static final int BILLION_I = 1000000000;
    private static final int DAY = 2;
    private static final int HOUR = 3;
    private static final int MILLISECOND = 6;
    private static final int MINUTE = 4;
    private static final int MONTH = 1;
    private static final int SECOND = 5;
    private static final int TIMEZONE = 7;
    private static final int YEAR = 0;
    private static final long serialVersionUID = 3905403108073447394L;
    private BigInteger orig_eon;
    private BigDecimal orig_fracSeconds;
    private static final BigInteger BILLION_B = BigInteger.valueOf(1000000000);
    private static final Date PURE_GREGORIAN_CHANGE = new Date(Long.MIN_VALUE);
    private static final int[] MIN_FIELD_VALUE = {Integer.MIN_VALUE, 1, 1, 0, 0, 0, 0, DatatypeConstants.MAX_TIMEZONE_OFFSET};
    private static final int[] MAX_FIELD_VALUE = {Integer.MAX_VALUE, 12, 31, 24, 59, 60, 999, DatatypeConstants.MIN_TIMEZONE_OFFSET};
    private static final String[] FIELD_NAME = {"Year", "Month", "Day", "Hour", "Minute", "Second", "Millisecond", "Timezone"};
    public static final XMLGregorianCalendar LEAP_YEAR_DEFAULT = createDateTime(400, 1, 1, 0, 0, 0, Integer.MIN_VALUE, Integer.MIN_VALUE);
    private static final BigInteger FOUR = BigInteger.valueOf(4);
    private static final BigInteger HUNDRED = BigInteger.valueOf(100);
    private static final BigInteger FOUR_HUNDRED = BigInteger.valueOf(400);
    private static final BigInteger SIXTY = BigInteger.valueOf(60);
    private static final BigInteger TWENTY_FOUR = BigInteger.valueOf(24);
    private static final BigInteger TWELVE = BigInteger.valueOf(12);
    private static final BigDecimal DECIMAL_ZERO = BigDecimal.valueOf(0L);
    private static final BigDecimal DECIMAL_ONE = BigDecimal.valueOf(1L);
    private static final BigDecimal DECIMAL_SIXTY = BigDecimal.valueOf(60L);
    private int orig_year = Integer.MIN_VALUE;
    private int orig_month = Integer.MIN_VALUE;
    private int orig_day = Integer.MIN_VALUE;
    private int orig_hour = Integer.MIN_VALUE;
    private int orig_minute = Integer.MIN_VALUE;
    private int orig_second = Integer.MIN_VALUE;
    private int orig_timezone = Integer.MIN_VALUE;
    private BigInteger eon = null;
    private int year = Integer.MIN_VALUE;
    private int month = Integer.MIN_VALUE;
    private int day = Integer.MIN_VALUE;
    private int timezone = Integer.MIN_VALUE;
    private int hour = Integer.MIN_VALUE;
    private int minute = Integer.MIN_VALUE;
    private int second = Integer.MIN_VALUE;
    private BigDecimal fractionalSecond = null;

    private static class DaysInMonth {
        private static final int[] table = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

        private DaysInMonth() {
        }
    }

    private final class Parser {
        private int fidx;
        private final int flen;
        private final String format;
        private final String value;
        private int vidx;
        private final int vlen;

        private Parser(String str, String str2) {
            this.format = str;
            this.value = str2;
            this.flen = str.length();
            this.vlen = str2.length();
        }

        private BigDecimal parseBigDecimal() throws IllegalArgumentException {
            int i = this.vidx;
            if (peek() != '.') {
                throw new IllegalArgumentException(this.value);
            }
            int i2 = this.vidx;
            while (true) {
                this.vidx = i2 + 1;
                if (!XMLGregorianCalendarImpl.isDigit(peek())) {
                    return new BigDecimal(this.value.substring(i, this.vidx));
                }
                i2 = this.vidx;
            }
        }

        private int parseInt(int i, int i2) throws IllegalArgumentException {
            int i3 = this.vidx;
            while (XMLGregorianCalendarImpl.isDigit(peek())) {
                int i4 = this.vidx;
                if (i4 - i3 >= i2) {
                    break;
                }
                this.vidx = i4 + 1;
            }
            int i5 = this.vidx;
            if (i5 - i3 >= i) {
                return Integer.parseInt(this.value.substring(i3, i5));
            }
            throw new IllegalArgumentException(this.value);
        }

        private void parseYear() throws IllegalArgumentException {
            int i;
            int i2 = this.vidx;
            if (peek() == '-') {
                this.vidx++;
                i = 1;
            } else {
                i = 0;
            }
            while (XMLGregorianCalendarImpl.isDigit(peek())) {
                this.vidx++;
            }
            int i3 = this.vidx;
            int i4 = (i3 - i2) - i;
            if (i4 < 4) {
                throw new IllegalArgumentException(this.value);
            }
            String strSubstring = this.value.substring(i2, i3);
            if (i4 < 10) {
                XMLGregorianCalendarImpl.this.setYear(Integer.parseInt(strSubstring));
            } else {
                XMLGregorianCalendarImpl.this.setYear(new BigInteger(strSubstring));
            }
        }

        private char peek() throws IllegalArgumentException {
            int i = this.vidx;
            if (i == this.vlen) {
                return (char) 65535;
            }
            return this.value.charAt(i);
        }

        private char read() throws IllegalArgumentException {
            int i = this.vidx;
            if (i == this.vlen) {
                throw new IllegalArgumentException(this.value);
            }
            String str = this.value;
            this.vidx = i + 1;
            return str.charAt(i);
        }

        private void skip(char c) throws IllegalArgumentException {
            if (read() != c) {
                throw new IllegalArgumentException(this.value);
            }
        }

        public void parse() throws IllegalArgumentException {
            while (true) {
                int i = this.fidx;
                if (i >= this.flen) {
                    if (this.vidx != this.vlen) {
                        throw new IllegalArgumentException(this.value);
                    }
                    return;
                }
                String str = this.format;
                this.fidx = i + 1;
                char cCharAt = str.charAt(i);
                if (cCharAt != '%') {
                    skip(cCharAt);
                } else {
                    String str2 = this.format;
                    int i2 = this.fidx;
                    this.fidx = i2 + 1;
                    char cCharAt2 = str2.charAt(i2);
                    if (cCharAt2 == 'D') {
                        XMLGregorianCalendarImpl.this.setDay(parseInt(2, 2));
                    } else if (cCharAt2 == 'M') {
                        XMLGregorianCalendarImpl.this.setMonth(parseInt(2, 2));
                    } else if (cCharAt2 == 'Y') {
                        parseYear();
                    } else if (cCharAt2 == 'h') {
                        XMLGregorianCalendarImpl.this.setHour(parseInt(2, 2));
                    } else if (cCharAt2 == 'm') {
                        XMLGregorianCalendarImpl.this.setMinute(parseInt(2, 2));
                    } else if (cCharAt2 == 's') {
                        XMLGregorianCalendarImpl.this.setSecond(parseInt(2, 2));
                        if (peek() == '.') {
                            XMLGregorianCalendarImpl.this.setFractionalSecond(parseBigDecimal());
                        }
                    } else {
                        if (cCharAt2 != 'z') {
                            throw new InternalError();
                        }
                        char cPeek = peek();
                        if (cPeek == 'Z') {
                            this.vidx++;
                            XMLGregorianCalendarImpl.this.setTimezone(0);
                        } else if (cPeek == '+' || cPeek == '-') {
                            this.vidx++;
                            int i3 = parseInt(2, 2);
                            skip(':');
                            XMLGregorianCalendarImpl.this.setTimezone(((i3 * 60) + parseInt(2, 2)) * (cPeek != '+' ? -1 : 1));
                        }
                    }
                }
            }
        }
    }

    public XMLGregorianCalendarImpl() {
    }

    private XMLGregorianCalendarImpl(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        setYear(i);
        setMonth(i2);
        setDay(i3);
        setTime(i4, i5, i6);
        setTimezone(i8);
        setFractionalSecond(i7 != Integer.MIN_VALUE ? BigDecimal.valueOf(i7, 3) : null);
        if (!isValid()) {
            throw new IllegalArgumentException(DatatypeMessageFormatter.formatMessage(null, "InvalidXGCValue-milli", new Object[]{new Integer(i), new Integer(i2), new Integer(i3), new Integer(i4), new Integer(i5), new Integer(i6), new Integer(i7), new Integer(i8)}));
        }
        save();
    }

    protected XMLGregorianCalendarImpl(String str) throws IllegalArgumentException {
        String str2;
        int length = str.length();
        if (str.indexOf(84) != -1) {
            str2 = "%Y-%M-%DT%h:%m:%s%z";
        } else if (length >= 3 && str.charAt(2) == ':') {
            str2 = "%h:%m:%s%z";
        } else if (!str.startsWith("--")) {
            length = str.indexOf(58) != -1 ? length - 6 : length;
            int i = 0;
            for (int i2 = 1; i2 < length; i2++) {
                if (str.charAt(i2) == '-') {
                    i++;
                }
            }
            str2 = i == 0 ? "%Y%z" : i == 1 ? "%Y-%M%z" : "%Y-%M-%D%z";
        } else if (length >= 3 && str.charAt(2) == '-') {
            str2 = "---%D%z";
        } else if (length == 4 || (length >= 6 && (str.charAt(4) == '+' || (str.charAt(4) == '-' && (str.charAt(5) == '-' || length == 10))))) {
            try {
                new Parser("--%M--%z", str).parse();
                if (!isValid()) {
                    throw new IllegalArgumentException(DatatypeMessageFormatter.formatMessage(null, "InvalidXGCRepresentation", new Object[]{str}));
                }
                save();
                return;
            } catch (IllegalArgumentException unused) {
                str2 = "--%M%z";
            }
        } else {
            str2 = "--%M-%D%z";
        }
        new Parser(str2, str).parse();
        if (!isValid()) {
            throw new IllegalArgumentException(DatatypeMessageFormatter.formatMessage(null, "InvalidXGCRepresentation", new Object[]{str}));
        }
        save();
    }

    protected XMLGregorianCalendarImpl(BigInteger bigInteger, int i, int i2, int i3, int i4, int i5, BigDecimal bigDecimal, int i6) {
        setYear(bigInteger);
        setMonth(i);
        setDay(i2);
        setTime(i3, i4, i5, bigDecimal);
        setTimezone(i6);
        if (!isValid()) {
            throw new IllegalArgumentException(DatatypeMessageFormatter.formatMessage(null, "InvalidXGCValue-fractional", new Object[]{bigInteger, new Integer(i), new Integer(i2), new Integer(i3), new Integer(i4), new Integer(i5), bigDecimal, new Integer(i6)}));
        }
        save();
    }

    public XMLGregorianCalendarImpl(GregorianCalendar gregorianCalendar) {
        int i = gregorianCalendar.get(1);
        setYear(gregorianCalendar.get(0) == 0 ? -i : i);
        setMonth(gregorianCalendar.get(2) + 1);
        setDay(gregorianCalendar.get(5));
        setTime(gregorianCalendar.get(11), gregorianCalendar.get(12), gregorianCalendar.get(13), gregorianCalendar.get(14));
        setTimezone((gregorianCalendar.get(15) + gregorianCalendar.get(16)) / 60000);
        save();
    }

    private void checkFieldValueConstraint(int i, int i2) throws IllegalArgumentException {
        if ((i2 < MIN_FIELD_VALUE[i] && i2 != Integer.MIN_VALUE) || i2 > MAX_FIELD_VALUE[i]) {
            throw new IllegalArgumentException(DatatypeMessageFormatter.formatMessage(null, "InvalidFieldValue", new Object[]{new Integer(i2), FIELD_NAME[i]}));
        }
    }

    private static int compareField(int i, int i2) {
        if (i == i2) {
            return 0;
        }
        if (i == Integer.MIN_VALUE || i2 == Integer.MIN_VALUE) {
            return 2;
        }
        return i < i2 ? -1 : 1;
    }

    private static int compareField(BigDecimal bigDecimal, BigDecimal bigDecimal2) {
        if (bigDecimal == bigDecimal2) {
            return 0;
        }
        if (bigDecimal == null) {
            bigDecimal = DECIMAL_ZERO;
        }
        if (bigDecimal2 == null) {
            bigDecimal2 = DECIMAL_ZERO;
        }
        return bigDecimal.compareTo(bigDecimal2);
    }

    private static int compareField(BigInteger bigInteger, BigInteger bigInteger2) {
        if (bigInteger == null) {
            return bigInteger2 == null ? 0 : 2;
        }
        if (bigInteger2 == null) {
            return 2;
        }
        return bigInteger.compareTo(bigInteger2);
    }

    public static XMLGregorianCalendar createDate(int i, int i2, int i3, int i4) {
        return new XMLGregorianCalendarImpl(i, i2, i3, Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE, i4);
    }

    public static XMLGregorianCalendar createDateTime(int i, int i2, int i3, int i4, int i5, int i6) {
        return new XMLGregorianCalendarImpl(i, i2, i3, i4, i5, i6, Integer.MIN_VALUE, Integer.MIN_VALUE);
    }

    public static XMLGregorianCalendar createDateTime(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        return new XMLGregorianCalendarImpl(i, i2, i3, i4, i5, i6, i7, i8);
    }

    public static XMLGregorianCalendar createDateTime(BigInteger bigInteger, int i, int i2, int i3, int i4, int i5, BigDecimal bigDecimal, int i6) {
        return new XMLGregorianCalendarImpl(bigInteger, i, i2, i3, i4, i5, bigDecimal, i6);
    }

    public static XMLGregorianCalendar createTime(int i, int i2, int i3, int i4) {
        return new XMLGregorianCalendarImpl(Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE, i, i2, i3, Integer.MIN_VALUE, i4);
    }

    public static XMLGregorianCalendar createTime(int i, int i2, int i3, int i4, int i5) {
        return new XMLGregorianCalendarImpl(Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE, i, i2, i3, i4, i5);
    }

    public static XMLGregorianCalendar createTime(int i, int i2, int i3, BigDecimal bigDecimal, int i4) {
        return new XMLGregorianCalendarImpl((BigInteger) null, Integer.MIN_VALUE, Integer.MIN_VALUE, i, i2, i3, bigDecimal, i4);
    }

    private String format(String str) {
        int day;
        StringBuffer stringBuffer = new StringBuffer();
        int length = str.length();
        int i = 0;
        while (i < length) {
            int i2 = i + 1;
            char cCharAt = str.charAt(i);
            if (cCharAt != '%') {
                stringBuffer.append(cCharAt);
                i = i2;
            } else {
                i += 2;
                char cCharAt2 = str.charAt(i2);
                if (cCharAt2 == 'D') {
                    day = getDay();
                } else if (cCharAt2 == 'M') {
                    day = getMonth();
                } else if (cCharAt2 != 'Y') {
                    if (cCharAt2 == 'h') {
                        day = getHour();
                    } else if (cCharAt2 == 'm') {
                        day = getMinute();
                    } else if (cCharAt2 == 's') {
                        printNumber(stringBuffer, getSecond(), 2);
                        if (getFractionalSecond() != null) {
                            String string = toString(getFractionalSecond());
                            stringBuffer.append(string.substring(1, string.length()));
                        }
                    } else {
                        if (cCharAt2 != 'z') {
                            throw new InternalError();
                        }
                        int timezone = getTimezone();
                        if (timezone == 0) {
                            stringBuffer.append('Z');
                        } else if (timezone != Integer.MIN_VALUE) {
                            if (timezone < 0) {
                                stringBuffer.append('-');
                                timezone *= -1;
                            } else {
                                stringBuffer.append('+');
                            }
                            printNumber(stringBuffer, timezone / 60, 2);
                            stringBuffer.append(':');
                            day = timezone % 60;
                        }
                    }
                } else if (this.eon == null) {
                    int i3 = this.year;
                    if (i3 < 0) {
                        stringBuffer.append('-');
                        i3 = -this.year;
                    }
                    printNumber(stringBuffer, i3, 4);
                } else {
                    printNumber(stringBuffer, getEonAndYear(), 4);
                }
                printNumber(stringBuffer, day, 2);
            }
        }
        return stringBuffer.toString();
    }

    private BigDecimal getSeconds() {
        int i = this.second;
        if (i == Integer.MIN_VALUE) {
            return DECIMAL_ZERO;
        }
        BigDecimal bigDecimalValueOf = BigDecimal.valueOf(i);
        BigDecimal bigDecimal = this.fractionalSecond;
        return bigDecimal != null ? bigDecimalValueOf.add(bigDecimal) : bigDecimalValueOf;
    }

    private static int internalCompare(XMLGregorianCalendar xMLGregorianCalendar, XMLGregorianCalendar xMLGregorianCalendar2) {
        if (xMLGregorianCalendar.getEon() == xMLGregorianCalendar2.getEon()) {
            int iCompareField = compareField(xMLGregorianCalendar.getYear(), xMLGregorianCalendar2.getYear());
            if (iCompareField != 0) {
                return iCompareField;
            }
        } else {
            int iCompareField2 = compareField(xMLGregorianCalendar.getEonAndYear(), xMLGregorianCalendar2.getEonAndYear());
            if (iCompareField2 != 0) {
                return iCompareField2;
            }
        }
        int iCompareField3 = compareField(xMLGregorianCalendar.getMonth(), xMLGregorianCalendar2.getMonth());
        if (iCompareField3 != 0) {
            return iCompareField3;
        }
        int iCompareField4 = compareField(xMLGregorianCalendar.getDay(), xMLGregorianCalendar2.getDay());
        if (iCompareField4 != 0) {
            return iCompareField4;
        }
        int iCompareField5 = compareField(xMLGregorianCalendar.getHour(), xMLGregorianCalendar2.getHour());
        if (iCompareField5 != 0) {
            return iCompareField5;
        }
        int iCompareField6 = compareField(xMLGregorianCalendar.getMinute(), xMLGregorianCalendar2.getMinute());
        if (iCompareField6 != 0) {
            return iCompareField6;
        }
        int iCompareField7 = compareField(xMLGregorianCalendar.getSecond(), xMLGregorianCalendar2.getSecond());
        return iCompareField7 != 0 ? iCompareField7 : compareField(xMLGregorianCalendar.getFractionalSecond(), xMLGregorianCalendar2.getFractionalSecond());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isDigit(char c) {
        return '0' <= c && c <= '9';
    }

    private static int maximumDayInMonthFor(int i, int i2) {
        if (i2 != 2) {
            return DaysInMonth.table[i2];
        }
        if (i % 400 == 0) {
            return 29;
        }
        if (i % 100 == 0 || i % 4 != 0) {
            return DaysInMonth.table[2];
        }
        return 29;
    }

    private static int maximumDayInMonthFor(BigInteger bigInteger, int i) {
        if (i != 2) {
            return DaysInMonth.table[i];
        }
        if (bigInteger.mod(FOUR_HUNDRED).equals(BigInteger.ZERO)) {
            return 29;
        }
        if (bigInteger.mod(HUNDRED).equals(BigInteger.ZERO) || !bigInteger.mod(FOUR).equals(BigInteger.ZERO)) {
            return DaysInMonth.table[i];
        }
        return 29;
    }

    private XMLGregorianCalendar normalizeToTimezone(XMLGregorianCalendar xMLGregorianCalendar, int i) {
        XMLGregorianCalendar xMLGregorianCalendar2 = (XMLGregorianCalendar) xMLGregorianCalendar.clone();
        int i2 = -i;
        boolean z = i2 >= 0;
        if (i2 < 0) {
            i2 = -i2;
        }
        xMLGregorianCalendar2.add(new DurationImpl(z, 0, 0, 0, 0, i2, 0));
        xMLGregorianCalendar2.setTimezone(0);
        return xMLGregorianCalendar2;
    }

    public static XMLGregorianCalendar parse(String str) {
        return new XMLGregorianCalendarImpl(str);
    }

    private void printNumber(StringBuffer stringBuffer, int i, int i2) {
        String strValueOf = String.valueOf(i);
        for (int length = strValueOf.length(); length < i2; length++) {
            stringBuffer.append(ExternalAnnotationProvider.NULLABLE);
        }
        stringBuffer.append(strValueOf);
    }

    private void printNumber(StringBuffer stringBuffer, BigInteger bigInteger, int i) {
        String string = bigInteger.toString();
        for (int length = string.length(); length < i; length++) {
            stringBuffer.append(ExternalAnnotationProvider.NULLABLE);
        }
        stringBuffer.append(string);
    }

    static BigInteger sanitize(Number number, int i) {
        if (i == 0 || number == null) {
            return BigInteger.ZERO;
        }
        BigInteger bigInteger = (BigInteger) number;
        return i < 0 ? bigInteger.negate() : bigInteger;
    }

    private void save() {
        this.orig_eon = this.eon;
        this.orig_year = this.year;
        this.orig_month = this.month;
        this.orig_day = this.day;
        this.orig_hour = this.hour;
        this.orig_minute = this.minute;
        this.orig_second = this.second;
        this.orig_fracSeconds = this.fractionalSecond;
        this.orig_timezone = this.timezone;
    }

    private void setEon(BigInteger bigInteger) {
        if (bigInteger != null && bigInteger.compareTo(BigInteger.ZERO) == 0) {
            bigInteger = null;
        }
        this.eon = bigInteger;
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

    private Object writeReplace() throws IOException {
        return new SerializedXMLGregorianCalendar(toXMLFormat());
    }

    /* JADX WARN: Code duplicated, block: B:38:0x01d7  */
    /* JADX WARN: Code duplicated, block: B:40:0x01dc  */
    /* JADX WARN: Code duplicated, block: B:41:0x01e6  */
    /* JADX WARN: Code duplicated, block: B:43:0x0205  */
    /* JADX WARN: Code duplicated, block: B:45:0x021c  */
    /* JADX WARN: Code duplicated, block: B:48:0x023d  */
    /* JADX WARN: Code duplicated, block: B:49:0x0254  */
    /* JADX WARN: Code duplicated, block: B:55:0x0279  */
    /* JADX WARN: Code duplicated, block: B:57:0x027d  */
    /* JADX WARN: Code duplicated, block: B:59:0x0280  */
    /* JADX WARN: Code duplicated, block: B:61:0x0283  */
    /* JADX WARN: Code duplicated, block: B:63:0x0286 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:64:0x0288 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:65:0x028a A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:67:0x028d  */
    /* JADX WARN: Code duplicated, block: B:68:0x0295  */
    /* JADX WARN: Code duplicated, block: B:69:0x0299  */
    /* JADX WARN: Code duplicated, block: B:70:0x029d  */
    /* JADX WARN: Code duplicated, block: B:71:0x02a1  */
    /* JADX WARN: Code duplicated, block: B:72:0x02a6  */
    /* JADX WARN: Code duplicated, block: B:73:0x02ac  */
    /* JADX WARN: Code duplicated, block: B:76:0x026f A[EDGE_INSN: B:76:0x026f->B:53:0x026f BREAK  A[LOOP:0: B:36:0x01cf->B:78:0x01cf], SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:77:0x025d A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:79:0x01cf A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:89:0x02af A[SYNTHETIC] */
    @Override // javax.xml.datatype.XMLGregorianCalendar
    public void add(Duration duration) {
        BigDecimal seconds;
        long j;
        BigInteger bigIntegerValueOf;
        BigInteger bigIntegerAdd;
        int i;
        int month;
        int i2;
        int iIntValue;
        int i3;
        BigInteger bigIntegerSubtract;
        int month2;
        boolean[] zArr = {false, false, false, false, false, false};
        int sign = duration.getSign();
        int month3 = getMonth();
        if (month3 == Integer.MIN_VALUE) {
            month3 = MIN_FIELD_VALUE[1];
            zArr[1] = true;
        }
        BigInteger bigIntegerAdd2 = BigInteger.valueOf(month3).add(sanitize(duration.getField(DatatypeConstants.MONTHS), sign));
        BigInteger bigIntegerSubtract2 = bigIntegerAdd2.subtract(BigInteger.ONE);
        BigInteger bigInteger = TWELVE;
        setMonth(bigIntegerSubtract2.mod(bigInteger).intValue() + 1);
        BigInteger bigInteger2 = new BigDecimal(bigIntegerAdd2.subtract(BigInteger.ONE)).divide(new BigDecimal(bigInteger), 3).toBigInteger();
        BigInteger eonAndYear = getEonAndYear();
        if (eonAndYear == null) {
            zArr[0] = true;
            eonAndYear = BigInteger.ZERO;
        }
        setYear(eonAndYear.add(sanitize(duration.getField(DatatypeConstants.YEARS), sign)).add(bigInteger2));
        if (getSecond() == Integer.MIN_VALUE) {
            zArr[5] = true;
            seconds = DECIMAL_ZERO;
        } else {
            seconds = getSeconds();
        }
        BigDecimal bigDecimalAdd = seconds.add(DurationImpl.sanitize((BigDecimal) duration.getField(DatatypeConstants.SECONDS), sign));
        BigDecimal bigDecimal = new BigDecimal(bigDecimalAdd.toBigInteger());
        BigDecimal bigDecimal2 = DECIMAL_SIXTY;
        BigDecimal bigDecimal3 = new BigDecimal(bigDecimal.divide(bigDecimal2, 3).toBigInteger());
        BigDecimal bigDecimalSubtract = bigDecimalAdd.subtract(bigDecimal3.multiply(bigDecimal2));
        BigInteger bigInteger3 = bigDecimal3.toBigInteger();
        setSecond(bigDecimalSubtract.intValue());
        BigDecimal bigDecimalSubtract2 = bigDecimalSubtract.subtract(new BigDecimal(BigInteger.valueOf(getSecond())));
        if (bigDecimalSubtract2.compareTo(DECIMAL_ZERO) < 0) {
            setFractionalSecond(DECIMAL_ONE.add(bigDecimalSubtract2));
            if (getSecond() == 0) {
                setSecond(59);
                bigInteger3 = bigInteger3.subtract(BigInteger.ONE);
            } else {
                setSecond(getSecond() - 1);
            }
        } else {
            setFractionalSecond(bigDecimalSubtract2);
        }
        int minute = getMinute();
        if (minute == Integer.MIN_VALUE) {
            zArr[4] = true;
            minute = MIN_FIELD_VALUE[4];
        }
        BigInteger bigIntegerAdd3 = BigInteger.valueOf(minute).add(sanitize(duration.getField(DatatypeConstants.MINUTES), sign)).add(bigInteger3);
        setMinute(bigIntegerAdd3.mod(SIXTY).intValue());
        BigInteger bigInteger4 = new BigDecimal(bigIntegerAdd3).divide(bigDecimal2, 3).toBigInteger();
        int hour = getHour();
        if (hour == Integer.MIN_VALUE) {
            zArr[3] = true;
            hour = MIN_FIELD_VALUE[3];
        }
        BigInteger bigIntegerAdd4 = BigInteger.valueOf(hour).add(sanitize(duration.getField(DatatypeConstants.HOURS), sign)).add(bigInteger4);
        BigInteger bigInteger5 = TWENTY_FOUR;
        setHour(bigIntegerAdd4.mod(bigInteger5).intValue());
        BigInteger bigInteger6 = new BigDecimal(bigIntegerAdd4).divide(new BigDecimal(bigInteger5), 3).toBigInteger();
        int day = getDay();
        if (day == Integer.MIN_VALUE) {
            zArr[2] = true;
            day = MIN_FIELD_VALUE[2];
        }
        BigInteger bigIntegerSanitize = sanitize(duration.getField(DatatypeConstants.DAYS), sign);
        int iMaximumDayInMonthFor = maximumDayInMonthFor(getEonAndYear(), getMonth());
        if (day <= iMaximumDayInMonthFor) {
            if (day < 1) {
                bigIntegerValueOf = BigInteger.ONE;
            } else {
                j = day;
            }
            bigIntegerAdd = bigIntegerValueOf.add(bigIntegerSanitize).add(bigInteger6);
            while (true) {
                if (bigIntegerAdd.compareTo(BigInteger.ONE) >= 0) {
                    if (bigIntegerAdd.compareTo(BigInteger.valueOf(maximumDayInMonthFor(getEonAndYear(), getMonth()))) > 0) {
                        break;
                    }
                    bigIntegerAdd = bigIntegerAdd.add(BigInteger.valueOf(-maximumDayInMonthFor(getEonAndYear(), getMonth())));
                    i = 1;
                } else {
                    if (this.month >= 2) {
                        bigIntegerSubtract = getEonAndYear();
                        month2 = getMonth() - 1;
                    } else {
                        bigIntegerSubtract = getEonAndYear().subtract(BigInteger.valueOf(1L));
                        month2 = 12;
                    }
                    bigIntegerAdd = bigIntegerAdd.add(BigInteger.valueOf(maximumDayInMonthFor(bigIntegerSubtract, month2)));
                    i = -1;
                }
                month = (getMonth() + i) - 1;
                i2 = month % 12;
                if (i2 < 0) {
                    i3 = i2 + 13;
                    iIntValue = BigDecimal.valueOf(month).divide(new BigDecimal(TWELVE), 0).intValue();
                } else {
                    iIntValue = month / 12;
                    i3 = i2 + 1;
                }
                setMonth(i3);
                if (iIntValue != 0) {
                    setYear(getEonAndYear().add(BigInteger.valueOf(iIntValue)));
                }
            }
            setDay(bigIntegerAdd.intValue());
            for (int i4 = 0; i4 <= 5; i4++) {
                if (!zArr[i4]) {
                    if (i4 != 0) {
                        setYear(Integer.MIN_VALUE);
                    } else if (i4 != 1) {
                        setMonth(Integer.MIN_VALUE);
                    } else if (i4 != 2) {
                        setDay(Integer.MIN_VALUE);
                    } else if (i4 != 3) {
                        setHour(Integer.MIN_VALUE);
                    } else if (i4 != 4) {
                        setMinute(Integer.MIN_VALUE);
                    } else if (i4 != 5) {
                        setSecond(Integer.MIN_VALUE);
                        setFractionalSecond(null);
                    }
                }
            }
        }
        j = iMaximumDayInMonthFor;
        bigIntegerValueOf = BigInteger.valueOf(j);
        bigIntegerAdd = bigIntegerValueOf.add(bigIntegerSanitize).add(bigInteger6);
        while (true) {
            if (bigIntegerAdd.compareTo(BigInteger.ONE) >= 0) {
                if (this.month >= 2) {
                    bigIntegerSubtract = getEonAndYear();
                    month2 = getMonth() - 1;
                } else {
                    bigIntegerSubtract = getEonAndYear().subtract(BigInteger.valueOf(1L));
                    month2 = 12;
                }
                bigIntegerAdd = bigIntegerAdd.add(BigInteger.valueOf(maximumDayInMonthFor(bigIntegerSubtract, month2)));
                i = -1;
            } else if (bigIntegerAdd.compareTo(BigInteger.valueOf(maximumDayInMonthFor(getEonAndYear(), getMonth()))) > 0) {
                break;
                break;
            } else {
                bigIntegerAdd = bigIntegerAdd.add(BigInteger.valueOf(-maximumDayInMonthFor(getEonAndYear(), getMonth())));
                i = 1;
            }
            month = (getMonth() + i) - 1;
            i2 = month % 12;
            if (i2 < 0) {
                i3 = i2 + 13;
                iIntValue = BigDecimal.valueOf(month).divide(new BigDecimal(TWELVE), 0).intValue();
            } else {
                iIntValue = month / 12;
                i3 = i2 + 1;
            }
            setMonth(i3);
            if (iIntValue != 0) {
                setYear(getEonAndYear().add(BigInteger.valueOf(iIntValue)));
            }
        }
        setDay(bigIntegerAdd.intValue());
        while (i4 <= 5) {
            if (!zArr[i4]) {
                if (i4 != 0) {
                    setYear(Integer.MIN_VALUE);
                } else if (i4 != 1) {
                    setMonth(Integer.MIN_VALUE);
                } else if (i4 != 2) {
                    setDay(Integer.MIN_VALUE);
                } else if (i4 != 3) {
                    setHour(Integer.MIN_VALUE);
                } else if (i4 != 4) {
                    setMinute(Integer.MIN_VALUE);
                } else if (i4 != 5) {
                    setSecond(Integer.MIN_VALUE);
                    setFractionalSecond(null);
                }
            }
        }
    }

    @Override // javax.xml.datatype.XMLGregorianCalendar
    public void clear() {
        this.eon = null;
        this.year = Integer.MIN_VALUE;
        this.month = Integer.MIN_VALUE;
        this.day = Integer.MIN_VALUE;
        this.timezone = Integer.MIN_VALUE;
        this.hour = Integer.MIN_VALUE;
        this.minute = Integer.MIN_VALUE;
        this.second = Integer.MIN_VALUE;
        this.fractionalSecond = null;
    }

    @Override // javax.xml.datatype.XMLGregorianCalendar
    public Object clone() {
        return new XMLGregorianCalendarImpl(getEonAndYear(), this.month, this.day, this.hour, this.minute, this.second, this.fractionalSecond, this.timezone);
    }

    @Override // javax.xml.datatype.XMLGregorianCalendar
    public int compare(XMLGregorianCalendar xMLGregorianCalendar) {
        if (getTimezone() == xMLGregorianCalendar.getTimezone()) {
            return internalCompare(this, xMLGregorianCalendar);
        }
        if (getTimezone() != Integer.MIN_VALUE && xMLGregorianCalendar.getTimezone() != Integer.MIN_VALUE) {
            return internalCompare((XMLGregorianCalendarImpl) normalize(), (XMLGregorianCalendarImpl) xMLGregorianCalendar.normalize());
        }
        if (getTimezone() != Integer.MIN_VALUE) {
            XMLGregorianCalendarImpl xMLGregorianCalendarImpl = getTimezone() != 0 ? (XMLGregorianCalendarImpl) normalize() : this;
            int iInternalCompare = internalCompare(xMLGregorianCalendarImpl, normalizeToTimezone(xMLGregorianCalendar, DatatypeConstants.MIN_TIMEZONE_OFFSET));
            if (iInternalCompare == -1) {
                return iInternalCompare;
            }
            int iInternalCompare2 = internalCompare(xMLGregorianCalendarImpl, normalizeToTimezone(xMLGregorianCalendar, DatatypeConstants.MAX_TIMEZONE_OFFSET));
            if (iInternalCompare2 == 1) {
                return iInternalCompare2;
            }
            return 2;
        }
        if (xMLGregorianCalendar.getTimezone() != 0) {
            xMLGregorianCalendar = (XMLGregorianCalendarImpl) normalizeToTimezone(xMLGregorianCalendar, xMLGregorianCalendar.getTimezone());
        }
        int iInternalCompare3 = internalCompare(normalizeToTimezone(this, DatatypeConstants.MAX_TIMEZONE_OFFSET), xMLGregorianCalendar);
        if (iInternalCompare3 == -1) {
            return iInternalCompare3;
        }
        int iInternalCompare4 = internalCompare(normalizeToTimezone(this, DatatypeConstants.MIN_TIMEZONE_OFFSET), xMLGregorianCalendar);
        if (iInternalCompare4 == 1) {
            return iInternalCompare4;
        }
        return 2;
    }

    @Override // javax.xml.datatype.XMLGregorianCalendar
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        return (obj instanceof XMLGregorianCalendar) && compare((XMLGregorianCalendar) obj) == 0;
    }

    @Override // javax.xml.datatype.XMLGregorianCalendar
    public int getDay() {
        return this.day;
    }

    @Override // javax.xml.datatype.XMLGregorianCalendar
    public BigInteger getEon() {
        return this.eon;
    }

    @Override // javax.xml.datatype.XMLGregorianCalendar
    public BigInteger getEonAndYear() {
        BigInteger bigInteger;
        int i = this.year;
        if (i != Integer.MIN_VALUE && (bigInteger = this.eon) != null) {
            return bigInteger.add(BigInteger.valueOf(i));
        }
        if (i == Integer.MIN_VALUE || this.eon != null) {
            return null;
        }
        return BigInteger.valueOf(i);
    }

    @Override // javax.xml.datatype.XMLGregorianCalendar
    public BigDecimal getFractionalSecond() {
        return this.fractionalSecond;
    }

    @Override // javax.xml.datatype.XMLGregorianCalendar
    public int getHour() {
        return this.hour;
    }

    @Override // javax.xml.datatype.XMLGregorianCalendar
    public int getMillisecond() {
        BigDecimal bigDecimal = this.fractionalSecond;
        if (bigDecimal == null) {
            return Integer.MIN_VALUE;
        }
        return bigDecimal.movePointRight(3).intValue();
    }

    @Override // javax.xml.datatype.XMLGregorianCalendar
    public int getMinute() {
        return this.minute;
    }

    @Override // javax.xml.datatype.XMLGregorianCalendar
    public int getMonth() {
        return this.month;
    }

    @Override // javax.xml.datatype.XMLGregorianCalendar
    public int getSecond() {
        return this.second;
    }

    @Override // javax.xml.datatype.XMLGregorianCalendar
    public TimeZone getTimeZone(int i) {
        int timezone = getTimezone();
        if (timezone != Integer.MIN_VALUE) {
            i = timezone;
        }
        if (i == Integer.MIN_VALUE) {
            return TimeZone.getDefault();
        }
        char c = i < 0 ? '-' : '+';
        if (c == '-') {
            i = -i;
        }
        int i2 = i / 60;
        int i3 = i - (i2 * 60);
        StringBuffer stringBuffer = new StringBuffer(8);
        stringBuffer.append(TimeZones.GMT_ID);
        stringBuffer.append(c);
        stringBuffer.append(i2);
        if (i3 != 0) {
            if (i3 < 10) {
                stringBuffer.append(ExternalAnnotationProvider.NULLABLE);
            }
            stringBuffer.append(i3);
        }
        return TimeZone.getTimeZone(stringBuffer.toString());
    }

    @Override // javax.xml.datatype.XMLGregorianCalendar
    public int getTimezone() {
        return this.timezone;
    }

    @Override // javax.xml.datatype.XMLGregorianCalendar
    public QName getXMLSchemaType() {
        int i = this.year;
        if (i != Integer.MIN_VALUE && this.month != Integer.MIN_VALUE && this.day != Integer.MIN_VALUE && this.hour != Integer.MIN_VALUE && this.minute != Integer.MIN_VALUE && this.second != Integer.MIN_VALUE) {
            return DatatypeConstants.DATETIME;
        }
        if (i != Integer.MIN_VALUE && this.month != Integer.MIN_VALUE && this.day != Integer.MIN_VALUE && this.hour == Integer.MIN_VALUE && this.minute == Integer.MIN_VALUE && this.second == Integer.MIN_VALUE) {
            return DatatypeConstants.DATE;
        }
        if (i == Integer.MIN_VALUE && this.month == Integer.MIN_VALUE && this.day == Integer.MIN_VALUE && this.hour != Integer.MIN_VALUE && this.minute != Integer.MIN_VALUE && this.second != Integer.MIN_VALUE) {
            return DatatypeConstants.TIME;
        }
        if (i != Integer.MIN_VALUE && this.month != Integer.MIN_VALUE && this.day == Integer.MIN_VALUE && this.hour == Integer.MIN_VALUE && this.minute == Integer.MIN_VALUE && this.second == Integer.MIN_VALUE) {
            return DatatypeConstants.GYEARMONTH;
        }
        if (i == Integer.MIN_VALUE && this.month != Integer.MIN_VALUE && this.day != Integer.MIN_VALUE && this.hour == Integer.MIN_VALUE && this.minute == Integer.MIN_VALUE && this.second == Integer.MIN_VALUE) {
            return DatatypeConstants.GMONTHDAY;
        }
        if (i != Integer.MIN_VALUE && this.month == Integer.MIN_VALUE && this.day == Integer.MIN_VALUE && this.hour == Integer.MIN_VALUE && this.minute == Integer.MIN_VALUE && this.second == Integer.MIN_VALUE) {
            return DatatypeConstants.GYEAR;
        }
        if (i == Integer.MIN_VALUE && this.month != Integer.MIN_VALUE && this.day == Integer.MIN_VALUE && this.hour == Integer.MIN_VALUE && this.minute == Integer.MIN_VALUE && this.second == Integer.MIN_VALUE) {
            return DatatypeConstants.GMONTH;
        }
        if (i == Integer.MIN_VALUE && this.month == Integer.MIN_VALUE && this.day != Integer.MIN_VALUE && this.hour == Integer.MIN_VALUE && this.minute == Integer.MIN_VALUE && this.second == Integer.MIN_VALUE) {
            return DatatypeConstants.GDAY;
        }
        throw new IllegalStateException(getClass().getName() + "#getXMLSchemaType() :" + DatatypeMessageFormatter.formatMessage(null, "InvalidXGCFields", null));
    }

    @Override // javax.xml.datatype.XMLGregorianCalendar
    public int getYear() {
        return this.year;
    }

    @Override // javax.xml.datatype.XMLGregorianCalendar
    public int hashCode() {
        int timezone = getTimezone();
        if (timezone == Integer.MIN_VALUE) {
            timezone = 0;
        }
        XMLGregorianCalendar xMLGregorianCalendarNormalizeToTimezone = timezone != 0 ? normalizeToTimezone(this, getTimezone()) : this;
        return xMLGregorianCalendarNormalizeToTimezone.getYear() + xMLGregorianCalendarNormalizeToTimezone.getMonth() + xMLGregorianCalendarNormalizeToTimezone.getDay() + xMLGregorianCalendarNormalizeToTimezone.getHour() + xMLGregorianCalendarNormalizeToTimezone.getMinute() + xMLGregorianCalendarNormalizeToTimezone.getSecond();
    }

    @Override // javax.xml.datatype.XMLGregorianCalendar
    public boolean isValid() {
        BigDecimal bigDecimal;
        int i;
        int i2 = this.month;
        if (i2 != Integer.MIN_VALUE && (i = this.day) != Integer.MIN_VALUE) {
            int i3 = this.year;
            if (i3 != Integer.MIN_VALUE) {
                if (this.eon == null) {
                    if (i > maximumDayInMonthFor(i3, i2)) {
                        return false;
                    }
                } else if (i > maximumDayInMonthFor(getEonAndYear(), this.month)) {
                    return false;
                }
            } else if (i > maximumDayInMonthFor(2000, i2)) {
                return false;
            }
        }
        if (this.hour != 24 || (this.minute == 0 && this.second == 0 && ((bigDecimal = this.fractionalSecond) == null || bigDecimal.compareTo(DECIMAL_ZERO) == 0))) {
            return (this.eon == null && this.year == 0) ? false : true;
        }
        return false;
    }

    @Override // javax.xml.datatype.XMLGregorianCalendar
    public XMLGregorianCalendar normalize() {
        XMLGregorianCalendar xMLGregorianCalendarNormalizeToTimezone = normalizeToTimezone(this, this.timezone);
        if (getTimezone() == Integer.MIN_VALUE) {
            xMLGregorianCalendarNormalizeToTimezone.setTimezone(Integer.MIN_VALUE);
        }
        if (getMillisecond() == Integer.MIN_VALUE) {
            xMLGregorianCalendarNormalizeToTimezone.setMillisecond(Integer.MIN_VALUE);
        }
        return xMLGregorianCalendarNormalizeToTimezone;
    }

    @Override // javax.xml.datatype.XMLGregorianCalendar
    public void reset() {
        this.eon = this.orig_eon;
        this.year = this.orig_year;
        this.month = this.orig_month;
        this.day = this.orig_day;
        this.hour = this.orig_hour;
        this.minute = this.orig_minute;
        this.second = this.orig_second;
        this.fractionalSecond = this.orig_fracSeconds;
        this.timezone = this.orig_timezone;
    }

    @Override // javax.xml.datatype.XMLGregorianCalendar
    public void setDay(int i) {
        checkFieldValueConstraint(2, i);
        this.day = i;
    }

    @Override // javax.xml.datatype.XMLGregorianCalendar
    public void setFractionalSecond(BigDecimal bigDecimal) {
        if (bigDecimal != null && (bigDecimal.compareTo(DECIMAL_ZERO) < 0 || bigDecimal.compareTo(DECIMAL_ONE) > 0)) {
            throw new IllegalArgumentException(DatatypeMessageFormatter.formatMessage(null, "InvalidFractional", new Object[]{bigDecimal}));
        }
        this.fractionalSecond = bigDecimal;
    }

    @Override // javax.xml.datatype.XMLGregorianCalendar
    public void setHour(int i) {
        checkFieldValueConstraint(3, i);
        this.hour = i;
    }

    @Override // javax.xml.datatype.XMLGregorianCalendar
    public void setMillisecond(int i) {
        BigDecimal bigDecimalValueOf;
        if (i == Integer.MIN_VALUE) {
            bigDecimalValueOf = null;
        } else {
            checkFieldValueConstraint(6, i);
            bigDecimalValueOf = BigDecimal.valueOf(i, 3);
        }
        this.fractionalSecond = bigDecimalValueOf;
    }

    @Override // javax.xml.datatype.XMLGregorianCalendar
    public void setMinute(int i) {
        checkFieldValueConstraint(4, i);
        this.minute = i;
    }

    @Override // javax.xml.datatype.XMLGregorianCalendar
    public void setMonth(int i) {
        checkFieldValueConstraint(1, i);
        this.month = i;
    }

    @Override // javax.xml.datatype.XMLGregorianCalendar
    public void setSecond(int i) {
        checkFieldValueConstraint(5, i);
        this.second = i;
    }

    @Override // javax.xml.datatype.XMLGregorianCalendar
    public void setTime(int i, int i2, int i3) {
        setTime(i, i2, i3, (BigDecimal) null);
    }

    @Override // javax.xml.datatype.XMLGregorianCalendar
    public void setTime(int i, int i2, int i3, int i4) {
        setHour(i);
        setMinute(i2);
        setSecond(i3);
        setMillisecond(i4);
    }

    @Override // javax.xml.datatype.XMLGregorianCalendar
    public void setTime(int i, int i2, int i3, BigDecimal bigDecimal) {
        setHour(i);
        setMinute(i2);
        setSecond(i3);
        setFractionalSecond(bigDecimal);
    }

    @Override // javax.xml.datatype.XMLGregorianCalendar
    public void setTimezone(int i) {
        checkFieldValueConstraint(7, i);
        this.timezone = i;
    }

    @Override // javax.xml.datatype.XMLGregorianCalendar
    public void setYear(int i) {
        if (i == Integer.MIN_VALUE) {
            this.year = Integer.MIN_VALUE;
        } else {
            if (Math.abs(i) >= 1000000000) {
                BigInteger bigIntegerValueOf = BigInteger.valueOf(i);
                BigInteger bigIntegerRemainder = bigIntegerValueOf.remainder(BILLION_B);
                this.year = bigIntegerRemainder.intValue();
                setEon(bigIntegerValueOf.subtract(bigIntegerRemainder));
                return;
            }
            this.year = i;
        }
        this.eon = null;
    }

    @Override // javax.xml.datatype.XMLGregorianCalendar
    public void setYear(BigInteger bigInteger) {
        if (bigInteger == null) {
            this.eon = null;
            this.year = Integer.MIN_VALUE;
        } else {
            BigInteger bigIntegerRemainder = bigInteger.remainder(BILLION_B);
            this.year = bigIntegerRemainder.intValue();
            setEon(bigInteger.subtract(bigIntegerRemainder));
        }
    }

    @Override // javax.xml.datatype.XMLGregorianCalendar
    public GregorianCalendar toGregorianCalendar() {
        int iIntValue;
        GregorianCalendar gregorianCalendar = new GregorianCalendar(getTimeZone(Integer.MIN_VALUE), Locale.getDefault());
        gregorianCalendar.clear();
        gregorianCalendar.setGregorianChange(PURE_GREGORIAN_CHANGE);
        int i = this.year;
        if (i != Integer.MIN_VALUE) {
            if (this.eon == null) {
                gregorianCalendar.set(0, i < 0 ? 0 : 1);
                iIntValue = Math.abs(this.year);
            } else {
                BigInteger eonAndYear = getEonAndYear();
                gregorianCalendar.set(0, eonAndYear.signum() == -1 ? 0 : 1);
                iIntValue = eonAndYear.abs().intValue();
            }
            gregorianCalendar.set(1, iIntValue);
        }
        int i2 = this.month;
        if (i2 != Integer.MIN_VALUE) {
            gregorianCalendar.set(2, i2 - 1);
        }
        int i3 = this.day;
        if (i3 != Integer.MIN_VALUE) {
            gregorianCalendar.set(5, i3);
        }
        int i4 = this.hour;
        if (i4 != Integer.MIN_VALUE) {
            gregorianCalendar.set(11, i4);
        }
        int i5 = this.minute;
        if (i5 != Integer.MIN_VALUE) {
            gregorianCalendar.set(12, i5);
        }
        int i6 = this.second;
        if (i6 != Integer.MIN_VALUE) {
            gregorianCalendar.set(13, i6);
        }
        if (this.fractionalSecond != null) {
            gregorianCalendar.set(14, getMillisecond());
        }
        return gregorianCalendar;
    }

    /* JADX WARN: Code duplicated, block: B:21:0x0047 A[PHI: r6
      0x0047: PHI (r6v10 java.math.BigInteger) = (r6v5 java.math.BigInteger), (r6v47 java.math.BigInteger) binds: [B:35:0x0078, B:20:0x0045] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:22:0x0049 A[PHI: r6
      0x0049: PHI (r6v6 java.math.BigInteger) = (r6v5 java.math.BigInteger), (r6v47 java.math.BigInteger) binds: [B:35:0x0078, B:20:0x0045] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:39:0x0080 A[PHI: r6
      0x0080: PHI (r6v42 int) = (r6v11 int), (r6v13 int) binds: [B:38:0x007e, B:43:0x008d] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:47:0x0095 A[PHI: r6
      0x0095: PHI (r6v41 int) = (r6v15 int), (r6v17 int) binds: [B:46:0x0093, B:51:0x00a1] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:55:0x00aa A[PHI: r6
      0x00aa: PHI (r6v40 int) = (r6v19 int), (r6v21 int) binds: [B:54:0x00a8, B:59:0x00b6] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:63:0x00bf A[PHI: r6
      0x00bf: PHI (r6v39 int) = (r6v23 int), (r6v25 int) binds: [B:62:0x00bd, B:67:0x00cb] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:71:0x00d4 A[PHI: r6
      0x00d4: PHI (r6v38 int) = (r6v27 int), (r6v29 int) binds: [B:70:0x00d2, B:75:0x00e0] A[DONT_GENERATE, DONT_INLINE]] */
    @Override // javax.xml.datatype.XMLGregorianCalendar
    public GregorianCalendar toGregorianCalendar(TimeZone timeZone, Locale locale, XMLGregorianCalendar xMLGregorianCalendar) {
        int year;
        BigInteger eonAndYear;
        int i;
        int iIntValue;
        int millisecond;
        if (timeZone == null) {
            timeZone = getTimeZone(xMLGregorianCalendar != null ? xMLGregorianCalendar.getTimezone() : Integer.MIN_VALUE);
        }
        if (locale == null) {
            locale = Locale.getDefault();
        }
        GregorianCalendar gregorianCalendar = new GregorianCalendar(timeZone, locale);
        gregorianCalendar.clear();
        gregorianCalendar.setGregorianChange(PURE_GREGORIAN_CHANGE);
        int i2 = this.year;
        if (i2 != Integer.MIN_VALUE) {
            if (this.eon == null) {
                gregorianCalendar.set(0, i2 < 0 ? 0 : 1);
                year = this.year;
                iIntValue = Math.abs(year);
                gregorianCalendar.set(1, iIntValue);
            } else {
                eonAndYear = getEonAndYear();
                if (eonAndYear.signum() == -1) {
                    i = 0;
                } else {
                    i = 1;
                }
                gregorianCalendar.set(0, i);
                iIntValue = eonAndYear.abs().intValue();
                gregorianCalendar.set(1, iIntValue);
            }
        } else if (xMLGregorianCalendar != null && (year = xMLGregorianCalendar.getYear()) != Integer.MIN_VALUE) {
            if (xMLGregorianCalendar.getEon() == null) {
                gregorianCalendar.set(0, year < 0 ? 0 : 1);
                iIntValue = Math.abs(year);
                gregorianCalendar.set(1, iIntValue);
            } else {
                eonAndYear = xMLGregorianCalendar.getEonAndYear();
                if (eonAndYear.signum() == -1) {
                    i = 0;
                } else {
                    i = 1;
                }
                gregorianCalendar.set(0, i);
                iIntValue = eonAndYear.abs().intValue();
                gregorianCalendar.set(1, iIntValue);
            }
        }
        int month = this.month;
        if (month == Integer.MIN_VALUE) {
            month = xMLGregorianCalendar != null ? xMLGregorianCalendar.getMonth() : Integer.MIN_VALUE;
            if (month != Integer.MIN_VALUE) {
                gregorianCalendar.set(2, month - 1);
            }
        } else {
            gregorianCalendar.set(2, month - 1);
        }
        int day = this.day;
        if (day == Integer.MIN_VALUE) {
            day = xMLGregorianCalendar != null ? xMLGregorianCalendar.getDay() : Integer.MIN_VALUE;
            if (day != Integer.MIN_VALUE) {
                gregorianCalendar.set(5, day);
            }
        } else {
            gregorianCalendar.set(5, day);
        }
        int hour = this.hour;
        if (hour == Integer.MIN_VALUE) {
            hour = xMLGregorianCalendar != null ? xMLGregorianCalendar.getHour() : Integer.MIN_VALUE;
            if (hour != Integer.MIN_VALUE) {
                gregorianCalendar.set(11, hour);
            }
        } else {
            gregorianCalendar.set(11, hour);
        }
        int minute = this.minute;
        if (minute == Integer.MIN_VALUE) {
            minute = xMLGregorianCalendar != null ? xMLGregorianCalendar.getMinute() : Integer.MIN_VALUE;
            if (minute != Integer.MIN_VALUE) {
                gregorianCalendar.set(12, minute);
            }
        } else {
            gregorianCalendar.set(12, minute);
        }
        int second = this.second;
        if (second == Integer.MIN_VALUE) {
            second = xMLGregorianCalendar != null ? xMLGregorianCalendar.getSecond() : Integer.MIN_VALUE;
            if (second != Integer.MIN_VALUE) {
                gregorianCalendar.set(13, second);
            }
        } else {
            gregorianCalendar.set(13, second);
        }
        if (this.fractionalSecond == null) {
            if ((xMLGregorianCalendar != null ? xMLGregorianCalendar.getFractionalSecond() : null) != null) {
                millisecond = xMLGregorianCalendar.getMillisecond();
            }
            return gregorianCalendar;
        }
        millisecond = getMillisecond();
        gregorianCalendar.set(14, millisecond);
        return gregorianCalendar;
    }

    @Override // javax.xml.datatype.XMLGregorianCalendar
    public String toXMLFormat() {
        String str;
        QName xMLSchemaType = getXMLSchemaType();
        if (xMLSchemaType == DatatypeConstants.DATETIME) {
            str = "%Y-%M-%DT%h:%m:%s%z";
        } else if (xMLSchemaType == DatatypeConstants.DATE) {
            str = "%Y-%M-%D%z";
        } else if (xMLSchemaType == DatatypeConstants.TIME) {
            str = "%h:%m:%s%z";
        } else if (xMLSchemaType == DatatypeConstants.GMONTH) {
            str = "--%M--%z";
        } else if (xMLSchemaType == DatatypeConstants.GDAY) {
            str = "---%D%z";
        } else if (xMLSchemaType == DatatypeConstants.GYEAR) {
            str = "%Y%z";
        } else if (xMLSchemaType == DatatypeConstants.GYEARMONTH) {
            str = "%Y-%M%z";
        } else {
            str = xMLSchemaType == DatatypeConstants.GMONTHDAY ? "--%M-%D%z" : null;
        }
        return format(str);
    }
}
