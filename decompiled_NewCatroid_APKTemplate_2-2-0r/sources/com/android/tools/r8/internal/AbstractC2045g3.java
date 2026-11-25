package com.android.tools.r8.internal;

import com.huawei.hms.ml.language.common.utils.Constant;

/* JADX INFO: renamed from: com.android.tools.r8.internal.g3, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC2045g3 {
    public static void a(int i, int i2, int i3) {
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
}
