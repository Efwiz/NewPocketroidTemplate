package com.android.tools.r8.internal;

import java.util.Iterator;
import java.util.Map;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Dj, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C0927Dj {
    private final Iterator a;
    private Map.Entry b;
    private final boolean c;

    /* synthetic */ C0927Dj(AbstractC0958Ej abstractC0958Ej, int i) {
        this(abstractC0958Ej);
    }

    public final void a(int i, C2547mb c2547mb) {
        while (true) {
            Map.Entry entry = this.b;
            if (entry == null || ((C0985Fj) entry.getKey()).a >= i) {
                return;
            }
            C0985Fj c0985Fj = (C0985Fj) this.b.getKey();
            if (this.c && c0985Fj.b.a() == M20.j && !c0985Fj.c) {
                int i2 = c0985Fj.a;
                B0 b0 = (B0) this.b.getValue();
                c2547mb.g(N20.a(1, 3));
                c2547mb.g(N20.a(2, 0));
                c2547mb.g(i2);
                c2547mb.b(3, b0);
                c2547mb.g(N20.a(1, 4));
            } else {
                C1785ci.a(c0985Fj, this.b.getValue(), c2547mb);
            }
            if (this.a.hasNext()) {
                this.b = (Map.Entry) this.a.next();
            } else {
                this.b = null;
            }
        }
    }

    private C0927Dj(AbstractC0958Ej abstractC0958Ej) {
        Iterator itE = abstractC0958Ej.a.e();
        this.a = itE;
        if (itE.hasNext()) {
            this.b = (Map.Entry) itE.next();
        }
        this.c = false;
    }
}
