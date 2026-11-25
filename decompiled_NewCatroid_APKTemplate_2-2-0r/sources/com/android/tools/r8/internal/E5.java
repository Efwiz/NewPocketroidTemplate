package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.utils.InterfaceC3880d;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class E5 extends A5 {
    private static final E5 a = new E5();
    static final /* synthetic */ boolean b = true;

    private E5() {
    }

    public static E5 g() {
        return a;
    }

    @Override // com.android.tools.r8.internal.AbstractC3122uL
    public final AbstractC3122uL a(C0702m c0702m, AbstractC3122uL abstractC3122uL, C0767v2 c0767v2, FW fw, InterfaceC3880d interfaceC3880d) {
        if (abstractC3122uL.c()) {
            if (b || abstractC3122uL == a) {
                return this;
            }
            throw new AssertionError();
        }
        if (abstractC3122uL instanceof C2582n10) {
            return abstractC3122uL;
        }
        boolean z = b;
        if (!z && !abstractC3122uL.d()) {
            throw new AssertionError();
        }
        if (!z) {
            AbstractC2168hc abstractC2168hcA = abstractC3122uL.a();
            abstractC2168hcA.getClass();
            if (!(abstractC2168hcA instanceof C2548mc)) {
                throw new AssertionError();
            }
        }
        return fw.a(abstractC3122uL);
    }
}
