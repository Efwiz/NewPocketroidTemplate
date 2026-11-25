package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import java.util.Set;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Kg, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1129Kg {
    static final /* synthetic */ boolean e = true;
    private final C0729q2 a;
    private final C1749cD b;
    private final Set c = OU.a();
    private Set d = null;

    public C1129Kg(C0702m c0702m, C0729q2 c0729q2, AbstractC1476Wj abstractC1476Wj) {
        if (!e && c0702m.E() != abstractC1476Wj) {
            throw new AssertionError();
        }
        this.a = c0729q2;
        this.b = C1749cD.a(abstractC1476Wj);
    }
}
