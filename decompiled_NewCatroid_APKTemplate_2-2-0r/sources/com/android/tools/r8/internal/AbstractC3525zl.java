package com.android.tools.r8.internal;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import name.antonsmirnov.firmata.FormatHelper;
import org.apache.commons.compress.archivers.tar.TarConstants;
import org.apache.commons.lang3.time.TimeZones;
import org.eclipse.jdt.internal.compiler.util.Util;

/* JADX INFO: renamed from: com.android.tools.r8.internal.zl, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC3525zl {
    private static final TimeZone a = TimeZone.getTimeZone("UTC");

    public static Date a(String str, ParsePosition parsePosition) throws ParseException {
        int i;
        int i2;
        int iA;
        int iA2;
        int length;
        TimeZone timeZone;
        char cCharAt;
        int length2;
        try {
            int index = parsePosition.getIndex();
            int i3 = index + 4;
            int iA3 = a(str, index, i3);
            if (a(str, '-', i3)) {
                i3 = index + 5;
            }
            int i4 = i3 + 2;
            int iA4 = a(str, i3, i4);
            if (a(str, '-', i4)) {
                i4 = i3 + 3;
            }
            int i5 = i4 + 2;
            int iA5 = a(str, i4, i5);
            boolean zA = a(str, Util.C_TYPE_VARIABLE, i5);
            if (!zA && str.length() <= i5) {
                GregorianCalendar gregorianCalendar = new GregorianCalendar(iA3, iA4 - 1, iA5);
                parsePosition.setIndex(i5);
                return gregorianCalendar.getTime();
            }
            if (zA) {
                int i6 = i4 + 5;
                int iA6 = a(str, i4 + 3, i6);
                if (a(str, ':', i6)) {
                    i6 = i4 + 6;
                }
                int i7 = i6 + 2;
                int iA7 = a(str, i6, i7);
                if (a(str, ':', i7)) {
                    i7 = i6 + 3;
                }
                if (str.length() <= i7 || (cCharAt = str.charAt(i7)) == 'Z' || cCharAt == '+' || cCharAt == '-') {
                    i2 = iA7;
                    iA = 0;
                    iA2 = 0;
                    i5 = i7;
                    i = iA6;
                } else {
                    int i8 = i7 + 2;
                    iA2 = a(str, i7, i8);
                    if (iA2 > 59 && iA2 < 63) {
                        iA2 = 59;
                    }
                    if (a(str, '.', i8)) {
                        int i9 = i7 + 3;
                        int i10 = i7 + 4;
                        while (true) {
                            if (i10 >= str.length()) {
                                length2 = str.length();
                                break;
                            }
                            char cCharAt2 = str.charAt(i10);
                            if (cCharAt2 >= '0' && cCharAt2 <= '9') {
                                i10++;
                            }
                            length2 = i10;
                            break;
                        }
                        int iMin = Math.min(length2, i7 + 6);
                        iA = a(str, i9, iMin);
                        int i11 = iMin - i9;
                        if (i11 == 1) {
                            iA *= 100;
                        } else if (i11 == 2) {
                            iA *= 10;
                        }
                        i = iA6;
                        i5 = length2;
                        i2 = iA7;
                    } else {
                        i = iA6;
                        i5 = i8;
                        i2 = iA7;
                        iA = 0;
                    }
                }
            } else {
                i = 0;
                i2 = 0;
                iA = 0;
                iA2 = 0;
            }
            if (str.length() <= i5) {
                throw new IllegalArgumentException("No time zone indicator");
            }
            char cCharAt3 = str.charAt(i5);
            if (cCharAt3 == 'Z') {
                timeZone = a;
                length = i5 + 1;
            } else {
                if (cCharAt3 != '+' && cCharAt3 != '-') {
                    throw new IndexOutOfBoundsException("Invalid time zone indicator '" + cCharAt3 + FormatHelper.QUOTE);
                }
                String strSubstring = str.substring(i5);
                if (strSubstring.length() < 5) {
                    strSubstring = strSubstring + TarConstants.VERSION_POSIX;
                }
                length = i5 + strSubstring.length();
                if ("+0000".equals(strSubstring) || "+00:00".equals(strSubstring)) {
                    timeZone = a;
                } else {
                    String str2 = TimeZones.GMT_ID + strSubstring;
                    TimeZone timeZone2 = TimeZone.getTimeZone(str2);
                    String id2 = timeZone2.getID();
                    if (!id2.equals(str2) && !id2.replace(":", "").equals(str2)) {
                        throw new IndexOutOfBoundsException("Mismatching time zone indicator: " + str2 + " given, resolves to " + timeZone2.getID());
                    }
                    timeZone = timeZone2;
                }
            }
            GregorianCalendar gregorianCalendar2 = new GregorianCalendar(timeZone);
            gregorianCalendar2.setLenient(false);
            gregorianCalendar2.set(1, iA3);
            gregorianCalendar2.set(2, iA4 - 1);
            gregorianCalendar2.set(5, iA5);
            gregorianCalendar2.set(11, i);
            gregorianCalendar2.set(12, i2);
            gregorianCalendar2.set(13, iA2);
            gregorianCalendar2.set(14, iA);
            parsePosition.setIndex(length);
            return gregorianCalendar2.getTime();
        } catch (IllegalArgumentException | IndexOutOfBoundsException | NumberFormatException e) {
            String str3 = str == null ? null : "\"" + str + FormatHelper.QUOTE;
            String message = e.getMessage();
            if (message == null || message.isEmpty()) {
                message = "(" + e.getClass().getName() + Constant.AFTER_QUTO;
            }
            ParseException parseException = new ParseException("Failed to parse date [" + str3 + "]: " + message, parsePosition.getIndex());
            parseException.initCause(e);
            throw parseException;
        }
    }

    private static boolean a(String str, char c, int i) {
        return i < str.length() && str.charAt(i) == c;
    }

    private static int a(String str, int i, int i2) {
        int i3;
        int i4;
        if (i < 0 || i2 > str.length() || i > i2) {
            throw new NumberFormatException(str);
        }
        if (i < i2) {
            i4 = i + 1;
            int iDigit = Character.digit(str.charAt(i), 10);
            if (iDigit < 0) {
                throw new NumberFormatException("Invalid number: " + str.substring(i, i2));
            }
            i3 = -iDigit;
        } else {
            i3 = 0;
            i4 = i;
        }
        while (i4 < i2) {
            int i5 = i4 + 1;
            int iDigit2 = Character.digit(str.charAt(i4), 10);
            if (iDigit2 < 0) {
                throw new NumberFormatException("Invalid number: " + str.substring(i, i2));
            }
            i3 = (i3 * 10) - iDigit2;
            i4 = i5;
        }
        return -i3;
    }
}
