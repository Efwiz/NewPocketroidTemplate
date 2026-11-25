package com.android.tools.r8.internal;

import java.util.regex.Matcher;

/* JADX INFO: renamed from: com.android.tools.r8.internal.aW, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1618aW implements InterfaceC2240iW {
    final /* synthetic */ String a;
    final /* synthetic */ AbstractC1698bW b;

    C1618aW(AbstractC1698bW abstractC1698bW, String str) {
        this.b = abstractC1698bW;
        this.a = str;
    }

    @Override // com.android.tools.r8.internal.InterfaceC2240iW
    public final boolean a() {
        return true;
    }

    @Override // com.android.tools.r8.internal.InterfaceC2240iW
    public final boolean a(WV wv, Matcher matcher) {
        int iStart = matcher.start(this.a);
        if (iStart == -1 || matcher.group(this.a).equals("Suppressed")) {
            return false;
        }
        wv.a(iStart, matcher.end(this.a), this.b.b());
        return true;
    }
}
