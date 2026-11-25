package org.apache.commons.beanutils.locale.converters;

import java.math.BigInteger;
import java.text.ParseException;
import java.util.Locale;
import org.apache.commons.beanutils.ConversionException;

/* JADX INFO: loaded from: classes12.dex */
public class BigIntegerLocaleConverter extends DecimalLocaleConverter {
    public BigIntegerLocaleConverter() {
        this(false);
    }

    public BigIntegerLocaleConverter(boolean z) {
        this(Locale.getDefault(), z);
    }

    public BigIntegerLocaleConverter(Locale locale) {
        this(locale, false);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public BigIntegerLocaleConverter(Locale locale, boolean z) {
        this(locale, (String) null, z);
    }

    public BigIntegerLocaleConverter(Locale locale, String str) {
        this(locale, str, false);
    }

    public BigIntegerLocaleConverter(Locale locale, String str, boolean z) {
        super(locale, str, z);
    }

    public BigIntegerLocaleConverter(Object obj) {
        this(obj, false);
    }

    public BigIntegerLocaleConverter(Object obj, boolean z) {
        this(obj, Locale.getDefault(), z);
    }

    public BigIntegerLocaleConverter(Object obj, Locale locale) {
        this(obj, locale, false);
    }

    public BigIntegerLocaleConverter(Object obj, Locale locale, boolean z) {
        this(obj, locale, null, z);
    }

    public BigIntegerLocaleConverter(Object obj, Locale locale, String str) {
        this(obj, locale, str, false);
    }

    public BigIntegerLocaleConverter(Object obj, Locale locale, String str, boolean z) {
        super(obj, locale, str, z);
    }

    @Override // org.apache.commons.beanutils.locale.converters.DecimalLocaleConverter, org.apache.commons.beanutils.locale.BaseLocaleConverter
    protected Object parse(Object obj, String str) throws ParseException {
        Object obj2 = super.parse(obj, str);
        if (obj2 == null || (obj2 instanceof BigInteger)) {
            return obj2;
        }
        if (obj2 instanceof Number) {
            return BigInteger.valueOf(((Number) obj2).longValue());
        }
        try {
            return new BigInteger(obj2.toString());
        } catch (NumberFormatException unused) {
            throw new ConversionException("Suplied number is not of type BigInteger: " + obj2);
        }
    }
}
