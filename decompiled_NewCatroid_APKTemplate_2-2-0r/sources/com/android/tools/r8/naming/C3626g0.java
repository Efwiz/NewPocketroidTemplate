package com.android.tools.r8.naming;

import com.android.tools.r8.graph.C0702m;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.naming.g0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3626g0 extends AbstractC3613c0 {
    private final C3624f0 e;

    public C3626g0(C0702m c0702m) {
        super(c0702m.Y().D0().n(), c0702m.Y().D0().y());
        this.e = new C3624f0();
    }

    public final String a(Predicate predicate, String str) {
        String strA;
        do {
            strA = a(str.toCharArray(), this.e);
        } while (predicate.test(strA));
        return strA;
    }
}
