package com.android.tools.r8.shaking;

/* JADX INFO: renamed from: com.android.tools.r8.shaking.r3, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public enum EnumC3791r3 {
    FIELD,
    ALL_FIELDS,
    ALL,
    ALL_METHODS,
    CLINIT,
    INIT,
    CONSTRUCTOR,
    METHOD;

    public boolean a() {
        return ordinal() <= ALL.ordinal();
    }

    public boolean b() {
        return ordinal() >= ALL.ordinal();
    }
}
