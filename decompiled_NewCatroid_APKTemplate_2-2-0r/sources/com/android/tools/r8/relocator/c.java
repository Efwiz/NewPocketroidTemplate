package com.android.tools.r8.relocator;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.J3;
import com.android.tools.r8.graph.Y0;
import com.android.tools.r8.internal.AbstractC2178hm;
import com.android.tools.r8.naming.AbstractC3647n0;
import com.android.tools.r8.utils.A1;
import java.util.IdentityHashMap;
import java.util.Map;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class c extends AbstractC3647n0 {
    private final Map d;
    private final Map e;

    private c(IdentityHashMap identityHashMap, AbstractC2178hm abstractC2178hm, C0722p1 c0722p1) {
        super(c0722p1);
        this.d = identityHashMap;
        this.e = abstractC2178hm;
    }

    @Override // com.android.tools.r8.naming.AbstractC3649o0
    public final String a(String str) {
        return (String) this.e.getOrDefault(str, str);
    }

    @Override // com.android.tools.r8.naming.AbstractC3649o0
    protected final C0759u2 a(C0767v2 c0767v2) {
        return (C0759u2) this.d.getOrDefault(c0767v2, c0767v2.e);
    }

    @Override // com.android.tools.r8.naming.AbstractC3649o0
    public final C0759u2 a(J3 j3, A1 a1) {
        return j3.c();
    }

    @Override // com.android.tools.r8.naming.AbstractC3649o0
    public final C0759u2 a(C0684j2 c0684j2) {
        return c0684j2.f;
    }

    @Override // com.android.tools.r8.naming.AbstractC3649o0
    public final C0759u2 a(Y0 y0) {
        return y0.f;
    }
}
