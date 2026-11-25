package com.android.tools.r8.utils;

import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0759u2;
import java.util.HashMap;

/* JADX INFO: renamed from: com.android.tools.r8.utils.u2, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public abstract class AbstractC3972u2 {
    private static final AbstractC3972u2 a = new C3960r2();
    private static final AbstractC3972u2 b = new C3964s2();

    public static AbstractC3972u2 a() {
        return b;
    }

    public static AbstractC3972u2 b() {
        return a;
    }

    public abstract C0759u2 a(C0729q2 c0729q2);

    public static AbstractC3972u2 a(HashMap map) {
        return new C3968t2(map);
    }
}
