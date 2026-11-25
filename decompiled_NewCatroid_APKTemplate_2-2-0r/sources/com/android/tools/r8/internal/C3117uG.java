package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0741s0;
import com.android.tools.r8.graph.C0684j2;

/* JADX INFO: renamed from: com.android.tools.r8.internal.uG, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3117uG {
    static final /* synthetic */ boolean c = true;
    private final C0684j2 a;
    private final AbstractC0741s0 b;

    public C3117uG(C0684j2 c0684j2, AbstractC0741s0 abstractC0741s0) {
        this.a = c0684j2;
        this.b = abstractC0741s0;
        if (!c && c0684j2.e != abstractC0741s0.t()) {
            throw new AssertionError();
        }
    }
}
