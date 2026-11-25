package com.android.tools.r8.internal;

import java.util.regex.Matcher;

/* JADX INFO: renamed from: com.android.tools.r8.internal.gW, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2086gW extends AbstractC2160hW {
    private C2086gW() {
        super();
    }

    @Override // com.android.tools.r8.internal.AbstractC2160hW
    final String a() {
        return "(?:(\\p{javaJavaIdentifierStart}[-\\p{javaJavaIdentifierPart}]*|\\<init\\>|\\<clinit\\>))";
    }

    @Override // com.android.tools.r8.internal.AbstractC2160hW
    final InterfaceC2240iW a(final String str) {
        return new InterfaceC2240iW() { // from class: com.android.tools.r8.internal.gW$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.internal.InterfaceC2240iW
            public final boolean a(WV wv, Matcher matcher) {
                return C2086gW.a(str, wv, matcher);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(String str, WV wv, Matcher matcher) {
        int iStart = matcher.start(str);
        if (iStart == -1) {
            return false;
        }
        wv.d(iStart, matcher.end(str));
        return true;
    }
}
