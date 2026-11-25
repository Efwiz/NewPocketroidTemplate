package com.android.tools.r8.internal;

import java.io.Reader;

/* JADX INFO: renamed from: com.android.tools.r8.internal.i9, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC2211i9 {
    protected AbstractC2211i9() {
    }

    public static AbstractC2211i9 a(CharSequence charSequence) {
        return charSequence instanceof String ? new C2136h9((String) charSequence) : new C2052g9(charSequence);
    }

    public abstract Reader a();
}
