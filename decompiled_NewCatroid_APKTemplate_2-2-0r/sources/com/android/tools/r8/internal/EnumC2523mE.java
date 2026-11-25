package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.mE, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public enum EnumC2523mE {
    OBJECT,
    BOOLEAN_OR_BYTE,
    CHAR,
    SHORT,
    INT,
    FLOAT,
    LONG,
    DOUBLE,
    INT_OR_FLOAT,
    LONG_OR_DOUBLE;

    public final boolean a() {
        return (this == INT_OR_FLOAT || this == LONG_OR_DOUBLE) ? false : true;
    }

    public static EnumC2523mE a(EnumC2523mE enumC2523mE, EnumC1968f20 enumC1968f20) {
        switch (enumC1968f20.ordinal()) {
            case 0:
                EnumC2523mE enumC2523mE2 = OBJECT;
                if (enumC2523mE == enumC2523mE2) {
                    return enumC2523mE2;
                }
                return null;
            case 1:
                EnumC2523mE enumC2523mE3 = INT;
                if (enumC2523mE == enumC2523mE3 || enumC2523mE == INT_OR_FLOAT) {
                    return enumC2523mE3;
                }
                return null;
            case 2:
                EnumC2523mE enumC2523mE4 = FLOAT;
                if (enumC2523mE == enumC2523mE4 || enumC2523mE == INT_OR_FLOAT) {
                    return enumC2523mE4;
                }
                return null;
            case 3:
                if (enumC2523mE == INT || enumC2523mE == FLOAT || enumC2523mE == INT_OR_FLOAT) {
                    return enumC2523mE;
                }
                return null;
            case 4:
                if (enumC2523mE == INT || enumC2523mE == FLOAT || enumC2523mE == OBJECT || enumC2523mE == INT_OR_FLOAT) {
                    return enumC2523mE;
                }
                return null;
            case 5:
                EnumC2523mE enumC2523mE5 = LONG;
                if (enumC2523mE == enumC2523mE5 || enumC2523mE == LONG_OR_DOUBLE) {
                    return enumC2523mE5;
                }
                return null;
            case 6:
                EnumC2523mE enumC2523mE6 = DOUBLE;
                if (enumC2523mE == enumC2523mE6 || enumC2523mE == LONG_OR_DOUBLE) {
                    return enumC2523mE6;
                }
                return null;
            case 7:
                if (enumC2523mE == LONG || enumC2523mE == DOUBLE || enumC2523mE == LONG_OR_DOUBLE) {
                    return enumC2523mE;
                }
                return null;
            default:
                throw new C3405y10("Unexpected type constraint: " + enumC1968f20);
        }
    }
}
