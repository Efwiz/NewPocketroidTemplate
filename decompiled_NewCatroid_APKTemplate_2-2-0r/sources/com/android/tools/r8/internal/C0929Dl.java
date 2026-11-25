package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0767v2;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Dl, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C0929Dl extends AbstractC1476Wj {
    private static C0929Dl b = new C0929Dl();
    static final /* synthetic */ boolean c = true;

    private C0929Dl() {
    }

    static C0929Dl j() {
        return b;
    }

    @Override // com.android.tools.r8.internal.AbstractC1476Wj
    public final com.android.tools.r8.graph.Y0 a(com.android.tools.r8.graph.Y0 y0) {
        return y0;
    }

    @Override // com.android.tools.r8.internal.AbstractC1476Wj
    /* JADX INFO: renamed from: a */
    public final com.android.tools.r8.graph.Y0 b(AbstractC1476Wj abstractC1476Wj, com.android.tools.r8.graph.Y0 y0) {
        return y0;
    }

    @Override // com.android.tools.r8.internal.AbstractC1476Wj
    public final C0767v2 a(C0767v2 c0767v2) {
        return c0767v2;
    }

    @Override // com.android.tools.r8.internal.AbstractC1476Wj
    public final String a(String str) {
        return str;
    }

    @Override // com.android.tools.r8.internal.AbstractC1476Wj
    /* JADX INFO: renamed from: b */
    public final C0684j2 c(AbstractC1476Wj abstractC1476Wj, C0684j2 c0684j2) {
        return c0684j2;
    }

    @Override // com.android.tools.r8.internal.AbstractC1476Wj
    /* JADX INFO: renamed from: c */
    public final C0767v2 a(AbstractC1476Wj abstractC1476Wj, C0767v2 c0767v2) {
        return c0767v2;
    }

    @Override // com.android.tools.r8.internal.AbstractC1476Wj
    public final com.android.tools.r8.graph.proto.k d(AbstractC1476Wj abstractC1476Wj, C0684j2 c0684j2) {
        return com.android.tools.r8.graph.proto.k.h();
    }

    @Override // com.android.tools.r8.internal.AbstractC1476Wj
    public final boolean f() {
        return false;
    }

    @Override // com.android.tools.r8.internal.AbstractC1476Wj
    public final boolean g() {
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC1476Wj
    public final boolean h() {
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC1476Wj
    public final boolean i() {
        return false;
    }

    @Override // com.android.tools.r8.internal.AbstractC1476Wj
    public final Iterable b(C0767v2 c0767v2) {
        return com.android.tools.r8.utils.I1.b(c0767v2);
    }

    @Override // com.android.tools.r8.internal.AbstractC1476Wj
    public final C0767v2 b(AbstractC1476Wj abstractC1476Wj, C0767v2 c0767v2) {
        if (c || c0767v2.O0()) {
            return c0767v2;
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.internal.AbstractC1476Wj
    public final KE a(C0684j2 c0684j2, C0684j2 c0684j3, EnumC1953et enumC1953et, AbstractC1476Wj abstractC1476Wj) {
        if (!c && abstractC1476Wj != null && !abstractC1476Wj.h()) {
            throw new AssertionError();
        }
        JE jeD = KE.d();
        jeD.a = c0684j2;
        return jeD.a(enumC1953et).a();
    }

    @Override // com.android.tools.r8.internal.AbstractC1476Wj
    protected final C1237Oh a(com.android.tools.r8.graph.Y0 y0, AbstractC1476Wj abstractC1476Wj, InterfaceC1424Uj interfaceC1424Uj) {
        C1210Nh c1210NhD = C1237Oh.d();
        c1210NhD.a = y0;
        return interfaceC1424Uj.a(c1210NhD.a());
    }

    @Override // com.android.tools.r8.internal.AbstractC1476Wj
    protected final KE a(C0684j2 c0684j2, C0684j2 c0684j3, EnumC1953et enumC1953et, AbstractC1476Wj abstractC1476Wj, InterfaceC1450Vj interfaceC1450Vj) {
        return interfaceC1450Vj.a(a(c0684j2, c0684j3, enumC1953et, abstractC1476Wj));
    }
}
