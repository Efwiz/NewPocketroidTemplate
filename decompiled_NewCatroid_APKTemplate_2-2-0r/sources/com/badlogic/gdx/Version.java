package com.badlogic.gdx;

import com.badlogic.gdx.utils.GdxRuntimeException;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/Version.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class Version {
    public static final int MAJOR;
    public static final int MINOR;
    public static final int REVISION;
    public static final String VERSION;

    static {
        String string = "1.13.1".toString();
        VERSION = string;
        try {
            String[] strArrSplit = string.split("\\.");
            int iIntValue = 0;
            MAJOR = strArrSplit.length < 1 ? 0 : Integer.valueOf(strArrSplit[0]).intValue();
            MINOR = strArrSplit.length < 2 ? 0 : Integer.valueOf(strArrSplit[1]).intValue();
            if (strArrSplit.length >= 3) {
                iIntValue = Integer.valueOf(strArrSplit[2]).intValue();
            }
            REVISION = iIntValue;
        } catch (Throwable th) {
            throw new GdxRuntimeException("Invalid version " + VERSION, th);
        }
    }

    public static boolean isHigher(int i, int i2, int i3) {
        return isHigherEqual(i, i2, i3 + 1);
    }

    public static boolean isHigherEqual(int i, int i2, int i3) {
        int i4 = MAJOR;
        if (i4 != i) {
            return i4 > i;
        }
        int i5 = MINOR;
        if (i5 != i2) {
            return i5 > i2;
        }
        return REVISION >= i3;
    }

    public static boolean isLower(int i, int i2, int i3) {
        return isLowerEqual(i, i2, i3 - 1);
    }

    public static boolean isLowerEqual(int i, int i2, int i3) {
        int i4 = MAJOR;
        if (i4 != i) {
            return i4 < i;
        }
        int i5 = MINOR;
        if (i5 != i2) {
            return i5 < i2;
        }
        return REVISION <= i3;
    }
}
