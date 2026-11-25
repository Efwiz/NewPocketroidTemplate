package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;

/* JADX INFO: renamed from: com.android.tools.r8.internal.nW, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2617nW extends S10 {
    static final /* synthetic */ boolean s = true;
    private final int q;
    private final P00 r;

    private C2617nW(P00 p00, AbstractC3472z00 abstractC3472z00, int i) {
        super(-1, abstractC3472z00, null);
        this.q = i;
        this.r = p00;
        if (!s && i < 0) {
            throw new AssertionError();
        }
    }

    public static C2617nW a(P00 p00, int i, C0702m c0702m) {
        return new C2617nW(p00, AbstractC3472z00.a(p00.a(), WH.i(), (C0702m<?>) c0702m), i);
    }

    @Override // com.android.tools.r8.internal.S10
    public final boolean Y() {
        return false;
    }

    public final C2617nW c(int i) {
        return new C2617nW(this.r, w(), i);
    }

    public final int j0() {
        return this.q;
    }

    public final P00 k0() {
        return this.r;
    }

    @Override // com.android.tools.r8.internal.S10
    public final String toString() {
        return "s" + this.q;
    }

    @Override // com.android.tools.r8.internal.S10
    public final void a(boolean z) {
        if (!s && z) {
            throw new AssertionError();
        }
    }
}
