package com.android.tools.r8.shaking;

import com.android.tools.r8.internal.AbstractC1639am;
import java.util.List;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class D3 extends E3 {
    private final List a;

    private D3(AbstractC1639am abstractC1639am) {
        this.a = abstractC1639am;
    }

    @Override // com.android.tools.r8.shaking.E3
    final boolean a(String str) {
        for (C3 c3 : this.a) {
            if (C3.a(0, 0, c3.b, str)) {
                return !c3.a;
            }
        }
        return false;
    }
}
