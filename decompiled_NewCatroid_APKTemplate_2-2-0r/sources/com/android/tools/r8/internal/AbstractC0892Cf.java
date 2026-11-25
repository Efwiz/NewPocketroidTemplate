package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.InterfaceC0593a0;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Cf, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC0892Cf {
    public static AbstractC0892Cf a(com.android.tools.r8.shaking.L2 l2) {
        return (l2 == null || !l2.z()) ? new C1446Vf() : new C3199vH(l2.a(new C0855Bf()));
    }

    public abstract void a(com.android.tools.r8.utils.A1 a1);

    public abstract boolean a(C0767v2 c0767v2);

    public final boolean a(InterfaceC0593a0 interfaceC0593a0) {
        return a(interfaceC0593a0.C());
    }
}
