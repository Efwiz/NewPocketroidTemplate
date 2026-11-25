package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Il, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public enum EnumC1076Il {
    EQ,
    GE,
    GT,
    LE,
    LT,
    NE;

    public final EnumC1076Il a() {
        switch (AbstractC1047Hl.a[ordinal()]) {
            case 1:
            case 2:
                return this;
            case 3:
                return LE;
            case 4:
                return LT;
            case 5:
                return GE;
            case 6:
                return GT;
            default:
                throw new C3405y10("Unknown if condition type.");
        }
    }

    public final EnumC1076Il b() {
        switch (AbstractC1047Hl.a[ordinal()]) {
            case 1:
                return NE;
            case 2:
                return EQ;
            case 3:
                return LT;
            case 4:
                return LE;
            case 5:
                return GT;
            case 6:
                return GE;
            default:
                throw new C3405y10("Unknown if condition type.");
        }
    }
}
