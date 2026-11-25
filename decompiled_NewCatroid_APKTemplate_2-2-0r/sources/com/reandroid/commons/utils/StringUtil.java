package com.reandroid.commons.utils;

import java.util.Calendar;

/* JADX INFO: loaded from: classes10.dex */
public class StringUtil {
    public static boolean isEmpty(CharSequence charSequence) {
        if (charSequence == null) {
            return true;
        }
        return isEmpty(charSequence.toString());
    }

    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

    public static String splitThousandComma(long j) {
        char[] charArray = String.valueOf(j).toCharArray();
        int length = charArray.length;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int i2 = length - i;
            if (i > 0 && i2 % 3 == 0) {
                sb.append(',');
            }
            sb.append(charArray[i]);
        }
        return sb.toString();
    }

    public static String toFileSize(long j) {
        int i = 0;
        String[] strArr = {"PB", "TB", "GB", "MB", "KB", "bytes"};
        long j2 = 0;
        long j3 = j;
        while (j3 > 1024) {
            long j4 = j3 / 1024;
            i++;
            j2 = j3 - (1024 * j4);
            j3 = j4;
        }
        if (i >= 6) {
            return String.valueOf(j);
        }
        return j3 + "." + String.format("%03d", Long.valueOf(j2)) + " " + strArr[5 - i];
    }

    public static String toReadableDate(long j) {
        if (j < 9999999999L) {
            j *= 1000;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(j);
        return String.format("%04d", Integer.valueOf(calendar.get(1))) + '-' + String.format("%02d", Integer.valueOf(calendar.get(2) + 1)) + '-' + String.format("%02d", Integer.valueOf(calendar.get(5))) + io.netty.util.internal.StringUtil.SPACE + String.format("%02d", Integer.valueOf(calendar.get(11))) + ':' + String.format("%02d", Integer.valueOf(calendar.get(12))) + ':' + String.format("%02d", Integer.valueOf(calendar.get(13)));
    }
}
