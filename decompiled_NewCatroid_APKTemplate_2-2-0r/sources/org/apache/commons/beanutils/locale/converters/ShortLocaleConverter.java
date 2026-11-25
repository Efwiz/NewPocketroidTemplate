package org.apache.commons.beanutils.locale.converters;

import java.text.ParseException;
import java.util.Locale;
import org.apache.commons.beanutils.ConversionException;

/* JADX INFO: loaded from: classes12.dex */
public class ShortLocaleConverter extends DecimalLocaleConverter {
    public ShortLocaleConverter() {
        this(false);
    }

    public ShortLocaleConverter(boolean z) {
        this(Locale.getDefault(), z);
    }

    public ShortLocaleConverter(Locale locale) {
        this(locale, false);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public ShortLocaleConverter(Locale locale, boolean z) {
        this(locale, (String) null, z);
    }

    public ShortLocaleConverter(Locale locale, String str) {
        this(locale, str, false);
    }

    public ShortLocaleConverter(Locale locale, String str, boolean z) {
        super(locale, str, z);
    }

    public ShortLocaleConverter(Object obj) {
        this(obj, false);
    }

    public ShortLocaleConverter(Object obj, boolean z) {
        this(obj, Locale.getDefault(), z);
    }

    public ShortLocaleConverter(Object obj, Locale locale) {
        this(obj, locale, false);
    }

    public ShortLocaleConverter(Object obj, Locale locale, boolean z) {
        this(obj, locale, null, z);
    }

    public ShortLocaleConverter(Object obj, Locale locale, String str) {
        this(obj, locale, str, false);
    }

    public ShortLocaleConverter(Object obj, Locale locale, String str, boolean z) {
        super(obj, locale, str, z);
    }

    @Override // org.apache.commons.beanutils.locale.converters.DecimalLocaleConverter, org.apache.commons.beanutils.locale.BaseLocaleConverter
    protected Object parse(Object obj, String str) throws ParseException {
        Object obj2 = super.parse(obj, str);
        if (obj2 == null || (obj2 instanceof Short)) {
            return obj2;
        }
        Number number = (Number) obj2;
        if (number.longValue() != number.shortValue()) {
            throw new ConversionException("Supplied number is not of type Short: " + number.longValue());
        }
        return new Short(number.shortValue());
    }
}
