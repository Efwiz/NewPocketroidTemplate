package com.android.tools.r8.tracereferences;

import com.android.tools.r8.internal.AbstractC1256Pa;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class d extends AbstractC1256Pa {
    private String c;

    private d() {
        super(589824);
    }

    @Override // com.android.tools.r8.internal.AbstractC1256Pa
    public final void a(int i, int i2, String str, String str2, String str3, String[] strArr) {
        this.c = str;
    }

    final String b() {
        return this.c;
    }
}
