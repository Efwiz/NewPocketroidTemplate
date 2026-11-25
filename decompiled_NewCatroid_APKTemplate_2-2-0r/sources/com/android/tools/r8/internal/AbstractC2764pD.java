package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.pD, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC2764pD {
    public static EnumC2687oD a(int i) {
        for (EnumC2687oD enumC2687oD : (EnumC2687oD[]) EnumC2687oD.values().clone()) {
            if (enumC2687oD.a == i) {
                return enumC2687oD;
            }
        }
        return null;
    }
}
