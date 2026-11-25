package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;

/* JADX INFO: renamed from: com.android.tools.r8.internal.k10, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2351k10 implements InterfaceC0989Fn {
    private static final C2351k10 a = new C2351k10();

    private C2351k10() {
    }

    public static C2351k10 b() {
        return a;
    }

    @Override // com.android.tools.r8.internal.InterfaceC0989Fn
    /* JADX INFO: renamed from: a */
    public final InterfaceC0989Fn b(C0702m c0702m, AbstractC1476Wj abstractC1476Wj, AbstractC1476Wj abstractC1476Wj2) {
        return this;
    }

    @Override // com.android.tools.r8.internal.InterfaceC0989Fn
    public final InterfaceC0989Fn a(com.android.tools.r8.graph.proto.c cVar) {
        return this;
    }

    @Override // com.android.tools.r8.internal.InterfaceC0989Fn
    public final boolean isUnknown() {
        return true;
    }

    public final String toString() {
        return "UnknownInstanceFieldInitializationInfo";
    }
}
