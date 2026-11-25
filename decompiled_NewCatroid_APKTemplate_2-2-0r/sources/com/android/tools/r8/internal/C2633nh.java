package com.android.tools.r8.internal;

import java.io.IOException;

/* JADX INFO: renamed from: com.android.tools.r8.internal.nh, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2633nh extends EZ {
    private EZ a;
    final /* synthetic */ boolean b;
    final /* synthetic */ boolean c;
    final /* synthetic */ C1862dk d;
    final /* synthetic */ I00 e;
    final /* synthetic */ C2722oh f;

    C2633nh(C2722oh c2722oh, boolean z, boolean z2, C1862dk c1862dk, I00 i00) {
        this.f = c2722oh;
        this.b = z;
        this.c = z2;
        this.d = c1862dk;
        this.e = i00;
    }

    @Override // com.android.tools.r8.internal.EZ
    public final Object a(C1486Wt c1486Wt) throws IOException {
        if (this.b) {
            c1486Wt.v();
            return null;
        }
        EZ ezA = this.a;
        if (ezA == null) {
            ezA = this.d.a(this.f, this.e);
            this.a = ezA;
        }
        return ezA.a(c1486Wt);
    }

    @Override // com.android.tools.r8.internal.EZ
    public final void a(C1799cu c1799cu, Object obj) throws IOException {
        if (this.c) {
            c1799cu.l();
            return;
        }
        EZ ezA = this.a;
        if (ezA == null) {
            ezA = this.d.a(this.f, this.e);
            this.a = ezA;
        }
        ezA.a(c1799cu, obj);
    }
}
