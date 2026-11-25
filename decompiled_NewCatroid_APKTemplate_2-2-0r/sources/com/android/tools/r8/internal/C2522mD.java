package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0725p4;
import com.android.tools.r8.graph.C0767v2;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;

/* JADX INFO: renamed from: com.android.tools.r8.internal.mD, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2522mD {
    static final /* synthetic */ boolean s = true;
    private final IdentityHashMap a = new IdentityHashMap();
    private final C0862Bm b = AbstractC1048Hm.g();
    private final IdentityHashMap c = new IdentityHashMap();
    private final C1946em d = AbstractC2178hm.e();
    private final C1946em e = AbstractC2178hm.e();
    private final C1946em f = AbstractC2178hm.e();
    private final C1946em g = AbstractC2178hm.e();
    private final C1946em h = AbstractC2178hm.e();
    private final C1946em i = AbstractC2178hm.e();
    private final C1946em j = AbstractC2178hm.e();
    private final C1946em k = AbstractC2178hm.e();
    private final LinkedHashMap l = new LinkedHashMap();
    private final C1946em m = AbstractC2178hm.e();
    private final C0862Bm n = AbstractC1048Hm.g();
    private final C1946em o = AbstractC2178hm.e();
    private final C0862Bm p = AbstractC1048Hm.g();
    private final C1946em q = AbstractC2178hm.e();
    private final C1946em r = AbstractC2178hm.e();

    C2522mD() {
    }

    public final void a(com.android.tools.r8.graph.Y0 y0, com.android.tools.r8.graph.Y0 y1) {
        this.d.a(y0, y1);
    }

    public final void b(C0767v2 c0767v2, C0767v2 c0767v3) {
        this.c.put(c0767v2, c0767v3);
    }

    public final void c(C0767v2 c0767v2, C0767v2 c0767v3) {
        boolean z = s;
        if (!z && c0767v2 == null) {
            throw new AssertionError();
        }
        if (!z && c0767v3 == null) {
            throw new AssertionError();
        }
        if (!z && c0767v2 == c0767v3) {
            throw new AssertionError();
        }
        if (!z && this.a.containsKey(c0767v2) && this.a.get(c0767v2) != c0767v3) {
            throw new AssertionError();
        }
        this.a.put(c0767v2, c0767v3);
    }

    public final void d(C0684j2 c0684j2, C0684j2 c0684j3) {
        this.f.a(c0684j2, c0684j3);
    }

    public final void a(C0684j2 c0684j2, C0684j2 c0684j3) {
        this.e.a(c0684j2, c0684j3);
    }

    public final void b(C0684j2 c0684j2, C0684j2 c0684j3) {
        this.i.a(c0684j2, c0684j3);
    }

    public final void a(C0767v2 c0767v2, C2556mg c2556mg) {
        this.k.a(c0767v2, c2556mg);
    }

    public final C0767v2 b(C0767v2 c0767v2) {
        return (C0767v2) this.a.get(c0767v2);
    }

    public final void a(C0684j2 c0684j2, C2404kg c2404kg) {
        this.h.a(c0684j2, c2404kg);
    }

    public final void a(C0684j2 c0684j2, C0684j2[] c0684j2Arr) {
        this.j.a(c0684j2, c0684j2Arr);
    }

    public final void a(C0767v2 c0767v2, P20 p20) {
        this.l.put(c0767v2, p20);
    }

    public final void c(C0767v2 c0767v2) {
        if (!s && c0767v2 == null) {
            throw new AssertionError();
        }
        this.b.a(c0767v2);
    }

    public final void a(C0684j2 c0684j2) {
        this.p.a(c0684j2);
    }

    public final void a(C0767v2 c0767v2, C0767v2 c0767v3) {
        this.m.a(c0767v2, c0767v3);
    }

    public final void c(C0684j2 c0684j2, C0684j2 c0684j3) {
        this.g.a(c0684j2, c0684j3);
    }

    public final void a(C0767v2 c0767v2) {
        this.n.a(c0767v2);
    }

    public final void a(C0767v2 c0767v2, C3004sd c3004sd) {
        this.o.a(c0767v2, c3004sd);
    }

    public final void a(C0684j2 c0684j2, C0725p4 c0725p4) {
        this.q.a(c0684j2, c0725p4);
    }

    public final void a(com.android.tools.r8.graph.Y0 y0, com.android.tools.r8.graph.Q2 q2) {
        this.r.a(y0, q2);
    }

    private void a(AbstractC1048Hm abstractC1048Hm) {
        ArrayList arrayList = new ArrayList();
        for (C0767v2 c0767v2 : this.a.keySet()) {
            if (abstractC1048Hm.contains(c0767v2)) {
                arrayList.add(c0767v2);
            }
        }
        if (!arrayList.isEmpty()) {
            throw new C1367Tb("The compilation cannot proceed because the desugared library specification contains ambiguous flags that the compiler cannot interpret: The following types are both rewritten and maintained " + arrayList);
        }
    }

    public final C2597nD a() {
        AbstractC1048Hm abstractC1048HmA = this.b.a();
        a(abstractC1048HmA);
        return new C2597nD(this.a, abstractC1048HmA, this.c, this.d.a(), this.e.a(), this.f.a(), this.g.a(), this.h.a(), this.i.a(), this.j.a(), this.k.a(), this.l, this.m.a(), this.n.a(), this.o.a(), this.p.a(), this.q.a(), this.r.a());
    }
}
