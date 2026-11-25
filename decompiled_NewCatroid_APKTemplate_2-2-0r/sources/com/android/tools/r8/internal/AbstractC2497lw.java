package com.android.tools.r8.internal;

import androidx.exifinterface.media.ExifInterface;

/* JADX INFO: renamed from: com.android.tools.r8.internal.lw, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC2497lw {
    public static String a(String str) {
        str.getClass();
        str.hashCode();
        switch (str) {
            case "double":
                return "D";
            case "int":
                return "I";
            case "byte":
                return "B";
            case "long":
                return "J";
            case "boolean":
                return "Z";
            case "float":
                return "F";
            case "short":
                return ExifInterface.LATITUDE_SOUTH;
            default:
                StringBuilder sb = new StringBuilder(str.length());
                int length = str.length() - 1;
                while (str.charAt(length) == ']') {
                    if (str.charAt(length - 1) != '[') {
                        throw new C2883qv("Invalid type: " + str);
                    }
                    sb.append('[');
                    length -= 2;
                }
                sb.append('L');
                for (int i = 0; i <= length; i++) {
                    char cCharAt = str.charAt(i);
                    if (cCharAt == '.') {
                        cCharAt = '/';
                    }
                    sb.append(cCharAt);
                }
                sb.append(';');
                return sb.toString();
        }
    }
}
