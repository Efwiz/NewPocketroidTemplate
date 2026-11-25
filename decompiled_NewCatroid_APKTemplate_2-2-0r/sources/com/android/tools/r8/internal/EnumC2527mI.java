package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0767v2;
import name.antonsmirnov.firmata.FormatHelper;

/* JADX INFO: renamed from: com.android.tools.r8.internal.mI, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public enum EnumC2527mI {
    BYTE,
    CHAR,
    SHORT,
    INT,
    LONG,
    FLOAT,
    DOUBLE;

    public final C0767v2 a(C0722p1 c0722p1) {
        switch (AbstractC2453lI.a[ordinal()]) {
            case 1:
                return c0722p1.w1;
            case 2:
                return c0722p1.x1;
            case 3:
                return c0722p1.C1;
            case 4:
                return c0722p1.A1;
            case 5:
                return c0722p1.B1;
            case 6:
                return c0722p1.z1;
            case 7:
                return c0722p1.y1;
            default:
                throw new C3405y10("Invalid numeric type '" + this + FormatHelper.QUOTE);
        }
    }

    public static EnumC2527mI a(C0767v2 c0767v2) {
        byte b = c0767v2.e.e[0];
        if (b == 70) {
            return FLOAT;
        }
        if (b == 83) {
            return SHORT;
        }
        if (b == 73) {
            return INT;
        }
        if (b != 74) {
            switch (b) {
                case 66:
                    return BYTE;
                case 67:
                    return CHAR;
                case 68:
                    return DOUBLE;
                default:
                    return null;
            }
        }
        return LONG;
    }

    public boolean a() {
        return this == LONG || this == DOUBLE;
    }
}
