package com.android.tools.r8.internal;

import java.util.HashMap;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class LY {
    static final /* synthetic */ boolean c = true;
    private final HashMap a = new HashMap();
    private final HashMap b = new HashMap();

    public void a(com.android.tools.r8.dex.code.B1 b1) {
        int iU = b1.u();
        int iX = b1.x() + iU;
        this.b.put(Integer.valueOf(iX), new KY(iU));
        if (this.a.containsKey(Integer.valueOf(iX))) {
            a((com.android.tools.r8.dex.code.d4) this.a.remove(Integer.valueOf(iX)));
        }
    }

    public int[] b(int i) {
        return ((KY) this.b.get(Integer.valueOf(i))).c;
    }

    public void a(com.android.tools.r8.dex.code.d4 d4Var) {
        int iU = d4Var.u();
        KY ky = (KY) this.b.get(Integer.valueOf(iU));
        if (ky == null) {
            this.a.put(Integer.valueOf(iU), d4Var);
            return;
        }
        int[] iArrP = d4Var.P();
        int[] iArr = new int[iArrP.length];
        for (int i = 0; i < iArrP.length; i++) {
            iArr[i] = ky.a + iArrP[i];
        }
        ky.b = iArr;
        ky.c = d4Var.N();
        d4Var.O();
    }

    public int[] a(int i) {
        return ((KY) this.b.get(Integer.valueOf(i))).b;
    }

    public final void a() {
        this.b.clear();
    }
}
