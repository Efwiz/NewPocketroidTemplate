package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.utils.C3979v2;

/* JADX INFO: renamed from: com.android.tools.r8.internal.hM, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2150hM implements InterfaceC2229iM {
    static final /* synthetic */ boolean e = true;
    private final int a;
    private C0684j2 b = null;
    private int c = -1;
    private int d = 1;

    C2150hM(com.android.tools.r8.utils.A1 a1) {
        this.a = a1.X0() ? Integer.MAX_VALUE : 1;
    }

    @Override // com.android.tools.r8.internal.InterfaceC2229iM
    public final C3979v2 a(AbstractC1915eM abstractC1915eM) {
        boolean z = e;
        if (!z && abstractC1915eM.g() == null) {
            throw new AssertionError();
        }
        if (this.b == abstractC1915eM.g()) {
            if (!z && this.c < 0) {
                throw new AssertionError();
            }
            if (abstractC1915eM.e() > this.c && abstractC1915eM.e() - this.c <= this.a) {
                this.d = ((abstractC1915eM.e() - this.c) - 1) + this.d;
            }
        }
        AbstractC1915eM.a aVarA = abstractC1915eM.a();
        int i = this.d;
        this.d = i + 1;
        AbstractC1915eM abstractC1915eMA = aVarA.a(i).a((AbstractC1915eM) null).a();
        this.c = abstractC1915eM.e();
        this.b = abstractC1915eM.g();
        return new C3979v2(abstractC1915eM, abstractC1915eMA);
    }
}
