package com.android.tools.r8.internal;

import java.io.Reader;
import java.io.StringReader;

/* JADX INFO: renamed from: com.android.tools.r8.internal.h9, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2136h9 extends C2052g9 {
    protected C2136h9(String str) {
        super(str);
    }

    @Override // com.android.tools.r8.internal.C2052g9, com.android.tools.r8.internal.AbstractC2211i9
    public final Reader a() {
        return new StringReader((String) this.a);
    }
}
