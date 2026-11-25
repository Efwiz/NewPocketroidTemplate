package com.android.tools.r8.utils;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0754u0;
import com.android.tools.r8.internal.C1367Tb;
import java.util.function.Supplier;

/* JADX INFO: renamed from: com.android.tools.r8.utils.e0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public final class C3887e0 extends X {
    public C3887e0(AbstractC3876c0 abstractC3876c0) {
        super(null, abstractC3876c0);
    }

    public static C3887e0 g() {
        return new C3887e0(null);
    }

    @Override // com.android.tools.r8.utils.X
    final Supplier a(AbstractC0727q0 abstractC0727q0) {
        return (C0754u0) abstractC0727q0;
    }

    @Override // com.android.tools.r8.utils.X
    final com.android.tools.r8.graph.I e() {
        return com.android.tools.r8.graph.I.d;
    }

    @Override // com.android.tools.r8.utils.X
    public final String toString() {
        return "classpath classes: " + super.toString();
    }

    @Override // com.android.tools.r8.utils.X
    final /* bridge */ /* synthetic */ AbstractC0727q0 a(AbstractC0727q0 abstractC0727q0, AbstractC0727q0 abstractC0727q1) {
        a((C0754u0) abstractC0727q0);
        throw null;
    }

    static void a(C0754u0 c0754u0) {
        throw new C1367Tb("Classpath type already present: " + c0754u0.d.r0());
    }
}
