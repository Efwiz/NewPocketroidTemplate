package com.android.tools.r8.internal;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.internal.In, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1079In {
    private ConcurrentHashMap a = new ConcurrentHashMap();

    public final C0963En a(int i) {
        return (C0963En) this.a.computeIfAbsent(Integer.valueOf(i), new Function() { // from class: com.android.tools.r8.internal.In$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return new C0963En(((Integer) obj).intValue());
            }
        });
    }

    public static C1136Kn a(C1229Oa c1229Oa, AbstractC3472z00 abstractC3472z00) {
        return new C1136Kn(c1229Oa, abstractC3472z00);
    }
}
