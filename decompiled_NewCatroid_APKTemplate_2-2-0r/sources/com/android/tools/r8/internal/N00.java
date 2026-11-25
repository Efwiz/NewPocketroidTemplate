package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0767v2;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class N00 implements P00 {
    static final /* synthetic */ boolean b = true;
    public final DG a;

    public N00(DG dg) {
        if (!b && dg == null) {
            throw new AssertionError();
        }
        this.a = dg;
    }

    @Override // com.android.tools.r8.internal.P00
    public final C0767v2 a() {
        return this.a.h;
    }

    public final String toString() {
        return "new:" + this.a.h;
    }
}
