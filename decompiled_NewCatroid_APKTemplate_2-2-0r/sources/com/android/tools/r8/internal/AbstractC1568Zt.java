package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Zt, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract /* synthetic */ class AbstractC1568Zt {
    public static /* synthetic */ String a(int i) {
        if (i == 1) {
            return "BEGIN_ARRAY";
        }
        if (i == 2) {
            return "END_ARRAY";
        }
        if (i == 3) {
            return "BEGIN_OBJECT";
        }
        if (i == 4) {
            return "END_OBJECT";
        }
        if (i == 5) {
            return "NAME";
        }
        if (i == 6) {
            return "STRING";
        }
        if (i == 7) {
            return "NUMBER";
        }
        if (i == 8) {
            return "BOOLEAN";
        }
        if (i == 9) {
            return "NULL";
        }
        return i == 10 ? "END_DOCUMENT" : "null";
    }
}
