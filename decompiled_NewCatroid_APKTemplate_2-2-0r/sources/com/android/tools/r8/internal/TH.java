package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.naming.AbstractC3649o0;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class TH extends AbstractC3361xV implements InterfaceC3014sn {
    static final TH c = new TH();
    static final /* synthetic */ boolean d = true;

    private TH() {
    }

    @Override // com.android.tools.r8.internal.AbstractC2826q4, com.android.tools.r8.internal.InterfaceC1348Si
    public final InterfaceC3054tM F() {
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC2826q4, com.android.tools.r8.internal.InterfaceC1348Si
    public final boolean J() {
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC2826q4, com.android.tools.r8.internal.InterfaceC1348Si
    public final TH O() {
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC2826q4, com.android.tools.r8.internal.InterfaceC1348Si
    public final C0767v2 a(C0722p1 c0722p1) {
        return C0722p1.S5;
    }

    public final String toString() {
        return "null";
    }

    @Override // com.android.tools.r8.internal.AbstractC2826q4, com.android.tools.r8.internal.InterfaceC1348Si
    public final InterfaceC2782pV v() {
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC2826q4, com.android.tools.r8.internal.InterfaceC1348Si
    public final InterfaceC3014sn x() {
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC2826q4, com.android.tools.r8.internal.InterfaceC1348Si
    public final C0767v2 a(C0722p1 c0722p1, C0767v2 c0767v2) {
        return C0722p1.S5;
    }

    @Override // com.android.tools.r8.internal.InterfaceC1348Si
    public final Object a(AbstractC1476Wj abstractC1476Wj, AbstractC3649o0 abstractC3649o0) {
        return JK.f;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.tools.r8.internal.InterfaceC2782pV
    public final InterfaceC2782pV a(C0702m c0702m, InterfaceC2782pV interfaceC2782pV) {
        if (this == interfaceC2782pV) {
            return this;
        }
        AbstractC2826q4 abstractC2826q4 = (AbstractC2826q4) interfaceC2782pV;
        abstractC2826q4.getClass();
        if (!(abstractC2826q4 instanceof FK) && !interfaceC2782pV.isPrimitive() && !interfaceC2782pV.r()) {
            if (d || interfaceC2782pV.m()) {
                return interfaceC2782pV;
            }
            throw new AssertionError();
        }
        return FK.c;
    }
}
