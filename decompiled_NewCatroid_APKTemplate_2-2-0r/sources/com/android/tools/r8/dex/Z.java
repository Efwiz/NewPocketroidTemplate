package com.android.tools.r8.dex;

import com.android.tools.r8.internal.C3405y10;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class Z extends c0 {
    public Z(d0 d0Var, String str) {
        super(d0Var, str);
    }

    @Override // com.android.tools.r8.dex.c0
    public final boolean a() {
        return false;
    }

    @Override // com.android.tools.r8.dex.c0
    public final char b() {
        return '.';
    }

    @Override // com.android.tools.r8.dex.c0
    public final boolean a(char c) {
        throw new C3405y10();
    }

    @Override // com.android.tools.r8.dex.c0
    public final boolean b(int i, int i2) {
        char cCharAt;
        if (i <= 0 || ((cCharAt = this.a.charAt(i - 1)) != '.' && cCharAt != '-')) {
            if (a(i2)) {
                return true;
            }
            char cCharAt2 = this.a.charAt(i2);
            if (cCharAt2 != '.' && cCharAt2 != '-') {
                return true;
            }
        }
        return false;
    }

    @Override // com.android.tools.r8.dex.c0
    protected final boolean a(int i, int i2) {
        throw new C3405y10();
    }
}
