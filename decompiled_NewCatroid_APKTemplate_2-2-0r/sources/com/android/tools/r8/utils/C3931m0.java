package com.android.tools.r8.utils;

/* JADX INFO: renamed from: com.android.tools.r8.utils.m0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
class C3931m0 implements InterfaceC3925l0 {
    static final /* synthetic */ boolean c = true;
    private final Object a;
    private int b;

    private C3931m0(Object obj) {
        this.b = 1;
        this.a = obj;
    }

    @Override // com.android.tools.r8.utils.InterfaceC3925l0
    public final boolean b() {
        return this.b == 2;
    }

    @Override // com.android.tools.r8.utils.InterfaceC3925l0
    public final Object c() {
        return this.a;
    }
}
