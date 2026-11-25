package com.crashinvaders.vfx.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;

/* JADX INFO: loaded from: classes8.dex */
public class CommonUtils {
    private static final Color tmpColor = new Color();

    public static int compare(float f, float f2) {
        if (f < f2) {
            return -1;
        }
        return f == f2 ? 0 : 1;
    }

    public static int compare(int i, int i2) {
        if (i < i2) {
            return -1;
        }
        return i == i2 ? 0 : 1;
    }

    public static int compare(boolean z, boolean z2) {
        if (z == z2) {
            return 0;
        }
        return z ? 1 : -1;
    }

    public static float getAlignFactorX(int i) {
        if ((i & 8) != 0) {
            return 0.0f;
        }
        return (i & 16) != 0 ? 1.0f : 0.5f;
    }

    public static float getAlignFactorY(int i) {
        if ((i & 4) != 0) {
            return 0.0f;
        }
        return (i & 2) != 0 ? 1.0f : 0.5f;
    }

    public static <T> T random(T[] tArr) {
        return tArr[MathUtils.random(tArr.length - 1)];
    }

    public static <T extends Comparable<? super T>> int compare(T t, T t2, boolean z) {
        if (t == t2) {
            return 0;
        }
        if (t == null) {
            return z ? 1 : -1;
        }
        if (t2 == null) {
            return z ? -1 : 1;
        }
        return t.compareTo(t2);
    }

    public static boolean stringEquals(String str, String str2) {
        if (str == null && str2 == null) {
            return true;
        }
        return str != null && str.equals(str2);
    }

    public static Color parseHexColor(String str) {
        int length = str.length();
        if (length == 3) {
            return parseHexColor3(str);
        }
        if (length == 4) {
            return parseHexColor4(str);
        }
        if (length == 6) {
            return parseHexColor6(str);
        }
        if (length == 8) {
            return parseHexColor8(str);
        }
        throw new IllegalArgumentException("Wrong format, HEX string value should be either 3, 4, 6 or 8 letters long.");
    }

    public static Color parseHexColor3(String str) {
        if (str.length() != 3) {
            throw new IllegalArgumentException("HEX string value should be exact 3 letters long.");
        }
        return tmpColor.set(Integer.valueOf(str.substring(0, 1), 16).intValue() / 15.0f, Integer.valueOf(str.substring(1, 2), 16).intValue() / 15.0f, Integer.valueOf(str.substring(2, 3), 16).intValue() / 15.0f, 1.0f);
    }

    public static Color parseHexColor4(String str) {
        if (str.length() != 4) {
            throw new IllegalArgumentException("HEX string value should be exact 4 letters long.");
        }
        return tmpColor.set(Integer.valueOf(str.substring(0, 1), 16).intValue() / 15.0f, Integer.valueOf(str.substring(1, 2), 16).intValue() / 15.0f, Integer.valueOf(str.substring(2, 3), 16).intValue() / 15.0f, Integer.valueOf(str.substring(3, 4), 16).intValue() / 15.0f);
    }

    public static Color parseHexColor6(String str) {
        if (str.length() != 6) {
            throw new IllegalArgumentException("HEX string value should be exact 6 letters long.");
        }
        return tmpColor.set(Integer.valueOf(str.substring(0, 2), 16).intValue() / 255.0f, Integer.valueOf(str.substring(2, 4), 16).intValue() / 255.0f, Integer.valueOf(str.substring(4, 6), 16).intValue() / 255.0f, 1.0f);
    }

    public static Color parseHexColor8(String str) {
        if (str.length() != 8) {
            throw new IllegalArgumentException("HEX string value should be exact 8 letters long.");
        }
        return tmpColor.set(Integer.valueOf(str.substring(0, 2), 16).intValue() / 255.0f, Integer.valueOf(str.substring(2, 4), 16).intValue() / 255.0f, Integer.valueOf(str.substring(4, 6), 16).intValue() / 255.0f, Integer.valueOf(str.substring(6, 8), 16).intValue() / 255.0f);
    }
}
