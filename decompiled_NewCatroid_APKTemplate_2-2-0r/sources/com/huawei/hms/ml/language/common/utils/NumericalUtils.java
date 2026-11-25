package com.huawei.hms.ml.language.common.utils;

import java.math.BigDecimal;

/* JADX INFO: loaded from: classes10.dex */
public class NumericalUtils {
    public static float decimalPlacesIntOrInt(int i, int i2) {
        if (i2 == 0) {
            return 0.0f;
        }
        return new BigDecimal(i / i2).setScale(6, 4).floatValue();
    }

    public static double decimalPlacesIntOrdouble(int i, float f) {
        if (isInputValue(f)) {
            return 0.0d;
        }
        return new BigDecimal(i / f).setScale(4, 4).doubleValue();
    }

    public static float decimalPlacesIntOrFloat(int i, float f) {
        if (isInputValue(f)) {
            return 0.0f;
        }
        return new BigDecimal(i / f).setScale(6, 4).floatValue();
    }

    public static boolean isInputValue(float f) {
        return ((double) Math.abs(f)) < 1.0E-8d;
    }
}
