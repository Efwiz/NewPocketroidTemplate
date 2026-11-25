package com.android.tools.r8.naming;

import com.android.tools.r8.MapIdEnvironment;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class K0 implements MapIdEnvironment {
    final /* synthetic */ String a;

    K0(String str) {
        this.a = str;
    }

    @Override // com.android.tools.r8.MapIdEnvironment
    public final String getMapHash() {
        return this.a;
    }
}
