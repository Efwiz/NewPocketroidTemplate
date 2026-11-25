package com.android.tools.r8.internal;

import java.util.regex.Matcher;

/* JADX INFO: renamed from: com.android.tools.r8.internal.kW, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2389kW extends AbstractC2160hW {
    private C2389kW() {
        super();
    }

    @Override // com.android.tools.r8.internal.AbstractC2160hW
    final String a() {
        return ".*";
    }

    @Override // com.android.tools.r8.internal.AbstractC2160hW
    final InterfaceC2240iW a(final String str) {
        return new InterfaceC2240iW() { // from class: com.android.tools.r8.internal.kW$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.internal.InterfaceC2240iW
            public final boolean a(WV wv, Matcher matcher) {
                return this.f$0.a(str, wv, matcher);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a(String str, WV wv, Matcher matcher) {
        int length;
        int iStart = matcher.start(str);
        if (iStart == -1) {
            return false;
        }
        String strGroup = matcher.group(str);
        for (int length2 = strGroup.length(); length2 > 0; length2--) {
            length = length2 - 1;
            char cCharAt = strGroup.charAt(length);
            if (cCharAt != ':' || length2 >= strGroup.length()) {
                if (!Character.isDigit(cCharAt)) {
                    length = strGroup.length();
                }
            }
            int i = length + iStart;
            wv.e(iStart, i);
            int iEnd = matcher.end(str);
            wv.a(Integer.min(i, iEnd), iEnd, true);
            return true;
        }
        length = strGroup.length();
        int i2 = length + iStart;
        wv.e(iStart, i2);
        int iEnd2 = matcher.end(str);
        wv.a(Integer.min(i2, iEnd2), iEnd2, true);
        return true;
    }
}
