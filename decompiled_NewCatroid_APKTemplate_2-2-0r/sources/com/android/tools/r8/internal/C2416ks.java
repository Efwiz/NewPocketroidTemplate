package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import java.util.IdentityHashMap;

/* JADX INFO: renamed from: com.android.tools.r8.internal.ks, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2416ks extends AbstractC3424yG {
    public static final /* synthetic */ int o = 0;
    private final R4 n;

    public C2416ks(C0702m c0702m, M4 m4, M4 m5, IdentityHashMap identityHashMap, Q4 q4) {
        super(c0702m, m4, m5, identityHashMap);
        this.n = q4;
    }

    @Override // com.android.tools.r8.internal.AbstractC3424yG, com.android.tools.r8.internal.DH
    public final C0684j2 f(C0684j2 c0684j2) {
        R4 r4 = this.n;
        Object objC = this.i.c(c0684j2);
        if (objC == null) {
            objC = c0684j2;
        }
        Object obj = (C0684j2) objC;
        Object objC2 = r4.c(c0684j2);
        if (objC2 != null) {
            obj = objC2;
        }
        return (C0684j2) obj;
    }

    @Override // com.android.tools.r8.internal.AbstractC3424yG
    public final C0684j2 h(C0684j2 c0684j2) {
        J4 j4 = this.i;
        Object objB = this.n.b(c0684j2);
        if (objB == null) {
            objB = c0684j2;
        }
        Object obj = (C0684j2) objB;
        Object objB2 = j4.b(c0684j2);
        if (objB2 != null) {
            obj = objB2;
        }
        return (C0684j2) obj;
    }

    public final R4 m() {
        return this.n;
    }
}
