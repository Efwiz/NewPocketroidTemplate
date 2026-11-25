package com.android.tools.r8.naming;

import com.android.tools.r8.AbstractC0402a;
import com.android.tools.r8.graph.C0702m;

/* JADX INFO: renamed from: com.android.tools.r8.naming.d0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3615d0 extends C3617e0 {
    private final String g;

    C3615d0(C0702m c0702m) {
        super(c0702m);
        this.g = AbstractC0402a.a(c0702m.Y().Z, 1, 0);
    }

    @Override // com.android.tools.r8.naming.AbstractC3613c0
    final String b(char[] cArr, I i) {
        String strB = super.b(cArr, i);
        if (cArr.length >= this.g.length() + 1) {
            int i2 = 0;
            while (i2 < this.g.length()) {
                char cCharAt = this.g.charAt(i2);
                i2++;
                if (cCharAt != cArr[i2]) {
                }
            }
            return strB;
        }
        return this.g + strB;
    }
}
