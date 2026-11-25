package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.utils.C3979v2;

/* JADX INFO: Access modifiers changed from: package-private */
/* JADX INFO: renamed from: com.android.tools.r8.internal.bA, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1671bA implements InterfaceC2743oz, InterfaceC1001Fz, InterfaceC1894eA {
    private final String a;

    private C1671bA(String str) {
        this.a = str;
    }

    @Override // com.android.tools.r8.shaking.InterfaceC3680a0
    public final void a(com.android.tools.r8.graph.Q0 q0) {
    }

    @Override // com.android.tools.r8.internal.InterfaceC2743oz, com.android.tools.r8.internal.InterfaceC1285Pz
    public final boolean a() {
        return true;
    }

    @Override // com.android.tools.r8.internal.InterfaceC2743oz
    public final String m() {
        throw new C3405y10("Should never be called");
    }

    @Override // com.android.tools.r8.internal.InterfaceC2743oz
    public final int[] t() {
        throw new C3405y10("Should never be called");
    }

    public final String toString() {
        return this.a;
    }

    @Override // com.android.tools.r8.internal.InterfaceC2743oz
    public final C3979v2 a(C0702m c0702m, AbstractC0727q0 abstractC0727q0) {
        throw new C3405y10("Should never be called");
    }
}
