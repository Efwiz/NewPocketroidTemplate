package org.jetbrains.kotlin.com.intellij.util.text;

import com.huawei.hms.ml.language.common.utils.LanguageCodeUtil;
import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.Format;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.Locale;

/* JADX INFO: loaded from: classes3.dex */
public final class OrdinalFormat {
    public static void apply(MessageFormat messageFormat) {
        Format[] formats = messageFormat.getFormats();
        NumberFormat ordinalFormat = null;
        for (int i = 0; i < formats.length; i++) {
            Format format = formats[i];
            if ((format instanceof DecimalFormat) && "ordinal".equals(((DecimalFormat) format).getPositivePrefix())) {
                if (ordinalFormat == null) {
                    ordinalFormat = getOrdinalFormat(messageFormat.getLocale());
                }
                messageFormat.setFormat(i, ordinalFormat);
            }
        }
    }

    private static NumberFormat getOrdinalFormat(Locale locale) {
        if (locale != null) {
            String language = locale.getLanguage();
            if (LanguageCodeUtil.EN.equals(language) || (language != null && language.length() == 0)) {
                return new EnglishOrdinalFormat();
            }
        }
        return new DecimalFormat();
    }

    private static class EnglishOrdinalFormat extends NumberFormat {
        private EnglishOrdinalFormat() {
        }

        @Override // java.text.NumberFormat
        public StringBuffer format(long j, StringBuffer stringBuffer, FieldPosition fieldPosition) {
            return new MessageFormat("{0}").format(new Object[]{OrdinalFormat.formatEnglish(j)}, stringBuffer, fieldPosition);
        }

        @Override // java.text.NumberFormat
        public StringBuffer format(double d, StringBuffer stringBuffer, FieldPosition fieldPosition) {
            throw new IllegalArgumentException("Cannot format non-integer number");
        }

        @Override // java.text.NumberFormat
        public Number parse(String str, ParsePosition parsePosition) {
            throw new UnsupportedOperationException();
        }
    }

    public static String formatEnglish(long j) {
        long jAbs = Math.abs(j) % 100;
        if (jAbs < 11 || jAbs > 13) {
            long j2 = jAbs % 10;
            if (j2 == 1) {
                return j + "st";
            }
            if (j2 == 2) {
                return j + "nd";
            }
            if (j2 == 3) {
                return j + "rd";
            }
        }
        return j + LanguageCodeUtil.TH;
    }
}
