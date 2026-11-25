package com.android.tools.r8.utils;

import com.android.tools.r8.origin.Origin;

/* JADX INFO: renamed from: com.android.tools.r8.utils.z2, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
final class C3998z2 extends Origin {
    private final String e;

    public C3998z2(String str) {
        super(Origin.unknown());
        this.e = str;
    }

    @Override // com.android.tools.r8.origin.Origin
    public final String part() {
        return this.e;
    }
}
