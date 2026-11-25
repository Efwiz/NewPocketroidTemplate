package com.android.tools.r8.internal;

import java.util.logging.Level;
import java.util.logging.Logger;

/* JADX INFO: renamed from: com.android.tools.r8.internal.nY, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC2619nY {
    public static String a(int i, String str) {
        int i2 = AbstractC3123uM.a;
        if (i <= 1) {
            if (i >= 0) {
                return i == 0 ? "" : str;
            }
            throw new IllegalArgumentException(a("invalid count: %s", Integer.valueOf(i)));
        }
        int length = str.length();
        long j = ((long) length) * ((long) i);
        int i3 = (int) j;
        if (i3 != j) {
            throw new ArrayIndexOutOfBoundsException(new StringBuilder(51).append("Required array size too large: ").append(j).toString());
        }
        char[] cArr = new char[i3];
        str.getChars(0, length, cArr, 0);
        while (true) {
            int i4 = i3 - length;
            if (length >= i4) {
                System.arraycopy(cArr, 0, cArr, length, i4);
                return new String(cArr);
            }
            System.arraycopy(cArr, 0, cArr, length, length);
            length <<= 1;
        }
    }

    public static String a(String str, Object... objArr) {
        int iIndexOf;
        String string;
        int i = 0;
        for (int i2 = 0; i2 < objArr.length; i2++) {
            Object obj = objArr[i2];
            if (obj == null) {
                string = "null";
            } else {
                try {
                    string = obj.toString();
                } catch (Exception e) {
                    String name2 = obj.getClass().getName();
                    String hexString = Integer.toHexString(System.identityHashCode(obj));
                    String string2 = new StringBuilder(String.valueOf(hexString).length() + name2.length() + 1).append(name2).append('@').append(hexString).toString();
                    Logger logger = Logger.getLogger("com.google.common.base.Strings");
                    Level level = Level.WARNING;
                    String strValueOf = String.valueOf(string2);
                    logger.log(level, strValueOf.length() != 0 ? "Exception during lenientFormat for ".concat(strValueOf) : new String("Exception during lenientFormat for "), (Throwable) e);
                    String name3 = e.getClass().getName();
                    string = new StringBuilder(name3.length() + String.valueOf(string2).length() + 9).append("<").append(string2).append(" threw ").append(name3).append(">").toString();
                }
            }
            objArr[i2] = string;
        }
        StringBuilder sb = new StringBuilder((objArr.length * 16) + str.length());
        int i3 = 0;
        while (i < objArr.length && (iIndexOf = str.indexOf("%s", i3)) != -1) {
            sb.append((CharSequence) str, i3, iIndexOf);
            sb.append(objArr[i]);
            i3 = iIndexOf + 2;
            i++;
        }
        sb.append((CharSequence) str, i3, str.length());
        if (i < objArr.length) {
            sb.append(" [");
            sb.append(objArr[i]);
            for (int i4 = i + 1; i4 < objArr.length; i4++) {
                sb.append(", ");
                sb.append(objArr[i4]);
            }
            sb.append(']');
        }
        return sb.toString();
    }
}
