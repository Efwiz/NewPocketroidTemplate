package com.android.tools.r8.utils;

import com.android.tools.r8.internal.AbstractC1256Pa;

/* JADX INFO: renamed from: com.android.tools.r8.utils.i, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
final class C3906i extends AbstractC1256Pa {
    private String c;

    private C3906i() {
        super(589824);
    }

    @Override // com.android.tools.r8.internal.AbstractC1256Pa
    public final void a(int i, int i2, String str, String str2, String str3, String[] strArr) {
        this.c = str;
    }

    final String b() {
        return "L" + this.c + ";";
    }
}
