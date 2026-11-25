package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;

/* JADX INFO: renamed from: com.android.tools.r8.internal.wV, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC3292wV extends B1 implements InterfaceC0989Fn {
    static final /* synthetic */ boolean c = true;

    public abstract AbstractC2412ko a(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var, C2145hI c2145hI, InterfaceC3176v00 interfaceC3176v00);

    public abstract boolean a(C0702m c0702m);

    abstract boolean a(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var);

    @Override // com.android.tools.r8.internal.B1
    /* JADX INFO: renamed from: c, reason: merged with bridge method [inline-methods] and merged with bridge method [inline-methods] */
    public abstract AbstractC3292wV b(C0702m c0702m, AbstractC1476Wj abstractC1476Wj, AbstractC1476Wj abstractC1476Wj2);

    @Override // com.android.tools.r8.internal.B1, com.android.tools.r8.internal.InterfaceC0989Fn
    public final boolean g() {
        return true;
    }

    @Override // com.android.tools.r8.internal.B1, com.android.tools.r8.internal.InterfaceC0989Fn
    public final AbstractC3292wV j() {
        return this;
    }

    @Override // com.android.tools.r8.internal.B1
    public final boolean y() {
        return true;
    }

    public final boolean b(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var) {
        if (!c0702m.q()) {
            return true;
        }
        if (c || c0702m.f().h()) {
            return a(c0702m.h0(), o5Var);
        }
        throw new AssertionError();
    }
}
