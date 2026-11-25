package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0722p1;
import java.util.IdentityHashMap;
import java.util.Map;

/* JADX INFO: renamed from: com.android.tools.r8.internal.ai, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1630ai extends AbstractC2094ge {
    static final /* synthetic */ boolean g = true;
    private final Map f;

    private C1630ai(IdentityHashMap identityHashMap, C0722p1 c0722p1, AbstractC1476Wj abstractC1476Wj) {
        super(c0722p1, abstractC1476Wj);
        this.f = identityHashMap;
    }

    public static C1556Zh l() {
        return new C1556Zh();
    }

    @Override // com.android.tools.r8.internal.AbstractC2094ge, com.android.tools.r8.internal.DH
    protected final C1237Oh b(C1237Oh c1237Oh) {
        boolean z = g;
        if (!z && c1237Oh.g()) {
            throw new AssertionError();
        }
        if (!z && c1237Oh.c()) {
            throw new AssertionError();
        }
        C1210Nh c1210Nh = (C1210Nh) C1237Oh.d().b((com.android.tools.r8.graph.Y0) c1237Oh.b());
        com.android.tools.r8.graph.Y0 y0 = (com.android.tools.r8.graph.Y0) c1237Oh.b();
        return ((C1210Nh) c1210Nh.a((com.android.tools.r8.graph.Y0) this.f.getOrDefault(y0, y0))).a();
    }

    @Override // com.android.tools.r8.internal.AbstractC1476Wj
    public final boolean f() {
        return false;
    }
}
