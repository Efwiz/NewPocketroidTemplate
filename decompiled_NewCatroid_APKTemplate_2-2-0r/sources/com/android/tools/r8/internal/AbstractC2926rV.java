package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;

/* JADX INFO: renamed from: com.android.tools.r8.internal.rV, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC2926rV extends AbstractC3361xV implements VM, InterfaceC2782pV {
    @Override // com.android.tools.r8.internal.AbstractC2826q4, com.android.tools.r8.internal.InterfaceC1348Si
    public final boolean E() {
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC2826q4, com.android.tools.r8.internal.InterfaceC1348Si
    public final InterfaceC3054tM F() {
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC2826q4, com.android.tools.r8.internal.InterfaceC1348Si
    public final AbstractC2926rV H() {
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC2826q4, com.android.tools.r8.internal.InterfaceC1348Si
    public final boolean J() {
        return true;
    }

    public boolean T() {
        return this instanceof C2204i5;
    }

    @Override // com.android.tools.r8.internal.AbstractC2826q4, com.android.tools.r8.internal.InterfaceC1348Si
    public final boolean a() {
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC2826q4, com.android.tools.r8.internal.InterfaceC1348Si
    public final VM asPrimitive() {
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC2826q4, com.android.tools.r8.internal.InterfaceC1348Si
    public final boolean isPrimitive() {
        return true;
    }

    public final String toString() {
        return getTypeName();
    }

    @Override // com.android.tools.r8.internal.AbstractC2826q4, com.android.tools.r8.internal.InterfaceC1348Si
    public final InterfaceC2782pV v() {
        return this;
    }

    @Override // com.android.tools.r8.internal.InterfaceC2782pV
    public final InterfaceC2782pV a(C0702m c0702m, InterfaceC2782pV interfaceC2782pV) {
        if (this == interfaceC2782pV) {
            return this;
        }
        return (T() && interfaceC2782pV.isPrimitive() && interfaceC2782pV.H().T()) ? C3461yr.c : FK.c;
    }
}
