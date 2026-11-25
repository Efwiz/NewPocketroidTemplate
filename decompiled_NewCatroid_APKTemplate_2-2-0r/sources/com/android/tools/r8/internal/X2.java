package com.android.tools.r8.internal;

import com.android.tools.r8.dex.code.C0524x0;
import com.android.tools.r8.dex.code.C0529y0;
import java.util.HashMap;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class X2 {
    static final /* synthetic */ boolean c = true;
    private final HashMap a = new HashMap();
    private final HashMap b = new HashMap();

    public final void a(C0524x0 c0524x0) {
        int iX = c0524x0.x() + c0524x0.u();
        if (!c && this.b.containsKey(Integer.valueOf(iX))) {
            throw new AssertionError();
        }
        this.b.put(Integer.valueOf(iX), new W2());
        if (this.a.containsKey(Integer.valueOf(iX))) {
            a((C0529y0) this.a.remove(Integer.valueOf(iX)));
        }
    }

    public final int b(int i) {
        return ((W2) this.b.get(Integer.valueOf(i))).a;
    }

    public final long c(int i) {
        return ((W2) this.b.get(Integer.valueOf(i))).b;
    }

    public final void a(C0529y0 c0529y0) {
        int iU = c0529y0.u();
        W2 w2 = (W2) this.b.get(Integer.valueOf(iU));
        if (w2 == null) {
            this.a.put(Integer.valueOf(iU), c0529y0);
            return;
        }
        w2.a = c0529y0.e;
        w2.b = c0529y0.f;
        w2.c = c0529y0.g;
    }

    public final short[] a(int i) {
        return ((W2) this.b.get(Integer.valueOf(i))).c;
    }

    public final void a() {
        this.b.clear();
    }
}
