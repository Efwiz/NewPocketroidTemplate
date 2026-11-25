package com.thoughtworks.xstream.converters.time;

import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter;
import io.netty.util.internal.StringUtil;
import java.time.DateTimeException;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.Chronology;
import java.time.chrono.Era;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/converters/time/AbstractChronoLocalDateConverter.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
abstract class AbstractChronoLocalDateConverter<E extends Era> extends AbstractSingleValueConverter {
    private static final Pattern CHRONO_DATE_PATTERN = Pattern.compile("^ (\\w+) (\\d+)-(\\d+)-(\\d+)$");

    protected abstract ChronoLocalDate chronoLocalDateOf(E e, int i, int i2, int i3);

    protected abstract E eraOf(String str);

    AbstractChronoLocalDateConverter() {
    }

    protected ChronoLocalDate parseChronoLocalDate(String str, String str2, Set<Chronology> set) {
        ConversionException conversionException = null;
        if (str == null) {
            return null;
        }
        Iterator<Chronology> it = set.iterator();
        while (it.hasNext()) {
            String id2 = it.next().getId();
            if (str.startsWith(id2 + StringUtil.SPACE)) {
                Matcher matcher = CHRONO_DATE_PATTERN.matcher(str.subSequence(id2.length(), str.length()));
                if (matcher.matches()) {
                    try {
                        Era eraEraOf = eraOf(matcher.group(1));
                        if (eraEraOf != null) {
                            try {
                                return chronoLocalDateOf(eraEraOf, Integer.parseInt(matcher.group(2)), Integer.parseInt(matcher.group(3)), Integer.parseInt(matcher.group(4)));
                            } catch (DateTimeException e) {
                                conversionException = new ConversionException("Cannot parse value as " + str2 + " date", e);
                                break;
                            }
                        }
                    } catch (IllegalArgumentException e2) {
                        conversionException = new ConversionException("Cannot parse value as " + str2 + " date", e2);
                    }
                } else {
                    continue;
                }
            }
        }
        if (conversionException == null) {
            conversionException = new ConversionException("Cannot parse value as " + str2 + " date");
        }
        conversionException.add("value", str);
        throw conversionException;
    }
}
