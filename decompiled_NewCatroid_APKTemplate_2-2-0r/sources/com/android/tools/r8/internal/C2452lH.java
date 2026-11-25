package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;

/* JADX INFO: renamed from: com.android.tools.r8.internal.lH, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2452lH extends AbstractC2309jV {
    static final /* synthetic */ boolean c = true;
    private final C0702m b;

    public C2452lH(C0702m c0702m, EnumC0928Dk enumC0928Dk) {
        if (!c && !enumC0928Dk.b()) {
            throw new AssertionError();
        }
        this.b = c0702m;
    }

    @Override // com.android.tools.r8.internal.AbstractC2309jV
    public final boolean a(C0729q2 c0729q2) {
        if (this.b.g0() == null) {
            return true;
        }
        return !this.b.g0().d(c0729q2.d);
    }

    @Override // com.android.tools.r8.internal.WL
    public final String e() {
        return "NotVerticallyMergedIntoSubtype";
    }
}
