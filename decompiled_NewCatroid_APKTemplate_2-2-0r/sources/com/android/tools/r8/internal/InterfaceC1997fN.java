package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0744s2;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.InterfaceC0694k5;

/* JADX INFO: renamed from: com.android.tools.r8.internal.fN, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public interface InterfaceC1997fN {
    InterfaceC1997fN a(C0684j2 c0684j2);

    default InterfaceC1997fN a(InterfaceC0694k5 interfaceC0694k5) {
        return a(interfaceC0694k5.getReference());
    }

    InterfaceC1997fN a(C0767v2 c0767v2);

    void a(C0684j2 c0684j2, com.android.tools.r8.graph.o5 o5Var);

    default InterfaceC1997fN a(AbstractC0744s2 abstractC0744s2) {
        abstractC0744s2.getClass();
        if (abstractC0744s2 instanceof C0767v2) {
            return a(abstractC0744s2.w0());
        }
        if (AbstractC1838dN.a || abstractC0744s2.y0()) {
            return a(abstractC0744s2.v0());
        }
        throw new AssertionError();
    }
}
