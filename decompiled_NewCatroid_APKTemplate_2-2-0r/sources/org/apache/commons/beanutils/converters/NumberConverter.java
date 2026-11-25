package org.apache.commons.beanutils.converters;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import name.antonsmirnov.firmata.FormatHelper;
import org.apache.commons.beanutils.ConversionException;

/* JADX INFO: loaded from: classes12.dex */
public abstract class NumberConverter extends AbstractConverter {
    private final boolean allowDecimals;
    private Locale locale;
    private String pattern;
    private boolean useLocaleFormat;
    private static final Integer ZERO = new Integer(0);
    private static final Integer ONE = new Integer(1);

    public NumberConverter(boolean z) {
        this.allowDecimals = z;
    }

    public NumberConverter(boolean z, Object obj) {
        this.allowDecimals = z;
        setDefaultValue(obj);
    }

    public boolean isAllowDecimals() {
        return this.allowDecimals;
    }

    public void setUseLocaleFormat(boolean z) {
        this.useLocaleFormat = z;
    }

    public String getPattern() {
        return this.pattern;
    }

    public void setPattern(String str) {
        this.pattern = str;
        setUseLocaleFormat(true);
    }

    public Locale getLocale() {
        return this.locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
        setUseLocaleFormat(true);
    }

    @Override // org.apache.commons.beanutils.converters.AbstractConverter
    protected String convertToString(Object obj) throws Throwable {
        String string;
        if (this.useLocaleFormat && (obj instanceof Number)) {
            NumberFormat format = getFormat();
            format.setGroupingUsed(false);
            string = format.format(obj);
            if (log().isDebugEnabled()) {
                log().debug("    Converted  to String using format '" + string + FormatHelper.QUOTE);
            }
        } else {
            string = obj.toString();
            if (log().isDebugEnabled()) {
                log().debug("    Converted  to String using toString() '" + string + FormatHelper.QUOTE);
            }
        }
        return string;
    }

    @Override // org.apache.commons.beanutils.converters.AbstractConverter
    protected <T> T convertToType(Class<T> cls, Object obj) throws Throwable {
        Number number;
        Class<?> cls2 = obj.getClass();
        if (obj instanceof Number) {
            return (T) toNumber(cls2, cls, (Number) obj);
        }
        if (obj instanceof Boolean) {
            return (T) toNumber(cls2, cls, ((Boolean) obj).booleanValue() ? ONE : ZERO);
        }
        if ((obj instanceof Date) && Long.class.equals(cls)) {
            return cls.cast(new Long(((Date) obj).getTime()));
        }
        if ((obj instanceof Calendar) && Long.class.equals(cls)) {
            return cls.cast(new Long(((Calendar) obj).getTime().getTime()));
        }
        String strTrim = obj.toString().trim();
        if (strTrim.length() == 0) {
            return (T) handleMissing(cls);
        }
        if (this.useLocaleFormat) {
            number = parse(cls2, cls, strTrim, getFormat());
        } else {
            if (log().isDebugEnabled()) {
                log().debug("    No NumberFormat, using default conversion");
            }
            number = toNumber(cls2, (Class<?>) cls, strTrim);
        }
        return (T) toNumber(cls2, cls, number);
    }

    private <T> T toNumber(Class<?> cls, Class<T> cls2, Number number) {
        if (cls2.equals(number.getClass())) {
            return cls2.cast(number);
        }
        if (cls2.equals(Byte.class)) {
            long jLongValue = number.longValue();
            if (jLongValue > 127) {
                throw new ConversionException(toString(cls) + " value '" + number + "' is too large for " + toString(cls2));
            }
            if (jLongValue < -128) {
                throw new ConversionException(toString(cls) + " value '" + number + "' is too small " + toString(cls2));
            }
            return cls2.cast(new Byte(number.byteValue()));
        }
        if (cls2.equals(Short.class)) {
            long jLongValue2 = number.longValue();
            if (jLongValue2 > 32767) {
                throw new ConversionException(toString(cls) + " value '" + number + "' is too large for " + toString(cls2));
            }
            if (jLongValue2 < -32768) {
                throw new ConversionException(toString(cls) + " value '" + number + "' is too small " + toString(cls2));
            }
            return cls2.cast(new Short(number.shortValue()));
        }
        if (cls2.equals(Integer.class)) {
            long jLongValue3 = number.longValue();
            if (jLongValue3 > 2147483647L) {
                throw new ConversionException(toString(cls) + " value '" + number + "' is too large for " + toString(cls2));
            }
            if (jLongValue3 < -2147483648L) {
                throw new ConversionException(toString(cls) + " value '" + number + "' is too small " + toString(cls2));
            }
            return cls2.cast(new Integer(number.intValue()));
        }
        if (cls2.equals(Long.class)) {
            return cls2.cast(new Long(number.longValue()));
        }
        if (cls2.equals(Float.class)) {
            if (number.doubleValue() > 3.4028234663852886E38d) {
                throw new ConversionException(toString(cls) + " value '" + number + "' is too large for " + toString(cls2));
            }
            return cls2.cast(new Float(number.floatValue()));
        }
        if (cls2.equals(Double.class)) {
            return cls2.cast(new Double(number.doubleValue()));
        }
        if (cls2.equals(BigDecimal.class)) {
            if ((number instanceof Float) || (number instanceof Double)) {
                return cls2.cast(new BigDecimal(number.toString()));
            }
            if (number instanceof BigInteger) {
                return cls2.cast(new BigDecimal((BigInteger) number));
            }
            if (number instanceof BigDecimal) {
                return cls2.cast(new BigDecimal(number.toString()));
            }
            return cls2.cast(BigDecimal.valueOf(number.longValue()));
        }
        if (cls2.equals(BigInteger.class)) {
            if (number instanceof BigDecimal) {
                return cls2.cast(((BigDecimal) number).toBigInteger());
            }
            return cls2.cast(BigInteger.valueOf(number.longValue()));
        }
        String str = toString(getClass()) + " cannot handle conversion to '" + toString(cls2) + FormatHelper.QUOTE;
        if (log().isWarnEnabled()) {
            log().warn("    " + str);
        }
        throw new ConversionException(str);
    }

    private Number toNumber(Class<?> cls, Class<?> cls2, String str) {
        if (cls2.equals(Byte.class)) {
            return new Byte(str);
        }
        if (cls2.equals(Short.class)) {
            return new Short(str);
        }
        if (cls2.equals(Integer.class)) {
            return new Integer(str);
        }
        if (cls2.equals(Long.class)) {
            return new Long(str);
        }
        if (cls2.equals(Float.class)) {
            return new Float(str);
        }
        if (cls2.equals(Double.class)) {
            return new Double(str);
        }
        if (cls2.equals(BigDecimal.class)) {
            return new BigDecimal(str);
        }
        if (cls2.equals(BigInteger.class)) {
            return new BigInteger(str);
        }
        String str2 = toString(getClass()) + " cannot handle conversion from '" + toString(cls) + "' to '" + toString(cls2) + FormatHelper.QUOTE;
        if (log().isWarnEnabled()) {
            log().warn("    " + str2);
        }
        throw new ConversionException(str2);
    }

    @Override // org.apache.commons.beanutils.converters.AbstractConverter
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(toString(getClass()));
        sb.append("[UseDefault=");
        sb.append(isUseDefault());
        sb.append(", UseLocaleFormat=");
        sb.append(this.useLocaleFormat);
        if (this.pattern != null) {
            sb.append(", Pattern=");
            sb.append(this.pattern);
        }
        if (this.locale != null) {
            sb.append(", Locale=");
            sb.append(this.locale);
        }
        sb.append(']');
        return sb.toString();
    }

    private NumberFormat getFormat() {
        NumberFormat numberFormat;
        if (this.pattern != null) {
            if (this.locale == null) {
                if (log().isDebugEnabled()) {
                    log().debug("    Using pattern '" + this.pattern + FormatHelper.QUOTE);
                }
                numberFormat = new DecimalFormat(this.pattern);
            } else {
                if (log().isDebugEnabled()) {
                    log().debug("    Using pattern '" + this.pattern + "' with Locale[" + this.locale + "]");
                }
                numberFormat = new DecimalFormat(this.pattern, new DecimalFormatSymbols(this.locale));
            }
        } else if (this.locale == null) {
            if (log().isDebugEnabled()) {
                log().debug("    Using default Locale format");
            }
            numberFormat = NumberFormat.getInstance();
        } else {
            if (log().isDebugEnabled()) {
                log().debug("    Using Locale[" + this.locale + "] format");
            }
            numberFormat = NumberFormat.getInstance(this.locale);
        }
        if (!this.allowDecimals) {
            numberFormat.setParseIntegerOnly(true);
        }
        return numberFormat;
    }

    private Number parse(Class<?> cls, Class<?> cls2, String str, NumberFormat numberFormat) {
        ParsePosition parsePosition = new ParsePosition(0);
        Number number = numberFormat.parse(str, parsePosition);
        if (parsePosition.getErrorIndex() < 0 && parsePosition.getIndex() == str.length() && number != null) {
            return number;
        }
        String str2 = "Error converting from '" + toString(cls) + "' to '" + toString(cls2) + FormatHelper.QUOTE;
        if (numberFormat instanceof DecimalFormat) {
            str2 = str2 + " using pattern '" + ((DecimalFormat) numberFormat).toPattern() + FormatHelper.QUOTE;
        }
        if (this.locale != null) {
            str2 = str2 + " for locale=[" + this.locale + "]";
        }
        if (log().isDebugEnabled()) {
            log().debug("    " + str2);
        }
        throw new ConversionException(str2);
    }
}
