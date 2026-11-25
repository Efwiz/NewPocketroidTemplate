package com.android.tools.r8.naming;

import com.android.tools.r8.utils.A1;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class O {
    static final /* synthetic */ boolean a = true;

    public static String a(C3605b... c3605bArr) throws MappingComposeException {
        return a(new A1(), c3605bArr);
    }

    public static String a(A1 a1, C3605b... c3605bArr) throws Throwable {
        if (!a && c3605bArr.length <= 0) {
            throw new AssertionError();
        }
        C3662t c3662t = new C3662t(a1);
        for (C3605b c3605b : c3605bArr) {
            c3662t.a(c3605b);
        }
        return c3662t.a();
    }
}
