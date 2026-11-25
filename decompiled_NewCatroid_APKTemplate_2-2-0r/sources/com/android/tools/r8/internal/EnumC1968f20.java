package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0767v2;
import name.antonsmirnov.firmata.FormatHelper;

/* JADX INFO: renamed from: com.android.tools.r8.internal.f20, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public enum EnumC1968f20 {
    OBJECT,
    INT,
    FLOAT,
    INT_OR_FLOAT,
    INT_OR_FLOAT_OR_OBJECT,
    LONG,
    DOUBLE,
    LONG_OR_DOUBLE;

    public final boolean a() {
        return this == OBJECT;
    }

    public final boolean b() {
        return (this == INT_OR_FLOAT || this == LONG_OR_DOUBLE || this == INT_OR_FLOAT_OR_OBJECT) ? false : true;
    }

    public final int c() {
        return (this == LONG || this == DOUBLE || this == LONG_OR_DOUBLE) ? 2 : 1;
    }

    public final XM d() {
        int iOrdinal = ordinal();
        if (iOrdinal == 1) {
            return AbstractC3472z00.k();
        }
        if (iOrdinal == 2) {
            return AbstractC3472z00.j();
        }
        if (iOrdinal == 5) {
            return AbstractC3472z00.l();
        }
        if (iOrdinal == 6) {
            return AbstractC3472z00.i();
        }
        throw new C3405y10("Unexpected type in conversion to primitive: " + this);
    }

    public static EnumC1968f20 a(EnumC1812d20 enumC1812d20) {
        int i2 = AbstractC1885e20.a[enumC1812d20.ordinal()];
        if (i2 == 1) {
            return OBJECT;
        }
        if (i2 == 2) {
            return INT;
        }
        if (i2 == 3) {
            return FLOAT;
        }
        if (i2 == 4) {
            return LONG;
        }
        if (i2 == 5) {
            return DOUBLE;
        }
        throw new C3405y10("Unexpected value type: " + enumC1812d20);
    }

    public static EnumC1968f20 a(EnumC2523mE enumC2523mE) {
        switch (enumC2523mE) {
            case OBJECT:
                return OBJECT;
            case BOOLEAN_OR_BYTE:
            case CHAR:
            case SHORT:
            case INT:
                return INT;
            case FLOAT:
                return FLOAT;
            case LONG:
                return LONG;
            case DOUBLE:
                return DOUBLE;
            case INT_OR_FLOAT:
                return INT_OR_FLOAT;
            case LONG_OR_DOUBLE:
                return LONG_OR_DOUBLE;
            default:
                throw new C3405y10("Unexpected member type: " + enumC2523mE);
        }
    }

    public static EnumC1968f20 a(char c) {
        if (c != 'F') {
            if (c != 'L') {
                if (c != 'S') {
                    if (c == 'V') {
                        throw new C3089ts("No value type for void type.");
                    }
                    if (c != 'I') {
                        if (c == 'J') {
                            return LONG;
                        }
                        if (c != 'Z') {
                            if (c != '[') {
                                switch (c) {
                                    case 'B':
                                    case 'C':
                                        break;
                                    case 'D':
                                        return DOUBLE;
                                    default:
                                        throw new C3405y10("Invalid descriptor char '" + c + FormatHelper.QUOTE);
                                }
                            }
                        }
                    }
                }
                return INT;
            }
            return OBJECT;
        }
        return FLOAT;
    }

    public static EnumC1968f20 a(C0767v2 c0767v2) {
        return a((char) c0767v2.e.e[0]);
    }

    public static EnumC1968f20 a(EnumC2527mI enumC2527mI) {
        switch (AbstractC1885e20.c[enumC2527mI.ordinal()]) {
            case 1:
            case 2:
            case 3:
            case 4:
                return INT;
            case 5:
                return FLOAT;
            case 6:
                return LONG;
            case 7:
                return DOUBLE;
            default:
                throw new C3405y10("Invalid numeric type '" + enumC2527mI + FormatHelper.QUOTE);
        }
    }

    public static EnumC1968f20 a(AbstractC3472z00 abstractC3472z00) {
        if (abstractC3472z00.I()) {
            return OBJECT;
        }
        if (!abstractC3472z00.A() && !abstractC3472z00.C()) {
            if (abstractC3472z00.B()) {
                return FLOAT;
            }
            if (abstractC3472z00.D()) {
                return LONG;
            }
            if (abstractC3472z00.z()) {
                return DOUBLE;
            }
            if (abstractC3472z00.K()) {
                return INT_OR_FLOAT;
            }
            if (abstractC3472z00.M()) {
                return LONG_OR_DOUBLE;
            }
            if (abstractC3472z00.L()) {
                return INT_OR_FLOAT_OR_OBJECT;
            }
            throw new C3405y10("Unexpected conversion of type: " + abstractC3472z00);
        }
        return INT;
    }
}
