package com.android.tools.r8.internal;

import java.util.List;

/* JADX INFO: renamed from: com.android.tools.r8.internal.yZ, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3443yZ {
    public C3338xA a;
    public C3338xA b;
    public C3338xA c;
    public String d;
    public List e;
    public List f;

    public C3443yZ(C3338xA c3338xA, C3338xA c3338xA2, C3338xA c3338xA3, String str) {
        this.a = c3338xA;
        this.b = c3338xA2;
        this.c = c3338xA3;
        this.d = str;
    }

    public final void a(AbstractC1899eF abstractC1899eF) {
        C3263wA c3263wAD = this.a.d();
        C3263wA c3263wAD2 = this.b.d();
        C3338xA c3338xA = this.c;
        abstractC1899eF.a(c3263wAD, c3263wAD2, c3338xA == null ? null : c3338xA.d(), this.d);
        List list = this.e;
        if (list != null) {
            int size = list.size();
            for (int i = 0; i < size; i++) {
                C3251w00 c3251w00 = (C3251w00) this.e.get(i);
                c3251w00.a(abstractC1899eF.b(c3251w00.d, c3251w00.e, c3251w00.b, true));
            }
        }
        List list2 = this.f;
        if (list2 != null) {
            int size2 = list2.size();
            for (int i2 = 0; i2 < size2; i2++) {
                C3251w00 c3251w01 = (C3251w00) this.f.get(i2);
                c3251w01.a(abstractC1899eF.b(c3251w01.d, c3251w01.e, c3251w01.b, false));
            }
        }
    }
}
