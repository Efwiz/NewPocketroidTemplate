package org.jetbrains.kotlin.it.unimi.dsi.fastutil;

import com.huawei.hms.ml.language.common.utils.Constant;

/* JADX INFO: loaded from: classes5.dex */
public class Arrays {
    public static void ensureFromTo(int i, int i2, int i3) {
        if (i2 < 0) {
            throw new ArrayIndexOutOfBoundsException("Start index (" + i2 + ") is negative");
        }
        if (i2 > i3) {
            throw new IllegalArgumentException("Start index (" + i2 + ") is greater than end index (" + i3 + Constant.AFTER_QUTO);
        }
        if (i3 > i) {
            throw new ArrayIndexOutOfBoundsException("End index (" + i3 + ") is greater than array length (" + i + Constant.AFTER_QUTO);
        }
    }

    public static void ensureOffsetLength(int i, int i2, int i3) {
        if (i2 < 0) {
            throw new ArrayIndexOutOfBoundsException("Offset (" + i2 + ") is negative");
        }
        if (i3 < 0) {
            throw new IllegalArgumentException("Length (" + i3 + ") is negative");
        }
        int i4 = i2 + i3;
        if (i4 > i) {
            throw new ArrayIndexOutOfBoundsException("Last index (" + i4 + ") is greater than array length (" + i + Constant.AFTER_QUTO);
        }
    }
}
