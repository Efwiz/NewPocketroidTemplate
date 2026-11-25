package com.android.tools.r8.utils;

/* JADX INFO: renamed from: com.android.tools.r8.utils.o0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
final class C3941o0 extends C3931m0 implements InterfaceC3936n0 {
    private Object d;

    private C3941o0(Object obj) {
        super(obj);
    }

    @Override // com.android.tools.r8.utils.InterfaceC3936n0
    public final Object a() {
        return this.d;
    }

    @Override // com.android.tools.r8.utils.InterfaceC3936n0
    public final boolean d() {
        return this.d != null;
    }

    @Override // com.android.tools.r8.utils.InterfaceC3936n0
    public final void a(Object obj) {
        this.d = obj;
    }
}
