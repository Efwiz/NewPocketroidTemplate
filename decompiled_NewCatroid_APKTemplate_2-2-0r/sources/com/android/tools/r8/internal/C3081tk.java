package com.android.tools.r8.internal;

import java.io.Serializable;

/* JADX INFO: renamed from: com.android.tools.r8.internal.tk, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3081tk extends AbstractC3154uk implements Serializable {
    final byte[] c;

    C3081tk(byte[] bArr) {
        this.c = (byte[]) AbstractC3123uM.a(bArr);
    }

    @Override // com.android.tools.r8.internal.AbstractC3154uk
    final boolean a(AbstractC3154uk abstractC3154uk) {
        C3081tk c3081tk = (C3081tk) abstractC3154uk;
        if (this.c.length != c3081tk.c.length) {
            return false;
        }
        boolean z = true;
        int i = 0;
        while (true) {
            byte[] bArr = this.c;
            if (i >= bArr.length) {
                return z;
            }
            z &= bArr[i] == c3081tk.c[i];
            i++;
        }
    }
}
